package csdc.tool.execution.importer;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import csdc.bean.GeneralApplication;
import csdc.bean.GeneralEndinspection;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralVariation;

/**
 * 导入《2005-2011年一般项目结项数据（修正版）_修正导入.xls》
 * Note: 未导入负责人变更信息
 * 
 * 以下项目找不到，其中后期数据未导入，请手工自行处理：
 * 项目 研究型大学图书馆服务能力与用户满意度研究     -- 找不到
 * 项目 湖北省新农村规划的实践与理念     -- 找不到

 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class GeneralProject2005_2011EndImporter extends Importer {
	
	//项目批准号 or 项目名称    -> 立项实体  
	private Map<String, GeneralGranted> ggMap = new HashMap<String, GeneralGranted>();

	//立项实体id  -> 结项实体  
	private Map<String, GeneralEndinspection> geiMap = new HashMap<String, GeneralEndinspection>();
	

	public GeneralProject2005_2011EndImporter(File file) {
		super(file);
		
		List<GeneralGranted> ggs = baseService.query("select gg from GeneralGranted gg left join fetch gg.application where gg.application.year >= 2005 and gg.application.year <= 2010");
		for (GeneralGranted gg : ggs) {
			ggMap.put(tools.toDBC(gg.getName()).replaceAll("[^\\w\\u4e00-\\u9fa5]+", ""), gg);
			if (gg.getNumber() != null) {
				ggMap.put(tools.toDBC(gg.getNumber()).trim(), gg);
			}
		}
		
		List<GeneralEndinspection> geiList = baseService.query("select gei from GeneralEndinspection gei");
		for (GeneralEndinspection gei : geiList) {
			geiMap.put(gei.getGranted().getId(), gei);
		}
		
	}
	
	public void work() throws Exception {
		getContentFromExcel(0);
		StringBuffer exceptionMsg = new StringBuffer();
		
		while (next()) {
			
			if (!F.startsWith("05") && !F.startsWith("06") && !F.startsWith("07") && !F.startsWith("08") && !F.startsWith("09") && !F.startsWith("10")) {
				continue;
			}
			
			//按项目批准号找到项目
			GeneralGranted gg = ggMap.get(F);
			//按项目名称找到项目
			if (gg == null) {
				gg = ggMap.get(D.replaceAll("[^\\w\\u4e00-\\u9fa5]+", ""));
			}
			if (gg == null) {
				exceptionMsg.append("项目 " + D + "     -- 找不到\n");
				continue;
			}
			
			GeneralApplication ga = gg.getApplication();

			//只处理立项时间在[2005, 2010]的项目
			Integer grantedYear = ga.getYear();
			if (grantedYear < 2005 || grantedYear > 2010) {
				continue;
			}
			
			//结项时间
			int year = Integer.parseInt(A);
			
			saveOrUpdate(gg);
			saveOrUpdate(ga);
			
			
			//结项通过
			if (H.contains("同意结项") || H.contains("结项通过")) {
				GeneralEndinspection gei = geiMap.get(gg.getId());
				if (gei == null) {
					gei = new GeneralEndinspection();
				}
				gei.setGranted(gg);
				gei.setReviewAuditDate(tools.getDate(year, 1, 1));
				gei.setCertificate(J);
				gei.setIsApproved(2);
				gei.setNote(I);
				gei.setIsImported(1);
				gei.setReviewAuditStatus(3);
				gei.setReviewAuditResultEnd(gei.getIsApproved());

				gg.setEndStopWithdrawDate(gei.getReviewAuditDate());
				gg.setStatus(2);
				
				saveOrUpdate(gei);
			}
			
			//结项未通过
			if (H.contains("结项未通过")) {
				GeneralEndinspection gei = new GeneralEndinspection();
				gei.setGranted(gg);
				gei.setReviewAuditDate(tools.getDate(year, 1, 1));
				gei.setIsApproved(1);
				gei.setNote(I);
				gei.setIsImported(1);
				gei.setReviewAuditStatus(3);
				gei.setReviewAuditResultEnd(gei.getIsApproved());
				saveOrUpdate(gei);
			}
			
			//延期
			if (H.isEmpty() && I.contains("延期")) {
				String fuck[] = ("0_" + I).split("\\D+");
				Integer delayYear = fuck.length > 1 ? Integer.parseInt(fuck[1]) : 8888;
				Integer delayMonth = fuck.length > 2 ? Integer.parseInt(fuck[2]) : 1;
				Integer delayDate = fuck.length > 3 ? Integer.parseInt(fuck[3]) : 1;
				Date date = delayYear == 8888 ? tools.getDate(year + 1, 12, 31) : tools.getDate(delayYear, delayMonth, delayDate);

				GeneralVariation gv = new GeneralVariation();
				gv.setGranted(gg);
				gv.setIsApproved(2);
				gv.setPostponedOnce(1);
				gv.setOldOnceDate(ga.getPlanEndDate() != null ? ga.getPlanEndDate() : tools.getDate(year, 1, 1));
				gv.setNewOnceDate(date);
				gv.setMinistryAuditDate(gv.getOldOnceDate());
				gv.setIsImported(1);
				gv.setMinistryAuditStatus(3);
				gv.setMinistryAuditResult(gv.getIsApproved());
				
				ga.setPlanEndDate(date);
				saveOrUpdate(gv);
			}
		}
		if (exceptionMsg.length() > 0) {
			System.err.println(exceptionMsg);
//			throw new Exception(exceptionMsg.toString());
		}
		
		finish();
	}

}
