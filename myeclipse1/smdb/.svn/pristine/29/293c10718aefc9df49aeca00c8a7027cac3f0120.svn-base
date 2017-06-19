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
		value1 = StringTool.toDBC(value1);
		value2 = StringTool.toDBC(value2);
		if (isPrepend) {
			String tmp = value1;
			value1 = value2;
			value2 = tmp;
		}
		
		StringBuffer result = new StringBuffer();
		
		List<String> all = new ArrayList<String>();
		for (String tmp : (value1 + "").split("[^-\\d\\(\\)]+")) {
			if (tmp.length() > 0) {
				all.add(tmp);
			}
		}
		for (String tmp : (value2 + "").split("[^-\\d\\(\\)]+")) {
			if (tmp.length() > 0) {
				all.add(tmp);
			}
		}
		
		List<String> allwithoutAreaCode = new ArrayList<String>();
		for (String tmp : all) {
			allwithoutAreaCode.add(tmp.replaceAll("^\\d*-", "").replaceAll("^\\(\\d*\\)", ""));
		}
		
		for (int i = 0; i < allwithoutAreaCode.size(); i++) {
			boolean duplicate = false;
			for (int j = 0; j < i; j++) {
				if (allwithoutAreaCode.get(i).contains(allwithoutAreaCode.get(j)) || allwithoutAreaCode.get(j).contains(allwithoutAreaCode.get(i))) {
					duplicate = true;
					break;
				}
			}
			if (!duplicate) {
				if (result.length() > 0) {
					result.append("; ");
				}
				result.append(all.get(i));
			}
		}
		return result.toString();
	}

}
