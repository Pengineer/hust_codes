package csdc.action.project.instp;

import java.util.HashMap;
import java.util.Map;

import csdc.service.IInstpService;


public class EndinspectionAction extends csdc.action.project.EndinspectionAction{
	private static final long serialVersionUID = 1L;
	private static final String HQL = "select app.id, gra.id, gra.name, app.director, uni.name, gra.subtype, gra.status, app.year, endi.id, endi.ministryResultEnd, endi.reviewResult, endi.finalAuditResultEnd, endi.finalAuditDate " +
			"from ProjectEndinspection all_endi, ProjectEndinspection endi left join endi.granted gra left join gra.application app, University uni " +
			"where endi.type = 'instp' and endi.granted.id = gra.id and all_endi.granted.id = gra.id and app.universityCode = uni.code";
	private static final String HQLG = " group by app.id, gra.id, gra.name, app.director, uni.name, gra.subtype, gra.status, app.year, endi.id, endi.ministryResultEnd, endi.reviewResult, endi.finalAuditResultEnd, endi.finalAuditDate, endi.applicantSubmitDate having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)";
	private static final String HQLC = "";
	private static final String PROJECT_TYPE = "instp";//项目类别
	private static final String CLASS_NAME = "ProjectEndinspection";//项目类名
	private static final String PAGENAME = "instpEndinspectionPage";
	private static final String column[] = {
		"gra.name",
		"app.director",
		"uni.name",
		"gra.subtype",
		"app.year",
		"endi.ministryResultEnd",
		"endi.reviewResult",
		"endi.finalAuditResultEnd",
		"gra.status",
		"midi.finalAuditDate desc"
	};
	protected IInstpService instpService;	//一般项目业务接口对象
	
	@Override
	public String listHQL() {
		return EndinspectionAction.HQL;
	}

	@Override
	public String listHQLG() {
		return EndinspectionAction.HQLG;
	}

	@Override
	public String listHQLC() {
		return EndinspectionAction.HQLC;
	}

	@Override
	public String projectType() {
		return EndinspectionAction.PROJECT_TYPE;
	}

	@Override
	public String className() {
		return EndinspectionAction.CLASS_NAME;
	}
	@Override
	public String[] column() {
		return column;
	}
	@Override
	public String pageName() {
		return PAGENAME;
	}

	/**
	 * 列表和初级检索条件
	 */
	@Override
	public Object[] simpleSearchCondition() {
		keyword = (keyword == null) ? "" : keyword.toLowerCase().trim();// 预处理关键字
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		String s_hql = "";//检索条件
		if(!keyword.isEmpty()) {
			hql.append(" and");
			if(searchType == 1) {//项目名称
				hql.append(" LOWER(gra.name) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if(searchType == 2) {//负责人
				hql.append(" LOWER(app.director) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else if(searchType == 3) {//依托高校
				hql.append(" LOWER(uni.name) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else if(searchType == 4) {//项目子类
				hql.append(" LOWER(gra.subtype) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else if(searchType == 5) {//项目年度
				hql.append(" LOWER(app.year) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			}
		}
		hql.append(s_hql);
		hql.append(HQLG);
		// 调用初级检索功能
		return new Object[]{
			hql.toString(),
			map,
			0,
			null,
			null
		};
	}

	public IInstpService getInstpService() {
		return instpService;
	}

	public void setInstpService(IInstpService instpService) {
		this.instpService = instpService;
	}

	
}
