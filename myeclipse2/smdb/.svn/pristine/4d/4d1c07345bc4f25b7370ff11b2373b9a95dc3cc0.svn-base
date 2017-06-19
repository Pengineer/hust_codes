package csdc.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import csdc.bean.AwardGranted;
import csdc.bean.AwardApplication;
import csdc.bean.AwardReview;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.tool.bean.AccountType;
/**
 * 奖励管理接口
 * @author 余潜玉
 */
public interface IAwardService extends IBaseService {
	
	/*------------------------获取奖励相关查询语句--------------------------------*/
	/**
	 * 根据登陆者的身份得到查询语句(用于奖励申报数据)
	 * @param	hql1 查询语句的查询部分
	 * @param	hql2查询语句的选择数据表部分
	 * @param	accountType登陆者身份	1：系统管理员	2：部级	3：省级	4、5：校级	6：院系	7：研究机构	8：外部专家	9：教师	10：学生
	 * @return StringBuffer 查询语句
	 */
	public StringBuffer getHql(String hql1, String hql2, AccountType accountType);
	
	/**
	 * 根据登陆者的身份得到查询语句（用于公示数据和获奖数据）
	 * @param hql	原hql
	 * @param accountType	登陆者身份	1：系统管理员	2：部级	3：省级	4、5：校级	6：院系	7：研究机构	8：外部专家	9：教师	10：学生
	 * @return StringBuffer
	 */
	public StringBuffer getHql(String hql,AccountType accountType);
	
	/**
	 * 根据原有hql、初级检索条件、查询列表类型得到初级检索hql
	 * @param hql	原hql
	 * @param searchType	初级检索条件
	 * @param listflag	查询列表类型		1：奖励申请列表	2：公示列表	3：获奖列表
	 * @return StringBuffer	初级检索hql
	 */
	public StringBuffer getHql(StringBuffer hql,int searchType,int listflag);
	/*------------获取或设置奖励申请相关信息--------------*/
	/**
	 * 根据奖励申请id获得奖励申请及申请人的相关信息
	 * @param entityId	奖励申请id
	 * @return AwardApplication对象
	 */
	public AwardApplication getAwardApplicationById(String entityId);
	/**
	 * 设置奖励申请各个字段参数
	 * @param awardApplication 奖励申请
	 * @param productId 成果id
	 * @return 奖励申请
	 * @author leide 2012-02-16
	 */
	public AwardApplication setAwardApplicationParam(AwardApplication awardApplication, String productId);
	
	/**
	 * 隐藏部分奖励相关信息
	 * @param awardApplication 奖励申请对象
	 * @param accountType登陆者身份	1：系统管理员	2：部级	3：省级	4、5：校级	6：院系	7：研究机构	8：外部专家	9：教师	10：学生
	 * @param allReviewSubmit  是否所有专家的评审已提交	1:是		0:否
	 * @return 隐藏信息后的奖励申请对象
	 */
	public AwardApplication hideAwardAppInfo(AwardApplication awardApplication, AccountType accountType, int allReviewSubmit);
	/**
	 * 获得id为personId的人报奖的所有届次的list
	 * @param personId 人员id
	 * @return 所有届次的list
	 */
	@SuppressWarnings("unchecked")
	public List getPersonalAllSession(String personId);
	/*---------------上传申请书--------------*/
	/**
	 * 得到上传文件的唯一路径并保存上传的文件
	 * @param uploadFile 上传的文件
	 * @return String 返回上传文件的路径
	 */
	public String getFileName(File uploadFile);
	/*-------------获取或设置获奖对象相关信息---------------*/
	/**
	 * 根据奖励申请id取出获奖信息
	 * @param entityId 奖励申请id
	 * @return Award  返回奖励对象
	 */
	public AwardGranted getAward(String entityId);
	/**
	 * 奖励申请公示审核通过后添加获奖信息
	 * @param awardApplication 奖励申请对象
	 */
	public void addAward(AwardApplication awardApplication);

	/*------------------获取或设置奖励评审相关信息--------------*/
	
	/**
	 * 根据奖励申请id及人员id获取判断是否奖励评审人及评审组长
	 * @param entityId 奖励申请id
	 * @param personId 人员id
	 * @return 0不是评审人；1是评审人但不是评审组长；2是评审人且是评审组长
	 */
	public int isReviewer(String entityId, String personId);
	
	/**
	 * 判断是否所有评审专家都提交了个人评审
	 * @param entityId 奖励申请id
	 * @return 0所有专家已提交；-1还有专家未提交
	 */
	public int isAllReviewSubmit(String entityId);
	
