package csdc.action.unit.agency;

import java.util.HashMap;
import java.util.Map;

import csdc.action.unit.AgencyAction;
import csdc.tool.bean.AccountType;

/**
 * 部级管理机构类
 * 该类没有什么特别的操作，全部继承自父类UnitAction
 * 区分为不同的子类，主要是考虑各个子类中的方法需对应不同的权限
 * @author 江荣国
 * @version 2011.04.13
 */
@SuppressWarnings("unchecked")
public class MinistryAction extends AgencyAction {

	private static final long serialVersionUID = 1L;
	
	private static final String HQL = "select ag.id,ag.code,ag.name,pe.name,sub.name,ag.phone,ag.fax,pe.id,sub.id from Agency ag left join ag.director pe left join ag.subjection sub where ag.type=1";
	private static final String PAGE_NAME = "ministryPage"; //子类参数
	private static final int SUB_TYPE = 1; //子类参数

	/**
	 * 初始化列表查询的hql、hql参数、默认排序列、错误信息(如有错误信息，加载列表时会显示该信息而非列表数据)
	 * @return 0:hql  1:hql参数   2:默认排序列编号  3:错误信息  
	 */
	@Override
	public Object[] simpleSearchCondition() {
		keyword = (keyword == null) ? "" : keyword.toLowerCase();
		AccountType accountType = loginer.getCurrentType();
		String belongUnitId = loginer.getCurrentBelongUnitId();//登录者所属id
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		this.MOEId = unitService.getMOEId(); //获取教育部id
		if(accountType.equals(AccountType.MINISTRY) && !MOEId.equals(belongUnitId)){ //系统管理员和moe账号可以查看全部，而中心账号只能查看部分部级列表
			hql.append(" and ag.subjection.id =:belongUnitId ");
			map.put("belongUnitId", belongUnitId);
		}else if(accountType.compareTo(AccountType.MINISTRY) >0 ){//部级以下账号无法查看
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public String pageName() {
		return MinistryAction.PAGE_NAME;
	}
	
	public int subType() {
		return MinistryAction.SUB_TYPE;
	}
	public String HQL() {
		return MinistryAction.HQL;
	}
}
