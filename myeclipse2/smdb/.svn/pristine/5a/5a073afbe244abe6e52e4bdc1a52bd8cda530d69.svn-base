package csdc.service;

import java.util.List;
import java.util.Map;

import org.csdc.domain.fm.ThirdUploadForm;

import csdc.bean.Account;
import csdc.bean.Expert;
import csdc.bean.Officer;
import csdc.bean.Passport;
import csdc.bean.Student;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.bean.LoginInfo;

@SuppressWarnings("unchecked")
public interface IBaseService {

	
	/**
	 * 根据指定standard, code获取其直接子节点, map的key为id
	 * @param standard
	 * @param code
	 * @return	map (id与name的映射)
	 */
	public Map<String,String> getSystemOptionMap(String standard, String code);
	/**
	 * 根据指定standard, code获取其直接子节点, map的key为name
	 * @param standard
	 * @param code
	 * @return	map (id与name的映射)
	 */
	public Map<String,String> getSystemOptionMapAsName(String standard, String code);
	
	////////////////////////////////////////////////////////////////////
	
	
	

	/**
	 * 判断某个实体是否在当前账号的管辖范围之内
	 * @param loginer当前登录对象
	 * @param id待判定的实体ID
	 * @param idType待判定的实体类别
	 *  1--部级机构，已知条件，ministryUnitId
	 *  2--省级机构，已知条件，provinceUnitId
	 *  3--校级机构，已知条件，universityUnitId
	 *  4--高校院系，已知条件，departmentUnitId
	 *  5--研究基地，已知条件，instituteUnitId
	 *  6--部级人员，已知条件，ministryOfficerId
	 *  7--省级人员，已知条件，provinceOfficerId
	 *  8--校级人员，已知条件，universityOfficerUnitId
	 *  9--院系人员，已知条件，departmentOfficerUnitId
	 * 10--基地人员，已知条件，instituteOfficerUnitId
	 * 11--外部专家，已知条件，expertId
	 * 12--高校教师，已知条件，teacherId
	 * 13--高校学生，已知条件，studentId
	 * 14--项目数据，已知条件，projectId
	 * 15--论文数据，已知条件，productId
	 * 16--著作数据，已知条件，productId
	 * 17--研究报告数据，已知条件，productId
	 * 18--奖励数据，已知条件，awardId
	 * 19--基地项目数据申报数据，已知条件，applicationId
	 * 20--后期资助项目申报数据，已知条件，applicationId
	 * 21--一般项目立项数据，已知条件，applicationId
	 * 22--基地项目立项数据，已知条件，applicationId
	 * 23--后期资助项目立项数据，已知条件，applicationId
	 * 24--重大攻关项目招标数据，已知条件，applicationId
	 * 25--重大攻关项目中标数据，已知条件，applicationId
	 * 26--重大攻关项目选题数据，已知条件，applicationId
	 * @param containSelf管理范围是否包含自己：true包括自己，false不包括自己
	 * @return true在当前账号管理范围之内, false不存在当前账号管理范围之内
	 */
	public boolean checkIfUnderControl(LoginInfo loginer, String id, int idType, boolean containSelf);
	
	/**
	 * 获取当前登陆者所在的院系
	 * @return	map (id与name的映射)
	 */
	public Map<String,String> getLocalUnitMap();
	/**
	 * 获取当前登陆者所在的基地
	 * @return	map (id与name的映射)
	 */
	public Map<String,String> getLocalInitMap();
	
	/**
	 * 根据officerId找到officer，包括里面的person、
	 * agency、department、institute等外键对象
	 * @param officerId
	 * @return officer完整对象
	 */
	public Officer getOfficerByOfficerId(String officerId);

	/**
	 * 根据personId找到expert，包括里面的person对象
	 * @param personId
	 * @return expert完整对象
	 */
	public Expert getExpertByPersonId(String personId);

	/**
	 * 根据personId找到teacher对象(专职)，包括里面的person、
	 * university、department、institute等外键对象
	 * @param personId
	 * @return teacher完整对象
	 */
	public Teacher getTeacherByPersonId(String personId);
	
	/**
	 * 根据personId找到student对象，包括里面的person、
	 * university、department、institute等外键对象
	 * @param personId
	 * @return student完整对象
	 */
	public Student getStudentByPersonId(String personId);

	/**
	 * 根据账号的officerId找到其所属agencyId
	 * @param officerId
	 * @return 机构ID
	 */
	public String getAgencyIdByOfficerId(String officerId);

	/**
	 * 根据账号的officerId找到其所属departmentId
	 * @param officerId
	 * @return 机构ID
	 */
	public String getDepartmentIdByOfficerId(String officerId);

	/**
	 * 根据账号的officerId找到其所属instituteId
	 * @param officerId
	 * @return 机构ID
	 */
	public String getInstituteIdByOfficerId(String officerId);

	/**
	 * 根据personId找到expertId
	 * @param personId
	 * @return expertId
	 */
	public String getExpertIdByPersonId(String personId);

	/**
	 * 根据personId找到teacherId(专职)
	 * @param personId
	 * @return teacherId
	 */
	public String getTeacherIdByPersonId(String personId);
	
