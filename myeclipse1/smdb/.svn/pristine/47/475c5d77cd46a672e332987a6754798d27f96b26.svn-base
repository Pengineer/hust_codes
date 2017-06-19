package csdc.action.project.special;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.SpecialApplication;
import csdc.bean.SpecialGranted;
import csdc.service.ISpecialService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;


/**
 * 专项任务项目申请申请评审审核管理
 * @author wangming
 */
public class ApplicationReviewAuditAction extends csdc.action.project.ApplicationReviewAuditAction {
	
	private static final long serialVersionUID = 1L;
	private static final String APPLICATION_CLASS_NAME = "SpecialApplication";
	private static final String GRANTED_CLASS_NAME = "SpecialGranted";
	private static final String PROJECT_TYPE = "special";
	private static final String BUSINESS_TYPE = "011";
	private static final int CHECK_APPLICATION_FLAG = 29;
	private static final int CHECK_GRANTED_FLAG = 30;
	
	private ISpecialService specialService;
	
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
		if(!this.specialService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		SpecialApplication application=(SpecialApplication)this.dao.query(SpecialApplication.class, entityId);
		application = (SpecialApplication) doWithAddOrModify(application);
		Date approveDate = this.projectService.setDateHhmmss(reviewAuditDate);
		application.setFinalAuditDate(approveDate);
		dao.modify(application);
		if(reviewAuditResult == 2){//同意立项
			SpecialGranted granted = new SpecialGranted();
			this.specialService.setGrantedInfoFromApp(application, granted);
			this.projectService.doWithToAdd(granted);
			granted.setNumber(number);
			granted.setApproveFee(approveFee);
			granted.setApproveDate(approveDate);
			granted.setApplication(application);
			this.dao.add(granted);
			
		}
		return SUCCESS;
	}
	
	//修改评审审核
	@SuppressWarnings("unchecked")
	@Transactional
	public String modify(){
		if(!this.specialService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		SpecialApplication application=(SpecialApplication)this.dao.query(SpecialApplication.class, entityId);
		application = (SpecialApplication) doWithAddOrModify(application);
		SpecialGranted granted = (SpecialGranted) this.specialService.getGrantedByAppId(entityId);
		Date approveDate = this.projectService.setDateHhmmss(reviewAuditDate);
		application.setFinalAuditDate(approveDate);
		if(null == granted){
			if(reviewAuditResult == 2){//同意立项，新建立项对象
				SpecialGranted newGranted = new SpecialGranted();
				this.specialService.setGrantedInfoFromApp(application, newGranted);
				this.projectService.doWithToAdd(granted);
				newGranted.setNumber(number);
				newGranted.setApproveFee(approveFee);
				newGranted.setApproveDate(approveDate);
				newGranted.setApplication(application);
				this.dao.add(newGranted);
				
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
			}
		}
		return SUCCESS;
	}
	public void setSpecialService(ISpecialService specialService) {
		this.specialService = specialService;
	}
	public ISpecialService getSpecialService() {
		return specialService;
	}
}
