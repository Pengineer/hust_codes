package csdc.tool.matcher.constraint;

import csdc.tool.matcher.Reviewer;
import csdc.tool.matcher.Subject;
import static csdc.tool.matcher.MatcherInfo.*;

/**
 * 一个项目的评审专家中，部属高校和地方高校的比例<br>
 * 一个专家内有inMinistryUniversity成员表示其为部属高校专家，请在初始化时设置好
 *
 * @author xuhan
 */
public class UniversityMinistryLocalRatio extends Constraint {
	
	/**
	 * 一个项目的评审专家中，部属高校专家数
	 */
	private Integer ministryUniversityNumber;

	/**
	 * 一个项目的评审专家中，地方高校专家数
	 */
	private Integer localUniversityNumber;


	public UniversityMinistryLocalRatio(Integer ministryUniversityNumber, Integer localUniversityNumber, Filter filter) {
		super(filter);
		this.ministryUniversityNumber = ministryUniversityNumber;
		this.localUniversityNumber = localUniversityNumber;
	}

	public UniversityMinistryLocalRatio(Integer ministryUniversityNumber, Integer localUniversityNumber) {
		super();
		this.ministryUniversityNumber = ministryUniversityNumber;
		this.localUniversityNumber = localUniversityNumber;
	}

	@Override
	public boolean pass(Subject subject, Reviewer reviewer) {
		boolean inMinistryUniversity = (null != reviewer.getIntrinsicProperty(IN_MINISTRY_UNIVERSITY)) ? true : false;//判断此专家是否是部属高校专家
		Integer ministryUnivCnt = (Integer) subject.getAlterableProperty(MINISTRY_UNIVERSITY_CNT);//部属高校数量
		Integer localUnivCnt = (Integer) subject.getAlterableProperty(LOCAL_UNIVERSITY_CNT);//地方高校数量
		if (ministryUnivCnt == null) ministryUnivCnt = 0;
		if (localUnivCnt == null) localUnivCnt = 0;
		
		/*
		 * 当reviewer为部属高校专家时，只有该项目还未选满 ministryUniversityNumber个部属高校专家时，才不突破条件
		 */
		if  (inMinistryUniversity && ministryUnivCnt >= ministryUniversityNumber) {
			return false;
		}
		
		/*
		 * 当reviewer为地方高校专家时，只有该项目还未选满 localUniversityNumber个地方高校专家时，才不突破条件。
		 */
		if (!inMinistryUniversity && localUnivCnt >= localUniversityNumber) {
			return false;
		}
		
		return true;
	}

	@Override
	public String breakWarnInfo() {
		return "突破评审专家中部属地方" + ministryUniversityNumber + ":" + localUniversityNumber + "限制";
	}

	@Override
	public boolean broken(Subject subject) {
		Integer ministryUnivCnt = (Integer) subject.getAlterableProperty(MINISTRY_UNIVERSITY_CNT);
		Integer localUnivCnt = (Integer) subject.getAlterableProperty(LOCAL_UNIVERSITY_CNT);
		if (ministryUnivCnt == null) {
			ministryUnivCnt = 0;
		}
		if (localUnivCnt == null) {
			localUnivCnt = 0;
		}
		/*
		 * 无论部属还是地方，只有超过了额定数量才算突破，小于额定数量不算突破
		 */
		return (ministryUnivCnt > ministryUniversityNumber || localUnivCnt > localUniversityNumber);
	}

}
