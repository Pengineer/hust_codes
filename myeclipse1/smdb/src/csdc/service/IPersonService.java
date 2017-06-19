package csdc.service;

import java.util.List;
import java.util.Map;

import csdc.bean.Academic;
import csdc.bean.Account;
import csdc.bean.Address;
import csdc.bean.BankAccount;
import csdc.bean.Passport;
import csdc.bean.Person;

/**
 * 人员管理service实现类
 * @author 雷达
 *
 */
public interface IPersonService  extends IBaseService {

	/**
	 * 判断系统是否有此身份证号码记录
	 * @param idcard 身份证号码
	 * @return
	 */
	public boolean checkIdcard(String idcard);

//	/**
//	 * 判断系统是否有此身份证号码记录，且此人是否专职人员
//	 * @param idcard 身份证号码
//	 * @return 
//	 */
//	public boolean checkOfficerByIdcard(String idcard);

	/**
	 * 根据证件号找到姓名
	 * @param idcard 身份证号码
	 * @return 姓名
	 */
	public String findNameByIdcard(String idcard);

	/**
	 * 根据证件号找到人员
	 * @param idcard 身份证号码
	 * @return 人
	 */
	public Person findPersonByIdcard(String idcard);

	/**
	 * 根据证件号和姓名判断人员是否存在
	 * @param idcardNumber 身份证号码
	 * @param name 姓名
	 * @return 	第一个元素 -- 0:不存在 1:存在且匹配 2:存在且不匹配
	 * 			第二个元素 -- person对象
	 */
	public Object[] checkIfPersonExists(String idcardNumber, String name);
	
	/**
	 * 根据姓名及证件号判断是否有此人（教师注册专用），此人是否已有账号
	 * @param idcardNumber证件号
	 * @param name姓名
	 * @return 	第一个元素——0：有人有账号 1：有人没账号 2：没人没账号
	 * 			第二个元素——person对象
	 * 			第三个元素——teacher对象
	 */
	public Object[] checkPersonInfo(String idcardNumber, String name);
	
	/**
	 * 合并时，初始化可选账号下拉选框
	 * @param incomeIds 可选的人员ID
	 * @param personType 人员类型
	 */
	public Map getOptionalPassportNames(String checkedIds,Integer personType);

	/**
	 * 检查用户名是否可用
	 * @param username
	 * @return true可用，false不可用
	 */
	public boolean checkUsername(String username);
	
	/**
	 * 检查邮箱是否可用
	 * @param email
	 * @return true可用，false不可用
	 */
	public boolean checkEmail(String email);
	
	// 证件类型
	public List<String> getIdcardTypeList();
	
	// 学历
	public List<String> getEducationBackgroundList();
	
	// 学位
	public List<String> getDegreeList();
	
	// 学生类别
	public List<String> getStudentTypeList();
	
	// 学生状态
	public List<String> getStudentStatusList();
	
	// 优秀学位论文等级
	public List<String> getExcellentGradeList();

	
	// 导师类型
	public List<String> getTutorTypeList();
	
	/**
	 * 根据证件号和姓名判断人员是否存在、是否是管理人员
	 * @param idcardNumber 身份证号码
	 * @param name 姓名
	 * @param officerId 管理人员id(添加时为空，修改时调用)
	 * @return 	第一个元素 -- 0:不存在 1:存在且匹配且不是管理员 2:存在且匹配且是管理员 3:存在且不匹配
	 * 			第二个元素 -- person对象
	 * 			第三个元素 -- officer对象
	 */
	public Object[] checkIfIsOfficer(String idcardNumber, String name, String officerId);

	/**
	 * 根据证件号和姓名找到人员
	 * @param idcard 身份证号码
	 * @param name 姓名
	 * @return 人
	 */
	public Object findPersonByIdcardAndName(String idcard, String name);

	/**
	 * 根据姓名、证件号判断是否已有专职officer
	 * @param personName 人员姓名
	 * @param idcardNumber 证件号
	 * @return 已有此人且有专职officer
	 */
	public boolean checkOfficerType(String personName, String idcardNumber);
	
	/**
	 * 根据ID号找到机构/院系/基地名
	 * @param id 机构/院系/基地ID
	 * @return 机构/院系/基地名
	 */
	public String findNameByADBId(String id, int type);

	/**
	 * 查找某人的学术信息
	 * @param person
	 * @return 学术信息
	 */
	public Academic findAcademic(Person person);

	/**
	 * 判断某人是否已经拥有专职教师职位
	 * @param idcardNumber 证件号
	 * @return 是:teacherID 否:null
	 */
	public String checkIfIsFulltimeTeacher(String idcardNumber);
	
