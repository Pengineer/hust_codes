package csdc.tool.merger;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Institute;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.BeanIdValueProcesser;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.beanutil.mergeStrategy.MergeStrategy;

/**
 * 合并一组基地
 * @author xuhan
 *
 */
public class InstituteMerger {
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Autowired
	protected BeanFieldUtils beanFieldUtils;

	/**
	 * 合并普通字段的策略
	 * 字段名 -> 合并策略
	 */
	protected Map<String, MergeStrategy> msMap;
	
	/**
	 * 需要合并的子表信息:类
	 */
	private Class[] classes = {
	};
	
	/**
	 * 需要合并的子表信息:字段名
	 */
	private String[] fieldNames = {
	};
	
	/**
	 * 需要合并的子表信息:处理方法
	 */
	private BeanIdValueProcesser[] beanIdValueProcesser = {
	};
	
	public InstituteMerger() {
		msMap = new HashMap<String, MergeStrategy>();
		
		msMap.put("name", BuiltinMergeStrategies.SUPPLY);
		msMap.put("approveSession", BuiltinMergeStrategies.SUPPLY);
		msMap.put("approveDate", BuiltinMergeStrategies.SUPPLY);
		msMap.put("researchType", BuiltinMergeStrategies.SUPPLY);
		msMap.put("researchArea", BuiltinMergeStrategies.SUPPLY);
		msMap.put("subjection", BuiltinMergeStrategies.SUPPLY);

		msMap.put("englishName", BuiltinMergeStrategies.LONGER);
		msMap.put("code", BuiltinMergeStrategies.LONGER);
		msMap.put("abbr", BuiltinMergeStrategies.LONGER);
		msMap.put("chineseBookAmount", BuiltinMergeStrategies.LONGER);
		msMap.put("foreignBookAmount", BuiltinMergeStrategies.LONGER);
		msMap.put("chinesePaperAmount", BuiltinMergeStrategies.LONGER);
		msMap.put("foreignPaperAmount", BuiltinMergeStrategies.LONGER);

		msMap.put("isIndependent", BuiltinMergeStrategies.LARGER);
		msMap.put("officeArea", BuiltinMergeStrategies.LARGER);
		msMap.put("dataroomArea", BuiltinMergeStrategies.LARGER);

		msMap.put("form", BuiltinMergeStrategies.APPEND);
		msMap.put("disciplineType", BuiltinMergeStrategies.APPEND);
		msMap.put("address", BuiltinMergeStrategies.APPEND);
		msMap.put("postcode", BuiltinMergeStrategies.APPEND);
		msMap.put("email", BuiltinMergeStrategies.APPEND);
		msMap.put("homepage", BuiltinMergeStrategies.APPEND);
		msMap.put("introduction", BuiltinMergeStrategies.APPEND);
		
		msMap.put("phone", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("fax", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);

		//负责人和联系人选用姓名最长的那个
		MergeStrategy personMergeStrategy = new MergeStrategy<Person>() {
			public Person merge(Person p1, Person p2) {
				Integer nameLength1 = (p1 == null || p1.getName() == null) ? 0 : p1.getName().length();
				Integer nameLength2 = (p2 == null || p2.getName() == null) ? 0 : p2.getName().length();
				return nameLength1 < nameLength2 ? p2 : p1;
			}
		};
		msMap.put("director", personMergeStrategy);
		msMap.put("linkman", personMergeStrategy);
		
		//基地类型选择级别最高的那个(部级 > 省部共建 > 其他)(在数据库中就是code最小的那个)
		msMap.put("type", new MergeStrategy<SystemOption>() {
			public SystemOption merge(SystemOption type1, SystemOption type2) {
				return Integer.parseInt(type1.getCode()) < Integer.parseInt(type2.getCode()) ? type1 : type2;
			}
		});
		msMap.put("createMode", BuiltinMergeStrategies.SUPPLY);
		msMap.put("createDate", BuiltinMergeStrategies.SUPPLY);
		msMap.put("updateDate", BuiltinMergeStrategies.SUPPLY);
	}
	
	/**
	 * 更新合并策略，用于默认合并策略不能满足要求的情况
	 * @param addMsMap
	 */
	public void updateMergeStrategy(Map<String, MergeStrategy> addMsMap) {
		msMap.putAll(addMsMap);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void mergeInstitute(Serializable targetInstId, List<Serializable> incomeInstId) throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		//合并后保留的基地
		Institute targetInst = (Institute) dao.query(Institute.class, targetInstId);
		
		//合并后删除的基地
		Map paraMap = new HashMap();
		incomeInstId.remove(targetInstId);
		paraMap.put("incomeInstId", incomeInstId);
		List<Institute> incomeInst = dao.query("select inst from Institute inst where inst.id in :incomeInstId", paraMap);

		mergeInstitute(targetInst, incomeInst);
	}
	
	/**
	 * 将income基地的信息合并至target，将income的子表指向target(，并删除income)
	 * @param targetInst 合并后保留的基地(PO)
	 * @param incomeInst合并后删除的基地(PO)
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void mergeInstitute(Institute targetInst, List<Institute> incomeInst) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (incomeInst.isEmpty()) {
			return;
		}

		targetInst.setName(targetInst.getName().replace(targetInst.getSubjection().getName(), "").replace("[待删除]", "").replace("　", "").trim());

		beanFieldUtils.moveSubElementsToSingleTarget(targetInst, incomeInst, classes, fieldNames, beanIdValueProcesser);
		
		for (Institute institute : incomeInst) {
			for (Iterator iterator = msMap.keySet().iterator(); iterator.hasNext();) {
				String fieldName = (String) iterator.next();
				beanFieldUtils.mergeField(targetInst, institute, fieldName, msMap.get(fieldName));
			}
			//institute.setName(institute.getName().replace(targetInst.getSubjection().getName(), "").replace("[待删除]", "").replace("　", "").trim() + "[待删除]");
			dao.delete(institute);
		}
	}

}
