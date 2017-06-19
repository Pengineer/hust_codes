package csdc.action.security.account;

import java.util.HashMap;
import java.util.Map;

import csdc.tool.bean.AccountType;

/**
 * 外部专家账号管理
 * @author 龚凡
 * @version 2011.04.07
 */
@SuppressWarnings("unchecked")
public class ExpertAction extends AccountAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select a.id, pp.name, p.id, p.name, a.startDate, a.expireDate, a.status, a.lastLoginDate from Account a left join a.passport pp, Expert e left join e.person p where a.person.id = p.id and a.type = 'EXPERT' and e.type = '专职人员' ";
	private static final String GROUP_BY = " group by a.id, pp.name, p.id, p.name, a.startDate, a.expireDate, a.status, a.lastLoginDate ";
	private static final String[] COLUMN = {
			"pp.name",
			"p.name, pp.name",
			"a.startDate, pp.name",
			"a.expireDate, pp.name",
			"a.status, pp.name",
			"a.lastLoginDate, pp.name"
	};// 排序列
	private static final String PAGE_NAME = "accountExpertPage";// 列表页面名称
	private static final AccountType SUB_CLASS_TYPE = AccountType.EXPERT;// 账户模块便于复用的标志
	private static final int SUB_CLASS_PRINCIPAL = 1;

	public String[] column() {
		return ExpertAction.COLUMN;
	}
	public String pageName() {
		return ExpertAction.PAGE_NAME;
	}
	public String groupBy() {
		return ExpertAction.GROUP_BY;
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
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号查询所有专家账号
			hql.append(" 1=1 ");
		} else {// 其它类型的账号无法查询任何专家账号
			hql.append(" 1=0 ");
		}
		hql.append(" and ");
		if (searchType == 1) {// 按账号名检索
			hql.append(" LOWER(pp.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {// 按专家姓名检索
			hql.append(" LOWER(p.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {// 按上述字段检索
			hql.append(" (LOWER(pp.name) like :keyword or LOWER(p.name) like :keyword) ");
			map.put("keyword", "%" + keyword + "%");
		}
		hql.append(GROUP_BY);// 拼接去重条件

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
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号查询所有专家账号
			hql.append(" 1=1 ");
		} else {// 其它类型的账号无法查询任何专家账号
			hql.append(" 1=0 ");
		}
		if (accountName != null && !accountName.isEmpty()) {// 按账号名检索
			accountName = accountName.toLowerCase();
			hql.append(" and LOWER(pp.name) like :accountName ");
			map.put("accountName", "%" + accountName + "%");
		}
		if (belongPersonName != null && !belongPersonName.isEmpty()) {// 按所属人员检索
			hql.append(" and LOWER(p.name) like :belongPersonName ");
			map.put("belongPersonName", "%" + belongPersonName + "%");
		}
		if (createDate1 != null) {// 账号创建时间查询起点
			hql.append(" and a.startDate > :createDate1");
			map.put("createDate1", createDate1);
		}
		if (createDate2 != null) {// 账号创建时间查询终点
			hql.append(" and a.startDate < :createDate2");
			map.put("createDate2", createDate2);
		}
		if (expireDate1 != null) {// 账号有效期查询起点
			hql.append(" and a.expireDate > :expireDate1");
			map.put("expireDate1", expireDate1);
		}
		if (expireDate2 != null) {// 账号有效期查询终点
			hql.append(" and a.expireDate < :expireDate2");
			map.put("expireDate2", expireDate2);
		}
		if (status == 1) {// 按账号状态检索
			hql.append(" and a.status = 1");
		} else if (status == 0) {
			hql.append(" and a.status = 0");
		}
		hql.append(GROUP_BY);
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
//		this.advSearch(hql, map, ORDER_BY, 2, 0, PAGE_NAME);// 调用高级检索功能
//		return SUCCESS;
	}

}
