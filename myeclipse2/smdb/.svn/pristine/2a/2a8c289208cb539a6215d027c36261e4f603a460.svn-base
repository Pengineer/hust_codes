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
 * 修复2010、2011年基地项目的拨款数据
 * 部级基地项目立项经费20万（10+10，教育部拨款10万，高校省厅配套10万），
 * 省部共建基地项目立项经费10万（5+5，教育部拨款5万，高校省厅配套5万）；
 * 
 * @author xuhan
 *
 */
@Component
public class Fix20120604 extends Execution {

	@Autowired
	private HibernateBaseDao dao;
	
	@Autowired
	private Tool tool;


	@Override
	protected void work() throws Throwable {
		List<InstpGranted> granteds = dao.query("from InstpGranted granted where granted.application.year in (2010, 2011, 2012) ");
		
		for (int i = 0; i < granteds.size(); i++) {
			InstpGranted granted = granteds.get(i);
			System.out.println(i + " / " + granteds.size() + " " + granted.getName());
			
			if (granted.getInstitute().getType().getCode().equals("01") || granted.getInstitute().getName().contains("人权")) {
				//部级基地、人权基地
				granted.setApproveFeeMinistry(10.0);
				granted.setApproveFeeUniversity(10.0);
				granted.setApproveFeeSubjection(0.0);
				granted.setApproveFeeOther(0.0);
				granted.setApproveFee(20.0);
			} else {
				//省部共建基地
				granted.setApproveFeeMinistry(5.0);
				granted.setApproveFeeUniversity(5.0);
				granted.setApproveFeeSubjection(0.0);
				granted.setApproveFeeOther(0.0);
				granted.setApproveFee(10.0);
			}
			
			granted.getFunding().clear();

			InstpFunding funding = new InstpFunding();
			granted.addFunding(funding);
			funding.setFee(granted.getApproveFeeMinistry());
			if (granted.getApplication().getYear() == 2010) {
				funding.setDate(tool.getDate(2010, 12, 23));
			} else if (granted.getApplication().getYear() == 2011) {
				funding.setDate(tool.getDate(2011, 12, 6));
			} else if (granted.getApplication().getYear() == 2012) {
				funding.setDate(tool.getDate(2012, 6, 1));
			} else {
				throw new RuntimeException();
			}
			funding.setAttn("刘杰");
		}
	}

}
