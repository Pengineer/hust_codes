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
		String str1 = value1 == null ? "" : value1;
		String str2 = value2 == null ? "" : value2;
		
		if (isPrepend) {
			String tmp = str1;
			str1 = str2;
			str2 = tmp;
		}
		
		StringBuffer result = new StringBuffer();
		for (String string : str1.split(splitter)) {
			if (result.indexOf(string) < 0) {
				if (result.length() > 0) {
					result.append(separator);
				}
				result.append(string);
			}
		}
		for (String string : str2.split(splitter)) {
			if (result.indexOf(string) < 0) {
				if (result.length() > 0) {
					result.append(separator);
				}
				result.append(string);
			}
		}
		return result.toString();
	}
}
