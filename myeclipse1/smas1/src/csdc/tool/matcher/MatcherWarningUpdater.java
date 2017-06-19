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

import csdc.bean.AwardApplication;
import csdc.bean.AwardApplicationReview;
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
 * 负责更新匹配警告信息
 * 本质上也是通过调用匹配器来实现，但是禁止新增匹配结果
 * @param <ProjectType>	项目类型（General, Instp等）
 * @param <MatchType>	匹配评审（GeneralReviewer, InstpReviewer等）
 */
public class MatcherWarningUpdater {
	
	@Autowired
	private HibernateBaseDao dao;

	@Autowired
	protected IProjectService projectService;//项目管理接口
	
	//专家
	private List<Expert> experts;
	//项目
	private List<ProjectApplication> projects;
	private List<ProjectApplicationReview> projectMatches;
	//奖励
	private List<AwardApplication> awards;
	private List<AwardApplicationReview> awardMatches;
	//公共
	private MatcherBeanTransformer transformer;
	private DisciplineBasedMatcher matcher;
	
	/**
	 * 类型
	 * 项目：general; instp
	 * 奖励：moeSocial
	 */
	private String type;
	
	@Autowired
	private GeneralService generalService;

	
	public MatcherWarningUpdater() {}

	/**
	 * 对外主方法
	 * @param objectType [general, instp; moeSocial]
	 * @param objectIds [项目ids；奖励ids]
	 * @param year
	 */
	public void update(String objectType, List<String> objectIds, int year) {
		this.type = objectType;
		
		System.out.println("\n开始更新警告信息...");
		long begin = System.currentTimeMillis();

		initMainData(objectIds, year);
		match();
		updateWarning();
		
		System.out.println("警告信息更新完成！耗时：" + (System.currentTimeMillis() - begin) + "ms");
	}

