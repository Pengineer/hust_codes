package csdc.action.mobile.statistic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import mondrian.olap.Result;
import csdc.action.mobile.MobileAction;
import csdc.bean.MDXQuery;
import csdc.service.IMobileStatisticService;
import csdc.service.IStatisticService;
import csdc.tool.bean.AccountType;

/**
 * mobile统计分析模块
 * @author suwb
 */
public class CommonStatisticAction extends MobileAction{

	private static final long serialVersionUID = -1791482314873147605L;
	private static final String PAGENAME = "commonStatisticPage"; 
	private static final String HQL4COMMON = "select m.id, m.title, m.year, m.date from MDXQuery m where 1 = 1";

	@Autowired
	private IStatisticService statisticService;
	@Autowired
	private IMobileStatisticService mobileStatisticService;
	
	/**
	 * 统计类型：<br>
	 * 1、person		人员<br>
	 * 2、unit	机构<br>
	 * 3、project	 项目（general、instp、post、key、entrust）<br>
	 * 4、product	 成果（all、paper、book、consultation、patent、electronic、other）<br>
	 * 5、award	 奖励
	 */
	private String statisticType;	//统计类型（person,unit,project,product,award）
	private int listType;
	
	//用于添加or修改常规统计
	private String chartTitle;
	private Integer chartYear;
	private Date chartDate;
	private String mdx;
//	private String chartConfig;//图表配置
	private Integer chart_config;//0默认排序 1自定义排序
	private Integer chartColumn;//展现列or排序列
	private Integer chartType;//统计图类型
	
	//用于高级检索
	private String title;
	private String year;
	private String date;
	
	//隐藏类初始化法
	//PERSONITEMS：人员
	private static final ArrayList PERSONITEMS = new ArrayList();
	static{
		PERSONITEMS.add("社科人员统计#1");
	}
	//UNITITEMS：机构
	private static final ArrayList UNITITEMS = new ArrayList();
	static{
		UNITITEMS.add("社科机构统计#1");
	}
	//PROJECTITEMS：项目
	private static final ArrayList PROJECTITEMS = new ArrayList();
	static{
		PROJECTITEMS.add("一般项目#1");
		PROJECTITEMS.add("重大攻关项目#2");
		PROJECTITEMS.add("基地项目#3");
		PROJECTITEMS.add("后期资助项目#4");
		PROJECTITEMS.add("委托应急课题#5");
	}
	//PRODUCTITEMS：成果
	private static final ArrayList PRODUCTITEMS = new ArrayList();
	static{
		PRODUCTITEMS.add("所有成果#1");
		PRODUCTITEMS.add("论文#2");
		PRODUCTITEMS.add("著作#3");
		PRODUCTITEMS.add("研究咨询报告#4");
		PRODUCTITEMS.add("电子出版物#5");
		PRODUCTITEMS.add("专利#6");
		PRODUCTITEMS.add("其他成果#7");
	}
	//AWARDITEMS：奖励
	private static final ArrayList AWARDITEMS = new ArrayList();
	static{
		AWARDITEMS.add("社科奖励统计#1");
	}
	//EXPERTITEMS：研修班
	private static final ArrayList EXPERTITEMS = new ArrayList();
	static{
		EXPERTITEMS.add("研修班学员统计#1");
	}
	
	/**
	 * 客户端主列表条目
	 * @return
	 */
	public String fetchMenu(){
		AccountType accountType = loginer.getCurrentType();
		Map mainItems = new LinkedHashMap();
		mainItems.put("Person", PERSONITEMS);
		mainItems.put("Unit", UNITITEMS);
		mainItems.put("Project", PROJECTITEMS);
		mainItems.put("Product", PRODUCTITEMS);
		mainItems.put("Award", AWARDITEMS);
		mainItems.put("Expert", EXPERTITEMS);
		switch (accountType) {
		case ADMINISTRATOR://管理员
		case MINISTRY://部级管理人员
		case TEACHER://教师	
			jsonMap.put("listItem", mainItems);	
			break;
		case PROVINCE://省级管理人员
		case LOCAL_UNIVERSITY:
		case MINISTRY_UNIVERSITY://高校管理人员
		case DEPARTMENT://院系管理人员
		case INSTITUTE://基地管理人员
		case EXPERT://外部专家
		case STUDENT://学生
			jsonMap.put("listItem", null);
			break;
		}
		return SUCCESS;
	}

