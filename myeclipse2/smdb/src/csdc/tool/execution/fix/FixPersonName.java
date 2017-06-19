package csdc.tool.execution.fix;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Person;
import csdc.dao.HibernateBaseDao;
import csdc.service.IPersonService;
import csdc.tool.execution.importer.Importer;
import csdc.tool.reader.ExcelReader;

/**
 * 修正规范人员姓名：如张三A，李四1，王 五，赵六(高师班)等，通过读取整理好的Excel文件来实现数据修正；
 * 涉及person表和人员关联的其他业务表（调用personService中的updatePersonName方法实现）。
 * @author fengcl
 *
 */
@SuppressWarnings("unchecked")
public class FixPersonName extends Importer {
	
	private ExcelReader reader;
	
	@Autowired
	private IPersonService personService;
	
	@Autowired
	private HibernateBaseDao dao;
	
	@Override
	public void work() throws Exception {
		importData();
	}

	/**
	 * 获取Excel数据
	 * @return
	 * @throws Exception
	 */
	public void importData() throws Exception {
		
		//读取人员Excel
		reader.readSheet(2);
		
		while(next(reader)){
			System.out.println(reader.getCurrentRowIndex() + "：" + B + " <- " + F);

			updateData();
			
			//刷新缓存
			if (reader.getCurrentRowIndex() % 100 == 0) {
				dao.flush();
				dao.clear();
			}
			
		}
	}
	
	/**
	 * 更新数据：
	 * A - 人员ID
	 * B - 修正后的姓名
	 * C - 英文名
	 * D - 国籍
	 * E - 民族
	 */
	@Transactional
	public void updateData(){
		if (A != null) {
			//1、修改人员表相关属性
			Person person = (Person) dao.query(Person.class, A);
			String oldName = person.getName();
			person.setName(B);						//修改中文名
			if (C != null && !"".equals(C.trim())) {
				person.setEnglishName(C.trim());	//修改英文名
			}
			if(D != null && !"".equals(D.trim())){
				person.setCountryRegion(D.trim());	//修改国籍
			}
			if(E != null && !"".equals(E.trim())){
				person.setEthnic(E.trim());			//修改民族
			}
			dao.modify(person);
			
			//2、更新其他表中对应的人员姓名
			if (!oldName.equals(B)) {
				personService.updatePersonName(A, B);
			}
		}
	}
	
	public FixPersonName() {
	}

	public FixPersonName(String filePath) {
		reader = new ExcelReader(filePath);
	}

}
