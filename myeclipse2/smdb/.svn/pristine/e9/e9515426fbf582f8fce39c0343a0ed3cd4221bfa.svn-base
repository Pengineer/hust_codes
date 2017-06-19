package csdc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.SystemOption;

/**
 * SystemOption Dao
 * @author xuhan
 *
 */
public class SystemOptionDao {
	
	@Autowired
	private HibernateBaseDao dao;
	
	/**
	 * 根据standard和code查询一条SystemOption
	 * @param standard
	 * @param code
	 * @return
	 */
	public SystemOption query(String standard, String code) {
		dao.setCacheRegion("csdc.bean.SystemOption");
		SystemOption systemOption = null;
		if(null == code){
			systemOption = (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = ? and so.code is null", standard);
		}else {
			systemOption = (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = ? and so.code = ?", standard, code);
		}		
		systemOption.getId();
		return systemOption;
	}
	
	/**
	 * 查询指定standard的SystemOption的根节点
	 * @param standard
	 * @return
	 */
	public SystemOption queryRoot(String standard) {
		dao.setCacheRegion("csdc.bean.SystemOption");
		return (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = ? and so.systemOption.id = '4028d88a296cfc1901296cfc98580505'", standard);
	}
	
	/**
	 * 查询指定SystemOption节点的子节点
	 * @param systemOption
	 * @return
	 */
	public List<SystemOption> queryChildren(SystemOption systemOption) {
		return new ArrayList<SystemOption>(systemOption.getSystemOptions());
	}
	
	/**
	 * 查询指定standard的一级子节点
	 * @param standard
	 * @return
	 */
	public List<SystemOption> queryChildren(String standard) {
		return queryChildren(queryRoot(standard));
	}
	
	/**
	 * 查询指定standard的所有叶子节点，按code排序
	 * @param standard
	 * @return
	 */
	public List<SystemOption> queryLeafNodes(String standard) {
		dao.setCacheRegion("csdc.bean.SystemOption");
		return dao.query("select so from SystemOption so left join so.systemOptions children where so.standard = ? and children is null order by so.code", standard);
	}
	

}
