package org.csdc.domain.fm;

import org.springframework.web.multipart.MultipartFile;
/**
 * 第三方上传表单
 * @author jintf
 * @date 2014-6-15
 */
public class ThirdUploadForm {
	private MultipartFile file;	
	private String fileName;
	private String title;
	private String sourceAuthor;
	private String tags;
	private String categoryPath; //文档上传的路径分类
	private String rating;
	private String categoryString; //给文档自动分类的字段
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getCategoryPath() {
		return categoryPath;
	}
	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCategoryString() {
		return categoryString;
	}
	public void setCategoryString(String categoryString) {
		this.categoryString = categoryString;
	}
	
	
}