	/**
	 * 初级检索列表
	 */
	public String simpleSearch(){
		keyword = (keyword == null) ? "" : keyword.toLowerCase();
		StringBuffer hql = new StringBuffer(HQL4COMMON);
		HashMap parMap = new HashMap();
		// 统计类型
		if("person".equals(statisticType)){
			if(listType == 1){
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", statisticType);
			}
		}else if("unit".equals(statisticType)){
			if(listType == 1){
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", statisticType);
			}			
		}else if("project".equals(statisticType)){
			switch (listType){
			case 1:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "project_general");
				break;
			case 2:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "project_instp");
				break;
			case 3:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "project_basep");
				break;
			case 4:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "project_post");
				break;
			case 5:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "project_committee");
				break;
			}
		}else if("product".equals(statisticType)){
			switch(listType){
			case 1:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", statisticType);
				break;
			case 2:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "product_paper");
				break;
			case 3:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "product_book");
				break;
			case 4:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "product_consultation");
				break;
			case 5:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "product_electronic");
				break;
			case 6:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "product_patent");
				break;
			case 7:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "product_otherProduct");
				break;
			}
		}else if("award".equals(statisticType)){
			if(listType == 1){
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", statisticType);
			}
		}
		if(!keyword.isEmpty()){
			int year;
			try {
				year = Integer.parseInt(keyword);
				hql.append(" and LOWER(m.year) =:year");
				parMap.put("year", year);
			} catch (Exception e) {
				hql.append(" and LOWER(m.title) like :keyword ");
				parMap.put("keyword", "%" + keyword + "%");
			}		
		}		
		hql.append(" order by m.title ");
		
		//调用公共接口
		search(hql.toString(), parMap);
		return SUCCESS;
	}
	
	/**
	 * 高级检索
	 */
	public String advSearch(){	
		StringBuffer hql = new StringBuffer(HQL4COMMON);
		HashMap parMap = new HashMap();
		// 统计类型
		if("person".equals(statisticType)){
			if(listType == 1){
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", statisticType);
			}
		}else if("unit".equals(statisticType)){
			if(listType == 1){
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", statisticType);
			}			
		}else if("project".equals(statisticType)){
			switch (listType){
			case 1:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "project_general");
				break;
			case 2:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "project_instp");
				break;
			case 3:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "project_basep");
				break;
			case 4:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "project_post");
				break;
			case 5:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "project_committee");
				break;
			}
		}else if("product".equals(statisticType)){
			switch(listType){
			case 1:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", statisticType);
				break;
			case 2:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "product_paper");
				break;
			case 3:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "product_book");
				break;
			case 4:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "product_consultation");
				break;
			case 5:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "product_electronic");
				break;
			case 6:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "product_patent");
				break;
			case 7:
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", "product_otherProduct");
				break;
			}
		}else if("award".equals(statisticType)){
			if(listType == 1){
				hql.append(" and m.type = :statisticType ");
				parMap.put("statisticType", statisticType);
			}
		}
		//高级检索条件处理
		if(title != null && !title.isEmpty()){
			hql.append(" and m.title = :title ");
			parMap.put("title", title);
		}
		if(year != null && !year.isEmpty()){
			hql.append(" and m.year = :year ");
			parMap.put("year", Integer.parseInt(year));
		}
		if(date != null && !date.isEmpty()){
			hql.append(" and m.date = :date ");
			parMap.put("date", date);
		}
		hql.append(" order by m.title ");
		
		//调用公共接口
		search(hql.toString(), parMap);
		return SUCCESS;
	}
	
	/**
	 * 详情查看
	 * @throws IOException 
	 */
	public String view() throws IOException{
		if(null != entityId && !entityId.isEmpty()) {
			MDXQuery mdxQuery = (MDXQuery) dao.query(MDXQuery.class, entityId);
			if(mdxQuery != null) {
				Result result = statisticService.getMondrianResult(mdxQuery.getType(), mdxQuery.getMdx());
				List<List> dataList=statisticService.getData(result,0);//获取表格数据
				dataList = statisticService.dealWithDatalist(dataList);//添加总计行
				String[] chartConfigs = mdxQuery.getChartConfig().split(";");
				for (String config : chartConfigs) {
					String[] temp = config.trim().split(":");
					String strChartType = temp[0].trim();//图表类型
					String chartShowIndexes = temp[1].trim();//需要显示的列
					jsonMap = mobileStatisticService.getMobileJsonMap(result, strChartType, chartShowIndexes);//获得图表json
					jsonMap.put("dataList", dataList);//常规统计的表格数据
					jsonMap.put("listTitle", mdxQuery.getTitle());//常规统计的标题
//					jsonMap.put("chartTitle", mdxQuery.getTitle());	
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 进入修改统计数据
	 */
	public String toModify(){
		return SUCCESS;
	}
	
	/**
	 * 修改数据统计
	 * @return
	 */
	public String modify(){
		return SUCCESS;
	}
	
	public String getStatisticType() {
		return statisticType;
	}
	public void setStatisticType(String statisticType) {
		this.statisticType = statisticType;
	}
	public Integer getListType() {
		return listType;
	}
	public void setListType(Integer listType) {
		this.listType = listType;
	}
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getYear(){
		return year;
	}
	public void setYear(String year){
		this.year = year;
	}
	public String getDate(){
		return date;
	}
	public void setDate(String date){
		this.date = date;
	}
	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public Integer getChartYear() {
		return chartYear;
	}

	public void setChartYear(Integer chartYear) {
		this.chartYear = chartYear;
	}

	public Date getChartDate() {
		return chartDate;
	}

	public void setChartDate(Date chartDate) {
		this.chartDate = chartDate;
	}

	public String getMdx() {
		return mdx;
	}

	public void setMdx(String mdx) {
		this.mdx = mdx;
	}

	public Integer getChart_config() {
		return chart_config;
	}

	public void setChart_Config(Integer chart_config) {
		this.chart_config = chart_config;
	}

	public Integer getChartColumn() {
		return chartColumn;
	}

	public void setChartColumn(Integer chartColumn) {
		this.chartColumn = chartColumn;
	}

	public Integer getChartType() {
		return chartType;
	}

	public void setChartType(Integer chartType) {
		this.chartType = chartType;
	}

	@Override
	public String pageName() {
		return PAGENAME;
	}
}
