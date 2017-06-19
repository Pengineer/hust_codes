package csdc.bean;

import java.util.Date;
import java.util.Set;

public class Resume {
	
	private String id;
	private String name;//姓名
	private Integer gender;//性别
	private Date birthday;//生日
	private String mobilephone;//手机号码
	private String hometown;//籍贯
	private String idcardType;//证件类型
	private String idcardNumber;//证件号码
	private String photo;//上传照片路径
	private String scholarship;//奖学金
	private String award;//竞赛获奖
	private String leaderExperience;//担任领导经历
	private String note;//备注
	private String eleresume;//上传电子简历路径
	private String production;//上传作品路径
	private String otherAttachment;//其他附件路径
	private Integer type;//简历类型(0社招 1校招 2实习生招聘)
	private Account account;//帐号
	private Integer isTalent;//是否加入人才库
	private String resumeName;//简历名称
	private String eleresumeName;//上传电子简历名称
	private String otherAttachmentName;//其他邮件名称
	private String productionName;//作品名称
	private Date createDate;//创建简历时间
	private Date modifyDate;//修改简历时间

	private Set<Education> education;//教育背景
	private Set<Experience> experience;//工作经验
	
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
	public int getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getHometown() {
		return hometown;
	}
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
	public String getIdcardType() {
		return idcardType;
	}
	public void setIdcardType(String idcardType) {
		this.idcardType = idcardType;
	}
	public String getIdcardNumber() {
		return idcardNumber;
	}
	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getScholarship() {
		return scholarship;
	}
	public void setScholarship(String scholarship) {
		this.scholarship = scholarship;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
	public String getLeaderExperience() {
		return leaderExperience;
	}
	public void setLeaderExperience(String leaderExperience) {
		this.leaderExperience = leaderExperience;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getEleresume() {
		return eleresume;
	}
	public void setEleresume(String eleresume) {
		this.eleresume = eleresume;
	}
	public String getProduction() {
		return production;
	}
	public void setProduction(String production) {
		this.production = production;
	}
	public String getOtherAttachment() {
		return otherAttachment;
	}
	public void setOtherAttachment(String otherAttachment) {
		this.otherAttachment = otherAttachment;
	}
	public int getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public int getIsTalent() {
		return isTalent;
	}
	public void setIsTalent(Integer isTalent) {
		this.isTalent = isTalent;
	}
	public String getResumeName() {
		return resumeName;
	}
	public void setResumeName(String resumeName) {
		this.resumeName = resumeName;
	}
	public String getEleresumeName() {
		return eleresumeName;
	}
	public void setEleresumeName(String eleresumeName) {
		this.eleresumeName = eleresumeName;
	}
	public String getOtherAttachmentName() {
		return otherAttachmentName;
	}
	public void setOtherAttachmentName(String otherAttachmentName) {
		this.otherAttachmentName = otherAttachmentName;
	}
	public String getProductionName() {
		return productionName;
	}
	public void setProductionName(String productionName) {
		this.productionName = productionName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate =  createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Set<Education> getEducation() {
		return education;
	}
	public void setEducation(Set<Education> education) {
		this.education = education;
	}
	public Set<Experience> getExperience() {
		return experience;
	}
	public void setExperience(Set<Experience> experience) {
		this.experience = experience;
	}
	public Resume(String _ID){
		id = _ID;
	}
	public Resume(){
	}
}