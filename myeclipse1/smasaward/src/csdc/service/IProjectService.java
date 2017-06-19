package csdc.service;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import csdc.bean.ProjectApplication;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectVariation;
import csdc.bean.common.ExpertLink;


/**
 * 项目管理公共接口
 * @author fengcl
 *
 */
public interface IProjectService extends IBaseService{
	
    /**
     * 专家树中根据选中的专家id","的字符串，获取响应的专家信息（用于右侧已经选择专家列表显示）
     * @param projectType :general,instp
     * @param ids
     * @return
     */
	public String getExpInfo(String projectType, String ids);
	
	/**
	 * 获取专家树数据：获取专家树节点信息<br>
	 * @return	专家树节点信息
	 */
	public List getNodesInfo(Map dataMap);
	
	/**
	 * 根据学科代码(code)获取学科名称(name) 
	 * @param code	学科代码
	 * @param map	学科代码与学科名称的映射（map）
	 * @return	学科名称 
	 */
	public String getDisciplineName(String code, Map map);
	
	/**
	 * 判断专家学科（disciplines，用分号空格隔开）是否包含一个所选的一级学科
	 * @param discipline1Strings 一级学科
	 * @param disciplines 专家学科，用分号空格隔开
	 * @return
	 */
	public boolean checkDisciplineLegal(String[] discipline1Strings, String disciplines);

	
	/**
	 * 获取评审专家的各类评审项目统计信息<br>
	 * @param year
	 * @return
	 * 返回 projectType --> Map（专家id --> cnt[string]）
	 */
	public Map getReviewersProjectsInfo(Integer year);
	
	/**
	 * 获取当前年高校对应的评审专家的跨类项目统计信息<br>
	 * @param year
	 * @return
	 * 返回 projectType --> Map（高校code --> cnt）
	 */
	public Map getUnivReviewersProjectsInfo(Integer year);

	/**
	 * 群删项目
	 * @param projectids 待删项目Id
	 * @param clazz 项目类型
	 */
	public void deleteProjects(List<String> projectids, Class clazz);
	/**
	 * 设置项目参评状态
	 * @param projectids待操作的项目集合
	 * @param label项目状态0退评，1参评
	 * @param notReviewReason 退评原因
	 */
	public void setReview(List<String> projectids, int label, String notReviewReason);
	/**
	 * 清除某年匹配信息
	 * 
	 * @param deleteAutoMatches 是否清除自动匹配对，1是，0否
	 * @param deleteManualMatches 是否清除手工匹配对，1是，0否
	 * @param year 待清除年
	 */
	public void deleteProjectMatchInfos(int deleteAutoMatches,int deleteManualMatches,int year,String projectType);
	/**
	 * 组装学科信息
	 */
	public String getDiscipline(String discipline);
	/**
	 * 初始化专家树
	 * @param projectIds 项目id，用分号空格隔开，如果不涉及项目则填null
	 */
	public void initExpertTree(String projectIds);
	/**
	 * 联合项目的各个学科代码（找到各个项目共有的学科代码）
	 * @param ids 项目ids
	 */
	public String unionProjectDis(String ids);
	/**
	 * 联合项目评审专家
	 * @param ids 项目ids
	 */
	public String unionProjectRev(String ids);
	/**
	 * 连接字符串（取两个项目的学科代码的交集，用于找到相同学科类属的专家）
	 * @param str1	项目1的学科代码字符串
	 * @param str2	项目2的学科代码字符串
	 * @return	两个项目的学科代码的交集
	 */
	public String unionString(String str1, String str2);
	/**
	 * 项目导出
	 * @param exportAll	1：导出当前年所有项目（参评+备评）；0：根据当前项目列表检索情况导出
	 * @param containReviewer ture：包含评审信息；false：不包含评审信息
	 * @param projectType 项目类型
	 * @return
	 */
	public InputStream exportProject(int exportAll, boolean containReviewer, String projectType);
	/**
	 * 在父类中定义项目导出的数据处理，子类处理数据
	 * @return
	 */
	public Map exportProjectDealWith(int exportAll, boolean containReviewer, Map container);
	/**
	 * 导出基地项目专家调整更新表
	 * @param year	匹配年度
	 * @param start	更新开始时间
	 * @param end	更新结束时间
	 * @param projectType 项目类型
	 */
	public InputStream exportMatchUpdate(Integer year, Date start, Date end, String projectType) throws Exception;

