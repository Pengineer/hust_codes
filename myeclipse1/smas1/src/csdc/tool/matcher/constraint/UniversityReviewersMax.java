package csdc.tool.matcher.constraint;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import csdc.tool.matcher.Reviewer;
import csdc.tool.matcher.Subject;
import static csdc.tool.matcher.MatcherInfo.*;

public class UniversityReviewersMax extends Constraint {

	/**
	 * 每个高校的评审专家上限
	 */
	private Integer univeReviewerMax;
	
	public UniversityReviewersMax (Integer univeReviewerMax, Filter filter) {
		super(filter);
		this.univeReviewerMax = univeReviewerMax;
	}
	
	public UniversityReviewersMax (Integer univeReviewerMax) {
		super();
		this.univeReviewerMax = univeReviewerMax;
	}
	
	@Override
	public boolean pass(Subject subject, Reviewer reviewer) {
		//判断当前高效的评审数量是否达到了univeReviewerMax，
		String univCode = (String) reviewer.getIntrinsicProperty(UNIVERSITY_CODE);
		Map<String, HashSet<Reviewer>> unive2MatchedReviewers =  matcher.getUniversityMatchedReviewers();
		
		Set univeMatchedReviewerSet = (Set) unive2MatchedReviewers.get(univCode);
		if (null == univeMatchedReviewerSet) {
			univeMatchedReviewerSet = new HashSet<Reviewer>();
			reviewer.setAlterableProperty(UNIVERSITY_CODE, univeMatchedReviewerSet);
			return true;
		} else if (univeMatchedReviewerSet.isEmpty() || univeMatchedReviewerSet.size() < univeReviewerMax){
			return true;
		}
		return false;
	}

	/**
	 * 无法判断条件，后面两轮约束放宽无法判断，仅作一定程度限制
	 */
	@Override
	public boolean broken(Subject subject) {
		return false;
	}

	@Override
	public String breakWarnInfo() {
		return "突破高校评审专家数目最大限制";
	}

}
