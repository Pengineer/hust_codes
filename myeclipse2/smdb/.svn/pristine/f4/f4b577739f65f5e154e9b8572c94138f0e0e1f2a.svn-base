package csdc.action.pop.select;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import csdc.tool.bean.AccountType;

/**
 * 弹层--校级单位
 * @author 龚凡
 */
public class UniversityAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select a.id, a.name, a.code, p.name from Agency a left join a.province p where ";
	private static final String[] COLUMN = {
			"a.name",
			"a.code",
			"p.name"
		};// 用于拼接的排序列
	private static final String PAGE_NAME = "selectUniversityPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "a.id";// 上下条查看时用于查找缓存的字段
	
	public String pageName() {
		return UniversityAction.PAGE_NAME;
	}
	public String[] column() {
		return UniversityAction.COLUMN;
	}
	public String dateFormat() {
		return UniversityAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return UniversityAction.PAGE_BUFFER_ID;
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
		if(label == 1){
			hql.append(" not exists (from Account ac where ac.agency.id = a.id) and ");
		}
		if(label !=2){
			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号
				hql.append(" (a.type = 4 or a.type = 3) and ");
			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号
				hql.append(" a.type = 4 and a.subjection.id = :unitid and ");
				map.put("unitid", loginer.getCurrentBelongUnitId());
			} else if(loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {
				hql.append(" a.id =:unitid and ");
				map.put("unitid", loginer.getCurrentBelongUnitId());
			} else{
				hql.append(" 1=0 and ");
			}
		}else{
			;
		}
		if (searchType == 1) {
			hql.append(" LOWER(a.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {
			hql.append(" LOWER(a.code) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 3) {
			hql.append(" LOWER(p.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {
			hql.append(" (LOWER(a.name) like :keyword or LOWER(a.code) like :keyword or LOWER(p.name) like :keyword) ");
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
	
	/**弹出层选择高校的自动补全功能
	 * @return
	 */
	public String fetchAutoData(){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		List<String> autoData = new ArrayList<String>();
		if(searchType == 1 || searchType == 2 || searchType == 3){
			if (searchType == 1) {
				hql.append("select distinct(a.name) from Agency a left join a.province p where ");
			} else if (searchType == 2) {
				hql.append("select distinct(a.code) from Agency a left join a.province p where ");
			} else if (searchType == 3) {
				hql.append("select distinct(p.name) from Agency a left join a.province p where ");
			} 
			if(label == 1){
				hql.append(" not exists (from Account ac where ac.agency.id = a.id) and ");
			}
			if(label !=2){
				if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号
					hql.append(" (a.type = 4 or a.type = 3) and ");
				} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号
					hql.append(" a.type = 4 and a.subjection.id = :unitid and ");
					map.put("unitid", loginer.getCurrentBelongUnitId());
				} else if(loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {
					hql.append(" a.id =:unitid and ");
					map.put("unitid", loginer.getCurrentBelongUnitId());
				} else{
					hql.append(" 1=0 and ");
				}
			}else{
				;
			}
			hql.append(" 1=1 ");
			autoData = dao.query(hql.toString(), map);
		}else {
			StringBuffer hql2 = new StringBuffer();
			Map map2 = new HashMap();
			StringBuffer hql3 = new StringBuffer();
			Map map3 = new HashMap();
			hql.append("select distinct(a.name) from Agency a left join a.province p where ");
			hql2.append("select distinct(a.code) from Agency a left join a.province p where ");
			hql3.append("select distinct(p.name) from Agency a left join a.province p where ");
			if(label == 1){
				hql.append(" not exists (from Account ac where ac.agency.id = a.id) and ");
				hql2.append(" not exists (from Account ac where ac.agency.id = a.id) and ");
				hql3.append(" not exists (from Account ac where ac.agency.id = a.id) and ");
			}
			if(label !=2){
				if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号
					hql.append(" (a.type = 4 or a.type = 3) and ");
					hql2.append(" (a.type = 4 or a.type = 3) and ");
					hql3.append(" (a.type = 4 or a.type = 3) and ");
				} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号
					hql.append(" a.type = 4 and a.subjection.id = :unitid and ");
					map.put("unitid", loginer.getCurrentBelongUnitId());
					hql2.append(" a.type = 4 and a.subjection.id = :unitid and ");
					map2.put("unitid", loginer.getCurrentBelongUnitId());
					hql3.append(" a.type = 4 and a.subjection.id = :unitid and ");
					map3.put("unitid", loginer.getCurrentBelongUnitId());
				} else if(loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {
					hql.append(" a.id =:unitid and ");
					map.put("unitid", loginer.getCurrentBelongUnitId());
					hql2.append(" a.id =:unitid and ");
					map2.put("unitid", loginer.getCurrentBelongUnitId());
					hql3.append(" a.id =:unitid and ");
					map3.put("unitid", loginer.getCurrentBelongUnitId());
				} else{
					hql.append(" 1=0 and ");
					hql2.append(" 1=0 and ");
					hql3.append(" 1=0 and ");
				}
			}else{
				;
			}
			hql.append(" 1=1 ");
			hql2.append(" 1=1 ");
			hql3.append(" 1=1 ");
			List<String> autoData1 = dao.query(hql.toString(), map);
			List<String> autoData2 = dao.query(hql2.toString(), map2);
			List<String> autoData3 = dao.query(hql3.toString(), map3);
			autoData.addAll(autoData1);
			autoData.addAll(autoData2);
			autoData.addAll(autoData3);
		}
		jsonMap.put("autoData", autoData);
		return SUCCESS;
	}
	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}
}