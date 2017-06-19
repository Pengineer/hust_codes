package csdc.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import csdc.bean.Account;
import csdc.bean.AwardGranted;
import csdc.bean.Product;
import csdc.bean.ProjectAnninspection;
import csdc.bean.ProjectAnninspectionProduct;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectEndinspectionProduct;
import csdc.bean.ProjectProduct;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectMidinspectionProduct;
import csdc.tool.bean.AccountType;
/**
 * 成果管理接口
 */
public interface IProductService extends IBaseService {
	
	//========================================================================================
	// 1.成果相关业务方法
	//========================================================================================
	/**
	 * 成果查看范围
	 * @param account 当前账号对象
	 * @return 查询语句、参数map
	 * type 1:系统管理员; 2:部级账号; 3:省级账号; 4:部属高校; 5:地方高校; 6:高校院系; 7:研究基地; 8:外部专家; 9:内部专家
	 * isPrincipal 1:主账号; 2:子账号
	 */
	@SuppressWarnings("rawtypes")
	public String getScopeHql(Account account, Map parMap);
	
	/**
	 * 根据文件、成果保存路径、成果形式生成保存成果文件名并保存文件
	 * @param uploadFile 上传的文件
	 * @param savePath 存储路径
	 * @param type 成果形式(1.论文；2.著作；3.研究咨询报告)
	 * @return 上传文件保存后的相对路径
	 */
	public String getFileName(File uploadFile, String savePath, int type);
	
	/**
	 * 根据账号、成果对象、作者填充成果作者、单位相关信息
	 * @param account : 账号对象; product : 成果对象; authorId : 作者id; authorType : {1: 教师, 2: 专家, 3: 学生 }
	 * @param authorTypeId : 专家、教师、学生id
	 */
	public Product fillAuthorAndAgencyInfos(AccountType type, Product product, String authorId, String authorTypeId, int authorType);
	
	/**
	 * 根据成果id获取成果作者id
	 * @param productId : 成果id
	 */
	public String getAuthorIdOfAuthorType(String productId);
	
	/**
	 * 根据成果id、审核结果、账号审核成果
	 * @param entityId ： 成果id; result : 审核结果{1 : 不同意  2 : 同意 }; account : 帐号对象
	 */
	public void auditProduct(String entityId, int result, Account account);
	
	//根据账号类型填充成果审核信息
	public Product fillAuditInfo(Account account, Product product, int result);
	
	//清除成果审核信息
	public void clearAuditInfo(Product product);
	
	/**
	 * 根据成果id判断成果能否被删除(被项目或奖励引用不能删除)
	 * @param productId : 成果id
	 */
	public boolean canDeleteProduct(String productId);
	
	/**
	 * 根据成果id判断成果能否被修改(下述情况不允许修改)
	 * @param productId : 成果id
	 * 1.关联到正在处理的中检; 2.关联到正在处理的结项; 3.关联到正在处理的报奖
	 */
	public boolean canModifyProduct(String productId);
	
	//========================================================================================
	// 2.成果-项目相关业务方法
	//========================================================================================
	/**
	 * 根据成果id获得成果相关项目信息
	 * @param productId : 成果id
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> getRelProjectInfos(String productId);
	
	/**
	 * 根据立项id, 返回项目下地所有成果类型（按照系统选项表代码标准排序）
	 * @param grantedId : 立项id
	 */
	public List<String> getProductTypesByProject(String grantedId);
	
	/**
	 * 根据成果id获取成果立项对象
	 * @param productId : 成果id
	 */
	public ProjectProduct getProjectProductByProductId(String productId);
	
	/**
	 * 根据成果id、结项id获取成果结项对象
	 * @param productId : 成果id, endInspectionId : 结项id
	 */
	public ProjectEndinspectionProduct getProjectEndinspectionProduct(String productId, String endInspectionId);
	
	/**
	 * 根据成果id、结项id重新设置结项最终成果（只有一个）
	 * @param productId : 成果id, endInspectionId : 结项id, isFinalProduct : 是否最终成果(0:否,  1:是)
	 */
	public void setEndInspectionFinalProduct(String productId, String endInspectionId, int isFinalProduct);
	
