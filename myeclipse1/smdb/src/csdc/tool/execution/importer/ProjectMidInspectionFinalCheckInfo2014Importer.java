package csdc.tool.execution.importer;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.GeneralApplication;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMidinspection;
import csdc.bean.GeneralVariation;
import csdc.bean.InstpApplication;
import csdc.bean.InstpGranted;
import csdc.bean.InstpMidinspection;
import csdc.bean.InstpVariation;
import csdc.bean.Officer;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectVariation;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 中检项目最终审核数据入库：
 * @author pengliang
 * 
 * 备注：关于Final字段数据入库,单独放在一个Java文件里面（以后可能不用）。中检申请数据来自社科网（有各级审核信息），final审核数据来自社科服务中心
 *     （如果我们能及时将中检申请数据入库，社科服务中心的人就不会单独发一个final审核的Excel信息，他们会直接在smdb系统中进行网上审核，这样就会省去Final数据的入库，
 *      本Java文件就不需要执行了。）
 */
public class ProjectMidInspectionFinalCheckInfo2014Importer extends Importer {

	/*
	 * 《2014年一般项目中检情况一览表.xls》
	 */
	private ExcelReader reader1;
	
	/*
	 * 《2014年基地项目中检情况一览表.xls》
	 */
	private ExcelReader reader2;
	
	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	@Autowired
	private Tool tool;
	
	public ProjectMidInspectionFinalCheckInfo2014Importer(){
	}
	
	public ProjectMidInspectionFinalCheckInfo2014Importer(String filepath1,String filepath2) {
		reader1 = new ExcelReader(filepath1);
		reader2 = new ExcelReader(filepath2);
	}
	
	@Override
	protected void work() throws Throwable {
		ImportGeneralData();
		ImportInstData();
	}

	public void ImportGeneralData() throws Throwable {
		int currentRow =0;
		reader1.readSheet(0);
		reader1.setCurrentRowIndex(1);
		Officer 刘杰 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '刘杰'"); 
		while(next(reader1)){
			System.out.println("general:" + B + "---" + (++currentRow));
			//通过项目批准号获取立项数据
			GeneralGranted granted = generalProjectFinder.findGranted(C);
			//有三条数据刘杰已经提前审过
			if(B.equals("大学生“双服务”素质教育模式的研究与探索") || B.equals("省以下转移支付绩效定量评价与政策研究") || B.equals("日本江户时代滑稽本研究") || B.equals("《甘泽谣》整理与研究")){
				continue;
			}		
			//2014年特殊数据，手工增加中检
			if(B.equals("俗神叙事的演化逻辑：以陈靖姑传说等为例") || B.equals("春秋金文及其地域特征研究") || B.equals("“虚拟”的真实影响：个体在现实与网络空间社会认知的差异及影响机制研究")){
				GeneralMidinspection gmi = new GeneralMidinspection();
				gmi.setImportedDate(new Date());
				granted.addMidinspection(gmi);
				if(B.equals("俗神叙事的演化逻辑：以陈靖姑传说等为例")){
					gmi.setApplicantSubmitDate(tool.getDate(2014, 6, 30));
				}
				if(B.equals("春秋金文及其地域特征研究")){
					gmi.setApplicantSubmitDate(tool.getDate(2014, 6, 9));
				}
				if(B.equals("“虚拟”的真实影响：个体在现实与网络空间社会认知的差异及影响机制研究")){
					gmi.setApplicantSubmitDate(tool.getDate(2014, 6, 9));
				}
				
				gmi.setApplicantSubmitStatus(3);
				gmi.setStatus(5);
				gmi.setFinalAuditResult(2);
				gmi.setFinalAuditStatus(3);
				gmi.setFinalAuditor(刘杰);
				gmi.setFinalAuditorName(刘杰.getPerson().getName());
				gmi.setFinalAuditorAgency(刘杰.getAgency());
				gmi.setFinalAuditorInst(刘杰.getInstitute());
				gmi.setFinalAuditDate(tool.getDate(2014, 9, 30));				
				continue;
			}
			//获取立项项目最近的一次中检数据
			GeneralMidinspection gm = null;
		    Set<GeneralMidinspection> gms = granted.getMidinspection();
			for(GeneralMidinspection gmTemp : gms){
				if(gmTemp.getImportedDate().after(tool.getDate(2014, 10, 1))){ //此时间只需要在ProjectMidInspectionImporter执行导入前几天就可以了
					gm = gmTemp;
				}
			}
			if(F.equals("未中检")){
				continue;
			}else if(F.equals("延期中检")){
				GeneralVariation gVariation = new GeneralVariation();			
				gVariation = new GeneralVariation();
				granted.addVariation(gVariation);
				gVariation.setImportedDate(new Date());
				gVariation.setApplicantSubmitStatus(3);	
				gVariation.setOtherInfo("申请项目中检延期。");
				gVariation.setOther(1);
				gVariation.setApplicantSubmitDate(tool.getDate(2014, 6, 12));
				gVariation.setStatus(5);
													
				gVariation.setFinalAuditStatus(3);
				gVariation.setFinalAuditResult(2);
				gVariation.setFinalAuditResultDetail("00000000000000000001");
				gVariation.setFinalAuditor(刘杰);
				gVariation.setFinalAuditorName(刘杰.getPerson().getName());
				gVariation.setFinalAuditorAgency(刘杰.getAgency());
				gVariation.setFinalAuditorInst(刘杰.getInstitute());
				gVariation.setFinalAuditDate(tool.getDate(2014, 9, 30));
				continue;
			}else if(F.equals("撤项")){			
				GeneralVariation gVariation = new GeneralVariation();			
				granted.addVariation(gVariation);
				granted.setEndStopWithdrawDate(tool.getDate(2014, 9, 30));
				granted.setStatus(4);
				gVariation.setImportedDate(new Date());
				gVariation.setApplicantSubmitStatus(3);				
				gVariation.setApplicantSubmitDate(tool.getDate(2014, 6, 12));//根据中间表具体申请时间来定
				gVariation.setFinalAuditStatus(3);
				gVariation.setFinalAuditResult(2);
				gVariation.setFinalAuditResultDetail("00000001000000000000");
				gVariation.setWithdraw(1);
				gVariation.setVariationReason(G);
				gVariation.setStatus(5);
				
				gVariation.setFinalAuditor(刘杰);
				gVariation.setFinalAuditorName(刘杰.getPerson().getName());
				gVariation.setFinalAuditorAgency(刘杰.getAgency());
				gVariation.setFinalAuditorInst(刘杰.getInstitute());
				gVariation.setFinalAuditDate(tool.getDate(2014, 9, 30));
				continue;			
			}else if(F.equals("中检通过")){
				gm.setFinalAuditResult(2);
			}else if(F.equals("中检未通过")){
				gm.setFinalAuditResult(1);
				gm.setFinalAuditOpinion(G);
			}	
			gm.setStatus(5);
			gm.setFinalAuditStatus(3);
			gm.setFinalAuditor(刘杰);
			gm.setFinalAuditorName(刘杰.getPerson().getName());
			gm.setFinalAuditorAgency(刘杰.getAgency());
			gm.setFinalAuditorInst(刘杰.getInstitute());
			gm.setFinalAuditDate(tool.getDate(2014, 9, 30));
		}
	}
	
