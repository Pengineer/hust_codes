package csdc.tool.widget;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Expert;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.importer.Tools;

/**
 * 把 四种联系方式(homePhone mobilePhone officePhone email)至少有一个相同&&高校相同&&姓名相同 的专家合并
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class ExpertMerger {
	
	@Autowired
	public IHibernateBaseDao dao;

	@Autowired
	private Tools tools;
	
	@Transactional
	public void start() {
		merge();
	}

	private void merge() {
		List<Expert> experts = dao.query("select e from Expert e order by e.universityCode asc, e.name asc, e.number asc");
		for (int i = 0; i < experts.size();) {
			System.out.println(i + " / " + experts.size());
			Expert expert = experts.get(i);
			int j = i + 1;
			while (j < experts.size() && experts.get(j).getName().equals(expert.getName()) && experts.get(j).getUniversityCode().equals(expert.getUniversityCode())) {
				++j;
			}
			//[i, j)内的专家满足姓名相同、高校相同
			for (int k = i; k < j; k++) {
				for (int l = k + 1; l < j; l++) {
					if (tools.sameExpert(experts.get(k), experts.get(l))) {
						tools.mergeExpert(experts.get(k), experts.get(l));
					}
				}
			}
			i = j;
		}
	}
}
