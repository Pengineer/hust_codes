// ========================================================================
// 文件名: UserAction.java
//
// 文件说明:
//     本文件主要实现用户管理模块的功能，包括用户的登入、退出、注册、验证、审批、
// 禁用、删除、查看、修改、检索等。主要用到service层的接口有IUserService
// 、IRoleRightService。各个action与页面的对应关系查看struts-user.xml文件。
//
// 历史记录:
// 2009-11-28  龚凡                        创建文件，完成基本功能.
// ========================================================================

package csdc.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.io.ByteArrayInputStream;

import java.io.InputStream;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Role;
import csdc.bean.SystemConfig;
import csdc.bean.SystemOption;
import csdc.bean.User;
import csdc.bean.common.Mail;
import csdc.bean.common.Visitor;
import csdc.service.IRoleRightService;
import csdc.service.IUserService;
import csdc.tool.FileTool;
import csdc.tool.MD5;
import csdc.tool.Mailer;
import csdc.tool.Pager;
import csdc.tool.SignID;

/**
 * 用户管理
 * @author 龚凡
 * @version 1.0 2010.03.31
 */
public class UserAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select user.id, user.username, user.chinesename, user.registertime, user.approvetime, user.expiretime from User user where user.issuperuser = 0";
	private static final String column[] = {
			"user.username",
			"user.chinesename",
			"user.registertime",
			"user.approvetime",
			"user.expiretime"
	};// 排序用的列
	private IUserService userservice;// 用户管理模块接口
	private IRoleRightService rolerightservice;// 角色与权限管理模块接口
	private String username, password, rand, repassword;// 登入变量
	private User user;// 用户对象
	private int userstatus;// 用户状态标志
	private List<String> userids;// 记录群操作的账号ID
	private String userid;
	private File pic;// 上传的图片
	private String picFileName;// 上传文件的文件名
	private String picContentType;// 上传文件的类型
	private Map<String, String> roles; //用户列表显示角色信息
	private Date validity;// 账号有效期
	private List<String> rolesid;// 角色ID
	private InputStream inputStream;// 定义返回的输入流 
	private Boolean valid;// 定义返回值：只能为Boolean类型 
	private int selflabel;
	private SystemConfig sysconfig;

	/**
	 * 登入系统，登入后用户相关权限等信息放入session中备用
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String login() {
		Map session = ActionContext.getContext().getSession();
		// 检测验证码是否正确
		String arandom = (String) (session.get("random"));
		if ((arandom == null) || (!arandom.equals(rand))) {
			request.setAttribute("tip", "验证码错误");
			return INPUT;
		}
		// 检测用户名和密码是否匹配
		password = MD5.getMD5(password);//MD5加密
		if (!userservice.checkPassword(username, password)) {
			request.setAttribute("tip", "用户名或者密码错误");
			return INPUT;
		}
		user = (User) userservice.getByUsername(username);
		// 检测账号是否可用
		if (user.getUserstatus() == 0) {
			request.setAttribute("tip", "此账号尚未通过审批");
			return INPUT;
		}else if (user.getUserstatus() == -1) {
			request.setAttribute("tip", "此账号已停用");
			return INPUT;
		}else {
			// 判断账号是否过期
			Date currenttime = new Date();
			// 若果过期，则停用此账号
			if (currenttime.after(user.getExpiretime())){
				user.setUserstatus(-1);
				userservice.modify(user);
				request.setAttribute("tip", "此账号已停用");
				return INPUT;
			}
			Visitor visitor = new Visitor();
			visitor.setUser(user);
			visitor.setUserRight(rolerightservice.getUserRight(user.getId()));
			session.put("visitor", visitor);
			return SUCCESS;
		}
	}

	/**
	 * 登入校验
	 */
	public void validateLogin() {
		if (username == null || username.isEmpty()) {
			this.addFieldError("knowError", "用户名不得为空");
		}
		if (password == null || password.isEmpty()) {
			this.addFieldError("knowError", "密码不得为空");
		}
		if (rand == null || rand.isEmpty()) {
			this.addFieldError("knowError", "验证码不得为空");
		}
	}

	/**
	 * 退出系统，清除用户的visitor对象
	 * @return 跳转成功
	 */
	public String logout() {
		request.getSession().invalidate();
		return SUCCESS;
	}

	/**
	 * 进入注册页面
	 * @return 跳转成功
	 */
	public String toRegister() {
		return SUCCESS;
	}

	/**
	 * 注册检测用户名、图片上传等
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String register() throws Exception {
		// 检测用户名是否可用
		if (userservice.checkUsername(user.getUsername())) {
			request.setAttribute("tip", "此用户名已被注册");
			return INPUT;
		}
		// 检测是否有图片上传
		if (pic != null) {
			ActionContext context = ActionContext.getContext();
			Map application = context.getApplication();
			String savePath = (String) application.get("UserPictureUploadPath");
			FileTool.scaleImage2(pic);
			// 获取随机数
			String signID = SignID.getInstance().getSignID();
			// 获取图片名称
			String realName = FileTool.saveUpload(pic, picFileName, savePath, signID);
			// 图片保存路径
			user.setPhotofile(realName);
		}
		Date date = new Date();
		user.setRegistertime(date);
		user.setUserstatus(0);
		user.setApprovetime(null);
		user.setExpiretime(null);
		user.setIssuperuser(0);
		user.setPwRetrieveCode(null);
		user.setPassword(MD5.getMD5(user.getPassword()));
		userid = (String) userservice.add(user);
		return SUCCESS;
	}

	/**
	 * 注册校验
	 */
	public void validateRegister() {
		if (user.getUsername() == null || user.getUsername().isEmpty()) {
			this.addFieldError("knowError", "用户名不得为空");
		} else if (!Pattern.matches("\\w{4,20}", user.getUsername().trim())) {
			this.addFieldError("knowError", "用户名必须是字母和数字，长度为4到20之间");
		}
		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			this.addFieldError("knowError", "密码不得为空");
		} else if (user.getPassword().length() < 6 || user.getPassword().length() > 20) {
			this.addFieldError("knowError", "密码长度必须在6到20之间");
		} else if (repassword == null || repassword.isEmpty()) {
			this.addFieldError("knowError", "重复密码不得为空");
		} else if (!repassword.equals(user.getPassword())) {
			this.addFieldError("knowError", "两次输入的密码不一致");
		}
		if (user.getChinesename() == null || user.getChinesename().isEmpty()) {
			this.addFieldError("knowError", "中文名不得为空");
		}else if (!Pattern.matches("[\u4E00-\u9FFF]{2,10}", user.getChinesename().trim())) {
			this.addFieldError("knowError", "请填写中文");
		}
		if (user.getBirthday() != null) {
			if (user.getBirthday().compareTo(new Date()) > 0) {
				this.addFieldError("knowError", "不合理的出生日期");
			}
		}
		if (user.getMobilephone() != null) {
			if (user.getMobilephone() != null && !user.getMobilephone().isEmpty() && (user.getMobilephone().length() < 10 || user.getMobilephone().length() > 12)) {
				this.addFieldError("knowError", "请输入有效的联系电话");
			}
		}
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			this.addFieldError("knowError", "邮箱不得为空");
		}else if (!Pattern.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+", user.getEmail().trim())) {
			this.addFieldError("knowError", "邮箱格式不合法");
		}
	}

	/**
	 * 检测用户名是否可用
	 * @return 跳转成功
	 */
	public String checkUsername() {
		// 根据userName判断该用户名是否已被注册 
		if(userservice.checkUsername(user.getUsername())) {   
			valid = false;
		} else {   
			valid = true;   
		}  
		inputStream = new ByteArrayInputStream(valid.toString().getBytes());             
		return Action.SUCCESS;
	}
	
	/*
	 * 检测用户状态
	 * @return 跳转成功
	 */
	public String checkUserStatus() {
		if (userstatus == 1) {// 已启用用户
			return "first";
		} else if (userstatus == -1) {// 未启用用户
			return "second";
		} else if (userstatus == 0){// 未审批用户
			return "third";
		} else{
			return INPUT;
		}
	}

	/**
	 * 进入找密码页面
	 * @return 跳转成功
	 */
	public String toRetrievePassword() {
		return SUCCESS;
	}

	/**
	 * 找回密码
	 * @return 跳转成功
	 */
	public String retrievePassword() throws Exception {
		// 检查用户是否存在
		if (!userservice.checkUsername(username)) {
			request.setAttribute("tip", "该用户未被注册。请联系管理员");
			return INPUT;
		}
		user = userservice.getByUsername(username);
		// 检查用户邮箱是否填写
		if (user.getEmail() == null) {
			request.setAttribute("tip", "该用户未填写注册邮箱。请联系管理员");
			return INPUT;
		}
		// 发送密码重置链接至用户邮箱
		if (user.getPwRetrieveCode() == null){
			user.setPwRetrieveCode(MD5.getMD5(new Date().toString()));
		}
		userservice.modify(user);
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		Mail mail = new Mail();
		mail.setSendTo(user.getEmail());
		mail.setSubject("【系统邮件请勿回复】");
		String body = user.getChinesename() + "(" + user.getUsername() + ") 您好,请点击以下链接重置密码:<br />" + 
				basePath + "selfspace/toResetPassword.action?username=" + user.getUsername() +
				"&resetPasswordCode=" + user.getPwRetrieveCode();
		mail.setBody(body);
		Mailer.send(mail);
		return SUCCESS;
	}

	/**
	 * 找回密码校验
	 */
	public void validateretRievePassword() {
		if (username == null || username.isEmpty()) {
			this.addFieldError("knowError", "请输入用户名");
		}
	}

	/**
	 * 查看用户列表，分三类
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String listUser() {
		ActionContext context = ActionContext.getContext();
		Map application = context.getApplication();
		Map session = context.getSession();
		String DisplayNumberEachPage = (String) application.get("DisplayNumberEachPage");
		Pager page = (Pager) session.get("userPage");
		String hql = HQL + " and user.userstatus = " + userstatus;
		if (page == null) {
			hql += " order by user.username asc, user.id asc";
			page = new Pager(userservice.count(hql), Integer.parseInt(DisplayNumberEachPage), hql);
		} else {
			// 判断是否由左侧菜单进入
			if (listLabel == 1) {
				hql += page.getHql().substring(page.getHql().indexOf(" order by "));
				page = new Pager(baseservice.count(hql), Integer.parseInt(DisplayNumberEachPage), hql);
			}
			if (pageNumber != 0) {
				page.setCurrentPage(pageNumber);
			}
		}
		session.put("userPage", page);
		pageList = userservice.list(page.getHql(), page.getStartRow(), page.getPageSize());
		this.initPageBuffer("userPage", null);
		return SUCCESS;
	}
	
	/**
	 * 查找用户角色并组成字符串
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String listRole(){
		// 判断是否为已审批用户列表
		if ((userstatus != 1)&&(userstatus != -1)){
			return SUCCESS;
		}
		roles = new HashMap<String, String>();
		// 声明角色列表用以存储用户角色信息
		List<Role> userrole = new ArrayList();
		// 抽象数组用以转化用户ID
		Object[] o;
		// map对象中用于key存ID
		String mapid = "";
		// 遍历当前已审批用户列表，依次查找各人的角色信息
		for (int i=0; i<pageList.size(); i++){
			// 获取用户ID
			o = (Object[]) pageList.get(i);
			mapid = o[0].toString();
			// map对象中用于value存角色
			String value = "";
			//根据用户ID找到其角色
			userrole = userservice.query("from Role role where role.id in (select user_role.role.id from UserRole user_role where user_role.user.id = '" + o[0].toString() + "') order by role.name asc");
			// 遍历userrole将其角色名拼接为一个字符串，以逗号隔开
			for (int j=0; j<userrole.size(); j++){
				value += userrole.get(j).getName()+",";
			}
			// 去掉最后的一个逗号
			if (!value.equals("")){
				value = value.substring(0, value.length()-1);
			}
			// 将其加入map对象中
			roles.put(mapid, value);
		}
		return SUCCESS;
	}

	/**
	 * 用户初级检索
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String simpleSearch() {
		ActionContext context = ActionContext.getContext();
		Map application = context.getApplication();
		Map session = context.getSession();
		String DisplayNumberEachPage = (String) application.get("DisplayNumberEachPage");
		keyword = keyword.toLowerCase();
		String hql = HQL + " and user.userstatus = " + userstatus;
		if (keyword != null && !keyword.isEmpty()) {
			hql += " and ";
			if (search_type == 1) {
				hql += "LOWER(user.username) like '%" + keyword + "%'";
			} else if (search_type == 2) {
				hql += "LOWER(user.chinesename) like '%" + keyword + "%'";
			} else {
				hql += "(LOWER(user.username) like '%" + keyword + "%' or LOWER(user.chinesename) like '%" + keyword + "%')";
			}
		}
		System.out.println(hql);
		Pager page = (Pager) session.get("userPage");
		if (page != null) {
			hql += page.getHql().substring(page.getHql().indexOf(" order by "));
		} else {
			hql += " order by user.username asc, user.id asc";
		}
		page = new Pager(userservice.count(hql), Integer.parseInt(DisplayNumberEachPage), hql);
		session.put("userPage", page);
		pageList = userservice.list(page.getHql(), page.getStartRow(), page.getPageSize());
		this.initPageBuffer("userPage", null);
		return SUCCESS;
	}

	/**
	 * 排序
	 * @return 列表页面
	 */
	public String sortUser() {
		if(ActionContext.getContext().getSession().get("userPage") == null)
			listUser();
		this.sort("userPage", column[columnLabel]);
		this.initPageBuffer("userPage", null);
		return SUCCESS;
	}

	/**
	 * 不在有效范围，统一设为0
	 */
	public void validateSortUser() {
		if (columnLabel < 0 || columnLabel > column.length - 1) {
			columnLabel  = 0;
		}
	}

	/**
	 * 上一条
	 */
	public String prevUser() {
		if(ActionContext.getContext().getSession().get("userPage") == null)
			listUser();
		userid = this.prevRecord("userPage", userid);
		if (userid == null || userid.isEmpty()) {
			request.setAttribute("tip", "无效的用户");
		}
		return SUCCESS;
	}

	/**
	 * 上一条校验
	 */
	public void validatePrevUser() {
		if (userid == null || userid.isEmpty()) {
			this.addFieldError("knowError", "请选择用户");
		}
	}

	/**
	 * 下一条
	 */
	public String nextUser() {
		if(ActionContext.getContext().getSession().get("userPage") == null)
			listRole();
		userid = this.nextRecord("userPage", userid);
		if (userid == null || userid.isEmpty()) {
			request.setAttribute("tip", "无效的用户");
		}
		return SUCCESS;
	}

	/**
	 * 下一条校验
	 */
	public void validateNextUser() {
		if (userid == null || userid.isEmpty()) {
			this.addFieldError("knowError", "请选择用户");
		}
	}

	/**
	 * 查看用户信息
	 * @return 跳转成功
	 */
	public String viewUser() {
		// 根据用户ID查找基本信息
		user = (User) userservice.query(User.class, userid);
		SystemOption sys;
		if (user.getEthnic() != null) {
			sys = (SystemOption) userservice.query(SystemOption.class, user.getEthnic().getId());
			user.setEthnic(sys);
		}
		if (user.getGender() != null) {
			sys = (SystemOption) userservice.query(SystemOption.class, user.getGender().getId());
			user.setGender(sys);
		}
		return SUCCESS;
	}

	/**
	 * 查看用户信息校验
	 */
	public void validateViewUser() {
		if (userid == null || userid.isEmpty()) {
			this.addFieldError("knowError", "无效的用户");
		} else {
			Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
			if (!(visitor.getUser().getIssuperuser() == 1 || visitor.getUserRight().contains("用户管理") || visitor.getUser().getId().equals(userid))) {
				this.addFieldError("knowError", "您无权查看此用户信息");
			}
		}
	}

	/**
	 * 进入修改用户信息页面
	 * @return 跳转成功
	 */
	public String loadUser() {
		user = (User) userservice.query(User.class, userid);
		ActionContext.getContext().getSession().put("userid", user.getId());
		return SUCCESS;
	}

	/**
	 * 加载用户信息校验
	 */
	public void validateLoadUser() {
		if (userid == null || userid.isEmpty()) {
			this.addFieldError("knowError", "无效的用户");
		} else {
			Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
			if (!(visitor.getUser().getIssuperuser() == 1 || visitor.getUserRight().contains("用户管理") || visitor.getUser().getId().equals(userid))) {
				this.addFieldError("knowError", "您无权修改此用户信息");
			}
		}
	}

	/**
	 * 修改用户信息
	 * @return 跳转成功
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String modifyUser() throws Exception {
		ActionContext context = ActionContext.getContext();
		Map application = context.getApplication();
		String savePath = (String) application.get("UserPictureUploadPath");
		// 检测是否有图片上传
		if (pic != null) {
			FileTool.scaleImage2(pic);
			// 获取随机数
			String signID = SignID.getInstance().getSignID();
			// 获取图片名称
			String realName = FileTool.saveUpload(pic, picFileName,	savePath, signID);
			// 图片保存路径
			user.setPhotofile(realName);
		}
		userid = (String) context.getSession().get("userid");
		//System.out.println(userid);
		User muser;
		muser = (User) userservice.query(User.class, userid);
		//System.out.println(muser.getId());
		muser.setChinesename(user.getChinesename());
		muser.setGender(user.getGender());
		muser.setBirthday(user.getBirthday());
		muser.setEthnic(user.getEthnic());
		muser.setEmail(user.getEmail());
		muser.setMobilephone(user.getMobilephone());
		// 先删除原有图片
		if (user.getPhotofile() != null){
			if (muser.getPhotofile() != null) {
				FileTool.fileDelete(muser.getPhotofile());
			}
			// 设置新的照片
			muser.setPhotofile(user.getPhotofile());
		}
		userservice.modify(muser);
		return SUCCESS;
	}

	/**
	 * 修改校验
	 */
	public void validateModifyUser() {
		if (user.getChinesename() == null || user.getChinesename().isEmpty()) {
			this.addFieldError("knowError", "中文名不得为空");
		}else if (!Pattern.matches("[\u4E00-\u9FFF]{2,10}", user.getChinesename().trim())) {
			this.addFieldError("knowError", "请填写中文");
		}
		if (user.getBirthday() != null) {
			if (user.getBirthday().compareTo(new Date()) > 0) {
				this.addFieldError("knowError", "不合理的出生日期");
			}
		}
		if (user.getMobilephone() != null && !user.getMobilephone().isEmpty() && (user.getMobilephone().length() < 10 || user.getMobilephone().length() > 12)) {
			this.addFieldError("knowError", "请输入有效的联系电话");
		}
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			this.addFieldError("knowError", "邮箱不得为空");
		}else if (!Pattern.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+", user.getEmail().trim())) {
			this.addFieldError("knowError", "邮箱格式不合法");
		}
	}

	/**
	 * 删除用户
	 * @return 跳转成功
	 */
	public String deleteUser() {
		try {
			userservice.delete(User.class, userid);
			this.refreshPager("userPage");
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("tip", "该用户不存在");
			return INPUT;
		}
	}

	/**
	 * 删除校验
	 */
	public void validateDeleteUser() {
		if (userid == null || userid.isEmpty()) {
			this.addFieldError("knowError", "请选择要删除的用户");
		}
	}

	/**
	 * 群删用户
	 * @return 跳转成功
	 */
	public String groupDeleteUser() {
		userservice.deleteGroupUser(userids);
		this.refreshPager("userPage");
		return SUCCESS;
	}

	/**
	 * 群删校验
	 */
	public void validateGroupDeleteUser() {
		if (userids == null || userids.isEmpty()) {
			this.addFieldError("knowError", "请选择要删除的用户");
		}
	}

	/**
	 * 进入批量审批或启用用户页面
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String toGroupOperationP() {
		if ((userstatus != 0) && (userstatus != -1)){
			return ERROR;
		}
		Map session = ActionContext.getContext().getSession();
		// 将要处理的账号ID放入session
		session.put("userids", userids);
		List<Role> noroles = userservice.query("from Role role order by role.name asc");
		session.put("noroles", noroles);
		return SUCCESS;
	}

	/**
	 * 批量审批校验
	 */
	public void validateToGroupOperationP() {
		if (userids == null || userids.isEmpty()) {
			this.addFieldError("knowError", "请选择要审批或启用的用户");
		}
	}

	/**
	 * 批量审批或启用用户
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String groupOperation() {
		Map session = ActionContext.getContext().getSession();
		// 从session中取得要处理的账号ID
		userids =  (List<String>) session.get("userids");
		userservice.enableGroupUser(userids, validity, userstatus, rolesid);
		this.refreshPager("userPage");
		return SUCCESS;
	}

	/**
	 * 批量审批或启用校验
	 */
	public void validateGroupOperation() {
		if (validity == null) {
			this.addFieldError("knowError", "请输入有效期");
		}
		if (userstatus != 0 && userstatus != -1) {
			this.addFieldError("knowError", "不合法的用户状态");
		} else if (userstatus == 0) {
			if (rolesid == null || rolesid.isEmpty()) {
				this.addFieldError("knowError", "用户角色不得为空");
			}
		}
	}

	/**
	 * 批量禁用用户
	 * @return 跳转成功
	 */
	public String groupDisableAccount() {
		userservice.disableGroupUser(userids);
		this.refreshPager("userPage");
		return SUCCESS;
	}

	/**
	 * 批量禁用校验
	 */
	public void validateGroupDisableAccount() {
		if (userids == null || userids.isEmpty()) {
			this.addFieldError("knowError", "请选择要禁用的用户");
		}
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRand() {
		return rand;
	}
	public void setRand(String rand) {
		this.rand = rand;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public int getUserstatus() {
		return userstatus;
	}
	public void setUserstatus(int userstatus) {
		this.userstatus = userstatus;
	}
	public List<String> getUserids() {
		return userids;
	}
	public void setUserids(List<String> userids) {
		this.userids = userids;
	}
	public File getPic() {
		return pic;
	}
	public void setPic(File pic) {
		this.pic = pic;
	}
	public String getPicFileName() {
		return picFileName;
	}
	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}
	public String getPicContentType() {
		return picContentType;
	}
	public void setPicContentType(String picContentType) {
		this.picContentType = picContentType;
	}
	public Map<String, String> getRoles() {
		return roles;
	}
	public void setRoles(Map<String, String> roles) {
		this.roles = roles;
	}
	public Date getValidity() {
		return validity;
	}
	public void setValidity(Date validity) {
		this.validity = validity;
	}
	public List<String> getRolesid() {
		return rolesid;
	}
	public void setRolesid(List<String> rolesid) {
		this.rolesid = rolesid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	public int getSelflabel() {
		return selflabel;
	}
	public void setSelflabel(int selflabel) {
		this.selflabel = selflabel;
	}
	public SystemConfig getSysconfig() {
		return sysconfig;
	}
	public void setSysconfig(SystemConfig sysconfig) {
		this.sysconfig = sysconfig;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public void setUserservice(IUserService userservice) {
		this.userservice = userservice;
	}
	public void setRolerightservice(IRoleRightService rolerightservice) {
		this.rolerightservice = rolerightservice;
	}
}