	public void ImportInstData() throws Throwable {
		int currentRow =0;
		reader2.readSheet(0);
		reader2.setCurrentRowIndex(1);
		Officer 刘杰 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '刘杰'"); 
		while(next(reader2)){
			System.out.println("base:" + B + "---" + (++currentRow));
			InstpGranted granted = instpProjectFinder.findGranted(C);
			InstpMidinspection im = null;
		    Set<InstpMidinspection> ims = (Set<InstpMidinspection>) granted.getMidinspection();
			for(InstpMidinspection imTemp : ims){
				if(imTemp.getImportedDate().after(tool.getDate(2014, 10, 1))){ 
					im = imTemp;
					break;
				}
			}

			if(F.equals("未中检") || F.equals("已结项")){
				continue;
			}else if(F.equals("延期中检")){			
				InstpVariation iVariation = new InstpVariation();;			
				granted.addVariation(iVariation);
				iVariation.setImportedDate(new Date());
				iVariation.setApplicantSubmitStatus(3);	
				iVariation.setOtherInfo("申请项目中检延期。");
				iVariation.setApplicantSubmitDate(tool.getDate(2014, 6, 12));
				iVariation.setOther(1);
				iVariation.setStatus(5);
												
				iVariation.setFinalAuditStatus(3);
				iVariation.setFinalAuditResult(2);
				iVariation.setFinalAuditResultDetail("00000000000000000001");
				iVariation.setFinalAuditor(刘杰);
				iVariation.setFinalAuditorName(刘杰.getPerson().getName());
				iVariation.setFinalAuditorAgency(刘杰.getAgency());
				iVariation.setFinalAuditorInst(刘杰.getInstitute());
				iVariation.setFinalAuditDate(tool.getDate(2014, 9, 30));
				continue;
			}else if(F.equals("中检通过")){
				im.setFinalAuditResult(2);  
			}else if(F.equals("中检未通过")){
				im.setFinalAuditResult(1);
				im.setFinalAuditOpinion(G);
			}
			im.setStatus(5);
			im.setFinalAuditStatus(3);
			im.setFinalAuditor(刘杰);
			im.setFinalAuditorName(刘杰.getPerson().getName());
			im.setFinalAuditorAgency(刘杰.getAgency());
			im.setFinalAuditorInst(刘杰.getInstitute());
			im.setFinalAuditDate(tool.getDate(2014, 9, 30));
		}
	}
}
