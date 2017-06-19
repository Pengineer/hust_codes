package csdc.tool;

import csdc.tool.bean.Level;

/**
 * 学科匹配度
 * @author jintianfan
 *
 */
public class DisciplineMatchLevel {
	//项目退避对应的值，值越小匹配度越高
	public static final int PRO_WD_33=0; //项目三级学科退至三级学科值为0
	public static final int PRO_WD_22=1; //项目二级学科退至二级学科值为1
	public static final int PRO_WD_11=2; //项目一级学科退至一级学科值为2
	public static final int PRO_WD_32=3; //项目三级学科退至二级学科值为3
	public static final int PRO_WD_21=4; //项目二级学科退至一级学科值为4
	public static final int PRO_WD_31=5; //项目三级学科退至一级学科值为5
	public static int[][]PRO_WD = {{PRO_WD_11,0,0},{PRO_WD_21,PRO_WD_22,0},{PRO_WD_31,PRO_WD_32,PRO_WD_33}};//辅助矩阵，方便计算
	//专家退避对应的值，值越小匹配度越高
	public static final int EXP_WD_33=0; //专家三级学科退至三级学科值为0
	public static final int EXP_WD_22=1; //专家二级学科退至二级学科值为1
	public static final int EXP_WD_11=2; //专家一级学科退至一级学科值为2
	public static final int EXP_WD_32=3; //专家三级学科退至二级学科值为3
	public static final int EXP_WD_21=4; //专家二级学科退至一级学科值为4
	public static final int EXP_WD_31=5; //专家三级学科退至一级学科值为5
	public static int[][] EXP_WD = {{EXP_WD_11,0,0},{EXP_WD_21,EXP_WD_22,0},{EXP_WD_31,EXP_WD_32,EXP_WD_33}};//辅助矩阵，方便计算
	

	/**
	 * 计算项目专家退避级别
	 * @param projectCodesString 项目学科字符串，多个学科用"; "隔开
	 * @param expertCodesString 专家学科字符串，多个学科用"; "隔开
	 * @return
	 */
	public static Level projectWithdrawLevel(String projectCodesString,String expertCodesString){
		String [] projectCodes = projectCodesString.replaceAll("\\s+", "").split("[;；]");
		String [] expertCodes = expertCodesString.replaceAll("\\s+", "").split("[;；]");
		Level level=new Level(100, 100); //初始匹配度
		for (int i = 0; i < projectCodes.length; i++) {
			for (int j = 0; j < expertCodes.length; j++) {
				Level bLevel=widthdrawLevel(projectCodes[i],expertCodes[j]);
				if(bLevel.isBetter(level))
					level = bLevel;
			}
		}
		return level;
	}
	
	/**
	 * 计算项目学科与专家学科的匹配度
	 * @param projectCode 项目学科代码
	 * @param expertCode 专家学科代码
	 * @return 匹配等级
	 */
	private static Level widthdrawLevel(String projectCode,String expertCode){
		int proLevel=100;
		int expLevel=100;
		int sameCharNum = compareSameChar(projectCode, expertCode);
		int proIndex=projectCode.length()/2-1;
		int expIndex=expertCode.length()/2-1;
		int wdIndex=sameCharNum/2-1;
		if(wdIndex>=0){
			proLevel = PRO_WD[proIndex][wdIndex];
			expLevel = EXP_WD[expIndex][wdIndex];
		}
		return new Level(proLevel, expLevel);
	}
	
	/**
	 * 计算两个学科代码相同前缀的字符个数
	 * @param code1 学科代码
	 * @param code2 学科代码
	 * @return 学科代码相同前缀的字符个数
	 */
	private static int compareSameChar(String code1,String code2){
		int count = 0;
		int length = code1.length()>code2.length()?code2.length():code1.length();
		for(int i=0;i<length;i++){
			if(code1.charAt(i)==code2.charAt(i)){
				count++;
			}else{
				break;	
			}
		}
		if(count<3){
			count = 0;
		}else if (count%2 == 0) {
			count--;
		}
		return count;
	}
	
}
