package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 获奖列表
 * @author 余潜玉
 */

public class AwardGranted implements java.io.Serializable {


	private static final long serialVersionUID = 4265875049400325677L;
	private String id;//id
	private String number;//证书编号
    //@CheckSystemOptionStandard("awardGrade")
	private SystemOption grade;//获奖等级 
	private AwardApplication application;//申请奖励成果信息
	private Date date;//获奖时间
	private int year;//获奖年份
	private int session;//获奖届次
	private Integer createMode;//数据创建模式[0：系统流程创建；1：系统录入创建；2：外部导入创建]
	private Date createDate;//创建时间
	private Date updateDate;//数据更新时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@JSON(serialize=false)
	public SystemOption getGrade() {
		return grade;
	}
	public void setGrade(SystemOption grade) {
		this.grade = grade;
	}
	@JSON(serialize=false)
	public AwardApplication getApplication() {
		return application;
	}
	public void setApplication(AwardApplication application) {
		this.application = application;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getSession() {
		return session;
	}
	public void setSession(int session) {
		this.session = session;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
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