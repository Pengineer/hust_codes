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
	private int isImported;//是否导入数据：1是，0否
	
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
	public int getIsImported() {
		return isImported;
	}
	public void setIsImported(int isImported) {
		this.isImported = isImported;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}