package csdc.tool.matcher;

import static csdc.tool.matcher.MatcherInfo.WARNINGS;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.dao.HibernateBaseDao;
import csdc.service.IProjectService;
import csdc.service.imp.GeneralService;
import csdc.tool.matcher.constraint.ForbidAll;
import csdc.tool.matcher.constraint.ReviewersDiffer;
import csdc.tool.matcher.constraint.ReviewersUniversityDiffer;
import csdc.tool.matcher.constraint.SpecialDisciplineRetreatPrinciple;
import csdc.tool.matcher.constraint.SubjectMemberReviewerNameDiffer;
import csdc.tool.matcher.constraint.SubjectMemberReviewerUniversityDiffer;
import csdc.tool.matcher.constraint.SubjectReviewerUniversityDiffer;
import csdc.tool.matcher.constraint.UniversityMinistryLocalRatio;

/**
 * 负责更新项目的匹配警告信息
 * 本质上也是通过调用匹配器来实现，但是禁止新增匹配结果
 * @param <ProjectType>	项目类型（General, Instp等）
 * @param <MatchType>	匹配评审（GeneralReviewer, InstpReviewer等）
 */
public class MatcherWarningUpdater {
	
	@Autowired
	private HibernateBaseDao dao;

	@Autowired
	protected IProjectService projectService;//项目管理接口
	private List<ProjectApplication> projects;
	private List<Expert> experts;
	private List<ProjectApplicationReview> matches;
	
	private MatcherBeanTransformer transformer;
	private DisciplineBasedMatcher matcher;
	
	//项目类型
	private String projectType;
	
	@Autowired
	private GeneralService generalService;

	
	public MatcherWarningUpdater() {}

	/**
	 * 对外主方法
	 * @param projectIds
	 * @param year
	 */
	public void update(String projectType, List<String> projectIds, int year) {
		this.projectType = projectType;
		
		System.out.println("\n开始更新警告信息...");
		long begin = System.currentTimeMillis();

		initMainData(projectIds, year);
		match();
		updateWarning();
		
		System.out.println("警告信息更新完成！耗时：" + (System.currentTimeMillis() - begin) + "ms");
	}