	/**
	 * 项目匹配更新子类业务直接调用
	 * （公共业务方法）
	 * @param projectType
	 * @param projectIds
	 * @param year
	 */
	public void projectUpdateWarningReviewer(String projectType, List<String> projectIds, Integer year);
	/**
	 * 自动匹配
	 * （公共业务方法）
	 * Action 调用 公共Service
	 * @param year
	 * @param expertIds
	 * @param projectids
	 * @param rejectExpertIds
	 * @throws Exception
	 */
	public void matchExpert(String projectType, 
			Integer year, 
			List<String> expertIds, 
			List<String> projectids, 
			List<String> rejectExpertIds) throws Exception;
	
	/**
	 * 取得专家职称（c_speciality_title）相关的集合信息
	 * （公共方法）
	 * aboveSubSeniorTitles：副高级职称以上专家<br>
	 * seniorTitles：正高级、高级职称专家<br>
	 * specialityTitle2RatingMap:职称对应的职称等级<br>
	 * 副高级:副高级的职称集合
	 * @return
	 */
	public Map selectSpecialityTitleInfo();
	
	/**
	 * 根据学科代码(code)获取学科名称(name) 
	 * @param code	学科代码
	 * @param map	学科代码与学科名称的映射（map）
	 * @return
	 */
	public String getDiscipline1Name(String code, Map map);
	
	public String getExpDetail(String id);
	/**
	 * 根据项目ID找到已分配专家的名称，高校
	 * @param projectid项目ID
	 * @return 相关信息
	 */
	public List<ExpertLink> getExpert(String projectid);
	/**
	 * 将项目的学科代码拆分为list
	 * @param discipline学科字符串
	 * @return 拆分后的学科代码list
	 */
	public List<String> getProjectDiscipline(String discipline);
	/**
	 * 根据学科名称搜索包含该名称的学科代码
	 * @param disciplineName学科名称
	 * @return 包含该关键字的学科代码集合
	 */
	public List<Object> getDisciplineCode(String disciplineName);
	/**
	 * 组装学科信息
	 */
	public String getDisciplineInfo(Map<String, String> disname2discode, String discipline);
	public int saveTemp(String c1, String c2);
	/**
	 * 获取所有人申报评审信息
	 * @param entityId
	 * @return
	 */
	public List getAllAppReviewList(String entityId);
	/**
	 * 根据项目申请id获取项目立项id
	 * @param appId 项目申请id
	 * @return 项目立项id
	 */
	public String getGrantedIdByAppId(String appId);
	/**
	 * 根据项目立项id获取项目申请id
	 * @param graId 项目立项id
	 * @return 项目申请id
	 */
	public String getApplicationIdByGrantedId(String graId);
	/**
	 * 根据项目立项id获取项目
	 * @param graId 项目立项id
	 * @return 项目
	 */
	public ProjectGranted getGrantedFetchDetailByGrantedId(String graId);
	/**
	 * 根据项目立项id获取通过结项
	 * @param graId 项目立项id
	 * @return 通过结项
	 */
	@SuppressWarnings("rawtypes")
	public List getPassEndinspectionByGrantedId(String graId);
	/**
	 * 根据项目立项id获取未审结项
	 * @param graId 项目立项id
	 * @return 未审结项
	 */
	@SuppressWarnings("rawtypes")
	public List getPendingEndinspectionByGrantedId(String graId);
	/**
	 * 根据项目立项id获取未审变更
	 * @param graId 项目立项id
	 * @return 未审变更
	 */
	@SuppressWarnings("rawtypes")
	public List getPendingVariationByGrantedId(String graId);
	/**
	 * 根据项目立项id获取所有结项
	 * @param graId 项目立项id
	 * @return 所有结项
	 */
	public List<ProjectEndinspection> getAllEndinspectionByGrantedId(String graId);
	/**
	 * 根据项目立项id获取当前结项
	 * @param graId 项目立项id
	 * @return 当前结项
	 */
	public ProjectEndinspection getCurrentEndinspectionByGrantedId(String graId);
	/**
	 * 获取所有人结项评审
	 * @param endId 结项id
	 * @return 所有人结项评审
	 */
	@SuppressWarnings("rawtypes")
	public List getAllEndReviewList(String endId);
	/**
	 * 根据项目结项id获取结项经费
	 * @param endIds 项目结项id
	 * @return 结项经费
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProjectFeeEndByEndId(String grantedId, List<String> endIds);
	/**
	 * 根据项目立项id所有变更列表
	 * @param graId 项目立项id 
	 * @return 获取所有变更列表
	 */
	public List<ProjectVariation> getAllVariationByGrantedId(String graId);
	/**
	 * 根据项目立项id获取当前变更
	 * @param graId 项目立项id
	 * @return 当前的变更
	 */
	public ProjectVariation getCurrentVariationByGrantedId(String graId);
	/**
	 * 根据项目变更id获取项目id
	 * @param varId 项目变更id
	 * @return 项目id
	 */
	public String getGrantedIdByVarId(String varId);
	
