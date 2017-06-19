package csdc.tool.beanutil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.FieldUpdate;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.beanutil.mergeStrategy.MergeStrategy;

/**
 * 用于Bean各个字段的相关操作
 * @author xuhan
 *
 */
@Component
public class BeanFieldUtils {
	
	@Autowired
	private IHibernateBaseDao dao;
	
	
	/**
	 * 将o1.propertyName1和o2.propertyName2按照策略mergeStrategy进行值合并，将合并结果写入o1.propertyName1<br>
	 * (o2不变化)
	 * @param o1
	 * @param propertyName1
	 * @param o2
	 * @param propertyName2
	 * @param mergeStrategy
	 * @throws IntrospectionException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void mergeField(Object o1, String propertyName1, Object o2, String propertyName2, MergeStrategy mergeStrategy) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Method getter1 = PropertyUtils.getReadMethod(new PropertyDescriptor(propertyName1, o1.getClass()));
		Method setter1 = PropertyUtils.getWriteMethod(new PropertyDescriptor(propertyName1, o1.getClass()));
		Method getter2 = PropertyUtils.getReadMethod(new PropertyDescriptor(propertyName2, o2.getClass()));
		
		Object originValue1 = getter1.invoke(o1); 
		Object originValue2 = getter2.invoke(o2); 
		
		Object mergeResult = mergeStrategy.merge(originValue1, originValue2);
		
		setter1.invoke(o1, mergeResult);
	}
	
	/**
	 * 将o1.propertyName和o2.propertyName按照策略mergeStrategy进行值合并，将合并结果写入o1.propertyName<br>
	 * (o2不变化)
	 * @param o1
	 * @param o2
	 * @param propertyName
	 * @param mergeStrategy
	 * @throws IntrospectionException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void mergeField(Object o1, Object o2, String propertyName, MergeStrategy mergeStrategy) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		mergeField(o1, propertyName, o2, propertyName, mergeStrategy);
	}

	/**
	 * 将o1和o2中名为propertyNames中元素的成员按照策略mergeStrategy进行值合并，将合并结果分别写入o1<br>
	 * (o2不变化)
	 * @param o1
	 * @param o2
	 * @param propertyNames
	 * @param mergeStrategy
	 * @throws IntrospectionException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void mergeField(Object o1, Object o2, String[] propertyNames, MergeStrategy mergeStrategy) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		for (String propertyName : propertyNames) {
			mergeField(o1, propertyName, o2, propertyName, mergeStrategy);
		}
	}

	/**
	 * 将o中名为propertyNames的成员按照mergeStrategy规则和incomingValue进行合并，结果仍然存入o.propertyNames，不持久化更新记录
	 * @param o
	 * @param propertyName
	 * @param incomingValue
	 * @param mergeStrategy
	 * @throws Exception 
	 */
	public void setField(Object o, String propertyName, Object incomingValue, MergeStrategy mergeStrategy) throws Exception {
		setField(o, propertyName, incomingValue, mergeStrategy, false);
	}

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 将o中名为propertyNames的成员按照mergeStrategy规则和incomingValue进行合并，结果仍然存入o.propertyNames
	 * @param o
	 * @param propertyName
	 * @param incomingValue
	 * @param mergeStrategy
	 * @param persistUpdate 是否持久化更新记录
	 * @throws Exception 
	 */
	public void setField(Object o, String propertyName, Object incomingValue, MergeStrategy mergeStrategy, boolean persistUpdate) throws Exception {
		Method getter = PropertyUtils.getReadMethod(new PropertyDescriptor(propertyName, o.getClass()));
		Method setter = PropertyUtils.getWriteMethod(new PropertyDescriptor(propertyName, o.getClass()));
		Object originValue = getter.invoke(o);
		Object mergeValue = mergeStrategy.merge(originValue, incomingValue);
		setter.invoke(o, mergeValue);
		
		if (persistUpdate && originValue != null && !originValue.equals(mergeValue)) {
			Method idGetter = PropertyUtils.getReadMethod(new PropertyDescriptor("id", o.getClass()));
			String entityId = (String) idGetter.invoke(o);
			
			//日期至少存在java.sql.Timestamp和java.util.Date两种类型，需要格式化再判断是否相等
			if (originValue instanceof Date) {
				originValue = sdf.format(originValue);
				mergeValue = mergeValue == null ? "null" : sdf.format(mergeValue);
				if (originValue.equals(mergeValue)) {
					return;
				}
			}
			
			FieldUpdate fieldUpdate = new FieldUpdate();
			fieldUpdate.setClassName(o.getClass().getName());
			fieldUpdate.setEntityId(entityId);			
			fieldUpdate.setFieldName(propertyName);
			fieldUpdate.setOldValue(originValue + "");
			fieldUpdate.setNewValue(mergeValue + "");
			fieldUpdate.setUpdateDate(new Date());
			dao.add(fieldUpdate);
		}
	}
	
