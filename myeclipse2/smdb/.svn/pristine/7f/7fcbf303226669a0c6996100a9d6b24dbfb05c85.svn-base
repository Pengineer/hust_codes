package csdc.tool.execution.importer;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import csdc.bean.GeneralApplication;
import csdc.bean.GeneralEndinspection;
import csdc.bean.GeneralGranted;

@SuppressWarnings("unchecked")
public class GeneralProjectEndInspection2011SecondSeasonImporter extends Importer {

	//项目批准号 or 项目名称    -> 立项实体  
	private Map<String, GeneralGranted> ggMap = new HashMap<String, GeneralGranted>();

	//立项实体id  -> 结项实体  
	private Map<String, GeneralEndinspection> geiMap = new HashMap<String, GeneralEndinspection>();
	
	private Set<String> certificateSet = new HashSet<String>();

	public GeneralProjectEndInspection2011SecondSeasonImporter(File file) {
		super(file);
		
		List<GeneralGranted> ggs = baseService.query("select gg from GeneralGranted gg left join fetch gg.application where gg.application.year >= 2005 and gg.application.year <= 2010");
		for (GeneralGranted gg : ggs) {
			ggMap.put(tools.toDBC(gg.getName()).replaceAll("[^\\w\\u4e00-\\u9fa5]+", ""), gg);
			if (gg.getNumber() != null) {
				ggMap.put(tools.toDBC(gg.getNumber()).trim(), gg);
			}
		}
		
		List<GeneralEndinspection> geiList = baseService.query("select gei from GeneralEndinspection gei where gei.isApproved = 2");
		for (GeneralEndinspection gei : geiList) {
			geiMap.put(gei.getGranted().getId(), gei);
		}
		
		certificateSet.addAll(baseService.query("select gei.certificate from GeneralEndinspection gei where gei.certificate is not null"));
	}

	public void work() throws Exception {
		getContentFromExcel(2);
		StringBuffer exceptionMsg = new StringBuffer();
		
		while (next()) {
			
			if (!G.startsWith("05") && !G.startsWith("06") && !G.startsWith("07") && !G.startsWith("08") && !G.startsWith("09") && !G.startsWith("10")) {
				continue;
			}
			
			//按项目批准号找到项目
			GeneralGranted gg = ggMap.get(G);
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
			if (certificateSet.contains(L)) {
				System.err.println("结项证书编号 " + L + " 已被占用\n");
				continue;
			}
			
			GeneralEndinspection gei = geiMap.get(gg.getId());
			if (gei == null) {
				gei = new GeneralEndinspection();
			}
			gei.setGranted(gg);
			gei.setReviewAuditDate(tools.getDate(A));
			gei.setMemberName(transMemberNames(I));
			gei.setNote(K);
			gei.setCertificate(L);

			gei.setIsApproved(2);
			gei.setIsImported(1);
			gei.setReviewAuditStatus(3);
			gei.setReviewAuditResultEnd(gei.getIsApproved());

			gg.setEndStopWithdrawDate(gei.getReviewAuditDate());
			gg.setStatus(2);
			
			saveOrUpdate(gg);
			saveOrUpdate(gei);
		}
		if (exceptionMsg.length() > 0) {
//			System.err.println(exceptionMsg);
			throw new Exception(exceptionMsg.toString());
		}
		finish();

	}

	/**
	 * 将成员信息转换成用英文分号空格隔开
	 * @param i
	 * @return
	 */
	private String transMemberNames(String i) {
		String [] names = i.split("[^\\u4e00-\\u9fa5]+");
		String result = "";
		for (String string : names) {
			if (string.length() > 1) {
				if (result.length() > 0) {
					result += "; ";
				}
				result += string;
			}
		}
		return result;
	}
}
