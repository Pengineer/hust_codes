package csdc.bean;

import java.util.Date;

/**
 * 模板表
 * @author suwb
 *
 */
public class Template implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;//主键
	private String name;//文件名
	private String templateFile;//模板文件
	private String description;//描述
	private Date date;//上传时间
	private String dfs;//文档云存储位置

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTemplateFile() {
		return templateFile;
	}
	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDfs() {
		return dfs;
	}
	public void setDfs(String dfs) {
		this.dfs = dfs;
	}
}
