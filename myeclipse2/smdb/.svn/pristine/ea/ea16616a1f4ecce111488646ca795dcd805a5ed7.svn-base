package csdc.action.projectFund;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.Account;
import csdc.bean.FundList;
import csdc.bean.ProjectFunding;
import csdc.service.IProjectService;
import csdc.tool.HSSFExport;
import csdc.tool.HqlTool;
import csdc.tool.bean.AccountType;

/**
 * 项目拨款概况管理基类（实现各类项目拨款概况管理公用方法）
 */
public abstract class ProjectFundBaseAction extends BaseAction {
	
	
	private static final long serialVersionUID = -774188166761647966L;
	protected IProjectService projectService;//项目管理接口
	private static String HQL1 = "select distinct app.id, gra.id, gra.number, gra.name, gra.applicantId, " +
	"gra.applicantName, uni.id, uni.name, so.name, app.year, gra.approveFee, gra.status ";
	public static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "gra.id";//缓存id
	public abstract String grantedClassName();//项目立项类类名
	public abstract String listHql2();
	public abstract String listHql3();
	public abstract String listHql4();
	public abstract String pageName(); 
	public abstract int checkGrantedFlag();//获得判断立项项目是否在管辖范围内的标志位
	public abstract String projectType();//项目类别
	protected String projectid;//项目立项id
	protected String mainFlag;//首页进入列表参数
	protected String projectNumber,projectName,researchType,projectSubtype,applicant,type,university;//高级检索条件
	protected int startYear,endYear,status;//高级检索条件
	protected Double fee;
	private String fileFileName;
	private int expFlag;//0：普通列表导出；1:高级检索导出
	private ProjectFunding projectFunding;
	private static final String[] COLUMN = new String[] {
		"gra.number",
		"gra.name",
		"gra.applicantName",
		"uni.name",
		"so.name",
		"app.year desc",
		"gra.approveFee"
	};
	
