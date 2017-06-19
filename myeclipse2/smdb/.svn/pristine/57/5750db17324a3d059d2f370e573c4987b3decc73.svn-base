package csdc.bean;

import java.util.Date;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

import csdc.bean.validation.CheckSystemOptionStandard;

/**
 * type
 * 1 部级
 * 2 省级
 * 3 部属高校
 * 4 地方高校
 * @author 龚凡
 */
public class Agency implements java.io.Serializable {

	private static final long serialVersionUID = -8746856544361185546L;
	private String id;// ID
	private String name;// 名称
	private String englishName;// 英文名称
	private String abbr;// 缩写
	private String code;// 代码
	private int type;//管理机构类型[1部级，2省级，3部属高校，4地方高校]
//@CheckSystemOptionStandard("gbt2260-2007")
	private SystemOption province;// 所在省
//@CheckSystemOptionStandard("gbt2260-2007")
	private SystemOption city;// 所在市
	private Agency subjection;// 上级管理部门
	private Person director;// 负责人
	private String introduction;//简介
	private String address;// 地址
	private String postcode;// 邮编
	private String phone;// 电话
	private String fax;// 传真
	private String email;// 邮箱
	private String homepage;// 主页
	private String sname;// 社科管理部门名称
	private Person sdirector;// 社科管理部门负责人
	private Person slinkman;// 社科管理部门联系人
	private String saddress;// 社科管理部门地址
	private String spostcode;// 社科管理部门邮编
	private String sphone;// 社科管理部门电话
	private String sfax;// 社科管理部门传真
	private String semail;// 社科管理部门邮箱
	private String shomepage;// 社科管理部门主页
	private String fbankAccount;// 银行账号
	private String fcupNumber;// 银联行号
	private String fbank;// 开户银行
	private String fbankBranch;// 银行支行
	private String fbankAccountName;// 开户名称
	private String fname;// 财务管理部门名称
	private String fdirector;// 财务管理部门负责人姓名
	private String flinkman;// 财务管理部门联系人姓名
	private String faddress;// 财务管理部门地址
	private String fpostcode;// 财务管理部门邮编
	private String fphone;// 财务管理部门电话
	private String ffax;// 财务管理部门传真
	private String femail;// 财务管理部门邮箱
	private String style;// 高校办学类型
	private String category;// 高校性质类别
	private String organizer;// 高校举办者
	private String reviewLevel;//参评优先级
	private Double reviewScore;//参评评分
	private String acronym;//管理机构首字母缩写
	private String standard;//管理机构首字母缩写
	private Date importedDate;//导入时间

	private Set<RoleAgency> roleAgency;
	private Set<Officer> officer;
	private Set<Teacher> teacher;
	private Set<Student> student;
	private Set<Institute> institute;
	private Set<Department> department;
	private Set<Agency> agency;
	private Set<Discipline> discipline;
	private Set<Doctoral> doctoral;
	private Set<AwardApplication> awardApplicationForUniversity;
	private Set<AwardApplication> awardApplicationForUniversityAuditorAgency;
	private Set<AwardApplication> awardApplicationForProvinceAuditorAgency;
	private Set<AwardApplication> awardApplicationForMinistryAuditorAgency;
	private Set<AwardApplication> awardApplicationForReviewerAgency;
	private Set<AwardApplication> awardApplicationForReviewAuditorAgency;
	private Set<AwardApplication> awardApplicationForFinalAuditorAgency;
	private Set<AwardReview> awardReviewForUniversity;
	private Set<Product> productForUniversity;
	private Set<Product> productForAuditorAgency;
	private Set<ProjectData> projectData;
	
	private Set<ProjectApplication> projectApplicationForUniversity;
	private Set<ProjectGranted> projectGrantedForUniversity;
	private Set<ProjectMember> projectMember;
	private Set<ProjectEndinspectionReview> projectEndinspectionReview;
	
	private Set<ProjectVariation> projectVariationForOldAgency;
	private Set<ProjectVariation> projectVariationForNewAgency;
	
	private Set<ProjectApplication> projectApplicationForUniversityAuditorAgency;
	private Set<ProjectApplication> projectApplicationForProvinceAuditorAgency;
	private Set<ProjectApplication> projectApplicationForMinistryAuditorAgency;
	private Set<ProjectApplication> projectApplicationForReviewerAgency;
	private Set<ProjectApplication> projectApplicationForReviewAuditorAgency;
	private Set<ProjectApplication> projectApplicationForFinalAuditorAgency;
	
