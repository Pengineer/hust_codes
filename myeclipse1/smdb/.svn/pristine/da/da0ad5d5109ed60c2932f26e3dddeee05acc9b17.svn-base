package csdc.tool.merger;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Address;
import csdc.bean.BankAccount;
import csdc.bean.Person;
import csdc.dao.IHibernateBaseDao;
import csdc.service.IPersonService;
import csdc.tool.SortT;
import csdc.tool.beanutil.BeanFieldUtils;
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
	
	protected Map<String, MergeStrategy> msMap = new HashMap<String, MergeStrategy>();//人员对象合并策略
	protected Map<String, MergeStrategy> acMap = new HashMap<String, MergeStrategy>();//学术信息字段合并策略
	protected Map<String, MergeStrategy> adMap = new HashMap<String, MergeStrategy>();//地址对象合并策略
	protected Map<String, MergeStrategy> baMap = new HashMap<String, MergeStrategy>();//银行账号对象合并策略
	
		
	public PersonMerger() {
		msMap = initPersonMap();
		adMap = initAddressMap();
		baMap = initBankAccountMap();
		acMap = initAcademicMap();
	}
	
	public Map<String, MergeStrategy> initPersonMap(){
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
		msMap.put("homePhone", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("homeFax", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("officePhone", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("officeFax", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("email", BuiltinMergeStrategies.APPEND);
		msMap.put("mobilePhone", BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
		msMap.put("qq", BuiltinMergeStrategies.APPEND);
		msMap.put("msn", BuiltinMergeStrategies.APPEND);
		msMap.put("homepage", BuiltinMergeStrategies.APPEND);
		msMap.put("introduction", BuiltinMergeStrategies.APPEND);
		msMap.put("createType", BuiltinMergeStrategies.SUPPLY);
		msMap.put("isKey", BuiltinMergeStrategies.SUPPLY);
		msMap.put("createMode", BuiltinMergeStrategies.SUPPLY);
		msMap.put("createDate", BuiltinMergeStrategies.SUPPLY);
		msMap.put("updateDate", BuiltinMergeStrategies.SUPPLY);
		return msMap;
	}
	
	//初始化地址对象合并策略
	public Map<String, MergeStrategy> initAddressMap(){
		
		return adMap;
	}
	
	//初始化银行账号对象合并策略
	public Map<String, MergeStrategy> initBankAccountMap(){
		
		return baMap;
	}
	
	//初始化学术信息对象合并策略
	public Map<String, MergeStrategy> initAcademicMap(){
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
	 * @throws Exception 
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
	
	/**
	 * 执行合并时处理银行账号和地址
	 * @param targetPerson 目标人员对象
	 * @param incomePerson 要合并的人员对象（不包含目标人员）
	 */
	public void mergeAddressAndBankAccount(Person targetPerson, List<Person> incomePerson) {
		List<Person> persons = new ArrayList<Person>();
		persons.addAll(incomePerson);
		persons.add(targetPerson);
		try {
			mergeAddress(targetPerson, persons);
			mergeBankAccount(targetPerson, persons);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//人员合并时对相关地址对象的处理
	private void mergeAddress(Person targetPerson, List<Person> persons) throws Exception{
		List<String> homeIds = new ArrayList<String>();
		List<String> officeIds = new ArrayList<String>();
		for(Person person:persons){
			List<String> home = dao.query("select a.id from Address a where a.ids = ?", person.getHomeAddressIds());
			homeIds.addAll(home);
			person.setHomeAddressIds(targetPerson.getHomeAddressIds());
			List<String> office = dao.query("select a.id from Address a where a.ids = ?", person.getOfficeAddressIds());
			officeIds.addAll(office);
			person.setOfficeAddressIds(targetPerson.getOfficeAddressIds());
			dao.modify(person);
		}
		Map map = new HashMap();
		map.put("homeIds", homeIds);
		map.put("officeIds", officeIds);
		List<Address> homeAddressList = new ArrayList<Address>();
		List<Address> officeAddressList = new ArrayList<Address>();
		SortT sort = new SortT();
		if(!homeIds.isEmpty()){
			homeAddressList = dao.query("select a from Address a where a.id in (:homeIds)", map);
			Map totalScoreMap1 = sort.objectSort(homeAddressList);
			Set set = new TreeSet(totalScoreMap1.keySet());
			Iterator it = set.iterator();
			for(int i=0; i<homeAddressList.size(); i++){
				int j = (Integer)totalScoreMap1.get(it.next());
				Address address = homeAddressList.get(j);
				address.setIds(targetPerson.getHomeAddressIds());
				address.setSn(set.size()-i);
				if(i==set.size()-1){
					address.setIsDefault(1);
				}else address.setIsDefault(0);
				dao.modify(address);
			}
		}
		if(!officeIds.isEmpty()){
			officeAddressList = dao.query("select a from Address a where a.id in (:officeIds)", map);
			Map totalScoreMap2 = sort.objectSort(officeAddressList);
			Set set = new TreeSet(totalScoreMap2.keySet());
			Iterator it = set.iterator();
			for(int i=0; i<officeAddressList.size(); i++){
				int j = (Integer)totalScoreMap2.get(it.next());
				Address address = officeAddressList.get(j);
				address.setIds(targetPerson.getOfficeAddressIds());
				address.setSn(set.size()-i);
				if(i==set.size()-1){
					address.setIsDefault(1);
				}else address.setIsDefault(0);
				dao.modify(address);
			}
		}
	}
	
	//人员合并时对相关银行账号对象的处理
	private void mergeBankAccount(Person targetPerson, List<Person> persons) throws Exception{
		List<String> bankIds = new ArrayList<String>();
		for(Person person:persons){
			List<String> bankId = dao.query("select ba.id from BankAccount ba where ba.ids = ?", person.getBankIds());
			bankIds.addAll(bankId);
			person.setBankIds(targetPerson.getBankIds());
			dao.modify(person);
		}
		Map map = new HashMap();
		map.put("bankIds", bankIds);
		List<BankAccount> bankAccountList = new ArrayList<BankAccount>();
		SortT sort = new SortT();
		if(!bankIds.isEmpty()){
			bankAccountList = dao.query("select ba from BankAccount ba where ba.id in (:bankIds)", map);
			Map totalScoreMap = sort.objectSort(bankAccountList);
			Set set = new TreeSet(totalScoreMap.keySet());
			Iterator it = set.iterator();
			for(int i=0; i<bankAccountList.size(); i++){
				int j = (Integer)totalScoreMap.get(it.next());
				BankAccount bankAccount = bankAccountList.get(j);
				bankAccount.setIds(targetPerson.getBankIds());
				bankAccount.setSn(set.size()-i);
				if(i==set.size()-1){
					bankAccount.setIsDefault(1);
				}else bankAccount.setIsDefault(0);
				dao.modify(bankAccount);
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
