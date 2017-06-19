package csdc.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import csdc.bean.Academic;
import csdc.bean.Account;
import csdc.bean.Department;
import csdc.bean.GeneralGranted;
import csdc.bean.Institute;
import csdc.bean.ProjectAnninspection;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectEndinspectionReview;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectFunding;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMember;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectVariation;
import csdc.bean.ProjectData;
import csdc.bean.SystemOption;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;

public interface IProjectService extends IBaseService  {
	
	//----------以下为各列表查询范围处理----------
	/**
	 * 处理项目申请查看范围
	 * @param account 当前账号对象
	 * @return 查询语句
	 */
	public String applicationInSearch(Account account);
	
	/**
	 * 处理项目立项后查看范围（立项、年检、中检、结项、变更）
	 * @param account 当前账号对象
	 * @return 查询语句
	 */
	public String grantedInSearch(Account account);
	
	//----------以下为以定制列表形式获取项目信息----------
	/**
	 * 根据项目立项id获取项目拨款
	 * @param graId 项目立项id
	 * @return 项目拨款
	 */
	@SuppressWarnings("rawtypes")
	public List getFundListByGrantedId(String graId);
	/**
	 * 根据项目id获取项目已拨款经费
	 * @param graId 项目id
	 * @return 项目拨款
	 */
	@SuppressWarnings("rawtypes")
	public Double getFundedFeeByGrantedId(String graId);
	//----------以下为设置项目经费相关信息----------
	/**
	 * 保存项目申请经费的相关字段的值
	 * @param projectFee 项目经费的对象
	 */
	public ProjectFee setProjectFee(ProjectFee projectFee);
	
	//----------以下为获得或设置项目相关信息----------
	/**
	 * 保存项目申请的相关字段的值
	 * @param application 项目申请的对象
	 * @return ProjectApplication
	 */
	public ProjectApplication setAppBaseInfoFromApp(ProjectApplication application);
	
	/**
	 * 保存项目申请的机构信息
	 * @param oldApplication 原始项目申请的对象
	 * @param application 项目申请的对象
	 * @param deptInstFlag 1：研究基地	2：院系
	 * @return ProjectApplication 原始项目申请的对象
	 */
	public ProjectApplication setAppAgencyInfoFromApp(ProjectApplication oldApplication, ProjectApplication application, int deptInstFlag);
	/**
	 * 保存项目申请人信息
	 * @param member 项目成员对象，member对象的member字段id为教师、专家或者学生id
	 * @param loginer 登陆人对象
	 * @return
	 */
	public ProjectMember setApplicantInfoFromMember(ProjectMember applicant, LoginInfo loginer);
	
	/**
	 * 保存项目成员信息
	 * @param member 项目成员对象，member对象的member字段id为教师、专家或者学生id
	 * @return ProjectMember
	 */
	public ProjectMember setMemberInfoFromMember(ProjectMember member);
	
	/**
	 * 保存项目成员信息(新建项目成员)
	 * @param member 项目成员对象，member对象的member字段id为教师、专家或者学生id
	 * @param entityId新建成员时选择的personId
	 * @return ProjectMember
	 */
	public ProjectMember setMemberInfoFromNewMember(ProjectMember member, String entityId);
	
	/**
	 * 根据变更成员的相关信息匹配库中人员
	 * 
	 */
	public List doWithPerson(Map personInfo);
	/**
	 * 保存项目成员信息
	 * @param member 项目成员对象，member对象的member字段id为personid
	 * @return ProjectMember
	 */
	public ProjectMember setMemberInfoFromPerson(ProjectMember member);
	                     
	/**
	 * 设置无人员id的成员信息
	 * @param member 项目成员对象
	 * @return ProjectMember
	 */
	public ProjectMember setMemberInfoForNoProsonIdMember(ProjectMember member);
	/**
	 * 设置项目成员对应的研究人员id(teacherId, ExpertId, studentId)的信息
	 * @param member 项目成员
	 * @return ProjectMember 项目成员
	 */
	public ProjectMember setMemberPersonInfoFromMember(ProjectMember member);
	/**
	 * 设置项目立项信息，用于走流程申报
	 * @param application 项目申请对象
	 * @param granted 项目立项对象
	 * @return ProjectGranted
	 */
	public ProjectGranted setGrantedInfoFromApp(ProjectApplication application, ProjectGranted granted);
	/**
	 * 设置项目立项信息,用于录入申报
	 * @param application 项目申请对象
	 * @param granted 项目立项对象
	 * @return ProjectGranted
	 */
	public ProjectGranted setGrantedInfoFromAppForImported(ProjectApplication application, ProjectGranted granted);
	/**
	 * 保存项目申请经费概算相关字段的值
	 * @param oldProjectFee 原始经费对象
	 * @param projectFee 页面经费对象
	 */
	public ProjectFee updateProjectFee(ProjectFee oldProjectFee, ProjectFee projectFee);
	/**
	 * 保存项目申请的相关字段的值
	 * @param oldApplication 原始项目申请的对象
	 * @param application 项目申请的对象
	 * @return ProjectApplication 原始项目申请的对象
	 */
	public ProjectApplication updateAppBaseInfoFromApp(ProjectApplication oldApplication, ProjectApplication application);
	/**
	 * 保存项目立项的相关字段的值
	 * @param oldGranted 原始项目立项的对象
	 * @param granted 项目立项的对象
	 * @return ProjectApplication 原始项目立项的对象
	 */
	public ProjectGranted updateGrantedInfoFromGranted(ProjectGranted oldGranted, ProjectGranted granted);
	/**
	 * 根据项目负责人成员对象获得项目负责人的人员主表id和name
	 * @param dirMember 项目负责人成员对象
	 * @return String[2] 0:id	1:name
	 */
	public String[] getAppDirectorIdAndName(ProjectMember dirMember);
	/**
	 * 根据项目申请对象获得项目所在部门的标志
	 * @param application 项目申请对象
	 * @return int 1：研究基地	2：院系
	 */
	public int getDeptInstFlagByApp(ProjectApplication application);
	//----------以下为对列表初级检索的处理----------
	/**
	 * 获得当前登陆者的初级检索项目申请的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目申请的条件语句
	 */
	public String getAppSimpleSearchHQL(int searchType);
	/**
	 * 获得当前登陆者的初级和高级检索项目申报申请的补充字段查询语句
	 * @param searchType 检索条件
	 * @return 初级检索项目申请的条件语句
	 */
	public String getAppSimpleSearchHQLWordAdd(AccountType accountType);
	/**
	 * 获得当前登陆者的初级检索项目申报申请的补充条件查询语句
	 * @param accountType 账号类型
	 * @return 初级检索项目申请的条件语句
	 */
	public String getAppSimpleSearchHQLAdd(AccountType accountType);
	/**
	 * 获得当前登陆者的初级检索项目申报评审的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目申报评审的条件语句
	 */
	public String getAppRevSimpleSearchHQL(int searchType);
	/**
	 * 获得当前登陆者的初级检索项目立项的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目立项的条件语句
	 */
	public String getGrantedSimpleSearchHQL(int searchType);
	/**
	 * 获得当前登陆者的初级检索项目拨款的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目立项的条件语句
	 */
	public String getFeeSimpleSearchHQL(int searchType);
	/**
	 * 获得当前登陆者的初级检索项目年检申请的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目申请的条件语句
	 */
	public String getAnnSimpleSearchHQL(int searchType);
	/**
	 * 获得当前登陆者的初级和高级检索项目年检申请的补充字段查询语句
	 * @param accountType 账号类型
	 * @return 初级检索项目申请的条件语句
	 */
	public String getAnnSearchHQLWordAdd(AccountType accountType);
	/**
	 * 获得当前登陆者的初级检索项目年检申请的补充条件查询语句
	 * @param accountType 账号类型
	 * @return 初级检索项目申请的条件语句
	 */
	public String getAnnSimpleSearchHQLAdd(AccountType accountType);
	/**
	 * 获得当前登陆者的初级检索项目中检申请的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目申请的条件语句
	 */
	public String getMidSimpleSearchHQL(int searchType);
	/**
	 * 获得当前登陆者的初级和高级检索项目中检申请的补充字段查询语句
	 * @param searchType 检索条件
	 * @return 初级检索项目申请的条件语句
	 */
	public String getMidSearchHQLWordAdd(AccountType accountType);
	/**
	 * 获得当前登陆者的初级检索项目中检申请的补充条件查询语句
	 * @param accountType 账号类型
	 * @return 初级检索项目申请的条件语句
	 */
	public String getMidSimpleSearchHQLAdd(AccountType accountType);
	/**
	 * 获得当前登陆者的初级检索项目结项申请的条件语句
	 * @param accountType 账号类型
	 * @return 初级检索项目申请的条件语句
	 */
	public String getEndSimpleSearchHQL(int searchType);
	/**
	 * 获得当前登陆者的初级检索项目结项申请的补充字段查询语句
	 * @param accountType 账号类型
	 * @return 初级检索项目申请的条件语句
	 */
	public String getEndSimpleSearchHQLWordAdd(AccountType accountType);
	/**
	 * 获得当前登陆者的高级检索项目结项申请的补充字段查询语句
	 * @param accountType 账号类型
	 * @return 初级检索项目申请的条件语句
	 */
	public String getEndAdvSearchHQLWordAdd(AccountType accountType);
	/**
	 * 获得当前登陆者的初级检索项目结项申请的补充条件查询语句
	 * @param accountType 账号类型
	 * @return 高级检索项目申请的条件语句
	 */
	public String getEndSimpleSearchHQLAdd(AccountType accountType);
	/**
	 * 获得当前登陆者的初级检索项目结项评审的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目申请的条件语句
	 */
	public String getEndRevSimpleSearchHQL(int searchType);
	//----------以下为获得项目对象或id----------
	/**
	 * 根据项目申请id获取项目申请
	 * @param appId 项目申请id
	 * @return 项目申请
	 */
	public ProjectApplication getApplicationFetchDetailByAppId(String appId);
	/**
	 * 根据申报id获取当前申报评审
	 * @param entityId 申报id
	 * @return 评审对象
	 */
	public ProjectApplicationReview getCurrentApplicationReviewByAppId(String entityId);
	/**
	 * 根据当前评审id获取当前申报
	 * @param appRevId 当前申报评审id
	 * @return 申报对象
	 */
	public ProjectApplication getCurrentApplicationByAppRevId(String appRevId);
	/**
	 * 根据项目申报id获取通过申报
	 * @param entityId 项目申报id
	 * @return 通过申报
	 */
	@SuppressWarnings("rawtypes")
	public List getPassApplicationByAppId(String entityId);
	/**
	 * 根据项目立项id获取项目申请id
	 * @param graId 项目立项id
	 * @return 项目申请id
	 */
	public String getApplicationIdByGrantedId(String graId);
	
