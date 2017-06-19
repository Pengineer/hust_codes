package csdc.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.project.key.TopicSelectionApplyAction;
import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.Expert;
import csdc.bean.KeyTopicSelection;
import csdc.bean.ProjectEndinspection;
import csdc.bean.Teacher;
import csdc.service.IKeyService;
import csdc.tool.bean.AccountType;
@Transactional
public class KeyService extends ProjectService implements IKeyService  {
	
	//-------------------以下为对列表初级检索的处理-----------------
	//选题申报相关查询语句
	/**
	 * 获得当前登陆者的初级检索项目选题申报的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目选题申报的条件语句
	 */
	public String getKeyTopicSimpleSearchHQL(int searchType){
		String hql = "";
		if(searchType == 1){//按选题名称查询
			hql = " and LOWER(tops.name) like :keyword";
		}else if(searchType == 3){//按选题年份查询
			hql = " and cast(tops.year as string) like :keyword";
		}else{//按上述所有字段查询
			hql = " and (LOWER(tops.name) like :keyword or cast(tops.year as string) like :keyword)";
		}
		return hql;
	}
	
	/**
	 * 获得当前登陆者的初级检索项目选题申报的补充字段查询语句
	 * @param accountType 账号类型
	 * @return 初级检索项目选题申报的条件语句
	 */
	public String getKeyTopicSimpleSearchHQLWordAdd(AccountType accountType){
		String hql = "";
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql = ", tops.applicantSubmitStatus, tops.applicantSubmitDate ";
		}
		else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			hql = ", tops.deptInstAuditStatus, tops.deptInstAuditResult, tops.deptInstAuditDate ";
		}
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql = ", tops.universityAuditStatus, tops.universityAuditResult, tops.universityAuditDate, tops.universitySubmitStatus, tops.universitySubmitDate ";
		}
		else if(accountType.equals(AccountType.PROVINCE)) {
			hql = ", tops.provinceAuditStatus, tops.provinceAuditResult, tops.provinceAuditDate ";
		}
		else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)) {
			hql = ", tops.finalAuditDate, tops.finalAuditResult ";
		}
		return hql;
	}
	
	/**
	 * 获得当前登陆者的初级检索项目选题申报的补充条件查询语句
	 * @param accountType 账号类型
	 * @return 初级检索选题申报的条件语句
	 */
	public String getKeyTopicSimpleSearchHQLAdd(AccountType accountType){
		String hql = "";
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql = " and (tops.status >= 1 or tops.isImported=1) ";
		}
		else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			hql = " and (tops.status >= 2 or tops.isImported=1) ";
		}
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql = " and (tops.status >= 3 or tops.isImported=1) ";
		}
		else if(accountType.equals(AccountType.PROVINCE) ) {
			hql = " and (tops.status >= 4 or tops.isImported=1) ";
		}
		else if(accountType.equals(AccountType.MINISTRY)) {
			hql = " and (tops.status >= 5 or tops.isImported=1) ";
		}
		return hql;
	}
	
	/**
	 * 处理项目选题申报查看范围
	 * @param account 当前账号对象
	 * @return 查询语句
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String topicSelectionInSearch(Account account){
		String belongId = this.getBelongIdByAccount(account);
		AccountType type = account.getType();
		int isPrincipal = account.getIsPrincipal();
		Map session = ActionContext.getContext().getSession();
		Map map = (Map) session.get("topicSelectionMap");
		StringBuffer hql = new StringBuffer();
		if(type.equals(AccountType.ADMINISTRATOR)){}//系统管理员
		else if(type.equals(AccountType.MINISTRY)){//部级
			if(isPrincipal == 1){//主账号 
			} else {//子账号
				String ministryId = this.getAgencyIdByOfficerId(belongId);
				if (ministryId != null){
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if(type.equals(AccountType.PROVINCE)){//省级
			if(isPrincipal == 1){//主账号
				hql.append(" and ((uni.type=4 and uni.subjection.id=:belongId) or (expUni.type=4 and expUni.subjection.id=:belongId))");
				map.put("belongId", belongId);
			}else{//子账号
				String provinceId = this.getAgencyIdByOfficerId(belongId);
				if(provinceId != null){
					hql.append(" and ((uni.type=4 and uni.subjection.id=:provinceId) or (expUni.type=4 and expUni.subjection.id=:provinceId))");
					map.put("provinceId", provinceId);
				}else{
					hql.append(" and 1=0");
				}
			}
		} else if(type.equals(AccountType.MINISTRY_UNIVERSITY) || type.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			if (isPrincipal == 1){//主账号
				hql.append(" and (uni.id=:belongId or expUni.id=:belongId)");
				map.put("belongId", belongId);
			} else {//子账号
				String universityId = this.getAgencyIdByOfficerId(belongId);
				if (universityId != null){
					hql.append(" and (uni.id=:universityId or expUni.id=:universityId)");
					map.put("universityId", universityId);
				} else {
					hql.append(" and 1=0");
				}
			}
		}else if(type.equals(AccountType.DEPARTMENT)){//院系
			if(isPrincipal == 1){//主账号
				hql.append(" and tops.expDepartment.id=:belongId");
				map.put("belongId", belongId);
			}else{//子账号
				String departmentId = this.getDepartmentIdByOfficerId(belongId);
				if(departmentId != null){
					hql.append(" and tops.expDepartment.id=:departmentId");
					map.put("departmentId", departmentId);
				}else{
					hql.append(" and 1=0");
				}
			}
		}else if(type.equals(AccountType.INSTITUTE)){//研究机构
			if(isPrincipal == 1){//主账号
				hql.append(" and tops.expInstitute.id=:belongId");
				map.put("belongId", belongId);
			}else{//子账号
				String instituteId = this.getInstituteIdByOfficerId(belongId);
				if(instituteId != null){
					hql.append(" and tops.expInstitute.id=:instituteId");
					map.put("instituteId", instituteId);
				}else{
					hql.append(" and 1=0");
				}
			}
		}else if(type.within(AccountType.EXPERT, AccountType.STUDENT)){//外部专家与内部专家或学生
			hql.append(" and tops.applicantId=:belongId" );
			map.put("belongId", belongId);
		}else{
			hql.append(" and 1=0");
		}
		session.put("topicSelectionMap", map);
		return hql.toString();
	}
	
	
	/**
	 * 保存选题申报人id，用于添加、修改
	 * @param oldTopicSelection 原选题对象，oldTopicSelection对象的applicant字段id为person id
	 * @param NewTopicSelection 新选题对象，NewTopicSelection对象的applicant字段id为教师、专家或者学生id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public KeyTopicSelection setApplicantIdFromTopicSelection(KeyTopicSelection oldTopicSelection, KeyTopicSelection NewTopicSelection){
		//人员信息
		if(NewTopicSelection.getApplicantId() != null && !NewTopicSelection.getApplicantId().isEmpty()){//有成员id信息
			if(oldTopicSelection.getApplicantType() == 1){//教师
				List<Teacher> teacher =  this.getTeacherFetchPerson(NewTopicSelection.getApplicantId());
				if(null != teacher.get(0)){
					oldTopicSelection.setApplicantId(teacher.get(0).getPerson().getId());
					oldTopicSelection.setExpUniversity(teacher.get(0).getUniversity());
					oldTopicSelection.setExpDepartment(teacher.get(0).getDepartment());
					oldTopicSelection.setExpInstitute(teacher.get(0).getInstitute());
					oldTopicSelection.setExpAgencyName(teacher.get(0).getUniversity().getName());
					if(teacher.get(0).getDepartment() != null){
						oldTopicSelection.setExpDivisionName(teacher.get(0).getDepartment().getName());
					}else if(teacher.get(0).getInstitute() != null){
						oldTopicSelection.setExpDivisionName(teacher.get(0).getInstitute().getName());
					}else{
						oldTopicSelection.setExpDivisionName(null);
					}
				}
			}else if(oldTopicSelection.getApplicantType() == 2){//专家
				List<Expert> expert =  this.getExpertFetchPerson(NewTopicSelection.getApplicantId());
				if(null != expert.get(0)){
					oldTopicSelection.setApplicantId(expert.get(0).getPerson().getId());
					oldTopicSelection.setUniversity(null);
					oldTopicSelection.setExpAgencyName(expert.get(0).getAgencyName());
					oldTopicSelection.setExpDepartment(null);
					oldTopicSelection.setExpInstitute(null);
					oldTopicSelection.setExpDivisionName(expert.get(0).getDivisionName());
				}
			}
		}else{//不含成员id信息
			oldTopicSelection.setApplicantId(null);
			oldTopicSelection.setExpUniversity(null);
			oldTopicSelection.setExpDepartment(null);
			oldTopicSelection.setExpInstitute(null);
			oldTopicSelection.setExpAgencyName(null);
			oldTopicSelection.setExpDivisionName(null);
		}
		return oldTopicSelection;
	}
	
	/**
	 * 保存项目成员信息，用于查看、修改预处理
	 * @param TopicSelection 选题对象，TopicSelection对象的applicantId字段id为person的id
	 * @return 处理后的TopicSelection对象
	 */
	@SuppressWarnings("unchecked")
	public KeyTopicSelection setApplicantIdFromPerson(KeyTopicSelection topicSelection){
		if(topicSelection.getApplicantId() != null){//有申报者id信息
			if(topicSelection.getApplicantType() == 1){//教师
				List<Teacher> teacher = this.getTeacherFetchPerson("", topicSelection.getApplicantId());
				if(null != teacher.get(0)){
					topicSelection.setApplicantId(teacher.get(0).getId());
					topicSelection.setExpUniversity(teacher.get(0).getUniversity());
					topicSelection.setExpDepartment(teacher.get(0).getDepartment());
					topicSelection.setExpInstitute(teacher.get(0).getInstitute());
					topicSelection.setExpAgencyName(teacher.get(0).getUniversity().getName());
					if(teacher.get(0).getDepartment() != null){
						topicSelection.setExpDivisionName(teacher.get(0).getDepartment().getName());
					}else if(teacher.get(0).getInstitute() != null){
						topicSelection.setExpDivisionName(teacher.get(0).getInstitute().getName());
					}else{
						topicSelection.setExpDivisionName(null);
					}
				}
			}else if(topicSelection.getApplicantType() == 2){//专家
				List<Expert> expert = this.getExpertFetchPerson("", topicSelection.getApplicantId());
				if(null != expert.get(0)){
					topicSelection.setApplicantId(expert.get(0).getId());
					topicSelection.setUniversity(null);
					topicSelection.setExpAgencyName(expert.get(0).getAgencyName());
					topicSelection.setExpDepartment(null);
					topicSelection.setExpInstitute(null);
					topicSelection.setExpDivisionName(expert.get(0).getDivisionName());
				}
			}
		}else{//不含成员id信息
			topicSelection.setApplicantId(null);
			topicSelection.setExpUniversity(null);
			topicSelection.setExpDepartment(null);
			topicSelection.setExpInstitute(null);
			topicSelection.setExpAgencyName(null);
			topicSelection.setExpDivisionName(null);
		}
		return topicSelection;
	}
	
	/**
	 * 判断申报选题是否部属高校
	 * @param topsId 选题申请id
	 * @return 1:部署高校, 0:地方高校
	 */
	public int isSubordinateUniversityTopicSelection(String topsId){
		if(topsId == null){
			return -1;
		}
		int utype = 0;
		KeyTopicSelection topicSelection = (KeyTopicSelection) dao.query(KeyTopicSelection.class, topsId);
		if(null != topicSelection.getUniversity() && null != topicSelection.getUniversity().getId() && !topicSelection.getUniversity().getId().isEmpty()){
			Agency uni = (Agency) dao.query(Agency.class, topicSelection.getUniversity().getId());
			utype = uni.getType();
		}else if(null != topicSelection.getExpUniversity() && null != topicSelection.getExpUniversity().getId() && !topicSelection.getExpUniversity().getId().isEmpty()){
			Agency uni = (Agency) dao.query(Agency.class, topicSelection.getExpUniversity().getId());
			utype = uni.getType();
		}else{
			;
		}
		if(utype == 3){
			return 1;
		}
		return 0;
	}
	
	/**
	 * 根据当前项目投标id获取当前选题
	 * @param entityId 当前投标id
	 * @return 选题对象
	 */
	@SuppressWarnings("unchecked")
	public String getCurrentTopicSelectionByAppId(String entityId){
		if(entityId == null){
			return "";
		}
		String hql = "select app.topicSelection.id from KeyApplication app where app.id = ?";
		List<String> list = dao.query(hql, entityId);
		return (list.size() > 0) ? list.get(0) : "";
	}
	
	/**
	 * 根据personid获得研究人员所负责的项目信息列表
	 * @param projectid 项目立项id
	 * @param personid 研究人 id
	 * @return 项目信息列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getDireProjectListByGrantedId(String projectid, String personid){
		if(projectid == null || personid == null){
			return new ArrayList();
		}
		Map map1 = new HashMap();
		map1.put("projectid", projectid);
		map1.put("personId",personid);
		String hql1 = "select gra.name, app.englishName, app.year, gra.applicantName, " +
				"gra.university.name, gra.divisionName from KeyGranted gra, " +
				"KeyApplication app, KeyMember mem where " +
				"gra.application.id=app.id and mem.application.id=app.id and gra.id=:projectid and " +
				"mem.isDirector=1 and mem.member.id=:personId";
		List projectList = dao.query(hql1, map1);
	
		return projectList;
	}

	/**
	 * 根据projectid获得研究人员所负责的项目信息列表
	 * @param projectid 项目立项id
	 * @return 项目信息列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getDireProjectListByGrantedId(String projectid){
		if(projectid == null){
			return new ArrayList();
		}
		Map map1 = new HashMap();
		map1.put("projectid", projectid);
		String hql1 = "select gra.name, app.englishName, app.year, gra.applicantName, " +
				"gra.university.name, gra.divisionName from KeyGranted gra, " +
				"KeyApplication app where gra.application.id=app.id and gra.id=:projectid ";
		List projectList = dao.query(hql1, map1);
	
		return projectList;
	}
	
	/**
	 * 处理选题审核人和所在机构信息
	 * @param 选题对象
	 * @return 信息list
	 */
	public List getTopsAuditorInfo(KeyTopicSelection topicSelection){
		List topsAuditorInfo = new ArrayList<String>();
		topsAuditorInfo.add(0, topicSelection.getDeptInstAuditor() == null ? "" : topicSelection.getDeptInstAuditor().getId());//院系基地审核人id
		topsAuditorInfo.add(1, topicSelection.getDeptInstAuditorDept() == null ? "" : topicSelection.getDeptInstAuditorDept().getId());//审核人所在院系id
		topsAuditorInfo.add(2, topicSelection.getDeptInstAuditorInst() == null ? "" : topicSelection.getDeptInstAuditorInst().getId());//审核人所在基地id
		topsAuditorInfo.add(3, topicSelection.getUniversityAuditor() == null ? "" : topicSelection.getUniversityAuditor().getId());//高校审核人id
		topsAuditorInfo.add(4, topicSelection.getUniversityAuditorAgency() == null ? "" : topicSelection.getUniversityAuditorAgency().getId());//审核人所在高校id
		topsAuditorInfo.add(5, topicSelection.getProvinceAuditor() == null ? "" : topicSelection.getProvinceAuditor().getId());//省厅审核人id
		topsAuditorInfo.add(6, topicSelection.getProvinceAuditorAgency() == null ? "" : topicSelection.getProvinceAuditorAgency().getId());//审核人所在省厅id
		topsAuditorInfo.add(7, topicSelection.getFinalAuditor() == null ? "" : topicSelection.getFinalAuditor().getId());//最终审核人id
		topsAuditorInfo.add(8, topicSelection.getFinalAuditorAgency() == null ? "" : topicSelection.getFinalAuditorAgency().getId());//最终审核人机构id
		return topsAuditorInfo;
	}

}
