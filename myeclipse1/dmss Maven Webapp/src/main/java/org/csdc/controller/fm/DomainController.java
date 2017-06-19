package org.csdc.controller.fm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.csdc.bean.JsonData;
import org.csdc.bean.TreeNode;
import org.csdc.controller.BaseController;
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
 * 文档域管理控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping ("/fm/domain")
public class DomainController extends BaseController{
	@Autowired
	private CategoryService categoryService;
	/**
	 * 新建一个文档域
	 * @param name 域名
	 * @param description 域描述
	 */
	@RequestMapping(value="/add", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_FM_DOMAIN_ADD')") 
	public Object createDomain(@RequestParam String name,@RequestParam String description){
		JsonData jsonData = new JsonData(true);
		if(name!=null && name.isEmpty()){
			jsonData.success = false;
			jsonData.msg = "分类目录名不能为空";
		}
		if(!categoryService.isExistDomainName(name)){
			String id =categoryService.addDomain(name, description, null);				
			Category category = baseDao.query(Category.class,id);
			TreeNode node = category.convertToTreeNode();
			jsonData.data.put("node", node);
		}else{
			jsonData.success = false;
			jsonData.msg = "分类域已存在";
		}
		return jsonData;
	}
	
	/**
	 * 删除域
	 * @param id 要删除的域ID
	 * @return
	 */
	@RequestMapping(value="/delete/{id}", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_FM_DOMAIN_DELETE')")
	public Object delete(@PathVariable String id){
		categoryService.deleteCategory(id);
		return new JsonData();
	}
	
	/**
	 * 重命名域
	 * @param id 要修改的分类目录节点id
	 * @return 
	 */
	@RequestMapping(value="/rename/{id}", produces="text/html;charset=UTF-8")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_FM_DOMAIN_RENAME')")    
	public Object rename(@PathVariable String id,HttpServletRequest request,@RequestParam String name){
		JsonData jsonData = new JsonData(true);
		if(name!=null && name.isEmpty()){
			jsonData.success = false;
			jsonData.msg = "域名称不能为空";
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
			jsonData.msg = "该域名已存在";
		}	
		return jsonData;
	}
}
