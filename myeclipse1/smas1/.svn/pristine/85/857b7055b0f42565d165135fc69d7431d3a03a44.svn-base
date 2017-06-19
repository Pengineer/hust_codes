package csdc.tool.matcher;

import static csdc.tool.matcher.MatcherInfo.ID;
import static csdc.tool.matcher.MatcherInfo.YEAR;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.dao.HibernateBaseDao;
import csdc.service.imp.GeneralService;

/**
 * 一般项目专家评审自动匹配器，封装了对MultiRoundsMatcher的调用
 * @author xuhan
 *
 */
@Component
@Scope("prototype")
public class GeneralReviewerMatcher extends ProjectReviewerMatcher {
	
//	private String srcPath = GeneralReviewerMatcher.class.getClassLoader().getResource("matherLog.txt").getPath().replace("%20", " ");
//	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String projectType = "general";
	
	@Autowired
	private HibernateBaseDao dao;
	@Autowired
	private GeneralService generalService;

//	/////////////////////////////////////////////////////////////
//	
//	
//	/**
//	 * 每位专家若参评，至少需参评的项目数
//	 */
//	private Integer expertProjectMin;
//	
//	/**
//	 * 每位专家评审的最大项目数
//	 */
//	private Integer expertProjectMax;
//	
//	/**
//	 * 每个项目所需部属高校评审专家数
//	 */
//	private Integer projectMinistryExpertNumber;
//	
//	/**
//	 * 每个项目所需地方高校评审专家数
//	 */
//	private Integer projectLocalExpertNumber;
//	
//	/**
//	 * 每个项目所需的评审专家数
//	 */
//	private Integer projectExpertNumber;
//	
//	/**
//	 * 每个高校若有专家参评，建议至少应参评的专家数
//	 */
//	private Integer universityExpertMin;
//	
//	/**
//	 * 每个高校若有专家参评，建议至多应参评的专家数
//	 */
//	private Integer universityExpertMax;
//	
	
	public GeneralReviewerMatcher() {
		Map application = ActionContext.getContext().getApplication();
	
		expertProjectMin = (Integer) application.get("GeneralExpertProjectMin");
		
		expertProjectMax = (Integer) application.get("GeneralExpertProjectMax");
		
		universityExpertMin = (Integer) application.get("GeneralUniversityExpertMin");
		
		universityExpertMax = (Integer) application.get("GeneralUniversityExpertMax");

		projectMinistryExpertNumber = (Integer) application.get("GeneralMinistryExpertNumber");
		
		projectLocalExpertNumber = (Integer) application.get("GeneralLocalExpertNumber");
		
		projectExpertNumber = projectMinistryExpertNumber + projectLocalExpertNumber;
		
	}
	
	/**
	 * 根据匹配器返回的Map形式的匹配结果得到ProjectApplicationReview实体bean
	 * @param match
	 * @return
	 */
	private ProjectApplicationReview getPRBean(MatchPair matchPair) {
		Reviewer reviewer = matchPair.getReviewer();
		Subject subject = matchPair.getSubject();
		Integer year = (Integer) subject.getIntrinsicProperty(YEAR);
		return new ProjectApplicationReview(reviewer.getIntrinsicProperty(ID), subject.getIntrinsicProperty(ID), 0, year, "general");
	}
	
	public List<Expert> getReviewerInfo(List<String> expertIds, List<String> projectIds, List<String> rejectExpertIds, Map paraMap){
		List<Expert> experts = new ArrayList<Expert>();
		Map disciplineMap = generalService.selectSpecialityTitleInfo();
		paraMap.put("aboveSubSeniorTitles", disciplineMap.get("aboveSubSeniorTitles"));	//副高级职称以上专家
		paraMap.put("seniorTitles", disciplineMap.get("seniorTitles"));	//正高级、高级职称专家
		
		//1、筛选所属高校办学类型为11和12，属性为参评、非重点人、专职人员，手机和邮箱全非空，当前年没申请项目， 评价等级大于限制阈值，当前时间6个月内更新入库的专家
		Date begin = new Date();
		StringBuffer hql4Reviewer = new StringBuffer("select expert from Expert expert, University u where expert.universityCode = u.code ");
		if (expertIds.size() == 0) {//如果未指定待匹配专家
			hql4Reviewer.append(generalService.selectReviewMatchExpert());//获取参与匹配专家的通用方法
		}else if (expertIds.size() > 0) {//如果指定了待匹配专家
			hql4Reviewer.append(" and expert.id in (:expertIds)");
		}
		if (rejectExpertIds.size() > 0) {//如果指定了待剔除专家
			hql4Reviewer.append(" and expert.id not in (:rejectExpertIds)");
		}
		hql4Reviewer.append(" order by expert.id ");
		experts = dao.query(hql4Reviewer.toString(), paraMap);
		System.out.println("查询[专家]用时: " + (new Date().getTime() - begin.getTime()) + "ms");
		System.out.println("查出[专家]数目: " + experts.size() + "\n");
		return experts;
	}
	
