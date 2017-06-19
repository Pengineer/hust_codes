package csdc.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import csdc.bean.Account;
import csdc.bean.Mail;
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMember;
import csdc.bean.Teacher;
import csdc.service.IMobileActivateService;
import csdc.tool.MD5;
import csdc.tool.SignID;
import csdc.tool.bean.AccountType;
import csdc.tool.mail.SendUndoneMails;

@Transactional
@SuppressWarnings("rawtypes")
public class MobileActivateService extends BaseService implements IMobileActivateService {
	@Autowired
	public SendUndoneMails sendUndoneMails;
	@Autowired
	private TransactionTemplate txTemplateRequiresNew;

	public int checkPersonInfo(String projectNumber, String idcardNumber) {
		//校验项目批准号是否存在
		ProjectGranted projectGranted = (ProjectGranted) dao.queryUnique("select pg from ProjectGranted pg where pg.number= ?", projectNumber);
		if (projectGranted==null) {
			return 2;
		}
		//校验项目批准号和身份证号是否对应
		Map map = new HashMap();
		map.put("applicationId", projectGranted.getApplicationId());
		map.put("idcardNumber", idcardNumber);
		map.put("groupNumber", projectGranted.getMemberGroupNumber());
		List<ProjectMember> members = dao.query("select pm from ProjectMember pm where pm.applicationId=:applicationId and pm.idcardNumber=:idcardNumber and pm.groupNumber=:groupNumber ", map);
		if (members.size()==0) {
			members = dao.query("select pm from ProjectMember pm where pm.applicationId=:applicationId and pm.member.idcardNumber=:idcardNumber and pm.groupNumber=:groupNumber ", map);
		}
		if (members.size()==0) {
			return 3;
		}
		Person person = members.get(0).getMember();
		//校验账号是否存在
		List accounts = dao.query("select acc.id from Account acc left join acc.person p where p.id =? ", person.getId());
		if (accounts.size()==0) {
			if (person.getEmail()==null) {
				return 4;
			}else {
				return 0;
			}
		}else {
			return 1;
		}
	}
	
	public boolean checkPassportName(String passportName) {
		List passports = dao.query("select p from Passport p where p.name =?", passportName);
		return passports.size()>0 ? false : true;
	}
	
