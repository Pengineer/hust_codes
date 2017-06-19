package csdc.tool.matcher.constraint;

import static csdc.tool.matcher.MatcherInfo.*;

import java.util.HashSet;

import csdc.tool.matcher.Reviewer;
import csdc.tool.matcher.Subject;

/**
 * 同一评审对象的评审专家间高校不能相同
 * @author xuhan
 *
 */
public class ReviewersUniversityDiffer extends Constraint {

	public ReviewersUniversityDiffer(Filter filter) {
		super(filter);
	}

	public ReviewersUniversityDiffer() {
		super();
	}

	@Override
	public boolean pass(Subject subject, Reviewer reviewer) {
		HashSet selectedReviewerUniversities = (HashSet) subject.getAlterableProperty(SELECTED_REVIEWER_UNIVERSITIES);//项目已选专家的高校代码集合
		String reviewerUniversity = (String) reviewer.getIntrinsicProperty(UNIVERSITY_CODE);//专家的高校代码
		return selectedReviewerUniversities == null || !selectedReviewerUniversities.contains(reviewerUniversity);
	}

	@Override
	public String breakWarnInfo() {
		return "突破评审人高校不同限制";
	}

	@Override
	public boolean broken(Subject subject) {
		HashSet selectedReviewerUniversities = (HashSet) subject.getAlterableProperty(SELECTED_REVIEWER_UNIVERSITIES);//项目选择的高校
		HashSet<String> selectedReviewers = (HashSet<String>) subject.getAlterableProperty(SELECTED_REVIEWERS);//项目已选择的专家
		if (null != selectedReviewerUniversities && null != selectedReviewers) {
			/*
			 * 专家数大于高校数，说明至少有两个专家属于同一个高校，即突破评审人高校不同限制
			 */
			return selectedReviewers.size() > selectedReviewerUniversities.size();
		} else {
			return false;
		}
	}

}
