package org.csdc.controller.sm.docdata;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.csdc.bean.GridModel;
import org.csdc.bean.JsonData;
import org.csdc.controller.BaseController;
import org.csdc.domain.fm.AddTemplateForm;
import org.csdc.model.Template;
import org.csdc.service.imp.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 模板管理控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping ("/sm/docdata/template")
public class TemplateController extends BaseController{
	
	@Autowired 
	private TemplateService templateService;
	
	/**
	 * 模板列表的显示
	 * @param request
	 * @return
	 */
    @RequestMapping(value="/list", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_DOCDATA_TEMPLATE_VIEW')")
	public Object list(HttpServletRequest request){
    	Map paraMap = new HashMap();
    	String hql = "select a.id,a.name,a.description,a.category,a.docCount,a.lastModifuedDate from Template a order by a.name,a.id";
    	if(request.getParameter("search")!=null &&request.getParameter("search").length()>0){
    		paraMap.put("key","%"+ request.getParameter("search").toLowerCase()+"%");
    		hql = "select a.id,a.name,a.description,a.category,a.docCount,a.lastModifuedDate from Template a where LOWER(a.name) like :key or LOWER(a.description) like :key or LOWER(a.category) like :key order by a.name,a.id";
    	}
    	GridModel grid = new GridModel(request,baseDao, hql, paraMap);
    	return grid.getResults();
	}

    /**
     * 模板的删除
     * @param id 模板ID
     * @return
     */
    @RequestMapping(value ="/delete/{id}",produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_DOCDATA_TEMPLATE_DELETE')")
    public Object delete(@PathVariable String id){
    	JsonData jsonData = new JsonData();
    	if(jsonData.success = templateService.deleteTemplate(id)){
    		jsonData.msg = "当前模板正在使用不可删除";
    	}else{
    		jsonData.msg ="模板删除成功";
    	}
    	return jsonData;
    } 
    
    /**
     * 模板添加
     * @param form 模板添加表单
     * @return
     */
    @RequestMapping(value ="/add",produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_DOCDATA_TEMPLATE_ADD')")
    public Object add(AddTemplateForm form){
    	JsonData jsonData = new JsonData(true);
    	if(templateService.isExistTemplateName(form.getTemplateName()))
    		jsonData.errors.put("templateName", "用户名已存在");
		if(!jsonData.errors.isEmpty()) {
    		jsonData.success = false;
    	}else {
    		templateService.addOrModifyTemplate(form); 		
    	}
    	return jsonData;    	
    }
    
    /**
     * 模板修改
     * @param form 模板修改表单
     * @return
     */
    @RequestMapping(value ="/modify",produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_DOCDATA_TEMPLATE_MODIFY')")
    public Object modify(AddTemplateForm form){
    	JsonData jsonData = new JsonData(true);
    	Template template =baseDao.query(Template.class,form.getId());
    	if(!template.getName().equals(form.getTemplateName()) && templateService.isExistTemplateName(form.getTemplateName()))
    		jsonData.errors.put("templateName", "用户名已存在");
		if(!jsonData.errors.isEmpty()) {
    		jsonData.success = false;
    	}else {
    		templateService.addOrModifyTemplate(form); 		
    	}
    	return jsonData;  
    }

    /**
     * 进入模板修改
     * @param id
     * @return
     */
    @RequestMapping(value ="/toModify/{id}",produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_DOCDATA_TEMPLATE')")
    public Object toModify(@PathVariable String id){
    	Map jsonMap = new HashMap();    	
    	jsonMap.put("form", templateService.getTemplateForm(id));
    	jsonMap.put("success", true);
    	return jsonMap;
    }
    
    /**
     * 获取模板扩展属性列表
     * @param templateId 模板ID
     * @return
     */
    @RequestMapping(value ="/ext/{templateId}",produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_DOCDATA_TEMPLATE')")
    public Object ext(@PathVariable String templateId,HttpServletRequest request){
    	if(null != templateId){
	    	Map paraMap = new HashMap();
	    	paraMap.put("templateId", templateId);
	    	String hql = "select a.id,a.name,a.label,a.type,a.stringValue,a.mandatory from TemplateExt a  where a.template.id =:templateId order by a.name,a.id";
	    	GridModel grid = new GridModel(request,baseDao, hql, paraMap);
	    	return grid.getResults();
    	}else {
			return null;
		}
    }
}
