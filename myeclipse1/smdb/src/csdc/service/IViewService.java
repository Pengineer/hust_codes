package csdc.service;

import java.util.Map;

/**
 * 其它模块查看接口
 * @author 龚凡
 */
@SuppressWarnings("unchecked")
public interface IViewService extends IBaseService {

	/**
	 * 根据账号ID获得其它模块查看时的数据，包括(账号名、启用状态、所属单位、
	 * 所属人员、账号类型)
	 * @param entityId
	 * @return map
	 */
	public Map viewAccount(String entityId);
	
	/**
	 * 查看通行证
	 * @param entityId
	 * @return map
	 */
	public Map viewPassport(String entityId);

	/**
	 * 根据人员ID获得其它模块查看时的数据，包括(姓名、性别、出生日期、
	 * 所在机构、邮箱)
	 * @param type, entityId
	 * @return map
	 */
	public Map viewPerson(int type, String entityId);

	/**
	 * 根据人员ID获得其它模块查看时的数据，包括(机构名称、上级管理部门、通讯地址
	 * 邮编、邮箱)
	 * @param type, entityId
	 * @return map
	 */
	public Map viewAgency(int type, String entityId);

	/**
	 * 根据项目ID获得其它模块查看时的数据，包括(项目名称、项目类型、年度
	 * 负责人、依托高校)
	 * @param projectTypeId, entityId
	 * @return map
	 */
	public Map viewProject(String projectTypeId,String entityId);
	
	/**
	 * 查看日志
	 * @param entityId 日志ID
	 * @return map
	 */
	public Map viewLog(String entityId);
	
	/**
	 * 备忘提醒
	 * @return map
	 */
	public Map viewMemo();
	
	/**
	 * 备忘提醒
	 * @param flag 处理标志区分:flag = 1 表示用户中心列表 ； 其他表示弹出层查看当天备忘
	 * @return
	 */
	public Map viewMemo(int flag);
	
	/**
	 * 查看团队
	 * @param entityId 团队ID
	 * @return map
	 */
	public Map viewOrganization(String entityId);

	public Map viewProjectFunding(String projectTypeId, String entityId);

}