	/**
	 * 根据项目立项id获取通过中检
	 * @param graId 项目立项id
	 * @return 通过中检
	 */
	@SuppressWarnings("rawtypes")
	public List getPassMidinspectionByGrantedId(String graId);
	/**
	 * 根据项目立项id获取未审中检
	 * @param graId 项目立项id
	 * @return 未审中检
	 */
	@SuppressWarnings("rawtypes")
	public List getPendingMidinspectionByGrantedId(String graId);
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
	/**
	 * 根据项目立项id获取所有可见范围内中检
	 * @param graId 项目立项id
	 * @param accountType 帐号类别
	 * @return 所有可见范围内中检
	 */
	public List<ProjectMidinspection> getAllMidinspectionByGrantedId(String graId);
	/**
	 * 根据项目中检id获取中检经费
	 * @param midIds 项目中检id
	 * @return 中检经费
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProjectFeeMidByMidId(String grantedId, List<String> midIds);
	/**
	 * 设置时间的十分秒为当前时间的十分秒
	 * @param oriDate 原时间
	 * @return 处理后的时间
	 */
	/**
	 * 根据项目立项id获取当前中检
	 * @param graId 项目立项id
	 * @return 当前的中检
	 */
	public ProjectMidinspection getCurrentMidinspectionByGrantedId(String graId);
	
	public Date setDateHhmmss(Date oriDate);	

	/**
	 * 判断立项项目是否部属高校
	 * @param graId 项目立项id
	 * @return 1:部署高校, 0:地方高校
	 */
	public int isSubordinateUniversityGranted(String graId);
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
	 * 获得可以同意的变更事项List
	 * @param varItemString 变更事项id，多个以,隔开
	 * @return list[code][name]
	 */
	@SuppressWarnings("rawtypes")
	public List getVarItemList(String varItemString);
	/**
	 * 通过同意变更事项的字串拼接成同意变更事项的编码(非项目模块负责人，请勿修改。要改则只允许扩展代码、不允许修改已有代码)
	 * @param varSelectApproveIssue	同意变更事项 多个已,隔开
	 * @return 同意变更事项的编码
	 */
	public String getVarApproveItem(String varSelectApproveIssue);
	/**
	 * 变更项目信息
	 * @param variation 变更对象
	 */
	public void variationProject(ProjectVariation variation) throws DocumentException;
	
