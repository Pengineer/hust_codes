package csdc.tool.matcher;

import static csdc.tool.matcher.MatcherInfo.ID;
import static csdc.tool.matcher.MatcherInfo.SELECTED_REVIEWERS;
import static csdc.tool.matcher.MatcherInfo.WARNINGS;
import static csdc.tool.matcher.MatcherInfo.YEAR;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.ProjectApplicationReviewUpdate;
import csdc.dao.HibernateBaseDao;


/**
 * 取项目匹配器父类
 * @author zhangn
 * 2014-11-6
 * 通过抽象方法与方法覆盖的形式处理项目参数的差异
 */
public abstract class ProjectReviewerMatcher {
	private String srcPath = GeneralReviewerMatcher.class.getClassLoader().getResource("matherLog.txt").getPath().replace("%20", " ");
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private HibernateBaseDao dao;
	//获取不同项目对应的服务层bean
//	public abstract Object getTheProjectService();
	public abstract String getProjectType();
	/**
	 * 每位专家若参评，至少需参评的项目数
	 */
	protected Integer expertProjectMin;
	
	/**
	 * 每位专家评审的最大项目数
	 */
	protected Integer expertProjectMax;
	
	/**
	 * 每个项目所需部属高校评审专家数
	 */
	protected Integer projectMinistryExpertNumber;
	
	/**
	 * 每个项目所需地方高校评审专家数
	 */
	protected Integer projectLocalExpertNumber;
	
	/**
	 * 每个项目所需的评审专家数
	 */
	protected Integer projectExpertNumber;
	
	/**
	 * 每个高校若有专家参评，建议至少应参评的专家数
	 */
	protected Integer universityExpertMin;
	
	/**
	 * 每个高校若有专家参评，建议至多应参评的专家数
	 */
	protected Integer universityExpertMax;
	

