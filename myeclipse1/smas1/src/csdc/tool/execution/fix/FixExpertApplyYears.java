package csdc.tool.execution.fix;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Expert;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;
import csdc.tool.execution.Execution;

public class FixExpertApplyYears extends Execution {
	
	@Autowired
	private IHibernateBaseDao dao;
	
	/**
	 * 高校名称 -> 高校代码
	 */
	private Map<String, String> univNameCodeMap;
	
	private int year = 2013;
	
	@Override
	public void work() throws Throwable {
		
		initExpertMap();
		
		univNameCodeMap = (Map<String, String>) ActionContext.getContext().getApplication().get("univNameCodeMap");
		
		HashSet<String> directors = new HashSet<String>();
		List<Object[]> list = dao.query("select p.directorUniversity, p.director from ProjectApplication p where p.type = 'instp' and p.year = ? ", year);
		
		for (Object[] obj : list) {
			String[] keys = getKeys((String)obj[0], (String)obj[1], true);//[高校代码+项目负责人]
			for (String key : keys) {
				directors.add(key);
			}
		}
		
		for (String key : directors) {
			Expert expert = expertMap.get(key);
			if (null != expert) {
				expert.setInstpApplyYears(year+"");
			}
		}
	}
	
	/**
	 * 根据项目负责人所属学校和项目负责人获取每个负责人的[高校代码+项目负责人]组合
	 * @param directorUniversity	项目负责人所属学校
	 * @param director				项目负责人
	 * @return
	 */
	private String[] getKeys(String directorUniversity, String director, boolean needPrint) {
		String[] universities = directorUniversity.split("[\\s;,、；]+");
		String[] directors = director.split("[\\s;,、；]+");
		int univCnt = universities.length;
		int directorCnt = directors.length;
		//根据名称获取高校代码
		String[] univCodes = new String[directorCnt];
		for (int i = 0; i < univCnt; i++) {
			String code = univNameCodeMap.get(universities[i]);
			if (null == code) {
				if (needPrint) {
					System.out.println(directors[i] + universities[i]);
				}
				continue; 
			}
			universities[i] = code;
			univCodes[i] = universities[i];
		}
		//补齐负责人所属学校代码数组
		for (int i = univCnt; i < directorCnt; i++) {
			univCodes[i] = universities[univCnt - 1];
		}
		//组合keys
		String[] keys = new String[directorCnt]; 
		for (int i = 0; i < directorCnt; i++) {
			keys[i] = univCodes[i] + StringTool.chineseCharacterFix(directors[i]);
		}
		
		return keys;
	}
	
	/**
	 * 学校编号+姓名 到 专家实体 的映射
	 */
	private Map<String, Expert> expertMap;
	
	/**
	 * 初始化expertMap
	 */
	private void initExpertMap() {
		expertMap = new HashMap<String, Expert>();
		
		List<Expert> experts = dao.query("select e from Expert e");
		for (Expert expert : experts) {
			expertMap.put(expert.getUniversityCode() + StringTool.chineseCharacterFix(expert.getName()), expert);
		}
	}
}
