package org.csdc.controller.fm;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.csdc.bean.GridModel;
import org.csdc.bean.JsonData;
import org.csdc.controller.BaseController;
import org.csdc.model.Account;
import org.csdc.service.imp.CategoryService;
import org.csdc.service.imp.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 书签管理控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping("/fm/bookmark")
public class BookmarkController extends BaseController{
	@Autowired
	private DocumentService documentService;
	
	/**
	 * 书签列表
	 * @param request
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_BOOKMARK_VIEW')") 
	@RequestMapping(value="/list", produces="application/json")
    @ResponseBody
	public Object list(HttpServletRequest request){  
		Account account = getSessionAccount(request);
    	Map paraMap = new HashMap();
    	paraMap.put("accountId", account.getId());
    	String hql = "select b.id,b.type,b.title,b.lastModifiedDate,b.document.id,b.category.id from Bookmark b where b.account.id=:accountId order by b.lastModifiedDate desc,b.id";
    	if(request.getParameter("search")!=null &&request.getParameter("search").length()>0){
    		paraMap.put("key","%"+ request.getParameter("search").toLowerCase()+"%");
    		hql = "select b.id,b.type,b.title,b.lastModifiedDate,b.document.id,b.category.id from Bookmark b where b.account.id=:accountId and LOWER(b.title) like :key order by b.lastModifiedDate desc,b.id";
    	}
    	GridModel grid = new GridModel(request,baseDao, hql, paraMap);
    	return grid.getResults();
	}
	
	/**
	 * 为文档添加书签
	 * @param id 文档ID
	 */
	@PreAuthorize("hasRole('ROLE_FM_BOOKMARK_ADD')")
	@RequestMapping(value="/add/{id}", produces="application/json")
    @ResponseBody
	public Object addDocumentBookmark(@PathVariable String id,HttpServletRequest request){
		JsonData jsonData = new JsonData();
		String catgeoryId = request.getParameter("catgeoryId");
		if(documentService.isExistBookmark(id,catgeoryId)){
			jsonData.msg = "书签已存在";
			jsonData.success = false;
		}else{
			documentService.addDocumentBookmark(id, request);
			jsonData.success = true;
			jsonData.msg = "书签添加成功";
		}
		return jsonData;
	}
	
	/**
	 * 删除文档书签
	 * @param id 文档ID
	 */
	@PreAuthorize("hasRole('ROLE_FM_BOOKMARK_DELETE')")
	@RequestMapping(value="/delete/{id}", produces="application/json")
    @ResponseBody
	public Object deleteDocumentBookmark(@PathVariable String id){
		JsonData jsonData = new JsonData();
		documentService.deleteDocumentBookmark(id);
		return jsonData;
	}

}
