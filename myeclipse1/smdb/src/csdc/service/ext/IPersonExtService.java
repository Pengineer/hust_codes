package csdc.service.ext;

import java.util.Map;

import csdc.service.IBaseService;

/**
 * 人员管理service实现类
 * @author 雷达
 *
 */
public interface IPersonExtService  extends IBaseService {

	/**
	 * 判断系统是否有此身份证号码记录
	 * @param idcard 身份证号码
	 * @return
	 */
	public boolean checkIdcard(String idcard);
	
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
	
	/**
	 * 根据高校id获取院系列表(id,name)list
	 * @param id
	 * @return
	 */
	public Map<String,String> getDepartmentByUniversity(String universityId);
	
	/**
	 * 将系统选项表中的专业职称的(name, description)组成list
	 * @param id
	 * @return
	 */
	public Map<String,String> getSpecialityTitleList();
	
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
}