	/**
	 * 根据personId找到studentId
	 * @param personId
	 * @return studentId
	 */
	public String getStudentIdByPersonId(String personId);

	/**
	 * 根据账号级别和类别，获得角色默认串的匹配位置
	 * @param type 账号级别
	 * @param isPrincipal 账号类别
	 * @return position 角色默认串配置位置
	 */
	public int getDefaultRoleType(AccountType type, int isPrincipal);
	
	/**
	 * 设置单个账号默认角色
	 * @param account 账号
	 */
	public void setAccountRole(Account account);
	
	/**
	 * 设置多个账号默认角色
	 * @param accounts 账号集合
	 */
	public void setAccountsRole(List<Account> accounts);
	
	/**
	 * 根据账号找到人员或机构的名字
	 * @param account 账号
	 * @return 人员或机构的名字
	 */
	public String getAccountBelongName(Account account);
	
	/**
	 * 系统选项表中获取子选项列表格式为（name, name）
	 * @param str 父选项name
	 * @return 子选项列表格式为（name, name）
	 */
	public Map<String,String> getSONameMapByParentName(String str);
	
	/**
	 * 获取系统选项id列表
	 * @param str 父选项name
	 * @return 系统选项id列表
	 */
	public List<String> getSOIdListByParentName(String str);
	
	/**
	 * 获取系统选项
	 * @param str 父选项name
	 * @return 系统选项
	 */
	public List<SystemOption> getSOByParentName(String str);
	
	/**
	 * 删除账号
	 * @param entityIds待删除账号ID集合
	 */
	public void deleteAccount(List<String> entityIds);
	
	/**
	 * 处理字串，多个以英文分号与空格隔开
	 * @param originString 原始字串
	 * @return 处理后字串
	 */
	
	/**
	 * 由于账号的删除或者账号信息的改变而相对的改变passport中的信息
	 * @param passport 改变信息的账号所对应的通行证
	 */	
	
	public void updatePassport(List<String> entityIds);
	public void updatePassport(String entityId);	
	
	public String MutipleToFormat(String originString);
	
	/**
	 * 获取文件大小
	 * @param fileLength
	 * @return 文件大小字符串
	 */
	public String accquireFileSize(long fileLength);
	
	/**
	 * 根据登陆信息、操作结果、操作状态、操作意见获得操作信息对象
	 * @param loginer	登陆信息
	 * @param result	操作结果		1:不同意	2：同意
	 * @param status	操作状态		1:退回	2：暂存	3：提交
	 * @param opinion	操作意见
	 * @return
	 */
	public AuditInfo getAuditInfo(LoginInfo loginer, int result, int status, String opinion);
	
	/**
	 * 根据账号类别和研究人员账号所属人员id获得该研究人员国有的机构
	 * @param  personId 研究人员id
	 * @param accountType 账号类别		8：外部专家	9：教师 10：学生
	 */
	public Map<String,String> getUnitDetailByAccountInfo(String personId, AccountType accountType);
	
	/**
	 * 根据新添人员进行入库和关联处理
	 * @param personInfoJson 存储人员信息的Map对象 至少要有的键有：idcardType, idcardNumber, personName, personType, gender, agencyName, agencyId, divisionName, divisionType
	 * @return 存储人员id及所在机构id的Map对象 键有：personId, researcherId, divisionId
	 */
	public Map doWithNewPerson(Map personInfoJson);
	
	/**
	 * 读取Properties文件，根据key获取value
	 * @param clazz		当前类的类型类
	 * @param fileName	properties文件的路径
	 * @param key		属性值value对应的key
	 * @return	属性值value
	 */
	public String getPropertiesValue(Class clazz, String fileName, String key);

	/**
	 * DMSS文件上传
	 * @param filePath 文件路径
	 * @param form 文件上传附加表单信息
	 * @return dmss的文档ID
	 */
	public String flushToDmss(String filePath ,ThirdUploadForm form);
	
	/**
	 * 获取上传后的文件的相对目录  （比如 filePath为"upload/award/moesocial/app/2001/hello.doc",则返回目录为"award/moesocial/app/2001"）
	 * @param filePath 上传后的文件相对路径
	 * @return 上传后的文件的相对目录  
	 */
	public String getRelativeFileDir(String filePath);
	
	/**
	 * 获取文件名
	 * @param filePath 上传后的文件相对路径
	 * @return 文件名  (比如  /aaa/bb.txt 返回 bb.txt)
	 */
	public String getFileName(String filePath);
	
	/**
	 * 获取文件标题
	 * @param filePath 上传后的文件相对路径
	 * @return 文件名  (比如  /aaa/bb.txt 返回 bb.txt)
	 */
	public String getFileTitle(String filePath);
	
	/**
	 * 根据账号获取账号所属Id
	 * @param account
	 * @return belongId 当前账号所属的Id
	 */
	public String getBelongIdByAccount(Account account);
	
	/**
	 * 根据账号获取账号所属Id
	 * @param loginer
	 * @return belongId 当前登陆者账号所属的Id
	 */
	public String getBelongIdByLoginer(LoginInfo loginer);
	
	/**
	 * 根据SMDB的文件存储相对路径，确定该文件在DMSS中的分类目录
	 * @param filePath
	 * @return
	 */
	public String getDmssCategory(String filePath);
}
