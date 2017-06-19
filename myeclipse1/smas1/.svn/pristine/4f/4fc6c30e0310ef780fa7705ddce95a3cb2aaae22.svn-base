package csdc.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public interface IBaseService {
	
	/**
	 * 添加实体
	 * @param entity 实体的类
	 * @return 返回实体添加后的ID
	 */
	public Serializable add(Object entity);

	/**
	 * 添加实体集合
	 * @param entity 实体的类
	 */
	public void add(Collection entity);

	/**
	 * 获取文件大小
	 * @param fileLength
	 * @return 文件大小字符串
	 */
	public String accquireFileSize(long fileLength);
	/**
	 * 添加/修改实体
	 * @param entity 实体的类
	 * @return 返回实体添加后的ID
	 */
	public void addOrModify(Object entity);

	/**
	 * 添加/修改实体集合
	 * @param entity 实体的类
	 */
	public void addOrModify(Collection entity);

	/**
	 * 删除实体
	 * @param entity 实体的类
	 */
	public void delete(Object entity);

	/**
	 * 根据实体类型和实体ID删除实体
	 * @param entityClass 实体类型
	 * @param id 实体ID
	 */
	public void delete(Class entityClass, Serializable id);

	/**
	 * 删除实体集合
	 * @param collection 实体集合
	 */
	public void deleteMore(Collection collection);

	/**
	 * 根据实体类型和实体ID集合删除多个实体
	 * @param entityClass 实体类型
	 * @param ids 实体ID集合
	 */
	public void deleteMore(Class entityClass, Collection<String> ids);

	/**
	 * 修改实体
	 * @param entity 实体的类
	 */
	public void modify(Object entity);
	
	/**
	 * 根据实体ID和实体类型查找实体对象
	 * @param entityClass 实体类型
	 * @param id 实体ID
	 * @return 返回实体对象
	 */
	public Object query(Class entityClass, Serializable id);

	/**
	 * 根据查询语句执行查询，返回所有结果
	 * @param queryString 查询语句
	 * @return 返回list的object[]对象
	 */
	public List query(String queryString);

	/**
	 * 根据查询语句和参数表执行查询，返回所有结果
	 * @param queryString 查询语句
	 * @param parMap 参数表
	 * @return 返回list的object[]对象
	 */
	public List query(String queryString, Map parMap) ;

	/**
	 * 根据查询语句和参数值执行查询，返回所有结果
	 * @param queryString 查询语句
	 * @param values 参数值
	 * @return 返回list的object[]对象
	 */
	public List query(String queryString, Object... values) ;

	/**
	 * 根据查询语句，从指定位置查找若干条连续记录
	 * @param queryString 查询语句
	 * @param firstResult 查询起始位置
	 * @param maxResults 查询记录数
	 * @return 返回list的object[]对象
	 */
	public List query(String queryString, int firstResult, int maxResults);

	/**
	 * 根据查询语句和参数表，从指定位置查找若干条连续记录
	 * @param queryString 查询语句
	 * @param parMap 参数表
	 * @param firstResult 查询起始位置
	 * @param maxResults 查询记录数
	 * @return 返回list的object[]对象
	 */
	public List query(String hql, Map parMap, int firstResult, int maxResults);

	/**
	 * 执行最多返回一条结果的查询语句，返回该对象
	 * @param queryString 查询语句
	 * @return 返回一个对象，若无对象，返回null
	 */
	public Object queryUnique(String queryString);

	/**
	 * 执行最多返回一条结果的查询语句，返回该对象
	 * @param queryString 查询语句
	 * @param parMap 参数表
	 * @return 返回一个对象，若无对象，返回null
	 */
	public Object queryUnique(String queryString, Map parMap) ;

	/**
	 * 根据查询语句和参数值执行查询，返回所有结果
	 * @param queryString 查询语句
	 * @param values 参数值
	 * @return 返回一个对象，若无对象，返回null
	 */
	public Object queryUnique(String queryString, Object... values) ;

	/**
	 * 获得查询结果的记录条数
	 * @param hql 查询语句
	 * @return 记录数
	 */
	public long count(String hql);

	public Long count(String hql, Map parMap);

	public void execute(String hql);

	public void execute(String hql, Map parMap);
	
	public void execute(String hql, Object... values);
	/**
	 * 处理字串，多个以英文分号与空格隔开
	 * @param originString 原始字串
	 * @return 处理后字串
	 */
	public String MutipleToFormat(String originString);
}
