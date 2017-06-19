package csdc.action.statistic.custom;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import mondrian.olap.Result;
import csdc.action.statistic.BaseStatisticAction;
import csdc.bean.MDXQuery;
import csdc.tool.DesUtils;
import csdc.tool.info.GlobalInfo;

/**
 * 项目定制统计分析
 * @author 雷达，fengcl，suwb
 */
public class ProjectStatisticAction extends BaseStatisticAction{

	private static final long serialVersionUID = 1L;
	
	//切片指标
	private Integer statistic_startYear; //起始年度
	private Integer statistic_endYear; //终止年度
	private String statistic_university; //高校名称
	private String statistic_discipline; //学科门类
	private String statistic_projectType; //项目类型
	private String statistic_subType; //项目子类
	private String statistic_projectArea; //项目区域
	private String statistic_productType; //成果类型
	private String statistic_province; //所在省份
	private String statistic_evaluType; //评审类型
	private String statistic_universityType; //高校类别
	private String statistic_universityOrganizer; //高校举办者
	private String statistic_endStatus; //结项状态
	
	/**
	 * 将定制统计向常规统计导入
	 * @return
	 */
	public String toCommon(){
		try {			
			String mdx = session.get("mdx").toString();
			List<String> parm = (List<String>) session.get("statistic_parm");
			String chartConfig = session.get("chart_config").toString();
			String data = "";
			String index = "";
			String type = "";
			String university = "";
			String discipline = "";
			String years = "";
			String title = "";
			String subType = "";
			String area = "";
			String productType = "";
			String province = "";
			String evaluType = "";
			String universityType = "";
			String organizer = "";
			String endStatus = "";
			for(String statistic_parm : parm){
				if(statistic_parm.contains("统计数据")){
					data = statistic_parm.substring(6);
				}
				if(statistic_parm.contains("统计指标")){
					index = statistic_parm.substring(6);
				}
				if(statistic_parm.contains("项目类型")){
					type = statistic_parm.substring(6);
				}
				if(statistic_parm.contains("项目年度")){
					years = statistic_parm.substring(6);
				}
				if(statistic_parm.contains("高校名称")){
					university = statistic_parm.substring(6);
				}
				if(statistic_parm.contains("学科门类")){
					discipline = statistic_parm.substring(6);
				}
				if(statistic_parm.contains("项目子类")) {
					subType = statistic_parm.substring(6);
				}
				if(statistic_parm.contains("项目区域")) {
					area = statistic_parm.substring(6);
				}
				if(statistic_parm.contains("成果形式")) {
					productType = statistic_parm.substring(6);
				}
				if(statistic_parm.contains("所在省份")) {
					province = statistic_parm.substring(6);
				}
				if(statistic_parm.contains("评审类型")) {
					evaluType = statistic_parm.substring(6);
				}
				if(statistic_parm.contains("高校类别")) {
					universityType = statistic_parm.substring(6);
				}
				if(statistic_parm.contains("高校举办者")) {
					organizer = statistic_parm.substring(6);
				}
				if(statistic_parm.contains("结项状态")) {
					endStatus =  statistic_parm.substring(6);
				}
			}
			if(type.equals("所有项目") && discipline == "" && university == "" && subType == "" && area == "" && productType == "" && province == "" && evaluType == "" && universityType == "" && organizer == "" && endStatus == ""){
				title = years + "关于" + data + "的" + index  + "统计";	
			}else{
				title = years + "针对" + type + university + discipline + subType + area + productType + province + evaluType + universityType + organizer + endStatus + "关于" + data + "的" + index  + "统计";			
			}	
			MDXQuery mdxQuery = new MDXQuery();
			mdxQuery.setMdx(mdx);
			mdxQuery.setTitle(title);
			if(type.equals("所有项目")){
				mdxQuery.setType("project_multiple");							
			}
			else {
				if(type.equals("一般项目")){
					mdxQuery.setType("project_general");					
				}else if(type.equals("基地项目")){
					mdxQuery.setType("project_basep");
				}else if(type.equals("后期资助项目")){
					mdxQuery.setType("project_post");
				}else if(type.equals("重大攻关项目")){
					mdxQuery.setType("project_key");
				}else if(type.equals("应急委托课题")){
					mdxQuery.setType("project_entrust");
				}
			}
			Date currentTime = new Date();
			mdxQuery.setDate(currentTime);
			Calendar c=Calendar.getInstance();
			int year=c.get(Calendar.YEAR);
			mdxQuery.setYear(year);
			mdxQuery.setChartConfig(chartConfig);
			dao.add(mdxQuery);
		} catch (Exception e) {
			jsonMap.put(GlobalInfo.ERROR_INFO, "导入失败，失败原因：\n" + e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 组装用于显示定制统计分析参数的字符串
	 */
	@SuppressWarnings("unchecked")
	public void getStatisticParmString() {
		List<String> statistic_parm = new ArrayList<String>();
		if(statistic_projectType != null && !statistic_projectType.isEmpty()) {
			statistic_parm.add("项目类型：　" + statistic_projectType);
		} else {
			statistic_parm.add("项目类型：　所有项目");
		}
		if (sortLabelByStatisticIndex == 1) {
			statistic_parm.add("统计指标：　" + statisticService.getFormatedData(statistic_index) + "（降序排序）");
		}else if (sortLabelByStatisticIndex == 2) {
			statistic_parm.add("统计指标：　" + statisticService.getFormatedData(statistic_index) + "（升序排序）");
		}else {
			statistic_parm.add("统计指标：　" + statisticService.getFormatedData(statistic_index) + "（默认排序）");
		}
		statistic_parm.add("统计数据：　" + statisticService.getFormatedData(statistic_data));
		if(statistic_startYear.compareTo(statistic_endYear) < 0) {
			statistic_parm.add("项目年度：　" + statistic_startYear + "年 至 " + statistic_endYear  + "年");
		} else if(statistic_startYear.compareTo(statistic_endYear) == 0) {
			statistic_parm.add("项目年度：　" + statistic_startYear + "年");
		}
		if(statistic_university != null && !statistic_university.isEmpty()) {
			statistic_parm.add("高校名称：　" + statistic_university);
		}
		if(statistic_discipline != null && !statistic_discipline.isEmpty()) {
			statistic_parm.add("学科门类：　" + statistic_discipline);
		}
		if(statistic_subType != null && !statistic_subType.isEmpty()) {
			statistic_parm.add("项目子类：　" + statistic_subType);
		}
		if(statistic_projectArea != null && !statistic_projectArea.isEmpty()) {
			statistic_parm.add("项目区域：　" + statistic_projectArea);
		}
		if(statistic_productType != null && !statistic_productType.isEmpty()) {
			statistic_parm.add("成果形式：　" + statistic_productType);
		}
		if(statistic_province != null && !statistic_province.isEmpty()) {
			statistic_parm.add("所在省份：　" + statistic_province);
		}
		if(statistic_evaluType != null && !statistic_evaluType.isEmpty()) {
			statistic_parm.add("评审类型：　" + statistic_evaluType);
		}
		if(statistic_universityType != null && !statistic_universityType.isEmpty()) {
			statistic_parm.add("高校类别：　" + statistic_universityType);
		}
		if(statistic_universityOrganizer != null && !statistic_universityOrganizer.isEmpty()) {
			statistic_parm.add("高校举办者：　" + statistic_universityOrganizer);
		}
		if(statistic_endStatus != null && !statistic_endStatus.isEmpty()) {
			statistic_parm.add("结项状态：　" + statistic_endStatus);
		}
		if(sort_type != 3) {
			String sortString = "";
			if(statistic_sortType == 1) {
				sortString = "（降序排序）";
			} else if(statistic_sortType == 2) {
				sortString = "（升序排序）";
			}
			statistic_parm.add("　排序列：　" + statisticService.getFormatedData(statistic_sortColumn) + sortString);
		}
		Map session = ActionContext.getContext().getSession();
		session.put("statistic_parm", statistic_parm);
		session.put("sortLabelByStatisticIndex", sortLabelByStatisticIndex);//统计指标的排序方式存入session
	}
	
	/**
	 * 进入定制页面
	 * @return
	 */
	public String toCustomStatistic(){
		return SUCCESS;
	}

	/**
	 * 项目定制分析，通过用户的选择条件拼接MDX
	 * @return
	 * @throws Exception 
	 */
	public String customStatistic() throws Exception {
		setMdx_columns(statistic_data);//统计数据：纵坐标
		setMdx_rows(statistic_index);//统计指标：横坐标
		setMdx_sortColumn(statistic_sortColumn);//排序列
		String[] indexes = statistic_data.split(",");
		int sortNum = 0;
		for(int i = 0; i < indexes.length; i++) {
			if(indexes[i].trim().equals(statistic_sortColumn)) {
				sortNum = i;//获取排序指标的序号
				break;
			}
		}
		StringBuffer chartConfigTmp = new StringBuffer(chartType + ":" + sortNum);
		for(int i = 0; i < indexes.length; i++) {
			if(i != sortNum) {
				chartConfigTmp.append("," + i);//补全后面的展现列
			}
		}
		setChartConfig(chartConfigTmp.toString());//该定制统计分析的图表配置字串
		session.put("chart_config", getChartConfig());
		if(statistic_startYear > statistic_endYear) {
			statistic_startYear = statistic_endYear;//起始年不能大于终止年
		}
		//测量值：组装各类统计总和的mdx方便后续使用
		String applyCount = "[Measures].[applyCount]";
		String grantedCount = "[Measures].[grantedCount]";
		String passMidCount = "[Measures].[passMidCount]";
		String passEndCount = "[Measures].[passEndCount]";
		String approveFee = "[Measures].[approveFee]";
		//如果"项目年度"不是统计指标，则项目年度作为范围条件，计算项目年度区间的总和
		if (!getMdx_rows().contains("[项目维度.项目年度].[项目年度].Members")) {
			String yearString = " sum({[项目维度.项目年度].[" + statistic_startYear + "]:[项目维度.项目年度].[" + statistic_endYear + "]},";
			applyCount = yearString + applyCount + ") ";
			grantedCount = yearString + grantedCount + ") ";
			passMidCount = yearString + passMidCount + ") ";
			passEndCount = yearString + passEndCount + ") ";
			approveFee = yearString + approveFee + ") ";
		} else {//如果"项目年度"是统计指标，则结合项目年度的起止年份进行过滤
			String mdxRows = "[项目维度.项目年度].[" + statistic_startYear + "]";
			int years = statistic_endYear - statistic_startYear;
			for(int i = 1; i <= years; i++){
				mdxRows += ", [项目维度.项目年度].[" + (statistic_startYear + i) + "]";
			}
			setMdx_rows(mdxRows);
		}
		
		//组装各类计算XX率的mdx方便后续使用
		StringBuffer mdxBuffer = new StringBuffer("with ");
		if(statistic_data.contains("[Measures].[立项率]")) {
			mdxBuffer.append(" member [Measures].[立项率] as 'IIf((" + applyCount + " > 0.0), (" + grantedCount + " / " + applyCount + "), 0.0)', FORMAT_STRING = \"0.00%\" ");
		}
		if(statistic_data.contains("[Measures].[中检通过率]")) {
			mdxBuffer.append(" member [Measures].[中检通过率] as 'IIf((" + grantedCount + " > 0.0), (" + passMidCount + " / " + grantedCount + "), 0.0)', FORMAT_STRING = \"0.00%\" ");
		}
		if(statistic_data.contains("[Measures].[结项率]")) {
			mdxBuffer.append(" member [Measures].[结项率] as 'IIf((" + grantedCount + " > 0.0), (" + passEndCount + " / " + grantedCount + "), 0.0)', FORMAT_STRING = \"0.00%\" ");
		}
		if(statistic_data.contains("[Measures].[申报数]")) {
			mdxBuffer.append(" member [Measures].[申报数] as " + applyCount);
		}
		if(statistic_data.contains("[Measures].[立项数]")) {
			mdxBuffer.append(" member [Measures].[立项数] as " + grantedCount);
		}
		if(statistic_data.contains("[Measures].[中检通过数]")) {
			mdxBuffer.append(" member [Measures].[中检通过数] as " + passMidCount);
		}
		if(statistic_data.contains("[Measures].[结项数]")) {
			mdxBuffer.append(" member [Measures].[结项数] as " + passEndCount);
		}
		if(statistic_data.contains("[Measures].[批准经费]")) {
			mdxBuffer.append(" member [Measures].[批准经费] as " + approveFee);
		}
		mdxBuffer.append(" select { " + getMdx_columns() + " } ON COLUMNS, ");
		String rowsString = "";
		if(statistic_showNonZero == 1) {//只统计非零行
			rowsString = " Filter({ " + getMdx_rows() + " }, " + getMdx_sortColumn() + " > 0) ";
		} else if (statistic_showNonZero == 0) {
			rowsString = "{ " + getMdx_rows() + " }";
		}
		if(sort_type == 3) {//默认排序
			if(getStatistic_showLineNum() != -1) {//配置结果显示条数
				mdxBuffer.append(" Head( " + rowsString + ", " + statistic_showLineNum + " ) " );
			} else {
				mdxBuffer.append(rowsString);
			}
		} else {//自定义排序
			if (statistic_sortType == 1) {//降序
				if(getStatistic_showLineNum() == -1) {//显示结果全部行
					mdxBuffer.append(" Order( " + rowsString + ", " + getMdx_sortColumn() + ", BDESC ) " );
				} else {
					mdxBuffer.append(" TopCount( " + rowsString + ", " + getStatistic_showLineNum() + ", " + getMdx_sortColumn() + " ) " );
				}	
			} else if (statistic_sortType == 2) {//升序
				if(getStatistic_showLineNum() == -1) {//显示结果全部行
					mdxBuffer.append(" Order( " + rowsString + ", " + getMdx_sortColumn() + ", BASC ) " );
				} else {
					mdxBuffer.append(" BottomCount( " + rowsString + ", " + getStatistic_showLineNum() + ", " + getMdx_sortColumn() + " ) " );
				}
			} 
		}
		mdxBuffer.append(" ON ROWS from [Project] ");//查询项目立方体
		//下面都是切片指标
		List<String> whereStrings = new ArrayList<String>();
		if(!mdx_rows.contains("[机构维度.高校名称]") && statistic_university != null && !statistic_university.isEmpty()) {
			whereStrings.add("[机构维度.高校名称].[" + statistic_university + "]");
		}
		if(!mdx_rows.contains("[机构维度.省份名称].[省份名称]") && statistic_province != null && !statistic_province.isEmpty()) {
			whereStrings.add("[机构维度.省份名称].[省份名称].[" + statistic_province + "]");
		}
		if(!mdx_rows.contains("[项目维度.项目子类]") && statistic_subType != null && !statistic_subType.isEmpty()) {
			whereStrings.add("[项目维度.项目子类].[" + statistic_subType + "]");
		}
		if(!mdx_rows.contains("[项目维度.项目区域]") && statistic_projectArea != null && !statistic_projectArea.isEmpty()) {
			whereStrings.add("[项目维度.项目区域].[" + statistic_projectArea + "]");
		}
		if(!mdx_rows.contains("[项目维度.成果形式]") && statistic_productType != null && !statistic_productType.isEmpty()) {
			whereStrings.add("[项目维度.成果形式].[" + statistic_productType + "]");
		}
		if(!mdx_rows.contains("[项目维度.项目类型]") && statistic_projectType != null && !statistic_projectType.isEmpty()) {
			whereStrings.add("[项目维度.项目类型].[" + statistic_projectType + "]");
		}
		if(!mdx_rows.contains("[项目维度.评审类型]") && statistic_evaluType != null && !statistic_evaluType.isEmpty()) {
			whereStrings.add("[项目维度.评审类型].[" + statistic_evaluType + "]");
		}
		if(!mdx_rows.contains("[机构维度.高校类别]") && statistic_universityType != null && !statistic_universityType.isEmpty()) {
			whereStrings.add("[机构维度.高校类别].[" + statistic_universityType + "]");
		}
		if(!mdx_rows.contains("[机构维度.举办者]") && statistic_universityOrganizer != null && !statistic_universityOrganizer.isEmpty()) {
			whereStrings.add("[机构维度.举办者].[" + statistic_universityOrganizer + "]");
		}
		if(!mdx_rows.contains("[项目维度.是否结项]") && statistic_endStatus != null && !statistic_endStatus.isEmpty()) {
			whereStrings.add("[项目维度.是否结项].[" + statistic_endStatus + "]");
		}
		if(!mdx_rows.contains("[项目维度.学科门类]") && statistic_discipline != null && !statistic_discipline.isEmpty()) {
			if(statistic_discipline.trim().equals("马克思主义")) {
				statistic_discipline = "马克思主义理论/思想政治教育";
			}
			whereStrings.add("[项目维度.学科门类].[" + statistic_discipline + "]");
		}
		//根据用户账号级别限制其统计的数据范围
		if(statisticService.getAccountBelongArea(loginer.getAccount().getId()) != null) {
			whereStrings.add(statisticService.getAccountBelongArea(loginer.getAccount().getId()));
		}
		//将前面的切片指标拼装好
		if(whereStrings.size() > 0) {
			mdxBuffer.append(" where (");
			for(int i = 0; i < whereStrings.size() - 1; i++) {
				mdxBuffer.append(whereStrings.get(i) + ",");
			}
			mdxBuffer.append(whereStrings.get(whereStrings.size() - 1));
			mdxBuffer.append(")");
		}
//		System.out.println("mdxBuffer = " + mdxBuffer);
		DesUtils des = new DesUtils();
		//将生成mdx加密供前台显示统计表和统计图用
		encryptedMdxQueryString = des.encrypt(mdxBuffer.toString());
		request.getSession().setAttribute("measureNum", statistic_data.split(",").length);
		getStatisticParmString();
		return SUCCESS;
	}
	
	/**
	 * 查看定制统计结果
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String toView() throws Exception {
		Map session = ActionContext.getContext().getSession();
		session.put("statisticType", "project");
		session.put("statisticListType", "社科项目统计");
		DesUtils des = new DesUtils();
		mdxQueryString = des.decrypt(encryptedMdxQueryString);//解密获取mdx语句
		Result result=statisticService.getMondrianResult("project", mdxQueryString);//获取统计结果
		sortLabelByStatisticIndex = (Integer) session.get("sortLabelByStatisticIndex");
		List<List> dataList=statisticService.getData(result, sortLabelByStatisticIndex);//将结果转成jsp方便显示的list类型
		//重构数据列表，并添加总计行
		dataList = statisticService.dealWithDatalist(dataList);
		session.put("dataList", dataList);
		session.put("mdx", mdxQueryString);
		session.put("statisticTitle", "社科项目定制统计");
		statisticType = "project";
		if(null != session.get("resultLines")){
			resultLines = (Integer)session.get("resultLines");
		}
		return SUCCESS;
	}

	public Integer getStatistic_startYear() {
		return statistic_startYear;
	}

	public void setStatistic_startYear(Integer statisticStartYear) {
		statistic_startYear = statisticStartYear;
	}

	public Integer getStatistic_endYear() {
		return statistic_endYear;
	}

	public void setStatistic_endYear(Integer statisticEndYear) {
		statistic_endYear = statisticEndYear;
	}

	public String getStatistic_university() {
		return statistic_university;
	}

	public void setStatistic_university(String statisticUniversity) {
		statistic_university = statisticUniversity;
	}

	public String getStatistic_discipline() {
		return statistic_discipline;
	}

	public void setStatistic_discipline(String statisticDiscipline) {
		statistic_discipline = statisticDiscipline;
	}

	public String getStatistic_projectType() {
		return statistic_projectType;
	}

	public void setStatistic_projectType(String statisticProjectType) {
		statistic_projectType = statisticProjectType;
	}

	public String getStatistic_subType() {
		return statistic_subType;
	}

	public void setStatistic_subType(String statisticSubType) {
		statistic_subType = statisticSubType;
	}

	public String getStatistic_projectArea() {
		return statistic_projectArea;
	}

	public void setStatistic_projectArea(String statisticProjectArea) {
		statistic_projectArea = statisticProjectArea;
	}

	public String getStatistic_productType() {
		return statistic_productType;
	}

	public void setStatistic_productType(String statisticProductType) {
		statistic_productType = statisticProductType;
	}

	public String getStatistic_province() {
		return statistic_province;
	}

	public void setStatistic_province(String statisticProvince) {
		statistic_province = statisticProvince;
	}

	public String getStatistic_evaluType() {
		return statistic_evaluType;
	}

	public void setStatistic_evaluType(String statisticEvaluType) {
		statistic_evaluType = statisticEvaluType;
	}

	public String getStatistic_universityType() {
		return statistic_universityType;
	}

	public void setStatistic_universityType(String statisticUniversityType) {
		statistic_universityType = statisticUniversityType;
	}
	public String getStatistic_universityOrganizer() {
		return statistic_universityOrganizer;
	}
	public void setStatistic_universityOrganizer(String statistic_universityOrganizer) {
		this.statistic_universityOrganizer = statistic_universityOrganizer;
	}

	public String getStatistic_endStatus() {
		return statistic_endStatus;
	}

	public void setStatistic_endStatus(String statistic_endStatus) {
		this.statistic_endStatus = statistic_endStatus;
	}
}
