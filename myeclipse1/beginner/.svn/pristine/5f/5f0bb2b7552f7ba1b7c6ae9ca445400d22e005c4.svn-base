// ========================================================================
// 文件名: RoleRightAction.java
//
// 文件说明:
//     本文件主要实现权限管理的功能，包括权限的添加、权限列表、权限的查看及修改。
// 主要用到service层的接口有IRoleRightService。各个action与页面的对应关系查看
// struts-right.xml文件。
//
// 历史记录:
// 2009-11-28  龚凡           创建文件，完成基本功能.
// 2010-03-09  龚凡          整理代码与角色分离
// ========================================================================

package csdc.action;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Right;
import csdc.service.IRoleRightService;
import csdc.tool.Pager;

/**
 * 权限管理
 * @author 龚凡
 * @version 1.0 2010.03.31
 *
 */
public class RightAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select right0.id, right0.name, right0.description from Right right0";
	private static final String column[] = {
			"right0.name",
			"right0.description"
	};// 排序用的列
	private IRoleRightService rolerightservice;// 角色与权限管理模块接口
	private Right right;// 权限信息，修改后的权限信息
	private List<String> urlsid, right_actionids, rightids;// 返回的已分配权限ID等
	private String rightid;
	private String[] actionurlarray, actiondesarray;//action批量修改时的字符串数组

	/**
	 * 权限列表
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String listRight() {
		ActionContext context = ActionContext.getContext();
		Map application = context.getApplication();
		Map session = context.getSession();
		String DisplayNumberEachPage = (String) application.get("DisplayNumberEachPage");
		Pager page = (Pager) session.get("rightPage");
		String hql = HQL;
		if (page == null) {
			hql += " order by right0.name asc, right0.id asc";
			page = new Pager(rolerightservice.count(hql), Integer.parseInt(DisplayNumberEachPage), hql);
		} else {
			// 判断是否由左侧菜单进入
			if (listLabel == 1) {
				hql += page.getHql().substring(page.getHql().indexOf(" order by "));
				page = new Pager(page.getTotalRows(), Integer.parseInt(DisplayNumberEachPage), hql);
			}
			if (pageNumber != 0) {
				page.setCurrentPage(pageNumber);
			}
		}
		pageList = rolerightservice.list(page.getHql(), page.getStartRow(), page.getPageSize());
		session.put("rightPage", page);
		this.initPageBuffer("rightPage", null);
		return SUCCESS;
	}

	/**
	 * 权限初级检索
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String simpleSearch() {
		ActionContext context = ActionContext.getContext();
		Map application = context.getApplication();
		Map session = context.getSession();
		String DisplayNumberEachPage = (String) application.get("DisplayNumberEachPage");
		keyword = keyword.toLowerCase();
		String hql = HQL;
		if (keyword != null && !keyword.isEmpty()) {
			hql += " where ";
			if (search_type == 1) {
				hql += "LOWER(right0.name) like '%" + keyword + "%'";
			} else if (search_type == 2) {
				hql += "LOWER(right0.description) like '%" + keyword + "%'";
			} else {
				hql += "(LOWER(right0.name) like '%" + keyword + "%' or LOWER(right0.description) like '%" + keyword + "%')";
			}
		}
		System.out.println(hql);
		Pager page = (Pager) session.get("rightPage");
		if (page != null) {
			hql += page.getHql().substring(page.getHql().indexOf(" order by "));
		} else {
			hql += " order by right0.name asc, right0.id asc";
		}
		page = new Pager(rolerightservice.count(hql), Integer.parseInt(DisplayNumberEachPage), hql);
		session.put("rightPage", page);
		pageList = rolerightservice.list(page.getHql(), page.getStartRow(), page.getPageSize());
		this.initPageBuffer("rightPage", null);
		return SUCCESS;
	}

	/**
	 * 排序
	 * @return 列表页面
	 */
	public String sortRight() {
		if(ActionContext.getContext().getSession().get("rightPage") == null)
			listRight();
		this.sort("rightPage", column[columnLabel]);
		this.initPageBuffer("rightPage", null);
		return SUCCESS;
	}

	/**
	 * 不在有效范围，统一设为0
	 */
	public void validateSortRight() {
		if (columnLabel < 0 || columnLabel > column.length - 1) {
			columnLabel  = 0;
		}
	}

	/**
	 * 上一条
	 */
	public String prevRight() {
		if(ActionContext.getContext().getSession().get("rightPage") == null)
			listRight();
		rightid = this.prevRecord("rightPage", rightid);
		if (rightid == null || rightid.isEmpty()) {
			request.setAttribute("tip", "无效的权限");
		}
		return SUCCESS;
	}

	/**
	 * 上一条校验
	 */
	public void validatePrevRight() {
		if (rightid == null || rightid.isEmpty()) {
			this.addFieldError("knowError", "请选择权限");
		}
	}

	/**
	 * 下一条
	 */
	public String nextRight() {
		if(ActionContext.getContext().getSession().get("rightPage") == null)
			listRight();
		rightid = this.nextRecord("rightPage", rightid);
		if (rightid == null || rightid.isEmpty()) {
			request.setAttribute("tip", "无效的权限");
		}
		return SUCCESS;
	}

	/**
	 * 下一条校验
	 */
	public void validateNextRight() {
		if (rightid == null || rightid.isEmpty()) {
			this.addFieldError("knowError", "请选择权限");
		}
	}

	/**
	 * 进入添加权限页面
	 * @return 跳转成功
	 */
	public String toAddRight() {
		return SUCCESS;
	}

	/**
	 * 添加权限
	 * @return 跳转成功
	 */
	public String addRight() {
		// 检查权限名称是否存在
		if (rolerightservice.checkRightname(right.getName())) {
			request.setAttribute("tip", "该权限名已存在");
			return INPUT;
		}
		rightid = rolerightservice.addRight(right, actionurlarray, actiondesarray);
		this.initPageBuffer("rightPage", null);
		return SUCCESS;
	}

	/**
	 * 添加权限校验
	 */
	public void validateAddRight() {
		if (right.getName() == null || right.getName().isEmpty()) {
			this.addFieldError("knowError", "权限名称不得为空");
		}
		if (right.getDescription() == null || right.getDescription().isEmpty()) {
			this.addFieldError("knowError", "权限描述不得为空");
		}
		if (actionurlarray == null || actionurlarray.length == 0) {
			this.addFieldError("knowError", "权限对应的url不得为空");
		} else if (actiondesarray == null || actiondesarray.length == 0) {
			this.addFieldError("knowError", "url对应的描述不得为空");
		} else if (actionurlarray.length != actiondesarray.length) {
			this.addFieldError("knowError", "url与其对应的描述数量不相等");
		}
	}

	/**
	 * 删除权限
	 * @return 跳转成功
	 */
	public String deleteRight() {
		try {
			rolerightservice.delete(Right.class, rightid);
			this.refreshPager("rightPage");
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("tip", "该权限不存在");
			return INPUT;
		}
	}

	/**
	 * 删除校验
	 */
	public void validateDeleteRight() {
		if (rightid == null || rightid.isEmpty()) {
			this.addFieldError("knowError", "请选择权限");
		}
	}

	/**
	 * 群删权限
	 * @return 跳转成功
	 */
	public String groupDeleteRights() {
		try {
			rolerightservice.deleteRight(rightids);
			this.refreshPager("rightPage");
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("tip", "有权限不存在");
			return INPUT;
		}
	}

	/**
	 * 删除校验
	 */
	public void validateGroupDeleteRole() {
		if (rightids == null || rightids.isEmpty()) {
			this.addFieldError("knowError", "请选择权限");
		}
	}


	/**
	 * 查看权限
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String viewRight() {
		right = (Right) rolerightservice.query(Right.class, rightid);
		if (right == null) {
			request.setAttribute("tip", "不存在的权限");
			return INPUT;
		}
		pageList = rolerightservice.query("from RightUrl right_action where right_action.right.id = '"
				+ right.getId() + "' order by right_action.actionurl asc");
		return SUCCESS;

	}

	/**
	 * 查看校验
	 */
	public void validateViewRight() {
		if (rightid == null || rightid.isEmpty()) {
			this.addFieldError("knowError", "请选择权限");
		}
	}

	/**
	 * 进入修改权限
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String loadRight() {
		//获取权限基本信息
		right = (Right) rolerightservice.query(Right.class, rightid);
		if (right == null) {
			request.setAttribute("tip", "不存在的权限");
			return INPUT;
		}
		//获取权限action信息
		pageList = rolerightservice
		.query("from RightUrl right_action where right_action.right.id = '"
				+ right.getId() + "' order by right_action.actionurl");
		return SUCCESS;
	}

	/**
	 * 进入权限修改校验
	 */
	public void validateLoadRight() {
		if (rightid == null || rightid.isEmpty()) {
			this.addFieldError("knowError", "请选择权限");
		}
	}

	/**
	 * 修改权限
	 * @return 跳转成功
	 */
	public String modifyRight() {
		Right mright = new Right();
		// 修改基本信息
		mright = (Right) rolerightservice.query(Right.class, right.getId());
		if (mright == null) {
			request.setAttribute("tip", "不存在的权限");
			return INPUT;
		}
		//System.out.println(mright.getName());
		//System.out.println(right.getName());
		if (!mright.getName().equals(right.getName())) {
			// 检查权限名称是否存在
			if (rolerightservice.checkRightname(right.getName())) {
				request.setAttribute("tip", "该权限名已存在");
				return INPUT;
			}
		}
		mright.setName(right.getName());
		mright.setDescription(right.getDescription());
		//System.out.println(mright.getName());
		rolerightservice.modifyRight(mright, actionurlarray, actiondesarray);
		return SUCCESS;
	}

	/**
	 * 修改权限校验
	 */
	public void validateModifyRight() {
		if (right.getName() == null || right.getName().isEmpty()) {
			this.addFieldError("knowError", "权限名称不得为空");
		}
		if (right.getDescription() == null || right.getDescription().isEmpty()) {
			this.addFieldError("knowError", "权限描述不得为空");
		}
		if (actionurlarray == null || actionurlarray.length == 0) {
			this.addFieldError("knowError", "权限对应的url不得为空");
		} else if (actiondesarray == null || actiondesarray.length == 0) {
			this.addFieldError("knowError", "url对应的描述不得为空");
		} else if (actionurlarray.length != actiondesarray.length) {
			this.addFieldError("knowError", "url与其对应的描述数量不相等");
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
	public List<String> getRightids() {
		return rightids;
	}
	public void setRightids(List<String> rightids) {
		this.rightids = rightids;
	}
	public String getRightid() {
		return rightid;
	}
	public void setRightid(String rightid) {
		this.rightid = rightid;
	}
	public String[] getActionurlarray() {
		return actionurlarray;
	}
	public void setActionurlarray(String[] actionurlarray) {
		this.actionurlarray = actionurlarray;
	}
	public String[] getActiondesarray() {
		return actiondesarray;
	}
	public void setActiondesarray(String[] actiondesarray) {
		this.actiondesarray = actiondesarray;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public void setRolerightservice(IRoleRightService rolerightservice) {
		this.rolerightservice = rolerightservice;
	}
}