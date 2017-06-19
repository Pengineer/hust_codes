package csdc.action.project.key;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.action.project.ProjectBaseAction;
import csdc.bean.Account;
import csdc.bean.KeyTopicSelection;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 项目选题申报审核管理
 * @author 肖雅
 */
@SuppressWarnings("unchecked")
public class TopicSelectionApplyAuditAction extends ProjectBaseAction{
	
	private static final long serialVersionUID = 1L;
	private static final String BUSINESS_TYPE = "046";//重大攻关项目选题业务编号
	private static final String PROJECT_TYPE = "key";
	private static final int CHECK_APPLICATION_FLAG = 24;
	private static final int CHECK_GRANTED_FLAG = 25;
	private static final int CHECK_TOPIC_SELECTION_FLAG = 26;
	private int topsAuditStatus;//选题申报状态
	private String topsAuditOpinion;//选题申报意见
	private int topsAuditResult;//选题申报结果
	private String topsId;//选题id
	private String topsAuditPerson;//选题申报审核人
	private Date topsAuditDate;//选题申报审核时间
	private int vtp;//查看审核参数
	
	
	public String businessType() {
		return TopicSelectionApplyAuditAction.BUSINESS_TYPE;
	}
	public int checkApplicationFlag() {
		return TopicSelectionApplyAuditAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag() {
		return TopicSelectionApplyAuditAction.CHECK_GRANTED_FLAG;
	}
	public String projectType() {
		return TopicSelectionApplyAuditAction.PROJECT_TYPE;
	}
	
	
	/**
	 * 查看项目选题
	 */
	public String view() {
		Account account = loginer.getAccount();
		AccountType accountType = loginer.getCurrentType();
		if(!this.keyService.checkIfUnderControl(loginer, topsId, CHECK_TOPIC_SELECTION_FLAG, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		KeyTopicSelection topicSelection = (KeyTopicSelection) this.dao.query(KeyTopicSelection.class, topsId);
		if(vtp == 1) {
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && ((null != topicSelection.getDeptInstAuditorDept() && account.getDepartment().getId().equals(topicSelection.getDeptInstAuditorDept().getId()) || (null != topicSelection.getDeptInstAuditorInst() && account.getInstitute().getId().equals(topicSelection.getDeptInstAuditorInst().getId())))))){
				topsAuditOpinion = topicSelection.getDeptInstAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != topicSelection.getDeptInstAuditor() && account.getOfficer().getId().equals(topicSelection.getDeptInstAuditor().getId()))) {
				topsAuditOpinion = topicSelection.getDeptInstAuditOpinion();
			}else {
				topsAuditOpinion = "";
			}
			topsAuditStatus = topicSelection.getDeptInstAuditStatus();
			topsAuditPerson = topicSelection.getDeptInstAuditorName();
			topsAuditDate = topicSelection.getDeptInstAuditDate();
			topsAuditResult = topicSelection.getDeptInstAuditResult();
		}
		else if(vtp == 2) {
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != topicSelection.getUniversityAuditorAgency() && account.getAgency().getId().equals(topicSelection.getUniversityAuditorAgency().getId()))) {
				topsAuditOpinion = topicSelection.getUniversityAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != topicSelection.getUniversityAuditor() && account.getOfficer().getId().equals(topicSelection.getUniversityAuditor().getId()))) {
				topsAuditOpinion = topicSelection.getUniversityAuditOpinion();
			}else {
				topsAuditOpinion = "";
			}
			topsAuditStatus = topicSelection.getUniversityAuditStatus();
			topsAuditPerson = topicSelection.getUniversityAuditorName();
			topsAuditDate = topicSelection.getUniversityAuditDate();
			topsAuditResult = topicSelection.getUniversityAuditResult();
		}
		else if(vtp == 3) {
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != topicSelection.getProvinceAuditorAgency() && account.getAgency().getId().equals(topicSelection.getProvinceAuditorAgency().getId()))) {
				topsAuditOpinion = topicSelection.getProvinceAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != topicSelection.getProvinceAuditor() && account.getOfficer().getId().equals(topicSelection.getProvinceAuditor().getId()))) {
				topsAuditOpinion = topicSelection.getProvinceAuditOpinion();
			}else {
				topsAuditOpinion = "";
			}
			topsAuditStatus = topicSelection.getProvinceAuditStatus();
			topsAuditPerson = topicSelection.getProvinceAuditorName();
			topsAuditDate = topicSelection.getProvinceAuditDate();
			topsAuditResult = topicSelection.getProvinceAuditResult();
		}
		else if(vtp == 4) {
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != topicSelection.getFinalAuditorAgency() && account.getAgency().getId().equals(topicSelection.getFinalAuditorAgency().getId()))) {
				topsAuditOpinion = topicSelection.getFinalAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != topicSelection.getFinalAuditor() && account.getOfficer().getId().equals(topicSelection.getFinalAuditor().getId()))) {
				topsAuditOpinion = topicSelection.getFinalAuditOpinion();
			}else {
				topsAuditOpinion = "";
			}
			topsAuditStatus = topicSelection.getFinalAuditStatus();
			topsAuditPerson = topicSelection.getFinalAuditorName();
			topsAuditDate = topicSelection.getFinalAuditDate();
			topsAuditResult = topicSelection.getFinalAuditResult();
		}
		return SUCCESS;
	}
	
	/**
	 * 准备添加审核
	 */
	public String toAdd() {
		AccountType accountType = loginer.getCurrentType();
		if(!this.keyService.checkIfUnderControl(loginer, topsId, CHECK_TOPIC_SELECTION_FLAG, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//选题业务状态
		topsStatus = this.keyService.getBusinessStatus(businessType());
		//选题各级别审核截止时间
		deadline  = this.keyService.checkIfTimeValidate(accountType, businessType());
		return SUCCESS;
	}
	
	/**
	 * 添加选题意见，选题状态，批量时用id串拼接
	 */
	
	@SuppressWarnings("rawtypes")
	@Transactional
	public String add() {
		AccountType accountType = loginer.getCurrentType();
		String[] gids = topsId.split(", ");
		for(int i = 0; i < gids.length; i++) {
			if(!this.keyService.checkIfUnderControl(loginer, gids[i].trim(), CHECK_TOPIC_SELECTION_FLAG, true)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
			KeyTopicSelection topicSelection = (KeyTopicSelection) this.dao.query(KeyTopicSelection.class, gids[i]);
			int topsType = topicSelection.getStatus();
			if(topicSelection.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && topsType == 5) || (accountType.equals(AccountType.PROVINCE) && topsType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && topsType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && topsType==2))){
				if(topsAuditOpinion != null){
					topsAuditOpinion = ("A"+topsAuditOpinion).trim().substring(1);
				}else{
					topsAuditOpinion = null;
				}
				int isSubUni = 0;
	        	if(topsType == 3){
	        		isSubUni = this.keyService.isSubordinateUniversityTopicSelection(gids[i]);
	        	}
	        	Map auditMap = new HashMap();
	        	AuditInfo auditInfo = this.keyService.getAuditInfo(loginer, topsAuditResult, topsAuditStatus, topsAuditOpinion);
	        	auditMap.put("auditInfo", auditInfo);
	        	auditMap.put("isSubUni", isSubUni);
	        	topicSelection.edit(auditMap);
				this.dao.modify(topicSelection);
			}
		}
		return SUCCESS;
	}
	
	public void validateAdd(){
		this.validateEdit(1);
	}
	
	/**
	 * 准备修改审核
	 */
	public String toModify() {
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		if(!this.keyService.checkIfUnderControl(loginer, topsId, CHECK_TOPIC_SELECTION_FLAG, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//选题业务状态
		topsStatus = this.keyService.getBusinessStatus(businessType());
		//选题各级别审核截止时间
		deadline  = this.keyService.checkIfTimeValidate(accountType, businessType());
		KeyTopicSelection topicSelection = (KeyTopicSelection) this.dao.query(KeyTopicSelection.class, topsId);
		//高校院系或研究机构
		if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			if (account.getIsPrincipal() == 1 && ((null != topicSelection.getDeptInstAuditorDept() && account.getDepartment().getId().equals(topicSelection.getDeptInstAuditorDept().getId()) || (null != topicSelection.getDeptInstAuditorInst() && account.getInstitute().getId().equals(topicSelection.getDeptInstAuditorInst().getId()))))){
				topsAuditOpinion = topicSelection.getDeptInstAuditOpinion();
				topsAuditStatus = topicSelection.getDeptInstAuditStatus();
				topsAuditResult = topicSelection.getDeptInstAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != topicSelection.getDeptInstAuditor() && account.getOfficer().getId().equals(topicSelection.getDeptInstAuditor().getId())) {
				topsAuditOpinion = topicSelection.getDeptInstAuditOpinion();
				topsAuditStatus = topicSelection.getDeptInstAuditStatus();
				topsAuditResult = topicSelection.getDeptInstAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		//地方或部署高校
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			if (account.getIsPrincipal() == 1 && null != topicSelection.getUniversityAuditorAgency() && account.getAgency().getId().equals(topicSelection.getUniversityAuditorAgency().getId())) {
				topsAuditOpinion = topicSelection.getUniversityAuditOpinion();
				topsAuditStatus = topicSelection.getUniversityAuditStatus();
				topsAuditResult = topicSelection.getUniversityAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != topicSelection.getUniversityAuditor() && account.getOfficer().getId().equals(topicSelection.getUniversityAuditor().getId())) {
				topsAuditOpinion = topicSelection.getUniversityAuditOpinion();
				topsAuditStatus = topicSelection.getUniversityAuditStatus();
				topsAuditResult = topicSelection.getUniversityAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
			
		}
		//省厅
		else if(accountType.equals(AccountType.PROVINCE)) {
			if (account.getIsPrincipal() == 1 && null != topicSelection.getProvinceAuditorAgency() && account.getAgency().getId().equals(topicSelection.getProvinceAuditorAgency().getId())) {
				topsAuditOpinion = topicSelection.getProvinceAuditOpinion();
				topsAuditStatus = topicSelection.getProvinceAuditStatus();
				topsAuditResult = topicSelection.getProvinceAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != topicSelection.getProvinceAuditor() && account.getOfficer().getId().equals(topicSelection.getProvinceAuditor().getId())) {
				topsAuditOpinion = topicSelection.getProvinceAuditOpinion();
				topsAuditStatus = topicSelection.getProvinceAuditStatus();
				topsAuditResult = topicSelection.getProvinceAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		//教育部
		else if(accountType.equals(AccountType.MINISTRY)) {
			if (account.getIsPrincipal() == 1 && null != topicSelection.getFinalAuditorAgency() && account.getAgency().getId().equals(topicSelection.getFinalAuditorAgency().getId())) {
				topsAuditOpinion = topicSelection.getFinalAuditOpinion();
				topsAuditStatus = topicSelection.getFinalAuditStatus();
				topsAuditResult = topicSelection.getFinalAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != topicSelection.getFinalAuditor() && account.getOfficer().getId().equals(topicSelection.getFinalAuditor().getId())) {
				topsAuditOpinion = topicSelection.getFinalAuditOpinion();
				topsAuditStatus = topicSelection.getFinalAuditStatus();
				topsAuditResult = topicSelection.getFinalAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 修改选题审核
	 */
	@Transactional
	public String modify(){
		return this.add();
	}
	
	public void validateModify(){
		this.validateEdit(2);
	}
	
	/**
	 * 退回申请
	 */
	@Transactional
	public String backAudit(){
		AccountType accountType =loginer.getCurrentType();
		if(!this.keyService.checkIfUnderControl(loginer, topsId, CHECK_TOPIC_SELECTION_FLAG, true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		KeyTopicSelection topicSelection = (KeyTopicSelection) this.dao.query(KeyTopicSelection.class, topsId);
		int isSubUni = keyService.isSubordinateUniversityTopicSelection(topsId);
		int topsType = topicSelection.getStatus();
		if(topicSelection.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && topsType == 5) || (accountType.equals(AccountType.PROVINCE) && topsType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && topsType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && topsType==2))){
			topicSelection.back(isSubUni);
			/* 以下代码为高校跳过部门直接退回到申请者 */
			if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				topicSelection.back(0);
			}
			if(topicSelection.getUniversity() != null && topicSelection.getUniversity().getId() != null && ((accountType.equals(AccountType.MINISTRY) && topsType == 5 && isSubUni == 1) || (accountType.equals(AccountType.PROVINCE) && topsType == 4 && isSubUni != 1))){
				topicSelection.setUniversitySubmitStatus(1);
				topicSelection.setUniversityAuditStatus(0);
			}
			/* 结束 */
			dao.modify(topicSelection);
		}
//		if(topicSelection.getUniversity().getId() != null && ((accountType.equals(AccountType.MINISTRY) && topsType == 5) || (accountType.equals(AccountType.PROVINCE) && topsType == 4))){
//			if(isSubUni == 1 && topsType == 5){
//				topicSelection.setStatus(3);
//			}else{
//				topicSelection.setStatus(topsType - 1);
//			}
//			if(topsType == 5){//部级
//				if(isSubUni == 1) {
//					topicSelection.setUniversitySubmitStatus(1);
//				}
//				else if(isSubUni == 0) {
//					topicSelection.setProvinceAuditStatus(1);
//				}
//				topicSelection.setFinalAuditStatus(0);
//				topicSelection.setFinalAuditResult(0);
//				topicSelection.setFinalAuditOpinion(null);
//				topicSelection.setFinalAuditorName(null);
//				topicSelection.setFinalAuditDate(null);
//				topicSelection.setFinalAuditor(null);
//				topicSelection.setFinalAuditorAgency(null);
//			}
//			else if(topsType == 4){//省厅
//				topicSelection.setUniversitySubmitStatus(1);
//				topicSelection.setProvinceAuditStatus(0);
//				topicSelection.setProvinceAuditResult(0);
//				topicSelection.setProvinceAuditOpinion(null);
//				topicSelection.setProvinceAuditorName(null);
//				topicSelection.setProvinceAuditDate(null);
//				topicSelection.setProvinceAuditor(null);
//				topicSelection.setProvinceAuditorAgency(null);
//			}
//		}
		return SUCCESS;
	}
	
	public void validateBackAudit(){
		this.validateEdit(3);
	}
	/**
	 * 提交审核
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public String submit(){
		AccountType accountType = loginer.getCurrentType();
		if(!this.keyService.checkIfUnderControl(loginer, topsId, CHECK_TOPIC_SELECTION_FLAG, true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		KeyTopicSelection topicSelection = (KeyTopicSelection) this.dao.query(KeyTopicSelection.class, topsId);
		int topsType = topicSelection.getStatus();
		if(topicSelection.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && topsType == 5) || (accountType.equals(AccountType.PROVINCE) && topsType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && topsType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && topsType==2))){
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.keyService.getAuditInfo(loginer, 0, 3, null);
			auditMap.put("auditInfo", auditInfo);
			auditMap.put("isSubUni", this.keyService.isSubordinateUniversityTopicSelection(topsId));
			topicSelection.submit(auditMap);
			dao.modify(topicSelection);
		}
		return SUCCESS;
	}
	
	public void validateSubmit(){
		this.validateEdit(4);
	}
	
	/**
	 * 用于检验审核
	 * @param type 1：添加	2：修改	3：退回	4：提交
	 */
	public void validateEdit(int type){
		String info ="";
		if (topsId == null || topsId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_TOPIC_AUDIT_SUBMIT_NULL);
			info += ProjectInfo.ERROR_TOPIC_AUDIT_SUBMIT_NULL;
		}
		//校验业务设置状态
		topsStatus = this.keyService.getBusinessStatus(businessType());
		if (topsStatus == 0){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BUSINESS_STOP);
			info += ProjectInfo.ERROR_BUSINESS_STOP;
		}
		//校验业务时间是否有效
		AccountType accountType = loginer.getCurrentType();
		Date date = new Date();
		deadline = this.keyService.checkIfTimeValidate(accountType, businessType());
		if (deadline != null && deadline.compareTo(new SimpleDateFormat("yyyy-MM-dd").format(date)) < 0){//业务已过截止时间
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_TIME_INVALIDATE);
			info += ProjectInfo.ERROR_TIME_INVALIDATE;
		}
		if(type == 1 || type == 2){
			if(topsAuditStatus!=1 && topsAuditStatus!=2 && topsAuditStatus!=3){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(topsAuditResult!=1 && topsAuditResult!=2){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(topsAuditOpinion!=null && topsAuditOpinion.length()>200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_AUDIT_OPINION_OUT);
				info += ProjectInfo.ERROR_MID_AUDIT_OPINION_OUT;
			}
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}

	
	public int getTopsAuditStatus() {
		return topsAuditStatus;
	}
	public void setTopsAuditStatus(int topsAuditStatus) {
		this.topsAuditStatus = topsAuditStatus;
	}
	public String getTopsAuditOpinion() {
		return topsAuditOpinion;
	}
	public void setTopsAuditOpinion(String topsAuditOpinion) {
		this.topsAuditOpinion = topsAuditOpinion;
	}
	public int getTopsAuditResult() {
		return topsAuditResult;
	}
	public void setTopsAuditResult(int topsAuditResult) {
		this.topsAuditResult = topsAuditResult;
	}
	public String getTopsId() {
		return topsId;
	}
	public void setTopsId(String topsId) {
		this.topsId = topsId;
	}
	public String getTopsAuditPerson() {
		return topsAuditPerson;
	}
	public void setTopsAuditPerson(String topsAuditPerson) {
		this.topsAuditPerson = topsAuditPerson;
	}
	public Date getTopsAuditDate() {
		return topsAuditDate;
	}
	public void setTopsAuditDate(Date topsAuditDate) {
		this.topsAuditDate = topsAuditDate;
	}
	public int getVtp() {
		return vtp;
	}
	public void setVtp(int vtp) {
		this.vtp = vtp;
	}

}
