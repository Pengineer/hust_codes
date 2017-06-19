package csdc.tool.execution.importer;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.InstpApplication;
import csdc.bean.InstpGranted;
import csdc.bean.InstpMidinspection;
import csdc.bean.Officer;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 《2011年基地项目中检情况_修正导入.xls》
 * @author xuhan
 * @status 已导入smdbtest
 * 
 * 找不到的项目：从马克思的《资本论》到中国特色社会主义的科学发展观 - 聂锦芳
 * 
 * 新形势下提升香港国际金融中心地位的研究 2009JJD790051 陈平 已结项，但库内未结项，待写入结项数据
 * 
 */
public class InstpMidInspection2011Importer extends Importer {
	
	@Autowired
	private Tool tool;

	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	public InstpMidInspection2011Importer() {}
	
	public InstpMidInspection2011Importer(String filePath) {
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
		Officer 刘杰 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '刘杰'");
		
		getContentFromExcel(0);
		
		while (next()) {
			System.out.println(curRowIndex);
	
			InstpApplication application = null;
			String[] applicantNames = E.split("、");
			for (String applicantName : applicantNames) {
				application = instpProjectFinder.findApplication(C, applicantName);
				if (application != null) {
					break;
				}
			}
			InstpGranted granted = application.getInstpGranted().iterator().next();
		
			InstpMidinspection midInspection = new InstpMidinspection();
			granted.addMidinspection(midInspection);
			
			midInspection.setIsImported(1);
			midInspection.setImportedDate(new Date());
			midInspection.setApplicantSubmitDate(tool.getDate(2011, 9, 30));
			midInspection.setFinalAuditDate(tool.getDate(2011, 9, 30));
			midInspection.setFinalAuditStatus(3);
			if (H.contains("中检未通过")) {
				midInspection.setFinalAuditResult(1);
				midInspection.setFinalAuditOpinionFeedback(I);
			} else {
				midInspection.setFinalAuditResult(2);
				midInspection.setFinalAuditOpinion(I);
			}
			midInspection.setFinalAuditorName(刘杰.getPerson().getName());
			midInspection.setFinalAuditor(刘杰.getPerson());
			midInspection.setFinalAuditorAgency(刘杰.getAgency());
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
			String[] applicantNames = E.split("、");
			InstpApplication application = null;
			for (String applicantName : applicantNames) {
				application = instpProjectFinder.findApplication(C, applicantName);
				if (application != null) {
					break;
				}
			}
			if (application == null || application.getInstpGranted().isEmpty()) {
				exMsg.add("找不到的项目：" + C + " - " + E);
			}
		}
		
		if (exMsg.size() > 0) {
			throw new RuntimeException(exMsg.toString().replaceAll(",\\s+", "\n"));
			//System.out.println(exMsg);
		}
	}


}
