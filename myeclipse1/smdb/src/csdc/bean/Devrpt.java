package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;


/**
 * 发展报告项目
 * @author maowh
 *
 */
public class Devrpt implements java.io.Serializable {

	private static final long serialVersionUID = 8156767738097179732L;
	
	private String id;
	private String name;//项目名称
	private String applicant;//负责人
	private Integer year;//立项年度
	private String number;//项目编号
	private String type;//项目类型
	private String topic;//模块
	private String university;//学校名称
	private String universityId;//学校id
	private String guide;//对应指南
	private int isDupCheckGeneral;//是否进行一般项目重项检查：1是，0否 
	private Integer createMode;//数据创建模式[0：系统流程创建；1：系统录入创建；2：外部导入创建]
	private Date createDate;//创建时间
	private Date updateDate;//数据更新时间
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
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getUniversityId() {
		return universityId;
	}
	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}
	public String getGuide() {
		return guide;
	}
	public void setGuide(String guide) {
		this.guide = guide;
	}
	public int getIsDupCheckGeneral() {
		return isDupCheckGeneral;
	}
	public void setIsDupCheckGeneral(int isDupCheckGeneral) {
		this.isDupCheckGeneral = isDupCheckGeneral;
	}	
	public Integer getCreateMode() {
		return createMode;
	}
	public void setCreateMode(Integer createMode) {
		this.createMode = createMode;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
