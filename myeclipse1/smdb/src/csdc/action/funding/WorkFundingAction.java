package csdc.action.funding;



public class WorkFundingAction extends FundingBaseAction{
	private static final long serialVersionUID = 7252266737062687012L;
	
	private static final String PAGE_NAME = "workFundingPage";// 列表页面名称
	private static final String FUNDING_TYPE = "workFunding";// 列表页面名称
	private static final String PAGE_BUFFER_ID = "fd.id";// 上下条查看时用于查找缓存的字段
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String[] COLUMN = {
		"fd.payee desc",
		"ag.name desc",
		"fd.fee desc",
		"fd.note desc"
	};

	public String pageName() {
		return WorkFundingAction.PAGE_NAME;
	}

	public String getFundingType() {
		return WorkFundingAction.FUNDING_TYPE;
	}

	public String[] column() {
		return WorkFundingAction.COLUMN;
	}

	public String dateFormat() {
		return WorkFundingAction.DATE_FORMAT;
	}

	public String pageBufferId() {
		return WorkFundingAction.PAGE_BUFFER_ID;
	}

}
