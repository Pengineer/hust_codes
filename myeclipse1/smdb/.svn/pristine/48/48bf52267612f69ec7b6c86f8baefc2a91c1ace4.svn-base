package csdc.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Account;
import csdc.bean.Log;
import csdc.service.ILogService;
import csdc.tool.LogProperty;
import csdc.tool.RequestIP;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
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
			LoginInfo loginer = (LoginInfo) request.getSession().getAttribute(GlobalInfo.LOGINER);// 获取当前登录对象
			Log log = new Log();// 日志对象
			
			if (loginer == null) {// 当前未登录，则不记录账号信息
				log.setAccount(null);
				log.setAccountName(null);
			} else {// 当前登录，则记录账号信息
				log.setAccount(loginer.getAccount());
				log.setAccountName(loginer.getPassport().getName());
			}
			
			// 设置日志相关属性
			log.setDate(new Date());
			log.setEventCode(null);// 嵌入生成的日志没有行为代码
			log.setEventDscription(eventDescription);
			log.setIp(RequestIP.getRequestIp(request));
			log.setIsStatistic(0);// 嵌入生成的日志不纳入统计的范畴
			
			dao.add(log);// 添加日志
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
		List<Log> re = dao.query("select l from Log l left join fetch l.account a where l.id = :logId", map);// 查询指定的日志信息
		return (re.isEmpty() ? null : re.get(0));
	}

	/**
	 * 删除日志
	 * @param logIds日志ID集合
	 */
	public void deleteLog(List<String> logIds) {
		for (String entityId : logIds){
			dao.delete(Log.class, entityId);// 根据ID删除日志，删除日志不会对其它任何数据造成影响
		}
	}

	/**
	 * 统计特定类型的账号或单个账号，在指定时间范围内的系统访问量。
	 * @param startDate开始时间
	 * @param endDate结束时间
	 * @param type账号类型
	 * @param accountName账号名称
	 * @return number访问次数
	 */
	private Long basicStatistic(Date startDate, Date endDate, int type, AccountType accountType, String accountName) {
		Long number = 0L;// 访问次数
		
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("eventCode", LogProperty.LOGIN);
		
		if (type == -1) {// 统计所有类型账号访问系统的次数
			hql.append("select count(log.id) from Log log where log.eventCode = :eventCode and log.isStatistic = 1 ");
		} else if (type == 0) {// 统计指定的单个账号访问系统的次数
			hql.append("select count(log.id) from Log log left join log.passport p where log.eventCode = :eventCode and log.isStatistic = 1 and p.name = :accountName ");
			map.put("accountName", accountName);
		} else {// 统计其它某类型的账号访问系统的次数
			if (type == 4) {// 校级账号包括部属高校、地方高校，统计查询条件需手动指定
				hql.append("select count(log.id) from Log log left join log.account a where log.eventCode = :eventCode and log.isStatistic = 1 and (a.type = 'MINISTRY_UNIVERSITY' or a.type = 'LOCAL_UNIVERSITY')");
			} else {// 其它账号，统计查询条件通过参数传递
				hql.append("select count(log.id) from Log log left join log.account a where log.eventCode = :eventCode and log.isStatistic = 1 and a.type = '" + accountType +"' ");
				map.put("accountType", accountType);
			}
		}
		
		if (startDate != null) {// 设置统计开始时间
			hql.append(" and log.date >= :startDate ");
			map.put("startDate", startDate);
		}
		if (endDate != null) {// 设置统计结束时间
			hql.append(" and log.date <= :endDate ");
			map.put("endDate", endDate);
		}
		number = ((Number) dao.query(hql.toString(), map).get(0)).longValue();// 统计访问次数
		
		return number;
	}
	
	/**
	 * 统计特定类型的账号或单个账号，在指定时间范围内的模块访问量。
	 * @param startDate开始时间
	 * @param endDate结束时间
	 * @param type账号类型
	 * @param accountName账号名称
	 * @return number访问次数
	 */
	private Long[] moduleStatistic(Date startDate, Date endDate, int type, AccountType accountType, String accountName) {
		Long[] number = {0L,0L,0L,0L,0L,0L,0L};// 访问次数，分别对应系统功能模块、安全认证模块、人员模块、机构模块、项目模块、成果模块、奖励模块
		
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		
		if (type == -1) {// 统计所有类型账号访问系统各模块的次数
			hql.append("select count(log.id) from Log log where log.eventCode like :keyword and log.isStatistic = 1 ");
		} else if (type == 0) {// 统计指定的单个账号访问系统各模块的次数
			hql.append("select count(log.id) from Log log left join log.passport p where log.eventCode like :keyword and log.isStatistic = 1 and p.name = :accountName ");
			map.put("accountName", accountName);
		} else {// 统计其它某类型的账号访问系统各模块的次数
			if (type == 4) {// 校级账号包括部属高校、地方高校，统计查询条件需手动指定
				hql.append("select count(log.id) from Log log left join log.account a where log.eventCode like :keyword and log.isStatistic = 1 and (a.type = 'MINISTRY_UNIVERSITY' or a.type = 'LOCAL_UNIVERSITY') ");
			} else {// 其它账号，统计查询条件通过参数传递
				hql.append("select count(log.id) from Log log left join log.account a where log.eventCode like :keyword and log.isStatistic = 1 and a.type = '"+accountType+"' ");
				map.put("accountType", accountType);
			}
		}
		
		if (startDate != null) {// 设置统计开始时间
			hql.append(" and log.date >= :startDate ");
			map.put("startDate", startDate);
		}
		if (endDate != null) {// 设置统计结束时间
			hql.append(" and log.date <= :endDate ");
			map.put("endDate", endDate);
		}
			map.remove("keyword");
			map.put("keyword", LogProperty.SYSTEM + "%");
			number[0] = ((Number) dao.query(hql.toString(), map).get(0)).longValue();// 统计系统功能模块访问次数
			
			map.remove("keyword");
			map.put("keyword", LogProperty.SECURITY + "%");
			number[1] = ((Number) dao.query(hql.toString(), map).get(0)).longValue();// 统计安全认证模块访问次数
			
			map.remove("keyword");
			map.put("keyword", LogProperty.PERSON + "%");
			number[2] = ((Number) dao.query(hql.toString(), map).get(0)).longValue();// 统计人员模块访问次数
			
			map.remove("keyword");
			map.put("keyword", LogProperty.UNIT + "%");
			number[3] = ((Number) dao.query(hql.toString(), map).get(0)).longValue();// 统计机构模块访问次数
			
			map.remove("keyword");
			map.put("keyword", LogProperty.PROJECT + "%");
			number[4] = ((Number) dao.query(hql.toString(), map).get(0)).longValue();// 统计项目模块访问次数
			
			map.remove("keyword");
			map.put("keyword", LogProperty.PRODUCT + "%");
			number[5] = ((Number) dao.query(hql.toString(), map).get(0)).longValue();// 统计成果模块访问次数
			
			map.remove("keyword");
			map.put("keyword", LogProperty.AWARD + "%");
			number[6] = ((Number) dao.query(hql.toString(), map).get(0)).longValue();// 统计奖励模块访问次数
			
			return number;
	}
	
	/**
	 * 统计访问信息
	 * @param startDate开始时间
	 * @param endDate结束时间
	 * @param type账号类别(-1-所有账号, 0-单个账号，1-系统管理员账号，2-部级账号，3-省级账号，4-校级账号，6-院系账号，7-基地账号，8-专家账号，9-教师账号，10-学生账号)
	 * @param accountName账号名称(type=0时有效)
	 * @return number访问次数，各个模块访问次数的集合
	 */
	public Map statistic(Date startDate, Date endDate, int type, AccountType accountType, String accountName) {
		Map map = new HashMap();
		
		Long number = basicStatistic(startDate, endDate, type, accountType, accountName);// 统计系统访问次数
		
		Long[] numbers = moduleStatistic(startDate, endDate, type, accountType, accountName);// 统计各模块访问次数
		
		// 将统计结果存入map对象
		map.put("serverCount", number);
		map.put("systemCount", numbers[0]);
		map.put("securityCount", numbers[1]);
		map.put("personCount", numbers[2]);
		map.put("unitCount", numbers[3]);
		map.put("projectCount", numbers[4]);
		map.put("productCount", numbers[5]);
		map.put("awardCount", numbers[6]);
		map.put("numbers", numbers);
		List lData = new ArrayList();
		lData.add(numbers[0]);
		lData.add(numbers[1]);
		lData.add(numbers[2]);
		lData.add(numbers[3]);
		lData.add(numbers[4]);
		lData.add(numbers[5]);
		lData.add(numbers[6]);
		map.put("lData", lData);
		
		return map;
	}

}
