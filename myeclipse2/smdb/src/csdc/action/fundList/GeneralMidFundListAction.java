package csdc.action.fundList;



public class GeneralMidFundListAction extends FundListBaseAction {
	/**
	 * 一般项目中检拨款清单管理
	 */
	private static final long serialVersionUID = -8697512346066048341L;
	private static final String PAGE_NAME = "generalMidFundPage";//列表页面名称
	private static final String PROJECT_TYPE = "general";//项目类别
	private static final String FUND_TYPE = "mid";//拨款类别
	//管理人员使用
	private static final String HQL2 = "from FundList fl where fl.projectType = 'general' and fl.fundType =     'mid' ";
	public String pageName() {
		return GeneralMidFundListAction.PAGE_NAME;
	}
	public String projectType(){
		return GeneralMidFundListAction.PROJECT_TYPE;
	}
	public String fundType(){
		return GeneralMidFundListAction.FUND_TYPE;
	}
	public String listHql2(){
		return GeneralMidFundListAction.HQL2;
	}
}