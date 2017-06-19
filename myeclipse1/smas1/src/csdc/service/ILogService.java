package csdc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import csdc.bean.Log;

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

}