	/**
	 * 将income中子表元素全移进target
	 * @param target
	 * @param income
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IntrospectionException 
	 */
	public void moveSubElementsToSingleTarget(Object target, List income, Class[] classes, String[] fieldNames, BeanIdValueProcesser[] beanIdValueProcessers) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException {
		Class clazz = target.getClass();
		Method idGetter = new PropertyDescriptor("id", clazz).getReadMethod();
		
		Object targetId = idGetter.invoke(target);
		List incomeIdList = new ArrayList();
		for (Object incomeObject : income) {
			if (target.getClass() != incomeObject.getClass()) {
				throw new RuntimeException("类型不一致，无法移动"+target.getClass()+";"+incomeObject.getClass());
			}
			Serializable incomeId = (Serializable) idGetter.invoke(incomeObject);
			if (incomeId != null) {
				incomeIdList.add(incomeId);
			}
		}

		if (incomeIdList.isEmpty()) {
			return;
		}

		Map paraMap = new HashMap();
		paraMap.put("oldIds", incomeIdList);
		paraMap.put("targetId", targetId);
		
		//更新子表外键
		for (Class otherClazz : AllBeans.findAllBeans()) {
			for (Field field : getAllFields(otherClazz)) {
				if (clazz.isAssignableFrom(field.getType())||field.getType().isAssignableFrom(clazz)) {
					String executeHql = "update " + otherClazz.getName() + " tmp set tmp." + field.getName() + ".id = :targetId where tmp." + field.getName() + ".id in (:oldIds)";
					dao.execute(executeHql, paraMap);
				}
			}
		}
		
		if (classes != null && fieldNames != null && beanIdValueProcessers != null) {
			if (classes.length != fieldNames.length || classes.length != beanIdValueProcessers.length) {
				throw new RuntimeException("指定字段属性错误：长度不一致");
			}
		} else if (classes != null || fieldNames != null || beanIdValueProcessers != null) {
			throw new RuntimeException("指定字段属性错误: 有的空，有的非空");
		} if (classes == null) {
			return;
		}
		
		//更新子表的表示其他表id的非外键型字段
		for (int i = 0; i < classes.length; i++) {
			//若beanIdValueProcesser为空，则和外键型类似，直接赋值
			if (beanIdValueProcessers[i] == null) {
				String executeHql = "update " + classes[i].getName() + " tmp set tmp." + fieldNames[i] + " = :targetId where tmp." + fieldNames[i] + " in (:oldIds)";
				dao.execute(executeHql, paraMap);
			} else {
				//若beanIdValueProcesser非空，则使用自定义的赋值方法(例如：用于更新前要做某些判断，或者更新分号空格隔开的若干值中的一段)
				for (int j = 0; j < incomeIdList.size(); j++) {
					List list = dao.query("select tmp from " + classes[i].getName() + " tmp where tmp." + fieldNames[i] + " like ? ", "%" + incomeIdList.get(j) + "%");
					for (Object object : list) {
						beanIdValueProcessers[i].process(object, incomeIdList.get(i), targetId);
					}
				}
			}
		}
	}
	
	/**
	 * 统计对象的非空fields数目
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public Integer nonNullFields(Object bean) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		int cnt = 0;
		
		Class clazz = bean.getClass();
		for (Field field : getAllFields(clazz)) {
			if (Set.class.isAssignableFrom(field.getType())) {
				Method getter = new PropertyDescriptor(field.getName(), clazz).getReadMethod();
				if (getter != null && getter.invoke(bean) != null) {
					++cnt;
				}
			}
		}
		
		return cnt;
	}

	/**
	 * 获取clazz类中类型为targetType子类的成员名
	 * @param clazz
	 * @return
	 */
	public Field[] getFieldsOfTargetType(Class clazz, Class targetType) {
		List<Field> res = new ArrayList<Field>();
		for (Field field : getAllFields(clazz)) {
			if (targetType.isAssignableFrom(field.getType())) {
				res.add(field);
			}
		}
		return res.toArray(new Field[0]);
	}
	
	/**
	 * 获取所有field，包括继承来的
	 * @param type
	 * @return
	 */
	public static List<Field> getAllFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();
	    for (Field field : clazz.getDeclaredFields()) {
	        fields.add(field);
	    }
	    if (clazz.getSuperclass() != null) {
	        fields.addAll(getAllFields(clazz.getSuperclass()));
	    }
	    return fields;
	}

	
}
