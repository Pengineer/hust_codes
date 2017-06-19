package csdc.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import mondrian.olap.Result;

/**
 * 统计分析接口
 * @author 雷达
 */
public interface IStatisticService extends IBaseService {
	
	/**
	 * 通过schema文件类型和查询语句，在后台生成mondrian的查询结果
	 * @param type 类别(project, award...)
	 * @param MdxQueryString MDX语句
	 * @return mondrian的统计结果
	 */
	public Result getMondrianResult(String type, String MdxQueryString);
	
	/**
	 * 通过mondrian生成的统计结果和统计图的类型生成OpenFlashChart支持的Json格式数据
	 * @param result mondrian的result
	 * @param chartType 图表类型
	 * @param chartShowIndexes 要将那几列显示在图表中（eg. "0, 3, 6"）
	 * @param sortLabelByStatisticIndex 按照统计指标排序：0默认、1降序、2升序
	 * @return 统计图表的Json字串
	 * @throws OFCException
	 * @throws IOException
	 */
	public String getChartJson(Result result, String chartType, String chartShowIndexes, int sortLabelByStatisticIndex) throws IOException ;
	
	/**
	 * 通过mondrian生成的统计结果生成显示列表用的List数据
	 * @param result mondrian的result
	 * @param sortLabelByStatisticIndex 是否按统计指标排序（1：是；0：否）
	 * @return 显示列表用的List数据
	 */
	@SuppressWarnings("unchecked")
	public List<List> getData(Result result, int sortLabelByStatisticIndex);
	
	/**
	 * 根据mondrian生成的统计结果和要钻取的单元格的坐标，生成钻取SQL语句
	 * @param result mondrian的result
	 * @param cell 要钻取的单元格的坐标
	 * @return 钻取SQL语句
	 */
	public String getDrillDownSQL(Result result,int[] cell);
	
	/**
	 * 解析SQL查询语句的列名，并将列名放到list中返回
	 * @param sql
	 * @return SQL查询列的list
	 */
	public List<String> parseSQL(String sql);
	
	/**
	 * 获取钻取级别数据
	 * @param result Mondrian result 对象
	 * @param seq 行号
	 */
	@SuppressWarnings("unchecked")
	public List<List> getLevelData(Result result, int seq);
	
	/**
	 * 将sql文件中的很多语句拆分成单挑语句，放入数组
	 * @param sqlFile 文件名
	 * @return sql数组
	 * @throws Exception
	 */
	public List<String> loadSql(String sqlFile) throws Exception ;
	
	/**
	 * 获取当前账号可统计的区域
	 * @param accountId 账号id
	 * @return 区域名称
	 */
	public String getAccountBelongArea(String accountId);
	
	/**
	 * 获取统计参数的格式化数据
	 * @param data 统计参数
	 * @return 格式化数据
	 */
	public String getFormatedData(String data);
	
	/**
	 * 重构统计分析的数据列表，添加了总计行
	 * @param dataList 统计结果数据list
	 * @return 包含了总计行的数据
	 */
	@SuppressWarnings("unchecked")
	public List<List> dealWithDatalist(List<List> dataList);
	
	/**
	 * 统计结果Excel导出
	 * @param dataList	数据源
	 * @param header	表头名称
	 * @param extras	附加信息：统计指标
	 * @return	输入流
	 */
	public ByteArrayInputStream commonExportExcel(List<List> dataList, String header, List<String> extras);
	
	/**
	 * 获取高校举办者：用于项目定制分析页面的下拉框数据
	 * @return
	 */
	public Map<String,String> getUniversityOrganizer();
	
	/**
	 * 获取项目年度：用于项目定制分析页面的下拉框数据
	 * @return
	 */
	public Map<Object,String> getProjectYear();
	
	/**
	 * 获取奖励届次：用于奖励定制分析页面的下拉框数据
	 * @return
	 */
	public Map<Object,String> getAwardSession();
}
