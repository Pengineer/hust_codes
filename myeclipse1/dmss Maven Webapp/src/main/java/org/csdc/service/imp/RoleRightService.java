package org.csdc.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.csdc.bean.CheckTreeNode;
import org.csdc.domain.sm.security.AddRoleForm;
import org.csdc.model.Category;
import org.csdc.model.CategorySecurity;
import org.csdc.model.Right;
import org.csdc.model.Role;
import org.dom4j.Element;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色权限管理业务类
 * @author jintf
 * @date 2014-6-16
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class RoleRightService extends BaseService {
	
	@Autowired
	private CategoryService categoryService;
	
	private static final String TMP_NODE_VALUE = "nodeValue";// 创建权限树时，缓存的已选项
	/**
	 * 判断角色名称是否存在
	 * @param roleName 角色名称
	 * @return true存在，false不存在
	 */
	public boolean isExistRoleName(String roleName) {
		if (roleName == null || roleName.isEmpty()) {
			return true;
		} else {
			Map map = new HashMap();
			map.put("roleName", roleName);
			List<String> re = baseDao.query("select r.id from Role r where r.name = :roleName", map);
			return re.isEmpty() ? false : true;
		}
	}

	/**
	 * 获得指定角色的权限节点值，若未指定角色，则获取所有权限的节点值
	 * @param roleId 指定的角色ID
	 * @return list 权限节点值集合
	 */
	public List<String> getRightNodeValue(String roleId) {
		List<String> re;
		if (roleId == null ||roleId.isEmpty()) {
			re = baseDao.query("select r.nodevalue from Right r");
		} else {
			Map map = new HashMap();
			map.put("roleId", roleId);
			re = baseDao.query("select r.nodevalue from Right r left join r.roleRight rR where rR.role.id = :roleId", map);
		}
		return re;
	}
	
	/**
	 * 添加角色
	 * @param form 角色添加表单
	 */	
	public void addOrModifyRole(AddRoleForm form) {
		Role role = new Role();
		if(null !=form.getId() && !form.getId().isEmpty()){
			role = baseDao.query(Role.class,form.getId());
		}
		role.setName(form.getName());
		role.setDescription(form.getDescription());
		if(!form.getDomains().isEmpty()){
			String[] doaminIds = form.getDomains().split(",");	
			for (String domainId: doaminIds) {
				CategorySecurity categorySecurity = new CategorySecurity();
				categorySecurity.setAddCategory(true);
				categorySecurity.setCheckIn(true);
				categorySecurity.setCheckOut(true);
				categorySecurity.setDelete(true);
				categorySecurity.setDownload(true);
				categorySecurity.setRead(true);
				categorySecurity.setWrite(true);
				categorySecurity.setSecurity(true);
				categorySecurity.setRole(role);
				Category category = baseDao.query(Category.class,domainId);		
				categorySecurity.setCategory(category);
				baseDao.add(categorySecurity);
			}
		}
		if(!form.getRights().isEmpty()){
			String[] rightIds = form.getRights().split(",");	
			Set<Right> rights = baseDao.query(Right.class,rightIds);
			role.setRights(rights);
		}
		baseDao.addOrModify(role);		
	}
	
	

	/**
	 * 添加权限
	 * @param right权限对象
	 * @return 权限ID
	 */
	public String addRight(Right right) {
		String rightId = (String)baseDao.add(right);
		return rightId;
	}

	/**
	 * 根据权限节点值获取指定权限
	 * @param nodevalue 权限节点值
	 * @return 权限对象
	 */
	private Right getRightByNodeValue(String nodevalue) {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select r from Right r where r.nodevalue = :nodevalue order by r.nodevalue asc ");
		map.put("nodevalue", nodevalue);
		List list = baseDao.query(hql.toString(), map);
		return (Right)list.get(0);
	}

	/**
	 * 递归生成某节点的子节点
	 * @param nodevalue 某节点的node值
	 * @param e 父节点
	 * @param rightMap 用于方便查询生成的权限节点值与描述的键值对
	 * @param str 需要勾选中的节点的节点值串，逗号分隔开
	 * @param rightNodes 权限节点集合
	 * @param rightNames 权限名称集合
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

	/**
	 * 生成权限树
	 * @param rights 以选中的权限
	 * @return
	 */
	public CheckTreeNode createRightTree(Set<Right> rights){
		CheckTreeNode root;//根节点不显示
		Object[] result = (Object[]) baseDao.queryUnique("select c.id, c.name from Right c where c.parent.id =null");
		root = new CheckTreeNode(result[0].toString(), result[1].toString());
		getChildNodes(root,rights);
		return root;
	}
	
	/**
	 * 获取权限子节点
	 * @param node 权限节点
	 * @param myRights 子节点
	 */
	private void getChildNodes(CheckTreeNode node,Set<Right> myRights){
		List<Right> rights = baseDao.query("select c from Right c where c.parent.id =? order by weight asc",node.getId());
		if(rights.size()==0){
			node.setLeaf(true);
		}
		for (Right right : rights) {
			CheckTreeNode n = new CheckTreeNode(right.getId(), right.getName());
			if(myRights.contains(right)){
				n.setChecked(true);
			}
			node.getChildren().add(n);
			getChildNodes(n,myRights);
		}
	} 
	
	/**
	 * 删除角色
	 * @param id 角色ID
	 */
	public void deleteRole(String id){
		Role role = baseDao.query(Role.class,id);
    	role.setRights(null);
    	baseDao.modify(role);
    	baseDao.flush();
    	baseDao.delete(role);
	}
	
	/**
	 * 根据角色ID获取该角色权限
	 * @param id 角色ID
	 * @return
	 */
	public Set<Right> getRightsByRoleId(String id){		
		Set<Right> rights = baseDao.query(Role.class,id).getRights();
		Hibernate.initialize(rights);
		return rights;
	}
	
	/**
	 * 根据角色获取管理域
	 * @param id 角色ID
	 * @return
	 */
	public Set<Category> getDomainByRoleId(String id){
		Set<Category> categories = baseDao.query(Role.class,id).getCategories();
		Hibernate.initialize(categories);
		return categories;
	}
	
	/**
	 * 获取该角色选中的权限节点
	 * @param roleId 角色ID
	 * @return
	 */
	public CheckTreeNode getSelectedRightTree(String roleId){
		Set<Right> rights = getRightsByRoleId(roleId);
    	return createRightTree(rights);
	}
	
	/**
	 * 获取该角色选中的管理域
	 * @param roleId 角色ID
	 * @return
	 */
	public List getSelectedDomain(String roleId){
		List<Category> domains = categoryService.getAllDomains();
    	Set<Category> myDomains = getDomainByRoleId(roleId);
    	List<Object[]> list = new ArrayList<Object[]>();
    	for(Category domain:domains){
    		Object[] o = new Object[4];
    		o[0] = domain.getId();
    		o[1] = domain.getName();
    		o[2] = domain.getDescription();
    		if(myDomains.contains(domain)){
    			o[3] = true;
    		}else{
    			o[3] = false;
    		}
    		list.add(o);
    	}
    	return list;
	}
	
	/**
	 * 根据角色ID获取角色表单
	 * @param roleId 角色ID
	 * @return
	 */
	public AddRoleForm getAddRoleForm(String roleId){		
		Role role = baseDao.query(Role.class,roleId);
    	AddRoleForm form = new AddRoleForm();
    	form.setId(role.getId());
    	form.setName(role.getName());
    	form.setDescription(role.getDescription());
    	String domains = "";
    	String rights = "";
    	return form;
	}

}