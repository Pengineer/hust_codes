package csdc.action.oa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Contact;
import csdc.bean.Customer;
import csdc.bean.Account;
import csdc.service.IBaseService;

public class CustomerAction extends ActionSupport {
	private Contact contact;
	private Customer customer;
	private IBaseService baseService;
	private String customerId;
	Map jsonMap = new HashMap();
	
	public String toList() {
		return SUCCESS;
	}

	public String list() {
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		List<Object[]> cList = new ArrayList<Object[]>();
		customerList = (ArrayList<Customer>) baseService.list(Customer.class, null);
		String[] item;
		for (Customer c : customerList) {
			item = new String[6];
			Map map = new HashMap();
			map.put("customerId", c.getId());
			Contact contact = (Contact) baseService.list(Contact.class, map).get(0);
			item[0] = c.getAbbreviation();
			item[1] = contact.getName();
			item[2] = c.getRegion();
			item[3] = c.getIndustry();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			item[4] = sdf.format(c.getConsultationTime());
			item[5] = c.getId();
			cList.add(item);
		}
		jsonMap.put("aaData", cList);
		return SUCCESS;
	}

	public String toAdd() {
		return SUCCESS;
	}
	
	public String add() {
		baseService.add(customer);
		contact.setCustomer(customer);
		baseService.add(contact);
		return SUCCESS;
	}

	public String view() {
		customer = (Customer) baseService.load(Customer.class, customerId);
		Map map = new HashMap();
		map.put("customerId", customerId);
		contact = (Contact) baseService.list(Contact.class, map).get(0);
		return SUCCESS;
	}
	
	public String toModify() {
		customer = (Customer) baseService.load(Customer.class, customerId);
		Map map = new HashMap();
		map.put("customerId", customerId);
		contact = (Contact) baseService.list(Contact.class, map).get(0);
		return SUCCESS;
	}
	
	public String modify() {
		baseService.modify(customer);
		contact.setCustomer(customer);
		baseService.modify(contact);
		return SUCCESS;
	}
	
	public String delete() {
		Map map = new HashMap();
		map.put("customerId", customerId);
		ArrayList<Contact> contact = (ArrayList<Contact>) baseService.list(Contact.class, map);
		baseService.delete(Contact.class, contact.get(0).getId());
		baseService.delete(Customer.class, customerId);
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	
}