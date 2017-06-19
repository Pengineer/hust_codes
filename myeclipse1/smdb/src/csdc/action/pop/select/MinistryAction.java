package csdc.action.pop.select;

import java.util.HashMap;
import java.util.Map;

import csdc.tool.bean.AccountType;

/**
 * 弹层--部级单位
 * @author 龚凡
 */
public class MinistryAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select a.id, a.name, a.sname from Agency a where a.type = 1 and ";
	private static final String[] COLUMN = {
			"a.name",
			"a.sname, a.name"
		};// 用于拼接的排序列
	private static final String PAGE_NAME = "selectMinistryPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "a.id";// 上下条查看时用于查找缓存的字段

	public String pageName() {
		return MinistryAction.PAGE_NAME;
	}
	public String[] column() {
		return MinistryAction.COLUMN;
	}
	public String dateFormat() {
		return MinistryAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return MinistryAction.PAGE_BUFFER_ID;
	}

	/**
	 * 查询部级单位账号，条件变，排序不变
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
		if(label == 1) {//分配账号，需要排除已有账号的实体
			hql.append(" not exists (from Account ac where ac.agency.id = a.id) and ");
		}
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {// 系统管理员
			hql.append(" 1=1 and ");
		} else if (loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 部级账号
			hql.append(" a.subjection.id = :currentUnitId and ");
			map.put("currentUnitId", loginer.getCurrentBelongUnitId());
		} else {
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