	@Transactional
	public void matchExpert(Integer currentYear, List<String> expertIds, List<String> projectIds, List<String> rejectExpertIds) throws Exception {
		
		FileWriter fWriter = new FileWriter(srcPath);
		fWriter.append(sdf.format(new Date()) + " 匹配开始\n");
		
		if (expertIds == null) {
			expertIds = new ArrayList<String>();
		}
		if (projectIds == null) {
			projectIds = new ArrayList<String>();
		}
		if (rejectExpertIds == null) {
			rejectExpertIds = new ArrayList<String>();
		}

		Map paraMap = new HashMap();
		paraMap.put("year", currentYear);	//当前年份
		paraMap.put("expertIds", expertIds);	//待匹配专家id
		paraMap.put("projectIds", projectIds);	//待匹配项目id
		paraMap.put("rejectExpertIds", rejectExpertIds);	//待剔除专家id
		paraMap.put("projectExpertNumber", Long.valueOf(projectExpertNumber));	//每个项目计划匹配人数
	
		List<Expert> experts = null;	//待匹配专家
		List<ProjectApplication> projects = null;	//待匹配项目
		List<ProjectApplicationReview> existingMatches = null;	//已匹配条目
		List<ProjectApplicationReview> rejectMatches = null;	//待剔除的匹配条目
			
		experts = getReviewerInfo(expertIds, projectIds, rejectExpertIds, paraMap);
		
        //2、筛选出当前年度（所有）待匹配的项目（不包含已经匹配成功的项目）
		projects = getSubjectInfo(projectIds, rejectExpertIds, paraMap);
		
		//3、找出和已有匹配条目(不包含待剔除条目)
		existingMatches = getExistingMatcher(getProjectType(), rejectExpertIds, paraMap);
		
		//4、找出待剔除的匹配条目
		rejectMatches = getRejectMatcher(getProjectType(), rejectExpertIds, paraMap);
		
		/*
		 * 以上找出的experts、projects、existingMatches、rejectMatches可能很多，为了节省内存，
		 * 将其从hibernate一级缓存(session)中清除。
		 */
		dao.clear();
		
		
		//最终匹配结果(直接用于添加到数据库)
		List<ProjectApplicationReview> matchResult = new ArrayList<ProjectApplicationReview>();
		
		/*
		 * 将projects、experts、existingMatches转化为和数据库无关的subjects、reviewers、matchPairs，
		 * 并设置好subjects和reviewers的固有属性
		 */
		MatcherBeanTransformer transformer = new MatcherBeanTransformer(projects, experts, existingMatches, getProjectType(), dao);
		
		//构造多轮匹配器
		MultiRoundsMatcher multiRoundsMatcher = new MultiRoundsMatcher(
			getProjectType(),
			transformer.getSubjects(),
			transformer.getReviewers(),
			transformer.getMatchPairs(),
			expertProjectMin,
			expertProjectMax,
			projectMinistryExpertNumber,
			projectLocalExpertNumber,
			projectExpertNumber,
			universityExpertMin,
			universityExpertMax
		);
		
		//开始匹配，返回最终匹配结果
		List<MatchPair> matchPairs = multiRoundsMatcher.match();
		
		//转换最终结果，存入matchResult
		for (MatchPair matchPair : matchPairs) {
			ProjectApplicationReview pr = getPRBean(matchPair, getProjectType());
			matchResult.add(pr);
		}

		/*
		 * 删除 待剔除专家的 被替换成功的 评审条目(用于专家批量替换)
		 * 遍历所有待删除的匹配对，记该匹配对为对象s <-> 专家r，若对象s当前选择的专家不足projectExpertNumber个，
		 * 则把专家r添加到对象s选择的专家中，且保留该匹配对；否则表明对象s已经选足projectExpertNumber个专家，故
		 * 将该匹配对删除。
		 */
		// TODO（注意）已经存在数据库总，只需将不合适的剔除（需要在最后更新匹配(匹配器中的更新，而不是更新入库)信息）
		// 只要对需要摒弃的匹配对（保留），最后都要进行一次全局的匹配警告更新
		for (ProjectApplicationReview pr : rejectMatches) {
			Subject subject = transformer.getSubject(pr.getProject());
			if (subject != null) {
				/*
				 * 一个评审对象已选的专家
				 */
				Set<Reviewer> selectedReviewers = (Set)subject.getAlterableProperty(SELECTED_REVIEWERS);
				if (selectedReviewers.size() >= projectExpertNumber) {
					/*
					 * 当前选择的专家已有projectExpertNumber个
					 */
					ProjectApplicationReviewUpdate pru = new ProjectApplicationReviewUpdate(pr, 0, 0);
					dao.add(pru);
					dao.delete(pr);
				} else {
					/*
					 * 当前选择的专家不足projectExpertNumber个
					 */
					selectedReviewers.add(transformer.getReviewer(pr.getReviewer()));
				}
			}
		}

		fWriter.append(sdf.format(new Date()) + " 向数据库写入匹配结果数量：" + matchResult.size() + "\n");
		
		//向数据库写入匹配结果
		for (int i = 0; i < matchResult.size(); i++) {
			ProjectApplicationReview pr = matchResult.get(i);
			//向 匹配表 写入
			dao.add(pr);
			//向 匹配更新表 写入
			ProjectApplicationReviewUpdate pru = new ProjectApplicationReviewUpdate(pr, 1, 0);
			dao.add(pru);
			/*
			 * 每隔一万条flush一次
			 * 若最后一次性flush会有什么问题来着?
			 */
			if (i % 10000 == 0) {
				System.out.println("写入匹配结果: " + i + " / " + matchResult.size());
				
				fWriter.append(sdf.format(new Date()) + " 写入匹配结果开始: " + i + " / " + matchResult.size() + "\n");
				
				dao.flush();
				
				fWriter.append(sdf.format(new Date()) + " flush匹配结果结束: \n" + i);
			}
		}
		fWriter.flush();
		fWriter.close();
		
		//匹配最后，更新警告信息
		System.out.println("\n开始更新警告信息...");
		for (ProjectApplication project : projects) {//对本年度（所有）项目进行更新操作
			Subject subject = transformer.getSubject(project);
			Set warnings = (Set) subject.getAlterableProperty(WARNINGS);
			String oldWarnings = project.getWarningReviewer() + "";
			String newWarnings = warnings + "";
			/*
			 * 只对警告信息变化了的的项目进行修改，以加快写入速度
			 */
			if (!oldWarnings.equals(newWarnings)) {
				if (warnings == null || warnings.isEmpty()) {
					newWarnings = null;
				}
				project.setWarningReviewer(newWarnings);
				dao.modify(project);
			}
		}
		System.out.println("警告信息更新完成！\n自动匹配全部完成。");
	}

