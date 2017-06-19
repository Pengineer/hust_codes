package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralMember;
import csdc.bean.GeneralVariation;
import csdc.bean.Institute;
import csdc.bean.InstpApplication;
import csdc.bean.InstpMember;
import csdc.bean.InstpVariation;
import csdc.bean.Person;
import csdc.bean.ProjectVariation;
import csdc.bean.SinossChecklogs;
import csdc.bean.SinossMembers;
import csdc.bean.SinossModifyContent;
import csdc.bean.SinossProjectVariation;
import csdc.bean.SpecialApplication;
import csdc.bean.SpecialVariation;
import csdc.bean.Teacher;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.ProductTypeNormalizer;
import csdc.tool.execution.importer.tool.SinossTableTool;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 变更数据自动化入库
 * @author pengliang
 * 
 */
@Component
public class ProjectVariationAutoImporter extends Importer {
	
	protected int importNumber = 0;
		
	@Autowired
	protected Tool tool;
	
	@Autowired
	protected SinossTableTool sTool;
	
	@Autowired
	protected InstpProjectFinder instpProjectFinder;
	
	@Autowired
	protected GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	protected UniversityFinder universityFinder;
	
	@Autowired
	protected DepartmentFinder departmentFinder;
	
	@Autowired
	protected ExpertFinder expertFinder;
	
	@Autowired
	protected IHibernateBaseDao dao;
	
	@Autowired
	protected UnivPersonFinder univPersonFinder;
	
	@Autowired
	protected InstituteFinder instituteFinder;
	
	@Autowired
	protected ProductTypeNormalizer productTypeNormalizer;
		
	protected List error = new ArrayList();
	
	//初始化审核数据
	protected Map<String, List<SinossChecklogs>> projectVaritionIdToSChecklogMap = null;
	
	//记录当前项目的审核数据
	protected List<SinossChecklogs> sinossTempChecklogs;
	
	protected int agencyError = 0;
	
	//总项目数
	protected int totalNum = 0;
	
	// 当前导入项目条数		  
	protected int currentNum = 0;
	
	// 总共导入项目条数		  
	protected int totalImportNum = 0;

	//是否取消导入（1：是；0：否）
	protected int status;
	
	//异常数据
	protected Map<String,List<String[]>> illegalException;
	
	//是否导入完毕 （1：是；0：否）
	protected int isFinished;
	
	/*
	 * 初始化中间表中所有变更项目的成员
	 */	
	protected Map<String, List<SinossMembers>> sinossMembersMap;	
	public void initSinossMembers(){
		Date begin = new Date();
		List<SinossProjectVariation> sinossProjectVariations = dao.query("select pv from SinossProjectVariation pv where pv.isAdded=1");
		for (SinossProjectVariation sinossProjectVariation : sinossProjectVariations) {
			if (sinossMembersMap == null) {
				sinossMembersMap = new HashMap<String, List<SinossMembers>>();
			}
			List<SinossMembers> initSinossMembers = dao.query("select sm from SinossMembers sm where sm.projectVariation.id= ?", sinossProjectVariation.getId());
			sinossMembersMap.put(sinossProjectVariation.getId(), initSinossMembers);
		}
		System.out.println("initSinossMembers completed! Use time : " + (new Date().getTime() - begin.getTime()) + "ms");		
	}
	
	/*
	 * 初始化中间表变更内容
	 */
	protected Map<String, List<SinossModifyContent>> sinossModifyContentMap;
	public void initSinossModifyContents(){
		Date begin = new Date();
		List<SinossProjectVariation> sinossProjectVariations = dao.query("select pv from SinossProjectVariation pv where pv.isAdded=1");
		for (SinossProjectVariation sinossProjectVariation : sinossProjectVariations) {
			if (sinossModifyContentMap == null) {
				sinossModifyContentMap = new HashMap<String, List<SinossModifyContent>>();
			}
			List<SinossModifyContent> initSinossModifyContents = dao.query("select smc from SinossModifyContent smc where smc.isAdded=1 and smc.projectVariation.id= ?", sinossProjectVariation.getId());
			sinossModifyContentMap.put(sinossProjectVariation.getId(), initSinossModifyContents);
		}
		System.out.println("initSinossModifyContents completed! Use time : " + (new Date().getTime() - begin.getTime()) + "ms");		
	}
	