	/**
	 * 由项目申请Id获取对应项目立项对象
	 * @param appId项目申请Id
	 * @return 项目立项对象
	 */
	public ProjectGranted getGrantedByAppId(String appId);
	/**
	 * 根据项目立项id获取项目
	 * @param graId 项目立项id
	 * @return 项目
	 */
	public ProjectGranted getGrantedFetchDetailByGrantedId(String graId);
	/**
	 * 根据项目申请id获取项目立项id
	 * @param appId 项目申请id
	 * @return 项目立项id
	 */
	public String getGrantedIdByAppId(String appId);
	/**
	 * 根据项目结项id获取项目立项id
	 * @param endId 项目结项id
	 * @return 项目立项id
	 */
	public String getGrantedIdByEndId(String endId);
	/**
	 * 根据项目年检id获取项目id
	 * @param annId 项目年检id
	 * @return 项目id
	 */
	public String getGrantedIdByAnnId(String annId);
	/**
	 * 根据项目中检id获取项目立项id
	 * @param midId 项目中检id
	 * @return 项目立项id
	 */
	public String getGrantedIdByMidId(String midId);
	
	/**
	 * 根据项目变更id获取项目id
	 * @param varId 项目变更id
	 * @return 项目id
	 */
	public String getGrantedIdByVarId(String varId);
	//----------以下为获得结项对象或id，包括所有结项、所有可见范围内结项、待审结项、通过结项、当前结项、当前待审录入/导入结项----------
	/**
	 * 根据项目立项id获取所有可见范围内结项
	 * @param graId 项目立项id
	 * @param accountType 帐号类别
	 * @return 所有可见范围内结项
	 */
	public List<ProjectEndinspection> getAllEndinspectionByGrantedIdInScope(String graId, AccountType accountType);
	/**
	 * 根据立项id及评审人id获得都对应的所有结项
	 * @param graId 项目立项id
	 * @param reviewerId 评审人id
	 * @return 所有可见范围内结项
	 */
	public List<ProjectEndinspection> getAllEndinspectionByGrantedIdAndReviewerId(String graId, String reviewerId);
	/**
	 * 根据项目立项id获取所有结项id
	 * @param graId 项目立项id
	 * @return 所有结项id
	 */
	public List<String> getAllEndinspectionIdByGrantedId(String graId);
	
	/**
	 * 根据项目立项id获取未审结项
	 * @param graId 项目立项id
	 * @return 未审结项
	 */
	@SuppressWarnings("rawtypes")
	public List getPendingEndinspectionByGrantedId(String graId);
	
