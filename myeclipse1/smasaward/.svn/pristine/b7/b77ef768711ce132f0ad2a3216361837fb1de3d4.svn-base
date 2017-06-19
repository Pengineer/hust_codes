package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Expert;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;

/**
 * 修复专家学位信息
 * @author xuhan
 *
 */
@Component
public class FixExpertDegree extends Execution {
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Override
	public void work() throws Throwable {
		List<Expert> experts = dao.query("from Expert");
		for (Expert expert : experts) {
			String degree = expert.getDegree();
			if (degree == null) {
				//TODO trcnkq
			} else if (degree.contains("博") || degree.contains("薄")) {
				expert.setDegree("博士 ");
			} else if (degree.contains("硕")) {
				expert.setDegree("硕士");
			} else if (degree.contains("学士") || degree.contains("大本") || degree.contains("大普") || degree.contains("本科")) {
				expert.setDegree("学士");
			}
		}
	}
	
}
