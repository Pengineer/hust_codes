package csdc.tool.matcher.constraint;

import java.util.Set;


import csdc.tool.matcher.Reviewer;
import csdc.tool.matcher.Subject;
import static csdc.tool.matcher.MatcherInfo.*;

/**
 * 评审对象成员和评审专家的高校不能相同
 * @author xuhan
 *
 */
public class SubjectMemberReviewerUniversityDiffer extends Constraint {

	public SubjectMemberReviewerUniversityDiffer(Filter filter) {
		super(filter);
	}

	public SubjectMemberReviewerUniversityDiffer() {
		super();
	}

	@Override
	public boolean pass(Subject subject, Reviewer reviewer) {
		String reviewerUniversity = (String) reviewer.getIntrinsicProperty(UNIVERSITY_CODE);

		//评审专家高校不能和评审对象的成员的高校相同
		Set subjectMemberUniversity = (Set) subject.getIntrinsicProperty(SUBJECT_MEMBER_UNIVERSITY);
		if (subjectMemberUniversity != null && subjectMemberUniversity.contains(reviewerUniversity)) {
			return false;
		}

		return true;
	}

	@Override
	public String breakWarnInfo() {
		return "突破评审对象成员和评审专家的高校不能相同的限制";
	}

	/**
	 * 基本限制条件，默认不会突破
	 */
	@Override
	public boolean broken(Subject subject) {
		return false;
	}

}
