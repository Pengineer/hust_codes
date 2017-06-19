package csdc.bean;

import java.util.Date;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class Committee implements java.io.Serializable {

	private static final long serialVersionUID = -4410230373430672139L;
	private String id;// ID
	private Institute institute;// 所属研究机构
	private String session;// 届次
	private Date startDate;// 成立时间
	private Date endDate;// 届满时间
	private Set<CommitteeMember> committeeMember;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public Institute getInstitute() {
		return institute;
	}
	public void setInstitute(Institute institute) {
		this.institute = institute;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@JSON(serialize=false)
	public Set<CommitteeMember> getCommitteeMember() {
		return committeeMember;
	}
	public void setCommitteeMember(Set<CommitteeMember> committeeMember) {
		this.committeeMember = committeeMember;
	}

}