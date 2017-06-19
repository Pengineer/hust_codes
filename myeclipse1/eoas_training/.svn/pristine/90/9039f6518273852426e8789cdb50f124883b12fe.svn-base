package csdc.action;

import java.io.ByteArrayInputStream;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 主要用于跳转到系统首页时读取首页相关信息
 * @author 龚凡
 * @version 2011.04.08
 */
@SuppressWarnings("unchecked")
public class ToIndexAction extends ActionSupport {

	private static final long serialVersionUID = 3885300682831655024L;

	/**
	 * 将浏览器语言存入session中
	 */
	public String toIndex() {
		Map session = ActionContext.getContext().getSession();
		String locale = ActionContext.getContext().getLocale().toString();
		session.put("locale", locale.substring(0, 2));//目前仅区分中文(zh/zh_cn/zh_tw/zh_hk等)和英文(en/en_au/en_us)，所以只取前两位
		return SUCCESS;
	}
}