	private Set<ProjectMidinspection> projectMidinspectionForUniversityAuditorAgency;
	private Set<ProjectMidinspection> projectMidinspectionForProvinceAuditorAgency;
	private Set<ProjectMidinspection> projectMidinspectionForFinalAuditorAgency;
	
	private Set<ProjectEndinspection> projectEndinspectionForUniversityAuditorAgency;
	private Set<ProjectEndinspection> projectEndinspectionForProvinceAuditorAgency;
	private Set<ProjectEndinspection> projectEndinspectionForMinistryAuditorAgency;
	private Set<ProjectEndinspection> projectEndinspectionForReviewerAgency;
	private Set<ProjectEndinspection> projectEndinspectionForFinalAuditorAgency;
	
	private Set<ProjectVariation> projectVariationForUniversityAuditorAgency;
	private Set<ProjectVariation> projectVariationForProvinceAuditorAgency;
	private Set<ProjectVariation> projectVariationForFinalAuditorAgency;


	public Agency(){}

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
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@JSON(serialize=false)
	public SystemOption getProvince() {
		return province;
	}
	public void setProvince(SystemOption province) {
		this.province = province;
	}
	
	public Date getImportedDate() {
		return importedDate;
	}

	public void setImportedDate(Date importedDate) {
		this.importedDate = importedDate;
	}

