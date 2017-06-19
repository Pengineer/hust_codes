package csdc.action.project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Account;

import csdc.bean.ProjectApplicationReview;
import csdc.bean.ProjectApplication;
import csdc.bean.SystemOption;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

public abstract class ApplicationReviewAction extends ProjectBaseAction{
	
	private static final long serialVersionUID = 1L;
	protected String reviewGrade;//评审等级
	protected String reviewerName;//专家姓名
	protected String appRevId;//申请评审id
	protected Integer reviewWay;//评审方式	1：通讯评审 2：会议评审
	protected int reviewStatus;//小组评审状态
	protected int reviewResult;//小组评审结果
	protected String reviewOpinionQualitative;//小组评审定性意见
	protected String reviewOpinion;//小组评审意见
	protected ProjectApplicationReview appReview;//专家评审
	protected ProjectApplication application;//项目结项
	protected int submitStatus;//专家评审提交状态
	protected String specificationScore;//专家评审分数明细
	protected String opinion;//专家评审意见
	protected String qualitativeOpinion;//专家评审定性意见
	protected Double innovationScore;//创新和突破得分
	protected Double scientificScore;//科学性和规范性
	protected Double benefitScore;//价值和效益	
	protected Double totalScore;//专家评审总分
	protected String grade;//专家评审等级
	@SuppressWarnings("rawtypes")
	protected List opinionList;//添加评审预备数据)
	@SuppressWarnings("rawtypes")
	protected List groupOpinion;//小组专家意见
	protected String projectName,researchType,projectSubtype,projectTopic,dtypeNames,
	applicant,university,discipline;//高级检索条件
	protected int startYear,endYear;//高级检索条件
	protected Double minScore,maxScore;//高级检索条件
	protected Date startDate,endDate;////高级检索条件
	protected int revResultFlag;//录入评审信息是否成功	1：成功
	protected Date reviewDate;//鉴定时间
	
	private static final String[] COLUMN = {
		"app.name",
		"app.applicantName",
		"app.agencyName",
		"so.name",
		"app.disciplineType",
		"app.year",
		"appRev.submitStatus",
		"appRev.date desc",
		"appRev.score",
		"app.reviewStatus"
	};//排序列
	public abstract String listHql();
	public abstract String pageName();
	public String[] column() {
		return ApplicationReviewAction.COLUMN;
	}
	
