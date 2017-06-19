package csdc.service.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.csdc.domain.fm.ThirdUploadForm;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Academic;
import csdc.bean.Account;
import csdc.bean.Address;
import csdc.bean.AwardApplication;
import csdc.bean.AwardReview;
import csdc.bean.BankAccount;
import csdc.bean.Expert;
import csdc.bean.Officer;
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.bean.Product;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectEndinspectionReview;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMember;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectVariation;
import csdc.bean.Student;
import csdc.bean.Teacher;
import csdc.service.IPersonService;
import csdc.service.ext.IPersonExtService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
/**
 * 人员管理service接口
 * @author 雷达
 *
 */
@SuppressWarnings("unchecked")
@Transactional
public class PersonService extends BaseService implements IPersonService, IPersonExtService {
	/**
	 * 判断系统是否有此身份证号码记录
	 * @param idcard 身份证号码
	 * @return
	 */
	public boolean checkIdcard(String idcard) {
		List list =  dao.query("select p.id from Person p where p.idcardNumber = '" + idcard + "' ");
		return list.size() == 0 ? false : true;
	}
	
	/**
	 * 根据证件号找到姓名
	 * @param idcard 身份证号码
	 * @return 姓名
	 */
	public String findNameByIdcard(String idcard){
		List list = dao.query("select p.name from Person p where LOWER(p.idcardNumber) = '" + idcard.trim().toLowerCase() + "' ");
		return list.isEmpty() ? null : (String)list.get(0);
	}

	/**
	 * 根据证件号找到人员
	 * @param idcard 身份证号码
	 * @return 人
	 */
	public Person findPersonByIdcard(String idcard){
		List list = dao.query("select p from Person p where LOWER(p.idcardNumber) = '" + idcard.trim().toLowerCase() + "' ");
		return list.isEmpty() ? null : (Person)list.get(0);
	}

	/**
	 * 根据证件号和姓名判断人员是否存在
	 * @param idcardNumber 身份证号码
	 * @param name 姓名
	 * @return 	第一个元素 -- 0:不存在 1:存在且匹配 2:存在且不匹配
	 * 			第二个元素 -- person对象
	 */
	public Object[] checkIfPersonExists(String idcardNumber, String name){
		List list = dao.query("select p from Person p where LOWER(p.idcardNumber) = '" + idcardNumber.trim().toLowerCase() + "' ");
		Object[] result = new Object[2];
		if (list.isEmpty()){
			result[0] = 0;
		} else {
			Person person = (Person) list.get(0);
			result[0] = person.getName().trim().equals(name.trim()) ? 1 : 2;
			if (result[0].equals(1)){
				result[1] = person;
			}
		}
		return result;
	}

	/**
	 * 根据姓名及证件号判断是否有此人（教师注册专用），此人是否已有账号
	 * @param idcardNumber证件号
	 * @param name姓名
	 * @return 	第一个元素——0：有人有账号 1：有人没账号 2：没人没账号 3：账号和证件号不匹配
	 * 			第二个元素——person对象
	 * 			第三个元素——teacher对象
	 */
	public Object[] checkPersonInfo(String idcardNumber, String name) {
		List<Object[]> list = dao.query("select p, t from Person p left join p.teacher t where LOWER(p.idcardNumber) = '" + idcardNumber.trim().toLowerCase() + "' ");
		Object[] result = new Object[3];
		if (list.isEmpty()){//没有证件号
			result[0] = 2;
		} else {//有证件号
			Person person = (Person) list.get(0)[0];
			if(!person.getName().trim().equals(name.trim())){
				result[0] = 3;
			} else{
				Teacher teacher = (Teacher) list.get(0)[1];
				List ac = dao.query("select ac from Account ac where ac.person.id = '" + person.getId() + "'");
				result[0] = (ac.isEmpty()) ? 1 : 0;
				if (!result[0].equals(2)){
					result[1] = person;
					result[2] = teacher;
				}
			}
		}
		return result;
	}
	
	/**
	 * 检查用户名是否可用
	 * @param username
	 * @return true可用，false不可用
	 */
	public boolean checkUsername(String username) {
		Map map = new HashMap();
		map.put("username", username);
		List re = dao.query("select ac.name from Account ac where ac.name = :username", map);
		return (re == null || re.isEmpty()) ? true : false;
	}
	
	/**
	 * 检查邮箱是否可用
	 * @param email
	 * @return true可用，false不可用
	 */
	public boolean checkEmail(String email) {
		Map map = new HashMap();
		map.put("email", email);
		List re = dao.query("select p.id from Person p where p.email = :email", map);
		return (re == null || re.isEmpty()) ? true : false;
	}
	
	/**
	 * 根据证件号和姓名判断人员是否存在、是否是管理人员
	 * @param idcardNumber 身份证号码
	 * @param name 姓名
	 * @param officerId 管理人员id
	 * @return 	第一个元素 -- 0:不存在 1:存在且匹配但无专职 2:存在且匹配且有专职 3:存在但不匹配（修改时去除自己）
	 * 			第二个元素 -- person对象
	 * 			第三个元素 -- officer对象
	 */
	public Object[] checkIfIsOfficer(String idcardNumber, String name, String officerId){
		List<Object[]> list0 = dao.query("select p, o from Person p left join p.officer o where LOWER(p.idcardNumber) = '" + idcardNumber.trim().toLowerCase() + "' " );
		List<Object[]> list = dao.query("select p, o from Person p left join p.officer o where LOWER(p.idcardNumber) = '" + idcardNumber.trim().toLowerCase() + "' and o.type = '专职人员' " );
		Object[] result = new Object[3];
		if (list0.isEmpty()){
			result[0] = 0;
		} else if (list.isEmpty()){
			Person person0 = (Person) list0.get(0)[0];
			result[0] = !person0.getName().equals(name) ? 3 : 1;
			result[1] = person0;
		} else {
			Person person0 = (Person) list0.get(0)[0];
			Person person = (Person) list.get(0)[0];
			Officer officer = (Officer) list.get(0)[1];
			result[0] = !person0.getName().equals(name) ? 3 : officer == null ? 1 : 2;
			if (result[0].equals(1)){
				result[1] = person;
				result[2] = officer;
			} else if (result[0].equals(2)){//如果有专职，则需判断当前修改的是否就是此专职
				result[0] = officer.getId().trim().equals(officerId) ? 1 : 2;
				result[1] = person;
			}
		}
		return result;
	}


	/**
	 * 根据证件号和姓名找到人员
	 * @param idcard 身份证号码
	 * @param name 姓名
	 * @return 人
	 */
	public Object findPersonByIdcardAndName(String idcard, String name){
		List list = dao.query("select p from Person p where LOWER(p.idcardNumber) = '" + idcard.trim().toLowerCase() + "' ");
		Map ret = new HashMap<String, Object>();
		if (list.isEmpty()){
			Person person = new Person();
			person.setName(name.trim().toLowerCase());
			person.setIdcardNumber(idcard.trim().toLowerCase());
			Academic academic = new Academic();
			ret.put("person", person);
			ret.put("academic", academic);
			return ret;
		} else {
			Person person = (Person) list.get(0);
			if (person.getName().equals(name.trim().toLowerCase())){
				List list1 = dao.query("select academic from Academic academic where academic.person.id = '" + person.getId() + "'");
				Academic academic = (Academic) (list1.isEmpty() ? null : list1.get(0));
				ret.put("person", person);
				ret.put("academic", academic);
				return ret;
			} else {
				return null;
			}
		}
	}

