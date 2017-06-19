package csdc.tool.execution.finder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Expert;
import csdc.bean.Person;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;


@Component
public class ExpertFinder {
	
	@Autowired
	private IHibernateBaseDao dao;

	/**
	 * [机构名称+姓名] -> [Expert.id]
	 */
	private Map<String, String> eMap;
	
	/**
	 * 置空辅助类，释放部分内存
	 */
	public void reset() {
		eMap = null;
	}
	
	/**
	 * 根据专家姓名和所在机构名称，返回一个Expert(PO)
	 * @param personName
	 * @param agencyName
	 * @return
	 */
	public Expert findExpert(String personName, String agencyName) {
		if (eMap == null) {
			init();
		}
		
		Expert expert = null;
		
		String key = StringTool.fix(agencyName + personName);
		String expertId = eMap.get(key);
		if (expertId != null) {
			expert = (Expert) dao.query(Expert.class, expertId);
		}
		if (expert == null) {
			Person person = new Person();
			person.setName(personName);
			
			expert = new Expert();
			expert.setPerson(person);
			expert.setAgencyName(agencyName);
			expert.setType("专职人员");

			dao.addOrModify(person);
			dao.addOrModify(expert);
			
			eMap.put(key, expert.getId());
		}
		return expert;
	}
	
	private void init() {
		Date begin = new Date();
		
		eMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select e.agencyName, e.person.name, e.id from Expert e");
		for (Object[] o : list) {
			String agencyName = StringTool.fix((String) o[0]);
			String personName = StringTool.fix((String) o[1]);
			String expertId = (String) o[2];
			
			eMap.put(agencyName + personName, expertId);
		}
		
		System.out.println("initExpert complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
}
