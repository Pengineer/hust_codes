package org.csdc.domain.fm;
/**
 * 文档上传表单
 * @author jintf
 * @date 2014-6-15
 */
public class DocUplaodForm {
	
	private String title;
	private String templateId;
	private String sourceAuthor;
	private String tags;
	private String categoryId;
	private String categoryPath;
	private String rating;
	private String autoUnZip;

	public String getTemplateId() {
		return templateId;
	}
	
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getSourceAuthor() {
		return sourceAuthor;
	}
	public void setSourceAuthor(String sourceAuthor) {
		this.sourceAuthor = sourceAuthor;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getAutoUnZip() {
		return autoUnZip;
	}
	public void setAutoUnZip(String autoUnZip) {
		this.autoUnZip = autoUnZip;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategoryPath() {
		return categoryPath;
	}
	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	
	
}