	/**
	 * 根据姓名、证件号判断是否已有专职officer
	 * @param personName 人员姓名
	 * @param idcardNumber 证件号
	 * @return 已有此人且有专职officer
	 */
	public boolean checkOfficerType(String personName, String idcardNumber){
		List list = dao.query("select p from Person p where LOWER(p.idcardNumber) = '" + idcardNumber.trim().toLowerCase() + "' ");
		if (!list.isEmpty()){
			Person person = (Person) list.get(0);
			if(person.getName().equals(personName)){
				Map parMap = new HashMap();
				parMap.put("personId", person.getId());
				String officerType = (String) dao.query("select o.type from Officer o where o.person.id =:personId", parMap).get(0);
				if(officerType.trim().equals("专职人员")){
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 根据ID号找到机构/院系/基地名
	 * @param id 机构/院系/基地ID
	 * @return 机构/院系/基地名
	 */
	public String findNameByADBId(String id, int type){
		List list;
		id = id.trim();
		switch (type) {
		case 0:
			list = dao.query("select a.name from Agency a where a.id='" + id + "'");
			return list.isEmpty() ? null : (String) list.get(0);
		case 1:
			list = dao.query("select d.name from Department d where d.id='" + id + "'");
			return list.isEmpty() ? null : (String) list.get(0);
		case 2:
			list = dao.query("select b.name from Base b where b.id='" + id + "'");
			return list.isEmpty() ? null : (String) list.get(0);
		default:
			break;
		}
		return null;
	}


	/**
	 * 查找某人的学术信息
	 * @param person
	 * @return 学术信息
	 */
	public Academic findAcademic(Person person){
		List list = dao.query("select academic from Academic academic where academic.person.id = '" + person.getId() + "'");
		return (Academic) (list.isEmpty() ? null : list.get(0));
	}

	/**
	 * 判断某人是否已经拥有专职教师职位
	 * @param idcardNumber 证件号
	 * @return 是:teacherID 否:null
	 */
	public String checkIfIsFulltimeTeacher(String idcardNumber) {
		Map paraMap = new HashMap();
		paraMap.put("idcardNumber", idcardNumber);
		List<String> list = dao.query("select t.id from Person p left join p.teacher t where p.idcardNumber = :idcardNumber and t.type = '专职人员' ", paraMap);
		return list.isEmpty() ? null : list.get(0);
	}
	
	//////////////////////////////////
	// 从系统选项表中获取下拉列表数据。
	//////////////////////////////////
	
	/**
	 * 将系统选项表中的专业职称的(name, description)组成list
	 * @param id
	 * @return
	 */
	public Map<String,String> getSpecialityTitleList(){
		Map<String,String> systemOption = Collections.synchronizedMap(new LinkedHashMap<String,String>());
		StringBuffer hql = new StringBuffer();
		hql.append("select sys.name, sys.description from SystemOption sys where sys.systemOption.standard='specialityTitle' and sys.systemOption.code is null order by sys.name ");
		List list=dao.query(hql.toString());
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				systemOption.put(((Object[])list.get(i))[0].toString(),((Object[])list.get(i))[1].toString());
			}
		}
		return systemOption;
	}
	
	/**
	 * 根据高校id获取院系列表(id,name)list
	 * @param id
	 * @return
	 */
	public Map<String,String> getDepartmentByUniversity(String universityId){
		Map<String,String> department = Collections.synchronizedMap(new LinkedHashMap<String,String>());
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select d.id, d.name from Department d where d.university.id = :id order by d.name ");
		map.put("id", universityId);
		List list=dao.query(hql.toString(),map);
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				department.put(((Object[])list.get(i))[0].toString(),((Object[])list.get(i))[1].toString());
			}
		}
		return department;
	}
	
	/**
	 * 获取民族列表
	 */
	public List<String> getEthnicList(){
		List<String> ethnicList = new ArrayList<String>();
		String hql = "select sys.name from SystemOption sys,SystemOption so where so.standard ='GB3304-91' and so.systemOption.id is null and sys.systemOption.id=so.id and sys.isAvailable = 1 order by sys.code ";
		List list = dao.query(hql);
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				ethnicList.add(list.get(i).toString());
			}
			return ethnicList;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取国家地区下拉列表
	 */
	public List<String> getCountryRegionList(){
		List<String> countryRegionList = new ArrayList<String>();
		String hql = "select sys.name from SystemOption sys,SystemOption so where so.standard ='ISO3166-1' and so.systemOption.id is null and sys.systemOption.id=so.id and sys.isAvailable = 1 order by sys.code ";
		List list = dao.query(hql);
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				countryRegionList.add(list.get(i).toString());
			}
			return countryRegionList;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取政治面貌下拉列表
	 */
	public List<String> getMembershipList(){
		List<String> membershipList = new ArrayList<String>();
		String hql = "select sys.name from SystemOption sys,SystemOption so where so.standard ='GB3304-91' and so.systemOption.id is null and sys.systemOption.id=so.id and sys.isAvailable = 1 order by sys.code ";
		List list = dao.query(hql);
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				membershipList.add(list.get(i).toString());
			}
			return membershipList;
		}else{
			return null;
		}
	}
	