	/**
	 * 根据项目立项id获取通过结项
	 * @param graId 项目立项id
	 * @return 通过结项
	 */
	@SuppressWarnings("rawtypes")
	public List getPassEndinspectionByGrantedId(String graId);
	/**
	 * 根据项目id获取当前录入的未审结项
	 * @param graId 项目立项id
	 * @return 当前录入的未审结项
	 */
	public ProjectEndinspection getCurrentPendingImpEndinspectionByGrantedId(String graId);
	/**
	 * 根据项目立项id获取当前结项
	 * @param graId 项目立项id
	 * @return 当前结项
	 */
	public ProjectEndinspection getCurrentEndinspectionByGrantedId(String graId);
	/**
	 * 根据结项id获取当前评审
	 * @param endId 结项id
	 * @return 评审对象
	 */
	public ProjectEndinspectionReview getCurrentEndinspectionReviewByEndId(String endId);
	/**
	 * 根据当前评审id获取当前结项
	 * @param endRevId 当前结项评审id
	 * @return 结项对象
	 */
	public ProjectEndinspection getCurrentEndinspectionByEndRevId(String endRevId);
	//----------以下为获取结项相关信息----------
	/**
	 * 通过结项申请id获得结项研究数据信息
	 * @param endId 结项申请id
	 * @return 研究数据对象
	 */
	public ProjectData getProjectDataByEndId(String endId);
	//----------以下为获得年度进展年度对象或id，包括所有年检、所有可见范围内年检、待审年检、通过年检、当前年检、当前待审录入/导入年检----------
	/**
	 * 根据项目立项id获取所有可见范围内年检
	 * @param graId 项目立项id
	 * @param accountType 帐号类别
	 * @return 所有可见范围内年检
	 */
	public List<ProjectAnninspection> getAllAnninspectionByGrantedIdInScope(String graId, AccountType accountType);
	/**
	 * 根据项目立项id获取未审年检
	 * @param graId 项目立项id
	 * @return 未审年检
	 */
	@SuppressWarnings("rawtypes")
	public List getPendingAnninspectionByGrantedId(String graId);
	/**
	 * 判断当前年检是否审核
	 * @param graId 项目立项id
	 * @return true:审核，false：未审核
	 */
	public Boolean getPassAnninspectionByGrantedId(String graId);
	/**
	 * 根据项目立项id获取当前录入的未审年检
	 * @param graId 项目立项id
	 * @return 当前录入的未审年检
	 */
	public ProjectAnninspection getCurrentPendingImpAnninspectionByGrantedId(String graId);
	/**
	 * 根据项目立项id获取立项经费拨款信息
	 * @param graId 项目立项id
	 * @return 立项经费拨款
	 */
	public ProjectFunding getProjectFundingByGraId(String graId);
	/**
	 * 根据项目立项id获取当前年检
	 * @param graId 项目立项id
	 * @return 当前的年检
	 */
	public ProjectAnninspection getCurrentAnninspectionByGrantedId(String graId);
	/**
	 * 根据项目年检id获取年检经费
	 * @param annIds 项目年检id
	 * @return 年检经费
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProjectFeeAnnByAnnId(String grantedId, List<String> annIds);
	/**
	 * 根据项目中检id获取中检经费
	 * @param midIds 项目中检id
	 * @return 中检经费
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProjectFeeMidByMidId(String grantedId, List<String> midIds);
	/**
	 * 根据项目结项id获取结项经费
	 * @param annIds 项目结项id
	 * @return 结项经费
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProjectFeeEndByEndId(String grantedId, List<String> endIds);
	//----------以下为获得中检对象或id，包括所有中检、所有可见范围内中检、待审中检、通过中检、当前中检、当前待审录入/导入中检----------
	/**
	 * 根据项目立项id获取所有可见范围内中检
	 * @param graId 项目立项id
	 * @param accountType 帐号类别
	 * @return 所有可见范围内中检
	 */
	public List<ProjectMidinspection> getAllMidinspectionByGrantedIdInScope(String graId, AccountType accountType);
	/**
	 * 根据项目立项id获取所有中检id
	 * @param graId 项目立项id
	 * @return 所有中检id
	 */
	public List<String> getAllMidinspectionIdByGrantedId(String graId);
	
	/**
	 * 根据项目立项id获取未审中检
	 * @param graId 项目立项id
	 * @return 未审中检
	 */
	@SuppressWarnings("rawtypes")
	public List getPendingMidinspectionByGrantedId(String graId);
	
	/**
	 * 根据项目立项id获取通过中检
	 * @param graId 项目立项id
	 * @return 通过中检
	 */
	@SuppressWarnings("rawtypes")
	public List getPassMidinspectionByGrantedId(String graId);
	
	/**
	 * 根据项目立项id获取当前录入的未审中检
	 * @param graId 项目立项id
	 * @return 当前录入的未审中检
	 */
	public ProjectMidinspection getCurrentPendingImpMidinspectionByGrantedId(String graId);
	
	/**
	 * 根据项目立项id获取当前中检
	 * @param graId 项目立项id
	 * @return 当前的中检
	 */
	public ProjectMidinspection getCurrentMidinspectionByGrantedId(String graId);
	//----------以下为获得变更对象或id，包括所有变更、所有可见范围内变更、待审变更----------
	/**
	 * 根据项目立项id、当前登陆者帐号类别来获取登陆者管辖范围内的所有变更列表
	 * @param graId 项目立项id 
	 * @param accountType 帐号类别
	 * @return 登陆者管辖范围内的所有变更列表
	 */
	public List<ProjectVariation> getAllVariationByGrantedIdInScope(String graId, AccountType accountType);
	/**
	 * 根据项目立项id获取所有变更id
	 * @param graId 项目立项id
	 * @return 所有变更id
	 */
	public List<String> getAllVariationIdByGrantedId(String graId);
	
	/**
	 * 根据项目立项id获取未审变更
	 * @param graId 项目立项id
	 * @return 未审变更
	 */
	@SuppressWarnings("rawtypes")
	public List getPendingVariationByGrantedId(String graId);
	
	/**
	 * 根据项目立项id获取已审并同意的变更
	 * @param graId 项目立项id
	 * @return 未审变更
	 */
	@SuppressWarnings("rawtypes")
	public List getAuditedVariationByGrantedId(String graId);
	
	/**
	 * 根据项目立项id获取当前变更
	 * @param graId 项目立项id
	 * @return 当前的变更
	 */
	public ProjectVariation getCurrentVariationByGrantedId(String graId);
	//----------以下为变更列表查询处理----------
	/**
	 * 获得当前登陆者的检索变更的查询语句
	 * @param HQL1  查询语句选择部分
	 * @param HQL2  查询语句条件部分
	 * @param accountType 帐号类别
	 * @return 检索变更的查询语句
	 */
	public StringBuffer getVarHql(String HQL1, String HQL2, AccountType accountType);
	
	/**
	 * 获得当前登陆者的初级检索变更的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索变更的条件语句
	 */
	public String getVarSimpleSearchHQL(int searchType);
	//----------以下为获取变更相关信息----------
	/**
	 * 获得变更次数
	 * @param graId 项目立项id
	 * @return 系统已有的变更次数
	 */
	public int getVarTimes(String graId);
	/***************************以下方法非项目模块负责人，请勿修改******************************/
	/**
	 * 根据变更对象获得可以同意的变更事项(非项目模块负责人，请勿修改)
	 * @param variation 项目变更对象
	 * @return 可以同意的变更事项
	 */
	public String getVarCanApproveItem(ProjectVariation variation);
	/**
	 * 通过审核结果详情编码获得可以同意的变更字串(非项目模块负责人，请勿修改。要改则只允许扩展代码、不允许修改已有代码)
	 * @param auditResultDetail	审核结果详情编码	长度为9，九位字符依次是：变更项目成员（含负责人）(0)、变更机构(1)、变更成果形式(2)、变更项目名称(3)、研究内容有重大调整(4)、延期(5)、、自行终止项目(6)、申请撤项(7)、其他(8)这十类变更结果的标志位。	'1'表示同意 '0'表示不同意
	 * @return	可以同意的变更字串
	 */
	public String getVarCanApproveItem(String auditResultDetail);
	
	/**
	 * 通过同意变更事项的字串拼接成同意变更事项的编码(非项目模块负责人，请勿修改。要改则只允许扩展代码、不允许修改已有代码)
	 * @param varSelectApproveIssue	同意变更事项 多个已,隔开
	 * @return 同意变更事项的编码
	 */
	public String getVarApproveItem(String varSelectApproveIssue);
	
