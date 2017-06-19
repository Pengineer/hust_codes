package csdc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import csdc.bean.Log;
import csdc.tool.bean.AccountType;

/**
 * 系统日志接口
 * @author 龚凡
 * @version 2011.04.11
 */
@SuppressWarnings("unchecked")
public interface ILogService extends IBaseService {

	/**
	 * 对外提供的侵入式日志记录
	 * @param request请求的request对象，记录日志时需要从中获取信息
	 * @param eventDescription事件描述
	 */
	public void addLog(HttpServletRequest request, String eventDescription);

	/**
	 * 查看日志详情
	 * @param logId日志ID
	 * @return 日志对象
	 */
	public Log viewLog(String logId);

	/**
	 * 删除日志
	 * @param logIds日志ID集合
	 */
	public void deleteLog(List<String> logIds);

	/**
	 * 统计访问信息
	 * @param startDate开始时间
	 * @param endDate结束时间
	 * @param type账号类别(0-单个账号，1-系统管理员账号，2-部级账号，3-省级账号，4-校级账号，5-院系账号，6-基地账号，7-专家账号，8-教师账号，9-学生账号)
	 * @param accountName账号名称(type=0时有效)
	 * @return number访问次数，各个模块访问次数的集合
	 */
	public Map statistic(Date startDate, Date endDate, int type, AccountType accountType, String accountName);

}
