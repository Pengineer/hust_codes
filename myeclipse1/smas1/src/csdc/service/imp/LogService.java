package csdc.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.User;
import csdc.bean.Log;
import csdc.bean.common.Visitor;
import csdc.service.ILogService;
import csdc.tool.LogProperty;
import csdc.tool.RequestIP;
import csdc.tool.info.GlobalInfo;

/**
 * 系统日志实现类
 * @author 龚凡
 * @version 2011.04.11
 */
@SuppressWarnings("unchecked")
@Transactional
public class LogService extends BaseService implements ILogService {
	
	/**
	 * 对外提供的侵入式日志记录
	 * @param request请求的request对象，记录日志时需要从中获取信息
	 * @param eventDescription事件描述
	 */
	public void addLog(HttpServletRequest request, String eventDescription) {
		if (request != null && eventDescription != null) {// 相关参数存在，则进行日志记录
			Visitor loginer = (Visitor) request.getSession().getAttribute("visitor");// 获取当前登录对象
			Log log = new Log();// 日志对象
			
			if (loginer == null) {// 当前未登录，则不记录账号信息
				log.setUser(null);
				log.setUsername(null);
			} else {// 当前登录，则记录账号信息
				log.setUser(loginer.getUser());
				log.setUsername(loginer.getUser().getUsername());
			}
			
			// 设置日志相关属性
			log.setDate(new Date());
			log.setEventCode(null);// 嵌入生成的日志没有行为代码
			log.setEventDescription(eventDescription);
			log.setIp(RequestIP.getRequestIp(request));
			log.setIsStatistic(0);// 嵌入生成的日志不纳入统计的范畴
			
			this.add(log);// 添加日志
		}
	}

	/**
	 * 查看日志详情
	 * @param logId日志ID
	 * @return 日志对象
	 */
	public Log viewLog(String logId) {
		Map map = new HashMap();
		map.put("logId", logId);
		List<Log> re = this.query("select l from Log l left join fetch l.user user where l.id = :logId", map);// 查询指定的日志信息
		return (re.isEmpty() ? null : re.get(0));
	}

	/**
	 * 删除日志
	 * @param logIds日志ID集合
	 */
	public void deleteLog(List<String> logIds) {
		for (String entityId : logIds){
			this.delete(Log.class, entityId);// 根据ID删除日志，删除日志不会对其它任何数据造成影响
		}
	}
		
}
