package csdc.action.pop.select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csdc.tool.bean.AccountType;

/**
 * 弹层--教师
 * @author 龚凡
 */
public class TeacherAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select t.id, p.name, d.name, i.name, u.name, p.id from Teacher t left join t.person p left join t.department d left join t.institute i left join t.university u left join i.type sys where ";
	private static final String[] COLUMN = {
			"p.name",
			"u.name,p.name",
			"CONCAT(d.name, i.name), p.name"
		};// 用于拼接的排序列
	private static final String PAGE_NAME = "selectTeacherPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "t.id";// 上下条查看时用于查找缓存的字段
	private String personName;
	public String pageName() {
		return TeacherAction.PAGE_NAME;
	}
	public String[] column() {
		return TeacherAction.COLUMN;
	}
	public String dateFormat() {
		return TeacherAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return TeacherAction.PAGE_BUFFER_ID;
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
		Map map = new HashMap();
		StringBuffer hql = new StringBuffer();
		hql.append(HQL);
		if(label == 1){
			hql.append(" not exists (from Account ac where ac.person.id = p.id) and ");
		}
		if(label == 3){
			hql.append(" 1=1 ");
		}else{
			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号
				hql.append(" 1=1 ");
			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号
				hql.append(" (u.type = 4 or (sys.code = '02' or sys.code = '03') and u.type = 3) and u.subjection.id = :currentunitid ");
				map.put("currentunitid", loginer.getCurrentBelongUnitId());
			} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
				hql.append("u.id = :currentunitid ");
				map.put("currentunitid", loginer.getCurrentBelongUnitId());
			} else if (loginer.getCurrentType().equals(AccountType.DEPARTMENT)) {// 院系账号
				hql.append("d.id = :currentunitid ");
				map.put("currentunitid", loginer.getCurrentBelongUnitId());
			} else if (loginer.getCurrentType().equals(AccountType.INSTITUTE)) {// 研究机构账号
				hql.append("i.id = :currentunitid ");
				map.put("currentunitid", loginer.getCurrentBelongUnitId());
			} else {
				hql.append(" 1=0 ");
			}
		}
		if (keyword != null && !keyword.trim().isEmpty()){
			if (searchType == 1) {
				hql.append(" and LOWER(p.name) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {
				hql.append(" and LOWER(u.name) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 3) {
				hql.append(" and LOWER(CONCAT(d.name, i.name)) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else {
				hql.append(" and (LOWER(p.name) like :keyword or LOWER(u.name) like :keyword or LOWER(CONCAT(d.name, i.name)) like :keyword) ");
				map.put("keyword", "%" + keyword + "%");
			}
		}
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
	
	public String fetchAutoData(){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select t.id, p.name, d.id, d.name, i.id, i.name, u.id, u.name, p.id from Teacher t left join t.person p left join t.department d left join t.institute i left join t.university u left join i.type sys where 1=1");
		if (personName!=null&&!personName.equals("")) {
			hql.append(" and LOWER(p.name) like :personName");
			map.put("personName", "%"+personName.toLowerCase()+"%");
		}else {
			hql.append(" and 1=0");
		}
		hql.append(" order by p.name, u.name, d.name, t.id");
		List autoData = dao.query(hql.toString(), map, 0, 30);
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
