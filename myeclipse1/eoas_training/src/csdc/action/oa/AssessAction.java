package csdc.action.oa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Account;
import csdc.bean.Assess;
import csdc.bean.Mail;
import csdc.bean.Person;
import csdc.service.IBaseService;

public class AssessAction extends ActionSupport {
	private IBaseService baseService;
	private Assess assess;
	private Person auditorPerson,accountPerson;//考核人，被考核人
	private String assessId;
	private String personId;
	private Map jsonMap = new HashMap();
	public String toList() {
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String list() {
		ArrayList<Assess> assessList = new ArrayList<Assess>();
		List<Object[]> aList = new ArrayList<Object[]>();
		try {
			assessList = (ArrayList<Assess>) baseService.list(Assess.class, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] item;
		for (Assess a : assessList) {
			item = new String[9];
			Person person = null;
			try {
				person = (Person) baseService.load(Person.class, a.getAccount().getBelongId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			item[0] = person.getRealName();
			item[1] = person.getStaffnum();
			if(null != person.getDepartment()) {
				item[2] = person.getDepartment().getName();
			} else {
				item[2] = "该用户尚未完善部门信息";
			}
			item[3] = a.getStatus() + "";
			if(null != a.getAuditor()) {
				Person auditPerson = (Person) baseService.load(Person.class, a.getAuditor().getBelongId());
				item[4] = auditPerson.getRealName();
			} else {
				item[4] = "审核人信息尚未完善";
			}
			SimpleDateFormat sdf = new SimpleDateFormat();
			item[5] = sdf.format(new Date());
			item[6] = a.getType() + "";
			item[7] = a.getId();
			Map map = new HashMap();
			map.put("belongId", person.getId());
			try {
				System.out.println(((Account)baseService.list(Account.class, map).get(0)).getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			item[8] = ((Account)baseService.list(Account.class, map).get(0)).getId();
			aList.add(item);
		}
		jsonMap.put("aaData", aList);
		return SUCCESS;
	}
	
	public String toAdd() {
		return SUCCESS;
	}
	public String add() {
		Person person  = (Person) baseService.load(Person.class, personId);
		Map map = new HashMap();
		map.put("belongId", person.getId());
		Account account = new Account();
		try {
			account = (Account) baseService.list(Account.class, map).get(0);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		assess.setAccount(account);
		
		Map session = ActionContext.getContext().getSession();
		Account auditor = (Account) session.get("account");
		assess.setAuditor(auditor);
		assess.setCreateTime(new Date());
		try {
			baseService.add(assess);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String delete() {
		
		return SUCCESS;
	}
	
	public String modify() {
		baseService.modify(assess);
		return SUCCESS;
	}
	
	public String view() {
		assess = (Assess) baseService.load(Assess.class, assessId);
		Account auditor = (Account) baseService.load(Account.class, assess.getAccount().getId());
		Person auditorPerson = (Person) baseService.load(Person.class, auditor.getBelongId());
		
		Account account = (Account) baseService.load(Account.class, assess.getAccount().getId());
		Person accountPerson = (Person) baseService.load(Person.class, account.getBelongId());
		return SUCCESS;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public Assess getAssess() {
		return assess;
	}

	public void setAssess(Assess assess) {
		this.assess = assess;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getAssessId() {
		return assessId;
	}

	public void setAssessId(String assessId) {
		this.assessId = assessId;
	}

	public Person getAuditorPerson() {
		return auditorPerson;
	}

	public void setAuditorPerson(Person auditorPerson) {
		this.auditorPerson = auditorPerson;
	}

	public Person getAccountPerson() {
		return accountPerson;
	}

	public void setAccountPerson(Person accountPerson) {
		this.accountPerson = accountPerson;
	}
}