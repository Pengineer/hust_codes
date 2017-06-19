package csdc.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.common.ExpertLink;

public interface IAwardService extends IBaseService{
	/**
	 * 奖励模块的DWR调用部分
	 */

	public String initParam(String data);
	/**
	 * 专家树中根据选中的专家id","的字符串，获取响应的专家信息（用于右侧已经选择专家列表显示）
	 */
	public String getExpInfo(String awardType, String ids);
	/**
	 * 根据奖励ID找到已分配专家的名称，高校
	 * @param awardId项目ID
	 * @return 相关信息
	 */
	public List<ExpertLink> getExpert(String awardId);
	/**
	 * 自动匹配
	 * （公共业务方法）
	 * Action 调用 公共Service
	 * @param year
	 * @param expertIds
	 * @param awardIds
	 * @param rejectExpertIds
	 * @throws Exception
	 */
	public void matchExpert(String awardType, 
			Integer year, 
			List<String> expertIds, 
			List<String> projectids, 
			List<String> rejectExpertIds) throws Exception ;
	/**
	 * 清除奖励某年匹配信息
	 * 
	 * @param deleteAutoMatches 是否清除自动匹配对，1是，0否
	 * @param deleteManualMatches 是否清除手工匹配对，1是，0否
	 * @param year 待清除年
	 */
	public void deleteAwardMatchInfos(int deleteAutoMatches,int deleteManualMatches,int year,String awardType);
	
	/**
	 * 项目匹配更新子类业务直接调用
	 * （公共业务方法）
	 * @param awardType
	 * @param awardIds
	 * @param year
	 */
	public void awardUpdateWarningReviewer(String awardType, List<String> awardIds, Integer year);
	/**
	 * 创建专家树
	 * @return
	 */
	public Map fetchExpertData();
	/**
	 * 初始化专家树
	 * @param projectIds 项目id，用分号空格隔开，如果不涉及项目则填null
	 */
	public void initExpertTree(String projectIds);
	
	/**
	 * 群删奖励
	 * 同时要删除奖励相关的其他表信息：
	 * @param awardids 待删奖励Id
	 */
	public void deleteProjects(List<String> awardids);
	/**
	 * 查询参与匹配专家的查询语句，在（匹配算法中、专家树中、导出专家中）使用
	 * 奖励模块
	 * @return
	 */
	public String selectReviewMatchExpert();
	
	/**
	 * 奖励导出
	 * @param exportAll	1：导出当前年所有项目（参评+备评）；0：根据当前项目列表检索情况导出
	 * @param containReviewer ture：包含评审信息；false：不包含评审信息
	 * @return
	 */
	public InputStream exportAward(int exportAll, boolean containReviewer);
	
	/**
	 * 导出奖励专家调整更新表
	 * @param year	匹配年度
	 * @param start	更新开始时间
	 * @param end	更新结束时间
	 */
	public InputStream exportMatchUpdate(Integer year, Date start, Date end) throws Exception;
	
}
