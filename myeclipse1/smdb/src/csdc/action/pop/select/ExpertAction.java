package csdc.action.pop.select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 弹层--外部专家
 * @author 龚凡
 */
public class ExpertAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select e.id, p.name, e.agencyName, e.divisionName,p.id from Expert e left join e.person p where ";
	private static final String[] COLUMN = {
			"p.name",
			"e.agencyName, p.name",
			"e.divisionName, p.name"
		};// 用于拼接的排序列
	private static final String PAGE_NAME = "selectExpertPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "e.id";// 上下条查看时用于查找缓存的字段
	private String personName;

	public String pageName() {
		return ExpertAction.PAGE_NAME;
	}
	public String[] column() {
		return ExpertAction.COLUMN;
	}
	public String dateFormat() {
		return ExpertAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return ExpertAction.PAGE_BUFFER_ID;
	}

	/**
	 * 查询专家，条件变，排序不变
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
			hql.append(" not exists (from Account ac where ac.person.id = p.id) and ");
		}if(label == 3){
			hql.append(" 1=1 and ");
		}
		if (searchType == 1) {
			hql.append(" LOWER(p.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {
			hql.append(" LOWER(e.agencyName) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 3) {
			hql.append(" LOWER(e.divisionName) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {
			hql.append(" (LOWER(p.name) like :keyword0 or LOWER(e.agencyName) like :keyword1 or LOWER(e.divisionName) like :keyword2) ");
			map.put("keyword0", "%" + keyword + "%");
			map.put("keyword1", "%" + keyword + "%");
			map.put("keyword2", "%" + keyword + "%");
		}
		System.out.println("hql++++++++++ ");
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
//		this.simpleSearch(hql, map, " order by p.name asc, p.id asc", 0, 1, PAGE_NAME);
//		return SUCCESS;
	}

	@Override
	public Object[] advSearchCondition() {
		return null;
	}
//	select e.id, p.name, e.agencyName, e.divisionName,p.id from Expert e left join e.person p where 1=1 and  (LOWER(p.name) like :keyword0 or LOWER(e.agencyName) like :keyword1 or LOWER(e.divisionName) like :keyword2) order by p.name, e.id

	public String fetchAutoData(){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select e.id, p.name, e.agencyName, e.divisionName, p.id from Expert e left join e.person p where 1=1 ");
		if (personName!=null&&!personName.equals("")) {
			hql.append(" and LOWER(p.name) like :personName");
			map.put("personName", "%"+personName.toLowerCase()+"%");
		}else {
			hql.append(" and 1=0");
		}
		hql.append(" order by p.name, e.agencyName, e.divisionName, e.id");
		List autoData = dao.query(hql.toString(), map);
		long count = dao.count(hql.toString(), map);
		jsonMap.put("count", count);
		jsonMap.put("autoData", autoData);
		return SUCCESS;
	}
	
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
}