package csdc.service;

import java.util.Map;

public interface IMobileFundingService extends IBaseService  {
	public String getFundingBatchId(String fundingBatchId);
	public Map getCountAndFee4FundingList(String fundingListId, String agencyId);
	public String convertFundingListType(String type);
	public String convertFundingListSubType4work(String type);
	public String convertProjectType(String type);
	public String convertFundingListSubSubType4project(String type);
}