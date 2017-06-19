package csdc.service;

import java.util.List;

import csdc.model.Right;
/**
 * 权限管理接口
 * @author 龚凡
 * @version 2011.04.11
 * 
 * @author huangxw
 * @version 2015.08.10
 */

public interface IRightService extends IBaseService {
	
	/**
	 * 判断权限名称是否存在
	 * @param rightName权限名称
	 * @return true存在，false不存在
	 */
	public boolean checkRightName(String rightName);

	/**
	 * 判断权限代码是否存在
	 * @param rightCode权限代码
	 * @return true存在，false不存在
	 */
	public boolean checkRightCode(String rightCode);

	/**
	 * 判断权限节点值是否存在
	 * @param rightNode 权限节点值
	 * @return true 存在，false 不存在
	 */
	public boolean checkRightNode(String rightNode);

	/**
	 * 添加权限
	 * @param right权限对象
	 * @return 权限ID
	 */
	public String addRight(Right right);

	/**
	 * 修改权限
	 * @param oldRight原始权限对象
	 * @param newRight更新权限对象
	 * @return 权限ID
	 */
	public String modifyRight(Right oldRight, Right newRight);

	/**
	 * 查看权限
	 * @param rightId权限ID
	 * @return 权限对象
	 */
	public Right viewRight(String rightId);

	/**
	 * 删除权限
	 * @param rightIds权限ID集合
	 */
	public void deleteRight(List<String> rightIds);

}
