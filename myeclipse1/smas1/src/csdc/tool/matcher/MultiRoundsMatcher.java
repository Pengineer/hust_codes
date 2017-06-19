package csdc.tool.matcher;

import static csdc.tool.matcher.MatcherInfo.BURDEN;
import static csdc.tool.matcher.MatcherInfo.UNIVERSITY_CODE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import csdc.tool.matcher.constraint.Constraint;
import csdc.tool.matcher.constraint.Filter;
import csdc.tool.matcher.constraint.ForbidAll;
import csdc.tool.matcher.constraint.ForbidUnusedReviewers;
import csdc.tool.matcher.constraint.ForbidUnusedUnivesity;
import csdc.tool.matcher.constraint.ReviewerBurdenMax;
import csdc.tool.matcher.constraint.ReviewersDiffer;
import csdc.tool.matcher.constraint.ReviewersUniversityDiffer;
import csdc.tool.matcher.constraint.SpecialDisciplineRetreatPrinciple;
import csdc.tool.matcher.constraint.SubjectMemberReviewerNameDiffer;
import csdc.tool.matcher.constraint.SubjectMemberReviewerUniversityDiffer;
import csdc.tool.matcher.constraint.SubjectReviewerUniversityDiffer;
import csdc.tool.matcher.constraint.UniversityMinistryLocalRatio;
import csdc.tool.matcher.constraint.UniversityReviewersMax;

/**
 * 多轮匹配器<br>
 * 其存在性完全因为以下两个需求:<br>
 * 1. 若某专家参评，建议其至少参评reviewerSubjectMin项<br>
 * 2. 若某高校有专家参评，建议其至少有universityReviewerMin个专家参评
 * 
 * @author xuhan
 */
public class MultiRoundsMatcher {
	
	/**
	 * 评审匹配器
	 */
	private DisciplineBasedMatcher matcher;
	
	/**
	 * 匹配器用项目列表
	 */
	private List<Subject> subjects;
	
	/**
	 * 匹配器用专家列表
	 */
	private List<Reviewer> reviewers;
	
	/**
	 * 匹配器用已有匹配列表
	 */
	private List<MatchPair> existingMatchPairs;
	
	/**
	 * 每位专家若参评，至少需参评的项目数
	 */
	private Integer reviewerSubjectMin;
	
	/**
	 * 每位专家评审的最大项目数
	 */
	private Integer reviewerSubjectMax;
	
	/**
	 * 每个项目所需部属高校评审专家数
	 */
	private Integer subjectMinistryReviewerNumber;
	
	/**
	 * 每个项目所需地方高校评审专家数
	 */
	private Integer subjectLocalReviewerNumber;
	
	/**
	 * 每个项目所需的评审专家数
	 */
	private Integer subjectReviewerNumber;
	
	/**
	 * 每个高校至少应选的专家数
	 */
	private Integer universityReviewerMin;
	
	/**
	 * 每个高校至多应选的专家数
	 */
	private Integer universityReviewerMax;
	
	/**
	 * 匹配的项目类型，根据不同项目类型，对匹配过程进行差异化处理<br>
	 * 提高匹配效果
	 * 对基地项目则在第七轮匹配，放松限制处理
	 * 项目：general, instp
	 * 奖励：moeSocial
	 */
	private String type;
	
	/**
	 * 两轮匹配器的构造方法
	 * @param subjects	匹配器用待匹配对象列表
	 * @param reviewers	匹配器用参评对象列表
	 * @param existingMatchPairs	匹配器用已有匹配列表
	 * @param ministryUniversityCode	部属院校Set
	 * @param reviewerSubjectMin	每位参评对象，至少需参评的项目数
	 * @param reviewerSubjectMax	每位参评对象，最大需参评的项目数
	 * @param subjectMinistryReviewerNumber	每个项目所需部属高校评审专家数
	 * @param subjectLocalReviewerNumber	每个项目所需地方高校评审专家数
	 * @param subjectReviewerNumber	每个项目所需总的评审专家数
	 */
	public MultiRoundsMatcher(
			String type,
			List<Subject> subjects,
			List<Reviewer> reviewers,
			List<MatchPair> existingMatchPairs,
			Integer reviewerSubjectMin,
			Integer reviewerSubjectMax,
			Integer subjectMinistryReviewerNumber,
			Integer subjectLocalReviewerNumber,
			Integer subjectReviewerNumber,
			Integer universityReviewerMin,
			Integer universityReviewerMax) {
		this.type = type;
		this.subjects = subjects;
		this.reviewers = reviewers;
		this.existingMatchPairs = existingMatchPairs;
		this.reviewerSubjectMin = reviewerSubjectMin;
		this.reviewerSubjectMax = reviewerSubjectMax;
		this.subjectMinistryReviewerNumber = subjectMinistryReviewerNumber;
		this.subjectLocalReviewerNumber = subjectLocalReviewerNumber;
		this.subjectReviewerNumber = subjectReviewerNumber;
		this.universityReviewerMin = universityReviewerMin;
		this.universityReviewerMax = universityReviewerMax;
	}
	
