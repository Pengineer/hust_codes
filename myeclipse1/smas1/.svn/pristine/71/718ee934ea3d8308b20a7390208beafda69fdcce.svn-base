package csdc.service;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;

import csdc.bean.ProjectApplication;
import csdc.bean.common.ExpertLink;

@SuppressWarnings("unchecked")
public interface IGeneralService  extends IProjectService{
	
	public void setGranting(List<String> projectids, int label);

	
	/**
	 * 根据传的一级学科数组生成专家树，同时支持高校名称和专家名称检索
	 * @param discipline1Strings 一级学科数组
	 * @param universityName 高校名称
	 * @param expertName 专家名称
	 * @param selectExpIds 已选专家id，用分号隔开
	 * @param projectIds 项目的id，分号空格隔开（手动选择专家时需要此参数）
	 * @return 树的dom对象
	 */
	public Document createExpertTree(String[] discipline1Strings, String universityName, String expertName, String selectExpIds, String projectIds);
		
	/**
	 * 初始化专家树
	 * @param projectIds 项目id，用分号空格隔开，如果不涉及项目则填null
	 */
	public void initExpertTree(String projectIds);
	/**
	 * dwr使用，初始专家树页面的参数
	 * @param data
	 * @return
	 */
	public String initParam(String data);
	
	/**
	 * 获取形如[张三(北京大学), 李四(清华大学)]的成员的信息
	 * @param memberInfo
	 * @return 0: Set姓名集合	1: Set高校代码集合	 若未能识别出高校，则代码置为null
	 */
	public Object[] getMemberInfo(ProjectApplication project);

	/**
	 * 查询参与匹配专家的查询语句，在（匹配算法中、专家树中、导出专家中）使用
	 * @return
	 */
	public String selectReviewMatchExpert();

	public Map fetchExpertData();
}
