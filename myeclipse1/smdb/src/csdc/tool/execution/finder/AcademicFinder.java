package csdc.tool.execution.finder;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Academic;
import csdc.bean.Person;
import csdc.dao.IHibernateBaseDao;

@Component
public class AcademicFinder {
	
	@Autowired
	private IHibernateBaseDao dao;

	/**
	 * person.id -> academic.id
	 */
	private Map<Serializable, Serializable> map;
	
	/**
	 * 置空辅助类，释放部分内存
	 */
	public void reset() {
		map = null;
	}
	
	/**
	 * 找到Person的academic实体
	 * @param personName
	 * @param agencyName
	 * @return
	 */
	public Academic findAcademic(Person person) {
		if (map == null) {
			init();
		}
		
		Serializable academicId = map.get(person.getId());
		return (Academic) (academicId != null ? dao.query(Academic.class, academicId) : person.getAcademicEntity()); 
	}
	
	private void init() {
		Date begin = new Date();
		
		map = new HashMap<Serializable, Serializable>();
		List<Object[]> list = dao.query("select academic.person.id, academic.id from Academic academic");
		for (Object[] o : list) {
			map.put((Serializable)o[0], (Serializable) o[1]);
		}
		
		System.out.println("initAcademic complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
}
