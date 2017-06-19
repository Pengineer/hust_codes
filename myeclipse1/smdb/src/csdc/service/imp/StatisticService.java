package csdc.service.imp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Collator;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mondrian.olap.Axis;
import mondrian.olap.Connection;
import mondrian.olap.DriverManager;
import mondrian.olap.Member;
import mondrian.olap.Position;
import mondrian.olap.Query;
import mondrian.olap.Result;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.dao.SqlBaseDao;
import csdc.service.IStatisticService;
import csdc.tool.ApplicationContainer;
import csdc.tool.JsonUtil;
import csdc.tool.bean.AccountType;

/**
 * 统计分析的业务逻辑层，包含了获取统计数据和图表数据等方法
 * @author 雷达
 */
public class StatisticService extends BaseService implements IStatisticService {
	
	@Autowired
	protected SqlBaseDao sqlDao;
	
	public Result getMondrianResult(String type, String MdxQueryString) {
		String schema = null;
		// 判断统计大类，选择合适的schema文件
		if (type.startsWith("project")) {
			schema = "/statistic/schema/project.xml";
		} else if (type.startsWith("award")) {
			schema = "/statistic/schema/award.xml";
		} else if (type.startsWith("person")) {
			schema = "/statistic/schema/person.xml";
		} else if (type.startsWith("unit")) {
			schema = "/statistic/schema/unit.xml";
		} else if (type.startsWith("product")) {
			schema = "/statistic/schema/product.xml";
		} else if (type.startsWith("expert")) {
			schema = "/statistic/schema/expert.xml";
		}
		try {
			//获取文件实际路径
			String schemaRealPath = ApplicationContainer.sc.getRealPath(schema);
			Connection connection = null;
			if (type.startsWith("expert")) {//研修班
				connection = DriverManager.getConnection("Provider=mondrian; Jdbc=jdbc:mysql://192.168.88.220:3306/experts?user=experts&password=experts123; Catalog="+ schemaRealPath +"; JdbcDriver=com.mysql.jdbc.Driver;",null);
			}else {//smdb
				String jndiname = getPropertiesValue(getClass(), "/init.properties", "jndiname");
				connection = DriverManager.getConnection("Provider=mondrian; DataSource="+ jndiname +"; Catalog=" + schemaRealPath + ";", null);
			}
			Query query = connection.parseQuery(MdxQueryString);
			Result result = connection.execute(query);//获取查询结果
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 自动生成图表的tip信息，用于在鼠标悬停时显示统计图信息
	 * @param result 数据结果
	 * @param num 主数据在第几行
	 * @param indexes tip要显示的所有行
	 * @param indexesLength tip的数量
	 * @return tip字串
	 */
	public String generateTips(Result result, int num, int[] indexes, int indexesLength) {
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < indexesLength; i++) {
			int[] xx = {indexes[i], num};//统计结果坐标
			Number value = (Number) result.getCell(xx).getValue();//获取单元的数字值
			String valuesString = "";
			if(value == null) {//结果为空就置为0
				value = 0;
			}
			if(value.toString().endsWith(".0")) { //如果是整数就不显示小数点
				valuesString = String.valueOf(value.intValue());
			}
			//显示百分数，如果是百分数的数字就按照百分数格式显示
			//检测栏位名称是否包含百分比的关键字
			if( value.doubleValue() <= 1.0 && checkRatiokey(result.getAxes()[0].getPositions().get(indexes[i]).get(0).getName())) {
				DecimalFormat df=(DecimalFormat)NumberFormat.getPercentInstance();
				df.applyPattern("#0.00%");
				valuesString = df.format(Float.parseFloat(value.toString()));//转换为百分数格式
			} else {
				valuesString = value.toString();
				if(valuesString.endsWith(".0")) {//去掉整数后面的.0
					valuesString = valuesString.substring(0, valuesString.length() - 2);
				}
			}
			//显示标题，Axis[0]一般存储指标的文字信息
			buffer.append("<br>" + result.getAxes()[0].getPositions().get(indexes[i]).get(0).getName() + ":" + valuesString);
		}
		return buffer.toString();
	}
	
	public String getChartJson(Result result, String chartType, String chartShowIndexes, int sortLabelByStatisticIndex) {
		int[] indexes = new int[100];//用于存储带查询数据的列号
		int indexesLength = 1;
		if (chartShowIndexes == null) {
			indexes[0] = 0;//默认第一个做显示列
		} else {
			String[] tmp = chartShowIndexes.split(",");
			indexesLength = tmp.length;
			for (int i = 0; i < tmp.length; i++) {
				indexes[i] = Integer.parseInt(tmp[i]);//字符转数字序号
			}
		}
		//统计图标题
		String chartTitle = (String) result.getAxes()[0].getPositions().get(indexes[0]).get(0).getName();
		String title = chartTitle + "统计图";
		Axis[] axis = result.getAxes();//坐标轴数组，一般多维统计结果有2个以上的轴
		List<Position> pos = axis[1].getPositions();//这个轴是存结果的		
		//json根对象
		JsonUtil util = new JsonUtil();
		if(chartTitle.contains("率")){
			util.put("isPercent", 1);
		}else util.put("isPercent", 0);
		String xTitle = "";
		Axis[] axs = result.getAxes();
		if (axs[1].getPositions().get(0).get(0).getLevel().isAll()) {
			xTitle = axs[1].getPositions().get(0).get(0).getLevel().getChildLevel().getName();
		} else {
			xTitle = axs[1].getPositions().get(0).get(0).getLevel().getName();
		}
		//第1类：垂直柱状图
		if(chartType.contains("BAR") || chartType.contains("LINE")) {
			String type = "";
			//柱状统计图类型
			if(chartType.contains("BAR")) {
				type = "column";
				util.put("type", type);
			} else if (chartType.contains("LINE")) {
				type = "line";
				util.put("type", type);
			}
			//设置y轴
			int maxRange = 0;
			int minRange = 1000000;
			Map<String,Object> yAxis = new HashMap<String,Object>();
			for (int i = 0; i < pos.size(); i++) {
				List<Member> mem = pos.get(i);
				if(mem.get(0).getDepth() == 1){//单层的统计数据
					int[] xx = { indexes[0], i };//数据单元坐标
					//获取数据
					Number value = (Number) result.getCell(xx).getValue();
					if(value == null) {
						value = 0;
					}
					//百分数处理
					if(checkRatiokey(axis[0].getPositions().get(indexes[0]).get(0).getName()))
						value = value.doubleValue() * 100;
					String name = (String) result.getAxes()[1].getPositions().get(i).get(0).getName();
					if(!name.equals("总计")) {
						if(value.intValue() > maxRange) {//获取统计结果数据中的最大值，用于设置坐标轴范围
							maxRange = value.intValue();
						}
						if(value.intValue() < minRange) {//获取统计结果数据中的最小值，用于设置坐标轴范围
							minRange = value.intValue();
						}
					}
				}
			}
			yAxis.put("min", (int)(minRange * 0.9));//坐标轴y轴最小范围
			yAxis.put("max", (int) (maxRange * 1.10));//坐标轴y轴最大范围
			yAxis.put("step", computeStep(maxRange, minRange));//设置坐标轴刻度值
			yAxis.put("title", chartTitle);
			
			//设置x轴
			Map<String, Object> xAxis = new HashMap<String, Object>();
			List<Object> listx = new LinkedList<Object>();			
			for (int i = 0; i < pos.size(); i++) {
				List<Member> mem = pos.get(i);
				if(mem.get(0).getDepth() == 1){
					//统计的数据的名称
					String name = (String) result.getAxes()[1].getPositions().get(i).get(0).getName();
					if(!name.equals("总计")) {
						listx.add(name);
					}
				}
			}
			listCompare(listx, sortLabelByStatisticIndex);
			xAxis.put("title", xTitle);//横坐标标题为统计指标：statsticIndex
			xAxis.put("categories", listx);
			
			//设置数据
			List<Map<String, Object>> listValue = new LinkedList<Map<String, Object>>();
			for (int i = 0; i < pos.size(); i++) {
				List<Member> mem = pos.get(i);
				if(mem.get(0).getDepth() == 1){
					int[] xx = { indexes[0], i };
					//柱图的高度
					Number value = (Number) result.getCell(xx).getValue();
					if(value == null) {
						value = 0;
					}
					//判断是否为百分数关键字
					if(checkRatiokey(axis[0].getPositions().get(indexes[0]).get(0).getName())) {
						value = value.doubleValue() * 100;
					}
					//去除整数后面的.0
					if(value.toString().endsWith(".0") && value.intValue() > 1) {
						value = value.intValue();
					}
					String name = (String) result.getAxes()[1].getPositions().get(i).get(0).getName();
					if(!name.equals("总计")) {
						Map<String, Object> valueMap = new HashMap<String, Object>();
						valueMap.put("tip", name + generateTips(result, i, indexes, indexesLength));//设置鼠标移上去的提示信息
						valueMap.put("color", getRandomColor());
						valueMap.put("y", value);
						listValue.add(valueMap);
					}
				}
			}
			mapCompare(listValue, "tip", sortLabelByStatisticIndex);
			
			util.put("title", title);
			util.put("xAxis", xAxis);
			util.put("yAxis", yAxis);
			util.put("data", listValue);
			return util.toString().replace("#null", "未知");			
		} else {			
			//设置数据
			List<Map<String, Object>> listValue = new ArrayList<Map<String, Object>>();			
			int totalCount = 0;
			for (int i = 0; i < pos.size(); i++) {
				List<Member> mem = pos.get(i);
				if(mem.get(0).getDepth() == 1){
					int[] xx = { indexes[0], i };
					Number value = (Number) result.getCell(xx).getValue();
					if(value == null)
						value = 0;
					String name = (String) result.getAxes()[1].getPositions().get(i).get(0).getName();
					if(!name.equals("总计")) {
						totalCount += value.intValue();
					} else {
						totalCount = value.intValue();
						break;
					}
				}
			}			
			for (int i = 0; i < pos.size(); i++) {
				List<Member> mem = pos.get(i);
				if(mem.get(0).getDepth() == 1){
					int[] xx = { indexes[0], i };
					Number value = (Number) result.getCell(xx).getValue();
					if(value == null)
						value = 0;
					if(value.toString().endsWith(".0") && value.intValue() > 1)
						value = value.intValue();
					else if(checkRatiokey(axis[0].getPositions().get(indexes[0]).get(0).getName()))
						value = value.doubleValue() * 100;
					String name = (String) result.getAxes()[1].getPositions().get(i).get(0).getName();
					
					DecimalFormat df=(DecimalFormat)NumberFormat.getPercentInstance();
					df.applyPattern("#0.00%");
					Float ratioFloat = value.floatValue() / totalCount;
					String ratiosString = df.format(Float.parseFloat(ratioFloat.toString()));
					
					if(!name.equals("总计")) {
						Map<String,Object> slice = new HashMap<String,Object>();
						slice.put("y", value);
						slice.put("tip", name + " " + ratiosString + generateTips(result, i, indexes, indexesLength));
						slice.put("color", getRandomColor());
						slice.put("name", name);
						listValue.add(slice);
					}
				}
			}
			// 按照统计指标进行排序
			mapCompare(listValue, "tip", sortLabelByStatisticIndex);
			
			util.put("title", title);
			util.put("data", listValue);
			util.put("type", "pie");
			return util.toString().replace("#null", "未知");
		}
	}
	
	/**
	 * 根据结果集对象获取统计表格数据
	 * @param mondrian result对象
	 * @param sortLabelByStatisticIndex 是否按统计指标排序（1：是；0：否）
	 * @return List<List>对象 统计表格列表数据
	 */
	@SuppressWarnings("unchecked")
	public List<List> getData(Result result, int sortLabelByStatisticIndex) {
		List<List> dataList = new ArrayList();
		try {
			Axis[] axs = result.getAxes();
			Map session = ActionContext.getContext().getSession();
			int measureNum = (null != session.get("measureNum")) ? (Integer) session.get("measureNum") : axs[0].getPositions().size();//查询结果需要显示的行数
			List list = new ArrayList();
			list.add(new String("序号"));
			//若Mondrian查询结果中第二维度为空，说明没有查询到数据，则只返回表头信息
			if(axs[1].getPositions().size()==0) {
				if(session.containsKey("statistic_index"))
					list.add(session.get("statistic_index"));
				//加表头
				for (int i = 0; i < measureNum; i++) {
					list.add(axs[0].getPositions().get(i).get(0).getName());
				}
				dataList.add(list);
				return dataList;
			}
			//获取表头数据列表
			if (axs[1].getPositions().get(0).get(0).getLevel().isAll()) {
				//总计行得到表格表头
				list.add(axs[1].getPositions().get(0).get(0).getLevel().getChildLevel().getName());
			} else {
				//非总计行得到表格表头
				list.add(axs[1].getPositions().get(0).get(0).getLevel().getName());
			}
			//加第一列
			for (int i = 0; i < measureNum; i++) {
				list.add(axs[0].getPositions().get(i).get(0).getName());
			}
			dataList.add(list);
			List<Position> pos = axs[1].getPositions();
			
			List<Integer> data = new ArrayList<Integer>();
			for(int i = 0; i < pos.size(); i++){
				List<Member> mem = pos.get(i);
				data.add(mem.get(0).getDepth());//1是单层 2是多层（即一个level下还有level）
			}
			//判别是否含有分层
			session.put("isLevels", false);
			for(Integer i : data){
				if(i == 2){
					session.put("isLevels", true);
					break;
				}
			}
			int count = 0;
			int temp = (pos.get(0).get(0).getDepth() == 0) ? 1 : 0;//是否含顶级成员
			int[] cell = new int[axs.length];
			int indexFlag = 0;
			for (int i = 0; i < pos.size(); i++) {
				list = new ArrayList();
				List<Member> mem = pos.get(i);
				
				if(mem.get(0).getDepth() ==1){
					if(count >= 1){
						temp += getLevelCountBySeq(1, count, data);
					}
					count++;
					//添加序号列
					list.add(new Integer(indexFlag++));
					//添加名称列
					String name = mem.get(0).getName();
					if ("#null".equals(name)) {
						list.add("未知");
					}else {
						list.add(name);
					}
					//添加数值列
					for (int j = 0; j < measureNum; j++) {
						cell[0] = j;
						cell[1] = i;
						//通过x/y坐标获取数据
						String s = (null != result.getCell(cell).getValue()) ? result.getCell(cell).getValue().toString() : "0";
						//如果是类似百分数、XX率的数据，要处理成百分数
						if (checkRatiokey(axs[0].getPositions().get(j).get(0).getName())) {//百分比数据
							DecimalFormat df = (DecimalFormat) NumberFormat.getPercentInstance();
							df.applyPattern("#0.00%");
							s = df.format(Float.parseFloat(s));
						} else {//非百分数的处理
							//获取数值
							Number value = (Number) result.getCell(cell).getValue();
							String valuesString = "";
							if(value == null) {
								value = 0;
							}
							//去掉整数后面的.0
							if(value.toString().endsWith(".0")) {
								valuesString = String.valueOf(value.intValue());
							} else {
								valuesString = value.toString();
							}
							s = valuesString;
						}
						list.add(s);
					}
				}
				dataList.add(list);
			}
			//总行数
			session.put("resultLines", count);//实际行数
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 按照统计指标进行排序
		if (sortLabelByStatisticIndex != 0) {
			List<List> tempDataList = dataList.subList(1, dataList.size());
			if (sortLabelByStatisticIndex == 1) {	//按照拼音降序
				Collections.sort(tempDataList, new Comparator<List>() {
					@Override
					public int compare(List o1, List o2) {
						String str1 = o1.get(1).toString();
						String str2 = o2.get(1).toString();
						return -Collator.getInstance(java.util.Locale.CHINA).compare(str1, str2);
					}
				});
			}else if (sortLabelByStatisticIndex == 2) {	//按照拼音升序
				Collections.sort(tempDataList, new Comparator<List>() {
					@Override
					public int compare(List o1, List o2) {
						String str1 = o1.get(1).toString();
						String str2 = o2.get(1).toString();
						return Collator.getInstance(java.util.Locale.CHINA).compare(str1, str2);
					}
				});
			}
			//重新编序号
			for (int j = 0, size = tempDataList.size(); j < size; j++) {
				int index = j + 1;
				tempDataList.get(j).set(0, index);	//修改序号
				dataList.set(index, tempDataList.get(j));
			}
		}
		return dataList;
	}
	
	//获取某个级别下面数据总数
	public int getLevelCountBySeq(int lv, int seq, List<Integer> data){
		int count = 0, num = 0;
		for(int i = 0; i < data.size(); i++){
			if(data.get(i) == lv){
				count++;
				continue;
			}
			if(count == seq){
				if(i == data.size() -1 || data.get(i) == lv){
					break;
				}
				if(data.get(i) == lv + 1){
					num++;
				}
			}
		}
		return num;
	}
	
	//获取偏移量
	public int getOffSet(int seq, List<Integer> data, boolean isContainAll){
		int count = seq, i = 1;
		while(i <= seq){
			count += getLevelCountBySeq(1, i, data);
			i++;
		}
		return (isContainAll) ? count + 1 : count;
	}
	
	/**
	 * 获取钻取级别数据
	 * @param result Mondrian result 对象
	 * @param seq 行号
	 */
	@SuppressWarnings("unchecked")
	public List<List> getLevelData(Result result, int seq){
		int measureNum = (null != ServletActionContext.getRequest()
			.getSession().getAttribute("measureNum")) ? (Integer) ServletActionContext
			.getRequest().getSession().getAttribute("measureNum")
			: result.getQuery().getMeasuresMembers().size();
		
		Axis[] axs = result.getAxes();
		List<Position> pos = axs[1].getPositions();
		
		List<List> dataList = new ArrayList();
		List list = new ArrayList();
		
		boolean isContainAll = false;
		List<Integer> data = new ArrayList<Integer>();
		for(int i = 0; i < pos.size(); i++){
			List<Member> mem = pos.get(i);
			data.add(mem.get(0).getDepth());
			if(i == 0){
				isContainAll = (mem.get(0).getDepth() == 0) ? !isContainAll : isContainAll;
			}
		}
		
		int offset1 = getOffSet(seq - 1, data, isContainAll) + 1;
		int offset2 = getOffSet(seq, data, isContainAll);
		
		int[] cell = new int[axs.length];
		for (int i = offset1; i <= offset2; i++) {
			list = new ArrayList();
			List<Member> mem = pos.get(i);
			
			if(mem.get(0).getDepth() ==2){
				list.add(mem.get(0).getName());
				for (int j = 0; j < measureNum; j++) {
					cell[0] = j;
					cell[1] = i;
					//从结果集中获取数据
					String s = (null != result.getCell(cell).getValue()) ? result
						.getCell(cell).getValue().toString() : "0";
					if (checkRatiokey(axs[0].getPositions().get(j).get(0).getName())) {//百分比数据
						DecimalFormat df = (DecimalFormat) NumberFormat
							.getPercentInstance();
						df.applyPattern("#0.00%");
						s = df.format(Float.parseFloat(s));
					} else {
						Number value = (Number) result.getCell(cell).getValue();
						String valuesString = "";
						if(value == null) {
							value = 0;
						}
						if(value.toString().endsWith(".0")) {
							valuesString = String.valueOf(value.intValue());
						} else {
							valuesString = value.toString();
						}
						s = valuesString;
					}
					list.add(s);
				}
			}
			dataList.add(list);
		}
		return dataList;
	}
	
	
	/**
	 * 根据result、统计单元数组获取钻取SQL语句
	 * @return 钻取相关SQL语句
	 */
	public String getDrillDownSQL(Result result,int[] cell){
		String sql = "";
		if(result.getCell(cell).canDrillThrough()){
			sql = result.getCell(cell).getDrillThroughSQL(true);
		}
		Axis[] axs = result.getAxes();
		String columnName = axs[0].getPositions().get(cell[0]).get(0).getName();
		ActionContext.getContext().getSession().put("columnName", columnName);
		return sql;
	}
	
	/**
	 * 根据SQL语句解析SQL语句列名
	 * @return 查询列名列表
	 */
	public List<String> parseSQL(String sql){
		List<String> list = new ArrayList<String>();
		List<String> columnList = new ArrayList<String>();
		String[] s = sql.substring(0, sql.indexOf("from")).split(",");//把sql语句中from前的语句按照,分割
		for(String ss : s){
			//获取查询栏位的真名而不是别名
			list.add(ss.substring(ss.lastIndexOf("as") + 2).replace("\"","").replace("\"", "").trim());
			if(ss.indexOf("select") != -1) 
				ss = ss.replace("select", "");//去掉select
			columnList.add(ss.trim());
		}
		ActionContext.getContext().getSession().put("columnList", columnList);
		return list;
	}
	
	/**
	 * 获取随机颜色字符串
	 * @return 随机颜色字符串
	 */
	public String getRandomColor() {
		try {
			StringBuffer color = new StringBuffer("#");
			for(int i = 0; i < 3; i++) {
				int tmp = RandomUtils.nextInt(256);
				if(tmp < 20 && (i == 0 || i == 2))
					tmp += 130;
				if(tmp > 200 && i == 1)
					tmp -= 120;
				String x = Integer.toHexString(tmp).toUpperCase();
				if(x.length() == 1) {
					x = "0" + x;
				}
				color.append(x);
			}
			return color.toString();
		} catch (Exception e) {
			return "#5673FF";
		}
	}
	
	/**
	 * 根据统计分析结果的最大范围自动计算统计图的坐标轴刻度值
	 * @param maxRange 最大值
	 * @param minRange 最小值
	 * @return 步进值
	 */
	public int computeStep(int maxRange, int minRange) {
		int range = maxRange - minRange;
		int minStepNum = 6; //最小步数
		int maxStepNum = 14; //最大步数
		int[] steps = {1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000, 200000, 500000, 1000000}; //步进值库
		for(int i = steps.length - 1; i >= 0 ; i--) {
			int num = range / steps[i];
			if(num >= minStepNum && num <= maxStepNum) {
				return steps[i];
			}
		}
		return (range / 10)==0?1:(range / 10);//没有得到计算结果默认返回值
	}
	
	/**
	 * 检测是否百分比关键词
	 * @param word 需要检测的关键词
	 * @return 是否百分比关键词
	 */
	public boolean checkRatiokey(String word) {
		String[] lib = {"立项率", "结项率", "中检率", "比例"};// 关键词字典
		for(int i = 0; i < lib.length; i++) {
			if(word.contains(lib[i]))
				return true;
		}
		return false;
	}
	
	/**
	 * 将更新数据仓库的sql脚本文件中的一系列语句拆分成单条语句，放入数组
	 * @param sqlFile 文件名
	 * @return sql数组
	 * @throws Exception
	 * @author leida
	 */
	public List<String> loadSql(String sqlFile) throws Exception {
        List<String> sqlList = new ArrayList<String>();
        try {
            InputStream sqlFileIn = new FileInputStream(sqlFile);
            StringBuffer sqlSb = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(sqlFileIn, "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {//一行行的读文件
				if(!line.isEmpty() && line.startsWith("?")) {
					line = line.replace("?", "");//java读取UTF-8文件的bug
				}
				if (!line.startsWith("--")) {//去掉注释
					sqlSb.append(" " + line + " ");
				}
			}
			//分割sql文件，分割成sql语句
//			String[] sqlArr = sqlSb.toString().split("(;)|(;\\s*\\r\\n)|(;\\s*\\n)");
			String[] sqlArr = sqlSb.toString().split(";--\\[end\\]");//分隔符为";--[end]"
			for (int i = 0; i < sqlArr.length; i++) {
				String sql = sqlArr[i].replaceAll("--.*", "").trim();//去掉注释，其中".*"表示匹配任意文本
				if (sql != null && !sql.isEmpty() && !sql.equals("null")) {
					sqlList.add(sql);
				}
			}
			return sqlList;
        } catch (Exception ex) {
        	ex.printStackTrace();
            return null;
        }
    }
	
	public String getAccountBelongArea(String accountId) {
		if(accountId != null && !accountId.isEmpty()) {
			Account account = (Account) dao.query(Account.class, accountId);
			if(account != null) {
				if(account.getType().equals(AccountType.ADMINISTRATOR) || account.getType().equals(AccountType.MINISTRY)) {//部级用户
					return null;
				}
				if(account.getType().equals(AccountType.PROVINCE)) {//省级用户
					Agency agency = (Agency) dao.query(Agency.class, account.getAgency().getId());
					if(agency != null) {
						return "[机构维度.省份名称].[省份名称].[" + agency.getName().replaceAll("教育厅", "") + "]";
					}
				}
				if(account.getType().equals(AccountType.MINISTRY_UNIVERSITY)|| account.getType().equals(AccountType.LOCAL_UNIVERSITY)) {//校级用户
					Agency agency = (Agency) dao.query(Agency.class, account.getAgency().getId());
					if(agency != null) {
						return "[机构维度.高校名称].[" + agency.getName() + "]";
					}
				}
			}
		}
		return "noArea";
	}
	
	public String getFormatedData(String data) {
		String resultsString = "";
		if(data != null && !data.isEmpty()) {
			String[] topStrings = data.split(",");
			for(int i = 0; i < topStrings.length; i++) {
				//后续处理：对“批准经费”维度加上单位
				if(topStrings[i].trim().equals("[Measures].[批准经费]")){
					topStrings[i] = "[Measures].[批准经费（万元）]";
				}
				// 按照分隔符隔开
				String[] arrayStrings = topStrings[i].trim().split("\\.");
				for(int j = arrayStrings.length - 1; j >= 0 ; j--) {
					if(arrayStrings[j].startsWith("[") && arrayStrings[j].endsWith("]") && arrayStrings[j].length() >= 2) {
						resultsString += arrayStrings[j].substring(1, arrayStrings[j].length() - 1) + ", ";
						break;
					}
				}
			}
			if(resultsString.endsWith(", ")) {
				resultsString = resultsString.substring(0, resultsString.length() - 2);
			}
		}
		return resultsString;
	}

	
	/**
	 * 重构数据列表，并添加总计行
	 */
	@SuppressWarnings("unchecked")
	public List<List> dealWithDatalist(List<List> dataList) {
		//如果没有就返回
		if(dataList.isEmpty()) return dataList;
		List<List> nlist =  new ArrayList<List>();
		List tlist = new ArrayList();
		int cnum = 0;
		for(int i = 0; i < dataList.size() + 1; i++) {
			if(i == 0) {//提取标题行
				nlist.add(dataList.get(i));
				cnum = dataList.get(i).size();//列数
			} else if(i == 1) {
				tlist.add(0);
				tlist.add("总计");
				for(int j = 0; j < cnum - 2; j++) {
					tlist.add(null);
				}
				nlist.add(tlist);//总计行
			} else {//其它行
				List list = dataList.get(i - 1);
				if(!list.isEmpty()) {
					dataList.get(i - 1).set(0, (Integer)dataList.get(i - 1).get(0) + 1);//序号数增1
					for(int j = 2; j < cnum; j++) {
						nlist.set(1, sum(nlist.get(1), j, dataList.get(i - 1).get(j)));
					}
				}
				nlist.add(dataList.get(i - 1));
			}
		}
		
		return dealPercent(nlist);
	}
	

	/**
	 * 在列表中搜索指定的元素，返回索引值， 或-1
	 */
	@SuppressWarnings("unchecked")
	private int search(List list, String key) {
		int index = -1;
		for(int i = 0; i < list.size(); i++) {
			if(key.equals(list.get(i).toString().trim())) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * 求和
	 * @param list
	 * @param j
	 * @param object
	 */
	@SuppressWarnings("unchecked")
	private List sum(List list, int index, Object o) {
		try {
			o = Integer.parseInt(o.toString());
			list.set(index, (null != list.get(index)) ? ((Integer)list.get(index) + (Integer)o) : o);
//			System.out.println(list.get(index));
		} catch(Exception e) {
			try {
				o = Double.parseDouble(o.toString()); 
				list.set(index, (null != list.get(index)) ? ((Double)list.get(index) + (Double)o) : o);
			} catch(Exception e1) {
				//百分数求和...
			}
		}
		return list;
	}
	
	/**
	 * 处理百分数求和
	 */
	
	@SuppressWarnings("unchecked")
	private List<List> dealPercent(List<List> nlist) {
		String[][] dic = {
				{"结项率", "中检率", "立项率", "鉴定结项比例", "非鉴定结项比例", "申请优秀成果结项比例", "论文结项比例", "著作结项比例", "研究咨询报告结项比例"}, 
				{"立项数", "结项数"}, 
				{"立项数", "中检通过数"}, 
				{"申请数", "立项数"},
				{"总结项数", "鉴定结项数"},
				{"总结项数", "非鉴定结项数"},
				{"总结项数", "申请优秀成果结项数"},
				{"总结项数", "论文结项数"},
				{"总结项数", "著作结项数"},
				{"总结项数", "研究咨询报告结项数"}
			};
			
			List headList = nlist.get(0);
			
			for(int i = 0; i < headList.size(); i++) {
				int index = -1, index1 = -1;
//				for(int k = 0; k < dic[0].length; k++) { 
//					index1 = search(headList, dic[0][k].toString());
//					if(index1 != -1) {
//						index = k;
//						break;
//					}
//				}
				index = searchIndex(dic[0], headList.get(i).toString());
				index1 = i;
				
				if(index != -1) {
					int index2 = search(headList, dic[index + 1][0].toString());
					int index3 = search(headList, dic[index + 1][1].toString());
					
					if(index2 != -1 && index3 != -1) {
						List tlist = nlist.get(1);
						double val = Double.parseDouble(tlist.get(index3).toString()) / Double.parseDouble(tlist.get(index2).toString());
						
						DecimalFormat df = (DecimalFormat) NumberFormat.getPercentInstance();
						df.applyPattern("#0.00%");
						
						tlist.set(index1, df.format(val));
						nlist.set(1, tlist);
					}
				}
			}
			
			return nlist;
	}
	
	/**
	 * 在列表中搜索指定的元素，返回索引值， 或-1
	 */
	@SuppressWarnings("unchecked")
	private int searchIndex(String[] dic, String key) {
		int index = -1;
		for(int i = 0; i < dic.length; i++) {
			if(key.equals(dic[i].trim())) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public ByteArrayInputStream commonExportExcel(List<List> dataList, String header, List<String> extras){
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet  sheet1=wb.createSheet();
		header = (null == header) ? "" : header;
		wb.setSheetName(0, ("".equals(header)) ? "Sheet1" : header);
		sheet1.setDefaultRowHeightInPoints(20);
		sheet1.setDefaultColumnWidth((short)22);
		//设置页脚
		HSSFFooter footer = sheet1.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
		//设置样式 表头
		HSSFCellStyle style1=wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font1=wb.createFont();
		font1.setFontHeightInPoints((short)20);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style1.setFont(font1);
		//合并标题行
		List title = dataList.get(0);
		sheet1.addMergedRegion(new Region(0,(short) 0,(short)0,(short)(title.size() - 1)));
		//设置样式 标题
		HSSFCellStyle style2=wb.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font2=wb.createFont();
		font2.setFontHeightInPoints((short)13);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style2.setFont(font2);
		style2.setBorderTop(CellStyle.BORDER_THIN);
		style2.setBorderRight(CellStyle.BORDER_THIN);
		style2.setBorderBottom(CellStyle.BORDER_THIN);
		style2.setBorderLeft(CellStyle.BORDER_THIN);
		//设置样式 正文
		HSSFCellStyle style3=wb.createCellStyle();
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style3.setWrapText(true);
		style3.setBorderTop(CellStyle.BORDER_THIN);
		style3.setBorderRight(CellStyle.BORDER_THIN);
		style3.setBorderBottom(CellStyle.BORDER_THIN);
		style3.setBorderLeft(CellStyle.BORDER_THIN);
		//设置样式 正文
		HSSFCellStyle style4=wb.createCellStyle();
		style4.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style4.setWrapText(false);

		//行号
		int rowNum = 0;
		//表头行
		HSSFRow row1=sheet1.createRow(rowNum++);
		row1.setHeightInPoints(35);
		
		HSSFCell cell = null;
		//第一行表头
		cell = row1.createCell((short)0);
		cell.setCellValue(header);
		cell.setCellStyle(style1);
		
		//附加信息（统计指标）行
		if (extras != null && !extras.isEmpty()) {
			for (int i = 0; i < extras.size(); i++) {
				sheet1.addMergedRegion(new Region(rowNum,(short) 0,(short)rowNum,(short)(title.size() - 1)));
				HSSFRow tmpRow = sheet1.createRow(rowNum++);
				tmpRow.setHeightInPoints(20);
				cell = tmpRow.createCell((short)0);
				cell.setCellValue((String)extras.get(i));
				cell.setCellStyle(style4);
			}
		}
		//标题行
		HSSFRow row2=sheet1.createRow(rowNum++);
		row2.setHeightInPoints(20);
		
		//第二行标题
		for(int i = 0, len = title.size(); i < len; i++){
			cell = row2.createCell((short)i);
			cell.setCellValue((String)title.get(i));
			cell.setCellStyle(style2);
		}
		
		//第三行正文
		for(int i = 1, size = dataList.size(); i < size; i++){
			List datas = dataList.get(i);
			if (datas.isEmpty()) {
				continue;
			}
			HSSFRow row3 = sheet1.createRow((short)(rowNum++)); // 第三行开始填充数据
//			row3.setHeight((short)500);
			for(int j = 0, len = datas.size(); j < len; j++){
				cell = row3.createCell((short)j);
				//如果是数字类型的，则设置单元格类型位数字,可选择求和
				if(datas.get(j) instanceof Integer){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					Integer intValue = (Integer) datas.get(j);
					cell.setCellValue(intValue);
				} else if(datas.get(j) instanceof Long){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					long longValue = (Long) datas.get(j);
					cell.setCellValue(longValue);
				} else if(datas.get(j) instanceof Float){//instanceof:java中的instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					float floatValue = (Float) datas.get(j);
					cell.setCellValue(floatValue);
				} else if(datas.get(j) instanceof Double){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					double doubleValue = (Double) datas.get(j);
					cell.setCellValue(doubleValue);
				} else {
					cell.setCellValue(new HSSFRichTextString(datas.get(j) == null ? "" : datas.get(j).toString()));//强制换行，用于历次拨款（万元）的分行显示
				}
				cell.setCellStyle(style3);
			}
		}
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			bos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		byte[] content = bos.toByteArray();
		ByteArrayInputStream bis = null;
		try{
			bis = new ByteArrayInputStream(content);
			bis.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return bis;
	}
	
	public Map<String,String> getUniversityOrganizer(){
		Map<String,String> map = null;
		List<String> univOrganizers = dao.query("select distinct ag.organizer from Agency ag where (ag.type = 3 or ag.type = 4) and ag.organizer is not null order by ag.organizer");
		if(univOrganizers.size() > 0){
			map = new LinkedHashMap<String, String>();
			for (String univOrganizer : univOrganizers) {
				map.put(univOrganizer, univOrganizer);
			}
		}
		return map;
	}
	
	public Map<Object,String> getProjectYear(){
		Map<Object,String> map = null;
		List years = sqlDao.query("select distinct c_year from S_D_PROJECT where c_year is not null order by c_year");
		if(years.size() > 0){
			map = new LinkedHashMap<Object, String>();
			for (Object year : years) {
				map.put(year, year + "年");
			}
		}
		return map;
	}
	
	public Map<Object,String> getAwardSession(){
		Map<Object,String> map = null;
		List<String> sessions = sqlDao.query("select distinct c_session from S_D_AWARD where c_session is not null order by c_session");
		if(sessions.size() > 0){
			map = new LinkedHashMap<Object, String>();
			for (String session : sessions) {
				map.put(session.replaceAll("\\D", ""), session);
			}
		}
		return map;
	}
	/**
	 * 对list进行排序
	 * @param list	待排序的列表
	 * @param sortLabelByStatisticIndex	按照统计指标排序：0默认、1降序、2升序
	 */
	public void listCompare(List<Object> list, int sortLabelByStatisticIndex){
		// 按照统计指标进行排序
		if (sortLabelByStatisticIndex == 1) {
			Collections.sort(list, new Comparator<Object>() {
				public int compare(Object o1, Object o2) {
					String str1 = o1.toString();
					String str2 = o2.toString();
					str1 = (str1.indexOf("<br>") > 0) ? str1.substring(0, str1.indexOf("<br>")) : str1;
					str2 = (str2.indexOf("<br>") > 0) ? str2.substring(0, str2.indexOf("<br>")) : str2;
					return -Collator.getInstance(java.util.Locale.CHINA).compare(str1, str2); 
				}
			});
		}else if (sortLabelByStatisticIndex == 2) {
			Collections.sort(list, new Comparator<Object>() {
				public int compare(Object o1, Object o2) {
					String str1 = o1.toString();
					String str2 = o2.toString();
					str1 = (str1.indexOf("<br>") > 0) ? str1.substring(0, str1.indexOf("<br>")) : str1;
					str2 = (str2.indexOf("<br>") > 0) ? str2.substring(0, str2.indexOf("<br>")) : str2;
					return Collator.getInstance(java.util.Locale.CHINA).compare(str1, str2);  
				}
			});
		}
	}
	
	/**
	 * 对list<map>进行排序
	 * @param list	待排序的列表
	 * @param key	map元素中待分析的的key
	 * @param sortLabelByStatisticIndex	按照统计指标排序：0默认、1降序、2升序
	 */
	public void mapCompare(List<Map<String, Object>> list, final String key, int sortLabelByStatisticIndex){
		// 按照统计指标进行排序
		if (sortLabelByStatisticIndex == 1) {
			Collections.sort(list, new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					String str1 = o1.get(key).toString();
					String str2 = o2.get(key).toString();
					str1 = (str1.indexOf("<br>") > 0) ? str1.substring(0, str1.indexOf("<br>")) : str1;
					str2 = (str2.indexOf("<br>") > 0) ? str2.substring(0, str2.indexOf("<br>")) : str2;
					return -Collator.getInstance(java.util.Locale.CHINA).compare(str1, str2); 
				}
			});
		}else if (sortLabelByStatisticIndex == 2) {
			Collections.sort(list, new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					String str1 = o1.get(key).toString();
					String str2 = o2.get(key).toString();
					str1 = (str1.indexOf("<br>") > 0) ? str1.substring(0, str1.indexOf("<br>")) : str1;
					str2 = (str2.indexOf("<br>") > 0) ? str2.substring(0, str2.indexOf("<br>")) : str2;
					return Collator.getInstance(java.util.Locale.CHINA).compare(str1, str2);  
				}
			});
		}
	}	
}