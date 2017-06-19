package csdc.action.award.moesocial;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.AwardApplication;
import csdc.tool.bean.AuditInfo;
import csdc.tool.info.AwardInfo;
import csdc.tool.info.GlobalInfo;

/**
 * 社科奖励公示数据审核管理
 * @author 余潜玉  王燕
 */
public class ApplicationPublicityAuditAction extends ApplicationAction {

	private static final long serialVersionUID = 7343785142346028615L;
	private String number;//证书编号
	private int year;//获奖年份
	private static final String PAGE_NAME="publicity";//公示及奖励列表页面名称
	public String pageName() {
		return ApplicationPublicityAuditAction.PAGE_NAME;
	}
	
	public String simpleSearch(){
		return null;
	}

	//准备奖励审核
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toAdd(){
		Map session = ActionContext.getContext().getSession();
	    Map<Integer, Integer> yearMap = awardService.getYearMap();
	    session.put("yearMap", yearMap);
		return SUCCESS;
	}

	//保存获奖审核
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String add(){
		for(int i = 0; i < entityIds.size(); i++){
			//判断是否在当前账号的管辖范围之内
			if(!this.awardService.checkIfUnderControl(loginer, entityIds.get(i),18, true)){
				jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
		}
		if(auditOpinion != null){//审核意见
			auditOpinion = ("A" + auditOpinion).trim().substring(1);
		}
		if(auditOpinionFeedback != null  && !auditOpinionFeedback.isEmpty()){//审核意见（反馈给负责人）
			auditOpinionFeedback = ("A" + auditOpinionFeedback).trim().substring(1);
		}
    	for(int i = 0; i < entityIds.size(); i++){
    		awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityIds.get(i));
    		if(awardApplication.getStatus() == 8 && awardApplication.getFinalAuditStatus() != 3){
    			if(auditResult == 2){//公示审核同意
    				awardApplication.setNumber(number);
        			awardApplication.setYear(year);
    			}else{
    				awardApplication.setNumber(null);
        			awardApplication.setYear(null);
    			}
    			awardApplication.setFinalAuditOpinionFeedback(auditOpinionFeedback);
    			Map auditMap = new HashMap();
    			//获得审核操作信息对象
    			AuditInfo auditInfo = this.awardService.getAuditInfo(loginer, auditResult, auditStatus, auditOpinion);
    			auditMap.put("auditInfo", auditInfo);
    			auditMap.put("isSubUni", 0);
    			awardApplication.edit(auditMap);//保存操作结果
    			dao.modify(awardApplication);
    			//公示审核同意则添加获奖信息记录
    			if(awardApplication.getFinalAuditStatus() == 3 && awardApplication.getFinalAuditResult() == 2){
    				this.awardService.addAward(awardApplication);
    			}
    		}
    	}
		return SUCCESS;
	}
	//校验添加公示审核
	@SuppressWarnings("unchecked")
	public void validateAdd(){
		String info = "";
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_AUDIT_NULL);
			info += AwardInfo.ERROR_AUDIT_NULL;
		}
		if(1 != auditResult && 2 != auditResult){
			this.addFieldError(GlobalInfo.ERROR_INFO,AwardInfo.ERROR_AUDIT_RESULT_NULL);
			info += AwardInfo.ERROR_AUDIT_RESULT_NULL;
		}
		if(auditOpinion.trim().length() > 200){
			this.addFieldError(GlobalInfo.ERROR_INFO,AwardInfo.ERROR_AUDIT_OPINION_OUT);
			info += AwardInfo.ERROR_AUDIT_OPINION_OUT;
		}
		if(auditOpinionFeedback.trim().length() > 200){
			this.addFieldError(GlobalInfo.ERROR_INFO,AwardInfo.ERROR_AUDIT_OPINION_OUT);
			info += AwardInfo.ERROR_AUDIT_OPINION_OUT;
		}
		if(auditResult == 2){
			if( "-1".equals(year)){
				this.addFieldError(GlobalInfo.ERROR_INFO,AwardInfo.ERROR_AWARD_YEAR_NULL);
				info += AwardInfo.ERROR_AWARD_YEAR_NULL;
			}
			if(audflag == 0){
				if(number == null || number.length() == 0){
					this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NUMBER_NULL);
					info += AwardInfo.ERROR_NUMBER_NULL;
				}else if(number.length() > 40){
					this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NUMBER_OUT);
					info += AwardInfo.ERROR_NUMBER_OUT;
				}else if( !this.awardService.isNumberUnique(entityIds.get(0), number)){//编号已存在
					this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NUMBER_EXIST);
					info += AwardInfo.ERROR_NUMBER_EXIST;
				}
			}
		}
		if(2 != auditStatus && 3 != auditStatus){
			this.addFieldError(GlobalInfo.ERROR_INFO,AwardInfo.ERROR_SUBMIT_STATUS_NULL);
			info += AwardInfo.ERROR_SUBMIT_STATUS_NULL;
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	//准备修改奖励审核
	public String toModify(){
		//判断是否在当前账号的管辖范围内
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityId.trim());
		auditResult = awardApplication.getFinalAuditResult();
		auditOpinion = awardApplication.getFinalAuditOpinion();
		auditOpinionFeedback = awardApplication.getFinalAuditOpinionFeedback();
		number = awardApplication.getNumber();
		if(awardApplication.getYear() != null){
			year = awardApplication.getYear();
		}
		return SUCCESS;
	}
	//校验修改公示审核
	public void validateToModify(){
		publicValidate(AwardInfo.ERROR_MODIFY_AUDIT_NULL);
	}
	
	//保存奖励审核
	@Transactional
	public String modify(){
		return this.add();
	}
	
	public void validateModify(){
		this.validateAdd();
	}
	
	//查看奖励审核
	public String view(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(),18, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityId.trim());
		return SUCCESS;
	}
	
	public void validateView(){
		publicValidate(AwardInfo.ERROR_VIEW_AWARDAPPLY_NULL);
	}
	
	//提交奖励审核
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submit(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(),18, true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityId.trim());
		if(!this.awardService.isNumberUnique(entityId, awardApplication.getNumber())){//编号已存在
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NUMBER_EXIST);
			return INPUT;
		}
		if (awardApplication.getStatus() == 8 && awardApplication.getFinalAuditStatus() != 3){
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.awardService.getAuditInfo(loginer, 0, 3, null);
			auditMap.put("auditInfo", auditInfo);
			auditMap.put("isSubUni", 0);
			awardApplication.submit(auditMap);//提交操作结果
			dao.modify(awardApplication);
			//公示审核同意则添加获奖信息记录
			if(awardApplication.getFinalAuditStatus() == 3 && awardApplication.getFinalAuditResult() == 2)
				this.awardService.addAward(awardApplication);
		}else{
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NO_RIGHRT);
		}
		return SUCCESS;
	}
	
	public void validateSubmit(){
		publicValidate(AwardInfo.ERROR_SUBMIT_AUDIT_NULL);
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
}