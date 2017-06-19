package csdc.tool.execution.finder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.Institute;
import csdc.dao.IHibernateBaseDao;
import csdc.dao.SystemOptionDao;
import csdc.tool.StringTool;
import csdc.tool.execution.importer.InstituteNameChangesImporter;

/**
 * 基地查找辅助类
 * @author xuhan
 *
 */
@Component
public class InstituteFinder {
	
	/**
	 * [学校代码+基地名称]到[基地ID]的映射
	 */
	private Map<String, String> instMap;
	
	/**
	 * 基地名称变更
	 */
	public Map<String, String> instNameChange;


	@Autowired
	private IHibernateBaseDao dao;
	
	@Autowired
	private SystemOptionDao systemOptionDao;
	
	@Autowired
	private InstituteNameChangesImporter instituteNameChangesImporter;

	
	/**
	 * 根据[学校]和[基地名称]找到基地实体
	 * @param university
	 * @param instituteName
	 * @param addIfNotFound 如果不存在此基地，是否向库内添加一个
	 * @return
	 */
	public Institute getInstitute(Agency university, String instituteName, boolean addIfNotFound) {
		if (university.getType() != 3 && university.getType() != 4) {
			throw new RuntimeException("传入的agency[" + university.getName() + "]不是学校!");
		}
		if (instMap == null) {
			initInstMap();
		}
		Institute institute = null;
		instituteName = instituteName.replace(university.getName(), "");
		String key = university.getCode() + StringTool.fix(getNewestInstName(university, instituteName));
		String instituteId = instMap.get(key);
		if (instituteId != null) {
			institute = (Institute) dao.query(Institute.class, instituteId);
		}
		if (institute == null && addIfNotFound) {
			institute = new Institute();
			institute.setName(instituteName);
			institute.setSubjection(university);
			//这个要非空，先设为“其他研究基地”，有必要的话再修改
			institute.setType(systemOptionDao.query("researchAgencyType", "06"));
			
			instMap.put(key, dao.add(institute));
		}
		return institute;
	}
	
	/**
	 * 将基地名称替换成最新名称
	 * @param univCode
	 * @return
	 * @throws Exception 
	 */
	public String getNewestInstName(Agency university, String instName) {
		if (university.getType() != 3 && university.getType() != 4) {
			throw new RuntimeException("传入的agency[" + university.getName() + "]不是学校!");
		}
		if (instNameChange == null) {
			instituteNameChangesImporter.excute();
			instNameChange = instituteNameChangesImporter.getInstituteNameChangesMap();
		}
		String newestInstName = instNameChange.get(university.getCode() + StringTool.fix(instName));
		return newestInstName == null ? instName : newestInstName;
	}
	
	/**
	 * 初始化[学校代码+基地名称]到[机构ID]的映射
	 * @return
	 */
	private void initInstMap() {
		long beginTime = new Date().getTime();

		instMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select i.subjection.code, i.subjection.name, i.name, i.id from Institute i");
		for (Object[] str : list) {
			String univCode = (String) str[0];
			String univName = (String) str[1];
			String instName = (String) str[2];
			if (!instName.isEmpty()) {
				instMap.put(univCode + StringTool.fix(instName).replace(univName, ""), (String) str[3]);
			}
		}

		System.out.println("initInstMap complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
	}
 

}
