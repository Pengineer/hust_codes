package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class CommitteeMember implements java.io.Serializable {

	private static final long serialVersionUID = 1796159304554072601L;
	private String id;// ID
	private Committee committee;// 所属学术委员会
	private Person member;// 成员
	private String position;// 任职

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public Committee getCommittee() {
		return committee;
	}
	public void setCommittee(Committee committee) {
		this.committee = committee;
	}
	@JSON(serialize=false)
	public Person getMember() {
		return member;
	}
	public void setMember(Person member) {
		this.member = member;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}