	/**
	 * 初始化中间表中申请项目的审核信息
	 */
	@SuppressWarnings("unchecked")
	public void initSinossCheckData(){
		Long begin = System.currentTimeMillis();
		List<SinossChecklogs> sinossCheckData = dao.query("select scl from SinossChecklogs scl where scl.isAdded=1 and scl.projectVariation is not null");
		String projectVaritionId = null;
		for (SinossChecklogs scl : sinossCheckData) {
			if (projectVaritionIdToSChecklogMap == null) {
				projectVaritionIdToSChecklogMap = new HashMap<String, List<SinossChecklogs>>();
			}
			projectVaritionId = scl.getProjectVariation().getId();
			List<SinossChecklogs> tempSinossChecklogs = projectVaritionIdToSChecklogMap.get(projectVaritionId);
			if (tempSinossChecklogs == null) {
				tempSinossChecklogs = new ArrayList<SinossChecklogs>();
			}
			tempSinossChecklogs.add(scl);
			projectVaritionIdToSChecklogMap.put(projectVaritionId, tempSinossChecklogs);
		}
		System.out.println("initSinossCheckData completed! Use time : " + (System.currentTimeMillis() - begin) + "ms");		
	}
	
	/*
	 * 初始化项目成员（初始化到内存的过程慢，数据太多，没有明显的过滤条件）
	 */
	/*private List<InstpMember> instpMembers = new ArrayList<InstpMember>();	
	private List<GeneralMember> generalMembers = new ArrayList<GeneralMember>();
	private List<ProjectMember> initProjectMembers;
	public void initProjectMembers(){
		Date begin = new Date();
		initProjectMembers = dao.query("select o from ProjectMember o where o.projectType='general' or o.projectType='instp'");
		System.out.println("initProjectMembers completed! Use time : " + (new Date().getTime() - begin.getTime()) + "ms");		
	}*/
	
	@Override
	public void work() throws Throwable{
	}

	public Object getMember(String memberName, String unitName) throws Exception {
		Object member = new Object();
		memberName = memberName.replaceAll(" ", "");
		unitName = unitName.replaceAll(" ", "");

		Agency memberUniv = null;
		Department memberDept = null;
		Institute memberInst = null;
		String divisionName = null;
		for (int len = unitName.length(); memberUniv == null && len >= 1; len--) {
			for (int j = 0; memberUniv == null && j + len <= unitName.length(); j++) {
				memberUniv = universityFinder.getUnivByName(unitName.substring(j, j + len));
				divisionName = unitName.substring(j + len);
			}
		}
		
		if (memberUniv == null) {
			member = expertFinder.findExpert(memberName, unitName);
		} else {
			memberInst = instituteFinder.getInstitute(memberUniv, divisionName, false);
			if (memberInst == null) {
				memberDept = departmentFinder.getDepartment(memberUniv, divisionName, true);
			}
			member = univPersonFinder.findTeacher(memberName, memberUniv, memberDept, memberInst);
		}
		return member;
	}
	
	public void addGeneralMember(GeneralApplication application, Object oMember, String specialistTitle, String workDivision, int memberSn, int groupNumber) {
		GeneralMember member = new GeneralMember();
		application.addMember(member);
		
		Person person = null;
		if (oMember instanceof Teacher) {
			Teacher teacher = (Teacher) oMember;
			person = teacher.getPerson();
			member.setUniversity(teacher.getUniversity());
			member.setInstitute(teacher.getInstitute());
			member.setDepartment(teacher.getDepartment());
			if (teacher.getUniversity().getName() != null) {
				member.setAgencyName(teacher.getUniversity().getName());
			} else {
				System.out.println("出错：" + application.getName());
				member.setAgencyName("未知机构");  
				agencyError++;
			}
			if (teacher.getDepartment() != null) {
				member.setDivisionName(teacher.getDepartment().getName());
				member.setDivisionType(2);
			} else {
				member.setDivisionName(teacher.getInstitute().getName());
				member.setDivisionType(1);
			}
			
			member.setMemberType(1);
		} else {
			Expert expert = (Expert) oMember;
			person = expert.getPerson();
			if (expert.getAgencyName() != null) {
				member.setAgencyName(expert.getAgencyName());
			} else {
				System.out.println("出错：" + application.getName());
				member.setAgencyName("未知机构");
				agencyError++;
			}
			member.setMemberType(2);
		}
		
		member.setMember(person);
		member.setMemberName(person.getName());
		
		member.setSpecialistTitle(specialistTitle);
		member.setWorkDivision(workDivision);
		member.setMemberSn(memberSn);
		member.setGroupNumber(groupNumber);
	}
	
