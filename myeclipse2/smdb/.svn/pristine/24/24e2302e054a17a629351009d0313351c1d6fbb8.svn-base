package csdc.tool.execution.importer;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralMidinspection;
import csdc.bean.Person;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 《2011年一般项目中检情况_修正导入.xls》
 * @author xuhan
 * @status 已导入smdb

 * 找不到的项目：中国近代地理学的发展研究——以中国地学会及《地学杂志》为研究对象 - 李兆江
 * 找不到的项目：公民道德教育实效性的社会心理学研究--以态度改变说服模型为分析工具 - 郭毅然
 * 找不到的项目：贸易、投资与环境协同发展的机制研究 - 张建中
 * 找不到的项目：动画文化对青少年价值观的影响 - 肖路
 * 找不到的项目：大学生毕业三年内就业及生存状况与政府应对措施研究--以浙江为例 - 孙丽园
 */
public class GeneralMidInspection2011Importer extends Importer {
	
	@Autowired
	private Tool tool;

	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	private String finalAuditorName = "刘杰";
	private String finalAuditorId = "ff80808131ef2e2e0131f01247e20027";
	private String finalAuditorAgencyId = "4028d8922d01bd4c012d01d8012e0009";
	
	
	public GeneralMidInspection2011Importer() {}
	
	public GeneralMidInspection2011Importer(String filePath) {
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
		
		int idx = 0;
		
		while (next()) {
			System.out.println(++idx);
	
			GeneralApplication ga = generalProjectFinder.findApplication(C, D);
			
			GeneralMidinspection gmi = new GeneralMidinspection();
			gmi.setIsImported(1);
			gmi.setImportedDate(new Date());
			gmi.setGranted(ga.getGeneralGranted().iterator().next());
			gmi.setApplicantSubmitDate(tool.getDate(2011, 9, 30));
			gmi.setFinalAuditDate(tool.getDate(2011, 9, 30));
			gmi.setFinalAuditStatus(3);
			if (H.contains("中检未通过")) {
				gmi.setFinalAuditResult(1);
				gmi.setFinalAuditOpinionFeedback(I);
			} else {
				gmi.setFinalAuditResult(2);
				gmi.setFinalAuditOpinion(I);
			}
			gmi.setFinalAuditorName(finalAuditorName);
			gmi.setFinalAuditor((Person) dao.query(Person.class, finalAuditorId));
			gmi.setFinalAuditorAgency((Agency) dao.query(Agency.class, finalAuditorAgencyId));
			dao.addOrModify(gmi);

			
			if (G.length() > 0) {
				GeneralFunding gf = new GeneralFunding();
				gf.setGranted(ga.getGeneralGranted().iterator().next());
				gf.setDate(tool.getDate(2011, 9, 30));
				gf.setFee(Double.parseDouble(G));
				dao.addOrModify(gf);
			}
		}
	}


	/**
	 * 检查申报数据是否库内存在
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws UnsupportedEncodingException 
	 */
	private void checkProjectExistence() throws UnsupportedEncodingException, InstantiationException, IllegalAccessException, Exception {
		Set<String> exMsg = new HashSet<String>();
		
		getContentFromExcel(0);
		
		while (next()) {
			GeneralApplication ga = generalProjectFinder.findApplication(C, D);
			if (ga == null || ga.getGeneralGranted().isEmpty()) {
				exMsg.add("找不到的项目：" + C + " - " + D);
			}
		}
		
		if (exMsg.size() > 0) {
			throw new RuntimeException(exMsg.toString().replaceAll(",\\s+", "\n"));
			//System.out.println(exMsg);
		}
	}


}
