package csdc.tool.beanutil.mergeStrategy;

/**
 * 用较长的那个，一样长则用value1
 * @author xuhan
 *
 */
public class Longer implements MergeStrategy<String> {

	public String merge(String value1, String value2) {
		String str1 = value1 == null ? "" : value1;
		String str2 = value2 == null ? "" : value2;
		return str1.length() < str2.length() ? str2 : str1;
	}

}
