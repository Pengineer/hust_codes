package csdc.tool.execution.importer;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.GeneralApplication;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralVariation;
import csdc.bean.Officer;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 《2005-2006年一般项目撤项情况一览表_修正导入.xls》
 * @author xuhan
 */
public class GeneralWithdraw2005_2006Importer extends Importer {
	
	@Autowired
	private Tool tool;

	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	public GeneralWithdraw2005_2006Importer() {}
	
	public GeneralWithdraw2005_2006Importer(String filePath) {
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
	
			GeneralApplication application = generalProjectFinder.findApplication(B, C);
			if (application == null) {
				application = generalProjectFinder.findGranted(E).getApplication();
			}
			GeneralGranted granted  = application.getGeneralGranted().iterator().next();
			if (granted.getStatus() == 4) {
				System.out.println("已经撤项的项目：" + B + " - " + C);
				continue;
			}
			
			granted.setEndStopWithdrawDate(tool.getDate(2011, 12, 31));
			granted.setStatus(4);

			GeneralVariation variation = new GeneralVariation();
			variation.setIsImported(1);
			variation.setImportedDate(new Date());

			variation.setWithdraw(1);
			variation.setFinalAuditResultDetail("000000010");

			variation.setApplicantSubmitDate(tool.getDate(2011, 12, 31));
			variation.setFinalAuditDate(tool.getDate(2011, 12, 31));
			variation.setFinalAuditorName(刘杰.getPerson().getName());
			variation.setFinalAuditor(刘杰.getPerson());
			variation.setFinalAuditorAgency(刘杰.getAgency());
			variation.setFinalAuditResult(2);
			variation.setFinalAuditStatus(3);
			
			granted.addVariation(variation);
		}
	}


	/**
	 * 检查立项数据是否库内存在
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws UnsupportedEncodingException 
	 */
	private void checkProjectExistence() throws UnsupportedEncodingException, InstantiationException, IllegalAccessException, Exception {
		Set<String> exMsg = new HashSet<String>();
		
		getContentFromExcel(0);
		
		while (next()) {
			GeneralApplication application = generalProjectFinder.findApplication(B, C);
			if (application == null) {
				application = generalProjectFinder.findGranted(E).getApplication();
			}
			if (application == null || application.getGeneralGranted().isEmpty()) {
				exMsg.add("找不到的项目：" + B + " - " + C);
			}
		}
		
		if (exMsg.size() > 0) {
			throw new RuntimeException(exMsg.toString().replaceAll(",\\s+", "\n"));
			//System.out.println(exMsg);
		}
	}


}
