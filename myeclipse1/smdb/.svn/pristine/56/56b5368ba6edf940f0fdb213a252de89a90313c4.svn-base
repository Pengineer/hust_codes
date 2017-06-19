package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.SystemOption;
import csdc.dao.SystemOptionDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《2013年一般项目立项及拨款一览表_修正导入.xls》
 * 《音乐学习对于人的全面发展影响的研究》此数据在申请中不存在，按申请名称立项，再做名称变更，变更时间就写立项后一天。
 * @author wangyi
 */
public class GeneralProjectGranted2013Importer extends Importer {
	
	private ExcelReader reader;
	
	public final static int YEAR = 2013;
	
	@Autowired
	private BeanFieldUtils beanFieldUtils;

	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private DepartmentFinder departmentFinder;

	@Autowired
	private GeneralProjectFinder generalProjectFinder;

	@Autowired
	private SystemOptionDao systemOptionDao;
	
	@Autowired
	private Tool tool;
	

	
	private void resetReader() throws Exception {
		reader.readSheet(0);
	}
	
	@Override
	public void work() throws Exception {
		validate();
		importData();
	}

	private void importData() throws Exception {
		resetReader();
		
		SystemOption 规划 = systemOptionDao.query("projectType", "011");
		SystemOption 青年 = systemOptionDao.query("projectType", "013");
		SystemOption 自筹 = systemOptionDao.query("projectType", "015");
		规划.getName();
		青年.getName();
		自筹.getName();
		
		departmentFinder.initDeptMap();

		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());

			GeneralApplication application = generalProjectFinder.findApplication(E, G, YEAR);
			GeneralGranted granted = null;
			Agency university = universityFinder.getUnivByName(C);
			
			if (application.getGeneralGranted().isEmpty()) {
				granted = new GeneralGranted();
				granted.setStatus(1);
				granted.setIsImported(1);
				granted.setImportedDate(new Date());
				granted.setApproveDate(tool.getDate(2013, 5, 21));
				granted.setPlanEndDate(application.getPlanEndDate());
				application.addGranted(granted);
			} else {
				granted = application.getGeneralGranted().iterator().next();
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
			
			if (D.contains("规划")) {
				granted.setSubtype(规划);
			} else if (D.contains("青年")) {
				granted.setSubtype(青年);
			} else if (D.contains("自筹")) {
				granted.setSubtype(自筹);
			} else {
				throw new RuntimeException();
			}
			
			beanFieldUtils.setField(granted, "name", E, BuiltinMergeStrategies.REPLACE);
//			beanFieldUtils.setField(granted, "planEndDate", tool.getDate(E), BuiltinMergeStrategies.PRECISE_DATE);

//			String[] productType = productTypeNormalizer.getNormalizedProductType(F);
			granted.setProductType(application.getProductType());
			granted.setProductTypeOther(application.getProductTypeOther());
			
			beanFieldUtils.setField(granted, "number", F, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(granted, "approveFee", tool.getFee(H), BuiltinMergeStrategies.REPLACE);
		
			//若没有任何拨款信息，则导入首批拨款
			if (granted.getFunding() == null || granted.getFunding().isEmpty()) {
				GeneralFunding funding = new GeneralFunding();
				funding.setDate(tool.getDate(2013, 5, 21));
				funding.setFee(tool.getFee(I));
				granted.addFunding(funding);
			}
			
			granted.getApplication().setFinalAuditResult(2);
		}
		
	}

	
	private void validate() throws Exception {
		resetReader();
		
		HashSet exMsg = new HashSet();
		while (next(reader)) {
			Agency university = universityFinder.getUnivByName(C);
			if (university == null) {
				exMsg.add("不存在的高校: " + C);
			}
			GeneralApplication application = generalProjectFinder.findApplication(E, G, YEAR);
			if (application == null) {
				exMsg.add("不存在的申请: " + E);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	

	public GeneralProjectGranted2013Importer(){
	}
	
	public GeneralProjectGranted2013Importer(String file) {
		reader = new ExcelReader(file);
	}


}
