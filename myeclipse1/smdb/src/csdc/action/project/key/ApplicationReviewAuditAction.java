package csdc.action.project.key;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.KeyApplication;
import csdc.bean.KeyFunding;
import csdc.bean.KeyGranted;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;


/**
 * 重大攻关招标申请评审审核管理
 * @author 肖雅
 */
public class ApplicationReviewAuditAction extends csdc.action.project.ApplicationReviewAuditAction {
	
	private static final long serialVersionUID = 1L;
	private static final String APPLICATION_CLASS_NAME = "KeyApplication";
	private static final String GRANTED_CLASS_NAME = "KeyGranted";
	private static final String PROJECT_TYPE = "key";
	private static final String BUSINESS_TYPE = "041";
	private static final int CHECK_APPLICATION_FLAG = 24;
	private static final int CHECK_GRANTED_FLAG = 25;
	
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
		if(!this.keyService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		KeyApplication application=(KeyApplication)this.dao.query(KeyApplication.class, entityId);
		application = (KeyApplication) doWithAddOrModify(application);
		Date approveDate = this.projectService.setDateHhmmss(reviewAuditDate);//设置指定日期时分秒为当前系统时间时分秒
		application.setFinalAuditDate(approveDate);
		dao.modify(application);
		if(reviewAuditResult == 2){//中标
			KeyGranted granted = new KeyGranted();
			this.keyService.setGrantedInfoFromApp(application, granted);
			this.projectService.doWithToAdd(granted);
			granted.setNumber(number);
			granted.setApproveFee(approveFee);
			granted.setApproveDate(approveDate);
			granted.setApplication(application);
			this.dao.add(granted);
			
//			KeyFunding keyFunding = new KeyFunding();
//			//立项则添加立项拨款申请，金额默认为批准经费的50%
////			keyFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//			keyFunding.setGranted(granted);
//			keyFunding.setGrantedId(granted.getId());
//			keyFunding.setProjectType(application.getType());
//			keyFunding.setStatus(0);
//			keyFunding.setType(1);
//			dao.add(keyFunding);
			
		}
		return SUCCESS;
	}
	
	//修改评审审核
	@SuppressWarnings("unchecked")
	@Transactional
	public String modify(){
		if(!this.keyService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		KeyApplication application=(KeyApplication)this.dao.query(KeyApplication.class, entityId);
		application = (KeyApplication) doWithAddOrModify(application);
		KeyGranted granted = (KeyGranted) this.keyService.getGrantedByAppId(entityId);
		Date approveDate = this.projectService.setDateHhmmss(reviewAuditDate);//设置指定日期时分秒为当前系统时间时分秒
		application.setFinalAuditDate(approveDate);
		if(null == granted){
			if(reviewAuditResult == 2){//中标，新建立项对象
				KeyGranted newGranted = new KeyGranted();
				this.keyService.setGrantedInfoFromApp(application, newGranted);
				this.projectService.doWithToAdd(granted);
				newGranted.setNumber(number);
				newGranted.setApproveFee(approveFee);
				newGranted.setApproveDate(approveDate);
				newGranted.setApplication(application);
				this.dao.add(newGranted);
				
//				KeyFunding keyFunding = new KeyFunding();
//				//立项则添加立项拨款申请，金额默认为批准经费的50%
////				keyFunding.setGrantedFundFee(DoubleTool.mul(newGranted.getApproveFee(),0.5));
//				keyFunding.setGranted(newGranted);
//				keyFunding.setGrantedId(newGranted.getId());
//				keyFunding.setProjectType(newGranted.getProjectType());
//				keyFunding.setStatus(0);
//				keyFunding.setType(1);
//				dao.add(keyFunding);
				
			}
		}else{
			if(reviewAuditResult == 1){//不中标
				this.dao.delete(granted);
			}else if(reviewAuditResult == 2){//中标，只修改其他字段
				granted.setNumber(number);
				granted.setApproveFee(approveFee);
				granted.setApproveDate(approveDate);
				granted.setApplication(application);
				this.dao.modify(granted);
				
//				Map parmap = new HashMap();
//				parmap.put("grantedId", granted.getId());
//				KeyFunding keyGrantedFundingold = (KeyFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
//				if (keyGrantedFundingold != null) {
//					if (keyGrantedFundingold.getStatus() == 0) {
//						//立项则添加立项拨款申请，金额默认为批准经费的50%
////						keyGrantedFundingold.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//						keyGrantedFundingold.setGrantedId(granted.getId());
//						keyGrantedFundingold.setProjectType(granted.getProjectType());
//						keyGrantedFundingold.setStatus(0);
//						dao.modify(keyGrantedFundingold);
//					}
//				}else {
//					KeyFunding keyGrantedFunding = new KeyFunding();
//					//立项则添加立项拨款申请，金额默认为批准经费的50%
////					keyGrantedFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//					keyGrantedFunding.setGranted(granted);
//					keyGrantedFunding.setGrantedId(granted.getId());
//					keyGrantedFunding.setProjectType(granted.getProjectType());
//					keyGrantedFunding.setStatus(0);
//					keyGrantedFunding.setType(1);
//					dao.add(keyGrantedFunding);
//				}
				
			}
		}
		return SUCCESS;
	}
}
