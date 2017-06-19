package csdc.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class SqlBaseDao implements IBaseDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public String add(Object entity) {
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
		//difference from HibernateBaseDao
		SQLQuery query = getSession().createSQLQuery(queryString);
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
	
	public Object queryUnique(String queryString) {
		return queryUnique(queryString, new HashMap());
	}

	public Object queryUnique(final String queryString, final Map paraMap) {
		//difference from HibernateBaseDao
		SQLQuery query = getSession().createSQLQuery(queryString);
		if (paraMap != null) {
			query.setProperties(paraMap);
		}
		return query.uniqueResult();
	}

	public Object queryUnique(final String queryString, final Object... values) {
		SQLQuery query = getSession().createSQLQuery(queryString);
		for (int position = 0; position < values.length; position++) {
			query.setParameter(position, values[position]);
		}
		return query.uniqueResult();
	}
	
	public long count(String queryString) {
		return count(queryString, Collections.emptyMap());
	}
	
	public long count(String queryString, Map paraMap) {
		String countQueryString = "select count(*) from (" + queryString + ")";
		BigDecimal cnt = (BigDecimal) queryUnique(countQueryString, paraMap);
		return cnt.longValue();
	}
	
	public long count(String queryString, Object... values) {
		throw new RuntimeException("未实现的方法");
	}

	public void execute(String statement) {
		execute(statement, new HashMap());
	}

	public void execute(final String statement, final Object... values) {
		SQLQuery query = getSession().createSQLQuery(statement);
		for (int position = 0; position < values.length; position++) {
			query.setParameter(position, values[position]);
		}
		query.executeUpdate();
	}

	public void execute(final String statement, final Map paraMap) {
		SQLQuery query = getSession().createSQLQuery(statement);
		if (paraMap != null) {
			query.setProperties(paraMap);
		}
		query.executeUpdate();
	}

}
