package csdc.action;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.tool.RandomNumUtil;
import csdc.tool.info.GlobalInfo;

/** 
* @Description: 主要用于跳转到系统首页时读取首页相关信息 
* @author wangming 
* @date 2015-3-12 
*/
@Component   
@Scope(value="prototype")
public class ToIndexAction extends ActionSupport {

	private static final long serialVersionUID = 5079312703841157332L;
	
	private ByteArrayInputStream inputStream; //验证码图片
	private int error;// 记录登录时的错误类别

	/**
	 * @Description: 首页跳转
	 * @return：notLoggedIn：未登入跳转至登入页面；loggedIn：已登入跳转至home页面
	 */
	public String toIndex() {
		Map session = ActionContext.getContext().getSession();
		if (session.get(GlobalInfo.LOGINER) == null) {
			return "notLoggedIn";
		} else {
			return "loggedIn";
		}
	}

	/** 
	* @Description: 获取验证码 
	* @return inputStream：验证码图片流
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
	 * @Description: 进入超时访问页面
	 * @return
	 */
	public String timeout() {
			return SUCCESS;
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
	
}