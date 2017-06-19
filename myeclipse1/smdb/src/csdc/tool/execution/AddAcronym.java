package csdc.tool.execution;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.dao.HibernateBaseDao;
import csdc.service.IBaseService;
import csdc.tool.PinyinCommon;;

@SuppressWarnings("unchecked")
public class AddAcronym extends Execution{

	@Autowired
	protected HibernateBaseDao dao;

	@Override
	public void work() throws Throwable {
		modifyAgency();
	}
	
	private void modifyAgency(){
		List<Agency> agencys = dao.query("select ag from Agency ag ");
		for(Agency agency : agencys){
			String agencyName = agency.getName();
			String acronym = PinyinCommon.getPinYinHeadChar(agencyName);
			if (agencyName.contains("重庆")) { // 四川外语学院重庆南方翻译学院
				acronym = (acronym.substring(0, agencyName.indexOf("重庆"))).concat("cq".concat(acronym.substring(agencyName.indexOf("重庆") + 2, agencyName.length())));
				System.out.println("1 +  重 + " + acronym + " + " + agencyName);
			}
			else if (agency.getName().contains("长")) {
				acronym = (acronym.substring(0, agencyName.indexOf("长"))).concat("c".concat(acronym.substring(agencyName.indexOf("长") + 1, agencyName.length())));
				System.out.println("2 + 长 + " + acronym + " + " + agencyName);
			}
			else if (agency.getName().contains("音乐")) {
				acronym = (acronym.substring(0, agencyName.indexOf("音乐"))).concat("yy".concat(acronym.substring(agencyName.indexOf("音乐") + 2, agencyName.length())));
				System.out.println("3 + 乐 + " + acronym + " + " + agencyName);
			}
			else if (agency.getName().contains("畜牧")) {
				acronym = (acronym.substring(0, agencyName.indexOf("畜牧"))).concat("xm".concat(acronym.substring(agencyName.indexOf("畜牧") + 2, agencyName.length())));
				System.out.println("4 + 畜 + " + acronym + " + " + agencyName);
			}
			agency.setAcronym(acronym);
		}
		// 在机构名中找出了四种常见的多音字: 
		// 重 除了重庆都是zhong 默认zhong
		// 长 都是chang 默认zhang
		// 乐 音乐学院都是le 默认le
		// 畜 都是xu（畜牧） 默认chu
	}

}
