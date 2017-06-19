package csdc.tool.matcher;

import static csdc.tool.matcher.MatcherInfo.EXISTING_MATCH_PAIRS;
import static csdc.tool.matcher.MatcherInfo.REVIEWERS;
import static csdc.tool.matcher.MatcherInfo.SUBJECTS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评审匹配器
 * @author xuhan
 *
 */
public abstract class ReviewMatcher {
	
	/**
	 * 匹配过程很费资源，同一时刻只允许同时跑一个匹配工作
	 * 该变量标记当前时刻是否有匹配工作在运行
	 */
	static private boolean isBusy;
	
	/**
	 * 待匹配的评审对象列表，每个对象为一个Map，以键值对的形式描述每个评审对象的属性。eg:<br />
	 * id : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx<br />
	 * name : 关于xxx的基于xxx的研究<br />
	 * universityCode : 10487<br />
	 * discipline: 110; 120
	 */
	protected List<Subject> subjects;

	/**
	 * 供选择的评审专家列表，每个专家为一个Map，以键值对的形式描述每个评审专家的属性。eg:<br />
	 * id : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx<br />
	 * name : 张三<br />
	 * universityCode : 10487<br />
	 * discipline: 110; 120
	 */
	protected List<Reviewer> reviewers;
	
	/**
	 * 已有匹配结果列表，每个对象为一个Map，以键值对的形式描述每个匹配结果的属性。eg:<br />
	 * 要求包含和subjects相关的所有现有匹配，且要求包含和reviewers相关的所有现有匹配
	 * subject  : (一个Map形式的subject对象)<br />
	 * reviewer : (一个Map形式的reviewer对象)<br />
	 * priority : (0-indexed) 专家所在序号
	 */
	protected List<MatchPair> existingMatchPairs;
	
	/**
	 * 新增匹配结果列表，每个对象为一个Map，以键值对的形式描述每个匹配结果的属性。eg:<br />
	 * subject  : (一个Map形式的subject对象)<br />
	 * reviewer : (一个Map形式的reviewer对象)<br />
	 * number: 2
	 */
	protected List<MatchPair> newMatchPairs = new ArrayList<MatchPair>();
	
	/**
	 * 用于存储匹配参数、中间数据。主要目的是将数据集中存放，方便子类和相关类获取
	 */
	protected Map data = new HashMap<String, Object>();
	
	/**
	 * 匹配限制等级：<br />
	 * 1：影响评审对象寻找专家的方式<br />
	 * 2：确定各个限制条件是否起作用<br />
	 * 初始时，该值为0，匹配过程中，若找不到专家，该值会逐渐增大，表示放宽限制条件
	 */
	protected Integer constraintLevel = 0;
	
	/**
	 * 限制等级上限，当限制级达到此值且未能再找到评审专家时，则该类的一个匹配流程结束
	 */
	protected Integer constraintLimit;


	
	/**
	 * 匹配主方法
	 */
	public void work() {
		if (isBusy) {
			System.err.println("Matcher is busy now.");
			return;
		}

		try {
			isBusy = true;
			match();//匹配，算法核心部分
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			isBusy = false;
		}
	}
	
	/**
	 * 多轮匹配，算法核心部分
	 */
	protected void match() {
		init();	//初始化
		while (constraintLevel <= constraintLimit) {
			System.out.println("constraintLevel : " + constraintLevel);
			boolean find = false;
			for (Subject subject : subjects) {
				Reviewer reviewer = findReviewer(subject);//给当前subject找reviewer
				if (null != reviewer) {
					newMatchPairs.add(buildMatch(subject, reviewer));//将返回的新匹配结果放入newMatchs
					find = true;
				}
			}
			if (!find) {//没找到满足条件的专家，constraintLevel增加，放宽限制条件
				++constraintLevel;
			}
		}
		finishingWork();//收尾工作
	}
	
	/**
	 * 子类初始化方法
	 */
	public abstract void init();

	/**
	 * 根据评审对象、限制级别、已有限制条件，找到一个满足条件的专家
	 * @param subject	评审对象
	 * @param constraintLevel	限制级别
	 * @return	满足条件的专家
	 */
	public abstract Reviewer findReviewer(Subject subject);

	/**
	 * 对一个专家和一个对象建立匹配连接时，需要对二者成员进行的更新
	 * @param subject	待匹配项目
	 * @param reviewer	待匹配专家
	 * @return 新匹配结果
	 */
	public abstract MatchPair buildMatch(Subject subject, Reviewer reviewer);
	
	/**
	 * 收尾工作
	 */
	public abstract void finishingWork();

	
	public void setConstraintLimit(Integer constraintLimit) {
		this.constraintLimit = constraintLimit;
	}
	public List<MatchPair> getNewMatchPairs() {
		return newMatchPairs;
	}
	public void setExistingMatchPairs(List<MatchPair> existingMatchPairs) {
		this.existingMatchPairs = existingMatchPairs;
		addData(EXISTING_MATCH_PAIRS, existingMatchPairs);
	}
	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
		addData(SUBJECTS, subjects);
	}
	public void setReviewers(List<Reviewer> reviewers) {
		this.reviewers = reviewers;
		addData(REVIEWERS, reviewers);
	}
	public void addData(String name, Object value) {
		data.put(name, value);
	}
	public Object getData(String name) {
		return data.get(name);
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public List<Reviewer> getReviewers() {
		return reviewers;
	}

	public List<MatchPair> getExistingMatchPairs() {
		return existingMatchPairs;
	}

}
