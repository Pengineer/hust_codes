package csdc.action.project.instp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.InstpApplication;
import csdc.bean.InstpFunding;
import csdc.bean.InstpGranted;
import csdc.service.IInstpService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;


/**
 * 社科项目申请申请评审审核管理
 * @author 肖雅
 */
public class ApplicationReviewAuditAction extends csdc.action.project.ApplicationReviewAuditAction {
	
	private static final long serialVersionUID = 1L;
	private static final String APPLICATION_CLASS_NAME = "InstpApplication";
	private static final String GRANTED_CLASS_NAME = "InstpGranted";
	private static final String PROJECT_TYPE = "instp";
	private static final String BUSINESS_TYPE = "021";
	private static final int CHECK_APPLICATION_FLAG = 19;
	private static final int CHECK_GRANTED_FLAG = 22;
	
	private IInstpService instpService;
	
	public String[] column() {
		return null;
	}
	public String pageName() {
		return null;
	}
	public String simpleSearch() {
		return null;
	}
	public String applicationClassName(){
		return ApplicationReviewAuditAction.APPLICATION_CLASS_NAME;
	}
	public String grantedClassName(){
		return ApplicationReviewAuditAction.GRANTED_CLASS_NAME;
	}
	public String projectType(){
		return ApplicationReviewAuditAction.PROJECT_TYPE;
	}
	public String businessType(){
		return ApplicationReviewAuditAction.BUSINESS_TYPE;
	}
	public int checkApplicationFlag(){
		return ApplicationReviewAuditAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag(){
		return ApplicationReviewAuditAction.CHECK_GRANTED_FLAG;
	}
	
	//添加评审审核
	@SuppressWarnings("unchecked")
	@Transactional
	public String add(){
		if(!this.instpService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		InstpApplication application=(InstpApplication)this.dao.query(InstpApplication.class, entityId);
		application = (InstpApplication) doWithAddOrModify(application);
		Date approveDate = this.projectService.setDateHhmmss(reviewAuditDate);//设置指定日期时分秒为当前系统时间时分秒
		application.setFinalAuditDate(approveDate);
		dao.modify(application);
		if(reviewAuditResult == 2){//同意立项
			InstpGranted granted = new InstpGranted();
			this.instpService.setGrantedInfoFromApp(application, granted);
			this.projectService.doWithToAdd(granted);
			granted.setNumber(number);
			granted.setApproveDate(approveDate);
			granted.setApproveFee(approveFee);
			granted.setApplication(application);
			this.dao.add(granted);
			
//			InstpFunding instpFunding = new InstpFunding();
//			//立项则添加立项拨款申请，金额默认为批准经费的50%
////			instpFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//			instpFunding.setGranted(granted);
//			instpFunding.setGrantedId(granted.getId());
//			instpFunding.setProjectType(application.getType());
//			instpFunding.setStatus(0);
//			instpFunding.setType(1);
//			dao.add(instpFunding);
			
		}
		return SUCCESS;
	}
	
	//修改评审审核
	@SuppressWarnings("unchecked")
	@Transactional
	public String modify(){
		if(!this.instpService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		InstpApplication application=(InstpApplication)this.dao.query(InstpApplication.class, entityId);
		application = (InstpApplication) doWithAddOrModify(application);
		InstpGranted granted = (InstpGranted) this.instpService.getGrantedByAppId(entityId);
		Date approveDate = this.projectService.setDateHhmmss(reviewAuditDate);//设置指定日期时分秒为当前系统时间时分秒
		application.setFinalAuditDate(approveDate);
		if(null == granted){
			if(reviewAuditResult == 2){//同意立项，新建立项对象
				InstpGranted newGranted = new InstpGranted();
				this.instpService.setGrantedInfoFromApp(application, newGranted);
				this.projectService.doWithToAdd(granted);
				newGranted.setNumber(number);
				newGranted.setApproveFee(approveFee);
				newGranted.setApproveDate(approveDate);
				newGranted.setApplication(application);
				this.dao.add(newGranted);
				
//				InstpFunding instpFunding = new InstpFunding();
//				//立项则添加立项拨款申请，金额默认为批准经费的50%
////				instpFunding.setGrantedFundFee(DoubleTool.mul(newGranted.getApproveFee(),0.5));
//				instpFunding.setGranted(newGranted);
//				instpFunding.setGrantedId(newGranted.getId());
//				instpFunding.setProjectType(newGranted.getProjectType());
//				instpFunding.setStatus(0);
//				instpFunding.setType(1);
//				dao.add(instpFunding);
				
			}
		}else{
			if(reviewAuditResult == 1){//不同意立项
				this.dao.delete(granted);
			}else if(reviewAuditResult == 2){//同意立项，只修改其他字段
				granted.setNumber(number);
				granted.setApproveFee(approveFee);
				granted.setApproveDate(approveDate);
				granted.setApplication(application);
				this.dao.modify(granted);
				
//				Map parmap = new HashMap();
//				parmap.put("grantedId", granted.getId());
//				InstpFunding instpGrantedFundingold = (InstpFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
//				if (instpGrantedFundingold != null) {
//					if (instpGrantedFundingold.getStatus() == 0) {
//						//立项则添加立项拨款申请，金额默认为批准经费的50%
////						instpGrantedFundingold.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//						instpGrantedFundingold.setGrantedId(granted.getId());
//						instpGrantedFundingold.setProjectType(granted.getProjectType());
//						instpGrantedFundingold.setStatus(0);
//						dao.modify(instpGrantedFundingold);
//					}
//				}else {
//					InstpFunding instpGrantedFunding = new InstpFunding();
//					//立项则添加立项拨款申请，金额默认为批准经费的50%
////					instpGrantedFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//					instpGrantedFunding.setGranted(granted);
//					instpGrantedFunding.setGrantedId(granted.getId());
//					instpGrantedFunding.setProjectType(granted.getProjectType());
//					instpGrantedFunding.setStatus(0);
//					instpGrantedFunding.setType(1);
//					dao.add(instpGrantedFunding);
//				}
				
			}
		}
		return SUCCESS;
	}
	
	public IInstpService getInstpService() {
		return instpService;
	}

	public void setInstpService(IInstpService instpService) {
		this.instpService = instpService;
	}
}
