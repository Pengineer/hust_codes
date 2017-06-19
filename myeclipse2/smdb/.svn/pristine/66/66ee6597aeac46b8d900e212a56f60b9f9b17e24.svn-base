package csdc.action.fundList;


public class GeneralGrantedFundListAction extends FundListBaseAction {
	/**
	 * 一般项目立项拨款清单管理
	 */
	private static final long serialVersionUID = -5843897364869611290L;
	private static final String PAGE_NAME = "generalGrantedFundPage";//列表页面名称
	private static final String PROJECT_TYPE = "general";//项目类别
	private static final String FUND_TYPE = "granted";//拨款类别
	//管理人员使用
	private static final String HQL2 = "from FundList fl where fl.projectType = 'general' and fl.fundType = 'granted' ";
	public String pageName() {
		return GeneralGrantedFundListAction.PAGE_NAME;
	}
	public String projectType(){
		return GeneralGrantedFundListAction.PROJECT_TYPE;
	}
	public String fundType(){
		return GeneralGrantedFundListAction.FUND_TYPE;
	}
	public String listHql2(){
		return GeneralGrantedFundListAction.HQL2;
	}
}