package csdc.action.pop.select;

import java.util.HashMap;
import java.util.Map;

/*
 * 选择选题
 * @author 肖雅
 */
public class TopicAction extends BaseAction {

	private static final long serialVersionUID = -7573122901693563620L;
	private static final String PAGE_NAME = "loadTopicPage";
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "tops.id";// 上下条查看时用于查找缓存的字段
	private static final String[] COLUMN = {
		"tops.name",
		"tops.year"
	};//排序列
	
	//项目选题查询语句
	private static final String HQL = "select tops.id, tops.name, tops.year from KeyTopic tops " + 
		"where tops.finalAuditStatus = 3 and tops.finalAuditResult = 2";
	
	@SuppressWarnings("unchecked")
	public Object[] simpleSearchCondition() {
	    keyword = (keyword == null) ? "" : keyword.toLowerCase();
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		hql.append(" and ");
		if (searchType == 1) {
			hql.append("LOWER(tops.name) like :keyword");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {
			hql.append("LOWER(tops.year) like :keyword");
			map.put("keyword", "%" + keyword + "%");
		} else {
			hql.append("(LOWER(tops.name) like :keyword or cast(tops.year as string) like :keyword)");
			map.put("keyword", "%" + keyword + "%");
		}
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
	}

	public String[] column() {
		return TopicAction.COLUMN;
	}
	public String dateFormat() {
		return TopicAction.DATE_FORMAT;
	}
	public String pageName() {
		return TopicAction.PAGE_NAME;
	}
	public String pageBufferId() {
		return TopicAction.PAGE_BUFFER_ID;
	}
	public Object[] advSearchCondition() {
		return null;
	}
}