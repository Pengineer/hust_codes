package csdc.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;

import csdc.bean.ProjectApplication;
import csdc.bean.common.ExpertLink;

@SuppressWarnings("unchecked")
public interface IInstpService  extends IProjectService{
	public Document createRightXML(String[] str, String selectExp, String uname, String ename, int flag, String projectid );
	public void updateWarningReviewer(ProjectApplication project);
	public void exportInstpReviewer() throws Exception;			

	/**
	 * dwr使用，初始专家树页面的参数
	 * @param data
	 * @return
	 */
	public String initParam(String data);
	
	/**
	 * 获取项目[成员集合]和[成员所在高校代码集合](成员包括负责人)<br>
	 * 其中，项目的成员信息形如[张三(北京大学), 李四(清华大学)]
	 * @param memberInfo
	 * @return 0: Set姓名集合	1: Set高校代码集合	 若未能识别出高校，则代码置为null
	 */
	public Object[] getMemberInfo(ProjectApplication project);
	
	/**
	 * 查询专家树所需数据
	 * @return	dataMap：<br>
	 * 包括专家总数totalExpert和expertTreeItem的list
	 */
	public Map fetchExpertData();

	
	/**
	 * 查询参与匹配专家的查询语句，在（匹配算法中、专家树中、导出专家中）使用
	 * @return
	 */
	public String selectReviewMatchExpert();
}
