package csdc.tool.execution.fix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Academic;
import csdc.bean.Person;
import csdc.dao.HibernateBaseDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.Execution;

/**
 * 完善学术表：对学生也启用学术表
 * 1、如果在Academic表中已存在该学生的学术信息，则更新专业和研究方向；
 * 2、如果在Academic表中不存在该学生的学术信息，则新增一条Academic记录。
 * @author Batys
 *
 */
@Component
public class FixAcademic extends Execution {

	@Autowired
	private HibernateBaseDao dao;
	
	@Autowired
	private BeanFieldUtils beanFieldUtils;

	@Override
	protected void work() throws Throwable {
		updateAcademic();
	}
	
	/**
	 * 更新Academic表
	 * @throws Exception
	 */
	private void updateAcademic() throws Exception{ 
		// 获取Student表中人员在学术表存在学术信息的记录：personId -> Academic的映射
		Map<String, Academic> existingAcademics = new HashMap<String, Academic>();
		List<Object[]> acs = dao.query("select distinct st.person.id, ac from Student st, Academic ac where st.person.id = ac.person.id");
		for (Object[] obj : acs) {
			existingAcademics.put((String) obj[0], (Academic) obj[1]);
		}
		
		System.out.println("学生已存在学术记录：" + acs.size());
		
		// 查询专业或研究方向不为空的学生记录，将专业和学生信息转移至Academic表
		List<Object[]> stus = dao.query("select p, st.major, st.researchField from Student st left join st.person p where st.major is not null or st.researchField is not null");
		
		System.out.println("专业或研究方向不为空的学生记录：" + stus.size());
		int i = 0;
		for (Object[] obj : stus) {
			Person person = (Person) obj[0];
			String major = (String) obj[1];
			String researchField = (String) obj[2];
			Academic academic = null;
			if (existingAcademics.containsKey(person.getId())) { // 如果在Academic表中已存在该学生的学术信息，则更新专业和研究方向
				academic = existingAcademics.get(person.getId());
				beanFieldUtils.setField(academic, "major", major, BuiltinMergeStrategies.APPEND);
				beanFieldUtils.setField(academic, "researchField", researchField, BuiltinMergeStrategies.APPEND);
				dao.modify(academic);
			}else{	// 如果在Academic表中不存在该学生的学术信息，则新增一条Academic记录
				academic = new Academic();
				academic.setPerson(person);
				academic.setMajor(major);
				academic.setResearchField(researchField);
				dao.add(academic);
				existingAcademics.put(person.getId(), academic);
				i++;
			}
		}
		System.out.println("针对学生，新增的学术记录：" + i);
	}
}
