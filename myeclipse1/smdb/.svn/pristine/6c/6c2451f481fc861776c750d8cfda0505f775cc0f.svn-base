package csdc.action.other;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.service.imp.NsfcService;
import csdc.tool.HSSFExport;
import csdc.tool.HqlTool;
import csdc.tool.info.GlobalInfo;

public class NsfcAction extends BaseAction {

	private static final long serialVersionUID = -6710507495994246679L;
	
	@Autowired
	private NsfcService nsfcService;
	
	private String fileFileName;//导出文件名
	
	private String keyword1, keyword2, keyword3, keyword4, keyword5, keyword6, keyword7, keyword10_1, keyword10_2;// 高级检索关键字
	private Date grantedStartDate, grantedEndDate, endStartDate, endEndDate;// 高级检索

	//所爬项目年度
	private int startYear;
	private int  endYear;

	private final static String HQL =
			"select " +
			"	nsfc.id, " +
			"	nsfc.name, " +
			"	nsfc.year, " +
			"	nsfc.number, " +
			"	nsfc.applyNumber, " +
			"	nsfc.applicant, " +
			"	nsfc.unit, " +
			"	nsfc.approvedFee, " +
			"	nsfc.startDate, " +
			"	nsfc.endDate, " +
			"	nsfc.grantType, " +
			"	nsfc.subGrantType, " +
			"	nsfc.grantDescription " +
			"from " +
			"	Nsfc nsfc " +
			"where 1=1 ";

	private final static String[] COLUMN = new String[]{
		"nsfc.name",
		"nsfc.year",
		"nsfc.number",
		"nsfc.applyNumber",
		"nsfc.applicant",
		"nsfc.unit",
		"nsfc.approvedFee",
		"nsfc.startDate",
		"nsfc.endDate",
		"nsfc.grantType",
		"nsfc.subGrantType",
		"nsfc.grantDescription"
	};// 排序列

	private final static String[] SEARCH_CONDITIONS = new String[]{
		"LOWER(nsfc.name) like :keyword",
		"nsfc.year = :year",
		"LOWER(nsfc.number) like :keyword",
		"LOWER(nsfc.applyNumber) like :keyword",
		"LOWER(nsfc.applicant) like :keyword",
		"LOWER(nsfc.unit) like :keyword",
		"LOWER(nsfc.grantType) like :keyword",
		"LOWER(nsfc.subGrantType) like :keyword",
		"LOWER(nsfc.grantDescription) like :keyword"
	};
	
	private final static String[] ADV_SEARCH_CONDITIONS = new String[]{
		"and LOWER(nsfc.name) like :keyword1 ",
		"and nsfc.year = :keyword2 ",
		"and LOWER(nsfc.number) like :keyword3 ",
		"and LOWER(nsfc.applyNumber) like :keyword4 ",
		"and LOWER(nsfc.applicant) like :keyword5 ",
		"and LOWER(nsfc.unit) like :keyword6 ",
		"and LOWER(nsfc.grantType) like :keyword7 ",
		"and LOWER(nsfc.subGrantType) like :keyword8 ",
		"and LOWER(nsfc.grantDescription) like :keyword9 ",
		"and LOWER(nsfc.approvedFee) > :keyword10_1 ",
		"and LOWER(nsfc.approvedFee) > :keyword10_1 ",
		"and LOWER(nsfc.startDate) > :grantedStartDate ",
		"and LOWER(nsfc.startDate) < :grantedEndDate ",
		"and LOWER(nsfc.endDate) > :endStartDate ",
		"and LOWER(nsfc.endDate) < :endEndDate "
	};

	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式

	@Override
	public String pageName() {
		return "nsfcPage";
	}

	@Override
	public String[] column() {
		return COLUMN;
	}

	@Override
	public String dateFormat() {
		return DATE_FORMAT;
	}

	@Override
	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer(HQL);
		Map map = new HashMap();
		
