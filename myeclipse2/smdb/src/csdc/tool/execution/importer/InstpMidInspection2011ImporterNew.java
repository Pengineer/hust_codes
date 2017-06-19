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
 * 《2011年基地项目中检及拨款一览表_修正导入.xls》
 * @author wangyi
 * @status 
 * 备注：
 * 1、Excel表格中，延期中检、未中检两类数据不作入库处理。
 * 2、Excel表格中，中检通过、中检未通过的数据已核查入库，只完善拨款信息。
 * 3、Excel表格中，中检情况为已结项，3条数据在数据库中为已结项，且已有中检，但中检时间在结项时间之后，删除中检记录，并完善拨款信息；
 *    剩下4条数据在数据库中不存在中检，在研数据添加结项记录并完善拨款信息，已结项数据则直接完善拨款信息，都不做中检录入。
 * 4、Excel表格中，中检情况为空白，汇总数据不作入库处理，有两条项目数据为一般项目，数据库中已存在中检，只需完善拨款信息。
 */
public class InstpMidInspection2011ImporterNew extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;

	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	public InstpMidInspection2011ImporterNew() {}
	
	public InstpMidInspection2011ImporterNew(String filePath) {
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
//		Officer 刘杰 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '刘杰'");
		
		excelReader.readSheet(0);
		
		while (next(excelReader)) {
			
//			int flag = 0;
	
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
			
//			InstpMidinspection gmi = new InstpMidinspection();
//			gmi.setIsImported(1);
//			gmi.setImportedDate(new Date());
//			granted.addMidinspection(gmi);
//			gmi.setApplicantSubmitDate(tool.getDate(2012, 5, 31));
//			gmi.setFinalAuditDate(tool.getDate(2012, 5, 31));
//			gmi.setFinalAuditStatus(3);
//			if (H.contains("中检未通过")) {
//				gmi.setFinalAuditResult(1);
//				gmi.setFinalAuditOpinionFeedback(I);
//			} else {
//				gmi.setFinalAuditResult(2);
//				gmi.setFinalAuditOpinion(I);
//			}
//			gmi.setFinalAuditorName(刘杰.getPerson().getName());
//			gmi.setFinalAuditor(刘杰.getPerson());
//			gmi.setFinalAuditorAgency(刘杰.getAgency());
			
			if (F.length() > 0) {
				InstpFunding gf = new InstpFunding();
				granted.addFunding(gf);
				gf.setDate(tool.getDate(2011, 9, 30));
				gf.setFee(Double.parseDouble(F));
			}

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
						if(midinspection.getFinalAuditResult() == 2 && midinspection.getFinalAuditDate().getYear()+1900 == 2011 && J.contains("中检通过")) {
							System.out.println("2011中检存在的项目：" + C + " - " + D);
							j++;
						}
						if(midinspection.getFinalAuditResult() == 1 && midinspection.getFinalAuditDate().getYear()+1900 == 2011 && J.contains("中检未通过")) {
							System.out.println("中检不同意的项目：" + C + " - " + D);
							k++;
						}
					}
				} else {
					System.out.println("中检不存在的项目：" + C + " - " + D);
					l++;
				}
//				if (!granted.getName().equals(C)) {
//					System.out.println("项目名称错误的项目：" + C + " - " + D);
//					k++;
//				}
//				if (!granted.getEndinspection().isEmpty()) {
//					Set<InstpEndinspection> endinspections = granted.getInstpEndinspection();
//					for (InstpEndinspection endinspection: endinspections) { 
//						if(endinspection.getFinalAuditResultEnd() == 2) {
//							System.out.println("结项存在的项目：" + C + " - " + D);
//							l++;
//						}
//					}
//				}
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
