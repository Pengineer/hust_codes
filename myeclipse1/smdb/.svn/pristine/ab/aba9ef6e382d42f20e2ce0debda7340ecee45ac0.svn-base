package csdc.service;

import java.util.List;
import java.util.Map;

import java_cup.internal_error;

public interface ISpecialService extends IProjectService  {
	
	/**
	 * 根据personid获得研究人员所负责的项目信息列表
	 * @param projectid 项目立项id
	 * @param personid 研究人 id
	 * @return 项目信息列表
	 */
	@SuppressWarnings("unchecked")
	public List getDireProjectListByGrantedId(String projectid, String personid);
	
	/**
	 * 根据projectid获得研究人员所负责的项目信息列表
	 * @param projectid 项目立项id
	 * @return 项目信息列表
	 */
	@SuppressWarnings("unchecked")
	public List getDireProjectListByGrantedId(String projectid);
	
	/**
	 * 计算比例（率）
	 * @param dividend	被除数
	 * @param divider	除数
	 * @return	百分数
	 */
	public Object calculate(Object dividend, Object divider);
	
	/**
	 * 获取每一届高校各类奖项的获奖总数
	 * @param session	获奖届次
	 * @param awardType	奖励类别
	 * @param grade	获奖等级
	 * @param 各个权值：
	 * @param 著作奖 一、二、三等奖的权值  bookAwardFir、bookAwardSec、bookAwardThi
	 * @param 论文奖 一、二、三等奖的权值 paperAwardFir、paperAwardSec、paperAwardThi
	 * @param 研究报告奖（出版）一、二、三等奖的权值  ResPubAwardFir、ResPubAwardSec、ResPubAwardThi
	 * @param 研究报告奖（采纳/批示）一、二、三等奖的权值  ResAdoAwardFir、 ResAdoAwardSec、 ResAdoAwardThi
	 * @param 成果普及奖 achPopuAward
	 * @return	获奖得分
	 */
	public Map getAwardScore(Integer session,String awardType,String grade, Double weight);
	
	
	/**
	 * 根据系统选项表的code和standard来找出对应的id
	 * @param code
	 * @param standard
	 * @return id
	 */
	public String getSystemId(String code , String standard);
}