	/**
	 * 用于变更导出
	 * 根据同意变更事项详情的编码获得同意变更事项的名称(非项目模块负责人，请勿修改。要改则只允许扩展代码、不允许修改已有代码)
	 * @param varApproveDetail 同意变更事项详情编码
	 * @return	同意变更事项的名称
	 */
	public String getVarApproveNameForExport(String varSelectApproveIssue);
	
	/**
	 * 根据同意变更事项详情的编码获得同意变更事项的名称(非项目模块负责人，请勿修改。要改则只允许扩展代码、不允许修改已有代码)
	 * @param varApproveDetail 同意变更事项详情编码
	 * @return	同意变更事项的名称
	 */
	public String getVarApproveName(String varApproveDetail);
	
	/**
	 * 获得可以同意的变更事项List
	 * @param varItemString 变更事项id，多个以,隔开
	 * @return list[code][name]
	 */
	@SuppressWarnings("rawtypes")
	public List getVarItemList(String varItemString);
	/*******************************************end*******************************************/
	/**
	 * 获得变更列表中变更对象的所有相关id,name
	 * @param varList 变更列表
	 * @return 变更列表的所有相关id,name列表 ，每条记录为Object[10]，其中0、1:变更前、后责任人id  	2、3、4、5、6、7:变更前、后学校、院系、基地id 		8,9为变更前后负责人的姓名
	 */
	@SuppressWarnings("rawtypes")
	public List getAllVariationRelIdNames(List<ProjectVariation> varList);
	
	//----------以下为设置变更相关信息----------
	/**
	 * 保存变更机构时的相关信息
	 * @param variation 项目变更对象
	 * @param graId 立项id
	 * @param deptInstFlag 院系或研究机构标志位	1：研究机构	2:院系
	 * @param deptInstId 院系或研究机构id
	 * @return 项目变更对象
	 */
	public ProjectVariation setVariationAgencyInfo(ProjectVariation variation, String graId, int deptInstFlag, String deptInstId);
	/**
	 * 变更项目信息
	 * @param variation 变更对象
	 */
	public void variationProject(ProjectVariation variation);
	
	/**
	 * 根据项目申请id及项目成员组编号对项目成员对应的人员、机构进行入库处理
	 * @param appId 项目申请id
	 * @param groupNumber 项目成员组编号
	 */
	public void doWithNewMember(String appId, Integer groupNumber);
	//----------以下为获取项目成员或负责人信息----------
	/**
	 * 根据项目申请id及项目成员组编号获取所有负责人id的list
	 * @param appId 项目申请id
	 * @param groupNumber 项目成员组编号
	 * @return 所有负责人id的list
	 */
	@SuppressWarnings("rawtypes")
	public List getDireIdByAppId(String appId, Integer groupNumber);
	/**
	 * 根据项目申请id获取项目成员
	 * @param appId 项目申请id
	 * @param groupNumber 项目成员组编号
	 * @return 项目成员
	 */
	@SuppressWarnings("rawtypes")
	public List getMemberListByAppId(String appId, Integer groupNumber);
	/**
	 * 获得项目成员
	 * @param appId项目申请id
	 * @param groupNumber 项目成员组编号
	 * @return 项目成员
	 */
	@SuppressWarnings("rawtypes")
	public List getMemberFetchUnit(String appId, Integer groupNumber);
	/**
	 * 根据项目申请id及项目成员组编号获得项目成员
	 * @param appId 项目申请id
	 * @param groupNumber 项目成员组编号
	 * @return 项目成员
	 */
	public List<ProjectMember> getMember(String appId, Integer groupNumber);
	/**
	 * 根据项目申请id和人员主表id获得项目成员对象
	 * @param appId 项目申请id
	 * @param personId 人员主表id
	 * @return 项目成员对象
	 */
	public ProjectMember getMember(String appId, String personId);
	/**
	 * 根据项目申请id获得项目的最大成员组编号
	 * @param appId 项目申请id
	 * @return 项目的最大成员组编号
	 */
	public int getMaxGroupNumber(String appId);
	/**
	 * 根据项目申请id及项目成员组编号获得项目的非负责人项目成员列表
	 * @param appId 项目申请id
	 * @param groupNumber 项目成员组编号
	 * @return 项目的非负责人项目成员列表
	 */
	public List<ProjectMember>  getNoDirMembers(String appId, Integer groupNumber);
	/**
	 * 根据项目申请id及项目成员组编号消除项目成员中的重复成员
	 * @param appId 项目申请id
	 * @param groupNumber 项目成员组编号
	 */
	public void deleteMulMember(String appId, Integer groupNumber);
	/**
	 * 对项目成员重新编码
	 * @param appId	项目申请id
	 * @param groupNumber 项目成员组编号
	 */
	public void refreshMemberSn(String appId, Integer groupNumber);
	/**
	 * 根据项目申请id获得项目的所有负责人列表
	 * @param appId 项目申请id
	 * @param groupNumber 项目成员组编号
	 * @return 项目负责人列表
	 */
	@SuppressWarnings("rawtypes")
	public List getDirectorList(String appId, Integer groupNumber);
	
	//----------以下为获取申报评审信息----------
	/**
	 * 根据项目申报id及人员id获取判断是否项目评审人及评审组长
	 * @param entityId 项目申报id
	 * @param personId 人员id
	 * @return 0不是评审人；1是评审人但不是评审组长；2是评审人且是评审组长
	 */
	public Integer isAppReviewer(String entityId, String personId);
	/**
	 * 保存申报评审信息
	 * @param applicationReview 申报评审对象
	 * @return 更新后的申报评审对象
	 */
	public ProjectApplicationReview setAppReviewInfoFromAppReview(ProjectApplicationReview applicationReview);
	/**
	 * 设置项目申报评审人员对应的研究人员id(teacherId, ExpertId, studentId)的信息
	 * @param review 项目申报评审对象
	 * @return ProjectApplicationReview 项目申报评审对象
	 */
	public ProjectApplicationReview setReviewPersonInfoFromReview(ProjectApplicationReview review);
	/**
	 * 获取个人申报评审对象
	 * @param entityId 申报id
	 * @param personId 人员id
	 * @return 申报评审对象
	 */
	public ProjectApplicationReview getPersonalAppReview(String entityId, String personId);
	/**
	 * 获取当前账号能看到的所有人申报评审
	 * @param entityId 申报id
	 * @param accountType 登陆者身份	1：系统管理员    2：部级	3：省级	4、5：校级	6：院系	7：研究机构	8：外部专家	9：教师	10：学生
	 * @return 当前账号能看到的所有人申报评审
	 */
	@SuppressWarnings("rawtypes")
	public List getAllAppReviewList(String entityId ,AccountType accountType);
	/**
	 * 判断项目申报评审是否所有专家全部提交
	 * @param entityId 申报id
	 * @return 0所有专家已提交；-1还有专家未提交
	 */
	public int isAllAppReviewSubmit(String entityId);
	/**
	 * 获取申报评审总分与均分
	 * @param entityId 申报id
	 * @return 申报评审总分与均分
	 */
	public double[] getAppReviewScore(String entityId);
	/**
	 * 判断项目申报是否有评审记录
	 * @param entityId 申报id
	 * @return -1无评审记录，1有评审记录且是专家评审，22教育部录入暂存，23教育部录入提交，32省厅录入暂存，33省厅录入提交，42高校录入暂存，43高校录入提交
	 */
	public int checkReviewFromAppReview(String entityId);
	/**
	 * 查询申报评审专家姓名
	 * @param appRevId 评审id
	 * @return 专家姓名
	 */
	public String getReviewerNameFromAppReview(String appRevId);
	/**
	 * 通过申报id获得申报评审组长的评审信息
	 * @param entityId 申报id
	 * @return 评审组长的评审信息
	 */
	public ProjectApplicationReview getGroupDirectorReviewFromAppReview(String entityId);
	/**
	 * 根据申报id获得申报的所有评审列表
	 * @param entityId 申报id
	 * @return 申报的所有评审列表
	 */
	@SuppressWarnings("rawtypes")
	public List getAllAppReviewByAppId(String entityId);
	/**
	 * 获得申报评审意见列表
	 * @param entityId申报id
	 * @return 申报评审意见列表
	 */
	@SuppressWarnings("rawtypes")
	public List getReviewOpinionListFromAppReview(String entityId);
	/**
	 * 获得项目经费默认值
	 * @return 项目经费
	 */
	public Double getDefaultFee(ProjectApplication application);
//	/**
//	 * 根据项目申报id对申报评审对象对应的人员、机构进行入库处理
//	 * @param entityId 项目申报id
//	 */
//	public void doWithNewReviewFromAppReview(String entityId);
	
