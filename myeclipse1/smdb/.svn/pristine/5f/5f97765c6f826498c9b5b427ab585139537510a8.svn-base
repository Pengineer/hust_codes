package csdc.action.other;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.service.imp.NssfService;
import csdc.tool.HSSFExport;
import csdc.tool.HqlTool;

public class NssfAction extends BaseAction {

	private static final long serialVersionUID = 6459717508852974162L;

	@Autowired
	private NssfService nssfService;
	
	private String fileFileName;//导出文件名
	
	private String keyword1, keyword2, keyword3, keyword4,
					keyword6, keyword7,keyword8, keyword9, keyword10,
					keyword11, keyword12, keyword13, keyword14,
					keyword16;// 高级检索关键字
	private Date grantedStartDate, grantedEndDate, endStartDate, endEndDate;// 高级检索	private Date grantedStartDate, grantedEndDate, endStartDate, endEndDate;// 高级检索	
	
	private final static String HQL = 
			"select " +
			"	nssf.name, " +
			"	nssf.number, " +
			"	nssf.type, " +
			"	nssf.disciplineType, " +
			"	nssf.startDate, " +
			"	nssf.applicant, " +
			"	nssf.specialityTitle, " +
			"	nssf.unit, " +
			"	nssf.unitType, " +
			"	nssf.province, " +
			"	nssf.belongSystem, " +
			"	nssf.productName, " +
			"	nssf.productType, " +
			"	nssf.productLevel, " +
			"	nssf.endDate, " +
			"	nssf.certificate, " +
			"	nssf.press, " +
			"	nssf.publishDate, " +
			"	nssf.author, " +
			"	nssf.prizeObtained, " +
			"	nssf.singleSubject, " +
			"	nssf.topic, " +
			"	nssf.subject, " +
			"	nssf.description, " +
			"	nssf.report, " +
			"	nssf.experts, " +
			"	nssf.noIdentifyReason, " +
			"	nssf.planEndDate, " +
			"	nssf.status " +
			"from " +
			"	Nssf nssf " +
			"where 1=1 ";

	private final static String[] COLUMN = new String[]{
		"nssf.name",
		"nssf.number",
		"nssf.type",
		"nssf.disciplineType",
		"nssf.startDate",
		"nssf.applicant",
		"nssf.specialityTitle",
		"nssf.unit",
		"nssf.unitType",
		"nssf.province",
		"nssf.belongSystem",
		"nssf.productName",
		"nssf.productType",
		"nssf.productLevel",
		"nssf.endDate",
		"nssf.certificate",
		"nssf.press",
		"nssf.publishDate",
		"nssf.author",
		"nssf.prizeObtained",
		"nssf.singleSubject",
		"nssf.topic",
		"nssf.subject",
		"nssf.description",
		"nssf.report",
		"nssf.experts",
		"nssf.noIdentifyReason",
		"nssf.planEndDate",
		"nssf.status"
	};// 排序列

	private final static String[] SEARCH_CONDITIONS = new String[]{
		"LOWER(nssf.name) like :keyword",
		"LOWER(nssf.number) like :keyword",
		"LOWER(nssf.type) like :keyword",
		"LOWER(nssf.disciplineType) like :keyword",
		"LOWER(to_char(nssf.startDate, 'yyyy-MM-dd')) like :keyword",
		"LOWER(nssf.applicant) like :keyword",
		"LOWER(nssf.specialityTitle) like :keyword",
		"LOWER(nssf.unit) like :keyword",
		"LOWER(nssf.unitType) like :keyword",
		"LOWER(nssf.province) like :keyword",
		"LOWER(nssf.belongSystem) like :keyword",
		"LOWER(nssf.productName) like :keyword",
		"LOWER(nssf.productType) like :keyword",
		"LOWER(nssf.productLevel) like :keyword",
		"LOWER(to_char(nssf.endDate, 'yyyy-MM-dd')) like :keyword",
		"LOWER(nssf.certificate) like :keyword",
		"LOWER(nssf.press) like :keyword",
		"LOWER(to_char(nssf.publishDate, 'yyyy-MM-dd')) like :keyword",
		"LOWER(nssf.author) like :keyword",
		"LOWER(nssf.prizeObtained) like :keyword",
		"LOWER(nssf.singleSubject) like :keyword",
		"LOWER(nssf.topic) like :keyword",
		"LOWER(nssf.subject) like :keyword",
		"LOWER(nssf.description) like :keyword",
		"LOWER(nssf.report) like :keyword",
		"LOWER(nssf.experts) like :keyword",
		"LOWER(nssf.noIdentifyReason) like :keyword",
		"LOWER(to_char(nssf.planEndDate, 'yyyy-MM-dd')) like :keyword"
	};
	