	/**
	 * 初始化列表查询的hql、hql参数、默认排序列、错误信息(如有错误信息，加载列表时会显示该信息而非列表数据)
	 * @return 0:hql  1:hql参数   2:默认排序列编号  3:错误信息  
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] simpleSearchCondition() {
		Map session = ActionContext.getContext().getSession();
		Account account = loginer.getAccount();
		String belongId = baseService.getBelongIdByAccount(account);
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("belongId", belongId);
		hql.append(listHql());
		//初级检索
		if (keyword == null) {// 预处理关键字
			keyword = "";
		} else {
			keyword = keyword.toLowerCase();
		}
		if(!keyword.equals("")){
			map.put("keyword", "%" + keyword + "%");
			hql.append(this.projectService.getAppRevSimpleSearchHQL(searchType));		
		}
		hql.append(" and (app.status >= 6 or app.createMode = 1 or app.createMode = 2) ");
		session.put("appReviewMap", map);
		if (null != mainFlag && !mainFlag.isEmpty()){//项目管理首页进入
			String searchHql = this.projectService.mainSearch(account, mainFlag, projectType());
			hql.append(searchHql);
		}
		map = (Map) session.get("appReviewMap");
		return new Object[]{
			hql.toString(),
			map,
			7,
			null
		};
	}
	
	//高级检索
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] advSearchCondition(){
		Map session = ActionContext.getContext().getSession();
		Account account = loginer.getAccount();
		String belongId = baseService.getBelongIdByAccount(account);
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("belongId", belongId);
		hql.append(listHql());
		//高级检索
		if(projectName!=null && !projectName.isEmpty()){
			projectName = projectName.toLowerCase();
			hql.append(" and LOWER(app.name) like :projectName");
			map.put("projectName", "%" + projectName + "%");
		}
		if(!projectType().equals("key")){
			if(projectSubtype!= null && !projectSubtype.equals("-1")){
				projectSubtype = projectSubtype.toLowerCase();
				hql.append(" and LOWER(so.id) like :projectSubtype");
				map.put("projectSubtype", "%" + projectSubtype + "%");
			}
		}else{//重大攻关项目
			if(researchType!= null && !researchType.equals("-1")){
				researchType = researchType.toLowerCase();
				hql.append(" and LOWER(so.id) like :researchType");
				map.put("researchType", "%" + researchType + "%");
			}
		}
		if(dtypeNames != null && !dtypeNames.isEmpty()){
			String[] dtypes = dtypeNames.split("; ");
			int len = dtypes.length;
			if(len > 0){
				hql.append(" and (");
				for(int i = 0; i < len; i++){
					map.put("dtype" + i, "%" + dtypes[i].toLowerCase() + "%");
					hql.append("LOWER(app.disciplineType) like :dtype" + i);
					if (i != len-1)
						hql.append(" or ");
				}hql.append(")");
			}
		}
		if(startYear!=-1){
			hql.append(" and app.year>=:startYear");
			map.put("startYear", startYear);
		}
		if(endYear!=-1){
			hql.append(" and app.year<=:endYear");
			map.put("endYear", endYear);
		}
		if(applicant!=null && !applicant.isEmpty()){
			applicant = applicant.toLowerCase();
			hql.append(" and LOWER(app.applicantName) like :applicant");
			map.put("applicant", "%" + applicant + "%");
		}
		if(university!=null && !university.isEmpty()){
			university = university.toLowerCase();
			hql.append(" and LOWER(app.agencyName) like :university");
			map.put("university", "%" + university + "%");
		}
		if (discipline!=null && !discipline.isEmpty()) {
			discipline = discipline.toLowerCase();
			String[] disciplines = discipline.split("\\D+");
			hql.append(" and (1=0 ");
			for (int i = 0; i < disciplines.length; i++) {
				map.put("discipline0"+i, disciplines[i]+"%");
				hql.append(" or LOWER(app.discipline) like :discipline0" + i + " ");
				hql.append(" or LOWER(app.relativeDiscipline) like :discipline0" + i + " ");
				map.put("discipline1"+i, "; "+disciplines[i]+"%");
				hql.append(" or LOWER(app.discipline) like :discipline1" + i + " ");
				hql.append(" or LOWER(app.relativeDiscipline) like :discipline1" + i + " ");
			}
			hql.append(" )");
		}
		if(submitStatus!=-1){
			hql.append(" and appRev.submitStatus = :submitStatus");
			map.put("submitStatus", submitStatus);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			map.put("startDate", df.format(startDate));
			hql.append(" and appRev.date is not null and to_char(appRev.date,'yyyy-MM-dd')>=:startDate");
		}
		if (endDate != null) {
			map.put("endDate", df.format(endDate));
			if(startDate == null){
				hql.append(" and appRev.date is not null");
			}
			hql.append(" and to_char(appRev.date,'yyyy-MM-dd')<=:endDate");
		}
		int resultStatus,saveStatus;
		if(reviewStatus!=-1){
			saveStatus=reviewStatus/10;
			resultStatus=reviewStatus%10;
			map.put("reviewStatus",  saveStatus);
			map.put("reviewResult", resultStatus);
			hql.append(" and app.reviewStatus =:reviewStatus and app.reviewResult =:reviewResult");
		}
		if(null != minScore && minScore>=0){
			hql.append(" and appRev.score >= :minScore");
			map.put("minScore", minScore);
		}
		if(null != maxScore && maxScore>0){
			hql.append(" and appRev.score <= :maxScore");
			map.put("maxScore", maxScore);
		}
		hql.append(" and (app.status >= 6 or app.createMode = 1 or app.createMode = 2) ");
		session.put("appReviewMap", map);
		map = (Map) session.get("appReviewMap");
		return new Object[]{
			hql.toString(),
			map,
			7,
			null
		};
	}
	
	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author 肖雅
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAdvSearchQuery(Map searchQuery) {
		if(projectName!=null && !projectName.isEmpty()){
			searchQuery.put("projectName", projectName);
		}
		if(!projectType().equals("key")){
			if (projectSubtype != null && !projectSubtype.equals("-1")) {
				searchQuery.put("projectSubtype", projectSubtype);
			}
		}else{//重大攻关
			if (researchType != null && !researchType.equals("-1")) {
				searchQuery.put("researchType", researchType);
			}
		}
		if(dtypeNames != null && !dtypeNames.isEmpty()){
			searchQuery.put("dtypeNames", dtypeNames);
		}
		if(discipline != null && !discipline.isEmpty()){
			searchQuery.put("discipline", discipline);
		}
		if(startYear!=-1){
			searchQuery.put("startYear", startYear);
		}
		if(endYear!=-1){
			searchQuery.put("endYear", endYear);
		}
		if(applicant!=null && !applicant.isEmpty()){
			searchQuery.put("applicant", applicant);
		}
		if(university!=null && !university.isEmpty()){
			searchQuery.put("university", university);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			searchQuery.put("startDate", df.format(startDate));
		}
		if (endDate != null) {
			searchQuery.put("endDate", df.format(endDate));
		}
		if(submitStatus!=-1){
			searchQuery.put("submitStatus", submitStatus);
		}
		if(reviewStatus!=-1){
			searchQuery.put("reviewStatus", reviewStatus);
		}
		if(null != minScore && minScore>=0){
			searchQuery.put("minScore", minScore);
		}
		if(null != maxScore && maxScore>0){
			searchQuery.put("maxScore", maxScore);
		}
	}
	
	/**
	 * 进入查看页面
	 */
	public String toViewReview() {
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查看查询
	 */
	public String viewReview() {
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 准备添加专家评审(鉴定)
	 */
	public String toAdd() {
		//申请业务状态
		appStatus = this.projectService.getBusinessStatus(businessType());
		//申请各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(businessType());
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}		
//		opinionList = this.projectService.getSOByParentName("一般项目申请评审定性意见");
		return SUCCESS;
	}
	
	/**
	 * 添加专家评审
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String add(){
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		application = (ProjectApplication)this.dao.query(ProjectApplication.class, entityId.trim());
		if(application.getStatus() != 6 || application.getReviewStatus() == 3 || application.getFinalAuditStatus() == 3){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_NO_RIGHRT);
			return INPUT;
		}
		String personId = baseService.getBelongIdByLoginer(loginer);
		ProjectApplicationReview appReview = (ProjectApplicationReview)this.projectService.getPersonalAppReview(entityId, personId);
		if(appReview!=null){
			appReview.setSubmitStatus(submitStatus);
			appReview.setInnovationScore(innovationScore);
			appReview.setScientificScore(scientificScore);
			appReview.setBenefitScore(benefitScore);
			if(opinion != null){
				appReview.setOpinion(("A"+opinion).trim().substring(1));
			}else{
				appReview.setOpinion(null);
			}
			appReview.setQualitativeOpinion(qualitativeOpinion.trim());
			appReview.setDate(new Date());
			//设置总分
			Double s = 0.0;
			s = innovationScore + scientificScore + benefitScore;
			if(s<=100 && s>=0){
				appReview.setScore(s);
			}else{
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_TOTAL_SCORE_ERROR);
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_TOTAL_SCORE_ERROR);
			}
			appReview.setGrade(this.projectService.getReviewGrade(s));
			this.dao.modify(appReview);
		}else
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		return SUCCESS;
	}
	
	/**
	 * 准备修改专家评审(鉴定)
	 */
	public String toModify() {
		//申请业务状态
		appStatus = this.projectService.getBusinessStatus(businessType());
		//申请各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(businessType());
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
//		opinionList = this.projectService.getSOByParentName("一般项目结项评审定性意见");
		ProjectApplicationReview appReview = (ProjectApplicationReview)this.dao.query(ProjectApplicationReview.class, appRevId);
		SystemOption gra = (SystemOption) this.dao.query(SystemOption.class, appReview.getGrade().getId());
		this.innovationScore = appReview.getInnovationScore();
		this.scientificScore = appReview.getBenefitScore();
		this.benefitScore = appReview.getBenefitScore();
		this.qualitativeOpinion = appReview.getQualitativeOpinion();
		this.totalScore = appReview.getScore();
		this.grade = gra.getName();
		this.opinion = appReview.getOpinion();
		return SUCCESS;
	}
	
	/**
	 * 修改专家评审(鉴定)
	 */
	@Transactional
	public String modify(){
		return this.add();
	}
	
	/**
	 * 提交专家评审(鉴定)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String submit(){
		//申请业务状态
		appStatus = this.projectService.getBusinessStatus(businessType());
		//申请各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(businessType());
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		application = (ProjectApplication)this.dao.query(ProjectApplication.class, entityId);
		if(application.getStatus() != 6 || application.getReviewStatus() == 3 || application.getFinalAuditStatus() == 3){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_NO_RIGHRT);
			return INPUT;
		}
		appReview = (ProjectApplicationReview)this.projectService.getCurrentApplicationReviewByAppId(entityId);
		appReview.setSubmitStatus(3);
		appReview.setDate(new Date());
		this.dao.modify(appReview);
		return SUCCESS;
	}
	
	/**
	 * 查看专家评审
	 */
	public String view(){
		appReview = (ProjectApplicationReview) this.dao.query(ProjectApplicationReview.class, appRevId);
		String entityId = (this.projectService.getCurrentApplicationByAppRevId(appRevId)).getId();
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
        //查询评审专家姓名
		reviewerName = this.projectService.getReviewerNameFromAppReview(appRevId);
		//查询等级
		grade = ((SystemOption)this.dao.query(SystemOption.class, appReview.getGrade().getId())).getName();
		return SUCCESS;
	}
	
	/**
	 * 获取小组评审的结果
	 */
	public String getGroupRev(){
		AccountType accountType = loginer.getCurrentType();
		List applicationReviews = new ArrayList();//所有人评审信息
		applicationReviews = this.projectService.getAllAppReviewList(entityId, accountType);
		
		if (null !=applicationReviews && applicationReviews.size() != 0) {
			jsonMap.put("applicationReviews", applicationReviews);//所有人评审信息
		} else {
			jsonMap.put("applicationReviews", null);//所有人评审信息
		}
		return SUCCESS;
	}
	
	
	public void validateAdd(){
		this.validateEdit(1);
	}
	
	public void validateModify(){
		this.validateEdit(2);
	}

	public void validateSubmit(){
		this.validateEdit(3);
	}
	
	public void validateAddGroup(){
		this.validateEdit(4);
	}
	
	public void validateModifyGroup(){
		this.validateEdit(5);
	}
	
	public void validateSubmitGroup(){
		this.validateEdit(6);
	}
	
	/**
	 * 编辑评审校验
	 * @param type 校验类型：1添加评审; 2修改评审; 3提交评审;  4添加小组评审; 5修改小组评审; 6提交小组评审
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	public void validateEdit(int type){
		String info = "";
		info = this.doWithValidateEdit(type);
		//校验业务设置状态
		appStatus = this.projectService.getBusinessStatus(businessType());
		if (appStatus == 0){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BUSINESS_STOP);
		}
		//校验业务时间是否有效
		Date date = new Date();
		deadline  = this.projectService.checkIfTimeValidate(businessType());
		if (deadline != null && deadline.compareTo(new SimpleDateFormat("yyyy-MM-dd").format(date)) < 0){//业务已过截止时间
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_TIME_INVALIDATE);
		}
		if(type == 4 || type == 5){
			if(reviewWay != 1 && reviewWay != 2){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_WAY_NULL);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
//	/**
//	 * 编辑录入评审校验
//	 * @param type 校验类型：1添加录入评审; 2修改录入评审; 3提交录入评审
//	 * @author 余潜玉
//	 */
//	@SuppressWarnings("unchecked")
//	public void validateEditResult(int type){
//		String info = "";
//		if(endId == null || endId.trim().isEmpty()) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_NULL);
//			info += ProjectInfo.ERROR_END_NULL;
//		}else{
//			endinspection = (ProjectEndinspection) this.dao.query(ProjectEndinspection.class, endId);
//			projectid = this.projectService.getGrantedIdByEndId(endId);
////			info = this.doWithValidateEditResult(type);
//			int accountType = loginer.getCurrentType();
//			int status = endinspection.getStatus();
//			if(endinspection.getFinalAuditStatus() == 3){
//				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_END_STOP);
//			}else if((accountType == 2 && status < 6) || (accountType == 3 && status < 5) || ((accountType == 4 || accountType == 5) && status < 4)){
//				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NO_RIGHRT);
//			}
//			int reviewflag = this.projectService.checkReview(endId);
//			if(type == 1){//添加录入评审
//				//校验是否已存在评审信息
//				if(reviewflag != -1){
//					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_EXIST);
//				}
//			}else if(type == 2 || type == 3){
//				if(reviewflag == 23 || reviewflag == 33 || reviewflag == 43){
//					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_SUBMIT);
//				}
//			}
//		}
//		if(type == 1 || type == 2){//添加录入评审或修改录入评审
//			if(reviewWay != 1 && reviewWay != 2){
//				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_WAY_NULL);
//				info += ProjectInfo.ERROR_REVIEW_WAY_NULL;
//			}
//		}
//		if (info.length() > 0) {
//			jsonMap.put(GlobalInfo.ERROR_INFO, info);
//		}
//	}
	/**
	 * 准备添加小组评审(鉴定)
	 */
	public String toAddGroup() {
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
//		opinionList = this.projectService.getSOByParentName("一般项目结项评审定性意见");
		return SUCCESS;
	}
	
	/**
	 * 添加小组评审(鉴定)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String addGroup(){
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		application = (ProjectApplication)this.dao.query(ProjectApplication.class, entityId);
		if(application.getStatus() != 6 || application.getReviewStatus() == 3 || application.getFinalAuditStatus() == 3){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_NO_RIGHRT);
			return INPUT;
		}
		if(reviewOpinion != null){
			reviewOpinion = ("A"+reviewOpinion).trim().substring(1);
		}
		application.setReviewOpinionQualitative(reviewOpinionQualitative);
		application.setReviewWay(reviewWay);
		//查询各专家分数，计算总分、均分及等级
		double[] scores = this.projectService.getAppReviewScore(entityId);
		double reviewTotalScore = scores[0];
		double reviewAvgScore = scores[1];
		application.setReviewGrade(this.projectService.getReviewGrade(reviewAvgScore));
		application.setReviewTotalScore(reviewTotalScore);
		application.setReviewAverageScore(reviewAvgScore);
		if(application.getFinalAuditStatus() != 3){
			Map auditMap = new HashMap();
			AuditInfo auditInfo = (AuditInfo)this.projectService.getAuditInfo(loginer, reviewResult, reviewStatus, reviewOpinion);
			auditMap.put("auditInfo", auditInfo);
			auditMap.put("isSubUni", this.projectService.isSubordinateUniversityApplication(entityId));
			application.edit(auditMap);
			this.dao.modify(application);
		}
		return SUCCESS;
	}
	
	/**
	 * 准备修改小组评审(鉴定)
	 */
	public String toModifyGroup(){
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
//		opinionList = this.projectService.getSOByParentName("一般项目结项评审定性意见");
		ProjectApplication application=(ProjectApplication)this.dao.query(ProjectApplication.class, entityId);
		reviewWay = application.getReviewWay();
		reviewStatus = application.getReviewStatus();
		reviewOpinion = application.getReviewOpinion();
		reviewOpinionQualitative = application.getReviewOpinionQualitative();
		reviewResult = application.getReviewResult();
		return SUCCESS;
	}
	
	/**
	 * 修改小组评审(鉴定)
	 */
	@Transactional
	public String modifyGroup(){
		return this.addGroup();
	}
	
	/**
	 * 提交小组评审(鉴定)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submitGroup(){
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		application = (ProjectApplication)this.dao.query(ProjectApplication.class, entityId);
		if(application.getStatus() != 6 || application.getReviewStatus() == 3 || application.getFinalAuditStatus() == 3){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_NO_RIGHRT);
			return INPUT;
		}
		Map auditMap = new HashMap();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
		auditMap.put("auditInfo",auditInfo);
		auditMap.put("isSubUni", this.projectService.isSubordinateUniversityApplication(entityId));
		application.submit(auditMap);//提交操作结果
		dao.modify(application);
		return SUCCESS;
	}

	/**
	 * 查看小组评审(鉴定)
	 */
	public String viewGroup(){
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		application = (ProjectApplication)this.dao.query(ProjectApplication.class, entityId);
		SystemOption grade = null;
		if(null != application.getReviewGrade()){
			grade = (SystemOption)this.dao.query(SystemOption.class,application.getReviewGrade().getId());
		}
		if(grade != null){
			reviewGrade = grade.getName();
		}
		return SUCCESS;
	}
	
	/**
	 * 查看小组评审(鉴定)意见
	 */
	public String viewGroupOpinion(){
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		this.groupOpinion = this.projectService.getReviewOpinionListFromAppReview(entityId);
		return SUCCESS;
	}
	
	/***
	 * 进入为项目结项评审添加专家
	 * @author yangfq
	 */
	public String toAddRevExpert(){
		return SUCCESS;
	}
	
	
	/**
	 * 准备录入评审
	 * @author 肖雅
	 */
	public String toAddResult() {
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
//		opinionList = this.projectService.getSOByParentName("一般项目结项评审定性意见");
//		session.put("opinionList", opinionList);
		setReviewDate(new Date());
		return SUCCESS;
	}
	/**
	 * 录入评审公共方法
	 * @param projectApplication 结项对象
	 * @author 余潜玉
	 */
	@Transactional
	public ProjectApplication doWithAddOrModifyResult(ProjectApplication projectApplication){
		//保存总评信息
		ProjectApplicationReview appRev = this.projectService.getGroupDirectorReviewFromAppReview(projectApplication.getId());
		projectApplication.setReviewerName(appRev.getReviewerName());
		projectApplication.setReviewerAgency(appRev.getUniversity());
		projectApplication.setReviewOpinion((reviewOpinion != null) ? ("A" + reviewOpinion).trim().substring(1) : null);
		projectApplication.setReviewOpinionQualitative(reviewOpinionQualitative);
		projectApplication.setReviewWay(reviewWay);
		projectApplication.setReviewResult(reviewResult);
		projectApplication.setReviewStatus(submitStatus);
		projectApplication.setReviewDate(new Date());
		//查询各专家分数，计算总分、均分及等级
		double[] reviewScore = this.projectService.getAppReviewScore(projectApplication.getId());
		double reviewTotalScore = reviewScore[0];
		double reviewAvgScore = reviewScore[1];
		projectApplication.setReviewTotalScore(reviewTotalScore);
		projectApplication.setReviewAverageScore(reviewAvgScore);
		projectApplication.setReviewGrade(this.projectService.getReviewGrade(reviewAvgScore));
		//录入评审(鉴定)提交时判断是否非导入数据，状态是否已在评审(鉴定)状态
		if(submitStatus == 3 && projectApplication.getCreateMode()!= 1 && projectApplication.getStatus() == 6){
			projectApplication.setStatus(projectApplication.getStatus() + 1);//结项状态跳一级
		}
		return projectApplication;
		
	}
	
	/**
	 * 提交录入评审
	 * @author 肖雅
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String submitResult(){
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		doWithSubmitResult();
//		int reviewType = this.projectService.getReviewTypeByAccountType(loginer.getCurrentType());
//		if((reviewType == 3 || reviewType == 4)){//提交则对评审人信息进行入库处理
//			this.projectService.doWithNewReviewFromAppReview(entityId);
//   		}
		return SUCCESS;
	}
	/**
	 * 准备录入评审(鉴定)校验
	 * @author 肖雅
	 */
	public void validateToAddResult(){
		//校验是否已存在鉴定信息
		if(this.projectService.checkReviewFromAppReview(entityId) != -1){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_EXIST);
		}
	}
	