	//----------以下为获取结项评审信息----------
	/**
	 * 根据项目id获取通过鉴定
	 * @param graId 项目id
	 * @return 通过鉴定
	 */
	@SuppressWarnings("rawtypes")
	public List getPassReviewByGrantedId(String graId);
	
	/**
	 * 根据项目id获取未审鉴定
	 * @param graId 项目id
	 * @return 未审鉴定
	 */
	@SuppressWarnings("rawtypes")
	public List getPendingReviewByGrantedId(String graId);
	/**
	 * 根据项目结项id及人员id获取判断是否项目评审人及评审组长
	 * @param endId 项目结项id
	 * @param personId 人员id
	 * @return 0不是评审人；1是评审人但不是评审组长；2是评审人且是评审组长
	 */
	public Integer isEndReviewer(String graId, String endId);
	/**
	 * 保存结项评审信息
	 * @param endinspectionReview 结项评审对象
	 * @return 更新后的结项评审对象
	 */
	public ProjectEndinspectionReview setEndReviewInfoFromEndReview(ProjectEndinspectionReview endinspectionReview);
	/**
	 * 设置项目结项评审人员对应的研究人员id(teacherId, ExpertId, studentId)的信息
	 * @param review 项目结项评审对象
	 * @return ProjectEndinspectionReview 项目结项评审对象
	 */
	public ProjectEndinspectionReview setReviewPersonInfoFromReview(ProjectEndinspectionReview review);
	/**
	 * 获取个人结项评审对象
	 * @param endId 结项id
	 * @param personId 人员id
	 * @return 结项评审对象
	 */
	public ProjectEndinspectionReview getPersonalEndReview(String endId, String personId);
	
	/**
	 * 获取当前账号能看到的所有人结项评审
	 * @param endId 结项id
	 * @param accountType 登陆者身份	1：系统管理员	2：部级	3：省级	4、5：校级	6：院系	7：研究机构	8：外部专家	9：教师	10：学生
	 * @return 当前账号能看到的所有人结项评审
	 */
	@SuppressWarnings("rawtypes")
	public List getAllEndReviewList(String endId ,AccountType accountType);
	
	/**
	 * 判断项目结项评审是否所有专家全部提交
	 * @param endId 结项id
	 * @return 0所有专家已提交；-1还有专家未提交
	 */
	public int isAllEndReviewSubmit(String endId);
	
	/**
	 * 获取结项评审总分与均分
	 * @param endId 结项id
	 * @return 结项评审总分与均分
	 */
	public double[] getEndReviewScore(String endId);
	
	/**
	 * 判断项目结项是否有评审记录
	 * @param endId 结项id
	 * @return -1无评审记录，1有评审记录且是专家评审，22教育部录入暂存，23教育部录入提交，32省厅录入暂存，33省厅录入提交，42高校录入暂存，43高校录入提交
	 */
	public int checkReview(String endId);
	
	/**
	 * 获得存储形式的分数指标id
	 * @return 存储形式的分数指标id
	 */
	public String getReviewSpecificationIds();
	
	/**
	 * 根据分数计算等级
	 * @param score分数
	 * @return 等级
	 */
	public SystemOption getReviewGrade(double score);
	
	/**
	 * 查询评审专家姓名
	 * @param endRevId评审id
	 * @return 专家姓名
	 */
	public String getReviewerName(String endRevId);
	
	/**
	 * 通过结项id获得结项评审组长的评审信息
	 * @param endId 结项id
	 * @return 评审组长的评审信息
	 */
	public ProjectEndinspectionReview getGroupDirectorReview(String endId);
	
	/**
	 * 根据结项id获得结项的所有评审列表
	 * @param endId 结项id
	 * @return 结项的所有评审列表
	 */
	@SuppressWarnings("rawtypes")
	public List getAllEndReviewByEndId(String endId);
	
	/**
	 * 获得结项评审意见列表
	 * @param endId结项id
	 * @return 结项评审意见列表
	 */
	@SuppressWarnings("rawtypes")
	public List getReviewOpinionList(String endId);
	
	/**
	 * 根据项目结项id对项目评审对象对应的人员、机构进行入库处理
	 * @param endId 项目结项id
	 */
	public void doWithNewReview(String endId);
	
	//------------------以下删除相关对象-----------
	/**
	 * 删除项目
	 * @param appId项目申请id
	 */
	public void deleteProject(String appId);
	
	/**
	 * 删除年检
	 * @param anninspection 年检对象
	 */
	public void deleteAnninspection(ProjectAnninspection anninspection);
	
	/**
	 * 删除中检
	 * @param midinspection 中检对象
	 */
	public void deleteMidinspection(ProjectMidinspection midinspection);
	
	/**
	 * 删除结项
	 * @param endinspection 结项对象
	 */
	public void deleteEndinspection(ProjectEndinspection endinspection);
	
	/**
	 * 删除变更
	 * @param variation变更对象
	 */
	public void deleteVariation(ProjectVariation variation);
	//----------以下为隐藏申报、年检、中检、结项、变更信息----------
	/**
	 * 根据账号类别隐藏申报信息
	 * @param list
	 * @param accountType
	 * @param isReviewer
	 * @return 处理后的申报信息
	 */
	public ProjectApplication hideAppInfo(ProjectApplication application, AccountType accountType, int isReviewer);
	/**
	 * 根据账号类别隐藏年检信息
	 * @param list
	 * @param accountType
	 * @return 处理后的年检信息
	 */
	public List<ProjectAnninspection> hideAnnInfo(List<ProjectAnninspection> list, AccountType accountType);
	/**
	 * 根据账号类别隐藏中检信息
	 * @param list
	 * @param accountType
	 * @return 处理后的中检信息
	 */
	public List<ProjectMidinspection> hideMidInfo(List<ProjectMidinspection> list, AccountType accountType);
	/**
	 * 根据账号类别隐藏结项信息
	 * @param list
	 * @param accountType
	 * @param isReviewer
	 * @return 处理后的结项信息
	 */
	public List<ProjectEndinspection> hideEndInfo(List<ProjectEndinspection> list, AccountType accountType, int isReviewer);
	/**
	 * 根据账号类别隐藏变更信息
	 * @param list
	 * @param accountType
	 * @return 处理后的变更信息
	 */
	public List<ProjectVariation> hideVarInfo(List<ProjectVariation> list, AccountType accountType);
	//----------以下为判断项目是否部属高校-------------------------
	/**
	 * 判断立项项目是否部属高校
	 * @param graId 项目立项id
	 * @return 1:部署高校, 0:地方高校
	 */
	public int isSubordinateUniversityGranted(String graId);
	
