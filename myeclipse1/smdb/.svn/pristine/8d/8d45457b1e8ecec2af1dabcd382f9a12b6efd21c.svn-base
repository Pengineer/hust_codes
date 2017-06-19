package csdc.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Account;
import csdc.bean.AccountRole;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.Mail;
import csdc.bean.Officer;
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.bean.Role;
import csdc.bean.RoleAgency;
import csdc.bean.Student;
import csdc.bean.Teacher;
import csdc.service.IAccountService;
import csdc.service.ext.IAccountExtService;
import csdc.tool.MD5;
import csdc.tool.SignID;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.RoleInfo;

/**
 * 账号管理实现类
 * @author 龚凡
 * @version 2011.04.08
 */
@SuppressWarnings("unchecked")
@Transactional
public class AccountService extends BaseService implements IAccountService, IAccountExtService {

	/**
	 * 获取当前通过认证的账号名称
	 */
	public String securityUsername() {
		// 获得当前通过认证的用户上下文信息
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// 获得当前通过认证用户的账号名
		String username;
		if (principal instanceof UserDetails) {// 如果上下文信息为UserDails实例，则调用该接口的getUsername方法获取账号名
			username = ((UserDetails)principal).getUsername();
		} else {// 如果上下文信息不是UserDails实例，则它就是账号名
			username = principal.toString();
		}
		return username;
	}
	
	/**
	 * 根据账号名获得账号权限
	 * @param name账号名
	 * @return 如果账号不存在，则返回null；
	 * 如果账号存在，则系统管理员账号返回所有权限；
	 * 其它账号，返回拥有角色所对应的权限。
	 * 返回结果均无重复项。
	 */
	public List<String> getRightByAccountName(String name) {
		if (name == null || name.isEmpty()) {// 如果账号名为空，则直接返回null
			return null;
		} else {// 如果账号名非空，则先查找该账号对象，根据账号的类型读取相应的权限。
			Map map = new HashMap();
			map.put("name", name);
			List<Passport> passports = dao.query("from Passport p where p.name = :name", map);// 获取指定通行证
			map.remove("name");
			
			if (passports.isEmpty()) {// 账号不存在，直接返回空
				return null;
			} else {// 账号存在，则根据账号类型查询相应的权限
				Passport passport = passports.get(0);
				map.put("id", passport.getId());
				List<Account> accounts = dao.query("select a from Account a where a.passport.id = :id", map);// 获取指定账号
				map.remove("id");
				List<String> re;// 权限集合
				if(accounts.isEmpty()) {
					return null;
				} else if(accounts.size() == 1) {
					Account account = accounts.get(0);
					if (account.getType().equals(AccountType.ADMINISTRATOR)) {// 系统管理员读取所有权限
						re = dao.query("select distinct r.code from Right r");
					} else {// 其它账号，根据账号、角色、权限关联关系，读取自己拥有的权限
						map.put("accountId", account.getId());
						re = dao.query("select distinct r.code from Right r, RoleRight rr, AccountRole ar where ar.account.id = :accountId and ar.role.id = rr.role.id and rr.right.id = r.id", map);
					}
					return re;
				} else {
					return null;
				}
			}
		}
	}

	/**
	 * 根据账号名获得账号权限
	 * @param name账号名
	 * @return 如果账号不存在，则返回null；
	 * 如果账号存在，则系统管理员账号返回所有权限；
	 * 其它账号，返回拥有角色所对应的权限。
	 * 返回结果均无重复项。
	 */
	public List<String> getRightByAccountId(String accountId) {
		if (accountId == null || accountId.isEmpty()) {// 如果账号名为空，则直接返回null
			return null;
		} else {// 如果账号名非空，则先查找该账号对象，根据账号的类型读取相应的权限。
			Map map = new HashMap();
			Account account = (Account) dao.query(Account.class, accountId);
			List<String> re;// 权限集合
			if (account.getType().equals(AccountType.ADMINISTRATOR)) {// 系统管理员读取所有权限
				re = dao.query("select distinct r.code from Right r");
			} else {// 其它账号，根据账号、角色、权限关联关系，读取自己拥有的权限
				map.put("accountId", account.getId());
				re = dao.query("select distinct r.code from Right r, RoleRight rr, AccountRole ar where ar.account.id = :accountId and ar.role.id = rr.role.id and rr.right.id = r.id", map);
			}
			return re;
		}
	}
	/**
	 * 检测账号名是否存在
	 * @param accountName账号名
	 * @return true存在，false不存在
	 */
	public boolean checkAccountName(String accountName) {
		if (accountName == null || accountName.isEmpty()) {// 如果账号名为空，则视为已存在
			return true;
		} else {// 如果账号名非空，则查询数据库，判断此账号名是否存在
			Map map = new HashMap();
			map.put("accountName", accountName);
			List<String> re = dao.query("select pp.id from Passport pp where pp.name = :accountName", map);// 获取指定名称的账号
			return !re.isEmpty();
		}
	}

	/**
	 * 根据账号的级别和类别，获取用于范围判断的entityIdType
	 * @param type账号级别
	 * @param isPrincipal账号类别
	 * @return 对应baseService中checkIfUnderControl方法需要的参数
	 * 返回值范围为-1到13
	 */
	public int getEntityIdType(AccountType type, int isPrincipal) {
		int entityIdType = 0;
		if(type.equals(AccountType.MINISTRY)){
			if (isPrincipal == 1) {// 主账号需要判断部级机构是否在管辖范围
				entityIdType = 1;
			} else {// 子账号需要判断部级管理人员是否在管辖范围
				entityIdType = 6;
			}
		} else if(type.equals(AccountType.PROVINCE)) {
			if (isPrincipal == 1) {// 主账号需要判断省级机构是否在管辖范围
				entityIdType = 2;
			} else {// 子账号需要判断省级管理人员是否在管辖范围
				entityIdType = 7;
			}
		} else if (type.equals(AccountType.LOCAL_UNIVERSITY) || type.equals(AccountType.MINISTRY_UNIVERSITY)) {
			if (isPrincipal == 1) {// 主账号需要判断校级机构是否在管辖范围
				entityIdType = 3;
			} else {// 子账号需要判断校级管理人员是否在管辖范围
				entityIdType = 8;
			}
		} else if (type.equals(AccountType.DEPARTMENT)) {
			if (isPrincipal == 1) {// 主账号需要判断院系是否在管辖范围
				entityIdType = 4;
			} else {// 子账号需要判断院系管理人员是否在管辖范围
				entityIdType = 9;
			}
		} else if (type.equals(AccountType.INSTITUTE)) {
			if (isPrincipal == 1) {// 主账号需要判断基地是否在管辖范围
				entityIdType = 5;
			} else {// 子账号需要判断基地管理人员是否在管辖范围
				entityIdType = 10;
			}
		} else if (type.equals(AccountType.EXPERT)) {
			entityIdType = 11;
		} else if (type.equals(AccountType.TEACHER)) {
			entityIdType = 12;
		} else if (type.equals(AccountType.STUDENT)) {
			entityIdType = 13;
		} else {
			entityIdType = -1;
		}
		return entityIdType;
	}

