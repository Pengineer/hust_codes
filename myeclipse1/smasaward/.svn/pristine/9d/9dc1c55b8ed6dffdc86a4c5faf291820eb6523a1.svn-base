package csdc.tool.matcher.constraint;

import static csdc.tool.matcher.MatcherInfo.UNIVERSITY_CODE;

import java.util.HashMap;
import java.util.HashSet;

import csdc.tool.matcher.Reviewer;
import csdc.tool.matcher.Subject;

/**
 * 禁止目前参评专家数为零的高校的专家参评
 * @author xuhan
 *
 */
public class ForbidUnusedUnivesity extends Constraint {

	public ForbidUnusedUnivesity(Filter filter) {
		super(filter);
	}
	
	public ForbidUnusedUnivesity() {
		super();
	}
	
	@Override
	public boolean pass(Subject subject, Reviewer reviewer) {
		HashMap<String, HashSet<Reviewer>> universityMatchedReviewers = matcher.getUniversityMatchedReviewers();
		String universityCode = (String) reviewer.getIntrinsicProperty(UNIVERSITY_CODE); //专家的高校代码
		HashSet<Reviewer> matchedReviewers = universityMatchedReviewers.get(universityCode); //高校已挑选的匹配专家
		
		if (matchedReviewers == null || matchedReviewers.isEmpty()) {
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
