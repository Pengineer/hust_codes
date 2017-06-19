package csdc.action.system.log;

import java.util.HashMap;
import java.util.Map;

import csdc.action.BaseAction;

/**
 * 嵌入式日志，主要用于其它模块(account)嵌入日志列表功能。
 * @author 龚凡
 * @version 2011.04.11
 */
@SuppressWarnings("unchecked")
public class LogEmbedAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQLA = "select l.id, l.date, l.ip, l.eventDscription, a.type from Log l left join l.account a where l.account.id = :entityId  ";
	private static final String HQLP = "select l.id, l.date, l.ip, l.eventDscription, a.type from Log l left join l.account a where l.passport.id = :entityId ";
	private static final String[] COLUMN = {
			"l.date desc",
			"l.ip, l.date desc",
			"l.eventDscription, l.date desc",
			"a.type"
	};// 用于拼接的排序列
	private static final String PAGE_NAME = "logEmbedPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "l.id";// 上下条查看时用于查找缓存的字段
	private String accountId;// 用于检索的账号ID
	private String passportId;// 用于检索的账号ID
	public String pageName() {
		return LogEmbedAction.PAGE_NAME;
	}
	public String[] column() {
		return LogEmbedAction.COLUMN;
	}
	public String dateFormat() {
		return LogEmbedAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return LogEmbedAction.PAGE_BUFFER_ID;
	}

	/**
	 * 处理初级检索条件，拼装查询语句。
	 */
	@Override
	public Object[] simpleSearchCondition() {
		// 获取查询语句
		StringBuffer hql = new StringBuffer();
		// 设置查询参数
		Map map = new HashMap();
		if (null == passportId) {//账号页面的日志
			hql.append(HQLA);
		} else {//通行证查看页面的日志
			hql.append(HQLP);
		}
		map.put("entityId", entityId);
		hql.append("group by l.id, l.date, l.ip, l.eventDscription, a.type");
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}
	
	/**
	 * 获取列表数据，并放入jsonMap
	 * @author xuhan
	 * @return
	 */
	@Override
	public String list() {
		if (pageNumber <= 1) {
			super.toList();
		}
		super.list();
		return SUCCESS;
	}
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getPassportId() {
		return passportId;
	}
	public void setPassportId(String passportId) {
		this.passportId = passportId;
	}
	@Override
	public Object[] advSearchCondition() {
		return null;
	}

}
