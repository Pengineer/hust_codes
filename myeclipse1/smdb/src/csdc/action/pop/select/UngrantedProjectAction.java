package csdc.action.pop.select;

import java.util.HashMap;
import java.util.Map;

import csdc.tool.bean.AccountType;
/*
 * 选择已申请未立项项目
 * @author 余潜玉
 */
public class UngrantedProjectAction extends BaseAction {

	private static final long serialVersionUID = -7573122901693563620L;
	private int proType;// 1:一般项目  2:重大攻关项目 3:基地项目 4:后期资助项目 5:委托应急课题
	private static final String PAGE_NAME = "ungrantedProjectPage";
	private static final String[] COLUMN = {
		"app.name",
		"app.applicantName, app.name",
		"app.agencyName, app.name",
		"so.name, app.name",
		"app.year, app.name"
	};//排序列
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "app.id";// 上下条查看时用于查找缓存的字段
	private static final String[] HQL={
		"select app.id,app.name,app.applicantName,app.agencyName,so.name,app.year " +
		"from GeneralApplication app left outer join app.subtype so left outer join app.university uni " +
		"left outer join app.department dep left outer join app.institute ins " +
		"where app.finalAuditResult = 1 and app.finalAuditStatus = 3",
		
		"select app.id,app.name,app.applicantName,app.agencyName,so.name,app.year " +
		"from KeyApplication app left outer join app.researchType so left outer join app.university uni " +
		"left outer join app.department dep left outer join app.institute ins " +
		"where app.finalAuditResult = 1 and app.finalAuditStatus = 3",
		
		"select app.id,app.name,app.applicantName,app.agencyName,so.name,app.year " +
		"from InstpApplication app left outer join app.subtype so left outer join app.university uni " +
		"left outer join app.department dep left outer join app.institute ins " +
		"where app.finalAuditResult = 1 and app.finalAuditStatus = 3",
		
		"select app.id,app.name,app.applicantName,app.agencyName,so.name,app.year " +
		"from PostApplication app left outer join app.subtype so left outer join app.university uni " +
		"left outer join app.department dep left outer join app.institute ins " +
		"where app.finalAuditResult = 1 and app.finalAuditStatus = 3",
		
		"select app.id,app.name,app.applicantName,app.agencyName,so.name,app.year " +
		"from EntrustApplication app left outer join app.subtype so left outer join app.university uni " +
		"left outer join app.department dep left outer join app.institute ins " +
		"where app.finalAuditResult = 1 and app.finalAuditStatus = 3"
	};

//	/**
//	 * 用于跳转到列表页面，并初始化检索类型、检索关键字、起始页码、
//	 * 页面大小、检索URL等参数。
//	 * @author 余潜玉
//	 */
//	@SuppressWarnings("unchecked")
//	public String toList() {
//		Map session = ActionContext.getContext().getSession();
//		Pager pager = (Pager) session.get(pageName());
//		initPager(simpleSearchCondition());
//		pager = (Pager) session.get(pageName());
//		pager.setNeedUpdate(true);
//		if (entityId != null) {
//			pager.setTargetEntityId(entityId.replaceAll("\\W.*", ""));
//		} else if (pageNumber != null) {
//			pager.setTargetPageNumber(pageNumber);
//		}
//		searchType = pager.getSearchType();
//		keyword = pager.getKeyword();
//		return SUCCESS;
//	}
	
	@SuppressWarnings("unchecked")
	public Object[] simpleSearchCondition() {
	    keyword = (keyword == null) ?  "" : keyword.toLowerCase();
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		setProType(((Integer)request.getAttribute("proType")).intValue());
		hql.append(HQL[proType-1]);//获取对应项目查询语句
		AccountType accountType = loginer.getCurrentType();
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			map.put("belongId", "%" + loginer.getPerson().getId() + "%");
		}else if(accountType.compareTo(AccountType.MINISTRY) > 0){//部级以下管理人员
			map.put("belongId", loginer.getCurrentBelongUnitId());
		}
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {//研究人员
			hql.append(" and app.applicantId like :belongId");
		}else if(accountType.equals(AccountType.INSTITUTE)) {//研究基地
			hql.append(" and ins.id = :belongId ");
		}else if(accountType.equals(AccountType.DEPARTMENT)){//院系
			if(proType == 3){//基地项目
				hql.append(" and 1=0 ");
			}else{
				hql.append(" and dep.id=:belongId ");
			}
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {//高校
			hql.append(" and uni.id = :belongId ");
		}else if(accountType.equals(AccountType.PROVINCE) ) {//省级
			hql.append(" and uni.type = 4 and uni.subjection.id = :belongId ");
		}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)) {
			;
		}else{
			hql.append(" and 1=0 ");
		}
		hql.append(" and ");
		if (searchType == 1) {
			hql.append("LOWER(app.name) like :keyword");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {
			hql.append("LOWER(app.applicantName) like :keyword");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 3) {
			hql.append("LOWER(app.agencyName) like :keyword");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 4) {
			hql.append("LOWER(so.name) like :keyword");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 5) {
			hql.append("cast(app.year as string) like :keyword");
			map.put("keyword", "%" + keyword + "%");
		} else {
			hql.append("(LOWER(app.name) like :keyword or LOWER(app.applicantName) like :keyword or LOWER(app.agencyName) like :keyword or LOWER(so.name) like :keyword or cast(app.year as string) like :keyword)");
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
		return UngrantedProjectAction.COLUMN;
	}
	public String dateFormat() {
		return UngrantedProjectAction.DATE_FORMAT;
	}
	public String pageName() {
		return UngrantedProjectAction.PAGE_NAME;
	}
	public String pageBufferId() {
		return UngrantedProjectAction.PAGE_BUFFER_ID;
	}

	public int getProType() {
		return proType;
	}

	public void setProType(int proType) {
		this.proType = proType;
	}

	@Override
	public Object[] advSearchCondition() {
		return null;
	}
}