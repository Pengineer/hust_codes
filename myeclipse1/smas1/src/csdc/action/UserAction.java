// ========================================================================
// 文件名: UserAction.java
//
// 文件说明:
//	 本文件主要实现用户管理模块的功能，包括用户的登入、退出、注册、验证、审批、
// 禁用、删除、查看、修改、检索等。主要用到service层的接口有IUserService
// 、IRoleRightService。各个action与页面的对应关系查看struts-user.xml文件。
//
// 历史记录:
// 2009-11-28  龚凡						创建文件，完成基本功能.
// ========================================================================

package csdc.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Mail;
import csdc.bean.Role;
import csdc.bean.SystemConfig;
import csdc.bean.SystemOption;
import csdc.bean.User;
import csdc.bean.common.Visitor;
import csdc.dao.HibernateBaseDao;
import csdc.service.IRoleRightService;
import csdc.service.IUserService;
import csdc.tool.FileTool;
import csdc.tool.MD5;
import csdc.tool.SignID;
import csdc.tool.info.GlobalInfo;
import csdc.tool.mail.MailController;

/**
 * 用户管理
 * @author 龚凡
 * @version 1.0 2010.03.31
 */
@SuppressWarnings("unchecked")
public class UserAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select user.id, user.username, user.chinesename, user.registertime, user.approvetime, user.expiretime, 'roles' from User user where user.issuperuser = 0";
	private static final String PAGENAME = "userPage";
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "user.id";//缓存id
	private static final String column[] = {
			"user.username",
			"user.chinesename",
			"user.registertime",
			"user.approvetime",
			"user.expiretime"
	};// 排序用的列
	private IUserService userservice;// 用户管理模块接口
	private IRoleRightService rolerightservice;// 角色与权限管理模块接口
	private String username, password, rand, repassword, newPassword;// 登入变量
	private User user;// 用户对象
	private int userstatus;// 用户状态标志
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
	private String pwRetrieveCode;// 密码重置校验码

	@Autowired
	private MailController mailController;
	
	@Autowired
	private HibernateBaseDao dao;
	/**
	 * 登入系统，登入后用户相关权限等信息放入session中备用
	 * @return 跳转成功
	 */
	public String login() {
		Map session = ActionContext.getContext().getSession();
		// 检测验证码是否正确
		String arandom = (String) (session.get("random"));
		if ((arandom == null) || (!arandom.equals(rand))) {
			request.setAttribute("errorInfo", "验证码错误");
			return INPUT;
		}
		// 检测用户名和密码是否匹配
		password = MD5.getMD5(password);//MD5加密
		if (!userservice.checkPassword(username, password)) {
			request.setAttribute("errorInfo", "用户名或者密码错误");
			return INPUT;
		}
		user = (User) userservice.getByUsername(username);
		// 检测账号是否可用
		if (user.getUserstatus() == 0) {
			request.setAttribute("errorInfo", "此账号尚未通过审批");
			return INPUT;
		}else if (user.getUserstatus() == -1) {
			request.setAttribute("errorInfo", "此账号已停用");
			return INPUT;
		}else {
			// 判断账号是否过期
			Date currenttime = new Date();
			// 若果过期，则停用此账号
			if (currenttime.after(user.getExpiretime())){
				user.setUserstatus(-1);
				userservice.modify(user);
				request.setAttribute("errorInfo", "此账号已停用");
				return INPUT;
			}
			
			//登陆者信息
			Visitor visitor = new Visitor();
			visitor.setUser(user);
			visitor.setUserRight(rolerightservice.getUserRight(user.getId()));
			session.put("visitor", visitor);
			
			Integer defaultYear = (Integer) ActionContext.getContext().getApplication().get("defaultYear");
			session.put("defaultYear", defaultYear);
			
//			int flag = 1;
//			Long generalReviewers = (Long) rolerightservice.queryUnique(" select count(pr) from GeneralReviewer pr where pr.year = '" + defaultYear + "' and pr.isManual = 0 ");
//			if(null == generalReviewers || generalReviewers == 0) {
//				flag = 0;
//			}
			
			SystemConfig sf = (SystemConfig) rolerightservice.query(SystemConfig.class, "sysconfig00015");
			int sysStatus = Integer.parseInt(sf.getValue());
			session.put("SystemStatus", sysStatus);
			
			//系统正在维护中...
//			if(user.getIssuperuser() == 0 && (flag == 0 || sysStatus == 0) ) {
			if(user.getIssuperuser() == 0 && sysStatus == 0) {
				request.getSession().invalidate();
				return "inMaintain";
			}
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
	public String register() throws Exception {
		// 检测用户名是否可用
		if (userservice.checkUsername(user.getUsername())) {
			request.setAttribute("errorInfo", "此用户名已被注册");
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
		entityId = (String) userservice.add(user);
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
			request.setAttribute("errorInfo", "该用户未被注册。请联系管理员");
			return INPUT;
		}
		user = userservice.getByUsername(username);
		// 检查用户邮箱是否填写
		if (user.getEmail() == null) {
			request.setAttribute("errorInfo", "该用户未填写注册邮箱。请联系管理员");
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
		mail.setTo(user.getEmail());
		Date createDate = new Date(System.currentTimeMillis());
		mail.setCreateDate(createDate);
//		mail.setFrom("742765702@qq.com");
		mail.setReplyTo("serv@csdc.info");
		mail.setSubject("【系统邮件请勿回复】");
		String body = user.getChinesename() + "(" + user.getUsername() + ") 您好,请点击以下链接重置密码:<br />" + 
				basePath + "selfspace/toResetPassword.action?username=" + user.getUsername() +
				"&resetPasswordCode=" + user.getPwRetrieveCode();
		mail.setBody(body);
		dao.add(mail);
		mailController.send(mail.getId());
//		Mailer.send(mail);
		return SUCCESS;
	}

	/**
	 * 找回密码校验
	 */
	public void validateRetrievePassword() {
		if (username == null || username.isEmpty()) {
			this.addFieldError("knowError", "请输入用户名");
		}
	}
	
	/**
	 * 进入修改密码页面。
	 */
	public String toModifyPassword() {
		/**
		 * 1、根据参数获取账号。
		 * 2、判断账号是否存在，级别及类别是否正确。
		 * 3、判断账号所属是否在当前账号管辖范围。
		 * 4、将待修改密码的账号ID放入session缓存。
		 */
		if (!userservice.checkUsername(username)) {
			request.setAttribute("errorInfo", "该用户未被注册。请联系管理员");
			return INPUT;
		}
		user = userservice.getByUsername(username);
		ActionContext.getContext().getSession().put("userid", user.getId());// 缓存账号ID到session
		return SUCCESS;
	}
	
	/**
	 * 修改密码。
	 */
	public String modifyPassword() {
		/**
		 * 1、根据参数获取账号。
		 * 2、设置是否登录提示。
		 * 3、修改密码。
		 * 4、清除session中缓存的账号ID
		 */
		Map session = ActionContext.getContext().getSession();
		user = (User) baseService.query(User.class, (String) session.get("userid"));// 获取指定账号
		user.setPassword(MD5.getMD5(newPassword.trim()));// 将密码加密后存储
		baseService.modify(user);// 更新到数据库
		session.remove("userid");// 清除缓存的账号ID
		return SUCCESS;
	}
	
	/**
	 * 找回密码校验
	 */
	public void validateModifyPassword() {
		if (newPassword.trim() == null || newPassword.trim().isEmpty()) {
			this.addFieldError("knowError", "请输入新密码");
		}
	}

	/**
	 * 列表和初级检索条件
	 */
	@Override
	public Object[] simpleSearchCondition() {
		keyword = (keyword == null) ? "" : keyword.toLowerCase().trim();// 预处理关键字
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		hql.append(" and user.userstatus = :userstatus");
		map.put("userstatus", userstatus);
		if (keyword != null && !keyword.isEmpty()) {
			hql.append(" and ");
			if (searchType == 1) {
				hql.append("LOWER(user.username) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {
				hql.append("LOWER(user.chinesename) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else {
				hql.append("(LOWER(user.username) like :keyword or LOWER(user.chinesename) like :keyword)");
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
	 * 重新list()
	 * 目的：查找用户角色并组成字符串
	 */
	public String list(){
		super.list();
		// 判断是否为已审批用户列表
		if ((userstatus != 1)&&(userstatus != -1)){
			return SUCCESS;
		}
		List oldLaData = (List) jsonMap.get("laData");
		// 声明角色列表用以存储用户角色信息
		List<Role> userRole = new ArrayList();
		// 抽象数组用以转化用户ID
		Object[] o;
		//遍历当前页所有专家
		for (int i=0; i<oldLaData.size(); i++){
			// 获取用户ID
			o = (Object[]) oldLaData.get(i);
			//组装后的值
			String value = "";
			//根据用户ID找到其角色
			userRole = userservice.query("from Role role where role.id in (select user_role.role.id from UserRole user_role where user_role.user.id = '" + o[0].toString() + "') order by role.name asc");
			// 遍历userrole将其角色名拼接为一个字符串，以逗号隔开
			for (int j=0; j<userRole.size(); j++){
				value += userRole.get(j).getName()+",";
			}
			// 去掉最后的一个逗号
			if (!value.equals("")){
				value = value.substring(0, value.length()-1);
			}
			// 将组装后的用户角色放入o[6](占位符)对象中
			o[6] = value;
		}
		laData = oldLaData;
		jsonMap.put("laData", laData);
		return SUCCESS;
	}
	
	/**
	 * 进入查看
	 * @return SUCCESS跳转查看页面
	 */
	public String toView(){
		return SUCCESS;
	}

	/**
	 * 查看用户信息
	 * @return 跳转成功
	 */
	public String view(){
		// 根据用户ID查找基本信息
		user = (User) userservice.query(User.class, entityId);
		if (user == null) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "不存在的用户");
			jsonMap.put(GlobalInfo.ERROR_INFO, "不存在的用户");
			return INPUT;
		}
		String ethnic = "";//民族
		String gender = "";//性别
		SystemOption sys;
		if (user.getEthnic() != null) {
			sys = (SystemOption) userservice.query(SystemOption.class, user.getEthnic().getId());
			user.setEthnic(sys);
			ethnic = user.getEthnic().getName();
		}
		if (user.getGender() != null) {
			sys = (SystemOption) userservice.query(SystemOption.class, user.getGender().getId());
			user.setGender(sys);
			gender = user.getGender().getName();
		}
		jsonMap.put("ethnic", ethnic);
		jsonMap.put("gender", gender);
		jsonMap.put("user", user);
		return SUCCESS;
	}
	
	/**
	 * 查看用户信息校验
	 */
	public void validateToView() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择要查看的专家");
			jsonMap.put(GlobalInfo.ERROR_INFO, "请选择要查看的专家");
		} else {
			Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
			if (!(visitor.getUser().getIssuperuser() == 1 || visitor.getUserRight().contains("用户管理") || visitor.getUser().getId().equals(entityId))) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "您无权查看此用户信息");
				jsonMap.put(GlobalInfo.ERROR_INFO, "您无权查看此用户信息"); 
			}
		}
	}

	/**
	 * 进入修改页面
	 * @return 跳转成功
	 */
	public String toModify(){
		user = (User) userservice.query(User.class, entityId);
		if (user == null) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的用户");
			jsonMap.put(GlobalInfo.ERROR_INFO, "无效的用户");
			return INPUT;
		} 
		session.put("entityId", entityId);
		return SUCCESS;
	}
	
	/**
	 * 加载用户信息校验
	 */
	public void validateToModify() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的用户");
			jsonMap.put(GlobalInfo.ERROR_INFO, "无效的用户");
		} else {
			Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
			if (!(visitor.getUser().getIssuperuser() == 1 || visitor.getUserRight().contains("用户管理") || visitor.getUser().getId().equals(entityId))) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "您无权修改此用户信息");
				jsonMap.put(GlobalInfo.ERROR_INFO, "您无权修改此用户信息");
			}
		}
	}
	
	/**
	 * 修改用户信息
	 * @return 跳转成功
	 * @throws Exception 
	 */
	public String modify() throws Exception {
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
		entityId = (String) session.get("entityId");
		User muser = (User) userservice.query(User.class, entityId);
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
	public void validateModify() {
		if (user.getChinesename() == null || user.getChinesename().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "中文名不得为空");
			jsonMap.put(GlobalInfo.ERROR_INFO, "中文名不得为空");
		}else if (!Pattern.matches("[\u4E00-\u9FFF]{2,10}", user.getChinesename().trim())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请填写中文");
			jsonMap.put(GlobalInfo.ERROR_INFO, "请填写中文");
		}
		if (user.getBirthday() != null) {
			if (user.getBirthday().compareTo(new Date()) > 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "不合理的出生日期");
				jsonMap.put(GlobalInfo.ERROR_INFO, "不合理的出生日期");
			}
		}
		if (user.getMobilephone() != null && !user.getMobilephone().isEmpty() && (user.getMobilephone().length() < 10 || user.getMobilephone().length() > 12)) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请输入有效的联系电话");
			jsonMap.put(GlobalInfo.ERROR_INFO, "请输入有效的联系电话");
		}
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "邮箱不得为空");
			jsonMap.put(GlobalInfo.ERROR_INFO, "邮箱不得为空");
		}else if (!Pattern.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+", user.getEmail().trim())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "邮箱格式不合法");
			jsonMap.put(GlobalInfo.ERROR_INFO, "邮箱格式不合法");
		}
	}

	/**
	 * 删除用户
	 * @return 跳转成功
	 */
	public String delete(){
		try {
			userservice.deleteUsers(entityIds);
			return SUCCESS;
		} catch (Exception e) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "该用户不存在");
			jsonMap.put(GlobalInfo.ERROR_INFO, "该用户不存在");
			return INPUT;
		}
	}

	/**
	 * 删除校验
	 */
	public void validateDelete() {
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择要删除的用户");
			jsonMap.put(GlobalInfo.ERROR_INFO, "请选择要删除的用户");
		}
	}

	/**
	 * 进入批量审批或启用用户页面
	 * @return 跳转成功
	 */
	public String toGroupOperationP() {
		if ((userstatus != 0) && (userstatus != -1)){
			return ERROR;
		}
		Map session = ActionContext.getContext().getSession();
		// 将要处理的账号ID放入session
		session.put("entityIds", entityIds);
		List<Role> noroles = userservice.query("from Role role order by role.name asc");
		List<Role> roles = new ArrayList<Role>();
		session.put("noroles", noroles);
		session.put("roles", roles);
		return SUCCESS;
	}

	/**
	 * 批量审批校验
	 */
	public void validateToGroupOperationP() {
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择要审批或启用的用户");
			jsonMap.put(GlobalInfo.ERROR_INFO, "请选择要审批或启用的用户");
		}
	}

	/**
	 * 批量审批或启用用户
	 * @return 跳转成功
	 */
	public String groupOperation() {
		Map session = ActionContext.getContext().getSession();
		// 从session中取得要处理的账号ID
		entityIds =  (List<String>) session.get("entityIds");
		userservice.enableGroupUser(entityIds, validity, userstatus, rolesid);
//		this.refreshPager("userPage");
		return SUCCESS;
	}

	/**
	 * 批量审批或启用校验
	 */
	public void validateGroupOperation() {
		if (validity == null) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请输入有效期");
			jsonMap.put(GlobalInfo.ERROR_INFO, "请输入有效期");
		}
		if (userstatus != 0 && userstatus != -1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "不合法的用户状态");
			jsonMap.put(GlobalInfo.ERROR_INFO, "不合法的用户状态");
		} else if (userstatus == 0) {
			if (rolesid == null || rolesid.isEmpty()) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "用户角色不得为空");
				jsonMap.put(GlobalInfo.ERROR_INFO, "用户角色不得为空");
			}
		}
	}

	/**
	 * 批量禁用用户
	 * @return 跳转成功
	 */
	public String groupDisableAccount() {
		userservice.disableGroupUser(entityIds);
//		this.refreshPager("userPage");
		return SUCCESS;
	}

	/**
	 * 批量禁用校验
	 */
	public void validateGroupDisableAccount() {
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择要禁用的用户");
			jsonMap.put(GlobalInfo.ERROR_INFO, "请选择要禁用的用户");
		}
	}

	/**
	 * 生成密码重置码。
	 * @throws Exception 
	 */
	public String retrieveCode() throws Exception {
		/**
		 * 1、根据参数获取账号。
		 * 2、判断账号是否存在。
		 * 3、生成密码重置码，并以邮件形式通知用户。
		 */
		// 检查用户是否存在
		username = username.trim();
		if (!userservice.checkUsername(username)) {
			jsonMap.put(GlobalInfo.ERROR_INFO, "该用户未被注册。请联系管理员");
			return INPUT;
		}
		user = userservice.getByUsername(username);
		// 检查用户邮箱是否填写
		if (user.getEmail() == null) {
			jsonMap.put(GlobalInfo.ERROR_INFO, "该用户未填写注册邮箱。请联系管理员");
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
		mail.setTo(user.getEmail());
		Date createDate = new Date(System.currentTimeMillis());
		mail.setCreateDate(createDate);
//		mail.setFrom("742765702@qq.com");
		mail.setReplyTo("serv@csdc.info");
		
		mail.setSubject("【系统邮件请勿回复】");
		String body = user.getChinesename() + "(" + user.getUsername() + ") 您好,请点击以下链接重置密码:<br />" + 
				basePath + "selfspace/toResetPassword.action?username=" + user.getUsername() +
				"&resetPasswordCode=" + user.getPwRetrieveCode();
		mail.setBody(body);
		dao.add(mail);
		mailController.send(mail.getId());
//		Mailer.send(mail);
		return SUCCESS;
	}

	/**
	 * 密码重置校验
	 */
	public void validateRetrieveCode() {
		if (username == null || username.isEmpty()) {// username不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择要重置密码的用户");
			jsonMap.put(GlobalInfo.ERROR_INFO, "请选择要重置密码的用户");
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

	public void setPwRetrieveCode(String pwRetrieveCode) {
		this.pwRetrieveCode = pwRetrieveCode;
	}

	public String getPwRetrieveCode() {
		return pwRetrieveCode;
	}
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
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
