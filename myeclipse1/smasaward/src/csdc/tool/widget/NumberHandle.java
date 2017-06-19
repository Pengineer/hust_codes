package csdc.tool.widget;

import java.text.DecimalFormat;

public class NumberHandle {
	
	/**
	 * 数字补位，左边补0
	 * @param num  	待转换的数字
	 * @param count	转换的数字位数
	 * @return
	 */
	public static String numFormat(int num, int count) {
		String zero = "";
		for(int i=0; i<count; i++){
			zero += 0; 
		}
        String numStr = zero + num;
        return numStr.substring(numStr.length() - count);
    }
	
	/**
	 * 数字转换为中文
	 * @param num	数字  如2011
	 * @param needSplit 是否需要分割，按位处理
	 * @return 替换后字符串
	 */
	public static String num2Chinese(int num, boolean needSplit){
		String[] character = new String[]{
				"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"
		};
		String resultStr ="";
		String numStr = num + "";
		if(needSplit){
			char[] numArray = numStr.toCharArray();;
			for(int i=0;i<numArray.length;i++){
				int index = numArray[i] - '0';//0字符的char值是48
				resultStr += character[index];
			}
		}else{
			int complement = 0;
			switch(num/10){
				case 0://num = 1~9
					complement = num%10;
					resultStr = character[complement];					
					break;
				case 1://num = 10~19
					complement = num%10;
					if(num == 10){
						resultStr = character[10];
					}else{
						resultStr = character[10] + character[complement];
					}
					break;
				case 2://num = 20~29
					complement = num%20;
					if(num == 20){
						resultStr = character[2] + character[10];
					}else{
						resultStr = character[2] + character[10] + character[complement];
					}
					break;
				case 3://num = 30~31
					complement = num%30;
					if(num == 30){
						resultStr = character[3] + character[10];
					}else{
						resultStr = character[3] + character[10] + character[complement];
					}
					break;
			}
		}
		return resultStr;
	}
	
//	/**
//	 * 根据申报数、立项数计算立项比例、返回格式化数据
//	 */
//	public static String getGrantedRate(int applyNum, int grantNum){
//		if(applyNum == 0){
//			return "0";
//		}
//		double rate = (grantNum * 1.0) / (applyNum * 1.0);
//		DecimalFormat df = new DecimalFormat();
//		df.applyPattern("#0.00%");
//		return df.format(rate);
//	}
	
	/**
	 * 根据申报数、立项数计算立项比例、返回格式化数据
	 */
	public static Object getGrantedRate(int applyNum, int grantNum){
		if(applyNum == 0){
			return 0;
		}
		double rate = (grantNum * 1.0) / (applyNum * 1.0);
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("#0.00%");
		return df.format(rate);
	}
	
//	/**
//	 * 数字日期转中文日期
//	 * @param {Object} num
//	 */
//	function num2Character(numStr, needSplit){
//		var AA = new Array("〇", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十");
//		var strArray = new Array();
//		if(needSplit){
//			var numArray = numStr.split("");
//			for(var i=0;i<numArray.length;i++){
//				strArray[i] = AA[numArray[i]*1];
//			}
//		}else{
//			
//			var num = numStr*1;
//			var complement;
//			switch(Math.floor(num/10)){
//				case 0:{//num = 1~9
//					complement = num%10;
//					strArray[0] = AA[complement];					
//					break;
//				}
//				case 1:{//num = 10~19
//					complement = num%10;
//					if(num == 10){
//						strArray[0] = AA[10];
//					}else{
//						strArray[0] = AA[10] + AA[complement];
//					}
//					break;
//				}
//				case 2:{//num = 20~29
//					complement = num%20;
//					if(num == 20){
//						strArray[0] = AA[2] + AA[10];
//					}else{
//						strArray[0] = AA[2] + AA[10] + AA[complement];
//					}
//					break;
//				}
//				case 3:{//num = 30~31
//					complement = num%30;
//					if(num == 30){
//						strArray[0] = AA[3] + AA[10];
//					}else{
//						strArray[0] = AA[3] + AA[10] + AA[complement];
//					}
//					break;
//				}
//			}
//		}
//		return strArray.join("");
//	}

}
