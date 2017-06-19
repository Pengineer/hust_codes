package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.GeneralSpecial;
import csdc.bean.Nsfc;
import csdc.bean.Nssf;
import csdc.dao.HibernateBaseDao;
import csdc.tool.execution.Execution;
import csdc.tool.execution.finder.UniversityFinder;

/**
 * 社科、自科、一般项目专项任务表结构添加学校ID、学校名称两个字段，更新数据库中的对应数据。 
 * @author wangyi
 * 
 */
@Component
public class Fix20130219 extends Execution {

	@Autowired
	private HibernateBaseDao dao;
	
	@Autowired
	private UniversityFinder universityFinder;

	@Override
	public void work() throws Throwable {
//		List<Nssf> nssfs = dao.query("from Nssf nssf where nssf.unit is not null ");//社科
//		
//		for (int i = 0; i < nssfs.size(); i++) {
//			Nssf nssf = nssfs.get(i);
//			Agency univ = universityFinder.getUniversityWithLongestName(nssf.getUnit());
//			if (univ != null) {
//				nssf.setUniversity(univ.getName());
//			}
//		}
//		
//		List<Nsfc> nsfcs = dao.query("from Nsfc nsfc where nsfc.unit is not null ");//自科
//		
//		for (int i = 0; i < nsfcs.size(); i++) {
//			Nsfc nsfc = nsfcs.get(i);
//			Agency univ = universityFinder.getUniversityWithLongestName(nsfc.getUnit());
//			if (univ != null) {
//				nsfc.setUniversity(univ.getName());
//			}
//		}
//		
//		List<GeneralSpecial> generalSpecials = dao.query("from GeneralSpecial generalSpecial where 1 = 1 ");//一般项目专项任务
//		
//		for (int i = 0; i < generalSpecials.size(); i++) {
//			GeneralSpecial generalSpecial = generalSpecials.get(i);
//			Agency univ = universityFinder.getUnivByName(generalSpecial.getUnit());
//			if (univ != null) {
//				generalSpecial.setUnit(univ.getName());
//			}
//		}
		
//		List<Nssf> nssfs = dao.query("from Nssf nssf where nssf.university is not null ");//社科
//		
//		for (int i = 0; i < nssfs.size(); i++) {
//			Nssf nssf = nssfs.get(i);
//			if (nssf.getUniversity().contains("; ")) {
//				String[] universityStrings = nssf.getUniversity().split("; ");
//				StringBuffer universityIds = new StringBuffer();
//				universityIds.append(universityFinder.getUnivByName(universityStrings[0]).getId());
//				for (int j = 1; j < universityStrings.length; j++) {
//					Agency univ = universityFinder.getUnivByName(universityStrings[j]);
//					universityIds.append("; " + univ.getId());
//				}
//				nssf.setUniversityId(universityIds.toString());
//			} else {
//				Agency univ = universityFinder.getUnivByName(nssf.getUniversity());
//				if (univ != null) {
//					nssf.setUniversityId(univ.getId());
//				}
//			}
//		}
		
//		List<Nsfc> nsfcs = dao.query("from Nsfc nsfc where nsfc.university is not null ");//自科
//		
//		for (int i = 0; i < nsfcs.size(); i++) {
//			Nsfc nsfc = nsfcs.get(i);
//			Agency univ = universityFinder.getUnivByName(nsfc.getUniversity());
//			if (univ != null) {
//				nsfc.setUniversityId(univ.getId());
//			}
//		}
		
		List<GeneralSpecial> generalSpecials = dao.query("from GeneralSpecial generalSpecial where 1 = 1 ");//一般项目专项任务
		
		for (int i = 0; i < generalSpecials.size(); i++) {
			GeneralSpecial generalSpecial = generalSpecials.get(i);
			Agency univ = universityFinder.getUnivByName(generalSpecial.getUnit());
			if (univ != null) {
				generalSpecial.setUniversity(univ.getName());
				generalSpecial.setUniversityId(univ.getId());
			}
		}

	}

}
