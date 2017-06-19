package csdc.dao;


import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 数据访问对象接口
 * @author gongfan
 * @author leida
 * @author xuhan
 *
 */
@SuppressWarnings({"rawtypes"})
public interface IBaseDao {
	public Serializable add(final Object entity);
	public void addOrModify(final Object entity);

	public void delete(final Object entity);
	public void delete(final Class entityClass, final Serializable id);
	public void deleteAll(final Class entityClass, final List<Serializable> ids);
	
	public void modify(final Object entity);
	
	public <T> T query(final Class<T> entityClass, final Serializable id);
	public <T> Set<T> query(final Class<T> entityClass,  Serializable [] ids);
	public List query(final String queryString);
	public List query(final String queryString, final Map paraMap);
	public List query(final String queryString, Object... values);
	public List query(final String queryString, final Integer firstResult, final Integer maxResults);
	public List query(final String queryString, final Map paraMap, final Integer firstResult, final Integer maxResults);
	public Object queryUnique(final String queryString);
	public Object queryUnique(final String queryString, final Map paraMap);
	public Object queryUnique(final String queryString, Object... values);
	
	public List nativeSQLQuery(final String queryString);
	public List nativeSQLQuery(final String queryString, Class clazz);

	public long count(final String queryString);
	public long count(final String queryString, final Map paraMap);

	public void execute(final String statement);
	public void execute(final String statement, final Map paraMap);
	public void execute(final String statement, Object... values);

}
