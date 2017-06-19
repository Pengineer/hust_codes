package csdc.tool;

import java.util.Arrays;
import java.util.Collection;
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
	 * 集合转成字符串
	 * @param collection	集合
	 * @param separator	分隔符
	 * @return
	 */
	public static String join(Collection<String> collection, String separator) {
		String result = null;
		if (null != collection && !collection.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			for (String str : collection) {
				if (null != str && !str.isEmpty()) {
					sb.append((sb.length() > 0 ? separator : "")).append(str);
				}
			}
			result = sb.toString();
		}
		return result;
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
	 * 全角转半角
	 * @param input 全角字符串.
	 * @return 半角字符串
	 */
	public static String toDBC(String input) {
		if(null == input){
			return "";
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
	 * TODO 寻找一种支持各种国家文字的方式，而不仅仅英文、中文、俄文
	 */
	public static String fix(String string) {
		if (string == null) {
			string = "";
		}
		return toDBC(string).replaceAll("[^\\w\\u4e00-\\u9fa5абвгдеёжзийклмнопрстуфхцчшщъыьэюяабвгдеёжзийклмнопрстуфхцчшщъыьэюя]+", "").toLowerCase();
	}

	/**
	 * 去除字符串中的空格、回车、换行符、制表符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
        if (str == null) {
        	str = "";
		}
        return str.replaceAll("\\s", "");
    }
	
	/**
	 * 判断字符串中是否包含中文字符
	 * @param str
	 * @return
	 */
	public static boolean containChineseCharacters(String str){
		return Pattern.compile("[\u4e00-\\u9fa5а]").matcher(str).find();
	}
	
	/**
	 * 对中文字串进行处理，只保留中文符，去除其他
	 * @param str
	 * @return
	 */
	public static String chineseCharacterFix(String str){
		if (null == str) {
			return null;
		}
		String result = fix(str);
		if(containChineseCharacters(str)){
			result = result.replaceAll("[^\\u4e00-\\u9fa5а]+", "");
		}
		return result;
	}
}
