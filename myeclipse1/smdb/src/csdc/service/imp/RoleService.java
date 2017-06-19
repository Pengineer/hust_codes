package csdc.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Account;
import csdc.bean.AccountRole;
import csdc.bean.Agency;
import csdc.bean.Right;
import csdc.bean.Role;
import csdc.bean.RoleAgency;
import csdc.bean.RoleRight;
import csdc.service.IRoleService;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.RoleInfo;

/**
 * 角色管理接口
 * @author 龚凡
 * @version 2011.04.11
 */
@SuppressWarnings("unchecked")
@Transactional
public class RoleService extends BaseService implements IRoleService {
	
	/**
	 * 判断角色名称是否存在
	 * @param roleName角色名称
	 * @return true存在，false不存在
	 */
	public boolean checkRoleName(String roleName) {
		if (roleName == null) {// 角色名称为空，视为角色名已存在
			return true;
		} else {// 角色名称非空，则查询数据库进行判断
			Map map = new HashMap();
			map.put("roleName", roleName);
			List<String> re = dao.query("select r.id from Role r where r.name = :roleName", map);// 查询指定名称的角色
			return re.isEmpty() ? false : true;
		}
	}

	/**
	 * 添加角色
	 * @param role角色对象
	 * @param rightNodeValue角色权限节点
	 * @return roleId角色ID
	 */
	public String addRole(Role role, String[] rightNodeValue, String defaultAgencyIds) {
		String roleId = dao.add(role);// 添加角色数据
		addRight(role, rightNodeValue);// 添加权限数据
		addType(role,defaultAgencyIds);// 添加类别信息
		return roleId;
	}

	/**
	 * 修改角色
	 * @param oldRole原始角色对象
	 * @param newRole更新角色对象
	 * @param rightNodeValue角色权限节点
	 * @param defaultAgencyIds新的默认机构id
	 * @return roleId角色ID
	 */
	public String modifyRole(Role oldRole, Role newRole, String[] rightNodeValue, String defaultAgencyIds) {
		// 获取角色类别信息状态（修改或未修改）
		boolean isTypeModified = this.checkIfTypeModified(oldRole, newRole, defaultAgencyIds);
		
		String roleId = oldRole.getId();
		// 更新角色基本信息
		oldRole.setName(newRole.getName());
		oldRole.setDescription(newRole.getDescription());
		oldRole.setCode(newRole.getCode());
		oldRole.setDefaultAccountType(newRole.getDefaultAccountType());
		dao.modify(oldRole);// 修改角色信息
		deleteRight(oldRole.getId());// 删除旧权限
		addRight(oldRole, rightNodeValue);// 添加新权限
		
		if (isTypeModified) {// 角色类别信息被修改，则更新角色类别信息
			deleteType(oldRole.getId());// 删除旧类别信息
			addType(oldRole,defaultAgencyIds);// 添加新类别信息
		}
		
		return roleId;
	}

