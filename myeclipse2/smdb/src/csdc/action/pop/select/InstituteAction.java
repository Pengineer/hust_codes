package csdc.action.pop.select;

import java.util.HashMap;
import java.util.Map;

import csdc.tool.bean.AccountType;

/**
 * 弹层--基地单位
 * @author 龚凡
 */
public class InstituteAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select i.id, i.name, i.code, u.name from Institute i left join i.subjection u left join i.type sys where 1=1 ";
	private static final String HQL4Teacher = "select i.id, i.name, i.code, u.name from Teacher t left join t.person p left join t.institute i left join i.subjection u where u.id = :universityId and p.name = :memberName ";
	private static final String HQL4Student = "select i.id, i.name, i.code, u.name from Student s left join s.person p left join t.institute i left join i.subjection u where u.id = :universityId and p.name = :memberName ";
	private static final String[] COLUMN = {
			"i.name",
			"i.code, i.name",
			"u.name, i.name"
		};// 用于拼接的排序列
	private static final String PAGE_NAME = "selectInstitutePage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "i.id";// 上下条查看时用于查找缓存的字段
	private String universityId;
	private int type;
	private String memberName;
	

	public String getUniversityId() {
		return universityId;
	}
	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}
	public String pageName() {
		return InstituteAction.PAGE_NAME;
	}
	public String[] column() {
		return InstituteAction.COLUMN;
	}
	public String dateFormat() {
		return InstituteAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return InstituteAction.PAGE_BUFFER_ID;
	}

	/**
	 * 查询基地单位，条件变，排序不变
	 * @return
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
			hql.append(" not exists (from Account ac where ac.institute.id = i.id) and ");
		}
		if(label ==2){
			hql.append(" u.id=:universityId and ");
			map.put("universityId", universityId);
		}else if(label==3){
			hql.append(" and ");
		}else{
			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号
				hql.append(" 1=1 and ");
			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号
				hql.append(" (sys.code = '02' or sys.code = '03' or u.type = 4) and u.subjection.id = :currentunitid and ");
				map.put("currentunitid", loginer.getCurrentBelongUnitId());
			} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
				hql.append(" u.id = :currentunitid and ");
				map.put("currentunitid", loginer.getCurrentBelongUnitId());
			} else if(loginer.getCurrentType().equals(AccountType.INSTITUTE)){ //基地账号
				hql.append(" i.id =:instId and ");
				map.put("instId", loginer.getCurrentBelongUnitId());
			}else {
				hql.append(" 1=0 and ");
			}
		}
		if (searchType == 1) {
			hql.append(" LOWER(i.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {
			hql.append(" LOWER(i.code) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 3) {
			hql.append(" LOWER(u.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {
			hql.append(" (LOWER(i.name) like :keyword or LOWER(i.code) like :keyword or LOWER(u.name) like :keyword) ");
			map.put("keyword", "%" + keyword + "%");
		}
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
//		this.simpleSearch(hql, map, " order by i.name asc, i.id asc", 0, 1, PAGE_NAME);
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
}
