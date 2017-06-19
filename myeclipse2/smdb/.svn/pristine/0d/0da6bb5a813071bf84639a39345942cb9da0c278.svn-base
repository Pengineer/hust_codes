package csdc.action.project.post;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.PostApplication;
import csdc.bean.PostFunding;
import csdc.bean.PostGranted;
import csdc.service.IPostService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;


/**
 * 社科项目申报申请评审审核管理
 * @author 肖雅
 */
public class ApplicationReviewAuditAction extends csdc.action.project.ApplicationReviewAuditAction {
	
	private static final long serialVersionUID = 1L;
	private static final String APPLICATION_CLASS_NAME = "PostApplication";
	private static final String GRANTED_CLASS_NAME = "PostGranted";
	private static final String PROJECT_TYPE = "post";
	private static final String BUSINESS_TYPE = "031";
	private static final int CHECK_APPLICATION_FLAG = 20;
	private static final int CHECK_GRANTED_FLAG = 23;
	
	private IPostService postService;
	
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
		if(!this.postService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		PostApplication application=(PostApplication)this.dao.query(PostApplication.class, entityId);
		application = (PostApplication) doWithAddOrModify(application);
		Date approveDate = this.projectService.setDateHhmmss(reviewAuditDate);//设置指定日期时分秒为当前系统时间时分秒
		application.setFinalAuditDate(approveDate);
		dao.modify(application);
		if(reviewAuditResult == 2){//同意立项
			PostGranted granted = new PostGranted();
			this.postService.setGrantedInfoFromApp(application, granted);
			this.projectService.doWithToAdd(granted);
			granted.setNumber(number);
			granted.setApproveFee(approveFee);
			granted.setApproveDate(approveDate);
			granted.setApplication(application);
			this.dao.add(granted);
			
			PostFunding postFunding = new PostFunding();
			//立项则添加立项拨款申请，金额默认为批准经费的50%
//			postFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
			postFunding.setGranted(granted);
			postFunding.setGrantedId(granted.getId());
			postFunding.setProjectType(application.getType());
			postFunding.setStatus(0);
			postFunding.setType(1);
			dao.add(postFunding);
			
		}
		return SUCCESS;
	}
	
	//修改评审审核
	@SuppressWarnings("unchecked")
	@Transactional
	public String modify(){
		if(!this.postService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		PostApplication application=(PostApplication)this.dao.query(PostApplication.class, entityId);
		application = (PostApplication) doWithAddOrModify(application);
		PostGranted granted = (PostGranted) this.postService.getGrantedByAppId(entityId);
		Date approveDate = this.projectService.setDateHhmmss(reviewAuditDate);//设置指定日期时分秒为当前系统时间时分秒
		application.setFinalAuditDate(approveDate);
		if(null == granted){
			if(reviewAuditResult == 2){//同意立项，新建立项对象
				PostGranted newGranted = new PostGranted();
				this.postService.setGrantedInfoFromApp(application, newGranted);
				this.projectService.doWithToAdd(granted);
				newGranted.setNumber(number);
				newGranted.setApproveFee(approveFee);
				newGranted.setApproveDate(approveDate);
				newGranted.setApplication(application);
				this.dao.add(newGranted);
				
				PostFunding postFunding = new PostFunding();
				//立项则添加立项拨款申请，金额默认为批准经费的50%
//				postFunding.setGrantedFundFee(DoubleTool.mul(newGranted.getApproveFee(),0.5));
				postFunding.setGranted(newGranted);
				postFunding.setGrantedId(newGranted.getId());
				postFunding.setProjectType(newGranted.getProjectType());
				postFunding.setStatus(0);
				postFunding.setType(1);
				dao.add(postFunding);
				
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
				PostFunding postGrantedFundingold = (PostFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
				if (postGrantedFundingold != null) {
					if (postGrantedFundingold.getStatus() == 0) {
						//立项则添加立项拨款申请，金额默认为批准经费的50%
//						postGrantedFundingold.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
						postGrantedFundingold.setGrantedId(granted.getId());
						postGrantedFundingold.setProjectType(granted.getProjectType());
						postGrantedFundingold.setStatus(0);
						dao.modify(postGrantedFundingold);
					}
				}else {
					PostFunding postGrantedFunding = new PostFunding();
					//立项则添加立项拨款申请，金额默认为批准经费的50%
//					postGrantedFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
					postGrantedFunding.setGranted(granted);
					postGrantedFunding.setGrantedId(granted.getId());
					postGrantedFunding.setProjectType(granted.getProjectType());
					postGrantedFunding.setStatus(0);
					postGrantedFunding.setType(1);
					dao.add(postGrantedFunding);
				}
				
			}
		}
		return SUCCESS;
	}
	
	public IPostService getPostService() {
		return postService;
	}

	public void setPostService(IPostService postService) {
		this.postService = postService;
	}
}
