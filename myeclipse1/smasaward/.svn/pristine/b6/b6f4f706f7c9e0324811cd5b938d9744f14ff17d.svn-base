package csdc.tool.matcher;

import static csdc.tool.matcher.MatcherInfo.BURDEN;
import static csdc.tool.matcher.MatcherInfo.DISCIPLINE_INDEX;
import static csdc.tool.matcher.MatcherInfo.REVIEWYEAR;
import static csdc.tool.matcher.MatcherInfo.GROUPS;
import static csdc.tool.matcher.MatcherInfo.ID;
import static csdc.tool.matcher.MatcherInfo.IN_MINISTRY_UNIVERSITY;
import static csdc.tool.matcher.MatcherInfo.IS_AWARDED;
import static csdc.tool.matcher.MatcherInfo.LAST_FAIL_LEVEL;
import static csdc.tool.matcher.MatcherInfo.LOCAL_UNIVERSITY_CNT;
import static csdc.tool.matcher.MatcherInfo.META_DISCIPLINE;
import static csdc.tool.matcher.MatcherInfo.MINISTRY_UNIVERSITY_CNT;
import static csdc.tool.matcher.MatcherInfo.RATING;
import static csdc.tool.matcher.MatcherInfo.REVIEWER_NEED_NUMBER;
import static csdc.tool.matcher.MatcherInfo.SELECTED_REVIEWERS;
import static csdc.tool.matcher.MatcherInfo.SELECTED_REVIEWER_UNIVERSITIES;
import static csdc.tool.matcher.MatcherInfo.SPECIALITYTITLE_RATING;
import static csdc.tool.matcher.MatcherInfo.UNIVERSITYRATING;
import static csdc.tool.matcher.MatcherInfo.UNIVERSITY_CODE;
import static csdc.tool.matcher.MatcherInfo.UNIVERSITY_REVIEWER_NUMBER;
import static csdc.tool.matcher.MatcherInfo.WARNINGS;
import static csdc.tool.matcher.MatcherInfo.OTHERBURDEN;
import static csdc.tool.matcher.MatcherInfo.SPECIALITYTITLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import csdc.tool.matcher.constraint.Constraint;

/**
 * 基于学科的匹配器
 * Note: groupStatus: 0需按原学科匹配	1降低一级学科匹配	2降低两级学科匹配
 * @author xuhan
 *
 */
public class DisciplineBasedMatcher extends ReviewMatcher {

	/**
	 * 基于学科的匹配器 的 构造器
	 * @param selector 根据constraintLevel决定选定分组的模式，返回0表示按原学科，返回1表示降一级学科，返回2表示降两级学科
	 * @param reviewerSubjectMax 每个专家的建议参评数上限
	 * @param reviewerSubjectMin 每个专家的建议参评数下限（若参评数非零）
	 * @param subjectReviewerNumber 每个项目需要的评审专家数
	 */
	public DisciplineBasedMatcher(
			Selector selector,
			Integer reviewerSubjectMax,
			Integer reviewerSubjectMin,
			Integer subjectReviewerNumber) {
		this.selector = selector;
		this.reviewerSubjectMax = reviewerSubjectMax;
		this.reviewerSubjectMin = reviewerSubjectMin;
		this.subjectReviewerNumber = subjectReviewerNumber;
	}

	/**
	 * 依据constraintLevel的值决定选择专家时的学科退避规则，<br>
	 * findGroupStatus(constraintLevel)返回0时不退避学科，返回1时退避一级学科，返回2时退避二级学科
	 */
	private Selector selector;

	/**
	 * 每个专家的最多参评项目数
	 */
	private Integer reviewerSubjectMax;

	/**
	 * 每个专家如果参评，则建议至少参评的项目数<br>
	 * （一个专家评审项目太少不利于任务分发、薪酬分发）
	 */
	private Integer reviewerSubjectMin;

	/**
	 * 每个项目需要的评审专家数
	 */
	private Integer subjectReviewerNumber;
	
	/**
	 * 评审专家组(组的学科编号 -> 组内专家集合)<br>
	 * “分组”指将专家按照所属学科进行分组，然后用该值存储学科代码到专家分组的映射<br>
	 * 一个专家可以属于多个组
	 */
	private HashMap<String, TreeSet<Reviewer>> reviewerGroups = new HashMap<String, TreeSet<Reviewer>>();
	