	/**
	 * 获取评审专家
	 * 如果expertIds为空，则查选满足条件的所有专家
	 * 否则查询expertIds的专家<br>
	 * 并且返回的专家不在rejectExpertIds中。
	 * @param expertIds
	 * @param projectIds
	 * @param rejectExpertIds
	 * @param paraMap
	 * @return
	 */
	public abstract List<Expert> getReviewerInfo(List<String> expertIds, List<String> projectIds, List<String> rejectExpertIds, Map paraMap);
	/**
	 * 筛选出当前年度（所有）待匹配的项目（不包含已经匹配成功的项目）
	 * @param projectIds
	 * @param rejectExpertIds
	 * @param paraMap
	 * @return
	 */
	public abstract List<ProjectApplication> getSubjectInfo(List<String> projectIds, List<String> rejectExpertIds, Map paraMap);

	/**
	 * 找出特定类型的匹配条目
	 * （共用方法）
	 * 要求：(不包含待剔除条目)
	 * @param projectType 
	 * @param rejectExpertIds
	 * @param paraMap
	 * @return
	 */
	public List<ProjectApplicationReview> getExistingMatcher(String projectType, List<String> rejectExpertIds, Map paraMap){
		List<ProjectApplicationReview> existingMatches = new ArrayList<ProjectApplicationReview>();
		Date begin = new Date();
		String hql4ExistingMatch = "select pr from ProjectApplicationReview pr left join fetch pr.project left join fetch pr.reviewer where pr.type = '" + projectType +"' and pr.year = :year " + (rejectExpertIds.isEmpty() ? "" : " and pr.reviewer.id not in (:rejectExpertIds) ");
		existingMatches = dao.query(hql4ExistingMatch, paraMap);
		System.out.println("查询[已有匹配]用时: " + (new Date().getTime() - begin.getTime()) + "ms");
		System.out.println("查出[已有匹配]数目: " + existingMatches.size() + "\n");
		return existingMatches;
	}
	
	/**
	 * 找出待剔除的匹配条目
	 * （共用方法）
	 * @param projectType 项目类型
	 * @param rejectExpertIds
	 * @param paraMap
	 * @return
	 */
	public List<ProjectApplicationReview> getRejectMatcher(String projectType, List<String> rejectExpertIds, Map paraMap){
		List<ProjectApplicationReview> rejectMatches = new ArrayList<ProjectApplicationReview>();
		Date begin = new Date();
		String hql4RejectMatch = "select pr from ProjectApplicationReview pr left join fetch pr.project left join fetch pr.reviewer where pr.type = '" + projectType + "' and pr.year = :year " + (rejectExpertIds.isEmpty() ? " and 1 = 0" : " and pr.reviewer.id in (:rejectExpertIds) ");
		rejectMatches = dao.query(hql4RejectMatch, paraMap);
		System.out.println("查询[待剔除匹配]用时: " + (new Date().getTime() - begin.getTime()) + "ms");
		System.out.println("查出[待剔除匹配]数目: " + rejectMatches.size() + "\n");
		return rejectMatches;
	}
	
	/**
	 * 根据匹配器返回的Map形式的匹配结果得到smas的ProjectApplicationReview实体bean
	 * (公共方法)
	 * @param match 不含项目类别信息
	 * @return
	 */
	private ProjectApplicationReview getPRBean(MatchPair matchPair ,String projectType) {
		Reviewer reviewer = (Reviewer) matchPair.getReviewer();
		Subject subject = (Subject) matchPair.getSubject();
		Integer year = (Integer) subject.getIntrinsicProperty(YEAR);
		return new ProjectApplicationReview(reviewer.getIntrinsicProperty(ID), subject.getIntrinsicProperty(ID), 0, year, projectType);
	}
	
	
}
