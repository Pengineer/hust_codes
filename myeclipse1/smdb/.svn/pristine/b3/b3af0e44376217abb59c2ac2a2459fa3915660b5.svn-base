package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.dao.HibernateBaseDao;
import csdc.tool.execution.Execution;

/**
 * 为高校的sdirector和slinkman添加officer子表信息
 * 
 * @author xuhan
 *
 */
@Component
public class Fix20120507 extends Execution {

	@Autowired
	private HibernateBaseDao dao;

	@Override
	protected void work() throws Throwable {
		List<Agency> univList = dao.query("from Agency univ where univ.type = 3 or univ.type = 4");
		
		for (int i = 0; i < univList.size(); i++) {
			Agency univ = univList.get(i);
			System.out.println(i + " / " + univList.size());
			
			Person sdirector = univ.getSdirector();
			if (sdirector != null && sdirector.getOfficer().isEmpty()) {
				Officer sdirectorOfficer = new Officer();
				sdirector.getOfficer().add(sdirectorOfficer);
				sdirectorOfficer.setPerson(sdirector);
				sdirectorOfficer.setType("专职人员");
				sdirectorOfficer.setAgency(univ);
				sdirectorOfficer.setOrgan(2);
			}
			Person slinkman = univ.getSlinkman();
			if (slinkman != null && slinkman.getOfficer().isEmpty()) {
				Officer slinkmanOfficer = new Officer();
				slinkman.getOfficer().add(slinkmanOfficer);
				slinkmanOfficer.setPerson(slinkman);
				slinkmanOfficer.setType("专职人员");
				slinkmanOfficer.setAgency(univ);
				slinkmanOfficer.setOrgan(2);
			}
		}

	}

}
