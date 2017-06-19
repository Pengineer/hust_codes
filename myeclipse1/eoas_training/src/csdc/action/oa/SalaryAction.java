package csdc.action.oa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Account;
import csdc.bean.Person;
import csdc.bean.Role;
import csdc.bean.Salary;
import csdc.service.IBaseService;

public class SalaryAction extends ActionSupport {
	private IBaseService baseService;
	private Salary salary;
	private String personId;


	private Map jsonMap = new HashMap();
	public String  toAdd() {
		return SUCCESS;
	}
	
	public String add() {
		Map session = ActionContext.getContext().getSession();
		Account account = (Account) session.get("account");
		salary.setAccount(account);//审核人就是有权限创建工资清单的人
		
		Person person = (Person) baseService.load(Person.class, personId);
		Map map = new HashMap();
		map.put("belongId", person.getId());
		Account auditor = (Account) baseService.list(Account.class, map).get(0);
		salary.setAuditor(auditor);//被审核人可选择
		try {
			baseService.add(salary);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String toList() {
		return SUCCESS;
	}
	
	public String list() {
		ArrayList<Salary> salaryList = new ArrayList<Salary>();
		List<Object[]> sList = new ArrayList<Object[]>();
		Map session = ActionContext.getContext().getSession();
		try {
			salaryList = (ArrayList<Salary>) baseService.list(Salary.class, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] item;
		for (Salary salary : salaryList) {
			item = new String[10];
			Account account1 = (Account) baseService.load(Account.class, salary.getAccount().getId());
			item[0] = ((Person) baseService.load(Person.class, account1.getBelongId())).getRealName();
			item[1] = salary.getFixedSalary();
			item[2] = salary.getBonus();
			item[3] = salary.getTaxes();	
			item[4] = salary.getSubsidies();
			item[5] = salary.getReleaseTime() + "";
			item[6] = salary.getTime() + "";
			item[7] = salary.getAuditTime() + "";
			item[8] = salary.getStatus() + "";
			Account account2 = (Account) baseService.load(Account.class, salary.getAccount().getId());
			item[9] = ((Person) baseService.load(Account.class, salary.getAuditor().getId())).getRealName();
			item[10] = salary.getBonus();item[2] = salary.getBonus();
			sList.add(item);
		}
		jsonMap.put("aaData", sList);
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

	public Salary getSalary() {
		return salary;
	}

	public void setSalary(Salary salary) {
		this.salary = salary;
	}
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
}