package csdc.action.security.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import csdc.bean.Academic;
import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.bean.Teacher;
import csdc.service.ext.IPersonExtService;
import csdc.tool.MD5;
import csdc.tool.bean.AccountType;
import csdc.tool.info.AccountInfo;
import csdc.tool.info.GlobalInfo;

/**
 * 教师账号管理
 * @author 龚凡
 * @version 2011.04.07
 */
@SuppressWarnings("unchecked")
public class TeacherAction extends AccountAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select a.id, pp.name, p.id, p.name, d.id, d.name, i.id, i.name, u.id, u.name, a.startDate, a.expireDate, a.status, a.lastLoginDate from Account a left join a.passport pp, Teacher t left join t.person p left join t.department d left join t.institute i left join t.university u left join i.type sys where a.person.id = p.id and a.type = 'TEACHER' and t.type = '专职人员' ";
	private static final String GROUP_BY = " group by a.id, pp.name, p.id, p.name, d.id, d.name, i.id, i.name, u.id, u.name, a.startDate, a.expireDate, a.status, a.lastLoginDate ";
	private static final String[] COLUMN = {
			"pp.name",
			"p.name, pp.name",
			"CONCAT(d.name, i.name), pp.name",
			"u.name, pp.name",
			"a.startDate, pp.name",
			"a.expireDate, pp.name",
			"a.status, pp.name",
			"a.lastLoginDate, pp.name"
	};// 排序列
	private static final String PAGE_NAME = "accountTeacherPage";
	private static final AccountType SUB_CLASS_TYPE = AccountType.TEACHER;// 账户模块便于复用的标志
	private static final int SUB_CLASS_PRINCIPAL = 1;

	private int registerType;//教师账号的注册类型 1有人无账号 2无人无账号
	private Person person;
	private Teacher teacher;
	private Academic academic;
	private boolean valid;// 是否有效，用于判断用户名、邮箱、生成人员编号等
	private String universityId, departmentId;//注册时选择的高校、院系id
	
	protected IPersonExtService personExtService;
	
	public String[] column() {
		return TeacherAction.COLUMN;
	}
	public String pageName() {
		return TeacherAction.PAGE_NAME;
	}
	public String groupBy() {
		return TeacherAction.GROUP_BY;
	}
	public AccountType getSubClassType() {
		return SUB_CLASS_TYPE;
	}
	public int getSubClassPrincipal() {
		return SUB_CLASS_PRINCIPAL;
	}
	
	public int getRegisterType() {
		return registerType;
	}
	public void setRegisterType(int registerType) {
		this.registerType = registerType;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public Academic getAcademic() {
		return academic;
	}
	public void setAcademic(Academic academic) {
		this.academic = academic;
	}
	/**
	 * 处理初级检索条件，拼装查询语句。
	 */
	public Object[] simpleSearchCondition() {
		if (keyword == null) {// 预处理关键字
			keyword = "";
		} else {
			keyword = keyword.toLowerCase();
		}
		
		// 拼接检索条件
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		hql.append(" and ");
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号查询所有教师账号
			hql.append(" 1=1 ");
		} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号查询本省地方高校所有教师账号或省属及省部共建基地教师账号
			hql.append(" (u.type = 4 or (sys.code = '02' or sys.code = '03') and u.type = 3) and u.subjection.id = :currentunitid ");
			map.put("currentunitid", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号查询本校所有教师账号
			hql.append(" u.id = :currentunitid ");
			map.put("currentunitid", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.DEPARTMENT)) {// 院系账号查询本院系所有教师账号
			hql.append(" d.id = :currentunitid ");
			map.put("currentunitid", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.INSTITUTE)) {// 研究机构账号查询本基地所有教师账号
			hql.append(" i.id = :currentunitid ");
			map.put("currentunitid", loginer.getCurrentBelongUnitId());
		} else {// 其它账号无法查询任何教师账号
			hql.append(" 1=0 ");
		}
		hql.append(" and ");
		if (searchType == 1) {// 按账号名检索
			hql.append(" LOWER(pp.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {// 按教师姓名检索
			hql.append(" LOWER(p.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {// 按上述字段检索
			hql.append(" (LOWER(pp.name) like :keyword or LOWER(p.name) like :keyword) ");
			map.put("keyword", "%" + keyword + "%");
		}
		//hql.append(GROUP_BY);// 拼接去重条件

		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
		// 调用初级检索功能
//		this.simpleSearch(hql, map, ORDER_BY, 2, 0, PAGE_NAME);
//		return SUCCESS;
	}

	/**
	 * 处理高级检索条件，拼装查询语句。
	 */
	public Object[] advSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		hql.append(" and ");
		
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号查询所有学生账号
			hql.append(" 1=1 ");
		} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号查询本省地方高校所有教师账号或省属及省部共建基地教师账号
			hql.append(" (u.type = 4 or (sys.code = '02' or sys.code = '03') and u.type = 3) and u.subjection.id = :currentunitid ");
			map.put("currentunitid", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号查询本校所有教师账号
			hql.append(" u.id = :currentunitid ");
			map.put("currentunitid", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.DEPARTMENT)) {// 院系账号查询本院系所有教师账号
			hql.append(" d.id = :currentunitid ");
			map.put("currentunitid", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.INSTITUTE)) {// 研究机构账号查询本基地所有教师账号
			hql.append(" i.id = :currentunitid ");
			map.put("currentunitid", loginer.getCurrentBelongUnitId());
		} else {// 其它账号无法查询任何学生账号
			hql.append(" 1=0 ");
		}
		
		if (accountName != null && !accountName.isEmpty()) {// 按账号名检索
			accountName = accountName.toLowerCase();
			hql.append(" and LOWER(pp.name) like :accountName ");
			map.put("accountName", "%" + accountName + "%");
		}
		if (belongPersonName != null && !belongPersonName.isEmpty()) {// 按所属人员检索
			hql.append(" and LOWER(p.name) like :belongPersonName ");
			map.put("belongPersonName", "%" + belongPersonName + "%");
		}
		if (createDate1 != null) {// 账号创建时间查询起点
			hql.append(" and a.startDate > :createDate1");
			map.put("createDate1", createDate1);
		}
		if (createDate2 != null) {// 账号创建时间查询终点
			hql.append(" and a.startDate < :createDate2");
			map.put("createDate2", createDate2);
		}
		if (expireDate1 != null) {// 账号有效期查询起点
			hql.append(" and a.expireDate > :expireDate1");
			map.put("expireDate1", expireDate1);
		}
		if (expireDate2 != null) {// 账号有效期查询终点
			hql.append(" and a.expireDate < :expireDate2");
			map.put("expireDate2", expireDate2);
		}
		if (status == 1) {// 按账号状态检索
			hql.append(" and a.status = 1");
		} else if (status == 0) {
			hql.append(" and a.status = 0");
		}
		hql.append(GROUP_BY);

		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
		// 调用高级检索功能
//		this.advSearch(hql, map, ORDER_BY, 2, 0, PAGE_NAME);
//		return SUCCESS;
	}
	
	public String toRegister() {
		return SUCCESS;
	}

	public String registerSuccess() {
		return SUCCESS;
	}
	
	/**
	 * 检测用户名是否可用
	 * @return
	 */
	public String checkUsername() {
		valid = personExtService.checkUsername(passport.getName());
		return SUCCESS;
	}
	
	/**
	 * 检测邮箱是否可用
	 * @return
	 */
	public String checkEmail() {
		valid = personExtService.checkEmail(person.getEmail());
		return SUCCESS;
	}
	
	/**
	 * 注册账号
	 * 1：有人没账号，修改人员分配账号
	 * 2：没人没账号，添加人员分配账号
	 * 3：有人有账号，提示用户已有账号而不进入此方法
	 * @return
	 */
	public String register() {
		Passport oldPassport = null;
		if(registerType == 1){
			List<Object[]> list = dao.query("select p, t from Person p left join p.teacher t where LOWER(p.idcardNumber) = '" + person.getIdcardNumber().trim().toLowerCase() + "' ");
			Person oldPerson = (Person) list.get(0)[0];
			oldPerson.setName(person.getName());
			oldPerson.setIdcardType(person.getIdcardType());
			if(!person.getMobilePhone().isEmpty()){oldPerson.setMobilePhone(person.getMobilePhone());}
			if(!person.getOfficePhone().isEmpty()){oldPerson.setOfficePhone(person.getOfficePhone());}
			oldPerson.setEmail(person.getEmail());
			oldPerson.setCreateType(2);
			dao.modify(oldPerson);
			
			Teacher oldTeacher = (Teacher) list.get(0)[1];
			oldTeacher.setUniversity((Agency)dao.query(Agency.class, universityId));
			oldTeacher.setDepartment((Department)dao.query(Department.class, departmentId));
			oldTeacher.setCreateType(2);
			dao.modify(oldTeacher);
			
			account = new Account();
			account.setPerson(oldPerson);
			oldPassport = accountService.getPassportByBelongId(oldPerson.getId(), SUB_CLASS_TYPE, 1);
			
			Map parMap = new HashMap();
			parMap.put("personId", oldPerson.getId());
			List<Object> list2 = dao.query("select a from Academic a where a.person.id = :personId", parMap);
			if(list2.isEmpty()){
				academic.setPerson(oldPerson);
				dao.add(academic);
			} else {
				Academic oldAcademic = (Academic) list2.get(0);
				oldAcademic.setSpecialityTitle(academic.getSpecialityTitle());
				dao.modify(oldAcademic);
			}
		} else if(registerType == 2){
			person.setCreateType(1);
			String personId  = dao.add(person);
			
			teacher.setCreateType(1);
			teacher.setUniversity((Agency)dao.query(Agency.class, universityId));
			teacher.setDepartment((Department)dao.query(Department.class, departmentId));
			teacher.setPerson(person);
			dao.add(teacher);
			
			academic.setPerson(person);
			dao.add(academic);
			
			account.setPerson(person);
		}
		account.setType(SUB_CLASS_TYPE);
		account.setIsPrincipal(SUB_CLASS_PRINCIPAL);
		if(oldPassport != null) {
			oldPassport.setName(passport.getName());
			oldPassport.setPassword(MD5.getMD5(newPassword));
			accountService.register(account, oldPassport);
		} else {
			passport.setPassword(MD5.getMD5(newPassword));
			accountService.register(account, passport);
		}
		
		accountService.setAccountRole(account);
		
		return SUCCESS;
	}
	
	public void validateRegister() {
		if (person.getName() == null || person.getName().trim().isEmpty()){
			this.addFieldError("person.name", "姓名不应为空");
		}
		if (person.getIdcardNumber().trim().length() > 20){
			this.addFieldError("person.idcardNumber", "证件号过长");
		}
		if (universityId.trim().isEmpty()){
			this.addFieldError("universityId", "高校不得为空");
		}
		if (departmentId.trim().equals("-1")){
			this.addFieldError("departmentId", "请选择院系");
		}
		if (academic.getSpecialityTitle().equals("-1")){
			this.addFieldError("academic.specialityTitle", "请选择专业职称");
		}
		if (!inputValidate.checkCellphone(person.getMobilePhone())){
			this.addFieldError("person.mobilePhone", "手机号码错误");
		}
		if (!inputValidate.checkPhone(person.getOfficePhone())){
			this.addFieldError("person.officePhone", "办公电话错误");
		}
		if (person.getEmail() == null || person.getEmail().trim().isEmpty()){
			this.addFieldError("person.email", "邮箱不应为空");
		} else 
			if(!inputValidate.checkEmail(person.getEmail())){
			this.addFieldError("person.email", "邮箱不合法");
		}
		if (passport.getName() == null || passport.getName().equals("")) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_NAME_NULL);
		} else if (!Pattern.matches("\\w{3,40}", passport.getName())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_NAME_ILLEGAL);
		}
		if (newPassword.trim() == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_PASSWORD_NULL);
		} else if (newPassword.trim().length() > 20 || newPassword.trim().length() < 3){
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_PASSWORD_ILLEGAL);
		}
		if (rePassword.trim() == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_REPASSWORD_NULL);
		} else if (!rePassword.trim().equals(newPassword.trim())){
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_REPASSWORD_ILLEGAL);
		}
	}
	
	/**
	 * 处理教师账号列表，重写
	 */
