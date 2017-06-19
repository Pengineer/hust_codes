package csdc.action.security.account.ministry;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import csdc.action.security.account.AccountAction;
import csdc.tool.bean.AccountType;

/**
 * 部级主账号管理
 * @author 龚凡
 * @version 2011.04.07
 */
@SuppressWarnings("unchecked")
public class MainAction extends AccountAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select a.id, pp.name, ag.id, ag.name, a.startDate, a.expireDate, a.status, a.lastLoginDate from Account a left join a.passport pp, Agency ag where a.agency.id = ag.id and a.type = 'MINISTRY' ";
	private static final String[] COLUMN = {
			"pp.name",
			"ag.name, pp.name",
			"a.startDate, pp.name",
			"a.expireDate, pp.name",
			"a.status, pp.name",
			"a.lastLoginDate, pp.name"
	};// 排序列
	private static final String PAGE_NAME = "accountMinistryMainPage";
	private static final AccountType SUB_CLASS_TYPE = AccountType.MINISTRY;// 账户模块便于复用的标志
	private static final int SUB_CLASS_PRINCIPAL = 1;

	public static String getHql() {
		return HQL;
	}
	public static String[] getColumn() {
		return COLUMN;
	}
	public static String getPageName() {
		return PAGE_NAME;
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
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {// 系统管理员查询所有部级主账号
			hql.append(" 1=1 ");
		} else {// 其它账号无法查询任何部级主账号
			hql.append(" 1=0 ");
		}
		hql.append(" and ");
		if (searchType == 1) {// 按账号名检索
			hql.append(" LOWER(pp.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {// 按部门名检索
			hql.append(" LOWER(ag.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {// 按上述字段检索
			hql.append(" (LOWER(pp.name) like :keyword or LOWER(ag.name) like :keyword) ");
			map.put("keyword", "%" + keyword + "%");
		}
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}

	/**
	 * 处理高级检索条件，拼装查询语句。
	 */
	public Object[] advSearchCondition(){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		hql.append(" and ");
		
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {// 系统管理员查询所有部级主账号
			hql.append(" 1=1 ");
		} else {// 其它账号无法查询任何部级主账号
			hql.append(" 1=0 ");
		}
		
		if (belongUnitName != null && !belongUnitName.isEmpty()) {// 按部门名检索
			belongUnitName = belongUnitName.toLowerCase();
			hql.append(" and LOWER(ag.name) like :belongUnitName ");
			map.put("belongUnitName", "%" + belongUnitName + "%");
		}
		if (accountName != null && !accountName.isEmpty()) {// 按账号名检索
			accountName = accountName.toLowerCase();
			hql.append(" and LOWER(pp.name) like :accountName");
			map.put("accountName", "%" + accountName + "%");
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
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
	}
	
	
	@Override
	public String pageName(){
		return PAGE_NAME;
	}
	@Override
	public String[] column(){
		return COLUMN;
	}

}
