package csdc.action.project.instp;

import java.util.HashMap;
import java.util.Map;

import csdc.service.IInstpService;


public class VariationAction extends csdc.action.project.VariationAction{
	private static final long serialVersionUID = 1L;
	private static final String HQL = "select app.id, gra.id, gra.name, app.director, uni.name, gra.subtype, app.disciplineType, app.year, vari.id, vari.finalAuditResult, vari.finalAuditDate " +
			"from ProjectVariation all_vari, ProjectVariation vari left join vari.granted gra left join gra.application app, University uni " +
			"where vari.type = 'instp' and vari.granted.id = gra.id and all_vari.granted.id = gra.id and app.universityCode = uni.code";
	private static final String HQLG = " group by app.id, gra.id, gra.name, app.director, uni.name, gra.subtype, app.disciplineType, app.year, vari.id, vari.finalAuditResult, vari.finalAuditDate, vari.applicantSubmitDate having vari.applicantSubmitDate = max(all_vari.applicantSubmitDate)";
	private static final String HQLC = "";
	private static final String PROJECT_TYPE = "instp";//项目类别
	private static final String CLASS_NAME = "ProjectVariation";//项目类名
	private static final String PAGENAME = "instpVariationPage";
	private static final String column[] = {
		"gra.name",
		"app.director",
		"uni.name",
		"gra.subtype",
		"app.disciplineType",
		"app.year",
		"vari.finalAuditResult",
		"vari.finalAuditDate desc"
	};
	protected IInstpService instpService;	//一般项目业务接口对象
	
	@Override
	public String listHQL() {
		return VariationAction.HQL;
	}

	@Override
	public String listHQLG() {
		return VariationAction.HQLG;
	}

	@Override
	public String listHQLC() {
		return VariationAction.HQLC;
	}

	@Override
	public String projectType() {
		return VariationAction.PROJECT_TYPE;
	}

	@Override
	public String className() {
		return VariationAction.CLASS_NAME;
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
