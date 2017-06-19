package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;


/**
 * 高校变更
 * @author wangyi
 *
 */
public class UniversityVariation {
	
	private String id;
	private String nameOld;//原高校名称
	private String codeOld;//原高校代码
	private String nameNew;//新学校名称
	private String codeNew;//新学校代码
	private int type;//类别[0默认，1更名，2合并]
	private String description;//备注
	private Date importedDate;//导入时间
	private Date date;//高校更名时间
	
	public Date getImportedDate() {
		return importedDate;
	}
	public void setImportedDate(Date importedDate) {
		this.importedDate = importedDate;
	}
	@JSON(format="yyyy-MM-dd") 
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNameOld() {
		return nameOld;
	}
	public void setNameOld(String nameOld) {
		this.nameOld = nameOld;
	}
	public String getCodeOld() {
		return codeOld;
	}
	public void setCodeOld(String codeOld) {
		this.codeOld = codeOld;
	}
	public String getNameNew() {
		return nameNew;
	}
	public void setNameNew(String nameNew) {
		this.nameNew = nameNew;
	}
	public String getCodeNew() {
		return codeNew;
	}
	public void setCodeNew(String codeNew) {
		this.codeNew = codeNew;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}