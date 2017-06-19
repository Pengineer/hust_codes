package csdc.dao;

import java.util.List;
import java.util.Map;

import csdc.tool.Pager;

public interface IBaseDao<T> {
	public void add(T obj);
	public void delete(Class<T> clazz,String id);
    public void modify(T obj);
    public T load(Class<T> clazz,String id);
    public T load(String sqlId,Map<String,Object> params);
    public T load(String sqlId,Object param);
    public Pager<T> find(Class<T> clazz,Map<String,Object> params);
    public Pager<T> find(String sqlId,Map<String,Object> params);
    public List<T> list(Class<T> clazz,Map<String,Object> params);
    public List<T> list(String sqlId,Map<String,Object> params);
}