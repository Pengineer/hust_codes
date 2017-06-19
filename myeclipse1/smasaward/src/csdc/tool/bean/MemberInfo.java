package csdc.tool.bean;

public class MemberInfo {

	private Integer memberType; //成员类型:1教师;2专家;3学生
	private String memberName;//成员姓名
	private String agencyName;//单位名称
	private String divisionName;//部门名称
	private String idcardType;// 证件类型
	private String idcardNumber;// 证件号
	private int isDirector;//是否负责人:1是；0否
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getIdcardType() {
		return idcardType;
	}
	public void setIdcardType(String idcardType) {
		this.idcardType = idcardType;
	}
	public String getIdcardNumber() {
		return idcardNumber;
	}
	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}
	public int getIsDirector() {
		return isDirector;
	}
	public void setIsDirector(int isDirector) {
		this.isDirector = isDirector;
	}
	
	
}
