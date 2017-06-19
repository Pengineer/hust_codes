package csdc.action.product;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import csdc.action.BaseAction;
import csdc.bean.AwardApplication;
import csdc.bean.Person;
import csdc.bean.Product;
import csdc.bean.ProjectProduct;
import csdc.service.IProductService;
import csdc.service.IUploadService;
import csdc.service.ext.IPersonExtService;
import csdc.tool.FileTool;
import csdc.tool.bean.AccountType;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProductInfo;

/**
 * 成果管理基类（成果子类Action的基类，实现各类成果管理公用方法，
 * 目前包括论文、著作、研究咨询报告、电子出版物、专利和其他成果）
 */
public abstract class ProductBaseAction extends BaseAction {

	private static final long serialVersionUID = 730604932276030784L;
	
	protected IProductService productService; //成果接口
	protected AwardApplication awardApplication;//奖励申报
	protected String personid;//作者id(奖励模块)
	protected String projectMemberId;//项目成员id
	protected String fileFileName;//成果名称
	protected String savePath;//成果保存路径
	protected int isProRel;//是否项目成果（1:项目成果；2:独立成果）
	protected int productflag;//0:各类型成果列表进入查看	1:所有成果列表进入查看
	protected int authorType;//1:教师;2:专家;3:学生
	protected Date pubDate1, pubDate2, audDate1, audDate2;//用于高级检索
	protected Date date1, date2;//发表时间 
	protected int auditResult;//审核结果（0：未审；1:未通过；2：通过）
	protected int auditStatus;//审核状态(3:提交)
	protected String auditorName;//审核人
	protected Date auditDate;//审核时间
	protected int viewflag;//查看审核详情类型      1:查看部门审核详情	 2:查看部级审核详情
	protected String projectId;//项目立项id
	protected Integer exflag;//项目,奖励添加成果标志(1.项目,奖励模块添加成果)  /审核入口	1：列表页面	0：查看详情页面
	protected String inputView;//添加成果失败返回视图
	protected Integer viewType;//1.年检； 2.中检；3.结项；4.相关列表；5.奖励添加成果
	protected String inspectionId;//年检或中检或结项id
	protected Map<String, String> unitDetails;//研究人员所在的所有机构
	protected String unitId;//研究人员所选单位	学校id、院系id和研究基地id以'; '连接
	protected String[] fileIds;	//标题提交上来的特征码list
	protected String uploadKey;	//文件上传授权码
	protected String authorTypeId;//作者所属类型id（专家|教师|学生）
	protected String authorId;//成果作者id(人员)
	
	protected Person author;//成果作者
	protected int organizationAuthorType;//1:教师;2:专家;3:学生
	protected String organizationAuthorTypeId;//作者所属类型id（专家|教师|学生）
	protected String organizationAuthorId;//成果作者id(人员)
	
	protected int applyType;//申报类型(0:个人; 1:团队)
	protected Product product;//成果对象
	protected String projectType;//项目类型
	protected ProjectProduct projectProduct;//成果项目立项对象
	protected int isFinalProduct;//是否结项最终成果(0:否, 1:是)
	protected String projectGrantedId;//项目立项id
	protected String translationWork;//译著
	protected String translation;//译 
	@Autowired
	protected IPersonExtService personExtService; //人员接口
	
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "p.id";//缓存id
	public abstract String[] column();
	public abstract Integer productType();
	public abstract String pageName(); 
	public abstract void initOptions();//加载系统选项
	
	public String dateFormat() {
		return ProductBaseAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return ProductBaseAction.PAGE_BUFFER_ID;
	}
	
	/**
	 * 转到查看页面
	 */
	public String toView() {
		//判断某个实体是否在当前账号的管辖范围之内
		if(!productService.checkIfUnderControl(loginer, entityId.trim(), 15, true)) {
			addActionError("您所选择的成果不在您的管辖范围内！");
			return ERROR;
		}
		return (null != exflag && exflag == 1) ? "popView" : SUCCESS;
	}
	
