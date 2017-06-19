package csdc.tool.execution.fix;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.Product;
import csdc.bean.Teacher;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;

/**
 * 修复Product的author和university
 * @author xuhan
 *
 */
@Component
public class FixProductAuthorAndUniversity extends Execution {

	@Autowired
	private UnivPersonFinder univPersonFinder;

	@Autowired
	private UniversityFinder universityFinder;

	@Autowired
	private IHibernateBaseDao dao;

	@Override
	protected void work() throws Throwable {
		Set<String> invalidUnivNames = new HashSet<String>();
		
		List<Product> products = dao.query("from Product");
		for (Product product : products) {
			Agency univ = universityFinder.getUnivByName(product.getAgencyName());
			if (univ == null) {
				invalidUnivNames.add(product.getAgencyName());
				continue;
			}
			
			Teacher author = univPersonFinder.findTeacher(product.getAuthorName(), univ);
			product.setAuthor(author.getPerson());
			product.setUniversity(univ);
			product.setDepartment(author.getDepartment());
			product.setInstitute(author.getInstitute());
		}
		
		if (invalidUnivNames.size() > 0) {
			System.out.println(invalidUnivNames.toString().replaceAll("\\s*,\\s*", "\n"));
			throw new RuntimeException();
		}
		
	}

}
