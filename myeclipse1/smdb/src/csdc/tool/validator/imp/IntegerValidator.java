package csdc.tool.validator.imp;

import csdc.tool.validator.IValidator;

/**
 * 整数校验器
 * @author 徐涵
 *
 */
public class IntegerValidator implements IValidator {

	public Integer upperBound;
	public Integer lowerBound;

	public StringBuffer validate(Object o) throws Exception {
		StringBuffer ret = new StringBuffer();
		Integer obj;
		try {
			try {
				obj = (Integer) o;
			} catch (Exception e) {
				obj = Integer.parseInt((String)o);
			}
			if (upperBound != null && obj > upperBound){
				ret.append("不应大于 " + upperBound);
			}
			if (lowerBound != null && obj < lowerBound){
				ret.append("不应小于 " + lowerBound);
			}
		} catch (NumberFormatException e) {
			ret.append("不是整数或为空");
		}
		return ret;
	}

	/**
	 * 默认构造函数
	 */
	public IntegerValidator(){}

	/**
	 * 带上界和下界的构造函数
	 * @param ub 上界
	 * @param lb 下界
	 */
	public IntegerValidator(int ub, int lb){
		this.upperBound = ub;
		this.lowerBound = lb;
	}

}
