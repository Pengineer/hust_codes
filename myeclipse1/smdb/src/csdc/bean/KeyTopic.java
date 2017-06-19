package csdc.bean;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import csdc.tool.bean.AuditInfo;

public class KeyTopic implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;//主键id
	private String name;//选题中文名
	private String englishName;//选题英文名
	private Integer year;//选题年份
	private String summary;//简要论证
	private Integer topicSource;//选题项目来源:0教育部;1:高校;2专家
	private Agency university;//依托高校
	private SystemOption province;// 高校所在省
	private String provinceName;//高校所在省
	private ProjectGranted project;//依托项目
	private String applicantId;//申请人Id
	private String applicantName;//申请人姓名
	private Integer applicantType;//申请人类型:1教师;2专家
	private Agency expUniversity;//推荐人所在高校
	private Department expDepartment;//推荐人所在院系
	private Institute expInstitute;//推荐人所在研究机构
	private String expAgencyName;//单位名称
	private String expDivisionName;//部门名称
	private int status;//选题状态
	private int applicantSubmitStatus;//专家选题推荐新建状态
	private Date applicantSubmitDate;//专家选题推荐新建时间
	private int deptInstAuditStatus;//院系/研究机构审核状态
	private int deptInstAuditResult;//院系/研究机构中检审核结果
	private String deptInstAuditorName;//院系/研究机构审核人名称
	private Officer deptInstAuditor;//院系/研究机构审核人
	private Department deptInstAuditorDept;//院系/研究机构审核人所在院系
	private Institute deptInstAuditorInst;//院系/研究机构审核人所在研究机构
	private String deptInstAuditOpinion;//院系/研究机构审核意见
	private Date deptInstAuditDate;//院系/研究机构审核时间
	private int universityAuditStatus;//高校审核状态
	private int universityAuditResult;//高校审核结果
    private String universityAuditorName;//高校审核人名称
    private Officer universityAuditor;//高校审核人
    private Agency universityAuditorAgency;//高校审核人所在机构
	private String universityAuditOpinion;//高校审核意见
	private int universitySubmitStatus;//高校选题推荐新建状态
	private Date universitySubmitDate;//高校选题推荐新建时间
	private Date universityAuditDate;//高校审核时间
	private int provinceAuditStatus;//省厅审核状态
	private int provinceAuditResult;//省厅审核结果
    private String provinceAuditorName;//省厅审核人名称
    private Officer provinceAuditor;//省厅审核人
    private Agency provinceAuditorAgency;//省厅审核人所在机构
	private String provinceAuditOpinion;//省厅审核意见
	private Date provinceAuditDate;//省厅审核时间
	private int finalAuditStatus;//最终审核状态
	private int finalAuditResult;//最终审核结果
	private String finalAuditorName;//最终审核人名称
	private Officer finalAuditor;//最终审核人
	private Agency finalAuditorAgency;//最终审核人所在机构
	private Department finalAuditorDept;//最终审核人所在院系
	private Institute finalAuditorInst;//最终审核人所在研究机构
	private String finalAuditOpinion;//最终审核人意见
	private Date finalAuditDate;//最终审核时间
	private Integer createMode;//数据创建模式[0：系统流程创建；1：系统录入创建；2：外部导入创建]
	private Date createDate;//创建时间
	private Date updateDate;//数据更新时间	
	/**
	 * 状态机STATUS_ARRAY是一个大小是[2][3][5]的三维数组。
	 * 一维：结果（1不同意，2同意）；
	 * 二维：操作（1退回，2暂存，3提交）；
	 * 三维：状态（1新建申请，2院系/研究机构审核，3校级审核，4省级审核，5最终审核）
	 * 数组中的0表示没有此操作
	 */
	private static final int[][][] STATUS_ARRAY =  new int[][][]
	{
		{{0,1,2,3,4}, {1,2,3,4,5}, {2,2,3,4,5}},
		{{0,1,2,3,4}, {1,2,3,4,5}, {2,3,4,5,5}}
	};
	
