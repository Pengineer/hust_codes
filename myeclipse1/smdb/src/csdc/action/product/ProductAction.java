package csdc.action.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.SystemOption;
import csdc.dao.SystemOptionDao;
import csdc.service.IBaseService;
import csdc.service.IProductService;
import csdc.service.IUploadService;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProductInfo;

/**
 * 成果管理
 */
@SuppressWarnings("unchecked")
public class ProductAction extends ActionSupport implements ServletRequestAware, SessionAware {

	private static final long serialVersionUID = -5921065236716663313L;
	private int proType;//成果类别      1:论文   2:著作   3:研究咨询报告   4：电子出版物   5：专利   6：其他成果
	private IProductService productService;
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	private String projectId;//项目id
	private int projectType;//0:默认     1:一般项目    2:基地项目     3:后期资助项目     4:重大攻关项目    5:委托应急课题
	private int viewType;//0:默认；1.年检；2.中检；3.结项； 4.相关成果
	private String inspectionId;//年检、中检或结项id
	private int auditResult;//审核结果   1.不同意 2.同意	
	private List<Integer> productTypes;//成果形式 1:论文	2:著作 	3:研究咨询报告	4：电子出版物	5：专利	6：其他成果
	private String returnView;//成果查看参数
	private String nameSpace;//命名空间
	private String productType;//成果形式 1:论文	2:著作 	3:研究咨询报告	4：电子出版物	5：专利	6：其他成果
	private int productflag;//0:各类型成果列表进入查看	1:所有成果列表进入查看
	private Integer exflag;//审核入口	1：列表页面	0：查看详情页面
	private SystemOption systemOption;//系统选项表对象
	
	protected Map jsonMap = new HashMap();// json对象容器
	protected LoginInfo loginer;// 当前登录账号信息对象
	protected String entityId;// 单个实体ID
	protected List<String> entityIds;// 多个实体ID
	protected List<Object[]> pageList;// 初始查询列表数据
	protected HttpServletRequest request;// 请求的request对象
	protected Map session;//session对象
	@Autowired
	private IBaseService baseService;
	@Autowired
	private SystemOptionDao soDao;//系统选项查询
	
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	/**
	 * 转到个人成果列表
	 */
	public String toSearchDirectProduct(){
		request.setAttribute("url", "searchDirectProduct");
		return SUCCESS;
	}
	
	/**
	 * 显示个人成果列表
	 */
	public String searchDirectProduct(){
		//获取研究人员、高校、院系、研究基地的成果列表
		pageList = productService.getProductListByEntityId(1, productService.getBelongIdByLoginer(loginer));
		jsonMap.put("laData", pageList);
		return SUCCESS;
	}
	
	/**
	 * 弹出层添加成果
	 */
	public String toAdd(){
		//所属单位、部门信息
		if(loginer.getCurrentType().equals(AccountType.EXPERT) || loginer.getCurrentType().equals(AccountType.TEACHER) || loginer.getCurrentType().equals(AccountType.STUDENT)) {
			Map<String, String> authorTypeIdMap = this.productService.getAgencyInfosByAccount(loginer.getCurrentType(), productService.getBelongIdByLoginer(loginer));
			session.put("authorTypeIdMap", authorTypeIdMap);
		}
		
		//奖励选择团队添加成果
//		session.put("awardOrganizationId", (null != organization) ? organization.getId() : null);
//		flag = 0;
		String[]  groList = {"file_add_paper","file_add_book","file_add_con","file_add_elec","file_add_pat","file_add_other"};
		for (String groupId : groList) {
			uploadService.resetGroup(groupId);
		}
		return (productflag != 1) ? SUCCESS : "success1";
	}
	
	/**
	 * 准备审核
	 */
	public String toAudit(){
		return SUCCESS;
	}
	
	/**
	 * 审核项目成果（年检、中检、结项、相关）
	 */
	public String audit(){
		Map<String, Object> sc = ActionContext.getContext().getApplication();
		for(String entityId : entityIds){//遍历成果id
			productService.auditProjectProduct(loginer.getAccount(), entityId, projectId, viewType, inspectionId, auditResult, 
					Integer.parseInt((String)sc.get("productFirstAuditLevel")), Integer.parseInt((String)sc.get("productFinalAuditLevel")));
		}
		return SUCCESS;
	}
	