	/**
	 * 筛选出当前年度待匹配的项目
	 * @param projectIds
	 * @param rejectExpertIds
	 * @param paraMap
	 * @return
	 */
	public List<ProjectApplication> getSubjectInfo(List<String> projectIds, List<String> rejectExpertIds, Map paraMap){
		List<ProjectApplication> projects = new ArrayList<ProjectApplication>();
		Date begin = new Date();
		String hql4Project = null;
		if (projectIds.size() == 0) {
			//若没有指定匹配的项目，则选取没有匹配完全和未开始匹配的项目。做法是查出这些项目的id，用in子句筛选这些项目
			String hql4ProjectId = "select project.id from ProjectApplication project left join project.applicationReview pr where project.type = 'general' and project.year = :year and project.isReviewable = 1 " + (rejectExpertIds.isEmpty() ? "" : " and pr.reviewer.id not in (:rejectExpertIds) ") + " group by project.id having count(*) < :projectExpertNumber";
			projectIds = dao.query(hql4ProjectId, paraMap);
			if (projectIds.size() >= 1000) {
				//若未匹配完全和未开始匹配的项目太多，则in效率太低，放弃使用in，而是选取所有当前年度项目。
				projectIds = new ArrayList<String>();
			}
			paraMap.put("projectIds", projectIds);	//projectIds有更新，需重新放入paraMap
		}
		if (projectIds.size() > 0) {
			//选取id在projectIds内的项目（用in子句筛选这些项目）
			hql4Project = "select project from ProjectApplication project where project.type = 'general' and project.year = :year and project.isReviewable = 1 and project.id in (:projectIds)";
		} else {
			//选取所有当前年度项目-重新进行匹配
			hql4Project = "select project from ProjectApplication project where project.type = 'general' and project.year = :year and project.isReviewable = 1";
		}
		try {
			projects = dao.query(hql4Project, paraMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("查询[项目]用时: " + (new Date().getTime() - begin.getTime()) + "ms");
		System.out.println("查出[项目]数目: " + projects.size() + "\n");
		return projects;
	}
	
	/**
	 * 找出和experts或projects关联的已有匹配条目(不包含待剔除条目)
	 * @param rejectExpertIds
	 * @param paraMap
	 * @return
	 */
	public List<ProjectApplicationReview> getExistingMatcher(List<String> rejectExpertIds, Map paraMap){
		List<ProjectApplicationReview> existingMatches = new ArrayList<ProjectApplicationReview>();
		Date begin = new Date();
		String hql4ExistingMatch = "select pr from ProjectApplicationReview pr join fetch pr.project join fetch pr.reviewer where pr.type = 'general' and pr.year = :year " + (rejectExpertIds.isEmpty() ? "" : " and pr.reviewer.id not in (:rejectExpertIds) ");
		existingMatches = dao.query(hql4ExistingMatch, paraMap);
		System.out.println("查询[已有匹配]用时: " + (new Date().getTime() - begin.getTime()) + "ms");
		System.out.println("查出[已有匹配]数目: " + existingMatches.size() + "\n");
		return existingMatches;
	}
	
	/**
	 * 找出待剔除的匹配条目
	 * @param rejectExpertIds
	 * @param paraMap
	 * @return
	 */
	public List<ProjectApplicationReview> getRejectMatcher(List<String> rejectExpertIds, Map paraMap){
		List<ProjectApplicationReview> rejectMatches = new ArrayList<ProjectApplicationReview>();
		Date begin = new Date();
		String hql4RejectMatch = "select pr from ProjectApplicationReview pr left join fetch pr.project left join fetch pr.reviewer where pr.type = 'general' and pr.year = :year " + (rejectExpertIds.isEmpty() ? " and 1 = 0" : " and pr.reviewer.id in (:rejectExpertIds) ");
		rejectMatches = dao.query(hql4RejectMatch, paraMap);
		System.out.println("查询[待剔除匹配]用时: " + (new Date().getTime() - begin.getTime()) + "ms");
		System.out.println("查出[待剔除匹配]数目: " + rejectMatches.size() + "\n");
		return rejectMatches;
	}
	
	@Override
	public String getProjectType() {
		return projectType;
	}
}