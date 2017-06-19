package csdc.tool.execution.fix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.ProjectMember;
import csdc.dao.IHibernateBaseDao;
import csdc.dao.JdbcDao;
import csdc.tool.execution.Execution;

/**
 * 处理141条项目对应的142条成员异常数据
 * 这些数据分两种情况
 * 1、groupNumber=1时sn不从1开始
 * 2、groupNumber为null
 * @author suwb
 *
 */
public class FixOldProject extends Execution {
	
	private JdbcDao jdbcDao;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	public FixOldProject() {
	}
	
	public FixOldProject(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
	@Override
	protected void work() throws Throwable {
		List<String[]> list = jdbcDao.query("select distinct(c_application_id) from t_project_member where c_application_id not in (select c_application_id from t_project_member where c_is_director = 1  and c_member_sn = 1 and c_group_number = 1)");
		Map map = new HashMap();
		//第一次遍历，处理groupNumber=1的
		for(String[] thisId : list){
			map.put("appId", thisId[0]);
			List<ProjectMember> pMList = (List<ProjectMember>)dao.query("select pm from ProjectMember pm where pm.groupNumber = 1 and pm.applicationId =:appId order by pm.memberSn", map);
			if(!pMList.isEmpty()){
				List director = dao.query("select tpa.applicantName from ProjectApplication tpa where tpa.id =:appId", map);
				int i = 2;
				for(ProjectMember pm : pMList){
					if(pm.getMemberName().equals(director.get(0))){
						pm.setIsDirector(1);
						pm.setMemberSn(1);
					}else {
						pm.setIsDirector(1);
						pm.setMemberSn(i);
						i++;
					}
					dao.modify(pm);
				}
			}
		}
		//第二次遍历，处理groupNumber为空的
		for(String[] thisId : list){
			map.put("appId", thisId[0]);
			List<ProjectMember> pMList = (List<ProjectMember>)dao.query("select pm from ProjectMember pm where pm.groupNumber is null and pm.applicationId =:appId order by pm.memberSn", map);
			if(!pMList.isEmpty()){
				int i = 1;
				List director = dao.query("select tpa.applicantName from ProjectApplication tpa where tpa.id =:appId", map);
				for(ProjectMember pm : pMList){
					pm.setGroupNumber(1);
					if(pm.getMemberName().equals(director.get(0))){
						pm.setIsDirector(1);
						pm.setMemberSn(i);
						i++;
					}
					dao.modify(pm);
				}
			}
		}
	}
}