//	public Map<String,String> getExpertTypeList(){
//		Map<String,String> systemOption = Collections.synchronizedMap(new LinkedHashMap<String,String>());
//		String hql = "select sys.id,sys.name from SystemOption sys where sys.systemOption.standard='expertType' and sys.systemOption.code is null order by sys.name ";
//		List list = dao.query(hql);
//		if(list !=null && list.size()>0){
//			for(int i=0;i<list.size();i++){
//				systemOption.put(((Object[])list.get(i))[0].toString(),((Object[])list.get(i))[1].toString());
//			}
//		}
//		return systemOption;
//	}

	// 证件类型
	public List<String> getIdcardTypeList(){
		List<String> idcardTypeList = new ArrayList<String>();
		String hql = "select sys.name from SystemOption sys where sys.systemOption.standard='idcardType' and sys.systemOption.code is null and sys.isAvailable = 1 order by sys.name ";
		List list = dao.query(hql);
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				idcardTypeList.add(list.get(i).toString());
			}
			return idcardTypeList;
		}else{
			return null;
		}
	}
	
	// 学历
	public List<String> getEducationBackgroundList(){
		List<String> educationBackgroundList = new ArrayList<String>();
		String hql = "select sys.name from SystemOption sys where sys.systemOption.standard='educationBackground' and sys.systemOption.code is null and sys.isAvailable = 1 order by sys.name ";
		List list = dao.query(hql);
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				educationBackgroundList.add(list.get(i).toString());
			}
			return educationBackgroundList;
		}else{
			return null;
		}
	}
	
	// 学位
	public List<String> getDegreeList(){
		List<String> degreeList = new ArrayList<String>();
		String hql = "select sys.name from SystemOption sys where sys.systemOption.standard='degree' and sys.systemOption.code is null and sys.isAvailable = 1 order by sys.name ";
		List list = dao.query(hql);
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				degreeList.add(list.get(i).toString());
			}
			return degreeList;
		}else{
			return null;
		}
	}
	
	// 学生类别
	public List<String> getStudentTypeList(){
		List<String> studentTypeList = new ArrayList<String>();
		String hql = "select sys.name from SystemOption sys where sys.systemOption.standard='studentType' and sys.systemOption.code is null and sys.isAvailable = 1 order by sys.name ";
		List list = dao.query(hql);
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				studentTypeList.add(list.get(i).toString());
			}
			return studentTypeList;
		}else{
			return null;
		}
	}
	
	// 学生状态
	public List<String> getStudentStatusList(){
		List<String> studentStatusList = new ArrayList<String>();
		String hql = "select sys.name from SystemOption sys where sys.systemOption.standard='studentStatus' and sys.systemOption.code is null and sys.isAvailable = 1 order by sys.name ";
		List list = dao.query(hql);
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				studentStatusList.add(list.get(i).toString());
			}
			return studentStatusList;
		}else{
			return null;
		}
	}
	
	// 优秀学位论文等级
	public List<String> getExcellentGradeList(){
		List<String> excellentGradeList = new ArrayList<String>();
		String hql = "select sys.name from SystemOption sys where sys.systemOption.standard='excellentGrade' and sys.systemOption.code is null and sys.isAvailable = 1 order by sys.name ";
		List list = dao.query(hql);
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				excellentGradeList.add(list.get(i).toString());
			}
			return excellentGradeList;
		}else{
			return null;
		}
	}

	
	// 导师类型
	public List<String> getTutorTypeList(){
		List<String> tutorTypeList = new ArrayList<String>();
		String hql = "select sys.name from SystemOption sys where sys.systemOption.standard='tutorType' and sys.systemOption.code is null and sys.isAvailable = 1 order by sys.name ";
		List list = dao.query(hql);
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				tutorTypeList.add(list.get(i).toString());
			}
			return tutorTypeList;
		}else{
			return null;
		}
	}
	
	/**
	 * 修改人员
	 * @param person
	 * @param origin_person
	 * @return 人员id
	 */
	public String modifyPerson(Person person, Person origin_person) {
		origin_person.setName(person.getName());
		origin_person.setEnglishName(person.getEnglishName());
		origin_person.setUsedName(person.getUsedName());
		origin_person.setPhotoFile(person.getPhotoFile());
		origin_person.setGender(person.getGender());
		origin_person.setCountryRegion(person.getCountryRegion());
		origin_person.setEthnic(person.getEthnic());
		origin_person.setBirthplace(person.getBirthplace());
		origin_person.setBirthday(person.getBirthday());
		origin_person.setMembership(person.getMembership());
		origin_person.setHomePhone(person.getHomePhone());
		origin_person.setOfficePhone(person.getOfficePhone());
		origin_person.setOfficeFax(person.getOfficeFax());
		origin_person.setEmail(person.getEmail());
		origin_person.setMobilePhone(person.getMobilePhone());
		origin_person.setQq(person.getQq());
		origin_person.setMsn(person.getMsn());
		origin_person.setHomepage(person.getHomepage());
		origin_person.setIdcardType(person.getIdcardType());
		origin_person.setIdcardNumber(person.getIdcardNumber());
		origin_person.setIntroduction(person.getIntroduction());
		origin_person.setHomeFax(person.getHomeFax());
		origin_person.setCreateType(person.getCreateType());
		origin_person.setUpdateDate(new Date());
		dao.modify(origin_person);
		return origin_person.getId();
	}
	
	/**
	 * 删除人员
	 * @param type 1管理员 2专家 3教师 4学生
	 * @param entityIds ID集合
	 * @return int 0 成功删除 1 因为关联人员没成功删除 2 机构 3 项目 4 成果 5 奖励 6 其他
	 * @author 余潜玉 周中坚（去除人员表中的accountId字段）
	 */
	public int deletePerson(int type, List<String> entityIds){
		String personid = "";
		Map map = new HashMap();
		switch(type){
		case 1:{//管理人员
			for(int i=0;i<entityIds.size();i++){
				Officer officer = (Officer)dao.query(Officer.class,entityIds.get(i));
				personid = officer.getPerson().getId();
				map.put("personid",personid);
				int flag = this.canDelete(1, personid, map);
				if(flag != 0){
					return flag;
				}
			}
			//所有选中人员都能删除才能执行以下操作
			for(int i=0;i<entityIds.size();i++){
				Officer officer = (Officer)dao.query(Officer.class,entityIds.get(i));
				personid = officer.getPerson().getId();
				map.put("personid",personid);
				List es = dao.query("select e from Expert e where e.person.id=:personid",map);
				List ts = dao.query("select t from Teacher t where t.person.id=:personid",map);
				List ss = dao.query("select s from Student s where s.person.id=:personid",map);
				map.put("officerid", entityIds.get(i));
				List os = dao.query("select o from Officer o where o.person.id=:personid and o.id !=:officerid",map);//主要判断多个officer对应一个person时，只删除officer
				List<String> acids = this.getAccountByPerson(entityIds.get(i), 1);
				if(!acids.isEmpty()){
					this.deleteAccount(acids);
				}
				if(ss.size()==0 && es.size()==0 && os.size()==0 && ts.size()==0){//若该人员主表无人员子表删除人员主表
					dao.delete(officer);
					Person person = dao.query(Person.class, personid);
					deleteAddress(person);
					deleteBank(person);
					dao.delete(person.getAcademicEntity());
					dao.delete(person);
					deletePersonPhotoFile(person);
				}else{
					dao.delete(officer);
				}
			}
			break;
		}	
		case 2:{//外部专家
			for(int i=0;i<entityIds.size();i++){
				Expert expert = (Expert)dao.query(Expert.class,entityIds.get(i));
				personid = expert.getPerson().getId();
				map.put("personid",personid);
				int flag = this.canDelete(2, personid, map);
				if(flag != 0){
					return flag;
				}
			}
			//所有选中人员都能删除才能执行以下操作
			for(int i=0;i<entityIds.size();i++){
				Expert expert = (Expert)dao.query(Expert.class,entityIds.get(i));
				personid = expert.getPerson().getId();
				map.put("personid",personid);
				List ss = dao.query("select s from Student s where s.person.id=:personid",map);
				List ts = dao.query("select t from Teacher t where t.person.id=:personid",map);
				List os = dao.query("select o from Officer o where o.person.id=:personid",map);
				map.put("expertid", entityIds.get(i));
				List es = dao.query("select e from Expert e where e.person.id=:personid and e.id !=:expertid",map);
				List<String> acids = this.getAccountByPerson(personid, 2);
				if(!acids.isEmpty()){
					this.deleteAccount(acids);
				}
				if(ss.size()==0 && es.size()==0 && os.size()==0 && ts.size()==0){//若该人员主表无人员子表删除主表
					dao.delete(expert);
					List<Academic> academics = dao.query("from Academic a where a.person.id = ?",personid);
					if(!academics.isEmpty()){
						dao.delete(academics);
					}
					Person person = dao.query(Person.class, personid);
					deleteAddress(person);
					deleteBank(person);
					dao.delete(person);
					deletePersonPhotoFile(person);
				}else{
					dao.delete(expert);
				}
			}	
			break;
		}
		case 3:{//教师
			for(int i=0;i<entityIds.size();i++){
				Teacher teacher = (Teacher)dao.query(Teacher.class,entityIds.get(i));
				personid = teacher.getPerson().getId();
				map.put("personid",personid);
				int flag = this.canDelete(3, personid, map);
				if(flag != 0){
					return flag;
				}
			}
			//所有选中人员都能删除才能执行以下操作
			for(int i=0;i<entityIds.size();i++){
				Teacher teacher = (Teacher)dao.query(Teacher.class,entityIds.get(i));
				personid = teacher.getPerson().getId();
				map.put("personid",personid);
				List ss = dao.query("select s from Student s where s.person.id=:personid",map);
				List es = dao.query("select e from Expert e where e.person.id=:personid",map);
				List os = dao.query("select o from Officer o where o.person.id=:personid",map);
				map.put("teacherid", entityIds.get(i));
				List ts = dao.query("select t from Teacher t where t.person.id=:personid and t.id !=:teacherid",map);
				List<String> acids = this.getAccountByPerson(personid, 3);
				if(!acids.isEmpty()){
					this.deleteAccount(acids);
				}
				if(ss.size()==0 && es.size()==0 && os.size()==0 && ts.size()==0){//若该人员主表无人员子表
					dao.delete(teacher);
					List<Academic> academics = dao.query("from Academic a where a.person.id = ?",personid);
					if(!academics.isEmpty()){
						dao.delete(academics);
					}
					Person person = dao.query(Person.class, personid);
					deleteAddress(person);
					deleteBank(person);
					dao.delete(person);
					deletePersonPhotoFile(person);
				}else{
					dao.delete(teacher);
				}
			}
			break;
		}
		case 4:{//学生
			for(int i=0;i<entityIds.size();i++){
				Student student = (Student)dao.query(Student.class,entityIds.get(i));
				personid = student.getPerson().getId();
				map.put("personid",personid);
				int flag = this.canDelete(4, personid, map);
				if(flag != 0){
					return flag;
				}
			}
			//所有选中人员都能删除才能执行以下操作
			for(int i=0;i<entityIds.size();i++){
				Student student = (Student)dao.query(Student.class,entityIds.get(i));
				personid = student.getPerson().getId();
				List es = dao.query("select e from Expert e where e.person.id=:personid",map);
				List ts = dao.query("select t from Teacher t where t.person.id=:personid",map);
				List os = dao.query("select o from Officer o where o.person.id=:personid",map);
				map.put("studentid", entityIds.get(i));
				List ss = dao.query("select s from Student s where s.person.id=:personid and s.id !=:studentid",map);
				List<String> acids = this.getAccountByPerson(personid, 4);
				if(!acids.isEmpty()){
					this.deleteAccount(acids);
				}
				if(ss.size()==0 && es.size()==0 && os.size()==0 && ts.size()==0){//若该人员主表无人员子表
					dao.delete(student);
					List<Academic> academics = dao.query("from Academic a where a.person.id = ?",personid);
					if(!academics.isEmpty()){
						dao.delete(academics);
					}
					Person person = dao.query(Person.class, personid);
					deleteAddress(person);
					deleteBank(person);
					dao.delete(person);
					deletePersonPhotoFile(person);
				}else{
					dao.delete(student);
				}
			}
			break;
		}
		}
		return 0;
	}
	
	/**
	 * 删除人员照片
	 * @param person
	 */
	public void deletePersonPhotoFile(Person person) {
		if(person.getPhotoFile() != null && !person.getPhotoFile().isEmpty()) {
			FileTool.fileDelete(person.getPhotoFile());
		}
		if(person.getPhotoDfs() != null && !person.getPhotoDfs().isEmpty() && dmssService.getStatus()) {
			dmssService.deleteFile(person.getPhotoDfs());
		}
	}
	
	/**
	 * 根据人员id和人员类型获得账号id
	 * @param entityId personId
	 * @param personType 1:officer;2:expert;3:teacher;4:student.
	 * @return List<String> acids
	 * @author 周中坚
	 */
	public List<String> getAccountByPerson(String entityId, int personType) {
		List<String> acids = null;
		Map map = new HashMap();
		map.put("entityId", entityId);
		switch (personType) {
			case 1:{//管理人员
				acids = dao.query("select ac.id from Account ac where ac.officer.id =:entityId and ac.type !='EXPERT' and ac.type != 'TEACHER' and  ac.type !='STUDENT' and ac.isPrincipal = 0", map);
			}
				break;
			case 2:{//外部专家
				acids = dao.query("select ac.id from Account ac where ac.person.id =:entityId and ac.type = 'EXPERT' and ac.isPrincipal = 1", map);
				}
				break;
			case 3:{//教师
				acids = dao.query("select ac.id from Account ac where ac.person.id =:entityId and ac.type = 'TEACHER' and ac.isPrincipal = 1", map);
				}
				break;
			case 4:{//学生
				acids = dao.query("select ac.id from Account ac where ac.person.id =:entityId and ac.type = 'STUDENT' and ac.isPrincipal = 1", map);
				}
				break;
			default:
				break;
			}
		return acids;
	}
	/**
	 * 根据人员id和人员类型获得账号id
	 * @param entityId 1:officerId;2/3/4:personId
	 * @param personType 1:officer;2:expert;3:teacher;4:student.
	 * @return List<String> acids
	 * @author 周中坚
	 */
	public List<String> getAccountByEntityId(String entityId, int personType) {
		List<String> acids = null;
		Map map = new HashMap();
		map.put("entityId", entityId);
		switch (personType) {
			case 1:{//管理人员
				acids = dao.query("select ac.id from Account ac where ac.officer.id =:entityId and ac.type !='EXPERT' and ac.type != 'TEACHER' and  ac.type !='STUDENT' and ac.isPrincipal = 0", map);
			}
				break;
			case 2:{//外部专家
				// 专家账号传过来的是expertId，但是要存personId
				Person person = (Person) dao.query("select p from Person p, Expert e where p.id = e.person.id and e.id = :entityId", map).get(0);
				// 所属ID更新为personId
				String personId = person.getId();
				map.put("personId", personId);
				acids = dao.query("select ac.id from Account ac where ac.person.id =:personId and ac.type = 'EXPERT' and ac.isPrincipal = 1", map);
				}
				break;
			case 3:{//教师
				// 教师账号传过来的是teacherId，但是要存personId
				try {
					Person person = (Person) dao.query("select p from Person p, Teacher t where p.id = t.person.id and t.id = :entityId", map).get(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Person person = (Person) dao.query("select p from Person p, Teacher t where p.id = t.person.id and t.id = :entityId", map).get(0);
				// 所属ID更新为personId
				String personId = person.getId();
				map.put("personId", personId);
				acids = dao.query("select ac.id from Account ac where ac.person.id =:personId and ac.type = 'TEACHER' and ac.isPrincipal = 1", map);
				}
				break;
			case 4:{//学生
				// 学生账号传过来的是studentId，但是要存personId
				Person person = (Person) dao.query("select p from Person p, Student s where p.id = s.person.id and s.id = :entityId", map).get(0);
				// 所属ID更新为personId
				String personId = person.getId();
				map.put("personId", personId);
				acids = dao.query("select ac.id from Account ac where ac.person.id =:personId and ac.type = 'STUDENT' and ac.isPrincipal = 1", map);
				}
				break;
			default:
				break;
			}
		return acids;
	}
	
	/**
	 * 根据人员id和人员类型获得通行证id
	 * @param entityId 1:officerId;2/3/4:personId
	 * @param personType 1:officer;2:expert;3:teacher;4:student.
	 * @return List<String> acids
	 * @author 杨发强
	 */
	public List<String> getPassportByPersonId(String entityId, int personType) {
		List<String> passportIds = null;
		Map map = new HashMap();
		map.put("entityId", entityId);
		switch (personType) {
			case 1:{//管理人员
				List<String> personIds = dao.query("select p.id from Officer o left join o.person p where o.id = :entityId", map);
				map.remove(entityId);
				String personId = personIds.get(0);
				map.put("personId", personId);
			}
				break;
			case 2:{//外部专家
				// 专家账号传过来的是expertId，但是要存personId
				Person person = (Person) dao.query("select p from Person p, Expert e where p.id = e.person.id and e.id = :entityId", map).get(0);
				// 所属ID更新为personId
				String personId = person.getId();
				map.put("personId", personId);
				}
				break;
			case 3:{//教师
				// 教师账号传过来的是teacherId，但是要存personId
				Person person = (Person) dao.query("select p from Person p, Teacher t where p.id = t.person.id and t.id = :entityId", map).get(0);
				// 所属ID更新为personId
				String personId = person.getId();
				map.put("personId", personId);
				}
				break;
			case 4:{//学生
				// 学生账号传过来的是studentId，但是要存personId
				Person person = (Person) dao.query("select p from Person p, Student s where p.id = s.person.id and s.id = :entityId", map).get(0);
				// 所属ID更新为personId
				String personId = person.getId();
				map.put("personId", personId);
				}
				break;
			default:
				break;
			}
		passportIds = dao.query("select distinct pp.id from Account a left join a.passport pp where a.person.id  = :personId", map);
		return passportIds;
	}
	
	/**
	 * 合并时，初始化可选账号下拉选框
	 * @param checkedIds 可选的人员ID(1:officerId,2:expertId,3:teacherId,4:studentId)
	 * @param personType 人员类型  (1:officer;2:expert;3:teacher;4:student.)
	 */
	public Map getOptionalPassportNames(String checkedIds,Integer personType) {
		Map<String, String> map = new HashMap<String, String>();
		List<String> incomeIds = new ArrayList<String>();
		Matcher matcher = Pattern.compile("\\w+").matcher(checkedIds);
		while (matcher.find()) {			
			switch (personType) {
			case 1:
				incomeIds.add(matcher.group());
				break;
			case 2:
				incomeIds.add((String)dao.queryUnique("select p.id from Expert t left join t.person p where  t.id=?",matcher.group()));
				break;
			case 3:
				incomeIds.add((String)dao.queryUnique("select p.id from Teacher t left join t.person p where t.type='专职人员' and t.id=?",matcher.group()));
				break;
			case 4:
				incomeIds.add((String)dao.queryUnique("select p.id from Student t left join t.person p where  t.id=?",matcher.group()));
				break;
			default:
				break;
			}
		}
		
		for(String officerId:incomeIds){
			List<String> acids = getAccountByPerson(officerId, personType);
			if(acids!=null && !acids.isEmpty()){
				Account ac = (Account) dao.query(Account.class, acids.get(0));				
				Passport passport = (Passport) dao.query(Passport.class, ac.getPassport().getId());
				if(passport!=null)
					map.put(ac.getId(), passport.getName());
			}
		}
		return map;
	}
	
	/**
	 * 根据人员id和人员类型获得账号
	 * @param entityId 1:officerId;2/3/4:personId
	 * @param type 1:管理人员； 2：外部专家； 3：教师； 4： 学生
	 * @return List<String> acids
	 */
	public Account getAccountByPersonId(String entityId, int type) {
		Account account = null;
		Map map = new HashMap();
		map.put("entityId", entityId);
		if (type == 1) {
			account = (Account) dao.queryUnique("select ac from Account ac where ac.officer.id =:entityId  ", map);
		} else {
			account = (Account) dao.queryUnique("select ac from Account ac where ac.person.id =:entityId  ", map);
		}
		return account;
	}
	
	/**
	 * 根据人员id和人员类型获得通信证
	 * @param entityId 1:officerId;2/3/4:personId
	 * @param personType 1:officer;2:expert;3:teacher;4:student.
	 * @return 通行证
	 * @author 金天凡
	 */
	public Passport getPassportByPerson(String entityId, int personType){
		Passport passport = null;
		List<String> acids = getAccountByPerson(entityId, 1);
		if(!acids.isEmpty()){
			Account account = (Account) dao.query(Account.class, acids.get(0));
			passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
		} 
		return passport;
	}
	
	/**
	 *根据personid判断该人员是否存在子记录
	 * @param type	1:管理人员	2：研究人员
	 * @param personid
	 * @return int: 0 能够删除 1 存在人员 2 存在机构 3 存在项目 4 存在成果 5 存在奖励 6 其他
	 * map hql查询参数
	 * @author 余潜玉 周中坚（添加基地及中后期，修改负责人判断）
	 */
	public int canDelete(int type, String personid, Map map){
		if(type==1){
			//查看是否存在相关联的机构信息
			List ages = dao.query("select age.id from Agency age where age.director.id=:personid or age.sdirector.id=:personid or age.slinkman.id=:personid",map);
			List deps = dao.query("select dep.id from Department dep where dep.director.id=:personid or dep.linkman.id=:personid",map);
			List inss = dao.query("select ins.id from Institute ins where ins.director.id=:personid or ins.linkman.id=:personid",map);
			List subs = dao.query("select sub.id from SubInstitute sub where sub.director.id=:personid",map);
			if(ages.size() + deps.size() + inss.size() + subs.size() > 0){//存在机构相关联子表
				return 2;
			} else{
				//查看是否存在相关联的项目
				List paps = dao.query("select pap.id from ProjectApplication pap where pap.applicantId like :personid or pap.deptInstAuditor.id=:personid or pap.universityAuditor.id=:personid or pap.provinceAuditor.id=:personid or pap.ministryAuditor.id=:personid or pap.reviewer.id=:personid or pap.finalAuditor.id=:personid",map);
				List pmes = dao.query("select pme.id from ProjectMember pme where pme.member.id=:personid",map);
				List pvas = dao.query("select pva.id from ProjectVariation pva where pva.deptInstAuditor.id=:personid or pva.universityAuditor.id=:personid or pva.provinceAuditor.id=:personid or pva.finalAuditor.id=:personid",map);
				List pers = dao.query("select per.id from ProjectEndinspectionReview per where per.reviewer.id=:personid",map);
				List pmds = dao.query("select pmd.id from ProjectMidinspection pmd where pmd.deptInstAuditor.id=:personid or pmd.universityAuditor.id=:personid or pmd.provinceAuditor.id=:personid or pmd.finalAuditor.id=:personid",map);
				List peds =	dao.query("select ped.id from ProjectEndinspection ped where ped.deptInstAuditor.id=:personid or ped.universityAuditor.id=:personid or ped.provinceAuditor.id=:personid or ped.ministryAuditor.id=:personid or ped.reviewer.id=:personid or ped.finalAuditor.id=:personid", map);
				if(paps.size() + pmes.size() + pvas.size() + pers.size() + pmds.size() + peds.size() > 0){//存在相关联的项目信息
					return 3;
				} else{
					//查看是否存在相关联的奖励
					List aaps = dao.query("select aap.id from AwardApplication aap where aap.applicant.id=:personid or aap.deptInstAuditor.id=:personid or aap.universityAuditor.id=:personid or aap.provinceAuditor.id=:personid or aap.ministryAuditor.id=:personid or aap.reviewer.id=:personid or aap.reviewAuditor.id=:personid or aap.finalAuditor.id=:personid",map);
					List ares = dao.query("select are.id from AwardReview are where are.reviewer.id=:personid",map);
					if(aaps.size() + ares.size() > 0){
						return 5;
					}else{
						//其他
						List cmes = dao.query("select cme from CommitteeMember cme where cme.member.id=:personid",map);
						if(cmes.size()>0){
							return 6;
						}
					}
				}
			}
		} else{
			//查看是否存在相关联的项目
			List paps = dao.query("select pap.id from ProjectApplication pap where pap.applicantId like :personid or pap.deptInstAuditor.id=:personid or pap.universityAuditor.id=:personid or pap.provinceAuditor.id=:personid or pap.ministryAuditor.id=:personid or pap.reviewer.id=:personid or pap.finalAuditor.id=:personid",map);
			List pmes = dao.query("select pme.id from ProjectMember pme where pme.member.id=:personid",map);
			List pvas = dao.query("select pva.id from ProjectVariation pva where pva.deptInstAuditor.id=:personid or pva.universityAuditor.id=:personid or pva.provinceAuditor.id=:personid or pva.finalAuditor.id=:personid",map);
			List pers = dao.query("select per.id from ProjectEndinspectionReview per where per.reviewer.id=:personid",map);
			List pmds = dao.query("select pmd.id from ProjectMidinspection pmd where pmd.deptInstAuditor.id=:personid or pmd.universityAuditor.id=:personid or pmd.provinceAuditor.id=:personid or pmd.finalAuditor.id=:personid",map);
			List peds =	dao.query("select ped.id from ProjectEndinspection ped where ped.deptInstAuditor.id=:personid or ped.universityAuditor.id=:personid or ped.provinceAuditor.id=:personid or ped.ministryAuditor.id=:personid or ped.reviewer.id=:personid or ped.finalAuditor.id=:personid", map);
			if(paps.size() + pmes.size() + pvas.size() + pers.size() + pmds.size() + peds.size() > 0){//存在相关联的项目信息
				return 3;
			}else{
				//查看是否存在相关联的成果
				List prods = dao.query("select prod.id from Product prod where prod.author.id=:personid or prod.auditor.id=:personid",map);
				if(prods.size() > 0){//存在相关连成果信息
					return 4;
				}else{
					//查看是否存在相关联的奖励
					List aaps = dao.query("select aap.id from AwardApplication aap where aap.applicant.id=:personid or aap.deptInstAuditor.id=:personid or aap.universityAuditor.id=:personid or aap.provinceAuditor.id=:personid or aap.ministryAuditor.id=:personid or aap.reviewer.id=:personid or aap.reviewAuditor.id=:personid or aap.finalAuditor.id=:personid",map);
					List ares = dao.query("select are.id from AwardReview are where are.reviewer.id=:personid",map);
					if(aaps.size() + ares.size() > 0){
						return 5;
					}else{
						List ages = dao.query("select age.id from Agency age where age.director.id=:personid or age.sdirector.id=:personid or age.slinkman.id=:personid",map);
						List deps = dao.query("select dep.id from Department dep where dep.director.id=:personid or dep.linkman.id=:personid",map);
						List inss = dao.query("select ins.id from Institute ins where ins.director.id=:personid or ins.linkman.id=:personid",map);
						List subs = dao.query("select sub.id from SubInstitute sub where sub.director.id=:personid",map);
						if(ages.size() + deps.size() + inss.size() + subs.size() > 0){//存在机构相关联子表
							return 2;
						}else{
							//其他
							List cmes = dao.query("select cme from CommitteeMember cme where cme.member.id=:personid",map);
							if(cmes.size()>0){
								return 6;
							}
						}
					}
				}
			}
		}
		return 0;
	}
	
	/**
	 * 姓名变化时，维护项目成果奖励中存的人员姓名冗余信息
	 * @param id 人员id
	 * @param name 人员新姓名
	 * 需要检查的表如下
	 * generalApplication	generalMidinspection	generalEndinspection	generalMember	generalEndinspectionReview
	 * generalVariation	instpApplication	instpMidinspection	instpEndinspection	instpVariation	instpMember	instpEndinspectionReview
	 * postApplication	postEndinspection	postVariation	postMember	postEndinspectionReview
	 * awardApplication	awardReview
	 */
	public void updatePersonName(String id, String name){
		Map map = new HashMap();
		map.put("id", "%" + id + "%");
		//修改项目申请表申请人和各级审核人评审人姓名
		List<ProjectApplication> ListPA = dao.query("select papp from ProjectApplication papp " +
				"left join papp.deptInstAuditor ia left join papp.universityAuditor ua left join " +
				"papp.provinceAuditor pa left join papp.ministryAuditor ma left join " +
				"papp.reviewer r left join papp.finalAuditor " +
				"fa where papp.applicantId like :id or ia.id like :id or ua.id like :id or " +
				"pa.id like :id or ma.id like :id or r.id like :id or " +
				"fa.id like :id", map);
		/*List<ProjectApplication> ListPA = dao.query("select pap from ProjectApplication pap " +
				"where pap.applicantId like :id or pap.deptInstAuditor.id=:id " +
				"or pap.universityAuditor.id=:id or pap.provinceAuditor.id=:id " +
				"or pap.ministryAuditor.id=:id or pap.reviewer.id=:id " +
				"or pap.finalAuditor.id=:id",map);*/
		for (int i = 0; i < ListPA.size(); i++) {
			ProjectApplication papp = ListPA.get(i);
			if(!papp.getApplicantId().equals(null)){
				String ids1 = papp.getApplicantId();
				String names1 = papp.getApplicantName();
				papp.setApplicantName(this.updateSubName(id, name, ids1, names1));
			}
			if(papp.getDeptInstAuditor() != null){
				String ids2 = papp.getDeptInstAuditor().getId();
				String names2 = papp.getDeptInstAuditorName();
				papp.setDeptInstAuditorName(this.updateSubName(id, name, ids2, names2));
			}
			if(papp.getUniversityAuditor() != null){
				String ids3 = papp.getUniversityAuditor().getId();
				String names3 = papp.getUniversityAuditorName();
				papp.setUniversityAuditorName(this.updateSubName(id, name, ids3, names3));
			}
			if(papp.getProvinceAuditor() != null){
				String ids4 = papp.getProvinceAuditor().getId();
				String names4 = papp.getProvinceAuditorName();
				papp.setProvinceAuditorName(this.updateSubName(id, name, ids4, names4));
			}
			if(papp.getMinistryAuditor() != null){
				String ids5 = papp.getMinistryAuditor().getId();
				String names5 = papp.getMinistryAuditorName();
				papp.setMinistryAuditorName(this.updateSubName(id, name, ids5, names5));
			}
			if(papp.getReviewer() != null){
				String ids6 = papp.getReviewer().getId();
				String names6 = papp.getReviewerName();
				papp.setReviewerName(this.updateSubName(id, name, ids6, names6));
			}
			if(papp.getFinalAuditor() != null){
				String ids8 = papp.getFinalAuditor().getId();
				String names8 = papp.getFinalAuditorName();
				papp.setFinalAuditorName(this.updateSubName(id, name, ids8, names8));
			}
			dao.modify(papp);
		}
		
		List<ProjectGranted> ListPG = dao.query("select pg from ProjectGranted pg " +
				"where pg.applicantId like :id", map);
		for(int i = 0; i < ListPG.size(); i++){
			ProjectGranted pg = ListPG.get(i);
			if(!pg.getApplicantId().equals(null)){
				String ids1 = pg.getApplicantId();
				String names1 = pg.getApplicantName();
				pg.setApplicantName(this.updateSubName(id, name, ids1, names1));
			}
			dao.modify(pg);
		}
		//修改项目中检表中各级审核人的姓名
		List<ProjectMidinspection> ListPM = dao.query("select pm from ProjectMidinspection pm " +
				"left join pm.deptInstAuditor dia left join pm.universityAuditor ua left join " +
				"pm.provinceAuditor pa left join pm.finalAuditor fa where dia.id like :id or " +
				"ua.id like :id or pa.id like :id or fa.id like :id", map);
		for(int i = 0; i < ListPM.size(); i++){
			ProjectMidinspection pm = ListPM.get(i);
			if(pm.getDeptInstAuditor() != null){
				String ids1 = pm.getDeptInstAuditor().getId();
				String names1 = pm.getDeptInstAuditorName();
				pm.setDeptInstAuditorName(this.updateSubName(id, name, ids1, names1));
			}
			if(pm.getUniversityAuditor() != null){
				String ids2 = pm.getUniversityAuditor().getId();
				String names2 = pm.getUniversityAuditorName();
				pm.setUniversityAuditorName(this.updateSubName(id, name, ids2, names2));
			}
			if(pm.getProvinceAuditor() != null){
				String ids3 = pm.getProvinceAuditor().getId();
				String names3 = pm.getProvinceAuditorName();
				pm.setProvinceAuditorName(this.updateSubName(id, name, ids3, names3));
			}
			if(pm.getFinalAuditor() != null){
				String ids4 = pm.getFinalAuditor().getId();
				String names4 = pm.getFinalAuditorName();
				pm.setFinalAuditorName(this.updateSubName(id, name, ids4, names4));
			}
			dao.modify(pm);
		}
		//修改结项表中各级审核人和评审人姓名
		List<ProjectEndinspection> listPE = dao.query("select pe from ProjectEndinspection pe " +
				"left join pe.deptInstAuditor dia left join pe.universityAuditor ua left join " +
				"pe.provinceAuditor pa left join pe.ministryAuditor ma left join pe.reviewer r " +
				"left join pe.finalAuditor fa where dia.id like :id or ua.id like :id or pa.id " +
				"like :id or ma.id like :id or r.id like :id or fa.id like :id", map);
		for (int i = 0; i < listPE.size(); i++) {
			ProjectEndinspection pe = listPE.get(i);
			if(pe.getDeptInstAuditor() != null){
				String ids1 = pe.getDeptInstAuditor().getId();
				String names1 = pe.getDeptInstAuditorName();
				pe.setDeptInstAuditorName(this.updateSubName(id, name, ids1, names1));
			}
			if(pe.getUniversityAuditor() != null){
				String ids2 = pe.getUniversityAuditor().getId();
				String names2 = pe.getUniversityAuditorName();
				pe.setUniversityAuditorName(this.updateSubName(id, name, ids2, names2));
			}
			if(pe.getProvinceAuditor() != null){
				String ids3 = pe.getProvinceAuditor().getId();
				String names3 = pe.getProvinceAuditorName();
				pe.setProvinceAuditorName(this.updateSubName(id, name, ids3, names3));
			}
			if(pe.getMinistryAuditor() != null){
				String ids4 = pe.getMinistryAuditor().getId();
				String names4 = pe.getMinistryAuditorName();
				pe.setMinistryAuditorName(this.updateSubName(id, name, ids4, names4));
			}
			if(pe.getReviewer() != null){
				String ids5 = pe.getReviewer().getId();
				String names5 = pe.getReviewerName();
				pe.setReviewerName(this.updateSubName(id, name, ids5, names5));
			}
			if(pe.getFinalAuditor() != null){
				String ids6 = pe.getFinalAuditor().getId();
				String names6 = pe.getFinalAuditorName();
				pe.setFinalAuditorName(this.updateSubName(id, name, ids6, names6));
			}
			dao.modify(pe);
		}
		//修改项目成员表中成员姓名
		List<ProjectMember> ListPMem = dao.query("select pm from ProjectMember pm " +
				"left join pm.member m where m.id like :id", map);
		for (int i = 0; i < ListPMem.size(); i++) {
			ProjectMember pm = ListPMem.get(i);
			if(pm.getMember() != null){
				String ids1 = pm.getMember().getId();
				String names1 = pm.getMemberName();
				pm.setMemberName(this.updateSubName(id, name, ids1, names1));
			}
			dao.modify(pm);
		}
		//修改项目结项评审表评审人姓名
		List<ProjectEndinspectionReview> listPER = dao.query("select per from ProjectEndinspectionReview " +
				"per left join per.reviewer r where r.id like :id", map);
		for (int i = 0; i < listPER.size(); i++) {
			ProjectEndinspectionReview ger = listPER.get(i);
			if(ger.getReviewer() != null){
				String ids1 = ger.getReviewer().getId();
				String names1 = ger.getReviewerName();
				ger.setReviewerName(this.updateSubName(id, name, ids1, names1));
			}
			dao.modify(ger);
		}
		//修改项目变更表中各级审核人姓名
		List<ProjectVariation> ListPV = dao.query("select pv from ProjectVariation pv " +
				"left join pv.deptInstAuditor dia left join pv.universityAuditor ua " +
				"left join pv.provinceAuditor pa left join pv.finalAuditor fa where " +
				"dia.id like :id or ua.id like :id or pa.id like :id or fa.id like :id", map);
		for (int i = 0; i < ListPV.size(); i++) {
			ProjectVariation gv = ListPV.get(i);
			if(gv.getDeptInstAuditor() != null){
				String ids3 = gv.getDeptInstAuditor().getId();
				String names3 = gv.getDeptInstAuditorName();
				gv.setDeptInstAuditorName(this.updateSubName(id, name, ids3, names3));
			}
			if(gv.getUniversityAuditor() != null){
				String ids4 = gv.getUniversityAuditor().getId();
				String names4 = gv.getUniversityAuditorName();
				gv.setUniversityAuditorName(this.updateSubName(id, name, ids4, names4));
			}
			if(gv.getProvinceAuditor() != null){
				String ids5 = gv.getProvinceAuditor().getId();
				String names5 = gv.getProvinceAuditorName();
				gv.setProvinceAuditorName(this.updateSubName(id, name, ids5, names5));
			}
			if(gv.getFinalAuditor() != null){
				String ids6 = gv.getFinalAuditor().getId();
				String names6 = gv.getFinalAuditorName();
				gv.setFinalAuditorName(this.updateSubName(id, name, ids6, names6));
			}
			dao.modify(gv);
		}
		// 新增ProjectApplicationReview
		List<ProjectApplicationReview> ListPAR = dao.query("select par from ProjectApplicationReview " +
				"par left join par.reviewer r where r.id like :id", map);
		for (int i = 0; i < ListPAR.size(); i++) {
			ProjectApplicationReview par = ListPAR.get(i);
			if(par.getReviewer() != null){
				String ids1 = par.getReviewer().getId();
				String names1 = par.getReviewerName();
				par.setReviewerName(this.updateSubName(id, name, ids1, names1));
			}
			dao.modify(par);
		}
		//修改奖励申请表申请人、各级审核人和评审人姓名
		List<AwardApplication> ListAA = dao.query("select aa from AwardApplication " +
				"aa left join aa.applicant p left join aa.deptInstAuditor dia " +
				"left join aa.universityAuditor ua left join aa.provinceAuditor pa " +
				"left join aa.ministryAuditor ma left join aa.reviewer r " +
				"left join aa.reviewAuditor ra left join aa.finalAuditor fa " +
				"where p.id like :id or dia.id like :id or ua.id like :id or " +
				"pa.id like :id or ma.id like :id or r.id like :id or " +
				"ra.id like :id or fa.id like :id", map);
		for (int i = 0; i < ListAA.size(); i++) {
			AwardApplication aa = ListAA.get(i);
			if(aa.getApplicant() != null){
				String ids1 = aa.getApplicant().getId();
				String names1 = aa.getApplicantName();
				aa.setApplicantName(this.updateSubName(id, name, ids1, names1));
			}
			if(aa.getDeptInstAuditor() != null){
				String ids2 = aa.getDeptInstAuditor().getId();
				String names2 = aa.getDeptInstAuditorName();
				aa.setDeptInstAuditorName(this.updateSubName(id, name, ids2, names2));
			}
			if(aa.getUniversityAuditor() != null){
				String ids3 = aa.getUniversityAuditor().getId();
				String names3 = aa.getUniversityAuditorName();
				aa.setUniversityAuditorName(this.updateSubName(id, name, ids3, names3));
			}
			if(aa.getProvinceAuditor() != null){
				String ids4 = aa.getProvinceAuditor().getId();
				String names4 = aa.getProvinceAuditorName();
				aa.setProvinceAuditorName(this.updateSubName(id, name, ids4, names4));
			}
			if(aa.getMinistryAuditor() != null){
				String ids5 = aa.getMinistryAuditor().getId();
				String names5 = aa.getMinistryAuditorName();
				aa.setMinistryAuditorName(this.updateSubName(id, name, ids5, names5));
			}
			if(aa.getReviewer() != null){
				String ids6 = aa.getReviewer().getId();
				String names6 = aa.getReviewerName();
				aa.setReviewerName(this.updateSubName(id, name, ids6, names6));
			}
			if(aa.getReviewAuditor() != null){
				String ids7 = aa.getReviewAuditor().getId();
				String names7 = aa.getReviewAuditorName();
				aa.setReviewAuditorName(this.updateSubName(id, name, ids7, names7));
			}
			if(aa.getFinalAuditor() != null){
				String ids8 = aa.getFinalAuditor().getId();
				String names8 = aa.getFinalAuditorName();
				aa.setFinalAuditorName(this.updateSubName(id, name, ids8, names8));
			}
			dao.modify(aa);
		}
		//修改奖励评审表评审人姓名
		List<AwardReview> ListAR = dao.query("select ar from AwardReview ar " +
				"left join ar.reviewer r where r.id like :id", map);
		for (int i = 0; i < ListAR.size(); i++) {
			AwardReview ar = ListAR.get(i);
			if(ar.getReviewer() != null){
				String ids1 = ar.getReviewer().getId();
				String names1 = ar.getReviewerName();
				ar.setReviewerName(this.updateSubName(id, name, ids1, names1));
			}
			dao.modify(ar);
		}
		// 修改成果作者、审核人姓名
		List<Product> ListP = dao.query("select p from Product p left join p.author author " +
				"left join p.auditor auditor where author.id like :id or auditor.id like :id", map);
		for (int i = 0; i < ListP.size(); i++) {
			Product product = ListP.get(i);
			if(product.getAuthor() != null){
				String ids1 = product.getAuthor().getId();
				String names1 = product.getAuthorName();
				product.setAuthorName(this.updateSubName(id, name, ids1, names1));
			}
			if(product.getAuditor() != null){
				String ids2 = product.getAuditor().getId();
				String names2 = product.getAuditorName();
				product.setAuditorName(this.updateSubName(id, name, ids2, names2));
			}
			dao.modify(product);
		}
	}
	
	/**
	 * 更新names
	 * @param id
	 * @param newName
	 * @param ids
	 * @param names
	 * @return
	 */
	private String updateSubName(String id, String name, String ids, String names) {
		String[] gAids = ids.split("; ");
		String[] gANames = names.split("; ");
		StringBuffer result = new StringBuffer();
		for (int j = 0; j < gAids.length; j++) {
			if(gAids[j].equals(id)){
				gANames[j] = name;
			}
		}
		for (int j = 0; j < gANames.length - 1; j++){
			result.append(gANames[j]);
			result.append("; ");
		}
		result.append(gANames[gANames.length - 1]);
		return result.toString();
	}
	
	/**
	 * 输入人名时可能有word中复制出的奇怪字符“•”，前台让其校验通过，在后台替换为“·”
	 * @param nameString
	 * @return
	 */
	public String regularNames(String nameString) {
		String[] names = nameString.split("•");
		String regularedName = "";
		for(String name:names){
			regularedName += name + "·";
		}
		regularedName = regularedName.substring(0, regularedName.length() - 1);
		return regularedName;
	}
	
	/**
	 * 判断数据库中的人员表结构是否有更改[人员合并前的校验]
	 */
	public boolean isPersonTablesExpired(){
		Integer curPersonLength =178;
		Integer curOfficerLength = 36;
		Integer curExpertLength = 22;
		Integer curTeacherLength = 38;
		Integer curStudentLength = 50;
		Integer curAddressLength = 27;
		Integer curBankLength = 35;
		Class personClass = Person.class;
		Class officerClass = Officer.class;
		Class expertClass = Expert.class;
		Class teacherClass = Teacher.class;
		Class studentClass = Student.class;
		Class addressClass = Address.class;
		Class bankClass = BankAccount.class;
		System.out.println(personClass.getMethods().length + " " + officerClass.getMethods().length + " " + expertClass.getMethods().length + " " +
				teacherClass.getMethods().length + " " + studentClass.getMethods().length + " " + addressClass.getMethods().length + " " + bankClass.getMethods().length);
		if(personClass.getMethods().length == curPersonLength && officerClass.getMethods().length == curOfficerLength && 
			expertClass.getMethods().length == curExpertLength && teacherClass.getMethods().length == curTeacherLength && 
			studentClass.getMethods().length == curStudentLength && addressClass.getMethods().length == curAddressLength &&
			bankClass.getMethods().length == curBankLength){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取专业职称的ID
	 * @param academic
	 * @return
	 */
	public String getSpecialityTitleId(Academic academic){
		String specialityTitleId = null;
		Map paraMap = new HashMap();
		if (academic!=null && academic.getSpecialityTitle() != null && academic.getSpecialityTitle().contains("/")) {
			paraMap.put("standard", "GBT8561-2001");
			String code = academic.getSpecialityTitle().substring(0, academic.getSpecialityTitle().lastIndexOf("/"));
			paraMap.put("code", code);
			specialityTitleId = (String) dao.query("select s.systemOption.id from SystemOption s where s.standard = :standard and s.code = :code",paraMap).get(0);
		}
		return specialityTitleId;
	}
	
	/**
	 * 获取专业职称的名字
	 * @param academic
	 * @return
	 */
	public String getSpecialityTitleName(Academic academic){
		String specialityTitleName = null;
		Map paraMap = new HashMap();
		if (academic!=null && academic.getSpecialityTitle() != null && academic.getSpecialityTitle().contains("/")) {
			paraMap.put("standard", "GBT8561-2001");
			String code = academic.getSpecialityTitle().substring(0, academic.getSpecialityTitle().lastIndexOf("/"));
			paraMap.put("code", code);
			specialityTitleName = (String) dao.queryUnique("select s.systemOption.name from SystemOption s where s.standard = :standard and s.code = :code",paraMap);
		}
		return specialityTitleName;
	}
	
	/**
	 * 同步人员照片到DMSS服务器
	 * @param 
	 * @return DMSS文档持久化后的标识
	 */
	public String flushToDmss(Person person){
		if(null != person.getPhotoFile() && !person.getPhotoFile().isEmpty() && dmssService.getOn() ==1 && dmssService.getStatus()){
			ThirdUploadForm form = new ThirdUploadForm();
	        form.setTitle(person.getName()+"_"+person.getIdcardNumber());
	        form.setFileName(getFileName(person.getPhotoFile()));
	        form.setSourceAuthor(person.getName());
	        form.setRating("5.0");
	        form.setTags(person.getEmail()+";"+person.getGender()+";"+person.getMobilePhone());
	        form.setCategoryPath("/SMDB/"+getRelativeFileDir(person.getPhotoFile()));
	        try {
				return dmssService.upload(ApplicationContainer.sc.getRealPath(person.getPhotoFile()), form);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}else {
			return null;
		}
	}

	public Person setBaseInfo(Person person) {
		if("-1".equals(person.getGender())){
			person.setGender(null);
		}
		if("-1".equals(person.getMembership())){
			person.setMembership(null);
		}
		if("-1".equals(person.getCountryRegion())){
			person.setCountryRegion(null);
		}
		if("-1".equals(person.getEthnic())){
			person.setEthnic(null);
		}
		if("-1".equals(person.getIdcardNumber())){
			person.setIdcardNumber(null);
		}
		return person;
	}
	
	public Person resetBankInfo(Person originPerson, List<BankAccount> bankList){
		if(originPerson.getBankIds()==null || originPerson.getBankIds().isEmpty()){//修改前没有银行信息
			if(!bankList.isEmpty()){
				String bankIds = UUID.randomUUID().toString().replaceAll("-", "");
				originPerson.setBankIds(bankIds);
				dao.modify(originPerson);
				int i = 1;
				for(BankAccount ba : bankList){
					if(ba.getIsDefault()==1){
						ba.setSn(1);
					}else {
						i++;
						ba.setSn(i);
					}
					ba.setCreateMode(1);
					ba.setCreateDate(new Date());
					ba.setIds(bankIds);
					dao.add(ba);
				}
			}
		}else {
			String bankIds = originPerson.getBankIds();
			if(!bankList.isEmpty()){
				List<String> oldIds = dao.query("select ba.id from BankAccount ba where ba.ids = ?", bankIds);//原银行信息的id集合
				List<String> newIds = new ArrayList<String>();
				int i = 1;
				for(BankAccount ba : bankList){
					if(ba.getIsDefault()==1){
						ba.setSn(1);
					}else {
						i++;
						ba.setSn(i);
					}
					if(ba.getId().isEmpty()){//新增的银行信息
						ba.setCreateMode(1);
						ba.setCreateDate(new Date());
						ba.setIds(bankIds);
						dao.add(ba);
					}else {
						ba = modifyBankAccount(ba);
						if(bankIsChanged(ba)){
							ba.setUpdateDate(new Date());
						}
						dao.modify(ba);
						newIds.add(ba.getId());
					}
				}
				for(String id : oldIds){//判断本次修改中是否删除了某条银行信息
					if(!newIds.contains(id)){
						dao.delete(dao.query(BankAccount.class ,id));
					}
				}
			}else {//原有的银行信息全被删除了
				bankList = (List<BankAccount>)dao.query("select ba from BankAccount ba where ba.ids = ? order by ba.sn ", bankIds);
				for(BankAccount ba : bankList){
					dao.delete(ba);
				}
				originPerson.setBankIds(null);
				dao.modify(originPerson);
			}
		}
		return originPerson;
	}
	
	public Person modifyAcademic(Person originPerson, Academic academic){
		if(academicNotNull(academic)){
			academic.setPerson(originPerson);
			if(academic.getId().isEmpty()){//新增的学术信息
				dao.add(academic);
			}else {//在原有的学术信息上修改
				dao.modify(academic);
			}
		}else {
			if(!academic.getId().isEmpty()){//待删除的学术信息
				dao.delete(academic);
			}
		}
		return originPerson;
	}
	
	public Person setAddress(Person person, List<Address> homeAddress, List<Address> officeAddress){
		if(!homeAddress.isEmpty()){
			String homeIds = UUID.randomUUID().toString().replaceAll("-", "");
			person.setHomeAddressIds(homeIds);
			int i = 1;
			for(Address address : homeAddress){
				if(address.getIsDefault() == 1){
					address.setSn(1);
				}else {
					i++;
					address.setSn(i);
				}
				address.setCreateMode(1);
				address.setCreateDate(new Date());
				address.setIds(homeIds);
				dao.add(address);
			}
			dao.modify(person);
		}
		if(!officeAddress.isEmpty()){
			String officeIds = UUID.randomUUID().toString().replaceAll("-", "");
			person.setOfficeAddressIds(officeIds);
			int i = 1;
			for(Address address : officeAddress){
				if(address.getIsDefault() == 1){
					address.setSn(1);
				}else {
					i++;
					address.setSn(i);
				}
				address.setCreateMode(1);
				address.setCreateDate(new Date());
				address.setIds(officeIds);
				dao.add(address);
			}
			dao.modify(person);
		}
		return person;
	}
	
	public Person resetAddress(Person originPerson, List<Address> homeAddress, List<Address> officeAddress){
		if(originPerson.getHomeAddressIds() == null || originPerson.getHomeAddressIds().isEmpty()){//修改前没有家庭住址信息
			if(!homeAddress.isEmpty()){
				String homeIds = UUID.randomUUID().toString().replaceAll("-", "");
				originPerson.setHomeAddressIds(homeIds);
				int i = 1;
				for(Address address : homeAddress){
					if(address.getIsDefault() == 1){
						address.setSn(1);
					}else {
						i++;
						address.setSn(i);
					}
					address.setCreateMode(1);
					address.setCreateDate(new Date());
					address.setIds(homeIds);
					dao.add(address);
				}
				dao.modify(originPerson);
			}
		}else {
			String homeIds = originPerson.getHomeAddressIds();
			if(!homeAddress.isEmpty()){
				List<String> oldIds = dao.query("select address.id from Address address where address.ids = ? ", homeIds);
				List<String> newIds = new ArrayList<String>();
				int i = 1;
				for(Address address : homeAddress){
					if(address.getIsDefault()==1){
						address.setSn(1);
					}else {
						i++;
						address.setSn(i);
					}
					if(address.getId().isEmpty()){//新增的地址信息
						address.setCreateMode(1);
						address.setCreateDate(new Date());
						address.setIds(homeIds);
						dao.add(address);
					}else {
						address = modifyAddress(address);
						if(addressIsChanged(address)){
							address.setUpdateDate(new Date());
						}
						dao.modify(address);
						newIds.add(address.getId());
					}
				}
				for(String id : oldIds){//判断本次修改中是否删除了某条地址信息
					if(!newIds.contains(id)){
						dao.delete(dao.query(Address.class ,id));
					}
				}
			}else {//原有的家庭住址信息全被删除了
				homeAddress = dao.query("select address from Address address where address.ids = ? ", homeIds);
				for(Address address : homeAddress){
					dao.delete(address);
				}
				originPerson.setHomeAddressIds(null);
				dao.modify(originPerson);
			}
		}
		if(originPerson.getOfficeAddressIds() == null || originPerson.getOfficeAddressIds().isEmpty()){//修改前没有办公地址信息
			if(!officeAddress.isEmpty()){
				String officeIds = UUID.randomUUID().toString().replaceAll("-", "");
				originPerson.setOfficeAddressIds(officeIds);
				int i = 1;
				for(Address address : officeAddress){
					if(address.getIsDefault() == 1){
						address.setSn(1);
					}else {
						i++;
						address.setSn(i);
					}
					address.setCreateMode(1);
					address.setCreateDate(new Date());
					address.setIds(officeIds);
					dao.add(address);
				}
				dao.modify(originPerson);
			}
		}else {
			String officeId = originPerson.getOfficeAddressIds();
			if(!officeAddress.isEmpty()){
				List<String> oldIds = dao.query("select address.id from Address address where address.ids = ? ", officeId);
				List<String> newIds = new ArrayList<String>();
				int i = 1;
				for(Address address : officeAddress){
					if(address.getIsDefault()==1){
						address.setSn(1);
					}else {
						i++;
						address.setSn(i);
					}
					if(address.getId().isEmpty()){//新增的地址信息
						address.setCreateMode(1);
						address.setCreateDate(new Date());
						address.setIds(officeId);
						dao.add(address);
					}else {
						address = modifyAddress(address);
						if(addressIsChanged(address)){
							address.setUpdateDate(new Date());
						}
						dao.modify(address);
						newIds.add(address.getId());
					}
				}
				for(String id : oldIds){//判断本次修改中是否删除了某条地址信息
					if(!newIds.contains(id)){
						dao.delete(dao.query(Address.class ,id));
					}
				}
			}else {//原有的办公地址信息全被删除了
				officeAddress = dao.query("select address from Address address where address.ids = ? ", officeId);
				for(Address address : officeAddress){
					dao.delete(address);
				}
				originPerson.setOfficeAddressIds(null);
				dao.modify(originPerson);
			}
		}
		return originPerson;
	}
	
	public List getHomeAddress(Person person){
		List<Address> addressList = dao.query("select address from Address address where address.ids = ?", person.getHomeAddressIds());
		String homeAddress = "";
		String homePostcode = "";
		List list = new ArrayList<String>();
		for(Address address : addressList){
			if(address.getAddress()!=null && !address.getAddress().isEmpty()){
				homeAddress = homeAddress + address.getAddress() + ";";
			}
			if(address.getPostCode()!=null && !address.getPostCode().isEmpty()){
				homePostcode = homePostcode + address.getPostCode() + ";";
			}
		}
		list.add(homeAddress.substring(0, homeAddress.length()-1));
		list.add(homePostcode.substring(0, homePostcode.length()-1));
		return list;
	}
	
	public List getOfficeAddress(Person person){
		List<Address> addressList = dao.query("select address from Address address where address.ids = ?", person.getOfficeAddressIds());
		String officerAddress = "";
		String officePostcode = "";
		List list = new ArrayList<String>();
		for(Address address : addressList){
			if(address.getAddress()!=null && !address.getAddress().isEmpty()){
				officerAddress = officerAddress + address.getAddress() + ";";
			}
			if(address.getPostCode()!=null && !address.getPostCode().isEmpty()){
				officePostcode = officePostcode + address.getPostCode() + ";";
			}
		}
		list.add(officerAddress.substring(0, officerAddress.length()-1));
		list.add(officePostcode.substring(0, officePostcode.length()-1));
		return list;
	}
	
	//补充银行对象中其他不由前台传递的数据
	private BankAccount modifyBankAccount(BankAccount bankAccount){
		BankAccount oldBankAccount = dao.query(BankAccount.class, bankAccount.getId());
		bankAccount.setCreateDate(oldBankAccount.getCreateDate());
		bankAccount.setCreateMode(oldBankAccount.getCreateMode());
		bankAccount.setIds(oldBankAccount.getIds());
		bankAccount.setUpdateDate(oldBankAccount.getUpdateDate());
		return bankAccount;
	}
	
	//补充地址对象中其他不由前台传递的数据
	private Address modifyAddress(Address address){
		Address oldAddress = dao.query(Address.class, address.getId());
		address.setCreateDate(oldAddress.getCreateDate());
		address.setCreateMode(oldAddress.getCreateMode());
		address.setIds(oldAddress.getIds());
		address.setUpdateDate(oldAddress.getUpdateDate());
		return address;
	}
	
	//TODO:academic对象是否有有效数据[id除外]
	private boolean academicNotNull(Academic academic){
		return true;
	}
	
	//原有的银行信息是否有更改[只判断用户可以选择的那5个字段]
	private boolean bankIsChanged(BankAccount ba){
		BankAccount oldBA = dao.query(BankAccount.class, ba.getId());
		if(ba.getAccountName().equals(oldBA.getAccountName()) && ba.getAccountNumber().equals(oldBA.getAccountNumber()) && ba.getBankCupNumber().equals(oldBA.getBankCupNumber()) && ba.getBankName().equals(oldBA.getBankName()) && ba.getIsDefault().equals(oldBA.getIsDefault())){
			return false;
		}else return true;
	}
	
	//原有的地址信息是否更改[只判断用户可以选择的那3个字段]
	private boolean addressIsChanged(Address address){
		Address oldAddress = dao.query(Address.class, address.getId());
		if(address.getAddress().equals(oldAddress.getAddress()) && address.getPostCode().equals(oldAddress.getPostCode()) && address.getIsDefault().equals(oldAddress.getIsDefault()) ){
			return false;
		}else return true;
	}
	
	//删除人员相关联的地址信息
	private void deleteAddress(Person person){
		if(person.getHomeAddressIds() !=null && !person.getHomeAddressIds().isEmpty()){
			List<Address> addressList = dao.query("select address from Address address where address.ids = ?", person.getHomeAddressIds());
			dao.delete(addressList);
		}
		if(person.getOfficeAddressIds() !=null && !person.getOfficeAddressIds().isEmpty()){
			List<Address> addressList = dao.query("select address from Address address where address.ids = ?", person.getOfficeAddressIds());
			dao.delete(addressList);
		}
	}
	
	//删除人员相关联的银行账号信息
	private void deleteBank(Person person){
		if(person.getBankIds() !=null && !person.getBankIds().isEmpty()){
			List<BankAccount> bankList = dao.query("select address from Address address where address.ids = ?", person.getBankIds());
			dao.delete(bankList);
		}
	}
}
