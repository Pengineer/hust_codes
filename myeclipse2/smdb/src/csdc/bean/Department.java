package csdc.bean;

import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class Department implements java.io.Serializable {

	private static final long serialVersionUID = -2035553684162341669L;
	private String id;// ID
	private String name;// 名称
	private String code;// 院系代码
	private String introduction;//简介
	private String address;// 地址
	private String postcode;// 邮编
	private String phone;// 电话
	private String fax;// 传真
	private String email;// 邮箱
	private String homepage;// 主页
	private Agency university;// 所属高校
	private Person director;// 负责人
	private Person linkman;// 联系人

	private Set<Officer> officer;
	private Set<Student> student;
	private Set<Teacher> teacher;
	private Set<Discipline> discipline;
	private Set<Doctoral> doctoral;
	private Set<AwardApplication> awardApplicationForDepartment;
	private Set<AwardApplication> awardApplicationForDeptInstAuditorDept;
	private Set<AwardApplication> awardApplicationForFinalAuditorDept;
	private Set<AwardReview> awardReview;
	private Set<Product> productForDepartment;
	private Set<Product> productForAuditorDept;
	private Set<ProjectApplication> projectApplicationForDepartment;
	private Set<ProjectGranted> projectGranted;
	private Set<ProjectMember> projectMember;
	private Set<ProjectEndinspectionReview> projectEndinspectionReviewForDepartment;
	private Set<ProjectVariation> projectVariationForOldDepartment;
	private Set<ProjectVariation> projectVariationForNewDepartment;
	private Set<ProjectApplication> projectApplicationForDeptInstAuditorDept;
	private Set<ProjectApplication> projectApplicationForFinalAuditorDept;
	private Set<ProjectMidinspection> projectMidinspectionForDeptInstAuditorDept;
	private Set<ProjectMidinspection> projectMidinspectionForFinalAuditorDept;
	private Set<ProjectEndinspection> projectEndinspectionForDeptInstAuditorDept;
	private Set<ProjectEndinspection> projectEndinspectionForFinalAuditorDept;
	private Set<ProjectVariation> projectVariationForDeptInstAuditorDept;
	private Set<ProjectVariation> projectVariationForFinalAuditorDept;	
	private Set<ProjectData> projectData;
	
	public Department(){};

	public Department(String id){
		this.id = id;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public Agency getUniversity() {
		return university;
	}
	public void setUniversity(Agency university) {
		this.university = university;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JSON(serialize=false)
	public Person getDirector() {
		return director;
	}
	public void setDirector(Person director) {
		this.director = director;
	}
	@JSON(serialize=false)
	public Person getLinkman() {
		return linkman;
	}
	public void setLinkman(Person linkman) {
		this.linkman = linkman;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	@JSON(serialize=false)
	public Set<Officer> getOfficer() {
		return officer;
	}
	public void setOfficer(Set<Officer> officer) {
		this.officer = officer;
	}
	@JSON(serialize=false)
	public Set<Student> getStudent() {
		return student;
	}
	public void setStudent(Set<Student> student) {
		this.student = student;
	}
	@JSON(serialize=false)
	public Set<Teacher> getTeacher() {
		return teacher;
	}
	public void setTeacher(Set<Teacher> teacher) {
		this.teacher = teacher;
	}
	@JSON(serialize=false)
	public Set<Discipline> getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Set<Discipline> discipline) {
		this.discipline = discipline;
	}
	@JSON(serialize=false)
	public Set<Doctoral> getDoctoral() {
		return doctoral;
	}

	public void setDoctoral(Set<Doctoral> doctoral) {
		this.doctoral = doctoral;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForDepartment() {
		return awardApplicationForDepartment;
	}

	public void setAwardApplicationForDepartment(
			Set<AwardApplication> awardApplicationForDepartment) {
		this.awardApplicationForDepartment = awardApplicationForDepartment;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForDeptInstAuditorDept() {
		return awardApplicationForDeptInstAuditorDept;
	}

	public void setAwardApplicationForDeptInstAuditorDept(
			Set<AwardApplication> awardApplicationForDeptInstAuditorDept) {
		this.awardApplicationForDeptInstAuditorDept = awardApplicationForDeptInstAuditorDept;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplicationForFinalAuditorDept() {
		return awardApplicationForFinalAuditorDept;
	}

	public void setAwardApplicationForFinalAuditorDept(
			Set<AwardApplication> awardApplicationForFinalAuditorDept) {
		this.awardApplicationForFinalAuditorDept = awardApplicationForFinalAuditorDept;
	}
	@JSON(serialize=false)
	public Set<AwardReview> getAwardReview() {
		return awardReview;
	}

	public void setAwardReview(Set<AwardReview> awardReview) {
		this.awardReview = awardReview;
	}
	@JSON(serialize=false)
	public Set<Product> getProductForDepartment() {
		return productForDepartment;
	}

	public void setProductForDepartment(Set<Product> productForDepartment) {
		this.productForDepartment = productForDepartment;
	}
	@JSON(serialize=false)
	public Set<Product> getProductForAuditorDept() {
		return productForAuditorDept;
	}

	public void setProductForAuditorDept(Set<Product> productForAuditorDept) {
		this.productForAuditorDept = productForAuditorDept;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForDepartment() {
		return projectApplicationForDepartment;
	}

	public void setProjectApplicationForDepartment(
			Set<ProjectApplication> projectApplicationForDepartment) {
		this.projectApplicationForDepartment = projectApplicationForDepartment;
	}

	@JSON(serialize=false)
	public Set<ProjectGranted> getProjectGranted() {
		return projectGranted;
	}

	public void setProjectGranted(Set<ProjectGranted> projectGranted) {
		this.projectGranted = projectGranted;
	}
	@JSON(serialize=false)
	public Set<ProjectMember> getProjectMember() {
		return projectMember;
	}

	public void setProjectMember(Set<ProjectMember> projectMember) {
		this.projectMember = projectMember;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspectionReview> getProjectEndinspectionReviewForDepartment() {
		return projectEndinspectionReviewForDepartment;
	}

	public void setProjectEndinspectionReviewForDepartment(
			Set<ProjectEndinspectionReview> projectEndinspectionReviewForDepartment) {
		this.projectEndinspectionReviewForDepartment = projectEndinspectionReviewForDepartment;
	}
	@JSON(serialize=false)
	public Set<ProjectVariation> getProjectVariationForOldDepartment() {
		return projectVariationForOldDepartment;
	}

	public void setProjectVariationForOldDepartment(
			Set<ProjectVariation> projectVariationForOldDepartment) {
		this.projectVariationForOldDepartment = projectVariationForOldDepartment;
	}
	@JSON(serialize=false)
	public Set<ProjectVariation> getProjectVariationForNewDepartment() {
		return projectVariationForNewDepartment;
	}

	public void setProjectVariationForNewDepartment(
			Set<ProjectVariation> projectVariationForNewDepartment) {
		this.projectVariationForNewDepartment = projectVariationForNewDepartment;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForDeptInstAuditorDept() {
		return projectApplicationForDeptInstAuditorDept;
	}

	public void setProjectApplicationForDeptInstAuditorDept(
			Set<ProjectApplication> projectApplicationForDeptInstAuditorDept) {
		this.projectApplicationForDeptInstAuditorDept = projectApplicationForDeptInstAuditorDept;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProjectApplicationForFinalAuditorDept() {
		return projectApplicationForFinalAuditorDept;
	}

	public void setProjectApplicationForFinalAuditorDept(
			Set<ProjectApplication> projectApplicationForFinalAuditorDept) {
		this.projectApplicationForFinalAuditorDept = projectApplicationForFinalAuditorDept;
	}
	@JSON(serialize=false)
	public Set<ProjectMidinspection> getProjectMidinspectionForDeptInstAuditorDept() {
		return projectMidinspectionForDeptInstAuditorDept;
	}

	public void setProjectMidinspectionForDeptInstAuditorDept(
			Set<ProjectMidinspection> projectMidinspectionForDeptInstAuditorDept) {
		this.projectMidinspectionForDeptInstAuditorDept = projectMidinspectionForDeptInstAuditorDept;
	}
	@JSON(serialize=false)
	public Set<ProjectMidinspection> getProjectMidinspectionForFinalAuditorDept() {
		return projectMidinspectionForFinalAuditorDept;
	}

	public void setProjectMidinspectionForFinalAuditorDept(
			Set<ProjectMidinspection> projectMidinspectionForFinalAuditorDept) {
		this.projectMidinspectionForFinalAuditorDept = projectMidinspectionForFinalAuditorDept;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspection> getProjectEndinspectionForDeptInstAuditorDept() {
		return projectEndinspectionForDeptInstAuditorDept;
	}

	public void setProjectEndinspectionForDeptInstAuditorDept(
			Set<ProjectEndinspection> projectEndinspectionForDeptInstAuditorDept) {
		this.projectEndinspectionForDeptInstAuditorDept = projectEndinspectionForDeptInstAuditorDept;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspection> getProjectEndinspectionForFinalAuditorDept() {
		return projectEndinspectionForFinalAuditorDept;
	}

	public void setProjectEndinspectionForFinalAuditorDept(
			Set<ProjectEndinspection> projectEndinspectionForFinalAuditorDept) {
		this.projectEndinspectionForFinalAuditorDept = projectEndinspectionForFinalAuditorDept;
	}
	@JSON(serialize=false)
	public Set<ProjectVariation> getProjectVariationForDeptInstAuditorDept() {
		return projectVariationForDeptInstAuditorDept;
	}

	public void setProjectVariationForDeptInstAuditorDept(
			Set<ProjectVariation> projectVariationForDeptInstAuditorDept) {
		this.projectVariationForDeptInstAuditorDept = projectVariationForDeptInstAuditorDept;
	}
	@JSON(serialize=false)
	public Set<ProjectVariation> getProjectVariationForFinalAuditorDept() {
		return projectVariationForFinalAuditorDept;
	}

	public void setProjectVariationForFinalAuditorDept(
			Set<ProjectVariation> projectVariationForFinalAuditorDept) {
		this.projectVariationForFinalAuditorDept = projectVariationForFinalAuditorDept;
	}
	@JSON(serialize=false)
	public Set<ProjectData> getProjectData() {
		return projectData;
	}

	public void setProjectData(Set<ProjectData> projectData) {
		this.projectData = projectData;
	}

}
