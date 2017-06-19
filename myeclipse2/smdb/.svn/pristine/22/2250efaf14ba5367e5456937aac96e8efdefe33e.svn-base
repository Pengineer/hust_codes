package csdc.tool.beanutil.mergeStrategy;

/**
 * 用较大的那个
 * @author xuhan
 *
 */
public class Larger implements MergeStrategy<Integer> {

	public Integer merge(Integer value1, Integer value2) {
		if (value1 == null && value2 == null) {
			return null;
		}
		Integer int1 = value1 == null ? 0 : value1;
		Integer int2 = value2 == null ? 0 : value2;
		return int1 < int2 ? int2 : int1;
	}

}
