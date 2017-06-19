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
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.bean.Student;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.beanutil.mergeStrategy.MergeStrategy;
/**
 * 合并学生
 * 
 * @author jintf
 */
public class StudentMerger extends PersonMerger  {
	
	private Student tmpStudent; //预合并后的学生	
	private Academic tmpAcademic; //预合并后的学术信息

	private Map<String, MergeStrategy> stMap = new HashMap<String, MergeStrategy>();//学生对象字段合并策略
	
	//添加学生表student中的相关字段
	public StudentMerger(){
		stMap.put("type", BuiltinMergeStrategies.SUPPLY);
		stMap.put("status", BuiltinMergeStrategies.SUPPLY);
		stMap.put("startDate", BuiltinMergeStrategies.SUPPLY);
		stMap.put("endDate", BuiltinMergeStrategies.SUPPLY);
		stMap.put("tutor", BuiltinMergeStrategies.SUPPLY);
		stMap.put("project", BuiltinMergeStrategies.SUPPLY);
		stMap.put("thesisTitle", BuiltinMergeStrategies.SUPPLY);
		stMap.put("studentCardNumber", BuiltinMergeStrategies.SUPPLY);
		stMap.put("isExcellent", BuiltinMergeStrategies.SUPPLY);
		stMap.put("excellentGrade", BuiltinMergeStrategies.SUPPLY);
		stMap.put("excellentYear", BuiltinMergeStrategies.SUPPLY);
		stMap.put("excellentSession", BuiltinMergeStrategies.SUPPLY);
		stMap.put("thesisFee", BuiltinMergeStrategies.SUPPLY);
		stMap.put("department", BuiltinMergeStrategies.SUPPLY);
		stMap.put("institute", BuiltinMergeStrategies.SUPPLY);
		stMap.put("university", BuiltinMergeStrategies.SUPPLY);
		stMap.put("agencyName", BuiltinMergeStrategies.SUPPLY);
		stMap.put("divisionName", BuiltinMergeStrategies.SUPPLY);
	}
	
	/**
	 * 预合并学生
	 * @param targetStudentId 目标学生ID
	 * @param incomeStudentIds 要合并的学生ID（包含目标学生ID）
	 * @throws IllegalArgumentException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public void mergeStudent(Serializable targetStudentId, List<Serializable> incomeStudentIds) throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		Map paraMap = new HashMap();
		List<Serializable> incomePersonIds = new ArrayList<Serializable>();
		//合并后保留的人员
		tmpStudent = (Student) dao.query(Student.class, targetStudentId);
		paraMap.put("expertId", targetStudentId);
		tmpAcademic = (Academic) dao.queryUnique("select a from Student e left join e.person p left join p.academic a where e.id=:expertId",paraMap);
		if(tmpAcademic==null){
			tmpAcademic = new Academic();
		}
		//合并后删除的人员
		paraMap.clear();
		incomeStudentIds.remove(targetStudentId);
		paraMap.put("incomeStudentIds", incomeStudentIds);
		List<Student> incomeStudents = dao.query("select o from Student o where o.id in (:incomeStudentIds)", paraMap);
		for (Student incomeStudent : incomeStudents) {
			incomePersonIds.add(incomeStudent.getPerson().getId());
			for (String fieldName : stMap.keySet()) {
				beanFieldUtils.mergeField(tmpStudent, incomeStudent, fieldName, stMap.get(fieldName));
			}
		}
		//合并学术信息
		List<Academic> incomeAcademics = dao.query("select a from Student e left join e.person p left join p.academic a where e.id in (:incomeStudentIds)",paraMap);
		for (Academic academic:incomeAcademics) {
			if(academic!=null){
				for (String fieldName : acMap.keySet()) {
					beanFieldUtils.mergeField(tmpAcademic, academic, fieldName, acMap.get(fieldName));
				}
			}
		}
		//合并人员表
		Serializable targetPersonId = tmpStudent.getPerson().getId();
		tmpPerson = mergePerson(targetPersonId, incomePersonIds);
	}
	
	/**
	 * 执行学生合并操作
	 * @param targetId 合并的目标学生ID
	 * @param incomeIds 要合并的学生ID （不包括targetId）
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
		
		incomeIds.remove(targetId);
		Map paraMap = new HashMap();
		Student targetStudent = (Student)dao.query(Student.class,targetId);
		String taregtStudentName = targetStudent.getPerson().getName();
		//合并后删除的人员
		incomeIds.remove(targetId);
		paraMap.put("incomeStudentIds", incomeIds);
		List<Student> incomeStudents = dao.query("select t from Student t where t.id in (:incomeStudentIds)", paraMap);
		Set<Person> otherPerson = new HashSet<Person>();
		List<Academic> otherAcademics = new ArrayList<Academic>();
		//改名字
		for (Student o:incomeStudents) {
			if(!o.getPerson().getName().equals(taregtStudentName)){
				personService.updatePersonName(o.getPerson().getId(), taregtStudentName);
			}
			otherPerson.add(o.getPerson());
			if(o.getPerson().getAcademic() != null)
				otherAcademics.add(o.getPerson().getAcademic());
			incomeAccounts.add(personService.getAccountByPersonId(o.getPerson().getId() ,4));
		}
		//改账号ID
		incomeAccounts.add(personService.getAccountByPersonId(targetStudent.getPerson().getId(), 4));
		incomeAccounts.remove(targetAccount);
		incomeAccounts.remove(null);
		if(targetAccount!=null && !incomeAccounts.isEmpty()){
			beanFieldUtils.moveSubElementsToSingleTarget(targetAccount,new ArrayList<Account>(incomeAccounts),null,null,null);
		}
		//学术信息在之前已经合并，因为一对一的原因，因此必须删除（最好移到action中）
		dao.delete(otherAcademics);
		//修改关联person的外键
		String targetPersonId = targetStudent.getPerson().getId();
		Person targetPerson = (Person)dao.query(Person.class,targetPersonId);
		otherPerson.remove(targetPerson);
		//修改银行账号和地址
		mergeAddressAndBankAccount(targetPerson, new ArrayList<Person>(otherPerson));
		beanFieldUtils.moveSubElementsToSingleTarget(targetPerson, new ArrayList<Person>(otherPerson), null, null, null);
		//修改关联管理人员的外键
		beanFieldUtils.moveSubElementsToSingleTarget(targetStudent, incomeStudents, null, null, null);
		dao.delete(incomeStudents);
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
			targetAccount.setPerson(targetStudent.getPerson());
	}


	public Student getTmpStudent() {
		return tmpStudent;
	}


	public void setTmpStudent(Student tmpStudent) {
		this.tmpStudent = tmpStudent;
	}


	public Academic getTmpAcademic() {
		return tmpAcademic;
	}

	public void setTmpAcademic(Academic tmpAcademic) {
		this.tmpAcademic = tmpAcademic;
	}
	
	
}
