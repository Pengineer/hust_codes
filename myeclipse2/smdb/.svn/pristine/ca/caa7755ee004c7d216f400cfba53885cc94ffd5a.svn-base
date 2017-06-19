package csdc.action.project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Account;
import csdc.bean.ProjectEndinspectionReview;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectGranted;
import csdc.bean.SystemOption;
import csdc.service.ext.IPersonExtService;
import csdc.tool.HqlTool;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 结项鉴定父类管理
 * 定义了子类需要实现的抽象方法并实现了所有项目结项鉴定共用的相关方法
 *
 */
public abstract class EndinspectionReviewAction extends ProjectBaseAction{
	
	@Autowired
	protected IPersonExtService personExtService;

	private static final long serialVersionUID = 1L;
	protected String reviewGrade;//鉴定等级
	protected String reviewerName;//专家姓名
	protected String endId;//结项id
	protected Integer reviewWay;//鉴定方式	1：通讯鉴定 2：会议鉴定
	protected int reviewStatus;//小组鉴定状态
	protected int reviewResult;//小组鉴定结果
	protected String reviewOpinionQualitative;//小组鉴定定性意见
	protected String reviewOpinion;//小组鉴定意见
	protected ProjectEndinspectionReview endReview;//专家鉴定
	protected ProjectEndinspection endinspection;//项目结项
	protected int submitStatus;//专家鉴定提交状态
	protected String specificationScore;//专家鉴定分数明细
	protected String opinion;//专家鉴定意见
	protected String qualitativeOpinion;//专家鉴定定性意见
	protected Double innovationScore;//创新和突破得分
	protected Double scientificScore;//科学性和规范性
	protected Double benefitScore;//价值和效益
	protected Double totalScore;//专家鉴定总分
	protected String grade;//专家鉴定等级
	@SuppressWarnings("rawtypes")
	protected List opinionList;//添加鉴定预备数据
	@SuppressWarnings("rawtypes")
	protected List groupOpinion;//小组专家意见
	protected String projectName,researchType,projectSubtype,projectTopic,dtypeNames,
	applicant,university;//高级检索条件
	protected int startYear,endYear;//高级检索条件
	protected Double minScore,maxScore;//高级检索条件
	protected Date startDate,endDate;////高级检索条件
	protected int revResultFlag;//录入鉴定信息是否成功	1：成功
	protected Date reviewDate;//鉴定时间
	
