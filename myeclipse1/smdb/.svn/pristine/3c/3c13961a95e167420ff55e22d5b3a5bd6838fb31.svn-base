package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.EntrustApplication;
import csdc.bean.GeneralApplication;
import csdc.bean.InstpApplication;
import csdc.bean.Officer;
import csdc.bean.SinossChecklogs;
import csdc.bean.SinossMembers;
import csdc.bean.SinossProjectApplication;
import csdc.tool.execution.finder.EntrustProjectFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.SinossTableTool;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;
/**
 * 2014年委托项目申请审核数据入库：《附件1：20140421_2014年一般项目初审最终结果（社科服务中心提供）.xls》    《附件2：2014年专项委托项目立项及拨款一览表.xls》
 * @author pengliang
 * 
 * 备注：本年度委托项目属于特殊数据
 * 
 */
public class EntrustProjectApplicationAudit2014Importer extends Importer {
	//《附件1：20140421_2014年一般项目初审最终结果（社科服务中心提供）.xls》
	private ExcelReader reader1;
	
	//《附件2：2014年专项委托项目立项及拨款一览表.xls》
	private ExcelReader reader2;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private EntrustProjectFinder entrustProjectFinder;
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private SinossTableTool sTool;
	
	private List<SinossChecklogs> sinossChecklogs = new ArrayList<SinossChecklogs>();
	
	/**
	 * 项目年度
	 */
	private final int year = 2014;
	
	/**
	 * 当前导入项目
	 */
	private SinossProjectApplication spa;
	/**
	 * 初始化社科服务中心提供的部级审核数据      项目申请人+项目名称 -> 未通过审核原因
	 */
	public Map<String, String> ministryCheck;
	
	public EntrustProjectApplicationAudit2014Importer(){
	
	}
	
	public EntrustProjectApplicationAudit2014Importer(String filepath1, String filePath2) {
		reader1 = new ExcelReader(filepath1);
		reader2 = new ExcelReader(filePath2);
	}
	
	@Override
	public void work() throws Throwable {
		 initMinistry();
		 importData();
	}
	
	public void importData() throws Exception {
		// 项目数
		int totalImport = 0;
		// 当前导入项目条数		 
		int currentImport = 0;
		System.out.println("开始导入...");
		
		reader2.readSheet(0);
		
		sTool.initSinossApplicationList("gener",10000,20000,"2014");
		sTool.initSinossChecklogsList("gener","2014");
		
		Officer 白晓 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '白晓'");
		
		Iterator<SinossProjectApplication> appIterator = sTool.sinossApplicationList.iterator();
		totalImport = reader2.getRowNumber();
		while(appIterator.hasNext()){	
			spa = appIterator.next();
			if(!isEntrustProject(spa.getProjectName(),spa.getUnitName(),tool.getName(spa.getApplyer()))){
				continue;
			}
			System.out.println("当前导入项目名：" + spa.getProjectName() + "  (学校代码：" + spa.getUnitCode() + ")————导入第" + (++currentImport) + "/" + totalImport+ "条数据");//用户查找出错项			
			EntrustApplication application = entrustProjectFinder.findApplication(spa.getProjectName(), spa.getApplyer(), year);
		
			projectapplicationAuditor(spa, application, 白晓);
			
			dao.addOrModify(application);
		}
		
		System.out.println("over");
		freeMemory();
	}

