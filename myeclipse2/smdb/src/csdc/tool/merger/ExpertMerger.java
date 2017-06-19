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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Academic;
import csdc.bean.Account;
import csdc.bean.Expert;
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.dao.IHibernateBaseDao;
import csdc.service.IPersonService;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.beanutil.mergeStrategy.MergeStrategy;
/**
 * 合并专家
 * 
 * @author jintf
 */
public class ExpertMerger extends PersonMerger  {
	
	private Expert tmpExpert; //预合并后的专家
	private Academic tmpAcademic; //预合并后的学术信息

	private Map<String, MergeStrategy> msMap = new HashMap<String, MergeStrategy>();	//Expert对象合并策略
	private Map<String, MergeStrategy> acMap = new HashMap<String, MergeStrategy>();   //Academic对象合并策略
	
	public ExpertMerger(){
		msMap.put("agencyName", BuiltinMergeStrategies.SUPPLY);
		msMap.put("divisionName", BuiltinMergeStrategies.SUPPLY);
		msMap.put("position", BuiltinMergeStrategies.SUPPLY);
		msMap.put("type", BuiltinMergeStrategies.SUPPLY);
		acMap = initAcademicMap();
	}
	
	/**
	 * 预合并专家
	 * @param targetId 目标专家ID
	 * @param incomeIds 要合并的专家ID（包含目标专家ID）
	 * @throws IllegalArgumentException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public void mergeExpert(Serializable targetId, List<Serializable> incomeIds) throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		Map paraMap = new HashMap();
		List<Serializable> incomePersonIds = new ArrayList<Serializable>();
		tmpExpert = (Expert) dao.query(Expert.class, targetId);
		paraMap.put("expertId", targetId);
		tmpAcademic = (Academic) dao.queryUnique("select a from Expert e left join e.person p left join p.academic a where e.id=:expertId",paraMap);
		if(tmpAcademic==null){
			tmpAcademic = new Academic();
		}
		//合并后删除的人员
		paraMap.clear();
		incomeIds.remove(targetId);
		paraMap.put("incomeIds", incomeIds);
		List<Expert> incomeExperts = dao.query("select o from Expert o where o.id in (:incomeIds)", paraMap);
		for (Expert incomeExpert : incomeExperts) {
			incomePersonIds.add(incomeExpert.getPerson().getId());
			for (String fieldName : msMap.keySet()) {
				beanFieldUtils.mergeField(tmpExpert, incomeExpert, fieldName, msMap.get(fieldName));
			}
		}
		List<Academic> incomeAcademics = dao.query("select a from Expert e left join e.person p left join p.academic a where e.id in (:incomeIds)",paraMap);
		for (Academic academic:incomeAcademics) {
			if(academic!=null){
				for (String fieldName : acMap.keySet()) {
					beanFieldUtils.mergeField(tmpAcademic, academic, fieldName, acMap.get(fieldName));
				}
			}
		}
		//合并人员表
		Serializable targetPersonId = tmpExpert.getPerson().getId();
		tmpPerson = mergePerson(targetPersonId, incomePersonIds);
	}
	
	 /**
	 * 执行专家的合并操作
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
		Expert targetExpert = (Expert)dao.query(Expert.class,targetId);
		String taregtExpertName = targetExpert.getPerson().getName();
		//合并后删除的人员
		paraMap.put("incomeIds", incomeIds);
		List<Expert> incomeExperts = dao.query("select t from Expert t where t.id in (:incomeIds)", paraMap);
		Set<Person> otherPerson = new HashSet<Person>();
		List<Academic> otherAcademics = new ArrayList<Academic>();
		//改名字
		for (Expert o:incomeExperts) {
			if(!o.getPerson().getName().equals(taregtExpertName)){
				personService.updatePersonName(o.getPerson().getId(), taregtExpertName);
			}	
			otherPerson.add(o.getPerson());
			if(o.getPerson().getAcademic() != null)
				otherAcademics.add(o.getPerson().getAcademic());
			incomeAccounts.add(personService.getAccountByPersonId(o.getPerson().getId(), 2));
		}
		//改账号ID
		incomeAccounts.add(personService.getAccountByPersonId(targetExpert.getPerson().getId(), 2));
		incomeAccounts.remove(targetAccount);
		incomeAccounts.remove(null);
		if(targetAccount!=null && !incomeAccounts.isEmpty()){
			beanFieldUtils.moveSubElementsToSingleTarget(targetAccount,new ArrayList<Account>(incomeAccounts),null,null,null);
		}
		//学术信息在之前已经合并，因为一对一的原因，因此必须删除
		dao.delete(otherAcademics);
		//修改关联person的外键
		String targetPersonId = targetExpert.getPerson().getId();
		Person targetPerson = (Person)dao.query(Person.class,targetPersonId);		
		beanFieldUtils.moveSubElementsToSingleTarget(targetPerson, new ArrayList<Person>(otherPerson), null, null, null);
		//修改关联管理人员的外键
		beanFieldUtils.moveSubElementsToSingleTarget(targetExpert, incomeExperts, null, null, null);
		dao.delete(incomeExperts);
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
			targetAccount.setPerson(targetExpert.getPerson());
	}


	

	public Expert getTmpExpert() {
		return tmpExpert;
	}

	public void setTmpExpert(Expert tmpExpert) {
		this.tmpExpert = tmpExpert;
	}

	public Academic getTmpAcademic() {
		return tmpAcademic;
	}

	public void setTmpAcademic(Academic tmpAcademic) {
		this.tmpAcademic = tmpAcademic;
	}
	
	
}