	/**
	 * 添加账号时，判断机构、人员是否已有账号。因为给专家、教师、学生
	 * 添加账号时，弹出层选择传递过来的参数是expertId、teacherId、
	 * studentId，此参数方便进行范围判断，但是account存储的是personId，
	 * 所以判断分为两类：
	 * 1、传递过来的参数是account中需要存储的belongId，此时查询账号表
	 * 进行判断。
	 * 2、传递过来的参数是expertId、teacherId、studentId等子表ID，则
	 * 需借助子表进行判断。
	 * @param id实体ID
	 * @param type账号级别(8、9、10查询子表进行判断)
	 * @return true有，false没有
	 */
	public boolean checkOwnAccount(String id, AccountType type, int isPrincipal) {
		if (id == null || id.isEmpty()) {// id为空，则视为已有账号
			return true;
		} else {
			Map map = new HashMap();
			map.put("id", id);
			List<String> re = new ArrayList<String>();// 查询结果
			if (type.equals(AccountType.EXPERT)) {// 专家账号根据专家子表ID查询人员ID，判断人员ID是否已被使用
				re = dao.query("select a.id from Account a, Expert e where a.person.id = e.person.id and e.id = :id and a.officer.id is null ", map);
			} else if (type.equals(AccountType.TEACHER)) {// 教师账号根据教师子表查询人员ID，判断人员ID是否已被使用
				re = dao.query("select a.id from Account a, Teacher t where a.person.id = t.person.id and t.id = :id and a.officer.id is null ", map);
			} else if (type.equals(AccountType.STUDENT)) {// 学生账号根据学生子表查询人员ID，判断人员ID是否已被使用
				re = dao.query("select a.id from Account a, Student s where a.person.id = s.person.id and s.id = :id and a.officer.id is null ", map);
			} else if (type.within(AccountType.MINISTRY, AccountType.LOCAL_UNIVERSITY)) {
				if (isPrincipal == 1) {//主账号
					re = dao.query("select a.id from Account a where a.agency.id = :id", map);
				} else {
					re = dao.query("select a.id from Account a where a.officer.id = :id", map);
				}
			} else if (type.equals(AccountType.DEPARTMENT)) {
				if (isPrincipal == 1) {//主账号
					re = dao.query("select a.id from Account a where a.department.id = :id", map);
				} else {
					re = dao.query("select a.id from Account a where a.officer.id = :id", map);
				}
			} else if (type.equals(AccountType.INSTITUTE)) {
				if (isPrincipal == 1) {//主账号
					re = dao.query("select a.id from Account a where a.institute.id = :id", map);
				} else {
					re = dao.query("select a.id from Account a where a.officer.id = :id", map);
				}
			}
			return !re.isEmpty();
		}
	}
	
	
	/**
	 * 根据通行证名查找通行证
	 * @param 通行证名（即用户名）
	 * @return 该通行证不存在，则返回null；否则返回通行证对象
	 */
	public Passport getPassportByName(String userName) {
		if (userName == null || userName.isEmpty()) {// 通行证名为空，则直接返回null
			return null;
		} else {
			Map map = new HashMap();
			map.put("userName", userName);
			List<Passport> re = dao.query("select p from Passport p where p.name = :userName or p.bindPhone = :userName or ( p.bindEmail = :userName and p.emailVerified = 1) ", map);// 查询指定的通行证
			return re.isEmpty() ? null : re.get(0);
		}
	}
	
	/**
	 * 根据通行证名查找账号list
	 * @param 通行证名（即用户名）
	 * @return 该通行证不存在，则返回null；否则返回账号list
	 */
	public List<Account> getAccountListByName(String userName) {
		if (userName == null || userName.isEmpty()) {
			return null;
		} else {
			Map map = new HashMap();
			map.put("userName", userName);
			List<Account> accountList = dao.query("select ac from Account ac left join ac.passport p where p.name = :userName or p.bindPhone = :userName or ( p.bindEmail = :userName and p.emailVerified = 1) order by ac.type asc", map);// 查询指定账号名对应的账号list
			return accountList;
		}
	}
	
	/**
	 * 添加指定类型的账号
	 * @param belongEntityId账号所属ID
	 * @param account账号对象
	 * @param passport通行证对象
	 * @param loginer当前登录对象
	 * @return 
	 * @return 账号ID
	 */
	public Map addAccount(String belongEntityId, Account account, Passport passport, LoginInfo loginer) {
		Map jsonMap = new HashMap();// json对象容器
		AccountType type = account.getType();
		int isPrincipal = account.getIsPrincipal();// 账号类别
		String userName;
		Passport oriPassport = this.getPassportByBelongId(belongEntityId, type, isPrincipal);
		
		// 设置账号相关属性
		String password = SignID.getRandomStringLower(8);// 生成随机密码
		passport.setPassword(MD5.getMD5(password));// 将密码用MD5加密存储
		passport.setPasswordRetrieveCode(null);// 设置密码重置验证码
		if(oriPassport != null) {
			oriPassport.setPassword(passport.getPassword());// 将密码用MD5加密存储
			oriPassport.setPasswordRetrieveCode(passport.getPasswordRetrieveCode());// 设置密码重置验证码
			oriPassport.setAllowedIp(passport.getAllowedIp());
			oriPassport.setMaxSession(passport.getMaxSession());
			oriPassport.setRefusedIp(passport.getRefusedIp());
			oriPassport.setStartDate(new Date());
			oriPassport.setStatus(account.getStatus());
			dao.addOrModify(oriPassport);
			account.setPassport(oriPassport);
			dao.modify(oriPassport);
			userName = oriPassport.getName();
		} else {
			dao.add(passport);
			userName = passport.getName();
			account.setPassport(passport);
			passport.setStartDate(new Date());
			passport.setStatus(account.getStatus());
			dao.modify(passport);
		}
		
		if (type.equals(AccountType.MINISTRY_UNIVERSITY)) {// 如果是校级账号，根据所属机构或人员重置账号级别
			// 部属高校机构类型为3，对应部属高校账号级别为4
			// 地方高校机构类型为4，对应地方高校账号级别为5
			if (isPrincipal == 1) {// 主账号查询所属机构进行设置
				Agency agency = (Agency) dao.query(Agency.class, belongEntityId);
				if(agency.getType() == 3) {
					type = AccountType.MINISTRY_UNIVERSITY;
				} else if(agency.getType() == 4) {
					type = AccountType.LOCAL_UNIVERSITY;
				}
			} else {// 子账号查询所属人员进行设置
				Officer officer = this.getOfficerByOfficerId(belongEntityId);
				if(officer.getAgency().getType() == 3){
					type = AccountType.MINISTRY_UNIVERSITY;
				}else if (officer.getAgency().getType() == 4) {
					type = AccountType.LOCAL_UNIVERSITY;
				}
//				type = officer.getAgency().getType() + 1;
			}
			account.setType(type);// 设置级别
		}
		
		account.setStartDate(new Date());// 设置账号创建时间
		
		
		/**
		 * 修正account表的belongId字段。
		 * 在教师、专家和学生账号表中，belongId保存的应该是personId，但是jsp中设的值分别是teacherId、
		 * expertId和studentId，所以需要修正。
		 */
		if (type.within(AccountType.MINISTRY, AccountType.LOCAL_UNIVERSITY)) {
			if (isPrincipal == 1) {
				Agency agency = dao.query(Agency.class, belongEntityId);
				account.setAgency(agency);
			} else if (isPrincipal == 0) {
				Officer officer = dao.query(Officer.class, belongEntityId);
				account.setOfficer(officer);
				Person person = dao.query(Person.class, officer.getPerson().getId());
				account.setPerson(person);
			}
		} else if (type.equals(AccountType.DEPARTMENT)) {
			if (isPrincipal == 1) {
				Department department = dao.query(Department.class, belongEntityId);
				account.setDepartment(department);
				Agency agency = dao.query(Agency.class, department.getUniversity().getId());
				account.setAgency(agency);
			} else {
				Officer officer = dao.query(Officer.class, belongEntityId);
				account.setOfficer(officer);
				Person person = dao.query(Person.class, officer.getPerson().getId());
				account.setPerson(person);
			}
		} else if (type.equals(AccountType.INSTITUTE)) {
			if (isPrincipal == 1) {
				Institute institute = dao.query(Institute.class, belongEntityId);
				account.setInstitute(institute);
				Agency agency = dao.query(Agency.class, institute.getSubjection().getId());
				account.setAgency(agency);
			} else if (isPrincipal == 0) {
				Officer officer = dao.query(Officer.class, belongEntityId);
				account.setOfficer(officer);
				Person person = dao.query(Person.class, officer.getPerson().getId());
				account.setPerson(person);
			}
		} else if (type.within(AccountType.EXPERT, AccountType.STUDENT)) {
			if (type.equals(AccountType.EXPERT)) {// 专家账号关联person表
				// 专家账号传过来的是expertId，但是要存personId
				Map map = new HashMap();
				map.put("belongEntityId", belongEntityId);
				Person person = (Person) dao.query("select p from Person p, Expert e where p.id = e.person.id and e.id = :belongEntityId", map).get(0);
				// 所属ID更新为personId
				account.setPerson(person);
			} else if (type.equals(AccountType.TEACHER)) {// 教师账号关联person表
				// 教师账号传过来的是teacherId，但是要存personId
				Map map = new HashMap();
				map.put("belongEntityId", belongEntityId);
				Person person = (Person) dao.query("select p from Person p, Teacher t where p.id = t.person.id and t.id = :belongEntityId", map).get(0);
				// 所属ID更新为personId
				account.setPerson(person);
			} else if (type.equals(AccountType.STUDENT)) {// 学生账号关联person表
				// 学生账号传过来的是studentId，但是要存personId
				Map map = new HashMap();
				map.put("belongEntityId", belongEntityId);
				Person person = (Person) dao.query("select p from Person p, Student s where p.id = s.person.id and s.id = :belongEntityId", map).get(0);
				// 所属ID更新为personId
				account.setPerson(person);
			}
		}
		String id = dao.add(account);// 添加账号
		
		// 设置账号角色
		this.setAccountRole(account);
		Mail mail = new Mail();
		// 生成邮件正文，发送邮件
		String body = "欢迎您访问中国高校社会科学管理数据库！" + 
					  "<br /><br />" + 
					  "访问地址：<a href=\"http://csdc.info/\">http://csdc.info/</a>" + 
					  "<br />" + 
					  "登录账号：" + userName + 
					  "<br />" + 
					  "初始密码：" + password + 
					  "<br /><br />" + 
					  "请您尽快登录系统，完善资料信息，并修改初始密码。";
		mail = email(account, mail, null,loginer, "创建账号" + userName, body, 1);
		jsonMap.put("mail", mail);
		jsonMap.put("id", id);
		return jsonMap;
//		return id;
	}