	public String projectapplicationAuditor(SinossProjectApplication spa,EntrustApplication application, Officer 白晓){		
		SinossChecklogs sic = null;
		//部级以下审核不通过(1,3,5)
		int statusNumber = 0;
		int lastCheckStatus = 0;
		boolean hasCheckInfo = false;  //是否有审核记录
		SinossChecklogs[] lastSChecklogs = {null, null};//[0]：校级审核记录；[1]:主管部门(省级)审核记录
		//分别获取校级和省级最终审核记录（有可能先通过，后来又不通过）
		sinossChecklogs = this.getSinossChecklogs(null);
		for(SinossChecklogs sicheck : sinossChecklogs){
			statusNumber = sicheck.getCheckStatus();
			if (statusNumber == 2 || statusNumber ==3 ){ //校级审核
				lastSChecklogs[0] = (lastSChecklogs[0] != null && sicheck.getCheckDate().before(lastSChecklogs[0].getCheckDate())) ? lastSChecklogs[0]: sicheck;
				hasCheckInfo = true;
			}else if (statusNumber == 4 || statusNumber ==5) { //省级审核
				lastSChecklogs[1] = (lastSChecklogs[1] != null && sicheck.getCheckDate().before(lastSChecklogs[1].getCheckDate())) ? lastSChecklogs[1]: sicheck;
				hasCheckInfo = true;
			}				
		}
		
		if(!hasCheckInfo){
			return "" ;
		}
		
		//获取最终审核状态
		if(lastSChecklogs[1] != null){ //校级必然通过
			lastCheckStatus = lastSChecklogs[1].getCheckStatus();
		}else{
			lastCheckStatus = lastSChecklogs[0].getCheckStatus();
		}
		
		application.setDeptInstAuditResult(2);//院系审核都通过
		application.setDeptInstAuditStatus(3);
		
		if(lastCheckStatus%2 ==1){   
			if(lastCheckStatus == 5){//省级审核不通过，说明校级通过
				sic = lastSChecklogs[1];
				application.setUniversityAuditResult(2);//[0：未审核；1：不同意；2：同意]
				application.setUniversityAuditStatus(3);//[0：默认；1：退回；2：暂存；3：提交]
				application.setUniversityAuditDate(lastSChecklogs[0].getCheckDate());
				application.setUniversityAuditorAgency(universityFinder.getUnivByName(lastSChecklogs[0].getChecker()));
				application.setUniversityAuditOpinion(lastSChecklogs[0].getCheckInfo());
				
				sinossChecklogs = this.getSinossChecklogs("2");
				sic = sinossChecklogs.iterator().next();
				application.setStatus(4);
				application.setProvinceAuditResult(1);
				application.setProvinceAuditStatus(3);
				application.setProvinceAuditDate(lastSChecklogs[1].getCheckDate());
				application.setProvinceAuditorAgency(universityFinder.getUnivByName(lastSChecklogs[1].getChecker()));
				application.setProvinceAuditOpinion(lastSChecklogs[1].getCheckInfo());				
			}
			if(lastCheckStatus ==3 || lastCheckStatus ==1){//校级审核不通过
				sic = lastSChecklogs[0];
				application.setStatus(3);
				application.setUniversityAuditResult(1);
				application.setUniversityAuditStatus(3);
				application.setUniversityAuditDate(lastSChecklogs[0].getCheckDate());
				application.setUniversityAuditorAgency(universityFinder.getUnivByName(lastSChecklogs[0].getChecker()));
				application.setUniversityAuditOpinion(lastSChecklogs[0].getCheckInfo());
			}
			application.setFinalAuditResult(1);
			application.setFinalAuditStatus(3);
			application.setFinalAuditDate(sic.getCheckDate());
			application.setFinalAuditorAgency(universityFinder.getUnivByName(sic.getChecker()));
			application.setFinalAuditOpinion(sic.getCheckInfo());
			return "";
		}
		
		//部级以下审核通过，查看部级审核结果（只记录了没通过的项目）
		if(lastCheckStatus%2 == 0){
			//校级必通过
			application.setStatus(3);
			application.setUniversityAuditResult(2);//[0：未审核；1：不同意；2：同意]
			application.setUniversityAuditStatus(3);//[0：默认；1：退回；2：暂存；3：提交]
			if(lastSChecklogs[0] != null){
				application.setUniversityAuditDate(lastSChecklogs[0].getCheckDate());
				application.setUniversityAuditorAgency(universityFinder.getUnivByName(lastSChecklogs[0].getChecker()));
				application.setUniversityAuditOpinion(lastSChecklogs[0].getCheckInfo());
			}
			//省级审核通过
			if(lastCheckStatus == 4){
				application.setStatus(4);
				application.setProvinceAuditResult(2);
				application.setProvinceAuditStatus(3);
				application.setProvinceAuditDate(lastSChecklogs[1].getCheckDate());
				application.setProvinceAuditorAgency(universityFinder.getUnivByName(lastSChecklogs[1].getChecker()));
				application.setProvinceAuditOpinion(lastSChecklogs[1].getCheckInfo());
			}
		}
				
		String applyerApplicationName = null;
		applyerApplicationName = application.getApplicantName() + application.getName();
		Set<Map.Entry<String, String>> checkInfos = ministryCheck.entrySet();
		Iterator<Entry<String, String>> iterator = checkInfos.iterator();
		while(iterator.hasNext()){
			Entry<String, String> checkInfo = iterator.next();
			if(checkInfo.getKey().equals(applyerApplicationName)){  //部级审核不通过 
				application.setStatus(5);
				application.setMinistryAuditResult(1);
				application.setMinistryAuditStatus(3);
				application.setMinistryAuditor(白晓);
				application.setMinistryAuditorName(白晓.getPerson().getName());
				application.setMinistryAuditorAgency(白晓.getAgency());
				application.setMinistryAuditDate(tool.getDate(2014, 4, 21));
				application.setMinistryAuditOpinion(checkInfo.getValue());
				
				application.setFinalAuditResult(1);
				application.setFinalAuditStatus(3);
				application.setFinalAuditor(白晓);
				application.setFinalAuditorName(白晓.getPerson().getName());
				application.setFinalAuditorAgency(白晓.getAgency());
				application.setFinalAuditorInst(白晓.getInstitute());
				application.setFinalAuditDate(tool.getDate(2014, 4, 21));
				application.setFinalAuditOpinion(checkInfo.getValue());
				return "";  
			}
		}
		//部级审核通过
		application.setStatus(5);
		application.setMinistryAuditResult(2);
		application.setMinistryAuditStatus(3);
		application.setMinistryAuditor(白晓);
		application.setMinistryAuditorName(白晓.getPerson().getName());
		application.setMinistryAuditorAgency(白晓.getAgency());
		application.setMinistryAuditDate(tool.getDate(2014, 4, 21));	
		
		return "";
	}
	
