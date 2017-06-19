package org.csdc.tool;

public class SignID {
	public static SignID getInstance() {
		if (si == null) {
			si = new SignID();
		}
		return si;
	}

	synchronized public String getSignID() {
		String signID = null;
		signID = getRandomString() + System.currentTimeMillis();
		return signID;
	}

	private String getRandomString() {
		double d = Math.random();
		String s = Double.toString(d).substring(2);
		return s;
	}

	/**
	 * 生成n位随机字符串,只包含数字和大写字母
	 * @return 随机字符串
	 */
	public static String getRandomString(int n) {
		String list = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM";
		StringBuffer tmp = new StringBuffer();
		while (n-- > 0){
			tmp.append(list.charAt((int)Math.ceil(Math.random() * 100000000) % list.length()));
		}
		return tmp.toString();
	}
	
	/**
	 * 生成n位随机字符串,只包含数字和小写字母
	 * @return 随机字符串
	 */
	public static String getRandomStringLower(int n) {
		String list = "0123456789qwertyiopasdfghjklzxcvbnm";
		StringBuffer tmp = new StringBuffer();
		while (n-- > 0){
			tmp.append(list.charAt((int)Math.ceil(Math.random() * 100000000) % list.length()));
		}
		return tmp.toString();
	}

	private static SignID si;

}
