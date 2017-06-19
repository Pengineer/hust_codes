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
	}
}
