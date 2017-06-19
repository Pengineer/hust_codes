package csdc.tool.execution.importer;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralVariation;
import csdc.bean.Officer;
import csdc.bean.Teacher;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.ProductTypeNormalizer;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 《2011年一般项目变更情况_修正导入.xls》
 * 
 * @author xuhan
 * 
 */
public class GeneralVariation2011Importer extends Importer {
	
	@Autowired
	private Tool tool;

	@Autowired
	private UnivPersonFinder univPersonFinder;

	@Autowired
	private UniversityFinder universityFinder;

	@Autowired
	private DepartmentFinder departmentFinder;
	
	@Autowired
	private ProductTypeNormalizer productTypeNormalizer;
	
	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	

	
	public GeneralVariation2011Importer() {}
	
	public GeneralVariation2011Importer(String filePath) {
		super(filePath);
	}
	

	@Override
	public void work() throws Exception {
		checkProjectExistence();
		importData();
	}

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		getContentFromExcel(0);
		
		Officer 刘杰 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '刘杰'");

		while (next()) {
			System.out.println(curRowIndex);
			
			GeneralApplication application = generalProjectFinder.findApplication(C, E);
			GeneralGranted granted = application.getGeneralGranted().iterator().next();
			
			GeneralVariation variation = new GeneralVariation();
			granted.addVariation(variation);

			variation.setFinalAuditResultDetail("000000000");
			
			boolean approved = !G.contains("不同意");

			String[] variationContent = F.split("\\s*；\\s*");
			for (String v : variationContent) {
				if (v.contains("变更项目成员为")) {
					changeMember(variation, v, approved);
				} else if (v.contains("变更项目管理单位为")) {
					changeAgency(variation, v, approved);
				} else if (v.contains("改变成果形式为")) {
					changeProductType(variation, v, approved);
				} else if (v.contains("改变项目名称为")) {
					changeName(variation, v, approved && !G.contains("除变更项目名称"));					
				} else if (v.contains("延期至")) {
					postpone(variation, v, approved);				
				} else if (v.contains("研究内容有重大调整")) {
					changeContent(variation, v, approved);				
				} else if (v.contains("其他（")) {
					changeOther(variation, v, approved);
				}
			}
			
			variation.setIsImported(1);
			variation.setImportedDate(new Date());
			variation.setApplicantSubmitDate(tool.getDate(2011, 9, 30));
			variation.setFinalAuditDate(tool.getDate(2011, 9, 30));
			variation.setFinalAuditStatus(3);
			if (approved) {
				variation.setFinalAuditResult(2);
			} else {
				variation.setFinalAuditResult(1);
				variation.setFinalAuditOpinionFeedback(H);
			}
			variation.setFinalAuditorName(刘杰.getPerson().getName());
			variation.setFinalAuditor(刘杰.getPerson());
			variation.setFinalAuditorAgency(刘杰.getAgency());
		}
	}

	/**
	 * 变更项目负责人
	 * @param variation
	 * @param v
	 * @param approved
	 */
	private void changeMember(GeneralVariation variation, String v, boolean approved) {
		GeneralGranted granted = variation.getGranted();

		int colonIndex = v.indexOf("：");
		String newDirectorName = v.substring(colonIndex + 1);
		Teacher newDirector = univPersonFinder.findTeacher(newDirectorName, granted.getUniversity());

		variation.setChangeMember(1);
		variation.setOldDirectorId(granted.getApplicantId());
		variation.setOldDirectorName(granted.getApplicantName());
		variation.setNewDirectorId(newDirector.getPerson().getId());
		variation.setNewDirectorName(newDirector.getPerson().getName());
		
		if (approved) {
			StringBuffer finalAuditResultDetail = new StringBuffer(variation.getFinalAuditResultDetail());
			finalAuditResultDetail.setCharAt(0, '1');
			variation.setFinalAuditResultDetail(finalAuditResultDetail.toString());

			granted.setApplicantId(variation.getNewDirectorId());
			granted.setApplicantName(variation.getNewDirectorName());
		}
	}

	/**
	 * 变更项目管理单位
	 * @param variation
	 * @param v
	 * @param approved
	 */
	private void changeAgency(GeneralVariation variation, String v, boolean approved) {
		GeneralGranted granted = variation.getGranted();
		
		int colonIndex = v.indexOf("：");
		String newAgencyName = v.substring(colonIndex + 1);
		Agency newAgency = universityFinder.getUnivByName(newAgencyName);

		variation.setChangeAgency(1);
		variation.setOldAgencyName(granted.getAgencyName());
		variation.setOldAgency(granted.getUniversity());
		variation.setOldDepartment(granted.getDepartment());
		variation.setOldInstitute(granted.getInstitute());
		variation.setOldDivisionName(granted.getDivisionName());
		variation.setNewAgencyName(newAgency.getName());
		variation.setNewAgency(newAgency);
		variation.setNewDepartment(departmentFinder.getDepartment(newAgency, null, false));
		variation.setNewDivisionName(variation.getNewDepartment().getName());
		
		if (approved) {
			StringBuffer finalAuditResultDetail = new StringBuffer(variation.getFinalAuditResultDetail());
			finalAuditResultDetail.setCharAt(1, '1');
			variation.setFinalAuditResultDetail(finalAuditResultDetail.toString());

			granted.setUniversity(variation.getNewAgency());
			granted.setDepartment(variation.getNewDepartment());
			granted.setInstitute(variation.getNewInstitute());
			granted.setAgencyName(variation.getNewAgencyName());
			granted.setDivisionName(variation.getNewDivisionName());
		}
	}
	
	/**
	 * 改变成果形式为
	 * @param variation
	 * @param v
	 * @param approved
	 * @throws Exception
	 */
	private void changeProductType(GeneralVariation variation, String v, boolean approved) throws Exception {
		GeneralGranted granted = variation.getGranted();
		
		int colonIndex = v.indexOf("：");
		String newProductTypeString = v.substring(colonIndex + 1);
		String[] newProductType = productTypeNormalizer.getNormalizedProductType(newProductTypeString);
		
		variation.setChangeProductType(1);
		variation.setOldProductType(granted.getProductType());
		variation.setOldProductTypeOther(granted.getProductTypeOther());
		variation.setNewProductType(newProductType[0]);
		variation.setNewProductTypeOther(newProductType[1]);
		
		if (approved) {
			StringBuffer finalAuditResultDetail = new StringBuffer(variation.getFinalAuditResultDetail());
			finalAuditResultDetail.setCharAt(2, '1');
			variation.setFinalAuditResultDetail(finalAuditResultDetail.toString());

			granted.setProductType(variation.getNewProductType());
			granted.setProductTypeOther(variation.getNewProductTypeOther());
		}
	}
	

	/**
	 * 改变项目名称
	 * @param variation
	 * @param v
	 * @param approved
	 */
	private void changeName(GeneralVariation variation, String v, boolean approved) {
		GeneralGranted granted = variation.getGranted();
		
		int colonIndex = v.indexOf("：");
		String newProjectName = v.substring(colonIndex + 1);
		
		variation.setChangeName(1);
		variation.setOldName(granted.getName());
		variation.setOldEnglishName(granted.getEnglishName());
		variation.setNewName(newProjectName);
		variation.setNewEnglishName(null);
		
		if (approved) {
			StringBuffer finalAuditResultDetail = new StringBuffer(variation.getFinalAuditResultDetail());
			finalAuditResultDetail.setCharAt(3, '1');
			variation.setFinalAuditResultDetail(finalAuditResultDetail.toString());

			granted.setName(variation.getNewName());
			granted.setEnglishName(variation.getNewEnglishName());
		}
	}
	
	/**
	 * 研究内容有重大调整
	 * @param variation
	 * @param v
	 * @param approved
	 */
	private void changeContent(GeneralVariation variation, String v, boolean approved) {
		variation.setChangeContent(1);
		
		if (approved) {
			StringBuffer finalAuditResultDetail = new StringBuffer(variation.getFinalAuditResultDetail());
			finalAuditResultDetail.setCharAt(4, '1');
			variation.setFinalAuditResultDetail(finalAuditResultDetail.toString());
		}
	}


	/**
	 * 延期
	 * @param variation
	 * @param v
	 * @param approved
	 */
	private void postpone(GeneralVariation variation, String v, boolean approved) {
		GeneralGranted granted = variation.getGranted();
		
		int colonIndex = v.indexOf("：");
		String newPlanFinishTime = v.substring(colonIndex + 1);
		
		variation.setPostponement(1);
		variation.setOldOnceDate(granted.getPlanEndDate());
		variation.setNewOnceDate(tool.getDate(newPlanFinishTime));
		
		if (approved) {
			StringBuffer finalAuditResultDetail = new StringBuffer(variation.getFinalAuditResultDetail());
			finalAuditResultDetail.setCharAt(5, '1');
			variation.setFinalAuditResultDetail(finalAuditResultDetail.toString());

			granted.setPlanEndDate(variation.getNewOnceDate());
		}
	}

	/**
	 * 其他变更
	 * @param variation
	 * @param v
	 * @param approved
	 */
	private void changeOther(GeneralVariation variation, String v, boolean approved) {
		System.out.println(v);
		Matcher matcher = Pattern.compile("[\\(（](.+)[）\\)]").matcher(v);
		matcher.find();
		String otherInfo = (variation.getOtherInfo() == null ? "" : variation.getOtherInfo() + "; ") + matcher.group(1);
		System.out.println(otherInfo);
		
		variation.setOther(1);
		variation.setOtherInfo(otherInfo);

		if (approved) {
			StringBuffer finalAuditResultDetail = new StringBuffer(variation.getFinalAuditResultDetail());
			finalAuditResultDetail.setCharAt(8, '1');
			variation.setFinalAuditResultDetail(finalAuditResultDetail.toString());
		}
	}





	
	/**
	 * 检查申请数据是否库内存在
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws UnsupportedEncodingException 
	 */
	private void checkProjectExistence() throws UnsupportedEncodingException, InstantiationException, IllegalAccessException, Exception {
		Set<String> exMsg = new HashSet<String>();
		
		getContentFromExcel(0);
		
		while (next()) {
			GeneralApplication ga = generalProjectFinder.findApplication(C, E);
			if (ga == null || ga.getGeneralGranted().isEmpty()) {
				exMsg.add("找不到的项目：" + C + " - " + E);
			}
		}
		
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}


}