	/**
	 * 多轮匹配核心步骤，当前算法使用八轮
	 * @return	匹配结果列表（List）
	 * @throws Exception
	 */
	public List<MatchPair> match() throws Exception {
		//最终匹配结果
		List<MatchPair> finalMatchPairs = new ArrayList<MatchPair>();
		
		//一轮的匹配结果
		List<MatchPair> roundMatchPairs = new ArrayList<MatchPair>();
		
		
		System.out.println("第一轮");
		roundMatchPairs = removeBadPairs(matchOneRound(
			11,
			new Selector() {
				public Integer findGroupStatus(Integer constraintLevel) {
					return constraintLevel / 2 % 3;
				}
			},
			new UniversityMinistryLocalRatio(subjectMinistryReviewerNumber, subjectLocalReviewerNumber, new Filter() {
				public boolean shouldWork(Integer constraintLevel) {
					return constraintLevel % 2 == 0;
				}
			}),
			new ReviewersUniversityDiffer(new Filter() {
				public boolean shouldWork(Integer constraintLevel) {
					return constraintLevel / 6 == 0;
				}
			}),
			new SpecialDisciplineRetreatPrinciple(),
			new ReviewerBurdenMax(reviewerSubjectMax),
			new UniversityReviewersMax(universityReviewerMax),
			new SubjectReviewerUniversityDiffer(),
			new ReviewersDiffer(),
			new SubjectMemberReviewerNameDiffer(),
			new SubjectMemberReviewerUniversityDiffer()
		));
		existingMatchPairs.addAll(roundMatchPairs);
		finalMatchPairs.addAll(roundMatchPairs);
			
		System.out.println("第二轮");
		roundMatchPairs = removeBadPairs(matchOneRound(
			11,
			new Selector() {
				public Integer findGroupStatus(Integer constraintLevel) {
					return constraintLevel / 2 % 3;
				}
			},
			new UniversityMinistryLocalRatio(subjectMinistryReviewerNumber, subjectLocalReviewerNumber, new Filter() {
				public boolean shouldWork(Integer constraintLevel) {
					return constraintLevel % 2 == 0;
				}
			}),
			new ReviewersUniversityDiffer(new Filter() {
				public boolean shouldWork(Integer constraintLevel) {
					return constraintLevel / 6 == 0;
				}
			}),
			new SpecialDisciplineRetreatPrinciple(),
			new ReviewerBurdenMax(reviewerSubjectMax),
			new UniversityReviewersMax(universityReviewerMax),
			new SubjectReviewerUniversityDiffer(),
			new ReviewersDiffer(),
			new SubjectMemberReviewerNameDiffer(),
			new SubjectMemberReviewerUniversityDiffer()
		));
		existingMatchPairs.addAll(roundMatchPairs);
		finalMatchPairs.addAll(roundMatchPairs);
		
		System.out.println("第三轮");
		roundMatchPairs = removeBadPairs(matchOneRound(
			5,
			new Selector() {
				public Integer findGroupStatus(Integer constraintLevel) {
					return constraintLevel % 3;
				}
			},
			new ReviewersUniversityDiffer(new Filter() {
				public boolean shouldWork(Integer constraintLevel) {
					return constraintLevel / 3 == 0;//反映出限制条件的随着限制级别，的渐进变化
				}
			}),
			new SpecialDisciplineRetreatPrinciple(),
			new ReviewerBurdenMax(reviewerSubjectMax),
			new UniversityReviewersMax(universityReviewerMax),
			new SubjectReviewerUniversityDiffer(),
			new ReviewersDiffer(),
			new SubjectMemberReviewerNameDiffer(),
			new SubjectMemberReviewerUniversityDiffer()
		));
		existingMatchPairs.addAll(roundMatchPairs);
		finalMatchPairs.addAll(roundMatchPairs);
		
		System.out.println("第四轮");
		roundMatchPairs = removeBadPairs(matchOneRound(
			1,
			new Selector() {//学科退避约束不发生变化
				public Integer findGroupStatus(Integer constraintLevel) {
					return 2;
				}
			},
			new ReviewersUniversityDiffer(new Filter() {
				public boolean shouldWork(Integer constraintLevel) {
					return constraintLevel == 0;
				}
			}),
			new SpecialDisciplineRetreatPrinciple(),
			new ReviewerBurdenMax(reviewerSubjectMax),
			new UniversityReviewersMax(universityReviewerMax),
			new SubjectReviewerUniversityDiffer(),
			new ReviewersDiffer(),
			new SubjectMemberReviewerNameDiffer(),
			new SubjectMemberReviewerUniversityDiffer()
		));
		existingMatchPairs.addAll(roundMatchPairs);
		finalMatchPairs.addAll(roundMatchPairs);
		
		System.out.println("第五轮");
		roundMatchPairs = removeBadPairs(matchOneRound(
			0,
			new Selector() {
				public Integer findGroupStatus(Integer constraintLevel) {
					return 2;
				}
			},
			new SpecialDisciplineRetreatPrinciple(),
			new ReviewerBurdenMax(reviewerSubjectMax),
			new UniversityReviewersMax(universityReviewerMax),
			new SubjectReviewerUniversityDiffer(),
			new ReviewersDiffer(),
			new SubjectMemberReviewerNameDiffer(),
			new SubjectMemberReviewerUniversityDiffer()
		));
		existingMatchPairs.addAll(roundMatchPairs);
		finalMatchPairs.addAll(roundMatchPairs);
		
		System.out.println("第六轮");
		roundMatchPairs = removeBadPairs(matchOneRound(
			0,
			new Selector() {
				public Integer findGroupStatus(Integer constraintLevel) {
					return 2;
				}
			},
			new ReviewerBurdenMax(reviewerSubjectMax),
			new UniversityReviewersMax(universityReviewerMax),
			new SubjectReviewerUniversityDiffer(),
			new ReviewersDiffer(),
			new SubjectMemberReviewerNameDiffer(),
			new SubjectMemberReviewerUniversityDiffer()
		));
		existingMatchPairs.addAll(roundMatchPairs);
		finalMatchPairs.addAll(roundMatchPairs);
		
		System.out.println("第七轮");
		roundMatchPairs = removeBadPairs(matchOneRound(
			0,
			new Selector() {
				public Integer findGroupStatus(Integer constraintLevel) {
					return 2;
				}
			},
			new ForbidUnusedUnivesity(),
			new UniversityReviewersMax(universityReviewerMax),
			new SubjectReviewerUniversityDiffer(),
			new ReviewersDiffer(),
			new SubjectMemberReviewerNameDiffer(),
			new SubjectMemberReviewerUniversityDiffer()
		));
		
//		//修改第七轮匹配，根据项目类型可进行选择性降低限制
//		roundMatchPairs = matchOneRound(
//				0,
//				new Selector() {
//					public Integer findGroupStatus(Integer constraintLevel) {
//						return 2;
//					}
//				},
//				new ForbidUnusedUnivesity(),
//				new SubjectReviewerUniversityDiffer(),
//				new ReviewersDiffer(),
//				new SubjectMemberReviewerNameDiffer(),
//				new SubjectMemberReviewerUniversityDiffer()
//			);
//		if (projectType.equals("general")) {//过滤处理，放松对instpl类型匹配限制
//			//剔除匹配
//			roundMatchPairs = removeBadPairs(roundMatchPairs);
//		}//对基地项目不剔除
		
		
		existingMatchPairs.addAll(roundMatchPairs);
		finalMatchPairs.addAll(roundMatchPairs);

		System.out.println("第八轮");
		roundMatchPairs = matchOneRound(
			0,
			new Selector() {
				public Integer findGroupStatus(Integer constraintLevel) {
					return 2;
				}
			},
			new ForbidUnusedReviewers(),
			new UniversityReviewersMax(universityReviewerMax),
			new SubjectReviewerUniversityDiffer(),
			new ReviewersDiffer(),
			new SubjectMemberReviewerNameDiffer(),
			new SubjectMemberReviewerUniversityDiffer()
		);
		existingMatchPairs.addAll(roundMatchPairs);
		finalMatchPairs.addAll(roundMatchPairs);

		/*
		 * 第九轮不会添加任何新匹配，只用作更新警告信息
		 */
		System.out.println("第九轮");
		matchOneRound(
			0,
			new Selector() {
				public Integer findGroupStatus(Integer constraintLevel) {
					return 2;
				}
			},
			new ForbidAll(),
			new ReviewersDiffer(),
			new ReviewersUniversityDiffer(),
			new SpecialDisciplineRetreatPrinciple(),
			new SubjectMemberReviewerNameDiffer(),
			new SubjectMemberReviewerUniversityDiffer(),
			new SubjectReviewerUniversityDiffer(),
			new UniversityMinistryLocalRatio(subjectMinistryReviewerNumber, subjectLocalReviewerNumber)
		);

		return finalMatchPairs;
	}
	
