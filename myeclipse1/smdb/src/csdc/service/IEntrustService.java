package csdc.service;

import java.util.List;

public interface IEntrustService extends IProjectService  {
	
	/**
	 * 根据personid获得研究人员所负责的项目信息列表
	 * @param projectid 项目立项id
	 * @param personid 研究人 id
	 * @return 项目信息列表
	 */
	@SuppressWarnings("unchecked")
	public List getDireProjectListByGrantedId(String projectid, String personid);
	
	/**
	 * 根据projectid获得研究人员所负责的项目信息列表
	 * @param projectid 项目立项id
	 * @return 项目信息列表
	 */
	@SuppressWarnings("unchecked")
	public List getDireProjectListByGrantedId(String projectid);
	
}