package csdc.service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import csdc.bean.FundingList;
import csdc.tool.bean.LoginInfo;

public interface IFundingService {
	public String getFundingBatchId(String fundingBatchId);

	public List getFundingBatchList();

	public FundingList addProjectFundingList(String fundingBatchId,
			String listName, String note, Integer year, Double rate,
			String subType, String subSubType, String projectSubtypeId);
	
	public FundingList addWorkFundingList(String fundingBatchId,
			String listName, String note, Integer year, String subType, String subSubType);
	public void deleteFunding(List<String> fundingIds, String fundingType);

	public String convertFundingListType(String type);

	public String convertFundingListSubType4work(String type);

	public String convertProjectType(String type);

	public String convertFundingListSubSubType4project(String type);

	public void updateAgencyFundingFee(String fundingBatchId);

	public double getAgencyFee4FundingListByListName(String fundingListName,
			String fundingBatchId, String agencyId);

	public double getAgencyFee4FundingListByListId(String fundingListId,
			String agencyId);

	public ByteArrayInputStream commonExportExcel(String fundingBatchId);

	public Map getCountAndFee4FundingList(String fundingListId, String agencyId);

	public void deleteProjectFundingList(String id);
	public void deleteWorkFundingList(String id);

	public FundingList auditProjectFundingList(String fundingListId, String attn);

	public void addEmailToUnit(String id, LoginInfo loginer);
}