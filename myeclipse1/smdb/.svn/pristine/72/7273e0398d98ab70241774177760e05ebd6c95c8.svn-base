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

import csdc.bean.Account;
import csdc.bean.Officer;
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.beanutil.mergeStrategy.MergeStrategy;

/**
 * 合并管理人员
 * 
 * @author jintf
 */
public class OfficerMerger extends PersonMerger  {
	
	private Officer tmpOfficer; //预合并后的管理人员
	
	private Map<String, MergeStrategy> ofMap = new HashMap<String, MergeStrategy>();//管理人员对象字段合并策略
	
	public OfficerMerger(){
		ofMap.put("position", BuiltinMergeStrategies.APPEND);
		ofMap.put("staffCardNumber", BuiltinMergeStrategies.SUPPLY);
		ofMap.put("startDate", BuiltinMergeStrategies.SUPPLY);
		ofMap.put("endDate", BuiltinMergeStrategies.SUPPLY);
		ofMap.put("type", BuiltinMergeStrategies.SUPPLY);
		ofMap.put("organ", BuiltinMergeStrategies.SUPPLY);
		ofMap.put("agencyName", BuiltinMergeStrategies.SUPPLY);
		ofMap.put("divisionName", BuiltinMergeStrategies.SUPPLY);
	}
	
	/**
	 * 预合并管理人员
	 * @param targetOfficerId 目标管理人员
	 * @param incomeOfficerIds 要合并的管理人员（包含目标管理人员）
	 * @throws IllegalArgumentException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public void mergeOfficer(Serializable targetOfficerId, List<Serializable> incomeOfficerIds) throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		List<Serializable> incomePersonIds = new ArrayList<Serializable>();
		tmpOfficer = (Officer) dao.query(Officer.class, targetOfficerId);
		
		//合并后删除的人员
		Map paraMap = new HashMap();
		incomeOfficerIds.remove(targetOfficerId);
		paraMap.put("incomeOfficerIds", incomeOfficerIds);
		List<Officer> incomeOfficers = dao.query("select o from Officer o where o.id in (:incomeOfficerIds)", paraMap);
		for (Officer incomeOfficer : incomeOfficers) {
			incomePersonIds.add(incomeOfficer.getPerson().getId());
			for (String fieldName : ofMap.keySet()) {
				beanFieldUtils.mergeField(tmpOfficer, incomeOfficer, fieldName, ofMap.get(fieldName));
			}
		}
		//合并人员表
		Serializable targetPersonId = tmpOfficer.getPerson().getId();
		tmpPerson = mergePerson(targetPersonId, incomePersonIds);
	}
	
	
	
	/**
	 * 执行管理人员的合并操作
	 * @param targetId 合并的目标ID
	 * @param incomeIds 要合并的ID （不包括targetId）
	 * @param selectedAccountId 要保留的相关账号ID
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void doMerge(Serializable targetId, List<Serializable> incomeIds,String selectedAccountId) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException{
		Account targetAccount = null;
		if(selectedAccountId != null)
			targetAccount = dao.query(Account.class,selectedAccountId);
		Set<Account> incomeAccounts = new HashSet<Account>();
		
		Map paraMap = new HashMap();
		Officer targetOfficer = (Officer)dao.query(Officer.class,targetId);
		String taregtOfficerName = targetOfficer.getPerson().getName();
		//合并后删除的人员
		paraMap.put("incomeOfficerIds", incomeIds);
		List<Officer> incomeOfficers = dao.query("select t from Officer t where t.id in (:incomeOfficerIds)", paraMap);
		Set<Person> otherPerson = new HashSet<Person>(); 
		//改待合并的对象涉及到的名字引用的地方全部用最新的目标名字来替代
		for (Officer o:incomeOfficers) {
			if(!o.getPerson().getName().equals(taregtOfficerName)){
				personService.updatePersonName(o.getPerson().getId(), taregtOfficerName);
			}	
			otherPerson.add(o.getPerson());
			incomeAccounts.add(personService.getAccountByPersonId(o.getId(), 1));
		}
		//改账号ID
		incomeAccounts.add(personService.getAccountByPersonId((String)targetId, 1));
		incomeAccounts.remove(targetAccount);
		incomeAccounts.remove(null);
		if(targetAccount!=null && !incomeAccounts.isEmpty()){
			beanFieldUtils.moveSubElementsToSingleTarget(targetAccount,new ArrayList<Account>(incomeAccounts),null,null,null);
		}
		//修改关联person的外键
		String targetPersonId = targetOfficer.getPerson().getId();
		Person targetPerson = (Person)dao.query(Person.class,targetPersonId);
		//修改银行账号和地址
		mergeAddressAndBankAccount(targetPerson, new ArrayList<Person>(otherPerson));
		//不对学术信息处理
		for (Person p:otherPerson) {
			if(p.getAcademic()!=null){
				dao.delete(p.getAcademic());
			}
		}
		beanFieldUtils.moveSubElementsToSingleTarget(targetPerson, new ArrayList<Person>(otherPerson), null, null, null);
		//修改关联管理人员的外键
		beanFieldUtils.moveSubElementsToSingleTarget(targetOfficer, incomeOfficers, null, null, null);
		
		dao.delete(incomeOfficers);
		dao.delete(otherPerson);
		
		//删除账号
		for (Account ac:incomeAccounts) {
			Passport pa = ac.getPassport();
			if(pa.getAccount().size()==1)
				dao.delete(pa);
			dao.delete(ac);
		}
		dao.flush();
		//在最后修改，防止重复belongId
		if(targetAccount!=null)
			targetAccount.setOfficer(targetOfficer);
			Person person = dao.query(Person.class, targetOfficer.getPerson().getId());
			targetAccount.setPerson(person);
	}


	public Officer getTmpOfficer() {
		return tmpOfficer;
	}


	public void setTmpOfficer(Officer tmpOfficer) {
		this.tmpOfficer = tmpOfficer;
	}

	
	
}
