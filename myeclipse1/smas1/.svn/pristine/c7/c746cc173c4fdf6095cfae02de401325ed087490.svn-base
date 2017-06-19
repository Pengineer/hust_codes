package csdc.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@SuppressWarnings("unchecked")
public class SqlBaseDao extends HibernateDaoSupport implements IBaseDao {
	
	public Serializable add(Object entity) {
		throw new RuntimeException("未实现的方法");
	}
	
	public void addOrModify(Object entity) {
		throw new RuntimeException("未实现的方法");
	}

	public void delete(Object entity) {
		throw new RuntimeException("未实现的方法");
	}
	
	public void delete(Class entityClass, Serializable id) {
		throw new RuntimeException("未实现的方法");
	}
	
	public void modify(Object entity) {
		throw new RuntimeException("未实现的方法");
	}

	public Object query(Class entityClass, Serializable id) {
		throw new RuntimeException("未实现的方法");
	}
	
	public List query(String queryString) {
		return query(queryString, null, null, null);
	}

	public List query(String queryString, Map paraMap) {
		return query(queryString, paraMap, null, null);
	}
	
	public List query(String queryString, Object... values) {
		throw new RuntimeException("未实现的方法");
	}

	public List query(String queryString, Integer firstResult, Integer maxResults) {
		return query(queryString, null, firstResult, maxResults);
	}

	public List query(final String queryString, final Map paraMap, final Integer firstResult, final Integer maxResults) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				//difference from HibernateBaseDao
				Query query = session.createSQLQuery(queryString);
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
				//difference from HibernateBaseDao
				Query query = session.createSQLQuery(queryString);
				if (paraMap != null) {
					query.setProperties(paraMap);
				}
				return query.uniqueResult();
			}
		});
	}

	public Object queryUnique(String queryString, Object... values) {
		throw new RuntimeException("未实现的方法");
	}
	
	public long count(String queryString) {
		return count(queryString, null);
	}
	
	public long count(String queryString, Map paraMap) {
		String countQueryString = "select count(*) from (" + queryString + ")";
		BigDecimal cnt = (BigDecimal) queryUnique(countQueryString, paraMap);
		return cnt.longValue();
	}

	public void execute(String statement) {
		execute(statement, new HashMap());
	}

	public void execute(String statement, Object... values) {
		throw new RuntimeException("未实现的方法");
	}

	public void execute(final String statement, final Map paraMap) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(statement);
				if (paraMap != null) {
					query.setProperties(paraMap);
				}
				query.executeUpdate();
				return null;
			}
		});
	}

}
