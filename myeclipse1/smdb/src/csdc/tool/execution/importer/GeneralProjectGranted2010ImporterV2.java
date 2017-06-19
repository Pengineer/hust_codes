package csdc.tool.execution.importer;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;

/**
 * 导入《2010年一般项目立项带结项形式.xls》中的拨款信息
 * @author xuhan
 */
@SuppressWarnings("unchecked")
public class GeneralProjectGranted2010ImporterV2 extends Importer {
	
	/**
	 * 项目名称    -> 立项实体 
	 */
	private Map<String, GeneralGranted> ggMap = new HashMap<String, GeneralGranted>();

	/**
	 * 项目名称    -> 一期拨款实体 
	 */
	private Map<String, GeneralFunding> gfMap = new HashMap<String, GeneralFunding>();

	
	@SuppressWarnings("deprecation")
	public GeneralProjectGranted2010ImporterV2(File file) {
		super(file);
		
		List<GeneralGranted> ggs = session.createQuery("select gg from GeneralGranted gg where gg.application.year = 2010").list();
		for (GeneralGranted gg : ggs) {
			ggMap.put(gg.getName().replaceAll("[　\\s]+", ""), gg);
			ggMap.put(gg.getApplication().getUniversity().getCode() + gg.getApplication().getApplicantName(), gg);
		}

		List<GeneralFunding> gfs = session.createQuery("select gf from GeneralFunding gf left join fetch gf.granted where gf.granted.application.year = 2010").list();
		for (GeneralFunding gf : gfs) {
			if (gf.getDate().getYear() + 1900 == 2010) {
				gfMap.put(tools.toDBC(gf.getGranted().getName()).replaceAll("[^\\w\\u4e00-\\u9fa5]+", ""), gf);
			}
		}

	}
	
	public void work() throws Exception {
		getContentFromExcel(0);
		StringBuffer exceptionMsg = new StringBuffer();
		StringBuffer missingApplicationName = new StringBuffer();
		
		while (next()) {
			System.out.println(curRowIndex);
			String projectName = C.replaceAll("[　\\s]+", "");
			String applicantName = G.replaceAll("[　\\s]+", "");
			if (applicantName.isEmpty()) {
				exceptionMsg.append(applicantName + " 不存在");
			}
			GeneralGranted gg = ggMap.get(projectName.replaceAll("[　\\s]+", ""));
			if (gg == null) {
				gg = ggMap.get(tools.getUnivByName(B).getCode() + applicantName);
			}
			if (gg == null) {
				exceptionMsg.append(projectName + " 不存在");
			}
			if (exceptionMsg.length() > 0) {
				continue;
			}
			
			gg.setApproveFee(tools.getFee(J));
			
			GeneralFunding gf = gfMap.get(projectName);
			if (gf == null) {
				gf = new GeneralFunding();
			}
			gf.setGranted(gg);
			gf.setDate(tools.getDate(2010, 9, 1));
			gf.setFee(tools.getFee(K));
			
			saveOrUpdate(gg);
			saveOrUpdate(gf);
		}
		if (exceptionMsg.length() > 0) {
			throw new Exception(exceptionMsg.toString());
		}
		
		finish();
		
		System.out.println(missingApplicationName);
	}


}
