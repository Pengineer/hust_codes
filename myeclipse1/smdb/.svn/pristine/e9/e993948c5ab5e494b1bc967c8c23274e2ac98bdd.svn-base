package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Academic;
import csdc.dao.IHibernateBaseDao;
import csdc.dao.SystemOptionDao;

/**
 * 规范Academic表中的SpecialityTitle字段：title -> code/title
 * @author pengliang
 *
 * 备注：如果原title在系统选项表中存在，则进行规范化，否则跳过处理。
 */

@Component
public class RegulateAcademicTitle extends Importer{
	@Autowired
	private SystemOptionDao systemOptionDao;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	private List<Academic> academics;
	
	/**
	 * 职称名  -> 代码/职称名
	 */
	private Map<String, String> nameToCodeNameMap;
	
	public void initAcademic(){
		Date begin = new Date();
		academics = dao.query("from Academic where C_SPECIALITY_TITLE is not null");
		System.out.println("initAcademic complete! Use time:" + (new Date().getTime() - begin.getTime()) + "ms" );
	}

	public void initSystemOption(){
		Date begin = new Date();		
		nameToCodeNameMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select so.name, so.code from SystemOption so where so.standard='GBT8561-2001'");
		for (Object[] o : list) {
			String name = (String) o[0];
			String code = (String) o[1];			
			nameToCodeNameMap.put(name, code + "/" + name);
		}		
		System.out.println("initSystemOption complete! Use time:" + (new Date().getTime() - begin.getTime()) + "ms" );
	}
	
	@Override
	protected void work() throws Throwable {
		initAcademic();
		initSystemOption();
		
		for(Academic academic : academics){
			String title = academic.getSpecialityTitle();
			if(title != null && !title.contains("/")){
				String codeName = nameToCodeNameMap.get(title);
				if(codeName != null){
					academic.setSpecialityTitle(codeName);
				}
			}
		}
	}
	
}
