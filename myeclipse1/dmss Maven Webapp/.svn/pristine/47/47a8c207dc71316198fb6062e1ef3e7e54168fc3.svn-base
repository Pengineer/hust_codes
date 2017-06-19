package org.csdc.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidate {

	//	这里主要调用的方法：
	//	1. boolean Pattern.matches(String input, Pattern pattern)：当要求输入的字符串input和正则表达式pattern精确匹配时使用该方法。也就是说当正则表达式完整地描述输入字符串时返回真值。
	//	2. boolean Pattern.matchesPrefix(String input, Pattern pattern)：要求正则表达式匹配输入字符串起始部分时使用该方法。也就是说当输入字符串的起始部分与正则表达式匹配时返回真值。
	//	3. boolean Pattern.contains(String input, Pattern pattern)：当正则表达式要匹配输入字符串的一部分时使用该方法。当正则表达式为输入字符串的子串时返回真值。

	/**
	 * 单个字串的输入校验
	 * @param str 待检验字段
	 * @param regex 定义检验规则的正则表达式
	 * @return b true: 校验通过；false: 校验不通过
	 */
	public boolean singleValidate(String str, String regex){
		if(str.isEmpty()){
			return true;
		}
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		boolean b = m.matches();
		return b;
	}

	/**
	 * 多个字串的输入校验
	 * @param str 带校验的字符串（用; 隔开）（必须是英文的分号与空格）
	 * @param regex 匹配的正则表达式
	 * @return b true:校验通过；false:校验不通过
	 */
	public boolean multiValidate(String str, String regex){
		if (str.isEmpty()){
			return true;
		}
		Pattern p = Pattern.compile(regex);

		String[] array = str.split("; ");
		boolean b = true;
		for (int i=0; i<array.length; i++){
			Matcher m = p.matcher(array[i]);
			b = b & m.matches();
		}
		return b;
	}

	/**
	 * 手机的输入校验
	 * @param str 待检验手机号
	 * @return b true:校验通过；false:校验不通过
	 */
	public boolean checkCellphone(String str){
		String regex = "\\d{11}";
		return singleValidate(str, regex);
	}


	/**
	 * 固定电话的输入校验
	 * @param str 待检验电话号码
	 * @return b true:校验通过；false:校验不通过
	 */
	public boolean checkFixedphone(String str){
		String regex = "^(0\\d{2,3}-)?\\d{6,8}$";
		return singleValidate(str, regex);
	}


	/**
	 * 个人主页的输入校验
	 * @param str 待检验的个人主页
	 * @return b true:校验通过；false:校验不通过
	 */
	public boolean checkHomepage(String str){
		String regex = "^(https?://)?.{1,100}$";
		return singleValidate(str, regex);
	}



	/**
	 * 电话的输入校验(座机及手机号）
	 * @param str 待检验电话
	 * @return b true:校验通过；false:校验不通过
	 */
	public boolean checkPhone(String str){
		String regex = "(\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$";
		return singleValidate(str, regex);
	}

	/**
	 * 邮政编码的校验
	 * @param str 待校验的邮政编码
	 * @return b true:校验通过；false:校验不通过
	 */
	public boolean checkPostcode(String str){
		String regex = "[0-9]{6}";
		return singleValidate(str, regex);
	}

	/**
	 * 传真校验
	 * @param str 待校验的传真
	 * @return b true;校验通过；false:校验不通过
	 */
	public boolean checkFax(String str){
		String regex = "^(0[0-9]{2,3}-)?[0-9]{6,8}$";
		return singleValidate(str, regex);
	}
	/**
	 * 邮箱校验
	 * @param str 带校验的邮箱
	 * @return b true:校验通过；false:校验不通过
	 */
	public boolean checkEmail(String str){
		// String regex = "^([a-z0-9A-Z]+[-|\\._]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		String regex = "^.{1,99}@.{2,100}$";
		return singleValidate(str, regex);
	}

	/**
	 * 证件号校验
	 * @param str 带校验的证件号
	 * @return b true:校验通过；false:校验不通过
	 */
	public boolean checkIdCardNumber(String str){
		String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		return singleValidate(str, regex);
	}
	
	/**
	 * Ip子段（每个ip四段）校验
	 * @param str 带校验的ip子段
	 * @return b true:校验通过；false:校验不通过
	 */
	public boolean checkSubIp(String str){
		//System.out.println(str);
		String regex = "^(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)$";
		return singleValidate(str, regex);
	}
	
	/**
	 * Ip校验
	 * @param str 待校验的ip串
	 * @return b true:校验通过；false:校验不通过
	 */
	public boolean checkIp(String str){
		//System.out.println(str); // str = 12-141.1.1.1
		int i;
		boolean flag = true;
		String[] str1 = str.split("; ");
		//System.out.println("str1.length" + str1.length); // str1 = {'12-141.1.1.1'}; str1[0] = 12-141.1.1.1

		for(i = 0; i < str1.length; i++){
			//System.out.println("str[i]" + str1[i]); // str1[0] = 12-141.1.1.1
			
			
			String str2[] = str1[i].split("\\.");
			//System.out.println(str2.length); // 4???
			//System.out.println(str2[0]); // str1.length = 1; str2 = {'12-141', '1', '1', '1'}; str2[0] = 12-141 ????
			for(int j = 0; j < str2.length; j++){
				if(str2[j] == null || str2[j].trim().equals("")){
					flag = false;
				} else if(str2[j].contains("*")){// 任何匹配
					if(str2[j].length() > 1){flag = false;}
				} else if(str2[j].contains("?")){// 匹配单个字符
					if(!this.checkSubIp(str2[j].replace("?", "1"))){flag = false;}
				} else if(str2[j].contains("-")){// ip范围匹配
					if(str2[j].split("-").length != 2){flag = false;}
					else if(!this.checkSubIp(str2[j].split("-")[0]) || !this.checkSubIp(str2[j].split("-")[1]) || Integer.parseInt(str2[j].split("-")[0]) >  Integer.parseInt(str2[j].split("-")[1])){flag = false;}
				} else {// 精确匹配
					if (!this.checkSubIp(str2[j])) {flag = false;}
				}
			}
		}
		return flag;
	}

}
