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
	private static final String HQL = "select d.id, u.name, d.name from Department d left join d.university u where 1=1 ";
	private static final String HQL4Teacher = "select d.id, u.name, d.name from Department d left join d.university u where u.id = :universityId ";
	private static final String HQL4Student = "select d.id, u.name, d.name from Department d left join d.university u where u.id = :universityId ";
	private static final String[] COLUMN = {
			"u.name",
			"u.name, d.name"
		};// 用于拼接的排序列
	private static final String PAGE_NAME = "selectDepartmentPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "d.id";// 上下条查看时用于查找缓存的字段
	private String universityId;
	private int type;
	private String memberName;
	private String universityName;//查询的学校名称
	private String departmentName;//查询的院系名称
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
		if (universityName == null) {
			universityName = "";
		} else {
			universityName = universityName.toLowerCase();
		}
		if (departmentName == null) {
			departmentName = "";
		} else {
			departmentName = departmentName.toLowerCase();
		}
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		if (type == 1) {//教师
			hql.append(HQL4Teacher);
			map.put("universityId", universityId);
//			map.put("memberName", personName);
		} else if (type == 3) {//学生
			hql.append(HQL4Student);
			map.put("universityId", universityId);
//			map.put("memberName", personName);
		} else {
			hql.append(HQL);
		}
		if(label == 1){
			hql.append(" not exists (from Account ac where ac.department.id = d.id) and ");
		}
		if(label == 2){
			hql.append(" u.id=:universityId and ");
			map.put("universityId", universityId);
		}else{
			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或者部级账号
				hql.append(" and ");
			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号
				hql.append(" and u.type = 4 and u.subjection.id = :currentunitid and ");
				map.put("currentunitid", loginer.getCurrentBelongUnitId());
			} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
				hql.append(" and u.id = :currentunitid and ");
				map.put("currentunitid", loginer.getCurrentBelongUnitId());
			} else if(loginer.getCurrentType().equals(AccountType.DEPARTMENT)){ //院系账号
				hql.append(" and d.id =:deptId and ");
				map.put("deptId", loginer.getCurrentBelongUnitId());
			}else {
				hql.append(" and 1=0 and ");
			}
		}
		hql.append(" (LOWER(d.name) like :departmentName and LOWER(u.name) like :universityName) ");
		map.put("departmentName", "%" + departmentName + "%");
		map.put("universityName", "%" + universityName + "%");
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
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}