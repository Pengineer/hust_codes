package csdc.tool.execution.importer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.GeneralMidinspection;
import csdc.bean.GeneralVariation;
import csdc.bean.InstpEndinspection;
import csdc.bean.InstpFunding;
import csdc.bean.InstpGranted;
import csdc.bean.InstpMidinspection;
import csdc.bean.InstpVariation;
import csdc.bean.Officer;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * Excel：《2013年基地项目中期检查情况一览表_修正导入.xls》
 * @author maowh
 * @status 
 * 备注：存在一条中检情况为“撤项”的数据，因在库中状态已为“撤项”，故不作处理
 * 
 * 
 */
public class InstpMidInspection2013Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;

	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	public InstpMidInspection2013Importer() {}
	
	public InstpMidInspection2013Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
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
		
		excelReader.readSheet(0);
		
		while (next(excelReader)) {
			
			System.out.println(excelReader.getCurrentRowIndex()+ "/" + excelReader.getRowNumber());
		
			InstpGranted granted = instpProjectFinder.findGranted(D);
			
			if (G.contains("延期中检")) {
				int flag = 0;
				Set<InstpVariation> ivs = granted.getInstpVariation();
				SimpleDateFormat sd = new SimpleDateFormat("yyyy");
				if (!ivs.isEmpty()) {
					for (InstpVariation iv: ivs) {
						String variImportedDate = sd.format(iv.getFinalAuditDate());
						if (iv.getOther() == 1 && "2013".equals(variImportedDate) && iv.getOtherInfo().contains("中检延期")) {
							if (iv.getFinalAuditResult() != 2){							
								iv.setFinalAuditStatus(3);
								iv.setFinalAuditResult(2);
								iv.setFinalAuditResultDetail("00000000000000000001");
								iv.setFinalAuditorName(刘杰.getPerson().getName());
								iv.setFinalAuditor(刘杰.getPerson());
								iv.setFinalAuditorAgency(刘杰.getAgency());
								iv.setFinalAuditDate(tool.getDate(2013, 9, 30));
								flag++;
							} else if (iv.getFinalAuditResult() == 2) {
								flag++;
							}
						} 					
					}
				}
				if (flag == 0) {
						InstpVariation instpVariation = new InstpVariation();
						instpVariation.setIsImported(1);
						instpVariation.setImportedDate(new Date());
						instpVariation.setApplicantSubmitStatus(3);
						instpVariation.setApplicantSubmitDate(tool.getDate(2013, 9, 30));
						instpVariation.setFinalAuditDate(tool.getDate(2013, 9, 30));
						instpVariation.setFinalAuditStatus(3);
						instpVariation.setFinalAuditResult(2);
						instpVariation.setFinalAuditResultDetail("00000000000000000001");
						instpVariation.setOther(1);
						instpVariation.setOtherInfo("中检延期");
						instpVariation.setFinalAuditorName(刘杰.getPerson().getName());
						instpVariation.setFinalAuditor(刘杰.getPerson());
						instpVariation.setFinalAuditorAgency(刘杰.getAgency());
						instpVariation.setGranted(granted);
						dao.add(instpVariation);
				}
					
			}
			
			if (G.contains("中检通过") || G.contains("中检未通过")) {
				int i = 0;
				if (!granted.getMidinspection().isEmpty()){
					Set<InstpMidinspection> midinspections = granted.getInstpMidinspection();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
					for (InstpMidinspection midinspection: midinspections){
						String midImportedDate = sdf.format(midinspection.getImportedDate());
						if ("2013".equals(midImportedDate) && midinspection.getFinalAuditResult() != 2) {
							i++;
							midinspection.setFinalAuditDate(tool.getDate(2013, 9, 30));
							midinspection.setFinalAuditStatus(3);
							midinspection.setFinalAuditorName(刘杰.getPerson().getName());
							midinspection.setFinalAuditor(刘杰.getPerson());
							midinspection.setFinalAuditorAgency(刘杰.getAgency());
							if (G.contains("中检未通过")) {
								midinspection.setFinalAuditResult(1);
								midinspection.setFinalAuditOpinionFeedback(H);
							} else if (G.contains("中检通过")) {
								midinspection.setFinalAuditResult(2);
							}

						}
					}				
				} 
				if (i == 0) {
			    // 对不存在中检同意数据的项目，新增一条中检信息
						InstpMidinspection gmi = new InstpMidinspection();
						gmi.setIsImported(1);
						gmi.setImportedDate(new Date());
						granted.addMidinspection(gmi);
						gmi.setGranted(granted);
						gmi.setApplicantSubmitStatus(3);
						gmi.setStatus(5);
						gmi.setFinalAuditDate(tool.getDate(2013, 9, 30));
						gmi.setApplicantSubmitDate(tool.getDate(2013, 9, 30));
						gmi.setFinalAuditStatus(3);
						if (G.contains("中检未通过")) {
							gmi.setFinalAuditResult(1);
							gmi.setFinalAuditOpinionFeedback(H);
						} else if (G.contains("中检通过")) {
							gmi.setFinalAuditResult(2);
						}
						gmi.setFinalAuditorName(刘杰.getPerson().getName());
						gmi.setFinalAuditor(刘杰.getPerson());
						gmi.setFinalAuditorAgency(刘杰.getAgency());
						dao.add(gmi);				
				}

			}
			
			if (G.contains("结项")) {
				granted.setStatus(2);
			}
			
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
		List<String> wrongNames = new ArrayList<String>();
//		List<String> list= new ArrayList<String>();
//		List<String> nameList = new ArrayList<String>();
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int n = 0;
		
		while (next(excelReader)) {
			int m = 0;
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
					//校验需要录入中检信息的项目是否存在已同意的中检延期的变更
					if (G.contains("中检通过") || G.contains("中检未通过")) {
						Set<InstpVariation> variations = granted.getInstpVariation();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
						for (InstpVariation variation : variations) {
							if (variation.getFinalAuditDate() != null) {
								String auditDate = sdf.format(variation.getFinalAuditDate());
								if (variation.getFinalAuditResult() == 2 && "2013".equals(auditDate)) {
									n++;
									System.out.println("存在本年度中检延期的变更（已同意）: " + C + " - " + D);
								}
							}
						}
					}
				}
				
				if (!granted.getName().equals(C)) {
					System.out.println("项目名称错误的项目：" + C + " - " + D + granted.getName());
					wrongNames.add(C + D + granted.getName());
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
			
			//找出存在多条中检延期变更的数据
//			if (!granted.getInstpVariation().isEmpty()) {
//				Set <InstpVariation> ivs = granted.getInstpVariation();
//				SimpleDateFormat sd = new SimpleDateFormat("yyyy");
//				for (InstpVariation iv: ivs) {
//					if (iv.getFinalAuditDate() != null) {
//						String finalAuditDate = sd.format(iv.getFinalAuditDate());
//						if (iv.getOther() == 1 && iv.getOtherInfo().contains("中检") && "2013".equals(finalAuditDate)) {
//							m++;
//						}
//					}
//				}
//			}
//			
//			if (m > 1) {
//				list.add(granted.getId());
//				nameList.add(C);
//			}

			if(A.length() == 0) {
				break;
			}						
		}
		
		//生成存问题数据的excel
//		try {
//		InputStream inp = new FileInputStream("D:/csdc/work/2.sks/1.smdb/0.参考资料/数据资料/社科项目/基地项目/2013/数据修复.xls");//excel中为有多条中检延期变更项目的立项id
//        Workbook wb =  WorkbookFactory.create(inp);
//        Sheet sheet = wb.getSheetAt(0);
//        Row row = sheet.createRow(0); 
//        row.createCell(0).setCellValue("立项id");
//        for(int ss = 0; ss < list.size(); ss++) {
//        	String pname = list.get(ss);
//        	row = sheet.createRow(1 + ss);
//        	row.createCell(0).setCellValue(pname);
//        }
//        FileOutputStream fileOut = new FileOutputStream("D:/csdc/work/2.sks/1.smdb/0.参考资料/数据资料/社科项目/基地项目/2013/数据修复.xls");
//        wb.write(fileOut);
//        fileOut.close();
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
			
		System.out.println("i="+i);
		System.out.println("j="+j);
		System.out.println("k="+k);
		System.out.println("l="+l);
		System.out.println("n="+n);
	}

}