	private final static String[] ADV_SEARCH_CONDITIONS = new String[]{
		"and LOWER(nssf.name) like :keyword1 ",
		"and LOWER(nssf.number) like :keyword2 ",
		"and LOWER(nssf.type) like :keyword3 ",
		"and LOWER(nssf.disciplineType) like :keyword4 ",
		"and LOWER(to_char(nssf.startDate, 'yyyy-MM-dd')) like :keyword5 ",
		"and LOWER(nssf.applicant) like :keyword6 ",
		"and LOWER(nssf.specialityTitle) like :keyword7 ",
		"and LOWER(nssf.unit) like :keyword8 ",
		"and LOWER(nssf.unitType) like :keyword9 ",
		"and LOWER(nssf.province) like :keyword10 ",
		"and LOWER(nssf.belongSystem) like :keyword11 ",
		"and LOWER(nssf.productName) like :keyword12 ",
		"and LOWER(nssf.productType) like :keyword13 ",
		"and LOWER(nssf.productLevel) like :keyword14 ",
		"and LOWER(to_char(nssf.endDate, 'yyyy-MM-dd')) like :keyword15 ",
		"and LOWER(nssf.certificate) like :keyword16 ",
		"and LOWER(nssf.press) like :keyword17 ",
		"and LOWER(to_char(nssf.publishDate, 'yyyy-MM-dd')) like :keyword18 ",
		"and LOWER(nssf.author) like :keyword19 ",
		"and LOWER(nssf.prizeObtained) like :keyword20 ",
		"and LOWER(nssf.singleSubject) like :keyword21 ",
		"and LOWER(nssf.topic) like :keyword22 ",
		"and LOWER(nssf.subject) like :keyword23 ",
		"and LOWER(nssf.description) like :keyword24 ",
		"and LOWER(nssf.report) like :keyword25 ",
		"and LOWER(nssf.experts) like :keyword26 ",
		"and LOWER(nssf.noIdentifyReason) like :keyword27 ",
		"and LOWER(to_char(nssf.planEndDate, 'yyyy-MM-dd')) like :keyword28 "
	};

	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式

	@Override
	public String pageName() {
		return "nssfPage";
	}

	@Override
	public String[] column() {
		return COLUMN;
	}

	@Override
	public String dateFormat() {
		return DATE_FORMAT;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		}
		
		HqlTool hqlToolWhere = new HqlTool( hql.toString());
		String whereHql = hqlToolWhere.getWhereClause();
		session.put("nssfGrantedWhereHql", whereHql);
		session.put("nssfGrantedMap", map);
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
	
