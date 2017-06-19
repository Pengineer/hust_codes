package csdc.tool.matcher.constraint;

import static csdc.tool.matcher.MatcherInfo.*;

import java.util.HashSet;

import csdc.tool.matcher.Reviewer;
import csdc.tool.matcher.Subject;

/**
 * 评审对象不能重复挑选同一专家<br>
 * 该条件为硬性条件，突破会有不可预估的问题
 * 
 * @author xuhan
 */
public class ReviewersDiffer extends Constraint {

	public ReviewersDiffer(Filter filter) {
		super(filter);
	}
	
	public ReviewersDiffer() {
		super();
	}
	

	@Override
	public boolean pass(Subject subject, Reviewer reviewer) {
		HashSet<Reviewer> selectedReviewers = (HashSet<Reviewer>) subject.getAlterableProperty(SELECTED_REVIEWERS);//项目已选择的专家
		if (null == selectedReviewers) {
			selectedReviewers = new HashSet<Reviewer>();
			subject.setAlterableProperty(SELECTED_REVIEWERS, selectedReviewers);
		}
		//判断当前专家是否已选
		return !selectedReviewers.contains(reviewer);
	}


	@Override
	public String breakWarnInfo() {
		return "评审专家重复";
	}


	/**
	 * 硬性限制条件，默认不会突破
	 */
	@Override
	public boolean broken(Subject subject) {
		return false;
	}

}