	/**
	 * 判断2014年的中间表申请项目是否为Excel中的委托项目
	 */
	private boolean isEntrustProject(String projectName, String unitversityName,String applicantName) throws Exception {		
		reader2.setCurrentRowIndex(1);
		while (next(reader2)) {
			if(C.equals(projectName) && D.equals(unitversityName) && tool.getName(E).equals(applicantName)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 初始化社科服务中心提供的部级审核数据      项目申请人+项目名称 -> 未通过审核原因 
	 * @throws Exception 
	 */
	public void initMinistry() throws Exception{
		Date begin = new Date();
		String applyerApplicationName = null;
		reader1.readSheet(0);		
		ministryCheck = new HashMap<String,String>();
		while (next(reader1)) {
			applyerApplicationName = C + E;
			ministryCheck.put(applyerApplicationName, F);
		}
		
		System.out.println("initMinistry complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
	
	/**
	 * 从内存的中间表获取指定项目的审核数据
	 * @param checkStatus[null:获取所有的；not null:获取指定审核状态的数据对象]
	 * @return
	 */
	public List<SinossChecklogs> getSinossChecklogs(String checkStatus){
		if(!sinossChecklogs.isEmpty()){
			sinossChecklogs.clear();
        }
        Iterator<SinossChecklogs> iterator = sTool.sinossChecklogsList.iterator();
		while(iterator.hasNext()){
			SinossChecklogs sc = iterator.next();
			if(sc.getProjectApplication().getId().equals(spa.getId())){
				if(checkStatus == null){
					sinossChecklogs.add(sc);
				}else if((sc.getCheckStatus()+"").equals(checkStatus)){
					sinossChecklogs.add(sc);
				}
			}
		}
		return sinossChecklogs;
	}
	
	public void freeMemory() {
		entrustProjectFinder.reset();
	}

}
