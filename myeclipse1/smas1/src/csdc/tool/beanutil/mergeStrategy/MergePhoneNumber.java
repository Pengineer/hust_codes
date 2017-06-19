package csdc.tool.beanutil.mergeStrategy;

import java.util.ArrayList;
import java.util.List;

import csdc.tool.StringTool;

/**
 * 合并电话号码<br>
 * 将value2的插在最前面<br>
 * (会尝试去掉区号来判重)
 * @author xuhan
 *
 */
public class MergePhoneNumber implements MergeStrategy<String> {
	
	private boolean isPrepend;
	
	/**
	 * 合并电话号码<br>
	 * 将value2的插在最前面<br>
	 * (会尝试去掉区号来判重)
	 * @author xuhan
	 *
	 */
	public MergePhoneNumber() {
		this.isPrepend = false;
	}

	/**
	 * 合并电话号码<br>
	 * 将value2的插在最前面<br>
	 * (会尝试去掉区号来判重)
	 * @param isPrepend 是否将value2追加在value1前
	 * 
	 * @author xuhan
	 */
	public MergePhoneNumber(boolean isPrepend) {
		this.isPrepend = isPrepend;
	}

	public String merge(String value1, String value2) {
		//第1步：数据处理
		value1 = StringTool.toDBC(value1).trim();//去除开头和结尾的空格
		value2 = StringTool.toDBC(value2).trim();//去除开头和结尾的空格
		if (isPrepend) {
			String tmp = value1;
			value1 = value2;
			value2 = tmp;
		}
		
		StringBuffer result = new StringBuffer();
		
		List<String> all = new ArrayList<String>();
		for (String tmp : (value1 + "").split("[^-\\d\\(\\)]+")) {
			if (tmp.length() >= 6) {//电话号码小于6位的认为是无效号码
				all.add(tmp);
			}
		}
		for (String tmp : (value2 + "").split("[^-\\d\\(\\)]+")) {
			if (tmp.length() >= 6) {
				all.add(tmp);
			}
		}
		//将"(027)"变成"027-"
		for (int i = 0; i < all.size(); i++) {
			all.set(i, all.get(i).replaceAll("^\\((\\d*)\\)", "$1-"));
		}
		
		//第2步：判断包含关系，取包含者，被包含者置空
		for (int i = 0; i < all.size(); i++) {
			if (all.get(i) == null) {
				continue;
			}
			for (int j = i + 1; j < all.size(); j++) {
				String stri = all.get(i);
				String strj = all.get(j);
				if (strj != null && strj.contains(stri)) {
					all.set(i, strj);
					all.set(j, null);
				}else if (strj != null && stri.contains(strj)) {
					all.set(j, null);
				}
			}
//			if (result.length() > 0) {
//				result.append("; ");
//			}
//			result.append(all.get(i));
		}
		
		//第3步：去重（主要针对02787645278;027-87645278）
		for (int i = 0; i < all.size(); i++) {
			if (all.get(i) == null) {
				continue;
			}
			for (int j = i + 1; j < all.size(); j++) {
				String stri = all.get(i);
				String strj = all.get(j);
				if (strj != null && stri.replaceAll("-", "").equals(strj.replaceAll("-", ""))) {
					all.set(i, (strj.length() > stri.length()) ? strj : stri);
					all.set(j, null);
				}
			}
			if (result.length() > 0) {
				result.append("; ");
			}
			result.append(all.get(i));
		}
		return result.toString();
		
//		//2、判断后缀
//		for (int i = 0; i < all.size(); i++) {
//			if (all.get(i) == null) {
//				continue;
//			}
//			//取尾6位数
//			String suffix = all.get(i).substring(all.get(i).length() - 6);
//			String longest = all.get(i);
//			for (int j = i + 1; j < all.size(); j++) {
//				if (all.get(j) != null && all.get(j).endsWith(suffix)) {
//					if (longest.length() < all.get(j).length()) {
//						longest = all.get(j);
//					}
//					all.set(j, null);
//				}
//			}
//			if (result.length() > 0) {
//				result.append("; ");
//			}
//			result.append(longest);
//		}
//		return result.toString();
	}
	public static void main(String[] args) {
//		String str1 = "027-54546554;(027)54546554;8764527;02788764527";
//		String str2 = "54546554;027;0278764527;02754546554";
		String str1 = "027）54546554;0871－5033890;（021）64478322/;02154814733";
		String str2 = "54546554;（010） 63408884；；（010）84044251，（021）54814733";
		MergePhoneNumber mergePhoneNumber = new MergePhoneNumber(true);
		System.out.println(mergePhoneNumber.merge(str1, str2));
	}
}
