// ========================================================================
// 文件名: MainAction.java
//
// 文件说明:
//     本文件主要实现用户登入后，主页面上action功能的加载。主要分为上下左右四帧
// 各个action与页面的对应关系查看struts.xml文件。
//
// 历史记录:
// 2009-11-28  龚凡                        创建文件，完成基本功能.
// ========================================================================

package csdc.action;

import java.io.ByteArrayInputStream;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.SystemConfig;
import csdc.service.IBaseService;
import csdc.tool.RandomNumUtil;

/**
 * 登入前后主页面
 * @author 龚凡
 * @version 1.0 2010.03.31
 */
public class MainAction extends ActionSupport{
	private static final long serialVersionUID = 1313613085019785449L;
	private IBaseService baseservice;
	private ByteArrayInputStream inputStream;
	private String UserPictureUploadPath;// 上传照片路径
	private String DisplayNumberEachPage;// 列表每页显示条数
	private SystemConfig general1, general2, general3, general4, general5, general6;

	/**
	 * top帧跳转
	 * @return 跳转成功
	 */
	public String page_top() {
		return SUCCESS;
	}

	/**
	 * bottom帧跳转
	 * @return 跳转成功
	 */
	public String page_bottom() {
		return SUCCESS;
	}

	/**
	 * left帧跳转
	 * @return 跳转成功
	 */
	public String page_left() {
		return SUCCESS;
	}

	/**
	 * right帧跳转
	 * @return 跳转成功
	 */
	public String page_right() {
		return SUCCESS;
	}

	/**
	 * 首页
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String toIndex() {
		Map session = ActionContext.getContext().getSession();
		String locale = ActionContext.getContext().getLocale().toString();
		session.put("locale", locale);
		return SUCCESS;
	}

	/**
	 * 获取验证码
	 * @return 跳转成功
	 */
	public String rand() {
		RandomNumUtil rdnu = RandomNumUtil.Instance();
		// 取得带有随机字符串的图片
		this.setInputStream(rdnu.getImage());
		// 取得随机字符串放入HttpSession
		ActionContext.getContext().getSession().put("random", rdnu.getString());
		return SUCCESS;
	}
	
	/**
	 * 进入系统设置页面
	 * @return 跳转成功
	 */
	public String toConfig() {
		return SUCCESS;
	}

	/**
	 * 进入上传路径配置页面
	 * @return 跳转成功
	 */
	public String toConfigUpload() {
		return SUCCESS;
	}
	
	/**
	 * 配置上传路径
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String configUpload() {
		ActionContext context = ActionContext.getContext();
		Map application = context.getApplication();
		SystemConfig sys;
		
		// 更新上传图片路径
		sys = (SystemConfig) baseservice.query(SystemConfig.class, "sysconfig00007");
		sys.setValue(UserPictureUploadPath);
		baseservice.modify(sys);
		// 更新到application对象中
		application.put("UserPictureUploadPath", UserPictureUploadPath);
		
		return SUCCESS;
	}

	/**
	 * 上传路径校验
	 */
	public void validateConfigUpload() {
		if (UserPictureUploadPath == null || UserPictureUploadPath.isEmpty()) {
			this.addFieldError("knowError", "上传路径不得为空");
		}
	}

	/**
	 * 进入页面大小配置页面
	 * @return 跳转成功
	 */
	public String toConfigPageSize() {
		return SUCCESS;
	}

	/**
	 * 配置页面大小
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String configPageSize() {
		ActionContext context = ActionContext.getContext();
		Map application = context.getApplication();
		SystemConfig sys;
		
		// 更新每页显示记录条数
		sys = (SystemConfig) baseservice.query(SystemConfig.class, "sysconfig00012");
		sys.setValue(DisplayNumberEachPage);
		baseservice.modify(sys);
		// 更新到application对象中
		application.put("DisplayNumberEachPage", DisplayNumberEachPage);
		return SUCCESS;
	}

	/**
	 * 页面大小设置
	 */
	public void validateConfigPageSize() {
		if (DisplayNumberEachPage == null || DisplayNumberEachPage.isEmpty()) {
			this.addFieldError("knowError", "每页显示记录数不得为空");
		}
	}
	
