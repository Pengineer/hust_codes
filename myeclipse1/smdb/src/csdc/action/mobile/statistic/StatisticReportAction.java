package csdc.action.mobile.statistic;

import csdc.action.mobile.MobileAction;

/**
 * mobile统计分析报告模块
 * @author suwb
 *
 */
public class StatisticReportAction extends MobileAction{

	private static final long serialVersionUID = 1L;
	private static final String PAGENAME = "statisticReportPage";

	@Override
	public String pageName() {
		return PAGENAME;
	}

}
