package csdc.action.award.moesocial;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.AwardApplication;
import csdc.bean.AwardReview;
import csdc.bean.SystemOption;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.info.AwardInfo;
import csdc.tool.info.GlobalInfo;

/**
 * 社科奖励申请数据评审管理
 * @author 余潜玉  王燕
 */
public class ApplicationReviewAction extends ApplicationAction {

	private static final long serialVersionUID = 8662147363734830370L;
	private Double meaningScore, innovationScore, influenceScore, methodScore, totalScore, averageScore;//研究内容意义和前沿性得分,主要创新和学术价值分数,学术影响或效益方法得分,研究方法和学术规范得分,总分，平均分
	private String reviewWay; //评审形式
	private String awardGradeid;//获奖等级id
	private List awardGrade;//获奖等级列表
	private List groupOpinion;//小组所有专家平身意见
	protected String reviewOpinionQualitative;//小组评审定性意见
	protected String reviewOpinion;//小组评审意见
	private AwardReview awardReview;//评审对象
	private List<AwardReview> reviews;//评审记录
	protected int submitStatus;//提交状态
	protected int reviewStatus;//小组评审状态
	protected int reviewResult;//小组评审结果
	protected int groupReviewStatus;//小组评审状态
	protected int revResultFlag;//录入评审信息是否成功	1：成功
	private static final String HQL = "select aa.id, aa.productName, aa.applicantName, aa.agencyName, aa.disciplineType, pr.productType, aa.session, aa.status, " +
			"aa.applicant.id, aa.university.id, aa.file, ar.submitStatus, ar.date, aa.reviewStatus, aa.reviewResult from AwardApplication aa, Product pr, " +
			"AwardReview ar where pr.id = aa.product.id and ar.application.id = aa.id and ar.reviewer.id =:personId and aa.status > 5";
	private static final String PAGE_NAME="review";//公示及奖励列表页面名称

	public String pageName() {
		return ApplicationReviewAction.PAGE_NAME;
	}
	
