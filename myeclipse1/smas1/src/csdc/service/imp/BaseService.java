// ========================================================================
// 文件名：BaseService.java
//
// 文件说明：
//	 本文件主要实现各模块service层的公共功能，包括增、删、改、计算
//
// 历史记录：
// [日期]------[姓名]--[描述]
// 2009-12-02  雷达	   创建文件。
// 2010-03-12  刘雅琴  修改文件，添加获得统计图方法。
// ========================================================================
package csdc.service.imp;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import csdc.dao.IBaseDao;
import csdc.service.IBaseService;
import csdc.tool.ApplicationContainer;

@SuppressWarnings("unchecked")
public class BaseService implements IBaseService {

	private IBaseDao baseDao;
	
	public Serializable add(Object entity) {
		return baseDao.add(entity);
	}

	public void add(Collection collection) {
		for (Object entity : collection) {
			baseDao.add(entity);
		}
	}

	public void addOrModify(Object entity) {
		baseDao.addOrModify(entity);
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

	public void addOrModify(Collection collection) {
		for (Object entity : collection) {
			baseDao.addOrModify(entity);
		}
	}

	public void delete(Object entity) {
		baseDao.delete(entity);
	}

	public void delete(Class entityClass, Serializable id) {
		baseDao.delete(entityClass, id);
	}

	/**
	 * 删除多条记录
	 * @param entityClass
	 * @param ids
	 */
	public void deleteMore(Collection collection) {
		for (Object entity : collection) {
			baseDao.delete(entity);
		}
	}
	
	/**
	 * 删除多条记录
	 * @param entityClass
	 * @param ids
	 */
	public void deleteMore(Class entityClass, Collection<String> entityIds) {
		for (String entityId : entityIds) {
			baseDao.delete(entityClass, entityId);
		}
	}
	
	public void modify(Object entity) {
		baseDao.modify(entity);
	}

	public Object query(Class entityClass, Serializable id) {
		return baseDao.query(entityClass, id);
	}

	public List query(String queryString) {
		return baseDao.query(queryString);
	}
	
	public List query(String queryString, Map paraMap) {
		return baseDao.query(queryString, paraMap);
	}

	public List query(String queryString, Object... values) {
		return baseDao.query(queryString, values);
	}

	public List query(String queryString, int firstResult, int maxResults) {
		return baseDao.query(queryString, firstResult, maxResults);
	}

	public List query(String queryString, Map paraMap, int firstResult, int maxResults) {
		return baseDao.query(queryString, paraMap, firstResult, maxResults);
	}
	
	public Object queryUnique(String queryString) {
		return baseDao.queryUnique(queryString);
	}

	public Object queryUnique(String queryString, Map paraMap) {
		return baseDao.queryUnique(queryString, paraMap);
	}

	public Object queryUnique(String queryString, Object... values) {
		return baseDao.queryUnique(queryString, values);
	}

	public long count(String hql) {
		return baseDao.count(hql);
	}

	public Long count(String hql, Map parMap) {
		return baseDao.count(hql, parMap);
	}

	public void execute(String hql) {
		baseDao.execute(hql);
	}

	public void execute(String hql, Map parMap) {
		baseDao.execute(hql, parMap);
	}
	
	public void execute(String hql, Object... values) {
		baseDao.execute(hql, values);
	}

	public IBaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	/**
	 * 处理字串，多个以英文分号与空格隔开
	 * @param originString 原始字串
	 * @return 处理后字串
	 */
	public String MutipleToFormat(String originString){
		if(null == originString || originString.trim().isEmpty()){
			return originString;
		}else{
			originString = originString.replaceAll("(\\s)+", " ");//消除多余空格
			originString = originString.replaceAll("；", ";");//统一英文分号
			originString = originString.replaceAll("(\\s)*;(\\s)*", "; ");//英文分号与空格隔开
			return originString;
		}
	}
	
	
	
	
	/**
	 * 取得专家职称（c_speciality_title）相关的集合信息:
	 * （公共方法）<br>
	 * aboveSubSeniorTitles：副高级职称以上专家<br>
	 * seniorTitles：正高级、高级职称专家<br>
	 * specialityTitle2RatingMap:职称对应的职称等级<br>
	 * 副高级:副高级的职称集合
	 * @return
	 */
	public Map selectSpecialityTitleInfo() {
		Map paraMap = new HashMap();
		/**
		 * 1.取出系统选项表中的c_name，c_code，c_description字段，组装‘c_code/c_name’与专家表中的c_speciality_title做对比
		 */
		List<Object[]> systemOptionList = (List<Object[]>) ApplicationContainer.sc.getAttribute("specialityTitleRank");
		HashMap<String, String> codeName2Description = new HashMap<String, String>();
		for (Object[] objects : systemOptionList) {
			String code = objects[0].toString();
			String name = objects[1].toString();
			String description = objects[2].toString();
			codeName2Description.put(code + "/" + name,description);
		}
		List<Object> expertSeciality = this.query("select distinct expert.specialityTitle from Expert expert where expert.specialityTitle is not null");
		List<String> seniorTitles = new ArrayList<String>();//正高级、高级职称专家
		List<String> aboveSubSeniorTitles = new ArrayList<String>();//副高级职称以上专家
		List<String> 副高级 = new ArrayList<String>();
		Map<String, Integer> specialityTitle2RatingMap = new HashMap<String, Integer>(); 
		for (Object object : expertSeciality) {
			String specialityTitle = object.toString();
			if (codeName2Description.get(specialityTitle) != null) {
				if (codeName2Description.get(specialityTitle).equals("正高级") || codeName2Description.get(specialityTitle).equals("高级")) {
					seniorTitles.add(specialityTitle);
					aboveSubSeniorTitles.add(specialityTitle);
				}
				if (codeName2Description.get(specialityTitle).equals("副高级")) {
					aboveSubSeniorTitles.add(specialityTitle);
					副高级.add(specialityTitle);
				}
				specialityTitle2RatingMap.put(specialityTitle,specialityTitle2Rating(codeName2Description.get(specialityTitle)));
			}else{
				System.out.println("没找到该专业职称对应的级别" + specialityTitle);
			}
		}
		paraMap.put("aboveSubSeniorTitles", aboveSubSeniorTitles);	//副高级职称以上专家
		paraMap.put("seniorTitles", seniorTitles);	//正高级、高级职称专家
		paraMap.put("specialityTitle2RatingMap", specialityTitle2RatingMap);
		paraMap.put("副高级", 副高级);
//		List<Object> selectedUnivCodeslList = this.query("select distinct u.code from GeneralReviewer gr ,Expert e,University u where u.code = e.universityCode and e.id = gr.reviewer.id and gr.year = '2014'");
//		List<String> selectedUnivCodes  = new ArrayList<String>();
//		for (Object object : selectedUnivCodeslList) {
//			selectedUnivCodes.add(object.toString());
//		}
//		paraMap.put("selectedUnivCodes", selectedUnivCodes);
		return paraMap;
	}
	protected Integer specialityTitle2Rating(String description){
		if (description.equals("正高级")) {
			return 6;
		}else if(description.equals("高级")) {
			return 5;
		}else if (description.equals("副高级")) {
			return 4;
		}else if (description.equals("中级")) {
			return 3;
		}else if (description.equals("助理级")) {
			return 2;
		}else{
			return 1;
		}
	}
	
}
