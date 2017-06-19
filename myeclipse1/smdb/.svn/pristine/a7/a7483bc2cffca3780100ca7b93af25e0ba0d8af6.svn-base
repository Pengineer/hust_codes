package csdc.tool.execution.importer;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import csdc.bean.Agency;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralEndinspection;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMember;
import csdc.bean.GeneralMidinspection;
import csdc.bean.GeneralVariation;

/**
 * 导入《1993-2010年一般项目中检、结项及变更数据库_修正导入.xls》
 * Note: 未导入负责人变更信息
 * 
 * 以下项目找不到，其中后期数据未导入，请手工自行处理：
 * 项目 研究型大学图书馆服务能力与用户满意度研究     -- 找不到
 * 项目 农民工子女接受非正规学前教育的民间道路探索     -- 找不到
 * 项目 人文社会科学研究成果评价的理论与方法研究     -- 找不到
 * 项目 儿童心理理论与欺骗发展的关系研究     -- 找不到
 * 项目 1990年代以来中国现代文学研究回瞻（原：1990年代以来中国现代文学研究反思）     -- 找不到
 * 项目 高校人文社会科学研究能力评价研究     -- 找不到
 * 项目 网络信息技术在科研基金项目管理中的应用研究     -- 找不到
 * 项目 中外社会科学评价体系比较研究     -- 找不到
 * 项目 高校人文社会科学“十五”通鉴     -- 找不到
 * 项目 会计信息透明度与投资者保护问题研究     -- 找不到
 * 项目 循环经济模式下我国汽车再制造产业发展理论与实践研究     -- 找不到
 * 项目 考古新发现所见契丹与辽朝的历史文化     -- 找不到
 * 项目 校园文化建设研究     -- 找不到
 * 项目 发达资本主义国家的劳动关系与经济绩效     -- 找不到
 * 项目 湖北省新农村规划基金项目的实践与理念     -- 找不到
 * 项目 印巴冲突中的大国因素研究     -- 找不到
 * 
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class GeneralProject1993_2010MidLateImporter extends Importer {
	
//	private List dataList = new ArrayList();
	
	/**
	 * 项目批准号 or 项目名称    -> 立项实体   
	 */
	private Map<String, GeneralGranted> ggMap = new HashMap<String, GeneralGranted>();
	
	/**
	 * 有拨款数据的项目批准号、项目名称 
	 */
	private Set<String> ggNumbersWithFunding = new HashSet<String>();
	

	public GeneralProject1993_2010MidLateImporter(File file) {
		super(file);
		
		List<GeneralGranted> ggs = baseService.query("select gg from GeneralGranted gg left join fetch gg.application where gg.application.year >= 2005 and gg.application.year <= 2010");
		for (GeneralGranted gg : ggs) {
			ggMap.put(tools.toDBC(gg.getName()).replaceAll("[^\\w\\u4e00-\\u9fa5]+", ""), gg);
			if (gg.getNumber() != null) {
				ggMap.put(tools.toDBC(gg.getNumber()).trim(), gg);
			}
		}
		
		List<GeneralGranted> ggWithFunding = baseService.query("select gg from GeneralFunding gf left join gf.granted gg where gg.application.year >= 2005 and gg.application.year <= 2010");
		for (GeneralGranted gg : ggWithFunding) {
			ggNumbersWithFunding.add(gg.getName().replaceAll("[^\\w\\u4e00-\\u9fa5]+", ""));
			ggNumbersWithFunding.add(gg.getNumber());
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void work() throws Exception {
		getContentFromExcel(0);
		StringBuffer exceptionMsg = new StringBuffer();
		
		while (next()) {
			
			//只弄立项时间在[2005, 2010]的项目
			Integer year = Integer.parseInt(A);
			if (year < 2005 || year > 2010) {
				continue;
			}
			
			//按项目批准号找到项目
			GeneralGranted gg = ggMap.get(F);
			//按项目名称找到项目
			if (gg == null) {
				gg = ggMap.get(C.replaceAll("[^\\w\\u4e00-\\u9fa5]+", ""));
			}
			if (gg == null) {
				exceptionMsg.append("项目 " + C + "     -- 找不到\n");
				continue;
			}
			GeneralApplication ga = gg.getApplication();
			saveOrUpdate(gg);
			saveOrUpdate(ga);
			
			//没有拨款信息的项目导入第一次拨款数据
			if (!ggNumbersWithFunding.contains(F) && !ggNumbersWithFunding.contains(C.replaceAll("[^\\w\\u4e00-\\u9fa5]+", ""))) {
				double fee = tools.getFee(P);
				if (fee > 0.01) {
					GeneralFunding gf = new GeneralFunding();
					gf.setGranted(gg);
					gf.setFee(fee);
					gf.setDate(new Date(year - 1900, 11, 1));
					saveOrUpdate(gf);
				}
			}
			
			//导入中检拨款数据
			double fee = tools.getFee(Q);
			if (fee > 0.01) {
				GeneralFunding gf = new GeneralFunding();
				gf.setGranted(gg);
				gf.setFee(fee);
				gf.setDate(new Date(2 + year - 1900, 8, 1));
				saveOrUpdate(gf);
			}
			
			//导入中检数据
			Matcher matcher = Pattern.compile("(\\d*)([YNyn])").matcher(S);
			while (matcher.find()) {
				Integer gmiYear = matcher.group(1).isEmpty() ? year + 2 : Integer.parseInt(matcher.group(1));
				boolean passed = matcher.group(2).toLowerCase().contains("y");
				GeneralMidinspection gmi = new GeneralMidinspection();
				gmi.setMinistryAuditDate(gmiYear == null ? null : new Date(gmiYear - 1900, 11, 1));
				gmi.setIsApproved(passed ? 2 : 1);
				gmi.setGranted(gg);
				gmi.setIsImported(1);
				gmi.setMinistryAuditStatus(3);
				gmi.setMinistryAuditResult(gmi.getIsApproved());
				saveOrUpdate(gmi);
			}
			
			//导入延期数据
			if (!T.isEmpty()) {
				String danao[] = ("0_" + T).split("\\D+");
				Integer delayYear = danao.length > 1 ? Integer.parseInt(danao[1]) : 8888;
				Integer delayMonth = danao.length > 2 ? Integer.parseInt(danao[2]) : 1;
				Integer delayDate = danao.length > 3 ? Integer.parseInt(danao[3]) : 1;
				Date date = delayYear == 8888 ? null : tools.getDate(delayYear, delayMonth, delayDate);
				GeneralVariation gv = new GeneralVariation();
				gv.setGranted(gg);
				gv.setIsApproved(2);
				gv.setMinistryAuditDate(tools.getDate(2010, 9, 1));
				gv.setPostponedOnce(1);
				gv.setOldOnceDate(ga.getPlanEndDate());
				gv.setNewOnceDate(date);
				gv.setIsImported(1);
				gv.setMinistryAuditStatus(3);
				gv.setMinistryAuditResult(gv.getIsApproved());
				ga.setPlanEndDate(date);
				saveOrUpdate(gv);
			}
			
			//导入结项数据
			GeneralEndinspection passedGei = null;	//通过了的结项条目
			matcher = Pattern.compile("(\\d*)([YNyn])").matcher(U);
			while (matcher.find()) {
				Integer geiYear = matcher.group(1).isEmpty() ? null : Integer.parseInt(matcher.group(1));
				boolean passed = matcher.group(2).toLowerCase().contains("y");
				GeneralEndinspection gei = new GeneralEndinspection();
				gei.setReviewAuditDate(geiYear == null ? null : new Date(geiYear - 1900, 11, 1));
				gei.setIsApproved(passed ? 2 : 1);
				gei.setGranted(gg);
				gei.setIsImported(1);
				gei.setReviewAuditStatus(3);
				gei.setReviewAuditResultEnd(gei.getIsApproved());
				if (passed) {
					gg.setEndStopWithdrawDate(gei.getReviewAuditDate());
					gg.setStatus(2);
					passedGei = gei;
				}
				saveOrUpdate(gei);
			}
			
			//导入备注
			if (!V.isEmpty()) {
				if (gg.getNote() == null) {
					gg.setNote(V);
				} else {
					gg.setNote(gg.getNote() + " " + V);
				}
			}
			
			//导入撤项数据
			if (!W.isEmpty()) {
				GeneralVariation gv = new GeneralVariation();
				gv.setIsApproved(2);
				gv.setGranted(gg);
				gv.setWithdraw(1);

				matcher = Pattern.compile("\\d+").matcher(W);
				if (matcher.find()) {
					gv.setMinistryAuditDate(new Date(Integer.parseInt(matcher.group()) - 1900, 11, 1));
				}
				gv.setIsImported(1);
				gv.setMinistryAuditStatus(3);
				gv.setMinistryAuditResult(gv.getIsApproved());

				gg.setStatus(4);//撤项
				gg.setEndStopWithdrawDate(gv.getMinistryAuditDate());
				gg.setEndStopWithdrawPerson(gv.getMinistryAuditorName());
				gg.setEndStopWithdrawOpinion(gv.getMinistryAuditOpinion());
			}
			
			//导入结项证书编号
			if (passedGei != null && Y.length() > 4) {
				passedGei.setCertificate(Y);
			}
			
			//导入 AA 和 AB 两列乱七八糟的变更信息
			String variString = AA + "；"+ AB;
			String varis[] = variString.split("；");
			for (String vari : varis) {
				if (vari.contains("不同意")) {
					continue;
				}
				boolean found = true;
				
				GeneralVariation gv = new GeneralVariation();
				gv.setGranted(gg);
				gv.setIsApproved(2);
				gv.setMinistryAuditDate(new Date(2010 - 1900, 8, 1));
				gv.setIsImported(1);
				gv.setMinistryAuditStatus(3);
				gv.setMinistryAuditResult(gv.getIsApproved());
				
				
//				//变更管理单位
//				if (vari.contains("变更管理单位")) {
//					gv.setChangeAgency(1);
//					gv.setOldAgency(ga.getUniversity());
//					gv.setOldAgencyName(ga.getAgencyName());
//					gv.setOldDepartment(ga.getDepartment());
//					gv.setOldDeptName(ga.getDivisionName());
//					gv.setOldInstitute(ga.getInstitute());
//				}
//				//变更项目责任人
//				else if (vari.contains("变更项目责任人为") || vari.contains("变更责任人为")) {
//					String newDirName = vari.substring(vari.indexOf("：") + 1);
//					gv.setChangeDirector(1);
//					gv.setOldDirector(ga.getApplicant());
//					gv.setNewDirectorName(newDirName);
//				}
//				else if (vari.contains("变更项目责任人") || vari.equals("变更责任人")) {
//					gv.setChangeDirector(1);
//				}
				//改成果形式
				if (vari.contains("改变成果形式") || vari.contains("改成果形式")) {
					String caonima = vari.substring(vari.indexOf("：") + 1);
					String oldProdType = ga.getProductType();
					String newProdType = null;
					if (caonima.contains("改")) {
						oldProdType = caonima.split("改")[0];
						newProdType = caonima.split("改")[1];
					} else {
						newProdType = caonima;
					}
					gv.setChangeProductType(1);
					gv.setOldProductType(oldProdType);
					gv.setNewProductType(newProdType);
					
					ga.setProductType(newProdType);
				}
				//改变项目名称
				else if (vari.contains("改变项目名称为")) {
					String newName = vari.substring(vari.indexOf("：") + 1).replaceAll("[《》]", "");
					gv.setChangeName(1);
					
					gv.setOldName(gg.getName());
					gv.setNewName(newName);
					
					gg.setName(newName);
					ga.setName(newName);
				}
				//研究内容改变
				else if (vari.contains("研究内容")) {
					gv.setChangeContent(1);
				}
				else if (vari.contains("变更项目管理单位为")) {
					String newAgenName = vari.split("：")[1];
					Agency university = tools.getUnivByName(newAgenName);
					if (university == null) {
						found = false;
						continue;
					}
					gv.setChangeAgency(1);

					gv.setOldAgency(ga.getUniversity());
					gv.setOldAgencyName(ga.getAgencyName());
					gv.setOldDepartment(ga.getDepartment());
					gv.setOldDeptName(ga.getDivisionName());
					gv.setOldInstitute(ga.getInstitute());

					gv.setNewAgencyName(newAgenName);
					gv.setNewAgency(university);
					
					ga.setUniversity(university);
					ga.setAgencyName(newAgenName);

					Map paraMap = new HashMap();
					paraMap.put("applicationId", ga.getId());
					GeneralMember gm = (GeneralMember)baseService.query("select gm from GeneralMember gm  where gm.application.id = :applicationId and gm.isDirector=1", paraMap).get(0);
					gm.setAgencyName(university.getName());
					gm.setUniversity(university);
					saveOrUpdate(gm);
				}
				else {
					found = false;
				}
				
				if (found) {
					saveOrUpdate(gv);
				}
			}
		}
//		if (exceptionMsg.length() > 0) {
//			throw new Exception(exceptionMsg.toString());
//		}
		
		finish();
		
	}

}