	public ProjectMember getProjectMemberInfo(String projectNumber, String idcardNumber) {
		ProjectGranted projectGranted = (ProjectGranted) dao.queryUnique("select pg from ProjectGranted pg where pg.number= ?", projectNumber);
		Map map = new HashMap();
		map.put("applicationId", projectGranted.getApplicationId());
		map.put("idcardNumber", idcardNumber);
		map.put("groupNumber", projectGranted.getMemberGroupNumber());
		List<ProjectMember> members = dao.query("select pm from ProjectMember pm where pm.applicationId=:applicationId and pm.idcardNumber=:idcardNumber and pm.groupNumber=:groupNumber ", map);
		if (members.size()==0) {
			members = dao.query("select pm from ProjectMember pm where pm.applicationId=:applicationId and pm.member.idcardNumber=:idcardNumber and pm.groupNumber=:groupNumber ", map);
		}
		return members.size()==0 ? null : members.get(0);
	}

	
	public Passport activateTeacherAccount(ProjectMember projectMember, String passportName,
			String password) {
		Person person = projectMember.getMember();
		//获取Teacher
		List<Teacher> teachers = dao.query("select t from Teacher t left join t.person p where p.id = ?", person.getId());
		Teacher teacher = teachers.size()>0? teachers.get(0) : createTeacherByProjectMember(projectMember);
		Passport passport = createPassport(passportName, password);
		dao.addOrModify(passport);
		Account account = createAccountByProjectMember(projectMember,passport);
		dao.addOrModify(account);
		this.setAccountRole(account);
		//发送邮件
		String activateVerifyCode = MD5.getMD5(SignID.getRandomString(8));
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		String url = path + "mobile/activate/secondActivate.action?passportId=" + passport.getId() + "&accountId=" + account.getId()+ "&activateVerifyCode=" + activateVerifyCode;
		passport.setActivateVerified(0);
		passport.setActivateVerifyCode(activateVerifyCode);
		passport.setActivateVerifyCodeStartDate(new Date());
		dao.modify(passport);
		sendEmail(person, url);
		return passport;
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void sendEmail(Person person, String url){
		final String email = person.getEmail();
		final String subject = "[SMDB]中国高校社会科学管理数据库账号激活--"+person.getName();
		final String body = person.getName()+",您好!欢迎访问中国高校社会科学管理数据库！" + 
				  "<br /><br />" +
				  "请点击以下链接进行账号激活认证，该链接一周内之内有效：" +
				  "<br />" +
				  "<a href=\"" + url + "\">" + url + "</a>" +
				  "<br /><br />" +
				  "如果该链接无法点击，请复制前面的链接地址到浏览器直接访问。" +
				  "<br />" +
				  "如果您没有进行过账号激活认证，请忽略此邮件。";
		txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					Mail mail = new Mail();
					mail.setSendTo(email);
					mail.setSubject(subject);
					mail.setBody(body);
					mail.setAccountBelong("系统管理员");
					mail.setFrom("serv@csdc.info");
					mail.setReplyTo("serv@csdc.info");
					mail.setIsHtml(1);
					mail.setCreateDate(new Date());
					mail.setFinishDate(null);
					mail.setSendTimes(0);
					mail.setStatus(0);
					dao.add(mail).toString();
				} catch (Exception e) {
					status.setRollbackOnly();
				}
			}
		});
		sendUndoneMails.send();
	}
	
	public Passport createPassport(String passportName, String password){
		Passport passport = new Passport();
		passport.setMaxSession(5);
		passport.setLoginCount(0);
		passport.setName(passportName);
		passport.setPassword(MD5.getMD5(password));
		passport.setStartDate(new Date());
		long expireTime=(new Date().getTime()/1000)+60*60*24*365;
		passport.setExpireDate(new Date(expireTime*1000));
		passport.setPasswordWarning(0);
		passport.setStatus(0);
		return passport;
	}
	
	public Teacher createTeacherByProjectMember(ProjectMember projectMember){
		Person person = projectMember.getMember();
		Teacher teacher = new Teacher();
		teacher.setPerson(person);
		teacher.setAgencyName(projectMember.getAgencyName());
		teacher.setCreateType(1);
		teacher.setDepartment(projectMember.getDepartment());
		teacher.setDivisionName(projectMember.getDivisionName());
		teacher.setInstitute(projectMember.getInstitute());
		teacher.setPosition(projectMember.getPosition());
		teacher.setUniversity(projectMember.getUniversity());
		teacher.setWorkMonthPerYear(projectMember.getWorkMonthPerYear());
		dao.add(teacher);
		return teacher;
	}
	
	public Account createAccountByProjectMember(ProjectMember projectMember, Passport passport){
		Account account = new Account();
		account.setPerson(projectMember.getMember());
		account.setType(AccountType.TEACHER);
		account.setAgency(projectMember.getUniversity());
		account.setCreateType(1);
		account.setDepartment(projectMember.getDepartment());
		account.setInstitute(projectMember.getInstitute());
		account.setIsLeapfrog(0);
		account.setIsPrincipal(1);
		account.setLoginCount(0);
		account.setPassport(passport);
		account.setStartDate(new Date());
		long expireTime=(new Date().getTime()/1000)+60*60*24*365;
		account.setExpireDate(new Date(expireTime*1000));
		account.setStatus(0);
		return account;
	}
	
	public int validateSecondActivate(String passportId, String activateVerifyCode) {
		Passport passport = dao.query(Passport.class, passportId);
		if (passport==null) {
			return 1;
		}
		if (passport.getStatus()==1 && passport.getActivateVerified() ==1) {
			return 2;
		}
		if (passport.getActivateVerified() ==0 && passport.getActivateVerifyCodeStartDate()==null) {
			return 3;
		}
		long expireTime=(passport.getActivateVerifyCodeStartDate().getTime()/1000)+60*60*24*7;
		if (new Date().after(new Date(expireTime*1000))) {
			return 4;
		}
		if (!activateVerifyCode.equals(passport.getActivateVerifyCode())) {
			return 5;
		}
		return 0;
	}
	
	public void secondActivate(String passportId, String accountId){
		long expireTime=(new Date().getTime()/1000)+60*60*24*365;
		Passport passport = dao.query(Passport.class, passportId);
		passport.setStatus(1);
		passport.setActivateVerified(1);
		passport.setActivateVerifyCode(null);
		passport.setActivateVerifyCodeStartDate(null);
		passport.setStartDate(new Date());
		passport.setExpireDate(new Date(expireTime*1000));
		dao.modify(passport);
		Account account = dao.query(Account.class, accountId);
		account.setStatus(1);
		account.setStartDate(new Date());
		account.setExpireDate(new Date(expireTime*1000));
		dao.modify(account);
	}
	
}
