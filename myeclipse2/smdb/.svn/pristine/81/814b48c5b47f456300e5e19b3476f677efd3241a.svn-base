package csdc.action.award.moesocial;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.opensymphony.xwork2.ActionContext;
import csdc.action.award.AwardBaseAction;
import csdc.bean.Agency;
import csdc.bean.AwardGranted;
import csdc.bean.AwardApplication;
import csdc.bean.AwardReview;
//import csdc.bean.Organization;
import csdc.bean.Product;
import csdc.bean.SystemOption;
import csdc.dao.SystemOptionDao;
import csdc.service.IPersonService;
import csdc.service.IUploadService;
import csdc.service.ext.IPersonExtService;
import csdc.tool.info.AwardInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.bean.AccountType;

/**
 * 社科奖励数据管理
 * @author 余潜玉   王燕
 */
public class ApplicationAction extends AwardBaseAction {

	private static final long serialVersionUID = 7682626091322992674L;
	protected AwardApplication awardApplication;//申请奖励
	protected String ptypeid;//成果形式id
	protected String ptype;//成果形式
	protected String dtypeNames,productName,applicantName,universityName,provinceName;//用于高级检索
	protected int session0, session1, session2, session3;//申请届次
	protected Date startDate,endDate;//用于高级检索
	protected int status;//奖励审核提交状态	22:提交通过	21：提交不通过	12暂审通过	11暂审不通过
	protected int auditResult; //操作结果	2：通过	1：不通过
	protected int auditStatus;//操作状态	3:提交	2:暂存    1:退回 
	protected String auditOpinion; //奖励审核意见
	protected String auditOpinionFeedback; //审核意见（反馈给项目负责人）
	protected int audflag;//是否批量审批	1：批量	0:单个审批 	//	奖励委员会是否修改总评	1：修改	0：不修改
	protected int keyword1;//初级检索届次
	protected AwardGranted award;//获奖名单
	protected int viewflag;//查看审核信息 	4:部级	3:省级	2：学校	1：院系或研究机构
	protected String personid;//当前登陆者id
	protected Product product;


	//	protected Organization organization;//团队对象
	private static final String PAGE_BUFFER_ID = "aa.id";//缓存id
	@Autowired
	protected SystemOptionDao soDao;
	@Autowired
	protected IPersonExtService personExtService;//对外的人员接口
	@Autowired
	protected IPersonService personService;//人员接口
	
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	
	
	private static final String[] COLUMN = {
		"aa.productName",
		"aa.applicantName",
		"aa.agencyName",
		"aa.disciplineType",
		"pr.productType",
		"aa.session desc, aa.applicantSubmitDate desc",
		"aa.session desc, aa.deptInstAuditDate desc",
		"aa.session desc, aa.universityAuditDate desc",
		"aa.session desc, aa.provinceAuditDate desc",
		"aa.session desc, aa.reviewAuditDate desc",
		"aa.session desc, aa.finalAuditDate desc",
		"aa.session desc, ar.date desc",
		"aa.applicantSubmitStatus",
		"aa.deptInstAuditStatus",
		"aa.universityAuditStatus",
		"aa.provinceAuditStatus",
		"aa.ministryAuditStatus",
		"aa.reviewStatus",
		"aa.reviewAuditStatus",
		"aa.finalAuditStatus",
		"aa.applicantSubmitDate",
		"aa.deptInstAuditDate",
		"aa.universityAuditDate",
		"aa.provinceAuditDate",
		"aa.reviewAuditDate",
		"aa.finalAuditDate",
		"aa.year",
		"gr.code",
		"ar.submitStatus",
		"ar.date"};
	
	public String pageBufferId() {
		return ApplicationAction.PAGE_BUFFER_ID;
	}
	
