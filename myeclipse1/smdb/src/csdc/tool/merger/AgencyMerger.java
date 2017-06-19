package csdc.tool.merger;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Agency;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.BeanIdValueProcesser;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.beanutil.mergeStrategy.MergeStrategy;

/**
 * 合并一组机构
 * 
 * @author xuhan
 */
public class AgencyMerger {
	
	@Autowired
	protected IHibernateBaseDao dao;
	
	@Autowired
	protected BeanFieldUtils beanFieldUtils;
	
	
	/**
	 * 合并普通字段的策略
	 * 字段名 -> 合并策略
	 */
	protected Map<String, MergeStrategy> msMap;
	
	/**
	 * 非外键的部分特殊处理
	 * 需要合并的子表信息:类
	 */
	private Class[] classes = {
	};
	
	/**
	 * 非外键的部分特殊处理
	 * 需要合并的子表信息:字段名
	 */
	private String[] fieldNames = {
	};
	
	/**
	 * 非外键的部分特殊处理
	 * 需要合并的子表信息:处理方法
	 */
	private BeanIdValueProcesser[] beanIdValueProcesser = {
	};
	
	public AgencyMerger() {
		msMap = new HashMap<String, MergeStrategy>();
		
		msMap.put("englishName", BuiltinMergeStrategies.SUPPLY);
		msMap.put("abbr", BuiltinMergeStrategies.SUPPLY);
		msMap.put("code", BuiltinMergeStrategies.SUPPLY);
		msMap.put("type", BuiltinMergeStrategies.SUPPLY);
		msMap.put("province", BuiltinMergeStrategies.SUPPLY);
		msMap.put("city", BuiltinMergeStrategies.SUPPLY);
		msMap.put("subjection", BuiltinMergeStrategies.SUPPLY);
		msMap.put("director", BuiltinMergeStrategies.SUPPLY);
		msMap.put("introduction", BuiltinMergeStrategies.APPEND);
		msMap.put("address", BuiltinMergeStrategies.APPEND);
		msMap.put("postcode", BuiltinMergeStrategies.APPEND);
		msMap.put("phone", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("fax", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("email", BuiltinMergeStrategies.APPEND);
		msMap.put("homepage", BuiltinMergeStrategies.APPEND);
		msMap.put("sname", BuiltinMergeStrategies.SUPPLY);
		msMap.put("sdirector", BuiltinMergeStrategies.SUPPLY);
		msMap.put("slinkman", BuiltinMergeStrategies.SUPPLY);
		msMap.put("saddress", BuiltinMergeStrategies.APPEND);
		msMap.put("spostcode", BuiltinMergeStrategies.APPEND);
		msMap.put("sphone", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("sfax", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("semail", BuiltinMergeStrategies.APPEND);
		msMap.put("shomepage", BuiltinMergeStrategies.APPEND);
		msMap.put("fbankAccount", BuiltinMergeStrategies.APPEND);
		msMap.put("fcupNumber", BuiltinMergeStrategies.APPEND);
		msMap.put("fbank", BuiltinMergeStrategies.APPEND);
		msMap.put("fbankBranch", BuiltinMergeStrategies.APPEND);
		msMap.put("fbankAccountName", BuiltinMergeStrategies.APPEND);
		msMap.put("fname", BuiltinMergeStrategies.SUPPLY);
		msMap.put("fdirector", BuiltinMergeStrategies.SUPPLY);
		msMap.put("flinkman", BuiltinMergeStrategies.SUPPLY);
		msMap.put("faddress", BuiltinMergeStrategies.APPEND);
		msMap.put("fpostcode", BuiltinMergeStrategies.APPEND);
		msMap.put("fphone", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("ffax", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("femail", BuiltinMergeStrategies.APPEND);
		msMap.put("style", BuiltinMergeStrategies.SUPPLY);
		msMap.put("category", BuiltinMergeStrategies.SUPPLY);
		msMap.put("organizer", BuiltinMergeStrategies.SUPPLY);
		msMap.put("reviewLevel", BuiltinMergeStrategies.SUPPLY);
		msMap.put("reviewScore", BuiltinMergeStrategies.SUPPLY);
		msMap.put("acronym", BuiltinMergeStrategies.SUPPLY);
		msMap.put("createMode", BuiltinMergeStrategies.SUPPLY);
		msMap.put("createDate", BuiltinMergeStrategies.SUPPLY);
		msMap.put("updateDate", BuiltinMergeStrategies.SUPPLY);
	}
	
	/**
	 * 更新合并策略，用于默认合并策略不能满足要求的情况
	 * @param addMsMap
	 */
	public void addMergeStrategy(Map<String, MergeStrategy> addMsMap) {
		msMap.putAll(addMsMap);
	}
	
	/**
	 * 根据[主要agencyId]和[次要agencyId]进行agency合并
	 * @param targetAgencyId
	 * @param incomeAgencyIds
	 * @throws IllegalArgumentException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void mergeAgency(Serializable targetAgencyId, List<Serializable> incomeAgencyIds) throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		//合并后保留的机构
		Agency targetAgency = (Agency) dao.query(Agency.class, targetAgencyId);

		//合并后删除的机构
		Map paraMap = new HashMap();
		incomeAgencyIds.remove(targetAgencyId);
		paraMap.put("incomeAgencyIds", incomeAgencyIds);
		List<Agency> incomeAgencies = dao.query("select agency from Agency agency where agency.id in (:incomeAgencyIds)", paraMap);
		
		mergeAgency(targetAgency, incomeAgencies);
	}
	
	/**
	 * 将income机构的信息合并至target，将income的子表指向target(，并删除income)
	 * @param targetAgency 合并后保留的机构(PO)
	 * @param incomeAgencies 合并后删除的机构(PO)
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void mergeAgency(Agency targetAgency, List<Agency> incomeAgencies) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String newTargetAgencyName = targetAgency.getName().replace("[待删除]", "").replace("　", "").trim();
		targetAgency.setName(newTargetAgencyName);

		beanFieldUtils.moveSubElementsToSingleTarget(targetAgency, incomeAgencies, classes, fieldNames, beanIdValueProcesser);
		
		for (Agency incomeAgency : incomeAgencies) {
			for (String fieldName : msMap.keySet()) {
				beanFieldUtils.mergeField(targetAgency, incomeAgency, fieldName, msMap.get(fieldName));
			}
			//agency.setName(agency.getName().replace(targetAgency.getUniversity().getName(), "").replace("[待删除]", "").replace("　", "").trim() + "[待删除]");
			dao.delete(incomeAgency);
		}
	}
}
