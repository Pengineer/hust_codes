package csdc.action.statistic;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import mondrian.olap.Axis;
import mondrian.olap.Member;
import mondrian.olap.Position;
import mondrian.olap.Result;

import org.apache.struts2.ServletActionContext;

import sun.misc.BASE64Decoder;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.MDXQuery;
import csdc.service.IStatisticService;
import csdc.tool.DesUtils;
import csdc.tool.info.GlobalInfo;

/**
 * 统计分析基类Action，提供查看统计图、更新数据仓库、下载统计图、表格钻取等基础功能接口
 * @author 雷达，fengcl
 */
public class BaseStatisticAction extends BaseAction  {
	
	private static final long serialVersionUID = 1L;
	protected IStatisticService statisticService;
	private String chartShowIndexes; //需要显示的列
	protected String statisticType; //统计类型（project, award...）
	protected int resultLines; //结果的行数
	protected int sortLabelByStatisticIndex; //按照统计指标排序：0默认、1降序、2升序
	
	protected String mdxQueryString; //mdx查询语句
	protected String encryptedMdxQueryString; //加密后的mdx查询语句
	protected String mdx_rows; //mdx查询语句中的rows
	protected String mdx_columns; //mdx查询语句中的columns
	protected String mdx_sortColumn; //mdx查询语句中的sortColumn
	
	protected String statistic_data; //需要统计的数据
	protected String statistic_index; //统计指标
	protected String statistic_sortColumn; //排序指标
	protected int statistic_showLineNum; //显示行数
	protected int sort_type; //排序方式  1.默认排序， 2.自定义排序， 3.不排序
	protected int statistic_sortType; //1 降序，2 升序
	protected int statistic_showNonZero; //1只显示非零行 0全部显示
	protected String imageBase64Str; //前台post来的统计图片的base64码
	protected String filepath; //统计图文件路径
	protected String MDXQueryId;
	
	//图表配置(多个配置用分号隔开；每个配置中用引号隔开，引号左边为图表类型，
	//引号右边为用逗号隔开的数字，第一个数字代表排序列栏位，
	//后面跟着的数字代表要显示的tip栏位
	protected String chartConfig; //图表配置（eg "PIE:0,1,2; NORMAL_BAR:3,1"）
	protected String chartType; // 图表类型
	
	protected Integer cell_row;//钻取元数据所在行
	protected Integer cell_column;//钻取元数据所在列
	
	private static String pageName="drillpage";
	private static String dateFormat = "yyyy-MM-dd";// 列表时间格式
	private static String pageBufferId = "C_ID";
	private static String[] column = new String[]{"C_ID"};// 排序列
	private static Object[] searchCondition;
	//导出
	private String fileFileName;//导出文件名
	
	/**
	 * 导出excel
	 * @return
	 */
	public String exportExcel(){
		return SUCCESS;
	}
	
	/**
	 * 导出excel
	 * @return 输入流
	 */
	public InputStream getDownloadFile() throws Exception{
		String header = session.get("statisticTitle") + "";
		//导出的Excel文件名
		fileFileName = header + ".xls";
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
		//获取Excel数据
		if(null == session.get("dataList")){
			return null;
		}
		List dataList = (List) session.get("dataList");	//Excel正文数据源
		List<String> extras = (List<String>) session.get("statistic_parm");	//Excel附加信息：统计指标
		return statisticService.commonExportExcel(dataList, header, extras);
	}
	
	/**
	 * 下载统计图
	 * @return 成功返回
	 */
	public String downloadImage(){
		return SUCCESS;
	}
	
	/**
	 * 生成处理后的统计图的Base64码缓存
	 * @return 是否成功
	 * @throws UnsupportedEncodingException
	 * @author leida 2011-11-04
	 */
	@SuppressWarnings("unchecked")
	public String generateImage() throws UnsupportedEncodingException {
		// Base64码post的时候'+'会被替换成' '，此处将其还原
		imageBase64Str = imageBase64Str.replace(' ', '+');
		Map session = ActionContext.getContext().getSession();
		session.put("imageBase64Str", imageBase64Str);
		return SUCCESS;
	}
	
