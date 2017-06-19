package org.csdc.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.csdc.bean.GlobalInfo;
import org.csdc.bean.LoginInfo;
import org.csdc.model.Log;
import org.csdc.tool.RequestIP;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 日志管理业
 * @author jintf
 * @date 2014-6-16
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class LogService extends BaseService  {
	
	/**
	 * 查看日志详情
	 * @param logId 日志ID
	 * @return 日志对象
	 */
	public Log viewLog(String logId) {
		Map map = new HashMap();
		map.put("logId", logId);
		List<Log> re = baseDao.query("select l from Log l left join fetch l.account a where l.id = :logId", map);
		return (re.isEmpty() ? null : re.get(0));
	}
	
	/**
	 * 对外提供的侵入式日志记录
	 * @param request 请求的request对象，记录日志时需要从中获取信息
	 * @param eventDescription 事件描述
	 */
	public void addLog(HttpServletRequest request, String eventDescription) {
		if (request != null && eventDescription != null) {
			HttpSession session = request.getSession();
			Object myLog = session.getAttribute("myLog");
			List<Log> logs;
			
			if (myLog == null) {
				logs = new ArrayList<Log>();
				session.setAttribute("myLog", logs);
			} else {
				logs = (List<Log>) myLog;
			}
			
			Log log = new Log();
			Object login = session.getAttribute(GlobalInfo.loginer);
			
			if (login == null) {
				//log.setAccount(null);
			} else {
				LoginInfo loginer = (LoginInfo) login;
				//log.setAccount(loginer.getAccount());
			}
			
			log.setDate(new Date());
			log.setEventCode(null);
			log.setEventDescription(eventDescription);
			log.setIp(RequestIP.getRequestIp(request));
			//log.setIsStatistic(0);
			
			logs.add(log);// 并不是直接添加到数据库，和用于统计的日志一样，先写入用户SESSION，当缓存达到指定数量时，一次写入数据库
		}
	}
}