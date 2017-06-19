package csdc.service.imp;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import mondrian.olap.Axis;
import mondrian.olap.Member;
import mondrian.olap.Position;
import mondrian.olap.Result;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import com.googlecode.charts4j.AxisLabels;
//import com.googlecode.charts4j.AxisLabelsFactory;
//import com.googlecode.charts4j.AxisStyle;
//import com.googlecode.charts4j.AxisTextAlignment;
//import com.googlecode.charts4j.BarChart;
//import com.googlecode.charts4j.BarChartPlot;
//import com.googlecode.charts4j.Color;
//import com.googlecode.charts4j.Data;
//import com.googlecode.charts4j.Fills;
//import com.googlecode.charts4j.GCharts;
//import com.googlecode.charts4j.Line;
//import com.googlecode.charts4j.LineChart;
//import com.googlecode.charts4j.LineStyle;
//import com.googlecode.charts4j.PieChart;
//import com.googlecode.charts4j.Plots;
//import com.googlecode.charts4j.Shape;
//import com.googlecode.charts4j.Slice;

import csdc.service.IMobileStatisticService;


public class MobileStatisticService extends StatisticService implements IMobileStatisticService{

	
	public JSONObject getMobileJsonMap(Result result, String chartType, String chartShowIndexes) {
		JSONObject jsonMap = new JSONObject();
		List<String> xList = new ArrayList<String>();
		List<Number> yList = new ArrayList<Number>();
		List<List> vList = new ArrayList<List>();
		
		int[] indexes = new int[100];
		int indexesLength = 1;
		if (chartShowIndexes == null) {
			indexes[0] = 0;
		} else {
			String[] tmp = chartShowIndexes.split(",");
			indexesLength = tmp.length;
			for (int i = 0; i < indexesLength; i++) {
				indexes[i] = Integer.parseInt(tmp[i]);
			}
		}
		
		//公共部分
		Axis[] axis = result.getAxes();
		String chartName = axis[0].getPositions().get(indexes[0]).get(0).getName();
		String chartTitle = chartName + "统计图";//图表标题
		jsonMap.put("chartTitle", chartTitle);		
		List<Position> pos = axis[1].getPositions();
		
		if(chartType.equals("PIE")) {	
			jsonMap.put("chartType", "pie");
			for (int i = 0; i < pos.size(); i++) {
				List pieList = new ArrayList(2);
				List<Member> mem = pos.get(i);
				if(mem.get(0).getDepth() == 1){//单层的统计数据
					String name = (String) result.getAxes()[1].getPositions().get(i).get(0).getName();
					if(!name.equals("总计") ){
						if("#null".equals(name)){
							name = "未知";
						}
						pieList.add(name);
					}
					int[] cellArgs = { indexes[0], i };
					Number value = (Number) result.getCell(cellArgs).getValue();
					if(value == null) {
						value = 0;
					}
//					if(checkRatiokey(chartName)) {
//						value = value.doubleValue() * 100;
//					}
					if(value.toString().endsWith(".0") && value.intValue() > 1) {
						value = value.intValue();
					}
					pieList.add(value);	
					vList.add(pieList);
				}				
			}
			jsonMap.put("vList", vList);
		}
		else {
			if(chartType.equals("NORMAL_BAR") || chartType.equals("GLASS_BAR") || chartType.equals("3D_BAR")) {
				jsonMap.put("chartType", "verticalColumn");	
			}else if (chartType.equals("HORIZONTAL_BAR")) {
				jsonMap.put("chartType", "horizontalColumn");
			}else if ("DOT_LINE".equals(chartType) || "HOLLOW_LINE".equals(chartType) || "NORMAL_LINE".equals(chartType)) {
				jsonMap.put("chartType", "line");
			}
			//x轴
			for (int i = 0; i < pos.size(); i++) {
				List<Member> mem = pos.get(i);
				if(mem.get(0).getDepth() == 1){//单层的统计数据
					String name = (String) result.getAxes()[1].getPositions().get(i).get(0).getName();
					if(!name.equals("总计") ){
						if("#null".equals(name)){
							name = "未知";
						}
						xList.add(name);				
					}
				}
			}
			//y轴
			for (int i = 0; i < pos.size(); i++) {
				List<Member> mem = pos.get(i);
				if(mem.get(0).getDepth() == 1){//单层的统计数据
					int[] cellArgs = { indexes[0], i };
					Number value = (Number) result.getCell(cellArgs).getValue();
					if(value == null) {
						value = 0;
					}
//					if(checkRatiokey(chartName)) {
//						value = value.doubleValue() * 100;
//					}
					if(value.toString().endsWith(".0") && value.intValue() > 1) {
						value = value.intValue();
					}
					yList.add(value);
				}
			}
			jsonMap.put("xList", xList);
			jsonMap.put("yList", yList);
			
		}
		return jsonMap;
			
//		} else if (chartType.equals("HORIZONTAL_BAR")) {//第2类：水平柱状图
//			JsonUtil util = new JsonUtil();
//			//标题
//			util.put("title", chartTitle);
//			util.put("type", "HBAR");//水平柱状图
//			//元素
//			Map<String,Object> element2 = new HashMap<String,Object>();
//			List<Map<String,Object>> listValue = new LinkedList<Map<String,Object>>();
//			int maxRange = 0;
//			for (int i = 0; i < pos.size(); i++) {
//				List<Member> mem = pos.get(i);
//				if(mem.get(0).getDepth() == 1){
//					int[] xx = { indexes[0], i };
//					Number value = (Number) result.getCell(xx).getValue();
//					if(value == null)
//						value = 0;
//					if(value.toString().endsWith(".0") && value.intValue() > 1)
//						value = value.intValue();
//					else if(checkRatiokey(axis[0].getPositions().get(indexes[0]).get(0).getName()))
//						value = value.doubleValue() * 100;
//					String name = (String) result.getAxes()[1].getPositions().get(i).get(0).getName();
//					if(!name.equals("总计")) {
//						Map<String,Object> hbar = new HashMap<String,Object>();
//						hbar.put("right", value);
//						hbar.put("tip", name + generateTips(result, i, indexes, indexesLength));
//						hbar.put("color", getRandomColor());
//						listValue.add(hbar);
//						if(value.intValue() > maxRange) {
//							maxRange = value.intValue();
//						}
//					}
//				}
//			}
//			element2.put("values", listValue);
//			util.put("elements", element2);
//			
////			int count = 0;
////			int temp = (pos.get(0).get(0).getDepth() == 0) ? 1 : 0;//是否含顶级成员
//			
//			List<Integer> data = new ArrayList<Integer>();
//			for(int i = 0; i < pos.size(); i++){
//				List<Member> mem = pos.get(i);
//				data.add(mem.get(0).getDepth());
//			}
//			//y轴
//			Map<String,Object> element3 = new HashMap<String,Object>();
//			List<Map<String,Object>> listy = new LinkedList<Map<String,Object>>();
//			int maxFlag = 0; 
//			int indexFlag = 0;
//			for (int i = 0; i < pos.size(); i++) {
//				List<Member> mem = pos.get(i);
//				if(mem.get(0).getDepth() == 1){
////					if(count >= 1){
////						temp += getLevelCountBySeq(1, count, data);
////					}
////					count++;
//					String name = mem.get(0).getName();
//					if(!name.equals("总计")) {
//						Map<String,Object> ylable =  new HashMap<String,Object>();
//						ylable.put("y", indexFlag);
//						indexFlag--;
//						ylable.put("text", name);
//						ylable.put("size", 12);
//						listy.add(ylable);
//					} else {
//						maxFlag = -1;
//					}
//				}
//			}
//			
//			element3.put("labels", listy);
//			element3.put("offset", 1);
//			element3.put("max", maxFlag);
//			element3.put("min", 1 - listy.size());
//			element3.put("stroke", 2);
//			element3.put("tick-length", 4);
//			util.put("y_axis", element3);
//			
//			//x轴
//			Map<String,Object> element5 = new HashMap<String,Object>();
//			element5.put("steps", computeStep(maxRange));
//			element5.put("max", (int)(maxRange * 1.05));
//			util.put("x_axis", element5);
//			
//			chartJson = util.toString();
//			
//		} else if(chartType.equals("PIE")) {//第3类：饼状图
//			JsonUtil util = new JsonUtil();
//			//标题
//			util.put("title", chartTitle);
//			util.put("type", "PIE");//饼状图
//			//element
//			Map<String,Object> element2 = new HashMap<String,Object>();
//			element2.put("font-size", 12);
//			element2.put("animate", true);
//			element2.put("alpha", 0.7);
//			List<Map<String,Object>> listValue = new LinkedList<Map<String,Object>>();
//			List<String> colors = new ArrayList<String>();
//			
//			int totalCount = 0;
//			for (int i = 0; i < pos.size(); i++) {
//				List<Member> mem = pos.get(i);
//				if(mem.get(0).getDepth() == 1){
//					int[] cellArgs = { indexes[0], i };
//					Number value = (Number) result.getCell(cellArgs).getValue();
//					if(value == null)
//						value = 0;
//					String name = mem.get(0).getName();
//					if(!name.equals("总计")) {
//						totalCount += value.intValue();
//					} else {
//						totalCount = value.intValue();
//						break;
//					}
//				}
//			}
//			
//			for (int i = 0; i < pos.size(); i++) {
//				List<Member> mem = pos.get(i);
//				if(mem.get(0).getDepth() == 1){
//					int[] cellArgs = { indexes[0], i };
//					Number value = (Number) result.getCell(cellArgs).getValue();
//					if(value == null)
//						value = 0;
//					if(value.toString().endsWith(".0") && value.intValue() > 1)
//						value = value.intValue();
//					else if(checkRatiokey(chartName))
//						value = value.doubleValue() * 100;
//					String name = mem.get(0).getName();
//					
//					DecimalFormat df=(DecimalFormat)NumberFormat.getPercentInstance();
//					df.applyPattern("#0.00%");
//					Float ratioFloat = value.floatValue() / totalCount;
//					String ratiosString = df.format(Float.parseFloat(ratioFloat.toString()));
//					
//					if(!name.equals("总计")) {
//						Map<String,Object> slice = new HashMap<String,Object>();
//						slice.put("value", value);
//						slice.put("tip", name + " " + ratiosString + generateTips(result, i, indexes, indexesLength));
//						slice.put("highlight", "alpha");
//						slice.put("label", name);
//						listValue.add(slice);
//						colors.add(getRandomColor());
//					}
//				}
//			}
//			
//			element2.put("values", listValue);
//			element2.put("colors", colors);
//			util.put("elements", element2);
//			
//			chartJson = util.toString();
//			
//		} else if(chartType.equals("DOT_LINE") || chartType.equals("HOLLOW_LINE") || chartType.equals("NORMAL_LINE")){//第4类：折线图
//			JsonUtil util = new JsonUtil();
//			// 标题
//			util.put("title", chartTitle);
//			util.put("type", "LINE");//折线图
//			// x轴
//			Map<String, Object> element2 = new HashMap<String, Object>();
//			List<Map<String, Object>> listx = new LinkedList<Map<String, Object>>();
//			for (int i = 0; i < pos.size(); i++) {
//				String name = pos.get(i).get(0).getName();
//				if (!name.equals("总计")) {
//					Map<String, Object> xlable = new HashMap<String, Object>();
//					xlable.put("text", name);
//					xlable.put("size", 12);
//					listx.add(xlable);
//				}
//			}
//			element2.put("labels", listx);
//			element2.put("grid-color", "#dddddd");
//			util.put("x_axis", element2);
//
//			// else
////			Map<String, Object> element3 = new HashMap<String, Object>();
////			element3.put("mouse", 2);
////			util.put("tooltip", element3);
//
//			// elements
//			Map<String, Object> element4 = new HashMap<String, Object>();
//			List<Map<String, Object>> listValue = new LinkedList<Map<String, Object>>();
//			int maxRange = 0;
//			int minRange = 99999999;
//			for (int i = 0; i < pos.size(); i++) {
//				int[] cellArgs = { indexes[0], i };
//				Number value = (Number) result.getCell(cellArgs).getValue();
//				if (value == null) {
//					value = 0;
//				}
//				if (value.toString().endsWith(".0") && value.intValue() > 1)
//					value = value.intValue();
//				else if (checkRatiokey(chartName))
//					value = value.doubleValue() * 100;
//				String name = pos.get(i).get(0).getName();
//				if (!name.equals("总计")) {
//					if (value.intValue() > maxRange) {
//						maxRange = value.intValue();
//					}
//					if(value.intValue() < minRange) {
//						minRange = value.intValue();
//					}
//				}
//				Map<String, Object> dot = new HashMap<String, Object>();
//				dot.put("value", value);
//				dot.put("tip", name + generateTips(result, i, indexes, indexesLength));
//				dot.put("color", getRandomColor());
//				if (!name.equals("总计")) {
//					listValue.add(dot);
//				}
//			}
//			element4.put("values", listValue);
//			element4.put("font-size", 12);
//			element4.put("color", getRandomColor());
//			util.put("elements", element4);
//			
//			// y轴
//			Map<String, Object> element5 = new HashMap<String, Object>();
//			element5.put("steps", computeStep(maxRange));
//			element5.put("max", (int) (maxRange * 1.05));
//			//TODO 计算区间取整数
//			element5.put("min", ((int) (minRange * 0.75 / 10.0)) * 10);
//			util.put("y_axis", element5);
//			
//			chartJson = util.toString();
//		}
//		return chartJson;
	}
	
//	public String createGoogleChartUrl(String chartJson, String chartType){
//		String googleChartUrl = null;
//
//		System.out.println(chartJson);
//		chartJson = chartJson.replace("\n", "");
////		System.out.println(chartJson.charAt(176));
////		System.out.println(chartJson.charAt(177));
////		System.out.println(chartJson.charAt(178));
////		System.out.println(chartJson.charAt(179));
////		System.out.println(chartJson.charAt(180));
////		System.out.println(chartJson);
//		System.out.println("图表类型：" + chartType);
//
//		//开始解析chartJson数据
//		JSONObject jsonObject = JSONObject.fromObject(chartJson);
//		
//		JSONObject title = JSONObject.fromObject(jsonObject.getString("title"));//获取图表的标题
//		String chartTitle = title.getString("text");// 图表标题
//		System.out.println("图表标题：" + chartTitle);	
//		
//		JSONArray elements = JSONArray.fromObject(jsonObject.getString("elements"));//获取key="elements"的内容数组
//		JSONObject element = elements.getJSONObject(0);//取数组中第一个元素
//		JSONArray values = element.getJSONArray("values");//取element中key="values"的值数组
//		
//		JSONObject jsonValue = null;//图表内容
//		
//		if ("HORIZONTAL_BAR".equals(chartType)) {//水平柱状图
//			
//			int size = values.size();//数值个数
//			List<Integer> datas = new ArrayList<Integer>();
//			List<String> legends = new ArrayList<String>();
//			for (int i = 0; i < size; i++) {
//				jsonValue = values.getJSONObject(i);
//				int value = jsonValue.getInt("right");//获取数值
//				datas.add(value);
//				String tip = jsonValue.getString("tip");
//				legends.add(tip);
//			}
//			int maxValue = Collections.max(datas);
//			double radio = 100.0 / maxValue;
//			for (int i = 0; i < size; i++) {
//				int newValue = (int) Math.round(datas.get(i) * radio);
//				datas.set(i, newValue);
//			}
//			
//			JSONObject x_axis = JSONObject.fromObject(jsonObject.getString("x_axis"));//获取图表的横坐标
//			int xinterval = (x_axis.containsKey("steps")) ? x_axis.getInt("steps") : 0;//步长
//			int xlower = (x_axis.containsKey("min")) ? x_axis.getInt("min") : 0;//x坐标起始值
//			int xupper = (x_axis.containsKey("max")) ? x_axis.getInt("max") : 0;//x坐标终止值
//			
//			JSONObject y_axis = JSONObject.fromObject(jsonObject.getString("y_axis"));//获取图表的纵坐标
//			//获取柱的个数
//			List<String> axisLabels = new ArrayList<String>();
//			JSONObject labels = JSONObject.fromObject(y_axis.getString("labels"));
//			JSONArray labelsArray = JSONArray.fromObject(labels.get("labels"));
//	        for(int i= 0, cnt = labelsArray.size(); i < cnt; i++){
//				String textLabel = labelsArray.getJSONObject(i).getString("text");
//				axisLabels.add(textLabel);
//			}
//			
//			BarChartPlot bcp = Plots.newBarChartPlot(Data.newData(datas), Color.LIGHTCORAL);
//	        BarChart bc = GCharts.newBarChart(bcp);
//	        
//	        // Adding axis info to chart.
//	        bc.addXAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(xlower, xupper, xinterval));
//	        bc.addYAxisLabels(AxisLabelsFactory.newAxisLabels(axisLabels));
//
//	        bc.setHorizontal(true);
//	        bc.setSize(240, 12*size + 50);
//	        bc.setBarWidth(10);
//	        bc.setSpaceWithinGroupsOfBars(2);
//	        bc.setDataStacked(true);
//	        bc.setTitle(chartTitle, Color.BLACK, 16);
//	        bc.setGrid(10, 100, 3, 2);
//	        bc.setBackgroundFill(Fills.newSolidFill(Color.LIGHTGOLDENRODYELLOW));
//	        
//	        googleChartUrl = bc.toURLString();
//	        
//		}else if ("NORMAL_BAR".equals(chartType) || "GLASS_BAR".equals(chartType) || "3D_BAR".equals(chartType)) {//垂直柱状图
//			int size = values.size();//数值个数
//			List<Integer> datas = new ArrayList<Integer>();
//			for (int i = 0; i < size; i++) {
//				jsonValue = values.getJSONObject(i);
//				int value = jsonValue.getInt("top");//获取数值
//				datas.add(value);
//			}
//			int maxValue = Collections.max(datas);
//			double radio = 100.0 / maxValue;
//			for (int i = 0; i < size; i++) {
//				int newValue = (int) Math.round(datas.get(i) * radio);
//				datas.set(i, newValue);
//			}
//			
//			JSONObject x_axis = JSONObject.fromObject(jsonObject.getString("x_axis"));//获取图表的纵坐标
//			//获取柱的个数
//			List<String> axisLabels = new ArrayList<String>();
//			JSONObject labels = JSONObject.fromObject(x_axis.getString("labels"));
//			JSONArray labelsArray = JSONArray.fromObject(labels.get("labels"));
//			for(int i= 0, cnt = labelsArray.size(); i < cnt; i++){
//				String textLabel = labelsArray.getJSONObject(i).getString("text");
//				axisLabels.add(textLabel);
//			}
//
//			JSONObject y_axis = JSONObject.fromObject(jsonObject.getString("y_axis"));//获取图表的横坐标
//			int yinterval = (y_axis.containsKey("steps")) ? y_axis.getInt("steps") : 0;//步长
//			int ylower = (y_axis.containsKey("min")) ? y_axis.getInt("min") : 0;//x坐标起始值
//			int yupper = (y_axis.containsKey("max")) ? y_axis.getInt("max") : 0;//x坐标终止值
//			
//			
//			BarChartPlot bcp = Plots.newBarChartPlot(Data.newData(datas), Color.PLUM);
//	        BarChart bc = GCharts.newBarChart(bcp);
//	        
//	        // Adding axis info to chart.
//	        bc.addXAxisLabels(AxisLabelsFactory.newAxisLabels(axisLabels));
//	        bc.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(ylower, yupper, yinterval));
//
//	        bc.setHorizontal(false);
//	        bc.setSize(13*size + 60, 300);
//	        bc.setBarWidth(10);
//	        bc.setSpaceWithinGroupsOfBars(2);
//	        bc.setDataStacked(true);
//	        bc.setTitle(chartTitle, Color.BLACK, 16);
//	        bc.setGrid(100, 10, 3, 2);
//	        bc.setBackgroundFill(Fills.newSolidFill(Color.LIGHTGOLDENRODYELLOW));
//	        
//	        googleChartUrl = bc.toURLString();
//		}else if ("PIE".equals(chartType)) {//饼状图
//			JSONArray colours = element.getJSONArray("colours");//获取element中key="colours"的颜色数组
//			//填充图标数据
////			List<String> legends = new ArrayList<String>();
//			List<Slice> slices = new ArrayList<Slice>();
//			for (int i = 0, size = values.size(); i < size; i++) {
//				jsonValue = values.getJSONObject(i);
//				int value = jsonValue.getInt("value");//获取数值
//				String label = jsonValue.getString("label");//获取label标签
//				String tip = jsonValue.getString("tip");
//				String ratio = tip.substring(tip.indexOf(" "), tip.indexOf("%")+1);
//				if ("#null".equals(label)) {
//					label = "未知";
//				}
////				legends.add(label);
//				label = label+":"+ratio;
//				String color = colours.getString(i).substring(1);//获取颜色
//				slices.add(Slice.newSlice(value, Color.newColor(color), label, ratio));
//			}
//			//构建饼图对象
//			PieChart pc = GCharts.newPieChart(slices);
//			pc.setTitle(chartTitle, Color.BLACK, 16);
//			pc.setSize(400, 200);
//			pc.setBackgroundFill(Fills.newSolidFill(Color.LIGHTGOLDENRODYELLOW));
//			
//			googleChartUrl = pc.toURLString();
//			
//		}else if ("DOT_LINE".equals(chartType) || "HOLLOW_LINE".equals(chartType) || "NORMAL_LINE".equals(chartType)) {//折线图
//			
//			List<Float> datas = new ArrayList<Float>();
//			for (int i = 0, size = values.size(); i < size; i++) {
//				jsonValue = values.getJSONObject(i);
//				Float value = (Float.parseFloat((String)jsonValue.getString("value")));//获取数值
//				datas.add(value);
//			}
//			String color = element.getString("colour").substring(1);//获取折线的颜色
//			//x轴
//			JSONObject x_axis = JSONObject.fromObject(jsonObject.getString("x_axis"));//获取图表的横坐标
//			//获取数据点的个数
//			List<String> axisLabels = new ArrayList<String>();
//			JSONObject labels = JSONObject.fromObject(x_axis.getString("labels"));
//			JSONArray labelsArray = JSONArray.fromObject(labels.get("labels"));
//			for(int i= 0, size = labelsArray.size(); i < size; i++){
//				String textLabel = labelsArray.getJSONObject(i).getString("text");
//				axisLabels.add(textLabel);
//			}
//			//y轴
//			JSONObject y_axis = JSONObject.fromObject(jsonObject.getString("y_axis"));//获取图表的纵坐标
//			int yinterval = (y_axis.containsKey("steps")) ? y_axis.getInt("steps") : 0;//步长
//			int ylower = (y_axis.containsKey("min")) ? y_axis.getInt("min") : 0;//y标轴起始值
//			int yupper = (y_axis.containsKey("max")) ? y_axis.getInt("max") : 0;//y坐标终止值
//			
//			Line line = Plots.newLine(Data.newData(datas), Color.newColor(color));
//			line.setLineStyle(LineStyle.newLineStyle(2, 1, 0));
//			line.addShapeMarkers(Shape.CIRCLE, Color.LAVENDERBLUSH, 10);
//			line.addShapeMarkers(Shape.CIRCLE, Color.RED, 7);
//
//	        // Defining chart.
//	        LineChart lc = GCharts.newLineChart(line);
//	        lc.setSize(400, 240);
//	        lc.setTitle(chartTitle, Color.BLACK, 14);
//	        lc.setGrid(25, 25, 3, 2);
//	        lc.setBackgroundFill(Fills.newSolidFill(Color.LIGHTGOLDENRODYELLOW));
//
//	        // Defining axis info and styles
//	        AxisStyle axisStyle = AxisStyle.newAxisStyle(Color.BLACK, 10, AxisTextAlignment.CENTER);
//	        AxisLabels xAxis = AxisLabelsFactory.newAxisLabels(axisLabels);
//	        xAxis.setAxisStyle(axisStyle);
//	        AxisLabels yAxis = AxisLabelsFactory.newNumericRangeAxisLabels(ylower, yupper, yinterval);
//	        yAxis.setAxisStyle(axisStyle);
//
//	        // Adding axis info to chart.
//	        lc.addXAxisLabels(xAxis);
//	        lc.addYAxisLabels(yAxis);
//	        
//	        googleChartUrl = lc.toURLString();
//		}
//		
//		System.out.println(googleChartUrl);	
//		return googleChartUrl;
//	}

}
