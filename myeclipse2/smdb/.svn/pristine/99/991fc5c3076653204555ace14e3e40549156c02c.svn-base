package csdc.tool.beanutil.mergeStrategy;

/**
 * 内建常用合并策略
 * @author xuhan
 *
 */
public class BuiltinMergeStrategies {
	
	public static SimpleAppend SIMPLE_APPEND = new SimpleAppend();
	public static Append APPEND = new Append();
	public static Append PREPEND = new Append("\\s*;\\s*", "; ", true);
	public static Larger LARGER = new Larger();
	public static Longer LONGER = new Longer();
	public static MergePhoneNumber MERGE_PHONE_NUMBER_APPEND = new MergePhoneNumber(false);
	public static MergePhoneNumber MERGE_PHONE_NUMBER_PREPEND = new MergePhoneNumber(true);
	public static Replace REPLACE = new Replace();
	public static Supply SUPPLY = new Supply();
	public static PreciseDate PRECISE_DATE = new PreciseDate();

}
