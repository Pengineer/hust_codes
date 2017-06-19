package csdc.action.project;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.ProjectApplication;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectVariation;
import csdc.bean.common.Visitor;
import csdc.service.IUploadService;
import csdc.tool.ApplicationContainer;
import csdc.tool.StringTool;
import csdc.tool.bean.FileRecord;
import csdc.tool.bean.MemberInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

public abstract class VariationAction extends ProjectAction {
	
	private static final long serialVersionUID = 1L;
	protected ProjectVariation variation;//项目变更
	protected String varId;// 项目变更id
	protected Date varAuditDate;
	protected int varAuditResult;//审核结果	1:不通过		2：通过
	protected String varAuditOpinion;//审核意见
	protected String varAuditOpinionFeedback;//审核意见（反馈给项目负责人）
	protected String varAuditSelectIssue;//同意的变更事项
	@SuppressWarnings("rawtypes")
	protected List varItems;//可以同意的变更事项
	
	protected Date varDate;//变更时间
	
	//异步文件上传所需
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	
	protected ArrayList<Object> varListForSelect;//供JSP页面显示的变更事项
	protected int times;//变更次数
	protected String defaultSelectCode;//变更事项选中code
	protected String chineseName;//变更中文名
	protected String projectName;
	protected String oldAgencyName;//变更前管理机构
	protected String newAgencyName;//变更后管理机构
	protected ProjectFee oldFee;//变更前经费
	protected String defaultSelectProductTypeCode;//变更成果形式默认选中;
	protected String oldProductTypeCode;//变更前的成果形式
	protected String productTypeOther;//其他成果类别名称
	protected String oldProductTypeOther;//变更前的其他成果类别名称
	protected Date newDate1;//延期一次
	protected String planEndDate;//原计划完成时间
	protected String otherInfo;//变更其他信息
	protected ProjectFee newFee;
	protected String members;//老成员
	protected List<MemberInfo> newMembers;//新成员
	
	protected int varResult;//变更结果	 1：不同意	2：同意
	protected List<String> selectIssue;//变更事项
	protected List<String> varSelectIssue;//同意变更事项
	protected List<String> selectProductType;//变更成果形式
	protected String varImportedOpinion;//录入变更结果意见
	protected String varOpinionFeedback;//录入变更结果意见 （反馈给负责人）
	protected String variationReason;//变更原因
	
	public String popAudit() {
		variation = (ProjectVariation)this.dao.query(ProjectVariation.class, varId.trim());
		String varCanApproveItem = "";//可以同意的变更事项，多个以','隔开
		int	isSubUni = projectService.isSubordinateUniversityGranted(this.projectService.getGrantedIdByVarId(variation.getId()));
		if(isSubUni == 1 && variation.getUniversityAuditResultDetail() != null && !variation.getUniversityAuditResultDetail().equals("00000000000000000000")){//部属高校;高校审核有同意变更的记录
			varCanApproveItem = this.projectService.getVarCanApproveItem(variation.getUniversityAuditResultDetail());
		} else if (isSubUni == 0 && variation.getProvinceAuditResultDetail() != null && !variation.getProvinceAuditResultDetail().equals("00000000000000000000")){//地方高校;省厅审核有同意变更的记录
			varCanApproveItem = this.projectService.getVarCanApproveItem(variation.getProvinceAuditResultDetail());
		} else if (isSubUni == 0 && variation.getUniversityAuditResultDetail() != null && !variation.getUniversityAuditResultDetail().equals("00000000000000000000")){//地方高校;省厅审核无同意变更的记录或者省厅无审核记录就读取高校的审核记录) 
			varCanApproveItem = this.projectService.getVarCanApproveItem(variation.getUniversityAuditResultDetail());
		} else {
			varCanApproveItem = this.projectService.getVarCanApproveItem(variation);
		}
		// 获取变更事项
		varItems = this.projectService.getVarItemList(varCanApproveItem);
		return SUCCESS;
	}
	