	/**
	 * 获取当前账号能看到的所有人结项评审
	 * @param entityId 奖励申请id
	 * @return 当前账号能看到的所有人结项评审
	 */
	@SuppressWarnings("unchecked")
	public List getAllReviewList(String entityId);
	
	/**
	 * 根据奖励申请id和评审专家id得到奖励评审信息
	 * @param entityId 奖励申请id
	 * @param personId 评审专家id
	 * @return 奖励评审对象
	 */
	public AwardReview  getAwardReview(String entityId, String personId);
	/**
	 * 根据奖励评审id得到奖励评审信息
	 * @param entityId 奖励评审id
	 * @return 奖励评审对象
	 */
	public AwardReview  getAwardReview(String entityId);
	/**
	 * 设置奖励评审的分数信息
	 * @param awardApplication 奖励申请对象
	 */
	public void setReviewScore(AwardApplication awardApplication);
	
	/**
	 * 根据奖励申请id获得奖励申请的所有评审意见
	 * @param entityId 奖励申请id
	 * @return 评审意见List
	 */
	@SuppressWarnings("unchecked")
	public List getGroupOpinionByAppId(String entityId);

	
	/* -------------------dwr需要用到的方法--------------------------*/
	
	/** 
	 * 根据奖励申请人id得到奖励申请人所属的所有团队名称
	 * @param personid 成果作者id
	 * @return	团队map
	 */
	public Map<String, String> getOrganization(String personid);
	
	/** 
	 * 根据成果类别和成果作者id得到该作者对应成果类别的所有成果
	 * @param ptype 成果类别
	 * @param personid 成果作者id
	 * @param organizationId 团队id
	 * @return	成果map
	 */
	public Map<String, String> getProduct(String ptype, String personid, String organizationId);
	
	/**
	 * 获得成果的学科门类列表
	 * @param ptype 成果类别
	 * @param product 成果id
	 * @return	成果对应的学科门类map
	 */
	public Map<String,String> getDtype(String ptype,String product);
	
	/**
	 * 判断奖励证书编号是否唯一
	 * @param entityId	奖励申请对象id
	 * @param number 证书编号
	 * @return	true:唯一	false:不唯一
	 */
	public Boolean isNumberUnique(String entityId, String number);
	
	/**
	 * 获得最大奖励申请年份
	 * @param entityId  奖励申请id，多个以";"隔开。
	 * @return 最大奖励申请年份
	 */
	public String getApplyYear(String entityId);
	
	/**
	 * 查询当年年份形成年份列表，最大年份为当前年份
	 * @return 年份列表
	 */
	public Map<Integer, Integer> getYearMap();

	/*--------------操作word宏的相关方法------------------*/
	/**
	 * 校验2012年奖励申报表是否合法
	 * @param wordFile word文件
	 * @param personId 当前登陆者id
	 * @return 错误信息(如果合法则返回null)
	 * @author leide 2012-02-14
	 */
	public String checkAwardWordFile(File wordFile, String personId);
	
	/**
	 * 导入奖励申报表的word宏信息
	 * @param wordFile word文件
	 * @param personId 人员id
	 * @param submitStatus 提交状态
	 * @return 成果id
	 * @author leide 2012-02-14
	 */
	public String importAwardFileData(File wordFile, String personId, int submitStatus);
	
	/**
	 * 通过word宏中的xml信息更新人员的信息
	 * @param wordFile word宏文件
	 * @param personId 人员id
	 * @author leida 2011-09-15
	 */
	public void updateDirectorData(File wordFile, String personId);
	
	/**
	 * 导入奖励申请的大段文字
	 * @param wordFile word文件
	 * @param awardApplication 
	 * @return awardApplication
	 * @author leida 2012-02-22
	 */
	public AwardApplication importBigSection(File wordFile, AwardApplication awardApplication);
	
	/**
	 * 提交奖励申请下面未提交的成果申请
	 * @param productId
	 * @author leida 2012-02-22
	 */
	public void submitAwardAppProduct(String productId);
	/*-------------------------获取其他信息-----------------------------*/
	/**
	 * 根据奖励申请判断是否是部署高校
	 * @param awardApplication 奖励申请对象
	 * @return 1：是		0：否
	 */
	public int getIsSubUniByApp(AwardApplication awardApplication);
	/**
	 * 获得所有成果类别列表
	 * @return 所有成果类别列表
	 */
	@SuppressWarnings("unchecked")
	public List getProductTypes();
	
	/**
	 * 根据SystemOption父项名称获得子项List
	 * @param name
	 * @return 子项List
	 */
	@SuppressWarnings("unchecked")
	public List getSystemOptionListByParentName(String name);

