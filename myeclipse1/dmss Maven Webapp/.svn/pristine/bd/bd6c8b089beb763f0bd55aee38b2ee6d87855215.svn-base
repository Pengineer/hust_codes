package org.csdc.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.csdc.dao.IBaseDao;
/**
 * ExtJS表格模型
 * @author jintf
 * @date 2014-6-15
 */
public class GridModel {	
	private IBaseDao baseDao;
	private String hql;
	private int start; //起始记录
	private int limit; //返回记录数记录数
	private Map paraMap; // 查询字符串
	
	public GridModel(HttpServletRequest request,IBaseDao baseDao,String hql,Map paraMap){
		this.baseDao = baseDao;
		this.hql = hql;
		start =  Integer.valueOf(request.getParameter("start"));
		limit = Integer.valueOf(request.getParameter("limit"));
		this.paraMap = paraMap;
	}
	
	public Map getResults(){
		Map result = new HashMap();
		result.put("data", baseDao.query(hql,paraMap, start, limit));
		result.put("totalCount", baseDao.count(hql,paraMap));
		return result;
	}

}
