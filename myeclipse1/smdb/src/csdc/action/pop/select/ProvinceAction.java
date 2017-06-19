package csdc.action.pop.select;

import java.util.HashMap;
import java.util.Map;

import csdc.tool.bean.AccountType;

/**
 * 弹层--省级单位
 * @author 龚凡
 */
public class ProvinceAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select a.id, a.name, a.sname from Agency a where a.type = 2 and ";
	private static final String[] COLUMN = {
			"a.name",
			"a.sname, a.name"
		};// 用于拼接的排序列
	private static final String PAGE_NAME = "selectProvincePage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "a.id";// 上下条查看时用于查找缓存的字段

	public String pageName() {
		return ProvinceAction.PAGE_NAME;
	}
	public String[] column() {
		return ProvinceAction.COLUMN;
	}
	public String dateFormat() {
		return ProvinceAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return ProvinceAction.PAGE_BUFFER_ID;
	}

	/**
	 * 查询省级单位账号，条件变，排序不变
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object[] simpleSearchCondition() {
		// 获得查询条件
		if (keyword == null) {
			keyword = "";
		} else {
			keyword = keyword.toLowerCase();
		}
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		if(label == 1){
			hql.append(" not exists (from Account ac where ac.agency.id = a.id) and ");
		}
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号
			hql.append(" 1=1 and ");
		} else if(loginer.getCurrentType().equals(AccountType.PROVINCE) ) {
			hql.append(" a.id =:unitid and ");
			map.put("unitid", loginer.getCurrentBelongUnitId());
		}else{
			hql.append(" 1=0 and ");
		}
		if (searchType == 1) {
			hql.append(" LOWER(a.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {
			hql.append(" LOWER(a.sname) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {
			hql.append(" (LOWER(a.name) like :keyword or LOWER(a.sname) like :keyword) ");
			map.put("keyword", "%" + keyword + "%");
		}
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
//		this.simpleSearch(hql, map, " order by a.name asc, a.id asc", 0, 1, PAGE_NAME);
//		return SUCCESS;
	}
	@Override
	public Object[] advSearchCondition() {
		return null;
	}
}