package csdc.tool.execution.importer;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.InstpApplication;
import csdc.bean.InstpGranted;
import csdc.bean.InstpMember;
import csdc.bean.InstpVariation;
import csdc.bean.Officer;
import csdc.bean.Teacher;
import csdc.service.imp.InstpService;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.importer.tool.ProductTypeNormalizer;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 《2011年基地项目变更情况_修正导入.xls》
 * 
 * 找不到的项目：俄罗斯文学中彼得堡的现代神话意蕴 - 金亚娜
 * 
 * @author xuhan
 * 
 */
public class InstpVariation2011Importer extends Importer {
	
	@Autowired
	private Tool tool;

	@Autowired
	private UnivPersonFinder univPersonFinder;

	@Autowired
	private ProductTypeNormalizer productTypeNormalizer;
	
	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	@Autowired
	private InstpService instpService;
	

	
	public InstpVariation2011Importer() {}
	
	public InstpVariation2011Importer(String filePath) {
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
			
			InstpApplication application = null;
			String[] applicantNames = F.split("、");
			for (String applicantName : applicantNames) {
				application = instpProjectFinder.findApplication(D, applicantName);
				if (application != null) {
					break;
				}
			}
			InstpGranted granted = application.getInstpGranted().iterator().next();
			
			InstpVariation variation = new InstpVariation();
			granted.addVariation(variation);

			variation.setFinalAuditResultDetail("000000000");
			
			boolean approved = !I.contains("不同意");

			String[] variationContent = H.split("\\s*；\\s*");
			for (String v : variationContent) {
				if (v.contains("变更项目成员为")) {
					changeMember(variation, v, approved);
				} else if (v.contains("改变成果形式为")) {
					changeProductType(variation, v, approved);
				} else if (v.contains("改变项目名称为")) {
					changeName(variation, v, approved);					
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
				instpService.variationProject(variation);
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
	private void changeMember(InstpVariation variation, String v, boolean approved) {
		InstpGranted granted = variation.getGranted();

		int colonIndex = v.indexOf("：");
		String newDirectorName = v.substring(colonIndex + 1);
		Teacher newDirector = univPersonFinder.findTeacher(newDirectorName, granted.getBelongInstitute().getSubjection());

		variation.setChangeMember(1);
		variation.setOldDirectorId(granted.getApplicantId());
		variation.setOldDirectorName(granted.getApplicantName());
		variation.setOldDirectorInfo(getDirectorInfo(granted.getApplication().getInstpMember()));
		
		InstpMember newDirectorMember = new InstpMember();
		newDirectorMember.setSpecialistTitle(newDirector.getPerson().getAcademicEntity().getSpecialityTitle());
		newDirectorMember.setMajor(newDirector.getPerson().getAcademicEntity().getMajor());
		newDirectorMember.setMemberType(1);
		newDirectorMember.setIsDirector(1);

		variation.setNewDirectorId(newDirector.getPerson().getId());
		variation.setNewDirectorName(newDirector.getPerson().getName());
		variation.setNewDirectorInfo(getDirectorInfo(Arrays.asList(new InstpMember[]{newDirectorMember})));
		
		if (approved) {
			StringBuffer finalAuditResultDetail = new StringBuffer(variation.getFinalAuditResultDetail());
			finalAuditResultDetail.setCharAt(0, '1');
			variation.setFinalAuditResultDetail(finalAuditResultDetail.toString());
		}
	}

	private String getDirectorInfo(Collection<InstpMember> members) {
		StringBuffer allRes = new StringBuffer();
		for (InstpMember member : members) {
			if (member.getIsDirector() == 0) {
				continue;
			}
			StringBuffer res = new StringBuffer();
			if (member.getSpecialistTitle() != null) {
				res.append(member.getSpecialistTitle());
			}
			res.append("/");
			if (member.getMajor() != null) {
				res.append(member.getMajor());
			}
			res.append("/");
			if (member.getWorkMonthPerYear() != null) {
				res.append(member.getWorkMonthPerYear());
			} else {
				res.append(0);
			}
			res.append("/");
			if (member.getWorkDivision() != null) {
				res.append(member.getWorkDivision());
			}
			res.append("/");
			if (member.getMemberType() != null) {
				res.append(member.getMemberType());
			}
			res.append("/0");

			if (allRes.length() > 0) {
				allRes.append("; ");
			}
			allRes.append(res);
		}
		return allRes.toString();
	}

	/**
	 * 改变成果形式为
	 * @param variation
	 * @param v
	 * @param approved
	 * @throws Exception
	 */
	private void changeProductType(InstpVariation variation, String v, boolean approved) throws Exception {
		InstpGranted granted = variation.getGranted();
		
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
		}
	}
	

	/**
	 * 改变项目名称
	 * @param variation
	 * @param v
	 * @param approved
	 */
	private void changeName(InstpVariation variation, String v, boolean approved) {
		InstpGranted granted = variation.getGranted();
		
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
		}
	}
	
	/**
	 * 研究内容有重大调整
	 * @param variation
	 * @param v
	 * @param approved
	 */
	private void changeContent(InstpVariation variation, String v, boolean approved) {
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
	private void postpone(InstpVariation variation, String v, boolean approved) {
		InstpGranted granted = variation.getGranted();
		
		int colonIndex = v.indexOf("：");
		String newPlanFinishTime = v.substring(colonIndex + 1);
		
		variation.setPostponement(1);
		variation.setOldOnceDate(granted.getPlanEndDate());
		variation.setNewOnceDate(tool.getDate(newPlanFinishTime));
		
		if (approved) {
			StringBuffer finalAuditResultDetail = new StringBuffer(variation.getFinalAuditResultDetail());
			finalAuditResultDetail.setCharAt(5, '1');
			variation.setFinalAuditResultDetail(finalAuditResultDetail.toString());
		}
	}

	/**
	 * 其他变更
	 * @param variation
	 * @param v
	 * @param approved
	 */
	private void changeOther(InstpVariation variation, String v, boolean approved) {
		System.out.println(v);
		Matcher matcher = Pattern.compile("（(.+)）").matcher(v);
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
			InstpApplication application = null;
			String[] applicantNames = F.split("、");
			for (String applicantName : applicantNames) {
				application = instpProjectFinder.findApplication(D, applicantName);
				if (application != null) {
					break;
				}
			}
			if (application == null || application.getInstpGranted().isEmpty()) {
				exMsg.add("找不到的项目：" + D + " - " + F);
			}
		}
		
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}


}
