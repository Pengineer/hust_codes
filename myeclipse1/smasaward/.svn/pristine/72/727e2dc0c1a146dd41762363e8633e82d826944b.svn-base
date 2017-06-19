package csdc.tool.beanutil.mergeStrategy;

/**
 * 如果value2非空，就用value2替换value1，否则value1保持不变
 * @author xuhan
 *
 */
public class Replace implements MergeStrategy<Object> {

	public Object merge(Object value1, Object value2) {
		if (value2 == null || value2 instanceof String && ((String)value2).trim().isEmpty()) {
			return value1;
		} else {
			return value2;
		}
	}

}
