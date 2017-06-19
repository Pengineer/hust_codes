package csdc.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

import csdc.service.IEntrustService;
@Transactional
public class EntrustService extends ProjectService implements IEntrustService  {
	
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
				"gra.university.name, gra.divisionName from EntrustGranted gra, " +
				"EntrustApplication app left outer join app.topic to,EntrustMember mem where " +
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
				"gra.university.name, gra.divisionName from EntrustGranted gra, EntrustApplication app " +
				"left outer join app.topic to where gra.application.id=app.id and gra.id=:projectid ";
		List projectList = dao.query(hql1, map1);
	
		return projectList;
	}
	
}