	/**
	 * 根据教师人员id得到教师信息
	 * @param personid 人员id
	 * @return Teacher  返回教师对象
	 */
	public Teacher findTeacherBypersonid(String personid);

	/**
	 * 根据论文类型获取论文描述
	 * @param productType
	 * @return 论文类型描述 
	 */
	public String fetchProductDescription(String productType);	
	
	/**
	 * 根据成果类型中文获取英文
	 * @param productType
	 * @return 英文名称
	 */
	public String fetchProductEnglish(String productType);	
	
	
	/*--------------------------用于奖励录入评审--------------------------------*/
	/**
	 * 根据登陆账号类别好的评审人身份
	 * @param accountType 登陆账号类别1：系统管理员	2：部级	3：省级	4、5：校级	6：院系	7：研究机构	8：外部专家	9：教师	10：学生
	 * @return 评审人身份	0默认，1专家，2教育部，3省厅，4高校
	 */
	public int getReviewTypeByAccountType(AccountType accountType);
	
	/**
	 * 判断奖励申报是否有评审记录
	 * @param entityId 申报id
	 * @return -1无评审记录，1有评审记录且是专家评审，22教育部录入暂存，23教育部录入提交，32省厅录入暂存，33省厅录入提交，42高校录入暂存，43高校录入提交
	 */
	public int checkReview(String entityId);
	
	/**
	 * 根据分数计算等级
	 * @param score分数
	 * @return 等级
	 */
	public SystemOption getReviewGrade(double score);
	
	/**
	 * 获得教师及其人员
	 * @param teacherId 教师id
	 * @return  教师
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getTeacherFetchPerson(String teacherId);
	
	/**
	 * 获得外部专家及其人员
	 * @param expertId 外部专家id
	 * @return  外部专家
	 */
	@SuppressWarnings("rawtypes")
	public List getExpertFetchPerson(String expertId);
	
	/**
	 * 保存奖励评审信息
	 * @param awardReview 奖励评审对象
	 * @return 更新后的奖励评审对象
	 */
	public AwardReview setAwardReviewInfoFromAwardReview(AwardReview awardReview);
	
	/**
	 * 通过申请id获得奖励评审组长的评审信息
	 * @param appId 申请id
	 * @return 评审组长的评审信息
	 */
	public AwardReview getGroupDirectorReview(String appId);
	
	/**
	 * 获取奖励评审总分与均分
	 * @param appId 申请id
	 * @return 奖励评审总分与均分
	 */
	public double[] getReviewScore(String appId);
	
	/**
	 * 根据申请id获得奖励的所有评审列表
	 * @param appId 奖励申请id
	 * @return 奖励的所有评审列表
	 */
	@SuppressWarnings("rawtypes")
	public List<AwardReview> getAllReviewByAppId(String appId);
	
//	/**
//	 * 根据申请id对奖励评审对象对应的人员、机构进行入库处理
//	 * @param appId 奖励申请id
//	 */
//	public void doWithNewReview(String appId);
	
	/**
	 * 根据奖励人员id、高校id、院系id获得教师id
	 * @param personId	人员主表id
	 * @param universityId	高校id
	 * @param department	院系
	 * @param institute	研究基地
	 * @return	教师id
	 */
	public String getTeacherIdByMemberAllUnit(String personId, String universityId, Department department, Institute institute);
	
	/**
	 * 根据奖励人员id、高校id获得教师id
	 * @param personId	人员主表id
	 * @param universityId	高校id
	 * @param department	院系
	 * @param institute	研究基地
	 * @return	教师id
	 */
	public String getTeacherIdByMemberPartUnit(String personId, String universityId);
	
	/**
	 * 根据奖励人员id、机构名称、部门名称获得专家id
	 * @param personId 人员主表id
	 * @param agencyName 机构名称
	 * @param divisionName 部门名称
	 * @return 专家id
	 */
	public String getExpertIdByPersonIdUnit(String personId, String agencyName, String divisionName);
	
	/**
	 * 设置奖励人员对应的研究人员id(teacherId, ExpertId, studentId)的信息
	 * @param review 奖励申报评审对象
	 * @return ProjectApplicationReview 奖励申报评审对象
	 */
	public AwardReview setReviewPersonInfoFromReview(AwardReview review);
	
	/**
	 * 同步奖励文件到DMSS服务器
	 * @param awardApplication
	 * @return DMSS文档持久化后的标识
	 */
	public String flushToDmss(AwardApplication awardApplication);
}
