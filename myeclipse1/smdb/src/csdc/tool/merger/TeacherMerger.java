package csdc.tool.merger;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Academic;
import csdc.bean.Account;
import csdc.bean.Person;
import csdc.bean.Teacher;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.beanutil.mergeStrategy.MergeStrategy;
/**
 * 合并教师
 * 
 * @author jintf
 */
public class TeacherMerger extends PersonMerger  {	
	
	private Teacher tmpTeacher;  //预合并后的教师
	private Academic tmpAcademic ; //预合并后的学术信息
	
	private Map<String, MergeStrategy> teMap = new HashMap<String, MergeStrategy>();//学生对象字段合并策略
	
	public TeacherMerger(){
		teMap.put("type", BuiltinMergeStrategies.SUPPLY);
		teMap.put("workMonthPerYear", BuiltinMergeStrategies.SUPPLY);
		teMap.put("startDate", BuiltinMergeStrategies.SUPPLY);
		teMap.put("endDate", BuiltinMergeStrategies.SUPPLY);
		teMap.put("position", BuiltinMergeStrategies.SUPPLY);
		teMap.put("staffCardNumber", BuiltinMergeStrategies.SUPPLY);
		teMap.put("agencyName", BuiltinMergeStrategies.SUPPLY);
		teMap.put("divisionName", BuiltinMergeStrategies.SUPPLY);
		teMap.put("createType", BuiltinMergeStrategies.SUPPLY);
	}
	
	/**
	 * 预合并教师
	 * @param targetTeacherId   合并的目标教师ID
	 * @param incomeTeacherIds  要合并的教师ID （不包括targetTeacherId）
	 * @throws IllegalArgumentException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public void mergeTeacher(Serializable targetTeacherId, Set<Serializable> incomeTeacherIds) throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		Map paraMap = new HashMap();
		Serializable targetPersonId=dao.query(Teacher.class,targetTeacherId).getPerson().getId();
		paraMap.put("incomeTeacherIds", incomeTeacherIds);
		List<Serializable> incomePersonIds = dao.query("select p.id from Teacher t left join t.person p where t.id in (:incomeTeacherIds)",paraMap);
		tmpAcademic = dao.query(Person.class,targetPersonId).getAcademic();
		if(tmpAcademic==null){
			tmpAcademic = new Academic();
		}
		//合并教师信息
		
		//合并学术信息
		paraMap.put("incomePersonIds", incomePersonIds);
		List<Academic> incomeAcademics = dao.query("select a from Person p left join p.academic a where p.id in (:incomePersonIds)",paraMap);
		for (Academic academic:incomeAcademics) {
			if(academic!=null){
				for (String fieldName : acMap.keySet()) {
					beanFieldUtils.mergeField(tmpAcademic, academic, fieldName, acMap.get(fieldName));
				}
			}
		}
		//合并人员表
		tmpPerson = mergePerson(targetPersonId, new ArrayList<Serializable>(incomePersonIds));
	}
	
	/**
	 * 执行教师合并
	 * @param targetId 专职教师id
	 * @param incomeIds 要合并的专职教师id
	 * @param selectedAccountId 选取的账号id
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 */
	public void doMerge(Serializable targetId, Set<Serializable> incomeIds,String selectedAccountId) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException{
		Account targetAccount = null;
		if(selectedAccountId != null)
			targetAccount = dao.query(Account.class,selectedAccountId);
		Set<Account> incomeAccounts = new HashSet<Account>();
		
		Map paraMap = new HashMap();
		Person targetPerson = dao.query(Person.class, targetId);
		//合并后删除的人员
		paraMap.put("otherPersonIds", incomeIds);
		List<Person> otherPerson = dao.query("select p from Person p where p.id in (:otherPersonIds)", paraMap);
		List<Academic> otherAcademics = new ArrayList<Academic>();
		//修改银行账号和地址
		mergeAddressAndBankAccount(targetPerson, new ArrayList<Person>(otherPerson));
		//改名字
		for (Person o:otherPerson) {
			if(!o.getName().equals(targetPerson.getName())){
				personService.updatePersonName(o.getId(), targetPerson.getName());
			}	
			if(o.getAcademic()!=null)
				otherAcademics.add(o.getAcademic());
			incomeAccounts.add(personService.getAccountByPersonId(o.getId(), 3));
		}
		//改账号ID
		incomeAccounts.add(personService.getAccountByPersonId(targetPerson.getId(), 3));
		incomeAccounts.remove(targetAccount);
		incomeAccounts.remove(null);
		if(targetAccount!=null && !incomeAccounts.isEmpty()){
			beanFieldUtils.moveSubElementsToSingleTarget(targetAccount,new ArrayList<Account>(incomeAccounts),null,null,null);
		}
		//学术信息在之前已经合并，因为一对一的原因，因此必须删除（最好移到action中）
		dao.delete(otherAcademics);
		//修改关联person的外键
		beanFieldUtils.moveSubElementsToSingleTarget(targetPerson, new ArrayList<Person>(otherPerson), null, null, null);		
		dao.delete(otherPerson);
	}


	public Teacher getTmpTeacher() {
		return tmpTeacher;
	}


	public void setTmpTeacher(Teacher tmpTeacher) {
		this.tmpTeacher = tmpTeacher;
	}

	public Academic getTmpAcademic() {
		return tmpAcademic;
	}

	public void setTmpAcademic(Academic tmpAcademic) {
		this.tmpAcademic = tmpAcademic;
	}
	
	
}
