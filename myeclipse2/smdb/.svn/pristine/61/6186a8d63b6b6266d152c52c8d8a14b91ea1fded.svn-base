package csdc.service;

import java.util.Map;

import csdc.tool.bean.LoginInfo;
import csdc.tool.bean.Mobile;


public interface IMobilePersonService extends IBaseService {
//	
//	/**
//	 * 获取各类人员初级检索查询语句
//	 * @param loginer  	            当前登录账号信息对象引用变量
//	 * @param keyword   	  初级检索关键字：姓名
//	 * @param personType 	 人员类型：0:部级管理人员;1:省级管理人员;2:高校管理人员;3:院系管理人员;4.基地管理人员;5:外部专家;6:教师;7:学生;
//	 * @return paraMap		查询语句hql ，参数map
//	 */
//	@SuppressWarnings("unchecked")
//	public Map getSimpleSearchHql(LoginInfo loginer, String keyword, Integer personType);
//	
//	/**
//	 * 获取各类人员高级检索查询语句
//	 * @param loginer	     当前登录账号信息对象引用变量	
//	 * @param mobile   	  Moblie工具类对象引用变量 
//	 * @param personType  人员类型：0:部级管理人员;1:省级管理人员;2:高校管理人员;3:院系管理人员;4.基地管理人员;5:外部专家;6:教师;7:学生;
//	 * @return paraMap	     查询语句hql ，参数map
//	 */
//	@SuppressWarnings("unchecked")
//	public Map getAdvSearchHql(LoginInfo loginer, Mobile mobile, Integer personType);
	
	/**
	 * 获取各类人员信息查看查询语句
	 * @param entityId    待查看的人员id
	 * @param personType  待查看人员类型
	 * @param listType    待查看人员类别
	 * @return paraMap	      查询语句hql ，参数map
	 */
	@SuppressWarnings("unchecked")
	public Map getViewHql(String entityId, String personType, Integer listType);
}