	/**
	 * 判断是申报项目是否部属高校
	 * @param appId 项目申请id
	 * @return 1:部署高校, 0:地方高校
	 */
	public int isSubordinateUniversityApplication(String appId);
	//----------以下为对立项、结项编号的查询----------
	/**
	 * 判断项目批准号是否唯一
	 * @param grantedClassName 立项类名称
	 * @param number 项目批准号
	 * @param appId 项目申请id
	 * @return 项目批准号是否唯一
	 */
	public boolean isGrantedNumberUnique(String grantedClassName, String number, String appId);
	
	/**
	 * 判断项目结项号是否唯一
	 * @param endinspectionClassName 结项类名称
	 * @param number 项目结项号
	 * @param endId 结项id
	 * @return 项目结项号是否唯一
	 */
	public boolean isEndNumberUnique(String endinspectionClassName, String number, String endId);
	/**
	 * 判断年检是否审核
	 * param anninspectionClassName 年检类名称
	 * @param year 报告年度
	 * @param annId 报告id
	 * @param graId 立项id
	 * @return 年检是否审核
	 */
	public boolean isAuditReport(String anninspectionClassName, Integer year, String annId, String graId);
	/**
     * 判断年检年度是否早于项目年度
     * @param year 报告年度
     * @param graId 立项id
     * @return 年检年度是否早于项目年度true:不早于 false:早于
     */
	public boolean isEarlyGranted(Integer year, String graId);
	/**
	 * 获得默认项目批准号
	 * @param grantedClassName 立项类名称
	 * @param applicationClassName 申报类名称
	 * @return 默认项目批准号
	 */
	public String getDefaultProjectNumber(String grantedClassName, String applicationClassName, String entityId);
	/**
	 * 获得默认结项证书编号
	 * @param endinspectionClassName 结项类名称
	 * @return 默认结项证书编号
	 */
	public String getDefaultEndCertificate(String endinspectionClassName);
	//----------------以下为上传文件----------------------
	/**
	 * 上传项目申请书文件
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param uploadFile 上传的文件
	 * @param submitStatus 提交状态
	 * @return 返回上传文件保存后的相对路径
	 */
	public String uploadAppFile(String projectType, ProjectApplication application, File uploadFile);
	/**
	 * 上传年检书
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param submitStatus 提交状态
	 * @param uploadFile上传的文件
	 * @return 返回上传文件保存后的相对路径
	 */
	public String uploadAnnFile(String projectType, String graId, int submitStatus, File uploadFile);
	/**
	 * 上传年检书
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param submitStatus 提交状态
	 * @param uploadFile上传的文件
	 * @param annId 年检书id
	 * @return 返回上传文件保存后的相对路径
	 */
	public String uploadAnnFileResult(String projectType, String graId, int submitStatus, File uploadFile, String annId); 
	/**
	 * 上传中检文件
	 * @param projectType 项目类别字符串
	 * @param midId 项目中检id
	 * @param graId 项目立项id
	 * @param midApplicantSubmitStatus
	 * @param uploadFile
	 * @return
	 */
	public String uploadMidFile(String projectType, String graId, int midApplicantSubmitStatus, File uploadFile);
	
	/**
	 * 上传立项计划书
	 * @param projectType 项目类别字符串
	 * @param midId 项目中检id
	 * @param graId 项目立项id
	 * @param midApplicantSubmitStatus
	 * @param uploadFile
	 * @return
	 */
	public String uploadGraFile(String projectType, String graId, int midApplicantSubmitStatus, File uploadFile);
	/**
	 * 中检后上传中检报告书
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param submitStatus 提交状态
	 * @param midId 当前中检id
	 * @param uploadFile上传的文件
	 * @return 返回上传文件保存后的相对路径
	 */
	public String uploadMidFileResult(String projectType, String graId, int submitStatus, File uploadFile, String midId);
	/**
	 * 上传结项文件
	 * @param projectType 项目类别字符串
	 * @param endId 项目结项id
	 * @param graId 项目立项id
	 * @param uploadFile
	 * @param submitStatus 提交状态
	 * @return
	 */
	public String uploadEndFile(String projectType, String graId, File uploadFile, int submitStatus);
	/**
	 * 导入上传结项文件
	 * @param projectType 项目类别字符串
	 * @param endId 项目结项id
	 * @param graId 项目立项id
	 * @param uploadFile
	 * @param endId 当前结项id
	 * @param submitStatus 提交状态
	 * @return
	 */
	public String uploadEndFileResult(String projectType, String graId, File uploadFile, int submitStatus, String endId);
	/**
	 * 上传结项研究数据包文件
	 * @param uploadFile
	 * @return
	 */
	public String uploadEndDataFile(File uploadFile, GeneralGranted projectGranted);
	/**
	 * 上传变更文件
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param uploadFile
	 * @return
	 */
	public String uploadVarFile(String projectType, String graId, int submitStatus, File uploadFile);
	/**
	 * 上传变更文件
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param varId 当前变更id
	 * @param uploadFile
	 * @return
	 */
	public String uploadVarFileResult(String projectType, String graId, int submitStatus, File uploadFile, String varId);
	/**
	 * 上传变更延期项目研究计划文件
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param uploadFile
	 * @return
	 */
	public String uploadVarPlanfile(String projectType, String graId, int submitStatus, File uploadFile);
	//-----------以下为获得项目某些字段的信息---------
	/**
	 * 根基项目申请id获得项目主题名称
	 * @param appId 项目申请id
	 * @return  项目主题名称
	 */
	public String getProjectTopicNameByAppId(String appId);
	//----------以下为业务处理相关操作----------
	/**
	 * 获得当前业务设置状态,用于项目申报
	 * @return 业务状态1:业务激活中，0:业务停止
	 */
	public int getBusinessStatus(String code);
	/**
	 * 获得当前业务设置状态,用于中检、结项、变更
	 * @return 业务状态1:业务激活中，0:业务停止
	 */
	public int getBusinessStatus(String code, String appId);
	/**
	 * 判断当前业务是否在时间有效期内，用于申报
	 * @param accountType 当前账号类型
	 * @param businessType 当前业务类型
	 * @return deadline 各级别审核截止时间，格式为字符串
	 */
	public String checkIfTimeValidate(AccountType accountType, String businessType);
	/**
	 * 判断当前业务是否在时间有效期内，用于专家评审
	 * @param businessType 当前业务类型
	 * @return deadline 各级别审核截止时间，格式为字符串
	 */
	public String checkIfTimeValidate(String businessType);
	/**
	 * 判断当前业务是否在时间有效期内，用于中检、结项、变更
	 * @param accountType 当前账号类型
	 * @param businessType 当前业务类型
	 * @param appId 当前项目申请id
	 * @return deadline 各级别审核截止时间，格式为字符串
	 */
	public String checkIfTimeValidate(AccountType accountType, String businessType, String appId);
	/**
	 * 获得当前业务对象设定的年度,用于申报、选题
	 * @param code 当前业务的code
	 * @param standard 当前业务的standard
	 * @return 业务对象年度
	 */
	public Integer getBusinessDefaultYear(String code, String standard);
	/**
	 * 获得当前业务对象设定的年度,用于年检
	 * @param code 当前业务的code
	 * @param standard 当前业务的standard
	 * @return 业务年份
	 */
	public Integer getBusinessAnnDefaultYear(String code, String standard);
	/**
	 * 根据账号ID获得登录首页中，用于显示的项目处理业务列表
	 * @param accountId
	 * @param projectType 项目类型
	 * @return 当前账号所有要处理的项目业务，包括项目名称、项目类型、业务类型；
	 */
	@SuppressWarnings("rawtypes")
	public Map getTeacherBusinessByAccount(String accountId, String projectType);
	
