package csdc.service.webService.server;

import java.io.IOException;
import java.util.Map;

import org.dom4j.Document;

import csdc.bean.Account;


public interface ISmasWebService extends IBaseWebService{

	/**
	 * SMAS同步SMDB重点人信息
	 * @param account
	 * @param requestAccountBelong
	 * @return
	 * @throws IOException 
	 */
	public String addKeyPerson() throws IOException;
	/**
	 * smas同步smdb的项目立项信息
	 * 以进行了封装以及转码处理
	 * @return
	 */
	public String requestProjectGranted(String projectType, int fetchSize, int counts);
	/**
	 * 获取 year 年的一般项目的申请数据<br>
	 * 说明：
	 * (1) 严格按照条件控制同步一般项目申请项目
	 * 参考：
	 * (1) 所属高校类型是部署高校 ， 高校审核状态已提交  ，高校审核通过；
	 * (2) 所属高校类型是地方高校 ， 省级审核状态已提交  ，省级审核通过。
	 * 备注：最终审核状态已提交， 最终审核结果通过（不用，不属于流程）<br>
	 * 一般项目不用同步“专项任务项目”的数据
	 * @param year 获取申请的年度条件
	 * @param projetType 项目类型
	 * @param fetchSize 获取数据大小
	 * @param counts 获取批次
	 * @return
	 */
	public String requestProjectApplication(String year, String projectType, int fetchSize, int counts);

	
	/**
	 * 获取项目变更信息ProjectVariation
	 * @param fetchSize
	 * @param counts
	 * @return
	 */
	public String requestProjectVariation(String projectType, int fetchSize, int counts);
	/**
	 * 获取项目结项信息ProjectEndinspection
	 * @param fetchSize
	 * @param counts
	 * @return
	 */
	public String requestProjectEndinspection(String projectType, int fetchSize, int counts);
	/**
	 * 获取项目中检信息Midinspection
	 * @param fetchSize
	 * @param counts
	 * @return
	 */
	public String requestProjectMidinspection(String projectType, int fetchSize, int counts);
	/**
	 *  根据项目类型，项目名称，申请人姓名，申请年度获取立项信息
	 * @param projectType
	 * @param projectName
	 * @param applicantName
	 * @param year
	 * @return
	 */
	public String requestUniqueProjectApplication(String projectType, String projectName, String applicantName, int year);
	
	/**
	 * 根据申请id获取对应的立项信息
	 * @param projectType
	 * @param projectApplicationID
	 * @return
	 */
	public String requestUniqueProjectGranted(String projectType, String projectApplicationID);
	/**
	 * 获取立项id对应的批量变更信息
	 * @param projectType
	 * @param projectGrantedID
	 * @return
	 */
	public String requestBatchProjectVariation(String projectType, String projectGrantedID);
	
	/**
	 * 获取立项id对应的批量中检信息
	 * @param projectType
	 * @param projectGrantedID
	 * @return
	 */
	public String requestBatchProjectMidinspection(String projectType, String projectGrantedID);
	/**
	 * 获取立项id对应的批量结项信息
	 * @param projectType
	 * @param projectGrantedID
	 * @return
	 */
	public String requestBatchProjectEndinspection(String projectType, String projectGrantedID);

	/**
	 * 根据高校代码，更新smas高校信息
	 * @param universityCode
	 * @return
	 */
	public String requestUniqueUniversityInfo(String universityCode);
	/**
	 * 根据smas中的smdb项目申请id，获取smdb中部级人员（最终确认后的）初评结果信息
	 * @param universityCode
	 * @return
	 */
	public String requestFixSmasProgram(String projectApplicationID);
	
	/**
	 * 入库smas后，更新smdb中的部级审核信息，采用逐条同步
	 * @param universityCode
	 * @return
	 */
	public String updateProjApplMinistryAuditInfo(Map<String, String> argsMap);
	
}
