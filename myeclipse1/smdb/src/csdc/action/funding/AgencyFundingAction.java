package csdc.action.funding;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csdc.action.BaseAction;
import csdc.bean.FundingBatch;
import csdc.service.IFundingService;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class AgencyFundingAction extends BaseAction{

	private static final long serialVersionUID = 6552663483224776979L;
	private static final String PAGE_BUFFER_ID = "agf";// 上下条查看时用于查找缓存的字段
	private static final String PAGE_NAME = "agencyFundingPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String[] COLUMN = {
		"agf.agencyName desc",
		"ag.code asc",
		"agf.fee desc",
		"agf.count desc",
		"ba.accountName desc",
		"ba.accountNumber desc", 
		"ba.bankName desc"
	};
	private String fileFileName;
	private String fundingBatchId;
	private List fundingBatchList;
	protected IFundingService fundingService;//
	public String updateAgencyFee() {
		fundingBatchId = fundingService.getFundingBatchId(fundingBatchId);
		fundingService.updateAgencyFundingFee(fundingBatchId);
		return SUCCESS;
	}
	
	public String toList() {
		session.remove(pageName());
		fundingBatchId = fundingService.getFundingBatchId(fundingBatchId);
		fundingBatchList = fundingService.getFundingBatchList();
		return SUCCESS;
	}
	
	public Object[] simpleSearchCondition() {
		fundingBatchId = fundingService.getFundingBatchId(fundingBatchId);
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("fundingBatchId", fundingBatchId);
		hql.append("select agf.id, agf.agencyName, ag.code, agf.fee, agf.count, ba.accountName, ba.accountNumber, ba.bankName, ag.id from AgencyFunding agf left join agf.agency ag left join agf.fundingBatch fb left join agf.bankAccount ba where fb.id = :fundingBatchId ");
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.equals("")){
			map.put("keyword", "%" + keyword + "%");
			switch (searchType) {
			case 0:
				hql.append("and agf.agencyName like :keyword ");
				break;
			case 1:
				hql.append("and ag.code like :keyword ");
				break;
			default:
				hql.append("and (agf.agencyName like :keyword or ag.code like :keyword) ");
			}
		}
		hql.append("order by ag.code desc");
		return new Object[]{
			hql.toString(),
			map,
			1,
			null
		};
	}
	
	public String toExport(){
		return SUCCESS;
	}
	
	public InputStream getDownloadFile() throws UnsupportedEncodingException {
		fundingBatchId = fundingService.getFundingBatchId(fundingBatchId);
		FundingBatch fundingBatch = dao.query(FundingBatch.class, fundingBatchId);
		fileFileName = fundingBatch.getName()+".xls";
		fileFileName = new String(fileFileName.getBytes("UTF-8"), "ISO8859-1");
		return fundingService.commonExportExcel(fundingBatchId);
	}


	@Override
	public Object[] advSearchCondition() {
		return null;
	}
	
	@Override
	public String pageName() {
		return AgencyFundingAction.PAGE_NAME;
	}

	@Override
	public String[] column() {
		return AgencyFundingAction.COLUMN;
	}

	@Override
	public String dateFormat() {
		return AgencyFundingAction.DATE_FORMAT;
	}
	
	@Override
	public String pageBufferId() {
		return AgencyFundingAction.PAGE_BUFFER_ID;
	}

	public String getFundingBatchId() {
		return fundingBatchId;
	}

	public void setFundingBatchId(String fundingBatchId) {
		this.fundingBatchId = fundingBatchId;
	}

	public List getFundingBatchList() {
		return fundingBatchList;
	}

	public void setFundingBatchList(List fundingBatchList) {
		this.fundingBatchList = fundingBatchList;
	}

	public String getFileFileName() {
		return fileFileName;
	}
	
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public IFundingService getFundingService() {
		return fundingService;
	}

	public void setFundingService(IFundingService fundingService) {
		this.fundingService = fundingService;
	}
}
