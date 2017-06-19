package csdc.tool.matcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.AwardApplication;
import csdc.bean.Expert;
import csdc.dao.HibernateBaseDao;
import csdc.service.IAwardService;
import csdc.service.imp.InstpService;

/**
 * 基地奖励专家评审自动匹配器，封装了对DisciplineBasedMatcher的调用
 * @author fengcl
 */
@Component
@Scope("prototype")
public class MoeSocialReviewerMatcher extends AwardReviewerMatcher {

	@Autowired
	private HibernateBaseDao dao;
	
	@Autowired
	private InstpService instpService;
	@Autowired
	private IAwardService awardService;
	private String awardType = "moeSocial";

	public MoeSocialReviewerMatcher() {
		Map application = ActionContext.getContext().getApplication();
	
		expertProjectMin = (Integer) application.get("AwardExpertProjectMin");
		
		expertProjectMax = (Integer) application.get("AwardExpertProjectMax");
		
		universityExpertMin = (Integer) application.get("AwardUniversityExpertMin");
		
		universityExpertMax = (Integer) application.get("AwardUniversityExpertMax");
		
		projectMinistryExpertNumber = (Integer) application.get("AwardMinistryExpertNumber");
		
		projectLocalExpertNumber = (Integer) application.get("AwardLocalExpertNumber");
		
		projectExpertNumber = projectMinistryExpertNumber + projectLocalExpertNumber;
		
	}
	
	//TODO 暂时和基地的专家选取情况保持一致
	public List<Expert> getReviewerInfo(List<String> expertIds, List<String> projectIds, List<String> rejectExpertIds, Map paraMap){
		List<Expert> experts = new ArrayList<Expert>();
		Map disciplineMap = instpService.selectSpecialityTitleInfo();
		paraMap.put("aboveSubSeniorTitles", disciplineMap.get("aboveSubSeniorTitles"));	//副高级职称以上专家
		paraMap.put("seniorTitles", disciplineMap.get("seniorTitles"));	//正高级、高级职称专家
		
		//1、筛选所属高校办学类型为11和12，属性为参评、非重点人、专职人员，手机和邮箱全非空，当前年没申请奖励， 评价等级大于限制阈值，当前时间6个月内更新入库的专家
		Date begin = new Date();
		StringBuffer hql4Reviewer = new StringBuffer("select expert from Expert expert, University u where expert.universityCode = u.code");
		if (expertIds.size() == 0) {//如果未指定待匹配专家
			hql4Reviewer.append(awardService.selectReviewMatchExpert());
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
	 * 筛选出当前年度待匹配的奖励
	 * @param projectIds
	 * @param rejectExpertIds
	 * @param paraMap
	 * @return
	 */
	public List<AwardApplication> getSubjectInfo(List<String> awardIds, List<String> rejectExpertIds, Map paraMap){
		List<AwardApplication> awardApplications = new ArrayList<AwardApplication>();
		Date begin = new Date();
		String hql4Project = null;
		if (awardIds.size() == 0) {
			//若没有指定匹配的奖励，则选取没有匹配完全和未开始匹配的奖励。做法是查出这些奖励的id，用in子句筛选这些奖励
			String hql4ProjectId = "select project.id from AwardApplication project left join project.awardApplicationReview pr where project.type = 'moeSocial' and project.year = :year and project.isReviewable = 1 " + (rejectExpertIds.isEmpty() ? "" : " and pr.reviewer.id not in (:rejectExpertIds) ") + " group by project.id having count(*) < :projectExpertNumber";
			
			awardIds = dao.query(hql4ProjectId, paraMap);
			
			if (awardIds.size() >= 1000) {
				//若未匹配完全和未开始匹配的奖励太多，则in效率太低，放弃使用in，而是选取所有当前年奖励。
				awardIds = new ArrayList<String>();
			}
			paraMap.put("awardIds", awardIds);	//projectIds有更新，需重新放入paraMap
		}
		if (awardIds.size() > 0) {
			//选取id在projectIds内的奖励（用in子句筛选这些奖励）
			hql4Project = "select project from AwardApplication project where project.type = 'moeSocial' and project.year = :year and project.isReviewable = 1 and project.id in (:awardIds)";
		} else {
			//选取所有当前年奖励
			hql4Project = "select project from AwardApplication project where project.type = 'moeSocial' and project.year = :year and project.isReviewable = 1";
		}
	
		awardApplications = dao.query(hql4Project, paraMap);

		System.out.println("查询[奖励]用时: " + (new Date().getTime() - begin.getTime()) + "ms");
		System.out.println("查出[奖励]数目: " + awardApplications.size() + "\n");
		return awardApplications;
	}
	
	@Override
	public String getAwardType() {
		return awardType;
	}

}