	/**
	 * 判断newRole与oldRole相比，类别信息是否发生变化，
	 * 以确定更新oldRole信息时，是否需要更新类别信息。
	 * @param oldRole原始角色
	 * @param newRole更新角色
	 * @param defaultAgencyIds新的默认机构id
	 * @return true类别信息发生变化,false类别信息无变化
	 */
	private boolean checkIfTypeModified(Role oldRole, Role newRole, String defaultAgencyIds) {
		boolean isTypeModified = false;// 角色类别信息是否修改标志
		/**
		 * 目前可能的情况包括：
		 * 1、非默认角色、指定账号类型的默认角色、指定机构的默认角色，三者类型之间相互转换；
		 * 2、指定账号类型的默认角色，默认账号类型发生变化；
		 * 3、指定机构的默认角色，默认机构发生变化或应用到的账号类型发生变化；
		 * 角色信息中defaultAccountType为非空，长度为15或2。
		 */
		String oldAccountType = oldRole.getDefaultAccountType();// 原始角色账号类别信息
		String newAccountType = newRole.getDefaultAccountType();// 更新角色账号类别信息
		if (oldAccountType.equals(newAccountType)) {// 账号类别信息未发生变化，则继续判断机构ID是否发生变化
			if (oldAccountType.length() == 2) {// 如果为机构默认角色，则需判断应用到的机构是否发生变化
				String oldAgencyId = "";// 原始角色机构ID信息
				String newAgencyId = defaultAgencyIds;// 更新角色机构ID信息
				Map parMap = new HashMap();
				parMap.put("oldRoleId", oldRole.getId());
				List<RoleAgency> oldRoleAgencies = dao.query("select ra from RoleAgency ra where ra.role.id =:oldRoleId", parMap);
				for(RoleAgency oldRoleAgency:oldRoleAgencies){
					oldAgencyId += oldRoleAgency.getDefaultAgency().getId() + "; ";
				}
				oldAgencyId = oldAgencyId.substring(0, oldAgencyId.length() - 2);
				if (!oldAgencyId.equals(newAgencyId)) {// 机构ID发生变化，则表明角色类别信息发生变化
					isTypeModified = true;
				}
			}
		} else {// 账号类别发生变化，则表明角色类别信息发生变化
			isTypeModified = true;
		}
		return isTypeModified;
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

	/**
	 * 添加角色类别
	 * @param role角色对象
	 */
	private void addType(Role role,String defaultAgencyIds) {
		if (defaultAgencyIds != null && !defaultAgencyIds.isEmpty()) {
			addTypeAgency(role,defaultAgencyIds);// 添加机构类别
		} else {
			addTypeAccount(role);// 添加账号类别
		}
	}

	/**
	 * 删除角色类别
	 * @param roleId角色对象
	 */
	private void deleteType(String roleId) {
		Map map = new HashMap();
		map.put("roleId", roleId);
		List<String> accountRoleIds = dao.query("select ar.id from AccountRole ar where ar.role.id = :roleId", map);// 获得账号对该角色的引用关系
		for (String entityId : accountRoleIds){
			dao.delete(AccountRole.class, entityId);// 删除账号对该角色的引用
		}
	}

	/**
	 * 添加账号类别
	 * @param role角色对象
	 */
	private void addTypeAccount(Role role) {
		if (!role.getDefaultAccountType().equals(RoleInfo.ERROR_UNDEFAULT_ROLL)) {// 非默认角色不需要级联更新，指定账号类型的角色进行级联更新
			String newType = role.getDefaultAccountType();// 分配状态，15位01字符串
			List<int[]> addType = new ArrayList<int[]>();// 记录应用状态
			for (int i = 0; i < newType.length(); i++) {// 遍历15位字符串，查找应用到的账号类型
				char newchar = newType.charAt(i);
				if (newchar == '1') {// 该位为'1'，表示应用，获取应用到的账号级别与类别信息
					// RoleInfo.ERROR_DEFAULT_ACCOUNT_TYPE记录了对应15位字符串所指账号的级别与类别信息
					addType.add(RoleInfo.ERROR_DEFAULT_ACCOUNT_TYPE[i]);
				}
			}
			
			if (addType != null && !addType.isEmpty()) {// 如果有应用到的账号类型，则建立账号与角色的引用关系
				for (int[] o : addType) {// 每次应用一类账号
					Map mapType = new HashMap();
					// 设置账号级别与账号类别参数
					AccountType accountType = AccountType.UNDEFINED;
					AccountType type = accountType.chageType(o[0]);
//					mapType.put("type", type);
//					mapType.put("isPrincipal", o[1]);
					List<Account> accounts = dao.query("select a from Account a where a.type = '" + type +"' and a.isPrincipal = ? ", o[1]);// 查询指定类型的账号
//					List<Account> accounts = dao.query("select a from Account a where a.type = :type and a.isPrincipal = :isPrincipal", mapType);// 查询指定类型的账号
					for (Account a : accounts) {// 遍历所有账号，应用此角色
						AccountRole ar = new AccountRole();
						ar.setRole(role);
						ar.setAccount(a);
						dao.add(ar);// 添加账号与角色对应关系记录
					}
				}
			}
		}
	}

	/**
	 * 添加机构类别
	 * @param role角色对象
	 */
	private void addTypeAgency(Role role, String defaultAgencyIds) {
		// 建立此角色新的引用关系(主账号、子账号)(部、省、部属高校、地方高校)
		String[] agencyIds = defaultAgencyIds.split("; ");// 将机构ID拆分为数组
		String applyTo = role.getDefaultAccountType();// 获取应用到的账号类别
		
		// 部级、省级、校级主账号查询语句，并满足账号所属机构的ID在agencyIds中
		String hqlMain = "select a from Account a where (a.type = 'MINISTRY' or a.type = 'PROVINCE' or a.type = 'MINISTRY_UNIVERSITY' or a.type = 'LOCAL_UNIVERSITY') and a.isPrincipal = 1 and a.agency.id in (:agencyIds)";
		// 部级、省级、校级子账号查询语句，并满足子账号所属机构的ID在agencyIds中
		String hqlSub = "select a from Account a, Officer o where (a.type = 'MINISTRY' or a.type = 'PROVINCE' or a.type = 'MINISTRY_UNIVERSITY' or a.type = 'LOCAL_UNIVERSITY') and a.isPrincipal = 0 and a.officer.id = o.id and o.agency.id in (:agencyIds)";
		
		List<Account> accounts = new ArrayList<Account>();// 账号集合，缓存最终此角色应用到的账号
		Map map = new HashMap();
		map.put("agencyIds", agencyIds);
		
		if (applyTo.equals("11")) {// 应用到主账号和子账号，将两次查询的结果合并
			accounts = dao.query(hqlMain, map);
			accounts.addAll(dao.query(hqlSub, map));
		} else if (applyTo.equals("10")) {// 应用到主账号，则只查询主账号
			accounts = dao.query(hqlMain, map);
		} else if (applyTo.equals("01")) {// 应用到子账号，则只查询子账号
			accounts = dao.query(hqlSub, map);
		} else {// 其它(00)表示不应用到任何账号
		}
		
		for (Account a : accounts) {// 遍历应用到的账号，建立账号对角色的引用关系
			AccountRole ar = new AccountRole();
			ar.setRole(role);
			ar.setAccount(a);
			dao.add(ar);// 添加账号与角色对应关系记录
		}
	}

	/**
	 * 查找角色，包括角色创建者信息
	 * @param roleId角色ID
	 * @return role角色对象
	 */
	public Role viewRole(String roleId) {
		Map map = new HashMap();
		map.put("roleId", roleId);
		try {
			dao.query("select r from Role r left join fetch r.account a where r.id = :roleId", map);// 查询指定的角色
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Role> roles = dao.query("select r from Role r left join fetch r.account a where r.id = :roleId", map);// 查询指定的角色
		return roles.isEmpty() ? null : roles.get(0);
	}

	/**
	 * 删除角色
	 * @param roleIds角色ID集合
	 */
	public void deleteRole(List<String> roleIds) {
		for (String entityId : roleIds ){
			dao.delete(Role.class, entityId);// 根据ID删除角色，账号与角色的对应关系，角色与权限的对应关系会自动删除，程序不做处理
		}
	}

	/**
	 * 根据角色所属机构ID，查找其名称，并按部、省、校的顺序返回
	 * @param agencyIds待查找的机构ID字符串，以"; "分隔
	 * @return [][]一维区分部、省、校；二维区分ID、NAME
	 */
	public String[][] getAgencyIdAndName(String agencyIds) {
		String[][] idAndName = {// 用于存储机构ID和NAME，初始化为""
				{"", ""},// 存储部级机构ID和NAME
				{"", ""},// 存储省级机构ID和NAME
				{"", ""}// 存储校级机构ID和NAME
		};
		if (agencyIds != null && !agencyIds.isEmpty()) {// 机构ID存在，则查询数据库，并将多个同类机构的ID和NAME合并为"; "分隔的字符串
			String[] tmpAgencyId = agencyIds.split("; ");// 将机构ID字符串拆分为数组，便于遍历
			Agency agency;// 缓存机构对象
			int type = 0;// 缓存机构类型(1部级机构，2省级机构，3|4校级机构)
			for (int i = 0; i < tmpAgencyId.length; i++) {// 遍历所有ID，查找名称及确定类型
				agency = (Agency) dao.query(Agency.class, tmpAgencyId[i]);
				if (agency != null) {
					type = agency.getType();
					if (type == 1) {// 部级机构，ID和NAME存入数组0号位
						idAndName[0][0] += agency.getId() + "; ";
						idAndName[0][1] += agency.getName() + "; ";
					} else if (type == 2) {// 省级机构，ID和NAME存入数组1号位
						idAndName[1][0] += agency.getId() + "; ";
						idAndName[1][1] += agency.getName() + "; ";
					} else if (type == 3 || type == 4) {// 校级机构，ID和NAME存入数组2号位
						idAndName[2][0] += agency.getId() + "; ";
						idAndName[2][1] += agency.getName() + "; ";
					}
				}
			}
			
			// 遍历ID、NAME数组，清除末尾的"; "
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 2; j++) {
					if (!idAndName[i][j].equals("")) {// 字符串非""，则需要清楚末尾的"; "
						idAndName[i][j] = idAndName[i][j].substring(0, idAndName[i][j].length() - 2);
					}
				}
			}
		}
		return idAndName;
	}

