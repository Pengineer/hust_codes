package csdc.action.fundList;



public class GeneralEndFundListAction extends FundListBaseAction {
	/**
	 * 一般项目结项拨款清单管理
	 */
	private static final long serialVersionUID = 6929019106903014921L;
	private static final String PAGE_NAME = "generalEndFundPage";//列表页面名称
	private static final String PROJECT_TYPE = "general";//项目类别
	private static final String FUND_TYPE = "end";//拨款类别
	//管理人员使用
	private static final String HQL2 = "from FundList fl where fl.projectType = 'general' and fl.fundType =     'end' ";
	public String pageName() {
		return GeneralEndFundListAction.PAGE_NAME;
	}
	public String projectType(){
		return GeneralEndFundListAction.PROJECT_TYPE;
	}
	public String fundType(){
		return GeneralEndFundListAction.FUND_TYPE;
	}
	public String listHql2(){
		return GeneralEndFundListAction.HQL2;
	}
}