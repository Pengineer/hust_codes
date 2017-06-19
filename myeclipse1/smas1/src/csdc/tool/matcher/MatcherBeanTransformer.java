package csdc.tool.matcher;

import static csdc.tool.matcher.MatcherInfo.DISCIPLINE;
import static csdc.tool.matcher.MatcherInfo.ID;
import static csdc.tool.matcher.MatcherInfo.IN_MINISTRY_UNIVERSITY;
import static csdc.tool.matcher.MatcherInfo.IS_AWARDED;
import static csdc.tool.matcher.MatcherInfo.IS_YANGTZE_SCHOLAR;
import static csdc.tool.matcher.MatcherInfo.IS_KEYPROJECT_DIRECTOR;
import static csdc.tool.matcher.MatcherInfo.EXPERT_TYPE;
import static csdc.tool.matcher.MatcherInfo.META_DISCIPLINE;
import static csdc.tool.matcher.MatcherInfo.NAME;
import static csdc.tool.matcher.MatcherInfo.OTHERBURDEN;
import static csdc.tool.matcher.MatcherInfo.RATING;
import static csdc.tool.matcher.MatcherInfo.REVIEWYEAR;
import static csdc.tool.matcher.MatcherInfo.SPECIALITYTITLE;
import static csdc.tool.matcher.MatcherInfo.SPECIALITYTITLE_RATING;
import static csdc.tool.matcher.MatcherInfo.SUBJECT_MEMBER_NAME;
import static csdc.tool.matcher.MatcherInfo.SUBJECT_MEMBER_UNIVERSITY;
import static csdc.tool.matcher.MatcherInfo.UNIVERSITYRATING;
import static csdc.tool.matcher.MatcherInfo.UNIVERSITY_CODE;
import static csdc.tool.matcher.MatcherInfo.YEAR;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.AwardApplication;
import csdc.bean.AwardApplicationReview;
import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.dao.HibernateBaseDao;
import csdc.tool.ApplicationContainer;

/**
 * 项目转换器：
 * 负责Project、Expert、ProjectApplicationReview向Subject、Reviewer、MatchPair的变换
 * 并设置好subjects和reviewers的固有属性
 * 
 * 奖励转换器：
 * 负责awards、Expert、AwardApplicationReview向Subject、Reviewer、MatchPair的变换
 * 并设置好subjects和reviewers的固有属性
 * @author xuhan
 * @author fengcl
 */

public class MatcherBeanTransformer {
	
	private HibernateBaseDao dao;
	
	/*
	 * 传入项
	 */
	//项目特有
	private List<ProjectApplication> projects;
	private List<ProjectApplicationReview> projectMatches;
	//奖励特有
	private List<AwardApplication> awards;
	private List<AwardApplicationReview> awardMatches;
	//(共用)传入项
	private List<Expert> experts;
	private String type;//type 项目的类型[general; instp]或者奖励的类型[moeSocial]

	/* 
	 * 传出项(转化为统一输出内容格式)
	 */
	private List<Subject> subjects;
	private List<Reviewer> reviewers;
	private List<MatchPair> matchPairs;

	/**
	 * reviewerId -> reviewer
	 */
	private HashMap<String, Reviewer> idReviewerMap;
	
	/**
	 * subjectId -> subject
	 */
	private HashMap<String, Subject> idSubjectMap;

	/**
	 * 部属高校代码集合
	 */
	private HashSet<String> ministryUniversityCode;
	
	/**
	 * 高校对应的评价等级
	 */
	private HashMap<String, Integer> universityRatingMap;
	
	/**
	 * 专家的职称对应的等级（例：教授对应正高级；specialityTitleRating = 6）（正高级6>高级5>副高级4>中级3>助理级2>员级1）
	 */
	private Map<String, Integer> specialityTitle2RatingMap;
	
	/**
	 * 专家其他参评项目数
	 */
	private Map<String, Integer> otherBurdenMap;
	public MatcherBeanTransformer() {
	}
	
