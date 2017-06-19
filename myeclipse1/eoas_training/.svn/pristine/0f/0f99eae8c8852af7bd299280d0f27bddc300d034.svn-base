package csdc.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.loading.PrivateClassLoader;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import csdc.bean.Right;
import csdc.bean.Role;
import csdc.bean.Role_Right;
import csdc.dao.IBaseDao;
import csdc.mappers.RightMapper;
import csdc.mappers.RoleMapper;
import csdc.mappers.RoleRightMapper;
import csdc.service.IRoleService;



public class RoleService extends BaseService implements IRoleService {
	private RightMapper rightDao;
	private RoleRightMapper roleRightDao;
    private IBaseDao baseDao;
    private Map map = new HashMap();
	// ==============================================================
	// 函数名：checkRolename
	// 函数描述：检测角色名称是否存在
	// 返回值：true表示存在，false表示不存在。
	// ==============================================================
	@SuppressWarnings("unchecked")
	public boolean checkRolename(String name) {
		Map map = new HashMap();
		map.put("name", name);
		return baseDao.list(Role.class, map).size() == 0 ? false : true;
	}

	/**
	 * 删除角色权限
	 * @param roleId角色ID
	 */
	private void deleteRight(String roleId) {
		Map map = new HashMap();
		map.put("roleId", roleId);
		List<Role_Right> roleRights = (List)baseDao.list(Role_Right.class, map);// 获得该角色对权限的引用关系
		for (Role_Right roleRight : roleRights){
			baseDao.delete(Role_Right.class, roleRight.getId());
		}
	}
	/**
	 * 添加角色
	 * @param role角色对象
	 * @param rightNodeValue角色权限节点
	 * @return roleId角色ID
	 */
	public String addRole(Role role, String[] rightNodeValue) {
		baseDao.add(role);// 添加角色数据
		String roleId = role.getId();
		addRight(role, rightNodeValue);// 添加权限数据
		return roleId;
	}
	
	/**
	 * 修改角色
	 * @param oldRole原始角色对象
	 * @param newRole更新角色对象
	 * @param rightNodeValue角色权限节点
	 * @return roleId角色ID
	 */
	public String modifyRole(Role oldRole, Role newRole, String[] rightNodeValue) {
		// 获取角色类别信息状态（修改或未修改）
		String roleId = oldRole.getId();
		// 更新角色基本信息
		oldRole.setName(newRole.getName());
		oldRole.setDescription(newRole.getDescription());
		try {
			baseDao.modify(oldRole);
		} catch (Exception e) {
			e.printStackTrace();
		}// 修改角色信息
		deleteRight(oldRole.getId());// 删除旧权限
		addRight(oldRole, rightNodeValue);// 添加新权限
		return roleId;
	}
	
	/**
	 * 添加角色权限
	 * @param role角色对象
	 * @param rightNodeValue权限节点
	 */
	private void addRight(Role role, String[] rightNodeValue) {
		if (rightNodeValue != null) {// 权限不为空，则给指定角色添加该权限
			for (String o : rightNodeValue) {// 遍历所有权限，进行添加
				Role_Right roleRight = new Role_Right();
				roleRight.setRole(role);
				try {
					getRightByNodeValue(o);
				} catch (Exception e) {
					e.printStackTrace();
				}
				roleRight.setRight(getRightByNodeValue(o));
				baseDao.add(roleRight);// 添加角色对权限的引用关系
			}
		}
	}
	


	/**
	 * 根据权限节点值获取指定权限
	 * @param nodevalue权限节点值
	 * @return 权限对象
	 */
	private Right getRightByNodeValue(String nodeValue) {
		map.put("nodeValue", nodeValue);
		System.out.println(Right.class.getName());
		System.out.println(Right.class.getSimpleName());
		Right right = (Right) baseDao.load(Right.class.getName() + ".loadByNodeValue", map);
		return right;
/*		List<Right> list = rightDao.selectByNodeValue(nodeValue);// 根据节点查询权限对象
		return list.get(0);*/
	}
	