	/**
	 * 根据账号ID获得登录首页中，用于显示的项目评审业务列表
	 * @param accountId
	 * @return 当前账号所有要处理的评审业务，包括需处理的评审总数及已待评审数；
	 */
	@SuppressWarnings("rawtypes")
	public Map getTeacherReviewProjectByAccount(String accountId, String projectType);
	
	/**
	 * 根据账号ID获得登录首页中，用于显示的项目处理业务列表
	 * @param accountId
	 * @return 当前账号所有要处理的项目待审业务数
	 */
	@SuppressWarnings("rawtypes")
	public Map getManagerBusinessByAccount(String accountId, String projectType);
	
	/**
	 * 根据账号ID获得登录首页中，用于显示专家评审截止时间
	 * @param accountId
	 * @return 专家评审截止时间
	 */
	@SuppressWarnings("rawtypes")
	public Map getReviewDeadlineByAccount(String accountId, String projectType);
	
	/**
	 * 根据账号及中后期管理系统首页的参数设置查询
	 * @param account 当前账号
	 * @param mainFlag 首页参数
	 * @return 查询条件
	 */
	public String mainSearch(Account account, String mainFlag, String projectType);
	//-------------以下为人员、机构相关处理--------------
	/**
	 * 获得人员id对应的所有教师id
	 * @param personid 人员主表id
	 * @return  教师id的list
	 */
	@SuppressWarnings("rawtypes")
	public List getTeacherIdList(String personid);
	
	/**
	 * 根据教师id获得教师及其人员
	 * @param teacherId 教师id
	 * @return  教师
	 */
	@SuppressWarnings("rawtypes")
	public List getTeacherFetchPerson(String teacherId);
	
	/**
	 * 根据教师id和人员id获得教师及其人员
	 * @param teacherId 教师id
	 * @param personId 人员id
	 * @return  教师
	 */
	@SuppressWarnings("rawtypes")
	public List getTeacherFetchPerson(String teacherId, String personId);
	
	/**
	 * 根据项目成员人员id、高校id、院系id获得教师id
	 * @param personId	人员主表id
	 * @param universityId	高校id
	 * @param department	院系
	 * @param institute	研究基地
	 * @return	教师id
	 */
	public String getTeacherIdByMemberAllUnit(String personId, String universityId, Department department, Institute institute);
	
	/**
	 * 根据项目成员人员id、高校id获得教师id
	 * @param personId	人员主表id
	 * @param universityId	高校id
	 * @param department	院系
	 * @param institute	研究基地
	 * @return	教师id
	 */
	public String getTeacherIdByMemberPartUnit(String personId, String universityId);
	
	/**
	 * 根据项目成员人员id、机构名称、部门名称获得专家id
	 * @param personId 人员主表id
	 * @param agencyName 机构名称
	 * @param divisionName 部门名称
	 * @return 专家id
	 */
	public String getExpertIdByPersonIdUnit(String personId, String agencyName, String divisionName);
	
	/**
	 * 获得人员id对应的所有学生id
	 * @param personid 人员主表id
	 * @return  须生id的list
	 */
	@SuppressWarnings("rawtypes")
	public List getStudentIdList(String personid);
	
	/**
	 * 根据学生id获得学生及其人员
	 * @param studnetId 学生id
	 * @return  学生
	 */
	@SuppressWarnings("rawtypes")
	public List getStudentFetchPerson(String studentId);
	
	/**
	 * 根据学生id和人员id获得学生及其人员
	 * @param studentId 外部专家id
	 * @param personId 人员id
	 * @return  学生
	 */
	@SuppressWarnings("rawtypes")
	public List getStudentFetchPerson(String studentId, String personId);
	
	/**
	 * 根据外部专家id获得外部专家及其人员
	 * @param expertId 外部专家id
	 * @return  外部专家
	 */
	@SuppressWarnings("rawtypes")
	public List getExpertFetchPerson(String expertId);
	
	/**
	 * 根据外部专家id和人员id获得外部专家及其人员
	 * @param expertId 外部专家id
	 * @param personId 人员id
	 * @return  外部专家
	 */
	@SuppressWarnings("rawtypes")
	public List getExpertFetchPerson(String expertId, String personId);
	
	/**
	 * 获得院系及其所属高校
	 * @param deptId 院系id
	 * @return  院系
	 */
	@SuppressWarnings("rawtypes")
	public List getDeptFetchUniv(String deptId);
	
	/**
	 * 获得基地及其所属高校
	 * @param instId 基地id
	 * @return  基地
	 */
	@SuppressWarnings("rawtypes")
	public List getInstFetchUniv(String instId);
	
	/**
	 * 根据人员主表id获得该人员的id以及所在的单位和部门
	 * @param personid 人员主表id
	 * @param memberType 1:教师	2：外部专家	3：学生
	 * @return 长度为2的一维字符数组  [0]：人员id（可能是教师id或外部专家id或学生id）	[1]：人员所在的单位与部门 
	 */
	public String[] getPerIdAndUnitName(String personid, int memberType);
	
	/**
	 * 根据基地id获得基地所在高校名称
	 * @param instituteId 基地id
	 * @return 基地所在高校名称
	 */
	public String getUnivNameByInstId(String instituteId);
	
	/**
	 * 由人员Id获取对应学术信息
	 * @param personId
	 * @return 学术信息
	 */
	public Academic getAcademicByPersonId(String personId);
	
	/**
	 * 项目所在机构是否属于西部省份
	 * @param provinceId项目所在机构省份id
	 * @return 是否属于结果，1：是，0：否
	 */
	public int isIncludedWestProvince(String provinceId);
	
	/**
	 * 项目所在机构是否是新疆省
	 * @param provinceId项目所在机构省份id
	 * @return 是否属于结果，1：是，0：否
	 */
	public int isXinjiangProvince(String provinceId);
	
	/**
	 * 项目所在机构是否是西藏省
	 * @param provinceId项目所在机构省份id
	 * @return 是否属于结果，1：是，0：否
	 */
	public int isXizangProvince(String provinceId);
	//-------------------------其他相关操作-------------------------
	
