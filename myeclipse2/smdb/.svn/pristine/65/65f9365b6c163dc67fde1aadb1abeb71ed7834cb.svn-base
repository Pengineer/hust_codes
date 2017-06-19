package csdc.action.pop.select;

import java.util.HashMap;
import java.util.Map;

import csdc.tool.bean.AccountType;

/**
 * 弹层--下属单位人员
 * @author 龚凡
 */
public class OfficerAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select o.id, p.name, o.position , ag.name from Officer o left join o.person p left join o.agency ag ";
	private static final String[] COLUMN = {
			"p.name",
			"o.position, ag.name"
		};// 用于拼接的排序列
	private static final String PAGE_NAME = "selectOfficerPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private String unitId;// 查看的单位ID
	private int type;// 选择的管理人员类别(2,3,4,5,6,7)
	private static final String PAGE_BUFFER_ID = "o.id";// 上下条查看时用于查找缓存的字段

	public String pageName() {
		return OfficerAction.PAGE_NAME;
	}
	public String[] column() {
		return OfficerAction.COLUMN;
	}
	public String dateFormat() {
		return OfficerAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return OfficerAction.PAGE_BUFFER_ID;
	}

	/**
	 * 查询下属单位人员，条件变，排序不变
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object[] simpleSearchCondition() {
		if (keyword != null) {
			keyword = keyword.toLowerCase();
		}
		StringBuffer hql = new StringBuffer();
		hql.append(HQL);
		Map map = new HashMap();
		if (unitId == null || unitId.isEmpty()) {// 如果不传参数过来，就认为是从所有能管理 的范围选取，典型举例为账号模块调用
			if (type == 2) {// 部级管理人员
				if (loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 部级账号，本部级单位管理人员
					hql.append(" where (o.agency.id = :currentUnitId or ag.subjection.id = :currentUnitId) ");
					map.put("currentUnitId", loginer.getCurrentBelongUnitId());
				} else {// 所有部级管理人员
					hql.append(" left join o.agency ag where ag.type = 1 ");
				}
			} else if (type == 3) {// 省级管理人员
				if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号，本省级单位管理人员
					hql.append(" where o.agency.id = :currentUnitId ");
					map.put("currentUnitId", loginer.getCurrentBelongUnitId());
				} else {// 所有省级管理人员
					hql.append(" left join o.agency ag where ag.type = 2 ");
				}
			} else if (type == 4 || type == 5) {// 校级管理人员
				if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号，本省所辖高校管理人员
					hql.append(" left join o.agency ag where ag.subjection.id = :currentUnitId and ag.type = 4 and o.department.id is null and o.institute.id is null ");
					map.put("currentUnitId", loginer.getCurrentBelongUnitId());
				} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号，本校级单位管理人员
					hql.append(" where o.agency.id = :currentUnitId and o.department.id is null and o.institute.id is null ");
					map.put("currentUnitId", loginer.getCurrentBelongUnitId());
				} else {// 所有校级管理人员
					hql.append(" left join o.agency ag where (ag.type = 3 or ag.type = 4) and o.department.id is null and o.institute.id is null ");
				}
			} else if (type == 6) {// 院系管理人员
				if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号，本省所辖院系管理人员
					hql.append(" left join o.agency ag where ag.subjection.id = :currentUnitId and ag.type = 4 and o.department.id is not null ");
					map.put("currentUnitId", loginer.getCurrentBelongUnitId());
				} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号，本校所辖院系管理人员
					hql.append(" where o.agency.id = :currentUnitId and o.department.id is not null ");
					map.put("currentUnitId", loginer.getCurrentBelongUnitId());
				} else if (loginer.getCurrentType().equals(AccountType.DEPARTMENT)) {// 本院系管理人员
					hql.append(" where o.department.id = :currentUnitId ");
					map.put("currentUnitId", loginer.getCurrentBelongUnitId());
				} else {// 所有院系管理人员
					hql.append(" where o.department.id is not null ");
				}
			} else if (type == 7) {// 基地管理人员
				if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号，本省所辖基地管理人员
					hql.append(" left join o.agency ag left join o.institute i left join i.type = sys where ag.subjection.id = :currentUnitId and (ag.type = 4 or sys.code = '02' or sys.code = '03') and o.institute.id is not null ");
					map.put("currentUnitId", loginer.getCurrentBelongUnitId());
				} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号，本校所辖基地管理人员
					hql.append(" where o.agency.id = :currentUnitId and o.institute.id is not null ");
					map.put("currentUnitId", loginer.getCurrentBelongUnitId());
				} else if (loginer.getCurrentType().equals(AccountType.INSTITUTE)) {// 本基地管理人员
					hql.append(" where o.institute.id = :currentUnitId ");
					map.put("currentUnitId", loginer.getCurrentBelongUnitId());
				} else {// 所有基地管理人员
					hql.append(" where o.institute.id is not null ");
				}
			} else {// 所有管理人员
				hql.append(" where 1=1 ");
			}
		} else {// 指定具体单位的officer
			hql.append(" where (o.agency.id = :unitId or o.institute.id = :unitId or o.department.id = :unitId) ");
			map.put("unitId", unitId);
		}
		if(label == 1){
			hql.append(" and not exists (from Account ac where ac.officer.id = o.id)");
		}
		if (null != keyword) {
			hql.append(" and ");
			if (searchType == 1) {
				hql.append(" LOWER(p.name) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {
				hql.append(" LOWER(o.position) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 3) {
				hql.append(" LOWER(ag.name) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else {
				hql.append(" (LOWER(p.name) like :keyword or LOWER(o.position) like :keyword) or LOWER(ag.name) like :keyword) ");
				map.put("keyword", "%" + keyword + "%");
			}
		}
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
	}

	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}
}
