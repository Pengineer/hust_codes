package csdc.action.funding;

import csdc.bean.ProjectFunding;

public class ProjectFundingListAction extends FundingListBaseAction{
	private static final long serialVersionUID = -6811628952522365091L;
	private static final String fundingListType = "project";
	private static final String PAGE_NAME = "ProjectFundingListPage";// 列表页面名称
	public String projectSubtypeId;
	public Double fee;
	
	public String pageName() {
		return ProjectFundingListAction.PAGE_NAME;
	}
	
	protected String getFundingListType(){
		return fundingListType;
	};
	
	public String delete(){
		for(int i=0;i<entityIds.size();i++){
			fundingService.deleteProjectFundingList(entityIds.get(i));
		}
		return SUCCESS;
	}
	
	public String add() {
		fundingService.addProjectFundingList(fundingBatchId, listName, note,
				year, rate, subType, subSubType, projectSubtypeId);
		return SUCCESS;
	}
	
	public String audit() {
		String attn;//经办人
		if (loginer.getCurrentPersonName()!=null) {
			attn = loginer.getCurrentPersonName();
		}else {
			attn = loginer.getCurrentBelongUnitName();
		}
		fundingService.auditProjectFundingList(entityId, attn);
//		fundingListService.addEmailToUnit(entityId,loginer);////按学校添加拨款通知邮件
		return SUCCESS;
	}
	
	public String toModifyFee() {
		ProjectFunding projectFunding = dao.query(ProjectFunding.class, entityId);
		fee = projectFunding.getFee();
		return SUCCESS;
	}
	
	public String modifyFee() {
		ProjectFunding projectFunding = dao.query(ProjectFunding.class, entityId);
		projectFunding.setFee(fee);
		dao.modify(projectFunding);
		return SUCCESS;
	}
	
	public String getProjectSubtypeId() {
		return projectSubtypeId;
	}

	public void setProjectSubtypeId(String projectSubtypeId) {
		this.projectSubtypeId = projectSubtypeId;
	}
	
	public Double getFee() {
		return fee;
	}
	
	public void setFee(Double fee) {
		this.fee = fee;
	}
	
}
