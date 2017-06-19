package csdc.service;

import java.util.List;

import csdc.bean.Account;
import csdc.bean.KeyTopicSelection;
import csdc.tool.bean.AccountType;

public interface IKeyService extends IProjectService  {
	
	/**
	 * 获得当前登陆者的初级检索项目选题申报的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目选题申报的条件语句
	 */
	public String getKeyTopicSimpleSearchHQL(int searchType);
	/**
	 * 获得当前登陆者的初级检索重大攻关项目选题申报的补充字段查询语句
	 * @param accountType 账号类型
	 * @return 初级检索项目申请的条件语句
	 */
	public String getKeyTopicSimpleSearchHQLWordAdd(AccountType accountType);
	/**
	 * 获得当前登陆者的初级检索项目选题申报的补充条件查询语句
	 * @param accountType 账号类型
	 * @return 初级检索选题申报的条件语句
	 */
	public String getKeyTopicSimpleSearchHQLAdd(AccountType accountType);
	/**
	 * 处理项目选题申报查看范围
	 * @param account 当前账号对象
	 * @return 查询语句
	 */
	public String topicSelectionInSearch(Account account);
	/**
	 * 保存选题申报人id，用于添加、修改
	 * @param oldTopicSelection 原选题对象，oldTopicSelection对象的applicant字段id为person id
	 * @param NewTopicSelection 新选题对象，NewTopicSelection对象的applicant字段id为教师、专家或者学生id
	 * @return
	 */
	public KeyTopicSelection setApplicantIdFromTopicSelection(KeyTopicSelection oldTopicSelection, KeyTopicSelection NewTopicSelection);
	/**
	 * 保存项目成员信息，用于查看、修改预处理
	 * @param TopicSelection 选题对象，TopicSelection对象的applicantId字段id为person的id
	 * @return 处理后的TopicSelection对象
	 */
	public KeyTopicSelection setApplicantIdFromPerson(KeyTopicSelection topicSelection);
	/**
	 * 判断申报选题是否部属高校
	 * @param topsId 选题申请id
	 * @return 1:部署高校, 0:地方高校
	 */
	public int isSubordinateUniversityTopicSelection(String topsId);
	/**
	 * 根据当前项目投标id获取当前选题
	 * @param entityId 当前投标id
	 * @return 选题对象
	 */
	public String getCurrentTopicSelectionByAppId(String entityId);
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
	 * 处理选题审核人和所在机构信息
	 * @param 选题对象
	 * @return 信息list
	 */
	public List getTopsAuditorInfo(KeyTopicSelection topicSelection);
	
}