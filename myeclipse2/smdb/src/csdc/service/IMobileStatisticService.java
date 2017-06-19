package csdc.service;

import net.sf.json.JSONObject;
import mondrian.olap.Result;

public interface IMobileStatisticService extends IStatisticService{
	
	/**
	 * 根据mondrian的result组织画图所需的数据
	 * 供客户端绘图
	 * @param result		
	 * @param chartType	绘制图表的类型：包括四大类，如下<br>
	 * 第1类：水平柱状图（"HORIZONTAL_BAR"）；<br>
	 * 第2类：垂直柱状图（"NORMAL_BAR"、"GLASS_BAR"、"3D_BAR"）；<br>
	 * 第3类：饼状图（"PIE"）<br>
	 * 第4类：折线图（"DOT_LINE"、"HOLLOW_LINE"、"NORMAL_LINE"）
	 * @param chartShowIndexes	绘制图表以哪一类为主
	 * @return JSONObject jsonMap
	 */
	public JSONObject getMobileJsonMap(Result result, String chartType, String chartShowIndexes);
	
}
