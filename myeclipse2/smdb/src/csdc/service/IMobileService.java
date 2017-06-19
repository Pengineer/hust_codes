package csdc.service;

import java.util.Map;

import csdc.tool.bean.LoginInfo;
import csdc.tool.bean.Mobile;

public interface IMobileService extends IBaseService {
	/**
	 * 获取初级检索查询语句
	 * @param loginer     当前登录账号信息对象引用变量
	 * @param keyword     初级检索关键字
	 * @param type  	     五大类的检索类型：人员类型，机构类型，项目类型，成果类型，奖励类型
	 * @return paraMap    查询语句hql ，参数map
	 */
	@SuppressWarnings("unchecked")
	public Map getSimpleSearchHql(LoginInfo loginer, String keyword, Integer type);
	
	/**
	 * 获取高级检索查询语句
	 * @param loginer	     当前登录账号信息对象引用变量	
	 * @param mobile      Moblie工具类对象引用变量 
	 * @param type        五大类的检索类型：人员类型，机构类型，项目类型，成果类型，奖励类型
	 * @return paraMap    查询语句hql ，参数map
	 */
	@SuppressWarnings("unchecked")
	public Map getAdvSearchHql(LoginInfo loginer, Mobile mobile, Integer type);
}