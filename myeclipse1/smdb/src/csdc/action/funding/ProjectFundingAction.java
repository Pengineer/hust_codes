package csdc.action.funding;

public class ProjectFundingAction extends FundingBaseAction{
	private static final long serialVersionUID = -6811628952522365091L;
	
	private static final String PAGE_NAME = "projectFundingPage";// 列表页面名称
	private static final String FUNDING_TYPE = "projectFunding";// 列表页面名称
	private static final String PAGE_BUFFER_ID = "fd.id";// 上下条查看时用于查找缓存的字段
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String[] COLUMN = {
		"fd.payee desc",
		"ag.name desc",
		"fd.fee desc",
		"fd.note desc"
	};
	
	public String getFundingType() {
		return ProjectFundingAction.FUNDING_TYPE;
	}
	
	public String pageName() {
		return ProjectFundingAction.PAGE_NAME;
	}

	public String[] column() {
		return ProjectFundingAction.COLUMN;
	}

	public String dateFormat() {
		return ProjectFundingAction.DATE_FORMAT;
	}

	public String pageBufferId() {
		return ProjectFundingAction.PAGE_BUFFER_ID;
	}

}