	/**
	 * 去除如下两种专家的匹配对：<br>
	 * 1. 有评审条目且不足20（reviewerSubjectMin=20）项<br>
	 * 2. 所在高校的参评专家数大于0且小于5
	 */
	private List<MatchPair> removeBadPairs(List<MatchPair> matchPairs) {
		List<MatchPair> resultMatchPairs = new ArrayList<MatchPair>();
		HashMap<String, HashSet<Reviewer>> univMatchedReviewers = (HashMap<String, HashSet<Reviewer>>) matcher.getUniversityMatchedReviewers();
		
		List<MatchPair> tmpMatchPairs = new ArrayList<MatchPair>();//去掉1后的匹配对
		for (MatchPair matchPair : matchPairs) {
			Reviewer reviewer = matchPair.getReviewer();
			Integer burden = (Integer) reviewer.getAlterableProperty(BURDEN);
			//1. 有评审条目且不足20（reviewerSubjectMin=20）项
			if (null != burden && burden > 0 && burden < reviewerSubjectMin) {
				reviewer.setAlterableProperty(BURDEN, --burden);
				if (burden == 0) {
					String universityCode = (String) reviewer.getIntrinsicProperty(UNIVERSITY_CODE);
					univMatchedReviewers.get(universityCode).remove(reviewer);
				}
				continue;
			}
			tmpMatchPairs.add(matchPair);//仅保留不符合条件1的专家的匹配对
		}
		for (MatchPair matchPair : tmpMatchPairs) {
			//2. 所在高校的参评专家数大于0且小于5（universityReviewerMin=5）
			String universityCode = (String) matchPair.getReviewer().getIntrinsicProperty(UNIVERSITY_CODE);
			Set matchedReviewers = univMatchedReviewers.get(universityCode);
			if (null != matchedReviewers && matchedReviewers.size() > 0 && matchedReviewers.size() < universityReviewerMin) {
				continue;
			}
		    //每个学校匹配专家数上限60，放在UniversityReviewersMax限制器中处理
			resultMatchPairs.add(matchPair);//仅保留不符合条件2的专家的匹配对
		}
		return resultMatchPairs;
	}
	
	/**
	 * 使用指定的限制级上限(inclusive)、学科退避选择器、限制级列表，进行一轮专家匹配，并返回新增的匹配结果
	 * @param constraintLimit 限制级上限(inclusive)
	 * @param selector 学科退避选择器
	 * @param constraints 限制级列表
	 * @return 新增的匹配结果
	 */
	private List<MatchPair> matchOneRound(Integer constraintLimit, Selector selector, Constraint... constraints) {
		//实例化匹配器
		matcher = new DisciplineBasedMatcher(selector, reviewerSubjectMax, reviewerSubjectMin, subjectReviewerNumber);
		matcher.setType(type);//添加类型属性
		//设置数据（项目，专家，已有匹配）
		matcher.setSubjects(subjects);
		matcher.setReviewers(reviewers);
		matcher.setExistingMatchPairs(existingMatchPairs);

		//设置限制级上限 
		matcher.setConstraintLimit(constraintLimit);
		
		//设置限制条件
		for (Constraint constraint : constraints) {
			matcher.addConstraint(constraint);
		}
		//可以设置学校评审专家上限约束，依赖本次匹配器。
		matcher.work();
		return matcher.getNewMatchPairs();
	}	
}