	/**
	 * 修改人员
	 * @param person
	 * @param origin_person
	 * @return 人员id
	 */
	public String modifyPerson(Person person, Person origin_person);
	
	/**
	 * 删除人员
	 * @param type 1管理员 2专家 3教师 4学生
	 * @param entityIds ID集合
	 * @return true：能删除人员切删除成功		false：不能删除人员
	 */
	public int deletePerson(int type,List<String> entityIds);
	
	/**
	 * 根据人员id和人员类型获得账号id
	 * @param entityId 1:officerId;2/3/4:personId
	 * @param personType 1:officer;2:expert;3:teacher;4:student.
	 * @return List<String> acids
	 * @author 周中坚
	 */
	public List<String> getAccountByPerson(String entityId, int personType);
	
	/**
	 * 根据人员id和人员类型获得账号id
	 * @param entityId 1:officerId;2/3/4:personId
	 * @param personType 1:officer;2:expert;3:teacher;4:student.
	 * @return List<String> acids
	 * @author 周中坚
	 */
	public List<String> getAccountByEntityId(String entityId, int personType);
	
	/**
	 * 根据人员id和人员类型获得通行证id
	 * @param entityId 1:officerId;2/3/4:personId
	 * @param personType 1:officer;2:expert;3:teacher;4:student.
	 * @return List<String> acids
	 * @author 杨发强
	 */
	public List<String> getPassportByPersonId(String entityId, int personType);
	
	/**
	 * 根据人员id和人员类型获得账号
	 * @param entityId 1:officerId;2/3/4:personId
	 * @param type 1:管理人员； 2：外部专家； 3：教师； 4： 学生
	 * @return List<String> acids
	 */
	public Account getAccountByPersonId(String entityId, int type);
	
	/**
	 * 根据人员id和人员类型获得通信证
	 * @param entityId 1:officerId;2/3/4:personId
	 * @param personType 1:officer;2:expert;3:teacher;4:student.
	 * @return 通行证
	 * @author 金天凡
	 */
	public Passport getPassportByPerson(String entityId, int personType);
	/**
	 * 姓名变化时，维护项目成果奖励中存的人员姓名冗余信息
	 * @param id
	 * @param name
	 */
	public void updatePersonName(String id, String name);
	
	/**
	 * 输入人名时可能有word中复制出的奇怪字符“•”，前台让其校验通过，在后台替换为“·”
	 * @param nameString
	 * @return
	 */
	public String regularNames(String nameString);
	
	/**
	 * 判断数据库中的人员表结构是否有更改
	 * @return
	 */
	public boolean isPersonTablesExpired();
	
	/**
	 * 获取专业职称的ID
	 * @param academic
	 * @return
	 */
	public String getSpecialityTitleId(Academic academic);
	
	/**
	 * 获取专业职称的名字
	 * @param academic
	 * @return
	 */
	public String getSpecialityTitleName(Academic academic);
	
	/**
	 * 同步人员照片到DMSS服务器
	 * @param awardApplication
	 * @return DMSS文档持久化后的标识
	 */
	public String flushToDmss(Person person);
	
	/**
	 * 替换人员基本信息中的部分“-1”值
	 * @param person
	 * @return
	 */
	public Person setBaseInfo(Person person);

	/**
	 * 重置人员相关的银行账号信息
	 * @param originPerson
	 * @param bankList
	 * @return
	 */
	public Person resetBankInfo(Person originPerson, List<BankAccount> bankList);
	
	/**
	 * 重置人员的学术信息
	 * @param originPerson
	 * @param academic
	 * @return
	 */
	public Person modifyAcademic(Person originPerson, Academic academic);
	
	/**
	 * 设置人员的地址信息
	 * @param originPerson
	 * @param homeAddress
	 * @param officeAddress
	 * @return
	 */
	public Person setAddress(Person originPerson, List<Address> homeAddress, List<Address> officeAddress);
	
	/**
	 * 重置人员的地址信息
	 * @param originPerson
	 * @param homeAddress
	 * @param officeAddress
	 * @return
	 */
	public Person resetAddress(Person originPerson, List<Address> homeAddress, List<Address> officeAddress);

	/**
	 * 获取人员的家庭住址信息
	 * @param person
	 * @return
	 */
	public List getHomeAddress(Person person);
	
	/**
	 * 获取人员的办公住址信息
	 * @param person
	 * @return
	 */
	public List getOfficeAddress(Person person);
}
