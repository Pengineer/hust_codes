package org.csdc.domain.fm;

/**
 * 模板添加表单
 * @author jintf
 * @date 2014-6-15
 */
public class AddTemplateForm {
	private String id;
    private String templateName;
    private String description;
    private String category;
    private String templateExts;
	
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTemplateExts() {
		return templateExts;
	}
	public void setTemplateExts(String templateExts) {
		this.templateExts = templateExts;
	}
	

}
