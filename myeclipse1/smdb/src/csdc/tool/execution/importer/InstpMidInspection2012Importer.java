package csdc.tool.execution.importer;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.InstpEndinspection;
import csdc.bean.InstpFunding;
import csdc.bean.InstpGranted;
import csdc.bean.InstpMidinspection;
import csdc.bean.Officer;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * Excel1：《2012年基地项目中检（2012年9月30日）及拨款（2012年12月31日）情况一览表 _修正导入.xls》
 * Excel2：《2012年基地项目中检情况（2012年5月31日）_修正导入.xls》
 * @author wangyi
 * @status 
 * 备注：
 * 1、Excel1表格中，同意延期中检、未中检两类数据不作入库处理。
 * 2、Excel1表格中，中检通过、中检未通过的数据全部依托程序自动处理入库。
 * 3、Excel1表格中，中检情况为已结项，数据库中全部为在研，手工添加结项记录，并完善拨款信息，不做中检录入。
 * 4、Excel1表格中，《中国藏黑水城民族文学文献整理出版》，数据库中已结项，完善拨款信息，不做中检录入。
 * 5、Excel2表格中，同意延期中检数据不作入库处理。
 * 6、Excel2表格中，中检通过、中检未通过的数据全部依托程序自动处理入库。
 */
public class InstpMidInspection2012Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;

	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	public InstpMidInspection2012Importer() {}
	
	public InstpMidInspection2012Importer(String filePath) {
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
	private void importData() throws Exception {
		Officer 刘杰 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '刘杰'");
		
		excelReader.readSheet(0);
		
		while (next(excelReader)) {
			
//			int flag = 0;
//	
			InstpGranted granted = instpProjectFinder.findGranted(D);
//			if (!granted.getMidinspection().isEmpty()) {
//				Set<InstpMidinspection> midinspections = granted.getInstpMidinspection();
//				for (InstpMidinspection midinspection: midinspections) { 
//					if(midinspection.getFinalAuditResult() == 2) {
//						flag++;
//					}
//				}
//			}
//			
//			// 下一条
//			if (flag == 1) {
//				continue;
//			}
			
			InstpMidinspection gmi = new InstpMidinspection();
			gmi.setIsImported(1);
			gmi.setImportedDate(new Date());
			granted.addMidinspection(gmi);
			gmi.setApplicantSubmitDate(tool.getDate(2012, 5, 31));
			gmi.setFinalAuditDate(tool.getDate(2012, 5, 31));
			gmi.setFinalAuditStatus(3);
			if (H.contains("中检未通过")) {
				gmi.setFinalAuditResult(1);
				gmi.setFinalAuditOpinionFeedback(I);
			} else {
				gmi.setFinalAuditResult(2);
				gmi.setFinalAuditOpinion(I);
			}
			gmi.setFinalAuditorName(刘杰.getPerson().getName());
			gmi.setFinalAuditor(刘杰.getPerson());
			gmi.setFinalAuditorAgency(刘杰.getAgency());
			
//			if (G.length() > 0) {
//				InstpFunding gf = new InstpFunding();
//				granted.addFunding(gf);
//				gf.setDate(tool.getDate(2012, 12, 31));
//				gf.setFee(Double.parseDouble(G));
//			}

			if(A.length() == 0) {
				break;
			}
		}
	}


	/**
	 * 检查中检数据是否库内存在
	 * @throws Exception 
	 */
	private void checkProjectExistence() throws Exception {
		
		excelReader.readSheet(0);
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		while (next(excelReader)) {
			InstpGranted granted = instpProjectFinder.findGranted(D);
			if (granted == null) {
				System.out.println("找不到的项目：" + C + " - " + D);
				i++;
			} else {
				if (!granted.getMidinspection().isEmpty()) {
					Set<InstpMidinspection> midinspections = granted.getInstpMidinspection();
					for (InstpMidinspection midinspection: midinspections) { 
						if(midinspection.getFinalAuditResult() == 2) {
							System.out.println("中检存在的项目：" + C + " - " + D);
							j++;
						}
					}
				}
				if (!granted.getName().equals(C)) {
					System.out.println("项目名称错误的项目：" + C + " - " + D);
					k++;
				}
				if (!granted.getEndinspection().isEmpty()) {
					Set<InstpEndinspection> endinspections = granted.getInstpEndinspection();
					for (InstpEndinspection endinspection: endinspections) { 
						if(endinspection.getFinalAuditResultEnd() == 2) {
							System.out.println("结项存在的项目：" + C + " - " + D);
							l++;
						}
					}
				}
			}
			if(A.length() == 0) {
				break;
			}
		}
		System.out.println("i="+i);
		System.out.println("j="+j);
		System.out.println("k="+k);
		System.out.println("l="+l);
	}

}