	public String[] column(){
		return ApplicationAction.COLUMN;
	}
	/**
	 * 重写baseAction中pageListDealWith方法
	 * 将列表查询结果pageList进行处理，存入laData。默认的处理只有把Date类型的数据按照一定方式格式化。
	 * 本方法默认仅格式化时间，各模块可按照自身需求任意处理，甚至可以使laData和pageList列数不同。
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void pageListDealWith() {
		super.pageListDealWith();
		Map<String, String> bufferMap = new HashMap<String,String>();
		//获取成果类型描述
		for(int i = 0; i < laData.size(); i++) {
			Object[] items = (Object[])laData.get(i);
			String productType = (String)items[5];
			if(null != bufferMap.get(productType)) {//缓存存在
				items[5] = bufferMap.get((String)items[5]);
			} else {//不存在
				items[5] = awardService.fetchProductDescription(productType);
				bufferMap.put(productType, (String)items[5]);
			}
			laData.set(i, items);
		}
	};
	//公共校验
	@SuppressWarnings("unchecked")
	public void publicValidate(String info){
		if (entityId == null || entityId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, info);
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	//转到查看页面
	public String toView(){
		if(listflag == 1 && !this.awardService.checkIfUnderControl(loginer, entityId.trim(),18, true)){
			addActionError("您所选择的奖励查看不在您的管辖范围内！");
			return ERROR;
		}
		return SUCCESS;
	}
	//校验准备查看
	public void validateToView() {
		publicValidate(AwardInfo.ERROR_VIEW_AWARDAPPLY_NULL);
	}
	
	//查看奖励申请详情
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String view(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		Map session = ActionContext.getContext().getSession();
		AccountType accountType = loginer.getCurrentType();
		awardApplication = (AwardApplication) dao.query(AwardApplication.class, entityId.trim());
		award = this.awardService.getAward(entityId.trim());
		Product product = (Product) dao.query(Product.class, awardApplication.getProduct().getId().trim());
		String ptype = Product.findTypeName(product.getProductType());
		int universityType = 0;//学校类别 3：部署高校		4:地方高校
		String universityId = "";//学校id
		String departmentId = (awardApplication.getDepartment()!= null) ? departmentId = awardApplication.getDepartment().getId() : ""; //院系id
		String instituteId = (awardApplication.getInstitute()!= null) ? awardApplication.getInstitute().getId() : "";//研究机构id
		String awardGrade = (null != award) ? ((SystemOption) dao.query(SystemOption.class, award.getGrade().getId().trim())).getName() : "";//获奖等级名称
		String adviceGrade = "";//专家建议获奖等级名称
		String adviceGroupGrade = "";//小组总评建议获奖等级名称
		String applicantId = awardApplication.getApplicant() != null ? awardApplication.getApplicant().getId().trim() : "";//申请人id
		if(awardApplication.getUniversity()!=null){
			Agency university = (Agency) dao.query(Agency.class,awardApplication.getUniversity().getId().trim());
			universityType = university.getType();
			universityId = university.getId();
		}
		AwardReview awardReview = null;//奖励评审对象
		List awardReviews = new ArrayList();//所有奖励评审列表
		//当前是否是奖励评审人 0不是评审人；1是评审人但不是评审组长；2是评审人且是评审组长
		int isReviewer = (accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER)) ? this.awardService.isReviewer(entityId, awardService.getBelongIdByLoginer(loginer)) : 0;
		//判断是否所有评审专家都提交了个人评审 0所有专家已提交；-1还有专家未提交
		int	allReviewSubmit = this.awardService.isAllReviewSubmit(awardApplication.getId());
		//个人评审等级
		if(isReviewer > 0){
			awardReview = this.awardService.getAwardReview(entityId, awardService.getBelongIdByLoginer(loginer));
			if(awardReview != null && awardReview.getSubmitStatus() != 0 && awardReview.getGrade() != null){
				SystemOption grade = (SystemOption) dao.query(SystemOption.class, awardReview.getGrade().getId());
				adviceGrade = grade.getName();
			}
		}
		//小组评审等级
		if(allReviewSubmit == 0 && awardApplication.getStatus() >= 6){
			if(awardApplication.getReviewGrade() != null){
				SystemOption grade = (SystemOption) dao.query(SystemOption.class,awardApplication.getReviewGrade().getId().trim());
				adviceGroupGrade = grade.getName();
			}
		}
		//录入评审
		int reviewflag = this.awardService.checkReview(entityId);
		jsonMap.put("reviewflag", reviewflag);
		//所有评审信息
		awardReviews = this.awardService.getAllReviewList(entityId);
		awardApplication = awardService.hideAwardAppInfo(awardApplication, accountType, allReviewSubmit);
		jsonMap.put("awardApplication", awardApplication);
//		if(null != awardApplication.get && !awardApplication.getOrganization().getId().isEmpty()) {
//			organization = (Organization)dao.query(Organization.class, awardApplication.getOrganization().getId());
//		}
		
//		jsonMap.put("orgName", orgName);
		jsonMap.put("accountType", accountType);
		jsonMap.put("award", (award==null)? new AwardGranted() : award);
		jsonMap.put("ptypeName", ptype);
		jsonMap.put("universityType", universityType);
		jsonMap.put("universityId", universityId);
		jsonMap.put("departmentId", departmentId);
		jsonMap.put("instituteId", instituteId);
		jsonMap.put("applicantId", applicantId);
		jsonMap.put("awardGrade", awardGrade);
		jsonMap.put("adviceGrade", adviceGrade);//专家建议获奖等级名称
		jsonMap.put("adviceGroupGrade",adviceGroupGrade);//小组总评建议获奖等级名称
		jsonMap.put("isReviewer",isReviewer);//是否是奖励评审人
		jsonMap.put("allReviewSubmit", allReviewSubmit);//是否所有评审专家都提交了个人评审 0所有专家已提交；-1还有专家未提交
		jsonMap.put("awardReview",awardReview);//个人评审信息
		jsonMap.put("awardReviews",awardReviews);//所有评审信息
		jsonMap.put("reviewsSize", awardReviews.size());//获奖等级名称
		session.put("awardViewJson", jsonMap);
		return SUCCESS;
	}
	//校验查看方法
	public void validateView() {
		publicValidate(AwardInfo.ERROR_VIEW_AWARDAPPLY_NULL);
	}

	public AwardApplication getAwardApplication() {
		return awardApplication;
	}
	
	public void setAwardApplication(AwardApplication awardApplication) {
		this.awardApplication = awardApplication;
	}

	public int getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(int auditResult) {
		this.auditResult = auditResult;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	public AwardGranted getAward() {
		return award;
	}

	public void setAward(AwardGranted award) {
		this.award = award;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getDtypeNames() {
		return dtypeNames;
	}

	public void setDtypeNames(String dtypeNames) {
		this.dtypeNames = dtypeNames;
	}
	public int getAudflag() {
		return audflag;
	}

	public void setAudflag(int audflag) {
		this.audflag = audflag;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceName() {
		return provinceName;
	}
	
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getViewflag() {
		return viewflag;
	}
	public void setViewflag(int viewflag) {
		this.viewflag = viewflag;
	}
	public String getPtypeid() {
		return ptypeid;
	}
	public void setPtypeid(String ptypeid) {
		this.ptypeid = ptypeid;
	}
	public String getSelectedTab() {
		return selectedTab;
	}
	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}
	public int getSession0() {
		return session0;
	}
	public void setSession0(int session0) {
		this.session0 = session0;
	}
	public int getSession1() {
		return session1;
	}
	public void setSession1(int session1) {
		this.session1 = session1;
	}
	public int getSession2() {
		return session2;
	}
	public void setSession2(int session2) {
		this.session2 = session2;
	}
	
	public int getSession3() {
		return session3;
	}

	public void setSession3(int session3) {
		this.session3 = session3;
	}

	public int getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(int keyword1) {
		this.keyword1 = keyword1;
	}

	public String getAuditOpinionFeedback() {
		return auditOpinionFeedback;
	}

	public void setAuditOpinionFeedback(String auditOpinionFeedback) {
		this.auditOpinionFeedback = auditOpinionFeedback;
	}
	
//	public Organization getOrganization() {
//		return organization;
//	}
//
//	public void setOrganization(Organization organization) {
//		this.organization = organization;
//	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public void setSoDao(SystemOptionDao soDao) {
		this.soDao = soDao;
	}

	public SystemOptionDao getSoDao() {
		return soDao;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
}