	/**
	 * 项目转换器构造
	 * @param projects 需要匹配的项目
	 * @param experts 需要进行参评的专家（并非所有）
	 * @param matches 已经存在的匹配对（不包含待剔除的匹配对）
	 * @param dao
	 */
	public MatcherBeanTransformer(List<ProjectApplication> projects, List<Expert> experts, List<ProjectApplicationReview> projectMatches, String type, HibernateBaseDao dao) {
		this.projects = projects;
		this.experts = experts;
		this.projectMatches = projectMatches;
		this.type = type;
		this.dao = dao;
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		getOtherBurden();
		processProjectAbout();
	}
	
	/**
	 * 奖励转换器构造
	 * @param projects 需要匹配的奖励
	 * @param experts 需要进行参评的专家（并非所有）
	 * @param matches 已经存在的匹配对（不包含待剔除的匹配对）
	 * @param type 项目的类型[general; instp]或者奖励的类型[moeSocial]
	 * @param dao
	 */
	public void transFormerAward(List<AwardApplication> awards, List<Expert> experts, List<AwardApplicationReview> awardMatches, String type, HibernateBaseDao dao) {
		this.awards = awards;
		this.experts = experts;
		this.awardMatches = awardMatches;
		this.dao = dao;
		this.type = type;
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		getOtherBurden();
		processAwardAbout();
	}
	
	public List<Subject> getSubjects() {
		return subjects;
	}

	public List<Reviewer> getReviewers() {
		return reviewers;
	}

	public List<MatchPair> getMatchPairs() {
		return matchPairs;
	}

	public Subject getSubject(ProjectApplication project) {
		return idSubjectMap.get(project.getId());
	}
	
	public Subject getSubject(AwardApplication award) {
		return idSubjectMap.get(award.getId());
	}
	
	public Reviewer getReviewer(Expert expert) {
		return idReviewerMap.get(expert.getId());
	}
	
	/**
	 * 初始化匹配器中，专家TreeSet排序所需条件（公共部分）
	 */
	private void init(){
		//找出部属高校
		Date begin = new Date();
		List ministryUniversityCodeList = dao.query("select u.code from University u where u.founderCode in ('308', '339', '360', '435') ");
		System.out.println("查询[部属高校]用时：" + (new Date().getTime() - begin.getTime()) + "ms");
		System.out.println("查询[部属高校]数目：" + ministryUniversityCodeList.size() + "\n");
		ministryUniversityCode = new HashSet<String>(ministryUniversityCodeList);//部属高校代码集合
		
		//找出高校对应的评价等级
		begin = new Date();
		List<Object[]> universityRating = dao.query("select u.code,u.rating from University u ");
		System.out.println("查询[高校对应的评价等级]用时：" + (new Date().getTime() - begin.getTime()) + "ms");
		System.out.println("查询[高校对应的评价等级]数目：" + universityRating.size() + "\n");
		universityRatingMap = new HashMap<String, Integer>(); 
		for (Object[] objects : universityRating) {
			String universityCode = objects[0].toString();
			universityRatingMap.put(universityCode, Integer.parseInt(objects[1].toString()));
		}
		
		//取出系统选项表中的c_name，c_code，c_description字段，组装‘c_code/c_name’与专家表中的c_speciality_title做对比
		List<Object[]> systemOptionList = dao.query("select so.code,so.name,so.description from SystemOption so where so.standard = 'GBT8561-2001' and so.description is not null");
		HashMap<String, String> codeName2Description = new HashMap<String, String>();
		for (Object[] objects : systemOptionList) {
			String code = objects[0].toString();
			String name = objects[1].toString();
			String description = objects[2].toString();
			codeName2Description.put(code + "/" + name,description);
		}
		List<Object> expertSeciality = dao.query("select distinct expert.specialityTitle from Expert expert where expert.specialityTitle is not null");
		specialityTitle2RatingMap = new HashMap<String, Integer>(); 
		for (Object object : expertSeciality) {
			String specialityTitle = object.toString();
			if (codeName2Description.get(specialityTitle) != null) {
				specialityTitle2RatingMap.put(specialityTitle,specialityTitle2Rating(codeName2Description.get(specialityTitle)));
			}
		}
	}
	
