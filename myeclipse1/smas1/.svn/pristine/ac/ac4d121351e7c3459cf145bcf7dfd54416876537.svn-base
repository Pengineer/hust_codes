package csdc.tool.matcher.constraint;

import static csdc.tool.matcher.MatcherInfo.*;

import java.util.Set;

import csdc.tool.matcher.Reviewer;
import csdc.tool.matcher.Subject;

/**
 * 特殊学科退避原则：<br>
 * 如果subject的当前学科属于特殊学科(项目学科和专家学科不能同时退避)<br>
 * 例如：subject的学科为76030（特殊学科），则只能由学科代码为760、76030、或76030xx三类的专家评审
 */
public class SpecialDisciplineRetreatPrinciple extends Constraint {

	/**
	 * 特殊学科:该列表下的学科作为项目的学科去匹配专家时，不能和专家的学科同时退避
	 */
	private String[] specialDisciplines = new String[] {
			"72020", // 中国哲学史
			"72025", // 东方哲学史
			"72030", // 西方哲学史
			"73027", // 基督教
			"74045", // 中国少数民族语言文字
			"74050", // 外国语言
			"75044", // 中国少数民族文学
			"75051", // 东方文学
			"75054", // 俄国文学
			"75057", // 英国文学
			"75061", // 法国文学
			"75064", // 德国文学
			"75067", // 意大利文学
			"75071", // 美国文学
			"75074", // 北欧文学
			"75077", // 东欧文学
			"75081", // 拉美文学
			"75084", // 非洲文学
			"75087", // 大洋洲文学
			"760", // 艺术学
			"77030", // 中国古代史
			"77035", // 中国近代史、现代史
			"77040", // 世界通史
			"77045", // 亚洲史
			"77050", // 非洲史
			"77055", // 美洲史
			"77060", // 欧洲史
			"77065", // 澳洲、大洋洲史
			"77070", // 专门史
			"82030", // 部门法学
			"82040", // 国际法学
	};

	public SpecialDisciplineRetreatPrinciple(String[] specialDisciplines, Filter filter) {
		super(filter);
		this.specialDisciplines = specialDisciplines;
	}

	public SpecialDisciplineRetreatPrinciple(String[] specialDisciplines) {
		super();
		this.specialDisciplines = specialDisciplines;
	}

	public SpecialDisciplineRetreatPrinciple(Filter filter) {
		super(filter);
	}

	public SpecialDisciplineRetreatPrinciple() {
		super();
	}

	@Override
	public boolean pass(Subject subject, Reviewer reviewer) {
		String[] metaDisciplineOfSubject = (String[]) subject.getIntrinsicProperty(META_DISCIPLINE);
		String[] metaDisciplineOfReviewer = (String[]) reviewer.getIntrinsicProperty(META_DISCIPLINE);
		
		/*
		 * 若存在[项目的某一个学科]和[专家的某一个学科]
		 * 能够在:
		 * 1. 特殊学科条件下通过不同时退避达到匹配；或者
		 * 2. 非特殊学科条件下一级学科相同
		 * 则认为未突破该限制条件
		 */
		for (String subjectDisc : metaDisciplineOfSubject) {
			for (String reviewerDisc : metaDisciplineOfReviewer) {
				/*
				 * 若一级学科都不一样，一定不匹配
				 */
				if (!reviewerDisc.startsWith(subjectDisc.substring(0, 3))) {
					continue;
				}
				
				if (isSpecial(subjectDisc) || isSpecial(reviewerDisc)) {
					/*
					 * 如果两者学科至少有一个是特殊学科，则要求不能同时退避
					 * 即：必须有一者是另一者的前缀
					 */
					if (subjectDisc.startsWith(reviewerDisc) || reviewerDisc.startsWith(subjectDisc)) {
						return true;
					}
				} else {
					/*
					 * 若均非特殊学科，一级学科相同，则未突破限制条件
					 */
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean broken(Subject subject) {
		Set<Reviewer> selectedReviewers = (Set<Reviewer>) subject.getAlterableProperty(SELECTED_REVIEWERS);
		for (Reviewer reviewer : selectedReviewers) {
			/*
			 * 若项目至少和它的一个评审专家之间突破了该条件，则表示该项目突破了该评审条件
			 */
			if (!pass(subject, reviewer)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String breakWarnInfo() {
		return "突破特殊学科的退避原则";
	}

	/**
	 * 以某个特殊学科代码为前缀，则为特殊
	 * 
	 * @param discipline 待判断学科
	 * @return
	 */
	private boolean isSpecial(String discipline) {
		for (String specialDiscipline : specialDisciplines) {
			if (discipline.startsWith(specialDiscipline)) {
				return true;
			}
		}
		return false;
	}

}
