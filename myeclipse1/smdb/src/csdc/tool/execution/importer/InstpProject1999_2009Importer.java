package csdc.tool.execution.importer;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.InstpApplication;
import csdc.bean.InstpEndinspection;
import csdc.bean.InstpGranted;
import csdc.bean.InstpMember;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.ProductTypeNormalizer;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 导入《1999-2009年基地项目一览表（工作表）_修正导入.xls》
 * (评价中心数据)
 * 撤项已合并至《1999-2011年基地项目立项数据_修正导入.xls》因此这里不导入
 * 内含少量成果数据，待手工录入
 * 
 * 以下项目编号的项目的负责人和服务中心的数据不一致，已改为服务中心的负责人
 * 06JJD780004
 * 08JJD751067
 * 08JJD850208
 * 06JJD790006
 * 07JJD751079
 * 
 * @author xuhan
 * @status 已在smdbtest导入
 * @status 已在smdb导入
 */
public class InstpProject1999_2009Importer extends Importer {
	
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
	
	Set<String> exMsg = new HashSet<String>();
	
	private Officer 张海泽;
	
	
	
	public InstpProject1999_2009Importer() {}

	public InstpProject1999_2009Importer(String filePath) {
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
		张海泽 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '张海泽'");
		SystemOption 优秀 = (SystemOption) dao.query(SystemOption.class, "youxiu");
		SystemOption 合格 = (SystemOption) dao.query(SystemOption.class, "hege");
		SystemOption 不合格 = (SystemOption) dao.query(SystemOption.class, "buhege");
		优秀.getId();
		合格.getId();
		不合格.getId();
		
		getContentFromExcel(0);
		while (next()) {
			System.out.println(curRowIndex);
			
			InstpApplication ba = null;
			InstpGranted bg = null;
			for (String applicantName : E.split("[\\s、]+")) {
				ba = instpProjectFinder.findApplication(C, applicantName);
				if (ba != null) {
					break;
				}
			}
			
			if (ba == null) {
				bg = instpProjectFinder.findGranted(G);
				if (bg != null) {
					ba = bg.getApplication();
				}
			}
			
			//申请库内没有，新建一个
			if (ba == null) {
				ba = new InstpApplication();
				ba.setName(C);
				
				Agency univ = universityFinder.getUnivByName(A);
				ba.setBelongInstitute(instituteFinder.getInstitute(univ, B, false));
				ba.setBelongInstituteName(ba.getBelongInstitute().getName());
				ba.setYear(Integer.parseInt(F));
				ba.setIsImported(1);
				ba.setImportedDate(new Date());

				String[] directorNames = E.split("[\\s、]+");
				String[] directorUnivNames = D.split("[\\s、]+");
				for (int i = 0; i < directorNames.length; i++) {
					addApplicant(ba, directorNames[i], directorUnivNames[Math.min(i, directorUnivNames.length - 1)]);
				}

				saveOrUpdate(ba);
			}

			if (ba.getInstpGranted() == null || ba.getInstpGranted().isEmpty()) {
				bg = new InstpGranted();
				ba.addGranted(bg);
			} else {
				bg = ba.getInstpGranted().iterator().next();
			}
			
			if (!G.isEmpty() && bg.getNumber() != null && !bg.getNumber().equals(G) && !bg.getNumber().startsWith("#")) {
				exMsg.add("立项编号异常：" + C + " - " + E + " - " + G);
			}
			if (!G.isEmpty() && (bg.getNumber() == null || bg.getNumber().startsWith("#"))) {
				bg.setNumber(G);
			}
			
			bg.setName(C);

			bg.setStatus(1);
			bg.setIsImported(1);
			bg.setBelongInstitute(ba.getBelongInstitute());
			bg.setBelongInstituteName(ba.getBelongInstituteName());
			bg.setApplicantId(ba.getApplicantId());
			bg.setApplicantName(ba.getApplicantName());

			//成果形式
			if (!Z.equals(AA)) {
				exMsg.add("成果形式有变化：" + C + " - " + E);
			}
			String[] productType = productTypeNormalizer.getNormalizedProductType(ba.getProductType() + " " + ba.getProductTypeOther() + " " + Z);
			ba.setProductType(productType[0]);
			bg.setProductType(productType[0]);
			ba.setProductTypeOther(productType[1]);
			bg.setProductTypeOther(productType[1]);

			//批准经费
			if (O.length() > 0) {
				bg.setApproveFee(tool.getFee(O));
			}
			if (P.length() > 0) {
				bg.setApproveFeeMinistry(tool.getFee(P));
			}
			if (Q.length() > 0) {
				bg.setApproveFeeUniversity(tool.getFee(Q));
			}
			if (R.length() > 0) {
				bg.setApproveFeeRelevance(tool.getFee(R));
			}
			if (S.length() > 0) {
				bg.setApproveFeeOther(tool.getFee(S));
			}
			
			//结项/鉴定
			if (K.contains("鉴定") || K.contains("结项")) {
				InstpEndinspection bei = new InstpEndinspection();
				bei.setIsImported(1);
				bei.setImportedDate(new Date());
				bg.addEndinspection(bei);
				
				//鉴定材料收到时间、申请时间
				bei.setReceiveMaterialDate(tool.getDate(H));
				if (bei.getReceiveMaterialDate() == null) {
					bei.setReceiveMaterialDate(tool.getDate(M));
				}
				if (bei.getReceiveMaterialDate() == null) {
					bei.setReceiveMaterialDate(tool.getDate(ba.getYear() + 4, 1, 1));
				}
				bei.setApplicantSubmitDate(bei.getReceiveMaterialDate());
				
				if (I.contains("通讯")) {
					bei.setReviewWay(1);
				} else if (I.contains("会议")) {
					bei.setReviewWay(2);
				} else {
					bei.setReviewWay(0);
					bei.setIsApplyNoevaluation(1);
				}
				
				if (J.contains("优秀")) {
					bei.setReviewGrade(优秀);
				} else if (J.contains("不合格")) {
					bei.setReviewGrade(不合格);
				} else if (J.contains("合格")) {
					bei.setReviewGrade(合格);
				}
				
				if (K.contains("结项")) {
					bei.setFinalAuditDate(tool.getDate(M));
					if (bei.getFinalAuditDate() == null) {
						bei.setFinalAuditDate(tool.getDate(H));
					}
					if (bei.getFinalAuditDate() == null) {
						bei.setFinalAuditDate(tool.getDate(ba.getYear() + 4, 1, 1));
					}
					if (bei.getFinalAuditDate().getTime() < bei.getReceiveMaterialDate().getTime()) {
						bei.setFinalAuditDate(bei.getReceiveMaterialDate());
					}
					bei.setReviewDate(bei.getFinalAuditDate());
					bei.setFinalAuditorName(张海泽.getPerson().getName());
					bei.setFinalAuditor(张海泽.getPerson());
					bei.setFinalAuditorAgency(张海泽.getAgency());

					bei.setFinalAuditStatus(3);

					if (K.contains("已结项")) {
						if (bei.getReviewWay() == 0) {
							bei.setFinalAuditResultNoevaluation(2);
						}
						bei.setFinalAuditResultEnd(2);
						bg.setStatus(2);
					} else if (K.contains("未结项")) {
						bei.setFinalAuditResultEnd(1);
					}
				}
			}
		}
		
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
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
		getContentFromExcel(0);
		
		while (next()) {
			Agency univ = universityFinder.getUnivByName(A);
			if (univ == null) {
				exMsg.add("找不到的学校：" + A);
				continue;
			}
			Institute institute = instituteFinder.getInstitute(univ, B, false);
			if (institute == null || !institute.getType().getCode().equals("1") && !institute.getType().getCode().equals("2")) {
				exMsg.add("找不到的部级/省部共建基地：" + A + " - " + B);
			}

		}
		
		if (exMsg.size() > 0) {
			throw new RuntimeException(exMsg.toString().replaceAll(",\\s+", "\n"));
			//System.out.println(exMsg);
		}
	}
}
