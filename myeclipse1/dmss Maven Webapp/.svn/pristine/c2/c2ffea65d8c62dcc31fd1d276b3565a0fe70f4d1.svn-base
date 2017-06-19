package org.csdc.controller.sm.info;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.csdc.bean.DataTable;
import org.csdc.bean.GridModel;
import org.csdc.controller.BaseController;
import org.csdc.dao.IBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 站内信管理
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping("/sm/info/message/")
public class MessageController extends BaseController{
	
	/**
	 * 站内信列表
	 * @param request
	 * @return
	 */
    @RequestMapping(value="/list", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_INFO_MESSAGE_LIST')")    
	public Object list(HttpServletRequest request){
    	Map paraMap = new HashMap();
    	String hql = "select a.id,ac.name,a.title,a.content,a.createdDate,a.isOpen from Message a left join a.account ac  order by a.createdDate desc,a.id";
    	if(request.getParameter("search")!=null &&request.getParameter("search").length()>0){
    		paraMap.put("key","%"+ request.getParameter("search").toLowerCase()+"%");
    		hql = "select a.id,ac.name,a.title,a.content,a.createdDate,a.isOpenfrom Message a left join a.account ac where LOWER(ac.name) like :key or LOWER(a.title) like :key or LOWER(a.content) like :key order by a.createdDate desc,a.id";
    	}
    	GridModel grid = new GridModel(request,baseDao, hql, paraMap);
    	return grid.getResults();
	}
}