	@SuppressWarnings("unchecked")
	public String viewTask() {
		long finishedCount = dao.count("select task from CrawlTask task where task.taskType = 'nssf' and task.finishTime is not null ");
		long unfinishedCount = dao.count("select task from CrawlTask task where task.taskType = 'nssf' and task.finishTime is null ");
		long runningCount = nssfService.getRunningTasks().size();
		Date lastUpdateDate = (Date) dao.queryUnique("select max(task.finishTime) from CrawlTask task where task.taskType = 'nssf' ");
		if (lastUpdateDate == null) {
			lastUpdateDate = new Date(0);
		}
		jsonMap.put("finishedCount", finishedCount);
		jsonMap.put("unfinishedCount", unfinishedCount);
		jsonMap.put("runningCount", runningCount);
		jsonMap.put("lastUpdateDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastUpdateDate));
		jsonMap.put("isCrawlerOutdated", nssfService.isCrawlerOutdated());
		return SUCCESS;
	}
	
	public void update() throws Throwable {
		nssfService.update();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object[] advSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		String[] asc = ADV_SEARCH_CONDITIONS;
		// 拼接检索条件，当检索关键字非空时，才添加检索条件，忽略大小写
		if (keyword1 != null && !keyword1.isEmpty()) {//项目名称
			keyword1 = keyword1.toLowerCase();
			hql.append(asc[0]);
			map.put("keyword1", "%" + keyword1 + "%");
		}
		if (keyword2 != null && !keyword2.isEmpty()) {//项目批准号
			keyword2 = keyword2.toLowerCase();
			hql.append(asc[1]);
			map.put("keyword2", Integer.parseInt(keyword2));
		}
		if (keyword3 != null && !keyword3.isEmpty()) {//项目类别
			keyword3 = keyword3.toLowerCase();
			hql.append(asc[2]);
			map.put("keyword3", "%" + keyword3 + "%");
		}
		if (keyword4 != null && !keyword4.isEmpty()) {//学科分类
			keyword4 = keyword4.toLowerCase();
			hql.append(asc[3]);
			map.put("keyword4", "%" + keyword4 + "%");
		}
		if (grantedStartDate != null) {//立项时间
			hql.append("and nssf.startDate > :grantedStartDate ");
			map.put("grantedStartDate", grantedStartDate);
		}
		if (grantedEndDate != null) {
			hql.append("and nssf.startDate < :grantedEndDate ");
			map.put("grantedEndDate", grantedEndDate);
		}
		if (keyword6 != null && !keyword6.isEmpty()) {//项目负责人
			keyword6 = keyword6.toLowerCase();
			hql.append(asc[5]);
			map.put("keyword6", "%" + keyword6 + "%");
		}
		if (keyword7 != null && !keyword8.isEmpty()) {//专业职务
			keyword7 = keyword7.toLowerCase();
			hql.append(asc[6]);
			map.put("keyword7", "%" + keyword7 + "%");
		}
		if (keyword8 != null && !keyword8.isEmpty()) {//工作单位
			keyword8 = keyword8.toLowerCase();
			hql.append(asc[7]);
			map.put("keyword8", "%" + keyword8 + "%");
		}
		if (keyword9 != null && !keyword9.isEmpty()) {//单位类别
			keyword9 = keyword9.toLowerCase();
			hql.append(asc[8]);
			map.put("keyword9", "%" + keyword9 + "%");
		}
		if (keyword10 != null && !keyword10.isEmpty()) {//所在省市区
			keyword10 = keyword10.toLowerCase();
			hql.append(asc[9]);
			map.put("keyword10", "%" + keyword10 + "%");
		}
		if (keyword11 != null && !keyword11.isEmpty()) {//所属系统
			keyword11 = keyword11.toLowerCase();
			hql.append(asc[10]);
			map.put("keyword11", "%" + keyword11 + "%");
		}
		if (keyword12 != null && !keyword12.isEmpty()) {//成果名称
			keyword12 = keyword12.toLowerCase();
			hql.append(asc[11]);
			map.put("keyword12", "%" + keyword12 + "%");
		}
		if (keyword13 != null && !keyword13.isEmpty()) {//成果形式
			keyword13 = keyword13.toLowerCase();
			hql.append(asc[12]);
			map.put("keyword13", "%" + keyword13 + "%");
		}
		if (keyword14 != null && !keyword14.isEmpty()) {//成果等级
			keyword14 = keyword14.toLowerCase();
			hql.append(asc[11]);
			map.put("keyword14", "%" + keyword14 + "%");
		}
		/*if (keyword15 != null && !keyword15.isEmpty()) {//结项时间
			keyword15 = keyword15.toLowerCase();
			hql.append(asc[12]);
			map.put("keyword15", Double.parseDouble(keyword15));
		}*/
		if (keyword16 != null && !keyword16.isEmpty()) {//结项证书编号
			keyword16 = keyword16.toLowerCase();
			hql.append(asc[11]);
			map.put("keyword16", "%" + keyword16 + "%");
		}
		if (endStartDate != null) {
			hql.append("and nssf.endDate > :endStartDate ");
			map.put("endStartDate", endStartDate);
		}
		if (endEndDate != null) {
			hql.append("and nssf.endDate < :endEndDate ");
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
		return "nssf.id";
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
		fileFileName = "国家社会科学基金项目立项数据一览表.xls";
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
		Map map = (Map) session.get("nssfGrantedMap");
		String whereHql = (String) session.get("nssfGrantedWhereHql");
		StringBuffer hql4Export = new StringBuffer("select 1, nssf.name, nssf.number, nssf.type, nssf.disciplineType, nssf.startDate, nssf.applicant, nssf.specialityTitle, nssf.unit, nssf.productName, nssf.productType, nssf.productLevel, nssf.endDate, nssf.certificate, nssf.experts, nssf.noIdentifyReason, 	nssf.planEndDate, 	nssf.status from 	Nssf nssf where ");
		hql4Export.append(whereHql);
		hql4Export.append(" order by nssf.name, nssf.id");
		List dataList = dao.query(hql4Export.toString(), map);
		//添加序号
		for (int i=0, size = dataList.size(); i < size; i++) {
			Object[] object = (Object[]) dataList.get(i);
			object[0] = i+1;
			dataList.set(i, object);
		}
		
		//导出配置
		String header = "国家社会科学基金项目初审结果一览表";
		String[] title = {"序号", "项目名称", "项目批准号","项目类别", "学科分类", "立项时间", "项目负责人", "专业职务", "工作单位", "成果名称", "成功形式", "成果等级", "结项时间", "结项证书号", "鉴定组专家", "免于鉴定理由", "计划完成时间", "项目状态"};
		
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

	public String getKeyword8() {
		return keyword8;
	}

	public void setKeyword8(String keyword8) {
		this.keyword8 = keyword8;
	}

	public String getKeyword9() {
		return keyword9;
	}

	public void setKeyword9(String keyword9) {
		this.keyword9 = keyword9;
	}

	public String getKeyword10() {
		return keyword10;
	}

	public void setKeyword10(String keyword10) {
		this.keyword10 = keyword10;
	}

	public String getKeyword11() {
		return keyword11;
	}

	public void setKeyword11(String keyword11) {
		this.keyword11 = keyword11;
	}

	public String getKeyword12() {
		return keyword12;
	}

	public void setKeyword12(String keyword12) {
		this.keyword12 = keyword12;
	}

	public String getKeyword13() {
		return keyword13;
	}

	public void setKeyword13(String keyword13) {
		this.keyword13 = keyword13;
	}

	public String getKeyword14() {
		return keyword14;
	}

	public void setKeyword14(String keyword14) {
		this.keyword14 = keyword14;
	}

	public String getKeyword16() {
		return keyword16;
	}

	public void setKeyword16(String keyword16) {
		this.keyword16 = keyword16;
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
	
}
