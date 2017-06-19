package csdc.tool.matcher.constraint;

import csdc.tool.matcher.MatcherWarningUpdater;
import csdc.tool.matcher.Reviewer;
import csdc.tool.matcher.Subject;

/**
 * 禁止一切新匹配
 * 目前仅用于警告更新器
 * 
 * @see MatcherWarningUpdater
 * @author xuhan
 */
public class ForbidAll extends Constraint {

	public ForbidAll(Filter filter) {
		super(filter);
	}
	
	public ForbidAll() {
		super();
	}
	

	/**
	 * 禁止一切匹配
	 */
	@Override
	public boolean pass(Subject subject, Reviewer reviewer) {
		return false;
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
