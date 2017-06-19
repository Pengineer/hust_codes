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

import csdc.bean.Account;
import csdc.bean.Department;
import csdc.bean.Person;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.bean.AccountType;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.BeanIdValueProcesser;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.beanutil.mergeStrategy.MergeStrategy;

/**
 * 合并一组院系
 * @author xuhan
 *
 */
public class DepartmentMerger {
	
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
	
	public DepartmentMerger() {
		msMap = new HashMap<String, MergeStrategy>();
		
		msMap.put("code", BuiltinMergeStrategies.SUPPLY);

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
	}
	
	/**
	 * 更新合并策略，用于默认合并策略不能满足要求的情况
	 * @param addMsMap
	 */
	public void addMergeStrategy(Map<String, MergeStrategy> addMsMap) {
		msMap.putAll(addMsMap);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void mergeDepartment(Serializable targetDeptId, List<Serializable> incomeDeptId) throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		//合并后保留的院系
		Department targetDept = (Department) dao.query(Department.class, targetDeptId);

		//合并后删除的院系
		Map paraMap = new HashMap();
		incomeDeptId.remove(targetDeptId);
		paraMap.put("incomeDeptId", incomeDeptId);
		List<Department> incomeDept = dao.query("select dept from Department dept where dept.id in (:incomeDeptId)", paraMap);
		
		mergeDepartment(targetDept, incomeDept);
	}
	
	/**
	 * 将income院系的信息合并至target，将income的子表指向target(，并删除income)
	 * @param targetDept 合并后保留的院系(PO)
	 * @param incomeDept 合并后删除的院系(PO)
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void mergeDepartment(Department targetDept, List<Department> incomeDept) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String newTargetDeptName = targetDept.getName().replace(targetDept.getUniversity().getName(), "").replace("[待删除]", "").replace("　", "").trim();
		if (newTargetDeptName.isEmpty()) {
			newTargetDeptName = "其他院系";
		}
		targetDept.setName(newTargetDeptName);

		beanFieldUtils.moveSubElementsToSingleTarget(targetDept, incomeDept, classes, fieldNames, beanIdValueProcesser);
		
		for (Department department : incomeDept) {
			for (Iterator iterator = msMap.keySet().iterator(); iterator.hasNext();) {
				String fieldName = (String) iterator.next();
				beanFieldUtils.mergeField(targetDept, department, fieldName, msMap.get(fieldName));
			}
			//department.setName(department.getName().replace(targetDept.getUniversity().getName(), "").replace("[待删除]", "").replace("　", "").trim() + "[待删除]");
			dao.delete(department);
		}
	}
}
