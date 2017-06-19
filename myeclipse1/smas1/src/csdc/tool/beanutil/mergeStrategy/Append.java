package csdc.tool.beanutil.mergeStrategy;



/**
 * 追加<br>
 * 将value1和value2用splitter分开，判重后用separator拼接<br>
 * NOTE:原始顺序并不保证
 * @author xuhan
 *
 */
public class Append implements MergeStrategy<String> {
	
	/**
	 * 用于分隔原值的分隔符
	 * (正则表达式)
	 */
	private String splitter;

	/**
	 * 用于组装结果的分隔符
	 * (普通字符串)
	 */
	private String separator;
	
	/**
	 * 为false则value1在前，value2在后<br>
	 * 为true则value2在前，value1在后<br>
	 * 默认为false
	 */
	private boolean isPrepend;
	
	////////////////////////////////////////////////////
	
	/**
	 * 追加<br>
	 * 将value1和value2用[分号空格]分开，判重后用[分号空格]拼接
	 */
	public Append() {
		this.splitter = "\\s*;\\s*";
		this.separator = "; ";
		this.isPrepend = false;
	}
	
	/**
	 * 追加<br>
	 * 将value1和value2用splitter分开，判重后用separator拼接
	 * @param splitter 用于分隔原值的分隔符(正则表达式)
	 * @param separator 用于组装结果的分隔符(普通字符串)
	 * @param isPrepend 是否追加至开头
	 */
	public Append(String splitter, String separator, boolean isPrepend) {
		this.splitter = splitter;
		this.separator = separator;
		this.isPrepend = isPrepend;
	}
	
	/////////////////////////////////////////////////////

	public String merge(String value1, String value2) {
		String str1 = value1 == null ? "" : value1.trim();//去除开头和结尾的空格
		String str2 = value2 == null ? "" : value2.trim();//去除开头和结尾的空格
		
		if (isPrepend) {
			String tmp = str1;
			str1 = str2;
			str2 = tmp;
		}
		
		StringBuffer result = new StringBuffer();
		for (String string : str1.split(splitter)) {
			boolean duplicate = false;
			for (String re : result.toString().split(separator)) {
				if (re.equals(string)) {
					duplicate = true;
					break;
				}
			}
			if (!duplicate) {
				if (result.length() > 0) {
					result.append(separator);
				}
				result.append(string);
			}
		}
		for (String string : str2.split(splitter)) {
			boolean duplicate = false;
			for (String re : result.toString().split(separator)) {
				if (re.equals(string)) {
					duplicate = true;
					break;
				}
			}
			if (!duplicate) {
				if (result.length() > 0) {
					result.append(separator);
				}
				result.append(string);
			}
		}
		return result.toString();
	}
	
	public static void main(String[] args) {
		Append append = new Append("\\s*;\\s*", "; ", true);
		String value1 = "123@126.com; dldd@qq.com; AAd4ldd@qq.comAA; Ad4ldd@qq.comA    ";
		String value2 = "dldd@qq.com; a.tom@yahoo.com.cn; tom@yahoo.com; ";
		System.out.println(append.merge(value1, value2));
	}
}