	private static final String[] COLUMN = {
		"gra.name",
		"gra.applicantName",
		"uni.name",
		"so.name",
		"app.disciplineType",
		"app.year",
		"endRev.submitStatus",
		"endRev.date desc",
		"endRev.score",
		"endi.reviewStatus"
	};//排序列
	public abstract String listHql();
	public String[] column() {
		return EndinspectionReviewAction.COLUMN;
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
			hql.append(this.projectService.getEndRevSimpleSearchHQL(searchType));		
		}
		hql.append(" and (endi.status >= 6 or endi.isImported = 1) ");
		session.put("endReviewMap", map);
		if (null != mainFlag && !mainFlag.isEmpty()){//项目管理首页进入
			String searchHql = this.projectService.mainSearch(account, mainFlag, projectType());
			hql.append(searchHql);
		}
		map = (Map) session.get("endReviewMap");
		HqlTool hqlTool = new HqlTool(hql.toString());
		hql.append(" group by " + hqlTool.getSelectClause() + ", endi.applicantSubmitDate having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)");
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
			if (projectSubtype != null && !projectSubtype.equals("-1")) {
				projectSubtype = projectSubtype.toLowerCase();
				hql.append(" and LOWER(so.id) like :projectSubtype");
				map.put("projectSubtype", "%" + projectSubtype + "%");
			}
		}else{//重大攻关
			if (researchType != null && !researchType.equals("-1")) {
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
			hql.append(" and LOWER(gra.applicantName) like :applicant");
			map.put("applicant", "%" + applicant + "%");
		}
		if(university!=null && !university.isEmpty()){
			university = university.toLowerCase();
			hql.append(" and LOWER(uni.name) like :university");
			map.put("university", "%" + university + "%");
		}
		if(submitStatus!=-1){
			hql.append(" and endRev.submitStatus = :submitStatus");
			map.put("submitStatus", submitStatus);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			map.put("startDate", df.format(startDate));
			hql.append(" and endRev.date is not null and to_char(endRev.date,'yyyy-MM-dd')>=:startDate");
		}
		if (endDate != null) {
			map.put("endDate", df.format(endDate));
			if(startDate == null){
				hql.append(" and endRev.date is not null");
			}
			hql.append(" and to_char(endRev.date,'yyyy-MM-dd')<=:endDate");
		}
		int resultStatus,saveStatus;
		if(reviewStatus!=-1){
			saveStatus=reviewStatus/10;
			resultStatus=reviewStatus%10;
			map.put("reviewStatus",  saveStatus);
			map.put("reviewResult", resultStatus);
			hql.append(" and endi.reviewStatus =:reviewStatus and endi.reviewResult =:reviewResult");
		}
		if(null != minScore && minScore>=0){
			hql.append(" and endRev.score >= :minScore");
			map.put("minScore", minScore);
		}
		if(null != maxScore && maxScore>0){
			hql.append(" and endRev.score <= :maxScore");
			map.put("maxScore", maxScore);
		}
		hql.append(" and (endi.status >= 6 or endi.isImported = 1) ");
		session.put("endReviewMap", map);
		map = (Map) session.get("endReviewMap");
		HqlTool hqlTool = new HqlTool(hql.toString());
		hql.append(" group by " + hqlTool.getSelectClause() + ", endi.applicantSubmitDate having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)");
		return new Object[]{
			hql.toString(),
			map,
			7,
			null
		};
	}
	
	
	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author yangfq
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
			searchQuery.put("dtypeNames",dtypeNames);
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
		if(submitStatus!=-1){
			searchQuery.put("submitStatus", submitStatus);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			searchQuery.put("startDate", df.format(startDate));
		}
		if (endDate != null) {
			searchQuery.put("endDate", df.format(endDate));
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
	 * 进入一般项目查看页面预处理
	 */
	public String toViewReview() {
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		this.projectid = this.projectService.getGrantedIdByAppId(entityId);
		return SUCCESS;
	}
	
	/**
	 * 查看一般项目
	 */
	public String viewReview() {
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 进入项目结项专家鉴定添加页面预处理
	 */
	public String toAdd() {
		AccountType accountType = loginer.getCurrentType();
		String appId = this.projectService.getApplicationIdByGrantedId(projectService.getGrantedIdByEndId(endId)).trim();
		//结项业务状态
		endStatus = this.projectService.getBusinessStatus(businessType(), appId);
		//结项各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
//		opinionList = this.projectService.getSOByParentName("一般项目结项鉴定定性意见");
		return SUCCESS;
	}
	
	/**
	 * 添加专家鉴定
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String add(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		endinspection = (ProjectEndinspection)this.dao.query(ProjectEndinspection.class, endId);
		if(endinspection.getStatus() != 6 || endinspection.getReviewStatus() == 3 || endinspection.getFinalAuditStatus() == 3){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_NO_RIGHRT);
			return INPUT;
		}
		String personId = baseService.getBelongIdByLoginer(loginer);
		ProjectEndinspectionReview endReview = (ProjectEndinspectionReview)this.projectService.getPersonalEndReview(endId, personId);
		if(endReview!=null){
			endReview.setSubmitStatus(submitStatus);
			endReview.setInnovationScore(innovationScore);
			endReview.setScientificScore(scientificScore);
			endReview.setBenefitScore(benefitScore);
			if(opinion != null){
				endReview.setOpinion(("A"+opinion).trim().substring(1));
			}else{
				endReview.setOpinion(null);
			}
			endReview.setQualitativeOpinion(qualitativeOpinion.trim());
			endReview.setDate(new Date());
			//设置总分
			Double s = 0.0;
			s = innovationScore + scientificScore + benefitScore;
			if(s<=100 && s>=0){
				endReview.setScore(s);
			}else{
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_TOTAL_SCORE_ERROR);
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_TOTAL_SCORE_ERROR);
			}
			endReview.setGrade(this.projectService.getReviewGrade(s));
			this.dao.modify(endReview);
		}else
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		return SUCCESS;
	}
	
	/**
	 * 进入项目结项专家鉴定页面预处理
	 */
	public String toModify() {
		AccountType accountType = loginer.getCurrentType();
		String appId = this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim();
		//结项业务状态
		endStatus = this.projectService.getBusinessStatus(businessType(), appId);
		//结项各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
//		opinionList = this.projectService.getSOByParentName("一般项目结项鉴定定性意见");
		ProjectEndinspectionReview endReview = (ProjectEndinspectionReview)this.dao.query(ProjectEndinspectionReview.class,this.entityId);
		SystemOption gra = (SystemOption) this.dao.query(SystemOption.class, endReview.getGrade().getId());
		this.innovationScore = endReview.getInnovationScore();
		this.scientificScore = endReview.getBenefitScore();
		this.benefitScore = endReview.getBenefitScore();
		this.qualitativeOpinion = endReview.getQualitativeOpinion();
		this.totalScore = endReview.getScore();
		this.grade = gra.getName();
        this.opinion = endReview.getOpinion();
		return SUCCESS;
	}
	
	/**
	 * 修改项目结项专家鉴定
	 */
	@Transactional
	public String modify(){
		return this.add();
	}
	
	/**
	 * 提交项目结项专家鉴定
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String submit(){
		AccountType accountType = loginer.getCurrentType();
		String appId = this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim();
		//结项业务状态
		endStatus = this.projectService.getBusinessStatus(businessType(), appId);
		//结项各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		endinspection = (ProjectEndinspection)this.dao.query(ProjectEndinspection.class, endId);
		if(endinspection.getStatus() != 6 || endinspection.getReviewStatus() == 3 || endinspection.getFinalAuditStatus() == 3){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_NO_RIGHRT);
			return INPUT;
		}
		endReview = (ProjectEndinspectionReview)this.projectService.getCurrentEndinspectionReviewByEndId(endId);
		endReview.setSubmitStatus(3);
		endReview.setDate(new Date());
		this.dao.modify(endReview);
		return SUCCESS;
	}
	
	/**
	 * 查看查看项目结项专家鉴定
	 */
	public String view(){
		endReview = (ProjectEndinspectionReview) this.dao.query(ProjectEndinspectionReview.class, entityId);
		String endId = (this.projectService.getCurrentEndinspectionByEndRevId(entityId)).getId();
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
        //查询鉴定专家姓名
		reviewerName = this.projectService.getReviewerName(entityId);
		//查询等级
		grade = ((SystemOption)this.dao.query(SystemOption.class, endReview.getGrade().getId())).getName();
		return SUCCESS;
	}
	
	/**
	 * 添加结项专家鉴定校验
	 */
	public void validateAdd(){
		this.validateEdit(1);
	}
	
	/**
	 * 修改结项专家鉴定校验
	 */
	public void validateModify(){
		this.validateEdit(2);
	}

	/**
	 * 提交结项专家鉴定校验
	 */
	public void validateSubmit(){
		this.validateEdit(3);
	}
	
	/**
	 * 添加结项专家小组鉴定校验
	 */
	public void validateAddGroup(){
		this.validateEdit(4);
	}
	
	/**
	 * 修改结项专家小组鉴定校验
	 */
	public void validateModifyGroup(){
		this.validateEdit(5);
	}
	
	/**
	 * 提交结项专家小组鉴定校验
	 */
	public void validateSubmitGroup(){
		this.validateEdit(6);
	}
	
	/**
	 * 编辑鉴定校验
	 * @param type 校验类型：1添加鉴定; 2修改鉴定; 3提交鉴定;  4添加小组鉴定; 5修改小组鉴定; 6提交小组鉴定
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	public void validateEdit(int type){
		String info = "";
		info = this.doWithValidateEdit(type);
		String appId = this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim();
		//校验业务设置状态
		endStatus = this.projectService.getBusinessStatus(businessType(), appId);
		if (endStatus == 0){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BUSINESS_STOP);
			info += ProjectInfo.ERROR_BUSINESS_STOP;
		}
		//校验业务时间是否有效
		AccountType accountType = loginer.getCurrentType();
		Date date = new Date();
		deadline = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		if (deadline != null && deadline.compareTo(new SimpleDateFormat("yyyy-MM-dd").format(date)) < 0){//业务已过截止时间
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_TIME_INVALIDATE);
			info += ProjectInfo.ERROR_TIME_INVALIDATE;
		}
		if(!"post".equals(projectType()) && !"entrust".equals(projectType())){
			int grantedYear = this.projectService.getGrantedYear(projectid);
			int endAllow = this.projectService.getEndAllowByGrantedDate(grantedYear);
			if(this.projectService.getPassMidinspectionByGrantedId(this.projectid).size() == 0 && endAllow == 0){//中检未通过并且结项时间未开始
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_CANNOT);
				info += ProjectInfo.ERROR_END_CANNOT;
			}
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
	
	/**
	 * 进入项目结项组鉴定添加页面预处理
	 */
	public String toAddGroup() {
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
//		opinionList = this.projectService.getSOByParentName("一般项目结项鉴定定性意见");
		return SUCCESS;
	}
	
	/**
	 * 添加项目结项组鉴定
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String addGroup(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		endinspection = (ProjectEndinspection)this.dao.query(ProjectEndinspection.class, endId);
		if(endinspection.getStatus() != 6 || endinspection.getReviewStatus() == 3 || endinspection.getFinalAuditStatus() == 3){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_NO_RIGHRT);
			return INPUT;
		}
		if(reviewOpinion != null){
			reviewOpinion = ("A"+reviewOpinion).trim().substring(1);
		}
		endinspection.setReviewOpinionQualitative(reviewOpinionQualitative);
		endinspection.setReviewWay(reviewWay);
		//查询各专家分数，计算总分、均分及等级
		double[] scores = this.projectService.getEndReviewScore(endId);
		double reviewTotalScore = scores[0];
		double reviewAvgScore = scores[1];
		endinspection.setReviewTotalScore(reviewTotalScore);
		endinspection.setReviewAverageScore(reviewAvgScore);
		endinspection.setReviewGrade(this.projectService.getReviewGrade(reviewAvgScore));
		if(endinspection.getFinalAuditStatus() != 3){
			Map auditMap = new HashMap();
			AuditInfo auditInfo = (AuditInfo)this.projectService.getAuditInfo(loginer, reviewResult, reviewStatus, reviewOpinion);
			auditMap.put("auditInfo", auditInfo);
			auditMap.put("isSubUni", this.projectService.isSubordinateUniversityGranted(projectid));
			endinspection.edit(auditMap);
			this.dao.modify(endinspection);
		}
		return SUCCESS;
	}
	
	/**
	 * 进入项目结项小组鉴定修改页面预处理
	 */
	public String toModifyGroup(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
//		opinionList = this.projectService.getSOByParentName("一般项目结项鉴定定性意见");
		ProjectEndinspection endinspection=(ProjectEndinspection)this.dao.query(ProjectEndinspection.class,this.endId);
		reviewWay = endinspection.getReviewWay();
		reviewStatus = endinspection.getReviewStatus();
		reviewOpinion = endinspection.getReviewOpinion();
		reviewOpinionQualitative = endinspection.getReviewOpinionQualitative();
		reviewResult = endinspection.getReviewResult();
		return SUCCESS;
	}
	
	/**
	 * 修改项目结项小组鉴定
	 */
	@Transactional
	public String modifyGroup(){
		return this.addGroup();
	}
	
	/**
	 * 提交项目结项小组鉴定
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submitGroup(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		endinspection = (ProjectEndinspection)this.dao.query(ProjectEndinspection.class, endId);
		if(endinspection.getStatus() != 6 || endinspection.getReviewStatus() == 3 || endinspection.getFinalAuditStatus() == 3){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_NO_RIGHRT);
			return INPUT;
		}
		Map auditMap = new HashMap();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
		auditMap.put("auditInfo",auditInfo);
		auditMap.put("isSubUni", this.projectService.isSubordinateUniversityGranted(this.projectid));
		endinspection.submit(auditMap);//提交操作结果
		dao.modify(endinspection);
		return SUCCESS;
	}

	/**
	 * 查看项目结项小组鉴定
	 */
	public String viewGroup(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		endinspection = (ProjectEndinspection)this.dao.query(ProjectEndinspection.class,this.endId);
		SystemOption grade = null;
		if(null != endinspection.getReviewGrade()){
			grade = (SystemOption)this.dao.query(SystemOption.class,endinspection.getReviewGrade().getId());
		}
		if(grade != null){
			reviewGrade = grade.getName();
		}
		return SUCCESS;
	}
	
	/**
	 * 查看项目结项小组内所有鉴定专家的鉴定意见
	 */
	public String viewGroupOpinion(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		this.groupOpinion = this.projectService.getReviewOpinionList(endId);
		return SUCCESS;
	}
	
	/**
	 * 进入项目结项鉴定录入添加页面预处理
	 * @author 余潜玉
	 */
	public String toAddResult() {
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
//		opinionList = this.projectService.getSOByParentName("一般项目结项鉴定定性意见");
//		session.put("opinionList", opinionList);
		setReviewDate(new Date());
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
	 * 添加和修改录入的项目结项鉴定的公共处理
	 * @param projectEndinspection 结项对象
	 * @author 余潜玉
	 */
	@Transactional
	public ProjectEndinspection doWithAddOrModifyResult(ProjectEndinspection projectEndinspection){
		//保存总评信息
		ProjectEndinspectionReview endRev = this.projectService.getGroupDirectorReview(projectEndinspection.getId());
		projectEndinspection.setReviewerName(endRev.getReviewerName());
		projectEndinspection.setReviewerAgency(endRev.getUniversity());
		projectEndinspection.setReviewOpinion((reviewOpinion != null) ? ("A" + reviewOpinion).trim().substring(1) : null);
		projectEndinspection.setReviewOpinionQualitative(reviewOpinionQualitative);
		projectEndinspection.setReviewWay(reviewWay);//后期资助项目鉴定方式只能为通讯鉴定
		projectEndinspection.setReviewResult(reviewResult);
		projectEndinspection.setReviewStatus(submitStatus);
		projectEndinspection.setReviewDate(new Date());
		//查询各专家分数，计算总分、均分及等级
		double[] reviewScore = this.projectService.getEndReviewScore(projectEndinspection.getId());
		double reviewTotalScore = reviewScore[0];
		double reviewAvgScore = reviewScore[1];
		projectEndinspection.setReviewTotalScore(reviewTotalScore);
		projectEndinspection.setReviewAverageScore(reviewAvgScore);
		projectEndinspection.setReviewGrade(this.projectService.getReviewGrade(reviewAvgScore));
		//录入鉴定(鉴定)提交时判断是否非导入数据，状态是否已在鉴定(鉴定)状态
		if(submitStatus == 3 && projectEndinspection.getIsImported() != 1 && projectEndinspection.getStatus() == 6){
			projectEndinspection.setStatus(projectEndinspection.getStatus() + 1);//结项状态跳一级
		}
		return projectEndinspection;
		
	}
	
	/**
	 * 一般项目结项鉴定录入提交
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String submitResult(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		doWithSubmitResult();
		int reviewType = this.projectService.getReviewTypeByAccountType(loginer.getCurrentType());
		if((reviewType == 3 || reviewType == 4)){//提交则对鉴定人信息进行入库处理
			this.projectService.doWithNewReview(endId);
   		}
		return SUCCESS;
	}
	/**
	 * 准备录入鉴定(鉴定)校验
	 * @author 肖雅
	 */
	public void validateToAddResult(){
		//校验是否已存在鉴定信息
		if(this.projectService.checkReview(endId) != -1){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_EXIST);
		}
	}
	
	/**
	 * 鉴定（鉴定）校验公用方法
	 * @param type 校验类型：1添加鉴定; 2修改鉴定; 3提交鉴定;  4添加小组鉴定; 5修改小组鉴定; 6提交小组鉴定
	 * @author 余潜玉
	 */
	public String doWithValidateEdit(int type){
		String info = "";
		if (endId == null || endId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}else{
			projectid = this.projectService.getGrantedIdByEndId(endId);
		}
		if (projectid == null || projectid.isEmpty()) {//项目id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}else{
			ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
			if(granted == null){
				this.addFieldError(GlobalInfo.ERROR_INFO,GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}else if(granted.getStatus() == 3){//中止
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_STOP);
				info += ProjectInfo.ERROR_PROJECT_STOP;
			}else if(granted.getStatus() == 4){//撤项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_REVOKE);
				info += ProjectInfo.ERROR_PROJECT_REVOKE;
			}
			if(!this.projectService.getPassEndinspectionByGrantedId(projectid).isEmpty()){//有已通过结项
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_ALREADY);
				info += ProjectInfo.ERROR_END_ALREADY;
			}
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
	
	/**
	 * 录入鉴定基本校验
	 * @param type 1:添加	2：修改	3：提交
	 */
	public void validateReviewBasic(int type){
		if(endId == null || endId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_NULL);
		}else{
			endinspection = (ProjectEndinspection) this.dao.query(ProjectEndinspection.class, endId);
			projectid = this.projectService.getGrantedIdByEndId(endId);
			if (projectid == null || projectid.isEmpty()) {//项目id不得为空
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			}else{
				ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
				if(granted == null){
					this.addFieldError(GlobalInfo.ERROR_INFO,GlobalInfo.ERROR_EXCEPTION_INFO);
				}else if(granted.getStatus() == 3){//中止
					this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_STOP);
				}else if(granted.getStatus() == 4){//撤项
					this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_REVOKE);
				}
			}
			AccountType accountType = loginer.getCurrentType();
			int status = endinspection.getStatus();
			if(endinspection.getFinalAuditStatus() == 3){
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_END_STOP);
			}else if((accountType.equals(AccountType.MINISTRY) && status < 6) || (accountType.equals(AccountType.PROVINCE) && status < 5) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && status < 4)){
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NO_RIGHRT);
			}
			int reviewflag = this.projectService.checkReview(endId);
			if(type == 1){//添加录入鉴定
				//校验是否已存在鉴定信息
				if(reviewflag != -1){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_EXIST);
				}
			}else if(type == 2 || type == 3){
				if(reviewflag == 23 || reviewflag == 33 || reviewflag == 43){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_SUBMIT);
				}
			}
		}
	}
	/**
	 * 录入鉴定的校验鉴定信息的公共部分
	 * @author 余潜玉
	 */
	public void validateReview(ProjectEndinspectionReview review){
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
	 * 录入时小组鉴定信息校验
	 */
	public void validateReviewGroup(){
		if(2 != submitStatus && 3 != submitStatus){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_SUBMITSTATUS_NULL);
		}
		if(reviewResult != 1 && reviewResult != 2){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEWER_NULL);
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
		endinspection = (ProjectEndinspection) this.dao.query(ProjectEndinspection.class, endId);
		reviewWay = endinspection.getReviewWay();
		reviewResult = endinspection.getReviewResult();
		reviewOpinionQualitative = endinspection.getReviewOpinionQualitative();
		reviewOpinion = endinspection.getReviewOpinion();
		reviewWay = endinspection.getReviewWay();
//		opinionList = this.projectService.getSOByParentName("一般项目结项鉴定定性意见");
		request.setAttribute("totalScore", endinspection.getReviewTotalScore());
		request.setAttribute("avgScore", endinspection.getReviewAverageScore());
		request.setAttribute("groupGrade", (endinspection.getReviewGrade()!= null) ? ((SystemOption)this.dao.query(SystemOption.class, endinspection.getReviewGrade().getId())).getName() : "");
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void doWithSubmitResult(){
		List endRevs = this.projectService.getAllEndReviewByEndId(endId);
		for(int i=0; i<endRevs.size(); i++){
			ProjectEndinspectionReview ger = (ProjectEndinspectionReview)endRevs.get(i);
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
		endinspection = (ProjectEndinspection) this.dao.query(ProjectEndinspection.class, endId);
		endinspection.setReviewStatus(3);
		endinspection.setReviewDate(endinspection.getProjectType().equals("post") ? this.projectService.setDateHhmmss(getReviewDate()) : new Date());
		//判断是否非导入数据，状态是否已在鉴定状态
		if(endinspection.getIsImported() != 1 && endinspection.getStatus() == 6){
			endinspection.setStatus(endinspection.getStatus() + 1);//结项状态跳一级
		}
		this.dao.modify(endinspection);
	}
	
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
	public String getEndId() {
		return endId;
	}
	public void setEndId(String endId) {
		this.endId = endId;
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
	public ProjectEndinspectionReview getEndReview() {
		return endReview;
	}
	public void setEndReview(ProjectEndinspectionReview endReview) {
		this.endReview = endReview;
	}
	public ProjectEndinspection getEndinspection() {
		return endinspection;
	}
	public void setEndinspection(ProjectEndinspection endinspection) {
		this.endinspection = endinspection;
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
	public Double getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
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
	@SuppressWarnings({ "rawtypes" })
	public List getOpinionList() {
		return opinionList;
	}
	@SuppressWarnings({ "rawtypes" })
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
}