	/**
	 * 根据belongId和账号类型查找是否已有通行证
	 * @param belongId 
	 * 专家账号传过来的是expertId
	 * 教师账号传过来的是teacherId
	 * 学生账号传过来的是studentId
	 * @param accountType
	 * @param accountPrincipal (0:子账号，1：主账号)
	 * @return 有返回passport 没有返回null
	 */
	public Passport getPassportByBelongId(String belongId, AccountType accountType, Integer accountPrincipal) {
		Passport passport = null;
		Map parMap = new HashMap();
		if(belongId != null) {
			if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)) {// 教师专家和学生belongId为personId
				if (accountType.equals(AccountType.EXPERT)) {// 专家账号关联person表
					// 专家账号传过来的是expertId，但是要存personId
					Map map = new HashMap();
					map.put("belongId", belongId);
					Person person = (Person) dao.query("select p from Person p, Expert e where p.id = e.person.id and e.id = :belongId", map).get(0);
					// 所属ID更新为personId
					String personId = person.getId();
					parMap.put("personId", personId);
				} else if (accountType.equals(AccountType.TEACHER)) {// 教师账号关联person表
					// 教师账号传过来的是teacherId，但是要存personId
					Map map = new HashMap();
					map.put("belongId", belongId);
					Person person = (Person) dao.query("select p from Person p, Teacher t where p.id = t.person.id and t.id = :belongId", map).get(0);
					// 所属ID更新为personId
					String personId = person.getId();
					parMap.put("personId", personId);
				} else if (accountType.equals(AccountType.STUDENT)) {// 学生账号关联person表
					// 学生账号传过来的是studentId，但是要存personId
					Map map = new HashMap();
					map.put("belongId", belongId);
					Person person = (Person) dao.query("select p from Person p, Student s where p.id = s.person.id and s.id = :belongId", map).get(0);
					// 所属ID更新为personId
					String personId = person.getId();
					parMap.put("personId", personId);
				}
				List<Passport> passports = dao.query("select distinct pp from Account a left join a.passport pp where a.person.id = :personId", parMap);
				if(passports.size() != 0) {passport = passports.get(0);}
				parMap.remove(belongId);
			} else if (accountType.compareTo(AccountType.ADMINISTRATOR) > 0 && accountType.compareTo(AccountType.EXPERT) < 0) {
				parMap.put("belongId", belongId);
				if (accountPrincipal == 0) { //管理人员子账号的账号belongId为officerId,管理人员账号既存了officerId同时也存了personId，此时需要根据officerId找到personId再去找passport
					List<String> personIds = dao.query("select p.id from Officer o left join o.person p where o.id = :belongId", parMap);
					parMap.remove(belongId);
					String personId = personIds.get(0);
					parMap.put("personId", personId);
					List<Passport> passports = dao.query("select distinct pp from Account a left join a.passport pp where a.person.id  = :personId", parMap);
					if(passports.size() != 0) {passport = passports.get(0);}
				} else { //管理人员主账号的账号belongId为agencyId
					List<Passport> passports = dao.query("select distinct pp from Account a left join a.passport pp, Agency ag where a.agency.id = :belongId", parMap);
					if(passports.size() != 0) {passport = passports.get(0);}
				}
			}
		}
		return passport;
	}
	
	/*
	 * 教师账号注册
	 */
	public String register(Account account, Passport passport) {
		Date currentDate = new Date();
		Date expireDate = new Date();
		long expireTime=(currentDate.getTime()/1000)+60*60*24*365;
		expireDate.setTime(expireTime*1000);
		account.setStartDate(currentDate);
		account.setExpireDate(expireDate);
		passport.setMaxSession(5);
		account.setStatus(1);
		account.setCreateType(1);//1表示注册 0表示分配
		dao.add(passport);
		account.setPassport(passport);
		String id = dao.add(account);// 添加账号
		return id;
	}
	
	/**
	 * 修改账号信息
	 * @param oldAccount oldPassport原始账号通行证
	 * @param account passport更新信息
	 * @param loginer当前登录对象
	 * @return 账号ID
	 */
	public String modifyAccount(Account oldAccount, Account account, Passport oldPassport, Passport passport, LoginInfo loginer) {
		// 更新账号基本属性
		oldPassport.setName(passport.getName());// 修改账号名
		oldAccount.setStatus(account.getStatus());// 修改账号状态
		oldAccount.setExpireDate(account.getExpireDate());// 修改有效期
		oldPassport.setPasswordWarning(passport.getPasswordWarning());// 修改账号登录提示
		
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号才能修改以下三个属性
			oldPassport.setAllowedIp(passport.getAllowedIp());// 允许登录ip
			oldPassport.setRefusedIp(passport.getRefusedIp());// 拒绝登录ip
			oldPassport.setMaxSession(passport.getMaxSession());// 最大连接数
		}
		
		dao.modify(oldAccount);// 更新账号信息
		dao.modify(oldPassport);// 更新通行证
		updatePassport(oldPassport.getId());
		
		return oldAccount.getId();
	}
	
	/**
	 * 修改账号信息
	 * @param oldAccount oldPassport原始账号通行证
	 * @param account passport更新信息
	 * @param loginer当前登录对象
	 * @return 账号ID
	 */
	public String modifyPassport(Passport oldPassport, Passport passport, LoginInfo loginer) {
		// 更新账号基本属性
		oldPassport.setName(passport.getName());// 修改账号名
		oldPassport.setPasswordWarning(passport.getPasswordWarning());// 修改账号登录提示
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号才能修改以下三个属性
			oldPassport.setAllowedIp(passport.getAllowedIp());// 允许登录ip
			oldPassport.setRefusedIp(passport.getRefusedIp());// 拒绝登录ip
			oldPassport.setMaxSession(passport.getMaxSession());// 最大连接数
		}
		dao.modify(oldPassport);// 更新通行证
		
		return oldPassport.getId();
	}

	/**
	 * 获取指定账号，所属的机构或人员ID及名称
	 * @param account指定的账号
	 * @return belongId所属实体ID belongName所属实体名称
	 */
	public String[] getAccountBelong(Account account) {
		// 账号所属非外键，需要手动查询相关所属信息
		String[] belongIdName = {"", ""};// 账号所属信息，0位记录ID，1位记录NAME
		if (account != null) {// 账号存在，则进行关联查询
			AccountType type = account.getType();// 账号级别
			int isPrincipal = account.getIsPrincipal();// 账号类别
			String belongId = this.getBelongIdByAccount(account);
			if (isPrincipal == 1) {// 主账号则需进一步判断账号级别
				if (type.equals(AccountType.MINISTRY) || type.equals(AccountType.PROVINCE) || type.equals(AccountType.MINISTRY_UNIVERSITY) || type.equals(AccountType.LOCAL_UNIVERSITY)) {// 部、省、校级主账号，查询agency表
					Agency agency = dao.query(Agency.class, belongId);
					if (agency != null) {
						belongIdName[0] = agency.getId();
						belongIdName[1] = agency.getName();
					}
				} else if (type.equals(AccountType.DEPARTMENT)) {// 院系主账号，查询department表
					Department department = dao.query(Department.class, belongId);
					if (department != null) {
						belongIdName[0] = department.getId();
						belongIdName[1] = department.getName();
					}
				} else if (type.equals(AccountType.INSTITUTE)) {// 基地主账号，查询institute表
					Institute institute = dao.query(Institute.class, belongId);
					if (institute != null) {
						belongIdName[0] = institute.getId();
						belongIdName[1] = institute.getName();
					}
				} else {// 外专家、教师、学生查询person表
					Person person = dao.query(Person.class, belongId);
					if (person != null) {
						belongIdName[0] = person.getId();
						belongIdName[1] = person.getName();
					}
				}
			} else {// 子账号查询officer表
				Officer officer = this.getOfficerByOfficerId(belongId);
				if (officer != null) {
					belongIdName[0] = officer.getPerson().getId();
					belongIdName[1] = officer.getPerson().getName();
				}
			}
		}
		return belongIdName;
	}

	/**
	 * 查看账号信息
	 * @param account待查看的账号
	 * @param jsonMap返回前端的数据
	 * @param loginer当前登录对象
	 * @return jsonMap包含相关数据的map对象
	 */
	public Map viewAccount(Account account, Map jsonMap, LoginInfo loginer) {
		AccountType type = account.getType();// 待查看的账号级别
		int isPrincipal = account.getIsPrincipal();// 待查看张类别
		
		Passport passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
		Agency agency;// 部、省、校级机构对象
		Department department;// 院系对象
		Institute institute;// 基地对象
		Person person;// 人员对象
		String belongId = this.getBelongIdByAccount(account);
		// 查询账号所属信息，并将其存入jsonMap对象
		if (isPrincipal == 1) {// 主账号需进一步判断账号级别
			if (type.equals(AccountType.MINISTRY) || type.equals(AccountType.PROVINCE) || type.equals(AccountType.MINISTRY_UNIVERSITY) || type.equals(AccountType.LOCAL_UNIVERSITY)) {// 部、省、校级主账号，查询agency表
				agency = dao.query(Agency.class, belongId);
				jsonMap.put("belongAgencyId", agency.getId());
				jsonMap.put("belongAgencyName", agency.getName());
			} else if (type.equals(AccountType.DEPARTMENT)) {// 院系主账号，查询department表
				department = dao.query(Department.class, belongId);
				agency = (Agency) dao.query(Agency.class, department.getUniversity().getId());
				
				jsonMap.put("belongDepartmentId", department.getId());
				jsonMap.put("belongDepartmentName", department.getName());
				jsonMap.put("belongAgencyId", agency.getId());
				jsonMap.put("belongAgencyName", agency.getName());
			} else if (type.equals(AccountType.INSTITUTE)) {// 基地主账号，查询institute表
				institute = dao.query(Institute.class, belongId);
				agency = (Agency) dao.query(Agency.class, institute.getSubjection().getId());
				
				jsonMap.put("belongInstituteId", institute.getId());
				jsonMap.put("belongInstituteName", institute.getName());
				jsonMap.put("belongAgencyId", agency.getId());
				jsonMap.put("belongAgencyName", agency.getName());
			} else if (type.equals(AccountType.EXPERT) || type.equals(AccountType.TEACHER) || type.equals(AccountType.STUDENT)) {// 外专家、教师、学生查询person表
				person = dao.query(Person.class, belongId);
				
				jsonMap.put("belongPersonId", person.getId());
				jsonMap.put("belongPersonName", person.getName());
			}
		} else {// 子账号查询officer表
			Officer officer = dao.query(Officer.class, belongId);
			person = (Person) dao.query(Person.class, officer.getPerson().getId());
			
			jsonMap.put("belongPersonId", person.getId());
			jsonMap.put("belongPersonName", person.getName());
			
			// 查询管理人员所属机构信息
			if (type.equals(AccountType.MINISTRY) || type.equals(AccountType.PROVINCE) || type.equals(AccountType.MINISTRY_UNIVERSITY) || type .equals(AccountType.LOCAL_UNIVERSITY)) {// 部、省、校级子账号，查询agency表
				agency = (Agency) dao.query(Agency.class, officer.getAgency().getId());
				
				jsonMap.put("belongAgencyId", agency.getId());
				jsonMap.put("belongAgencyName", agency.getName());
			} else if (type.equals(AccountType.DEPARTMENT)) {// 院系主账号，查询department表
				department = (Department) dao.query(Department.class, officer.getDepartment().getId());
				agency = (Agency) dao.query(Agency.class, department.getUniversity().getId());
				
				jsonMap.put("belongDepartmentId", department.getId());
				jsonMap.put("belongDepartmentName", department.getName());
				jsonMap.put("belongAgencyId", agency.getId());
				jsonMap.put("belongAgencyName", agency.getName());
			} else if (type.equals(AccountType.INSTITUTE)) {// 基地主账号，查询institute表
				institute = (Institute) dao.query(Institute.class, officer.getInstitute().getId());
				agency = (Agency) dao.query(Agency.class, institute.getSubjection().getId());
				
				jsonMap.put("belongInstituteId", institute.getId());
				jsonMap.put("belongInstituteName", institute.getName());
				jsonMap.put("belongAgencyId", agency.getId());
				jsonMap.put("belongAgencyName", agency.getName());
			}
		}
		
		// 获得账号角色名称
		String rolename = this.getRoleName(account.getId());
		
		if (!loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {// 如果非系统管理员，清除不需要显示的信息
			passport.setAllowedIp(null);
			passport.setRefusedIp(null);
			passport.setLastLoginIp(null);
			account.setLastLoginDate(null);
			account.setLastLoginSystem(null);
			account.setLoginCount(0);
			passport.setLoginCount(0);
			passport.setMaxSession(0);
		}
		
		jsonMap.put("account", account);
		jsonMap.put("passport", passport);
		jsonMap.put("rolename", rolename);
		return jsonMap;
	}

	/**
	 * 根据账号id获得其角色名称，并组成一个以中文逗号隔开的字符串
	 * @param accountId 账号ID
	 * @return 角色名称字符串
	 */
	public String getRoleName(String accountId) {
		String rolename = "";// 角色名称字符串
		if (accountId != null && !accountId.isEmpty()) {// 账号ID非空，则查询关联的角色信息
			Map map = new HashMap();
			map.put("accountId", accountId);
			List<String> roleList = dao.query("select r.name from Role r, AccountRole ar where r.id = ar.role.id and ar.account.id = :accountId order by r.name asc", map);// 查询指定账号拥有的角色
			
			for (int i = 0; i < roleList.size(); i++) {// 遍历该账号拥有的角色，拼接为以"，"分隔的字符串
				rolename += roleList.get(i) + "，";
			}
			
			if (!rolename.equals("")) {// 如果角色信息存在，则清除末尾的"，"
				rolename = rolename.substring(0, rolename.length() - 1);
			}
		}
		return rolename;
	}

	

	/**
	 * 启用账号
	 * @param ids账号ID集合
	 */
	public void enable(List<String> ids, Date date) {
		Account account;// 账号对象
		for (int i = 0; i < ids.size(); i++) {// 遍历所有账号，进行启用
			account = (Account) dao.query(Account.class, ids.get(i));// 获取账号对象
			account.setExpireDate(date);// 设置账号有效期
			account.setStatus(1);// 设置账号启用状态
			dao.modify(account);// 更新账号数据
			updatePassport(account.getPassport().getId());
		}
	}

	/**
	 * 停用账号
	 * @param ids账号ID集合
	 */
	public void disable(List<String> ids) {
		Account account;// 账号对象
		for (int i = 0; i < ids.size(); i++) {// 遍历所有账号，进行停用
			account = (Account) dao.query(Account.class, ids.get(i));// 获取账号对象
			account.setStatus(0);// 设置账号停用状态
			dao.modify(account);// 更新账号数据
			updatePassport(account.getPassport().getId());
		}
	}

	/**
	 * 根据账号级别、类别、ID以及是否批量操作，获取已分配和可分配角色
	 * 如果是批量操作，则只能分配指定账号类型的默认角色。
	 * 如果是单个操作，则先获取指定机构的默认角色，如果是系统管理员，还要取出非默认角色。然后获取该账号已分配角色，并从可分配角色中，去除已分配角色。
	 * @param account账号对象
	 * @param type 操作类别(1列表批量,0查看单个)
	 * @param loginer当前登录对象
	 * @return 可分配和已分配角色集合
	 */
	public List<Role>[] getAssignRole(Account account, int type, LoginInfo loginer, List<String> accountIds) {
		List<Role> toAssignRole;// 当前管理账号，可分配角色
		List<Role> assignRole = new ArrayList<Role>();// 当前账号，已分配角色
		List<Role> commonRoles = new ArrayList<Role>();//列表页面选择多个账号时，选择他们已经分配账号的集合
		
		int position = this.getDefaultRoleType(account.getType(), account.getIsPrincipal());// 获取指定账号类型默认角色进行匹配的位置
		Account  act = loginer.getAccount();//获取当前登陆者账号
		// 读取该账号类型的默认可分配角色
		Map map = new HashMap();
		map.put("index", position);
		// 将指定账号类型的默认角色添加到可分配角色列表
		toAssignRole = dao.query("select r from Role r left join r.roleAgency ra where substring(r.defaultAccountType, :index, 1) = '1' and ra is null order by r.name asc group by r", map);
		map.remove("index");

		
		// 查看页面对单个账号进行角色分配，可选角色包括指定账号类型的默认角色、指定机构的默认角色和非默认角色
		List<Role> privateRole;// 机构默认角色
		if (type == 0) {// 
			if (account.getType().equals(AccountType.MINISTRY) || account.getType().equals(AccountType.PROVINCE) || account.getType().equals(AccountType.MINISTRY_UNIVERSITY) || account.getType().equals(AccountType.LOCAL_UNIVERSITY)) {// 部、省、校级账号需要查询机构默认角色
				if (account.getIsPrincipal() == 1) {// 主账号根据机构ID查询机构默认角色
					map.put("agencyId", "%" + this.getBelongIdByAccount(account) + "%");
					privateRole = dao.query("select r from Role r left join r.roleAgency ra where (r.defaultAccountType = '11' or r.defaultAccountType = '10') and ra.defaultAgency.id like :agencyId order by r.name asc group by r", map);
				} else {// 子账号要先读取所在机构，再根据机构ID查询机构默认角色
					map.put("officerId", this.getBelongIdByAccount(account));
					List<String> re = dao.query("select o.agency.id from Officer o where o.id = :officerId", map);
					map.remove("officerId");
					map.put("agencyId", "%" + re.get(0) + "%");
					privateRole = dao.query("select r from Role r left join r.roleAgency ra where (r.defaultAccountType = '11' or r.defaultAccountType = '01') and ra.defaultAgency.id like :agencyId order by r.name asc group by r", map);
				}
				map.remove("agencyId");
				
				toAssignRole.addAll(privateRole);// 将机构默认角色添加到可分配角色列表
			}
			// 如果是系统管理员，则取出非默认角色(由于所有的子角色均是非默认角色，因此系统管理员只能分配自己创建的子角色）
			// 非系统管理员只能分配自己创建的角色
			map.put("roleType", RoleInfo.ERROR_UNDEFAULT_ROLL);
			map.put("act", act);
			privateRole = dao.query("select r from Role r where r.defaultAccountType = :roleType and r.account = :act order by r.name asc", map);
			map.remove("roleType");
			toAssignRole.addAll(privateRole);// 将非默认角色添加到可分配角色列表
			// 列出已分配角色
			StringBuffer hql = new StringBuffer();
			hql.append("select r from Role r, AccountRole ar where r.id = ar.role.id and ar.account.id = :accountId ");
			hql.append(" order by r.name asc");
			map.put("accountId", account.getId());
			assignRole = dao.query(hql.toString(), map);// 查询已分配角色
			
			for (Role r : assignRole) {// 遍历已分配角色
				if (toAssignRole.contains(r)) {// 从可分配角色中剔除已分配角色
					toAssignRole.remove(r);
				}
			}
		} else if (type ==1) {//列表查出所选账号已分配角色的交集
			if (accountIds != null) {// 账号ID非空，则进行角色分配
				for (String accountId : accountIds) {// 遍历待分配角色的账号
					// 列出已分配角色
					StringBuffer hql = new StringBuffer();
					hql.append("select r from Role r, AccountRole ar where r.id = ar.role.id and ar.account.id = :accountId ");
					hql.append(" order by r.name asc");
					map.put("accountId", accountId);
					assignRole = dao.query(hql.toString(), map);// 查询已分配角色
					if(commonRoles.isEmpty())
						commonRoles.addAll(assignRole);
					commonRoles = fetchCommon(commonRoles, assignRole);
				}
				for (Role r : commonRoles) {// 遍历已分配角色
					if (toAssignRole.contains(r)) {// 从可分配角色中剔除已分配角色
						toAssignRole.remove(r);
					}
				}
				
			}
		}
		if (type == 1) {
			List[] tmp = {toAssignRole, commonRoles};// 将可分配角色列表，已分配角色列表封装为一个数组返回
			return tmp;
		} else {
			List[] tmp = {toAssignRole, assignRole};// 将可分配角色列表，已分配角色列表封装为一个数组返回
			return tmp;
		}
	}
	
	/**
	 * 获取两个角色集合的公共角色
	 * @param list1
	 * @param list2
	 * @return 公共角色
	 */
	public List fetchCommon(List<Role> list1,List<Role> list2){
		List<Role> roles = new ArrayList<Role>();
		for (int i = 0; i < list1.size(); i++) {
			if(list2.contains(list1.get(i)))
				roles.add(list1.get(i));
		}
		return roles;
	}
	/**
	 * 分配角色
	 * @param loginer当前登录对象
	 * @param accountIds待分配角色账号ID集合
	 * @param roleIds分配角色ID集合
	 * @param type区分是列表(1)调用分配角色，还是查看页面(0)调用分配角色
	 */
	public void assignRole(LoginInfo loginer, List<String> accountIds, String[] roleIds, int type) {
		if (accountIds != null) {// 账号ID非空，则进行角色分配
			for (String accountId : accountIds) {// 遍历待分配角色的账号
				Account account;// 账号对象
				Role role;// 角色对象
				
				int position;// 账号类型对应的角色匹配索引位置
				String agencyId = "";// 账号实体所在机构ID
				AccountType accountType;// 账号级别
				int accountPrincipal;// 账号类别
				
				String defaultAccountType;// 默认所属账号类型
				String defaultAgencyId = null;// 默认所属机构
				
				Map map = new HashMap();
				List<String> arIds;// 账号角色对应关系ID
				
				account = (Account) dao.query(Account.class, accountId);// 获得账号对象
				
				// 获得当前遍历账号所有角色
				map.put("accountId", accountId);
				arIds = dao.query("select ar.role.id from AccountRole ar where ar.account.id = :accountId", map);
				map.remove("accountId");
				
				// 获取该账号可分配角色的账号类型及角色机构ID，以便进行分配控制
				position = this.getDefaultRoleType(account.getType(), account.getIsPrincipal());// 获得该账号对应的默认角色类型
				accountType = account.getType();// 账号级别
				accountPrincipal = account.getIsPrincipal();// 账号类别
				
				if (accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.PROVINCE) || accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {// 部、省、校级账号读取所属机构ID
					if (accountPrincipal == 1) {// 主账号指定读取账号的belongId
						agencyId = account.getAgency().getId();
					} else {// 子账号需要根据officerId查询所属机构ID
						map.put("officerId", account.getOfficer().getId());
						agencyId = dao.query("select o.agency.id from Officer o where o.id = :officerId", map).get(0).toString();
						map.remove("officerId");
					}
				}
				
				if (roleIds != null) {// 待分配的角色非空，则进行角色剔除
					for (String o : roleIds) {// 遍历传过来的角色集合
						role = (Role) dao.query(Role.class, o);// 获取指定角色
						
						if (role != null) {// 角色存在，则判断是否能够应用到本次遍历的账号
							defaultAccountType = role.getDefaultAccountType();// 角色默认账号类型字符串
							
							// 角色默认机构ID字符串
							Map parMap = new HashMap();
							parMap.put("roleId", role.getId());
							List<RoleAgency> roleAgencies = dao.query("select ra from RoleAgency ra where ra.role.id =:roleId", parMap);
							for(RoleAgency roleAgency:roleAgencies){
								defaultAgencyId += roleAgency.getDefaultAgency().getId() + "; ";
							}
							if(roleAgencies.size() != 0){
								defaultAgencyId = defaultAgencyId.substring(0, defaultAgencyId.length() - 2);
								parMap.put("defaultAgencyId", defaultAgencyId);
							}
							// 如果机构ID为空，则角色类型为15位字符串，当指定位为'1'时可分配，或者该角色为非默认角色，系统管理员可分配
							// 如果机构ID不为空，且分配的为机构账号，则需要该角色指定的机构中包含该账号所在机构
							// hql字符截取位置从1开始，java从0开始
							if ((parMap.get("defaultAgencyId") == null && (defaultAccountType.charAt(position - 1) == '1' || defaultAccountType.equals(RoleInfo.ERROR_UNDEFAULT_ROLL)))
									|| ((accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.PROVINCE) || accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && parMap.get("defaultAgencyId") != null  && ((String) parMap.get("defaultAgencyId")).indexOf(agencyId) > -1)) {// 通过是否默认账号类型角色，判断是否合法角色
								if (arIds.contains(o)) {// 如果已经有此角色，则从arIds中删除该角色ID，剩下的角色后面会被清除
									arIds.remove(o);
								} else {// 如果没有此角色，则添加本次遍历账号对该角色的引用
									AccountRole ar = new AccountRole();// 用于添加账号、角色对应关系
									ar.setAccount(account);
									ar.setRole(role);
									dao.add(ar);
								}
								parMap.remove("defaultAgencyId");
							} 
						}
					}
				}
				
				// 删除多余的角色引用
				for (String o : arIds) {
					role = (Role) dao.query(Role.class, o);// 查询指定的角色
					if (role != null) {// 角色存在，则进行该账号对此角色的清除工作
						/**
						 * 该账号剩下的角色分为，
						 * 1、该账号类型的公共角色，此类角色一律清除
						 * 2、该账号所在机构特有角色，此类角色，若本功能通过列表调用，则保留；若本功能通过查看调用，则清除
						 * 3、该账号非默认角色，系统管理员才能清除
						 * 总之：
						 * 如果是列表页面进行的角色分配，则不处理非默认角色和机构角色
						 * 如果是查看页面进行的角色分配，则普通账号不处理非默认角色
						 */
						// 角色默认机构ID字符串
						Map parMap = new HashMap();
						parMap.put("roleId", role.getId());
						List<RoleAgency> roleAgencies = dao.query("select ra from RoleAgency ra where ra.role.id =:roleId", parMap);
						for(RoleAgency roleAgency:roleAgencies){
							defaultAgencyId += roleAgency.getDefaultAgency().getId() + "; ";
						}
						if(roleAgencies.size() != 0){
							defaultAgencyId = defaultAgencyId.substring(0, defaultAgencyId.length() - 2);
						}
						//type=0:查看页面进行角色分配； type=1：列表页面进行的角色分配
						if (type == 0 || (type == 1 && defaultAgencyId == null && !role.getDefaultAccountType().equals(RoleInfo.ERROR_UNDEFAULT_ROLL))) {
							map.put("accountId", account.getId());
							map.put("roleId", role.getId());
							List<String> delAccountRoleIds = dao.query("select ar.id from AccountRole ar where ar.account.id = :accountId and ar.role.id = :roleId", map);// 查找此次遍历账号对此角色的引用
							map.remove("accountId");
							for (String entityId : delAccountRoleIds) {
								dao.delete(AccountRole.class, entityId);
							}
							//如果当前账号依赖某个角色创建了一个子角色，当这个角色没移除之后要删除子角色
							List<String> deleteRoleIds = dao.query("select r.id from Role r where r.parentId = :roleId", map);
							map.remove("roleId");
							for (String deleteRoleId : deleteRoleIds) {
								dao.delete(Role.class, deleteRoleId);
							}
						} 
					}
				}
			}
		}
	}

	/**
	 * 根据传过来的account对象，查找相应子表中的email
	 * @param account待查找email的account对象
	 * @return email(若为空，则返回"")
	 */
	public String getEmailByAccount(Account account) {
		if (account == null) {// 账号对象为空，则直接返回""
			return "";
		} else {// 账号存在，则查询相应的所属表获取email
			StringBuffer hql = new StringBuffer();
			Map map = new HashMap();
			AccountType type = account.getType();// 账号级别
			int isPrincipal = account.getIsPrincipal();// 账号类别
			
			// 根据账号级别和类别查询相应所属表，获取email信息
			if (type.equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号没有所属信息，即没有email，返回""
				return "";
			} else if ((type.equals(AccountType.MINISTRY) || type.equals(AccountType.PROVINCE) || type.equals(AccountType.MINISTRY_UNIVERSITY) || type.equals(AccountType.LOCAL_UNIVERSITY)) && isPrincipal == 1) {// 部、省、校级主账号从agency表查找社科主管部门email
				hql.append("select a.semail from Agency a where a.id = :belongId");
				map.put("belongId", account.getAgency().getId());
			} else if (type.equals(AccountType.DEPARTMENT) && isPrincipal == 1) {// 院系主账号从department表查找email
				hql.append("select d.email from Department d where d.id = :belongId");
				map.put("belongId", account.getDepartment().getId());
			} else if (type.equals(AccountType.INSTITUTE) && isPrincipal == 1) {// 基地主账号从institute表查找email
				hql.append("select i.email from Institute i where i.id = :belongId");
				map.put("belongId", account.getInstitute().getId());
			} else if (type.equals(AccountType.EXPERT) || type.equals(AccountType.TEACHER) || type.equals(AccountType.STUDENT)) {// 专家、教师、学生从person表查找email
				hql.append("select p.email from Person p where p.id = :belongId");
				map.put("belongId", this.getBelongIdByAccount(account));
			} else {// 管理人员从Officer表查找email
				hql.append("select p.email from Person p, Officer o where o.id = :belongId and o.person.id = p.id");
				map.put("belongId", this.getBelongIdByAccount(account));
			}
			List<String> emails = dao.query(hql.toString(), map);
			if (emails == null || emails.isEmpty()) {// 邮箱不存在，则返回""
				return "";
			} else {// 邮箱存在，则返回email
				return emails.get(0);
			}
		}
	}
	
	/**
	 * 组装邮件
	 * @param account待发邮件账号
	 * @param loginer当前登录对象
	 * @param subject邮件主题
	 * @param body邮件正文
	 * @param isHtml是否页面
	 */
	public Mail email(Account account, Mail mail, String bindEmail, LoginInfo loginer, String subject, String body, int isHtml) {
		// 生成邮件对象
		if (bindEmail == null) {
			String email = this.getEmailByAccount(account);// 获取该账号所属者的邮箱
			mail.setSendTo(email);
		} else {
			mail.setSendTo(bindEmail);
		}
		// 设置邮件对象属性
		mail.setSubject("[SMDB] " + subject);
		mail.setReplyTo("serv@csdc.info");// 认证地址
		mail.setBody(body);
		mail.setIsHtml(isHtml);
		mail.setCreateDate(new Date());
		
		mail.setFinishDate(null);
		mail.setSendTimes(0);
		mail.setStatus(0);
		
		// 设置邮件发送账号及发送者属性
		String accountBelong = "";// 账号所属名称
		String belongId = this.getBelongIdByAccount(account);
		if (loginer != null) {// 当前处于登录状态，则从登录对象中获取账号及账号所属者信息
			mail.setAccount(loginer.getAccount());// 设置发送账号
			
			// 从loginer中获取发送者名称信息
			if (loginer.getCurrentBelongUnitName() != null) {// 所属机构信息存在，则读取机构名称
				accountBelong = loginer.getCurrentBelongUnitName();
			}
			
			if (loginer.getCurrentPersonName() != null) {// 所属人员信息存在，则读取人员名称
				accountBelong = loginer.getCurrentPersonName();
			}
			
			mail.setAccountBelong(accountBelong);// 设置发送者名称
		} else {// 当前处于未登录状态，则需查找账号所属者信息
			mail.setAccount(account);// 设置发送账号
			AccountType type = account.getType();// 账号级别
			int isPrincipal = account.getIsPrincipal();// 账号类别
			
			// 查找账号所属
			if (isPrincipal == 0) {// 管理人员账号，根据officer查找所属人员名称
				Officer officer = this.getOfficerByOfficerId(belongId);
				if (officer != null) {// officer存在，
					accountBelong = officer.getPerson().getName();
				}
			} else {// 其它账号，需进一步判断账号级别
				if (type.equals(AccountType.EXPERT) || type.equals(AccountType.TEACHER) || type.equals(AccountType.STUDENT)) {// 专家、教师、学生账号，根据person查找所属人员名称
					Person person = dao.query(Person.class, belongId);// 查找指定人员
					if (person != null) {// 人员存在，则读取人员姓名
						accountBelong = person.getName();
					}
				} else if (type.equals(AccountType.MINISTRY) || type.equals(AccountType.PROVINCE) || type.equals(AccountType.MINISTRY_UNIVERSITY) || type.equals(AccountType.LOCAL_UNIVERSITY)) {// 部、省、校级主账号，根据agency查找所属机构名称
					Agency agency = dao.query(Agency.class, belongId);// 查找指定机构
					if (agency != null) {// 机构存在，则读取机构名称
						accountBelong = agency.getName();
					}
				} else if (type.equals(AccountType.DEPARTMENT)) {// 院系主账号，根据department查找所属机构名称
					Department department = dao.query(Department.class, belongId);// 查找指定院系
					if (department != null) {// 院系存在，则读取院系名称
						accountBelong = department.getName();
					}
				} else if (type.equals(AccountType.INSTITUTE)) {// 基地主账号，根据institute查找所属机构名称
					Institute institute = dao.query(Institute.class, belongId);// 查找指定基地
					if (institute != null) {// 基地存在，则读取基地名称
						accountBelong = institute.getName();
					}
				} else if (type.equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号设置为"系统管理员"
					accountBelong = "系统管理员";
				}
			}
			mail.setAccountBelong(accountBelong);// 设置邮件发送者信息
		}
		dao.add(mail);// 添加邮件
		return mail;
	}
	
	/**
	 * 重置密码，并邮件通知
	 * @param Account待处理账号
	 * @param loginer当前登录对象
	 * @param path邮件链接地址
	 */
	public Mail retrieveCode(Account account, LoginInfo loginer, String path) {
		String passwordRetrieveCode = MD5.getMD5(SignID.getRandomString(4));// 生成重置码
		Passport passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
		passport.setPasswordRetrieveCode(passwordRetrieveCode);// 将重置码保存到重置账号中
		passport.setPasswordRetrieveCodeStartDate(new Date());// 记录重置码生成时间
		dao.modify(passport);// 更新到数据库
		Mail mail = new Mail();
		// 生成密码重置链接和邮件正文
		String url = path + "selfspace/toResetPassword.action?entityId=" + account.getId() + "&pwRetrieveCode=" + passwordRetrieveCode;
		String body = "欢迎您访问中国高校社会科学管理数据库！" + 
					  "<br /><br />" +
					  "请点击以下链接进行密码重置，该链接24小时之内有效：" +
					  "<br />" +
					  "<a href=\"" + url + "\">" + url + "</a>" +
					  "<br /><br />" +
					  "如果该链接无法点击，请复制前面的链接地址到浏览器直接访问。" +
					  "<br />" +
					  "如果您没有进行过找密码操作，请忽略此邮件。";
		String name = this.getAccountBelongName(account);// 获取账号所属者名称
		mail = email(account, mail, null, loginer, "重置密码 " + name, body, 1);// 发送邮件通知
		return mail;
	}
	
	/**
	 * 绑定邮箱
	 * @param Account待处理账号
	 * @param loginer当前登录对象
	 * @param path邮件链接地址
	 */
	public Mail bindEmailCode(Account account, LoginInfo loginer, String path, String bindEmail, String bindEmailVerifyCode) {
		Passport passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
		Mail mail = new Mail();
		// 生成密码重置链接和邮件正文
		String url = path + "selfspace/verifyEmail.action?entityId=" + account.getId() + "&emailVerifyCode=" + bindEmailVerifyCode;
		String body = "欢迎您访问中国高校社会科学管理数据库！" + 
					  "<br /><br />" +
					  "请点击以下链接进行邮箱绑定确认：" +
					  "<br />" +
					  "<a href=\"" + url + "\">" + url + "</a>" +
					  "<br /><br />" +
					  "如果该链接无法点击，请复制前面的链接地址到浏览器直接访问。" +
					  "<br />" +
					  "如果您没有进行过邮箱绑定操作，请忽略此邮件。";
		String name = this.getAccountBelongName(account);// 获取账号所属者名称
		mail = email(account, mail, bindEmail, loginer, "绑定邮箱" + name, body, 1);// 发送邮件通知
		return mail;
	}
	
	/**
	 * 修改账号密码
	 * @param passport待处理账号
	 * @param password新密码
	 */
	public void modifyPassword(Passport passport, String password) {
		passport.setPassword(MD5.getMD5(password));// 将密码加密后存储
		dao.modify(passport);// 更新到数据库
	}

	/**
	 * 根据用户名查找用户账号信息
	 * @param username 用户名（即通行证名）
	 * @return 该用户拥有的账号信息数组（每个账号信息包含：账号类型，是否是主账号，账号ID，账号所属的名称，账号类型名称，账号状态， 账号所属的ID，院系、基地所属高校的名称，院系、基地所属高校的ID），拥有的角色个数，账号所属人员的ID
	 */
	public List<String[]> userInfo(String username) {

		List<Account> accountList = this.getAccountListByName(username);
		// 用于存储当前账号可选择的账号信息
		List<String[]> userList = new ArrayList<String[]>();
		
		for (Account account : accountList) {
			List accountRole = new ArrayList<String>();
			if (!account.getType().equals(AccountType.ADMINISTRATOR)) {
				accountRole = dao.query("select ac.id from AccountRole ac where ac.account.id = ?", account.getId());
			} else {
				accountRole.add("admin");
			}
			int status = account.getStatus();
			if((new Date()).after(account.getExpireDate())){
				account.setStatus(0);
				dao.modify(account);
				updatePassport(account.getPassport().getId());
				status = 0;
			}
			AccountType type = account.getType();// 账号级别
			int isPrincipal = account.getIsPrincipal();// 是否主账号
			String id = account.getId();// 账号ID
			String typeName = "";// 账号类型名称
			String name = "";//账号所属的名称
			String belongId = this.getBelongIdByAccount(account);//账号所属的ID
			String personId = "";//账号所属人员的ID
			String belongAgencyName = "";//院系、基地所属高校的名称
			String belongAgencyId = "";//院系、基地所属高校的ID
			if (type.equals(AccountType.ADMINISTRATOR)) {
				typeName = "系统管理员账号";
			} else if (type.equals(AccountType.MINISTRY)) {
				typeName = ((isPrincipal == 1) ? "部级主账号" : "部级子账号");
			} else if (type.equals(AccountType.PROVINCE)) {
				typeName = ((isPrincipal == 1) ? "省级主账号" : "省级子账号");
			} else if (type.equals(AccountType.MINISTRY_UNIVERSITY)) {
				typeName = ((isPrincipal == 1) ? "部属高校主账号" : "部属高校子账号");
			} else if (type.equals(AccountType.LOCAL_UNIVERSITY)) {
				typeName = ((isPrincipal == 1) ? "地方高校主账号" : "地方高校子账号");
			} else if (type.equals(AccountType.DEPARTMENT)) {
				typeName = ((isPrincipal == 1) ? "高校院系主账号" : "高校院系子账号");
			} else if (type.equals(AccountType.INSTITUTE)) {
				typeName = ((isPrincipal == 1) ? "研究基地主账号" : "研究基地子账号");
			} else if (type.equals(AccountType.EXPERT)) {
				typeName = "外部专家账号";
			} else if (type.equals(AccountType.TEACHER)) {
				typeName = "教师账号";
			} else if (type.equals(AccountType.STUDENT)) {
				typeName = "学生账号";
			} else {
				typeName = "未知类型账号";
			}
			if (type.within(AccountType.MINISTRY, AccountType.LOCAL_UNIVERSITY)) {// 部级、省级、校级账号，需要查找agency信息
				if (isPrincipal == 1) {// 部级、省级、校级主账号查找机构信息
					Agency agency = dao.query(Agency.class, belongId);
					name = agency.getName();
				} else {// 部级、省级、校级子账号查找机构和人员信息
					Officer officer = this.getOfficerByOfficerId(account.getOfficer().getId());
					name = officer.getPerson().getName();
					personId = officer.getPerson().getId();
				}
			} else if (type.equals(AccountType.DEPARTMENT)) {// 院系账号，需要查找department信息
				if (isPrincipal == 1) {// 院系主账号查找院系信息
					Department department = dao.query(Department.class, belongId);
					Agency agency = (Agency) dao.query(Agency.class, department.getUniversity().getId());
					name = department.getName();
					belongAgencyName = agency.getName();//院系所属高校的名称
					belongAgencyId = agency.getId();//所属高校的ID
				} else {// 院系子账号查找院系和人员信息
					Officer officer = this.getOfficerByOfficerId(account.getOfficer().getId());
					name = officer.getPerson().getName();
				}
			} else if (type.equals(AccountType.INSTITUTE)) {// 基地账号，需要查找institute信息
				if (isPrincipal == 1) {// 基地主账号
					Institute institute = dao.query(Institute.class, belongId);
					Agency agency = (Agency) dao.query(Agency.class, institute.getSubjection().getId());
					name = institute.getName();
					belongAgencyName = agency.getName();//基地所属机构的名称
					belongAgencyId = agency.getId();//基地所属机构的ID
				} else {// 基地子账号
					Officer officer = this.getOfficerByOfficerId(account.getOfficer().getId());
					name = officer.getPerson().getName();
				}
			} else if (type.equals(AccountType.EXPERT)) {// 外部专家账号，需要查找人员信息
				Expert expert = this.getExpertByPersonId(account.getPerson().getId());
				name = expert.getPerson().getName();
			} else if (type.equals(AccountType.TEACHER)) {// 教师账号，需要查找学校和人员信息
				Teacher teacher = this.getTeacherByPersonId(account.getPerson().getId());
				name = teacher.getPerson().getName();
			} else if (type.equals(AccountType.STUDENT)) {// 学生账号，需要查找学校和人员信息
				Student student = this.getStudentByPersonId(account.getPerson().getId());
				name = student.getPerson().getName();
			} else if (type.equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号提示为"系统管理员"
				name = "系统管理员";
			} else {// 其它账号类型统一设置为"未知账号"
				name = "未知所属";
			}
			userList.add(new String[]{type.toString(), Integer.toString(isPrincipal), id, name, typeName, Integer.toString(status), belongId,belongAgencyName,belongAgencyId,Integer.toString(accountRole.size()),personId});
		}
		return userList;
	
	}
	
	public List getVersionCode(int flag){
		
		//HashMap<String,Integer> map = new HashMap<String,Integer>();
		List<Integer> list;
		if(flag== 1){
			list = dao.query("select sd.value from SystemConfig sd where LOWER(sd.id) like 'android%'");
		}else{
			list = dao.query("select sd.value from SystemConfig sd where LOWER(sd.id) like 'ios%'");
		}
		/*map.put("forceversion", (Integer)list.get(0));
		map.put("lastversion", (Integer)list.get(1));*/
		return list;
		
	}
	
}
