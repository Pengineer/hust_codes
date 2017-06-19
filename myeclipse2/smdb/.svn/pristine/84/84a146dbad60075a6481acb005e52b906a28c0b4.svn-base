package csdc.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.ProjectEndinspection;
import csdc.service.IPostService;
import csdc.tool.bean.AuditInfo;
@Transactional
public class PostService extends ProjectService implements IPostService  {
	/**
	 * 根据personid获得研究人员所负责的项目信息列表
	 * @param projectid 项目立项id
	 * @param personid 研究人 id
	 * @return 项目信息列表
	 */
	@SuppressWarnings("unchecked")
	public List getDireProjectListByGrantedId(String projectid, String personid){
		if(projectid == null || personid == null){
			return new ArrayList();
		}
		Map map1 = new HashMap();
		map1.put("projectid", projectid);
		map1.put("personId",personid);
		String hql1 = "select gra.name, app.englishName, to.name, app.year, gra.applicantName, " +
				"gra.university.name, gra.divisionName from PostGranted gra, " +
				"PostApplication app left outer join app.topic to,PostMember mem where " +
				"gra.application.id=app.id and mem.application.id=app.id and gra.id=:projectid and " +
				"mem.isDirector=1 and mem.member.id=:personId";
		List projectList = dao.query(hql1, map1);
		return projectList;
	}
	
	/**
	 * 根据projectid获得研究人员所负责的项目信息列表
	 * @param projectid 项目立项id
	 * @return 项目信息列表
	 */
	@SuppressWarnings("unchecked")
	public List getDireProjectListByGrantedId(String projectid){
		if(projectid == null){
			return new ArrayList();
		}
		Map map1 = new HashMap();
		map1.put("projectid", projectid);
		String hql1 = "select gra.name, app.englishName, to.name, app.year, gra.applicantName, " +
				"gra.university.name, gra.divisionName from PostGranted gra, PostApplication app " +
				"left outer join app.topic to where gra.application.id=app.id and gra.id=:projectid ";
		List projectList = dao.query(hql1, map1);
	
		return projectList;
	}
	
	/**
	 * 录入鉴定结项表最终审核字段设置
	 * @param endinspection结项对象，auditInfo审核信息
	 * @return 结项对象
	 */
	public ProjectEndinspection fillFinalAuditInfos(ProjectEndinspection endinspection, AuditInfo auditInfo){
		endinspection.setFinalAuditor(auditInfo.getAuditor());
		endinspection.setFinalAuditorName(auditInfo.getAuditorName());
		endinspection.setFinalAuditorAgency(auditInfo.getAuditorAgency());
		endinspection.setFinalAuditorDept(auditInfo.getAuditorDept());
		endinspection.setFinalAuditorInst(auditInfo.getAuditorInst());
		endinspection.setFinalAuditStatus(3);
		endinspection.setFinalAuditResultEnd(1);
		endinspection.setFinalAuditResultNoevaluation(1);
		endinspection.setFinalAuditResultExcellent(1);
		return endinspection;
	}
}