	public void addInstpMember(InstpApplication application, Object oMember, String specialistTitle, String workDivision, int memberSn, int groupNumber) {
		InstpMember member = new InstpMember();
		application.addMember(member);
		
		Person person = null;
		if (oMember instanceof Teacher) {
			Teacher teacher = (Teacher) oMember;
			person = teacher.getPerson();
			member.setUniversity(teacher.getUniversity());
			member.setInstitute(teacher.getInstitute());
			member.setDepartment(teacher.getDepartment());
			if (teacher.getUniversity().getName() != null) {
				member.setAgencyName(teacher.getUniversity().getName());
			} else {
				System.out.println("出错！" + application.getName());
				member.setAgencyName("未知机构");
				agencyError++;
			}
			if (teacher.getDepartment() != null) {
				member.setDivisionName(teacher.getDepartment().getName());
				member.setDivisionType(2);
			} else {
				member.setDivisionName(teacher.getInstitute().getName());
				member.setDivisionType(1);
			}
			
			member.setMemberType(1);
		} else {
			Expert expert = (Expert) oMember;
			person = expert.getPerson();
			if (expert.getAgencyName() != null) {
				member.setAgencyName(expert.getAgencyName());
			} else {
				System.out.println("出错！" + application.getName());
				member.setAgencyName("未知机构");
				agencyError++;
			}

			member.setMemberType(2);
		}
		
		member.setMember(person);
		member.setMemberName(person.getName());
		
		member.setSpecialistTitle(specialistTitle);
		member.setWorkDivision(workDivision);
		member.setMemberSn(memberSn);
		member.setGroupNumber(groupNumber);
	}
			
	/**
	* 得到指定月前的日期（格式为：2014-01）

	*/
	public String getBeforMonth(int month, Date date) {
	        Calendar c = Calendar.getInstance();//获得一个日历的实例  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
	  
	        c.setTime(date);//设置日历时间  
	        c.add(Calendar.MONTH,-month);//在日历的月份上增加6个月   
	        String strDate = sdf.format(c.getTime());//的到你想要得6个月前的日期   
	        return strDate;
	 }
	
	public Date getBeforMonthDate(int distinctMonth, Date date) {
        Calendar c = Calendar.getInstance();//获得一个日历的实例   
        c.setTime(date);//设置日历时间  
        c.add(Calendar.MONTH,-distinctMonth);//在日历的月份上增加6个月    
        return c.getTime();
	}
	
	/*
	 * 判断changeBefore和changeCurrent相同的索引位上是否都是1
	 */
	public boolean isAddedBefore(StringBuffer changeBefore, StringBuffer changeCurrent){
		for(int i = 0; i < 20; i++){
			if(changeBefore.charAt(i) == changeCurrent.charAt(i) && changeBefore.charAt(i) == '1'){
				return true;
			}
		}
			
		return false;
	}
	