	/**
	 * 对所选项目，奖励信息，更新警告信息 
	 */
	private void updateWarning() {
		if (this.type.equals("general") || this.type.equals("instp") ) {
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
		} else if (this.type.equals("moeSocial")) {
			for (AwardApplication award : awards) {
				Subject subject = transformer.getSubject(award);
				
				Set warnings = (Set) subject.getAlterableProperty(WARNINGS);
				String oldWarnings = award.getWarningReviewer() + "";
				String newWarnings = warnings + "";
				
				/*
				 * 只对警告信息变化了的的项目进行修改，以加快写入速度
				 */
				if (!oldWarnings.equals(newWarnings)) {
					if (warnings == null || warnings.isEmpty()) {
						newWarnings = null;
					}
					award.setWarningReviewer(newWarnings);
					dao.modify(award);
				}
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
		Integer ministryExpertNumber = null;//每个评审对象中部级专家评审数量
		Integer localExpertNumber = null;//每个对象中地方专家评审数量
		
		/*
		 * 通过项目的类型来选取不同的匹配参数，这里有待优化
		 */
		if (this.type.equals("general")){
			/*
			 * 一般项目匹配参数
			 */
			expertProjectMin = (Integer) application.get("GeneralExpertProjectMin");
			expertProjectMax = (Integer) application.get("GeneralExpertProjectMax");
			ministryExpertNumber = (Integer) application.get("GeneralMinistryExpertNumber");
			localExpertNumber = (Integer) application.get("GeneralLocalExpertNumber");
		} else if (this.type.equals("instp")){
			/*
			 * 基地项目匹配参数
			 */
			expertProjectMin = (Integer) application.get("InstpExpertProjectMin");
			expertProjectMax = (Integer) application.get("InstpExpertProjectMax");
			ministryExpertNumber = (Integer) application.get("InstpMinistryExpertNumber");
			localExpertNumber = (Integer) application.get("InstpLocalExpertNumber");
		} else if (this.type.equals("moeSocial")) {
			/*
			 * 奖励匹配参数
			 */
			expertProjectMin = (Integer) application.get("AwardExpertProjectMin");
			expertProjectMax = (Integer) application.get("AwardExpertProjectMax");
			ministryExpertNumber = (Integer) application.get("AwardMinistryExpertNumber");
			localExpertNumber = (Integer) application.get("AwardLocalExpertNumber");
		}
		
		Integer ExpertNumber = ministryExpertNumber + localExpertNumber;
		//转换器设置
		if (this.type.equals("general") || this.type.equals("instp") ) {
			transformer = new MatcherBeanTransformer(projects, experts, projectMatches,type, dao);
		} else if (this.type.equals("moeSocial")) {
			transformer = new MatcherBeanTransformer();
			transformer.transFormerAward(awards, experts, awardMatches, type, dao);
		}
		
		//实例化匹配器
		matcher = new DisciplineBasedMatcher(new Selector() {
			public Integer findGroupStatus(Integer constraintLevel) {
				return 2;
			}
		}, expertProjectMax, expertProjectMin, ExpertNumber);
		//-----------------
		matcher.setType(type);//设置类型general, instp; moeSocial
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
		matcher.addConstraint(new UniversityMinistryLocalRatio(ministryExpertNumber, localExpertNumber));
		
		////////////////////////////////////////////////////////////////////////////////////////

		matcher.work();
		
	}
	
	
	/**
	 * 从数据库查询projects、experts、matches
	 * @param ojbectIds 需要更新警告的项目(或者奖励)id，如果为空，则更新当年所有项目（奖励）ids
	 * @param year
	 */
	private void initMainData(List<String> ojbectIds, int year) {
		Map paraMap = new HashMap();
		if (this.type.equals("general") || this.type.equals("instp")) {
			//项目
			if (ojbectIds == null) {
				ojbectIds = new ArrayList<String>();
			}
			paraMap.put("projectIds", ojbectIds);
			paraMap.put("year", year);

			if (ojbectIds.size() > 0 && ojbectIds.size() < 100) {
				/*
				 * 找出项目、匹配对，如果给出的项目id较少，则按id查询。
				 */
				projects = dao.query("select project from ProjectApplication project where project.year = :year and project.id in (:projectIds) and project.type = '" + this.type + "'", paraMap);
				projectMatches = dao.query("select match from ProjectApplicationReview match left join fetch match.reviewer left join fetch match.project where match.year = :year and match.project.id in (:projectIds) and match.type = '" + this.type + "'", paraMap);
			} else {
				/*
				 * 否则查出当年的所有项目及其现有匹配对
				 */
				projects = dao.query("select project from ProjectApplication project where project.year = :year and project.type = '" + this.type + "'", paraMap);
				projectMatches = dao.query("select match from ProjectApplicationReview match left join fetch match.reviewer left join fetch match.project where match.year = :year and match.type = '" + this.type + "'", paraMap);
			}
		} else  if (this.type.equals("moeSocial")) {
			//奖励
			if (ojbectIds == null) {
				ojbectIds = new ArrayList<String>();
			}
			paraMap.put("awardIds", ojbectIds);
			paraMap.put("year", year);

			if (ojbectIds.size() > 0 && ojbectIds.size() < 100) {
				/*
				 * 找出项目、匹配对，如果给出的奖励id较少，则按id查询。
				 */
				try {
					awards = dao.query("select aa from AwardApplication aa where aa.year = :year and aa.id in (:awardIds) and aa.type = '" + this.type + "'", paraMap);
					awardMatches = dao.query("select aar from AwardApplicationReview aar left join fetch aar.reviewer left join fetch aar.award where aar.year = :year and aar.award.id in (:awardIds) and aar.type = '" + this.type + "'", paraMap);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				/*
				 * 否则查出当年的所有奖励及其现有匹配对
				 */
				awards = dao.query("select aa from AwardApplication aa where aa.year = :year and aa.type = '" + this.type + "'", paraMap);
				awardMatches = dao.query("select aar from AwardApplicationReview aar left join fetch aar.reviewer left join fetch aar.award where aar.year = :year and aar.type = '" + this.type + "'", paraMap);
			}
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
//		experts = getRelatedExpert(this.type);
		experts = dao.query("select expert from Expert expert");
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
	 * (该方法不用)
	 * @param projectType
	 * @return
	 */
	private List<Expert> getRelatedExpert(String type) {
		List<Expert> experts = null;//返回专家
		Map paraMap = new HashMap();
		Map disciplineMap = projectService.selectSpecialityTitleInfo();
		paraMap.put("aboveSubSeniorTitles", disciplineMap.get("aboveSubSeniorTitles"));	//副高级职称以上专家
		paraMap.put("seniorTitles", disciplineMap.get("seniorTitles"));	//正高级、高级职称专家
		StringBuffer hql4Reviewer = new StringBuffer("select expert from Expert expert, University u where expert.universityCode = u.code");
		if (type.equals("general")) {
			hql4Reviewer.append(getExpertGeneralHql());
		} else if (type.equals("instp")) {
			hql4Reviewer.append(getExpertInstpHql());
		} else if (type.equals("moeSocial")) {
			//奖励专家条件目前和基地保持一致
			//TODO 
			hql4Reviewer.append(getExpertAwardHql());
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
		//筛选所属高校办学类型为11和12，属性为参评、非重点人、专职人员，手机和邮箱全非空，当前年没申请项目， 评价等级大于限制阈值，当前时间6个月内更新入库的专家
		StringBuffer hql4Reviewer = new StringBuffer(" and (((u.style like '11%' or u.style like '12%') and expert.expertType = 1)");			//所属高校办学类型为11和12的内部专家
		hql4Reviewer.append(" or expert.expertType = 2)");																//或所有外部专家
		hql4Reviewer.append(" and u.useExpert = 1");			                                                        //使用该校评审专家
		hql4Reviewer.append(" and ((u.useViceExpert = 1 and expert.specialityTitle in (:aboveSubSeniorTitles)) or (expert.specialityTitle in (:seniorTitles)))");             	//使用该校副高级职称专家			
//		hql4Reviewer.append(" and u.code in (:selectedUnivCodes)");                                                     //使用有参与评审专家的高校
		hql4Reviewer.append(" and expert.isReviewer = 1 and expert.isKey = 0 and expert.type = '专职人员'");				//参评，非重点人，专职人员
		hql4Reviewer.append(" and expert.email is not null and expert.mobilePhone is not null");						//手机和邮箱全非空
		hql4Reviewer.append(" and (expert.generalApplyYears is null or expert.generalApplyYears not like '%" + defaultYear + "%')");	//当前年没申请项目
		hql4Reviewer.append(" and expert.rating > 0 ");												                        //评价等级大于限制阈值（数字越大，评价等级越高）
		hql4Reviewer.append(" and expert.discipline is not null ");	
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
		//筛选所属高校办学类型为11和12，属性为参评、非重点人、专职人员，手机和邮箱全非空，当前年没申请项目， 评价等级大于限制阈值，当前时间6个月内更新入库的专家
		StringBuffer hql4Reviewer = new StringBuffer(" and (((u.style like '11%' or u.style like '12%') and (u.founderCode in ('308', '339', '360', '435')) and expert.expertType = 1)");//所属高校办学类型为11和12且为部属高校的内部专家
		hql4Reviewer.append(" or expert.expertType = 2)");																//或所有外部专家
		hql4Reviewer.append(" and expert.specialityTitle in (:seniorTitles)");	                                    //正高级职称专家	
		hql4Reviewer.append(" and expert.isReviewer = 1 and expert.isKey = 0 and expert.type = '专职人员'");				//参评，非重点人，专职人员
		hql4Reviewer.append(" and expert.email is not null and expert.mobilePhone is not null");						//手机和邮箱全非空
		hql4Reviewer.append(" and (expert.instpApplyYears is null or expert.instpApplyYears not like '%" + defaultYear + "%')");	//当前年没申请项目
		hql4Reviewer.append(" and expert.rating > " + 0 + " ");												//评价等级大于限制阈值
		hql4Reviewer.append(" and expert.discipline is not null ");	
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
	
	private String getExpertAwardHql() {
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		Integer defaultYear = (Integer) session.get("defaultYear");
		Map application = ActionContext.getContext().getApplication();
		String awardReviewerImportedStartDate = (String) application.get("AwardReviewerImportedStartDate");
		String awardReviewerImportedEndDate = (String) application.get("AwardReviewerImportedEndDate");
		String awardReviewerBirthdayStartDate = (String) application.get("AwardpReviewerBirthdayStartDate");
		String awardReviewerBirthdayEndDate = (String) application.get("AwardReviewerBirthdayEndDate");
		//筛选所属高校办学类型为11和12，属性为参评、非重点人、专职人员，手机和邮箱全非空，当前年没申请项目， 评价等级大于限制阈值，当前时间6个月内更新入库的专家
		StringBuffer hql4Reviewer = new StringBuffer(" and (((u.style like '11%' or u.style like '12%') and u.founderCode in ('308', '339', '360', '435') and expert.expertType = 1 ");
		if (awardReviewerImportedStartDate != null && !awardReviewerImportedStartDate.equals("不限")) {
			hql4Reviewer.append(" and expert.importedDate > to_date('" + awardReviewerImportedStartDate + "','yyyy-mm-dd') ");	    //设置一般项目_评审专家_导入_开始时间         
		}
		if (awardReviewerImportedEndDate != null && !awardReviewerImportedEndDate.equals("不限")) {
			hql4Reviewer.append(" and expert.importedDate < to_date('" + awardReviewerImportedEndDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_导入_结束时间
		};//所属高校办学类型为11和12且为部属高校的内部专家, 且 只对内部专家的导入时间做限制
		hql4Reviewer.append(" ) or expert.expertType = 2)");
		hql4Reviewer.append(" and expert.specialityTitle in (:seniorTitles)");	                                    //正高级职称专家	
		hql4Reviewer.append(" and expert.isReviewer = 1 and expert.isKey = 0 and expert.type = '专职人员'");				//参评，非重点人，专职人员
		hql4Reviewer.append(" and expert.email is not null and expert.mobilePhone is not null");						//手机和邮箱全非空
		hql4Reviewer.append(" and (expert.instpApplyYears is null or expert.instpApplyYears not like '%" + defaultYear + "%')");	//当前年没申请项目
		hql4Reviewer.append(" and expert.rating > " + 0 + " ");												//评价等级大于限制阈值
		hql4Reviewer.append(" and expert.discipline is not null ");	
		if (awardReviewerBirthdayStartDate != null && !awardReviewerBirthdayStartDate.equals("不限")) {
			hql4Reviewer.append(" and expert.birthday > to_date('" + awardReviewerBirthdayStartDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_出生日期_开始时间
		}
		if (awardReviewerBirthdayEndDate != null && !awardReviewerBirthdayEndDate.equals("不限")) {
			hql4Reviewer.append(" and expert.birthday < to_date('" + awardReviewerBirthdayEndDate + "','yyyy-mm-dd') ");		        //设置一般项目_评审专家_出生日期_结束时间
		}
		return hql4Reviewer.toString();
	}
}
