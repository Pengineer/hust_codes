package csdc.tool.validator.imp;

import csdc.tool.validator.IValidator;

/**
 * 字符串校验器
 * @author 徐涵
 *
 */
public class StringValidator implements IValidator{

	public Integer maxLength;

	public StringBuffer validate(Object obj) throws Exception {
		StringBuffer ret = new StringBuffer();
		try {
			String o = obj.toString();
			if (o == null || o.isEmpty()){
				ret.append("不应为空");
			}
			if (maxLength != null && o.length() > maxLength){
				ret.append("长度不应超过 " + maxLength);
			}
		} catch (Exception e) {
			ret.append("未知错误");
		}
		return ret;
	}

	/**
	 * 默认构造器
	 */
	public StringValidator(){}

	/**
	 * 带最大长度限制的构造器
	 * @param ml 最大长度限制
	 */
	public StringValidator(int ml){
		this.maxLength = ml;
	}

}
