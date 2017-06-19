package csdc.action.pop.select;

import java.util.HashMap;
import java.util.Map;

import csdc.tool.bean.AccountType;

/**
 * 弹层--院系单位
 * @author 龚凡
 */
public class DepartmentAction extends csdc.action.pop.select.BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select d.id, d.name, d.code, u.name from Department d left join d.university u where 1=1 ";
	private static final String HQL4Teacher = "select d.id, d.name, d.code, u.name from Teacher t left join t.person p left join t.department d left join d.university u where u.id = :universityId and p.name = :memberName ";
	private static final String HQL4Student = "select d.id, d.name, d.code, u.name from Student t left join t.person p left join t.department d left join d.university u where u.id = :universityId and p.name = :memberName ";
	private static final String[] COLUMN = {
			"d.name",
			"d.code, d.name",
			"u.name, d.name"
		};// 用于拼接的排序列
	private static final String PAGE_NAME = "selectDepartmentPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "d.id";// 上下条查看时用于查找缓存的字段
	private String universityId;
	private int type;
	private String memberName;
	public String pageName() {
		return DepartmentAction.PAGE_NAME;
	}
	public String[] column() {
		return DepartmentAction.COLUMN;
	}
	public String dateFormat() {
		return DepartmentAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return DepartmentAction.PAGE_BUFFER_ID;
	}

	/**
	 * 查询院系单位账号，条件变，排序不变
	 * @return
	 * @throws  
	 */
	
	@SuppressWarnings("unchecked")
	public Object[] simpleSearchCondition() {
		String personName = null;
		try {
			byte[] b1 = memberName.getBytes("ISO8859_1");
			personName = new String(b1, "UTF-8"); //编码解码相同，正常显示
			System.out.print(personName);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// 获得查询条件
		if (keyword == null) {
			keyword = "";
		} else {
			keyword = keyword.toLowerCase();
		}
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		if (type == 1) {//教师
			hql.append(HQL4Teacher);
			map.put("universityId", universityId);
			map.put("memberName", personName);
		} else if (type == 3) {//学生
			hql.append(HQL4Student);
			map.put("universityId", universityId);
			map.put("memberName", personName);
		} else {
			hql.append(HQL);
		}
		if(label == 1){
			hql.append(" not exists (from Account ac where ac.department.id = d.id) and ");
		}
		if(label == 2){
			hql.append(" u.id=:universityId and ");
			map.put("universityId", universityId);
			
		}else if(label==3){
			hql.append(" and ");
		}else{
			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或者部级账号
				hql.append(" 1=1 and ");
			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号
				hql.append(" u.type = 4 and u.subjection.id = :currentunitid and ");
				map.put("currentunitid", loginer.getCurrentBelongUnitId());
			} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
				hql.append(" u.id = :currentunitid and ");
				map.put("currentunitid", loginer.getCurrentBelongUnitId());
			} else if(loginer.getCurrentType().equals(AccountType.DEPARTMENT)){ //院系账号
				hql.append(" d.id =:deptId and ");
				map.put("deptId", loginer.getCurrentBelongUnitId());
			}else {
				hql.append(" 1=0 and ");
			}
		}
		if (searchType == 1) {
			hql.append(" LOWER(d.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {
			hql.append(" LOWER(d.code) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 3) {
			hql.append(" LOWER(u.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {
			hql.append(" (LOWER(d.name) like :keyword0 or LOWER(d.code) like :keyword1 or LOWER(u.name) like :keyword2) ");
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
//		this.simpleSearch(hql, map, " order by d.name asc, d.id asc", 0, 1, PAGE_NAME);
//		return SUCCESS;
	}
	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getUniversityId() {
		return universityId;
	}
	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}
}