	/**
	 * 高校代码 -> 该高校参评专家集合
	 */
	private HashMap<String, HashSet<Reviewer>> universityMatchedReviewers = new HashMap<String, HashSet<Reviewer>>();
	
	/**
	 * 限制条件列表
	 */
	private List<Constraint> constraints = new ArrayList<Constraint>();
	
	
	/**
	 * 各个高校代码对应的专家匹配对个数
	 */
	private Map<String, Integer> university2MatchNumMap;
	
	/**
	 * 各个高校代码对应的已选评审专家
	 */
	private Map<String, Set<Reviewer>> university2SelectExpertNumMap;
	
	@Override
	public void init() {
		countUniversityReviewerNumber();//初始化各个高校的参评专家数目
		clearAlterableProperties();		//清空可变属性Map
		processExistingMathches();		//处理已有匹配结果
		groupReviewers();				//将专家按学科分组
		sortSubjectByDiscipline();		//将项目按照可用的专家数升序排序
	}

	/**
	 * 初始化各个高校的参评专家数目，放在Map<String, Integer> univReviewerNumber内
	 */
	private void countUniversityReviewerNumber(){
		university2MatchNumMap = new HashMap<String, Integer>();
		university2SelectExpertNumMap = new HashMap<String, Set<Reviewer>>();
		Set<Reviewer> avaliableReviewersReviewer = new HashSet<Reviewer>(reviewers);
		//遍历已有的匹配结构集
		for (MatchPair existingMatchPair : existingMatchPairs) {
			Reviewer reviewer = (Reviewer) existingMatchPair.getReviewer();
			avaliableReviewersReviewer.add(reviewer);
			String universityCode = (String) reviewer.getIntrinsicProperty(UNIVERSITY_CODE);//获取该专家所属高校代码
			Integer cnt = university2MatchNumMap.get(universityCode);//获取当前高校已有专家计数
			university2MatchNumMap.put(universityCode, (cnt == null ? 0 : cnt) + 1);//当前高校专家计数加 1，存入以该高校代码为Key的Map中
			Set<Reviewer> reviewerSet = university2SelectExpertNumMap.get(universityCode);
			if (reviewerSet == null) {
				reviewerSet = new HashSet<Reviewer>();
			}
			reviewerSet.add(reviewer);
			university2SelectExpertNumMap.put(universityCode, reviewerSet);
		}
		Map<String, Integer> univReviewerNumber = new HashMap<String, Integer>();
		for (Reviewer reviewer : avaliableReviewersReviewer) {
			String universityCode = (String) reviewer.getIntrinsicProperty(UNIVERSITY_CODE);//获取该专家所属高校代码
			Integer cnt = univReviewerNumber.get(universityCode);//获取当前高校已有专家计数
			univReviewerNumber.put(universityCode, (cnt == null ? 0 : cnt) + 1);//当前高校专家计数加 1，存入以该高校代码为Key的Map中
		}
		this.addData(UNIVERSITY_REVIEWER_NUMBER, univReviewerNumber);//更新data中各个高校参评专家数
	}
	
	/**
	 * 清空可变属性Map
	 */
	private void clearAlterableProperties() {
		for (Reviewer reviewer : reviewers) {
			reviewer.clearAlterableProperties();
		}
		
		for (Subject subject : subjects) {
			subject.clearAlterableProperties();
		}
	}
	
	/**
	 * 处理已有匹配结果<br>
	 * 将 项目 和 专家 中会随着匹配情况变化的属性按照existingMatchPairs设置好
	 */
	private void processExistingMathches() {
		if (existingMatchPairs == null) {
			throw new NullPointerException("No existing matches.");
		}
		for (MatchPair existingMatchPair : existingMatchPairs) {
			Subject subject = (Subject) existingMatchPair.getSubject();
			Reviewer reviewer = (Reviewer) existingMatchPair.getReviewer();
			buildMatch(subject, reviewer);
		}
	}
	