	//导入审核信息
	public void AddProjectVaritionCheckLogs(SinossProjectVariation spv, ProjectVariation projectVariation) {
		if (projectVariation instanceof GeneralVariation) {
			projectVariation = (GeneralVariation)projectVariation;
		} else if (projectVariation instanceof InstpVariation) {
			projectVariation = (InstpVariation)projectVariation;
		} else if (projectVariation instanceof SpecialVariation) {
			projectVariation = (SpecialVariation)projectVariation;
		}
		
		sinossTempChecklogs = projectVaritionIdToSChecklogMap.get(spv.getId());
		SinossChecklogs[] maxSChecklogs = {null, null};//[0]：校级审核记录；[1]:主管部门(省级)审核记录
		for (SinossChecklogs sChecklog : sinossTempChecklogs) {
			int checkStatus = sChecklog.getCheckStatus();
			if (checkStatus == 2 || checkStatus ==3 ) {//校级审核
				maxSChecklogs[0] = (maxSChecklogs[0] != null && sChecklog.getCheckDate().before(maxSChecklogs[0].getCheckDate())) ? maxSChecklogs[0]: sChecklog;
			}else if (checkStatus == 4 || checkStatus ==5) {//主管部门审核
				maxSChecklogs[1] = (maxSChecklogs[1] != null && sChecklog.getCheckDate().before(maxSChecklogs[1].getCheckDate())) ? maxSChecklogs[1]: sChecklog;
			}
			sChecklog.setIsAdded(0);
		}
		//将中间表中的校级审核记录写到变更表的对应字段
		if (maxSChecklogs[0] != null) {
			if(maxSChecklogs[0].getCheckStatus() == 2){//校级同意			
				projectVariation.setUniversityAuditResult(2);
				if(projectVariation.getGranted().getUniversity().getType() == 3){//部属高校
					projectVariation.setStatus(5);
				}else{
					projectVariation.setStatus(4);
				}
				
			}else if(maxSChecklogs[0].getCheckStatus() == 3){
				projectVariation.setUniversityAuditResult(1);
				projectVariation.setStatus(3);
				projectVariation.setFinalAuditResult(1);
				projectVariation.setFinalAuditStatus(3);
				projectVariation.setFinalAuditDate(maxSChecklogs[0].getCheckDate());
				projectVariation.setFinalAuditResultDetail("00000000000000000000");
				projectVariation.setFinalAuditOpinion(maxSChecklogs[0].getCheckInfo());
			}
			projectVariation.setUniversityAuditStatus(3);
			projectVariation.setUniversityAuditDate(maxSChecklogs[0].getCheckDate());
			projectVariation.setUniversityAuditOpinion(maxSChecklogs[0].getCheckInfo());
			Agency university = universityFinder.getAgencyByName(maxSChecklogs[0].getChecker());
			projectVariation.setUniversityAuditorAgency(university);	
		}
		//将中间表中的主管部门审核记录写到变更表的对应字段
		if (maxSChecklogs[1] != null) {
			if(maxSChecklogs[1].getCheckStatus() == 4){
				projectVariation.setProvinceAuditResult(2);
				projectVariation.setStatus(5);
			}else if(maxSChecklogs[1].getCheckStatus() == 5){						
				projectVariation.setProvinceAuditResult(1);
				projectVariation.setStatus(4);
				projectVariation.setFinalAuditResult(1);
				projectVariation.setFinalAuditStatus(3);
				projectVariation.setFinalAuditDate(maxSChecklogs[1].getCheckDate());
				projectVariation.setFinalAuditResultDetail("00000000000000000000");
				projectVariation.setFinalAuditOpinion(maxSChecklogs[1].getCheckInfo());
			}
			projectVariation.setDeptInstAuditResult(2);
			projectVariation.setDeptInstAuditStatus(3);
			projectVariation.setUniversityAuditResult(2);
			projectVariation.setUniversityAuditStatus(3);
			projectVariation.setProvinceAuditStatus(3);
			projectVariation.setProvinceAuditDate(maxSChecklogs[1].getCheckDate());
			projectVariation.setProvinceAuditOpinion(maxSChecklogs[1].getCheckInfo());
			Agency province = universityFinder.getProByName(maxSChecklogs[1].getChecker());
			projectVariation.setProvinceAuditorAgency(province);
		}
	}
	