	/**
	 * 获得成果形式code，多个以逗号隔开
	 * @param productTypeNames 成果形式名称 多个以英文分号和空格隔开
	 * @return 成果code
	 */
	public String getProductTypeCodes(String productTypeNames);
	
	/**
	 * 获得成果形式名称，多个以逗号隔开
	 * @param productTypeCodes 成果形式code List
	 * @return 成果名称
	 */
	public String getProductTypeNames(List<String> productTypeCodes);
	
	/**
	 * 设置时间的十分秒为当前时间的十分秒
	 * @param oriDate 原时间
	 * @return 处理后的时间
	 */
	public Date setDateHhmmss(Date oriDate);
	
	/**
	 * 根据登陆账号类别好的评审人身份
	 * @param accountType 登陆账号类别1：系统管理员	2：部级	3：省级	4、5：校级	6：院系	7：研究机构	8：外部专家	9：教师	10：学生
	 * @return 评审人身份	0默认，1专家，2教育部，3省厅，4高校
	 */
	public int getReviewTypeByAccountType(AccountType accountType);
	
	/**
	 * 根据成果类别及数量名称、成果类别及数量名称获得显示的实际成果类别
	 * @param productType 成果类别及数量名称
	 * @param productTyprOther	成果类别及数量名称
	 * @return	显示的实际成果类别
	 */
	public String getProductTypeReal(String productType, String productTypeOther);
	
	/**
	 * 字符串拼接
	 * @param arr 字符串数组
	 * @param sign 并接符号
	 * @author 肖雅
	 */
	public String join(List<String> arr, String sign);
	
	
	/**
	 * 根据证件类别、证件号、人员姓名、人员性别判断这些信息是否符合数据库中的信息
	 * @param idcardType 证件类别
	 * @param idcardNumber 证件号
	 * @param name 人员姓名
	 * @param gender 人员性别
	 * @return 0:不匹配 1：匹配	2：不存在此人员
	 */
	public int isPersonMatch(String idcardType, String idcardNumber, String name, String gender);
	
	/**
	 * 根据立项id获得立项年份
	 * @param graId 立项id
	 * @return 立项年份
	 */
	public int getGrantedYear(String graId);
	
	/**
	 * 根据立项年份判断中检是否禁止
	 * @param grantedYear 立项年份
	 * @return 1：禁止         0：未禁止
	 */
	public int getMidForbidByGrantedDate(int grantedYear);
	
	/**
	 * 根据立项年份判断结项起始时间是否开始
	 * @param grantedYear 立项年份
	 * @return 1：开始         0：未开始
	 */
	public int getEndAllowByGrantedDate(int grantedYear);
	//---------------------------以下为word宏的相关处理------------------------
	
	/**
	 * 生成结项申请Zip文件
	 * @param grantedId 立项id
	 * @param loginerBelongId 登录人id
	 * @param sessionId sessionId
	 * @param projectType 项目类型
	 * @return 是否成功生成文件
	 * @author leida 2011-12-29
	 */
	public boolean createEndinspectionZip(String grantedId, String loginerBelongId, String sessionId, String projectType);
	
	/**
	 * 生成年检申请Zip文件
	 * @param grantedId 立项id
	 * @param loginerBelongId 登录人id
	 * @param sessionId sessionId
	 * @param projectType 项目类型
	 * @return 是否成功生成文件
	 */
//	public boolean createAnninspectionZip(String grantedId, String loginerBelongId, String sessionId, String projectType);
	
	/**
	 * 生成中检申请Zip文件
	 * @param grantedId 立项id
	 * @param loginerBelongId 登录人id
	 * @param sessionId sessionId
	 * @param projectType 项目类型
	 * @return 是否成功生成文件
	 * @author leida 2011-12-29
	 */
	public boolean createMidinspectionZip(String grantedId, String loginerBelongId, String sessionId, String projectType);
	
	/**
	 * 生成变更申请Zip文件
	 * @param grantedId 立项id
	 * @param loginerBelongId 登录人id
	 * @param sessionId sessionId
	 * @param projectType 项目类型
	 * @return 是否成功生成文件
	 * @author leida 2011-12-29
	 */
	public boolean createVariationZip(String grantedId, String loginerBelongId, String sessionId, String projectType);
	
	/**
	 * 导入中检的word宏
	 * @param wordFile word文件
	 * @param grantedId 立项id
	 * @param projectType 项目类型
	 */
	public void importMidinspectionWordXMLData(File wordFile, String grantedId, String projectType);
	
	/**
	 * 导入结项word宏的信息
	 * @param wordFile word文件
	 * @param grantedId 立项id
	 * @param projectType 项目类型
	 * @author leida 2012-01-06
	 */
	public void importEndinspectionWordXMLData(File wordFile, String grantedId, String projectType);
	
	/**
	 * 校验word
	 * @param grantedId 立项id
	 * @param projectType 项目类型
	 * @param wordFile word文件
	 * @param businessType 1.中检 2.变更 3.结项
	 * @return 错误信息
	 */
	public String checkWordFileLegal(String grantedId, String projectType, File wordFile, int businessType);
	//----------以下为项目导出一览表用到---------
	/**
	 * 处理项目类型对应的code
	 * @param 项目类型
	 * @return 查询语句
	 */
	public String getProjectCodeByType(String projectType);
	//----------以下为判断审核人和当前登录人是否一致用到---------
	/**
	 * 处理申报审核人和所在机构信息
	 * @param 申报对象
	 * @return 信息list
	 */
	public List getAppAuditorInfo(ProjectApplication application,LoginInfo loginer);
	/**
	 * 处理立项计划审核人和所在机构信息
	 * @param 中检对象
	 * @return 信息list
	 */
	public List getGraAuditorInfo(ProjectGranted projectGranted, LoginInfo loginer);
	/**
	 * 处理年检审核人和所在机构信息
	 * @param 年检对象
	 * @return 信息list
	 */
	public List getAnnAuditorInfo(ProjectAnninspection anninspection,LoginInfo loginer);
	/**
	 * 处理中检审核人和所在机构信息
	 * @param 中检对象
	 * @return 信息list
	 */
	public List getMidAuditorInfo(ProjectMidinspection midinspection,LoginInfo loginer);
	/**
	 * 处理结项审核人和所在机构信息
	 * @param 结项对象
	 * @return 信息list
	 */
	public List getEndAuditorInfo(ProjectEndinspection endinspection,LoginInfo loginer);
	/**
	 * 处理变更审核人和所在机构信息
	 * @param 变更对象
	 * @return 信息list
	 */
	public List getVarAuditorInfo(ProjectVariation variation,LoginInfo loginer);

	public String getFundListSimpleSearchHQL(int searchType);
	/**
	 * 进入添加立项计划申请时的处理
	 */
	public void doWithToAdd(ProjectGranted projectGranted);
	
	//----------以下为变更列表查询处理----------

	/**
	 * 获得当前登陆者的检索变更的查询语句
	 * @param HQL1  查询语句选择部分
	 * @param HQL2  查询语句条件部分
	 * @param accountType 帐号类别
	 * @return 检索变更的查询语句
	 */
	public StringBuffer getMidHql(String HQL1, String HQL2, AccountType accountType);
}