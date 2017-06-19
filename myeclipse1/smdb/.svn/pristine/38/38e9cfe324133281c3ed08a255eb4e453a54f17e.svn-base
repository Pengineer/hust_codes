package csdc.action.project.general;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.GeneralApplication;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.service.IGeneralService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;


/**
 * 社科项目申请申请评审审核管理
 * @author 肖雅
 */
public class ApplicationReviewAuditAction extends csdc.action.project.ApplicationReviewAuditAction {
	
	private static final long serialVersionUID = 1L;
	private static final String APPLICATION_CLASS_NAME = "GeneralApplication";
	private static final String GRANTED_CLASS_NAME = "GeneralGranted";
	private static final String PROJECT_TYPE = "general";
	private static final String BUSINESS_TYPE = "011";
	private static final int CHECK_APPLICATION_FLAG = 14;
	private static final int CHECK_GRANTED_FLAG = 21;
	
	private IGeneralService generalService;
	
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
		if(!this.generalService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		GeneralApplication application=(GeneralApplication)this.dao.query(GeneralApplication.class, entityId);
		application = (GeneralApplication) doWithAddOrModify(application);
		Date approveDate = this.projectService.setDateHhmmss(reviewAuditDate);
		application.setFinalAuditDate(approveDate);
		dao.modify(application);
		if(reviewAuditResult == 2){//同意立项
			GeneralGranted granted = new GeneralGranted();
			this.generalService.setGrantedInfoFromApp(application, granted);
			this.projectService.doWithToAdd(granted);
			granted.setNumber(number);
			granted.setApproveFee(approveFee);
			granted.setApproveDate(approveDate);
			granted.setApplication(application);
			this.dao.add(granted);
			
//			GeneralFunding generalFunding = new GeneralFunding();
//			//立项则添加立项拨款申请，金额默认为批准经费的50%
////			generalFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//			generalFunding.setGranted(granted);
//			generalFunding.setGrantedId(granted.getId());
//			generalFunding.setProjectType(granted.getProjectType());
//			generalFunding.setStatus(0);
//			generalFunding.setType(1);
//			dao.add(generalFunding);
			
		}
		return SUCCESS;
	}
	
	//修改评审审核
	@SuppressWarnings("unchecked")
	@Transactional
	public String modify(){
		if(!this.generalService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		GeneralApplication application=(GeneralApplication)this.dao.query(GeneralApplication.class, entityId);
		application = (GeneralApplication) doWithAddOrModify(application);
		GeneralGranted granted = (GeneralGranted) this.generalService.getGrantedByAppId(entityId);
		Date approveDate = this.projectService.setDateHhmmss(reviewAuditDate);
		application.setFinalAuditDate(approveDate);
		if(null == granted){
			if(reviewAuditResult == 2){//同意立项，新建立项对象
				GeneralGranted newGranted = new GeneralGranted();
				this.generalService.setGrantedInfoFromApp(application, newGranted);
				this.projectService.doWithToAdd(granted);
				newGranted.setNumber(number);
				newGranted.setApproveFee(approveFee);
				newGranted.setApproveDate(approveDate);
				newGranted.setApplication(application);
				this.dao.add(newGranted);
				
//				GeneralFunding generalFunding = new GeneralFunding();
//				//立项则添加立项拨款申请，金额默认为批准经费的50%
////				generalFunding.setGrantedFundFee(DoubleTool.mul(newGranted.getApproveFee(),0.5));
//				generalFunding.setGranted(newGranted);
//				generalFunding.setGrantedId(newGranted.getId());
//				generalFunding.setProjectType(newGranted.getProjectType());
//				generalFunding.setStatus(0);
//				generalFunding.setType(1);
//				dao.add(generalFunding);
				
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
//				GeneralFunding generalGrantedFundingold = (GeneralFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
//				if (generalGrantedFundingold != null) {
//					if (generalGrantedFundingold.getStatus() == 0) {
//						//立项则添加立项拨款申请，金额默认为批准经费的50%
////						generalGrantedFundingold.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//						generalGrantedFundingold.setGrantedId(granted.getId());
//						generalGrantedFundingold.setProjectType(granted.getProjectType());
//						generalGrantedFundingold.setStatus(0);
//						dao.modify(generalGrantedFundingold);
//					}
//				}else {
//					GeneralFunding generalGrantedFunding = new GeneralFunding();
//					//立项则添加立项拨款申请，金额默认为批准经费的50%
////					generalGrantedFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//					generalGrantedFunding.setGranted(granted);
//					generalGrantedFunding.setGrantedId(granted.getId());
//					generalGrantedFunding.setProjectType(granted.getProjectType());
//					generalGrantedFunding.setStatus(0);
//					generalGrantedFunding.setType(1);
//					dao.add(generalGrantedFunding);
//				}
				
			}
		}
		return SUCCESS;
	}
	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}
	public IGeneralService getGeneralService() {
		return generalService;
	}
}
