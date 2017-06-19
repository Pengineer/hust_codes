// ========================================================================
// 文件名: RoleRightAction.java
//
// 文件说明:
//	 本文件主要实现权限管理的功能，包括权限的添加、权限列表、权限的查看及修改。
// 主要用到service层的接口有IRoleRightService。各个action与页面的对应关系查看
// struts-right.xml文件。
//
// 历史记录:
// 2009-11-28  龚凡		   创建文件，完成基本功能.
// 2010-03-09  龚凡		  整理代码与角色分离
// ========================================================================

package csdc.action.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import csdc.action.BaseAction;
import csdc.bean.Right;
import csdc.service.IRoleRightService;
import csdc.tool.info.GlobalInfo;

/**
 * 权限管理
 * @author 龚凡
 * @author fengcl
 * @version 1.0 2010.03.31
 * @version 1.1 2012.02.10
 */
@SuppressWarnings("unchecked")
public class RightAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select r.id, r.name, r.description from Right r";
	private static final String PAGENAME = "rightPage";
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "r.id";//缓存id
	private static final String column[] = {
			"r.name",
			"r.description"
	};// 排序用的列
	private IRoleRightService rolerightservice;// 角色与权限管理模块接口
	private Right right;// 权限信息，修改后的权限信息
	private List<String> urlsid, right_actionids;// 返回的已分配权限ID等
	private String[] actionUrlArray, actionDesArray;//action批量修改时的字符串数组

	
	/**
	 * 列表和初级检索条件
	 */
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
	
	/**
	 * 进入查看
	 * @return SUCCESS跳转查看页面
	 */
	public String toView(){
		return SUCCESS;
	}
	
	/**
	 * 校验
	 */
	public void validateToView() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择要查看的权限");
			jsonMap.put(GlobalInfo.ERROR_INFO, "请选择要查看的权限");
		}
	}
	
	/**
	 * 查看权限
	 * @return 跳转成功
	 */
	public String view(){
		// 获取角色基本信息
		right = (Right) rolerightservice.query(Right.class, entityId);
		if (right == null) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "不存在的权限");
			jsonMap.put(GlobalInfo.ERROR_INFO, "不存在的权限");
			return INPUT;
		}
		// 获取角色的权限信息
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("from RightUrl right_action where right_action.right.id = :rightId order by right_action.actionurl asc");
		map.put("rightId", entityId);
		pageList = rolerightservice.query(hql.toString(), map);
		jsonMap.put("right", right);
		jsonMap.put("pageList", pageList);
		return SUCCESS;
	}
	
	/**
	 * 进入添加权限页面
	 * @return 跳转成功
	 */
	public String toAdd() {
		return SUCCESS;
	}

	/**
	 * 添加权限
	 * @return 跳转成功
	 */
	public String add() {
		// 检查权限名称是否存在
		if (rolerightservice.checkRightName(right.getName())) {
			request.setAttribute("errorInfo", "该权限名已存在");
			return INPUT;
		}
		entityId = rolerightservice.addRight(right, actionUrlArray, actionDesArray);
		List<Object> rightUrl = baseService.query("select right0.name, right_action.actionurl from Right right0, RightUrl right_action where right0.id = right_action.right.id order by right_action.actionurl asc");
		request.getSession().getServletContext().setAttribute("rightUrl", rightUrl);
		return SUCCESS;
	}

	/**
	 * 添加权限校验
	 */
	public void validateAdd() {
		if (right.getName() == null || right.getName().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "权限名称不得为空");
		}
		if (right.getDescription() == null || right.getDescription().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "权限描述不得为空");
		}
		if (actionUrlArray == null || actionUrlArray.length == 0) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "权限对应的url不得为空");
		} else if (actionDesArray == null || actionDesArray.length == 0) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "url对应的描述不得为空");
		} else if (actionUrlArray.length != actionDesArray.length) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "url与其对应的描述数量不相等");
		}
	}

	/**
	 * 删除权限
	 * @return 跳转成功
	 */
	public String delete() {
		try {
			rolerightservice.deleteRight(entityIds);
			return SUCCESS;
		} catch (Exception e) {
			jsonMap.put(GlobalInfo.ERROR_INFO, "有权限不存在");
			return INPUT;
		}
	}

	/**
	 * 删除校验
	 */
	public void validateDelete() {
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择权限");
			jsonMap.put(GlobalInfo.ERROR_INFO, "请选择权限");
		}
	}

	/**
	 * 进入修改权限
	 * @return 跳转成功
	 */
	public String toModify() {
		session.put("rightId", entityId);
		//获取权限基本信息
		right = (Right) rolerightservice.query(Right.class, entityId);
		if (right == null) {
			request.setAttribute("errorInfo", "不存在的权限");
			return INPUT;
		}
		//获取权限action信息
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("from RightUrl right_action where right_action.right.id = :rightId order by right_action.actionurl");
		map.put("rightId", entityId);
		pageList = rolerightservice.query(hql.toString(), map);
		return SUCCESS;
	}

	/**
	 * 进入权限修改校验
	 */
	public void validateToModify() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择要修改的权限");
		}
	}

	/**
	 * 修改权限
	 * @return 跳转成功
	 */
	public String modify() {
		entityId = (String) session.get("rightId");
		// 修改基本信息
		Right mright = (Right) rolerightservice.query(Right.class, entityId);
		if (mright == null) {
			request.setAttribute("tip", "不存在的权限");
			return INPUT;
		}
		if (!mright.getName().equals(right.getName())) {
			// 检查权限名称是否存在
			if (rolerightservice.checkRightName(right.getName())) {
				request.setAttribute("tip", "该权限名已存在");
				return INPUT;
			}
		}
		mright.setName(right.getName());
		mright.setDescription(right.getDescription());
		rolerightservice.modifyRight(mright, actionUrlArray, actionDesArray);
		List<Object> rightUrl = baseService.query("select right0.name, right_action.actionurl from Right right0, RightUrl right_action where right0.id = right_action.right.id order by right_action.actionurl asc");
		request.getSession().getServletContext().setAttribute("rightUrl", rightUrl);
		return SUCCESS;
	}

	/**
	 * 修改权限校验
	 */
	public void validateModifyRight() {
		if (right.getName() == null || right.getName().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "权限名称不得为空");
		}
		if (right.getDescription() == null || right.getDescription().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "权限描述不得为空");
		}
		if (actionUrlArray == null || actionUrlArray.length == 0) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "权限对应的url不得为空");
		} else if (actionDesArray == null || actionDesArray.length == 0) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "url对应的描述不得为空");
		} else if (actionUrlArray.length != actionDesArray.length) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "url与其对应的描述数量不相等");
		}
	}

	public Right getRight() {
		return right;
	}
	public void setRight(Right right) {
		this.right = right;
	}
	public List<String> getUrlsid() {
		return urlsid;
	}
	public void setUrlsid(List<String> urlsid) {
		this.urlsid = urlsid;
	}
	public List<String> getRight_actionids() {
		return right_actionids;
	}
	public void setRight_actionids(List<String> right_actionids) {
		this.right_actionids = right_actionids;
	}
	public String[] getActionUrlArray() {
		return actionUrlArray;
	}
	public void setActionUrlArray(String[] actionUrlArray) {
		this.actionUrlArray = actionUrlArray;
	}
	public String[] getActionDesArray() {
		return actionDesArray;
	}
	public void setActionDesArray(String[] actionDesArray) {
		this.actionDesArray = actionDesArray;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public void setRolerightservice(IRoleRightService rolerightservice) {
		this.rolerightservice = rolerightservice;
	}

	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] column() {
		return column;
	}
	@Override
	public String pageName() {
		return PAGENAME;
	}
	@Override
	public String dateFormat() {
		return DATE_FORMAT;
	}
	@Override
	public String pageBufferId() {
		return PAGE_BUFFER_ID;
	}
}