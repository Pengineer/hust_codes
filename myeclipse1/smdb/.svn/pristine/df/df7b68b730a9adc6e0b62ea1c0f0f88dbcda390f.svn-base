package csdc.action.funding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csdc.action.BaseAction;
import csdc.bean.FundingBatch;
import csdc.service.IFundingService;
import csdc.tool.DoubleTool;
import csdc.tool.bean.AccountType;

public class FundingListAction extends BaseAction{
	private static final long serialVersionUID = -6811628952522365091L;
	
	private static final String PAGE_BUFFER_ID = "fl.id";// 上下条查看时用于查找缓存的字段
	private static final String PAGE_NAME = "fundingListPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String[] COLUMN = {
		"fl.name",
		"fl.year",
		"fl.type",
		"fl.subType"
	};
	private String agencyId;
	private String agencyName;
	private Date fundingBatchDate;
	private String fundingListId;
	private String fundingBatchId;
	private List fundingBatchList;
	private IFundingService fundingService;
	private double totalFee;

	public String toList() {
		session.remove(pageName());
		fundingBatchId = fundingService.getFundingBatchId(fundingBatchId);
		fundingBatchList = fundingService.getFundingBatchList();
		if(loginer.getCurrentType().within(AccountType.MINISTRY_UNIVERSITY, AccountType.LOCAL_UNIVERSITY)){//高校管理人员
			agencyName = loginer.getAgency().getName();
			agencyId = loginer.getAgency().getId();
		}
		return SUCCESS;
	}
	
	@Override
	public String list() {
		fundingBatchId = fundingService.getFundingBatchId(fundingBatchId);
		return super.list();
	}

	public String toPopFundingListByAgency() {
		session.remove(pageName());
		fundingBatchId = fundingService.getFundingBatchId(fundingBatchId);
		fundingBatchList = fundingService.getFundingBatchList();
		return SUCCESS;
	}
	
	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		fundingBatchId = fundingService.getFundingBatchId(fundingBatchId);
		fundingBatchDate = dao.query(FundingBatch.class, fundingBatchId).getDate();
		jsonMap.put("fundingBatchDate", fundingBatchDate);
		map.put("fundingBatchId", fundingBatchId);
		hql.append("select fl.id, fl.name, fl.year, fl.type, fl.subType from FundingList fl left join fl.fundingBatch fb where fb.id =:fundingBatchId ");
		if(loginer.getCurrentType().within(AccountType.MINISTRY_UNIVERSITY, AccountType.LOCAL_UNIVERSITY)){//高校管理人员
			agencyId = loginer.getAccount().getAgency().getId();
		}
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.equals("")){
			map.put("keyword", "%" + keyword + "%");
			switch (searchType) {
			case 0:
				hql.append("and fl.name like :keyword ");
				break;
			case 1:
				hql.append("and to_char(fl.year) like :keyword ");
				break;
			case 2:
				hql.append("and fl.type like :keyword ");
				break;
			case 3:
				hql.append("and fl.subType like :keyword ");
				break;
			default:
				hql.append("and (fl.name like :keyword or fl.type like :keyword or fl.subType like :keyword or to_char(fl.year) like :keyword) ");
			}
		}
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}
	
	public void pageListDealWith() {
		laData = new ArrayList();// 处理之后的列表数据
		Object[] o;// 缓存查询结果的一行
		String[] item;// 缓存查询结果一行中的每一字段
		SimpleDateFormat dateformat = new SimpleDateFormat(dateFormat());// 时间格式化对象
		String datestr;// 格式化之后的时间字符串
		// 遍历初始查询列表数据，按照指定格式，格式化其中的时间字段，其它字段转化为字符串
		totalFee =0;
		for (Object p : pageList) {
			o = (Object[]) p;
			item = new String[7];
			for (int i = 0; i < 3; i++) {
				if (o[i] == null) {// 如果字段值为空，则以""替换
					item[i] = "";
				}else {// 如果字段值非空，则做进一步判断
					if (o[i] instanceof Date) {// 如果字段为时间对象，则按照子类定义的时间格式格式化
						datestr = dateformat.format((Date) o[i]);
						item[i] = datestr;
					} else {// 如果字段非时间对象，则直接转化为字符串
						item[i] = o[i].toString();
					}
				}
			}
			item[3] = fundingService.convertFundingListType(o[3].toString());
			if (o[3].toString().equals("work")) {
				item[4] = fundingService.convertFundingListSubType4work(o[4].toString());
			}else if (o[3].toString().equals("project")) {
				item[4] = fundingService.convertProjectType(o[4].toString());
			}else {
				item[4] = o[4].toString();
			}
			Map data = fundingService.getCountAndFee4FundingList((String) o[0], agencyId);
			item[o.length] = data.get("count").toString();
			item[o.length+1] = data.get("fee").toString();
			totalFee = DoubleTool.sum(totalFee, (Double) data.get("fee"));
			laData.add(item);
		}
	}
	
	public void jsonListPut() {
		// 将列表相关的公共变量存入jsonMap对象
		jsonMap.put("laData", laData);
		jsonMap.put("totalRows", totalRows);
		jsonMap.put("pageSize", pageSize);
		jsonMap.put("startPageNumber", startPageNumber);
		jsonMap.put("pageNumber", pageNumber);
		jsonMap.put("pageBackNumber", pageBackNumber);
		jsonMap.put("sortColumn", sortColumn);
		jsonMap.put("sortColumnLabel", sortColumnLabel);
		jsonMap.put("totalFee", totalFee);
		fundingBatchDate = dao.query(FundingBatch.class, fundingBatchId).getDate();
		jsonMap.put("fundingBatchDate", fundingBatchDate);
	}
	@Override
	public Object[] advSearchCondition() {
		return null;
	}
	@Override
	public String pageName() {
		return FundingListAction.PAGE_NAME;
	}

	@Override
	public String[] column() {
		return FundingListAction.COLUMN;
	}

	@Override
	public String dateFormat() {
		return FundingListAction.DATE_FORMAT;
	}
	@Override
	public String pageBufferId() {
		return FundingListAction.PAGE_BUFFER_ID;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public Date getFundingBatchDate() {
		return fundingBatchDate;
	}

	public void setFundingBatchDate(Date fundingBatchDate) {
		this.fundingBatchDate = fundingBatchDate;
	}

	public String getFundingListId() {
		return fundingListId;
	}

	public void setFundingListId(String fundingListId) {
		this.fundingListId = fundingListId;
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

	public IFundingService getFundingService() {
		return fundingService;
	}

	public void setFundingService(IFundingService fundingService) {
		this.fundingService = fundingService;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

}
