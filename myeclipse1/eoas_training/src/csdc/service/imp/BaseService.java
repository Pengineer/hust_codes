package csdc.service.imp;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import csdc.bean.SystemOption;
import csdc.dao.IBaseDao;
import csdc.service.IBaseService;
import csdc.tool.Pager;


public class BaseService implements IBaseService {
	public IBaseDao baseDao;

	public IBaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@SuppressWarnings("unchecked")
	public void add(Object obj) {
		baseDao.add(obj);
	}
	
	public void delete(Class clazz,String id) {
		baseDao.delete(clazz, id);
	}
	
    public void modify(Object obj) {
    	baseDao.modify(obj);
    }
    @SuppressWarnings("unchecked")
	public Object load(Class clazz,String id) {
    	return baseDao.load(clazz, id);
    }
    
    @SuppressWarnings("unchecked")
	public Object load(String sqlId,Map<String,Object> params) {
    	return baseDao.load(sqlId, params);
    }
    
    public Object load(String sqlId,Object param) {
    	return baseDao.load(sqlId, param);
    }
    
    public Pager find(Class clazz,Map<String,Object> params) {
    	return baseDao.find(clazz, params);
    }
    
    public Pager find(String sqlId,Map<String,Object> params) {
    	return baseDao.find(sqlId, params);
    }
    
	public List list(Class clazz,Map<String,Object> params) {
    	return baseDao.list(clazz, params);
    }
    
    @SuppressWarnings("unchecked")
	public List list(String sqlId,Map<String,Object> params) {
    	return baseDao.list(sqlId, params);
    }
	

	
	/**
	 * 获取文件大小
	 * @param fileLength
	 * @return 文件大小字符串
	 */
	public String accquireFileSize(long fileLength) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileLength < 1024) {
			fileSizeString = df.format((double) fileLength) + "B";
		} else if (fileLength < 1048576) {
			fileSizeString = df.format((double) fileLength / 1024) + "K";
		} else if (fileLength < 1073741824) {
			fileSizeString = df.format((double) fileLength / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileLength / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 由学科(一级学科)Id获取该学科对应的学科树
	 * @param id 一级学科Id
	 */
	public Document createDisciplineXML(String id){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("utf-8");
		Element root = document.addElement("tree");
		root.addAttribute("id", "0");
		Element item0 = root.addElement("item");
		if(id !=null && id.trim().length()>0){
			SystemOption dis = this.getDisciplineById(id);
			item0.addAttribute("text",dis.getCode() + dis.getName());
			item0.addAttribute("id",dis.getId());
		}else{ //未指定根学科，便初始化为所有学科
			item0.addAttribute("text", "所有学科");
			item0.addAttribute("id", "4028d88a296cfa9501296cfb59dd0dd0"); //09年代码表根节点编号
		}
		item0.addAttribute("im0", "folder_closed.gif");
		item0.addAttribute("im1", "folder_open.gif");
		item0.addAttribute("im2", "folder_closed.gif");
		item0.addAttribute("open", "1"); //展开该节点
		item0.addAttribute("check", "0");
		newItem(item0); //根据根节点递归各子节点
		return document;
	}
	
	/**
	 * 由学科Id获取学科对象（系统选项表）
	 * @param id 学科Id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SystemOption getDisciplineById(String id){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
/*		List<SystemOption> list = null;
			list = (List<SystemOption>) baseDao.load(SystemOption.class, id);*/
		
		SystemOption systemOption = new SystemOption() ;
		try {
			systemOption =  (SystemOption) baseDao.load(SystemOption.class, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*		hql.append("from SystemOption so where so.isAvailable=1 and so.id=:id order by so.code asc ");
		map.put("id", id);
		List<SystemOption> list = baseDao.list(System.class, map);*/
		/*return (SystemOption)list.get(0);*/
		
		return systemOption;
	}
	
	/**
	 * 产生新节点
	 * @param item
	 */
	@SuppressWarnings("unchecked")
	public void newItem(Element item){ //递归方法
		String id = item.attributeValue("id");
		Map map = new HashMap();
		map.put("id", id);
		List<SystemOption> dises = null;
		try {
			dises = baseDao.list(SystemOption.class.getName() + ".listByParentId", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if( dises != null && dises.size()>0){
			for (int i=0;i<dises.size();i++){
				SystemOption dis = dises.get(i);
				Element item2 = item.addElement("item");
				item2.addAttribute("id",dis.getId());
				item2.addAttribute("text",dis.getCode() + dis.getName());
				item2.addAttribute("im0", "folder_closed.gif");
				item2.addAttribute("im1", "folder_open.gif");
				item2.addAttribute("im2", "folder_closed.gif");
				newItem(item2);
			}
		}
	}
}