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

import csdc.bean.Account;
import csdc.bean.Person;
import csdc.dao.IHibernateBaseDao;
import csdc.service.IPersonService;
import csdc.tool.bean.AccountType;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.BeanIdValueProcesser;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.beanutil.mergeStrategy.MergeStrategy;

/**
 * 合并人员
 * @author jintianfan
 *
 */
public class PersonMerger {
	@Autowired
	protected IPersonService personService;
	
	@Autowired
	protected IHibernateBaseDao dao;
	
	@Autowired
	protected BeanFieldUtils beanFieldUtils;	
	
	protected Person tmpPerson; //预合并后的人员
	
	private Map<String, MergeStrategy> msMap = new HashMap<String, MergeStrategy>();	//人员对象合并策略
	
		
	public PersonMerger() {
		msMap = initPersonMap();		
	}
	
	public Map<String, MergeStrategy> initPersonMap(){
		msMap = new HashMap<String, MergeStrategy>();
		msMap.put("name", BuiltinMergeStrategies.SUPPLY);
		msMap.put("englishName", BuiltinMergeStrategies.SUPPLY);
		msMap.put("usedName", BuiltinMergeStrategies.SUPPLY);
		msMap.put("photoFile", BuiltinMergeStrategies.SUPPLY);
		msMap.put("idcardType", BuiltinMergeStrategies.SUPPLY);
		msMap.put("idcardNumber", BuiltinMergeStrategies.SUPPLY);
		msMap.put("gender", BuiltinMergeStrategies.SUPPLY);
		msMap.put("countryRegion", BuiltinMergeStrategies.SUPPLY);
		msMap.put("ethnic", BuiltinMergeStrategies.SUPPLY);
		msMap.put("birthplace", BuiltinMergeStrategies.SUPPLY);
		msMap.put("birthday", BuiltinMergeStrategies.SUPPLY);
		msMap.put("membership", BuiltinMergeStrategies.SUPPLY);
		msMap.put("homeAddress", BuiltinMergeStrategies.SIMPLE_APPEND);
		msMap.put("homePhone", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("homePostcode", BuiltinMergeStrategies.SIMPLE_APPEND);
		msMap.put("homeFax", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("officeAddress", BuiltinMergeStrategies.SIMPLE_APPEND);
		msMap.put("officePhone", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("officePostcode", BuiltinMergeStrategies.SIMPLE_APPEND);
		msMap.put("officeFax", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("email", BuiltinMergeStrategies.APPEND);
		msMap.put("mobilePhone", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("qq", BuiltinMergeStrategies.APPEND);
		msMap.put("msn", BuiltinMergeStrategies.APPEND);
		msMap.put("homepage", BuiltinMergeStrategies.APPEND);
		msMap.put("introduction", BuiltinMergeStrategies.APPEND);
		msMap.put("bankAccount", BuiltinMergeStrategies.APPEND);
		msMap.put("cupNumber", BuiltinMergeStrategies.APPEND);
		msMap.put("bankName", BuiltinMergeStrategies.APPEND);
		msMap.put("bankBranch", BuiltinMergeStrategies.APPEND);
		msMap.put("bankAccountName", BuiltinMergeStrategies.APPEND);
		msMap.put("createType", BuiltinMergeStrategies.SUPPLY);
		msMap.put("isKey", BuiltinMergeStrategies.SUPPLY);
		return msMap;
	}
	
	
	/**
	 * 初始化学术信息合并策略
	 * @return
	 */
	public Map<String, MergeStrategy> initAcademicMap(){
		Map acMap =  new HashMap<String, MergeStrategy>();
		acMap.put("ethnicLanguage", BuiltinMergeStrategies.APPEND);
		acMap.put("language", BuiltinMergeStrategies.APPEND);
		acMap.put("disciplineType", BuiltinMergeStrategies.APPEND);
		acMap.put("discipline", BuiltinMergeStrategies.APPEND);
		acMap.put("relativeDiscipline", BuiltinMergeStrategies.APPEND);
		acMap.put("researchField", BuiltinMergeStrategies.APPEND);
		acMap.put("major", BuiltinMergeStrategies.APPEND);
		acMap.put("researchSpeciality", BuiltinMergeStrategies.APPEND);	
		acMap.put("furtherEducation", BuiltinMergeStrategies.APPEND);
		acMap.put("parttimeJob", BuiltinMergeStrategies.APPEND);	
		acMap.put("specialityTitle", BuiltinMergeStrategies.SUPPLY);
		acMap.put("positionLevel", BuiltinMergeStrategies.SUPPLY);	
		acMap.put("tutorType", BuiltinMergeStrategies.SUPPLY);		
		acMap.put("postdoctor", BuiltinMergeStrategies.SUPPLY);	
		acMap.put("isReviewer", BuiltinMergeStrategies.SUPPLY);
		acMap.put("reviewLevel", BuiltinMergeStrategies.SUPPLY);
		acMap.put("reviewScore", BuiltinMergeStrategies.SUPPLY);
		acMap.put("talent", BuiltinMergeStrategies.APPEND);
		acMap.put("lastEducation", BuiltinMergeStrategies.SUPPLY);
		acMap.put("lastDegree", BuiltinMergeStrategies.SUPPLY);
		acMap.put("expertType", BuiltinMergeStrategies.SUPPLY);
		acMap.put("countryRegion", BuiltinMergeStrategies.SUPPLY);
		acMap.put("degreeDate", BuiltinMergeStrategies.SUPPLY);
		acMap.put("computerLevel", BuiltinMergeStrategies.SUPPLY);
		return acMap;
	}
	
	/**
	 * 更新合并策略，用于默认合并策略不能满足要求的情况
	 * @param addMsMap
	 */
	public void addMergeStrategy(Map<String, MergeStrategy> addMsMap) {
		msMap.putAll(addMsMap);
	}
	
	
	/**
	 * 合并人员字段
	 * @param targetPersonId 目标人员
	 * @param incomePersonIds 要合并的人员（包含目标人员）
	 * @return 合并后的人员
	 * @throws IllegalArgumentException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Person mergePerson(Serializable targetPersonId, List<Serializable> incomePersonIds) throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		//合并后保留的人员
		Person targetPerson = (Person) dao.query(Person.class, targetPersonId);

		//合并后删除的人员
		Map paraMap = new HashMap();
		incomePersonIds.remove(targetPersonId);
		paraMap.put("incomePersonIds", incomePersonIds);
		List<Person> incomePerson = dao.query("select agency from Person agency where agency.id in (:incomePersonIds)", paraMap);
		
		mergePerson(targetPerson, incomePerson);
		return targetPerson;
	}
	
	/**
	 * 合并人员字段
	 * @param targetPerson 目标人员
	 * @param incomPerson 要合并的人员（不包含目标人员）
	 * @throws IntrospectionException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void mergePerson(Person targetPerson, List<Person> incomPerson) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		for (Person incomePerson : incomPerson) {
			for (String fieldName : msMap.keySet()) {
				beanFieldUtils.mergeField(targetPerson, incomePerson, fieldName, msMap.get(fieldName));
			}
		}
	}

	public Person getTmpPerson() {
		return tmpPerson;
	}

	public void setTmpPerson(Person tmpPerson) {
		this.tmpPerson = tmpPerson;
	}
	
}