	/**
	 * 生成统计图文件流
	 * @return 统计图文件流
	 * @throws Exception
	 * @author leida 2011-11-04
	 */
	@SuppressWarnings("unchecked")
	public InputStream getTargetImage() throws Exception{
		Map session = ActionContext.getContext().getSession();
		imageBase64Str = (String) session.get("imageBase64Str");
		session.remove("imageBase64Str");
		BASE64Decoder decoder = new BASE64Decoder();
		filepath = new String("统计图.jpg".getBytes(), "ISO-8859-1");
		try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imageBase64Str);
            InputStream is = new ByteArrayInputStream(bytes);
            return is;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	/**
	 * 展示图表
	 * @return
	 * @throws Exception
	 */
	public String showChart() throws Exception {
		MDXQuery mdxQuery = null;
		if(MDXQueryId != null && !MDXQueryId.isEmpty()) {//常规统计分析，直接通id获取mdx及其他内容
			mdxQuery = (MDXQuery) dao.query(MDXQuery.class, MDXQueryId);
			if(mdxQuery != null) {
				mdxQueryString = mdxQuery.getMdx();
				statisticType = mdxQuery.getType();
				chartConfig = mdxQuery.getChartConfig();
			}
		} else {//定制统计分析
			DesUtils des = new DesUtils();
			mdxQueryString = des.decrypt(encryptedMdxQueryString); //获得解密后可执行的mdx查询统计语句
		}
//		System.out.println(mdxQueryString);
		//获取统计分析结果
		Result result = statisticService.getMondrianResult(statisticType, mdxQueryString);
		String chartJson = null;
		//如果常规统计分析已经编辑好图表json则直接使用此json画图
		if(MDXQueryId != null && !MDXQueryId.isEmpty() && mdxQuery.getChartJson() != null) {
			chartJson = mdxQuery.getChartJson();//直接获取编辑好的json
		} else {
			//通过实时的统计分析结果获得图表json
			sortLabelByStatisticIndex = session.containsKey("sortLabelByStatisticIndex") ? (Integer) session.get("sortLabelByStatisticIndex") : 0;
			chartJson = statisticService.getChartJson(result, chartType, chartShowIndexes, sortLabelByStatisticIndex); 
		}
	    //返回response响应结果
		HttpServletResponse response = ServletActionContext.getResponse();
	    response.setContentType("application/json-rpc;charset=utf-8");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setHeader("Expires", "0");
	    response.setHeader("Pragma", "No-cache");
	    response.getWriter().write(chartJson);
	    response.getWriter().flush();
	    response.getWriter().close();
	    return null;
	}
	
	/**
	 * 表格钻取
	 */
	@SuppressWarnings("unchecked")
	public String drillDown() throws Exception{
		
		//1、获取解密后的MDX查询语句
		DesUtils des = new DesUtils();
		mdxQueryString = des.decrypt(encryptedMdxQueryString);
		Result result = statisticService.getMondrianResult(statisticType, mdxQueryString);
		
		//2、获取钻取的元素cell，其中cell_column列号和cell_row行号是从前台传递过来的
		int[] cell = new int[] {getCell_column()-2, getCell_row()-1};
		
		//3、判定该cell能否被钻取
		String cellValue = (null != result.getCell(cell) && null != result.getCell(cell).getValue()) ? result.getCell(cell).getValue().toString() : "0";
		if(Float.parseFloat(cellValue) == 0){
			jsonMap.put(GlobalInfo.ERROR_INFO, "该数据不能钻取！");
			return SUCCESS;
		}
		 
		//4、获取钻取的SQL语句
		String sql = statisticService.getDrillDownSQL(result, cell);
		System.out.println("SQL1：" + sql);
		
		//5、获取查询的字段列表
		List<String> headList = statisticService.parseSQL(sql);
		Map session = ActionContext.getContext().getSession();
		
		//6、组装、拼接SQL语句
		StringBuffer sb =new StringBuffer("select ");
		String columnName = (String)session.get("columnName");
		List<String> columnList = (List<String>)session.get("columnList");
		
		String tabName = columnList.get(0);
		tabName = tabName.split("\\.")[0];
		
		//钻取列表上要显示的列
		String[] columns = DrillProperty.drillMap.get(columnName);//根据columnName（统计名）获取钻取列表上要显示的列（通过DrillProperty中配置的）
		jsonMap.put("headList", Arrays.asList(columns));
		
		if(columnName.equals("立项数")){	//对于立项数，在sql语句中添加“已立项”过滤条件，调用concat函数进行拼接
			sql = sql.substring(0,sql.lastIndexOf("order by"))
					.concat(" and \"S_D_PROJECT\".C_IS_GRANTED = '已立项' ")
						.concat(sql.substring(sql.lastIndexOf("order by")));
		}
		if(columnName.equals("结项数")){	//对于结项数，在sql语句中添加“已结项”过滤条件，调用concat函数进行拼接
			sql = sql.substring(0,sql.lastIndexOf("order by"))
					.concat(" and \"S_D_PROJECT\".C_IS_FINISHED = '已结项' ")
						.concat(sql.substring(sql.lastIndexOf("order by")));
		}
		if(columnName.equals("中检通过数")){
			sql = sql.substring(0,sql.lastIndexOf("order by"))
					.concat(" and \"S_D_PROJECT\".C_IS_PASSMID = '通过中检' ")
						.concat(sql.substring(sql.lastIndexOf("order by")));
		}
		if(columnName.equals("部属高校") || columnName.equals("地方高校")){
			sql = sql.substring(0,sql.lastIndexOf("order by"))
					.concat(" and \"S_D_UNIT\".\"C_TYPE\" = '" + columnName + "' ")
						.concat(sql.substring(sql.lastIndexOf("order by")));
		}
		for(String s : new String[]{"特等奖", "一等奖", "二等奖", "三等奖", "普及奖"}){
			if(columnName.equals(s)){
				sql = sql.substring(0,sql.lastIndexOf("order by"))
					.concat(" and \"S_D_AWARD\".\"C_GRADE\" = '" + columnName + "' ")
						.concat(sql.substring(sql.lastIndexOf("order by")));
				break;
			}
		}
		
		Axis[] axs = result.getAxes();
		List<Position> pos = axs[1].getPositions();
		List<Member> mem = pos.get(getCell_row()-1);
		String drillrow = mem.get(0).getName();
		
		jsonMap.put("drillRow", drillrow);
		jsonMap.put("drillColumn",columnName);
		
		for(int i = 0; i < columns.length; i++){
			if(columns[i].equals("姓名") || columns[i].equals("负责人")){
				sb.append("\"S_D_PERSON\".\"C_NAME\" as \"" + columns[i] + "\", ");
			}
			for(int j=0; j < headList.size(); j++){
				if(headList.get(j).equals(columns[i])){
					if(i < columns.length - 1){
						sb.append(columnList.get(j)).append(", ");
					} else {
						sb.append(columnList.get(j));
					}
				} 
			}
		}
		
		sb.append(" " + sql.substring(sql.indexOf("from")));//截取sql语句from后面的语句
		sql = sb.toString();
		
		System.out.println("SQL2：" + sql);
		
		//初始化列表相关属性
		keyword = "";
		BaseStatisticAction.column = new String[]{tabName + ".\"C_ID\""};
		BaseStatisticAction.pageBufferId = tabName + ".\"C_ID\"";
		BaseStatisticAction.searchCondition = new Object[]{sql.substring(0, sql.lastIndexOf("order by")), new HashMap(), 0, null};
		//查询、获取钻取列表数据
		this.toList();
		this.simpleSearch();

		return SUCCESS;
	}
	
