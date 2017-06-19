package csdc.action.unit.agency;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import csdc.action.unit.AgencyAction;
import csdc.bean.Agency;
import csdc.tool.SpringBean;
import csdc.tool.bean.AccountType;
import csdc.tool.merger.AgencyMerger;

/**
 * 校级管理机构类
 * 该类没有什么特别的操作，全部继承自父类UnitAction
 * 区分为不同的子类，主要是考虑各个子类中的方法需对应不同的权限
 * @author 江荣国
 * @version 2011.04.13
 */
@SuppressWarnings("unchecked")
public class UniversityAction extends AgencyAction {

	private static final long serialVersionUID = 1L;
	
	private static final String HQL = "select ag.id,ag.code,ag.name,pe.name,ag.type,ag.sname,pr.name,ag.sphone,ag.sfax,pe.id,pr.id from Agency ag left join ag.director pe left join ag.sdirector pr";
	private static final String PAGE_NAME = "universityPage";
	private static final int SUB_TYPE = 3; //子类为校级机构
	
	private List agencies; // 管理机构列表对象
	private String ids;// 管理机构编号序列
	private String[] delid;// 选中的id

	private String checkedIds, mainId, name, code;//合并高校、院系、基地时的所选id和主id，合并后的名称和代码

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
		if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){// 系统管理员、部级账号可以查看所有高校
			hql.append(" where (ag.type=3 or ag.type=4)");
		}else if(accountType.equals(AccountType.PROVINCE)){//省级管理员(3)只能管理本省地方高校(type=4)；
			hql.append(" where ag.subjection.id =:belongUnitId and ag.type=4");
			map.put("belongUnitId", belongUnitId);
		}else{//高校下级单位(accountType > 3)无法查看
			hql.append(" where 1 = 0");
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
			}else if (searchType==4){
				int key=0;
				if("部属高校".indexOf(keyword)>=0 && "地方高校".indexOf(keyword)>=0){
					
				}else if("部属高校".indexOf(keyword)>=0){
					key = 3;
					hql.append("and ag.type = :keyword");
					map.put("keyword", key);
				}else if("地方高校".indexOf(keyword)>=0){
					key = 4;
					hql.append("and ag.type = :keyword");
					map.put("keyword", key);
				}else{
					return null;
				}
			}else{
				hql.append(" and (LOWER(ag.name) like :keyword or LOWER(ag.code) like :keyword or LOWER(pe.name) like :keyword");
				map.put("keyword", '%'+keyword+'%');
				int key=0;
				if("部属高校".indexOf(keyword)>=0 && "地方高校".indexOf(keyword)>=0){
					hql.append(" or 1=1)");
				}else if("部属高校".indexOf(keyword)>=0){
					key = 3;
					hql.append(" or ag.type = :keyword2)");
					map.put("keyword2",key);
				}else if("地方高校".indexOf(keyword)>=0){
					key = 4;
					hql.append(" or ag.type = :keyword2)");
					map.put("keyword2",key);
				}else{
					hql.append(")");
				}
			}
		}
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}
	
	/**
	 * 
	 * @return
	 */
	public String toMerge() {
		// 对所选id必要的判断
		List universities = unitService.getCheckedList(checkedIds, 1);
		request.setAttribute("list", universities);
		return SUCCESS;
	}
	
	/**
	 * 合并高校
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String merge() throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		AgencyMerger merger = (AgencyMerger) SpringBean.getBean("agencyMerger");

		Serializable targetId = mainId;
		Set<Serializable> incomeIds = new HashSet<Serializable>();
		Matcher matcher = Pattern.compile("\\w+").matcher(checkedIds);
		while (matcher.find()) {
			incomeIds.add(matcher.group());
		}
		
		merger.mergeAgency(targetId, new ArrayList<Serializable>(incomeIds));
		
		Agency newAgency = (Agency) dao.query(Agency.class, targetId);
		newAgency.setName(name);
		newAgency.setCode(code);
		
		return SUCCESS;
	}

	/**
	 * 校验所选高校和主高校是否符合规范
	 * @return
	 */
	public String validateMergeUniversity() {
		
		return SUCCESS;
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
		return UniversityAction.PAGE_NAME;
	}

	public String getPageName() {
		return PAGE_NAME;
	}
	
	public int subType(){
		return UniversityAction.SUB_TYPE;
	}

	public String HQL() {
		return HQL;
	}

	public String getCheckedIds() {
		return checkedIds;
	}

	public String getMainId() {
		return mainId;
	}

	public void setCheckedIds(String checkedIds) {
		this.checkedIds = checkedIds;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
