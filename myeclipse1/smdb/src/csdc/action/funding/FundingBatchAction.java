package csdc.action.funding;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import csdc.action.BaseAction;
import csdc.bean.FundingBatch;

@SuppressWarnings("rawtypes")
public class FundingBatchAction extends BaseAction {

	private static final long serialVersionUID = 19952403447427832L;

	private static final String PAGE_BUFFER_ID = "fb.id";// 上下条查看时用于查找缓存的字段
	private static final String PAGE_NAME = "fundingBatchPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String[] COLUMN = { "fb.name", "fb.date" };

	private String fundingBatchName;
	private Date fundingBatchDate;
	private String fundingBatchId;
	private FundingBatch fundingBatch;

	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select fb.id, fb.name, fb.date from FundingBatch fb order by fb.date desc");
		return new Object[] { hql.toString(), map, 1, null };
	}
	public String toView() {
		return SUCCESS;
	}

	public String view() {
		fundingBatch = dao.query(FundingBatch.class, entityId);
		jsonMap.put("fundingBatch", fundingBatch);
		return SUCCESS;
	}
	
	public String toAdd() {
		return SUCCESS;
	}

	public String add() {
		fundingBatch = new FundingBatch();
		if (fundingBatchDate!=null) {
			fundingBatch.setDate(fundingBatchDate);
		}else {
			fundingBatch.setDate(new Date());
		}
		if (fundingBatchName!=null) {
			fundingBatch.setName(fundingBatchName);
		}
		entityId = dao.add(fundingBatch);
		return SUCCESS;
	}
	
	public String toModify() {
		fundingBatch = dao.query(FundingBatch.class, entityId);
		return SUCCESS;
	}

	public String modify() {
		fundingBatch = dao.query(FundingBatch.class, entityId);
		if (fundingBatchDate!=null) {
			fundingBatch.setDate(fundingBatchDate);
		}
		if (fundingBatchName!=null) {
			fundingBatch.setName(fundingBatchName);
		}
		dao.modify(fundingBatch);
		return SUCCESS;
	}

	public String delete() {		
		for (String entityId : entityIds) {
			dao.delete(FundingBatch.class, entityId);
		}
		return SUCCESS;
	}
	
	public Object[] advSearchCondition() {
		return null;
	}

	public String pageBufferId() {
		return FundingBatchAction.PAGE_BUFFER_ID;
	}

	public String pageName() {
		return FundingBatchAction.PAGE_NAME;
	}

	public String[] column() {
		return FundingBatchAction.COLUMN;
	}

	public String dateFormat() {
		return FundingBatchAction.DATE_FORMAT;
	}

	public String getFundingBatchId() {
		return fundingBatchId;
	}

	public void setFundingBatchId(String fundingBatchId) {
		this.fundingBatchId = fundingBatchId;
	}

	public String getFundingBatchName() {
		return fundingBatchName;
	}

	public void setFundingBatchName(String fundingBatchName) {
		this.fundingBatchName = fundingBatchName;
	}

	public Date getFundingBatchDate() {
		return fundingBatchDate;
	}

	public void setFundingBatchDate(Date fundingBatchDate) {
		this.fundingBatchDate = fundingBatchDate;
	}

	public FundingBatch getFundingBatch() {
		return fundingBatch;
	}

	public void setFundingBatch(FundingBatch fundingBatch) {
		this.fundingBatch = fundingBatch;
	}

}