	/**
	 * 对所选项目信息，更新警告信息 
	 */
	private void updateWarning() {
		for (ProjectApplication project : projects) {
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
	}

	/**
	 * 使用匹配器来更新警告信息
	 * 通过添加ForbidAll限制条件以保证过程中不会新增匹配条目
	 */
	private void match() {
		//获取匹配器参数
		Map application = ActionContext.getContext().getApplication();
		Integer expertProjectMin = null;
		Integer expertProjectMax = null;
		Integer projectMinistryExpertNumber = null;
		Integer projectLocalExpertNumber = null;
		
		/*
		 * 通过项目的类型来选取不同的匹配参数，这里有待优化
		 */
		if (this.projectType.equals("general")){
			/*
			 * 一般项目匹配参数
			 */
			expertProjectMin = (Integer) application.get("GeneralExpertProjectMin");
			expertProjectMax = (Integer) application.get("GeneralExpertProjectMax");
			projectMinistryExpertNumber = (Integer) application.get("GeneralMinistryExpertNumber");
			projectLocalExpertNumber = (Integer) application.get("GeneralLocalExpertNumber");
		}else if (this.projectType.equals("instp")){
			/*
			 * 基地项目匹配参数
			 */
			expertProjectMin = (Integer) application.get("InstpExpertProjectMin");
			expertProjectMax = (Integer) application.get("InstpExpertProjectMax");
			projectMinistryExpertNumber = (Integer) application.get("InstpMinistryExpertNumber");
			projectLocalExpertNumber = (Integer) application.get("InstpLocalExpertNumber");
		}
		Integer projectExpertNumber = projectMinistryExpertNumber + projectLocalExpertNumber;
	
		transformer = new MatcherBeanTransformer(projects, experts, matches, dao);
		
		//实例化匹配器
		matcher = new DisciplineBasedMatcher(new Selector() {
			public Integer findGroupStatus(Integer constraintLevel) {
				return 2;
			}
		}, expertProjectMax, expertProjectMin, projectExpertNumber);
		
		//设置数据（项目，专家，已有匹配）
		matcher.setSubjects(transformer.getSubjects());
		matcher.setReviewers(transformer.getReviewers());
		matcher.setExistingMatchPairs(transformer.getMatchPairs());

		
		////////////////////////////////////////////////////////////////////////////////////////
		
		//限制级上限 
		matcher.setConstraintLimit(0);
		
		/*
		 * 将所有突破则希望给出警告信息的限制条件都加上
		 * 
		 * 其中第一个限制条件是禁止任何新匹配，以保证只更新警告信息，但不影响匹配结果。
		 * 同时，这个限制条件不给出警告信息。
		 */
		matcher.addConstraint(new ForbidAll());
		matcher.addConstraint(new ReviewersDiffer());
		matcher.addConstraint(new ReviewersUniversityDiffer());
		matcher.addConstraint(new SpecialDisciplineRetreatPrinciple());
		matcher.addConstraint(new SubjectMemberReviewerNameDiffer());
		matcher.addConstraint(new SubjectMemberReviewerUniversityDiffer());
		matcher.addConstraint(new SubjectReviewerUniversityDiffer());
		matcher.addConstraint(new UniversityMinistryLocalRatio(projectMinistryExpertNumber, projectLocalExpertNumber));
		
		////////////////////////////////////////////////////////////////////////////////////////

		matcher.work();
		
	}
	
	
	/**
	 * 从数据库查询projects、experts、matches
	 * @param projectIds 需要更新警告的项目id，如果为空，则更新当年所有项目ids
	 * @param year
	 */
	private void initMainData(List<String> projectIds, int year) {
		Map paraMap = new HashMap();
		if (projectIds == null) {
			projectIds = new ArrayList<String>();
		}
		paraMap.put("projectIds", projectIds);
		paraMap.put("year", year);

		if (projectIds.size() > 0 && projectIds.size() < 100) {
			/*
			 * 找出项目、匹配对，如果给出的项目id较少，则按id查询。
			 */
			projects = dao.query("select project from ProjectApplication project where project.year = :year and project.id in (:projectIds) and project.type = '" + this.projectType + "'", paraMap);
			matches = dao.query("select match from ProjectApplicationReview match left join fetch match.reviewer left join fetch match.project where match.year = :year and match.project.id in (:projectIds) and match.type = '" + this.projectType + "'", paraMap);
		} else {
			/*
			 * 否则查出当年的所有项目及其现有匹配对
			 */
			projects = dao.query("select project from ProjectApplication project where project.year = :year and project.type = '" + this.projectType + "'", paraMap);
			matches = dao.query("select match from ProjectApplicationReview match left join fetch match.reviewer left join fetch match.project where match.year = :year and match.type = '" + this.projectType + "'", paraMap);
		}
		
		System.out.println("查询专家···");
		//查询所有专家时间太长，对专家的条件进行限制，提高查询速度
		// TODO wang
//		Date begin = new Date();
//		experts = dao.query("select expert from Expert expert");
//		System.out.println("原有：查询[专家]用时: " + (zhongDate.getTime() - begin.getTime()) + "ms");
//		System.out.println("查出[专家]数目: " + experts.size() + "\n");
//		dao.clear();
		Date zhongDate = new Date();
		experts = getRelatedExpert(this.projectType);
//		experts = dao.query("select expert from Expert expert");
		Date nowDate = new Date();
		System.out.println("改进：查询[专家]用时: " + (nowDate.getTime() - zhongDate.getTime()) + "ms");
		System.out.println("查出[专家]数目: " + experts.size() + "\n");
		System.out.println("查询专家结束。");
		/*
		 * 为节约内存，从一级缓存中清除之
		 */
		dao.clear();
	}
	
	/**
	 * 获取与项目类型相关的满足查询条件的专家
	 * 提高查询效率
	 * @param projectType
	 * @return
	 */
	private List<Expert> getRelatedExpert(String projectType) {
		List<Expert> experts = null;//返回专家
		Map paraMap = new HashMap();
		Map disciplineMap = projectService.selectSpecialityTitleInfo();
		paraMap.put("aboveSubSeniorTitles", disciplineMap.get("aboveSubSeniorTitles"));	//副高级职称以上专家
		paraMap.put("seniorTitles", disciplineMap.get("seniorTitles"));	//正高级、高级职称专家
		StringBuffer hql4Reviewer = new StringBuffer("select expert from Expert expert, University u where expert.universityCode = u.code");
		if (projectType.equals("general")) {
			hql4Reviewer.append(getExpertGeneralHql());
		} else if (projectType.equals("instp")) {
			hql4Reviewer.append(getExpertInstpHql());
		}
		experts = dao.query(hql4Reviewer.toString(), paraMap);
		return experts;
	}
	/**
	 * 专家限制条件
	 * @return
	 */
	private String getExpertGeneralHql() {
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		Integer defaultYear = (Integer) session.get("defaultYear");
		Map application = ActionContext.getContext().getApplication();
		String generalReviewerImportedStartDate = (String) application.get("GeneralReviewerImportedStartDate");
		String generalReviewerImportedEndDate = (String) application.get("GeneralReviewerImportedEndDate");
		String generalReviewerBirthdayStartDate = (String) application.get("GeneralReviewerBirthdayStartDate");
		String generalReviewerBirthdayEndDate = (String) application.get("GeneralReviewerBirthdayEndDate");
		//筛选所属高校办学类型为11和12，属性为参评、非重点人、专职人员，手机和邮箱全非空，当前年没申报项目， 评价等级大于限制阈值，当前时间6个月内更新入库的专家
		StringBuffer hql4Reviewer = new StringBuffer(" and (((u.style like '11%' or u.style like '12%') and expert.expertType = 1)");			//所属高校办学类型为11和12的内部专家
		hql4Reviewer.append(" or expert.expertType = 2)");																//或所有外部专家
		hql4Reviewer.append(" and u.useExpert = 1");			                                                        //使用该校评审专家
		hql4Reviewer.append(" and ((u.useViceExpert = 1 and expert.specialityTitle in (:aboveSubSeniorTitles)) or (expert.specialityTitle in (:seniorTitles)))");             	//使用该校副高级职称专家			
//		hql4Reviewer.append(" and u.code in (:selectedUnivCodes)");                                                     //使用有参与评审专家的高校
		hql4Reviewer.append(" and expert.isReviewer = 1 and expert.isKey = 0 and expert.type = '专职人员'");				//参评，非重点人，专职人员
		hql4Reviewer.append(" and expert.email is not null and expert.mobilePhone is not null");						//手机和邮箱全非空
		hql4Reviewer.append(" and (expert.generalApplyYears is null or expert.generalApplyYears not like '%" + defaultYear + "%')");	//当前年没申报项目
		hql4Reviewer.append(" and expert.rating > 0 ");												                        //评价等级大于限制阈值（数字越大，评价等级越高）	
		if (generalReviewerImportedStartDate != null && !generalReviewerImportedStartDate.equals("不限")) {
			hql4Reviewer.append(" and expert.importedDate > to_date('" + generalReviewerImportedStartDate + "','yyyy-mm-dd') ");	    //设置一般项目_评审专家_导入_开始时间         
		}
		if (generalReviewerImportedEndDate != null && !generalReviewerImportedEndDate.equals("不限")) {
			hql4Reviewer.append(" and expert.importedDate < to_date('" + generalReviewerImportedEndDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_导入_结束时间
		}
		if (generalReviewerBirthdayStartDate != null && !generalReviewerBirthdayStartDate.equals("不限")) {
			hql4Reviewer.append(" and expert.birthday > to_date('" + generalReviewerBirthdayStartDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_出生日期_开始时间
		}
		if (generalReviewerBirthdayEndDate != null && !generalReviewerBirthdayEndDate.equals("不限")) {
			hql4Reviewer.append(" and expert.birthday < to_date('" + generalReviewerBirthdayEndDate + "','yyyy-mm-dd') ");		        //设置一般项目_评审专家_出生日期_结束时间
		}
		return hql4Reviewer.toString();
	}
	
	private String getExpertInstpHql() {
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		Integer defaultYear = (Integer) session.get("defaultYear");
		Map application = ActionContext.getContext().getApplication();
		String instpReviewerImportedStartDate = (String) application.get("InstpReviewerImportedStartDate");
		String instpReviewerImportedEndDate = (String) application.get("InstpReviewerImportedEndDate");
		String instpReviewerBirthdayStartDate = (String) application.get("InstpReviewerBirthdayStartDate");
		String instpReviewerBirthdayEndDate = (String) application.get("InstpReviewerBirthdayEndDate");
		//筛选所属高校办学类型为11和12，属性为参评、非重点人、专职人员，手机和邮箱全非空，当前年没申报项目， 评价等级大于限制阈值，当前时间6个月内更新入库的专家
		StringBuffer hql4Reviewer = new StringBuffer(" and (((u.style like '11%' or u.style like '12%') and (u.founderCode in ('308', '339', '360', '435')) and expert.expertType = 1)");//所属高校办学类型为11和12且为部属高校的内部专家
		hql4Reviewer.append(" or expert.expertType = 2)");																//或所有外部专家
		hql4Reviewer.append(" and expert.specialityTitle in (:seniorTitles)");	                                    //正高级职称专家	
		hql4Reviewer.append(" and expert.isReviewer = 1 and expert.isKey = 0 and expert.type = '专职人员'");				//参评，非重点人，专职人员
		hql4Reviewer.append(" and expert.email is not null and expert.mobilePhone is not null");						//手机和邮箱全非空
		hql4Reviewer.append(" and (expert.instpApplyYears is null or expert.instpApplyYears not like '%" + defaultYear + "%')");	//当前年没申报项目
		hql4Reviewer.append(" and expert.rating > " + 0 + " ");												//评价等级大于限制阈值
		if (instpReviewerImportedStartDate != null && !instpReviewerImportedStartDate.equals("不限")) {
			hql4Reviewer.append(" and expert.importedDate > to_date('" + instpReviewerImportedStartDate + "','yyyy-mm-dd') ");	    //设置一般项目_评审专家_导入_开始时间         
		}
		if (instpReviewerImportedEndDate != null && !instpReviewerImportedEndDate.equals("不限")) {
			hql4Reviewer.append(" and expert.importedDate < to_date('" + instpReviewerImportedEndDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_导入_结束时间
		}
		if (instpReviewerBirthdayStartDate != null && !instpReviewerBirthdayStartDate.equals("不限")) {
			hql4Reviewer.append(" and expert.birthday > to_date('" + instpReviewerBirthdayStartDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_出生日期_开始时间
		}
		if (instpReviewerBirthdayEndDate != null && !instpReviewerBirthdayEndDate.equals("不限")) {
			hql4Reviewer.append(" and expert.birthday < to_date('" + instpReviewerBirthdayEndDate + "','yyyy-mm-dd') ");		        //设置一般项目_评审专家_出生日期_结束时间
		}
		return hql4Reviewer.toString();
	}
}