	/**
	 * 预处理添加变更审核校验
	 */
	public void validatePopAudit() {
		String info = "";
		if (varId == null || varId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_VIEW_NULL);
			info += ProjectInfo.ERROR_VAR_VIEW_NULL;
		}else{
			projectid = this.projectService.getGrantedIdByVarId(varId);
			if (projectid == null || projectid.isEmpty()) {//项目id不得为空
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}else{
				ProjectGranted general = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
				if(general == null){
					this.addFieldError(GlobalInfo.ERROR_INFO,GlobalInfo.ERROR_EXCEPTION_INFO);
					info += GlobalInfo.ERROR_EXCEPTION_INFO;
				}else if(general.getStatus() == 3){//中止
					this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_STOP);
					info += ProjectInfo.ERROR_PROJECT_STOP;
				}else if(general.getStatus() == 4){//撤项
					this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_REVOKE);
					info += ProjectInfo.ERROR_PROJECT_REVOKE;
				}
			}
		}
		if(this.projectService.getPassEndinspectionByGrantedId(projectid).size()>0){//存在已通过结项
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_PASS);
			info += ProjectInfo.ERROR_END_PASS;
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	/**
	 * 添加变更审核信息校验
	 */
	public void validateAudit() {
		this.validatePopAudit();
		if(1 != varAuditResult && 2 != varAuditResult){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_VAR_AUDIT_RESULT_NULL);
		}
		if(varAuditResult == 2 && (varAuditSelectIssue == null || varAuditSelectIssue.trim().length() == 0)){//同意变更
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_VAR_SELECT_ISSUE_NULL);
		}
		if(varAuditOpinion != null && varAuditOpinion.trim().length() > 200){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_VAR_AUDIT_OPINION_OUT);
		}
		if(varAuditOpinionFeedback != null && varAuditOpinionFeedback.trim().length() > 200){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_OPINION_FEEDBACK_OUT);
		}
	}
	/**
	 * 添加项目变更申请审核
	 */
	@Transactional
	public String audit() throws Exception{
		variation=(ProjectVariation)this.dao.query(ProjectVariation.class, varId.trim());
    	if(varAuditOpinion != null){
			variation.setFinalAuditOpinion(("A"+varAuditOpinion).trim().substring(1));
		} else {
			variation.setFinalAuditOpinion(null);
		}
		if(varAuditOpinionFeedback != null){
			variation.setFinalAuditOpinionFeedback(("A" + varAuditOpinionFeedback).trim().substring(1));
		}else{
			variation.setFinalAuditOpinionFeedback(null);
		}
		String varApproveIssue;//同意变更事项
    	if(varAuditResult == 2){//同意
    		varApproveIssue = this.projectService.getVarApproveItem(varAuditSelectIssue);
    	}else{
    		varApproveIssue = null;
    	}
		variation.setFinalAuditResultDetail(varApproveIssue);
		Date approveDate = this.projectService.setDateHhmmss(varAuditDate);
		variation.setFinalAuditDate(approveDate);
		Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
		variation.setFinalAuditorName(visitor.getUser().getUsername());
		if(varAuditResult == 2)//部级同意变更
	        this.projectService.variationProject(variation);
		variation.setFinalAuditResult(varAuditResult);
		baseService.modify(variation);
		return SUCCESS;
	}
	
	/**
	 * 查看变更审核校验
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	public void validateAuditView(){
		if (varId == null || varId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_VIEW_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_VIEW_NULL);
		}
	}
	
	/**
	 * 查看变更审核详情
	 * @return
	 */
	public String auditView() {
		ProjectVariation variation = (ProjectVariation) baseService.query(ProjectVariation.class, varId);
		varAuditDate = variation.getFinalAuditDate();
		varAuditResult = variation.getFinalAuditResult();
		varAuditOpinion = variation.getFinalAuditOpinion();
		varAuditOpinionFeedback = variation.getFinalAuditOpinionFeedback();
		return SUCCESS;
	}
	
	/**
	 * 预处理修改变更审核校验
	 */
	
	public void validateToAuditModify() {
		this.validatePopAudit();
	}
	
	/**
	 * 准备修改评审审核
	 */
	public String toAuditModify() {
		variation = (ProjectVariation) this.dao.query(ProjectVariation.class,varId.trim());
		String varCanApproveItem = "";//可以同意的变更事项，多个以','隔开
		varAuditOpinion = variation.getFinalAuditOpinion();
		varAuditOpinionFeedback = variation.getFinalAuditOpinionFeedback();
		varAuditResult = variation.getFinalAuditResult();
		varAuditSelectIssue = this.projectService.getVarCanApproveItem(variation.getFinalAuditResultDetail());
		int	isSubUni = projectService.isSubordinateUniversityGranted(this.projectService.getGrantedIdByVarId(variation.getId()));
		if(isSubUni == 1 && variation.getUniversityAuditResultDetail() != null && !variation.getUniversityAuditResultDetail().equals("00000000000000000000")){//部属高校;高校审核有同意变更的记录
			varCanApproveItem = this.projectService.getVarCanApproveItem(variation.getUniversityAuditResultDetail());
		} else if (isSubUni == 0 && variation.getProvinceAuditResultDetail() != null && !variation.getProvinceAuditResultDetail().equals("0000000000000000000000000000000000000000")){//地方高校;省厅审核有同意变更的记录
			varCanApproveItem = this.projectService.getVarCanApproveItem(variation.getProvinceAuditResultDetail());
		} else if (isSubUni == 0 && variation.getUniversityAuditResultDetail() != null && !variation.getUniversityAuditResultDetail().equals("0000000000000000000000000000000000000000")){//地方高校;省厅审核无同意变更的记录或者省厅无审核记录就读取高校的审核记录) 
			varCanApproveItem = this.projectService.getVarCanApproveItem(variation.getUniversityAuditResultDetail());
		} else {
			varCanApproveItem = this.projectService.getVarCanApproveItem(variation);
		}
		// 获取变更事项
		varItems = this.projectService.getVarItemList(varCanApproveItem);
		return SUCCESS;
	}
	/**
	 * 修改变更审核校验
	 * @author 余潜玉
	 */
	public void validateAuditModify(){
		this.validateAudit();
	}
	/**
	 * 修改变更审核
	 * @author 余潜玉
	 */
	@Transactional
	public String auditModify() throws Exception{
		return this.audit();
	}
	
	/**
	 * 准备添加一般项目变更录入校验
	 * @author 余潜玉
	 */
	public void validateToAddResult() {
		if(projectid == null || "".equals(projectid)){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_NOT_GRANTED);
		}else{
			ProjectGranted general = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
			if(general == null){
				this.addFieldError(GlobalInfo.ERROR_INFO,GlobalInfo.ERROR_EXCEPTION_INFO);
			}else if(general.getStatus() == 2){//结项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_END_PASS);
			}else if(general.getStatus() == 3){//中止
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_STOP);
			}else if(general.getStatus() == 4){//撤项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_REVOKE);
			}
		}
		if(this.projectService.getPassEndinspectionByGrantedId(this.projectid).size()>0){//存在已通过结项
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_PASS);
		}
		ProjectVariation variation = this.projectService.getCurrentVariationByGrantedId(projectid);
		if (null!=variation) {
			if(this.projectService.getPendingVariationByGrantedId(this.projectid).size()>0){//有未处理变更
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_DEALING);
			}
		}
		
	}
	
	/**
	 * 准备添加一般项目变更录入
	 * @author 余潜玉
	 */
	public String toAddResult(){
		varDate = new Date();
		ProjectGranted granted = (ProjectGranted) baseService.query(ProjectGranted.class, projectid);
		Map application = ActionContext.getContext().getApplication();
		varListForSelect = new ArrayList<Object>();
		List list = (List)application.get("varItems");
		varListForSelect.addAll(list);
		List<ProjectVariation> vars = this.projectService.getAuditedVariationByGrantedId(projectid);
		for (int i = 0; i < vars.size(); i++) {
			if (vars.get(i).getChangeFee() == 1) {
				if (varListForSelect.size() == 10) {
					varListForSelect.remove(8);
					break;
				}
			}
		}
		String groupId1 = "file_add";
		String groupId2 = "file_postponementPlan_add";
		uploadService.resetGroup(groupId1);
		uploadService.resetGroup(groupId2);
		//默认项目成员信息
		members = granted.getApplication().getMembers();
		//当前变更次数
		times = this.projectService.getVarTimes(projectid)+1;
		//默认变更事项
		defaultSelectCode = "";
		//默认项目名称
		chineseName = granted.getName();
		projectName = chineseName;
		oldAgencyName = granted.getAgencyName()+ "; " + granted.getDivisionName();
		newAgencyName = oldAgencyName;
		//变更前经费
		if (granted.getProjectFee() != null) {
			oldFee = (ProjectFee) baseService.query(ProjectFee.class, granted.getProjectFee().getId());
		}
		defaultSelectProductTypeCode = this.projectService.getProductTypeCodes(granted.getProductType());
		oldProductTypeCode = defaultSelectProductTypeCode;
		productTypeOther = granted.getProductTypeOther();
		oldProductTypeOther = granted.getProductTypeOther();
		//默认计划完成时间
		newDate1 = granted.getPlanEndDate();
		if(granted.getPlanEndDate() != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			planEndDate = df.format(granted.getPlanEndDate());
		}
		//是否有待审中检
		int midPending = this.projectService.getPendingMidinspectionByGrantedId(this.projectid).size()>0 ? 1 : 0;
		request.setAttribute("midPending", midPending);//是否有待审中检
		return SUCCESS;
	}

	/**
	 * 录入变更结果校验
	 * @author 余潜玉
	 * @throws Exception 
	 */
	public void validateAddResult() {
		if(projectid == null || "".equals(projectid)){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_NOT_GRANTED);
		}else{
			ProjectGranted general = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
			if(general == null){
				this.addFieldError(GlobalInfo.ERROR_INFO,GlobalInfo.ERROR_EXCEPTION_INFO);
			}else if(general.getStatus() == 2){//结项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_END_PASS);
			}else if(general.getStatus() == 3){//中止
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_STOP);
			}else if(general.getStatus() == 4){//撤项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_REVOKE);
			}
		}
		if(this.projectService.getPassEndinspectionByGrantedId(this.projectid).size()>0){//存在已通过结项
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_PASS);
		}
		ProjectVariation variation = this.projectService.getCurrentVariationByGrantedId(projectid);
		if (null!=variation) {
			if(this.projectService.getPendingVariationByGrantedId(this.projectid).size()>0){//有未处理变更
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_DEALING);
			}
		}
		if(varResult != 1 && varResult != 2){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_RESULT_NULL);
		}
		if(varResult == 2){//同意变更
			if(varSelectIssue == null || varSelectIssue.size() == 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_SELECT_ISSUE_NULL);
			}
		}
		if(varDate == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_DATE_NULL);
		}
		if(this.selectIssue == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_ISSUE_NULL);
		} else {
			int count = this.selectIssue.size();
			for(int i = 0; i < count; i++){
				if(this.selectIssue.get(i).equals("01")) {
					//TODO 成员校验有待考虑
					if(newMembers == null || newMembers.size() == 0){
						this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_MEMBER_NULL);
					}
				}
				if(selectIssue.equals("02")){//变更机构
					if(newAgencyName == null || "".equals(newAgencyName)){
						this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_DEPT_INST_NULL);
					}else if(newAgencyName.equals(oldAgencyName)){
						this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_DEPT_INST_SAME);
					}
				}
				if(selectIssue.equals("03")){//变更成果形式
					if(this.selectProductType == null){
						this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_PRODUCT_TYPE_NULL);
					}else if(this.selectProductType.contains("otherProductType")){
						if(productTypeOther == null || productTypeOther.trim().isEmpty()){
							this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_PRODUCT_TYPE_OTHER_NULL);
						}else if(productTypeOther.trim().length() > 50){
							this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_PRODUCT_TYPE_OTHER_OUT);
						}
					}
				}
				if(selectIssue.equals("04")){
					if(this.chineseName.equals("")){
						this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_PROJECT_NAME_NULL);
					}else if(this.chineseName.length() > 50){
						this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_NAME_OUT);
					}
				}
				if(selectIssue.equals("06")){
					if(this.newDate1 == null){
						this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_END_TIME_NULL);
					}
					ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class,projectid);
					if(granted.getPlanEndDate() != null){
						int d2 = this.newDate1.compareTo(granted.getPlanEndDate());
						if(d2 < 0){
							this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_PROJECT_TIME_INVALIDATE);
						}
					}
				}
				if(selectIssue.equals("10")){//其他变更
					if(null == otherInfo || otherInfo.length() == 0){
						this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_OTHER_NULL);
					}
				}
			}
		}
		if(varImportedOpinion != null && varImportedOpinion.trim().length() > 200){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_OPINION_OUT);
		}
		if(varOpinionFeedback != null && varOpinionFeedback.trim().length() > 200){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_OPINION_OUT);
		}
	}
	/**
	 * 一般项目变更结果录入添加
	 * @author 余潜玉、肖雅
	 * @throws Exception 
	 */
	@Transactional
	public String addResult() throws Exception {
		variation = new ProjectVariation();
		variation.setType(projectType());
		ProjectGranted granted = (ProjectGranted) baseService.query(ProjectGranted.class, projectid);
		variation.setGranted(granted);
		variation.setIsImported(1);//设为导入数据
		Date submitDate = this.projectService.setDateHhmmss(varDate);
		variation.setImportedDate(new Date());
		variation.setApplicantSubmitDate(submitDate);
		variation.setFinalAuditDate(submitDate);
		variation.setFinalAuditResult(varResult);
		Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
		variation.setFinalAuditorName(visitor.getUser().getUsername());
		if(varImportedOpinion != null && varImportedOpinion.trim().length() > 0){
			variation.setFinalAuditOpinion(("A" + varImportedOpinion).trim().substring(1));
		}else{
			variation.setFinalAuditOpinion(null);
		}
		if(variationReason != null && variationReason.trim().length() > 0){
			variation.setVariationReason(("A" + variationReason).trim().substring(1));
		}else{
			variation.setVariationReason(null);
		}
		
		if(varOpinionFeedback != null && varOpinionFeedback.trim().length() > 0){
			variation.setFinalAuditOpinionFeedback(("A" + varOpinionFeedback).trim().substring(1));
		}else{
			variation.setFinalAuditOpinionFeedback(null);
		}
		if(varResult == 2){//同意
			String varSelectIssues = "";
			for(int i = 0; i < varSelectIssue.size(); i++){
				varSelectIssues += varSelectIssue.get(i) + ",";
			}
			variation.setFinalAuditResultDetail(this.projectService.getVarApproveItem(varSelectIssues));
		}else{
			variation.setFinalAuditResultDetail(null);
		}
		String groupId = "file_add";
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadVarFile(projectType(), projectid, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		variation.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		uploadService.flush(groupId);
		String selectIssues = "";
		for(int i = 0; i < selectIssue.size(); i++){
			selectIssues += selectIssue.get(i) + ";";
		}
		if(selectIssues.indexOf("01") != -1){//变更项目成员
			ProjectApplication application = granted.getApplication();
			variation.setChangeMember(1);
			variation.setOldMembers(application.getMembers());
			//TODO 新成员的设置
			Document document = DocumentHelper.createDocument();
			Element membersElement = document.addElement("Members");
			for(MemberInfo mInfo : newMembers) {
				Element memberElement = membersElement.addElement("member");
				Element mtElement = memberElement.addElement("memberType");
				mtElement.setText(String.valueOf(mInfo.getMemberType()));
				Element mnElement = memberElement.addElement("memberName");
				mnElement.setText(mInfo.getMemberName());
				Element anElement = memberElement.addElement("agencyName");
				anElement.setText(mInfo.getAgencyName());
				Element dnElement = memberElement.addElement("divisionName");
				dnElement.setText(mInfo.getDivisionName());
				Element itElement = memberElement.addElement("idcardType");
				itElement.setText(mInfo.getIdcardType());
				Element inElement = memberElement.addElement("idcardNumber");
				inElement.setText(mInfo.getIdcardNumber());
				Element idElement = memberElement.addElement("isDirector");
				idElement.setText(String.valueOf(mInfo.getIsDirector()));
			}
			String xmlNewMembers = document.asXML();
			variation.setNewMembers(xmlNewMembers);
		}
		if(selectIssues.indexOf("02") != -1){//变更机构
			variation.setChangeAgency(1);
			variation.setOldAgencyName(oldAgencyName);
			variation.setNewAgencyName(newAgencyName);
		}
		if(selectIssues.indexOf("03")!= -1){//变更成果形式
			variation.setChangeProductType(1);
			String productTypeNames = this.projectService.getProductTypeNames(selectProductType);
			if(productTypeNames != null && productTypeNames.contains("其他")){
				variation.setNewProductTypeOther(this.projectService.MutipleToFormat(productTypeOther.trim()));
				if(granted.getProductType() != null && granted.getProductType().equals(productTypeNames) && productTypeOther.equals(granted.getProductTypeOther())){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_PRODUCT_TYPE_SAME);
				}
			}else{
				variation.setNewProductTypeOther(null);
				if(granted.getProductType() != null && granted.getProductType().equals(productTypeNames)){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_PRODUCT_TYPE_SAME);
				}
			}
			variation.setOldProductType(granted.getProductType());
			variation.setOldProductTypeOther(granted.getProductTypeOther());
			variation.setNewProductType(productTypeNames);
		}
		if(selectIssues.indexOf("04") != -1){//变更项目名称
			if(granted.getName() != null && granted.getName().equals(chineseName)){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_PROJECT_NAME_SAME);
			}
			variation.setChangeName(1);
			variation.setOldName(granted.getName());
			variation.setNewName(chineseName);
		}
		if(selectIssues.indexOf("05") != -1){//研究内容有重大调整
			variation.setChangeContent(1);
		}
		if(selectIssues.indexOf("06") != -1){//延期
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if(granted.getPlanEndDate() != null && df.format(granted.getPlanEndDate()).equals(df.format(newDate1))){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_END_TIME_SAME);
			}
			variation.setPostponement(1);
			variation.setOldOnceDate(granted.getPlanEndDate());
			variation.setNewOnceDate(newDate1);
			String groupId1 = "file_postponementPlan_add";
			List<String> files1 = new ArrayList<String>();
			for (FileRecord fileRecord : uploadService.getGroupFiles(groupId1)) {
				//只能上传一个，在js中作了限制
				File curFile = fileRecord.getOriginal();
				String savePath = this.projectService.uploadVarPlanfile(projectType(), projectid, curFile);
				//将文件放入list中暂存
				files1.add(savePath);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
			}
			variation.setPostponementPlanFile(StringTool.joinString(files1.toArray(new String[0]), "; "));
			uploadService.flush(groupId);
		}
		if(selectIssues.indexOf("07") != -1){//自行终止项目
			variation.setStop(1);
		}
		if(selectIssues.indexOf("08") != -1){//申请撤项
			variation.setWithdraw(1);
		}
		if(selectIssues.indexOf("09") != -1){//变更经费预算
			ProjectFee newProjectFee = new ProjectFee();
			ProjectFee oldProjectFee = new ProjectFee();
			newProjectFee = projectService.setProjectFee(newFee);
			if (granted.getProjectFee() != null) {
				oldProjectFee = (ProjectFee) baseService.query(ProjectFee.class, granted.getProjectFee().getId());
				variation.setOldProjectFee(oldProjectFee);
			}
			dao.add(newProjectFee);
			variation.setChangeFee(1);
			variation.setNewProjectFee(newProjectFee);
		}
		if(selectIssues.indexOf("10") != -1){//其他变更
			variation.setOther(1);
			variation.setOtherInfo(otherInfo);
		}
		baseService.add(variation);
		if(varResult == 2) {
			this.projectService.variationProject(variation);
		}
		return SUCCESS;
	}
	

	public ProjectVariation getVariation() {
		return variation;
	}

	public void setVariation(ProjectVariation variation) {
		this.variation = variation;
	}

	public String getVarId() {
		return varId;
	}

	public void setVarId(String varId) {
		this.varId = varId;
	}

	public Date getVarAuditDate() {
		return varAuditDate;
	}

	public void setVarAuditDate(Date varAuditDate) {
		this.varAuditDate = varAuditDate;
	}

	public int getVarAuditResult() {
		return varAuditResult;
	}

	public void setVarAuditResult(int varAuditResult) {
		this.varAuditResult = varAuditResult;
	}

	public String getVarAuditOpinion() {
		return varAuditOpinion;
	}

	public void setVarAuditOpinion(String varAuditOpinion) {
		this.varAuditOpinion = varAuditOpinion;
	}

	public String getVarAuditOpinionFeedback() {
		return varAuditOpinionFeedback;
	}

	public void setVarAuditOpinionFeedback(String varAuditOpinionFeedback) {
		this.varAuditOpinionFeedback = varAuditOpinionFeedback;
	}

	public String getVarAuditSelectIssue() {
		return varAuditSelectIssue;
	}

	public void setVarAuditSelectIssue(String varAuditSelectIssue) {
		this.varAuditSelectIssue = varAuditSelectIssue;
	}

	public List getVarItems() {
		return varItems;
	}

	public void setVarItems(List varItems) {
		this.varItems = varItems;
	}

	public Date getVarDate() {
		return varDate;
	}

	public void setVarDate(Date varDate) {
		this.varDate = varDate;
	}

	public ArrayList<Object> getVarListForSelect() {
		return varListForSelect;
	}

	public void setVarListForSelect(ArrayList<Object> varListForSelect) {
		this.varListForSelect = varListForSelect;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	public String getDefaultSelectCode() {
		return defaultSelectCode;
	}

	public void setDefaultSelectCode(String defaultSelectCode) {
		this.defaultSelectCode = defaultSelectCode;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOldAgencyName() {
		return oldAgencyName;
	}

	public void setOldAgencyName(String oldAgencyName) {
		this.oldAgencyName = oldAgencyName;
	}

	public String getNewAgencyName() {
		return newAgencyName;
	}

	public void setNewAgencyName(String newAgencyName) {
		this.newAgencyName = newAgencyName;
	}

	public ProjectFee getOldFee() {
		return oldFee;
	}

	public void setOldFee(ProjectFee oldFee) {
		this.oldFee = oldFee;
	}

	public String getDefaultSelectProductTypeCode() {
		return defaultSelectProductTypeCode;
	}

	public void setDefaultSelectProductTypeCode(String defaultSelectProductTypeCode) {
		this.defaultSelectProductTypeCode = defaultSelectProductTypeCode;
	}

	public String getOldProductTypeCode() {
		return oldProductTypeCode;
	}

	public void setOldProductTypeCode(String oldProductTypeCode) {
		this.oldProductTypeCode = oldProductTypeCode;
	}

	public String getProductTypeOther() {
		return productTypeOther;
	}

	public void setProductTypeOther(String productTypeOther) {
		this.productTypeOther = productTypeOther;
	}

	public String getOldProductTypeOther() {
		return oldProductTypeOther;
	}

	public void setOldProductTypeOther(String oldProductTypeOther) {
		this.oldProductTypeOther = oldProductTypeOther;
	}

	public Date getNewDate1() {
		return newDate1;
	}

	public void setNewDate1(Date newDate1) {
		this.newDate1 = newDate1;
	}

	public String getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public ProjectFee getNewFee() {
		return newFee;
	}

	public void setNewFee(ProjectFee newFee) {
		this.newFee = newFee;
	}

	public List<MemberInfo> getNewMembers() {
		return newMembers;
	}

	public void setNewMembers(List<MemberInfo> newMembers) {
		this.newMembers = newMembers;
	}

	public int getVarResult() {
		return varResult;
	}

	public void setVarResult(int varResult) {
		this.varResult = varResult;
	}

	public List<String> getSelectIssue() {
		return selectIssue;
	}

	public void setSelectIssue(List<String> selectIssue) {
		this.selectIssue = selectIssue;
	}

	public List<String> getVarSelectIssue() {
		return varSelectIssue;
	}

	public void setVarSelectIssue(List<String> varSelectIssue) {
		this.varSelectIssue = varSelectIssue;
	}

	public List<String> getSelectProductType() {
		return selectProductType;
	}

	public void setSelectProductType(List<String> selectProductType) {
		this.selectProductType = selectProductType;
	}

	public String getVarImportedOpinion() {
		return varImportedOpinion;
	}

	public void setVarImportedOpinion(String varImportedOpinion) {
		this.varImportedOpinion = varImportedOpinion;
	}

	public String getVarOpinionFeedback() {
		return varOpinionFeedback;
	}

	public void setVarOpinionFeedback(String varOpinionFeedback) {
		this.varOpinionFeedback = varOpinionFeedback;
	}

	public String getVariationReason() {
		return variationReason;
	}

	public void setVariationReason(String variationReason) {
		this.variationReason = variationReason;
	}
	
	
}
