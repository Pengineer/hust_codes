package org.csdc.service.imp;

import java.util.HashSet;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.csdc.domain.fm.AddTemplateForm;
import org.csdc.model.Template;
import org.csdc.model.TemplateExt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 模板管理业务类
 * @author jintf
 * @date 2014-6-16
 */
@Service
@Transactional
public class TemplateService extends BaseService {

	/**
	 * 删除模板
	 * @param id
	 * @return
	 */
	public boolean deleteTemplate(String id){
		Template template = baseDao.query(Template.class, id);
    	if(template.getDocuments().size()>0){
    		return false;
    	}else{
    		baseDao.delete(template);   		
    		return true;
    	}
	}
	
	/**
	 * 判断模板名是否存在
	 * @param name 模板名
	 * @return
	 */
	public boolean isExistTemplateName(String name){
		return baseDao.query("select t from Template t where t.name=?",name).size()>0;
	}
	
	/**
	 * 添加或修改模板
	 * @param form
	 */
	public void addOrModifyTemplate(AddTemplateForm form){
		Template template = null;
		if(null != form.getId() && !form.getId().isEmpty()){
			template = baseDao.query(Template.class,form.getId());
		}else{
			template = new Template();
		}
		template.setName(form.getTemplateName());		
		template.setDescription(form.getDescription());
		template.setCategory(form.getCategory());
		//更新模板属性信息
		Set<TemplateExt> exts = new HashSet<TemplateExt>();
		if(null !=template.getTemplateExts())
			baseDao.delete( template.getTemplateExts());
		if(null != form.getTemplateExts() && !form.getTemplateExts().isEmpty()){
			JSONArray array = JSONArray.fromObject(form.getTemplateExts());
			for(int i=0;i<array.size();i++){
				TemplateExt ext = (TemplateExt) JSONObject.toBean((JSONObject)array.get(i), TemplateExt.class);
				ext.setId(null);
				ext.setTemplate(template);
				exts.add(ext);			
			}	
		}
		template.setTemplateExts(exts);
		baseDao.addOrModify(template);
	}
	
	/**
	 * 返回模板表单
	 * @param id 模板ID
	 */
	public AddTemplateForm getTemplateForm(String id){
		AddTemplateForm form = new AddTemplateForm();
		Template template = baseDao.query(Template.class,id);
		form.setCategory(template.getCategory());
		form.setId(template.getId());
		form.setTemplateName(template.getName());
		form.setDescription(template.getDescription());
		return form;
	}
}
