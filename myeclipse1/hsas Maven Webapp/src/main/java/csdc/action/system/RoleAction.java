package csdc.action.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import csdc.action.BaseAction;
import csdc.model.Role;
import csdc.service.IRoleService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.RoleInfo;


@Component
@Scope(value="prototype")

public class RoleAction extends BaseAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3948153379379236362L;
	private static final String GROUP_BY = "group by r.id, r.name, r.description, a.id, pp.name";
	private static final String HQL = "select r.id, r.name, r.description from Role r";
	
	private static final String[] SORT_COLUMNS = {
			"r.name",
			"r.description, r.name",

	};// 用于拼接的排序列
	private static final String PAGE_NAME = "rolePage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "r.id";// 上下条查看时用于查找缓存的字段
	private static final String TMP_ENTITY_ID = "roleId";// 用于session缓存实体的ID名称
	private static final String TMP_NODE_VALUE = "nodeValue";// 创建权限树时，缓存的已选项
	
	private int type;// type，记录创建角色的类型(0非默认角色，1指定账号类型默认角色,2指定机构默认角色)
	private int keyword4;// 检索，角色默认分配账号类型
	private int saveOrSubmit;//1：进入修改页面；2：保存
	private String rightNodeValues;// 角色分配权限时，用于记录权限节点值
	private String keyword1, keyword2, keyword3;// 用于高级检索
	private String[] newNode = null;// 权限节点数组化，可考虑由struts2的自定义类型转换功能实现
	private String mainRoleId;
	
	private List<Role> roles;//当前登陆者拥有的角色列表
	private Role role;// 角色对象
	
	@Autowired
	private IRoleService roleService;// 角色管理接口
	
	
	
	
	

	@Override
	public String toAdd() {
		
		return SUCCESS;
	}

	@Override
	public String add() {
		
		if (rightNodeValues != null && !rightNodeValues.isEmpty()) {// 分配的权限非空，则将其封装为数组
			rightNodeValues = rightNodeValues.trim();
			newNode = rightNodeValues.split(",");
		}
		// 检测角色名是否存在
		if (roleService.checkRoleName(role.getName())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "所选择的角色名已存在！");
			return INPUT;
		}

		// 添加角色
		entityId = roleService.addRole(role, newNode);
		jsonMap.put("tag", 1);
		return SUCCESS;
		
	}

	@Override
	public String delete() {
		try {
			roleService.deleteRole(entityIds);
			return SUCCESS;
		} catch (Exception e) {
			jsonMap.put(GlobalInfo.ERROR_INFO, "有角色不存在");
			return INPUT;
		}	
	}

	@Override
	public String toModify() {
		// TODO Auto-generated method stub
		return SUCCESS;
	}

	@Override
	public String modify() {
		// TODO Auto-generated method stub
		return SUCCESS;
	}

	@Override
	public String view() {
		jsonMap.clear();
		role = roleService.viewRole(entityId);
		if(role == null){
			jsonMap.put(GlobalInfo.ERROR_INFO, RoleInfo.ERROR_ROLL_NULL);
			return INPUT;
		} else {
			
			jsonMap.put("role", role);
			return SUCCESS;
		}
		
	}

	@Override
	public String toView() {
		
		return SUCCESS;
	}

	@Override
	public String toList() {
		
		return defaultToList();
	}

	@Override
	public String list() {
		
		
		return defaultList();
	}

	@Override
	public Object[] simpleSearchCondition() {
		
		keyword = (keyword == null) ? "" : keyword.toLowerCase().trim();// 预处理关键字
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		if (keyword != null && !keyword.isEmpty()) {
			hql.append(" where ");
			if (searchType == 1) {
				hql.append("LOWER(r.name) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {
				hql.append("LOWER(r.description) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else {
				hql.append("(LOWER(r.name) like :keyword or LOWER(r.description) like :keyword)");
				map.put("keyword", "%" + keyword + "%");
			}
		}
		// 调用初级检索功能
		return new Object[]{
			hql.toString(),
			map,
			0,
			null,
			null
		};
	}

	@Override
	public String simpleSearch() {
		
		return defaultSimpleSearch();
	}

	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advSearch() {
		
		return defaultAdvSearch();
	}

	@Override
	public String changePageSize() {
		
		return defaultChangePageSize();
	}

	@Override
	public String toPage() {
		
		return defaultToPage();
	}

	@Override
	public String toEntity() {
		
		return defaultToEntity();
	}

	@Override
	public String sort() {
		
		return defaultSort();
	}

	@Override
	public String pageName() {
		
		return this.PAGE_NAME;
	}

	@Override
	public String pageBufferId() {
		
		return this.PAGE_BUFFER_ID;
	}

	@Override
	public String[] sortColumn() {
		
		return this.SORT_COLUMNS;
	}

	@Override
	public String dateFormat() {
		
		return this.DATE_FORMAT;
	}

	
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getKeyword4() {
		return keyword4;
	}

	public void setKeyword4(int keyword4) {
		this.keyword4 = keyword4;
	}

	public int getSaveOrSubmit() {
		return saveOrSubmit;
	}

	public void setSaveOrSubmit(int saveOrSubmit) {
		this.saveOrSubmit = saveOrSubmit;
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

	public String getKeyword3() {
		return keyword3;
	}

	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
	}

	public String[] getNewNode() {
		return newNode;
	}

	public void setNewNode(String[] newNode) {
		this.newNode = newNode;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	

}
