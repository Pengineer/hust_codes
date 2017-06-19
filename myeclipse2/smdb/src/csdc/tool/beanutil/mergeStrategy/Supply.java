package csdc.tool.beanutil.mergeStrategy;

/**
 * 如果value1为空，就用value2填补value1，否则value1保持不变
 * @author xuhan
 *
 */
public class Supply implements MergeStrategy<Object> {

	public Object merge(Object value1, Object value2) {
		if (value1 == null || value1 instanceof String && ((String)value1).trim().isEmpty()) {
			return value2;
		} else {
			return value1;
		}
	}

}
