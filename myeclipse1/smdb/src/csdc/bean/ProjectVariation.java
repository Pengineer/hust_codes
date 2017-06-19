package csdc.bean;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import csdc.tool.bean.AuditInfo;
/**
 * 状态机STATUS_ARRAY是一个大小是[2][3][5]的三维数组。
 * 一维：结果（1不同意，2同意）；
 * 二维：操作（1退回，2暂存，3提交）；
 * 三维：状态（1新建申请，2院系/研究机构审核，3校级审核，4省级审核，5最终审核）
 * 数组中的0表示没有此操作
 * 各级auditResultDetail	审核结果详情编码	长度为9，九位字符依次是：变更项目成员（含负责人）(0)、变更机构(1)、变更成果形式(2)、变更项目名称(3)、研究内容有重大调整(4)、延期(5)、自行终止项目(6)、申请撤项(7)、其他(20)这九类变更结果的标志位。	'1'表示同意 '0'表示不同意
 */
public abstract class ProjectVariation{

	private static final int[][][] STATUS_ARRAY = new int[][][]{//定义状态机
			{{0,1,2,3,4},{1,2,3,4,5},{2,2,3,4,5}},
			{{0,1,2,3,4},{1,2,3,4,5},{2,3,4,5,5}}
	};
	private String id;
	private String projectType;//项目类型
	private int changeMember;//变更项目成员:默认0；1变更；2不变更
	private Integer oldMemberGroupNumber;//变更前项目成员表的组编号
	private Integer newMemberGroupNumber;//变更后项目成员表的组编号
	private int changeAgency;//变更管理机构
	private String oldAgencyName;//原机构名
	private Agency oldAgency;//原机构id
	private String oldDivisionName;//原部门名
	private Department oldDepartment;//原院系
	private Institute oldInstitute;//原研究机构
	private String newAgencyName;//新机构名
	private Agency newAgency;//新机构
	private String newDivisionName;//新部门名
	private Department newDepartment;//新院系
	private Institute newInstitute;//新研究机构
	private int changeProductType;//变更成果形式
	private String oldProductType;//原成果形式
	private String oldProductTypeOther;//原其他成果形式
	private String newProductType;//新成果形式
	private String newProductTypeOther;//新其他成果形式
	private int changeName;//变更项目名称
	private String oldName;//原名称
	private String newName;//新名称
	private String oldEnglishName;//原英文名称
	private String newEnglishName;//新英文名称
	private int changeContent;//变更研究内容
	private int postponement;//延期：每次只能延期一年，最多延期两次
	private Date oldOnceDate;//延期一次前计划完成时间
	private Date newOnceDate;//延期一次后计划完成时间
	private int stop;//自行中止
	private int withdraw;//申请撤项
	private int other;//其他
	private String otherInfo;//其他信息
	private String file;//变更申请文件
	private String postponementPlanFile;//变更延期研究计划
	private String note;//备注
	private int status;//变更流程状态
	private Date applicantSubmitDate;//新建申请时间
	private int applicantSubmitStatus;//新建申请状态
	private int deptInstAuditStatus;//院系/研究机构审核状态
	private int deptInstAuditResult;//院系/研究机构审核结果
	private String deptInstAuditResultDetail;//院系/研究机构审核结果详情
    private String deptInstAuditorName;//院系/研究机构审核人姓名
    private Officer deptInstAuditor;//院系/研究机构审核人id
    private Department deptInstAuditorDept;//院系/研究机构审核人所在院系
    private Institute deptInstAuditorInst;//院系/研究机构审核人所在研究机构id
	private Date deptInstAuditDate;//院系/研究机构审核时间
	private String deptInstAuditOpinion;//院系/研究机构审核意见
	private int universityAuditStatus;//高校审核状态
	private int universityAuditResult;//高校审核结果
	private String universityAuditResultDetail;//高校审核结果详情
    private String universityAuditorName;//高校审核人姓名
    private Officer universityAuditor;//高校审核人id
    private Agency universityAuditorAgency;//高校审核人机构id
	private Date universityAuditDate;//高校审核时间
	private String universityAuditOpinion;//高校审核意见
	private int provinceAuditStatus;//省厅审核状态
	private int provinceAuditResult;//省厅审核结果
	private String provinceAuditResultDetail;//省厅审核结果详情
    private String provinceAuditorName;//省审核人姓名
    private Officer provinceAuditor;//省审核人id
    private Agency provinceAuditorAgency;//省审核人所在机构id
	private Date provinceAuditDate;//省厅审核时间
	private String provinceAuditOpinion;//省厅审核意见
	private int finalAuditStatus;//最终审核状态
	private int finalAuditResult;//最终审核结果
	private String finalAuditResultDetail;//最终审核结果详情
	private String finalAuditorName;//最终审核人姓名
	private Officer finalAuditor;//最终审核人id
	private Agency finalAuditorAgency;//最终审核人所在机构
	private Department finalAuditorDept;//最终审核人所在院系
	private Institute finalAuditorInst;//最终审核人所在研究机构
	private Date finalAuditDate;//最终审核时间
	private String finalAuditOpinion;//最终审核意见
	private String finalAuditOpinionFeedback;//最终审核意见（反馈给项目负责人）	
	private int changeFee;//变更经费
	private ProjectFee oldProjectFee;//原经费预算id
	private ProjectFee newProjectFee;//新经费预算id
	private Integer createMode;//数据创建模式[0：系统流程创建；1：系统录入创建；2：外部导入创建]
	private Date createDate;//创建时间
	private Date updateDate;//数据更新时间

