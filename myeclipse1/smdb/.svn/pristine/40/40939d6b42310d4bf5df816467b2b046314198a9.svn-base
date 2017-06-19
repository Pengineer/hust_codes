package csdc.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import csdc.bean.Applicant;
import csdc.bean.Job;

public interface IPortalService {

	/**
	 * 查询职位列表
	 * @return 职位列表信息
	 */
	public List<String[]> searchJob();
	
	/**
	 * 查看职位详情
	 * @param jobId
	 * @return 此职位对象
	 */
	public Job viewJob(String jobId);
	
	/**
	 * 初始化数据填充
	 * @param a 存值的map
	 * @param b 申请记录
	 * @return
	 */
	public Map initInfo(Map a, Applicant b);
	
	/**
	 * 处理各种详情数据
	 * @param a 存值的map
	 * @param b 申请记录
	 * @return
	 * @throws ParseException
	 */
	public Map initAppDate(Map a, Applicant b) throws ParseException;
	
	/**
	 * 获取文件大小
	 * @param fileLength
	 * @return 文件大小字符串
	 */
	public String accquireFileSize(long fileLength);
}
