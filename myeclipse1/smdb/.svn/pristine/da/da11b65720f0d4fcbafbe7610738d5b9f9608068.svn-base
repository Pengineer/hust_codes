package csdc.tool.execution.fix;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.InstpEndinspection;
import csdc.bean.PostEndinspection;
import csdc.bean.SystemOption;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;

/**
 * 基地项目：
 * endInspection.reviewGrade in (优秀、合格)的, endInspection.reviewResult=2 endInspection.reviewStatus=3
 * endInspection.reviewGrade==不合格的, endInspection.reviewResult=1,endInspection.reviewStatus=3
 * 删掉“鉴定材料收到时间”列
 * 删掉 reviewGrade 为空的 endInspection行
 * granted.status==2的，granted.endStopWithdrawDate=endInspection.FinalAuditDate,granted.endStopWithdrawPerson=endInspection.FinalAuditPerson
 * 
 * 后期资助项目：
 * endInspection.reviewStatus=3
 * endInspection.reviewResult=2
 * endInspection.reviewWay=1
 * endInspection.reviewGrade=合格
 * endInspection.reviewDate为空的设为endInspection.finalAuditDate
 * endInspection.finalAuditResultEnd==0的，清空endInspection.finalAudit****
 * granted.status==5的，granted.EndStopWithdrawDate=endInspection.reviewDate
 *
 * @author xuhan
 *
 */
public class Fix20120215 extends Execution {

	@Autowired
	private IHibernateBaseDao dao;

	@Override
	protected void work() throws Throwable {
		修正基地项目();
		修正后期资助项目();
	}


	private void 修正基地项目() {
		SystemOption 不合格 = (SystemOption) dao.query(SystemOption.class, "buhege");
		
		dao.execute("delete from InstpEndinspection ei where ei.reviewGrade is null");
		
		List<InstpEndinspection> eis = dao.query("from InstpEndinspection");
		for (int i = 0; i < eis.size(); i++) {
			if (i % 1000 == 0) {
				System.out.println(i);
			}
			InstpEndinspection ei = eis.get(i);

			if (ei.getStatus() == 0) {
				ei.setIsImported(1);
				ei.setImportedDate(new Date());
			}
			
			if (ei.getFinalAuditResultEnd() == 2) {
				ei.setReviewResult(2);
				ei.setReviewStatus(3);
			} else if (ei.getFinalAuditResultEnd() == 1) {
				ei.setReviewResult(1);
				ei.setReviewStatus(3);
				ei.setReviewGrade(不合格);
			}
			
			if (ei.getGranted().getStatus() == 2) {
				ei.getGranted().setEndStopWithdrawDate(ei.getFinalAuditDate());
				ei.getGranted().setEndStopWithdrawPerson(ei.getFinalAuditor().getName());
			}
		}
	}
	
	private void 修正后期资助项目() {
		SystemOption 合格 = (SystemOption) dao.query(SystemOption.class, "hege");
		合格.getId();
		
		List<PostEndinspection> eis = dao.query("from PostEndinspection");
		for (PostEndinspection ei : eis) {
			if (ei.getStatus() == 0) {
				ei.setIsImported(1);
				ei.setImportedDate(new Date());
			}
			
			ei.setReviewStatus(3);
			ei.setReviewResult(2);
			ei.setReviewWay(1);
			ei.setReviewGrade(合格);
			if (ei.getReviewDate() == null) {
				ei.setReviewDate(ei.getFinalAuditDate());
			}
			if (ei.getFinalAuditResultEnd() == 0) {
				ei.setFinalAuditStatus(0);
				ei.setFinalAuditor(null);
				ei.setFinalAuditorName(null);
				ei.setFinalAuditorAgency(null);
			}
			if (ei.getGranted().getStatus() == 5) {
				ei.getGranted().setEndStopWithdrawDate(ei.getReviewDate());
			}
		}
	}


}