	private String grantedId;
	private String variationReason;//变更申请原因
	private int finalAuditResultPublish;//最终审核结果发布[0：否；1：是]
	private String dfs;//变更申请文件云存储位置
	private String postponementPlanDfs;//变更延期研究计划书云存储位置

	/**
	 * 获取项目立项对象
	 */
	@JSON(serialize=false)
	public abstract ProjectGranted getGranted();
	
	/**
	 * 关联项目立项对象
	 */
	public abstract void setGranted(ProjectGranted granted);
	/**
	 * 获取项目对应变更类名
	 */
	@JSON(serialize = false)
	public abstract String getVariationClassName();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getGrantedId() {
		return grantedId;
	}
	public void setGrantedId(String grantedId) {
		this.grantedId = grantedId;
	}
	public int getChangeMember() {
		return changeMember;
	}
	public void setChangeMember(int changeMember) {
		this.changeMember = changeMember;
	}
	public int getChangeAgency() {
		return changeAgency;
	}
	public void setChangeAgency(int changeAgency) {
		this.changeAgency = changeAgency;
	}
	public String getOldAgencyName() {
		return oldAgencyName;
	}
	public void setOldAgencyName(String oldAgencyName) {
		this.oldAgencyName = oldAgencyName;
	}
	@JSON(serialize=false)
	public Agency getOldAgency() {
		return oldAgency;
	}
	public void setOldAgency(Agency oldAgency) {
		this.oldAgency = oldAgency;
	}
	public String getOldDivisionName() {
		return oldDivisionName;
	}
	public void setOldDivisionName(String oldDivisionName) {
		this.oldDivisionName = oldDivisionName;
	}
	@JSON(serialize=false)
	public Department getOldDepartment() {
		return oldDepartment;
	}
	public void setOldDepartment(Department oldDepartment) {
		this.oldDepartment = oldDepartment;
	}
	public String getNewAgencyName() {
		return newAgencyName;
	}
	public void setNewAgencyName(String newAgencyName) {
		this.newAgencyName = newAgencyName;
	}
	@JSON(serialize=false)
	public Agency getNewAgency() {
		return newAgency;
	}
	public void setNewAgency(Agency newAgency) {
		this.newAgency = newAgency;
	}
	public String getNewDivisionName() {
		return newDivisionName;
	}
	public void setNewDivisionName(String newDivisionName) {
		this.newDivisionName = newDivisionName;
	}
	@JSON(serialize=false)
	public Department getNewDepartment() {
		return newDepartment;
	}
	public void setNewDepartment(Department newDepartment) {
		this.newDepartment = newDepartment;
	}
	public int getChangeProductType() {
		return changeProductType;
	}
	public void setChangeProductType(int changeProductType) {
		this.changeProductType = changeProductType;
	}
	public String getOldProductTypeOther() {
		return oldProductTypeOther;
	}
	public void setOldProductTypeOther(String oldProductTypeOther) {
		this.oldProductTypeOther = oldProductTypeOther;
	}
	public String getNewProductTypeOther() {
		return newProductTypeOther;
	}
	public void setNewProductTypeOther(String newProductTypeOther) {
		this.newProductTypeOther = newProductTypeOther;
	}
	public String getOldProductType() {
		return oldProductType;
	}
	public void setOldProductType(String oldProductType) {
		this.oldProductType = oldProductType;
	}
	public String getNewProductType() {
		return newProductType;
	}
	public void setNewProductType(String newProductType) {
		this.newProductType = newProductType;
	}
	public int getChangeName() {
		return changeName;
	}
	public void setChangeName(int changeName) {
		this.changeName = changeName;
	}
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	public int getChangeContent() {
		return changeContent;
	}
	public void setChangeContent(int changeContent) {
		this.changeContent = changeContent;
	}
	public int getStop() {
		return stop;
	}
	public void setStop(int stop) {
		this.stop = stop;
	}
	public int getWithdraw() {
		return withdraw;
	}
	public void setWithdraw(int withdraw) {
		this.withdraw = withdraw;
	}
	public int getOther() {
		return other;
	}
	public void setOther(int other) {
		this.other = other;
	}
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getPostponementPlanFile() {
		return postponementPlanFile;
	}
	public void setPostponementPlanFile(String postponementPlanFile) {
		this.postponementPlanFile = postponementPlanFile;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getUniversityAuditStatus() {
		return universityAuditStatus;
	}
	public void setUniversityAuditStatus(int universityAuditStatus) {
		this.universityAuditStatus = universityAuditStatus;
	}
	public Date getUniversityAuditDate() {
		return this.universityAuditDate;
	}
	public void setUniversityAuditDate(Date universityAuditDate) {
		this.universityAuditDate = universityAuditDate;
	}
	public String getUniversityAuditOpinion() {
		return universityAuditOpinion;
	}
	public void setUniversityAuditOpinion(String universityAuditOpinion) {
		this.universityAuditOpinion = universityAuditOpinion;
	}
	public int getProvinceAuditStatus() {
		return provinceAuditStatus;
	}
	public void setProvinceAuditStatus(int provinceAuditStatus) {
		this.provinceAuditStatus = provinceAuditStatus;
	}
	public Date getProvinceAuditDate() {
		return this.provinceAuditDate;
	}
	public void setProvinceAuditDate(Date provinceAuditDate) {
		this.provinceAuditDate = provinceAuditDate;
	}
	public String getProvinceAuditOpinion() {
		return provinceAuditOpinion;
	}
	public void setProvinceAuditOpinion(String provinceAuditOpinion) {
		this.provinceAuditOpinion = provinceAuditOpinion;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getOldOnceDate() {
		return oldOnceDate;
	}
	public void setOldOnceDate(Date oldOnceDate) {
		this.oldOnceDate = oldOnceDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getNewOnceDate() {
		return newOnceDate;
	}
	public void setNewOnceDate(Date newOnceDate) {
		this.newOnceDate = newOnceDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getApplicantSubmitDate() {
		return applicantSubmitDate;
	}
	public void setApplicantSubmitDate(Date applicantSubmitDate) {
		this.applicantSubmitDate = applicantSubmitDate;
	}
	public int getApplicantSubmitStatus() {
		return applicantSubmitStatus;
	}
	public void setApplicantSubmitStatus(int applicantSubmitStatus) {
		this.applicantSubmitStatus = applicantSubmitStatus;
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
	public String getDeptInstAuditResultDetail() {
		return deptInstAuditResultDetail;
	}
	public void setDeptInstAuditResultDetail(String deptInstAuditResultDetail) {
		this.deptInstAuditResultDetail = deptInstAuditResultDetail;
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
	public Date getDeptInstAuditDate() {
		return deptInstAuditDate;
	}
	public void setDeptInstAuditDate(Date deptInstAuditDate) {
		this.deptInstAuditDate = deptInstAuditDate;
	}
	public String getDeptInstAuditOpinion() {
		return deptInstAuditOpinion;
	}
	public void setDeptInstAuditOpinion(String deptInstAuditOpinion) {
		this.deptInstAuditOpinion = deptInstAuditOpinion;
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
	@JSON(format="yyyy-MM-dd")
	public Date getFinalAuditDate() {
		return finalAuditDate;
	}
	public void setFinalAuditDate(Date finalAuditDate) {
		this.finalAuditDate = finalAuditDate;
	}
	public String getFinalAuditOpinion() {
		return finalAuditOpinion;
	}
	public void setFinalAuditOpinion(String finalAuditOpinion) {
		this.finalAuditOpinion = finalAuditOpinion;
	}
	public String getFinalAuditOpinionFeedback() {
		return finalAuditOpinionFeedback;
	}
	public void setFinalAuditOpinionFeedback(String finalAuditOpinionFeedback) {
		this.finalAuditOpinionFeedback = finalAuditOpinionFeedback;
	}
	
	public int getFinalAuditResultPublish() {
		return finalAuditResultPublish;
	}
	
	public void setFinalAuditResultPublish(int finalAuditResultPublish) {
		this.finalAuditResultPublish = finalAuditResultPublish;
	}

	
	public String getDfs() {
		return dfs;
	}

	public void setDfs(String dfs) {
		this.dfs = dfs;
	}

	public String getPostponementPlanDfs() {
		return postponementPlanDfs;
	}

	public void setPostponementPlanDfs(String postponementPlanDfs) {
		this.postponementPlanDfs = postponementPlanDfs;
	}

	@JSON(serialize=false)
	public Institute getOldInstitute() {
		return oldInstitute;
	}
	public void setOldInstitute(Institute oldInstitute) {
		this.oldInstitute = oldInstitute;
	}
	@JSON(serialize=false)
	public Institute getNewInstitute() {
		return newInstitute;
	}
	public void setNewInstitute(Institute newInstitute) {
		this.newInstitute = newInstitute;
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
	@JSON(serialize=false)
	public Department getFinalAuditorDept() {
		return finalAuditorDept;
	}
	public void setFinalAuditorDept(Department finalAuditorDept) {
		this.finalAuditorDept = finalAuditorDept;
	}
	public String getUniversityAuditResultDetail() {
		return universityAuditResultDetail;
	}
	public void setUniversityAuditResultDetail(String universityAuditResultDetail) {
		this.universityAuditResultDetail = universityAuditResultDetail;
	}
	public String getProvinceAuditResultDetail() {
		return provinceAuditResultDetail;
	}
	public void setProvinceAuditResultDetail(String provinceAuditResultDetail) {
		this.provinceAuditResultDetail = provinceAuditResultDetail;
	}
	public String getFinalAuditResultDetail() {
		return finalAuditResultDetail;
	}
	public void setFinalAuditResultDetail(String finalAuditResultDetail) {
		this.finalAuditResultDetail = finalAuditResultDetail;
	}
	public int getPostponement() {
		return postponement;
	}
	public void setPostponement(int postponement) {
		this.postponement = postponement;
	}
	public String getOldEnglishName() {
		return oldEnglishName;
	}
	public void setOldEnglishName(String oldEnglishName) {
		this.oldEnglishName = oldEnglishName;
	}
	public String getNewEnglishName() {
		return newEnglishName;
	}
	public void setNewEnglishName(String newEnglishName) {
		this.newEnglishName = newEnglishName;
	}
	public Integer getOldMemberGroupNumber() {
		return oldMemberGroupNumber;
	}
	public void setOldMemberGroupNumber(Integer oldMemberGroupNumber) {
		this.oldMemberGroupNumber = oldMemberGroupNumber;
	}
	public Integer getNewMemberGroupNumber() {
		return newMemberGroupNumber;
	}
	public void setNewMemberGroupNumber(Integer newMemberGroupNumber) {
		this.newMemberGroupNumber = newMemberGroupNumber;
	}
	public String getVariationReason() {
		return variationReason;
	}

	public void setVariationReason(String variationReason) {
		this.variationReason = variationReason;
	}

	/**
	 * 提交操作结果
	 * @param auditMap 审核参数，Map类型，键值有auditInfo,isSubUni,varApproveIssue等等
	 */
	@SuppressWarnings("rawtypes")
	public void edit(Map auditMap){
		String varApproveIssue = (String)auditMap.get("varApproveIssue");
		AuditInfo auditInfo = (AuditInfo)auditMap.get("auditInfo");
		int isSubUni = (Integer)auditMap.get("isSubUni");
		int result = auditInfo.getAuditResult();//1：不同意	2：同意
		int status = auditInfo.getAuditStatus();//1：退回	2：暂存	3：提交
		saveResult(auditInfo, varApproveIssue);
		if(isSubUni == 1 && getStatus() == 3 && result == 2 && status == 3){//部属高校提交
			if(STATUS_ARRAY[1][2][getStatus()] != 0)
				setStatus(STATUS_ARRAY[result - 1][status - 1][getStatus()]);
		}else{
			if(STATUS_ARRAY[1][2][getStatus() - 1] != 0)
				setStatus(STATUS_ARRAY[result - 1][status - 1][getStatus() - 1]);
		}
		if(getStatus() > 1 && result == 1 && status == 3){
			notApproved();//不获奖
		}
	}
	/**
	 * 保存结果
	 * @param auditMap 审核参数，Map类型，键值有auditInfo,isSubUni等等
	 * @param varApproveIssue 同意变更事项
	 */
	public void saveResult(AuditInfo auditInfo, String varApproveIssue){
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
			setDeptInstAuditResultDetail(varApproveIssue);
			setDeptInstAuditStatus(status);
			setDeptInstAuditOpinion(opinion);
			setDeptInstAuditorName(auditorName);
			setDeptInstAuditor(auditor);
			setDeptInstAuditorDept(auditInfo.getAuditorDept());
			setDeptInstAuditorInst(auditInfo.getAuditorInst());
		}else if(getStatus() == 3){
			setUniversityAuditDate(date);
			setUniversityAuditResult(result);
			setUniversityAuditResultDetail(varApproveIssue);
			setUniversityAuditStatus(status);
			setUniversityAuditOpinion(opinion);
			setUniversityAuditorName(auditorName);
			setUniversityAuditor(auditor);
			setUniversityAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 4){
			setProvinceAuditDate(date);
			setProvinceAuditResult(result);
			setProvinceAuditResultDetail(varApproveIssue);
			setProvinceAuditStatus(status);
			setProvinceAuditOpinion(opinion);
			setProvinceAuditorName(auditorName);
			setProvinceAuditor(auditor);
			setProvinceAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 5){
//			setFinalAuditDate(date);
			setFinalAuditResult(result);
			setFinalAuditResultDetail(varApproveIssue);
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
	@SuppressWarnings("rawtypes")
	public void submit(Map auditMap){
		AuditInfo auditInfo = (AuditInfo)auditMap.get("auditInfo");
		int isSubUni = (Integer)auditMap.get("isSubUni");
		int isAgree = getIsAgree();//1：不同意	2：同意
		changeStatus(auditInfo);
		if(isSubUni == 1 && getStatus() == 3 && isAgree == 2){//部属高校提交
			if(STATUS_ARRAY[isAgree - 1][2][getStatus()] != 0)
				setStatus(STATUS_ARRAY[isAgree - 1][2][getStatus()]);
		}else{
			if(STATUS_ARRAY[isAgree - 1][2][getStatus() - 1] != 0)
				setStatus(STATUS_ARRAY[isAgree - 1][2][getStatus() - 1]);
		}
		if(getStatus() > 1 && isAgree == 1)
			notApproved();//不批准
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
		setFinalAuditResultDetail(null);
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
	 * @param isSubUni 是否部属高校项目（只有审核才需要，其他可直接传0） 1:是	0：不是
	 */
	private void backResult(int isSubUni){
		if(getStatus() == 1)//申请者
			;
		else if(getStatus() == 2) {//院系研究机构
			setApplicantSubmitStatus(1);
			setDeptInstAuditStatus(0);
			setDeptInstAuditResult(0);
			setDeptInstAuditResultDetail(null);
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
			setUniversityAuditResultDetail(null);
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
			setProvinceAuditResultDetail(null);
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
			setFinalAuditResultDetail(null);
			setFinalAuditOpinion(null);
			setFinalAuditorName(null);
			setFinalAuditDate(null);
			setFinalAuditor(null);
			setFinalAuditorAgency(null);
		}
		setFinalAuditOpinionFeedback(null);
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

	public void setOldProjectFee(ProjectFee oldProjectFee) {
		this.oldProjectFee = oldProjectFee;
	}
	@JSON(serialize = false)
	public ProjectFee getOldProjectFee() {
		return oldProjectFee;
	}

	public void setNewProjectFee(ProjectFee newProjectFee) {
		this.newProjectFee = newProjectFee;
	}
	@JSON(serialize = false)
	public ProjectFee getNewProjectFee() {
		return newProjectFee;
	}

	public void setChangeFee(int changeFee) {
		this.changeFee = changeFee;
	}

	public int getChangeFee() {
		return changeFee;
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