		if (keyword != null && !keyword.trim().isEmpty()){
			//处理查询条件
			boolean flag = false;
			StringBuffer tmp = new StringBuffer("(");
			String[] sc = SEARCH_CONDITIONS;
			for (int i = 0; !flag && i < sc.length; i++) {
				if (searchType == i){
					hql.append(" and ").append(sc[i]);
					flag = true;
				}
				tmp.append(sc[i]).append(i < sc.length - 1 ? " or " : ") ");
			}
			if (!flag){
				hql.append(" and ").append(tmp);
			}
			map.put("keyword", "%" + (keyword == null ? "" : keyword.toLowerCase()) + "%");
			map.put("year", keyword != null && keyword.matches("\\d{4}")? Integer.valueOf(keyword) : 0);
		}
		HqlTool hqlToolWhere = new HqlTool( hql.toString());
		String whereHql = hqlToolWhere.getWhereClause();
		session.put("nsfcGrantedWhereHql", whereHql);
		session.put("nsfcGrantedMap", map);
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}
	
	public String toViewTask() {
		return SUCCESS;
	}
	
	public String viewTask() {
		long finishedCount = dao.count("select task from CrawlTask task where task.taskType = 'nsfc' and task.finishTime is not null ");
		long unfinishedCount = dao.count("select task from CrawlTask task where task.taskType = 'nsfc' and task.finishTime is null ");
		long runningCount = nsfcService.getRunningTasks().size();
		long updatedCount = 0;
		
		String currentYear= new SimpleDateFormat("yyyy").format(new Date());
		List<Integer> years = new ArrayList<Integer>();
		for (int i=1997; i <= Integer.parseInt(currentYear); i++) {
			years.add(i);
		}
		
		if (session.containsKey("totalRows")) {
			updatedCount = dao.count("select nsfc.id from Nsfc nsfc") - (long)(Integer)session.get("totalRows");
		} 
		Date lastUpdateDate = (Date) dao.queryUnique("select max(task.finishTime) from CrawlTask task where task.taskType = 'nsfc' ");
		if (lastUpdateDate == null) {
			lastUpdateDate = new Date(0);
		}

		if(session.get("finish") == null || (Integer)session.get("finish") == 2 ) {
			session.put("finish", 0);
		}
		if (runningCount > 0) {
			session.put("finish", 1);//有正在执行的任务
		}
		if(runningCount == 0 && (Integer)session.get("finish") == 1) {
			session.put("finish", 2);//runningCount从非0变成0，即正在执行的任务已完成
		}
		jsonMap.put("finish", session.get("finish"));
		jsonMap.put("years", years);
		jsonMap.put("finishedCount", finishedCount);
		jsonMap.put("unfinishedCount", unfinishedCount);
		jsonMap.put("runningCount", runningCount);
		jsonMap.put("lastUpdateDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastUpdateDate));
		jsonMap.put("updatedCount", updatedCount);
		jsonMap.put("isCrawlerOutdated", nsfcService.isCrawlerOutdated());
		return SUCCESS;
	}
	
	public String update() throws Throwable {
		if (startYear > endYear) { 
			jsonMap.put(GlobalInfo.ERROR_INFO, "开始年份不能大于结束年份");
			return INPUT;
		}
		nsfcService.update(startYear, endYear);
		return SUCCESS;
	}
	
	public String cancel() throws Throwable {
		nsfcService.cancelNow();
		return SUCCESS;
	}

	@Override
	public Object[] advSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		String[] asc = ADV_SEARCH_CONDITIONS;
		// 拼接检索条件，当检索关键字非空时，才添加检索条件，忽略大小写
		if (keyword1 != null && !keyword1.isEmpty()) {// 按事件描述检索
			keyword1 = keyword1.toLowerCase();
			hql.append(asc[0]);
			map.put("keyword1", "%" + keyword1 + "%");
		}
		if (keyword2 != null && !keyword2.isEmpty()) {// 按事件描述检索
			keyword2 = keyword2.toLowerCase();
			hql.append(asc[1]);
			map.put("keyword2", Integer.parseInt(keyword2));
		}
		if (keyword3 != null && !keyword3.isEmpty()) {// 按事件描述检索
			keyword3 = keyword3.toLowerCase();
			hql.append(asc[2]);
			map.put("keyword3", "%" + keyword3 + "%");
		}
		if (keyword4 != null && !keyword4.isEmpty()) {// 按事件描述检索
			keyword4 = keyword4.toLowerCase();
			hql.append(asc[3]);
			map.put("keyword4", "%" + keyword4 + "%");
		}
		if (keyword5 != null && !keyword5.isEmpty()) {// 按事件描述检索
			keyword5 = keyword5.toLowerCase();
			hql.append(asc[4]);
			map.put("keyword5", "%" + keyword5 + "%");
		}
		if (keyword6 != null && !keyword6.isEmpty()) {// 按事件描述检索
			keyword6 = keyword6.toLowerCase();
			hql.append(asc[5]);
			map.put("keyword6", "%" + keyword6 + "%");
		}
		if (keyword7 != null && !keyword7.isEmpty()) {// 按事件描述检索
			keyword7 = keyword7.toLowerCase();
			hql.append(asc[6]);
			map.put("keyword7", "%" + keyword7 + "%");
		}
		if (keyword10_1 != null && !keyword10_1.isEmpty()) {// 按事件描述检索
			keyword10_1 = keyword10_1.toLowerCase();
			hql.append(asc[9]);
			map.put("keyword10_1", Double.parseDouble(keyword10_1));
		}
		if (keyword10_2 != null && !keyword10_2.isEmpty()) {// 按事件描述检索
			keyword10_2 = keyword10_2.toLowerCase();
			hql.append(asc[10]);
			map.put("keyword10_2", Double.parseDouble(keyword10_2));
		}
		if (grantedStartDate != null) {
			hql.append(asc[11]);
			map.put("grantedStartDate", grantedStartDate);
		}
		if (grantedEndDate != null) {
			hql.append(asc[12]);
			map.put("grantedEndDate", grantedEndDate);
		}
		if (endStartDate != null) {
			hql.append(asc[13]);
			map.put("endStartDate", endStartDate);
		}
		if (endEndDate != null) {
			hql.append(asc[14]);
			map.put("endEndDate", endEndDate);
		}
		HqlTool hqlToolWhere = new HqlTool(hql.toString());
		String whereHql = hqlToolWhere.getWhereClause();
		session.put("nsfcGrantedWhereHql", whereHql);
		session.put("nsfcGrantedMap", map);
		return new Object[]{
				hql.toString(),
				map,
				0,
				null
			};
	}

	@Override
	public String pageBufferId() {
		return "nsfc.id";
	}
	
	/**
	 * 确认导出初审结果
	 * @return
	 */
	public String confirmExportOverView() {
		return SUCCESS;
	}

	/**
	 * 导出excel
	 * @return 输入流
	 */
	public InputStream getDownloadFile() throws Exception {
		//导出的Excel文件名
		fileFileName = "国家自然科学基金项目立项数据一览表.xls";
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
		return exportGranted();
	}
	
	/**
	 * 导出国家社会科学基金项目立项数据一览表
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public InputStream exportGranted() throws Exception {
		
		Map session = ActionContext.getContext().getSession();
		Map map = (Map) session.get("nsfcGrantedMap");
		String whereHql = (String) session.get("nsfcGrantedWhereHql");
		StringBuffer hql4Export = new StringBuffer("select 1, nsfc.name, nsfc.year, 	nsfc.number, 	nsfc.applyNumber, 	nsfc.applicant, 	nsfc.unit, 	nsfc.approvedFee, 	nsfc.startDate, 	nsfc.endDate, 	nsfc.grantType, 	nsfc.subGrantType, 	nsfc.grantDescription from Nsfc nsfc where ");
		hql4Export.append(whereHql);
		hql4Export.append(" order by nsfc.name, nsfc.id");
		List dataList = dao.query(hql4Export.toString(), map);
		//添加序号
		for (int i=0, size = dataList.size(); i < size; i++) {
			Object[] object = (Object[]) dataList.get(i);
			object[0] = i+1;
			dataList.set(i, object);
		}
		
		//导出配置
		String header = "国家自然科学基金项目立项数据一览表";
		String[] title = {"序号", "项目名称", "年份", "项目批准号", "申请编号", "项目负责人", "工作单位", "批准金额", "立项时间", "计划完成时间", "资助类别", "亚类说明", "附注说明"};
		
		return HSSFExport.commonExportExcel(dataList, header, title);
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}

	public String getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}

	public String getKeyword3() {
		return keyword3;
	}

	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
	}

	public String getKeyword4() {
		return keyword4;
	}

	public void setKeyword4(String keyword4) {
		this.keyword4 = keyword4;
	}

	public String getKeyword5() {
		return keyword5;
	}

	public void setKeyword5(String keyword5) {
		this.keyword5 = keyword5;
	}

	public String getKeyword6() {
		return keyword6;
	}

	public void setKeyword6(String keyword6) {
		this.keyword6 = keyword6;
	}

	public String getKeyword7() {
		return keyword7;
	}

	public void setKeyword7(String keyword7) {
		this.keyword7 = keyword7;
	}

	public String getKeyword10_1() {
		return keyword10_1;
	}

	public void setKeyword10_1(String keyword10_1) {
		this.keyword10_1 = keyword10_1;
	}

	public String getKeyword10_2() {
		return keyword10_2;
	}

	public void setKeyword10_2(String keyword10_2) {
		this.keyword10_2 = keyword10_2;
	}

	public Date getGrantedStartDate() {
		return grantedStartDate;
	}

	public void setGrantedStartDate(Date grantedStartDate) {
		this.grantedStartDate = grantedStartDate;
	}

	public Date getGrantedEndDate() {
		return grantedEndDate;
	}

	public void setGrantedEndDate(Date grantedEndDate) {
		this.grantedEndDate = grantedEndDate;
	}

	public Date getEndStartDate() {
		return endStartDate;
	}

	public void setEndStartDate(Date endStartDate) {
		this.endStartDate = endStartDate;
	}

	public Date getEndEndDate() {
		return endEndDate;
	}

	public void setEndEndDate(Date endEndDate) {
		this.endEndDate = endEndDate;
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
}