	/**
	 * 根据成果id判定成果是否为项目成果
	 * @param productId : 成果id
	 */
	public boolean isRelProduct(String productId);
	
	/**
	 * 根据立项id, 成果id判定成果是否为年检成果
	 * @param grantedId : 立项id; productId : 成果id
	 */
	public boolean isAnnProduct(String grantedId, String productId);
	
	/**
	 * 根据立项id, 成果id判定成果是否为中检成果
	 * @param grantedId : 立项id; productId : 成果id
	 */
	public boolean isMidProduct(String grantedId, String productId);
	
	/**
	 * 根据立项id, 成果id判定成果是否为结项成果
	 * @param grantedId : 立项id; productId : 成果id
	 */
	public boolean isEndProduct(String grantedId, String productId);
	
	/**
	 * 根据立项id获取项目相关成果列表
	 * @param grantedId : 项目立项id
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> getRelProducts(String grantedId);
	
	/**
	 * 根据立项id, 年检id获取项目年检成果列表
	 * @param id ：项目立项id; annIds : 年检id
	 * @return List<Map>对象, 包含项目年检成果信息
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getAnnProducts(String grantedId, List<String> annIds);
	/**
	 * 根据立项id, 中检id获取项目中检成果列表
	 * @param id ：项目立项id; midIds : 中检ids
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> getMidProducts(String grantedId, List<String> midIds);
	
	/**
	 * 根据立项id, 结项id获取项目结项成果列表
	 * @param id ：项目立项id; midIds : 结项ids
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> getEndProducts(String grantedId, List<String> endIds);
	
	/**
	 * 根据项目立项id判断是否能添加相关结果
	 * @param grantedId : 项目立项id
	 */
	public Boolean canAddRelProduct(String grantedId);
	
	/**
	 * 根据项目立项id判断是否能添加年度结果
	 * @param grantedId : 项目立项id
	 */
	public Boolean canAddAnnProduct(String grantedId);
	
	/**
	 * 根据项目立项id判断是否能添加中检结果
	 * @param grantedId : 项目立项id
	 */
	public Boolean canAddMidProduct(String grantedId);
	
	/**
	 * 根据项目立项id判断是否能添加结项结果
	 * @param grantedId : 项目立项id
	 */
	public Boolean canAddEndProduct(String grantedId);
	
	/**
	 * 根据项目立项id、成果类型、列表类型、检查id获取成果
	 * @param grantedId : 项目立项id; listType : {1 : 年检; 2 : 中检; 3：结项}; productType : 成果类型; inspectionId : 年检、中检、结项id
	 */
	public Map<String, String> getProduct(String grantedId, int projectType, int listType, String productType, String inspectionId);
	/**
	 * 根据账号类型、审核级别判定账号是否能够审核年检成果
	 * @param account : 账号对象; firstAuditLevel : 初审级别; finalAuditLevel : 终审级别
	 * @return true: 能审核; false: 不能审核
	 */
	public boolean canAuditAnninspectionProduct(List<ProjectAnninspection> annList, Account account, int firstAuditLevel, int finalAuditLevel);
	
	/**
	 * 根据账号类型、审核级别判定账号是否能够审核中检成果
	 * @param account : 账号对象; firstAuditLevel : 初审级别; finalAuditLevel : 终审级别
	 */
	public boolean canAuditMidInspectionProduct(List<ProjectMidinspection> midList, Account account, int firstAuditLevel, int finalAuditLevel);
	
	/**
	 * 根据账号类型、审核级别判定账号是否能够审核结项成果
	 * @param account : 账号对象; firstAuditLevel : 初审级别; finalAuditLevel : 终审级别
	 */
	public boolean canAuditEndInspectionProduct(List<ProjectEndinspection> endList, Account account, int firstAuditLevel, int finalAuditLevel);
	
	/**
	 * 根据账号级别、审核级别判定当前审核类型
	 * @param account : 账号对象; firstAuditLevel : 初审级别; finalAuditLevel : 终审级别
	 * @return 1 : 初审; 2 : 终审
	 */
	public int getAuditType(Account account, int firstAuditLevel, int finalAuditLevel);
	
