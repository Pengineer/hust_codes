package csdc.action.project.general;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csdc.action.project.ProjectAction;
import csdc.service.IGeneralService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.widget.NumberHandle;

/**
 * 一般项目立项
 * @author fengcl
 *
 */
@SuppressWarnings("unchecked")
public class GrantedAction extends csdc.action.project.GrantedAction{

	private static final long serialVersionUID = 78484149646990953L;
	private static final String HQL = "select app.id, gra.id, gra.number, gra.name, gra.applicantName, uni.name, gra.subtype, app.disciplineType, app.year, TO_CHAR(gra.approveDate), gra.status " +
			"from ProjectGranted gra left join gra.application app, University uni " +
			"where gra.type = 'general' and app.universityCode = uni.code and app.isGranted = 1";
	private static final String HQLG = " app.id, gra.id, gra.number, gra.name, gra.applicantName, uni.name, gra.subtype, app.disciplineType, app.year, TO_CHAR(gra.approveDate), gra.status";
	private static final String HQLC = "";
	private static final String PROJECT_TYPE = "general";//项目类别
	private static final String CLASS_NAME = "ProjectGranted";//项目类名
	private static final String PAGENAME = "generalGrantedPage";
	private static final String column[] = {
		"gra.name",
		"gra.number",
		"gra.applicantName",
		"uni.name",
		"gra.subtype",
		"p.disciplineType",
		"app.year desc",
		"gra.approveDate",
		"gra.status",
		"app.id"
	};
	private IGeneralService generalService;
	
	/**
	 * 列表和初级检索条件
	 */
	@Override
	public Object[] simpleSearchCondition() {
		keyword = (keyword == null) ? "" : keyword.toLowerCase().trim();// 预处理关键字
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		if(!keyword.isEmpty()){
			hql.append(" and ");
			if (searchType == 1) {// 按项目名称检索
				hql.append(" LOWER(gra.name) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {// 按项目批准号检索
				hql.append(" LOWER(gra.number) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 3) {// 按负责人检索
				hql.append(" LOWER(gra.applicantName) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 4) {// 按高校名称检索
				hql.append(" LOWER(uni.name) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if(searchType == 5){//按项目子类检索
				hql.append(" LOWER(gra.subtype) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if(searchType == 6){//按学科门类检索
				hql.append(" LOWER(app.disciplineType) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if(searchType == 7){//按项目年度检索
				hql.append(" cast(app.year as string) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else {// 按上述字段检索
				hql.append(" (LOWER(gra.name) like :keyword or LOWER(gra.number) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(uni.name) like :keyword" +
						" or LOWER(gra.subtype) like :keyword or LOWER(app.disciplineType) like :keyword or cast(app.year as string) like :keyword)");
				map.put("keyword", "%" + keyword + "%");
			}
		}
		// 调用初级检索功能
		return new Object[]{
			hql.toString(),
			map,
			0,
			null,
			null
		};
	}
	
	@Override
	public String[] column() {
		return column;
	}
	@Override
	public String pageName() {
		return PAGENAME;
	}
	@Override
	public String dateFormat() {
		return DATE_FORMAT;
	}
	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String listHQL() {
		return GrantedAction.HQL;
	}

	@Override
	public String listHQLG() {
		return GrantedAction.HQLG;
	}

	@Override
	public String listHQLC() {
		return GrantedAction.HQLC;
	}

	@Override
	public String projectType() {
		return GrantedAction.PROJECT_TYPE;
	}

	@Override
	public String className() {
		return GrantedAction.CLASS_NAME;
	}

	public IGeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}
	
	
}
