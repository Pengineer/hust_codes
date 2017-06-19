package csdc.action.pop.select;

import java.util.HashMap;
import java.util.Map;

import csdc.bean.Account;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
/*
 * 选择项目
 * @author zhangli
 */
public class ProjectAction extends BaseAction {

	private static final long serialVersionUID = -7573122901693563620L;
	private int proType;//1:一般项目; 2:重大攻关项目; 3:基地项目 ; 4:后期资助项目; 5:委托应急课题
	private String personId;//人员id
	private String universityId;//高校id
	private static final String PAGE_NAME = "loadProjectPage";
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "gra.id";// 上下条查看时用于查找缓存的字段
	private static final String[] COLUMN = {
		"gra.name",
		"gra.applicantName, gra.name",
		"uni.name, gra.name",
		"so.name, gra.name",
		"app.year, gra.name"
	};//排序列
	
	//项目查询语句
	private static final String[] HQL = {//研究人员选择所属项目
		"select gra.id, gra.name, gra.applicantName, uni.name, so.name, app.year " +
		"from GeneralGranted gra, GeneralMember mem left join gra.application app " +
		"left join gra.university uni left join gra.subtype so where " +
		"mem.application.id = app.id and mem.member.id = :personId",
		
		"select gra.id, gra.name, gra.applicantName, uni.name, so.name, app.year " +
		"from KeyGranted gra, KeyMember mem left join gra.application app " +
		"left join gra.university uni left join app.researchType so where " +
		"mem.application.id = app.id and mem.member.id = :personId",
		
		"select gra.id, gra.name, gra.applicantName, uni.name, so.name, app.year " +
		"from InstpGranted gra, InstpMember mem left join gra.application app " +
		"left join gra.university uni left join gra.subtype so where " +
		"mem.application.id = app.id and mem.member.id = :personId",
		
		"select gra.id, gra.name, gra.applicantName, uni.name, so.name, app.year " +
		"from PostGranted gra, PostMember mem left join gra.application app " +
		"left join gra.university uni left join gra.subtype so where " +
		"mem.application.id = app.id and mem.member.id = :personId",
		
		"select gra.id, gra.name, gra.applicantName, uni.name, so.name, app.year " +
		"from EntrustGranted gra, EntrustMember mem left join gra.application app " +
		"left join gra.university uni left join gra.subtype so where " +
		"mem.application.id = app.id and mem.member.id = :personId"
	};
	
	private static final String[] HQLUNI = {//高校选择所属项目
		"select gra.id, gra.name, gra.applicantName, uni.name, so.name, app.year " +
		"from GeneralGranted gra left join gra.application app " +
		"left join gra.university uni left join gra.subtype so where " +
		"uni.id = :universityId",
		
		"select gra.id, gra.name, gra.applicantName, uni.name, so.name, app.year " +
		"from KeyGranted gra left join gra.application app " +
		"left join gra.university uni left join app.researchType so where " +
		"uni.id = :universityId",
		
		"select gra.id, gra.name, gra.applicantName, uni.name, so.name, app.year " +
		"from InstpGranted gra left join gra.application app " +
		"left join gra.university uni left join gra.subtype so where " +
		"uni.id = :universityId",
		
		"select gra.id, gra.name, gra.applicantName, uni.name, so.name, app.year " +
		"from PostGranted gra left join gra.application app " +
		"left join gra.university uni left join gra.subtype so where " +
		"uni.id = :universityId",
		
		"select gra.id, gra.name, gra.applicantName, uni.name, so.name, app.year " +
		"from EntrustGranted gra left join gra.application app " +
		"left join gra.university uni left join gra.subtype so where " +
		"uni.id = :universityId"
	};
	
	
//	/**
//	 * 用于跳转到列表页面，并初始化检索类型、检索关键字、起始页码
//	 * 页面大小、检索URL等参数。
//	 * @author 余潜玉
//	 */
//	public String toList() {
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] simpleSearchCondition() {
		LoginInfo loginer=(LoginInfo) session.get("loginer");
	    Account account = loginer.getAccount();// 获取账号信息
	    keyword = (keyword == null) ? "" : keyword.toLowerCase();
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		setProType(((Integer)request.getAttribute("proType")).intValue());
		if(null != universityId && !universityId.isEmpty()){
			hql.append(HQLUNI[proType-1]);//获取对应高校项目查询语句
			if(account.getType().equals(AccountType.MINISTRY_UNIVERSITY) || account.getType().equals(AccountType.LOCAL_UNIVERSITY)){//部属高校|地方高校
				map.put("universityId", baseService.getBelongIdByAccount(account));
			} else {
				map.put("universityId", (null != universityId) ? universityId : null);
			}
		}else{
			hql.append(HQL[proType-1]);//获取对应研究人员项目查询语句
			if(account.getType().equals(AccountType.EXPERT) || account.getType().equals(AccountType.TEACHER) || account.getType().equals(AccountType.STUDENT)){//教师|专家|学生
				map.put("personId", baseService.getBelongIdByAccount(account));
			} else {
				map.put("personId", (null != personId) ? personId : null);
			}
		}
		hql.append(" and ");
		if (searchType == 1) {
			hql.append("LOWER(gra.name) like :keyword");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {
			hql.append("LOWER(gra.applicantName) like :keyword");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 3) {
			hql.append("LOWER(uni.name) like :keyword");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 4) {
			hql.append("LOWER(so.name) like :keyword");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 5) {
			hql.append("cast(app.year as string) like :keyword");
			map.put("keyword", "%" + keyword + "%");
		} else {
			hql.append("(LOWER(gra.name) like :keyword or LOWER(gra.applicantName) " +
				"like :keyword or LOWER(uni.name) like :keyword or LOWER(so.name) " +
				"like :keyword or cast(app.year as string) like :keyword)");
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
		return ProjectAction.COLUMN;
	}
	public String dateFormat() {
		return ProjectAction.DATE_FORMAT;
	}
	public String pageName() {
		return ProjectAction.PAGE_NAME;
	}
	public String pageBufferId() {
		return ProjectAction.PAGE_BUFFER_ID;
	}
	public int getProType() {
		return proType;
	}
	public void setProType(int proType) {
		this.proType = proType;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getUniversityId() {
		return universityId;
	}

	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}

	public Object[] advSearchCondition() {
		return null;
	}
}