	/**
	 * 校验审核成果
	 */
	public void validateAudit(){
		if (entityIds == null || entityIds.isEmpty()) {//审核成果id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_AUDIT_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_AUDIT_NULL);
		} else if(null == String.valueOf(auditResult) || String.valueOf(auditResult).isEmpty()){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_AUDIT_RESULT_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_AUDIT_RESULT_NULL);
		}
	}
	
	/**
	 * 删除项目年检、中检、结项或相关成果（删除关联性）
	 */
	@Transactional
	public String deleteProjectInfo() {
		for(String entityId : entityIds) {
			productService.deleteProjectInfo(entityId, projectId, viewType, inspectionId);
		}
		return SUCCESS;
	}
	
	/**
	 * 校验删除项目成果
	 */
	public void validateDeleteProjectInfo(){
		for(String entityId : entityIds) {
			if (null == entityId) {//成果id不得为空
				this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_DELETE_NULL);
				jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_DELETE_NULL);
			} 
			if(!productService.checkIfUnderControl(loginer, entityId, 15, true)){//判断是否在管辖范围之内
				this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_NOT_IN_SCOPE);
				jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_NOT_IN_SCOPE);
			}
		}
	}
	
	/**
	 * 检查成果能否被修改
	 */
	public String checkModifyProduct() {
		if(!productService.canModifyProduct(entityId)) {
			jsonMap.put(GlobalInfo.ERROR_INFO, "当前成果不能被修改！");
		}
		return SUCCESS;
	}
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public int getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(int auditResult) {
		this.auditResult = auditResult;
	}
	public IProductService getProductService() {
		return productService;
	}
	public List<Integer> getProductTypes() {
		return productTypes;
	}
	public void setProductTypes(List<Integer> productTypes) {
		this.productTypes = productTypes;
	}
	public String getReturnView() {
		return returnView;
	}
	public void setReturnView(String returnView) {
		this.returnView = returnView;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public int getProductflag() {
		return productflag;
	}
	public void setProductflag(int productflag) {
		this.productflag = productflag;
	}
	public int getProType() {
		return proType;
	}
	public void setProType(int proType) {
		this.proType = proType;
	}
	public int getProjectType() {
		return projectType;
	}
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}
	public int getViewType() {
		return viewType;
	}
	public void setViewType(int viewType) {
		this.viewType = viewType;
	}
	public Integer getExflag() {
		return exflag;
	}
	public void setExflag(Integer exflag) {
		this.exflag = exflag;
	}
	public IUploadService getUploadService() {
		return uploadService;
	}

	public void setUploadService(IUploadService uploadService) {
		this.uploadService = uploadService;
	}

	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	public String getInspectionId() {
		return inspectionId;
	}
	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.loginer = (LoginInfo) request.getSession().getAttribute(GlobalInfo.LOGINER);
		this.request = request;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public List<String> getEntityIds() {
		return entityIds;
	}
	public void setEntityIds(List<String> entityIds) {
		this.entityIds = entityIds;
	}
	public List<Object[]> getPageList() {
		return pageList;
	}
	public void setPageList(List<Object[]> pageList) {
		this.pageList = pageList;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	@SuppressWarnings("rawtypes")
	public Map getJsonMap() {
		return jsonMap;
	}
	@SuppressWarnings("rawtypes")
	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}
//	public Organization getOrganization() {
//		return organization;
//	}
//	public void setOrganization(Organization organization) {
//		this.organization = organization;
//	}
	public void setSoDao(SystemOptionDao soDao) {
		this.soDao = soDao;
	}
	public SystemOptionDao getSoDao() {
		return soDao;
	}
	public void setSystemOption(SystemOption systemOption) {
		this.systemOption = systemOption;
	}
	public SystemOption getSystemOption() {
		return systemOption;
	}
	public IBaseService getBaseService() {
		return baseService;
	}
	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
}