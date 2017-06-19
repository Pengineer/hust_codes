package csdc.action.unit.agency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csdc.action.unit.AgencyAction;
import csdc.action.unit.UnitAction;
import csdc.tool.bean.AccountType;


/**
 * 省级管理机构类
 * 该类没有什么特别的操作，全部继承自父类UnitAction
 * 区分为不同的子类，主要是考虑各个子类中的方法需对应不同的权限
 * @author 江荣国
 * @version 2011.04.13
 */
@SuppressWarnings("unchecked")
public class ProvinceAction extends AgencyAction {

	private static final long serialVersionUID = 1L;

	private static final String HQL = "select ag.id,ag.code,ag.name,pe.name,ag.sname,pr.name,ag.sphone,ag.sfax,pe.id,pr.id from Agency ag left join ag.director pe left join ag.sdirector pr where ag.type=2";
	private static final String PAGE_NAME="provincePage"; //子类参数
	private static final int SUB_TYPE = 2; //子类参数，2表示省级

	private List agencies; // 管理机构列表对象
	private String ids;// 管理机构编号序列
	private String[] delid;// 选中的id
	//private String provinceid;// 搜索是省编号
	//private String universityid; //学校编号

	/**
	 * 初始化列表查询的hql、hql参数、默认排序列、错误信息(如有错误信息，加载列表时会显示该信息而非列表数据)
	 * @return 0:hql  1:hql参数   2:默认排序列编号  3:错误信息  
	 */
	@Override
	public Object[] simpleSearchCondition() {
		keyword = (null == keyword) ? "" : keyword.toLowerCase();// 预处理关键字 
		AccountType accountType = loginer.getCurrentType();
		String belongUnitId = loginer.getCurrentBelongUnitId();//belongUnitId：subjectionId 检索范围
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL); 
		if (accountType.equals(AccountType.PROVINCE) && loginer.getIsPrincipal() == 1) {// 省级账号且是主账号
			hql.append(" and ag.subjection.id =:belongUnitId ");
			map.put("belongUnitId", belongUnitId);
		} else if (accountType.compareTo(AccountType.PROVINCE) > 0){//省级以下账号无法查看
			hql.append(" and 1 = 0 ");
		}
		if(!keyword.isEmpty()){
			if(searchType ==1){
				hql.append("and LOWER(ag.code) like :keyword");
				map.put("keyword", '%'+keyword+'%');
			}else if (searchType==2){
				hql.append("and LOWER(ag.name) like :keyword");
				map.put("keyword", '%'+keyword+'%');
			}else if(searchType==3){
				hql.append("and LOWER(pe.name) like :keyword");
				map.put("keyword", '%'+keyword+'%');
			}else{
				hql.append(" and (LOWER(ag.name) like :keyword or LOWER(ag.code) like :keyword or LOWER(pe.name) like :keyword)");
				map.put("keyword", '%'+keyword+'%');
			}
		}
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}
	
	public List getAgencies() {
		return agencies;
	}

	public void setAgencies(List agencies) {
		this.agencies = agencies;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String[] getDelid() {
		return delid;
	}

	public void setDelid(String[] delid) {
		this.delid = delid;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String pageName() {
		return ProvinceAction.PAGE_NAME;
	}
	
	public int subType(){
		return ProvinceAction.SUB_TYPE;
	}
	
	public String HQL() {
		return HQL;
	}
}
