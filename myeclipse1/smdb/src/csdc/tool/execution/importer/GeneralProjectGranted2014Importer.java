package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.Officer;
import csdc.bean.SinossProjectApplication;
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
 * 导入《2014年一般项目立项及拨款一览表.xls》
 * @author pengliang
 * 
 * 备注：有5条数据在sheet2中明确指出更换高校，还是按原来的代码入库，添加一条变更数据，变更时间为立项的后一天（审核通过）。
 */
public class GeneralProjectGranted2014Importer extends Importer {
	
	private ExcelReader reader;
	
	public final static int YEAR = 2014;
	
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
	
	int year = 2014;
	
	/*
	 * 本年度一般申请项目总数
	 */
	private List<GeneralApplication> generalApplications;
	
	/*
	 * 本年度不立项的一般申请项目
	 */
	private List<GeneralApplication> ungrantedGeneralApplications;
		
	private void resetReader() throws Exception {
		reader.readSheet(0);
	}
	
	@Override
	public void work() throws Exception {
		resetReader();
		ungrantedGeneralApplication();
		validate();
		importData();
	}

	private void importData() throws Exception {			
		Officer 白晓 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '白晓'");
		
		SystemOption 规划 = systemOptionDao.query("projectType", "011");
		SystemOption 青年 = systemOptionDao.query("projectType", "013");
		SystemOption 自筹 = systemOptionDao.query("projectType", "015");
		规划.getName();
		青年.getName();
		自筹.getName();
		
		departmentFinder.initDeptMap();
		reader.setCurrentRowIndex(0);
		while (next(reader)) {
			System.out.println("本年度立项的一般项目共有：" + reader.getRowNumber() + "条");
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());

			GeneralApplication application = generalProjectFinder.findApplication(E, G, YEAR);
			GeneralGranted granted = null;
			Agency university = universityFinder.getUnivByName(C);
			
			if (application.getGeneralGranted().isEmpty()) {
				granted = new GeneralGranted();
				granted.setStatus(1);
				granted.setIsImported(1);
				granted.setImportedDate(new Date());
				granted.setApproveDate(tool.getDate(2014, 7, 3));
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
			beanFieldUtils.setField(granted, "approveFee", tool.getFee(I), BuiltinMergeStrategies.REPLACE);
		
			//若没有任何拨款信息，则导入首批拨款
			if (granted.getFunding() == null || granted.getFunding().isEmpty()) {
				GeneralFunding funding = new GeneralFunding();
				funding.setDate(tool.getDate(2014, 9, 17));
				funding.setFee(tool.getFee(J));
				funding.setStatus(1);
			//	funding.setType(1); 从15年开始，增加的拨款类型字段
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
		
		//不立项项目(如果申请项目采用的自动化入库代码，此处可不要)
		for(GeneralApplication ungrantedGeneralApplication : ungrantedGeneralApplications){
			ungrantedGeneralApplication.setFinalAuditResult(1);
			ungrantedGeneralApplication.setFinalAuditStatus(3);
			ungrantedGeneralApplication.setFinalAuditor(白晓);
			ungrantedGeneralApplication.setFinalAuditorName(白晓.getPerson().getName());
			ungrantedGeneralApplication.setFinalAuditorAgency(白晓.getAgency());
			ungrantedGeneralApplication.setFinalAuditorInst(白晓.getInstitute());
			ungrantedGeneralApplication.setFinalAuditDate(tool.getDate(2014, 7, 3));
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
	
	/**
	 * 获取本年度不立项的一般项目：没有出现在Excel中的就设置为不立项
	 * @throws Exception 
	 */
	public void ungrantedGeneralApplication() throws Exception{
		generalApplications = dao.query("select pa from ProjectApplication pa where pa.type = 'general' AND pa.year = ?",year);
		System.out.println("本年度申请的一般项目共有：" + generalApplications.size() + "条");
		while(next(reader)){
			GeneralApplication application = generalProjectFinder.findApplication(E, G, YEAR);
			generalApplications.remove(application);
		}
		ungrantedGeneralApplications = generalApplications;
		System.out.println("本年度不立项的一般项目共有：" + ungrantedGeneralApplications.size() + "条");
	}

	public GeneralProjectGranted2014Importer(){
	}
	
	public GeneralProjectGranted2014Importer(String file) {
		reader = new ExcelReader(file);
	}


}