	/**
	 * 审核项目成果
	 * @param account : 账号对象; productId : 成果id; grantedId : 立项id; viewType : 列表类型; inspectionId : 中检、结项id; auditResult : 审核结果
	 */
	public void auditProjectProduct(Account account, String productId, String grantedId, int viewType, String inspectionId, int auditResultint, int firstAuditLevel, int finalAuditLevel);
	
	//根据账号类型填充成果年度审核信息
	public ProjectAnninspectionProduct fillAnninspectionAuditInfo(Account account, ProjectAnninspectionProduct projectAnninspectionProduct, int result, int auditType);
	
	//根据账号类型填充成果中检审核信息
	public ProjectMidinspectionProduct fillMidinspectionAuditInfo(Account account, ProjectMidinspectionProduct projectMidinspectionProduct, int result, int auditType);
	
	//根据账号类型填充成果结项审核信息
	public ProjectEndinspectionProduct fillEndinspectionAuditInfo(Account account, ProjectEndinspectionProduct projectEndinspectionProduct, int result, int auditType);
	
	//清除成果年度审核信息
	public void clearAnninspectionAuditInfo(ProjectAnninspectionProduct projectAnninspectionProduct, int auditType);
	
	//清除成果中检审核信息
	public void clearMidinspectionAuditInfo(ProjectMidinspectionProduct projectMidinspectionProduct, int auditType);
	
	//清除成果结项审核信息
	public void clearEndinspectionAuditInfo(ProjectEndinspectionProduct projectEndinspectionProduct, int auditType);
	
	/**
	 * 删除成果对应项目信息
	 * @param productId : 成果id; grantedId : 立项id; viewType : 查看类型; inspectionId : 年检、中检、结项id
	 */
	public void deleteProjectInfo(String productId, String grantedId, int viewType, String inspectionId);
	
	/**
	 * 添加已有成果到项目中检或结项
	 * @param productId : 成果id; grantedId : 立项id; viewType : 查看类型; inspectionId : 年检、中检、结项id
	 */
	public void addExistedProductToProjectInspection(String productId, String grantedId, int viewType, String inspectionId);
	
	/**
	 * 添加新成果到项目相关、中检或结项
	 * @param productId : 成果id; grantedId : 立项id; viewType : 查看类型; inspectionId : 年检、中检、结项id
	 * @param projectProduct : 项目立项成果对象
	 */
	public void addNewProductToProjectInspection(ProjectProduct projectProduct, String productId, String grantedId, int viewType, String inspectionId, int isFinalProduct);
	
	/**
	 * 根据中检、结项id判断该此检查下是否成果是否全部审核
	 * @param inspectionId : 年检、中检、结项id
	 * @param type 1: 中检; 2：结项
	 * @return true : 已全部审核 false : 存在未审成果
	 */
	public boolean isProductAuditedOfInspection(int type, String inspectionId);
	
	//========================================================================================
	// 3.成果-奖励相关业务方法
	//========================================================================================
	/**
	 * 根据成果id得到成果的获奖情况
	 * @param productId : 成果id
	 */
	public AwardGranted getAward(String productId);
	
	/**
	 * 根据成果id得到成果的相关获奖信息
	 * @param productId : 成果id
	 */
	@SuppressWarnings({ "rawtypes" })
	public Map getRelAwardInfos(String productId);
	
	//========================================================================================
	// 4.成果-人员、机构相关业务方法
	//========================================================================================
	/**
	 * 得到研究人员、高校、院系、研究基地的成果列表
	 * @param type 类型( 1 : 研究人员 , 2 : 高校 , 3 : 院系, 4 : 研究基地) entityId : 人员、机构id
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getProductListByEntityId(int type, String entityId);
	
	/**
	 * 根据账号类型、账号所属id获取专家、教师、学生所在单位部门信息
	 * @param type : 账号类型(8.专家; 9.教师; 10.学生); personId : 账号所属人员id
	 */
	public Map<String, String> getAgencyInfosByAccount(AccountType type, String personId);
	
	
	/**
	 * 把成果上传到MDSS
	 * @param product
	 * @return 返回dfsId
	 * @throws Exception
	 */
	public String uploadToDmss(Product product) throws Exception;
	
	/**
	 * 把成果检入到MDSS
	 * @param product
	 * @return 返回dfsId
	 * @throws Exception
	 */
	public String checkInToDmss(Product product) throws Exception;
}