	/**
	 * 将评审专家按照belongGroups分组，放入reviewerGroups<br>
	 * 每个专家内部用GROUPS表示所属的分组编号(即学科代码)集合
	 */
	private void groupReviewers() {
		/*
		 * 组内专家顺序为：
		 * 1.其他评审数（专家参评其他评审对象的数量，按升序排序）
		 * 2.评审数不同，按如下先后顺序：
		 *     a.评审数非零,评审数不足reviewerSubjectMin(按评审数降序)
		 *     b.评审数非零,评审数大于等于reviewerSubjectMin且不足reviewerSubjectMax(按评审数升序)
		 *     c.评审数为零
		 *     d.评审数大于等于reviewerSubjectMax；
		 * 3.评价等级高的排在前（值越大，等级越高）
		 * 4.专家的职称等级（正高级>高级>副高级>中级>助理级>员级）
		 * 5.专家是否入选人才奖励计划（入选>非入选）
		 * 6.专家所在高校的评价等级（值越大，等级越高）
		 * 7.专家所在高校的使用情况（使用情况低的>使用情况高的）
		 * 8.专家是否参评往年项目（0：否；1：是），优先选择往年参评项目的专家
		 * 9.专家id字典序较小的排在前面
		 */
		Comparator<Reviewer> groupComparator = new Comparator<Reviewer>() {
			public int compare(Reviewer reviewer1, Reviewer reviewer2) {//返回值小于0，排在前面
				//其他评审数（专家参评其他评审对象的数量，按升序排序）
				//即，避开已经参评其他项目的专家
				Integer otherBurder1 = (Integer) reviewer1.getIntrinsicProperty(OTHERBURDEN);
				Integer otherBurder2 = (Integer) reviewer2.getIntrinsicProperty(OTHERBURDEN);
				if (otherBurder1 != otherBurder2) {
					return otherBurder1 < otherBurder2 ? -1 : 1;
				}
				
				Integer burden1 = (Integer) reviewer1.getAlterableProperty(BURDEN);
				Integer burden2 = (Integer) reviewer2.getAlterableProperty(BURDEN);
				/*
				 * 对专家评审数做一些预处理
				 */
				if (null == burden1 || burden1.equals(0)) {
					/*
					 * 如果评审数为零，则设成reviewerSubjectMax
					 */
					burden1 = reviewerSubjectMax;
				} else if (burden1 >= reviewerSubjectMax) {
					/*
					 * 如果评审数本来就大于等于reviewerSubjectMax，则自增1(以给原本为零的腾出位置)
					 */
					++burden1;
				}
				if (null == burden2 || burden2.equals(0)) {
					burden2 = reviewerSubjectMax;
				} else if (burden2 >= reviewerSubjectMax) {
					++burden2;
				}
				//若评审数不相等
				if (!burden1.equals(burden2)) {
					if (burden1 < reviewerSubjectMin && burden2 < reviewerSubjectMin){
						return burden1 > burden2 ? -1 : 1;
					} else {
						return burden1 < burden2 ? -1 : 1;
					}
				}
				
				//优先选评价等级高(rating较大)的专家
				Integer rating1 = (Integer) reviewer1.getIntrinsicProperty(RATING);
				Integer rating2 = (Integer) reviewer2.getIntrinsicProperty(RATING);
				if (!rating1.equals(rating2)) {
					return rating1 > rating2 ? -1 : 1;
				}
				
				//专家的职称等级（正高级>高级>副高级>中级>助理级>员级）
				if (reviewer1.getIntrinsicProperty(SPECIALITYTITLE) != null && reviewer2.getIntrinsicProperty(SPECIALITYTITLE) != null) {
					Integer specialityRating1 = (Integer)reviewer1.getIntrinsicProperty(SPECIALITYTITLE_RATING);
					Integer specialityRating2 = (Integer)reviewer2.getIntrinsicProperty(SPECIALITYTITLE_RATING);				
					if (specialityRating1 != null && specialityRating2 != null && !specialityRating1.equals(specialityRating2)) {
						return specialityRating1 > specialityRating2 ? -1 : 1;
					}
				}
				
				//专家是否入选人才奖励计划（入选>非入选）
				Integer expertAward1 = (Integer) reviewer1.getIntrinsicProperty(IS_AWARDED);
				Integer expertAward2 = (Integer) reviewer2.getIntrinsicProperty(IS_AWARDED);
				if (!expertAward1.equals(expertAward2)) {
					return expertAward1 > expertAward2 ? -1 : 1;
				}

				//专家所在高校的评价等级（值越大，等级越高）
				Integer universityRating1 = (Integer) reviewer1.getIntrinsicProperty(UNIVERSITYRATING);
				Integer universityRating2 = (Integer) reviewer1.getIntrinsicProperty(UNIVERSITYRATING);
				if (universityRating1 != null && universityRating2 != null && !universityRating1.equals(universityRating2)) {
					return universityRating1 > universityRating2 ? -1 : 1;
				}
				
				//专家所在高校的使用情况（使用情况低的>使用情况高的）
				String universityCode1 = (String) reviewer1.getIntrinsicProperty(UNIVERSITY_CODE);
				String universityCode2 = (String) reviewer2.getIntrinsicProperty(UNIVERSITY_CODE);
				if (!universityCode1.equals(universityCode2)) {
					int a = universityUseFrequency(universityCode1,universityCode2);
					if (a != 0) {
						return a;
					}
				}
							
				//优先选专家总数较多的高校的专家
//				HashMap<String, Integer> univReviewerNumber = (HashMap<String, Integer>) data.get(UNIVERSITY_REVIEWER_NUMBER);
//				if (univReviewerNumber != null) {
//					String univCode1 = (String) reviewer1.getIntrinsicProperty(UNIVERSITY_CODE);
//					String univCode2 = (String) reviewer2.getIntrinsicProperty(UNIVERSITY_CODE);
//					Integer univReviewerNumber1 = univReviewerNumber.containsKey(univCode1) ? univReviewerNumber.get(univCode1) : 0;
//					Integer univReviewerNumber2 = univReviewerNumber.containsKey(univCode2) ? univReviewerNumber.get(univCode2) : 0;
//					if (!univReviewerNumber1.equals(univReviewerNumber2)) {
//						return univReviewerNumber1 > univReviewerNumber2 ? -1 : 1;
//					}
//				}
				
				//专家是否参评往年项目（0：否；1：是），优先选择往年参评项目的专家
				Integer expertReviewYears1 = (Integer) reviewer1.getIntrinsicProperty(REVIEWYEAR);
				Integer expertReviewYears2 = (Integer) reviewer2.getIntrinsicProperty(REVIEWYEAR);
				if (!expertReviewYears1.equals(expertReviewYears2)) {
					return expertReviewYears1 > expertReviewYears2 ? -1 : 1;
				}
				
				//如果都相等，则优先选id字典序较小的专家
				String id1 = (String) reviewer1.getIntrinsicProperty(ID);
				String id2 = (String) reviewer2.getIntrinsicProperty(ID);
				return id1.compareTo(id2);
			}
		};
		
		for (Reviewer reviewer : reviewers) {
			/*
			 * 将专家所属分组编号放入reviewer.GROUPS
			 */
			String[] groups = belongGroups(reviewer);
			reviewer.setAlterableProperty(GROUPS, groups);
			
			/*
			 * 将专家放入其所属分组对应的专家集合
			 */
			for (String groupKey : groups) {
				TreeSet<Reviewer> reviewerSet = reviewerGroups.get(groupKey);
				if (reviewerSet == null) {
					reviewerSet = new TreeSet<Reviewer>(groupComparator);
					reviewerGroups.put(groupKey, reviewerSet);
				}
				reviewerSet.add(reviewer);
			}
		}
	}
	
	
	/**
	 * 查询高校对应的专家总数
	 * 计算高校对应的匹配对对数
	 * 通过计算（高校对应的匹配对对数/高校对应的专家总数）来计算高校的使用情况
	 */
	private int universityUseFrequency(String universityCode1, String universityCode2){
		int result;	
		Integer university2MatchNum1 = university2MatchNumMap.get(universityCode1);//获取当前高校对应的匹配对
		Integer university2MatchNum2 = university2MatchNumMap.get(universityCode2);
		Set<Reviewer> reviewers1 = university2SelectExpertNumMap.get(universityCode1);//获取当前高校对应的已有评审项目专家
		Set<Reviewer> reviewers2 = university2SelectExpertNumMap.get(universityCode2);
		Integer university2SelectNum1 = (reviewers1 == null) ? 0 : reviewers1.size();
		Integer university2SelectNum2 = (reviewers2 == null) ? 0 : reviewers2.size();
		if (university2MatchNum1 == null) {
			university2MatchNum1 = 0;
			university2MatchNumMap.put(universityCode1, 0);
		}
		if (university2MatchNum2 == null) {
			university2MatchNum2 = 0;
			university2MatchNumMap.put(universityCode2, 0);
		}
		double universityUseFrequency1 = 0.0;
		double universityUseFrequency2 = 0.0;
		if (university2SelectNum1 != 0) {
			universityUseFrequency1 = university2MatchNum1 * 1.0 / university2SelectNum1;
		}
		if (university2SelectNum2 != 0) {
			universityUseFrequency2 = university2MatchNum2 * 1.0 / university2SelectNum2;
		}
		if (universityUseFrequency1 > universityUseFrequency2) {
			result = 1;
		}else if(universityUseFrequency1 < universityUseFrequency2) {
			result = -1;
		}else{
			result = 0;
		}
		return result;
	}
	
