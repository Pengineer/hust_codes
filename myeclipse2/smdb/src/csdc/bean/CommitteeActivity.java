package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class CommitteeActivity implements java.io.Serializable {

	private static final long serialVersionUID = -4429848600944706128L;
	private String id;// ID
	private Institute institute;// 所属研究机构
	private Date date;// 时间
	private int population;// 人员
	private String title;// 主题
	private String content;// 内容

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public Institute getInstitute() {
		return institute;
	}
	public void setInstitute(Institute institute) {
		this.institute = institute;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}