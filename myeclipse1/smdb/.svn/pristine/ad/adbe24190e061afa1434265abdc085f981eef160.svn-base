package csdc.tool.execution.importer;

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
import csdc.tool.execution.importer.tool.ProductTypeNormalizer;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《2012年一般项目立项及拨款一览表（新）_修正导入.xls》
 *
 * @author xuhan
 */
public class GeneralProjectGranted2012Importer extends Importer {
	
	private ExcelReader reader;
	
	public final static int YEAR = 2012;
	
	@Autowired
	private BeanFieldUtils beanFieldUtils;

	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private DepartmentFinder departmentFinder;

	@Autowired
	private GeneralProjectFinder generalProjectFinder;

	@Autowired
	private ProductTypeNormalizer productTypeNormalizer;

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

			GeneralApplication application = generalProjectFinder.findApplication(D, H, YEAR);
			GeneralGranted granted = null;
			Agency university = universityFinder.getUnivByName(B);
			
			if (application.getGranted().isEmpty()) {
				granted = new GeneralGranted();
				granted.setStatus(1);
				granted.setIsImported(1);
				application.addGranted(granted);
			} else {
				granted = application.getGranted().iterator().next();
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
			
			if (C.contains("规划")) {
				granted.setSubtype(规划);
			} else if (C.contains("青年")) {
				granted.setSubtype(青年);
			} else if (C.contains("自筹")) {
				granted.setSubtype(自筹);
			} else {
				throw new RuntimeException();
			}
			
			beanFieldUtils.setField(granted, "name", D, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(granted, "planEndDate", tool.getDate(E), BuiltinMergeStrategies.PRECISE_DATE);

			String[] productType = productTypeNormalizer.getNormalizedProductType(F);
			granted.setProductType(productType[0]);
			granted.setProductTypeOther(productType[1]);
			
			beanFieldUtils.setField(granted, "number", G, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(granted, "approveFee", tool.getFee(J), BuiltinMergeStrategies.REPLACE);
		
			//若没有任何拨款信息，则导入首批拨款
			if (granted.getFunding() == null || granted.getFunding().isEmpty()) {
				GeneralFunding funding = new GeneralFunding();
				funding.setDate(tool.getDate(2012, 4, 1));
				funding.setFee(tool.getFee(K));
				granted.addFunding(funding);
			}
			
			granted.getApplication().setFinalAuditResult(2);
		}
		
	}

	
	private void validate() throws Exception {
		resetReader();
		
		HashSet exMsg = new HashSet();
		while (next(reader)) {
			Agency university = universityFinder.getUnivByName(B);
			if (university == null) {
				exMsg.add("不存在的高校: " + B);
			}
			GeneralApplication application = generalProjectFinder.findApplication(D, H, YEAR);
			if (application == null) {
				exMsg.add("不存在的申请: " + D);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	

	public GeneralProjectGranted2012Importer(){
	}
	
	public GeneralProjectGranted2012Importer(String file) {
		reader = new ExcelReader(file);
	}


}
