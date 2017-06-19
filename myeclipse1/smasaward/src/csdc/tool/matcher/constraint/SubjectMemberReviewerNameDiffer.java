package csdc.tool.matcher.constraint;

import java.util.Set;

import csdc.tool.matcher.Reviewer;
import csdc.tool.matcher.Subject;
import static csdc.tool.matcher.MatcherInfo.*;

/**
 * 评审对象成员和评审专家的姓名不能相同
 * @author xuhan
 *
 */
public class SubjectMemberReviewerNameDiffer extends Constraint {

	public SubjectMemberReviewerNameDiffer(Filter filter) {
		super(filter);
	}

	public SubjectMemberReviewerNameDiffer() {
		super();
	}

	@Override
	public boolean pass(Subject subject, Reviewer reviewer) {
		String reviewerName = (String) reviewer.getIntrinsicProperty(NAME);

		//评审对象成员和评审专家的姓名不能相同
		Set subjectMemberName = (Set) subject.getIntrinsicProperty(SUBJECT_MEMBER_NAME);
		if (subjectMemberName != null && subjectMemberName.contains(reviewerName)) {
			return false;
		}

		return true;
	}

	@Override
	public String breakWarnInfo() {
		return "突破评审对象成员和评审专家的姓名不能相同的限制";
	}

	/**
	 * 硬性限制条件，默认不会突破
	 */
	@Override
	public boolean broken(Subject subject) {
		return false;
	}
}
