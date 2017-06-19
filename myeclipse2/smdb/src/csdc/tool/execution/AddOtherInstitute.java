package csdc.tool.execution;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Institute;
import csdc.bean.SystemOption;
import csdc.dao.IHibernateBaseDao;

/**
 * 为所有学校添加一个"其他基地"
 * @author xuhan
 *
 */
public class AddOtherInstitute extends Execution {
	
	@Autowired
	private IHibernateBaseDao dao;


	@Override
	protected void work() throws Throwable {
		SystemOption 其他研究基地 = (SystemOption) dao.query(SystemOption.class, "qtyjjg");
		其他研究基地.getId();
		
		List<Agency> univ = dao.query("select agency from Agency agency where agency.type = 3 or agency.type = 4");
		for (Agency agency : univ) {
			Institute otherInstitute = new Institute();
			otherInstitute.setName("其他基地");
			otherInstitute.setSubjection(agency);
			otherInstitute.setType(其他研究基地);
			dao.add(otherInstitute);
		}
	}

}
