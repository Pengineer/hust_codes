package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.Nsfc;
import csdc.bean.Nssf;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.Importer;

/**
 * 
 * @author pengliang
 *
 */

@Component
public class Fix20150415 extends Importer {
	
	@Autowired
	UniversityFinder universityFinder;

	@Override
	protected void work() throws Throwable {
		fixNsfc();
		fixNssc();
	}
	
	@SuppressWarnings("unchecked")
	public void fixNsfc() {
		int num=0;
		List<Nsfc> nsfcList = dao.query("select nsfc from Nsfc nsfc where to_char(nsfc.importDate,'yyyy-mm')='2015-04'");
		for(Nsfc nsfc : nsfcList) {
			System.out.println(++num);
			if(nsfc.getUniversity() == null) {
				Agency university = universityFinder.getAgencyByName(nsfc.getUnit());
				if(university != null){
					nsfc.setUniversity(university.getName());
					nsfc.setUniversityId(university.getId());
				}
			}
			dao.modify(nsfc);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void fixNssc() {
		int num=0;
		List<Nssf> nssfList = dao.query("select nssf from Nssf nssf where to_char(nssf.importDate,'yyyy-mm')='2015-04'");
		for(Nssf nssf : nssfList) {
			System.out.println(++num);
			if(nssf.getUniversity() == null) {
				Agency university = universityFinder.getAgencyByName(nssf.getUnit());
				if(university != null){
					nssf.setUniversity(university.getName());
					nssf.setUniversityId(university.getId());
				}
			}
			dao.modify(nssf);
		}
	}

}