	public String dateFormat() {
		return ProjectFundBaseAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return ProjectFundBaseAction.PAGE_BUFFER_ID;
	}
	public String[] column() {
		return ProjectFundBaseAction.COLUMN;
	}
	public IProjectService getProjectService() {
		return projectService;
	}
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	
	public static void setHQL1(String hQL1) {
		HQL1 = hQL1;
	}
	public static String getHQL1() {
		return HQL1;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public int getExpFlag() {
		return expFlag;
	}
	public void setExpFlag(int expFlag) {
		this.expFlag = expFlag;
	}
	public String getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getResearchType() {
		return researchType;
	}
	public void setResearchType(String researchType) {
		this.researchType = researchType;
	}
	public String getProjectSubtype() {
		return projectSubtype;
	}
	public void setProjectSubtype(String projectSubtype) {
		this.projectSubtype = projectSubtype;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public int getStartYear() {
		return startYear;
	}
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	public int getEndYear() {
		return endYear;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	public String getMainFlag() {
		return mainFlag;
	}
	public void setMainFlag(String mainFlag) {
		this.mainFlag = mainFlag;
	}
	public void setProjectFunding(ProjectFunding projectFunding) {
		this.projectFunding = projectFunding;
	}
	public ProjectFunding getProjectFunding() {
		return projectFunding;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	
	/**
	 * 初级检索
	 */
	@Override
	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("entityId", entityId);
		AccountType accountType = loginer.getCurrentType();
		hql.append(HQL1);
		if (listForFundList ==1) {
			hql.append(", pf.fee, pf.status, pf.id ");
			hql.append(listHql4());
		}else {
			if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
				hql.append(listHql3());
			}else{//管理人员
				hql.append(listHql2());
			}
		}
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.equals("")){
			map.put("keyword", "%" + keyword + "%");
			hql.append(this.projectService.getFeeSimpleSearchHQL(searchType));
		}
		Map session = ActionContext.getContext().getSession();
		session.put("feeMap", map);
		Account account = loginer.getAccount();
		//处理查询范围
		String addHql = this.projectService.grantedInSearch(account);
		hql.append(addHql);
		map = (Map) session.get("feeMap");
		HqlTool hqlToolWhere = new HqlTool(hql.toString());
		String whereHql =null;
		if (listForFundList ==1) {
			whereHql = hqlToolWhere.getWhereClause().substring(112);
		}else {
			whereHql = hqlToolWhere.getWhereClause().substring(57);
		}
		session.put("whereHql", whereHql);
		return new Object[]{
			hql.toString(),
			map,
			5,
			null
		};
	}
	
	/**
	 * 高级检索
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] advSearchCondition(){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("entityId", entityId);
		AccountType accountType = loginer.getCurrentType();
		hql.append(HQL1);
		if (listForFundList ==1) {
			hql.append(", pf.fee, pf.status, pf.id ");
			hql.append(listHql4());
		}else {
			if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
				hql.append(listHql3());
			}else{//管理人员
				hql.append(listHql2());
			}
		}
		if (projectNumber != null && !projectNumber.isEmpty()) {
			projectNumber = projectNumber.toLowerCase();
			hql.append(" and LOWER(gra.number) like :projectNumber");
			map.put("projectNumber", "%" + projectNumber + "%");
		}
		if (projectName != null && !projectName.isEmpty()) {
			projectName = projectName.toLowerCase();
			hql.append(" and LOWER(gra.name) like :projectName");
			map.put("projectName", "%" + projectName + "%");
		}
		if(!projectType().equals("key")){
			if (projectSubtype != null && !projectSubtype.equals("-1")) {
				projectSubtype = projectSubtype.toLowerCase();
				hql.append(" and LOWER(so.id) like :projectSubtype");
				map.put("projectSubtype", "%" + projectSubtype + "%");
			}
		}else{//重大攻关
			if (researchType != null && !researchType.equals("-1")) {
				researchType = researchType.toLowerCase();
				hql.append(" and LOWER(so.id) like :researchType");
				map.put("researchType", "%" + researchType + "%");
			}
		}
		if (startYear != -1) {
			hql.append(" and app.year>=:startYear");
			map.put("startYear", startYear);
		}
		if (endYear != -1) {
			hql.append(" and app.year<=:endYear");
			map.put("endYear", endYear);
		}
		if (applicant!= null && !applicant.isEmpty()) {
			applicant = applicant.toLowerCase();
			hql.append(" and LOWER(gra.applicantName) like :applicant");
			map.put("applicant", "%" + applicant + "%");
		}
		if (university != null && !university.isEmpty()) {
			university = university.toLowerCase();
			hql.append(" and LOWER(uni.name) like :university");
			map.put("university", "%" + university + "%");
		}
//		if (type != null && !type.equals("")) {
//			type = type.toLowerCase();
//			hql.append(" and LOWER(pf.type) like :type");
//			map.put("type", "%" + type + "%");
//		}
//		if (status != -1) {
////			status = status.toLowerCase();
//			hql.append(" and pf.status = :status ");
////			hql.append(" and LOWER(pf.status) like :status");
////			map.put("status", "%" + status + "%");
//			map.put("status", status);
//		}
		
		Map session = ActionContext.getContext().getSession();
		session.put("feeMap", map);
		Account account = loginer.getAccount();
		//处理查询范围
		String addHql = this.projectService.grantedInSearch(account);
		hql.append(addHql);
		map = (Map) session.get("feeMap");
		expFlag =1;
		HqlTool hqlToolWhere = new HqlTool(hql.toString());
		String whereHql =null;
		if (listForFundList ==1) {
			whereHql = hqlToolWhere.getWhereClause().substring(112);
		}else {
			whereHql = hqlToolWhere.getWhereClause().substring(57);
		}
		session.put("whereHql", whereHql);
		return new Object[]{
			hql.toString(),
			map,
			6,
			null
		};
	}
	
	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author 肖宁
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAdvSearchQuery(Map searchQuery) {
		if (projectNumber != null && !projectNumber.isEmpty()) {
			searchQuery.put("projectNumber", projectNumber);
		}
		if(projectName!=null && !projectName.isEmpty()){
			searchQuery.put("projectName", projectName);
		}
		if(!projectType().equals("key")){
			if (projectSubtype != null && !projectSubtype.equals("-1")) {
				searchQuery.put("projectSubtype", projectSubtype);
			}
		}else{//重大攻关
			if (researchType != null && !researchType.equals("-1")) {
				searchQuery.put("researchType", researchType);
			}
		}
//		if (type != null && !type.equals("")) {
//			searchQuery.put("type", type);
//		}
//		if (status != -1) {
//			searchQuery.put("status", status);
//		}
		if(startYear!=-1){
			searchQuery.put("startYear", startYear);
		}
		if(endYear!=-1){
			searchQuery.put("endYear", endYear);
		}
		if(applicant!=null && !applicant.isEmpty()){
			searchQuery.put("applicant", applicant);
		}
		if(university!=null && !university.isEmpty()){
			searchQuery.put("university", university);
		}
		searchQuery.put("expFlag", expFlag);
		session.put("searchQuery", searchQuery);
	}
	
	
	/**
	 * 确认导出一览表
	 * @return
	 * @author xn
	 */
	public String confirmExportOverView(){
		if (!request.getParameter("listForFundList").equals(null) && !request.getParameter("listForFundList").isEmpty()) {
			listForFundList = Integer.parseInt(request.getParameter("listForFundList").trim());
		}
		if (!request.getParameter("entityId").equals(null) && !request.getParameter("entityId").isEmpty()) {
			entityId = request.getParameter("entityId");
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public InputStream getDownloadFile() throws UnsupportedEncodingException{
		String header = "";//表头
		FundList fundList = dao.query(FundList.class, entityId);
		if (listForFundList ==1) {
			header = fundList.getName();
		}else {
			if("general".equals(projectType())){
				header = "教育部人文社会科学研究一般项目拨款一览表";
			}else if ("instp".equals(projectType())){
				header = "教育部人文社会科学研究基地项目拨款一览表";
			}else if ("post".equals(projectType())){
				header = "教育部人文社会科学研究后期资助项目拨款一览表";
			}else if ("key".equals(projectType())){
				header = "教育部人文社会科学研究重大攻关项目拨款一览表";
			}else if ("entrust".equals(projectType())){
				header = "教育部人文社会科学研究委托应急课题拨款一览表";
			}
		}
		StringBuffer hql4Export = new StringBuffer();
		String[] title = new String[]{};//标题
		if ("instp".equals(projectType())){//若是基地项目则需要查询基地名称
			title = new String[]{
					"序号",
					"项目名称",
					"项目类别",
					"项目年度",
					"项目批准号",
					"依托高校",
					"基地名称",
					"项目负责人",
					"批准经费（万元）",
					"拨款类型",
					"拨款金额",
				};
			hql4Export.append("select gra.id, uni.name, ins.name, gra.name, so.name, app.year, gra.number, gra.applicantName, "+
						"gra.approveFee, gra.status, pf.fee, pf.type, pf.id  " );
//			hql4Export.append("select gra.id, uni.name, ins.name, gra.name, so.name, app.year, gra.number, gra.applicantName, "+
//					"gra.approveFee, gra.status, pf.grantedFundFee, pf.midFundFee, pf.endFundFee, pf.grantedFundStatus, pf.midFundStatus, pf.endFundStatus, pf.id from  ProjectFunding pf, "  + grantedClassName() + " gra left outer join gra.university uni left outer join gra.institute ins " +
//					" left outer join gra.application app left outer join gra.subtype so, ProjectMember mem  where mem.applicationId = gra.applicationId and "+
//					"gra.memberGroupNumber = mem.groupNumber and mem.isDirector =1 and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id " );
			AccountType accountType = loginer.getCurrentType();
			if (listForFundList ==1) {
				hql4Export.append("from ProjectFunding pf, InstpGranted gra left outer join gra.application app "+ 
								"left outer join gra.university uni left outer join gra.institute ins left outer join gra.subtype so where 1=1 " +
								"and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.fundList.id = :entityId ");
			}else {
				if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
					hql4Export.append("from ProjectFunding pf, InstpGranted gra left outer join gra.application app "+ 
									"left outer join gra.university uni left outer join gra.institute ins left outer join gra.subtype so, InstpMember mem " +
									"where mem.application.id = app.id and pf.grantedId=gra.id ");
				}else{//管理人员
					hql4Export.append("from ProjectFunding pf, InstpGranted gra left outer join gra.application app "+ 
									"left outer join gra.university uni left outer join gra.institute ins left outer join gra.subtype so where 1=1 " +
									"and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id ");
				}
			}
		}else {
			title = new String[]{
					"序号",
					"项目名称",
					"项目类别",
					"项目年度",
					"项目批准号",
					"依托高校",
					"项目负责人",
					"批准经费（万元）",
					"拨款类型",
					"拨款金额（万元）",
				};
			
//			hql4Export = "select gra.id, uni.name, gra.name, so.name, app.year, gra.number, gra.applicantName, gra.productType, gra.productTypeOther, gra.approveFee, gra.status, to_char(gra.planEndDate,'yyyy-MM-dd') from "  + grantedClassName() + " gra left outer join gra.university uni left outer join gra.application app left outer join app.topic top left outer join gra.subtype so where app.finalAuditStatus=3 and app.finalAuditResult=2 ";
			hql4Export.append("select app.id, gra.id, gra.number, gra.name, gra.applicantId, gra.applicantName, uni.id, uni.name, so.name, app.year, gra.approveFee, gra.status, pf.fee, pf.type, pf.id " );
			AccountType accountType = loginer.getCurrentType();
			if (listForFundList ==1) {
				hql4Export.append(listHql4());
			}else {
				if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
					hql4Export.append(listHql3());
				}else{//管理人员
					hql4Export.append(listHql2());
				}
			}
		}
		fileFileName = header + ".xls";
		fileFileName = new String(fileFileName.getBytes("UTF-8"), "ISO8859-1");
		StringBuffer sb = new StringBuffer();
		sb.append(hql4Export);
		Map parMap = (Map) session.get("feeMap");
		/*if (null!= (String) session.get("whereHql")) {
			HqlTool hqlToolWhere = new HqlTool((String) session.get("whereHql"));
			sb.append(hqlToolWhere.getWhereClause().substring(57));
		}*/
		if (null!= (String) session.get("whereHql")) {
			sb.append((String) session.get("whereHql"));
		}
		if("instp".equals(projectType())){
			sb.append(" order by uni.name asc, gra.applicantName asc");
		} else {
			sb.append(" order by uni.name asc, gra.applicantName asc");
		}
		HqlTool hqlTool = new HqlTool(sb.toString());
		sb.append(" group by " + hqlTool.getSelectClause());
		
		System.out.println(sb);
		List<Object[]> list = dao.query(sb.toString(), parMap);
		List dataList = new ArrayList();
		Map<Object, Object[]> lastData = new HashMap<Object, Object[]>();
		int index = 1;
		//若是基地项目则需要查询基地名称
		if("instp".equals(projectType())){
			for (Object[] o : list) {
				Object[] data = null;
				if (lastData.containsKey(o[6])) {
					data = lastData.get(o[6]);
					String univNames = data[7].toString(); 
					if(!univNames.contains((String)o[7])){
						univNames += "; " + o[7];
						data[7] = univNames;
					}
				}else{
					data = new Object[o.length];
					data[0] = index++;
					data[1] = o[3];//项目名称
					data[2] = o[4];//项目类别
					data[3] = o[5];//项目年度
					data[4] = o[6];//项目批准号
					data[5] = o[1];//依托高校
					data[6] = o[2];//基地名称
					data[7] = o[7];//项目负责人
					data[8] = o[8];//批准经费
					if (o[11].toString().equals("1")) {
						data[9] = "立项拨款";
						data[10] = o[10];//拨款金额
					}else if (o[11].toString().equals("2")) {
						data[9] = "中检拨款";
						data[10] = o[10];//拨款金额
					}else if (o[11].toString().equals("3")) {
						data[9] = "结项拨款";
						data[10] = o[10];//拨款金额
					}else {
						data[9] = "其他拨款";
						data[10] = o[10];//拨款金额
					}
		
//					data[11] = o[11];//批准经费（万元）
//					data[12] = o[13];//计划完成时间
//					data[13] = o[12] != null && (Integer)o[12] == 1 ? "在研" : o[12] != null && (Integer)o[12] == 2 ? "已结项" : o[12] != null && (Integer)o[12] == 3 ? "已中止" : o[12] != null && (Integer)o[12] == 4 ? "已撤项" : "";//项目状态
					dataList.add(data);
				}
				lastData.put(o[6], data);
			}
		}else {
			for (Object object : list) {
				Object[] o = (Object[]) object;
				Object[] data = new Object[10];
				data[0] = index++;
				data[1] = o[3];//项目名称
				data[2] = o[8];//项目类别
				data[3] = o[9];//项目年度
				data[4] = o[2];//项目批准号
				data[5] = o[7];//依托高校
				data[6] = o[5];//项目负责人
				data[7] = o[10];//批准经费
				if (o[13].toString().equals("1")) {
					data[8] = "立项拨款";
					data[9] = o[12];//拨款金额
				}else if (o[13].toString().equals("2")) {
					data[8] = "中检拨款";
					data[9] = o[12];//拨款金额
				}else if (o[13].toString().equals("3")) {
					data[8] = "结项拨款";
					data[9] = o[12];//拨款金额
				}else {
					data[8] = "其他拨款";
					data[9] = o[12];//拨款金额
				}
				
				
//				data[9] = o[9];//批准经费（万元）
//				data[10] = o[11];//计划完成时间
//				data[11] = o[10] != null && (Integer)o[10] == 1 ? "在研" : o[10] != null && (Integer)o[10] == 2 ? "已结项" : o[10] != null && (Integer)o[10] == 3 ? "已中止" : o[10] != null && (Integer)o[10] == 4 ? "已撤项" : "";//项目状态
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//				data[9] = df.format(o[11]);//批准经费（万元）
				dataList.add(data);
			}
		}
		return HSSFExport.commonExportExcel(dataList, header, title);
	}
}