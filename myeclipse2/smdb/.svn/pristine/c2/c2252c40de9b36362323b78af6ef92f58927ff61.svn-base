package csdc.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Right;
import csdc.service.IRightService;

/**
 * 权限管理接口实现
 * @author 龚凡
 * @version 2011.04.11
 */
@SuppressWarnings("unchecked")
@Transactional
public class RightService extends BaseService implements IRightService {

	/**
	 * 判断权限名称是否存在
	 * @param rightName权限名称
	 * @return true存在，false不存在
	 */
	public boolean checkRightName(String rightName) {
		if (rightName == null) {// 权限名称为空，则视为已存在
			return true;
		} else {// 权限名称非空，则查询数据库进行判断
			Map map = new HashMap();
			map.put("rightName", rightName);
			List<String> re = dao.query("select r.id from Right r where r.name = :rightName", map);// 查询指定名称的权限
			return re.isEmpty() ? false : true;
		}
	}

	/**
	 * 判断权限代码是否存在
	 * @param rightCode权限代码
	 * @return true存在，false不存在
	 */
	public boolean checkRightCode(String rightCode) {
		if (rightCode == null) {// 权限代码为空，则视为已存在
			return true;
		} else {// 权限代码非空，则查询数据库进行判断
			Map map = new HashMap();
			map.put("rightCode", rightCode);
			List<String> re = dao.query("select r.id from Right r where r.code = :rightCode", map);// 查询指定代码的权限
			return re.isEmpty() ? false : true;
		}
	}

	/**
	 * 判断权限节点值是否存在
	 * @param rightNode权限节点值
	 * @return true存在，false不存在
	 */
	public boolean checkRightNode(String rightNode) {
		if (rightNode == null) {// 权限节点为空，则视为已存在
			return true;
		} else {// 权限节点非空，则查询数据进行判断
			Map map = new HashMap();
			map.put("rightNode", rightNode);
			List<String> re = dao.query("select r.id from Right r where r.nodevalue = :rightNode", map);// 查询指定节点的权限
			return re.isEmpty() ? false : true;
		}
	}

	/**
	 * 添加权限
	 * @param right权限对象
	 * @return 权限ID
	 */
	public String addRight(Right right) {
		String rightId = dao.add(right);// 添加权限数据
		return rightId;
	}

	/**
	 * 修改权限
	 * @param oldRight原始权限对象
	 * @param newRight更新权限对象
	 * @return 权限ID
	 */
	public String modifyRight(Right oldRight, Right newRight) {
		// 更新权限属性
		oldRight.setName(newRight.getName());
		oldRight.setDescription(newRight.getDescription());
		oldRight.setCode(newRight.getCode());
		oldRight.setNodevalue(newRight.getNodevalue());
		dao.modify(oldRight);// 修改权限数据
		return oldRight.getId();
	}

	/**
	 * 查看权限
	 * @param rightId权限ID
	 * @return 权限对象
	 */
	public Right viewRight(String rightId) {
		Right right = (Right) dao.query(Right.class, rightId);// 根据权限ID查询权限
		return right;
	}

	/**
	 * 删除权限
	 * @param rightIds权限ID集合
	 */
	public void deleteRight(List<String> rightIds) {
		for (String entityId : rightIds){
			dao.delete(Right.class, entityId);// 根据ID删除权限，角色与权限的对应关系会自动删除，程序不做处理
		}
	}

}
