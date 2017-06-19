package csdc.tool.execution.fix;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.GeneralApplication;
import csdc.bean.GeneralEndinspection;
import csdc.bean.GeneralGranted;
import csdc.tool.execution.importer.Importer;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 检查《2009年一般项目结项原始记录》《2010年一般项目结项原始记录》与数据库数据对比
 * 
 * @author wangyi
 * 
 */
public class Fix20121102 extends Importer {

	private ExcelReader excelReader2009;
	private ExcelReader excelReader2010;

	@Autowired
	private Tool tool;

	@SuppressWarnings("deprecation")
	public void work() throws Exception {
		excelReader2009.readSheet(0);

		while (next(excelReader2009)) {
			if(G.length() == 0) {
				break;
		    }
			GeneralGranted granted = (GeneralGranted) dao.queryUnique("select granted from GeneralGranted granted where granted.number = ?", G);
//			if(granted == null) {
//			    System.out.println(G);
//			}

//			GeneralApplication application = granted.getApplication();
//			if (application.getProductType() == null && application.getProductTypeOther() == null) {
//				application.setProductType(E);
//			}
//			else {
//				//添加成果形式
//				GeneralApplication application = granted.getApplication();
//				if (application.getProductType() == null && application.getProductTypeOther() == null) {
//					application.setProductType(E);
//				}
//				if (granted.getProductType() == null && granted.getProductTypeOther() == null) {
//				    granted.setProductType(E);
//				}
//			}
			
			if(J.contains("Y")) {
				GeneralEndinspection approvedEndinspection = null;
				if(granted == null) {
					System.out.println(G);
				} else{					
					Set<GeneralEndinspection> endinspections = granted.getEndinspection();
					for (GeneralEndinspection endinspection: endinspections) { 
						if(endinspection.getFinalAuditResultEnd() == 2) {
							approvedEndinspection = endinspection;
							break;
						}
					}
					//				if (approvedEndinspection == null) {
					//					System.out.println(G);
					//				}
					if (approvedEndinspection == null) {
						approvedEndinspection = new GeneralEndinspection();
						granted.addEndinspection(approvedEndinspection);
						approvedEndinspection.setFinalAuditResultEnd(2);
					}
					//立项信息中审核时间修复
					if (granted.getStatus() == 2) {
						granted.setEndStopWithdrawDate(approvedEndinspection.getFinalAuditDate());
					} else {
						System.out.println(G);
					}
//					if (!I.isEmpty()) {
////						if (approvedEndinspection.getMemberName() == null) {
//							approvedEndinspection.setMemberName(I);
////						} else {
////							System.out.println(G);
////						}
//					}
////					approvedEndinspection.setFinalAuditDate(tool.getDate(A));//按Excel修改结项时间
////					if(M.contains("免予鉴定")) {
//////						approvedEndinspection.setIsApplyNoevaluation(1);
////						approvedEndinspection.setFinalAuditResultNoevaluation(2);
////					} else {
////						approvedEndinspection.setIsApplyNoevaluation(0);
////					}
//					//			} else {
//					//				Set<GeneralEndinspection> endinspections = granted.getEndinspection();
//					//				GeneralEndinspection notApprovedEndinspection = null;
//					//				for (GeneralEndinspection endinspection: endinspections) { 
//					//					if(endinspection.getFinalAuditResultEnd() == 2){
//					//						if(tool.getDate(B).after(endinspection.getFinalAuditDate())) {
//					//							//Excel结项时间早于数据库结项时间
//					//						}
//					//					} else {
//					//						if(tool.getDate(B).getYear() == endinspection.getFinalAuditDate().getYear()) {
//					//							notApprovedEndinspection = endinspection;
//					//							break;
//					//						}
//					//					}					
//					//				}
//					//				if (notApprovedEndinspection == null) {
//					//					notApprovedEndinspection = new GeneralEndinspection();
//					//					granted.addEndinspection(notApprovedEndinspection);
//					//					notApprovedEndinspection.setFinalAuditResultEnd(1);
//					//				}				
//					//				notApprovedEndinspection.setFinalAuditDate(tool.getDate(B));
//					//				if(L.contains("免予鉴定")) {
//					//					notApprovedEndinspection.setIsApplyNoevaluation(1);
//					//				} else {
//					//					notApprovedEndinspection.setIsApplyNoevaluation(0);
				}
			}

		}
		System.out.print("ffffffffff");
		excelReader2010.readSheet(0);

		while (next(excelReader2010)) {
			GeneralGranted granted = (GeneralGranted) dao.queryUnique("select granted from GeneralGranted granted where granted.number = ?", G);
			if(J.contains("Y")) {
				GeneralEndinspection approvedEndinspection = null;
				if(granted == null) {
					System.out.println(G);
				} else{
					Set<GeneralEndinspection> endinspections = granted.getEndinspection();
					for (GeneralEndinspection endinspection: endinspections) { 
						if(endinspection.getFinalAuditResultEnd() == 2) {
							approvedEndinspection = endinspection;
							break;
						}
					}
					//					if (approvedEndinspection == null) {
					//						System.out.println(G);
					//					}

					if (approvedEndinspection == null) {
						approvedEndinspection = new GeneralEndinspection();
						granted.addEndinspection(approvedEndinspection);
						approvedEndinspection.setFinalAuditResultEnd(2);
					}
					//立项信息中审核时间修复
					if (granted.getStatus() == 2) {
						granted.setEndStopWithdrawDate(approvedEndinspection.getFinalAuditDate());
					} else {
						System.out.println(G);
					}
//					if (!I.isEmpty()) {
////						if (approvedEndinspection.getMemberName() == null) {
//							approvedEndinspection.setMemberName(I);
////						} else {
////							System.out.println(G);
////						}
//					}
//					approvedEndinspection.setFinalAuditDate(tool.getDate(B));//按Excel修改结项时间
//					if(L.contains("免予鉴定")) {
////						approvedEndinspection.setIsApplyNoevaluation(1);
//						approvedEndinspection.setFinalAuditResultNoevaluation(2);
//					} else {
//						approvedEndinspection.setIsApplyNoevaluation(0);
//					}
					//				} else {
					//					Set<GeneralEndinspection> endinspections = granted.getEndinspection();
					//					GeneralEndinspection notApprovedEndinspection = null;
					//					for (GeneralEndinspection endinspection: endinspections) { 
					//						if(endinspection.getFinalAuditResultEnd() == 2){
					//							if(tool.getDate(B).after(endinspection.getFinalAuditDate())) {
					//								//Excel结项时间早于数据库结项时间
					//							}
					//						} else {
					//							if(tool.getDate(B).getYear() == endinspection.getFinalAuditDate().getYear()) {
					//								notApprovedEndinspection = endinspection;
					//								break;
					//							}
					//						}					
					//					}
					//					if (notApprovedEndinspection == null) {
					//						notApprovedEndinspection = new GeneralEndinspection();
					//						granted.addEndinspection(notApprovedEndinspection);
					//						notApprovedEndinspection.setFinalAuditResultEnd(1);
					//					}				
					//					notApprovedEndinspection.setFinalAuditDate(tool.getDate(B));
					//					if(L.contains("免予鉴定")) {
					//						notApprovedEndinspection.setIsApplyNoevaluation(1);
					//					} else {
					//						notApprovedEndinspection.setIsApplyNoevaluation(0);
					//					}
				}
			}
			if(A.length() == 0) {
				break;
			}
		}
		System.out.print("eeeeeeee");
	}

	public Fix20121102() {
	}

	public Fix20121102(String filePath1, String filePath2) {
		excelReader2009 = new ExcelReader(filePath1);
		excelReader2010 = new ExcelReader(filePath2);
	}
}
