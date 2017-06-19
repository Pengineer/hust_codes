package csdc.action.pop.select;

import java.util.HashMap;
import java.util.Map;

/**
 * 弹层--校级单位
 * @author 龚凡
 */
public class UniversityGroupAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select a.id, a.name, a.sname, ag.name from Agency a left join a.subjection ag where (a.type = 3 or a.type = 4) and ";
	private static final String[] COLUMN = {
			"a.name",
			"a.sname, a.name",
			"ag.name, a.name"
		};// 用于拼接的排序列
	private static final String PAGE_NAME = "selectUniversityGroupPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "a.id";// 上下条查看时用于查找缓存的字段

	public String pageName() {
		return UniversityGroupAction.PAGE_NAME;
	}
	public String[] column() {
		return UniversityGroupAction.COLUMN;
	}
	public String dateFormat() {
		return UniversityGroupAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return UniversityGroupAction.PAGE_BUFFER_ID;
	}

	/**
	 * 查询校级单位账号，条件变，排序不变
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
		if (searchType == 1) {
			hql.append(" LOWER(a.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {
			hql.append(" LOWER(a.sname) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 3) {
			hql.append(" LOWER(ag.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {
			hql.append(" (LOWER(a.name) like :keyword or LOWER(a.sname) like :keyword or LOWER(ag.name) like :keyword) ");
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