//	/**
//	 * 获取项目立项对象
//	 */
//	@JSON(serialize=false)
//	public abstract ProjectGranted getGranted();
//	
//	/**
//	 * 关联项目立项对象
//	 */
//	public abstract void setGranted(ProjectGranted granted);
	
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
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Integer getTopicSource() {
		return topicSource;
	}
	public void setTopicSource(Integer topicSource) {
		this.topicSource = topicSource;
	}
	@JSON(serialize=false)
	public Agency getUniversity() {
		return university;
	}
	public void setUniversity(Agency university) {
		this.university = university;
	}
	@JSON(serialize=false)
	public ProjectGranted getProject() {
		return project;
	}
	public void setProject(ProjectGranted project) {
		this.project = project;
	}
	public String getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public Integer getApplicantType() {
		return applicantType;
	}
	public void setApplicantType(Integer applicantType) {
		this.applicantType = applicantType;
	}
	@JSON(serialize=false)
	public Agency getExpUniversity() {
		return expUniversity;
	}
	public void setExpUniversity(Agency expUniversity) {
		this.expUniversity = expUniversity;
	}
	@JSON(serialize=false)
	public Department getExpDepartment() {
		return expDepartment;
	}
	public void setExpDepartment(Department expDepartment) {
		this.expDepartment = expDepartment;
	}
	@JSON(serialize=false)
	public Institute getExpInstitute() {
		return expInstitute;
	}
	public void setExpInstitute(Institute expInstitute) {
		this.expInstitute = expInstitute;
	}
	public String getExpAgencyName() {
		return expAgencyName;
	}
	public void setExpAgencyName(String expAgencyName) {
		this.expAgencyName = expAgencyName;
	}
	public String getExpDivisionName() {
		return expDivisionName;
	}
	public void setExpDivisionName(String expDivisionName) {
		this.expDivisionName = expDivisionName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getApplicantSubmitStatus() {
		return applicantSubmitStatus;
	}
	public void setApplicantSubmitStatus(int applicantSubmitStatus) {
		this.applicantSubmitStatus = applicantSubmitStatus;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getApplicantSubmitDate() {
		return applicantSubmitDate;
	}
	public void setApplicantSubmitDate(Date applicantSubmitDate) {
		this.applicantSubmitDate = applicantSubmitDate;
	}
	public int getDeptInstAuditStatus() {
		return deptInstAuditStatus;
	}
	public void setDeptInstAuditStatus(int deptInstAuditStatus) {
		this.deptInstAuditStatus = deptInstAuditStatus;
	}
	public int getDeptInstAuditResult() {
		return deptInstAuditResult;
	}
	public void setDeptInstAuditResult(int deptInstAuditResult) {
		this.deptInstAuditResult = deptInstAuditResult;
	}
	public String getDeptInstAuditorName() {
		return deptInstAuditorName;
	}
	public void setDeptInstAuditorName(String deptInstAuditorName) {
		this.deptInstAuditorName = deptInstAuditorName;
	}
	@JSON(serialize=false)
	public Officer getDeptInstAuditor() {
		return deptInstAuditor;
	}
	public void setDeptInstAuditor(Officer deptInstAuditor) {
		this.deptInstAuditor = deptInstAuditor;
	}
	@JSON(serialize=false)
	public Department getDeptInstAuditorDept() {
		return deptInstAuditorDept;
	}
	public void setDeptInstAuditorDept(Department deptInstAuditorDept) {
		this.deptInstAuditorDept = deptInstAuditorDept;
	}
	@JSON(serialize=false)
	public Institute getDeptInstAuditorInst() {
		return deptInstAuditorInst;
	}
	public void setDeptInstAuditorInst(Institute deptInstAuditorInst) {
		this.deptInstAuditorInst = deptInstAuditorInst;
	}
	public String getDeptInstAuditOpinion() {
		return deptInstAuditOpinion;
	}
	public void setDeptInstAuditOpinion(String deptInstAuditOpinion) {
		this.deptInstAuditOpinion = deptInstAuditOpinion;
	}
	public Date getDeptInstAuditDate() {
		return deptInstAuditDate;
	}
	public void setDeptInstAuditDate(Date deptInstAuditDate) {
		this.deptInstAuditDate = deptInstAuditDate;
	}
	public int getUniversityAuditStatus() {
		return universityAuditStatus;
	}
	public void setUniversityAuditStatus(int universityAuditStatus) {
		this.universityAuditStatus = universityAuditStatus;
	}
	public String getUniversityAuditOpinion() {
		return universityAuditOpinion;
	}
	public void setUniversityAuditOpinion(String universityAuditOpinion) {
		this.universityAuditOpinion = universityAuditOpinion;
	}
	public void setUniversitySubmitStatus(int universitySubmitStatus) {
		this.universitySubmitStatus = universitySubmitStatus;
	}
	public int getUniversitySubmitStatus() {
		return universitySubmitStatus;
	}
	public void setUniversitySubmitDate(Date universitySubmitDate) {
		this.universitySubmitDate = universitySubmitDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getUniversitySubmitDate() {
		return universitySubmitDate;
	}
	public Date getUniversityAuditDate() {
		return universityAuditDate;
	}
	public void setUniversityAuditDate(Date universityAuditDate) {
		this.universityAuditDate = universityAuditDate;
	}
	public int getProvinceAuditStatus() {
		return provinceAuditStatus;
	}
	public void setProvinceAuditStatus(int provinceAuditStatus) {
		this.provinceAuditStatus = provinceAuditStatus;
	}
	public String getProvinceAuditOpinion() {
		return provinceAuditOpinion;
	}
	public void setProvinceAuditOpinion(String provinceAuditOpinion) {
		this.provinceAuditOpinion = provinceAuditOpinion;
	}
	public Date getProvinceAuditDate() {
		return provinceAuditDate;
	}
	public void setProvinceAuditDate(Date provinceAuditDate) {
		this.provinceAuditDate = provinceAuditDate;
	}
	public int getFinalAuditStatus() {
		return finalAuditStatus;
	}
	public void setFinalAuditStatus(int finalAuditStatus) {
		this.finalAuditStatus = finalAuditStatus;
	}
	public int getFinalAuditResult() {
		return finalAuditResult;
	}
	public void setFinalAuditResult(int finalAuditResult) {
		this.finalAuditResult = finalAuditResult;
	}
	public String getFinalAuditOpinion() {
		return finalAuditOpinion;
	}
	public void setFinalAuditOpinion(String finalAuditOpinion) {
		this.finalAuditOpinion = finalAuditOpinion;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getFinalAuditDate() {
		return finalAuditDate;
	}
	public void setFinalAuditDate(Date finalAuditDate) {
		this.finalAuditDate = finalAuditDate;
	}
	public int getUniversityAuditResult() {
		return universityAuditResult;
	}
	public void setUniversityAuditResult(int universityAuditResult) {
		this.universityAuditResult = universityAuditResult;
	}
	public int getProvinceAuditResult() {
		return provinceAuditResult;
	}
	public void setProvinceAuditResult(int provinceAuditResult) {
		this.provinceAuditResult = provinceAuditResult;
	}
	public Integer getCreateMode() {
		return createMode;
	}
	public void setCreateMode(Integer createMode) {
		this.createMode = createMode;
	}
	public String getUniversityAuditorName() {
		return universityAuditorName;
	}
	public void setUniversityAuditorName(String universityAuditorName) {
		this.universityAuditorName = universityAuditorName;
	}
	@JSON(serialize=false)
	public Officer getUniversityAuditor() {
		return universityAuditor;
	}
	public void setUniversityAuditor(Officer universityAuditor) {
		this.universityAuditor = universityAuditor;
	}
	public String getProvinceAuditorName() {
		return provinceAuditorName;
	}
	public void setProvinceAuditorName(String provinceAuditorName) {
		this.provinceAuditorName = provinceAuditorName;
	}
	@JSON(serialize=false)
	public Officer getProvinceAuditor() {
		return provinceAuditor;
	}
	public void setProvinceAuditor(Officer provinceAuditor) {
		this.provinceAuditor = provinceAuditor;
	}
	public String getFinalAuditorName() {
		return finalAuditorName;
	}
	public void setFinalAuditorName(String finalAuditorName) {
		this.finalAuditorName = finalAuditorName;
	}
	@JSON(serialize=false)
	public Officer getFinalAuditor() {
		return finalAuditor;
	}
	public void setFinalAuditor(Officer finalAuditor) {
		this.finalAuditor = finalAuditor;
	}
	@JSON(serialize=false)
	public Department getFinalAuditorDept() {
		return finalAuditorDept;
	}
	public void setFinalAuditorDept(Department finalAuditorDept) {
		this.finalAuditorDept = finalAuditorDept;
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
	@JSON(serialize=false)
	public Institute getFinalAuditorInst() {
		return finalAuditorInst;
	}
	public void setFinalAuditorInst(Institute finalAuditorInst) {
		this.finalAuditorInst = finalAuditorInst;
	}
	@JSON(serialize=false)	
	public Agency getUniversityAuditorAgency() {
		return universityAuditorAgency;
	}
	public void setUniversityAuditorAgency(Agency universityAuditorAgency) {
		this.universityAuditorAgency = universityAuditorAgency;
	}
	@JSON(serialize=false)
	public Agency getProvinceAuditorAgency() {
		return provinceAuditorAgency;
	}
	public void setProvinceAuditorAgency(Agency provinceAuditorAgency) {
		this.provinceAuditorAgency = provinceAuditorAgency;
	}
	@JSON(serialize=false)
	public Agency getFinalAuditorAgency() {
		return finalAuditorAgency;
	}
	public void setFinalAuditorAgency(Agency finalAuditorAgency) {
		this.finalAuditorAgency = finalAuditorAgency;
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
	/**
	 * 提交操作结果
	 * @param auditMap 审核参数，Map类型，键值有auditInfo,isSubUni等等
	 */
	@SuppressWarnings("unchecked")
	public void edit(Map auditMap){
		AuditInfo auditInfo = (AuditInfo)auditMap.get("auditInfo");
		int isSubUni = (Integer)auditMap.get("isSubUni");
		int result = auditInfo.getAuditResult();//1：不同意	2：同意
		int status = auditInfo.getAuditStatus();//1：退回	2：暂存	3：提交
		saveResult(auditInfo);
		if(isSubUni == 1 && getStatus() == 3 && result == 2 && status == 3){//部属高校提交
			if(STATUS_ARRAY[1][2][getStatus()] != 0)
				setStatus(STATUS_ARRAY[result - 1][status - 1][getStatus()]);
		}else{
			if(STATUS_ARRAY[1][2][getStatus() - 1] != 0)
				setStatus(STATUS_ARRAY[result - 1][status - 1][getStatus() - 1]);
		}
		if(getStatus() > 1 && result == 1 && status == 3){
			notApproved();//审核不通过
		}
	}
	
	/**
	 *保存结果
	 * @param auditMap 审核参数，Map类型，键值有audit	Info,isSubUni等等
	 */
	private void saveResult(AuditInfo auditInfo) {
		Date date = auditInfo.getAuditDate();
		int result = auditInfo.getAuditResult();
		int status = auditInfo.getAuditStatus();
		String opinion = auditInfo.getAuditOpinion();
		String auditorName = auditInfo.getAuditorName();
		Officer auditor = auditInfo.getAuditor();
		if(getStatus() == 0){
			setStatus(1);
			setApplicantSubmitDate(date);
			setApplicantSubmitStatus(status);
		}else if(getStatus() == 1){
			setApplicantSubmitDate(date);
			setApplicantSubmitStatus(status);
		}else if(getStatus() == 2){
			setDeptInstAuditDate(date);
			setDeptInstAuditResult(result);
			setDeptInstAuditStatus(status);
			setDeptInstAuditOpinion(opinion);
			setDeptInstAuditorName(auditorName);
			setDeptInstAuditor(auditor);
			setDeptInstAuditorDept(auditInfo.getAuditorDept());
			setDeptInstAuditorInst(auditInfo.getAuditorInst());
		}else if(getStatus() == 3){
			setUniversityAuditDate(date);
			setUniversityAuditResult(result);
			setUniversityAuditStatus(status);
			setUniversityAuditOpinion(opinion);
			setUniversityAuditorName(auditorName);
			setUniversityAuditor(auditor);
			setUniversityAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 4){
			setProvinceAuditDate(date);
			setProvinceAuditResult(result);
			setProvinceAuditStatus(status);
			setProvinceAuditOpinion(opinion);
			setProvinceAuditorName(auditorName);
			setProvinceAuditor(auditor);
			setProvinceAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 5){
			setFinalAuditDate(date);
			setFinalAuditResult(result);
			setFinalAuditStatus(status);
			setFinalAuditOpinion(opinion);
			setFinalAuditorName(auditorName);
			setFinalAuditor(auditor);
			setFinalAuditorAgency(auditInfo.getAuditorAgency());
		}
	}
	
	/**
	 * 提交操作结果
	 * @param auditMap 审核参数，Map类型，键值有auditInfo,isSubUni等等(auditInfo中auditResult,auditOpinion可传为任意值)
	 */
	@SuppressWarnings("unchecked")
	public void submit(Map auditMap) {
		AuditInfo auditInfo = (AuditInfo)auditMap.get("auditInfo");
		int isSubUni = (Integer)auditMap.get("isSubUni");
		int isAgree = getIsAgree();
		changeStatus(auditInfo);
		if(isSubUni == 1 && getStatus() == 3 && isAgree == 2){//部属高校提交
			if(STATUS_ARRAY[isAgree - 1][2][getStatus()] != 0)
				setStatus(STATUS_ARRAY[isAgree - 1][2][getStatus()]);
		}else{
			if(STATUS_ARRAY[isAgree - 1][2][getStatus() - 1] != 0)
				setStatus(STATUS_ARRAY[isAgree - 1][2][getStatus() - 1]);
		}
		if(getStatus() > 1 && isAgree == 1)
			notApproved();//不同意
	}
	
	/**
	 * @param auditInfo 审核信息
	 */
	private void changeStatus(AuditInfo auditInfo) {
		Date date = auditInfo.getAuditDate();
		int status = auditInfo.getAuditStatus();
		String auditorName = auditInfo.getAuditorName();
		Officer auditor = auditInfo.getAuditor();
		if(getStatus() == 1){
			setApplicantSubmitStatus(status);
			setApplicantSubmitDate(date);
		}else if(getStatus() == 2){
			setDeptInstAuditDate(date);
			setDeptInstAuditStatus(status);
			setDeptInstAuditorName(auditorName);
			setDeptInstAuditor(auditor);
			setDeptInstAuditorDept(auditInfo.getAuditorDept());
			setDeptInstAuditorInst(auditInfo.getAuditorInst());
		}else if(getStatus() == 3){
			setUniversityAuditDate(date);
			setUniversityAuditStatus(status);
			setUniversityAuditorName(auditorName);
			setUniversityAuditor(auditor);
			setUniversityAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 4){
			setProvinceAuditDate(date);
			setProvinceAuditStatus(status);
			setProvinceAuditorName(auditorName);
			setProvinceAuditor(auditor);
			setProvinceAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 5){
			setFinalAuditDate(date);
			setFinalAuditStatus(status);
			setFinalAuditorName(auditorName);
			setFinalAuditor(auditor);
			setFinalAuditorAgency(auditInfo.getAuditorAgency());
		}
	}
	
	/**
	 * 退回
	 * @param isSubUni 是否部属高校项目（只有审核才需要，其他可直接传0） 1:是	0：不是
	 */
	public void back(int isSubUni) {
		backResult(isSubUni);
		if(isSubUni == 1 && getStatus() == 5){//部级退回
			if(STATUS_ARRAY[1][0][getStatus()- 2]!= 0)
				setStatus(STATUS_ARRAY[1][0][getStatus()- 2]);
		}else{
			if(STATUS_ARRAY[1][0][getStatus()- 1]!= 0)
				setStatus(STATUS_ARRAY[1][0][getStatus()- 1]);
		}
	}
	
	/**
	 * 设置不同意
	 */
	private void notApproved(){
		setFinalAuditResult(1);
		setFinalAuditStatus(3);
		if(getStatus() == 1){
			;
		}else if(getStatus() == 2){
			setFinalAuditorName(getDeptInstAuditorName());
			setFinalAuditOpinion(getDeptInstAuditOpinion());
			setFinalAuditDate(getDeptInstAuditDate());
			setFinalAuditor(getDeptInstAuditor());
			setFinalAuditorDept(getDeptInstAuditorDept());
			setFinalAuditorInst(getDeptInstAuditorInst());
		}else if(getStatus() == 3){
			setFinalAuditorName(getUniversityAuditorName());
			setFinalAuditOpinion(getUniversityAuditOpinion());
			setFinalAuditDate(getUniversityAuditDate());
			setFinalAuditor(getUniversityAuditor());
			setFinalAuditorAgency(getUniversityAuditorAgency());
		}else if(getStatus() == 4){
			setFinalAuditorName(getProvinceAuditorName());
			setFinalAuditOpinion(getProvinceAuditOpinion());
			setFinalAuditDate(getProvinceAuditDate());
			setFinalAuditor(getProvinceAuditor());
			setFinalAuditorAgency(getProvinceAuditorAgency());
		}else if(getStatus() == 5){
			;
		}
		
	}

	/**
	 * 退回
	 * @param isSubUni（只有审核才需要，其他可直接传0） 是否部属高校项目 1:是	0：不是
	 */
	private void backResult(int isSubUni){
		if(getStatus() == 1)//申请者
			;
		else if(getStatus() == 2) {//院系研究机构
			setApplicantSubmitStatus(1);
			setDeptInstAuditStatus(0);
			setDeptInstAuditResult(0);
			setDeptInstAuditOpinion(null);
			setDeptInstAuditorName(null);
			setDeptInstAuditDate(null);
			setDeptInstAuditor(null);
			setDeptInstAuditorDept(null);
			setDeptInstAuditorInst(null);
		}
		else if(getStatus() == 3) {//校级
			setDeptInstAuditStatus(1);
			setUniversityAuditStatus(0);
			setUniversityAuditResult(0);
			setUniversityAuditOpinion(null);
			setUniversityAuditorName(null);
			setUniversityAuditDate(null);
			setUniversityAuditor(null);
			setUniversityAuditorAgency(null);

		}
		else if(getStatus() == 4){//省厅
			setUniversityAuditStatus(1);
			setProvinceAuditStatus(0);
			setProvinceAuditResult(0);
			setProvinceAuditOpinion(null);
			setProvinceAuditorName(null);
			setProvinceAuditDate(null);
			setProvinceAuditor(null);
			setProvinceAuditorAgency(null);
		}
		else if(getStatus() == 5) {//部级
			if(isSubUni == 1) {
				setUniversityAuditStatus(1);
			}
			else if(isSubUni == 0) {
				setProvinceAuditStatus(1);
			}
			setFinalAuditStatus(0);
			setFinalAuditResult(0);
			setFinalAuditOpinion(null);
			setFinalAuditorName(null);
			setFinalAuditDate(null);
			setFinalAuditor(null);
			setFinalAuditorAgency(null);
		}
	}
	
	/**
	 * 获取当前状态级别的是否同意信息
	 * @return 当前状态级别的是否同意
	 */
	private int getIsAgree() {
		if(getStatus() == 1)
			return 2;
		else if(getStatus() == 2)
			return getDeptInstAuditResult();
		else if(getStatus() == 3)
			return getUniversityAuditResult();
		else if(getStatus() == 4)
			return getProvinceAuditResult();
		else if(getStatus() == 5)
			return getFinalAuditResult();
		return 0;
	}
}