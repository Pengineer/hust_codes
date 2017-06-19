package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.InstpApplication;
import csdc.bean.Officer;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 修正基地项目的申报表的FinalAudit**等字段
 * @author xuhan
 *
 */
public class Fix20120207 extends Execution {

	@Autowired
	private IHibernateBaseDao dao;

	@Autowired
	private Tool tool;

	@Override
	protected void work() throws Throwable {
		Officer	白晓 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '白晓'");
		
		List<InstpApplication> bas = dao.query("from InstpApplication ba left join fetch ba.instpGranted");
		for (int i = 0; i < bas.size(); i++) {
			if (i % 1000 == 0) {
				System.out.println(i);
			}

			InstpApplication ba = bas.get(i);
			
			ba.setFinalAuditor(白晓.getPerson());
			ba.setFinalAuditorName(白晓.getPerson().getName());
			ba.setFinalAuditorAgency(白晓.getAgency());
			ba.setFinalAuditorInst(白晓.getInstitute());
			ba.setApplicantSubmitDate(tool.getDate(ba.getYear(), 3, 1));
			ba.setFinalAuditDate(tool.getDate(ba.getYear(), 6, 1));
			if (!ba.getInstpGranted().isEmpty()) {
				ba.setFinalAuditResult(2);
				ba.getInstpGranted().iterator().next().setApproveDate(ba.getFinalAuditDate());
			} else {
				ba.setFinalAuditResult(1);
			}
			ba.setFinalAuditStatus(3);
		}
		
	}

}
