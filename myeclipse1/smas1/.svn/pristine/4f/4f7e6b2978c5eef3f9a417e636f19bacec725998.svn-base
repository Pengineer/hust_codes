package csdc.tool.matcher.constraint;

import static csdc.tool.matcher.MatcherInfo.BURDEN;
import csdc.tool.matcher.Reviewer;
import csdc.tool.matcher.Subject;

/**
 * 专家的评审项目数是否已达到上限
 * @author xuhan
 */
public class ReviewerBurdenMax extends Constraint {

	/**
	 * 每个专家的最多参评项目数
	 */
	private Integer reviewerSubjectMax;
	
	public ReviewerBurdenMax(Integer reviewerSubjectMax, Filter filter) {
		super(filter);
		this.reviewerSubjectMax = reviewerSubjectMax;
	}
	
	public ReviewerBurdenMax(Integer reviewerSubjectMax) {
		super();
		this.reviewerSubjectMax = reviewerSubjectMax;
	}
	

	@Override
	public boolean pass(Subject subject, Reviewer reviewer) {
		//当前专家是否已达到最大参评数
		Integer burden = (Integer) reviewer.getAlterableProperty(BURDEN);//当前专家的已参评的项目数
		if (null != burden && burden >= reviewerSubjectMax) {
			return false;
		}
		return true;
	}


	@Override
	public String breakWarnInfo() {
		return null;
	}


	/**
	 * 无法判断是否突破
	 */
	@Override
	public boolean broken(Subject subject) {
		return false;
	}

}
