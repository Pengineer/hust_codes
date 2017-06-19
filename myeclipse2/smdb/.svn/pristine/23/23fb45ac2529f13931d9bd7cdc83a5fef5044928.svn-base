package csdc.service;

import java.util.List;

import csdc.bean.ProjectEndinspection;
import csdc.tool.bean.AuditInfo;

public interface IPostService extends IProjectService  {
	
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
	/**
	 * 录入鉴定结项表最终审核字段设置
	 * @param endinspection结项对象，auditInfo审核信息
	 * @return 结项对象
	 */
	public ProjectEndinspection fillFinalAuditInfos(ProjectEndinspection endinspection, AuditInfo auditInfo);
	
//	/**
//	 * 校验上传的word宏文件
//	 * @param graId 后期资助项目立项id
//	 * @param wordFile word文件
//	 * @param type  2.变更 3.结项
//	 * @return 错误信息，若返回空则没有错误
//	 * @author leida 2011-09-27
//	 */
//	public String checkWordFileLegal(String graId, File wordFile, int type);
}