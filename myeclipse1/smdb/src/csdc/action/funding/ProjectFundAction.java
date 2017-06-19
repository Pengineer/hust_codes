package csdc.action.funding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csdc.action.BaseAction;
import csdc.service.imp.FundingService;

public class ProjectFundAction extends BaseAction{
	private static final long serialVersionUID = -6811628952522365091L;
	
	private static final String PAGE_NAME = "projectFundPage";// 列表页面名称
	private static final String PAGE_BUFFER_ID = "app.id";// 上下条查看时用于查找缓存的字段
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String[] COLUMN = {
		"gra.name desc",
		"gra.number desc",
		"app.year desc",
		"gra.approveFee desc",
	};
	private static String HQL1 = "select distinct app.id, gra.id, gra.name, gra.number, gra.applicantId, " +
	"gra.applicantName, uni.id, uni.name, so.name, app.year, gra.approveFee, 'null','未拨款', 'null', '未拨款', 'null', '未拨款' " +
	"from ProjectGranted gra, ProjectApplication app left outer join gra.university uni left outer join gra.subtype so " +
	"where gra.applicationId = app.id ";
	
	private FundingService fundingService;
	public String pageName() {
		return ProjectFundAction.PAGE_NAME;
	}

	public String[] column() {
		return ProjectFundAction.COLUMN;
	}

	public String dateFormat() {
		return ProjectFundAction.DATE_FORMAT;
	}

	public String pageBufferId() {
		return ProjectFundAction.PAGE_BUFFER_ID;
	}

	@Override
	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL1);
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.equals("")){
			map.put("keyword", "%" + keyword + "%");
			switch (searchType) {
			case 0:
				hql.append("and gra.name like :keyword ");
				break;
			case 1:
				hql.append("and gra.number like :keyword ");
				break;
			case 2:
				hql.append("and to_char(app.year) like :keyword ");
				break;
			case 3:
				hql.append("and gra.applicantName like :keyword ");
				break;
			default:
				hql.append("and (gra.name like :keyword or gra.number like :keyword or to_char(app.year) like :keyword or gra.applicantName like :keyword) ");
			}
		}
		return new Object[]{
				hql.toString(),
				map,
				0,
				null
			};
	}

	@Override
	public Object[] advSearchCondition() {
		return null;
	}
	
	public void pageListDealWith() {
		laData = new ArrayList();// 处理之后的列表数据
		Object[] o;// 缓存查询结果的一行
		String[] item;// 缓存查询结果一行中的每一字段
		SimpleDateFormat dateformat = new SimpleDateFormat(dateFormat());// 时间格式化对象
		String datestr;// 格式化之后的时间字符串
		
		// 遍历初始查询列表数据，按照指定格式，格式化其中的时间字段，其它字段转化为字符串
		int length = ((Object[]) pageList.get(0)).length;
		for (Object p : pageList) {
			o = (Object[]) p;
			item = new String[length];
			for (int i = 0; i < length; i++) {
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
			List<Object[]> graFunds = dao.query("select pf.id, pf.fee from ProjectFunding pf where pf.grantedId =? and pf.type = 'granted' and pf.status = 1 ", o[1]);
			if (graFunds.size()>0) {
				item[length-6] = graFunds.get(0)[0].toString();
				item[length-5] = graFunds.get(0)[1].toString();
			}
			List<Object[]> midFunds = dao.query("select pf.id, pf.fee from ProjectFunding pf where pf.grantedId =? and pf.type = 'mid' and pf.status = 1 ", o[1]);
			if (midFunds.size()>0) {
				item[length-4] = midFunds.get(0)[0].toString();
				item[length-3] = midFunds.get(0)[1].toString();
			}
			List<Object[]> endFunds = dao.query("select pf.id, pf.fee from ProjectFunding pf where pf.grantedId =? and pf.type = 'end' and pf.status = 1 ", o[1]);
			if (endFunds.size()>0) {
				item[length-2] = endFunds.get(0)[0].toString();
				item[length-1] = endFunds.get(0)[1].toString();
			}
			laData.add(item);// 将处理好的数据存入laData
		}
	}

	public FundingService getFundingService() {
		return fundingService;
	}

	public void setFundingService(FundingService fundingService) {
		this.fundingService = fundingService;
	}
	
}
