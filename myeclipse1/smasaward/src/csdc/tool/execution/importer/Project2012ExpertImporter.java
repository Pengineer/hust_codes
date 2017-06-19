package csdc.tool.execution.importer;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import csdc.bean.Expert;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.ProjectApplicationReviewUpdate;
import csdc.bean.University;

/**
 * 导入2012年专家库《20111103_2012年专家库整理合并_整合修正导入.xls》
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class Project2012ExpertImporter extends Importer {//Project2012ExpertImporter
	
	private Integer currentYear = 2012;//待评审项目年度
	
	/**
	 * 学校编号+姓名 到 专家实体 的映射
	 */
	private Map<String, Expert> expertMap = new HashMap<String, Expert>();
	
	
	public Project2012ExpertImporter(File file) {
		super(file);
	}
	
	@Override
	public void work() throws Exception {
		initExpertMap();
		importData();
	}

	/**
	 * 初始化expertMap
	 */
	private void initExpertMap() {
		List<Expert> experts = baseService.query("select e from Expert e");
		for (Expert expert : experts) {
			expertMap.put(expert.getUniversityCode() + expert.getName(), expert);
		}
	}

	private void importData() throws Exception {
//		int maxInnerExpertNumber = (Integer) tools.session.createQuery("select max(e.number) from Expert e where e.number < 20000000").uniqueResult();
//		int maxOuterExpertNumber = Math.max(20000000, (Integer) tools.session.createQuery("select max(e.number) from Expert e").uniqueResult());
		int maxInnerExpertNumber = (Integer) baseService.queryUnique("select max(e.number) from Expert e where e.number < 20000000");
		int maxOuterExpertNumber = Math.max(20000000, (Integer) baseService.queryUnique("select max(e.number) from Expert e"));
		
		getContentFromExcel(0);
		
		while (next()) {
			University university = B.length() > 0 ? tools.getUnivByName(B.replaceAll("\\s+", "")) : tools.getUnivByCode(C);
			if (university == null) {
				throw new RuntimeException((B.length() > 0 ? B : C) + " 不存在");
			}
			String univCode = university.getCode();
			String expertName = D.replaceAll("\\s+", "");
			
			Expert existingExpert = expertMap.get(univCode + expertName);
			Expert newExpert = new Expert();

			newExpert.setName(expertName);
			newExpert.setGeneralApplyYears(G.isEmpty() ? "" : "2011");
			
			String generalReviewYears = H.length() > 0 ? "2009" : "";
			if (I.length() > 0) {
				generalReviewYears += (generalReviewYears.length() > 0 ? "; " : "") + "2010";
			}
			if (AP.length() > 0) {
				generalReviewYears += (generalReviewYears.length() > 0 ? "; " : "") + "2011";
			}
			
			newExpert.setGeneralReviewYears(generalReviewYears);
			newExpert.setUniversityCode(univCode);
			newExpert.setGender(K);
			newExpert.setBirthday(Tools.getDate(L));
			newExpert.setSpecialityTitle(M);
			newExpert.setIdCardNumber(J);
			newExpert.setDiscipline(tools.transformDisc(Y + " " + Z + " " + AA + " " + AB + " " + AC + " " + AD));
			newExpert.setHomePhone(T);
			newExpert.setMobilePhone(U);
			newExpert.setOfficePhone(V);
			newExpert.setEmail(W);
			newExpert.setMoeProject(AE);
			newExpert.setNationalProject(AF);
			newExpert.setJob(O);
			newExpert.setIsDoctorTutor(P);
			newExpert.setDepartment(N);
			newExpert.setDegree(R);
			newExpert.setLanguage(S);
			newExpert.setPositionLevel(Q);
			newExpert.setAward(AH);
			newExpert.setResearchField(AI);
			newExpert.setPartTime(X);
			newExpert.setOfficeAddress(AJ);
			newExpert.setOfficePostcode(AK);
			
			String remark = AL;
			if (AM.length() > 0) {
				remark += (remark.isEmpty() ? "" : "; ") + AM;
			}
			newExpert.setRemark(remark);

			newExpert.setIsReviewer(1);
			
			if (A.contains("1")) {
				//高龄
				newExpert.setIsReviewer(0);
				newExpert.setReason("高龄");
			}
			if (A.contains("3")) {
				//校长/党委书记
				newExpert.setRating(999 + "");
				AM.replace("校长/党委书记", "");
			}
			if (A.contains("2") && AM.length() > 0) {
				//学科有问题
				newExpert.setIsReviewer(0);
				newExpert.setReason("学科有问题");
			}
			
			if (AN.length() > 0) {
				//退评
				newExpert.setIsReviewer(0);
				newExpert.setReason(AN);
			}
			
			if (AO.length() > 0) {
				//高优先级，数据来自《20111028_优先专家.xls》
				newExpert.setIsReviewer(1);
				newExpert.setRating(1 + "");
			}
			
			//数据库里找不到，就新加
			if (existingExpert == null) {
				Integer univCodeNumber = Integer.valueOf(univCode);
				if (univCodeNumber > 10000 && univCodeNumber < 50000) {
					//内部专家
					newExpert.setNumber(++maxInnerExpertNumber);
				} else {
					//外部专家
					newExpert.setNumber(++maxOuterExpertNumber);
				}
				addEntity(newExpert);
			} else {
				tools.mergeExpert(existingExpert, newExpert);
			}
			
			//若改成了退评，则删除其当前年所有评审条目
			if (existingExpert != null && existingExpert.getIsReviewer().equals(0) && existingExpert.getApplicationReview() != null) {
				System.out.println(existingExpert.getName());
				for (Iterator iterator = existingExpert.getApplicationReview().iterator(); iterator.hasNext();) {
					ProjectApplicationReview pr = (ProjectApplicationReview) iterator.next();
					if (pr.getYear().equals(currentYear)) {
						ProjectApplicationReviewUpdate pru = new ProjectApplicationReviewUpdate(pr, 0, 0);
//						session.delete(pr);
//						session.save(pru);
						baseService.delete(pr);
						baseService.add(pru);
						iterator.remove();
					}
				}
			}
		}
	}

}
