package csdc.service.imp;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

import csdc.service.IGeneralService;
@Transactional
public class GeneralService extends ProjectService implements IGeneralService  {
	
	/**
	 * 根据personid获得研究人员所负责的项目信息列表
	 * @param projectid 项目立项id
	 * @param personid 研究人 id
	 * @return 项目信息列表
	 */
	@SuppressWarnings("unchecked")
	public List getDireProjectListByGrantedId(String projectid, String personid){
		if(projectid == null || personid == null){
			return new ArrayList();
		}
		Map map1 = new HashMap();
		map1.put("projectid", projectid);
		map1.put("personId",personid);
		String hql1 = "select gra.name, app.englishName, app.year, gra.applicantName, " +
				"gra.university.name, gra.divisionName from GeneralGranted gra, " +
				"GeneralApplication app ,GeneralMember mem where " +
				"gra.application.id=app.id and mem.application.id=app.id and gra.id=:projectid and " +
				"mem.isDirector=1 and mem.member.id=:personId";
		List projectList = dao.query(hql1, map1);
	
		return projectList;
	}
	
	/**
	 * 根据projectid获得研究人员所负责的项目信息列表
	 * @param projectid 项目立项id
	 * @return 项目信息列表
	 */
	@SuppressWarnings("unchecked")
	public List getDireProjectListByGrantedId(String projectid){
		if(projectid == null){
			return new ArrayList();
		}
		Map map1 = new HashMap();
		map1.put("projectid", projectid);
		String hql1 = "select gra.name, app.englishName, app.year, gra.applicantName, " +
				"gra.university.name, gra.divisionName from GeneralGranted gra, GeneralApplication app " +
				"where gra.application.id=app.id and gra.id=:projectid ";
		List projectList = dao.query(hql1, map1);
	
		return projectList;
	}
	/**
	 * 计算比例（率）
	 * @param dividend	被除数
	 * @param divider	除数
	 * @return	百分数
	 */
	public Object calculate(Object dividend, Object divider){
		Number per = 0;
		DecimalFormat df=(DecimalFormat)NumberFormat.getPercentInstance();
		df.applyPattern("#0.000000000");
		
		if (null != divider && null != dividend) {
			double _dividend = Double.parseDouble(String.valueOf(dividend));
			double _divider = Double.parseDouble(String.valueOf(divider));
			if(_divider != 0){
				double result = _dividend / _divider;
				String percentResult = df.format(result);
				try {
					per = df.parse(percentResult);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else {
				try {
					per = df.parse("1");
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return per;
	}
	
	/**
	 * 获取每一届高校各类奖项的获奖分值和各个高校的ID
	 * @param session	获奖届次
	 * @param awardType	奖励类别
	 * @param grade	获奖等级
	 * @param 各个权值 0-12位分别为：
	 * @param 著作奖 一、二、三等奖的权值  bookAwardFir、bookAwardSec、bookAwardThi
	 * @param 论文奖 一、二、三等奖的权值 paperAwardFir、paperAwardSec、paperAwardThi
	 * @param 研究报告奖（出版）一、二、三等奖的权值  ResPubAwardFir、ResPubAwardSec、ResPubAwardThi
	 * @param 研究报告奖（采纳/批示）一、二、三等奖的权值  ResAdoAwardFir、 ResAdoAwardSec、 ResAdoAwardThi
	 * @param 成果普及奖 achPopuAward
	 * @return	获奖得分和高校ID组成的MAP
	 */
	public Map getAwardScore(Integer session,String awardType,String grade, Double weight){
		Map map = new HashMap();
		Double score = null;//分数
		map.put("session", session);
		map.put("awardType", awardType);
		if (grade != null) {
			map.put("grade", grade);
			String awardScore = "select ap.university.id, count(ap.id) from AwardGranted a left join a.application ap where a.session = :session " +
					"and ap.subType.id = :awardType and a.grade.id = :grade group by ap.university.id ";
			List<Object[]> awardScoreList = dao.query(awardScore, map);
			map.clear();
			for (Object[] objects : awardScoreList) {
				objects[1] = (Double) weight*((Long)objects[1]);
				map.put(objects[0], objects[1]);
			}
		} else {
			String awardScore = "select ap.university.id, count(ap.id) from AwardGranted a left join a.application ap where a.session = :session " +
					"and ap.subType.id = :awardType group by ap.university.id ";
			List<Object[]> awardScoreList = dao.query(awardScore, map);
			map.clear();
			for (Object[] objects : awardScoreList) {
				objects[1] = (Double) weight*((Long)objects[1]);
				map.put(objects[0], objects[1]);
			}
		}
		return map;
	}
	
	
	/**
	 * 根据系统选项表的code和standard来找出对应的id
	 * @param code
	 * @param standard
	 * @return id
	 */
	public String getSystemId(String code , String standard){
		Map map = new HashMap();
		map.put("code", code);
		map.put("standard", standard);
		return (String) dao.query("select s.id from SystemOption s where s.code = :code and s.standard = :standard ",map).get(0);
	}
}