	/**
	 * 根据数据库查出的专家列表、对象列表、和已有评审对象列表，初始化符合matcher需求的输入数据<br>
	 * 同时初始化[专家id -> 专家 映射][项目id -> 项目 映射]
	 * 
	 * @param reviewers 专家列表reviewerList
	 * @param subjects 对象列表subjectList
	 * @param existingMatches 已有评审对象列表existingMatchList
	 */

	//完成项目相关转化
	private void processProjectAbout() {
		System.out.println("开始对象转化...");
		long begin = System.currentTimeMillis();
		processExpert();
		long end1 = System.currentTimeMillis();
		System.out.println("完成专家转化，用时：" + (end1 - begin) + " ms");

		idSubjectMap = new HashMap<String, Subject>();
		subjects = new ArrayList<Subject>();
		try {
			for (ProjectApplication project : projects) {
			Subject subject = transSubject(project);
			subjects.add(subject);
			idSubjectMap.put(project.getId(), subject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end2 = System.currentTimeMillis();
		System.out.println("完成项目转化，用时：" + (end2 - begin) + " ms");
		matchPairs = new ArrayList<MatchPair>();
		//TODO延迟加载
		for (ProjectApplicationReview projectReviewer : projectMatches) {
			String rid = projectReviewer.getReviewer().getId();
			String pid = projectReviewer.getProject().getId();
			
			boolean reviewerAbsent = !idReviewerMap.containsKey(rid);
			boolean subjectAbsent = !idSubjectMap.containsKey(pid);
//			if (reviewerAbsent && subjectAbsent) {
//				/*
//				 * 只使用和reviewers中专家关联、或者和subjects评审对象关联的匹配对，
//				 * 无视其他匹配对
//				 */
//				continue;
//			}
			
			if (reviewerAbsent) {
				/*
				 * projectReviewer.subject是subjects元素，projectReviewer.reviewer非reviewers元素
				 * 因此需要额外一次数据库查询查出projectReviewer.reviewer的所需成员，并更新idReviewerMap
				 */
				idReviewerMap.put(rid, transReviewer(projectReviewer.getReviewer()));
			}
			Reviewer reviewer = idReviewerMap.get(rid);
			
			if (subjectAbsent) {
				/*
				 * projectReviewer.reviewer是reviewers元素ProjectApplicationReviewr.subject非subjects元素
				 * 因此需要额外一次数据库查询查出projectReviewer.subject的所需成员，并更新idSubjectMap
				 */
				idSubjectMap.put(pid, transSubject((ProjectApplication)projectReviewer.getProject()));
			}
			Subject subject = idSubjectMap.get(pid);

			MatchPair matchPair = new MatchPair(subject, reviewer);
			matchPairs.add(matchPair);
			long end3 = System.currentTimeMillis();
			System.out.println("完成已有匹配对转化，用时：" + (end3 - begin) + " ms");
		}
	}
	//完成奖励相关转化
	private void processAwardAbout() {
		System.out.println("开始对象转化...");
		long begin = System.currentTimeMillis();
		processExpert();
		long end1 = System.currentTimeMillis();
		System.out.println("完成专家转化，用时：" + (end1 - begin) + " ms");
		
		idSubjectMap = new HashMap<String, Subject>();
		subjects = new ArrayList<Subject>();
		try {
			for (AwardApplication award : awards) {
			Subject subject = transSubject(award);
			subjects.add(subject);
			idSubjectMap.put(award.getId(), subject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end2 = System.currentTimeMillis();
		System.out.println("完成项目转化，用时：" + (end2 - begin) + " ms");
		
		matchPairs = new ArrayList<MatchPair>();
		//TODO延迟加载
	try {
		//------------调试---------------
		

		for (AwardApplicationReview awardReviewer : awardMatches) {
			String rid = awardReviewer.getReviewer().getId();
			String pid = awardReviewer.getAward().getId();
			
			boolean reviewerAbsent = !idReviewerMap.containsKey(rid);
			boolean subjectAbsent = !idSubjectMap.containsKey(pid);
			
			if (reviewerAbsent) {
				/*
				 * projectReviewer.subject是subjects元素，projectReviewer.reviewer非reviewers元素
				 * 因此需要额外一次数据库查询查出projectReviewer.reviewer的所需成员，并更新idReviewerMap
				 */
				idReviewerMap.put(rid, transReviewer(awardReviewer.getReviewer()));
			}
			Reviewer reviewer = idReviewerMap.get(rid);
			
			if (subjectAbsent) {
				idSubjectMap.put(pid, transSubject((AwardApplication)awardReviewer.getAward()));
			}
			Subject subject = idSubjectMap.get(pid);
			MatchPair matchPair = new MatchPair(subject, reviewer);
			matchPairs.add(matchPair);

		}
	} catch (Exception e) {
		e.printStackTrace();
	}
		long end3 = System.currentTimeMillis();
		System.out.println("完成已有匹配对转化，用时：" + (end3 - begin) + " ms");
	}
	//完成专家转化
	private void processExpert() {
		idReviewerMap = new HashMap<String, Reviewer>();
		reviewers = new ArrayList<Reviewer>();
		for (Expert expert : experts) {
			Reviewer reviewer = transReviewer(expert);
			reviewers.add(reviewer);
			idReviewerMap.put(expert.getId(), reviewer);
		}
	}
	private void process() {
		System.out.println("开始对象转化...");
		long begin = System.currentTimeMillis();
		long end1 = System.currentTimeMillis();
		System.out.println("完成专家转化，用时：" + (end1 - begin) + " ms");
		
		long end2 = System.currentTimeMillis();
		System.out.println("完成专项目转化，用时：" + (end2 - begin) + " ms");
		
		long end3 = System.currentTimeMillis();
		System.out.println("完成已有匹配对转化，用时：" + (end3 - begin) + " ms");
	}

	/**
	 * 评审专家的相关处理<br>
	 * expert对象转换为Reviewer，并初始化所有固有属性
	 * 
	 * @param expert 专家
	 * @return
	 */
	private Reviewer transReviewer(Expert expert) {
		Reviewer reviewer = new Reviewer();
		if (expert.getUniversityCode() == null) {
//			dao.refresh(expert);
			expert = (Expert) dao.query(Expert.class, expert.getId());
		}
		reviewer.setIntrinsicProperty(ID, expert.getId());
		reviewer.setIntrinsicProperty(NAME, expert.getName());
		reviewer.setIntrinsicProperty(UNIVERSITY_CODE, expert.getUniversityCode());
		reviewer.setIntrinsicProperty(DISCIPLINE, expert.getDiscipline());
		reviewer.setIntrinsicProperty(META_DISCIPLINE, splitDiscipline(expert.getDiscipline()));
		reviewer.setIntrinsicProperty(RATING, Integer.parseInt(expert.getRating()));
		reviewer.setIntrinsicProperty(SPECIALITYTITLE, expert.getSpecialityTitle());
		//专家是否是长江学者
		reviewer.setIntrinsicProperty(IS_YANGTZE_SCHOLAR, expert.getIsYangtzeScholar());
		//专家是否是重大项目负责人
		reviewer.setIntrinsicProperty(IS_KEYPROJECT_DIRECTOR, expert.getIsKeyProjectDirector());
		//专家类型
		reviewer.setIntrinsicProperty(EXPERT_TYPE, expert.getExpertType());
		reviewer.setIntrinsicProperty(IS_AWARDED, expertIsAward(expert.getAward()));//专家是否入选人才奖励计划（0：否；1：是）
		reviewer.setIntrinsicProperty(REVIEWYEAR, expertIsReview(expert.getGeneralReviewYears()));//专家是否参评往年项目（0：否；1：是）
		if (ministryUniversityCode.contains(expert.getUniversityCode())) {
			reviewer.setIntrinsicProperty(IN_MINISTRY_UNIVERSITY, 1);//专家所属是部属高校，则用 1 标记
		}
		
		reviewer.setIntrinsicProperty(UNIVERSITYRATING, universityRatingMap.get(expert.getUniversityCode()));
		reviewer.setIntrinsicProperty(SPECIALITYTITLE_RATING, specialityTitle2RatingMap.get(expert.getSpecialityTitle()));
		reviewer.setIntrinsicProperty(OTHERBURDEN, otherBurdenMap.get(expert.getId()) == null ? 0 : otherBurdenMap.get(expert.getId()));
		return reviewer;
	}

	/**
	 * 评审对象（项目）的相关处理<br>
	 * project对象转换为Subject，并初始化所有固有属性
	 * 
	 * @param project 项目
	 * @return
	 */
	private Subject transSubject(ProjectApplication project) {
		Subject subject = new Subject();
//		try {
//			if (project instanceof HibernateProxy || project.getDiscipline() == null || project.getUniversityCode() == null) {
////				dao.refresh(project);
//				String dddString;
////				project = (ProjectApplication) dao.query(ProjectApplication.class, project.getId());
//			}
//		} catch (Exception e) {
//			System.out.println("kk");
//		}
		subject.setIntrinsicProperty(ID, project.getId());
		subject.setIntrinsicProperty(UNIVERSITY_CODE, project.getUniversityCode());
		subject.setIntrinsicProperty(DISCIPLINE, project.getDiscipline());
		subject.setIntrinsicProperty(META_DISCIPLINE, splitDiscipline(project.getDiscipline()));
		subject.setIntrinsicProperty(YEAR, project.getYear());
		if (project.getMembers() != null) {
			Object[] memberInfo = this.getMemberInfo(project);
			subject.setIntrinsicProperty(SUBJECT_MEMBER_NAME, memberInfo[0]);
			subject.setIntrinsicProperty(SUBJECT_MEMBER_UNIVERSITY, memberInfo[1]);
		}
		return subject;
	}
	
	/**
	 * 评审对象（奖励）的相关处理<br>
	 * project对象转换为Subject，并初始化所有固有属性
	 * 
	 * @param project 项目
	 * @return
	 */
	private Subject transSubject(AwardApplication award) {
		Subject subject = new Subject();
		subject.setIntrinsicProperty(ID, award.getId());
		subject.setIntrinsicProperty(UNIVERSITY_CODE, award.getUnitCode());
		//奖励的学科代码属性需要处理和项目申请的保持一致
		String matcherDiscipline = filterDisciplineCode(award.getDisciplineCode());
		subject.setIntrinsicProperty(DISCIPLINE, matcherDiscipline);
		subject.setIntrinsicProperty(META_DISCIPLINE, splitDiscipline(matcherDiscipline));
		subject.setIntrinsicProperty(YEAR, award.getYear());
		//合适AwardApplication 与ProjectApplication的对象异同以及处理方式
		//AwardApplication无成员信息则：不需要解析
		return subject;
	}
	/**
	 * 因为奖励申请中的学科代码是与smdb保持一致的，即将smdb的学科代码属性格式“82099/法学其他学科; 1902099/社会心理学其他学科”
	 * 转化为匹配器通用格式“82099; 1902099”
	 */
	private String filterDisciplineCode(String smdbStyleDiscipline) {
		if (smdbStyleDiscipline == null || smdbStyleDiscipline.equals("")) {
			return null;
		}
		String[] diss = smdbStyleDiscipline.split("\\D+");
		String resultDis = "";
		for (int i = 0; i < diss.length; i++) {
			String item = diss[i].trim();
			resultDis += (resultDis.equals("") ? item : "; " + item);
		}
		return resultDis;
	}
	/**
	 * 分割原始学科代码，并做处理<br>
	 * 如果某个专家或项目的所属的学科中既存在高级学科（如一级学科）又存在以此高级学科代码为前缀的低级级学科（如三级学科），<br>
	 * 则剔除高级别学科代码，保留低级别学科代码，使匹配更精确<br>
	 * 例如:某专家学科代码为: "123; 1234567; 77788"，则返回[1234567, 77788]<br>
	 * @param discipline 原始学科代码
	 * @return 处理后的精确学科代码数组
	 */
	private static String[] splitDiscipline(String discipline) {
		if (discipline == null) {
			discipline = "";
		}
		Set<String> result = new TreeSet<String>();
		String[] originDisc = discipline.split("\\D+");
		for (int i = 0; i < originDisc.length; i++) {
			if (originDisc[i].isEmpty()) {
				continue;
			}
			if (originDisc[i].length() != 3 && originDisc[i].length() != 5 && originDisc[i].length() != 7) {
				/*
				 * 学科代码的位数只允许为：3(一级学科)、5(二级学科)、7(三级学科)
				 */
				throw new RuntimeException("有误的学科代码: " + discipline);
			}
			boolean asPrefix = false;//标识是否为前缀
			for (int j = 0; j < originDisc.length; j++) {
				if (!originDisc[j].equals(originDisc[i]) && originDisc[j].startsWith(originDisc[i])) {
					//如果不是当前学科自身(因自身也是自身的前缀)，且存在以originDisc[i]为前缀的originDisc[j]，则剔除originDisc[i]
					asPrefix = true;
					break;
				}
			}
			if (!asPrefix) {
				result.add(originDisc[i]);
			}
		}
		return result.toArray(new String [0]);
	}
	
	/**
	 * 获取项目[成员集合]和[成员所在高校代码集合](成员包括负责人)<br>
	 * 其中，项目的成员信息形如[张三(北京大学), 李四(清华大学)]
	 * @param memberInfo
	 * @return 0: Set姓名集合	1: Set高校代码集合	 若未能识别出高校，则代码置为null
	 */
	public Object[] getMemberInfo(ProjectApplication project) {
		if (project == null || project.getMembers() == null) {
			return new Object[]{new HashSet<String>(), new HashSet<String>()};
		}
		HashMap<String, String> univNameCodeMap = (HashMap<String, String>) ApplicationContainer.sc.getAttribute("univNameCodeMap");
		//member形式多种，提高解析能力
		String[] members = project.getMembers().split("\\s*[,;， ]\\s*");
		Set<String> names = new HashSet<String>();
		Set<String> univCodes = new HashSet<String>();		
		for (String string : members) {
			String name = null;
			String agencyName = null;
			int begin = string.indexOf('(');
			if ( begin > 0) {
				name = string.substring(0, begin);
				int end = string.indexOf(")");
				agencyName = string.substring(begin+1, end);
			} else {
				name = string;
			}
			if (agencyName != null) {
				for (int i = 0; i < agencyName.length(); i++) {
					for (int j = agencyName.length(); j > i; j--) {
						String subName = agencyName.substring(i, j);
						if (univNameCodeMap.get(subName) != null) {
							univCodes.add(univNameCodeMap.get(subName));//项目成员所属高校
							break;
						}
					}
				}
			}
			names.add(name);//项目成员
		
		}
//		
//		String[] members = project.getMembers().split("\\)[,，;]\\s*");
//		Set<String> names = new HashSet<String>();
//		Set<String> univCodes = new HashSet<String>();
//		for (String string : members) {
//			int splitIndex = string.indexOf('(');
//			String personName = string.substring(0, splitIndex);
//			String agencyName = string.substring(splitIndex + 1);
//			for (int i = 0; i < agencyName.length(); i++) {
//				for (int j = agencyName.length(); j > i; j--) {
//					String subName = agencyName.substring(i, j);
//					if (univNameCodeMap.get(subName) != null) {
//						univCodes.add(univNameCodeMap.get(subName));//项目成员所属高校
//						break;
//					}
//				}
//			}
//			names.add(personName);//项目成员
//		}
		names.add(project.getDirector());//项目负责人
		univCodes.add(project.getUniversityCode());//项目所属高校
		return new Object[]{names, univCodes};
	}
	
	/**
	 * 根据专家奖励信息，判断该专家是否入选人才奖励计划（0：未入选；1：入选）
	 * @param expertAward
	 * @return
	 */
	public Integer expertIsAward(String expertAward){
		Integer expertIsAward = 0;
		if (expertAward != null && !expertAward.isEmpty()) {
			if (expertAward.contains("长江学者奖励计划") || expertAward.contains("国家杰出青年科学基金") || expertAward.contains("国家有突出贡献中青年专家") || expertAward.contains("海外高层次人才引进计划（千人计划）") || expertAward.contains("国家新世纪百千万人才工程") || expertAward.contains("教育部跨世纪优秀人才培养计划") || expertAward.contains("教育部新世纪优秀人才支持计划") || expertAward.contains("全国百篇优秀博士论文获得者") || expertAward.contains("教育部高等学校优秀青年教师教学科研奖励计划（高校青年教师奖）")) {
				expertIsAward = 1;
			}
		}
		return expertIsAward;
	}
	
	/**
	 * 根据专家一般项目参评年份，判断该专家是否参评往年项目（0：否；1：是）
	 * @param expertReviewYears
	 * @return
	 */
	public Integer expertIsReview(String expertReviewYears){
		Integer expertIsReview = 0;
		if (expertReviewYears != null && !expertReviewYears.isEmpty()) {
			expertIsReview = 1;
		}
		return expertIsReview;
	}
	
	/**
	 * 通过专家的专业职称，进行登记划分（正高级>高级>副高级>中级>助理级>员级）
	 * @param description
	 * @return
	 */
	private Integer specialityTitle2Rating(String description){
		if (description.equals("正高级")) {
			return 6;
		}else if(description.equals("高级")) {
			return 5;
		}else if (description.equals("副高级")) {
			return 4;
		}else if (description.equals("中级")) {
			return 3;
		}else if (description.equals("助理级")) {
			return 2;
		}else{
			return 1;
		}
	}
	
	/**
	 * 获得专家其他的评审项目数量
	 * 依据专家匹配中同等条件的专家按照“其他评审项目数量”的正序的优先级，排在前面的优先级最高
	 * 目前smas系统有“项目”“奖励”两个模块需要根据情况统计，如果添加其他类型，需要在本处代码调整
	 */
	private void getOtherBurden(){
		otherBurdenMap = new HashMap<String, Integer>();
		long begin = System.currentTimeMillis();
		Map session = ActionContext.getContext().getSession();
		Integer defaultYear = (Integer) session.get("defaultYear");
		//根据不同项目类型计算专家的其他评审属相“burden”
		if (this.type.equals("general") && projects != null && projects.size() != 0) {
			//项目模块其他类型评审情况统计
			List<Object[]> otherBurdenProject = dao.query("select expert.id,count(ir.id) from Expert expert,ProjectApplicationReview ir where ir.reviewer.id = expert.id and ir.year = '" + defaultYear + "' and ir.type != 'general' group by expert.id");
			for (Object[] objects : otherBurdenProject) {
				String expertId = objects[0].toString();
				Integer otherProjectBurden = Integer.parseInt(objects[1].toString());
				
				Integer burdernCount = otherBurdenMap.get(expertId);
				if (null == burdernCount) {
					burdernCount = 0;
					otherBurdenMap.put(expertId, burdernCount);
				} 
				otherBurdenMap.put(expertId, burdernCount + otherProjectBurden);
			}
			//奖励模块其他类型评审情况统计
			List<Object[]> otherBurdenAward = dao.query("select expert.id,count(aar.id) from Expert expert,AwardApplicationReview aar where aar.reviewer.id = expert.id and aar.year = '" + defaultYear + "' and aar.type != 'general' group by expert.id");
			for (Object[] objects : otherBurdenAward) {
				String expertId = objects[0].toString();
				Integer otherAwardBurden = Integer.parseInt(objects[1].toString());
				
				Integer burdernCount = otherBurdenMap.get(expertId);
				if (null == burdernCount) {
					burdernCount = 0;
					otherBurdenMap.put(expertId, burdernCount);
				}
				otherBurdenMap.put(expertId, burdernCount + otherAwardBurden);
			}
			//
			
		} else if (this.type.equals("instp") && projects != null && projects.size() != 0) {
			//项目模块其他类型评审情况统计
			List<Object[]> otherBurdenProject = dao.query("select expert.id,count(pa.id) from Expert expert,ProjectApplicationReview pa where pa.reviewer.id = expert.id and pa.year = '" + defaultYear + "' and pa.type != 'instp' group by expert.id");
			for (Object[] objects : otherBurdenProject) {
				String expertId = objects[0].toString();
				Integer otherProjectBurden = Integer.parseInt(objects[1].toString());
				
				Integer burdernCount = otherBurdenMap.get(expertId);
				if (null == burdernCount) {
					burdernCount = 0;
					otherBurdenMap.put(expertId, burdernCount);
				} 
				otherBurdenMap.put(expertId, burdernCount + otherProjectBurden);
			}
			//奖励模块其他类型评审情况统计
			List<Object[]> otherBurdenAward = dao.query("select expert.id,count(aar.id) from Expert expert,AwardApplicationReview aar where aar.reviewer.id = expert.id and aar.year = '" + defaultYear + "' and aar.type != 'instp' group by expert.id");
			for (Object[] objects : otherBurdenAward) {
				String expertId = objects[0].toString();
				Integer otherAwardBurden = Integer.parseInt(objects[1].toString());
				
				Integer burdernCount = otherBurdenMap.get(expertId);
				if (null == burdernCount) {
					burdernCount = 0;
					otherBurdenMap.put(expertId, burdernCount);
				}
				otherBurdenMap.put(expertId, burdernCount + otherAwardBurden);
			}
			//
		} else if (this.type.equals("moeSocial") && awards != null && awards.size() != 0) {
			//项目模块其他类型评审情况统计
			List<Object[]> otherBurdenProject = dao.query("select expert.id,count(pa.id) from Expert expert,ProjectApplicationReview pa where pa.reviewer.id = expert.id and pa.year = '" + defaultYear + "' and pa.type != 'moeSocial' group by expert.id");
			for (Object[] objects : otherBurdenProject) {
				String expertId = objects[0].toString();
				Integer otherProjectBurden = Integer.parseInt(objects[1].toString());
				
				Integer burdernCount = otherBurdenMap.get(expertId);
				if (null == burdernCount) {
					burdernCount = 0;
					otherBurdenMap.put(expertId, burdernCount);
				} 
				otherBurdenMap.put(expertId, burdernCount + otherProjectBurden);
			}
			//奖励模块其他类型评审情况统计
			List<Object[]> otherBurdenAward = dao.query("select expert.id,count(aar.id) from Expert expert,AwardApplicationReview aar where aar.reviewer.id = expert.id and aar.year = '" + defaultYear + "' and aar.type != 'moeSocial' group by expert.id");
			for (Object[] objects : otherBurdenAward) {
				String expertId = objects[0].toString();
				Integer otherAwardBurden = Integer.parseInt(objects[1].toString());
				
				Integer burdernCount = otherBurdenMap.get(expertId);
				if (null == burdernCount) {
					burdernCount = 0;
					otherBurdenMap.put(expertId, burdernCount);
				}
				otherBurdenMap.put(expertId, burdernCount + otherAwardBurden);
			}
		} else {
			return ;
		}
		
		long end = System.currentTimeMillis();
		System.out.println("完成burden初始化：用时：" + ( end - begin) + "ms");
	}
}
