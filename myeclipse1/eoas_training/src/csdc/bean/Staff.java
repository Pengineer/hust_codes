package csdc.bean;

import java.sql.Date;

/**
 * Title: Staff
 * Description:职员
 * Company: HUST-NADR
 * @author SHAO xiao
 * @date 2014-4-24
 */
public class Staff {

	private String id;// 职员id（PK）
	private String personId;// 人员id（FK）
	private Date intime;// 入职时间
	private String bankNum;// 工资卡号
	private String staffNum;// 员工编号
	private Person person;

	// ---get & set---
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPersonid() {
		return personId;
	}

	public void setPersonid(String personid) {
		this.personId = personid;
	}

	public Date getIntime() {
		return intime;
	}

	public void setIntime(Date intime) {
		this.intime = intime;
	}

	public String getBanknum() {
		return bankNum;
	}

	public void setBanknum(String banknum) {
		this.bankNum = banknum;
	}

	public String getStaffnum() {
		return staffNum;
	}

	public void setStaffnum(String staffnum) {
		this.staffNum = staffnum;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
