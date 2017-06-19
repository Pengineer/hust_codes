package csdc.service;

import java.util.Map;


public interface IMobilePersonService extends IBaseService {
	
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