	/**
	 * 将项目按照可用的专家数升序排序<br>
	 * 这是一个尝试性的优化方案，思路是：记能评审学科d的专家数为r(d)，项目p的学科集合为D(p)，<br>
	 * 则，对项目p，记"该项目可用的评审专家数" num(p) = sum(r(d) : (d in D(p)))。<br>
	 * 该方法做的事情就是将所有项目按num(p)升序排列，以便优先让num(p)小的项目挑选专家。<br>
	 * 该方案并不能经得起推敲，但实践表明使用该方案结果比不使用该方案略好一点（指项目更容易匹配全）。
	 */
	private void sortSubjectByDiscipline() {
		/**
		 * 每个学科有多少空余专家 的名额可用
		 */
		final HashMap<String, Integer> discCnt = new HashMap<String, Integer>();
		
		Comparator subjectComparator = new Comparator() {  
			public int compare(Object o1, Object o2) {//数量小的排前面
				Integer cnt1 = discCnt.get(o1);
				Integer cnt2 = discCnt.get(o2);
				return cnt1 != null && cnt2 != null && cnt1 < cnt2 ? -1 : 1;
			}
		};
		
		for (Reviewer reviewer : reviewers) {
			Integer burden = (Integer) reviewer.getAlterableProperty(BURDEN);
			burden = (null == burden) ? 0 : burden;
			Integer vacantJob = reviewerSubjectMax - burden;
			String[] metaDiscipline = (String[]) reviewer.getIntrinsicProperty(META_DISCIPLINE);
			for (String discipline : metaDiscipline) {
				Integer cnt = discCnt.containsKey(discipline) ? discCnt.get(discipline) + vacantJob : vacantJob;
				discCnt.put(discipline, cnt);//统计每个学科有多少空余专家 的名额可用
			}
		}
		
		for (Subject subject : subjects) {
			String[] metaDiscipline = (String[]) subject.getIntrinsicProperty(META_DISCIPLINE);

			int reviewerNeed = 0;
			for (String discipline : metaDiscipline) {
				reviewerNeed += discCnt.containsKey(discipline) ? discCnt.get(discipline) : 0;
			}
			subject.setAlterableProperty(REVIEWER_NEED_NUMBER, reviewerNeed);//该项目需要的专家数量

			Arrays.sort(metaDiscipline, subjectComparator);
		}
		
		Collections.sort(subjects, new Comparator() {//需要专家数少的项目排在前面  
			public int compare(Object o1, Object o2) {
				Integer number1 = (Integer) ((Subject)o1).getAlterableProperty(REVIEWER_NEED_NUMBER);
				Integer number2 = (Integer) ((Subject)o2).getAlterableProperty(REVIEWER_NEED_NUMBER);
				return number1 == null || number2 == null || number1 < number2 ? -1 : 1;
			}
		});
		
	}

