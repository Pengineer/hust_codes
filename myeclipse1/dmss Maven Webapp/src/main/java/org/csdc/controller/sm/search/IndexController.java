package org.csdc.controller.sm.search;

import org.csdc.bean.JsonData;
import org.csdc.controller.BaseController;
import org.csdc.service.imp.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 索引管理控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping("/sm/search/index")
@PreAuthorize("hasRole('ROLE_SM_SEARCH_INDEX')") 
public class IndexController extends BaseController{
	@Autowired
	private IndexService indexService;
	
	@RequestMapping(value="/update", produces="application/json")
    @ResponseBody
	public Object update(){
		JsonData jsonData = new JsonData();
		indexService.updateIndex();
		return jsonData;
	}
	
	@RequestMapping(value="/rebuild", produces="application/json")
    @ResponseBody
	public Object rebuild(){
		JsonData jsonData = new JsonData();
		indexService.rebuildAllIndex();
		return jsonData;
	}
	
	@RequestMapping(value="/drop", produces="application/json")
    @ResponseBody      
	public Object drop(){
		JsonData jsonData = new JsonData();
		indexService.deleteAllIndex();
		return jsonData;
	}
}
