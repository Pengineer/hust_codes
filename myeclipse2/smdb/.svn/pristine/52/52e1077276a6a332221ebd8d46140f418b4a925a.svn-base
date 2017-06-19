package csdc.action.statistic.custom;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mondrian.olap.Result;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.statistic.BaseStatisticAction;
import csdc.bean.MDXQuery;
import csdc.tool.DesUtils;
import csdc.tool.info.GlobalInfo;
/**
 * 成果定制统计分析
 * @author 雷达，fengcl，suwb
 */
public class ProductStatisticAction extends BaseStatisticAction {

	private static final long serialVersionUID = 7665649933697133548L;
	private String statistic_university_name;// 指定高校统计
	
	/**
	 * 将定制统计向常规统计导入
	 * @return
	 */
	public String toCommon(){
		try {		
			String mdx = session.get("mdx").toString();
			List<String> parm = (List<String>) session.get("statistic_parm");
			String chartConfig = session.get("chart_config").toString();
			String university = "";
			String data = "";
			String index = "";
			String title = "";
			for(String statistic_parm : parm){
				if(statistic_parm.contains("高校名称")){
					university = statistic_parm.substring(6);
				}
				if(statistic_parm.contains("统计数据")){
					data = statistic_parm.substring(6);
				}
				if(statistic_parm.contains("统计指标")){
					index = statistic_parm.substring(6);
				}
			}
			if(university != ""){
				title ="针对" + university + "关于" + data + "的" + index  + "统计";			
			}else{
				title ="关于" + data + "的" + index  + "统计";
			}
			MDXQuery mdxQuery = new MDXQuery();
			mdxQuery.setMdx(mdx);
			mdxQuery.setTitle(title);
			mdxQuery.setType("product_multiple");
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
		if (sortLabelByStatisticIndex == 1) {
			statistic_parm.add("统计指标：　" + statisticService.getFormatedData(statistic_index) + "（降序排序）");
		}else if (sortLabelByStatisticIndex == 2) {
			statistic_parm.add("统计指标：　" + statisticService.getFormatedData(statistic_index) + "（升序排序）");
		}else {
			statistic_parm.add("统计指标：　" + statisticService.getFormatedData(statistic_index) + "（默认排序）");
		}
		if(statistic_university_name != null && !statistic_university_name.isEmpty()) {
			statistic_parm.add("高校名称：　" + statistic_university_name);
		}
		statistic_parm.add("统计数据：　" + statisticService.getFormatedData(statistic_data));
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
	 * 人员定制分析，通过条件拼接MDX
	 * @return
	 * @throws Exception 
	 */
	public String customStatistic() throws Exception {
		setMdx_columns(statistic_data);
		setMdx_rows(statistic_index);
		setMdx_sortColumn(statistic_sortColumn);
		String[] indexes = statistic_data.split(",");
		int sortNum = 0;
		for(int i = 0; i < indexes.length; i++) {
			if(indexes[i].trim().equals(statistic_sortColumn)) {
				sortNum = i;
				break;
			}
		}
		StringBuffer chartConfigTmp = new StringBuffer(chartType + ":" + sortNum);
		for(int i = 0; i < indexes.length; i++) {
			if(i != sortNum) {
				chartConfigTmp.append("," + i);
			}
		}
//		System.out.println(chartConfigTmp.toString());
		setChartConfig(chartConfigTmp.toString());
		StringBuffer mdxBuffer = new StringBuffer();
		mdxBuffer.append(" select { " + getMdx_columns() + " } ON COLUMNS, ");
		String rowsString = "";
		if(statistic_showNonZero == 1) {//只统计非零行
			rowsString = " Filter({ " + getMdx_rows() + " }, " + getMdx_sortColumn() + " > 0) ";
		} else if (statistic_showNonZero == 0) {
			rowsString = "{ " + getMdx_rows() + " }";
		}
		if(sort_type == 3) {
			if(getStatistic_showLineNum() != -1) {
				mdxBuffer.append(" Head( " + rowsString + ", " + statistic_showLineNum + " ) " );
			} else {
				mdxBuffer.append(rowsString);
			}
		} else {
			if (statistic_sortType == 1) {
				if(getStatistic_showLineNum() == -1) {
					mdxBuffer.append(" Order( " + rowsString + ", " + getMdx_sortColumn() + ", BDESC ) " );
				} else {
					mdxBuffer.append(" TopCount( " + rowsString + ", " + getStatistic_showLineNum() + ", " + getMdx_sortColumn() + " ) " );
				}	
			} else if (statistic_sortType == 2) {
				if(getStatistic_showLineNum() == -1) {
					mdxBuffer.append(" Order( " + rowsString + ", " + getMdx_sortColumn() + ", BASC ) " );
				} else {
					mdxBuffer.append(" BottomCount( " + rowsString + ", " + getStatistic_showLineNum() + ", " + getMdx_sortColumn() + " ) " );
				}
			} 
		}
		mdxBuffer.append(" ON ROWS from [Product] ");
		//下面都是切片指标
		if(statistic_university_name != null && !statistic_university_name.isEmpty()) {
			mdxBuffer.append(" where ([机构维度.高校名称].&[" + statistic_university_name + "])");
		}
//		System.out.println("mdxBuffer = " + mdxBuffer);
		DesUtils des = new DesUtils();
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
		session.put("statisticType", "product");
		session.put("statisticListType", "社科成果统计");
		DesUtils des = new DesUtils();
		mdxQueryString = des.decrypt(encryptedMdxQueryString);
		Result result=statisticService.getMondrianResult("product", mdxQueryString);
		sortLabelByStatisticIndex = (Integer) session.get("sortLabelByStatisticIndex");
		List<List> dataList=statisticService.getData(result, sortLabelByStatisticIndex);
		//重构数据列表，并添加总计行
		dataList = statisticService.dealWithDatalist(dataList);
		session.put("mdx", mdxQueryString);
		session.put("chart_config", getChartConfig());
		session.put("dataList", dataList);
		session.put("statisticTitle", "社科成果定制统计");
		statisticType = "product";
		if(null != session.get("resultLines")){
			resultLines = (Integer)session.get("resultLines");
		}
		return SUCCESS;
	}

	public String getStatistic_university_name() {
		return statistic_university_name;
	}

	public void setStatistic_university_name(String statisticUniversityName) {
		statistic_university_name = statisticUniversityName;
	}
}
