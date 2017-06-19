package csdc.bean;

import java.util.Date;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class User {

	private String id;
	private String username; // 账号
	private String chinesename; // 中文名
	private String password; // 密码
	private Date birthday; // 出生日期
	private String photofile; // 照片
	private int userstatus; // 用户状态
	private String email; // 邮箱
	private String mobilephone; // 手机
	private SystemOption gender; // 性别
	private Date registertime; // 注册时间
	private Date approvetime; // 审批启用时间
	private Date expiretime; // 有效期限
	private SystemOption ethnic;// 民族下拉框
	private String pwRetrieveCode; // 密码重置码
	private int issuperuser; // 是否超级账号

	private Set<UserRole> user_role;	//用户角色

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getChinesename() {
		return chinesename;
	}
	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhotofile() {
		return photofile;
	}
	public void setPhotofile(String photofile) {
		this.photofile = photofile;
	}
	public int getUserstatus() {
		return userstatus;
	}
	public void setUserstatus(int userstatus) {
		this.userstatus = userstatus;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public Date getRegistertime() {
		return registertime;
	}
	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}
	public Date getApprovetime() {
		return approvetime;
	}
	public void setApprovetime(Date approvetime) {
		this.approvetime = approvetime;
	}
	public Date getExpiretime() {
		return expiretime;
	}
	public void setExpiretime(Date expiretime) {
		this.expiretime = expiretime;
	}
	@JSON(serialize=false)
	public SystemOption getGender() {
		return gender;
	}
	public void setGender(SystemOption gender) {
		this.gender = gender;
	}
	@JSON(serialize=false)
	public SystemOption getEthnic() {
		return ethnic;
	}
	public void setEthnic(SystemOption ethnic) {
		this.ethnic = ethnic;
	}
	public String getPwRetrieveCode() {
		return pwRetrieveCode;
	}
	public void setPwRetrieveCode(String pwRetrieveCode) {
		this.pwRetrieveCode = pwRetrieveCode;
	}
	public int getIssuperuser() {
		return issuperuser;
	}
	public void setIssuperuser(int issuperuser) {
		this.issuperuser = issuperuser;
	}
	@JSON(serialize=false)
	public Set<UserRole> getUser_role() {
		return user_role;
	}
	public void setUser_role(Set<UserRole> user_role) {
		this.user_role = user_role;
	}
}