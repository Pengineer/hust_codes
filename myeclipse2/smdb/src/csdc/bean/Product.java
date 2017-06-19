package csdc.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * 成果基类
 */
public abstract class Product {
	
	private String id;//主键id
	private String productType;//成果类型
	private String chineseName;//中文名称
    private String englishName;//英文名称
    private String disciplineType;//学科门类
    private String discipline;//学科代码
    private String file;//成果电子档文件
    private Date submitDate;//提交时间
    private Person author;//第一作者
    private String otherAuthorName;//其它作者
    private String keywords;//关键词
    private String authorName;//第一作者名称
    private int authorType;//第一作者类型：1教师    2专家    3学生
    private Agency university;// 所属高校
    private Department department;// 所属院系
    private Institute institute;// 所属研究机构
    private String agencyName;//第一作者单位信息（所属高校）    
    private SystemOption province;//高校所在省
    private String provinceName;//高校所在省    
    private String divisionName;//第一作者部门信息（所属院系或所属独立研究机构）
    private String note;//备注
    private int isForeignCooperation;//是否与国（境）外合作
    private int isImported;//是否导入数据：1是，0否
    private int submitStatus;//提交状态0:默认 1：保留	 2：暂存	3：提交
    private String introduction;//简介
    private String auditorName;//审核人姓名 
    private Date auditDate;//审核时间
    private int auditStatus;//审核状态:0默认，1保留，2保留，3提交
    private int auditResult;//审核结果:0未审核，1不同意，2同意
    private Person auditor;//审核人id
    private Department auditorDept;//审核人所在院系
    private Institute auditorInst;//审核人所在研究机构
    private Agency auditorAgency;//审核人所在机构
    //@CheckSystemOptionStandard("productForm")
    private SystemOption form;//形态
    private Set<AwardApplication> awardApplication;//奖励申请
    private String orgName;//以团队等名义申请名称
    private String orgDiscipline;//学科门类
	private String orgOfficeAddress;// 办公地址
	private String orgOfficePhone;// 办公电话
	private String orgOfficePostcode;// 办公邮编
	private String orgEmail;// 邮箱
	private String orgMobilePhone;// 电话
	private Person orgPerson;// 第一负责人id
	private String orgMember;// 团队、课题组、机构的其他成员（多个以英文分号与空格隔开）
	private String language;//成果语言
	private String dfs;
	
    
    public final static Map<String, String> typeMap = new HashMap<String, String>();
    
    public static String findTypeName(String productType) {//通过成果类型获得中文名称
    	return typeMap.get(productType);
    }
    
    
    public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getDisciplineType() {
		return disciplineType;
	}
	public void setDisciplineType(String disciplineType) {
		this.disciplineType = disciplineType;
	}
	public String getDiscipline() {
		return discipline;
	}
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	@JSON(serialize=false)
	public Person getAuthor() {
		return author;
	}
	public void setAuthor(Person author) {
		this.author = author;
	}
	public String getOtherAuthorName() {
		return otherAuthorName;
	}
	public void setOtherAuthorName(String otherAuthorName) {
		this.otherAuthorName = otherAuthorName;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public int getAuthorType() {
		return authorType;
	}
	public void setAuthorType(int authorType) {
		this.authorType = authorType;
	}
	@JSON(serialize=false)
	public Agency getUniversity() {
		return university;
	}
	public void setUniversity(Agency university) {
		this.university = university;
	}
	@JSON(serialize=false)
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	@JSON(serialize=false)
	public Institute getInstitute() {
		return institute;
	}
	public void setInstitute(Institute institute) {
		this.institute = institute;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public int getIsForeignCooperation() {
		return isForeignCooperation;
	}
	public void setIsForeignCooperation(int isForeignCooperation) {
		this.isForeignCooperation = isForeignCooperation;
	}
	public int getIsImported() {
		return isImported;
	}
	public void setIsImported(int isImported) {
		this.isImported = isImported;
	}
	public int getSubmitStatus() {
		return submitStatus;
	}
	public void setSubmitStatus(int submitStatus) {
		this.submitStatus = submitStatus;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public int getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	public int getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(int auditResult) {
		this.auditResult = auditResult;
	}
	@JSON(serialize=false)
	public Person getAuditor() {
		return auditor;
	}
	public void setAuditor(Person auditor) {
		this.auditor = auditor;
	}
	@JSON(serialize=false)
	public Department getAuditorDept() {
		return auditorDept;
	}
	public void setAuditorDept(Department auditorDept) {
		this.auditorDept = auditorDept;
	}
	@JSON(serialize=false)
	public Institute getAuditorInst() {
		return auditorInst;
	}
	public void setAuditorInst(Institute auditorInst) {
		this.auditorInst = auditorInst;
	}
	@JSON(serialize=false)
	public Agency getAuditorAgency() {
		return auditorAgency;
	}
	public void setAuditorAgency(Agency auditorAgency) {
		this.auditorAgency = auditorAgency;
	}
	@JSON(serialize=false)
	public SystemOption getForm() {
		return form;
	}
	public void setForm(SystemOption form) {
		this.form = form;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardApplication() {
		return awardApplication;
	}
	public void setAwardApplication(Set<AwardApplication> awardApplication) {
		this.awardApplication = awardApplication;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgDiscipline() {
		return orgDiscipline;
	}

	public void setOrgDiscipline(String orgDiscipline) {
		this.orgDiscipline = orgDiscipline;
	}

	public String getOrgOfficeAddress() {
		return orgOfficeAddress;
	}

	public void setOrgOfficeAddress(String orgOfficeAddress) {
		this.orgOfficeAddress = orgOfficeAddress;
	}

	public String getOrgOfficePhone() {
		return orgOfficePhone;
	}

	public void setOrgOfficePhone(String orgOfficePhone) {
		this.orgOfficePhone = orgOfficePhone;
	}

	public String getOrgOfficePostcode() {
		return orgOfficePostcode;
	}

	public void setOrgOfficePostcode(String orgOfficePostcode) {
		this.orgOfficePostcode = orgOfficePostcode;
	}

	public String getOrgEmail() {
		return orgEmail;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	public String getOrgMobilePhone() {
		return orgMobilePhone;
	}

	public void setOrgMobilePhone(String orgMobilePhone) {
		this.orgMobilePhone = orgMobilePhone;
	}

	
	@JSON(serialize=false)
	public Person getOrgPerson() {
		return orgPerson;
	}

	public void setOrgPerson(Person orgPerson) {
		this.orgPerson = orgPerson;
	}

	public String getOrgMember() {
		return orgMember;
	}

	public void setOrgMember(String orgMember) {
		this.orgMember = orgMember;
	}
	
	@JSON(serialize=false)
	public SystemOption getProvince() {
		return province;
	}

	public void setProvince(SystemOption province) {
		this.province = province;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}


	public String getDfs() {
		return dfs;
	}


	public void setDfs(String dfs) {
		this.dfs = dfs;
	}
	
	

}