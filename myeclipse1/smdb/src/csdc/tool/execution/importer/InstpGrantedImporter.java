package csdc.tool.execution.importer;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.InstpApplication;
import csdc.bean.InstpFunding;
import csdc.bean.InstpGranted;
import csdc.bean.InstpMember;
import csdc.bean.InstpMidinspection;
import csdc.bean.InstpVariation;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.Teacher;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.ProductTypeNormalizer;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 导入《1999-2011年基地项目立项数据_修正导入.xls》
 * (说是立项表，其实里面还有中检、撤项、结项各种数据，麻烦得很)
 * (结项数据不导入)
 *
 * (以下项目按年份和负责人可以找到申请数据，但按项目名称和负责人找不到申请数据。疑似项目名称有问题)
 * 项目查找异常：国际货币体系改革路径及基本条件和中国的角色定位：主导国货币或超国家主权货币？——基于国际经验和社会网络关系的实证分析 - 华民
 * 项目查找异常：从马克思的《资本论》到中国特色社会主义的科学发展观 - 聂锦芳
 * 项目查找异常：义务教育阶段思想品德类学科核心能力模型与测评框架研究 - 高峡
 * 项目查找异常：中国特色社会主义发展经济学理论与西部区域经济发展实践研究 - 何炼成
 * 项目查找异常：中国新疆十二木卡姆研究 - 肖学俊
 * 项目查找异常：中国21世纪新课程改革研究 - 张华
 * 项目查找异常：民国时期康区研究的三种学术期刊的整理出版与研究 - 姚乐野
 *
 * @author xuhan
 * @status 已在smdbtest1导入
 */
public class InstpGrantedImporter extends Importer {
	
	@Autowired
	private Tool tool;

	@Autowired
	private UnivPersonFinder univPersonFinder;

	@Autowired
	private ExpertFinder expertFinder;
	
	@Autowired
	private InstituteFinder instituteFinder;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private ProductTypeNormalizer productTypeNormalizer;
	
	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	private Officer 刘杰;
	
	
	
	public InstpGrantedImporter() {}

	public InstpGrantedImporter(String filePath) {
		super(filePath);
	}
	
	@Override
	public void work() throws Exception {
		validateData();
		importData();
	}

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		刘杰 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '刘杰'");
		
