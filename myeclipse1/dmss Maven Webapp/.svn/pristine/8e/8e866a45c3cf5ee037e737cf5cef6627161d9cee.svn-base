package org.csdc.controller.sm.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.csdc.bean.GridModel;
import org.csdc.controller.BaseController;
import org.csdc.dao.IBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 会话管理控制器（会话即服务器端session）
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping ("/sm/security/session")
public class SessionController extends BaseController{
	@Autowired
	private IBaseDao baseDao;

	/**
	 * 会话管理列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SECURITY_SESSION_VIEW')")   
	public Object list(HttpServletRequest request){
    	Map paraMap = new HashMap();
    	String hql = "select a.id,a.sid,a.startDate,a.endDate,a.ip,ac.name,a.eventCount,a.onlineTime from Session a left join a.account ac order by a.startDate desc,a.id";
    	if(request.getParameter("search")!=null &&request.getParameter("search").length()>0){
    		paraMap.put("key","%"+ request.getParameter("search").toLowerCase()+"%");
    		hql = "select a.id,a.sid,a.startDate,a.endDate,a.ip,ac.name,a.eventCount,a.onlineTime from Session a left join a.account ac where LOWER(ac.name) like :key or LOWER(a.ip) like :key order by a.startDate desc ,a.id";
    	}
    	GridModel grid = new GridModel(request,baseDao, hql, paraMap);
    	return grid.getResults();
    }
}
