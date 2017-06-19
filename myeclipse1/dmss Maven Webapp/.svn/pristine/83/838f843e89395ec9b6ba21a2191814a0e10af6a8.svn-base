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
 * 邮件管理控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping(value="/sm/info/mail")
public class MailController extends BaseController{
	
	/**
	 * 邮件列表
	 * @param request
	 * @return
	 */
    @RequestMapping(value="/list", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_INFO_MAIL_VIEW')")
	public Object list(HttpServletRequest request){
    	Map paraMap = new HashMap();
    	String hql = "select a.id,ac.name,a.subject,a.send,a.sendto,a.finishedDate,a.status from Mail a left join a.account ac  order by a.finishedDate desc,a.id";
    	if(request.getParameter("search")!=null &&request.getParameter("search").length()>0){
    		paraMap.put("key","%"+ request.getParameter("search").toLowerCase()+"%");
    		hql = "select a.id,ac.name,a.subject,a.send,a.sendto,a.finishedDate,a.status from Mail a left join a.account ac where LOWER(ac.name) like :key or LOWER(a.subject) like :key or LOWER(a.send) like :key or LOWER(a.sendto) like :key order by a.finishedDate desc,a.id";
    	}
    	GridModel grid = new GridModel(request,baseDao, hql, paraMap);
    	return grid.getResults();
	}
}