	/**
	 * 生成权限树，并设置默认选中
	 * @param roleId指定的角色
	 * @param str默认选中
	 * @return 树结构文档
	 */
	public Document createRightXML(String roleId, List<String> str) {
		Map map = new HashMap();
		map.put("roleId", roleId);
		List<Right> rights = null;
		rights = baseDao.list(Right.class,map);
			/*rights = rightDao.selectRightByRoleId(roleId);*/

		List<Right> allRights = baseDao.list(Right.class, null);
		Map<String, String> rightMap = new HashMap<String, String>();
		int MAXlength = 0;
		for(int i = 0; i < allRights.size(); i++) {
			//获取所有权限后生成权限的节点值和描述的键值对
			if( allRights.get(i).getNodeValue().length() / 2 > MAXlength ) {
				MAXlength = allRights.get(i).getNodeValue().length() / 2;
			}
			rightMap.put(allRights.get(i).getNodeValue(), allRights.get(i).getName());
		}
		
		//将长度为2,4,6,8...的权限节点值分别放入对应的数组
		List<List<String>> rightNodes = new ArrayList();
		List<List<String>> rightNames = new ArrayList();
		for(int i = 0; i <= MAXlength; i++) {
			List<String> tmp1 = new ArrayList<String>();
			List<String> tmp2 = new ArrayList<String>();
			rightNodes.add(tmp1);
			rightNames.add(tmp2);
		}
		
		//根据需要生成树的权限的list，将其放入其节点长度对应的数组
		for(int i = 0; i < rights.size(); i++) {
			String idString = rights.get(i).getNodeValue();
			for(int j = 1; j <= MAXlength; j++) {
				if(idString.length() / 2 >= j && ( !rightNodes.get(j).contains(idString.substring(0, 2 * j)) )) {
					rightNodes.get(j).add(idString.substring(0, 2 * j));
					rightNames.get(j).add(rightMap.get(idString.substring(0, 2 * j)) == null ? "未定义权限" : rightMap.get(idString.substring(0, 2 * j)));
				}
			}
		}
		
		//根节点的建立
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("utf-8");
		Element root = document.addElement("tree");
		root.addAttribute("id", "0");
		Element item0 = root.addElement("item");
		item0.addAttribute("text", "所有权限");
		item0.addAttribute("id", "all");
		item0.addAttribute("im0", "folder_closed.gif");
		item0.addAttribute("im1", "folder_open.gif");
		item0.addAttribute("im2", "folder_closed.gif");
		item0.addAttribute("open", "1");
		item0.addAttribute("select", "1");
		
		//根节点的节点值长度为2*0，第一层节点值长度为2*1...
		rightNodes.get(0).add("");
		
		//递归添加节点的叶子节点
		addTreeItem(rightNodes.get(0).get(0), item0, rightMap, str, rightNodes, rightNames);

		return document;
	}
	
	/**
	 * 递归生成某节点的子节点
	 * @param nodevalue某节点的node值
	 * @param e父节点
	 * @param rightMap用于方便查询生成的权限节点值与描述的键值对
	 * @param str需要勾选中的节点的节点值串，逗号分隔开
	 * @param rightNodes权限节点集合
	 * @param rightNames权限名称集合
	 */
	private void addTreeItem(String nodevalue, Element e, Map<String, String> rightMap, List<String> str, List<List<String>> rightNodes, List<List<String>> rightNames) {
		int len = nodevalue.length() / 2;
		//如果该节点没有子节点就返回
		if(rightNodes.size() < len + 2 || rightNodes.get(len + 1) == null || rightNodes.get(len + 1).size() == 0) {
			return ;
		}
		for(int i = 0; i < rightNodes.get(len + 1).size(); i++) {
			if( rightNodes.get(len + 1).get(i).substring(0, len * 2).equals(nodevalue) ) {
				Element t = e.addElement("item");
				t.addAttribute("text", rightMap.get(rightNodes.get(len + 1).get(i)) == null ? "未定义权限" : rightMap.get(rightNodes.get(len + 1).get(i)));
				t.addAttribute("id", rightNodes.get(len + 1).get(i));
				t.addAttribute("im0", "folder_closed.gif");
				t.addAttribute("im1", "folder_open.gif");
				t.addAttribute("im2", "folder_closed.gif");
				//如果当前节点的节点值在需要选中的list中，就设置为选中
				if(str != null && str.contains(rightNodes.get(len + 1).get(i))) {
					t.addAttribute("checked", "1");
				}
				//递归添加此节点的叶子节点
				addTreeItem(rightNodes.get(len + 1).get(i), t, rightMap, str, rightNodes, rightNames);
			}
		}
	}

	
	public RightMapper getRightDao() {
		return rightDao;
	}
	public void setRightDao(RightMapper rightDao) {
		this.rightDao = rightDao;
	}
	
	public RoleRightMapper getRoleRightDao() {
		return roleRightDao;
	}

	public void setRoleRightDao(RoleRightMapper roleRightDao) {
		this.roleRightDao = roleRightDao;
	}

	public IBaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
}