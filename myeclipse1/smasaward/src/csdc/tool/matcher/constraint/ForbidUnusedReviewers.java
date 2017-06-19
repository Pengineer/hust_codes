package csdc.tool.matcher.constraint;

import static csdc.tool.matcher.MatcherInfo.*;
import csdc.tool.matcher.Reviewer;
import csdc.tool.matcher.Subject;

/**
 * 禁止目前没有参评项目的专家参评
 * @author xuhan
 *
 */
public class ForbidUnusedReviewers extends Constraint {

	public ForbidUnusedReviewers(Filter filter) {
		super(filter);
	}
	
	public ForbidUnusedReviewers() {
		super();
	}

	@Override
	public boolean pass(Subject subject, Reviewer reviewer) {
		Integer burden = (Integer) reviewer.getAlterableProperty(BURDEN);//专家已参评项目数
		if (null == burden || burden.equals(0)) {
			return false;
		}
		return true;
	}


	@Override
	public String breakWarnInfo() {
		return null;
	}

	@Override
	public boolean broken(Subject subject) {
		return false;
	}

}
