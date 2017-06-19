package csdc.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface IBaseDao {
	public Serializable add(Object entity);
	@SuppressWarnings("unchecked")
	public void add(Collection entity);
	public void delete(Object entity);
	@SuppressWarnings("unchecked")
	public void delete(Class entityClass, Serializable id);
	public void update(String hql);
	public void modify(Object entity);
	@SuppressWarnings("unchecked")
	public void modify(Collection entity);
	@SuppressWarnings("unchecked")
	public void modify(Class entityClass, Serializable id);
	@SuppressWarnings("unchecked")
	public List query(String hql);
	@SuppressWarnings("unchecked")
	public List query(String queryString, int FirstResult, int MaxResult);
	@SuppressWarnings("unchecked")
	public Object query(Class entityClass, Serializable id);
//	public void add1(Object entity);
}
