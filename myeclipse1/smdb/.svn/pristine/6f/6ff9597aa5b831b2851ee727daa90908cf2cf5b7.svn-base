package csdc.action.funding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csdc.action.BaseAction;
import csdc.bean.FundingBatch;
import csdc.bean.FundingList;
import csdc.service.IFundingService;
import csdc.tool.DoubleTool;
import csdc.tool.bean.AccountType;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class FundingListBaseAction extends BaseAction{
	private static final long serialVersionUID = -6811628952522365091L;
	
	protected static final String PAGE_BUFFER_ID = "fl.id";// 上下条查看时用于查找缓存的字段
	protected static final String PAGE_NAME = "fundingListPage";// 列表页面名称
	protected static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	protected static final String[] COLUMN = {
		"fl.name",
		"fl.year",
		"fl.type",
		"fl.subType"
	};
	protected String agencyId;
	protected String agencyName;
	protected Date fundingBatchDate;
	protected String fundingListId;
	protected String fundingBatchId;
	protected List fundingBatchList;
	protected FundingList fundingList;//清单
	protected FundingBatch fundingBatch;
	protected String listName;//清单名称
	protected String attn;//经办人
	protected Date createDate;//生成时间
	protected String note;//清单备注
	protected String subType;//清单类型子类[general：一般项目；instp：基地项目；post：后期资助项目；special:xxxx：专项任务项目（具体专项名称）；devrpt：发展报告项目；key：重大攻关项目]
	protected String subSubType;//2.1.1立项2.1.2中检2.1.3结项
	protected Double rate;//拨款比率
	protected Integer year;//拨款年度
	protected double totalFee;

	protected IFundingService fundingService;
	
	protected String getFundingListType(){
		return "all";
	};
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
//		session.remove(pageName());
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
		if (!getFundingListType().equals("all")) {
			hql.append("and fl.type = '"+getFundingListType()+"' ");
		}
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
	
	public String toAdd() {
		fundingBatchList = fundingService.getFundingBatchList();
		return SUCCESS;
	}
	
	public String toModify() {
		fundingBatchList = fundingService.getFundingBatchList();
		fundingList= dao.query(FundingList.class, entityId);
		fundingBatch = fundingList.getFundingBatch();
		return SUCCESS;
	}
	
	public String modify() {
		fundingList = dao.query(FundingList.class, entityId);
		if (fundingBatchId!=null) {
			fundingBatch= dao.query(FundingBatch.class, fundingBatchId);
			fundingList.setFundingBatch(fundingBatch);
		}
		if (listName!=null) {
			fundingList.setName(listName);
		}
		if (note!=null) {
			fundingList.setNote(note);
		}
		dao.modify(fundingList);
		return SUCCESS;
	}
	
	public String toView() {
		return SUCCESS;
	}
	
	public String view() {
		fundingList= dao.query(FundingList.class, entityId);
		if (fundingList.getType().equals("project")) {
			fundingList.setSubSubType(fundingService.convertFundingListSubSubType4project(fundingList.getSubType()));
			fundingList.setSubType(fundingService.convertProjectType(fundingList.getSubType()));
		}else if (fundingList.getType().equals("work")) {
			fundingList.setSubSubType(fundingService.convertFundingListSubType4work(fundingList.getSubType()));
			fundingList.setSubType(fundingService.convertFundingListSubType4work(fundingList.getSubType()));
		}
		fundingList.setType(fundingService.convertFundingListType(fundingList.getType()));
		fundingBatch= dao.query(FundingBatch.class, fundingList.getFundingBatch().getId());
		jsonMap.put("fundingList", fundingList);
		jsonMap.put("fundingBatch", fundingBatch);
		return SUCCESS;
	}
	
	@Override
	public String pageName() {
		return FundingListBaseAction.PAGE_NAME;
	}

	@Override
	public String[] column() {
		return FundingListBaseAction.COLUMN;
	}

	@Override
	public String dateFormat() {
		return FundingListBaseAction.DATE_FORMAT;
	}
	@Override
	public String pageBufferId() {
		return FundingListBaseAction.PAGE_BUFFER_ID;
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

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public FundingList getFundingList() {
		return fundingList;
	}

	public void setFundingList(FundingList fundingList) {
		this.fundingList = fundingList;
	}

	public FundingBatch getFundingBatch() {
		return fundingBatch;
	}

	public void setFundingBatch(FundingBatch fundingBatch) {
		this.fundingBatch = fundingBatch;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getAttn() {
		return attn;
	}

	public void setAttn(String attn) {
		this.attn = attn;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getSubSubType() {
		return subSubType;
	}

	public void setSubSubType(String subSubType) {
		this.subSubType = subSubType;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public static String getPageBufferId() {
		return PAGE_BUFFER_ID;
	}

	public IFundingService getFundingService() {
		return fundingService;
	}

	public void setFundingService(IFundingService fundingService) {
		this.fundingService = fundingService;
	}
	
}