	/**
	 * 查看校验
	 */
	@SuppressWarnings("unchecked")
	public void validateView() {
		if (null == entityId || entityId.trim().isEmpty()) {//判断实体id是否为空
			this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_VIEW_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_VIEW_NULL);
		} else {
			product = (Product)dao.query(Product.class, entityId);
			if(null == product) {// 判断成果是否存在
				this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_PRODUCT_NULL);
				jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_PRODUCT_NULL);
			} else {//判断是否在查看范围内
				if(!this.productService.checkIfUnderControl(loginer, entityId.trim(), 15, true)){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_NOT_IN_SCOPE);
					jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_NOT_IN_SCOPE);
				}
			}
		}
	}
	
	/**
	 * 转到高级检索
	 */
	public String toAdvSearch() {
		initOptions();//加载初始化选项
		return SUCCESS;
	}
	
	/**
	 * 删除若干条成果
	 */
	@Transactional
	public String delete() {
		for(String entityId : entityIds) {//遍历成果id
			product = (Product)dao.query(Product.class, entityId);
			String filename = product.getFile();//附件名称
			String dfsId = product.getDfs();
			dao.delete(product);
			if(null != filename && !filename.isEmpty()){//删除成果相关文件
				FileTool.fileDelete(filename);
			}
			if(null != dfsId && !dfsId.isEmpty() && dmssService.getStatus()){
				dmssService.deleteFile(dfsId);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 校验删除成果
	 */
	@SuppressWarnings("unchecked")
	public void validateDelete() {
		if(null != entityIds && !entityIds.isEmpty()) {
			for(String entityId : entityIds) {//遍历成果id
				if(!productService.checkIfUnderControl(loginer, entityId, 15, true)) {//判断是否在管辖范围内
					this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_DELETE_NULL);
					jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_NOT_IN_SCOPE);
				}
				if(!productService.canDeleteProduct(entityId)) {//判断是否能删除
					this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_DELETE_FORBIDDEN);
					jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_DELETE_FORBIDDEN);
				}
			}
		} else {//成果id不能为空
			this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_DELETE_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_DELETE_NULL);
		}
	}
	
	/**
	 * 转到添加成果
	 */
	public String toAdd() {
		fetchAuthorInfo();//获取作者相关信息
		initOptions();//载入相关系统选项
		productflag = 0; 
		String groupId = "file_add";
		uploadService.resetGroup(groupId);//重置文件组，需要在初始化文件上传表单时执行
		return SUCCESS;
	}
	
	/**
	 * 成果附件下载
	 */
	public String download(){
		return SUCCESS;
	}
	
	/**
	 * 成果下载流
	 */
	public InputStream getTargetFile() throws Exception {
		if(null != fileFileName && !fileFileName.isEmpty()){
			String filename = new String(fileFileName.getBytes("iso8859-1"), "utf-8");
			fileFileName = new String(filename.substring(filename.lastIndexOf("/") + 1).getBytes(), "ISO-8859-1");
			return ServletActionContext.getServletContext().getResourceAsStream(filename);
		 }
		return null;
	}

	/**
	 * 文件是否存在校验
	 */
	@SuppressWarnings("unchecked")
	public String validateFile() throws Exception{
		//1.成果id不能为空
		if(null == entityId || entityId.isEmpty()) {
			jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_PRODUCT_NULL);
		} else {
			//2.成果须在管辖范围内
			if(!productService.checkIfUnderControl(loginer, entityId.trim(), 15, true)){
				jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_NOT_IN_SCOPE);
			}
			product = (Product)dao.query(Product.class, entityId);
			if(null == product) {//3.成果对象不能为空
				jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_PRODUCT_NULL);
			} else if(null == product.getFile() || !product.getFile().equals(fileFileName)) {//4.文件名须匹配
				jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_FILE_NOT_MATCH);
			} else {//5.文件须存在
				String filename = new String(fileFileName.getBytes("iso8859-1"), "utf-8");
				if(null == ServletActionContext.getServletContext().getResourceAsStream(filename)){
					jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_FILE_NOT_EXIST);
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 转到添加成果审核
	 */
	public String toAudit() {
		return SUCCESS;
	}

	/**
	 * 审核成果
	 */
	@Transactional
	public String audit() {
		for(String entityId : entityIds) {//遍历成果id
			productService.auditProduct(entityId, auditResult, loginer.getAccount());//审核成果
		}
		return SUCCESS;
	}

	/**
	 * 校验审核成果
	 */
	@SuppressWarnings("unchecked")
	public void validateAudit() {
		for(String entityId : entityIds) {
			//1.成果id不能为空
			if(null == entityId || entityId.isEmpty()) {
				this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_PRODUCT_NULL);
				jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_PRODUCT_NULL);
			} else {
				//2.成果须在管辖范围内
				if(!productService.checkIfUnderControl(loginer, entityId.trim(), 15, true)){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_NOT_IN_SCOPE);
					jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_NOT_IN_SCOPE);
				}
				product = (Product)dao.query(Product.class, entityId);
				if(null == product) {//3.成果对象不能为空
					this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_PRODUCT_NULL);
					jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_PRODUCT_NULL);
				}
				if(!(auditResult == 1 || auditResult == 2)) {//4.审核结果不能为空且合法
					this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_AUDIT_RESULT_NULL);
					jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_AUDIT_RESULT_NULL);
				}
			}
		}
	}
	
	/**
	 * 查看审核
	 */
	public String viewAudit(){
		product = (Product)dao.query(Product.class, entityId);//获取成果对象
		auditResult = product.getAuditResult();//审核结果
		auditStatus = product.getAuditStatus();//审核状态
		auditorName = product.getAuditorName();//审核人
		auditDate = product.getAuditDate();//审核时间
		return SUCCESS;
	}
	
	/**
	 * 校验查看审核
	 */
	@SuppressWarnings("unchecked")
	public void validateViewAudit(){
		//1.成果id不得为空
		if (null == entityId || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_VIEW_AUDIT_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_VIEW_AUDIT_NULL);
		} else {
			//2.成果须在管辖范围内
			if(!productService.checkIfUnderControl(loginer, entityId.trim(), 15, true)) {
				this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_NOT_IN_SCOPE);
				jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_NOT_IN_SCOPE);
			}
		}
	}
	
	/**
	 * 添加成果报奖申请
	 */
	@SuppressWarnings("unchecked")
	public String toAddApply(){
		if(!productService.checkIfUnderControl(loginer, entityId.trim(), 15, true)){
			addActionError("您所选择的成果不在您的管辖范围内！");
			return ERROR;}//是否在管辖范围之内
		Map<String, String> dtypemap = new HashMap<String, String>();
		product = (Product)dao.query(Product.class, entityId);
		//学科门类重组
		String[] dtypes = product.getDisciplineType().split(";");
		for(int i = 0; i < dtypes.length; i++) {
			dtypemap.put(dtypes[i], dtypes[i]);
		}
		session.put("dtypemap", dtypemap);
		personid = product.getAuthor().getId();
		//获得该研究人员的所属机构信息
		unitDetails = productService.getUnitDetailByAccountInfo(productService.getBelongIdByLoginer(loginer), loginer.getCurrentType());
		return SUCCESS;
	}
	
	/**
	 * 准备修改成果报奖申请，转到修改成果报奖申请页面
	 */
	@SuppressWarnings("unchecked")
	public String toModifyApply(){
		if(!productService.checkIfUnderControl(loginer, entityId.trim(), 15, true)){
			addActionError("您所选择的成果不在您的管辖范围内！");
			return ERROR;}
		Map<String, String> dtypemap = new HashMap<String, String>();
		product = (Product)dao.query(Product.class, entityId);
		String[] dtypes = product.getDisciplineType().split(";");
		for(int i = 0; i < dtypes.length; i++){
			dtypemap.put(dtypes[i], dtypes[i]);
		}
		session.put("dtypemap", dtypemap);
		//获取奖励申请信息
		awardApplication = (AwardApplication)dao.queryUnique(
			"from AwardApplication aa where aa.productId = ? and aa.applicantSubmitStatus != 3", product.getId());
		//获得该研究人员的所属机构信息
		unitDetails = productService.getUnitDetailByAccountInfo(productService.getBelongIdByLoginer(loginer), loginer.getCurrentType());
	  	unitId = "";
	  	unitId = unitId + ((null != awardApplication.getUniversity()) ? awardApplication.getUniversity().getId().trim() : "") + "; ";
		unitId = unitId + ((null != awardApplication.getDepartment()) ? awardApplication.getDepartment().getId().trim() : "") + "; ";
		unitId = unitId + ((null != awardApplication.getInstitute()) ? awardApplication.getInstitute().getId().trim() : "");
		session.put("awardApplicationId", awardApplication.getId());
		//获取该 成果的作者id
		personid = product.getAuthor().getId();
		return SUCCESS;
	}
	
	/**
	 * 项目添加已有成果
	 */
	@Transactional
	public String exAdd(){
		//添加已有成果到项目中检或结项
		productService.addExistedProductToProjectInspection(entityId, projectId, viewType, inspectionId);
		return SUCCESS;
	}
	
	/**
	 * 校验项目添加已有成果
	 */
	@SuppressWarnings("unchecked")
	public void validateExAdd(){
		//1.成果id不得为空
		if (null == entityId || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_VIEW_AUDIT_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_VIEW_AUDIT_NULL);
		} else {
			product = (Product)dao.query(Product.class, entityId);
			if(null == product) {//2.成果对象不能为空
				this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_PRODUCT_NULL);
				jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_PRODUCT_NULL);
			}
			if(viewType == 1) {
				if(!productService.canAddAnnProduct(projectId)){//3.能够添加年检成果
					this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_CANNOT_ADD_ANN_PRODUCT);
					jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_CANNOT_ADD_ANN_PRODUCT);
				}
			}else if(viewType == 2) {
				if(!productService.canAddMidProduct(projectId)){//4.能够添加中检成果
					this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_CANNOT_ADD_MID_PRODUCT);
					jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_CANNOT_ADD_MID_PRODUCT);
				}
			} else if(viewType == 3) {
				if(!productService.canAddEndProduct(projectId)){//5.能够添加结项成果
					this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_CANNOT_ADD_END_PRODUCT);
					jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_CANNOT_ADD_END_PRODUCT);
				}
			}
		}
	}
	
	/**
	 * 获取作者信息
	 */
	@SuppressWarnings("unchecked")
	public void fetchAuthorInfo() {
		//判断是否为研究人员
		if(loginer.getCurrentType().equals(AccountType.EXPERT) || loginer.getCurrentType().equals(AccountType.TEACHER) || loginer.getCurrentType().equals(AccountType.STUDENT)) {
			AccountType type = loginer.getCurrentType();
			//获取专家、教师、学生所在单位部门信息
			Map<String, String> authorTypeIdMap = productService.
				getAgencyInfosByAccount(type, productService.getBelongIdByLoginer(loginer));
			session.put("authorTypeIdMap", authorTypeIdMap);
			authorType = (type.equals(AccountType.EXPERT)) ? 2 : ((type.equals(AccountType.TEACHER)) ? 1 : 3);
			session.put("authorType", authorType);
		}
	}
	
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	public AwardApplication getAwardApplication() {
		return awardApplication;
	}
	public void setAwardApplication(AwardApplication awardApplication) {
		this.awardApplication = awardApplication;
	}
	public String getPersonid() {
		return personid;
	}
	public void setPersonid(String personid) {
		this.personid = personid;
	}
	public String getProjectMemberId() {
		return projectMemberId;
	}
	public void setProjectMemberId(String projectMemberId) {
		this.projectMemberId = projectMemberId;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public int getIsProRel() {
		return isProRel;
	}
	public void setIsProRel(int isProRel) {
		this.isProRel = isProRel;
	}
	public int getProductflag() {
		return productflag;
	}
	public void setProductflag(int productflag) {
		this.productflag = productflag;
	}
	public int getAuthorType() {
		return authorType;
	}
	public void setAuthorType(int authorType) {
		this.authorType = authorType;
	}
	public Date getPubDate1() {
		return pubDate1;
	}
	public void setPubDate1(Date pubDate1) {
		this.pubDate1 = pubDate1;
	}
	public Date getPubDate2() {
		return pubDate2;
	}
	public void setPubDate2(Date pubDate2) {
		this.pubDate2 = pubDate2;
	}
	public Date getAudDate1() {
		return audDate1;
	}
	public void setAudDate1(Date audDate1) {
		this.audDate1 = audDate1;
	}
	public Date getAudDate2() {
		return audDate2;
	}
	public void setAudDate2(Date audDate2) {
		this.audDate2 = audDate2;
	}
	public int getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(int auditResult) {
		this.auditResult = auditResult;
	}
	public int getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public int getViewflag() {
		return viewflag;
	}
	public void setViewflag(int viewflag) {
		this.viewflag = viewflag;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public Integer getExflag() {
		return exflag;
	}
	public void setExflag(Integer exflag) {
		this.exflag = exflag;
	}
	public String getInputView() {
		return inputView;
	}
	public void setInputView(String inputView) {
		this.inputView = inputView;
	}
	public Integer getViewType() {
		return viewType;
	}
	public void setViewType(Integer viewType) {
		this.viewType = viewType;
	}
	public Map<String, String> getUnitDetails() {
		return unitDetails;
	}
	public void setUnitDetails(Map<String, String> unitDetails) {
		this.unitDetails = unitDetails;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String[] getFileIds() {
		return fileIds;
	}
	public void setFileIds(String[] fileIds) {
		this.fileIds = fileIds;
	}
	public String getUploadKey() {
		return uploadKey;
	}
	public void setUploadKey(String uploadKey) {
		this.uploadKey = uploadKey;
	}
	public String getAuthorTypeId() {
		return authorTypeId;
	}
	public void setAuthorTypeId(String authorTypeId) {
		this.authorTypeId = authorTypeId;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public Date getDate1() {
		return date1;
	}
	public void setDate1(Date date1) {
		this.date1 = date1;
	}
	public Date getDate2() {
		return date2;
	}
	public void setDate2(Date date2) {
		this.date2 = date2;
	}
	public IProductService getProductService() {
		return productService;
	}
	public String getInspectionId() {
		return inspectionId;
	}
	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
	}
	public ProjectProduct getProjectProduct() {
		return projectProduct;
	}
	public void setProjectProduct(ProjectProduct projectProduct) {
		this.projectProduct = projectProduct;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getApplyType() {
		return applyType;
	}
	public void setApplyType(int applyType) {
		this.applyType = applyType;
	}
	public int getOrganizationAuthorType() {
		return organizationAuthorType;
	}
	public void setOrganizationAuthorType(int organizationAuthorType) {
		this.organizationAuthorType = organizationAuthorType;
	}
	public String getOrganizationAuthorTypeId() {
		return organizationAuthorTypeId;
	}
	public void setOrganizationAuthorTypeId(String organizationAuthorTypeId) {
		this.organizationAuthorTypeId = organizationAuthorTypeId;
	}
	public String getOrganizationAuthorId() {
		return organizationAuthorId;
	}
	public void setOrganizationAuthorId(String organizationAuthorId) {
		this.organizationAuthorId = organizationAuthorId;
	}
	public Person getAuthor() {
		return author;
	}
	public void setAuthor(Person author) {
		this.author = author;
	}
	public int getIsFinalProduct() {
		return isFinalProduct;
	}
	public void setIsFinalProduct(int isFinalProduct) {
		this.isFinalProduct = isFinalProduct;
	}
	public String getProjectGrantedId() {
		return projectGrantedId;
	}
	public void setProjectGrantedId(String projectGrantedId) {
		this.projectGrantedId = projectGrantedId;
	}
	public String getTranslationWork() {
		return translationWork;
	}
	public void setTranslationWork(String translationWork) {
		this.translationWork = translationWork;
	}
	public String getTranslation() {
		return translation;
	}
	public void setTranslation(String translation) {
		this.translation = translation;
	}
}