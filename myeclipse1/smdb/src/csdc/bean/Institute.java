package csdc.bean;

import java.util.Date;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * type
 * 1 部属研究机构
 * 2 省部共建研究机构
 * 3 省属研究机构
 * 4 校属研究机构
 * 5 校企合作研究机构
 * @author 龚凡
 */
public class Institute implements java.io.Serializable {

	private static final long serialVersionUID = -4363618778866914644L;
	private String id;// ID
	private String name;// 名称
	private String englishName;// 英文名
	private String abbr;// 缩写
	private String code;// 代码
	private String introduction;//简介
	private String phone;// 电话
	private String fax;// 传真
	private String email;// 邮箱
	private String homepage;// 主页
	private String addressIds;// 地址

	private Agency subjection;// 上级管理部门
	private Person director;// 负责人
	private Person linkman;// 联系人

	//@CheckSystemOptionStandard("researchAgencyType")
	private SystemOption type; //研究机构类型
	//@CheckSystemOptionStandard("researchActivityType")
	private SystemOption researchActivityType;// 研究活动类型
 
	private Date approveDate;// 批准设立时间
	private String approveSession;// 批准批次
	private String form;// 组成方式
	private String disciplineType;// 所属学科门类
	private String researchArea;// 所属学术片
	private String chineseBookAmount;// 中文藏书数量
	private String foreignBookAmount;// 外文藏书数量
	private String chinesePaperAmount;// 中文报刊数量
	private String foreignPaperAmount;// 外文报刊数量
	private int isIndependent;// 是否独立实体机构
	private int officeArea;// 办公用房面积
	private int dataroomArea;// 资料室面积
	
	private Integer createMode;//数据创建模式[0：系统流程创建；1：系统录入创建；2：外部导入创建]
	private Date createDate;//创建时间
	private Date updateDate;//数据更新时间

	private Set<Officer> officer;
	private Set<Teacher> teacher;
	private Set<Committee> committee;
	private Set<Student> student;
	private Set<CommitteeActivity> committeeActivitie;
	private Set<SubInstitute> subInstitute;
	private Set<AwardApplication> awardApplication;
	private Set<AwardApplication> awardDeptInstAudInstId;
	private Set<AwardApplication> awardFinAudInstId;
	private Set<AwardReview> awardReviewInst;
	private Set<Discipline> discipline;
	private Set<Doctoral> doctorDept;
	private Set<Product> productInstitute;
	private Set<Product> productAuditorInstitute;
	private Set<ProjectApplication> applicationInstitute;
	private Set<ProjectGranted> grantedInstitute;
	private Set<ProjectMember> memberInstitute;
	private Set<ProjectEndinspectionReview> proEndReviewInstitute;
	private Set<ProjectVariation> proVarOldInstitute;
	private Set<ProjectVariation> proVarNewInstitute;
	private Set<ProjectApplication> proAppDeptInstAudInstId;
	private Set<ProjectApplication> proAppFinAudInstId;
	private Set<ProjectMidinspection> proMidDeptInstAudInstId;
	private Set<ProjectMidinspection> proMidFinAudInstId;
	private Set<ProjectEndinspection> proEndDeptInstAudInstId;
	private Set<ProjectEndinspection> proEndFinAudInstId;
	private Set<ProjectVariation> proVarDeptInstAudInstId;
	private Set<ProjectVariation> proVarFinAudInstId;
	private Set<ProjectData> resDataInst;
	private Set<InstituteFunding> instituteFunding;

	public Institute(){}