	/**
	 * 查看一般项目评审参数
	 * @return
	 */
	public String viewConfigGeneral() {
		general1 = (SystemConfig) baseservice.query(SystemConfig.class, "sysconfig00001");
		general2 = (SystemConfig) baseservice.query(SystemConfig.class, "sysconfig00002");
		general3 = (SystemConfig) baseservice.query(SystemConfig.class, "sysconfig00003");
		general4 = (SystemConfig) baseservice.query(SystemConfig.class, "sysconfig00004");
		general5 = (SystemConfig) baseservice.query(SystemConfig.class, "sysconfig00005");
		general6 = (SystemConfig) baseservice.query(SystemConfig.class, "sysconfig00006");
		return SUCCESS;
	}
	
	/**
	 * 进入业务配置
	 * @return SUCCESS跳转成功
	 */
	public String toConfigGeneral() {
		general1 = (SystemConfig) baseservice.query(SystemConfig.class, "sysconfig00001");
		general2 = (SystemConfig) baseservice.query(SystemConfig.class, "sysconfig00002");
		general3 = (SystemConfig) baseservice.query(SystemConfig.class, "sysconfig00003");
		general4 = (SystemConfig) baseservice.query(SystemConfig.class, "sysconfig00004");
		general5 = (SystemConfig) baseservice.query(SystemConfig.class, "sysconfig00005");
		general6 = (SystemConfig) baseservice.query(SystemConfig.class, "sysconfig00006");
		return SUCCESS;
	}

	/**
	 * 修改一般项目配置
	 * @return
	 */
	public String configGeneral() {
		SystemConfig sc;
		sc = (SystemConfig) baseservice.query(SystemConfig.class, general1.getId());
		sc.setValue(general1.getValue());
		baseservice.modify(sc);
		sc = (SystemConfig) baseservice.query(SystemConfig.class, general2.getId());
		sc.setValue(general2.getValue());
		baseservice.modify(sc);
		sc = (SystemConfig) baseservice.query(SystemConfig.class, general3.getId());
		sc.setValue(general3.getValue());
		baseservice.modify(sc);
		sc = (SystemConfig) baseservice.query(SystemConfig.class, general4.getId());
		sc.setValue(general4.getValue());
		baseservice.modify(sc);
		sc = (SystemConfig) baseservice.query(SystemConfig.class, general5.getId());
		sc.setValue(general5.getValue());
		baseservice.modify(sc);
		sc = (SystemConfig) baseservice.query(SystemConfig.class, general6.getId());
		sc.setValue(general6.getValue());
		baseservice.modify(sc);
		return SUCCESS;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getUserPictureUploadPath() {
		return UserPictureUploadPath;
	}
	public void setUserPictureUploadPath(String userPictureUploadPath) {
		UserPictureUploadPath = userPictureUploadPath;
	}
	public String getDisplayNumberEachPage() {
		return DisplayNumberEachPage;
	}
	public void setDisplayNumberEachPage(String displayNumberEachPage) {
		DisplayNumberEachPage = displayNumberEachPage;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public SystemConfig getGeneral1() {
		return general1;
	}
	public void setGeneral1(SystemConfig general1) {
		this.general1 = general1;
	}
	public SystemConfig getGeneral2() {
		return general2;
	}
	public void setGeneral2(SystemConfig general2) {
		this.general2 = general2;
	}
	public SystemConfig getGeneral3() {
		return general3;
	}
	public void setGeneral3(SystemConfig general3) {
		this.general3 = general3;
	}
	public SystemConfig getGeneral4() {
		return general4;
	}
	public void setGeneral4(SystemConfig general4) {
		this.general4 = general4;
	}
	public SystemConfig getGeneral5() {
		return general5;
	}
	public void setGeneral5(SystemConfig general5) {
		this.general5 = general5;
	}

	public SystemConfig getGeneral6() {
		return general6;
	}
	public void setGeneral6(SystemConfig general6) {
		this.general6 = general6;
	}
	public void setBaseservice(IBaseService baseservice) {
		this.baseservice = baseservice;
	}
}