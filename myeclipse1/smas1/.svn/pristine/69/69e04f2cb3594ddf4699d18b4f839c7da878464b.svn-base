package csdc.action.project.general;

import java.util.HashMap;
import java.util.Map;

import csdc.service.IGeneralService;

public class MidinspectionAction extends csdc.action.project.MidinspectionAction{


	private static final long serialVersionUID = 1L;
	private static final String HQL = "select app.id, gra.id, gra.name, app.director, uni.name, gra.subtype, app.disciplineType, app.year, midi.id, midi.finalAuditResult, midi.finalAuditDate " +
			"from ProjectMidinspection all_midi, ProjectMidinspection midi left join midi.granted gra left join gra.application app, University uni " +
			"where midi.type = 'general' and all_midi.type = 'general' and midi.granted.id = gra.id and all_midi.granted.id = gra.id and app.universityCode = uni.code";
	private static final String HQLG = " group by app.id, gra.id, gra.name, app.director, uni.name, gra.subtype, app.disciplineType, app.year, midi.id, midi.finalAuditResult, midi.finalAuditDate, midi.applicantSubmitDate having midi.applicantSubmitDate = max(all_midi.applicantSubmitDate)";
	private static final String HQLC = "";
	private static final String PROJECT_TYPE = "general";//项目类别
	private static final String CLASS_NAME = "ProjectMidinspection";//项目类名
	private static final String PAGENAME = "generalMidinspectionPage";
	private static final String column[] = {
		"gra.name",
		"app.director",
		"uni.name",
		"gra.subtype",
		"app.disciplineType",
		"app.year",
		"midi.finalAuditResult",
		"midi.finalAuditDate desc"
	};
	protected IGeneralService generalService;	//一般项目业务接口对象
	
	@Override
	public String listHQL() {
		return MidinspectionAction.HQL;
	}

	@Override
	public String listHQLG() {
		return MidinspectionAction.HQLG;
	}

	@Override
	public String listHQLC() {
		return MidinspectionAction.HQLC;
	}

	@Override
	public String projectType() {
		return MidinspectionAction.PROJECT_TYPE;
	}

	@Override
	public String className() {
		return MidinspectionAction.CLASS_NAME;
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
			} else if(searchType == 5) {//学科门类
				hql.append(" LOWER(app.disciplineType) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else if(searchType == 6) {//项目年度
				hql.append(" cast(app.year as string) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else {
				hql.append(" (LOWER(gra.name) like :keyword or LOWER(app.director) like :keyword or LOWER(uni.name) like :keyword or LOWER(gra.subtype) like :keyword" +
						" or LOWER(app.disciplineType) like :keyword or cast(app.year as string) like :keyword)");
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

	public IGeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}
	
	
}
