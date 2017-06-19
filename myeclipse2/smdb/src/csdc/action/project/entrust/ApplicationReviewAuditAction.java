package csdc.action.project.entrust;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.EntrustApplication;
import csdc.bean.EntrustFunding;
import csdc.bean.EntrustGranted;
import csdc.service.IEntrustService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;


/**
 * 社科项目申报申请评审审核管理
 * @author 肖雅
 */
public class ApplicationReviewAuditAction extends csdc.action.project.ApplicationReviewAuditAction {
	
	private static final long serialVersionUID = 1L;
	private static final String APPLICATION_CLASS_NAME = "EntrustApplication";
	private static final String GRANTED_CLASS_NAME = "EntrustGranted";
	private static final String PROJECT_TYPE = "entrust";
	private static final String BUSINESS_TYPE = "051";
	private static final int CHECK_APPLICATION_FLAG = 27;
	private static final int CHECK_GRANTED_FLAG = 28;
	
	private IEntrustService entrustService;
	
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
		if(!this.entrustService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		EntrustApplication application=(EntrustApplication)this.dao.query(EntrustApplication.class, entityId);
		application = (EntrustApplication) doWithAddOrModify(application);
		Date approveDate = this.projectService.setDateHhmmss(reviewAuditDate);
		application.setFinalAuditDate(approveDate);
		dao.modify(application);
		if(reviewAuditResult == 2){//同意立项
			EntrustGranted granted = new EntrustGranted();
			this.entrustService.setGrantedInfoFromApp(application, granted);
			this.projectService.doWithToAdd(granted);
			granted.setNumber(number);
			granted.setApproveFee(approveFee);
			granted.setApproveDate(approveDate);
			granted.setApplication(application);
			this.dao.add(granted);
			
			EntrustFunding entrustFunding = new EntrustFunding();
			//立项则添加立项拨款申请，金额默认为批准经费的50%
//			entrustFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
			entrustFunding.setGranted(granted);
			entrustFunding.setGrantedId(granted.getId());
			entrustFunding.setProjectType(application.getType());
			entrustFunding.setStatus(0);
			entrustFunding.setType(1);
			dao.add(entrustFunding);
			
		}
		return SUCCESS;
	}
	
	//修改评审审核
	@SuppressWarnings("unchecked")
	@Transactional
	public String modify(){
		if(!this.entrustService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		EntrustApplication application=(EntrustApplication)this.dao.query(EntrustApplication.class, entityId);
		application = (EntrustApplication) doWithAddOrModify(application);
		EntrustGranted granted = (EntrustGranted) this.entrustService.getGrantedByAppId(entityId);
		Date approveDate = this.projectService.setDateHhmmss(reviewAuditDate);
		application.setFinalAuditDate(approveDate);
		if(null == granted){
			if(reviewAuditResult == 2){//同意立项，新建立项对象
				EntrustGranted newGranted = new EntrustGranted();
				this.entrustService.setGrantedInfoFromApp(application, newGranted);
				this.projectService.doWithToAdd(granted);
				newGranted.setNumber(number);
				newGranted.setApproveFee(approveFee);
				newGranted.setApproveDate(approveDate);
				newGranted.setApplication(application);
				this.dao.add(newGranted);
				
				EntrustFunding entrustFunding = new EntrustFunding();
				//立项则添加立项拨款申请，金额默认为批准经费的50%
//				entrustFunding.setGrantedFundFee(DoubleTool.mul(newGranted.getApproveFee(),0.5));
				entrustFunding.setGranted(newGranted);
				entrustFunding.setGrantedId(newGranted.getId());
				entrustFunding.setProjectType(application.getType());
				entrustFunding.setStatus(0);
				entrustFunding.setType(1);
				dao.add(entrustFunding);
				
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
				
				Map parmap = new HashMap();
				parmap.put("grantedId", granted.getId());
				EntrustFunding entrustGrantedFundingold = (EntrustFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
				if (entrustGrantedFundingold != null) {
					if (entrustGrantedFundingold.getStatus() == 0) {
						//立项则添加立项拨款申请，金额默认为批准经费的50%
//						entrustGrantedFundingold.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
						entrustGrantedFundingold.setGrantedId(granted.getId());
						entrustGrantedFundingold.setProjectType(granted.getProjectType());
						entrustGrantedFundingold.setStatus(0);
						dao.modify(entrustGrantedFundingold);
					}
				}else {
					EntrustFunding entrustGrantedFunding = new EntrustFunding();
					//立项则添加立项拨款申请，金额默认为批准经费的50%
//					entrustGrantedFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
					entrustGrantedFunding.setGranted(granted);
					entrustGrantedFunding.setGrantedId(granted.getId());
					entrustGrantedFunding.setProjectType(granted.getProjectType());
					entrustGrantedFunding.setStatus(0);
					entrustGrantedFunding.setType(1);
					dao.add(entrustGrantedFunding);
				}
				
			}
		}
		return SUCCESS;
	}
	public void setEntrustService(IEntrustService entrustService) {
		this.entrustService = entrustService;
	}
	public IEntrustService getEntrustService() {
		return entrustService;
	}
}
