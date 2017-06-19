package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.GeneralApplication;
import csdc.bean.Officer;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 一般项目申请表的final审核字段修正；注意final审核时间应同步修正到立项信息标签中的批准时间（它们应该是一样的）。
 * 申请表中的final审核时间和立项表中的批准时间可参考各年度一般项目立项通知发布的时间确定。
 * @author xuhan
 *
 */
public class Fix20120112 extends Execution {

	@Autowired
	private IHibernateBaseDao dao;

	@Autowired
	private Tool tool;

	@Override
	protected void work() throws Throwable {
		Officer	白晓 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '白晓'");
		
		List<GeneralApplication> gas = dao.query("from GeneralApplication ga left join fetch ga.generalGranted");
		for (int i = 0; i < gas.size(); i++) {
			if (i % 1000 == 0) {
				System.out.println(i);
			}

			GeneralApplication ga = gas.get(i);
//			if (ga.getPlanEndDate() != null && (ga.getPlanEndDate().getYear() > 2020 - 1900 || ga.getPlanEndDate().getYear() < 1980 - 1900)) {
//				continue;
//			}
			
			ga.setFinalAuditor(白晓.getPerson());
			ga.setFinalAuditorName(白晓.getPerson().getName());
			ga.setFinalAuditorAgency(白晓.getAgency());
			ga.setFinalAuditorDept(白晓.getDepartment());
			ga.setFinalAuditorInst(白晓.getInstitute());
			ga.setApplicantSubmitDate(tool.getDate(ga.getYear(), 3, 1));
			ga.setFinalAuditDate(tool.getDate(ga.getYear(), 6, 1));
			if (!ga.getGeneralGranted().isEmpty()) {
				ga.setFinalAuditResult(2);
				ga.getGeneralGranted().iterator().next().setApproveDate(ga.getFinalAuditDate());
			} else {
				ga.setFinalAuditResult(1);
			}
			ga.setFinalAuditStatus(3);
		}
		
	}

}
