package csdc.tool.matcher;

/**
 * 根据constraintLevel确定项目选取专家时的学科折衷程度
 * @author xuhan
 *
 */
public interface Selector {
	/**
	 * 根据constraintLevel确定项目选取专家时的学科折衷程度
	 * @param constraintLevel	限制级
	 * @return 聚类方式 ：返回0表示按原学科，返回1表示降一级学科，返回2表示降两级学科
	 */
	public Integer findGroupStatus(Integer constraintLevel);
}
