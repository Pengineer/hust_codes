package csdc.action.project;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.Account;
import csdc.service.IProjectService;
import csdc.tool.HSSFExport;
import csdc.tool.HqlTool;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;

public abstract class MidinspectionApplyRequiredAction extends BaseAction {

	private static final long serialVersionUID = -5382106351870716640L;

	private static final String DATE_FORMAT = "yyyy-MM-dd";//列表页面时间格式
	private static final String PAGE_BUFFER_ID = "gra.id";//缓存id
	public abstract String projectType();//项目类别
	protected static final String HQL1="select gra.id, gra.name, gra.number, uni.name, uni.code, gra.applicantName, so.name, app.disciplineType, app.year ";
	protected static final String HQL4TS = "from ProjectGranted gra, SystemOption so, ProjectMember mem, ProjectApplication app join gra.university uni " +
			"where mem.application.id=app.id and gra.applicationId = app.id and gra.subtype.id = so.id ";
	protected static final String HQL4M = "from ProjectGranted gra , SystemOption so, ProjectApplication app join gra.university uni " +
			"where gra.applicationId = app.id and gra.subtype.id = so.id ";
	
	private IProjectService projectService;

	protected IProjectService getProjectService() {
		return projectService;
	}
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
	private String fileFileName;
	
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	private static final String[] CCOLUMNNAME = {
		"项目名称",
		"项目批准号",
		"依托高校",
		"依托高校代码",
		"项目负责人",
		"项目子类",
		"学科门类",
		"项目年度"
	};
	private static final String[] COLUMN = {
		"gra.name",
		"gra.number",
		"uni.name",
		"uni.code",
		"gra.applicantName",
		"so.name",
		"app.disciplineType",
		"app.year"
	};//排序列
	
	public String pageName() {
		return null;
	}

	
	public String[] column() {
		return MidinspectionApplyRequiredAction.COLUMN;
	}
	
	public String[] columnName() {
		return MidinspectionApplyRequiredAction.CCOLUMNNAME;
	}
	
	public String dateFormat() {
		return MidinspectionApplyRequiredAction.DATE_FORMAT;
	}

	public Object[] simpleSearchCondition() {
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		Account account = loginer.getAccount();
		String belongId = projectService.getBelongIdByAccount(account);
		int isPrincipal =account.getIsPrincipal();
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("projectType", projectType());
		hql.append(HQL1);
		if(account.getType().within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql.append(HQL4TS);
			hql.append(" and mem.member.id=:belongId and mem.groupNumber = gra.memberGroupNumber");
			map.put("belongId", belongId);
		} else if (account.getType().equals(AccountType.ADMINISTRATOR)) {//系统管理员
			hql.append(HQL4M);
			
		} else if (account.getType().equals(AccountType.MINISTRY)) {
			hql.append(HQL4M);
			if (isPrincipal!= 1) {// 子账号
				if (projectService.getAgencyIdByOfficerId(belongId) == null) {
					hql.append(" and 1=0");
				}
			}
		} else if(account.getType().equals(AccountType.PROVINCE)){
			hql.append(HQL4M);
			if (isPrincipal == 1) {// 主账号
				hql.append(" and uni.type=4 and uni.subjection.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String provinceId = projectService.getAgencyIdByOfficerId(belongId);
				if (provinceId != null) {
					hql.append(" and uni.type=4 and uni.subjection.id=:provinceId");
					map.put("provinceId", provinceId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if(account.getType().equals(AccountType.MINISTRY_UNIVERSITY) || account.getType().equals(AccountType.LOCAL_UNIVERSITY)){
			hql.append(HQL4M);
			if (isPrincipal == 1) {// 主账号
				hql.append(" and uni.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String universityId = projectService.getAgencyIdByOfficerId(belongId);
				if (universityId != null) {
					hql.append(" and uni.id=:universityId");
					map.put("universityId", universityId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if(account.getType().equals(AccountType.DEPARTMENT)){
			hql.append(HQL4M);
			if (isPrincipal == 1) {// 主账号
				hql.append(" and gra.department.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String departmentId = projectService.getDepartmentIdByOfficerId(belongId);
				if (departmentId != null) {
					hql.append(" and gra.department.id=:departmentId");
					map.put("departmentId", departmentId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (account.getType().equals(AccountType.INSTITUTE)) {
			hql.append(HQL4M);
			if (isPrincipal == 1) {// 主账号
				hql.append(" and gra.institute.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String instituteId = projectService.getInstituteIdByOfficerId(belongId);
				if (instituteId != null) {
					hql.append(" and gra.institute.id=:instituteId");
					map.put("instituteId", instituteId);
				} else {
					hql.append(" and 1=0");
				}
			}
		}else {
			hql.append(HQL4M);
			hql.append("and 1=0 ");
		}
		int numberTemp = Integer.parseInt(year.substring(year.length()- 2, year.length()));//项目批准号条件拼接
		hql.append(" and (gra.number like '" + (numberTemp - 3) + "%' or gra.number like '" + (numberTemp - 2) + "%') "+
				"and gra.status = 1 " +
				"and not exists(from ProjectMidinspection mid where mid.grantedId = gra.id and mid.finalAuditResult = 2) " +
				"and (gra.projectType = :projectType )");
		
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.equals("")){
			map.put("keyword", "%" + keyword + "%");
			hql.append(this.projectService.getMidSimpleSearchHQL(searchType));
		}
		HqlTool hqlTool = new HqlTool(hql.toString());
		hql.append(" group by " + hqlTool.getSelectClause());
		session.put("midRequiredHql", hql);
		session.put("midRequiredMap", map);
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}

	public Object[] advSearchCondition() {
		return null;
	}
	
	public String exportOverView() {
		return SUCCESS;
	}
	
	public InputStream getDownloadFile() throws UnsupportedEncodingException{
		String header = "";//表头
		List dataList = new ArrayList();
		String[] title = new String[]{};//标题
		if("general".equals(projectType())){
			header = "教育部人文社会科学研究一般项目需中检情况一览表";
		}else if ("instp".equals(projectType())){
			header = "教育部人文社会科学研究基地项目需中检情况一览表";
		}else if ("post".equals(projectType())){
			header = "教育部人文社会科学研究后期资助项目需中检情况一览表";
		}else if ("key".equals(projectType())){
			header = "教育部人文社会科学研究重大攻关项目需中检情况一览表";
		}else if ("entrust".equals(projectType())){
			header = "教育部人文社会科学研究委托应急课题需中检情况一览表";
		}
		fileFileName = header + ".xls";
		fileFileName = new String(fileFileName.getBytes("UTF-8"), "ISO8859-1");
		title = new String[]{
				"id",
				"项目名称",
				"项目批准号",
				"依托高校",
				"依托高校代码",
				"项目负责人",
				"项目子类",
				"学科门类",
				"项目年度"
			};
		StringBuffer hql = (StringBuffer) session.get("midRequiredHql");
		Map map = (Map) session.get("midRequiredMap");
		List<String[]> list = (List) dao.query(hql.toString(), map);
		return HSSFExport.commonExportExcel(list, header, title);
	}
	
	public String pageBufferId() {
		return MidinspectionApplyRequiredAction.PAGE_BUFFER_ID;
	}

}
