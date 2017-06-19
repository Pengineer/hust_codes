package csdc.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Hibernate版本的BaseDao
 * 新增了若干session相关的操作，用于特别需求
 * @author xuhan
 *
 */
public interface IHibernateBaseDao extends IBaseDao {

	public void flush();

	public void clear();

	public boolean contains(Object entity);
	
	public void evict(Object entity);

	public Object merge(Object entity);
	
	public void persist(Object entity);
	
	public void refresh(Object entity);

	public Iterator iterate(String queryString);

	public Iterator iterate(String queryString, Object... values);

	void deleteAll(Class entityClass, List<Serializable> ids);

}
