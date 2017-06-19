package org.csdc.controller.fm;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.charfilter.BaseCharFilter;
import org.csdc.bean.GridModel;
import org.csdc.bean.JsonData;
import org.csdc.controller.BaseController;
import org.csdc.domain.sm.security.AccountForm;
import org.csdc.service.imp.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 回收站管理控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping ("/fm/recycle")
public class RecycleController extends BaseController {
	@Autowired
	private DocumentService documentService;
	
	/**
	 * 显示回收站列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_FM_RECYCLE_VIEW')")   
	public Object list(HttpServletRequest request){    	
    	Map paraMap = new HashMap();
    	String hql = "select d.id,d.type,d.title,d.lastModifiedDate from Document d where d.deleted = true order by d.lastModifiedDate desc,d.id";
    	if(request.getParameter("search")!=null &&request.getParameter("search").length()>0){
    		paraMap.put("key","%"+ request.getParameter("search").toLowerCase()+"%");
    		hql = "select d.id,d.type,d.title,d.lastModifiedDate from Document d where d.deleted = true and (LOWER(d.title) like :key) order by d.lastModifiedDate desc,d.id";
    	}
    	GridModel grid = new GridModel(request,baseDao, hql, paraMap);
    	return grid.getResults();
	}
	
	
	/**
	 * 删除回收站中的文档
	 * @param id 文档ID
	 * @return
	 */
	@RequestMapping(value="/delete/{id}", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_FM_RECYCLE_DELETE')")    
    public Object delete(@PathVariable String  id){
		JsonData jsonData = new JsonData();  
		try{
			documentService.deleteRecycle(id);
		}catch (Exception e) {
			jsonData.success = false;
		}
    	return jsonData;
    }
	
	
	/**
	 * 清空回收站（文档不可恢复）
	 * @return
	 */
	@RequestMapping(value="/deleteAll", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_FM_RECYCLE_EMPTY')")   
    public Object deleteAll(){
		JsonData jsonData = new JsonData();  
		try{
			documentService.deleteRecycleAll();
		}catch (Exception e) {
			jsonData.success = false;
		}
    	return jsonData;
    }
	
	/**
	 * 还原回收站中的文档
	 * @param id 要还原的文档ID
	 * @return
	 */
	@RequestMapping(value="/restore/{id}", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_FM_RECYCLE_RESTORE')")      
    public Object restore(@PathVariable String  id){
		JsonData jsonData = new JsonData(); 
		documentService.restoreRecycle(id);	
    	return jsonData;
    }
}
