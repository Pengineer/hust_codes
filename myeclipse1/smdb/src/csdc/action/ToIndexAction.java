package csdc.action;

import java.io.ByteArrayInputStream;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.tool.RandomNumUtil;
import csdc.tool.info.GlobalInfo;

/**
 * 主要用于跳转到系统首页时读取首页相关信息
 * @author 龚凡
 * @version 2011.04.08
 */
@SuppressWarnings("unchecked")
public class ToIndexAction extends ActionSupport {

	private static final long serialVersionUID = 3885300682831655024L;
	private ByteArrayInputStream inputStream; //验证码图片
	private int error;// 记录登录时的错误类别
	private double time;// 记录登录时的错误类别

	/**
	 * 将浏览器语言存入session中
	 */
	public String toIndex() {
		Map session = ActionContext.getContext().getSession();
		String locale = ActionContext.getContext().getLocale().toString();
		session.put("locale", locale.substring(0, 2));//目前仅区分中文(zh/zh_cn/zh_tw/zh_hk等)和英文(en/en_au/en_us)，所以只取前两位
		if (session.get(GlobalInfo.LOGINER) == null) {
			return "notLoggedIn";
		} else {
			return "loggedIn";
		}
	}

	/**
	 * 获取验证码
	 */
	public String rand() {
		RandomNumUtil rdnu = null;
		rdnu = RandomNumUtil.Instance();
		// 取得带有随机字符串的图片
		this.setInputStream(rdnu.getImage());
		// 取得随机字符串放入HttpSession
		ActionContext.getContext().getSession().put("random", rdnu.getString());
		
		return SUCCESS;
	}

	/**
	 * 进入超时访问页面
	 */
	public String timeout() {
		System.out.println(ActionContext.getContext().getSession().get("user_agent"));
		String userAgent = (String) ActionContext.getContext().getSession().get("user_agent");
		if((userAgent.toLowerCase()).contains("darwin")){
			System.out.println("iPhone或Mac机访问");
			return "mobile";
		}else{
//			System.out.println("PC机访问");
			return SUCCESS;
		}
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}
}