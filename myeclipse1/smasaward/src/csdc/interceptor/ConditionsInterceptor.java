package csdc.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;

import csdc.bean.SystemConfig;
import csdc.bean.common.Visitor;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.ConditionsProperty;
import csdc.tool.info.GlobalInfo;

public class ConditionsInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = 1L;
	@Autowired
	protected IHibernateBaseDao dao;
	
	public String intercept(ActionInvocation arg0) throws Exception {
		Map session = arg0.getInvocationContext().getSession();
		// 获取命名空间及action名，并拼成一个简略的URL
		String nameSpace = arg0.getProxy().getNamespace();
		String actionName = arg0.getProxy().getActionName();
		String url = nameSpace.substring(1) + "/" + actionName;
		Visitor visitor = (Visitor) session.get("visitor");
		if(visitor == null || visitor.getUser() == null) {
			return arg0.invoke();
		}
		if(ConditionsProperty.YEAR_FILTER_URL_MAP.get(url) != null) {
			int currentYear = (Integer) session.get("defaultYear");
			int defaultYear = Integer.parseInt(((SystemConfig) dao.query(SystemConfig.class, "sysconfig00014")).getValue());
			if(currentYear == defaultYear) {
				return arg0.invoke();
			} else {
				// 获得值栈  
				ValueStack valueStack = arg0.getStack();
				Map jsonMap = (Map) valueStack.findValue("jsonMap");
				jsonMap.put(GlobalInfo.ERROR_INFO, "不允许对当前年度项目做此操作");
				return "reminder";
			}
		}
		return arg0.invoke();
	}

}
