package csdc.tool.matcher;


/**
 *	匹配器中所用的常量 
 *
 */
public class MatcherInfo {
	
	/*
	 * 公共部分
	 */
	
	/**
	 * subject或reviewer的id
	 */
	public static final String ID = "id";
	
	/**
	 * subject或reviewer所属的高校代码
	 */
	public static final String UNIVERSITY_CODE = "universityCode";
	
	/**
	 * subject或reviewer的学科代码
	 */
	public static final String DISCIPLINE = "discipline";
	
	/**
	 * subject或reviewer的原始学科代码数组
	 * @see MatcherBeanTransformer#splitDiscipline
	 */
	public static final String META_DISCIPLINE = "metaDiscipline";
	
	/*
	 * reviewer部分
	 */

	/**
	 * reviewer的姓名
	 */
	public static final String NAME = "name";
	
	/**
	 * reviewer的评价等级
	 */
	public static final String RATING = "rating";
	
	/**
	 * reviewer的专业职称
	 */
	public static final String SPECIALITYTITLE = "specialityTitle";
	
	/**
	 * reviewer的奖励
	 */
	public static final String IS_AWARDED = "isAwarded";
	
	/**
	 * reviewer是否参评往年项目
	 */
	public static final String REVIEWYEAR = "reviewYear";
	
	/**
	 * reviewer所属高校为部属高校
	 */
	public static final String IN_MINISTRY_UNIVERSITY = "inMinistryUniversity";
	
	/**
	 * reviewer已有的subject数量
	 */
	public static final String BURDEN = "burden";
	
	/**
	 * reviewer所在高校的评价等级
	 */
	public static final String UNIVERSITYRATING = "universityRating";
	
	/**
	 * reviewer的职称对应的等级（例：教授对应正高级；specialityTitleRating = 6）
	 */
	public static final String SPECIALITYTITLE_RATING = "specialityTitleRating";
	/**
	 * reviewer的其他参评项目数
	 */
	public static final String OTHERBURDEN = "otherBurden";
	/**
	 * reviewer的聚类
	 */
	public static final String GROUPS = "groups";
	
	/*
	 * subject部分
	 */

	/**
	 * subject的年度
	 */
	public static final String YEAR = "year";
	
	/**
	 * subject的成员姓名
	 */
	public static final String SUBJECT_MEMBER_NAME = "subjectMemberName";
	
	/**
	 * subject的成员所属高校
	 */
	public static final String SUBJECT_MEMBER_UNIVERSITY = "subjectMemberUniversity";
	
	/**
	 * 当前subject需要的专家数量
	 */
	public static final String REVIEWER_NEED_NUMBER = "reviewerNeedNumber";
	
	/**
	 * 上次失败的限制级
	 */
	public static final String LAST_FAIL_LEVEL = "lastFailLevel";
	
	/**
	 * subject已选择的reviewer集合
	 */
	public static final String SELECTED_REVIEWERS = "selectedReviewers";
	
	/**
	 * subject已选择的reviewer所属高校集合集合
	 */
	public static final String SELECTED_REVIEWER_UNIVERSITIES = "selectedReviewerUniversities";
	
	/**
	 * 当前subject轮到按其第几个学科选取reviewer
	 */
	public static final String DISCIPLINE_INDEX = "disciplineIndex";
	
	/**
	 * 当前subject已选专家的部属高校专家数
	 */
	public static final String MINISTRY_UNIVERSITY_CNT = "ministryUniversityCnt";
	
	/**
	 * 当前subject已选专家的地方高校专家数
	 */
	public static final String LOCAL_UNIVERSITY_CNT = "localUniversityCnt";
	
	/**
	 * 警告信息
	 */
	public static final String WARNINGS = "warnings";
	
	// data部分
	/**
	 * 待匹配对象
	 */
	public static final String SUBJECTS = "subjects";
	
	/**
	 * 参评对象（专家）
	 */
	public static final String REVIEWERS = "reviewers";
	
	/**
	 * 已存在的匹配对
	 */
	public static final String EXISTING_MATCH_PAIRS = "existingMatchPairs";
	
	/**
	 * 每个专家的最多参评的subject数
	 */
	public static final String REVIEWER_SUBJECT_MAX = "reviewerSubjectMax";
	
	/**
	 * 每个专家的至少参评的subject数
	 */
	public static final String REVIEWER_SUBJECT_MIN = "reviewerSubjectMin";
	
	/**
	 * 每个subject所需部属高校评审专家数
	 */
	public static final String MINISTRY_UNIVERSITY_NUMBER = "ministryUniversityNumber";
	
	/**
	 * 每个subject所需地方高校评审专家数
	 */
	public static final String LOCAL_UNIVERSITY_NUMBER = "localUniversityNumber";
	
	/**
	 * subject所需的reviewer总数
	 */
	public static final String SUBJECT_REVIEWER_NUMBER = "subjectReviewerNumber";
	
	/**
	 * 各个高校的已匹配专家数
	 */
	public static final String UNIVERSITY_MATCHED_REVIEWERS = "universityMatchedReviewers";
	
	/**
	 * 各个高校的参评专家数（包括尚未匹配的）
	 */
	public static final String UNIVERSITY_REVIEWER_NUMBER = "universityReviewerNumber";
	
	/**
	 * 部属高校代码
	 */
	public static final String MINISTRY_UNIVERSITY_CODE = "ministryUniversityCode";
	
}
