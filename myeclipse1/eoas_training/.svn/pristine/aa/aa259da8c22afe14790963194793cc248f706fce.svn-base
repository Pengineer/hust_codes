package csdc.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.PRIVATE_MEMBER;

import csdc.bean.Expert;
import csdc.bean.SystemOption;
import csdc.dao.IBaseDao;
import csdc.service.IExpertService;

public class ExpertService extends BaseService implements IExpertService {
	private IBaseDao baseDao;
	
	/**
	 * 根据学科编号序列获取”编号%名称%代码；“组成的序列，”%“，”；“为分隔符
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDispIdCodeNameById(String ids){
		String[] id = ids.split(";");
		if(id !=null && id.length>0){
			StringBuffer hql = new StringBuffer();
			Map map = new HashMap();
/*			hql.append("select sys.id,sys.code,sys.name from SystemOption sys where ");
			hql.append(" sys.id in(:id)");
			map.put("id", id);
			String hql1= hql.toString();
			hql1 += "order by sys.code";
			List list = dao.query(hql1,map);*/
			map.put("id", id[0]);
			List<Map<String, String>> list = null;
			try {
				list = baseDao.list(SystemOption.class.getName() + ".getDispIdCodeNameById", map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(list != null && list.size()>0){
				StringBuffer str = new StringBuffer();
				for(int j=0; j<list.size(); j++){
					try {
						
						str.append(list.get(0).get("id").toString()+"%"+list.get(0).get("code").toString()+"%"+list.get(0).get("name").toString()+";");
						/*str.append(((Object[])list.get(j))[0].toString()+"%"+((Object[])list.get(j))[1].toString()+"%"+((Object[])list.get(j))[2].toString()+";");*/
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				String str1 = str.toString();
				str1 = str1.substring(0,str.length()-1);
				return str1;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	
	/**
	 * 根据学科代码/名称序列获取”编号%名称%代码；“组成的序列，”%“，”；“分隔符
	 * @param ids
	 * @return
	 */
	public String getDispIdCodeNameByCodeName(String codeName){
		String[] code = codeName.replace("; ", "/").split("/");
		if(code !=null && code.length>0){
			StringBuffer hql = new StringBuffer();
			Map map = new HashMap();
/*			hql.append("select sys.id,sys.code,sys.name from SystemOption sys where sys.standard like '%2009%' and ");
			hql.append(" sys.code in(:code)");
			map.put("code", code);
			String hql1= hql.toString();
			hql1 += "order by sys.code";
			List list = dao.query(hql1,map);*/
			
			map.put("code", code);
			List list = null;
			try {
				list =  baseDao.list(SystemOption.class.getName() + ".getDispIdCodeNameById", map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
/*			
			map.put("code", code);
			List list = baseDao.list(SystemOption.class.getName() + ".listSystemOptionMap", map);*/
			if(list != null && list.size()>0){
				StringBuffer str = new StringBuffer();
				for(int j=0; j<list.size(); j++){
					
					try {
						System.out.println(((Map)list.get(j)).get("id"));
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					str.append(((Map)list.get(j)).get("id").toString()+"%"+((Map)list.get(j)).get("code").toString()+"%"+((Map)list.get(j)).get("name").toString()+"; ");
				}
				String str1 = str.toString();
				str1 = str1.substring(0,str.length()-2);
				return str1;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public IBaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}
}