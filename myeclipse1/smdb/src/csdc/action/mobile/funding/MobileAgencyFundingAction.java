package csdc.action.mobile.funding;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.service.IMobileFundingService;

/**
 * mobile各单位经费概况模块
 * @author wangming
 *
 */
public class MobileAgencyFundingAction extends MobileFundingBaseAction{
	
	private static final long serialVersionUID = 1L;
	private static final String PAGE_NAME = "mobileFundingPage"; 
	
	private String fundingBatchId;//拨款批次id
	
	@Autowired
	private IMobileFundingService mobileFundingService;
	

	/**
	 * 拨款概况列表
	 * @return
	 */
	public String simpleSearch(){
		StringBuffer hql = new StringBuffer();
		HashMap map = new HashMap();
		fundingBatchId = mobileFundingService.getFundingBatchId(fundingBatchId);
		map.put("fundingBatchId", fundingBatchId);
		hql.append("select agf.id, agf.agencyName, ag.code, agf.fee, ba.accountName, ba.accountNumber, ba.bankName, ag.id from AgencyFunding agf left join agf.bankAccount ba left join agf.agency ag left join agf.fundingBatch fb where fb.id = :fundingBatchId ");
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.equals("")){
			map.put("keyword", "%" + keyword + "%");
			hql.append("and (agf.agencyName like :keyword or ag.code like :keyword or to_char(agf.fee) like :keyword or ba.bankName like :keyword) ");
		}
		hql.append("order by ag.code desc");
		search(hql, map);
		return SUCCESS;
	}

	@Override
	public String pageName() {
		return PAGE_NAME;
	}

	public String getFundingBatchId() {
		return fundingBatchId;
	}

	public void setFundingBatchId(String fundingBatchId) {
		this.fundingBatchId = fundingBatchId;
	}
}
