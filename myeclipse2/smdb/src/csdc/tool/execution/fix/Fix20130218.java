package csdc.tool.execution.fix;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.GeneralEndinspection;
import csdc.bean.GeneralGranted;
import csdc.dao.HibernateBaseDao;
import csdc.tool.execution.Execution;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 将一般项目2013年1月18日之前未结项的对应数据(在研数据和2013年1月18日24点以后结项的数据)查重标记C_IS_DUP_CHECK_GENERAL由0改为1。 
 * 更新：将2013年1月18日以后结项的数据查重标记C_IS_DUP_CHECK_GENERAL由1改为0。
 * @author wangyi
 * 
 */
@Component
public class Fix20130218 extends Execution {

	@Autowired
	private HibernateBaseDao dao;
	
	@Autowired
	private Tool tool;

	@Override
	public void work() throws Throwable {
//		List<GeneralGranted> granteds1 = dao.query("from GeneralGranted granted where granted.status in (1, 2) ");//在研数据和2013年1月18日之后结项数据
//		
//		for (int i = 0; i < granteds1.size(); i++) {
//			GeneralGranted granted = granteds1.get(i);
//			if (granted.getStatus() == 1) {
//				granted.setIsDupCheckGeneral(1);
//			} else {
//				Set<GeneralEndinspection> endinspections = granted.getEndinspection();
//				for (GeneralEndinspection endinspection: endinspections) { 
//					if(endinspection.getFinalAuditResultEnd() == 2 && endinspection.getFinalAuditDate().after(tool.getDate(2013, 1, 19))) {
//						granted.setIsDupCheckGeneral(1);
//					}
//				}
//			}
//		}
		
		List<GeneralGranted> granteds2 = dao.query("from GeneralGranted granted where granted.status = 2 ");//2013年1月18日之后结项数据
		
		for (int i = 0; i < granteds2.size(); i++) {
			GeneralGranted granted = granteds2.get(i);
			Set<GeneralEndinspection> endinspections = granted.getEndinspection();
			for (GeneralEndinspection endinspection: endinspections) { 
				if(endinspection.getFinalAuditResultEnd() == 2 && endinspection.getFinalAuditDate().after(tool.getDate(2013, 1, 19))) {
					granted.setIsDupCheckGeneral(0);
				}
			}
		}
	}

}
