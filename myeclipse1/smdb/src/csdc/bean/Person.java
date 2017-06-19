package csdc.bean;

import java.util.Date;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class Person implements java.io.Serializable {

	private static final long serialVersionUID = 5725221761243288610L;
	private String id;// ID
	private String name;// 姓名
	private String englishName;// 英文名
	private String usedName;// 曾用名
	private String photoFile;// 照片
	private String idcardType;// 证件类型
	private String idcardNumber;// 证件号
	private String gender;// 性别
	private String countryRegion;// 国家或地区
	private String ethnic;// 民族
	private String birthplace;// 籍贯（省、市、自治区）
	private Date birthday;// 生日
	private String membership;// 政治面貌
	private String homePhone;// 住宅电话
	private String homeFax;// 住宅传真
	private String officePhone;// 办公电话
	private String officeFax;// 办公传真
	private String email;// 邮箱
	private String mobilePhone;// 电话
	private String qq;
	private String msn;
	private String homepage;// 主页
	private String introduction;// 个人简介
	private int createType;//0:系统已有; 1:新注册; 2:系统已有且被注册
	private Academic academic;
	private int isKey;//0:否；1：是； （是否重点人）
	private String photoDfs; //照片的云存储路径
	private String bankIds;//银行账户组ID
	private String homeAddressIds;//家庭住址组ID
	private String officeAddressIds;//办公地址组ID
	private Integer createMode;//数据创建模式[0：系统流程创建；1：系统录入创建；2：外部导入创建]
	private Date createDate;//创建时间
	private Date updateDate;//数据更新时间
	
	private Set<Expert> expert;
	private Set<Teacher> teacher;
	private Set<Officer> officer;
	private Set<Student> studentForPerson;
	private Set<Student> studentForTutor;
	private Set<Work> work;
	private Set<Abroad> abroad;
	private Set<Education> education;
	private Set<Agency> agencyForDirector;
	private Set<Agency> agencyForSdirector;
	private Set<Agency> agencyForSlinkman;
	private Set<Department> departmentForLinkman;
	private Set<Department> departmentForDirector;
	private Set<Institute> instituteForLinkman;
	private Set<Institute> instituteForDirector;
	private Set<SubInstitute> subInstituteForDirector;
	private Set<CommitteeMember> committeeMember;
	private Set<AwardApplication> awardApplicationForApplicant;
	private Set<AwardReview> awardReview;
	private Set<AwardApplication> awardApplicationForDeptInstAuditor;
	private Set<AwardApplication> awardApplicationForUniversityAuditor;
	private Set<AwardApplication> awardApplicationForProvinceAuditor;
	private Set<AwardApplication> awardApplicationForMinistryAuditor;
	private Set<AwardApplication> awardApplicationForReviewer;
	private Set<AwardApplication> awardApplicationForReviewerAuditor;
	private Set<AwardApplication> awardApplicationForFinalAuditor;
	private Set<Product> productForAuthor;
	private Set<Product> productForAuditor;
	private Set<ProjectApplication> projectApplicationForDeptInstAuditor;
	private Set<ProjectApplication> projectApplicationForUniversityAuditor;
	private Set<ProjectApplication> projectApplicationForProvinceAuditor;
	private Set<ProjectApplication> projectApplicationForMinistryAuditor;
	private Set<ProjectApplication> projectApplicationForReviewer;
	private Set<ProjectApplication> projectApplicationForReviewAuditor;
	private Set<ProjectApplication> projectApplicationForFinalAuditor;
	private Set<ProjectMember> projectMember;
	private Set<ProjectMidinspection> projectMidinspectionForDeptInstAuditor;
	private Set<ProjectMidinspection> projectMidinspectionForUniversityAuditor;
	private Set<ProjectMidinspection> projectMidinspectionForProvinceAuditor;
	private Set<ProjectMidinspection> projectMidinspectionForFinalAuditor;
	private Set<ProjectEndinspection> projectEndinspectionForDeptInstAuditor;
	private Set<ProjectEndinspection> projectEndinspectionForUniversityAuditor;
	private Set<ProjectEndinspection> projectEndinspectionForProvinceAuditor;
	private Set<ProjectEndinspection> projectEndinspectionForMinistryAuditor;
	private Set<ProjectEndinspection> projectEndinspectionForReviewer;
	private Set<ProjectEndinspection> projectEndinspectionForFinalAuditor;
	private Set<ProjectEndinspectionReview> projectEndinspectionReview;
	private Set<ProjectVariation> projectVariationForDeptInstAuditor;
	private Set<ProjectVariation> projectVariationForUniversityAuditor;
	private Set<ProjectVariation> projectVariationForProvinceAuditor;
	private Set<ProjectVariation> projectVariationForFinalAuditor;
	
	public Person(String id) {
		this.id = id;
	}
	
	public Person(){}
	
	/**
	 * 获取该人的学术信息
	 * @return
	 */
	@JSON(serialize=false)
	public Academic getAcademicEntity() {
		if (this.getAcademic() == null) {
			Academic academic = new Academic();
			academic.setPerson(this);
			this.setAcademic(academic);
		} else {
			academic = this.getAcademic();
		}
		return academic;
	}
	
	public String getOfficeAddressIds() {
		return officeAddressIds;
	}

	public void setOfficeAddressIds(String officeAddressIds) {
		this.officeAddressIds = officeAddressIds;
	}
	
	public String getHomeAddressIds() {
		return homeAddressIds;
	}

	public void setHomeAddressIds(String homeAddressIds) {
		this.homeAddressIds = homeAddressIds;
	}

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
	public String getUsedName() {
		return usedName;
	}
	public void setUsedName(String usedName) {
		this.usedName = usedName;
	}
	public String getPhotoFile() {
		return photoFile;
	}
	public void setPhotoFile(String photoFile) {
		if (photoFile != null){
			photoFile = photoFile.replaceAll("^[/\\\\]+", "");
		}
		this.photoFile = photoFile;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCountryRegion() {
		return countryRegion;
	}
	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}
	public String getEthnic() {
		return ethnic;
	}
	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}
	public String getBirthplace() {
		return birthplace;
	}
	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}
	
	@JSON(format="yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getMembership() {
		return membership;
	}
	public void setMembership(String membership) {
		this.membership = membership;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getHomeFax() {
		return homeFax;
	}

	public void setHomeFax(String homeFax) {
		this.homeFax = homeFax;
	}

	public String getOfficePhone() {
		return officePhone;
	}
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	public String getOfficeFax() {
		return officeFax;
	}
	public void setOfficeFax(String officeFax) {
		this.officeFax = officeFax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public void setCreateType(int createType) {
		this.createType = createType;
	}

	public int getCreateType() {
		return createType;
	}

	public void setIsKey(int isKey) {
		this.isKey = isKey;
	}

	public int getIsKey() {
		return isKey;
	}

	public String getBankIds() {
		return bankIds;
	}

	public void setBankIds(String bankIds) {
		this.bankIds = bankIds;
	}

	@JSON(serialize=false)
	public Set<Expert> getExpert() {
		return expert;
	}
	public void setExpert(Set<Expert> expert) {
		this.expert = expert;
	}
	@JSON(serialize=false)
	public Set<Teacher> getTeacher() {
		return teacher;
	}
	public void setTeacher(Set<Teacher> teacher) {
		this.teacher = teacher;
	}
	@JSON(serialize=false)
	public Set<Officer> getOfficer() {
		return officer;
	}
	public void setOfficer(Set<Officer> officer) {
		this.officer = officer;
	}
	@JSON(serialize=false)
	public Set<Student> getStudentForPerson() {
		return studentForPerson;
	}
	public void setStudentForPerson(Set<Student> studentForPerson) {
		this.studentForPerson = studentForPerson;
	}
	@JSON(serialize=false)
	public Set<Student> getStudentForTutor() {
		return studentForTutor;
	}
	public void setStudentForTutor(Set<Student> studentForTutor) {
		this.studentForTutor = studentForTutor;
	}
	@JSON(serialize=false)
	public Set<Work> getWork() {
		return work;
	}
	public void setWork(Set<Work> work) {
		this.work = work;
	}
	@JSON(serialize=false)
	public Set<Abroad> getAbroad() {
		return abroad;
	}

	public void setAbroad(Set<Abroad> abroad) {
		this.abroad = abroad;
	}
	@JSON(serialize=false)
	public Set<Education> getEducation() {
		return education;
	}

	public void setEducation(Set<Education> education) {
		this.education = education;
	}
	@JSON(serialize=false)
	public Set<Agency> getAgencyForDirector() {
		return agencyForDirector;
	}

	public void setAgencyForDirector(Set<Agency> agencyForDirector) {
		this.agencyForDirector = agencyForDirector;
	}
	@JSON(serialize=false)
	public Set<Agency> getAgencyForSdirector() {
		return agencyForSdirector;
	}

	public void setAgencyForSdirector(Set<Agency> agencyForSdirector) {
		this.agencyForSdirector = agencyForSdirector;
	}
	@JSON(serialize=false)
	public Set<Agency> getAgencyForSlinkman() {
		return agencyForSlinkman;
	}

	public void setAgencyForSlinkman(Set<Agency> agencyForSlinkman) {
		this.agencyForSlinkman = agencyForSlinkman;
	}
	@JSON(serialize=false)
	public Set<Department> getDepartmentForLinkman() {
		return departmentForLinkman;
	}

	public void setDepartmentForLinkman(Set<Department> departmentForLinkman) {
		this.departmentForLinkman = departmentForLinkman;
	}
	@JSON(serialize=false)
	public Set<Department> getDepartmentForDirector() {
		return departmentForDirector;
	}

	public void setDepartmentForDirector(Set<Department> departmentForDirector) {
		this.departmentForDirector = departmentForDirector;
	}
	@JSON(serialize=false)
	public Set<Institute> getInstituteForLinkman() {
		return instituteForLinkman;
	}

	public void setInstituteForLinkman(Set<Institute> instituteForLinkman) {
		this.instituteForLinkman = instituteForLinkman;
	}
	@JSON(serialize=false)
	public Set<Institute> getInstituteForDirector() {
		return instituteForDirector;
	}

	public void setInstituteForDirector(Set<Institute> instituteForDirector) {
		this.instituteForDirector = instituteForDirector;
	}
	@JSON(serialize=false)
	public Set<SubInstitute> getSubInstituteForDirector() {
		return subInstituteForDirector;
	}

	public void setSubInstituteForDirector(Set<SubInstitute> subInstituteForDirector) {
		this.subInstituteForDirector = subInstituteForDirector;
	}
	@JSON(serialize=false)
	public Set<CommitteeMember> getCommitteeMember() {
		return committeeMember;
	}

	public void setCommitteeMember(Set<CommitteeMember> committeeMember) {
		this.committeeMember = committeeMember;
	}
	@JSON(serialize=false)
	public Academic getAcademic() {
		return academic;
	}
	public void setAcademic(Academic academic) {
		this.academic = academic;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForApplicant() {
		return awardApplicationForApplicant;
	}

	public void setAwardApplicationForApplicant(
			Set<AwardApplication> awardApplicationForApplicant) {
		this.awardApplicationForApplicant = awardApplicationForApplicant;
	}
	@JSON(serialize=false)
	public Set<AwardReview> getAwardReview() {
		return awardReview;
	}

	public void setAwardReview(Set<AwardReview> awardReview) {
		this.awardReview = awardReview;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForDeptInstAuditor() {
		return awardApplicationForDeptInstAuditor;
	}

	public void setAwardApplicationForDeptInstAuditor(
			Set<AwardApplication> awardApplicationForDeptInstAuditor) {
		this.awardApplicationForDeptInstAuditor = awardApplicationForDeptInstAuditor;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForUniversityAuditor() {
		return awardApplicationForUniversityAuditor;
	}

	public void setAwardApplicationForUniversityAuditor(
			Set<AwardApplication> awardApplicationForUniversityAuditor) {
		this.awardApplicationForUniversityAuditor = awardApplicationForUniversityAuditor;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForProvinceAuditor() {
		return awardApplicationForProvinceAuditor;
	}

	public void setAwardApplicationForProvinceAuditor(
			Set<AwardApplication> awardApplicationForProvinceAuditor) {
		this.awardApplicationForProvinceAuditor = awardApplicationForProvinceAuditor;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForMinistryAuditor() {
		return awardApplicationForMinistryAuditor;
	}

	public void setAwardApplicationForMinistryAuditor(
			Set<AwardApplication> awardApplicationForMinistryAuditor) {
		this.awardApplicationForMinistryAuditor = awardApplicationForMinistryAuditor;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForReviewer() {
		return awardApplicationForReviewer;
	}

	public void setAwardApplicationForReviewer(
			Set<AwardApplication> awardApplicationForReviewer) {
		this.awardApplicationForReviewer = awardApplicationForReviewer;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForReviewerAuditor() {
		return awardApplicationForReviewerAuditor;
	}

	public void setAwardApplicationForReviewerAuditor(
			Set<AwardApplication> awardApplicationForReviewerAuditor) {
		this.awardApplicationForReviewerAuditor = awardApplicationForReviewerAuditor;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForFinalAuditor() {
		return awardApplicationForFinalAuditor;
	}

	public void setAwardApplicationForFinalAuditor(
			Set<AwardApplication> awardApplicationForFinalAuditor) {
		this.awardApplicationForFinalAuditor = awardApplicationForFinalAuditor;
	}
	@JSON(serialize=false)
	public Set<Product> getProductForAuthor() {
		return productForAuthor;
	}

	public void setProductForAuthor(Set<Product> productForAuthor) {
		this.productForAuthor = productForAuthor;
	}
	@JSON(serialize=false)
	public Set<Product> getProductForAuditor() {
		return productForAuditor;
	}

	public void setProductForAuditor(Set<Product> productForAuditor) {
		this.productForAuditor = productForAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForDeptInstAuditor() {
		return projectApplicationForDeptInstAuditor;
	}

	public void setProjectApplicationForDeptInstAuditor(
			Set<ProjectApplication> projectApplicationForDeptInstAuditor) {
		this.projectApplicationForDeptInstAuditor = projectApplicationForDeptInstAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForUniversityAuditor() {
		return projectApplicationForUniversityAuditor;
	}

	public void setProjectApplicationForUniversityAuditor(
			Set<ProjectApplication> projectApplicationForUniversityAuditor) {
		this.projectApplicationForUniversityAuditor = projectApplicationForUniversityAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForProvinceAuditor() {
		return projectApplicationForProvinceAuditor;
	}

	public void setProjectApplicationForProvinceAuditor(
			Set<ProjectApplication> projectApplicationForProvinceAuditor) {
		this.projectApplicationForProvinceAuditor = projectApplicationForProvinceAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForMinistryAuditor() {
		return projectApplicationForMinistryAuditor;
	}

	public void setProjectApplicationForMinistryAuditor(
			Set<ProjectApplication> projectApplicationForMinistryAuditor) {
		this.projectApplicationForMinistryAuditor = projectApplicationForMinistryAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForReviewer() {
		return projectApplicationForReviewer;
	}

	public void setProjectApplicationForReviewer(
			Set<ProjectApplication> projectApplicationForReviewer) {
		this.projectApplicationForReviewer = projectApplicationForReviewer;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForReviewAuditor() {
		return projectApplicationForReviewAuditor;
	}

	public void setProjectApplicationForReviewAuditor(
			Set<ProjectApplication> projectApplicationForReviewAuditor) {
		this.projectApplicationForReviewAuditor = projectApplicationForReviewAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForFinalAuditor() {
		return projectApplicationForFinalAuditor;
	}

	public void setProjectApplicationForFinalAuditor(
			Set<ProjectApplication> projectApplicationForFinalAuditor) {
		this.projectApplicationForFinalAuditor = projectApplicationForFinalAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectMember> getProjectMember() {
		return projectMember;
	}

	public void setProjectMember(Set<ProjectMember> projectMember) {
		this.projectMember = projectMember;
	}
	@JSON(serialize=false)
	public Set<ProjectMidinspection> getProjectMidinspectionForDeptInstAuditor() {
		return projectMidinspectionForDeptInstAuditor;
	}

	public void setProjectMidinspectionForDeptInstAuditor(
			Set<ProjectMidinspection> projectMidinspectionForDeptInstAuditor) {
		this.projectMidinspectionForDeptInstAuditor = projectMidinspectionForDeptInstAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectMidinspection> getProjectMidinspectionForUniversityAuditor() {
		return projectMidinspectionForUniversityAuditor;
	}

	public void setProjectMidinspectionForUniversityAuditor(
			Set<ProjectMidinspection> projectMidinspectionForUniversityAuditor) {
		this.projectMidinspectionForUniversityAuditor = projectMidinspectionForUniversityAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectMidinspection> getProjectMidinspectionForProvinceAuditor() {
		return projectMidinspectionForProvinceAuditor;
	}

	public void setProjectMidinspectionForProvinceAuditor(
			Set<ProjectMidinspection> projectMidinspectionForProvinceAuditor) {
		this.projectMidinspectionForProvinceAuditor = projectMidinspectionForProvinceAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectMidinspection> getProjectMidinspectionForFinalAuditor() {
		return projectMidinspectionForFinalAuditor;
	}

	public void setProjectMidinspectionForFinalAuditor(
			Set<ProjectMidinspection> projectMidinspectionForFinalAuditor) {
		this.projectMidinspectionForFinalAuditor = projectMidinspectionForFinalAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspection> getProjectEndinspectionForDeptInstAuditor() {
		return projectEndinspectionForDeptInstAuditor;
	}

	public void setProjectEndinspectionForDeptInstAuditor(
			Set<ProjectEndinspection> projectEndinspectionForDeptInstAuditor) {
		this.projectEndinspectionForDeptInstAuditor = projectEndinspectionForDeptInstAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspection> getProjectEndinspectionForUniversityAuditor() {
		return projectEndinspectionForUniversityAuditor;
	}

	public void setProjectEndinspectionForUniversityAuditor(
			Set<ProjectEndinspection> projectEndinspectionForUniversityAuditor) {
		this.projectEndinspectionForUniversityAuditor = projectEndinspectionForUniversityAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspection> getProjectEndinspectionForProvinceAuditor() {
		return projectEndinspectionForProvinceAuditor;
	}

	public void setProjectEndinspectionForProvinceAuditor(
			Set<ProjectEndinspection> projectEndinspectionForProvinceAuditor) {
		this.projectEndinspectionForProvinceAuditor = projectEndinspectionForProvinceAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspection> getProjectEndinspectionForMinistryAuditor() {
		return projectEndinspectionForMinistryAuditor;
	}

	public void setProjectEndinspectionForMinistryAuditor(
			Set<ProjectEndinspection> projectEndinspectionForMinistryAuditor) {
		this.projectEndinspectionForMinistryAuditor = projectEndinspectionForMinistryAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspection> getProjectEndinspectionForReviewer() {
		return projectEndinspectionForReviewer;
	}

	public void setProjectEndinspectionForReviewer(
			Set<ProjectEndinspection> projectEndinspectionForReviewer) {
		this.projectEndinspectionForReviewer = projectEndinspectionForReviewer;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspection> getProjectEndinspectionForFinalAuditor() {
		return projectEndinspectionForFinalAuditor;
	}

	public void setProjectEndinspectionForFinalAuditor(
			Set<ProjectEndinspection> projectEndinspectionForFinalAuditor) {
		this.projectEndinspectionForFinalAuditor = projectEndinspectionForFinalAuditor;
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
	public Set<ProjectVariation> getProjectVariationForDeptInstAuditor() {
		return projectVariationForDeptInstAuditor;
	}

	public void setProjectVariationForDeptInstAuditor(
			Set<ProjectVariation> projectVariationForDeptInstAuditor) {
		this.projectVariationForDeptInstAuditor = projectVariationForDeptInstAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectVariation> getProjectVariationForUniversityAuditor() {
		return projectVariationForUniversityAuditor;
	}

	public void setProjectVariationForUniversityAuditor(
			Set<ProjectVariation> projectVariationForUniversityAuditor) {
		this.projectVariationForUniversityAuditor = projectVariationForUniversityAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectVariation> getProjectVariationForProvinceAuditor() {
		return projectVariationForProvinceAuditor;
	}

	public void setProjectVariationForProvinceAuditor(
			Set<ProjectVariation> projectVariationForProvinceAuditor) {
		this.projectVariationForProvinceAuditor = projectVariationForProvinceAuditor;
	}
	@JSON(serialize=false)
	public Set<ProjectVariation> getProjectVariationForFinalAuditor() {
		return projectVariationForFinalAuditor;
	}

	public void setProjectVariationForFinalAuditor(
			Set<ProjectVariation> projectVariationForFinalAuditor) {
		this.projectVariationForFinalAuditor = projectVariationForFinalAuditor;
	}

	public String getPhotoDfs() {
		return photoDfs;
	}

	public void setPhotoDfs(String photoDfs) {
		this.photoDfs = photoDfs;
	}

	public Integer getCreateMode() {
		return createMode;
	}

	public void setCreateMode(Integer createMode) {
		this.createMode = createMode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
