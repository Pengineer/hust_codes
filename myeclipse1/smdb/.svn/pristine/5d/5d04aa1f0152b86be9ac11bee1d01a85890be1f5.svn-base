package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Academic;
import csdc.dao.HibernateBaseDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.Execution;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 目前数据库中有部分person有多条academic信息，此类合并之
 * @author Isun
 *
 */
@Component
public class MergeAcademic extends Execution {
	
	@Autowired
	private HibernateBaseDao dao;

	@Autowired
	private BeanFieldUtils beanFieldUtils;

	@Autowired
	private Tool tool;

	@Override
	protected void work() throws Throwable {
		List<String> personIds = dao.query("select p.id from Person p join p.academic ac group by p.id having count(*) > 1");
		
		for (String personId : personIds) {
			System.out.println(personId);
			List<Academic> academics = dao.query("select ac from Academic ac where ac.person.id = ? order by ac.id", personId);
			for (int i = 1; i < academics.size(); i++) {
				academics.get(0).setDiscipline(tool.transformDisc(academics.get(0).getDiscipline() + " " + academics.get(i).getDiscipline()));
				academics.get(0).setRelativeDiscipline(tool.transformDisc(academics.get(0).getRelativeDiscipline() + " " + academics.get(i).getRelativeDiscipline()));
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "ethnicLanguage", BuiltinMergeStrategies.APPEND);
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "language", BuiltinMergeStrategies.APPEND);
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "disciplineType", BuiltinMergeStrategies.APPEND);
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "researchField", BuiltinMergeStrategies.APPEND);
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "major", BuiltinMergeStrategies.APPEND);
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "researchSpeciality", BuiltinMergeStrategies.APPEND);
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "furtherEducation", BuiltinMergeStrategies.APPEND);
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "parttimeJob", BuiltinMergeStrategies.APPEND);
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "specialityTitle", BuiltinMergeStrategies.APPEND);
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "positionLevel", BuiltinMergeStrategies.APPEND);
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "tutorType", BuiltinMergeStrategies.APPEND);
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "postdoctor", BuiltinMergeStrategies.LARGER);
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "talent", BuiltinMergeStrategies.APPEND);
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "lastEducation", BuiltinMergeStrategies.APPEND);
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "lastDegree", BuiltinMergeStrategies.APPEND);
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "countryRegion", BuiltinMergeStrategies.APPEND);
				beanFieldUtils.mergeField(academics.get(0), academics.get(i), "degreeDate", BuiltinMergeStrategies.PRECISE_DATE);
				dao.delete(academics.get(i));
			}
		}
	}
	
}