	/**
	 * 获取专家从属的一二三级学科代码集合<br>
	 * 例如:某专家学科代码为: "123; 1234567; 77788"，则返回[123, 12345, 1234567, 777, 77788]
	 */
	private String[] belongGroups(Reviewer reviewer) {
		Set<String> result = new HashSet();
		String[] metaDiscipline = (String[]) reviewer.getIntrinsicProperty(META_DISCIPLINE);
		for (String disc : metaDiscipline) {
			while (disc.length() >= 3) {
				result.add(disc);
				disc = disc.substring(0, disc.length() - 2);
			}
		}
		return result.toArray(new String[0]);
	}
	
	/**
	 * 在当前限制级constraintLevel条件下，为评审对象subject查找一个专家，
	 * 若找到，则返回该专家，否则返回null
	 */
	@Override
	public Reviewer findReviewer(Subject subject) {
		//判断当前优先级下是否失败过，失败过则跳过，加速匹配
		if (constraintLevel.equals(subject.getAlterableProperty(LAST_FAIL_LEVEL))) {
			return null;
		}
		//判断该项目是否已经选满专家，满则直接跳出
		HashSet<String> selectedReviewerSet = (HashSet<String>) subject.getAlterableProperty(SELECTED_REVIEWERS);//项目已选择的专家
		if (null == selectedReviewerSet) {
			selectedReviewerSet = new HashSet<String>();
			subject.setAlterableProperty(SELECTED_REVIEWERS, selectedReviewerSet);
		}
		if(selectedReviewerSet.size() >= subjectReviewerNumber){//已满
			return null;
		}
		
		String[] metaDiscipline = (String[]) subject.getIntrinsicProperty(META_DISCIPLINE);
		Integer disciplineIndex = (Integer) subject.getAlterableProperty(DISCIPLINE_INDEX);	//当前项目轮到按其第几个学科选取专家
		if (disciplineIndex == null) {
			disciplineIndex = 0;
		}
		/*
		 * 从subject的第disciplineIndex个学科开始挑选专家，一旦找到专家，则返回
		 * 否则按下一个学科挑选专家，直到找到专家；或者遍历了所有学科也没找到专家，
		 * 此时，标记该项目在当先限制级下查找专家失败。
		 */
		for (int i = disciplineIndex; i < metaDiscipline.length + disciplineIndex; ++i) {
			String discipline = metaDiscipline[i % metaDiscipline.length];	//原学科
			String adjustedDiscipline = null;	//按照groupStatus退避后的学科
			//根据constraintLevel确定选取专家组方式
			int groupStatus = selector.findGroupStatus(constraintLevel);
		
			/*
			 * 根据groupStatus算出用来选取专家的学科编码
			 * 若groupStatus为0,adjustedDiscipline = discipline;
			 * 若groupStatus为1,adjustedDiscipline = discipline降一级学科(三级->二级；二级->一级；一级->一级);
			 * 若groupStatus为2,adjustedDiscipline = discipline降两级学科(三级->一级；二级->一级；一级->一级);
			 */
			adjustedDiscipline = discipline.substring(0, discipline.length() - Math.min(discipline.length() - 3, groupStatus * 2));
			
			/*
			 * adjustedDiscipline对应专家集合
			 */
			TreeSet<Reviewer> group = reviewerGroups.get(adjustedDiscipline);
			if (group != null) {
				for (Reviewer reviewer : group) {
					if (passConstrains(subject, reviewer)) {//判断各类限制条件是否通过
						subject.setAlterableProperty(DISCIPLINE_INDEX, (i + 1) % metaDiscipline.length);
						String universityCode = reviewer.getIntrinsicProperty(UNIVERSITY_CODE).toString();
						Integer cnt = university2MatchNumMap.get(universityCode);
						university2MatchNumMap.put(universityCode, (cnt == null ? 0 : cnt) + 1);
						Set<Reviewer> reviewers= university2SelectExpertNumMap.get(universityCode);
						if (reviewers == null) {
							reviewers = new HashSet<Reviewer>();
						}
						reviewers.add(reviewer);
						university2SelectExpertNumMap.put(universityCode, reviewers);
						return reviewer;
					}
				}
			}	
		}
		/*
		 * 按照所有学科都没有找到专家，标记该项目在当先限制级下查找专家失败
		 */
		subject.setAlterableProperty(LAST_FAIL_LEVEL, constraintLevel);
		return null;
	}
	