	/**
	 * 评审（鉴定）校验公用方法
	 * @param type 校验类型：1添加评审; 2修改评审; 3提交评审;  4添加小组评审; 5修改小组评审; 6提交小组评审
	 * @author 肖雅
	 */
	public String doWithValidateEdit(int type){
		String info = "";
		if (entityId == null || entityId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}
		if(!this.projectService.getPassApplicationByAppId(entityId).isEmpty()){//该项目已立项
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_GRANTED_EXIST);
			info += ProjectInfo.ERROR_GRANTED_EXIST;
		}
		if(type == 1 || type == 2){
			if(submitStatus != 2 && submitStatus != 3){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(opinion.trim().length() > 2000){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_OPINION_OUT);
				info += ProjectInfo.ERROR_REVIEW_OPINION_OUT;
			}
			if("-1".equals(qualitativeOpinion)||qualitativeOpinion.trim().length() == 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_QUALITATIVE_OPINION_NULL);
				info += ProjectInfo.ERROR_REVIEW_QUALITATIVE_OPINION_NULL;
			}
			if(innovationScore == null){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_INNOVATION_SCORE_NULL);
				info += ProjectInfo.ERROR_END_ALREADY;
			}else if(innovationScore > 50){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_INNOVATION_SCORE_OUT);
				info += ProjectInfo.ERROR_END_ALREADY;
			}
			if(scientificScore == null){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_SCIENTIFIC_SCORE_NULL);
				info += ProjectInfo.ERROR_SCIENTIFIC_SCORE_NULL;
			}else if(scientificScore > 25){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_SCIENTIFIC_SCORE_OUT);
				info += ProjectInfo.ERROR_SCIENTIFIC_SCORE_OUT;
			}
			if(benefitScore == null){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BENEFIT_SCORE_NULL);
				info += ProjectInfo.ERROR_BENEFIT_SCORE_NULL;
			}else if(benefitScore > 25){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BENEFIT_SCORE_OUT);
				info += ProjectInfo.ERROR_BENEFIT_SCORE_OUT;
			}
		}else if(type == 4 || type == 5){
			if(reviewStatus != 2 && reviewStatus != 3){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(reviewResult != 1 && reviewResult != 2){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_RESULT_NULL);
				info += ProjectInfo.ERROR_REVIEW_RESULT_NULL;
			}
			if("-1".equals(reviewOpinionQualitative)||reviewOpinionQualitative.trim().length() == 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_QUALITATIVE_OPINION_NULL);
				info += ProjectInfo.ERROR_REVIEW_QUALITATIVE_OPINION_NULL;
			}
			if(reviewOpinion.trim().length() > 2000){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_OPINION_OUT);
				info += ProjectInfo.ERROR_REVIEW_OPINION_OUT;
			}
		}
		return info;
	}
	
