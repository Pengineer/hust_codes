package org.csdc.controller.fm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.csdc.bean.GridModel;
import org.csdc.bean.JsonData;
import org.csdc.bean.TreeNode;
import org.csdc.controller.BaseController;
import org.csdc.model.Account;
import org.csdc.model.Category;
import org.csdc.service.imp.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 分类管理列表
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping ("/fm/category")
public class CategoryController extends BaseController {
	@Autowired 
	private CategoryService categoryService;
	
	/**
	 * 显示分类树
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/tree", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_FM_CATEGORY_VIEW')")
	public Object tree(HttpServletRequest request){
		return categoryService.getCategoryTree(request);
	}
	
	/**
	 * 分类目录删除
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete/{id}", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_FM_CATEGORY_DELETE')")
	public Object delete(@PathVariable String id,HttpServletRequest request){
		categoryService.deleteCategory(id);
		return "success";
	}
	
	/**
	 * 分类目录重命名
	 * @param id 要修改的分类目录节点id
	 * @param request
	 * @return 返回修改后的名字
	 */
	@RequestMapping(value="/rename/{id}", produces="text/html;charset=UTF-8")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_FM_CATEGORY_MODIFY')")    
	public Object rename(@PathVariable String id,HttpServletRequest request){
		JsonData jsonData = new JsonData(true);
		String name = (String)request.getParameter("name").trim();
		if(name!=null && name.isEmpty()){
			jsonData.success = false;
			jsonData.msg = "分类目录名不能为空";
			return jsonData;
		}
		Category category = baseDao.query(Category.class,id);
		if(!categoryService.isExistSubCategoryName(name, category.getParent().getId())){
			category.setName(name);
			baseDao.modify(category);
			TreeNode node = category.convertToTreeNode();
			jsonData.data.put("node", node);
		}else{
			jsonData.success = false;
			jsonData.msg = "分类目录名已存在";
		}	
		return jsonData;
	}
	
	/**
	 * 添加一个分类目录
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/add", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_FM_CATEGORY_ADD')")    
	public Object addSubCategory(HttpServletRequest request){
		JsonData jsonData = new JsonData(true);
		String name = (String)request.getParameter("name").trim();
		String description = (String)request.getParameter("description");
		String pid = (String)request.getParameter("pid");
		if(!categoryService.isExistSubCategoryName(name, pid)){
			String id =categoryService.addSubCategory((String)request.getParameter("name"), (String)request.getParameter("description"), (String)request.getParameter("pid"), null);				
			Category category = baseDao.query(Category.class,id);
			TreeNode node = category.convertToTreeNode();
			jsonData.data.put("node", node);
			return jsonData;
		}else{
			jsonData.success = false;
			jsonData.msg = "分类目录名已存在";
			return jsonData;
		}		
	}
	
	/**
	 * 移动分类目录
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/move/{id}", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_FM_CATEGORY_MOVE')")    
	public Object move(@PathVariable String id,HttpServletRequest request){
		Category srcCategory = baseDao.query(Category.class,id);
		Category destCategory = baseDao.query(Category.class,request.getParameter("destId"));
		Integer index = Integer.valueOf(request.getParameter("index"));
		categoryService.moveCategory(srcCategory, destCategory, index);
		return new JsonData();
	}
	
	

	/**
	 * 判断是否目的文件夹已存在
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/isExist", produces="application/json")
    @ResponseBody
	public Object isExist(@RequestParam String id, String pid){
		Category category =baseDao.query(Category.class,id);
		if(category.getParent().getId() == pid)
			return new JsonData();
		if(!categoryService.isExistSubCategoryName(id, pid))
			return new JsonData();
		return new JsonData(false);			
	}
}
