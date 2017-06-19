package csdc.tool.execution.fix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Expert;
import csdc.dao.IHibernateBaseDao;
import csdc.dao.SqlBaseDao;
import csdc.tool.execution.Execution;

/**
 * 修复专家专业职称
 * 新的专业职称格式为："code" + "/" + "specialityTitle"
 * @author xuhan
 *
 */
@Component
public class FixExpertSpecialityTitleNew extends Execution {
	
	@Autowired
	private IHibernateBaseDao dao;
		
	@Override
	public void work() throws Throwable {
		String hql = "select so.code, so.name from SystemOption so where so.standard = 'GBT8561-2001' order by so.code asc";
		Map<String, String> specialityTitleMap = new HashMap<String, String>();
		List<Object[]> sos = dao.query(hql);		 
		for (Object[] so : sos) {
			if(!so[0].toString().endsWith("0")) {
				specialityTitleMap.put(so[1].toString(), so[0].toString() + "/" +so[1].toString()); 
			}
		}		
		
		List<Expert> experts = dao.query("select e from Expert e where e.specialityTitle is not null");
		for(Expert e:experts){
			String key = e.getSpecialityTitle();
			if (specialityTitleMap.containsKey(key)) {
				e.setSpecialityTitle(specialityTitleMap.get(key));	
			}else {
				System.out.println(key);
			}
		}
	}
}

