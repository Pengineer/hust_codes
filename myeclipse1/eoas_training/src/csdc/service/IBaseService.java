package csdc.service;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;

import csdc.tool.Pager;


public interface IBaseService {
	public void add(Object obj);
	public void delete(Class clazz,String id);
    public void modify(Object obj);
    public Object load(Class clazz,String id);
    public Object load(String sqlId,Map<String,Object> params);
    public Object load(String sqlId,Object param);
    public Pager find(Class clazz,Map<String,Object> params);
    public Pager find(String sqlId,Map<String,Object> params);
    public List list(Class clazz,Map<String,Object> params);
    public List list(String sqlId,Map<String,Object> params);
	
    public Document createDisciplineXML(String id);
	public String accquireFileSize(long fileLength);
}