	/**
	 * 在评审对象和评审专家之间建立匹配对，并返回之<br>
	 * 同时更新专家在其所在组内的位置、专家、评审对象内随匹配对变化的属性
	 */
	@Override
	public MatchPair buildMatch(Subject subject, Reviewer reviewer) {
		/*
		 * 先暂从组中将专家删掉，reviewerGroups是TreeSet<Map>，专家是按顺序存储的，
		 * 直接改变专家内相关属性的值会导致专家在其分组内的位置错乱，故先暂时将其删除，
		 * 待属性更改完毕后再将专家放回分组。
		 */
		String[] groups = (String[]) reviewer.getAlterableProperty(GROUPS);
		if (null != groups) {
			for (String groupKey : groups) {
				reviewerGroups.get(groupKey).remove(reviewer);
			}
		}
		
		//更新评审对象已选的专家集合
		HashSet<Reviewer> selectedReviewers = (HashSet<Reviewer>) subject.getAlterableProperty(SELECTED_REVIEWERS);//项目已选择的专家
		if(null == selectedReviewers){
			selectedReviewers = new HashSet<Reviewer>();
			subject.setAlterableProperty(SELECTED_REVIEWERS, selectedReviewers);
		}
		selectedReviewers.add(reviewer);//将当前待匹配专家放入已选专家集，更新已选专家集合
		
		//更新评审对象已选的专家所属高校集合
		HashSet selectedReviewerUniversities = (HashSet) subject.getAlterableProperty(SELECTED_REVIEWER_UNIVERSITIES);
		if (null == selectedReviewerUniversities) {
			selectedReviewerUniversities = new HashSet();
			subject.setAlterableProperty(SELECTED_REVIEWER_UNIVERSITIES, selectedReviewerUniversities);
		}
		selectedReviewerUniversities.add(reviewer.getIntrinsicProperty(UNIVERSITY_CODE));

		//更新专家已有的评审项目数
		Integer burden = (Integer)reviewer.getAlterableProperty(BURDEN);
		if (null == burden) {
			burden = 0;
		}
		reviewer.setAlterableProperty(BURDEN, burden + 1);
		
		//更新评审对象已选的专家中部属高校专家数、地方高校专家数
		Integer inMinistryUniversity = (Integer) reviewer.getIntrinsicProperty(IN_MINISTRY_UNIVERSITY);
		//如果当前reviewers是部属高校专家，更新当前项目的已选专家的部属高校专家数
		if (null != inMinistryUniversity && 1 == inMinistryUniversity) {
			Integer ministryUnivCnt = (Integer) subject.getAlterableProperty(MINISTRY_UNIVERSITY_CNT);
			subject.setAlterableProperty(MINISTRY_UNIVERSITY_CNT, null == ministryUnivCnt ? 1 : ministryUnivCnt + 1);
		} else {//如果当前reviewers是地方高校专家，则更新当前项目的已选专家的地方高校专家数
			Integer localUnivCnt = (Integer) subject.getAlterableProperty(LOCAL_UNIVERSITY_CNT);
			subject.setAlterableProperty(LOCAL_UNIVERSITY_CNT, null == localUnivCnt ? 1 : localUnivCnt + 1);
		}
		
		//更新高校的已匹配专家数
		String universityCode = (String) reviewer.getIntrinsicProperty(UNIVERSITY_CODE);//获取当前专家所属高校代码
		HashSet<Reviewer> matchedReviewers = universityMatchedReviewers.get(universityCode);//和当前专家同校的已匹配专家
		if (null == matchedReviewers) {
			matchedReviewers = new HashSet<Reviewer>();
			universityMatchedReviewers.put(universityCode, matchedReviewers);
		}
		matchedReviewers.add(reviewer);//将当前专家加入已匹配专家集合，更新高校的已匹配专家
		
		//将专家加回组
		if (null != groups) {
			for (String groupKey : groups) {
				reviewerGroups.get(groupKey).add(reviewer);
			}
		}
		
		//构建匹配对
		MatchPair matchPair = new MatchPair(subject, reviewer);
		
		//返回当前匹配条目
		return matchPair;
	}

	/**
	 * 遍历所有限制条件，检查每个项目分别突破了哪些限制条件。将突破的警告信息放入subject.WARNINGS
	 */
	@Override
	public void finishingWork() {
		for (Subject subject : subjects) {
			Set warnings = new TreeSet<String>();
			for (Constraint constraint : constraints) {
				if (constraint.breakWarnInfo() != null && constraint.broken(subject)) {
					warnings.add(constraint.breakWarnInfo());
				}
			}
			subject.setAlterableProperty(WARNINGS, warnings);
		}
	}
	
	/**
	 * 判断各类限制条件是否通过
	 * @param subject 评审对象
	 * @param reviewer 评审专家
	 * @return ture:通过限制条件; false:未通过限制条件
	 */
	private boolean passConstrains(Subject subject, Reviewer reviewer) {
		for (Constraint constraint : constraints) {
			if (!constraint.pass(subject, reviewer, constraintLevel)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 添加限制条件
	 * @param constraint 限制条件
	 */
	public void addConstraint(Constraint constraint) {
		constraint.setMatcher(this);
		constraints.add(constraint);
	}
		
	public HashMap<String, HashSet<Reviewer>> getUniversityMatchedReviewers() {
		return universityMatchedReviewers;
	}

}
