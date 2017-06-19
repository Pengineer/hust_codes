package csdc.tool.execution.finder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.AgencyFunding;
import csdc.dao.IHibernateBaseDao;

/**
 * 经费系统辅助查找类
 * @author pengliang
 *
 */
@Component
public class AgencyFundingFinder {
	
	@Autowired
	private IHibernateBaseDao dao;
	
	public Map<String, AgencyFunding> agencyFundingMap;
	
	/**
	 * 根据[一个机构名]和[拨款批次名]，返回一个AgencyFunding(PO)
	 * @param agencyName
	 * @param batchName
	 * @return
	 */
	public AgencyFunding getAgencyFunding(String agencyName, String batchName){
		if(agencyFundingMap == null){
			initAgencyFunding();
		}
		return agencyFundingMap.get(agencyName + batchName);
	}
	
	public Map<String, AgencyFunding> agencyFundingMap2;
	
	/**
	 * 根据机构名返回一个AgencyFunding实体
	 * @param agencyName
	 * @return
	 */
	public AgencyFunding getAgencyFundingByAgencyName(String agencyName){
		if (agencyFundingMap2 == null) {
			agencyFundingMap2 = new HashMap<String, AgencyFunding>();
			List<AgencyFunding> list = dao.query("from AgencyFunding");
			for (AgencyFunding agencyFunding : list) {
				agencyFundingMap2.put(agencyFunding.getAgencyName(), agencyFunding);
			}
		}
		return agencyFundingMap2.get(agencyName);
	}
	
	public void initAgencyFunding(){
		Date begin = new Date();
	
		agencyFundingMap = new HashMap<String, AgencyFunding>();
		List<String[]> list = dao.query("select af.agencyName, af.id, af.fundingBatch.name from AgencyFunding af");
		for (Object[] o : list) {
			AgencyFunding agencyFunding = (AgencyFunding) dao.query(AgencyFunding.class, (String)o[1]);
			agencyFundingMap.put((String)o[0] + (String)o[2], agencyFunding);
		}
		System.out.println("init GeneralApplication complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
	
	/**
	 * 置空辅助类，释放部分内存
	 */
	public void reset() {
		agencyFundingMap = null;
		agencyFundingMap2 = null;
	}
}
