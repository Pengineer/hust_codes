package csdc.tool;

import java.util.HashMap;
import java.util.Map;

public class MaintainProperty {
	//发动机系统作业
	public static final String ENGINE= "0";//"01";
	public static final String ENGINE1= "1";//"0101";
	public static final String ENGINE2= "2";//"0102";
	public static final String ENGINE3= "3"; //"0103";
	public static final String ENGINE4= "4"; //"0104";
	public static final String ENGINE5= "5";//"0105";
	public static final String ENGINE6= "6";//"0106";
	public static final String ENGINE7= "7";//"0107";
	public static final String ENGINE8= "8";//"0108";
	public static final String ENGINE9= "9";//"0109";
	public static final String ENGINE10= "10";//"0110";
	public static final String ENGINE11= "11";//"0111";
	public static final String ENGINE12= "12";//"0112";
	public static final String ENGINE13= "13";//"0113";
	public static final String ENGINE14= "14";//"0114";
	public static final String ENGINE15= "15";//"0115";
	public static final String ENGINE16= "16";//"0116";		

	private static final String[][] Maintain_Map = {
		{ENGINE1, "V型皮带张紧力及磨损状态", "20", "50", "检查V型皮带张磨损状态", "更换V型皮带"},
		{ENGINE2, "点火电缆有无损伤搭铁", "40", "", "检查点火电缆绝缘层",""},
		{ENGINE3, "发动机正时齿带", "", "70", "", "更换发动机正时齿带"},
		{ENGINE4, "火花塞", "10", "20", "检查火花塞打火情况", "更换火花塞"},
		{ENGINE5, "散热器软管", "40", "", "检查散热器软管老化程度", ""},
		{ENGINE6, "发动机冷却液", "10", "20", "检查发动机冷却液液面高低及颜色", "更换发动机冷却液"},
		{ENGINE7, "空气滤清器侣芯", "10", "20", "清理空气滤清器滤芯灰尘", "更换空气滤芯"},
		{ENGINE8, "变速器齿轮油", "", "10", "", "更换齿轮油"},
		{ENGINE9, "离合器油液", "5", "40", "检查离合器油量", "更换离合器油"},
		{ENGINE10, "离合器踏板自由行程", "5", "", "调整离合器踏板自有行程", ""},
		{ENGINE11, "制动器油液", "5", "40", "检查制动器油液是否缺少","更换制动器油液"},
		{ENGINE12, "制动器踏板自由行程", "10", "", "调整制动器踏板自有行程",""},
		{ENGINE13, "蓄电池", "12", "", "检查蓄电池接线柱的氧化程度及充放电", ""},
		{ENGINE14, "燃油滤清器", "", "10","","更换燃油滤清器"},
		{ENGINE15, "液压助力油", "", "40", "", "更换液压助力油"},
		{ENGINE16, "汽缸盖紧固", "", "", "",""},
	};
	
	public static final Map<String, String[]> Maintain_Table;
	static {
		Maintain_Table = new HashMap<String, String[]>();
		for (String[] tmp : Maintain_Map) {
			Maintain_Table.put(tmp[0],tmp);
		}
	}

}
