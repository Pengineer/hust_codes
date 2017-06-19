package csdc.action.mobile.funding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.service.IMobileFundingService;
import csdc.tool.DoubleTool;
import csdc.tool.bean.AccountType;

/**
 * mobile经费清单模块
 * @author wangming
 *
 */
public class MobileFundingListAction extends MobileFundingBaseAction{
	
	private static final long serialVersionUID = 1L;
	private static final String PAGE_NAME = "mobileFundingListPage";
	
	@Autowired
	private IMobileFundingService mobileFundingService;

	private String agencyId;//拨款单位id(可以传空，传空显示所有单位合并起来的清单)
	private String fundingBatchId;//拨款批次id
	private double totalFee;//所有清单合并的费用

	
	/**
	 * 拨款批次列表
	 * @return
	 */
	public String simpleSearch(){
		StringBuffer hql = new StringBuffer();
		HashMap map = new HashMap();
		fundingBatchId = mobileFundingService.getFundingBatchId(fundingBatchId);
		map.put("fundingBatchId", fundingBatchId);
		hql.append("select fl.id, fl.name, fl.year, fl.type, fl.subType from FundingList fl left join fl.fundingBatch fb where fb.id =:fundingBatchId ");
		if(loginer.getCurrentType().within(AccountType.MINISTRY_UNIVERSITY, AccountType.LOCAL_UNIVERSITY)){//高校管理人员
			agencyId = loginer.getAccount().getAgency().getId();
		}
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.equals("")){
			map.put("keyword", "%" + keyword + "%");
			hql.append("and (fl.name like :keyword or fl.type like :keyword or fl.subType like :keyword or to_char(fl.year) like :keyword) ");
		}
		search(hql, map);
		return SUCCESS;
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
			item = new String[o.length+2];
			for (int i = 0; i < o.length; i++) {
				if (o[i] == null) {// 如果字段值为空，则以""替换
					item[i] = "";
				} else {// 如果字段值非空，则做进一步判断
					if (o[i] instanceof Date) {// 如果字段为时间对象，则按照子类定义的时间格式格式化
						datestr = dateformat.format((Date) o[i]);
						item[i] = datestr;
					} else {// 如果字段非时间对象，则直接转化为字符串
						item[i] = o[i].toString();
					}
				}
			}
			if (item[3] != null&&item[3].equals("project")) {
				item[4] = mobileFundingService.convertProjectType(item[4]);
			} else if (item[3] != null&&item[3].equals("work")) {
				item[4] = mobileFundingService.convertFundingListSubType4work(item[4]);
			}
			item[3] = mobileFundingService.convertFundingListType(item[3]);
			
			Map data = mobileFundingService.getCountAndFee4FundingList((String) o[0], agencyId);
			item[o.length] = data.get("count").toString();
			item[o.length+1] = data.get("fee").toString();
			totalFee = DoubleTool.sum(totalFee, (Double) data.get("fee"));
			laData.add(item);
		}
	}
	
	/**
	 * 将列表功能的公共成员变量放入jsonMap对象中，
	 * 主要包括：列表数据、总记录数
	 */
	public void jsonListPut() {
		// 将列表相关的公共变量存入jsonMap对象
		jsonMap.put("laData", laData);
		jsonMap.put("totalRows", totalRows + "");
		jsonMap.put("totalPageNums", totalPageNums + "");
		
		jsonMap.put("totalFee", totalFee);
	}

	@Override
	public String pageName() {
		return PAGE_NAME;
	}
	
	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getFundingBatchId() {
		return fundingBatchId;
	}

	public void setFundingBatchId(String fundingBatchId) {
		this.fundingBatchId = fundingBatchId;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}
	
}