	//初级检索奖励申请
		/**
	 * 初始化列表查询的hql、hql参数、默认排序列、错误信息(如有错误信息，加载列表时会显示该信息而非列表数据)
	 * @return 0:hql  1:hql参数   2:默认排序列编号  3:错误信息  
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer(HQL);
		Map map = new HashMap();
		map.put("personId",baseService.getBelongIdByLoginer(loginer));
		if (keyword1 > 0) {
			hql.append(" and aa.session =:session ");
			map.put("session", keyword1);
		}
		hql.append(" and ");
		hql = this.awardService.getHql(hql, searchType,4);
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		map.put("keyword", "%" + keyword + "%");
		return new Object[]{
			hql.toString(),
			map,
			11,
			null
		};
	}
	
	//高级检索
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] advSearchCondition(){
		StringBuffer hql = new StringBuffer(HQL);
		Map map = new HashMap();
		map.put("personId", baseService.getBelongIdByLoginer(loginer));
		if (productName != null && !productName.isEmpty()) {
			productName = productName.toLowerCase();
			hql.append(" and LOWER(aa.productName) like :productName");
			map.put("productName", "%" + productName + "%");
		}
		if (ptypeid != null && !"-1".equals(ptypeid)) {
			ptypeid = ptypeid.toLowerCase();
			hql.append(" and LOWER(pt.id) like :ptypeid");
			map.put("ptypeid",  ptypeid );
		}
		if(dtypeNames != null && !dtypeNames.isEmpty()){
			String[] dtypes = dtypeNames.split("; ");
			int len = dtypes.length;
			if(len > 0){
				hql.append(" and (");
				for(int i = 0; i < len; i++){
					map.put("disciplineType" + i, "%" + dtypes[i].toLowerCase() + "%");
					hql.append("LOWER(aa.disciplineType) like :disciplineType" + i);
					if (i != len-1)
						hql.append(" or ");
				}hql.append(")");
			}
		}
		if (applicantName != null && !applicantName.isEmpty()) {
			applicantName = applicantName.toLowerCase();
			hql.append(" and LOWER(aa.applicantName) like :applicantName");
			map.put("applicantName", "%" + applicantName + "%");
		}
		if (universityName != null && !universityName.isEmpty()) {
			universityName = universityName.toLowerCase();
			hql.append(" and LOWER(un.name) like :universityName");
			map.put("universityName", "%" + universityName + "%");
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			map.put("startDate", df.format(startDate));
			hql.append(" and ar.date is not null and to_char(ar.date,'yyyy-MM-dd')>=:startDate");
		}
		if (endDate != null) {
			map.put("endDate", df.format(endDate));
			if(startDate == null){
				hql.append(" and ar.date is not null");
			}
			hql.append(" and to_char(ar.date,'yyyy-MM-dd')<=:endDate");
		}
		if(session1 > 0){
			hql.append(" and aa.session >=:session1");
			map.put("session1",  session1 );
		}
		if(session2 > 0){
			hql.append(" and aa.session <=:session2");
			map.put("session2",  session2);
		}
		if(reviewStatus != -1){
			map.put("reviewStatus",  reviewStatus);
			hql.append(" and ar.submitStatus = :reviewStatus");
		}
		int resultStatus,saveStatus;
		if(groupReviewStatus != -1){
			saveStatus = groupReviewStatus/10;
			resultStatus = groupReviewStatus%10;
			map.put("reviewStatus",  saveStatus);
			map.put("reviewResult", resultStatus);
			hql.append(" and aa.reviewStatus =:reviewStatus and aa.reviewResult =:reviewResult");
		}
		return new Object[]{
			hql.toString(),
			map,
			11,
			null
		};
	}
	
	//专家准备评审
	public String toAdd(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		return SUCCESS;
	}
	
	//保存专家评审信息
	@SuppressWarnings("unchecked")
	@Transactional
	public String add(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityId);
		if(awardApplication.getStatus() != 6 || awardApplication.getReviewStatus() != 0 || awardApplication.getFinalAuditStatus() == 3){
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_CANNOT_DO);
			return INPUT;
		}
		awardReview = this.awardService.getAwardReview(entityId, baseService.getBelongIdByLoginer(loginer));
		if(awardReview.getSubmitStatus() != 3){//当前评审未提交，否则不能进行任何操作
			if(auditOpinion != null){
				auditOpinion = ("A" + auditOpinion).trim().substring(1);
			}
			awardReview.setMeaningScore(meaningScore);
			awardReview.setInnovationScore(innovationScore);
			awardReview.setInfluenceScore(influenceScore);
			awardReview.setMethodScore(methodScore);
			awardReview.setScore(meaningScore + innovationScore + influenceScore + methodScore);
			awardReview.setOpinion(auditOpinion);
			awardReview.setSubmitStatus(auditStatus);
			SystemOption grade = (SystemOption)dao.query(SystemOption.class, awardGradeid.trim());
			awardReview.setGrade(grade);
			awardReview.setDate(new Date());
			dao.modify(awardReview);
		}else{
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_SUBMIT_AlREADY);
			return INPUT;
		}
		return SUCCESS;
	}
	//校验添加
	@SuppressWarnings("unchecked")
	public void validateAdd(){
		String info = "";
		if(entityId == null || entityId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_VIEW_AWARDAPPLY_NULL);
			info += AwardInfo.ERROR_VIEW_AWARDAPPLY_NULL;
		}
		if(meaningScore == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_MEANING_SCORE_NULL);
			info += AwardInfo.ERROR_REVIEW_MEANING_SCORE_NULL;
		}else if(meaningScore > 20){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_MEANING_SCORE_OUT);
			info += AwardInfo.ERROR_REVIEW_MEANING_SCORE_OUT;
		}
		if(innovationScore == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_INNOVATION_SCORE_NULL);
			info += AwardInfo.ERROR_REVIEW_INNOVATION_SCORE_NULL;
		}else if(innovationScore > 30){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_INNOVATION_SCORE_OUT);
			info += AwardInfo.ERROR_REVIEW_INNOVATION_SCORE_OUT;
		}
		if(influenceScore == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_INFLUECCE_SCORE_NULL);
			info += AwardInfo.ERROR_REVIEW_INFLUECCE_SCORE_NULL;
		}else if(influenceScore > 30){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_INFLUENCE_SCORE_OUT);
			info += AwardInfo.ERROR_REVIEW_INFLUENCE_SCORE_OUT;
		}
		if(methodScore == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_METHOD_SCORE_NULL);
			info += AwardInfo.ERROR_REVIEW_METHOD_SCORE_NULL;
		}else if(methodScore > 20){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_METHOD_SCORE_OUT);
			info +=  AwardInfo.ERROR_REVIEW_METHOD_SCORE_OUT;
		}
		if("-1".equals(awardGradeid)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_AWARD_GRADE_NULL);
			info += AwardInfo.ERROR_AWARD_GRADE_NULL;
		}
		if(2 != auditStatus && 3 != auditStatus){
			this.addFieldError(GlobalInfo.ERROR_INFO,AwardInfo.ERROR_SUBMIT_STATUS_NULL);
			info += AwardInfo.ERROR_SUBMIT_STATUS_NULL;
		}
		if(auditOpinion.trim().length() > 200){
			this.addFieldError(GlobalInfo.ERROR_INFO,AwardInfo.ERROR_REVIEW_OPINION_OUT);
			info += AwardInfo.ERROR_REVIEW_OPINION_OUT;
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info.substring(0, info.length() - 1));
		}
	}
	
	
	//专家准备修改评审
	@SuppressWarnings("unchecked")
	public String toModify(){
		awardReview = (AwardReview)dao.query(AwardReview.class, entityId);
		String awardApplicationId = awardReview.getApplication().getId();
		if(!this.awardService.checkIfUnderControl(loginer, awardApplicationId.trim(), 18, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		awardApplication = (AwardApplication)dao.query(AwardApplication.class, awardApplicationId);
		if(awardApplication.getStatus() != 6 || awardApplication.getReviewStatus() != 0 || awardApplication.getFinalAuditStatus() == 3 || awardReview.getSubmitStatus() == 3){
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_CANNOT_DO);
			return INPUT;
		}
		auditOpinion = awardReview.getOpinion();
		meaningScore = awardReview.getMeaningScore();
		innovationScore = awardReview.getInnovationScore();
		influenceScore = awardReview.getInfluenceScore();
		methodScore = awardReview.getMethodScore();
		totalScore = awardReview.getScore();
		SystemOption grade = (SystemOption)dao.query(SystemOption.class, awardReview.getGrade().getId().trim());
		awardGradeid = grade.getId();
		return SUCCESS;
	}
	
	//专家修改评审
	@Transactional
	public String modify(){
		return this.add();
	}
	//校验修改
	public void validateModify(){
		this.validateAdd();
	}
	
	//查看专家评审
	public String viewReview(){
		awardReview = this.awardService.getAwardReview(entityId);
		String awardApplicationId = awardReview.getApplication().getId();
		if(!this.awardService.checkIfUnderControl(loginer, awardApplicationId.trim(), 18, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		return SUCCESS;
	}
	//校验查看专家评审
	public void validateViewReview(){
		publicValidate(AwardInfo.ERROR_VIEW_REVIEW_NULL);
	}
	
	//提交专家评审
	@Transactional
	public String submit(){
		awardReview = (AwardReview)dao.query(AwardReview.class, entityId);
		String awardApplicationId = awardReview.getApplication().getId();
		//判断是否在当前账号的管辖范围内
		if(!this.awardService.checkIfUnderControl(loginer, awardApplicationId.trim(), 18, true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		//获取奖励申请对象
		awardApplication = (AwardApplication)dao.query(AwardApplication.class, awardReview.getApplication().getId());
		if(awardApplication.getStatus() != 6 || awardApplication.getReviewStatus() != 0 || awardApplication.getFinalAuditStatus() == 3 || awardReview.getSubmitStatus() == 3){
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_CANNOT_DO);
			return INPUT;
		}
		awardReview.setSubmitStatus(3);
		dao.modify(awardReview);
		return SUCCESS;
	}
	//校验提交评审
	public void validateSubmit(){
		publicValidate(AwardInfo.ERROR_SUBMIT_REVIEW_NUll);
	}
	
	//准备小组评审
	public String toAddGroup(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityId);
		if(awardApplication.getStatus() != 6 || awardApplication.getReviewStatus() == 3 || awardApplication.getFinalAuditResult() == 3){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_CANNOT_DO);
			return INPUT;
		}
		if(awardApplication.getReviewTotalScore() == null && awardApplication.getReviewAverageScore() == null){
			this.awardService.setReviewScore(awardApplication);
		}
		totalScore = awardApplication.getReviewTotalScore();
		averageScore = awardApplication.getReviewAverageScore();
//		awardGrade = this.soDao.queryChildren("awardGrade");//获奖等级
		return SUCCESS;
	}
	
	//保存小组评审信息
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String addGroup(){
		//判断是否在当前账号的管辖范围内
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		//获取奖励申请对象
		awardApplication = (AwardApplication)dao.query(AwardApplication.class,entityId);
		if(awardApplication.getStatus() == 6 && awardApplication.getReviewStatus() != 3 && awardApplication.getFinalAuditStatus() != 3){
			if(auditOpinion != null){
				auditOpinion = ("A" + auditOpinion).trim().substring(1);
			}
			if(auditResult == 1 && auditOpinionFeedback != null){
	    		auditOpinionFeedback = ("A" + auditOpinionFeedback).trim().substring(1);
			}
			if(!"-1".equals(reviewWay)){
				awardApplication.setReviewWay(reviewWay);
			}
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.awardService.getAuditInfo(loginer, auditResult, auditStatus, auditOpinion);
			awardApplication.setFinalAuditOpinionFeedback(auditOpinionFeedback);
			auditMap.put("auditInfo", auditInfo);
			auditMap.put("isSubUni", 0);
			awardApplication.edit(auditMap);//保存操作结果
			if(auditResult == 2 && !"-1".equals(awardGradeid)){//评审同意获奖
				SystemOption grade = (SystemOption)dao.query(SystemOption.class, awardGradeid.trim());
				awardApplication.setReviewGrade(grade);
			}else{
				awardApplication.setReviewGrade(null);
			}
			dao.modify(awardApplication);
		}else{
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NO_RIGHRT);
		}
		return SUCCESS;
	}
	//校验添加小组评审
	@SuppressWarnings("unchecked")
	public void validateAddGroup(){
		String info = "";
		if (entityId == null || entityId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_VIEW_AWARDAPPLY_NULL);
			info += AwardInfo.ERROR_VIEW_AWARDAPPLY_NULL;
		}
		if(auditResult != 1 && auditResult != 2){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_RESULT_NULL);
			info += AwardInfo.ERROR_REVIEW_RESULT_NULL;
		}
		if(auditResult == 2 && ("-1".equals(awardGradeid) || awardGradeid.trim().length() == 0)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_AWARD_GRADE_NULL);
			info += AwardInfo.ERROR_AWARD_GRADE_NULL;
		}
		if(2 != auditStatus && 3 != auditStatus){
			this.addFieldError(GlobalInfo.ERROR_INFO,AwardInfo.ERROR_SUBMIT_STATUS_NULL);
			info += AwardInfo.ERROR_SUBMIT_STATUS_NULL;
		}
		if(auditOpinion.trim().length() > 200){
			this.addFieldError(GlobalInfo.ERROR_INFO,AwardInfo.ERROR_REVIEW_OPINION_OUT);
			info += AwardInfo.ERROR_REVIEW_OPINION_OUT;
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info.substring(0, info.length() - 1));
		}
	}
	
	//准备修改小组评审
	public String toModifyGroup(){
		//判断是否在当前账号的管辖范围内
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//获取奖励申请对象
		awardApplication = (AwardApplication)dao.query(AwardApplication.class,entityId.trim());
		if(awardApplication.getStatus() != 6 || awardApplication.getReviewStatus() == 3 || awardApplication.getFinalAuditResult() == 3){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
//		awardGrade = this.soDao.queryChildren("awardGrade");//获奖等级
		reviewWay = awardApplication.getReviewWay();
		totalScore = awardApplication.getReviewTotalScore();
		averageScore = awardApplication.getReviewAverageScore();
		auditResult = awardApplication.getReviewResult();
		auditOpinion = awardApplication.getReviewOpinion();
		auditOpinionFeedback = awardApplication.getFinalAuditOpinionFeedback();
		if(awardApplication.getReviewGrade() != null){
			awardGradeid = awardApplication.getReviewGrade().getId();
		}
		return SUCCESS;
	}
	//校验准备修改小组评审
	public void validateToModifyGroup(){
		publicValidate(AwardInfo.ERROR_MODIFY_AUDIT_NULL);
	}
	
	//修改小组评审
	@Transactional
	public String modifyGroup(){
		return this.addGroup();
	}
	//校验修改小组评审
	public void validateModifyGroup(){
		this.validateAddGroup();
	}
	
	//查看小组评审详情
	public String viewGroup(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		awardApplication = (AwardApplication)this.awardService.getAwardApplicationById(entityId.trim());
		return SUCCESS;
	}
	//校验查看小组评审详情
	public void validateViewGroup(){
		publicValidate(AwardInfo.ERROR_VIEW_REVIEW_NULL);
	}
	//查看小组评审的所有评审意见
	public String viewGroupOpinion(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(),18, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		this.groupOpinion = this.awardService.getGroupOpinionByAppId(entityId);
		return SUCCESS;
	}
	//校验查看小组评审的所有评审意见
	public void validateViewGroupOpinion(){
		publicValidate(AwardInfo.ERROR_VIEW_REVIEW_NULL);
	}
	
	//提交评审
	@Transactional
	public String submitGroup(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityId.trim());
		if(awardApplication.getStatus() == 6 && awardApplication.getReviewStatus() < 3 && awardApplication.getReviewStatus() > 0 && awardApplication.getFinalAuditStatus() != 3){// 评审提交
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.awardService.getAuditInfo(loginer, 0, 3, null);
			auditMap.put("auditInfo", auditInfo);
			auditMap.put("isSubUni", 0);
			awardApplication.submit(auditMap);//提交操作结果
			if((awardApplication.getCreateMode() != 1 && awardApplication.getCreateMode() != 2) && awardApplication.getStatus() == 6){
				awardApplication.setStatus(awardApplication.getStatus() + 1);//结项状态跳一级
			}
			dao.modify(awardApplication);
		}else{
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NO_RIGHRT);
		}
		return SUCCESS;
	}
	
	/**
	 * 准备选择评审专家
	 * @return
	 */
	public String toAddExpert(){
		
		return SUCCESS;
	}
	
