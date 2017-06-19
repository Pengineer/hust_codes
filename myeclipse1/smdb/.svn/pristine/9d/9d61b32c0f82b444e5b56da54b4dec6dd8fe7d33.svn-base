package csdc.service;

import java.util.List;
import java.util.Map;

import csdc.bean.Agency;
import csdc.bean.Person;
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
	
	/**
	 * 院系或基地获取地址
	 * @param t
	 * @return
	 * @throws Exception 
	 */
	public <T> List getAddress(T t) throws Exception;
	
	/**
	 * 人员获取家庭地址
	 * @param person
	 * @return
	 */
	public List getHomeAddress(Person person);
	
	/**
	 * 部省校级管理机构获取社科管理部门地址
	 * @param person
	 * @return
	 */
	public List getSAddress(Agency agency);
}