package csdc.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Hibernate实现的IBaseDao
 * 取消显式控制事务边界，全部交给Spring在service层控制
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class HibernateBaseDao extends HibernateDaoSupport implements IHibernateBaseDao {
	
	/**
	 * 将map中值为null的统一设成一个不可能取到的String
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void fillNullValue(Map map) {
		if (map != null) {
			for (Object key : map.keySet()) {
				if (map.get(key) == null) {
					map.put(key, "不能这么搞，不要传null进来!");
				}
			}
		}
	}

	public Serializable add(Object entity) {
		return getHibernateTemplate().save(entity);
	}
	
	public void addOrModify(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public void delete(Object entity) {
		Object persistentEntity = getHibernateTemplate().merge(entity);
		getHibernateTemplate().delete(persistentEntity);
	}
	
	public void delete(Class entityClass, Serializable id) {
		Object persistentEntity = getHibernateTemplate().get(entityClass, id);
		getHibernateTemplate().delete(persistentEntity);
	}
	
	public void modify(Object entity) {
		getHibernateTemplate().merge(entity);
	}

	public Object query(Class entityClass, Serializable id) {
		return getHibernateTemplate().get(entityClass, id);
	}
	
	public List query(String queryString) {
		return getHibernateTemplate().find(queryString);
	}

	public List query(String queryString, Map paraMap) {
		return query(queryString, paraMap, null, null);
	}

	public List query(final String queryString, Object... values) {
		return getHibernateTemplate().find(queryString, values);
	}

	public List query(String queryString, Integer firstResult, Integer maxResults) {
		return query(queryString, null, firstResult, maxResults);
	}
	
	public List query(final String queryString, final Map paraMap, final Integer firstResult, final Integer maxResults) {
//		System.out.println(queryString);

		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				if (paraMap != null) {
					query.setProperties(paraMap);
				}
				if (firstResult != null) {
					query.setFirstResult(firstResult);
				}
				if (maxResults != null) {
					query.setMaxResults(maxResults);
				}
				return query.list();
			}
		});
	}
	
	public Object queryUnique(String queryString) {
		return queryUnique(queryString, new HashMap());
	}

	public Object queryUnique(final String queryString, final Map paraMap) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				if (paraMap != null) {
					query.setProperties(paraMap);
				}
				return query.uniqueResult();
			}
		});
	}
	
	public Object queryUnique(final String queryString, final Object... values) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				for (int position = 0; position < values.length; position++) {
					query.setParameter(position, values[position]);
				}
				return query.uniqueResult();
			}
		});
	}


	public long count(String queryString) {
		return count(queryString, null);
	}

	public long count(final String queryString, final Map paraMap) {
		String queryStringLowerCase = queryString.toLowerCase();
		String countQueryString = "select count(*) " + queryString.substring(queryString.indexOf("from"));
		if (queryStringLowerCase.contains("group by") || queryStringLowerCase.contains("distinct")) {
			return query(countQueryString, paraMap).size();
		} else {
			return (Long) queryUnique(countQueryString, paraMap);
		}
	}

	public void execute(String statement) {
		execute(statement, new HashMap());
	}
	
	public void execute(final String statement, final Map paraMap) {
//		System.out.println(statement);

		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(statement);
				if (paraMap != null) {
					query.setProperties(paraMap);
				}
				query.executeUpdate();
				return null;
			}
		});
	}
	
	public void execute(final String statement, Object... values) {
		getHibernateTemplate().bulkUpdate(statement, values);
	}
	
	//////////////////////////////以下为IHibernateBaseDao声明的方法////////////////////////////////////
	
	public void flush() {
		getHibernateTemplate().flush();
	}

	public void clear() {
		getHibernateTemplate().clear();
	}

	public boolean contains(Object entity) {
		return getHibernateTemplate().contains(entity);
	}
	
	public void evict(Object entity) {
		getHibernateTemplate().evict(entity);
	}

	public Object merge(Object entity) {
		return (Object)getHibernateTemplate().merge(entity);
	}
	
	public void persist(Object entity) {
		getHibernateTemplate().persist(entity);
	}
	
	public void refresh(Object entity) {
		getHibernateTemplate().refresh(entity);
	}
	
	public Iterator iterate(String queryString) {
		return getHibernateTemplate().iterate(queryString);
	}
	
	public Iterator iterate(String queryString, Object... values) {
		return getHibernateTemplate().iterate(queryString, values);
	}
	
	public Session _getSession() {
		return super.getSession();
	}

	public void _releaseSession(Session session) {
		super.releaseSession(session);
	}
	
	public Object execute(HibernateCallback callback) {
		return getHibernateTemplate().execute(callback);
	}
}
