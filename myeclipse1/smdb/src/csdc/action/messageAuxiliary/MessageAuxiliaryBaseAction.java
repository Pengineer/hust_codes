package csdc.action.messageAuxiliary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import csdc.dao.IHibernateBaseDao;
import csdc.service.IAuxiliaryService;
import csdc.service.IMessageAuxiliaryService;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;

/**
 * 辅助信息平台基类
 * @author yangfq
 *
 */
public class MessageAuxiliaryBaseAction extends ActionSupport implements ServletRequestAware, SessionAware{
	
	@Autowired
	protected IMessageAuxiliaryService messageAssistService;
	
	@Autowired
	protected IAuxiliaryService assistService;
	
	@Autowired
	protected IHibernateBaseDao dao;
	
	protected Map jsonMap = new HashMap();// json对象容器
	protected Map session;//session对象
	protected LoginInfo loginer;// 当前登录账号信息对象
	protected HttpServletRequest request;// 请求的request对象
	protected String entityId;// 单个实体ID
	protected List<Integer> query_data;// 查询数据的类型
	protected List<String> query_parm;
	protected int showLineNum; //显示行数
	protected int chartType;//图标显示样式

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.loginer = (LoginInfo) request.getSession().getAttribute(GlobalInfo.LOGINER);
		this.request = request;
	}

	public Map getSession() {
		return session;
	}

	public LoginInfo getLoginer() {
		return loginer;
	}

	public void setLoginer(LoginInfo loginer) {
		this.loginer = loginer;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public List<Integer> getQuery_data() {
		return query_data;
	}

	public void setQuery_data(List<Integer> query_data) {
		this.query_data = query_data;
	}

	public List<String> getQuery_parm() {
		return query_parm;
	}

	public void setQuery_parm(List<String> query_parm) {
		this.query_parm = query_parm;
	}

	public int getShowLineNum() {
		return showLineNum;
	}

	public void setShowLineNum(int showLineNum) {
		this.showLineNum = showLineNum;
	}

	public int getChartType() {
		return chartType;
	}

	public void setChartType(int chartType) {
		this.chartType = chartType;
	}
	
}
