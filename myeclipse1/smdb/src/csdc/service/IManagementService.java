package csdc.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import csdc.bean.Applicant;

public interface IManagementService {
	
	/**
	 * 通过id获取app对象
	 * @param id
	 * @return
	 */
	public Applicant getAppById(String id);
	
	/**
	 * 处理各种详情数据
	 * @param a 存值的map
	 * @param b 申请记录
	 * @return
	 * @throws ParseException
	 */
	public Map initAppDate(Map a, Applicant b) throws ParseException;
	
	/**
	 * 初始化模板列表
	 * @return
	 */
	public List<String[]> listTemplate();

}