	@JSON(serialize=false)
	public SystemOption getCity() {
		return city;
	}
	public void setCity(SystemOption city) {
		this.city = city;
	}
	@JSON(serialize=false)
	public Agency getSubjection() {
		return subjection;
	}
	public void setSubjection(Agency subjection) {
		this.subjection = subjection;
	}
	@JSON(serialize=false)
	public Person getDirector() {
		return director;
	}
	public void setDirector(Person director) {
		this.director = director;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	@JSON(serialize=false)
	public Person getSdirector() {
		return sdirector;
	}
	public void setSdirector(Person sdirector) {
		this.sdirector = sdirector;
	}
	@JSON(serialize=false)
	public Person getSlinkman() {
		return slinkman;
	}
	public void setSlinkman(Person slinkman) {
		this.slinkman = slinkman;
	}
	public String getSaddress() {
		return saddress;
	}
	public void setSaddress(String saddress) {
		this.saddress = saddress;
	}
	public String getSpostcode() {
		return spostcode;
	}
	public void setSpostcode(String spostcode) {
		this.spostcode = spostcode;
	}
	public String getSphone() {
		return sphone;
	}
	public void setSphone(String sphone) {
		this.sphone = sphone;
	}
	public String getSfax() {
		return sfax;
	}
	public void setSfax(String sfax) {
		this.sfax = sfax;
	}
	public String getSemail() {
		return semail;
	}
	public void setSemail(String semail) {
		this.semail = semail;
	}
	public String getShomepage() {
		return shomepage;
	}
	public void setShomepage(String shomepage) {
		this.shomepage = shomepage;
	}


	public String getFbankAccount() {
		return fbankAccount;
	}

	public void setFbankAccount(String fbankAccount) {
		this.fbankAccount = fbankAccount;
	}

	public String getFcupNumber() {
		return fcupNumber;
	}

	public void setFcupNumber(String fcupNumber) {
		this.fcupNumber = fcupNumber;
	}

	public String getFbank() {
		return fbank;
	}

	public void setFbank(String fbank) {
		this.fbank = fbank;
	}

	public String getFbankBranch() {
		return fbankBranch;
	}

	public void setFbankBranch(String fbankBranch) {
		this.fbankBranch = fbankBranch;
	}

	public String getFbankAccountName() {
		return fbankAccountName;
	}

	public void setFbankAccountName(String fbankAccountName) {
		this.fbankAccountName = fbankAccountName;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setFdirector(String fdirector) {
		this.fdirector = fdirector;
	}

	public void setFlinkman(String flinkman) {
		this.flinkman = flinkman;
	}

	public String getFlinkman() {
		return flinkman;
	}

	public String getFdirector() {
		return fdirector;
	}

	public String getFaddress() {
		return faddress;
	}

	public void setFaddress(String faddress) {
		this.faddress = faddress;
	}

	public String getFpostcode() {
		return fpostcode;
	}

	public void setFpostcode(String fpostcode) {
		this.fpostcode = fpostcode;
	}

	public String getFphone() {
		return fphone;
	}

	public void setFphone(String fphone) {
		this.fphone = fphone;
	}

	public String getFfax() {
		return ffax;
	}

	public void setFfax(String ffax) {
		this.ffax = ffax;
	}

	public String getFemail() {
		return femail;
	}

	public void setFemail(String femail) {
		this.femail = femail;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOrganizer() {
		return organizer;
	}

	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	
	public String getReviewLevel() {
		return reviewLevel;
	}
	
	public void setReviewLevel(String reviewLevel) {
		this.reviewLevel = reviewLevel;
	}
	
	public Double getReviewScore() {
		return reviewScore;
	}
	
	public void setReviewScore(Double reviewScore) {
		this.reviewScore = reviewScore;
	}
	
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String getAcronym() {
		return acronym;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	@JSON(serialize=false)
	public Set<RoleAgency> getRoleAgency() {
		return roleAgency;
	}
	public void setRoleAgency(Set<RoleAgency> roleAgency) {
		this.roleAgency = roleAgency;
	}
	@JSON(serialize=false)
	public Set<Officer> getOfficer() {
		return officer;
	}
	public void setOfficer(Set<Officer> officer) {
		this.officer = officer;
	}
	@JSON(serialize=false)
	public Set<Teacher> getTeacher() {
		return teacher;
	}
	public void setTeacher(Set<Teacher> teacher) {
		this.teacher = teacher;
	}
	@JSON(serialize=false)
	public Set<Student> getStudent() {
		return student;
	}
	public void setStudent(Set<Student> student) {
		this.student = student;
	}
	@JSON(serialize=false)
	public Set<Institute> getInstitute() {
		return institute;
	}

	public void setInstitute(Set<Institute> institute) {
		this.institute = institute;
	}
	@JSON(serialize=false)
	public Set<Department> getDepartment() {
		return department;
	}
	public void setDepartment(Set<Department> department) {
		this.department = department;
	}
	@JSON(serialize=false)
	public Set<Agency> getAgency() {
		return agency;
	}
	public void setAgency(Set<Agency> agency) {
		this.agency = agency;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForUniversity() {
		return awardApplicationForUniversity;
	}
	public void setAwardApplicationForUniversity(Set<AwardApplication> awardApplicationForUniversity) {
		this.awardApplicationForUniversity = awardApplicationForUniversity;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForUniversityAuditorAgency() {
		return awardApplicationForUniversityAuditorAgency;
	}
	public void setAwardApplicationForUniversityAuditorAgency(
			Set<AwardApplication> awardApplicationForUniversityAuditorAgency) {
		this.awardApplicationForUniversityAuditorAgency = awardApplicationForUniversityAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<Discipline> getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Set<Discipline> discipline) {
		this.discipline = discipline;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForProvinceAuditorAgency() {
		return awardApplicationForProvinceAuditorAgency;
	}

	public void setAwardApplicationForProvinceAuditorAgency(
			Set<AwardApplication> awardApplicationForProvinceAuditorAgency) {
		this.awardApplicationForProvinceAuditorAgency = awardApplicationForProvinceAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForMinistryAuditorAgency() {
		return awardApplicationForMinistryAuditorAgency;
	}

	public void setAwardApplicationForMinistryAuditorAgency(
			Set<AwardApplication> awardApplicationForMinistryAuditorAgency) {
		this.awardApplicationForMinistryAuditorAgency = awardApplicationForMinistryAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForReviewerAgency() {
		return awardApplicationForReviewerAgency;
	}

	public void setAwardApplicationForReviewerAgency(
			Set<AwardApplication> awardApplicationForReviewerAgency) {
		this.awardApplicationForReviewerAgency = awardApplicationForReviewerAgency;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForReviewAuditorAgency() {
		return awardApplicationForReviewAuditorAgency;
	}

	public void setAwardApplicationForReviewAuditorAgency(
			Set<AwardApplication> awardApplicationForReviewAuditorAgency) {
		this.awardApplicationForReviewAuditorAgency = awardApplicationForReviewAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForFinalAuditorAgency() {
		return awardApplicationForFinalAuditorAgency;
	}

	public void setAwardApplicationForFinalAuditorAgency(
			Set<AwardApplication> awardApplicationForFinalAuditorAgency) {
		this.awardApplicationForFinalAuditorAgency = awardApplicationForFinalAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<AwardReview> getAwardReviewForUniversity() {
		return awardReviewForUniversity;
	}

	public void setAwardReviewForUniversity(
			Set<AwardReview> awardReviewForUniversity) {
		this.awardReviewForUniversity = awardReviewForUniversity;
	}
	@JSON(serialize=false)
	public Set<Doctoral> getDoctoral() {
		return doctoral;
	}

	public void setDoctoral(Set<Doctoral> doctoral) {
		this.doctoral = doctoral;
	}
	@JSON(serialize=false)
	public Set<Product> getProductForUniversity() {
		return productForUniversity;
	}

	public void setProductForUniversity(Set<Product> productForUniversity) {
		this.productForUniversity = productForUniversity;
	}
	@JSON(serialize=false)
	public Set<Product> getProductForAuditorAgency() {
		return productForAuditorAgency;
	}

	public void setProductForAuditorAgency(Set<Product> productForAuditorAgency) {
		this.productForAuditorAgency = productForAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectData> getProjectData() {
		return projectData;
	}

	public void setProjectData(Set<ProjectData> projectData) {
		this.projectData = projectData;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForUniversity() {
		return projectApplicationForUniversity;
	}

	public void setProjectApplicationForUniversity(
			Set<ProjectApplication> projectApplicationForUniversity) {
		this.projectApplicationForUniversity = projectApplicationForUniversity;
	}
	@JSON(serialize=false)
	public Set<ProjectGranted> getProjectGrantedForUniversity() {
		return projectGrantedForUniversity;
	}

	public void setProjectGrantedForUniversity(
			Set<ProjectGranted> projectGrantedForUniversity) {
		this.projectGrantedForUniversity = projectGrantedForUniversity;
	}
	@JSON(serialize=false)
	public Set<ProjectMember> getProjectMember() {
		return projectMember;
	}

	public void setProjectMember(Set<ProjectMember> projectMember) {
		this.projectMember = projectMember;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspectionReview> getProjectEndinspectionReview() {
		return projectEndinspectionReview;
	}

	public void setProjectEndinspectionReview(
			Set<ProjectEndinspectionReview> projectEndinspectionReview) {
		this.projectEndinspectionReview = projectEndinspectionReview;
	}
	@JSON(serialize=false)
	public Set<ProjectVariation> getProjectVariationForOldAgency() {
		return projectVariationForOldAgency;
	}

	public void setProjectVariationForOldAgency(
			Set<ProjectVariation> projectVariationForOldAgency) {
		this.projectVariationForOldAgency = projectVariationForOldAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectVariation> getProjectVariationForNewAgency() {
		return projectVariationForNewAgency;
	}

	public void setProjectVariationForNewAgency(
			Set<ProjectVariation> projectVariationForNewAgency) {
		this.projectVariationForNewAgency = projectVariationForNewAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForUniversityAuditorAgency() {
		return projectApplicationForUniversityAuditorAgency;
	}

	public void setProjectApplicationForUniversityAuditorAgency(
			Set<ProjectApplication> projectApplicationForUniversityAuditorAgency) {
		this.projectApplicationForUniversityAuditorAgency = projectApplicationForUniversityAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForProvinceAuditorAgency() {
		return projectApplicationForProvinceAuditorAgency;
	}

	public void setProjectApplicationForProvinceAuditorAgency(
			Set<ProjectApplication> projectApplicationForProvinceAuditorAgency) {
		this.projectApplicationForProvinceAuditorAgency = projectApplicationForProvinceAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForMinistryAuditorAgency() {
		return projectApplicationForMinistryAuditorAgency;
	}

	public void setProjectApplicationForMinistryAuditorAgency(
			Set<ProjectApplication> projectApplicationForMinistryAuditorAgency) {
		this.projectApplicationForMinistryAuditorAgency = projectApplicationForMinistryAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForReviewerAgency() {
		return projectApplicationForReviewerAgency;
	}

	public void setProjectApplicationForReviewerAgency(
			Set<ProjectApplication> projectApplicationForReviewerAgency) {
		this.projectApplicationForReviewerAgency = projectApplicationForReviewerAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForReviewAuditorAgency() {
		return projectApplicationForReviewAuditorAgency;
	}

	public void setProjectApplicationForReviewAuditorAgency(
			Set<ProjectApplication> projectApplicationForReviewAuditorAgency) {
		this.projectApplicationForReviewAuditorAgency = projectApplicationForReviewAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForFinalAuditorAgency() {
		return projectApplicationForFinalAuditorAgency;
	}

	public void setProjectApplicationForFinalAuditorAgency(
			Set<ProjectApplication> projectApplicationForFinalAuditorAgency) {
		this.projectApplicationForFinalAuditorAgency = projectApplicationForFinalAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectMidinspection> getProjectMidinspectionForUniversityAuditorAgency() {
		return projectMidinspectionForUniversityAuditorAgency;
	}

	public void setProjectMidinspectionForUniversityAuditorAgency(
			Set<ProjectMidinspection> projectMidinspectionForUniversityAuditorAgency) {
		this.projectMidinspectionForUniversityAuditorAgency = projectMidinspectionForUniversityAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectMidinspection> getProjectMidinspectionForProvinceAuditorAgency() {
		return projectMidinspectionForProvinceAuditorAgency;
	}

	public void setProjectMidinspectionForProvinceAuditorAgency(
			Set<ProjectMidinspection> projectMidinspectionForProvinceAuditorAgency) {
		this.projectMidinspectionForProvinceAuditorAgency = projectMidinspectionForProvinceAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectMidinspection> getProjectMidinspectionForFinalAuditorAgency() {
		return projectMidinspectionForFinalAuditorAgency;
	}

	public void setProjectMidinspectionForFinalAuditorAgency(
			Set<ProjectMidinspection> projectMidinspectionForFinalAuditorAgency) {
		this.projectMidinspectionForFinalAuditorAgency = projectMidinspectionForFinalAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspection> getProjectEndinspectionForUniversityAuditorAgency() {
		return projectEndinspectionForUniversityAuditorAgency;
	}

	public void setProjectEndinspectionForUniversityAuditorAgency(
			Set<ProjectEndinspection> projectEndinspectionForUniversityAuditorAgency) {
		this.projectEndinspectionForUniversityAuditorAgency = projectEndinspectionForUniversityAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspection> getProjectEndinspectionForProvinceAuditorAgency() {
		return projectEndinspectionForProvinceAuditorAgency;
	}

	public void setProjectEndinspectionForProvinceAuditorAgency(
			Set<ProjectEndinspection> projectEndinspectionForProvinceAuditorAgency) {
		this.projectEndinspectionForProvinceAuditorAgency = projectEndinspectionForProvinceAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspection> getProjectEndinspectionForMinistryAuditorAgency() {
		return projectEndinspectionForMinistryAuditorAgency;
	}

	public void setProjectEndinspectionForMinistryAuditorAgency(
			Set<ProjectEndinspection> projectEndinspectionForMinistryAuditorAgency) {
		this.projectEndinspectionForMinistryAuditorAgency = projectEndinspectionForMinistryAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspection> getProjectEndinspectionForReviewerAgency() {
		return projectEndinspectionForReviewerAgency;
	}

	public void setProjectEndinspectionForReviewerAgency(
			Set<ProjectEndinspection> projectEndinspectionForReviewerAgency) {
		this.projectEndinspectionForReviewerAgency = projectEndinspectionForReviewerAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspection> getProjectEndinspectionForFinalAuditorAgency() {
		return projectEndinspectionForFinalAuditorAgency;
	}

	public void setProjectEndinspectionForFinalAuditorAgency(
			Set<ProjectEndinspection> projectEndinspectionForFinalAuditorAgency) {
		this.projectEndinspectionForFinalAuditorAgency = projectEndinspectionForFinalAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectVariation> getProjectVariationForUniversityAuditorAgency() {
		return projectVariationForUniversityAuditorAgency;
	}

	public void setProjectVariationForUniversityAuditorAgency(
			Set<ProjectVariation> projectVariationForUniversityAuditorAgency) {
		this.projectVariationForUniversityAuditorAgency = projectVariationForUniversityAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectVariation> getProjectVariationForProvinceAuditorAgency() {
		return projectVariationForProvinceAuditorAgency;
	}

	public void setProjectVariationForProvinceAuditorAgency(
			Set<ProjectVariation> projectVariationForProvinceAuditorAgency) {
		this.projectVariationForProvinceAuditorAgency = projectVariationForProvinceAuditorAgency;
	}
	@JSON(serialize=false)
	public Set<ProjectVariation> getProjectVariationForFinalAuditorAgency() {
		return projectVariationForFinalAuditorAgency;
	}

	public void setProjectVariationForFinalAuditorAgency(
			Set<ProjectVariation> projectVariationForFinalAuditorAgency) {
		this.projectVariationForFinalAuditorAgency = projectVariationForFinalAuditorAgency;
	}
}