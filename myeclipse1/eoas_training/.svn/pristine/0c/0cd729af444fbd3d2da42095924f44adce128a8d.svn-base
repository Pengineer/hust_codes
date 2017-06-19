package csdc.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 * @author 龚凡
 * @version 2011.05.19
 */
public class StringTool {

	/**
	 * 将按指定分隔符存储的字符串进行排序
	 * @param str 原始字符串
	 * @param splitStr 分隔符
	 * @return 排序后的字符串
	 */
	public static String sortString(String str, String splitStr) {
		String strSort = "";
		try {
			if (str != null) {
				String[] tmp = str.split(splitStr);
				Arrays.sort(tmp);
				for (String o : tmp) {
					strSort += o + "; ";
				}
				strSort = strSort.substring(0, strSort.length() - 2);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return strSort;
	}
	
	/**
	 * 类似js的join函数
	 * @param strings	字符串数组
	 * @param separator	分隔符
	 * @return
	 */
	public static String joinString(String strings[], String separator) {
		StringBuffer sb = new StringBuffer();
		for (String string : strings) {
			if (sb.length() > 0) {
				sb.append(separator);
			}
			sb.append(string);
		}
		return sb.toString();
	}
	
	/**
	 * 按正则表达式reg解析text,返回第1组的内容
	 * @param text
	 * @param reg
	 * @return
	 */
	public static String regFind(String text, String reg){
		return regFind(text, reg, 1);
	}

	/**
	 * 按正则表达式reg解析text,返回第groupNumber组的内容
	 * @param text 待分析串
	 * @param reg 正则表达式
	 * @param groupNumber 组号
	 * @return
	 */
	public static String regFind(String text, String reg, int groupNumber){
		Matcher m = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(text);
		return m.find() ? m.group(groupNumber).trim() : null;
	}
	
	/**
	 * 按正则表达式reg解析text,将找到的第一个匹配的所有捕获的分组以String[]返回
	 * @param text 待分析串
	 * @param reg 正则表达式
	 * @return
	 */
	public static String[] regGroup(String text, String reg){
		Matcher m = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(text);
		if (!m.find()) {
			return null;
		}
		String[] res = new String[m.groupCount() + 1];
		for (int i = 0; i <= m.groupCount(); i++) {
			res[i] = m.group(i).trim();
		}
		return res;
	}
	
	/**
	 * 按正则表达式reg解析text,将找到的所有匹配的所有捕获的分组以List<String[]>返回
	 * @param text 待分析串
	 * @param reg 正则表达式
	 * @return
	 */
	public static List<String[]> regGroupAll(String text, String reg){
		List<String[]> ans = new ArrayList<String[]>();
		Matcher m = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(text);
		while (m.find()) {
			String[] res = new String[m.groupCount() + 1];
			for (int i = 0; i <= m.groupCount(); i++) {
				res[i] = m.group(i).trim();
			}
			ans.add(res);
		}
		return ans;
	}
	
	/**
	 * 全角转半角
	 * @param input 全角字符串.
	 * @return 半角字符串
	 */
	public static String toDBC(String input) {
		if (null == input) {
			return null;
		}
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);
			}
		}
		return new String(c);
	}
	
	/**
	 * 全角转半角，然后只保留字母、数字、汉字
	 * @param string
	 * @return
	 */
	public static String fix(String string) {
		if (string == null) {
			string = "";
		}
		return toDBC(string).replaceAll("[^\\w\\u4e00-\\u9fa5абвгдеёжзийклмнопрстуфхцчшщъыьэюяабвгдеёжзийклмнопрстуфхцчшщъыьэюя]+", "").toLowerCase();
	}
	
	/**
	 * 匹配两个字符串的相似度  ----字符串相似度算法(编辑距离)java实现
	 * @param oriString
	 * @param targetString
	 * @return
	 */
	public static float stringSimilarity(String oriString, String targetString){
		return 1 - (float)compare(oriString, targetString)/Math.max(oriString.length(), targetString.length());
	}
	
	public static int compare(String str, String target) {
		int d[][]; // 矩阵
		int n = str.length();
		int m = target.length();
		int i; // 遍历str的
		int j; // 遍历target的
		char ch1; // str的
		char ch2; // target的
		int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}
		d = new int[n + 1][m + 1];
		for (i = 0; i <= n; i++) { // 初始化第一列
			d[i][0] = i;
		}

		for (j = 0; j <= m; j++) { // 初始化第一行
			d[0][j] = j;
		}

		for (i = 1; i <= n; i++) { // 遍历str
			ch1 = str.charAt(i - 1);
			// 去匹配target
			for (j = 1; j <= m; j++) {
				ch2 = target.charAt(j - 1);
				if (ch1 == ch2) {
					temp = 0;
				} else {
					temp = 1;
				}

				// 左边+1,上边+1, 左上角+temp取最小
				d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
			}
		}
		return d[n][m];
	}

	public static int min(int one, int two, int three) {
		return (one = one < two ? one : two) < three ? one : three;
	}

}