	/**
	 * 获得指定角色的权限节点值，若未指定角色，则获取所有权限的节点值
	 * @param roleId指定的角色ID
	 * @return list权限节点值集合
	 */
	public List<String> getRightNodeValue(String roleId) {
		List<String> re;// 权限节点集合
		if (roleId == null) {// 未指定角色，则读取所有权限节点
			re = dao.query("select r.nodevalue from Right r");
		} else {// 指定角色，则读取该角色所有权限节点
			Map map = new HashMap();
			map.put("roleId", roleId);
			re = dao.query("select r.nodevalue from Right r left join r.roleRight rR where rR.role.id = :roleId", map);
		}
		return re;
	}

	/**
	 * 生成权限树，并设置默认选中
	 * @param roleId指定的角色
	 * @param str默认选中
	 * @return 树结构文档
	 */
	public Document createRightXML(String roleId, List<String> str, LoginInfo loginer, String mainRoleId, String keyword) {
		List<Right> rights = null;
		List<String> nodevalue = null;
		if (!loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {// 非系统管理员在创建角色的时候需要过滤权限树
			Map map = new HashMap();
			Account account = loginer.getAccount();
			map.put("account", account);
			if (roleId != null && !roleId.isEmpty()) {
				map.put("roleId", roleId);
				map.put("keyword", "%" + keyword + "%");
//				map.put("mainRoleId", mainRoleId);
				rights = dao.query("select r from Right r, RoleRight rr where rr.right.id = r.id and rr.role.id = :roleId and LOWER(r.name) like :keyword order by r.nodevalue asc", map);
				nodevalue = dao.query("select r.nodevalue from Right r, RoleRight rr where rr.right.id = r.id and rr.role.id = :roleId and LOWER(r.name) like :keyword order by r.nodevalue asc", map);
			} else {
				map.put("mainRoleId", mainRoleId);
				map.put("keyword", "%" + keyword + "%");
				rights = dao.query("select distinct r from Right r, RoleRight rr where rr.right.id = r.id and rr.role.id = :mainRoleId and LOWER(r.name) like :keyword order by r.nodevalue asc", map);
				nodevalue = dao.query("select distinct r.nodevalue from Right r, RoleRight rr where rr.right.id = r.id and rr.role.id = :mainRoleId and LOWER(r.name) like :keyword order by r.nodevalue asc", map);
			}
			
		} else {
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
			if( allRights.get(i).getNodevalue().length() / 2 > MAXlength ) {
				MAXlength = allRights.get(i).getNodevalue().length() / 2;
			}
			rightMap.put(allRights.get(i).getNodevalue(), allRights.get(i).getName());
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
			String idString = rights.get(i).getNodevalue();
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
	 * 根据权限节点值获取指定权限
	 * @param nodevalue权限节点值
	 * @return 权限对象
	 */
	private Right getRightByNodeValue(String nodevalue) {
		Map map = new HashMap();
		map.put("nodevalue", nodevalue);
		List<Right> list = dao.query("select r from Right r where r.nodevalue = :nodevalue order by r.nodevalue asc ", map);// 根据节点查询权限对象
		return list.get(0);
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
