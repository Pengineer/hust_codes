package csdc.tool.interceptor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.bean.Log;
import csdc.bean.Officer;
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.LogProperty;
import csdc.tool.RequestIP;
import csdc.tool.ResponseStatusCodes;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;

/**
 * 日志拦截器，根据配置的日志要求，记录用户日志
 * @author 龚凡
 * @version 2011.03.03
 */
public class LogInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	@Autowired
	protected IHibernateBaseDao dao;
	long timeGap;//action的运行响应时间
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	
	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {
		Map session = invocation.getInvocationContext().getSession();
		String resultCode="";
		Date actionStartDate,actionEndDate;
		try {
			String sDate = df.format(new Date() );
			actionStartDate = df.parse(sDate);
			resultCode = invocation.invoke();
			String eDate = df.format(new Date() );
			actionEndDate = df.parse(eDate);
			timeGap = actionEndDate.getTime() - actionStartDate.getTime();//单位:毫秒（ms）
		} catch (Exception e) {
			System.out.print("invocation.invoke报错");
			e.printStackTrace();
			//进行action异常记录
		}
		
		// 获取命名空间及action名，并拼成一个简略的URL
		String nameSpace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String url = nameSpace.substring(1) + "/" + actionName;
		
		// 获取该URL对应的日志描述信息
		String[] logInfo = LogProperty.LOG_CODE_URL_MAP.get(url);
		
		if (logInfo != null) {// 如果不为空，则进行日志记录
			// 获取当前登录账号
			LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
			Passport passport = loginer == null ? null : loginer.getPassport();
			Account account = loginer == null ? null : loginer.getAccount();
			
			if (account != null) {// 如果当前账号存在，则表示用户处于登录状态，记录日志
				if (url.equals("login/doLogin")) {// 如果是登录日志，单独记录。因为退出不是action，为了记录退出日志，需要复用日志IP及account
					Log loginLog = (Log) session.get("loginLog");
					if (loginLog == null) {// 因为未退出，也可以回首页继续登录，此时不再记录登录日志
						loginLog = addLog(account, logInfo,1);
						session.put("loginLog", loginLog);// 将登录日志存入session备用
						dao.add(loginLog);// 保证登录信息最先记录
					}
				} else {// 其它日志，临时记录在用户的session中，当用户日志达到一定数量时，再一次写入数据库
					List<Log> logs = (List<Log>) session.get("myLog");
					if (logs == null) {// 如果日志缓存对象为空，则new一个
						logs = new ArrayList<Log>();
						session.put("myLog", logs);
					}
					if (url.contains("toList") || url.equals("system/option/toView")) {//进入列表的时候做单独处理
						Log log = addLog(account, logInfo, 2);// 创建一个日志对象
						logs.add(log);// 将日志对象添加到缓存列表中
					} else {
						Log log = addLog(account, logInfo, 0);// 创建一个日志对象
						logs.add(log);// 将日志对象添加到缓存列表中
					}
					// 当达到指定存储时，将日志写入数据库，并清除当前缓存的日志
					if (logs.size() >= GlobalInfo.LOG_NUMBER_EACH_TIME) {
						for(Log l : logs) {
							dao.add(l);
						}
						logs.clear();
					} else if (url.equals("login/ckeckAccount")) {
						for(Log l : logs) {
							dao.add(l);
						}
						logs.clear();
					}
				}
			} else {//表示还没有选择账号
				if (url.equals("login/doLogin")) {// 如果是登录日志，单独记录。因为退出不是action，为了记录退出日志，需要复用日志IP及account
					Log loginLog = (Log) session.get("loginLog");
					if (loginLog == null) {// 因为未退出，也可以回首页继续登录，此时不再记录登录日志
						loginLog = addLogP(passport, logInfo);
						session.put("loginLog", loginLog);// 将登录日志存入session备用
						dao.add(loginLog);// 保证登录信息最先记录
					}
				}
			}
		}
		return resultCode;
	}

	/**
	 * 生成日志对象
	 * @param account当前账号对象
	 * @param logInfo日志信息描述
	 * @return log日志对象
	 * @throws Exception 
	 */
	private Log addLog(Account account, String[] logInfo ,Integer flag) throws Exception {
		Log log = new Log();
		String name = "";
		Passport passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
		log.setPassport(passport);
		if(flag == 1) {
			log.setAccount(null);
		} else {
			log.setAccount(account);
		}
		if (passport != null) {
			name = passport.getName();
			log.setAccountName(name);
		}
		AccountType type = account.getType();// 待查看的账号级别
		int isPrincipal = account.getIsPrincipal();// 待查看张类别
		String belongId = "";// 账号所属ID
		AccountType accountType = account.getType();
		if (accountType.within(AccountType.MINISTRY, AccountType.LOCAL_UNIVERSITY)) {
			if (account.getIsPrincipal() == 1) {
				belongId = account.getAgency().getId();
			} else {
				belongId = account.getOfficer().getId(); 
			}
		} else if (accountType.equals(AccountType.DEPARTMENT)) {
			if (account.getIsPrincipal() == 1) {
				belongId = account.getDepartment().getId();
			} else {
				belongId = account.getOfficer().getId();
			}
		} else if (accountType.equals(AccountType.INSTITUTE)) {
			if (account.getIsPrincipal() == 1) {
				belongId = account.getInstitute().getId();
			} else {
				belongId = account.getOfficer().getId();
			}
		} else if (accountType.within(AccountType.EXPERT, AccountType.STUDENT) || accountType.equals(AccountType.ADMINISTRATOR)) {
			belongId = account.getPerson().getId();
		}
		Agency agency;// 部、省、校级机构对象
		Department department;// 院系对象
		Institute institute;// 基地对象
		Person person;// 人员对象
		
		// 查询账号所属信息，并将其存入jsonMap对象
		if (isPrincipal == 1) {// 主账号需进一步判断账号级别
			if (type.equals(AccountType.MINISTRY) || type.equals(AccountType.PROVINCE) || type.equals(AccountType.MINISTRY_UNIVERSITY) || type.equals(AccountType.LOCAL_UNIVERSITY)) {// 部、省、校级主账号，查询agency表
				agency = (Agency) dao.query(Agency.class, belongId);
				log.setAccountBelong(agency.getName());
			} else if (type.equals(AccountType.DEPARTMENT)) {// 院系主账号，查询department表
				department = (Department) dao.query(Department.class, belongId);
				agency = (Agency) dao.query(Agency.class, department.getUniversity().getId());
				log.setAccountBelong(agency.getName() + department.getName());
			} else if (type.equals(AccountType.INSTITUTE)) {// 基地主账号，查询institute表
				institute = (Institute) dao.query(Institute.class, belongId);
				agency = (Agency) dao.query(Agency.class, institute.getSubjection().getId());
				log.setAccountBelong(agency.getName() + institute.getName() );
			} else if (type.equals(AccountType.EXPERT) || type.equals(AccountType.TEACHER) || type.equals(AccountType.STUDENT)) {// 外专家、教师、学生查询person表
				person = (Person) dao.query(Person.class, belongId);
				log.setAccountBelong(person.getName());
			}
		} else {// 子账号查询officer表
			Officer officer = (Officer) dao.query(Officer.class, belongId);
			person = (Person) dao.query(Person.class, officer.getPerson().getId());
			// 查询管理人员所属机构信息
			if (type.equals(AccountType.MINISTRY) || type.equals(AccountType.PROVINCE) || type.equals(AccountType.MINISTRY_UNIVERSITY) || type .equals(AccountType.LOCAL_UNIVERSITY)) {// 部、省、校级子账号，查询agency表
				agency = (Agency) dao.query(Agency.class, officer.getAgency().getId());
				log.setAccountBelong(agency.getName() + person.getName());
			} else if (type.equals(AccountType.DEPARTMENT)) {// 院系主账号，查询department表
				department = (Department) dao.query(Department.class, officer.getDepartment().getId());
				agency = (Agency) dao.query(Agency.class, department.getUniversity().getId());
				log.setAccountBelong(agency.getName() + department.getName() + person.getName());
			} else if (type.equals(AccountType.INSTITUTE)) {// 基地主账号，查询institute表
				institute = (Institute) dao.query(Institute.class, officer.getInstitute().getId());
				agency = (Agency) dao.query(Agency.class, institute.getSubjection().getId());
				log.setAccountBelong(agency.getName()+institute.getName()+person.getName());
			}
		}
		if (type.equals(AccountType.ADMINISTRATOR)) {
			log.setAccountBelong("系统管理员");
		}
		
		log.setDate(new Date());
		log.setIp(RequestIP.getRequestIp(ServletActionContext.getRequest()));
		log.setEventCode(logInfo[0]);
		Map mobileSession = ActionContext.getContext().getSession();
		String mobileLoginTag = (String) mobileSession.get("mobileTag");
		if(null != mobileLoginTag){
			log.setEventDscription(mobileLoginTag+"-"+logInfo[1]);//补充机型标记
		}
		else log.setEventDscription(logInfo[1]);
		log.setIsStatistic(1);
		if (flag == 2) {
			JSONObject mapObj = new JSONObject();
			mapObj.put("description", logInfo[1]);
			mapObj.put("url", logInfo[2]);
			log.setRequest(mapObj.toString());
			log.setResponse("进入列表");
		} else {
			log.setRequest(this.reqString(ServletActionContext.getRequest()));
			log.setResponse(this.respString(ServletActionContext.getResponse()));
		}
		
		return log;
	}
	
	private Log addLogP(Passport passport, String[] logInfo ) throws Exception {
		Log log = new Log();
		log.setPassport(passport);
		log.setAccountName(passport.getName());
		log.setDate(new Date());
		log.setIp(RequestIP.getRequestIp(ServletActionContext.getRequest()));
		log.setEventCode(logInfo[0]);
		Map mobileSession = ActionContext.getContext().getSession();
		String mobileLoginTag = (String) mobileSession.get("mobileTag");
		if(null != mobileLoginTag){
			log.setEventDscription(mobileLoginTag+"-"+logInfo[1]);//补充机型标记
		}
		else log.setEventDscription(logInfo[1]);
		log.setIsStatistic(1);
		log.setRequest(this.reqString(ServletActionContext.getRequest()));
		log.setResponse(this.respString(ServletActionContext.getResponse()));
		return log;
	}

	private String reqString(HttpServletRequest request) throws Exception {
		JSONObject mapObj = new JSONObject();
		mapObj.put("authType", request.getAuthType());
		mapObj.put("characterEncoding", request.getCharacterEncoding());
		mapObj.put("protocol", request.getProtocol());
		mapObj.put("server", request.getServerName() + ":" + request.getServerPort());
		mapObj.put("servletPath", request.getServletPath());
		mapObj.put("requestURI", request.getRequestURI());
		mapObj.put("parameters", this.getParemeters(request));
		mapObj.put("referer", request.getHeader("Referer"));
		mapObj.put("userAgent", request.getHeader("User-Agent"));
		mapObj.put("requestMethod", request.getMethod());
		mapObj.put("accept", request.getHeader("Accept"));	
		return mapObj.toString();
	}
	
	
	private String respString(HttpServletResponse response) {
		JSONObject mapObj = new JSONObject();
		mapObj.put("characterEncoding", response.getCharacterEncoding());
		mapObj.put("contentType", response.getContentType());
		mapObj.put("locale", response.getLocale());
		mapObj.put("bufferSize", response.getBufferSize());
//		mapObj.put("responseStatus",ResponseStatusCodes.Response_Status_Codes_MAP.get(String.valueOf(response.getStatus())));		
		mapObj.put("operationTime",timeGap);
		return mapObj.toString();
	}
	
	@SuppressWarnings("unchecked")
	private String getParemeters(HttpServletRequest request) throws Exception {
		Map<String, String[]> map = request.getParameterMap();
		JSONObject jsonOjb = JSONObject.fromObject(map);
		if(map.isEmpty()){
			return jsonOjb.toString();
		}
		JSONObject mapObj = new JSONObject();
		for(String key : map.keySet()){
			mapObj.put(key, request.getParameter(key).toString());
		}
		return jsonOjb.toString();
	}
}
