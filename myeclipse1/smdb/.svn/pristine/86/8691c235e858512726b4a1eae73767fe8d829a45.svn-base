package csdc.action.funding;


public class WorkFundingListAction extends FundingListBaseAction{
	private static final long serialVersionUID = -6811628952522365091L;

	private static final String PAGE_NAME = "workFundingListPage";// 列表页面名称
	private static final String fundingListType = "work";

	public String pageName() {
		return WorkFundingListAction.PAGE_NAME;
	}
	
	protected String getFundingListType(){
		return fundingListType;
	};
	
	public String delete(){
		for(int i=0;i<entityIds.size();i++){
			fundingService.deleteWorkFundingList(entityIds.get(i));
		}
		return SUCCESS;
	}
	
	public String add() {
		entityId = fundingService.addWorkFundingList(fundingBatchId, listName, note, year, subType, subSubType).getId();
		return SUCCESS;
	}
}
