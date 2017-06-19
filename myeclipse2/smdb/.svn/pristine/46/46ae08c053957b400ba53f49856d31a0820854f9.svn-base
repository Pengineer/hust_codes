package csdc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.dao.IHibernateBaseDao;
import csdc.service.IMessageAuxiliaryService;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;


/**
 * 导航栏相关的操作
 * @author yangfq
 *
 */

public class NavigationAction extends ActionSupport {
	
	@Autowired
	protected IHibernateBaseDao dao;
	
	@Autowired
	protected IMessageAuxiliaryService messageAssistService;
	
	private List<Object> menus;//历史访问的列表记录
	
	protected Map jsonMap = new HashMap();// json对象容器
	
	/**
	 * 历史访问记录以及收藏夹
	 * @author yangfq
	 * @return List
	 */
	public String getHistory(){
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		menus = messageAssistService.getHistoryMenu(loginer);
		return SUCCESS;
	}
	

	public List<Object> getMenus() {
		return menus;
	}

	public void setMenus(List<Object> menus) {
		this.menus = menus;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}


}
