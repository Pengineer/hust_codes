package edu.hust.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Transactional
public class HibernateDao {
	
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public String add(Object entity) {
		if (entity instanceof Collection) {
			for (Object element : (Collection) entity) {
				add(element);
			}
			return null;
		} else if (entity instanceof Object[]) {
			for (Object element : (Object[]) entity) {
				add(element);
			}
			return null;
		}
		return (String) getSession().save(entity);
	}
	
	public void delete(Object entity) {
		if (entity instanceof Collection) {
			for (Object element : (Collection) entity) {
				delete(element);
			}
		} else if (entity instanceof Object[]) {
			for (Object element : (Object[]) entity) {
				delete(element);
			}
		} else {
			Object persistentEntity = getSession().merge(entity);
			getSession().delete(persistentEntity);
		}
	}
	
	public void modify(Object entity) {
		if (entity instanceof Collection) {
			for (Object element : (Collection) entity) {
				modify(element);
			}
		} else if (entity instanceof Object[]) {
			for (Object element : (Object[]) entity) {
				modify(element);
			}
		} else {
			getSession().merge(entity);
		}
	}
	
	public <T> T query(Class<T> entityClass, Serializable id) {
		return (T) getSession().get(entityClass, id);
	}

	public List query(String queryString) {
		return query(queryString, null, null, null);
	}

	public List query(String queryString, Map paraMap) {
		return query(queryString, paraMap, null, null);
	}

	public List query(final String queryString, final Object... values) {
		Query query = getSession().createQuery(queryString);
		
		for (int position = 0; position < values.length; position++) {
			query.setParameter(position, values[position]);
		}
		return query.list();
	}

	public List query(String queryString, Integer firstResult, Integer maxResults) {
		return query(queryString, null, firstResult, maxResults);
	}

	public List query(final String queryString, final Map paraMap, final Integer firstResult, final Integer maxResults) {
		Query query = getSession().createQuery(queryString);
		
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

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