//	public void pageListDealWith() {
//		laData = new ArrayList();// 处理之后的列表数据
//		Object[] o;// 缓存查询结果的一行
//		String[] item;// 缓存查询结果一行中的每一字段
//		SimpleDateFormat dateformat = new SimpleDateFormat(dateFormat());// 时间格式化对象
//		
//		for (Object p : pageList) {
//			o = (Object[]) p;
//			item = new String[o.length + 1];
//			item[0] = o[0].toString();
//			item[1] = o[1].toString();
//			item[2] = o[2].toString();
//			item[3] = o[3].toString();
//			item[7] = o[8].toString();
//			item[8] = o[9].toString();
//			item[9] = dateformat.format((Date) o[10]);
//			item[10] = dateformat.format((Date) o[11]);
//			item[11] = o[12].toString();;
//			item[12] = dateformat.format((Date) o[13]);
//			item[o.length] = accountService.getRoleName(item[0]);
//			if (o[6] == null){
//				item[4] = o[4].toString();
//				item[5] = o[5].toString();
//				item[6] = "linkD";
//			} else {
//				item[4] = o[6].toString();
//				item[5] = o[7].toString();
//				item[6] = "linkI";
//			}
//			laData.add(item);
//		}
//		System.out.println(laData);
//	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public boolean isValid() {
		return valid;
	}
	public String getUniversityId() {
		return universityId;
	}
	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public IPersonExtService getPersonExtService() {
		return personExtService;
	}
	public void setPersonExtService(IPersonExtService personExtService) {
		this.personExtService = personExtService;
	}
}
