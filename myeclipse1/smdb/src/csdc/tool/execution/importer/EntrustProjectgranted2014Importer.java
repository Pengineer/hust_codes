package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.EntrustApplication;
import csdc.bean.EntrustFunding;
import csdc.bean.EntrustGranted;
import csdc.bean.GeneralFunding;
import csdc.bean.Officer;
import csdc.dao.SystemOptionDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.finder.EntrustProjectFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《2014年专项委托项目立项及拨款一览表.xls》
 * @author pengliang
 * 
 * 备注：原先这些项目是按一般项目申请的，将这些申请项目设置为不立项。（在申请入库代码重新为这些项目导入申请数据，类型为委托项目，然后立项）
 */
public class EntrustProjectgranted2014Importer extends Importer {
	
	private ExcelReader reader;
	
	public final static int YEAR = 2014;
	
	@Autowired
	private BeanFieldUtils beanFieldUtils;

	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private DepartmentFinder departmentFinder;

	@Autowired
	private EntrustProjectFinder entrustProjectFinder;

	@Autowired
	private SystemOptionDao systemOptionDao;
	
	@Autowired
	private Tool tool;
		
	private void resetReader() throws Exception {
		reader.readSheet(0);
		reader.setCurrentRowIndex(1);
	}
	
	@Override
	public void work() throws Exception {
		validate();
		importData();
	}

	private void importData() throws Exception {
		resetReader();
		
		departmentFinder.initDeptMap();
		
		Officer 白晓 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '白晓'");

		while (next(reader)) {
			System.out.println((reader.getCurrentRowIndex()-1) + "/" + (reader.getRowNumber()-1));

			EntrustApplication application = entrustProjectFinder.findApplication(C, E, YEAR);
			EntrustGranted granted = null;
			Agency university = universityFinder.getUnivByName(D);
			
			if (application.getEntrustGranted().isEmpty()) {
				granted = new EntrustGranted();
				granted.setStatus(1);
				granted.setIsImported(1);
				granted.setImportedDate(new Date());
				granted.setApproveDate(tool.getDate(2014, 7, 3));
				granted.setPlanEndDate(application.getPlanEndDate());
				application.addGranted(granted);
			} else {
				granted = application.getEntrustGranted().iterator().next();
			}
			
			granted.setUniversity(university);
			granted.setAgencyName(application.getAgencyName());
			
			if (application.getDepartment().getUniversity().getName().equals(university.getName())) {
				granted.setDepartment(application.getDepartment());
				granted.setDivisionName(application.getDivisionName());
			} else {
				Department otherDepartment = departmentFinder.getDepartment(university, null, true);
				granted.setDepartment(otherDepartment);
				granted.setDivisionName(otherDepartment.getName());
			}
			
			granted.setApplicantId(application.getApplicantId());
			granted.setApplicantName(application.getApplicantName());
			granted.setMemberGroupNumber(1);
			
			beanFieldUtils.setField(granted, "name", C, BuiltinMergeStrategies.REPLACE);
//			beanFieldUtils.setField(granted, "planEndDate", tool.getDate(E), BuiltinMergeStrategies.PRECISE_DATE);

//			String[] productType = productTypeNormalizer.getNormalizedProductType(F);
			granted.setProductType(application.getProductType());
			granted.setProductTypeOther(application.getProductTypeOther());
			
			beanFieldUtils.setField(granted, "number", B, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(granted, "approveFee", tool.getFee(F), BuiltinMergeStrategies.REPLACE);
		
			//若没有任何拨款信息，则导入首批拨款
			if (granted.getFunding() == null || granted.getFunding().isEmpty()) {
				EntrustFunding funding = new EntrustFunding();
				funding.setDate(tool.getDate(2014, 9, 17));
				funding.setFee(tool.getFee(G));
				funding.setStatus(1);
				granted.addFunding(funding);
			}
			
			granted.getApplication().setFinalAuditResult(2);
			granted.getApplication().setFinalAuditStatus(3);
			granted.getApplication().setFinalAuditor(白晓);
			granted.getApplication().setFinalAuditorName(白晓.getPerson().getName());
			granted.getApplication().setFinalAuditorAgency(白晓.getAgency());
			granted.getApplication().setFinalAuditorInst(白晓.getInstitute());
			granted.getApplication().setFinalAuditDate(tool.getDate(2014, 7, 3));
		}
		
	}
	
	private void validate() throws Exception {
		resetReader();
		
		HashSet exMsg = new HashSet();
		while (next(reader)) {
			Agency university = universityFinder.getUnivByName(D);
			if (university == null) {
				exMsg.add("不存在的高校: " + D);
			}
			EntrustApplication application = entrustProjectFinder.findApplication(C, E, YEAR);
			if (application == null) {
				exMsg.add("不存在的申请: " + C);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	public EntrustProjectgranted2014Importer(){
	}
	
	public EntrustProjectgranted2014Importer(String file) {
		reader = new ExcelReader(file);
	}


}