	/**
	 * 选择评审专家
	 * @return
	 */
	public String addExpert(){
		AwardApplication application = (AwardApplication)dao.query(AwardApplication.class, entityId);
		for(int i = 0; i < reviews.size(); i++){
			AwardReview review = reviews.get(i);
			review.setApplication(application);
			review.setReviewType(1);
			review = (AwardReview) this.awardService.setAwardReviewInfoFromAwardReview(review);
			review.setReviewerSn(i+1);
			dao.add(review);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 准备录入奖励评审
	 * @author 王燕
	 */
	public String toAddResult() {
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		return SUCCESS;
	}
	
	/**
	 * 录入奖励评审
	 * @author 王燕
	 */
	public String addResult(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		//保存各个专家的评审信息
		AwardApplication application = (AwardApplication)dao.query(AwardApplication.class, entityId);
		int reviewType = this.awardService.getReviewTypeByAccountType(loginer.getCurrentType());
		if(reviewType == 2){//教育部录入
			for(int i = 0; i < reviews.size(); i++){
				AwardReview review = reviews.get(i);
				review.setApplication(application);
				review.setReviewType(reviewType);
				review = (AwardReview) this.awardService.setAwardReviewInfoFromAwardReview(review);
				review.setReviewerSn(i+1);
				review.setSubmitStatus(submitStatus);
				dao.add(review);
			}
		}
		//保存总评信息
		AwardReview review = this.awardService.getGroupDirectorReview(entityId);
		application.setReviewerName(review.getReviewerName());
		application.setReviewerAgency(review.getUniversity());
		application.setReviewOpinion((reviewOpinion != null) ? ("A" + reviewOpinion).trim().substring(1) : null);
		application.setReviewWay(reviewWay);
		application.setReviewResult(reviewResult);
		application.setReviewStatus(submitStatus);
		application.setReviewDate(new Date());
		//查询各专家分数，计算总分、均分及等级
		double[] reviewScore = this.awardService.getReviewScore(entityId);
		double reviewTotalScore = reviewScore[0];
		double reviewAvgScore = reviewScore[1];
		application.setReviewTotalScore(reviewTotalScore);
		application.setReviewAverageScore(reviewAvgScore);
		application.setReviewGrade(this.awardService.getReviewGrade(reviewAvgScore));
		//录入评审提交时判断是否非导入数据，状态是否已在评审状态
		if(submitStatus == 3 && (awardApplication.getCreateMode() != 1 && awardApplication.getCreateMode() != 2) && application.getStatus() == 6){
			application.setStatus(application.getStatus() + 1);//奖励申请状态跳一级
		}
		dao.modify(application);
//		if((reviewType == 3 || reviewType == 4) && submitStatus == 3){//提交则对评审人信息进行入库处理
//			this.awardService.doWithNewReview(entityId);
//   		}
		revResultFlag = 1;
		return SUCCESS;
	}
	
	
	/**
	 *准备修改录入评审
	 * @author 王燕
	 */
	@SuppressWarnings("unchecked")
	public String toModifyResult(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		awardReview = this.awardService.getAwardReview(entityId, baseService.getBelongIdByLoginer(loginer));
		reviews = this.awardService.getAllReviewByAppId(entityId);
		//教育部录入时对评审人人员id的处理
		if(loginer.getCurrentType().equals(AccountType.MINISTRY) && reviews.size() > 0){
			for (int i=0; i < reviews.size(); i++){
				AwardReview review = reviews.get(i);
				review = (AwardReview)this.awardService.setReviewPersonInfoFromReview(review);
				reviews.set(i, review);
			}
		}
		//下面的方法即为公共方法doWithToModifyResult()
		AwardApplication application = (AwardApplication)dao.query(AwardApplication.class, entityId);
		reviewWay = application.getReviewWay();
		reviewResult = application.getReviewResult();
		reviewOpinion = application.getReviewOpinion();
		request.setAttribute("totalScore", application.getReviewTotalScore());
		request.setAttribute("avgScore", application.getReviewAverageScore());
		request.setAttribute("groupGrade", (application.getReviewGrade()!= null) ? ((SystemOption) dao.query(SystemOption.class, application.getReviewGrade().getId())).getName() : "");
		return SUCCESS;
	}
	
	/**
	 * 修改录入评审
	 * @author 王燕
	 */
	@SuppressWarnings({ "unchecked" })
	@Transactional
	public String modifyResult(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		//删除原有评审信息
		List<AwardReview> oldReviews = this.awardService.getAllReviewByAppId(entityId);
		for(AwardReview awardReview : oldReviews){
			dao.delete(awardReview);
		}
		//保存各个专家的评审信息
		AwardApplication application = (AwardApplication) dao.query(AwardApplication.class, entityId);
		int reviewType = this.awardService.getReviewTypeByAccountType(loginer.getCurrentType());
		if(reviewType == 2){//教育部录入
			for(int i = 0; i < reviews.size(); i++){
				AwardReview review = reviews.get(i);
				review.setApplication(application);
				review.setReviewType(reviewType);
				review = (AwardReview) this.awardService.setAwardReviewInfoFromAwardReview(review);
				review.setReviewerSn(i+1);
				review.setSubmitStatus(submitStatus);
				dao.add(review);
			}
		}
		//保存总评信息
		AwardReview review = this.awardService.getGroupDirectorReview(entityId);
		application.setReviewerName(review.getReviewerName());
		application.setReviewerAgency(review.getUniversity());
		application.setReviewOpinion((reviewOpinion != null) ? ("A" + reviewOpinion).trim().substring(1) : null);
		application.setReviewWay(reviewWay);
		application.setReviewResult(reviewResult);
		application.setReviewStatus(submitStatus);
		application.setReviewDate(new Date());
		//查询各专家分数，计算总分、均分及等级
		double[] reviewScore = this.awardService.getReviewScore(entityId);
		double reviewTotalScore = reviewScore[0];
		double reviewAvgScore = reviewScore[1];
		application.setReviewTotalScore(reviewTotalScore);
		application.setReviewAverageScore(reviewAvgScore);
		application.setReviewGrade(this.awardService.getReviewGrade(reviewAvgScore));
		//录入评审提交时判断是否非导入数据，状态是否已在评审状态
		if(submitStatus == 3 && (awardApplication.getCreateMode() != 1 && awardApplication.getCreateMode() != 2) && application.getStatus() == 6){
			application.setStatus(application.getStatus() + 1);//结项状态跳一级
		}
		dao.modify(application);
//		if((reviewType == 3 || reviewType == 4) && submitStatus == 3){//提交则对评审人信息进行入库处理
//			this.awardService.doWithNewReview(entityId);
//   		}
		revResultFlag = 1;
		return SUCCESS;
	}
	
	/**
	 * 提交录入评审
	 * @author 王燕
	 */
	@Transactional
	public String submitResult(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		doWithSubmitResult();
//		int reviewType = this.awardService.getReviewTypeByAccountType(loginer.getCurrentType());
//		if((reviewType == 3 || reviewType == 4)){//提交则对评审人信息进行入库处理
//			this.awardService.doWithNewReview(entityId);
//   		}
		return SUCCESS;
	}
	
	/**
	 * 处理提交的评审结果
	 * @author 王燕
	 */
	public void doWithSubmitResult(){
		List reviews = this.awardService.getAllReviewByAppId(entityId);
		for(int i=0; i< reviews.size(); i++){
			AwardReview review = (AwardReview)reviews.get(i);
			if(review.getSubmitStatus()!=3){
				review.setSubmitStatus(3);
				review.setDate(new Date());
				dao.modify(review);
			}
		}
		//获取奖励申请对象
		AwardApplication application = (AwardApplication)dao.query(AwardApplication.class, entityId);
		application.setReviewStatus(3);
		//判断是否非导入数据，状态是否已在评审状态
		if((awardApplication.getCreateMode() != 1 && awardApplication.getCreateMode() != 2) && application.getStatus() == 6){
			application.setStatus(application.getStatus() + 1);//结项状态跳一级
		}
		dao.modify(application);
	}
	
	/**
	 * 准备录入评审校验
	 * @author 王燕
	 */
	public void validateToAddResult(){
		//校验是否已存在鉴定信息
		if(this.awardService.checkReview(entityId) != -1){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_AWARD_REVIEW_EXIST);
		}
	}
	
	/**
	 * 录入评审校验
	 * @author 王燕
	 */
	public void validateAddResult(){
		this.validateEditResult(1);
	}
	
	/**
	 * 修改录入评审结果校验
	 * @author 王燕
	 */
	public void validateModifyResult(){
		this.validateEditResult(2);
	}
	
	/**
	 * 提交录入评审校验
	 * @author 王燕
	 */
	public void validateSubmitResult(){
		this.validateEditResult(3);
	}
	
	/**
	 * 录入评审校验公用方法
	 * @param type 校验类型：1添加录入评审; 2修改录入评审; 3提交录入评审
	 * @author 王燕
	 */
	public void validateEditResult(int type){
		int reviewflag = this.awardService.checkReview(entityId);
		if(type == 1){//添加录入评审
			//校验是否已存在评审信息
			if(reviewflag != -1){
				this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_AWARD_REVIEW_EXIST);
			}
		}else if(type == 2 || type == 3){
			if(reviewflag == 23 || reviewflag == 33 || reviewflag == 43){
				this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_AWARD_REVIEW_SUBMIT);
			}
		}
		if(type == 1 || type == 2){//添加录入评审或修改录入评审
			if(reviews == null ||reviews.size() == 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			}else{
				for(int i=0 ;i <reviews.size();i++){
					this.validateReview(reviews.get(i));
				}
			}
			validateReviewGroup();
		}
	}
	
	/**
	 * 录入评审的校验评审信息的公共部分
	 * @author 余潜玉
	 */
	public void validateReview(AwardReview review){
		if(review.getReviewerType() != 1 && review.getReviewerType() != 2){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEWER_TYPE_NULL);
		}
		if(review.getInnovationScore() == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_INNOVATION_SCORE_NULL);
		}else if(review.getInnovationScore() > 20){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_INNOVATION_SCORE_OUT);
		}
		if(review.getMethodScore() == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_METHOD_SCORE_NULL);
		}else if(review.getMethodScore() > 20){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_METHOD_SCORE_OUT);
		}
		if(review.getInfluenceScore() == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_INFLUECCE_SCORE_NULL);
		}else if(review.getInfluenceScore() > 30){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_INFLUENCE_SCORE_OUT);
		}
		if(review.getMeaningScore() == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_MEANING_SCORE_NULL);
		}else if(review.getMeaningScore() > 30){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_MEANING_SCORE_OUT);
		}
		if(review.getOpinion()!= null && review.getOpinion().length()>2000){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_OPINION_OUT);
		}
		if(loginer.getCurrentType().equals(AccountType.MINISTRY)){//教育部录入
			if(review.getReviewer() == null){
				this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEWER_NULL);
			}
		}else {//其他
			if(review.getReviewerName() == null || review.getReviewerName().trim().isEmpty()){
				this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_AWARD_MEMBER_NAME_NULL);
			}
			if(review.getIdcardType() == null || "-1".equals(review.getIdcardType().trim())){
				this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_PERSON_IDCARD_TYPE_NULL);
			}
			if(review.getIdcardNumber() == null || review.getIdcardNumber().trim().isEmpty()){
				this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_PERSON_IDCARD_NUMBER_NULL);
			}else if(review.getIdcardNumber().trim().length() > 18){
				this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_PERSON_IDCARD_NUMBER_OUT);
			}
			if (review.getGender() == null || (!review.getGender().trim().equals("男") && !review.getGender().trim().equals("女"))){
				this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_PERSON_GENDER_NULL);
			}
			if(review.getDivisionType() == -1){
				this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_PERSON_DIVISION_TYPE_NULL);
			}
			if(review.getAgencyName() == null || review.getAgencyName().trim().isEmpty()){
				this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_AWARD_UNIT_NULL);
			}
			if(review.getDivisionName() == null || review.getDivisionName().trim().isEmpty()){
				this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_AWARD_DEPT_INST_NULL);
			}
		}
	}
	
	/**
	 * 录入时小组评审信息校验
	 */
	public void validateReviewGroup(){
		if(2 != submitStatus && 3 != submitStatus){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_SUBMITSTATUS_NULL);
		}
		if(reviewResult != 1 && reviewResult != 2){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEWER_NULL);
		}
		if(reviewOpinion.trim().length() > 2000){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_OPINION_OUT);
		}
	}
	/**
	 * 校验提交小组评审
	 */
	public void validateSubmitGroup(){
		publicValidate(AwardInfo.ERROR_SUBMIT_REVIEW_NUll);
	}
	public String getReviewWay() {
		return reviewWay;
	}
	public void setReviewWay(String reviewWay) {
		this.reviewWay = reviewWay;
	}

	public String getAwardGradeid() {
		return awardGradeid;
	}
	public void setAwardGradeid(String awardGradeid) {
		this.awardGradeid = awardGradeid;
	}
	@SuppressWarnings("rawtypes")
	public List getAwardGrade() {
		return awardGrade;
	}
	@SuppressWarnings("rawtypes")
	public void setAwardGrade(List awardGrade) {
		this.awardGrade = awardGrade;
	}
	public AwardReview getAwardReview() {
		return awardReview;
	}
	public void setAwardReview(AwardReview awardReview) {
		this.awardReview = awardReview;
	}
	public int getReviewStatus() {
		return reviewStatus;
	}
	public void setReviewStatus(int reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	public int getGroupReviewStatus() {
		return groupReviewStatus;
	}
	public void setGroupReviewStatus(int groupReviewStatus) {
		this.groupReviewStatus = groupReviewStatus;
	}
	@SuppressWarnings("rawtypes")
	public List getGroupOpinion() {
		return groupOpinion;
	}
	@SuppressWarnings("rawtypes")
	public void setGroupOpinion(List groupOpinion) {
		this.groupOpinion = groupOpinion;
	}

	public Double getMeaningScore() {
		return meaningScore;
	}

	public void setMeaningScore(Double meaningScore) {
		this.meaningScore = meaningScore;
	}

	public Double getInnovationScore() {
		return innovationScore;
	}

	public void setInnovationScore(Double innovationScore) {
		this.innovationScore = innovationScore;
	}

	public Double getInfluenceScore() {
		return influenceScore;
	}

	public void setInfluenceScore(Double influenceScore) {
		this.influenceScore = influenceScore;
	}

	public Double getMethodScore() {
		return methodScore;
	}

	public void setMethodScore(Double methodScore) {
		this.methodScore = methodScore;
	}

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}

	public Double getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(Double averageScore) {
		this.averageScore = averageScore;
	}

	public List<AwardReview> getReviews() {
		return reviews;
	}

	public void setReviews(List<AwardReview> reviews) {
		this.reviews = reviews;
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

	public int getSubmitStatus() {
		return submitStatus;
	}

	public void setSubmitStatus(int submitStatus) {
		this.submitStatus = submitStatus;
	}

	public int getReviewResult() {
		return reviewResult;
	}

	public void setReviewResult(int reviewResult) {
		this.reviewResult = reviewResult;
	}

	public int getRevResultFlag() {
		return revResultFlag;
	}

	public void setRevResultFlag(int revResultFlag) {
		this.revResultFlag = revResultFlag;
	}
}
