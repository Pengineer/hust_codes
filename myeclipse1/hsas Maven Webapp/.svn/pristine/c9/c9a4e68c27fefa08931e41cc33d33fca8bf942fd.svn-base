package csdc.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import com.sun.net.httpserver.Authenticator.Success;




import csdc.model.Right;
import csdc.model.Role;
import csdc.model.RoleRight;
import csdc.service.IRoleService;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;

/**
 * 
 * 角色管理接口实现
 * @author huangxw
 *
 */
@Service
@Transactional
public class RoleService extends BaseService implements IRoleService {

	@Override
	public boolean checkRoleName(String roleName) {
		if(roleName == null){
			return true;// 角色名称为空，视为角色名已存在
		} else {
			Map<String,String> map = new HashMap<String, String>();
			map.put("roleName", roleName);
			List <Role> list = dao.query("from Role r where r.name = :roleName", map);
			return !list.isEmpty() ;
		}
	}

	@Override
	public String addRole(Role role, String[] rightNodeValue) {
		String roleId = (String) dao.add(role);// 添加角色数据
		addRight(role, rightNodeValue);// 添加权限数据
		
		return roleId;
	}

	@Override
	public String modifyRole(Role oldRole, Role newRole,
			String[] rightNodeValue) {
		
		String roleId = oldRole.getId();
		oldRole.setDescription(newRole.getDescription());
		oldRole.setAccounts(newRole.getAccounts());
		oldRole.setName(newRole.getName());
		oldRole.setRoleRights(newRole.getRoleRights());
		dao.modify(oldRole);
		deleteRight(roleId);//删除旧权限
		addRight(oldRole, rightNodeValue);// 添加新权限
		
		return roleId;
	}

	@Override
	public Role viewRole(String roleId) {
		
		return dao.query(Role.class, roleId);
	}

	
	@Override
	public void deleteRole(List<String> roleIds) {
		
		if(roleIds!=null && !roleIds.isEmpty()){
			
			for(String id : roleIds){
				dao.delete(Role.class, id);
			}
		}
		
	}
	
	
	/**
	 * 删除角色权限
	 * @param roleId角色ID
	 */
	private void deleteRight(String roleId) {
		Map map = new HashMap();
		map.put("roleId", roleId);
		List<String> roleRightIds = dao.query("select rr.id from RoleRight rr where rr.role.id = :roleId", map);// 获得该角色对权限的引用关系
		for (String entityId : roleRightIds){
			dao.delete(RoleRight.class, entityId);
		}
	}


	@Override
	public List<String> getRightNodeValue(String roleId) {
		List<String> re;// 权限节点集合
		if (roleId == null) {// 未指定角色，则读取所有权限节点
			re = dao.query("select r.nodeValue from Right r");
		} else {// 指定角色，则读取该角色所有权限节点
			Map map = new HashMap();
			map.put("roleId", roleId);
			re = dao.query("select r.nodeValue from Right r left join r.roleRight rR where rR.role.id = :roleId", map);
		}
		return re;
	}
	
	/**
	 * 添加角色权限
	 * @param role角色对象
	 * @param rightNodeValue权限节点
	 */
	private void addRight(Role role, String[] rightNodeValue) {
		if (rightNodeValue != null) {// 权限不为空，则给指定角色添加该权限
			for (String o : rightNodeValue) {// 遍历所有权限，进行添加
				RoleRight roleright = new RoleRight();
				roleright.setRole(role);
				roleright.setRight(getRightByNodeValue(o));
				dao.add(roleright);// 添加角色对权限的引用关系
			}
		}
	}

	
	
	/**
	 * 根据权限节点值获取指定权限
	 * @param nodevalue权限节点值
	 * @return 权限对象
	 */
	private Right getRightByNodeValue(String nodeValue) {
		Map map = new HashMap();
		map.put("nodeValue", nodeValue);
		List<Right> list = dao.query("select r from Right r where r.nodeValue = :nodeValue order by r.nodeValue asc ", map);// 根据节点查询权限对象
		return list.get(0);
	}
	@Override
	public Document createRightXML(String roleId, List<String> str,
			LoginInfo loginer, String mainRoleId, String keyword) {
		List<Right> rights = null;
		List<String> nodevalue = null;
		
			Map map = new HashMap();
			if (roleId != null && !roleId.isEmpty()) {
				map.put("keyword", "%" + keyword + "%");
				map.put("roleId", roleId);
				rights = dao.query("select r from Right r, RoleRight rr where rr.role.id = :roleId and rr.right.id = r.id and LOWER(r.name) like :keyword order by r.nodevalue asc", map);
				nodevalue = dao.query("select r.nodevalue from Right r, RoleRight rr where rr.role.id = :roleId and rr.right.id = r.id and LOWER(r.name) like :keyword order by r.nodevalue asc", map);
			} else {
				map.put("keyword", "%" + keyword + "%");
				rights = dao.query("select r from Right r where LOWER(r.name) like :keyword  order by r.nodevalue asc", map);
				nodevalue = dao.query("select r.nodevalue from Right r where LOWER(r.name) like :keyword  order by r.nodevalue asc", map);
			}
		
		if (str != null) {
			Map session = ActionContext.getContext().getSession();
			List<String> oldNodeValue = new ArrayList<String>();
			for (int i = 0; i < str.size(); i++) {
				if (!nodevalue.contains(str.get(i))) {
					oldNodeValue.add(str.get(i));
				}
			}
			session.put("oldNodeValue", oldNodeValue);
		}
		List<Right> allRights = dao.query("select r from Right r order by r.nodevalue asc");
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


}