	//从中间表中录入审核信息(2015-05-18用AddProjectVaritionCheckLogs方法替换此方法，一段时候后即可删除此注释部分)
//	@SuppressWarnings("unchecked")
//	public void importCheckData(HashSet<String> dumpDateSet) {
//		System.out.println("开始导入变更审核信息。。。");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String importedDate = sdf.format(new Date());
//		//找出刚才录入的变更数据（内存中）
//		List<ProjectVariation> projectVariations = dao.query("from ProjectVariation so where to_char(so.importedDate, 'yyyy-MM-dd') = ?", importedDate);
//		
//		//在中间表中根据中检表中的立项id搜索对应数据
//		for (ProjectVariation projectVariation: projectVariations) {
//			//取消导入
//			if (status != 0) {
//				freeMemory();
//				throw new RuntimeException();
//			}
//			if (projectVariation.getOtherInfo() !=null && projectVariation.getOtherInfo().equals("申请项目中检延期。")) {
//				continue;
//			}
//			List<SinossProjectVariation> sProjectVariations = dao.query("from SinossProjectVariation o where o.projectGranted.id = ?", projectVariation.getGrantedId());
//			if (sProjectVariations.size() == 0) {
//				continue;
//			}
//			if (sProjectVariations.size() != 1) {								
//				for (SinossProjectVariation sProjectVariation : sProjectVariations) { 
//					for(String dumpDate : dumpDateSet) {
//						if (sdf.format(sProjectVariation.getDumpDate()).equals(dumpDate)) { //得到本项目本次导入的变更数据
//							System.out.println(projectVariation.getGrantedId());  
//							addCheckInfoDetial(sProjectVariation.getId(),projectVariation);
//							break;
//						}
//					}
//				}
//			} else {
//				addCheckInfoDetial(sProjectVariations.get(0).getId(),projectVariation);
//			}
//		}
//		System.out.println("Import over !");
//	}
//	@SuppressWarnings("unchecked")
//	public void addCheckInfoDetial(String sProjectVariationId,ProjectVariation projectVariation) {
//		if(projectVariation.getApplicantSubmitStatus() >1){
//			projectVariation.setDeptInstAuditResult(2);
//			projectVariation.setDeptInstAuditStatus(3);
//			projectVariation.setStatus(3);
//		}
//		
//		//在审核记录表中根据中间表中的立项id搜索对审核记录数据
//		List<SinossChecklogs> sChecklogs = dao.query("from SinossChecklogs o where o.projectVariation.id = ?", sProjectVariationId);
//		SinossChecklogs[] maxSChecklogs = {null, null};//[0]：校级审核记录；[1]:主管部门审核记录
//		for (SinossChecklogs sChecklog : sChecklogs) {
//			int checkStatus = sChecklog.getCheckStatus();
//			if (checkStatus == 2 || checkStatus ==3 ) {//校级审核
//				maxSChecklogs[0] = (maxSChecklogs[0] != null && sChecklog.getCheckDate().before(maxSChecklogs[0].getCheckDate())) ? maxSChecklogs[0]: sChecklog;
//			}else if (checkStatus == 4 || checkStatus ==5) {//主管部门审核
//				maxSChecklogs[1] = (maxSChecklogs[1] != null && sChecklog.getCheckDate().before(maxSChecklogs[1].getCheckDate())) ? maxSChecklogs[1]: sChecklog;
//			}				
//		}																																		
//		//将中间表中的校级审核记录写到变更表的对应字段
//		if (maxSChecklogs[0] != null) {
//			if(maxSChecklogs[0].getCheckStatus() == 2){//校级同意			
//				projectVariation.setUniversityAuditResult(2);
//				if(projectVariation.getGranted().getUniversity().getType() == 3){//部属高校
//					projectVariation.setStatus(5);
//				}else{
//					projectVariation.setStatus(4);
//				}
//				
//			}else if(maxSChecklogs[0].getCheckStatus() == 3){
//				projectVariation.setUniversityAuditResult(1);
//				projectVariation.setStatus(3);
//			}
//			projectVariation.setUniversityAuditStatus(3);
//			projectVariation.setUniversityAuditDate(maxSChecklogs[0].getCheckDate());
//			projectVariation.setUniversityAuditOpinion(maxSChecklogs[0].getCheckInfo());
//			Agency university = universityFinder.getAgencyByName(maxSChecklogs[0].getChecker());
//			projectVariation.setUniversityAuditorAgency(university);	
//		}
//		//将中间表中的主管部门审核记录写到变更表的对应字段
//		if (maxSChecklogs[1] != null) {
//			if(maxSChecklogs[1].getCheckStatus() == 4){
//				projectVariation.setProvinceAuditResult(2);
//				projectVariation.setStatus(5);
//			}else if(maxSChecklogs[1].getCheckStatus() == 5){						
//				projectVariation.setProvinceAuditResult(1);
//				projectVariation.setStatus(4);
//			}
//			projectVariation.setDeptInstAuditResult(2);
//			projectVariation.setDeptInstAuditStatus(3);
//			projectVariation.setUniversityAuditResult(2);
//			projectVariation.setUniversityAuditStatus(3);
//			projectVariation.setProvinceAuditStatus(3);
//			projectVariation.setProvinceAuditDate(maxSChecklogs[1].getCheckDate());
//			projectVariation.setProvinceAuditOpinion(maxSChecklogs[1].getCheckInfo());
//			Agency province = universityFinder.getProByName(maxSChecklogs[1].getChecker());
//			projectVariation.setProvinceAuditorAgency(province);
//		}
//	}

	/**
	 * 在数据自动入库过程中，根据以前的警告信息，以及当前遇到的警告情况，更新警告信息
	 * @param originExchangeNoteInfo
	 * @param exceptionName
	 * @param projectName
	 * @param errorInfo
	 * @return
	 */
	public String exchangeNoteInfo(String originExchangeNoteInfo, String exceptionName ,String projectName , String errorInfo){
		if (originExchangeNoteInfo == null) {
			return exceptionName + "_" + projectName + "_" + errorInfo;
		}else {
			return originExchangeNoteInfo + "; " + exceptionName + "_" + projectName + "_" + errorInfo;
		}
	}
	
	/**
	 * 必须释放初始化时占据的内存，否则审核信息将无法正常初始化
	 * @author 2014-8-30 
	 */
	public void freeMemory(){
		universityFinder.reset();
		instpProjectFinder.reset();
		generalProjectFinder.reset();
		departmentFinder.reset();
		expertFinder.reset();
		univPersonFinder.reset();
		instituteFinder.reset();
		sinossMembersMap = null;
		sinossModifyContentMap = null;
	}
	
	public ProjectVariationAutoImporter(){
	}
	public int getTotalNum() {
		return totalNum;
	}
	
	public int getCurrentNum() {
		return currentNum;
	}
	
	public Map getIllegalException() {
		return illegalException;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsFinished() {
		return isFinished;
	}

	public int getTotalImportNum() {
		return totalImportNum;
	}
}