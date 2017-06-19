package csdc.tool.matcher.constraint;

import csdc.tool.matcher.Reviewer;
import csdc.tool.matcher.Subject;
import static csdc.tool.matcher.MatcherInfo.*;

/**
 * 评审对象和评审专家的高校不能相同
 * @author xuhan
 *
 */
public class SubjectReviewerUniversityDiffer extends Constraint {

	public SubjectReviewerUniversityDiffer(Filter filter) {
		super(filter);
	}

	public SubjectReviewerUniversityDiffer() {
		super();
	}

	@Override
	public boolean pass(Subject subject, Reviewer reviewer) {
		String reviewerUniversity = (String) reviewer.getIntrinsicProperty(UNIVERSITY_CODE);

		//评审专家高校不能和评审对象高校相同
		String subjectUniversity = (String) subject.getIntrinsicProperty(UNIVERSITY_CODE);
		if (subjectUniversity != null && subjectUniversity.equals(reviewerUniversity)) {
			return false;
		}

		//基地项目：评审专家高校不能和评审对象负责人所在高校相同（基地项目有负责人高校和项目高校不同的情况）
		String directorUniversityCode = (String) subject.getIntrinsicProperty("directorUniversityCode");
		if (directorUniversityCode != null && directorUniversityCode.equals(reviewerUniversity)) {
			return false;
		}
		
		return true;
	}

	@Override
	public String breakWarnInfo() {
		return "突破评审对象和评审专家的高校不能相同的限制";
	}

	/**
	 * 基本限制条件，默认不会突破
	 */
	@Override
	public boolean broken(Subject subject) {
		return false;
	}

}