	/**
	 * 切片级别钻取
	 */
	@SuppressWarnings("unchecked")
	public String drillLevel() throws Exception{
		DesUtils des = new DesUtils();
		mdxQueryString = des.decrypt(encryptedMdxQueryString);
		Result result = statisticService.getMondrianResult(statisticType, mdxQueryString);
		List<List> dataList = statisticService.getLevelData(result, cell_row);
		jsonMap.put("dataList", dataList);
		return SUCCESS;
	}
	
	/**
	 * 更新数据仓库
	 * @return 更新成功
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String updateDW() throws Exception {
		Connection con = null;
		Statement smt = null;
		try {
			//读取数据源配置文件
			String jndiname = statisticService.getPropertiesValue(getClass(), "/init.properties", "jndiname");
			//获取数据源
			Context ic = new InitialContext();
			DataSource source = (DataSource) ic.lookup(jndiname);

			//更新过程放到事务中
			try {
				con = source.getConnection();
				//此处调用setAutoCommit(false)指定要在事务中提交(将自动提交关闭了)
				con.setAutoCommit(false);
				smt = con.createStatement();
				//获取数据仓库的SQL脚本文件
				String realPath = ServletActionContext.getServletContext().getRealPath("/statistic/build/build.sql");
				//解析脚本，并拆分成单挑语句，方便批处理执行
				List<String> sqlList = statisticService.loadSql(realPath);
				for (String sql : sqlList) {
					if(null != sql && !sql.isEmpty() && !"null".equals(sql)) {
						smt.addBatch(sql);//添加批处理任务
					}
	            }
				System.out.println("开始更新数据仓库...");
	            smt.executeBatch();//批处理
	            con.commit();//提交
	            
	            //只更新数据仓库内容不会触发mordrian缓存更新，会导致数据仓库的更新不能反映到系统中
	            mondrian.rolap.agg.AggregationManager.instance().getCacheControl(null).flushSchemaCache();//更新mordrian缓存
	            
	            //记录更新时间，更新系统配置表中的ID = 'sysconfig00026'的字段值
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
	            String updateTime = sdf.format(new Date());
	            dao.execute("update SystemConfig sc set sc.value = ? where sc.id = ?", updateTime, "sysconfig00026");
	            ActionContext.getContext().getApplication().put("DWUpdateTime", updateTime);
	            
	            System.out.println("更新数据仓库完毕！");
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();//回滚
				jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO + "\n错误信息：" + e.getMessage());
	        } finally {
	        	if (smt != null) smt.close();
	        	if (con != null) con.close();
	        }   
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO + "\n错误信息：" + e.getMessage());
		}
		return SUCCESS;
	}
	
	public IStatisticService getStatisticService() {
		return statisticService;
	}

	public void setStatisticService(IStatisticService statisticService) {
		this.statisticService = statisticService;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getChartShowIndexes() {
		return chartShowIndexes;
	}

	public void setChartShowIndexes(String chartShowIndexes) {
		this.chartShowIndexes = chartShowIndexes;
	}

	public String getEncryptedMdxQueryString() {
		return encryptedMdxQueryString;
	}

	public void setEncryptedMdxQueryString(String encryptedMdxQueryString) {
		this.encryptedMdxQueryString = encryptedMdxQueryString;
	}

	public String getStatisticType() {
		return statisticType;
	}

	public void setStatisticType(String statisticType) {
		this.statisticType = statisticType;
	}

	public int getResultLines() {
		return resultLines;
	}

	public void setResultLines(int resultLines) {
		this.resultLines = resultLines;
	}
	public int getSortLabelByStatisticIndex() {
		return sortLabelByStatisticIndex;
	}
	public void setSortLabelByStatisticIndex(int sortLabelByStatisticIndex) {
		this.sortLabelByStatisticIndex = sortLabelByStatisticIndex;
	}
	public String getMdxQueryString() {
		return mdxQueryString;
	}

	public void setMdxQueryString(String mdxQueryString) {
		this.mdxQueryString = mdxQueryString;
	}

	public String getStatistic_data() {
		return statistic_data;
	}

	public void setStatistic_data(String statisticData) {
		statistic_data = statisticData;
	}

	public String getStatistic_index() {
		return statistic_index;
	}

	public void setStatistic_index(String statisticIndex) {
		statistic_index = statisticIndex;
	}

	public String getStatistic_sortColumn() {
		return statistic_sortColumn;
	}

	public void setStatistic_sortColumn(String statisticSortColumn) {
		statistic_sortColumn = statisticSortColumn;
	}

	public int getStatistic_showLineNum() {
		return statistic_showLineNum;
	}

	public void setStatistic_showLineNum(int statisticShowLineNum) {
		statistic_showLineNum = statisticShowLineNum;
	}

	public int getStatistic_sortType() {
		return statistic_sortType;
	}

	public void setStatistic_sortType(int statisticSortType) {
		statistic_sortType = statisticSortType;
	}

	public String getMdx_rows() {
		return mdx_rows;
	}

	public void setMdx_rows(String mdxRows) {
		mdx_rows = mdxRows;
	}

	public String getMdx_columns() {
		return mdx_columns;
	}

	public void setMdx_columns(String mdxColumns) {
		mdx_columns = mdxColumns;
	}

	public String getMdx_sortColumn() {
		return mdx_sortColumn;
	}

	public void setMdx_sortColumn(String mdxSortColumn) {
		mdx_sortColumn = mdxSortColumn;
	}

	public String getChartConfig() {
		return chartConfig;
	}

	public void setChartConfig(String chartConfig) {
		this.chartConfig = chartConfig;
	}

	@Override
	public String[] column() {
		return BaseStatisticAction.column;
	}
	@Override
	public String dateFormat() {
		return BaseStatisticAction.dateFormat;
	}
	@Override
	public String pageBufferId() {
		return BaseStatisticAction.pageBufferId;
	}
	@Override
	public String pageName() {
		return BaseStatisticAction.pageName;
	}
	public Integer getCell_row() {
		return cell_row;
	}
	public void setCell_row(Integer cellRow) {
		cell_row = cellRow;
	}
	public Integer getCell_column() {
		return cell_column;
	}
	public void setCell_column(Integer cellColumn) {
		cell_column = cellColumn;
	}
	public int getStatistic_showNonZero() {
		return statistic_showNonZero;
	}
	public void setStatistic_showNonZero(int statisticShowNonZero) {
		statistic_showNonZero = statisticShowNonZero;
	}
	public String getImageBase64Str() {
		return imageBase64Str;
	}
	public void setImageBase64Str(String imageBase64Str) {
		this.imageBase64Str = imageBase64Str;
	}
	@Override
	public Object[] advSearchCondition() {
		return null;
	}
	@Override
	public Object[] simpleSearchCondition() {
		return BaseStatisticAction.searchCondition;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public int getSort_type() {
		return sort_type;
	}
	public void setSort_type(int sortType) {
		sort_type = sortType;
	}
	public String getMDXQueryId() {
		return MDXQueryId;
	}
	public void setMDXQueryId(String mDXQueryId) {
		MDXQueryId = mDXQueryId;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
}
