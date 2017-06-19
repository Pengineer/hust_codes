package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.InstpFunding;
import csdc.bean.InstpGranted;
import csdc.dao.HibernateBaseDao;
import csdc.tool.execution.Execution;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 2012年基地项目的拨款数据之前导入有误，部级基地的拨款全部是：
 * 部级基地项目立项经费20万（10+10，教育部拨款10万，高校省厅配套10万），
 * 没有省厅资助一说
 * 
 * @author xuhan
 *
 */
@Component
public class Fix20120529 extends Execution {

	@Autowired
	private HibernateBaseDao dao;
	
	@Autowired
	private Tool tool;


	@Override
	protected void work() throws Throwable {
		List<InstpGranted> granteds = dao.query("from InstpGranted granted where granted.number like '12%' and granted.approveFeeSubjection > 0.01 ");
		
		for (int i = 0; i < granteds.size(); i++) {
			InstpGranted granted = granteds.get(i);
			System.out.println(i + " / " + granteds.size() + " " + granted.getName());
			
			granted.setApproveFeeMinistry(granted.getApproveFeeSubjection());
			granted.setApproveFeeSubjection(null);
			
			if (granted.getApproveFeeMinistry() != null && granted.getApproveFeeMinistry() > 0.01) {
				InstpFunding funding = new InstpFunding();
				funding.setFee(granted.getApproveFeeMinistry());
				funding.setDate(tool.getDate(2012, 4, 1));
				granted.addFunding(funding);
			}
		}
	}

}
