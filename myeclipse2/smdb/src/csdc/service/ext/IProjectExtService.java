package csdc.service.ext;

import java.util.List;

import csdc.bean.Academic;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectFunding;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectVariation;
import csdc.tool.bean.AccountType;

public interface IProjectExtService {
	//----------以下为以定制列表形式获取项目信息----------
	/**
	 * 根据项目立项id获取项目拨款
	 * @param graId 项目立项id
	 * @return 项目拨款
	 */
	@SuppressWarnings("rawtypes")
	public List getFundListByGrantedId(String graId);
	//----------以下为获得项目对象或id----------
	/**
	 * 根据项目申请id获取项目申请
	 * @param appId 项目申请id
	 * @return 项目申请
	 */
	public ProjectApplication getApplicationFetchDetailByAppId(String appId);
	/**
	 * 根据项目申请id获取项目立项id
	 * @param appId 项目申请id
	 * @return 项目立项id
	 */
	public String getGrantedIdByAppId(String appId);
	/**
	 * 根据项目立项id获取项目
	 * @param graId 项目立项id
	 * @return 项目
	 */
	public ProjectGranted getGrantedFetchDetailByGrantedId(String graId);
	/**
	 * 根据项目结项id获取项目立项id
	 * @param endId 项目结项id
	 * @return 项目立项id
	 */
	public String getGrantedIdByEndId(String endId);
	//----------以下为获得结项对象或id，包括所有结项、所有可见范围内结项、待审结项、通过结项、当前结项、当前待审录入/导入结项----------
	/**
	 * 根据项目立项id获取所有可见范围内结项
	 * @param graId 项目立项id
	 * @param accountType 帐号类别
	 * @return 所有可见范围内结项
	 */
	public List<ProjectEndinspection> getAllEndinspectionByGrantedIdInScope(String graId, AccountType accountType);
	//----------以下为获得中检对象或id，包括所有中检、所有可见范围内中检、待审中检、通过中检、当前中检、当前待审录入/导入中检----------
	/**
	 * 根据项目立项id获取所有可见范围内中检
	 * @param graId 项目立项id
	 * @param accountType 帐号类别
	 * @return 所有可见范围内中检
	 */
	public List<ProjectMidinspection> getAllMidinspectionByGrantedIdInScope(String graId, AccountType accountType);
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
	//----------以下为获得变更对象或id，包括所有变更、所有可见范围内变更、待审变更----------
	/**
	 * 根据项目立项id、当前登陆者帐号类别来获取登陆者管辖范围内的所有变更列表
	 * @param graId 项目立项id 
	 * @param accountType 帐号类别
	 * @return 登陆者管辖范围内的所有变更列表
	 */
	public List<ProjectVariation> getAllVariationByGrantedIdInScope(String graId, AccountType accountType);
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
	//-----------以下为获得项目某些字段的信息---------
	/**
	 * 根基项目申请id获得项目主题名称
	 * @param appId 项目申请id
	 * @return  项目主题名称
	 */
	public String getProjectTopicNameByAppId(String appId);
	//-------------以下为人员、机构相关处理--------------
	/**
	 * 由人员Id获取对应学术信息
	 * @param personId
	 * @return 学术信息
	 */
	public Academic getAcademicByPersonId(String personId);
	//-------------------------其他相关操作-------------------------
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
	//----------以下为以定制列表形式获取项目信息,人员、机构模块用到---------
	/**
	 * 得到研究人员、高校、院系、研究基地的项目列表
	 * @param type 类型(1:研究人员 2:高校 3:院系 4:研究基地)
	 * @param entityId 实体id
	 * @return 项目列表
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getProjectListByEntityId(int type, String entityId);
}