		getContentFromExcel(0);
		while (next()) {
			System.out.println(curRowIndex);
			
			InstpApplication ba = null;
			for (String applicantName : F.split("[\\s、]+")) {
				ba = instpProjectFinder.findApplication(D, applicantName);
				if (ba != null) {
					break;
				}
			}
			
			//申请库内没有，新建一个
			if (ba == null) {
				ba = new InstpApplication();
				ba.setName(D);
				
				Agency univ = universityFinder.getUnivByName(B);
				ba.setBelongInstitute(instituteFinder.getInstitute(univ, C, false));
				ba.setBelongInstituteName(ba.getBelongInstitute().getName());
				ba.setYear(Integer.parseInt(A.replaceAll("\\D+", "")));
				ba.setIsImported(1);
				ba.setImportedDate(new Date());

				String[] directorNames = F.split("[\\s、]+");
				String[] directorUnivNames = G.split("[\\s、]+");
				for (int i = 0; i < directorNames.length; i++) {
					addApplicant(ba, directorNames[i], directorUnivNames[Math.min(i, directorUnivNames.length - 1)]);
				}

				saveOrUpdate(ba);
			}
			
			ba.setFinalAuditResult(2);

			InstpGranted bg = null;
			if (ba.getInstpGranted() == null || ba.getInstpGranted().isEmpty()) {
				bg = new InstpGranted();
				ba.addGranted(bg);
			} else {
				bg = ba.getInstpGranted().iterator().next();
			}
			
			bg.setNumber(E);
			bg.setName(D);

			bg.setStatus(1);
			bg.setIsImported(1);
			bg.setBelongInstitute(ba.getBelongInstitute());
			bg.setBelongInstituteName(ba.getBelongInstituteName());
			bg.setApplicantId(ba.getApplicantId());
			bg.setApplicantName(ba.getApplicantName());

			//成果形式
			String[] productType = productTypeNormalizer.getNormalizedProductType(ba.getProductType() + " " + ba.getProductTypeOther() + " " + H);
			ba.setProductType(productType[0]);
			bg.setProductType(productType[0]);
			ba.setProductTypeOther(productType[1]);
			bg.setProductTypeOther(productType[1]);

			//批准经费
			if (I.length() > 0) {
				bg.setApproveFee(tool.getFee(I));
			}
			if (J.length() > 0) {
				bg.setApproveFeeMinistry(tool.getFee(J));
			}
			if (K.length() > 0) {
				bg.setApproveFeeUniversity(tool.getFee(K));
			}
			if (L.length() > 0) {
				bg.setApproveFeeRelevance(tool.getFee(L));
			}

			//首次拨款
			InstpFunding bfFirst = new InstpFunding();
			bfFirst.setDate(tool.getDate(ba.getYear(), 12, 1));
			bfFirst.setFee(tool.getFee(M));
			if (bfFirst.getFee() > 0.0) {
				bg.addFunding(bfFirst);
			}

			//2008年拨款
			InstpFunding bf2008 = new InstpFunding();
			bf2008.setDate(tool.getDate(2008, 12, 1));
			bf2008.setFee(Math.max(tool.getFee(N), tool.getFee(O)));
			if (bf2008.getFee() > 0.0) {
				bg.addFunding(bf2008);
			}
			
			doMidinspection(bg, R);
			doWithdraw(bg, S);
		}
	}
	
	/**
	 * 处理中检
	 * @param bg
	 * @param mid
	 */
	private void doMidinspection(InstpGranted bg, String mid) {
		if (!mid.matches("\\d{4}[YN]")) {
			return;
		}
		Integer year = Integer.valueOf(mid.substring(0, 4));
		boolean pass = (mid.charAt(4) == 'Y');

		InstpMidinspection bmi = new InstpMidinspection();
		bmi.setApplicantSubmitDate(tool.getDate(year, 9, 30));
		bmi.setFinalAuditStatus(3);
		bmi.setFinalAuditResult(pass ? 2 : 1);
		bmi.setFinalAuditorName(刘杰.getPerson().getName());
		bmi.setFinalAuditor(刘杰.getPerson());
		bmi.setFinalAuditorAgency(刘杰.getAgency());
		bmi.setFinalAuditDate(tool.getDate(year, 9, 30));
		bmi.setIsImported(1);
		bmi.setImportedDate(new Date());
		
		bg.addMidinspection(bmi);
	}
	

	/**
	 * 处理撤项
	 * @param bg
	 * @param withdraw
	 */
	private void doWithdraw(InstpGranted bg, String withdraw) {
		String withdrawYear = withdraw.replaceAll("\\D+", "");
		if (withdrawYear.isEmpty()) {
			return;
		}
		Integer year = Integer.valueOf(withdrawYear);

		bg.setEndStopWithdrawDate(tool.getDate(year, 1, 1));
		bg.setStatus(4);

		InstpVariation bv = new InstpVariation();
		bv.setIsImported(1);
		bv.setImportedDate(new Date());
		bv.setWithdraw(1);
		bv.setFinalAuditDate(tool.getDate(year, 1, 1));
		bv.setFinalAuditResult(2);
		bv.setFinalAuditStatus(3);
		
		bg.addVariation(bv);
	}
	
	/**
	 * 添加一个负责人
	 * @param ba
	 * @param personName
	 * @param agencyName
	 * @param title 职称
	 * @param positon 职务
	 * @throws Exception
	 */
	private void addApplicant(InstpApplication ba, String personName, String agencyName) throws Exception {
		if (agencyName.isEmpty()) {
			agencyName = "未知机构";
		}
		
		Person applicant = null;
		InstpMember bm = new InstpMember();
		
		//判断机构名是否学校名称，以判断该负责人是否教师。
		Agency univ = universityFinder.getUnivByName(agencyName);
		if (univ != null) {
			//教师
			Teacher teacher = univPersonFinder.findTeacher(personName, univ);
			applicant = teacher.getPerson();

			bm.setUniversity(teacher.getUniversity());
			bm.setDepartment(teacher.getDepartment());
			bm.setInstitute(teacher.getInstitute());
			bm.setDivisionName(teacher.getDepartment() != null ? teacher.getDepartment().getName() : teacher.getInstitute() != null ? teacher.getInstitute().getName() : null);
			bm.setMemberType(1);
		} else {
			//外部专家
			Expert expert = expertFinder.findExpert(personName, agencyName);
			applicant = expert.getPerson();

			bm.setMemberType(2);
		}

		bm.setMember(applicant);
		bm.setMemberName(applicant.getName());
		bm.setAgencyName(agencyName);
		bm.setIsDirector(1);
		
		Academic academic = applicant.getAcademicEntity();
		bm.setSpecialistTitle(academic.getSpecialityTitle());
		
		ba.addMember(bm);
		bm.setMemberSn(ba.getInstpMember().size());
		
		String applicantName = ba.getApplicantName() == null ? "" : ba.getApplicantName();
		String applicantId = ba.getApplicantId() == null ? "" : ba.getApplicantId();
		if (!applicantName.contains(applicant.getName())) {
			if (applicantId.length() > 0) {
				applicantId += "; ";
			}
			applicantId += applicant.getId();
			
			if (applicantName.length() > 0) {
				applicantName += "; ";
			}
			applicantName += applicant.getName();
			
			ba.setApplicantId(applicantId);
			ba.setApplicantName(applicantName);
		}
	}
	
	/**
	 * 检查基地是否库内存在
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws UnsupportedEncodingException 
	 */
	private void validateData() throws UnsupportedEncodingException, InstantiationException, IllegalAccessException, Exception {
		Set<String> exMsg = new HashSet<String>();
		
		getContentFromExcel(0);
		
		while (next()) {
//			InstpApplication ba1 = instpProjectFinder.findApplication(D, F);
//			InstpApplication ba2 = instpProjectFinder.findApplication(Integer.parseInt(A.replaceAll("\\D+", "")), F);
//			if (ba1 == null && ba2 != null) {
//				exMsg.add("项目查找异常：" + D + " - " + F);
//			}

			Agency univ = universityFinder.getUnivByName(B);
			if (univ == null) {
				exMsg.add("找不到的学校：" + B);
				continue;
			}
			Institute institute = instituteFinder.getInstitute(univ, C, false);
			if (institute == null || !institute.getType().getCode().equals("1") && !institute.getType().getCode().equals("2")) {
				exMsg.add("找不到的部级/省部共建基地：" + B + " - " + C);
			}

		}
		
		if (exMsg.size() > 0) {
			throw new RuntimeException(exMsg.toString().replaceAll(",\\s+", "\n"));
			//System.out.println(exMsg);
		}
	}
}
