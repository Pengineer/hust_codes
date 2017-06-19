package csdc.action.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import oracle.net.aso.l;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.Passport;
import csdc.bean.Role;
import csdc.bean.RoleAgency;
import csdc.bean.RoleRight;
import csdc.service.IRoleService;
import csdc.tool.bean.AccountType;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.RoleInfo;

/**
 * 角色表字段说明详见csdc.bean.Role.java中字段注释。
 * 角色管理模块，实现的功能包括：添加、删除、修改、查看。
 * @author 龚凡
 * @version 2011.04.11
 */
@SuppressWarnings("unchecked")
public class RoleAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private static final String HQL = "select r.id, r.name, r.description, a.id, pp.name from AccountRole ar, Role r left join r.account a left join a.passport pp left join r.roleAgency ra where 1=1  ";
	private static final String GROUP_BY = "group by r.id, r.name, r.description, a.id, pp.name";
	private static final String[] COLUMN = {
			"r.name",
			"r.description, r.name",
			"pp.name, r.name"
	};// 用于拼接的排序列
	private static final String PAGE_NAME = "rolePage";// 列表页面名称
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "r.id";// 上下条查看时用于查找缓存的字段
	private static final String TMP_ENTITY_ID = "roleId";// 用于session缓存实体的ID名称
	private static final String TMP_NODE_VALUE = "nodeValue";// 创建权限树时，缓存的已选项
	
	private IRoleService roleService;// 角色管理接口
	private Role role;// 角色对象
	private int type;// type，记录创建角色的类型(0非默认角色，1指定账号类型默认角色,2指定机构默认角色)
	private String rightNodeValues;// 角色分配权限时，用于记录权限节点值
	private String keyword1, keyword2, keyword3;// 用于高级检索
	private int keyword4;// 检索，角色默认分配账号类型
	private String[] newNode = null;// 权限节点数组化，可考虑由struts2的自定义类型转换功能实现
	private String defaultAgencyIds;// 机构默认角色的机构
	private List<Role> roles;//当前登陆者拥有的角色列表
	private String mainRoleId;
	private int saveOrSubmit;//1：进入修改页面；2：保存
	private String ministryId, ministryName, provinceId, provinceName, universityId, universityName;// 修改指定机构角色时，为了在返回编辑页面时，保证此信息不丢失
	
	public String pageName(){
		return PAGE_NAME;
	}
	public String[] column(){
		return COLUMN;
	}
	public String HQL() {
		return HQL;
	}
	public String dateFormat() {
		return RoleAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return RoleAction.PAGE_BUFFER_ID;
	}

	/**
	 * 进入添加
	 */
	public String toAdd() {
		// 清除session中可能存在的权限树节点缓存 f31c4a822fd8c4a2012ffbebe8e509a0  4028d8843b0876f6013b087cb4030010
		ActionContext.getContext().getSession().remove(TMP_NODE_VALUE);
		Account account = loginer.getAccount();
		if (!loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {
			Map parMap = new HashMap();
			parMap.put("accountId", account.getId());
			roles = dao.query("from Role r, AccountRole ar where ar.role.id = r.id and ar.account.id = :accountId and r.isPrincipal = 1 ", parMap);
		} 
		return SUCCESS;
	}

	/**
	 * 添加角色
	 */
	@Transactional
	public String add() {
		if (roleService.checkRoleName(role.getName())) {// 角色名称存在，则阻止添加
			this.addFieldError(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_NAME_EXIST);
			return INPUT;
		} else {// 角色名称不存在，则允许添加
			// 记录角色创建者
			role.setAccount(loginer.getAccount());
			if (!loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {//非系统管理员创建的角色需要进行校验
				Map parMap = new HashMap();
				parMap.put("accountId", loginer.getAccount().getId());
				parMap.put("mainRoleId", mainRoleId);
				List<String> roleIds = dao.query("select r.id from Role r, AccountRole ar where ar.role.id = r.id and ar.account.id = :accountId and r.isPrincipal = 1 ", parMap);
				List<String> nodes = dao.query("select distinct r.nodevalue from Right r, RoleRight rr where rr.right.id = r.id and rr.role.id = :mainRoleId order by r.nodevalue asc", parMap);
				for (String o : newNode) {
					if (!nodes.contains(o)) {
						this.addFieldError(GlobalInfo.ERROR_INFO, "所选择的权限超出了主角的权限！");
						return INPUT;
					}
				}
				if (!roleIds.contains(mainRoleId)) {
					this.addFieldError(GlobalInfo.ERROR_INFO, "所选的主角色不正确！");
					return INPUT;
				} else {
					role.setIsPrincipal(0);
					role.setParentId(mainRoleId);
					entityId = roleService.addRole(role, newNode,defaultAgencyIds);
				}
			} else {
				// 添加角色
				role.setIsPrincipal(1);
				entityId = roleService.addRole(role, newNode, defaultAgencyIds);
				if(type == 2){// 添加机构账号的角色机构信息
					String[] defaultAgencyIdsList = defaultAgencyIds.split("; ");
					for(String defaultAgencyId:defaultAgencyIdsList){
						Agency defaultAgency = (Agency) dao.query(Agency.class, defaultAgencyId);
						RoleAgency roleAgency = new RoleAgency();
						roleAgency.setRole(role);
						roleAgency.setDefaultAgency(defaultAgency);
						dao.add(roleAgency);
					}
				}
				
			}
			if (saveOrSubmit == 1) {//保存
				return "save";
			} else {
				return SUCCESS;
			}
		}
	}

	/**
	 * 添加校验
	 */
	public void validateAdd() {
		this.updateValidate();
	}
	
	/**
	 * 进入修改
	 */
	@Transactional
	public String toModify() {
		role = (Role) dao.query(Role.class, entityId);// 获取角色
		if (role == null) {// 角色不存在，返回错误信息
			this.addFieldError(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_ROLL_NULL);
			return INPUT;
		} else {// 角色存在，处理角色信息用于回显
			Map parMap = new HashMap();
			Account account = loginer.getAccount();
			if (!loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {
				parMap.put("accountId", account.getId());
				roles = dao.query("from Role r, AccountRole ar where ar.role.id = r.id and ar.account.id = :accountId and r.isPrincipal = 1 ", parMap);
			}
			parMap.put("roleId", role.getId());
			List<RoleAgency> roleAgencies = dao.query("select ra from RoleAgency ra where ra.role.id =:roleId", parMap);
			// 获取角色类别信息
			if (roleAgencies.size() != 0) {// 有角色机构信息，则为机构默认角色
				type = 2;
			} else {// 机构ID为空，则判断账号类型
				if (role.getDefaultAccountType().equals(RoleInfo.ERROR_UNDEFAULT_ROLL)) {// 全0则为非默认角色
					type = 0;
				} else {// 非全0，则为账号默认角色
					type = 1;
				}
			}
			
			if (type == 2) {// 如果是机构角色，查找机构名称，并将机构ID和名称存入成员变量中，以便页面显示
				String agencyIds = "";
				for(RoleAgency roleAgency:roleAgencies){
					agencyIds += roleAgency.getDefaultAgency().getId() + "; ";
				}
				String[][] defaultAgencyIdAndName = roleService.getAgencyIdAndName(agencyIds);
				ministryId = defaultAgencyIdAndName[0][0];
				ministryName = defaultAgencyIdAndName[0][1];
				provinceId = defaultAgencyIdAndName[1][0];
				provinceName = defaultAgencyIdAndName[1][1];
				universityId = defaultAgencyIdAndName[2][0];
				universityName = defaultAgencyIdAndName[2][1];
			}
			
			// 将角色ID和角色拥有的权限节点存入session
			Map session = ActionContext.getContext().getSession();
			session.put(TMP_ENTITY_ID, entityId);
			List<String> rightNodeValue = roleService.getRightNodeValue(entityId);
			session.put(TMP_NODE_VALUE, rightNodeValue);
			return SUCCESS;
		}
	}
	
	/**
	 * 修改角色
	 */
	@Transactional
	public String modify() {
		String[] nodeValue = null;
		// 根据缓存的角色ID查找角色对象
		entityId = (String) session.get(TMP_ENTITY_ID);
		Role oldRole = (Role) dao.query(Role.class, entityId);
		Map parMap = new HashMap();
		if (oldRole == null) {// 角色不存在，则返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_ROLL_NULL);
			return INPUT;
		} else {// 角色存在，则修改角色信息
			if (!oldRole.getName().equals(role.getName())) {// 如果角色名称发生变化，则判断该名称是否可用
				if (roleService.checkRoleName(role.getName())) {// 角色名称存在，则返回错误提示
					this.addFieldError(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_NAME_EXIST);
					return INPUT;
				}
			}
			if (session.get("oldNodeValue") != null) {//进行过检索
				List node = (List) session.get("oldNodeValue");
				if (null != newNode) {
					for (int i = 0; i < newNode.length; i++) {
						if (!node.contains(newNode[i])) {
							node.add(newNode[i]);
						}
					}
				}
				nodeValue = (String[])node.toArray(new String[node.size()]);
			}
			if (!loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {//非系统管理员创建的角色需要进行校验
				parMap.put("accountId", loginer.getAccount().getId());
				parMap.put("mainRoleId", mainRoleId);
				List<String> roleIds = dao.query("select r.id from Role r, AccountRole ar where ar.role.id = r.id and ar.account.id = :accountId and r.isPrincipal = 1 ", parMap);
				List<String> nodes = dao.query("select distinct r.nodevalue from Right r, RoleRight rr where rr.right.id = r.id and rr.role.id = :mainRoleId order by r.nodevalue asc", parMap);
				for (String o : nodeValue) {
					if (!nodes.contains(o)) {
						this.addFieldError(GlobalInfo.ERROR_INFO, "所选择的权限超出了主角的权限！");
						return INPUT;
					}
				}
				if (!roleIds.contains(mainRoleId)) {
					this.addFieldError(GlobalInfo.ERROR_INFO, "所选的主角色不正确！");
					return INPUT;
				} else {
					oldRole.setParentId(mainRoleId);
					dao.modify(oldRole);
					// 规整角色所属机构ID，将角色所属机构ID排序
					entityId = roleService.modifyRole(oldRole, role, nodeValue, defaultAgencyIds);// 修改角色信息
				}
			} else {//系统管理员修改了主角色时要检测是否需要修改依赖这个主角色创建的子角色的权限
				parMap.put("oldRoleId", oldRole.getId());
				Boolean flag = false;
				Boolean flag1 = false;
				List<String> childRoleIds = dao.query(" select r.id from Role r where r.parentId = :oldRoleId ", parMap );//依赖当前角色新建的子角色
				//如果主角色权限变化影响子角色，那么需要删除之角色中被影响的权限
//				List<String> mianRoleRightIds = dao.query("select rr.id from RoleRight rr where rr.role.id = :oldRoleId", parMap);// 获得主角色对权限的引用关系
				for (String childRoleId : childRoleIds) {
					parMap.put("childRoleId", childRoleId);
					List<String> subRoleRightIds = dao.query("select rr.id from RoleRight rr where rr.role.id = :childRoleId", parMap);// 获得子角色对权限的引用关系
					for (String subRoleRightId : subRoleRightIds) {
						List<String> subNodevalues = dao.query("select r.nodevalue from RoleRight rr, Right r where rr.right.id = r.id and rr.id = ?", subRoleRightId);// 获得子角色拥有的权限
						List<String> newNodeList = Arrays.asList(nodeValue);//将数组转化为List之后就可以使用list的方法进行判断
						if (!newNodeList.contains(subNodevalues.get(0))) {
							dao.delete(RoleRight.class, subRoleRightId);
						}
					}
				}
				// 规整角色所属机构ID，将角色所属机构ID排序
				entityId = roleService.modifyRole(oldRole, role, nodeValue, defaultAgencyIds);// 修改角色信息
				
				// 找到原来的角色机构并删除，然后添加新的角色机构
				if(type == 2){
					// 删除
					parMap.put("oldRoleId", oldRole.getId());
					List<RoleAgency> oldRoleAgencies = dao.query("select ra from RoleAgency ra where ra.role.id =:oldRoleId", parMap);
					for(RoleAgency oldRoleAgency:oldRoleAgencies){
						dao.delete(oldRoleAgency);
					}
					// 添加
					String[] defaultAgencyIdsList = defaultAgencyIds.split("; ");
					for(String defaultAgencyId:defaultAgencyIdsList){
						Role role = (Role) dao.query(Role.class, entityId);
						Agency defaultAgency = (Agency) dao.query(Agency.class, defaultAgencyId);
						RoleAgency roleAgency = new RoleAgency();
						roleAgency.setRole(role);
						roleAgency.setDefaultAgency(defaultAgency);
						dao.add(roleAgency);
					}
				}
			}
			
			// 删除SESSION中的备用信息
			session.remove(TMP_NODE_VALUE);
			session.remove(TMP_ENTITY_ID);
			if (saveOrSubmit == 1) {//保存
				return "save";
			} else {
				return SUCCESS;
			}
		}
	}

	/**
	 * 进入修改校验
	 */
	public void validateToModify() {
		if (entityId == null || entityId.isEmpty()) {// 角色ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_MODIFY_NULL);
		} else if (!loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {
			role = dao.query(Role.class, entityId);
			if (role.getIsPrincipal() == 1) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "您无权修改由系统管理员创建的角色！");
			} else {
				Map parMap = new HashMap();
				parMap.put("accountId", loginer.getAccount().getId());
				List<String> ownRoles = dao.query("select r.id from Role r, AccountRole ar where ar.role.id = r.id and ar.account.id = :accountId and r.isPrincipal = 1 or r.account.id = :accountId group by r.id", parMap);
				if (!ownRoles.contains(entityId)) {
					this.addFieldError(GlobalInfo.ERROR_INFO, "您无权修改此角色！");
				}
			}
		}
	}
	/**
	 * 进入复制
	 */
	@Transactional
	public String toCopy() {
		toModify();
		return SUCCESS;
	}

	/**
	 * 进入修改校验
	 */
	public void validateToCopy() {
		if (entityId == null || entityId.isEmpty()) {// 角色ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_MODIFY_NULL);
		} else if (!loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {
			role = dao.query(Role.class, entityId);
			if (role.getIsPrincipal() == 1) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "您无权复制由系统管理员创建的角色！");
			}
		}
	}

	/**
	 * 修改校验
	 */
	public void validateModify() {
		this.updateValidate();
	}

	/**
	 * 输入校验
	 */
	public void updateValidate() {
		if (role.getName() == null || role.getName().isEmpty()) {// 角色名称不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_NAME_NULL);
		} else if (role.getName().length() > 200) {// 角色名称不得超过200字符
			this.addFieldError(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_NAME_ILLEGAL);
		}
		if (role.getDescription() == null || role.getDescription().isEmpty()) {// 角色描述不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_DESC_NULL);
		} else if (role.getDescription().length() > 800) {// 角色描述不得超过800字符
			this.addFieldError(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_DESC_ILLEGAL);
		}
		if (type == 1) {// 创建账号默认角色，defaultAccountType为非全0的01字符串，defaultAgencyId为空
			if (!Pattern.matches("[01]{15}", role.getDefaultAccountType())) {// 此字段需为15为01字符串
				this.addFieldError(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_DEFAULT_ACCOUNT_TYPE_ERROR);
			}
		} else if (type == 2) {// 创建机构默认角色，defaultAccountType为2位01字符串，defaultAgencyId不得为空
			if (defaultAgencyIds == null || defaultAgencyIds.isEmpty()) {
				this.addFieldError(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_DEFAULT_AGENCY_ID_ERROR);
			} else if (!Pattern.matches("[01]{2}", role.getDefaultAccountType())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_DEFAULT_APPLYTO_ERROR);
			}
		} else {// 非默认角色，defaultAccountType为全0字符串，defaultAgencyId为空
			role.setDefaultAccountType(RoleInfo.ERROR_UNDEFAULT_ROLL);
		}
		role.setCode(null);// 此字段无用
		if (rightNodeValues != null && !rightNodeValues.isEmpty()) {// 分配的权限非空，则将其封装为数组
			rightNodeValues = rightNodeValues.trim();
			newNode = rightNodeValues.split(",");
		}
	}

	/**
	 * 删除角色
	 */
	@Transactional
	public String delete() {
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {//系统管理员删除主角色，删除依赖主角色创建的子角色
			for (String entityId : entityIds){
				role = dao.query(Role.class, entityId);
				if (role.getIsPrincipal() == 1) {//删除主角色时，要删除依赖主角色创建的子角色
					List<String> childRoList = dao.query("select r.id from Role r where r.parentId = ?", entityId);//依赖主角色创建的所有子角色
					for (String id : childRoList ){
						dao.delete(Role.class, id);// 根据ID删除子角色，账号与角色的对应关系，角色与权限的对应关系会自动删除，程序不做处理
					}
				}
			}
		} else {//非系统管理员只能查看自己拥有的角色或者是自己创建的角色
			Map parMap = new HashMap();
			parMap.put("accountId", loginer.getAccount().getId());
			List<String> ownRoles = dao.query("select r.id from Role r where r.account.id = :accountId group by r.id", parMap);
			for (String entityId : entityIds) {
				if (!ownRoles.contains(entityId)) {
					jsonMap.put(GlobalInfo.ERROR_INFO, "你无权删除由系统管理员创建的角色！");
					return INPUT;
				}
			}
			
		}
		roleService.deleteRole(entityIds);// 删除角色
		return SUCCESS;
	}

	/**
	 * 删除校验
	 */
	public void validateDelete() {
		if (entityIds == null || entityIds.isEmpty()) {// 角色ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_DELETE_NULL);
		} else {
			Map parMap = new HashMap();
			parMap.put("accountId", loginer.getAccount().getId());
			if (!loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {//非系统管理员只能查看自己拥有的角色或者是自己创建的角色
				List<String> ownRoles = dao.query("select r.id from Role r where r.account.id = :accountId group by r.id", parMap);
				for (String entityId : entityIds) {
					if (!ownRoles.contains(entityId)) {
						jsonMap.put(GlobalInfo.ERROR_INFO, "你无权删除由系统管理员创建的角色！");
					}
				}
			}
		}
	}
	
	/**
	 * 进入查看
	 */
	public String toView() {
		return SUCCESS;
	}
	
	/**
	 * 进入查看校验
	 */
	public void validateToView() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_VIEW_NULL);
		} else {
			Map parMap = new HashMap();
			parMap.put("accountId", loginer.getAccount().getId());
			if (!loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {//非系统管理员只能查看自己拥有的角色或者是自己创建的角色
				List<String> ownRoles = dao.query("select r.id from Role r, AccountRole ar where ar.role.id = r.id and ar.account.id = :accountId and r.isPrincipal = 1 or r.account.id = :accountId group by r.id", parMap);
				if (!ownRoles.contains(entityId)) {
					this.addFieldError(GlobalInfo.ERROR_INFO, "你无权查看此角色！");
				}
			}
		}
	}
	/**
	 * 查看详情
	 */
	public String view() {
		role = (Role) roleService.viewRole(entityId);// 查询角色
		if (role == null) {// 角色不存在，返回错误信息
			jsonMap.put(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_ROLL_NULL);
			return INPUT;
		} else {// 角色存在，存入jsonMap，并更新页面对象
			Map parMap = new HashMap();
			parMap.put("roleId", role.getId());
			List<RoleAgency> roleAgencies = dao.query("select ra from RoleAgency ra where ra.role.id =:roleId", parMap);
			// 获取角色类别信息
			if (roleAgencies.size() != 0) {// 有角色机构信息，则为机构默认角色
				type = 2;
			} else {// 机构ID为空，则判断账号类型
				if (role.getDefaultAccountType().equals(RoleInfo.ERROR_UNDEFAULT_ROLL)) {// 全0则为非默认角色
					type = 0;
				} else {// 非全0，则为账号默认角色
					type = 1;
				}
			}
			
			// 读取机构角色的机构名称信息，并存入jsonMap中
			String agencyIds = "";
			for(RoleAgency roleAgency:roleAgencies){
				agencyIds += roleAgency.getDefaultAgency().getId() + "; ";
			}
			if(roleAgencies.size() != 0){
				agencyIds = agencyIds.substring(0, agencyIds.length() - 2);
			}
			String[][] defaultAgencyIdAndName = roleService.getAgencyIdAndName(agencyIds);
			jsonMap.put("ministryId", defaultAgencyIdAndName[0][0]);
			jsonMap.put("ministryName", defaultAgencyIdAndName[0][1]);
			jsonMap.put("provinceId", defaultAgencyIdAndName[1][0]);
			jsonMap.put("provinceName", defaultAgencyIdAndName[1][1]);
			jsonMap.put("universityId", defaultAgencyIdAndName[2][0]);
			jsonMap.put("universityName", defaultAgencyIdAndName[2][1]);
			
			// 角色、角色类型、角色创建者存入jsonMap
			jsonMap.put("role", role);
			jsonMap.put("belongid", role.getAccount().getId());
			Account account = (Account) dao.query(Account.class, role.getAccount().getId());
			Passport passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
			jsonMap.put("belongname", passport.getName());
			jsonMap.put("type", type);
			return SUCCESS;
		}
	}

	/**
	 * 查看校验
	 */
	public String validateView() {
		if (entityId == null || entityId.isEmpty()) {// 角色ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_VIEW_NULL);
			return INPUT;
		} else {
			return null;
		}
	}

	/**
	 * 处理初级检索条件，拼装查询语句。
	 */
	public Object[] simpleSearchCondition() {
		if (keyword == null) {// 预处理关键字
			keyword = "";
		} else {
			keyword = keyword.toLowerCase();
		}
		if (keyword4 < -1 || keyword4 > 15) {// 角色类型值不合法，则置为所有角色
			keyword4 = 0;
		}
		
		// 拼接检索条件
		StringBuffer hql = new StringBuffer(HQL());
		Map map = new HashMap();
		if (!loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {//非系统管理员只能看到自己创建的角色和自己拥有的角色
			String accountId = loginer.getAccount().getId();
			hql.append(" and  ( r.account.id = :accountId or ar.account.id = :accountId and ar.role.id = r.id )");
			map.put("accountId", accountId);
		}
		
		if (keyword4 == -1) {// 检索非默认角色或者机构默认角色，添加非默认角色或机构默认角色约束条件
			hql.append(" and (r.defaultAccountType = :undefaultRole or ra is not null) ");
			map.put("undefaultRole", RoleInfo.ERROR_UNDEFAULT_ROLL);
		} else if (keyword4 == 0) {// 检索所有角色，无需额外条件
		} else {// 检索指定账号类型的角色，添加账号类型约束条件
			hql.append(" and substring(r.defaultAccountType, :keyword4, 1) = '1' and ra is null ");
			map.put("keyword4", keyword4);
		}
		hql.append(" and ");
		if (searchType == 1) {// 按角色名称检索
			hql.append(" LOWER(r.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {// 按角色描述检索
			hql.append(" LOWER(r.description) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 3) {// 按创建者检索
			hql.append(" LOWER(pp.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {// 按上述字段检索
			hql.append(" (LOWER(r.name) like :keyword or LOWER(r.description) like :keyword or LOWER(pp.name) like :keyword) ");
			map.put("keyword", "%" + keyword + "%");
		}
		hql.append(GROUP_BY);
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}

	/**
	 * 处理高级检索条件，拼装查询语句。
	 */
	public Object[] advSearchCondition(){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL());
		// 拼接检索条件，当检索关键字非空时，才添加检索条件，忽略大小写
		if (keyword1 != null && !keyword1.isEmpty()) {// 按角色名称检索
			keyword1 = keyword1.toLowerCase();
			hql.append(" and LOWER(r.name) like :keyword1");
			map.put("keyword1", "%" + keyword1 + "%");
		}
		if (keyword2 != null && !keyword2.isEmpty()) {// 按角色描述检索
			keyword2 = keyword2.toLowerCase();
			hql.append(" and LOWER(r.description) like :keyword2");
			map.put("keyword2", "%" + keyword2 + "%");
		}
		if (keyword3 != null && !keyword3.isEmpty()) {// 按创建者检索
			keyword3 = keyword3.toLowerCase();
			hql.append(" and LOWER(pp.name) like :keyword3");
			map.put("keyword3", "%" + keyword3 + "%");
		}
		if (keyword4 == -1) {// 检索非默认角色或者机构默认角色，添加非默认角色或机构默认角色约束条件
			hql.append(" and (r.defaultAccountType = :undefaultRole or ra is not null) ");
			map.put("undefaultRole", RoleInfo.ERROR_UNDEFAULT_ROLL);
		} else if (keyword4 == 0) {// 检索所有角色，无需额外条件
		} else {// 检索指定账号类型的角色，添加账号类型约束条件
			hql.append(" and substring(r.defaultAccountType, :keyword4, 1) = '1' and ra is null ");
			map.put("keyword4", keyword4);
		}
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
	}
	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author yangfq
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAdvSearchQuery(Map searchQuery) {
		if(null != keyword1 && !keyword1.isEmpty()){
			searchQuery.put("keyword1", keyword1);
		}
		if(null != keyword2 && !keyword2.isEmpty()){
			searchQuery.put("keyword2", keyword2);
		}
		if(null != keyword3 && !keyword3.isEmpty()){
			searchQuery.put("keyword3", keyword3);
		}
		if(keyword4 > -1 && keyword4 < 15){
			searchQuery.put("keyword4", keyword4);
		}
	}
	
	/**
	 * 高级检索校验
	 */
	public void validateAdvSearch() {
		if (keyword4 < -1 || keyword4 > 15) {// 角色类型值不合法，则置为所有角色
			keyword4 = 0;
		}
	}

	/**
	 * 创建权限树
	 * @throws UnsupportedEncodingException 
	 */
	public String createRightTree() throws UnsupportedEncodingException {
		List<String> str = (List<String>) ActionContext.getContext().getSession().get(TMP_NODE_VALUE);// 获取选中节点
		if (keyword != null) {
			keyword =  URLDecoder.decode(keyword,"utf-8");//解决中文乱码
			keyword = keyword.toLowerCase();
		} else {
			keyword = "";
		}
		Document doc = roleService.createRightXML(entityId, str, loginer, mainRoleId,keyword);
		String content = doc.asXML();
		HttpServletResponse response = ServletActionContext.getResponse();
		// 使用utf-8
		response.setContentType("text/xml;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(content); // 通过Io流写到页面上去了
		// 必须返回空
		return null;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getRightNodeValues() {
		return rightNodeValues;
	}
	public void setRightNodeValues(String rightNodeValues) {
		this.rightNodeValues = rightNodeValues;
	}
	public String getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}
	public String getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}
	public String getMainRoleId() {
		return mainRoleId;
	}
	public void setMainRoleId(String mainRoleId) {
		this.mainRoleId = mainRoleId;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getKeyword3() {
		return keyword3;
	}
	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
	}
	public int getKeyword4() {
		return keyword4;
	}
	public void setKeyword4(int keyword4) {
		this.keyword4 = keyword4;
	}
	public String getDefaultAgencyIds() {
		return defaultAgencyIds;
	}
	public void setDefaultAgencyIds(String defaultAgencyIds) {
		this.defaultAgencyIds = defaultAgencyIds;
	}
	public String getMinistryId() {
		return ministryId;
	}
	public void setMinistryId(String ministryId) {
		this.ministryId = ministryId;
	}
	public String[] getNewNode() {
		return newNode;
	}
	public void setNewNode(String[] newNode) {
		this.newNode = newNode;
	}
	public String getMinistryName() {
		return ministryName;
	}
	public void setMinistryName(String ministryName) {
		this.ministryName = ministryName;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public int getSaveOrSubmit() {
		return saveOrSubmit;
	}
	public void setSaveOrSubmit(int saveOrSubmit) {
		this.saveOrSubmit = saveOrSubmit;
	}
	public String getUniversityId() {
		return universityId;
	}
	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public static String getGroupBy() {
		return GROUP_BY;
	}

}
