package csdc.tool.execution.finder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;
import csdc.tool.execution.importer.UniversityNameChangesImporter;

/**
 * 学校查找辅助类
 * @author xuhan
 *
 */
@Component
public class UniversityFinder {
	
	/**
	 * [学校代码]->[学校实体] union [学校名称]->[学校实体]
	 */
	public Map<String, Agency> univMap;
	
	/**
	 * 学校名称变更
	 */
	public Map<String, String> univNameChange;
	
	public Map<String, Agency> proMap;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Autowired
	private UniversityNameChangesImporter universityNameChangesImporter;
	
	
	
	/**
	 * 根据学校代码找到学校实体
	 * @param univCode
	 * @return
	 */
	public Agency getUnivByCode(String univCode) {
		if (univMap == null) {
			initUnivMap();
		}
		return univMap.get(univCode);
	}
	
	/**
	 * 根据学校名称找到学校实体
	 * @param univCode
	 * @return
	 * @throws Exception 
	 */
	public Agency getUnivByName(String univName) {
		if (univMap == null) {
			initUnivMap();
		}
		String newestUnivName = getNewestUnivName(univName);
		return univMap.get(StringTool.fix(newestUnivName));
	}
	
	public Agency getProByName(String proName) {
		if (proMap == null) {
			initUnivMap();
		}	
		Agency aa = proMap.get(proName);
		if (aa == null) {
			System.out.print(proName);
			
		}
//		System.out.print(aa.getAcronym());
		return proMap.get(proName);
		
		
	
	}
	
	/**
	 * 根据学校名称获取其最新名称
	 * @param univName 待判断的学校名称
	 * @return
	 * @throws Exception 
	 */
	public String getNewestUnivName(String univName) {
		if (univNameChange == null) {
			universityNameChangesImporter.excute();
			univNameChange = universityNameChangesImporter.getUniversityNameChangesMap();
		}
		String newestUnivName = univNameChange.get(StringTool.fix(univName));
		return newestUnivName == null ? univName : newestUnivName;
	}
	
	/**
	 * 寻找最长子串，使得是某个学校的名字。返回该学校。
	 * @param agencyName
	 * @return
	 * @throws Exception
	 */
	public Agency getUniversityWithLongestName(String agencyName) throws Exception {
		if (agencyName == null) {
			return null;
		}
		if (agencyName.equals("国际关系学院")) {
			Agency agency = getUnivByName(agencyName);
			if (agency != null) {
				return agency;
			}
		}
		for (int len = agencyName.length(); len >= 1; len--) {
			for (int i = 0; i + len <= agencyName.length(); i++) {
				String subName = agencyName.substring(i, i + len);
				if (!subName.equals("国际关系学院") && !subName.equals("国际关系学院/")) {
					Agency agency = getUnivByName(subName);
					if (agency != null) {
						return agency;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 初始化univMap
	 * @return
	 */
	private void initUnivMap() {
		long beginTime  = new Date().getTime();

		univMap = new HashMap<String, Agency>();
		proMap = new HashMap<String, Agency>();
		List<Agency> universityList = dao.query("select agency from Agency agency where agency.name not like '[%' and (agency.type = 3 or agency.type = 4)");
		List<Agency> provinceList = dao.query("select agency from Agency agency where agency.name not like '[%' and agency.type = 2");
		for (Agency agency : universityList) {
			univMap.put(agency.getCode(), agency);
			univMap.put(StringTool.fix(agency.getName()), agency);
			
		}
		
		for (Agency agency : provinceList) {
			proMap.put(agency.getName(), agency);
			
		}
		
		System.out.println("initUnivMap complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
	}

}
