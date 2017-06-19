package csdc.tool.execution.importer;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMidinspection;
import csdc.bean.Officer;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 《2012年一般项目中检及拨款情况一览表_修正导入.xls》
 * @author wangyi
 * @status 已入库smdb
 * 备注：
 * 1、Excel表格中，不同意延期中检、同意延期中检、未中检等三类数据不作入库处理；已结项、撤项等两类数据因SMDB中已经手工录入，也无需再进行入库处理。
 * 2、因本次2012年中检数据中包含的4条数据在SMDB中存在2011年9月30日同意中检的数据，故手工处理此4条数据：将2011年中检同意改为不同意，删除2011年对应中检拨款信息，以2012年新入库的中检及拨款数据为准。
 * 3、Excel表格中，中检通过、中检未通过的数据全部依托程序自动处理入库，入库时对于2012年部分已经录入的中检同意数据，完善了其拨款信息并核查了其中检状态。 
 */
public class GeneralMidInspection2012Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;

	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
//	private String finalAuditorName = "刘杰";
//	private String finalAuditorId = "ff80808131ef2e2e0131f01247e20027";
//	private String finalAuditorAgencyId = "4028d8922d01bd4c012d01d8012e0009";
	
	
	public GeneralMidInspection2012Importer() {}
	
	public GeneralMidInspection2012Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
//		checkProjectExistence();
		importData();
	}

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	@SuppressWarnings({ "deprecation" })
	private void importData() throws Exception {
		
		Officer 刘杰 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '刘杰'");
		
		excelReader.readSheet(0);		
		
		while (next(excelReader)) {
//			System.out.println(excelReader.getCurrentRowIndex());
			
			int flag = 0;
	
			GeneralGranted granted = generalProjectFinder.findGranted(D);
			
			if (granted == null) {
				System.out.println("找不到的项目：" + E + " - " + D);
			} else {
				if (!granted.getMidinspection().isEmpty()) {
					Set<GeneralMidinspection> midinspections = granted.getMidinspection();
					for (GeneralMidinspection midinspection: midinspections) { 
						if(midinspection.getFinalAuditDate().getYear()+1900 == 2012) {
							if (I.length() > 0) {
								GeneralFunding gf = new GeneralFunding();
								granted.addFunding(gf);
								gf.setDate(tool.getDate(2012, 12, 31));
								gf.setFee(Double.parseDouble(I));
							}
							flag++;
						}
					}
				}
			}
			
			// 下一条
			if (flag == 1) {
				continue;
			}
			
			GeneralMidinspection gmi = new GeneralMidinspection();
			gmi.setIsImported(1);
			gmi.setImportedDate(new Date());
			granted.addMidinspection(gmi);
			gmi.setApplicantSubmitDate(tool.getDate(2012, 9, 30));
			gmi.setFinalAuditDate(tool.getDate(2012, 9, 30));
			gmi.setFinalAuditStatus(3);
			if (J.contains("中检未通过")) {
				gmi.setFinalAuditResult(1);
				gmi.setFinalAuditOpinionFeedback(K);
			} else {
				gmi.setFinalAuditResult(2);
				gmi.setFinalAuditOpinion(K);
			}
			gmi.setFinalAuditorName(刘杰.getPerson().getName());
			gmi.setFinalAuditor(刘杰.getPerson());
			gmi.setFinalAuditorAgency(刘杰.getAgency());
//			dao.addOrModify(gmi);
			
			if (I.length() > 0) {
				GeneralFunding gf = new GeneralFunding();
				granted.addFunding(gf);
				gf.setDate(tool.getDate(2012, 12, 31));
				gf.setFee(Double.parseDouble(I));
//				dao.addOrModify(gf);
			}
			
			if(A.length() == 0) {
				break;
			}
//			System.out.println("general: " + gmi.getGranted().getId());
//			break;
		}
	}


	/**
	 * 检查中检数据是否库内存在
	 * @throws Exception  
	 */
	@SuppressWarnings("deprecation")
	private void checkProjectExistence() throws Exception {
//		Set<String> exMsg = new HashSet<String>();
		
		excelReader.readSheet(0);
		int i = 0;
		while (next(excelReader)) {
			GeneralGranted granted = generalProjectFinder.findGranted(D);
			if (granted == null) {
				System.out.println("找不到的项目：" + E + " - " + D);
			} else {
				if (!granted.getMidinspection().isEmpty()) {
					Set<GeneralMidinspection> midinspections = granted.getMidinspection();
					for (GeneralMidinspection midinspection: midinspections) { 
						if(midinspection.getFinalAuditDate().getYear()+1900 == 2012) {
							System.out.println("中检存在的项目：" + E + " - " + D);
							i++;
						}
					}
				}
				if (!granted.getName().equals(B)) {
					System.out.println("项目名称错误的项目：" + E + " - " + D);
				}
			}
			
			if(A.length() == 0) {
				break;
			}
		}
		System.out.println(i);
//		if (exMsg.size() > 0) {
//			throw new RuntimeException(exMsg.toString().replaceAll(",\\s+", "\n"));
//			//System.out.println(exMsg);
//		}
	}


}
