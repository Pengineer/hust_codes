package csdc.tool.execution.importer;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMidinspection;
import csdc.bean.GeneralVariation;
import csdc.bean.Officer;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 《2013年一般项目中检及拨款情况一览表_修正导入.xls》
 * @author maowh
 * @status 
 * 备注：
 * 1、Excel表格中，未中检不作入库处理；撤项(共四条，其中三条已有撤项信息，仅一条需要处理（手工）)、延期中检会在变更表中添加一条。
 * 2、当延期中检对应的项目已存关于延期中检的变更且确实对应本次中检时，仅修改最终审核状态等信息，否则新建一条。 
 */
public class GeneralMidInspection2013Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;

	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private UniversityFinder universityFinder;
			
	public GeneralMidInspection2013Importer() {}
	
	public GeneralMidInspection2013Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
		//checkProjectExistence();
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
			

	
			GeneralGranted granted = generalProjectFinder.findGranted(C);			
			
			if (G.contains("延期中检")) {
				Set<GeneralVariation> gvs = granted.getGeneralVariation();
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				if (!gvs.isEmpty()) {
					for (GeneralVariation gv: gvs) {
						String variImportedDate = sd.format(gv.getImportedDate());
						if (("2013-10-20".equals(variImportedDate) || "2013-10-22".equals(variImportedDate))&&gv.getFinalAuditResult() != 2 && gv.getOtherInfo().contains("中检延期") && gv.getOther() == 1){							
							gv.setFinalAuditStatus(3);
							gv.setFinalAuditResult(2);
							gv.setFinalAuditorName(刘杰.getPerson().getName());
							gv.setFinalAuditor(刘杰.getPerson());
							gv.setFinalAuditorAgency(刘杰.getAgency());
							gv.setFinalAuditDate(tool.getDate(2013, 9, 30));								
						} 					
					}
				}else {
					GeneralVariation generalVariation = new GeneralVariation();
					generalVariation.setIsImported(1);
					generalVariation.setImportedDate(tool.getDate(2013, 9, 30));
					generalVariation.setApplicantSubmitStatus(3);
					generalVariation.setApplicantSubmitDate(tool.getDate(2013, 9, 30));
					generalVariation.setFinalAuditDate(tool.getDate(2013, 9, 30));
					generalVariation.setFinalAuditStatus(3);
					generalVariation.setFinalAuditResult(2);
					generalVariation.setOther(1);
					generalVariation.setOtherInfo("中检延期");
					generalVariation.setFinalAuditorName(刘杰.getPerson().getName());
					generalVariation.setFinalAuditor(刘杰.getPerson());
					generalVariation.setFinalAuditorAgency(刘杰.getAgency());
					generalVariation.setGranted(granted);
					dao.add(generalVariation);
				}					
			} 
			
			if (G.contains("中检通过") || G.contains("中检未通过")) {
				if (!granted.getMidinspection().isEmpty()){
					Set<GeneralMidinspection> midinspections = granted.getMidinspection();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					for (GeneralMidinspection midinspection: midinspections){
						String midImportedDate = sdf.format(midinspection.getImportedDate());
						if ("2013-10-20".equals(midImportedDate) && midinspection.getFinalAuditResult() != 2) {
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
				} else {// 对不存在中检同意数据的项目，新增一条中检信息
					GeneralMidinspection gmi = new GeneralMidinspection();
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
					
			if (F.length() > 0) {
				Agency university = universityFinder.getUnivByName(I);
				GeneralFunding gf = new GeneralFunding();
				gf.setGranted(granted);
				gf.setDate(tool.getDate(2013, 12, 16));
				gf.setFee(Double.parseDouble(F));
				gf.setAgency(university);
				gf.setAgencyName(A);
				gf.setAttn("刘杰");
				dao.add(gf);
			}
			
			if (B.contains("和谐社会建设与合作式司法改革")) {
				GeneralVariation gvw= new GeneralVariation();
				gvw.setIsImported(1);
				gvw.setImportedDate(tool.getDate(2013, 9, 30));
				gvw.setApplicantSubmitStatus(3);
				gvw.setApplicantSubmitDate(tool.getDate(2013, 9, 30));
				gvw.setFinalAuditDate(tool.getDate(2013, 9, 30));
				gvw.setFinalAuditStatus(3);
				gvw.setFinalAuditResult(2);
				gvw.setWithdraw(1);
				gvw.setFinalAuditResultDetail("00000001000000000000");
				gvw.setFinalAuditorName(刘杰.getPerson().getName());
				gvw.setFinalAuditor(刘杰.getPerson());
				gvw.setFinalAuditorAgency(刘杰.getAgency());
				granted.setStatus(4);
				gvw.setGranted(granted);
				dao.add(gvw); 
			}
			
			if(A.length() == 0) {
				break;
			}
		}
	}


	private boolean test(Double a, Double b){
		DecimalFormat df = new DecimalFormat("#.#");
		boolean flag = false;
		if (a == null && b == null) {
			flag = true;
		}else if (a != null && b != null) {
			flag = df.format(a).equals(df.format(b));
		}
		return flag;
	}
	
	/**
	 * 检查中检数据是否库内存在
	 * @throws Exception  
	 */
	@SuppressWarnings("deprecation")
	private void checkProjectExistence() throws Exception {
//		Set<String> exMsg = new HashSet<String>();
		
		excelReader.readSheet(0);
//		int i = 0;
		int j = 0;
		int k = 0;
		List<String> list= new ArrayList<String>();
		while (next(excelReader)) {
			GeneralGranted granted = generalProjectFinder.findGranted(C);
			
			Agency university = universityFinder.getUnivByName(I);
	
//			if (!test(granted.getApproveFee(), Double.valueOf(E))) {
//				System.out.println("批准经费不匹配" + E + "实际经费：" + granted.getApproveFee() + "-" + C);
//				i++;
//			}
			
			if (university == null) {
				list.add(A);
			}
			
//			if (!granted.getMidinspection().isEmpty()) {
//				Set<GeneralMidinspection> midinspections = granted.getMidinspection();
//				for (GeneralMidinspection midinspection: midinspections) { 
//					if(midinspection.getFinalAuditResult() == 2) {
//						System.out.println("中检存在的项目：" + B + "项目编号为：" + C);
//						i++;
//					}
//				}
//			}
//			
//			if (!granted.getEndinspection().isEmpty()) {
//				Set<GeneralEndinspection> endinspections = granted.getGeneralEndinspection();
//				for (GeneralEndinspection endinspection: endinspections) { 
//					if(endinspection.getFinalAuditResultEnd() == 2) {
//						System.out.println("结项存在的项目：" + C + " - " + B);
//						k++;
//					}
//				}
//			}
			
	   if(A.length() == 0) {
				break;
			}
		}
		
		
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		System.out.println(j);
//		System.out.println(k);
//		if (exMsg.size() > 0) {
//			throw new RuntimeException(exMsg.toString().replaceAll(",\\s+", "\n"));
//			//System.out.println(exMsg);
//		}
	}


}