	/**
	 * 根据立项id获得立项年份
	 * @param graId 立项id
	 * @return 立项年份
	 */
	public int getGrantedYear(String graId);
	/**
	 * 获得默认结项证书编号
	 * @param projectType 项目类型
	 * @return 默认结项证书编号
	 */
	public String getDefaultEndCertificate();
	/**
	 * 判断项目结项号是否唯一
	 * param projectType 项目类型
	 * @param number 项目结项号
	 * @param endId 结项id
	 * @return 项目结项号是否唯一true：唯一 false：不唯一
	 */
	public boolean isEndNumberUnique(String number, String endId);
	/**
	 * 根据项目结项id获取项目id
	 * @param endId 项目结项id
	 * @return 项目id
	 */
	@SuppressWarnings("unchecked")
	public String getGrantedIdByEndId(String endId);
	
	/**
	 * 管理人员录入中检报告书
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param uploadFile上传的文件
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
	public String uploadMidFile(String projectType, String graId, File uploadFile);
	/**
	 * 导入中检的word宏
	 * @param wordFile word文件
	 * @param grantedId 立项id
	 * @param projectType 项目类型
	 */
	public void importMidinspectionWordXMLData(File wordFile, String grantedId, String projectType);
	
	/**
	 * 根据项目立项id获取已审并同意的变更
	 * @param graId 项目立项id
	 * @return 未审变更
	 */
	@SuppressWarnings("rawtypes")
	public List getAuditedVariationByGrantedId(String graId);
	/**
	 * 获得变更次数
	 * @param graId 项目立项id
	 * @return 系统已有的变更次数
	 */
	public int getVarTimes(String graId);
	/**
	 * 获得成果形式code，多个以逗号隔开
	 * @param productTypeNames 成果形式名称 多个以英文分号和空格隔开
	 * @return 成果code
	 */
	public String getProductTypeCodes(String productTypeNames);
	/**
	 * 研究人员上传变更文件
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param uploadFile 上传的文件
	 * @return 返回上传文件保存后的相对路径
	 */
	public String uploadVarFile(String projectType, String graId, File uploadFile);
	/**
	 * 获得成果形式名称，多个以逗号隔开
	 * @param productTypeCodes 成果形式code List
	 * @return 成果名称
	 */
	public String getProductTypeNames(List<String> productTypeCodes);
	/**
	 * 上传变更延期项目研究计划文件
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param uploadFile
	 * @return
	 */
	public String uploadVarPlanfile(String projectType, String graId, File uploadFile);
	//----------以下为设置项目经费相关信息----------
	/**
	 * 保存项目申请经费的相关字段的值
	 * @param projectFee 项目经费的对象
	 */
	public ProjectFee setProjectFee(ProjectFee projectFee);
	
	/**
	 * 输入人名时可能有word中复制出的奇怪字符“•”，前台让其校验通过，在后台替换为“·”
	 * @param nameString
	 * @return
	 */
	public String regularNames(String nameString);
	/**
	 * 上传结项文件
	 * @param projectType 项目类别字符串
	 * @param endId 项目结项id
	 * @param graId 项目立项id
	 * @param uploadFile
	 * @param submitStatus 提交状态
	 * @return
	 */
	public String uploadEndFile(String projectType, String graId, File uploadFile);

	/**
	 * 获得项目经费默认值
	 * @return 项目经费
	 */
	public Double getDefaultFee(ProjectApplication application);
	/**
	 * 根据项目id获取项目已拨款经费
	 * @param graId 项目id
	 * @return 项目拨款
	 */
	public Double getFundedFeeByGrantedId(String graId);
	/**
	 * 设置项目立项信息,用于走流程申报
	 * @param application 项目申请对象
	 * @param granted 项目立项对象
	 * @return ProjectGranted
	 */
	public ProjectGranted setGrantedInfoFromApp(ProjectApplication application, ProjectGranted granted);
	
}
