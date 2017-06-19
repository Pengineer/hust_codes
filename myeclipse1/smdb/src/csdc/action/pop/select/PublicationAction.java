package csdc.action.pop.select;

import java.util.HashMap;
import java.util.Map;

/**
 * 弹层--选择刊物级别
 * @author 余潜玉
 */
public class PublicationAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select so.id, so.name, so.description from SystemOption so where so.isAvailable=1 and so.systemOption.id=:publicationLevelId ";
	private static final String[] COLUMN = {
			"so.name",
			"so.description, so.name"
		};
	private static final String PAGE_NAME = "selectPublicationPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "so.id";// 上下条查看时用于查找缓存的字段
	private String publicationLevelId;
	
	public String getPublicationLevelId() {
		return publicationLevelId;
	}
	public void setPublicationLevelId(String publicationLevelId) {
		this.publicationLevelId = publicationLevelId;
	}
	public String pageName() {
		return PublicationAction.PAGE_NAME;
	}
	public String[] column() {
		return PublicationAction.COLUMN;
	}
	public String dateFormat() {
		return PublicationAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return PublicationAction.PAGE_BUFFER_ID;
	}

	/**
	 * 查询院系单位账号，条件变，排序不变
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
		hql.append(" and ");
		map.put("publicationLevelId", publicationLevelId);
		if (searchType == 1) {
			hql.append(" LOWER(so.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {
			hql.append(" LOWER(so.description) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {
			hql.append(" (LOWER(so.name) like :keyword0 or LOWER(so.description) like :keyword1 ) ");
			map.put("keyword0", "%" + keyword + "%");
			map.put("keyword1", "%" + keyword + "%");
		}
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
//		this.simpleSearch(hql, map, " order by so.name asc, so.id asc", 0, 1, PAGE_NAME);
//		return SUCCESS;
	}
	@Override
	public Object[] advSearchCondition() {
		return null;
	}
}