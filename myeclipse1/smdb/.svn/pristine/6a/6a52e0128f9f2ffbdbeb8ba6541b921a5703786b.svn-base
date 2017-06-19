package csdc.action.security.account.department;

import java.util.HashMap;
import java.util.Map;

import csdc.action.security.account.AccountAction;
import csdc.tool.bean.AccountType;

/**
 * 院系主账号管理
 * @author 龚凡
 * @version 2011.04.07
 */
@SuppressWarnings("unchecked")
public class MainAction extends AccountAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL ="select a.id, pp.name, d.id, d.name, u.id, u.name, a.startDate, a.expireDate, a.status, a.lastLoginDate from Account a left join a.passport pp, Department d left join d.university u where a.department.id = d.id and a.type = 'DEPARTMENT' ";
	private static final String[] COLUMN = {
			"pp.name",
			"d.name, pp.name",
			"u.name, pp.name",
			"a.startDate, pp.name",
			"a.expireDate, pp.name",
			"a.status, pp.name",
			"a.lastLoginDate, pp.name"
	};// 排序列
	private static final String PAGE_NAME = "accountDepartmentMainPage";
	private static final AccountType SUB_CLASS_TYPE = AccountType.DEPARTMENT;// 账户模块便于复用的标志
	private static final int SUB_CLASS_PRINCIPAL = 1;

	public String[] column() {
		return MainAction.COLUMN;
	}
	public String pageName() {
		return MainAction.PAGE_NAME;
	}
	public String groupBy() {
		return "";
	}
	public AccountType getSubClassType() {
		return SUB_CLASS_TYPE;
	}
	public int getSubClassPrincipal() {
		return SUB_CLASS_PRINCIPAL;
	}

	/**
	 * 处理初级检索条件，拼装查询语句。
	 */
	public Object[] simpleSearchCondition() {
		if (keyword == null) {// 预处理关键字
			keyword = "";
		} else {
			keyword = keyword.toLowerCase();
		}
		
		// 拼接检索条件
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		hql.append(" and ");
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号查询所有院系主账号
			hql.append(" 1=1 ");
		} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号查询本省地方高校所有院系主账号
			hql.append(" u.subjection.id = :currentUnitId and u.type = 4 ");
			map.put("currentUnitId", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号查询本校所有院系主账号
			hql.append(" u.id = :currentUnitId ");
			map.put("currentUnitId", loginer.getCurrentBelongUnitId());
		} else {// 其它账号无法查询任何院系主账号
			hql.append(" 1=0 ");
		}
		hql.append(" and ");
		if (searchType == 1) {// 按账号名检索
			hql.append(" LOWER(pp.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {// 按高校名称检索
			hql.append(" LOWER(u.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 3) {// 按院系名称检索
			hql.append(" LOWER(d.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {// 按上述字段检索
			hql.append(" (LOWER(pp.name) like :keyword or LOWER(u.name) like :keyword or LOWER(d.name) like :keyword) ");
			map.put("keyword", "%" + keyword + "%");
		}
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
		// 调用初级检索功能
//		this.simpleSearch(hql, map, ORDER_BY, 2, 0, PAGE_NAME);
//		return SUCCESS;
	}

	/**
	 * 处理高级检索条件，拼装查询语句。
	 */
	public Object[] advSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		hql.append(" and ");
		
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号查询所有院系主账号
			hql.append(" 1=1 ");
		} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号查询本省地方高校所有院系主账号
			hql.append(" u.subjection.id = :currentUnitId and u.type = 4 ");
			map.put("currentUnitId", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号查询本校所有院系主账号
			hql.append(" u.id = :currentUnitId ");
			map.put("currentUnitId", loginer.getCurrentBelongUnitId());
		} else {// 其它账号无法查询任何院系主账号
			hql.append(" 1=0 ");
		}
		
		if (belongUnitName != null && !belongUnitName.isEmpty()) {// 按高校名称或院系名称检索
			belongUnitName = belongUnitName.toLowerCase();
			hql.append(" and (LOWER(d.name) like :belongUnitName or LOWER(u.name) like :belongUnitName) ");
			map.put("belongUnitName", "%" + belongUnitName + "%");
		}
		try {
			getMainAdvSearchHql(hql, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
		// 调用高级检索功能
//		this.advSearch(hql, map, ORDER_BY, 2, 0, PAGE_NAME);
//		return SUCCESS;
	}

}
