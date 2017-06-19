package csdc.action.mobile.funding;

import java.util.HashMap;

/**
 * mobile拨款批次模块
 * @author wangming
 *
 */
public class MobileFundingBatchAction extends MobileFundingBaseAction{
	
	private static final long serialVersionUID = 1L;
	private static final String PAGE_NAME = "mobileFundingBatchPage";
	
	/**
	 * 拨款批次列表
	 * @return
	 */
	public String list(){
		StringBuffer hql = new StringBuffer();
		HashMap map = new HashMap();
		hql.append("select fb.id, fb.name, fb.date from FundingBatch fb order by fb.date desc");
		search(hql, map);
		return SUCCESS;
	}

	@Override
	public String pageName() {
		return PAGE_NAME;
	}
}