	public Institute(String id){
		this.id = id;
	}

	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplication() {
		return awardApplication;
	}
	public void setAwardApplication(Set<AwardApplication> awardApplication) {
		this.awardApplication = awardApplication;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	public SystemOption getType() {
		return type;
	}
	public void setType(SystemOption type) {
		this.type = type;
	}
	
	public String getAddressIds() {
		return addressIds;
	}

	public void setAddressIds(String addressIds) {
		this.addressIds = addressIds;
	}
	
	@JSON(serialize=false)
	public Agency getSubjection() {
		return subjection;
	}
	public void setSubjection(Agency subjection) {
		this.subjection = subjection;
	}
	public String getApproveSession() {
		return approveSession;
	}
	public void setApproveSession(String approveSession) {
		this.approveSession = approveSession;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public int getIsIndependent() {
		return isIndependent;
	}
	public void setIsIndependent(int isIndependent) {
		this.isIndependent = isIndependent;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	@JSON(serialize=false)
	public SystemOption getResearchActivityType() {
		return researchActivityType;
	}
	public void setResearchActivityType(SystemOption researchActivityType) {
		this.researchActivityType = researchActivityType;
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
	public int getOfficeArea() {
		return officeArea;
	}
	public void setOfficeArea(int officeArea) {
		this.officeArea = officeArea;
	}
	public String getChineseBookAmount() {
		return chineseBookAmount;
	}
	public void setChineseBookAmount(String chineseBookAmount) {
		this.chineseBookAmount = chineseBookAmount;
	}
	public String getForeignBookAmount() {
		return foreignBookAmount;
	}
	public void setForeignBookAmount(String foreignBookAmount) {
		this.foreignBookAmount = foreignBookAmount;
	}
	public String getChinesePaperAmount() {
		return chinesePaperAmount;
	}
	public void setChinesePaperAmount(String chinesePaperAmount) {
		this.chinesePaperAmount = chinesePaperAmount;
	}
	public String getForeignPaperAmount() {
		return foreignPaperAmount;
	}
	public void setForeignPaperAmount(String foreignPaperAmount) {
		this.foreignPaperAmount = foreignPaperAmount;
	}
	public int getDataroomArea() {
		return dataroomArea;
	}
	public void setDataroomArea(int dataroomArea) {
		this.dataroomArea = dataroomArea;
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
	public Set<Committee> getCommittee() {
		return committee;
	}
	public void setCommittee(Set<Committee> committee) {
		this.committee = committee;
	}
	@JSON(serialize=false)
	public Set<Student> getStudent() {
		return student;
	}
	public void setStudent(Set<Student> student) {
		this.student = student;
	}
	@JSON(serialize=false)
	public Set<CommitteeActivity> getCommitteeActivitie() {
		return committeeActivitie;
	}
	public void setCommitteeActivitie(Set<CommitteeActivity> committeeActivitie) {
		this.committeeActivitie = committeeActivitie;
	}
	@JSON(serialize=false)
	public Set<SubInstitute> getSubInstitute() {
		return subInstitute;
	}
	public void setSubInstitute(Set<SubInstitute> subInstitute) {
		this.subInstitute = subInstitute;
	}
	public String getDisciplineType() {
		return disciplineType;
	}
	public void setDisciplineType(String disciplineType) {
		this.disciplineType = disciplineType;
	}

	@JSON(serialize=false)
	public Set<AwardReview> getAwardReviewInst() {
		return awardReviewInst;
	}
	public void setAwardReviewInst(Set<AwardReview> awardReviewInst) {
		this.awardReviewInst = awardReviewInst;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardDeptInstAudInstId() {
		return awardDeptInstAudInstId;
	}
	
	public void setAwardDeptInstAudInstId(
			Set<AwardApplication> awardDeptInstAudInstId) {
		this.awardDeptInstAudInstId = awardDeptInstAudInstId;
	}
	@JSON(serialize=false)
	public Set<Discipline> getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Set<Discipline> discipline) {
		this.discipline = discipline;
	}

	@JSON(serialize=false)
	public Set<Doctoral> getDoctorDept() {
		return doctorDept;
	}
	public void setDoctorDept(Set<Doctoral> doctorDept) {
		this.doctorDept = doctorDept;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardFinAudInstId() {
		return awardFinAudInstId;
	}

	public void setAwardFinAudInstId(Set<AwardApplication> awardFinAudInstId) {
		this.awardFinAudInstId = awardFinAudInstId;
	}
	@JSON(serialize=false)
	public Set<ProjectData> getResDataInst() {
		return resDataInst;
	}

	public void setResDataInst(Set<ProjectData> resDataInst) {
		this.resDataInst = resDataInst;
	}

	public String getResearchArea() {
		return researchArea;
	}

	public void setResearchArea(String researchArea) {
		this.researchArea = researchArea;
	}
	@JSON(serialize=false)
	public Set<Product> getProductInstitute() {
		return productInstitute;
	}

	public void setProductInstitute(Set<Product> productInstitute) {
		this.productInstitute = productInstitute;
	}
	@JSON(serialize=false)
	public Set<Product> getProductAuditorInstitute() {
		return productAuditorInstitute;
	}

	public void setProductAuditorInstitute(Set<Product> productAuditorInstitute) {
		this.productAuditorInstitute = productAuditorInstitute;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getApplicationInstitute() {
		return applicationInstitute;
	}

	public void setApplicationInstitute(Set<ProjectApplication> applicationInstitute) {
		this.applicationInstitute = applicationInstitute;
	}
	@JSON(serialize=false)
	public Set<ProjectGranted> getGrantedInstitute() {
		return grantedInstitute;
	}

	public void setGrantedInstitute(Set<ProjectGranted> grantedInstitute) {
		this.grantedInstitute = grantedInstitute;
	}
	@JSON(serialize=false)
	public Set<ProjectMember> getMemberInstitute() {
		return memberInstitute;
	}

	public void setMemberInstitute(Set<ProjectMember> memberInstitute) {
		this.memberInstitute = memberInstitute;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspectionReview> getProEndReviewInstitute() {
		return proEndReviewInstitute;
	}

	public void setProEndReviewInstitute(
			Set<ProjectEndinspectionReview> proEndReviewInstitute) {
		this.proEndReviewInstitute = proEndReviewInstitute;
	}
	@JSON(serialize=false)
	public Set<ProjectVariation> getProVarOldInstitute() {
		return proVarOldInstitute;
	}

	public void setProVarOldInstitute(Set<ProjectVariation> proVarOldInstitute) {
		this.proVarOldInstitute = proVarOldInstitute;
	}
	@JSON(serialize=false)
	public Set<ProjectVariation> getProVarNewInstitute() {
		return proVarNewInstitute;
	}

	public void setProVarNewInstitute(Set<ProjectVariation> proVarNewInstitute) {
		this.proVarNewInstitute = proVarNewInstitute;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProAppDeptInstAudInstId() {
		return proAppDeptInstAudInstId;
	}

	public void setProAppDeptInstAudInstId(
			Set<ProjectApplication> proAppDeptInstAudInstId) {
		this.proAppDeptInstAudInstId = proAppDeptInstAudInstId;
	}
	@JSON(serialize=false)
	public Set<ProjectApplication> getProAppFinAudInstId() {
		return proAppFinAudInstId;
	}

	public void setProAppFinAudInstId(Set<ProjectApplication> proAppFinAudInstId) {
		this.proAppFinAudInstId = proAppFinAudInstId;
	}
	@JSON(serialize=false)
	public Set<ProjectMidinspection> getProMidDeptInstAudInstId() {
		return proMidDeptInstAudInstId;
	}

	public void setProMidDeptInstAudInstId(
			Set<ProjectMidinspection> proMidDeptInstAudInstId) {
		this.proMidDeptInstAudInstId = proMidDeptInstAudInstId;
	}
	@JSON(serialize=false)
	public Set<ProjectMidinspection> getProMidFinAudInstId() {
		return proMidFinAudInstId;
	}

	public void setProMidFinAudInstId(Set<ProjectMidinspection> proMidFinAudInstId) {
		this.proMidFinAudInstId = proMidFinAudInstId;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspection> getProEndDeptInstAudInstId() {
		return proEndDeptInstAudInstId;
	}

	public void setProEndDeptInstAudInstId(
			Set<ProjectEndinspection> proEndDeptInstAudInstId) {
		this.proEndDeptInstAudInstId = proEndDeptInstAudInstId;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspection> getProEndFinAudInstId() {
		return proEndFinAudInstId;
	}

	public void setProEndFinAudInstId(Set<ProjectEndinspection> proEndFinAudInstId) {
		this.proEndFinAudInstId = proEndFinAudInstId;
	}
	@JSON(serialize=false)
	public Set<ProjectVariation> getProVarDeptInstAudInstId() {
		return proVarDeptInstAudInstId;
	}

	public void setProVarDeptInstAudInstId(
			Set<ProjectVariation> proVarDeptInstAudInstId) {
		this.proVarDeptInstAudInstId = proVarDeptInstAudInstId;
	}
	@JSON(serialize=false)
	public Set<ProjectVariation> getProVarFinAudInstId() {
		return proVarFinAudInstId;
	}

	public void setProVarFinAudInstId(Set<ProjectVariation> proVarFinAudInstId) {
		this.proVarFinAudInstId = proVarFinAudInstId;
	}
	@JSON(serialize=false)
	public Set<InstituteFunding> getInstituteFunding() {
		return instituteFunding;
	}

	public void setInstituteFunding(Set<InstituteFunding> instituteFunding) {
		this.instituteFunding = instituteFunding;
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