//	/**
//	 * 录入评审（鉴定）校验公用方法
//	 * @param type 校验类型：1添加录入评审; 2修改录入评审; 3提交录入评审
//	 * @author 余潜玉
//	 */
//	public String doWithValidateEditResult(int type){
//		String info = "";
//		if (projectid == null || projectid.isEmpty()) {//项目id不得为空
//			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
//			info += GlobalInfo.ERROR_EXCEPTION_INFO;
//		}else{
//			ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
//			if(granted == null){
//				this.addFieldError(GlobalInfo.ERROR_INFO,GlobalInfo.ERROR_EXCEPTION_INFO);
//				info += GlobalInfo.ERROR_EXCEPTION_INFO;
//			}else if(granted.getStatus() == 3){//中止
//				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_STOP);
//				info += ProjectInfo.ERROR_PROJECT_STOP;
//			}else if(granted.getStatus() == 4){//撤项
//				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_REVOKE);
//				info += ProjectInfo.ERROR_PROJECT_REVOKE;
//			}
//		}
//		if(type == 1 || type == 2){//添加录入评审或修改录入评审
//			for(int i=0 ;i <this.reviewerNum();i++){
//				if(unitTypes.get(i)!=1 && unitTypes.get(i)!=2 && unitTypes.get(i)!=3){
//					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_DEPTINST_NULL);
//					info += ProjectInfo.ERROR_END_DEPTINST_NULL;
//				}
//				if(unitNames.get(i)!=null && unitNames.get(i).length()>50){
//					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_UNIVNAME_OUT);
//					info += ProjectInfo.ERROR_END_UNIVNAME_OUT;
//				}
//				if( deptNames.size()!=this.reviewerNum()  ||deptNames.get(i)!=null && deptNames.get(i).length()>50){
//					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_DEPTNAME_OUT);
//					info += ProjectInfo.ERROR_END_DEPTNAME_OUT;
//				}
//				if(null == qualitativeOpinions.get(i) || "-1".equals(qualitativeOpinions.get(i))){
//					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_QUALITATIVE_OPINION_NULL);
//					info += ProjectInfo.ERROR_REVIEW_QUALITATIVE_OPINION_NULL;
//				}
//				if(opinions.get(i)!= null && opinions.get(i).length()>200){
//					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_OPINION_OUT);
//					info += ProjectInfo.ERROR_REVIEW_OPINION_OUT;
//				}
//			}
//			if(2 != submitStatus && 3 != submitStatus){
//				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_SUBMITSTATUS_NULL);
//				info += ProjectInfo.ERROR_SUBMITSTATUS_NULL;
//			}
//			if(reviewResult != 1 && reviewResult != 2){
//				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_RESULT_NULL);
//				info += ERROR_REVIEW_RESULT_NULL;
//			}
//			if("-1".equals(reviewOpinionQualitative)||reviewOpinionQualitative.trim().length() == 0){
//				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_QUALITATIVE_OPINION_NULL);
//				info += ProjectInfo.ERROR_REVIEW_QUALITATIVE_OPINION_NULL;
//			}
//			if(reviewOpinion.trim().length() > 200){
//				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_OPINION_OUT);
//				info += ProjectInfo.ERROR_REVIEW_OPINION_OUT;
//			}
//		}
//		return info;
//	}
	
	/**
	 * 录入评审基本校验
	 * @param type 1:添加	2：修改	3：提交
	 */
	public void validateReviewBasic(int type){
		if(entityId == null || entityId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_APP_NULL);
		}else{
			application = (ProjectApplication) this.dao.query(ProjectApplication.class, entityId);
			if(!this.projectService.getPassApplicationByAppId(entityId).isEmpty()){//该项目已立项
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_GRANTED_EXIST);
			}
			AccountType accountType = loginer.getCurrentType();
			int status = application.getStatus();
			if(application.getFinalAuditStatus() == 3){
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_APP_STOP);
			}else if((accountType.equals(AccountType.MINISTRY) && status < 6) || (accountType.equals(AccountType.PROVINCE) && status < 5) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && status < 4)){
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NO_RIGHRT);
			}
			int reviewflag = this.projectService.checkReviewFromAppReview(entityId);
			if(type == 1){//添加录入评审
				//校验是否已存在评审信息
				if(reviewflag != -1){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_APP_REVIEW_EXIST);
				}
			}else if(type == 2 || type == 3){
				if(reviewflag == 23 || reviewflag == 33 || reviewflag == 43){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_APP_REVIEW_SUBMIT);
				}
			}
		}
	}
	/**
	 * 录入评审的校验评审信息的公共部分
	 * @author 余潜玉
	 */
	public void validateReview(ProjectApplicationReview review){
		if(review.getReviewerType() != 1 && review.getReviewerType() != 2){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEWER_TYPE_NULL);
		}
		if(review.getInnovationScore() == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_INNOVATION_SCORE_NULL);
		}else if(review.getInnovationScore() > 50){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_INNOVATION_SCORE_OUT);
		}
		if(review.getScientificScore() == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_SCIENTIFIC_SCORE_NULL);
		}else if(review.getScientificScore() > 25){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_SCIENTIFIC_SCORE_OUT);
		}
		if(review.getBenefitScore() == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BENEFIT_SCORE_NULL);
		}else if(review.getBenefitScore() > 25){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BENEFIT_SCORE_OUT);
		}
		if(review.getQualitativeOpinion() == null || "-1".equals(review.getQualitativeOpinion())){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_QUALITATIVE_OPINION_NULL);
		}
		if(review.getOpinion()!= null && review.getOpinion().length()>2000){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_OPINION_OUT);
		}
		if(loginer.getCurrentType().equals(AccountType.MINISTRY)){//教育部录入
			if(review.getReviewer() == null){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEWER_NULL);
			}
		}else {//其他
			if(review.getReviewerName() == null || review.getReviewerName().trim().isEmpty()){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_MEMBER_NAME_NULL);
			}
			if(review.getIdcardType() == null || "-1".equals(review.getIdcardType().trim())){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PERSON_IDCARD_TYPE_NULL);
			}
			if(review.getIdcardNumber() == null || review.getIdcardNumber().trim().isEmpty()){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PERSON_IDCARD_NUMBER_NULL);
			}else if(review.getIdcardNumber().trim().length() > 18){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PERSON_IDCARD_NUMBER_OUT);
			}
			if (review.getGender() == null || (!review.getGender().trim().equals("男") && !review.getGender().trim().equals("女"))){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PERSON_GENDER_NULL);
			}
			if(this.projectService.isPersonMatch(review.getIdcardType(), review.getIdcardNumber(), review.getReviewerName(), review.getGender()) == 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PERSON_EXCEPTION);
			}
			if(review.getDivisionType() == -1){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PERSON_DIVISION_TYPE_NULL);
			}
			if(review.getAgencyName() == null || review.getAgencyName().trim().isEmpty()){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_UNIT_NULL);
			}
			if(review.getDivisionName() == null || review.getDivisionName().trim().isEmpty()){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_DEPT_INST_NULL);
			}
		}
	}
	
	/**
	 * 录入时小组评审信息校验
	 */
	public void validateReviewGroup(){
		if(2 != submitStatus && 3 != submitStatus){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_SUBMITSTATUS_NULL);
		}
		if(reviewResult != 1 && reviewResult != 2){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_RESULT_NULL);
		}
		if("-1".equals(reviewOpinionQualitative)||reviewOpinionQualitative.trim().length() == 0){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_QUALITATIVE_OPINION_NULL);
		}
		if(reviewOpinion.trim().length() > 2000){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_OPINION_OUT);
		}
		if(reviewWay != 1 && reviewWay != 2){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_WAY_NULL);
		}
	}

	public void doWithToModifyResult(){
		application = (ProjectApplication) this.dao.query(ProjectApplication.class, entityId);
		reviewWay = application.getReviewWay();
		reviewResult = application.getReviewResult();
		reviewOpinionQualitative = application.getReviewOpinionQualitative();
		reviewOpinion = application.getReviewOpinion();
		reviewWay = application.getReviewWay();
//		opinionList = this.projectService.getSOByParentName("一般项目结项评审定性意见");
		request.setAttribute("totalScore", application.getReviewTotalScore());
		request.setAttribute("avgScore", application.getReviewAverageScore());
		request.setAttribute("groupGrade", (application.getReviewGrade()!= null) ? ((SystemOption)this.dao.query(SystemOption.class, application.getReviewGrade().getId())).getName() : "");
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void doWithSubmitResult(){
		List appRevs = this.projectService.getAllAppReviewByAppId(entityId);
		for(int i=0; i<appRevs.size(); i++){
			ProjectApplicationReview ger = (ProjectApplicationReview)appRevs.get(i);
			if(ger.getSubmitStatus()!=3){
				ger.setSubmitStatus(3);
				if(ger.getProjectType().equals("post")){
					ger.setDate(this.projectService.setDateHhmmss(getReviewDate()));
				}else{
					ger.setDate(new Date());
				}
				this.dao.modify(ger);
			}
		}
		application = (ProjectApplication) this.dao.query(ProjectApplication.class, entityId);
		application.setReviewStatus(3);
		application.setReviewDate(application.getType().equals("post") ? this.projectService.setDateHhmmss(getReviewDate()) : new Date());
		//判断是否非导入数据，状态是否已在评审状态
		if(application.getCreateMode() != 1 && application.getStatus() == 6){
			application.setStatus(application.getStatus() + 1);//结项状态跳一级
		}
		this.dao.modify(application);
	}
	
//	/**
//	 * 保存总评共用方法
//	 * @param hql 原始hql
//	 */
//	public ProjectEndinspection fillTotalReviewInfos(ProjectEndinspection projectEndinspection){
//		projectEndinspection.setReviewerName(reviewerNames.get(0));
//		projectEndinspection.setReviewerAgency((Agency)this.dao.query(Agency.class,universitys.get(0)));
//		projectEndinspection.setReviewOpinion((reviewOpinion != null) ? ("A"+reviewOpinion).trim().substring(1) : null);
//		projectEndinspection.setReviewOpinionQualitative(reviewOpinionQualitative);
//		projectEndinspection.setReviewWay(reviewWay);//后期资助项目鉴定方式只能为通讯鉴定
//		projectEndinspection.setReviewResult(reviewResult);
//		projectEndinspection.setReviewStatus(submitStatus);
//		return projectEndinspection;
//	}
	
//	@SuppressWarnings("unchecked")
//	public ProjectEndinspectionReview fillReviewInfos(ProjectEndinspectionReview endinspectionReview, int i){
//		int reviewerType = this.projectService.getReviewTypeByAccountType(loginer.getCurrentType());
//		endinspectionReview.setDate(new Date());
//		endinspectionReview.setOpinion(opinions.get(i));
//		endinspectionReview.setQualitativeOpinion(qualitativeOpinions.get(i));
//		endinspectionReview.setReviewer(null);
//		endinspectionReview.setReviewerName(reviewerNames.get(i));
//		endinspectionReview.setReviewerSn(i+1);
//		endinspectionReview.setReviewType(reviewerType);
//		endinspectionReview.setIsManual(1);//手动分配专家
//		endinspectionReview.setSubmitStatus(submitStatus);
//		endinspectionReview.setSpecificationId(this.projectService.getReviewSpecificationIds());
//		String scores = "";//鉴定分数明细，多个以英文分号和空格隔开
//		Double s = 0.0;//设置总分
//		for(int j=0;j < specificationNum();j++){
//			scores += specificationScores.get(i*specificationNum()+j);
//			s += Double.parseDouble(specificationScores.get(i*specificationNum()+j));
//			if(j!=specificationNum()-1){
//				   scores += "; ";
//			   }
//		}
//		if(s<=100 && s>=0){
//			endinspectionReview.setScore(s);
//		}else{
//			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_TOTAL_SCORE_ERROR);
//			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_TOTAL_SCORE_ERROR);
//		}
//		endinspectionReview.setGrade(this.projectService.getReviewGrade(s));
//		endinspectionReview.setSpecificationScore(scores);
//		if(unitTypes.get(i)==1 || unitTypes.get(i)==2){//院系或基地
//			Agency univ = (Agency)this.dao.query(Agency.class,universitys.get(i));
//			endinspectionReview.setUniversity(univ);
//			endinspectionReview.setAgencyName(univ.getName());
//			if(unitTypes.get(i)==1){//院系
//				Department dep = (Department)this.dao.query(Department.class,deptInsts.get(i));
//				endinspectionReview.setDepartment(dep);
//				endinspectionReview.setInstitute(null);
//				endinspectionReview.setDivisionName(dep.getName());
//			}else{//基地
//				Institute ins = (Institute)this.dao.query(Institute.class,deptInsts.get(i));
//				endinspectionReview.setDepartment(null);
//				endinspectionReview.setInstitute(ins);
//				endinspectionReview.setDivisionName(ins.getName());
//			}
//		}else if(unitTypes.get(i)==3){//其他单位与部门
//			endinspectionReview.setUniversity(null);
//			endinspectionReview.setDepartment(null);
//			endinspectionReview.setInstitute(null);
//			endinspectionReview.setAgencyName(unitNames.get(i));
//			endinspectionReview.setDivisionName(deptNames.get(i));
//		}
//		return endinspectionReview;
//	}
	
	public String doWithReviewGrade(double reviewAvgScore){
		String reviewGrade = "";
		if(reviewAvgScore > 90){
			reviewGrade = "youxiu";
		}else if(reviewAvgScore >= 65 && reviewAvgScore <= 90){
			reviewGrade = "hege";
		}else{
			reviewGrade = "buhege";
		}
		return reviewGrade;
	}
	
	public String getReviewGrade() {
		return reviewGrade;
	}
	public void setReviewGrade(String reviewGrade) {
		this.reviewGrade = reviewGrade;
	}
	public String getReviewerName() {
		return reviewerName;
	}
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
	public String getAppRevId() {
		return appRevId;
	}
	public void setAppRevId(String appRevId) {
		this.appRevId = appRevId;
	}
	public Integer getReviewWay() {
		return reviewWay;
	}
	public void setReviewWay(Integer reviewWay) {
		this.reviewWay = reviewWay;
	}
	public int getReviewStatus() {
		return reviewStatus;
	}
	public void setReviewStatus(int reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	public int getReviewResult() {
		return reviewResult;
	}
	public void setReviewResult(int reviewResult) {
		this.reviewResult = reviewResult;
	}
	public String getReviewOpinionQualitative() {
		return reviewOpinionQualitative;
	}
	public void setReviewOpinionQualitative(String reviewOpinionQualitative) {
		this.reviewOpinionQualitative = reviewOpinionQualitative;
	}
	public String getReviewOpinion() {
		return reviewOpinion;
	}
	public void setReviewOpinion(String reviewOpinion) {
		this.reviewOpinion = reviewOpinion;
	}
	public ProjectApplicationReview getAppReview() {
		return appReview;
	}
	public void setAppReview(ProjectApplicationReview appReview) {
		this.appReview = appReview;
	}
	public ProjectApplication getApplication() {
		return application;
	}
	public void setApplication(ProjectApplication application) {
		this.application = application;
	}
	public int getSubmitStatus() {
		return submitStatus;
	}
	public void setSubmitStatus(int submitStatus) {
		this.submitStatus = submitStatus;
	}
	public String getSpecificationScore() {
		return specificationScore;
	}
	public void setSpecificationScore(String specificationScore) {
		this.specificationScore = specificationScore;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getQualitativeOpinion() {
		return qualitativeOpinion;
	}
	public void setQualitativeOpinion(String qualitativeOpinion) {
		this.qualitativeOpinion = qualitativeOpinion;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Double getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}
	@SuppressWarnings("rawtypes")
	public List getGroupOpinion() {
		return groupOpinion;
	}
	@SuppressWarnings("rawtypes")
	public void setGroupOpinion(List groupOpinion) {
		this.groupOpinion = groupOpinion;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getResearchType() {
		return researchType;
	}
	public void setResearchType(String researchType) {
		this.researchType = researchType;
	}
	public String getProjectSubtype() {
		return projectSubtype;
	}
	public void setProjectSubtype(String projectSubtype) {
		this.projectSubtype = projectSubtype;
	}
	public String getProjectTopic() {
		return projectTopic;
	}
	public void setProjectTopic(String projectTopic) {
		this.projectTopic = projectTopic;
	}
	public String getDtypeNames() {
		return dtypeNames;
	}
	public void setDtypeNames(String dtypeNames) {
		this.dtypeNames = dtypeNames;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public int getStartYear() {
		return startYear;
	}
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	public int getEndYear() {
		return endYear;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	public Double getMinScore() {
		return minScore;
	}
	public void setMinScore(Double minScore) {
		this.minScore = minScore;
	}
	public Double getMaxScore() {
		return maxScore;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setMaxScore(Double maxScore) {
		this.maxScore = maxScore;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getRevResultFlag() {
		return revResultFlag;
	}
	public void setRevResultFlag(int revResultFlag) {
		this.revResultFlag = revResultFlag;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	public Date getReviewDate() {
		return reviewDate;
	}
	@SuppressWarnings("rawtypes")
	public List getOpinionList() {
		return opinionList;
	}
	@SuppressWarnings("rawtypes")
	public void setOpinionList(List opinionList) {
		this.opinionList = opinionList;
	}
	public Double getInnovationScore() {
		return innovationScore;
	}
	public void setInnovationScore(Double innovationScore) {
		this.innovationScore = innovationScore;
	}
	public Double getScientificScore() {
		return scientificScore;
	}
	public void setScientificScore(Double scientificScore) {
		this.scientificScore = scientificScore;
	}
	public Double getBenefitScore() {
		return benefitScore;
	}
	public void setBenefitScore(Double benefitScore) {
		this.benefitScore = benefitScore;
	}
	public String getDiscipline() {
		return discipline;
	}
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	
}
