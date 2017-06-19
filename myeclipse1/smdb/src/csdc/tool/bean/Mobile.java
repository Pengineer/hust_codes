package csdc.tool.bean;

/**
 * 此类用于保存mobile请求的相关信息
 * @author fengcl
 */
public class Mobile {
	//===========================[person]============================//
	private String name;//高级检索人员姓名
	private String gender;// 高级检索人员性别
	private String startAge;//高级检索人员开始年龄
	private String endAge;//高级检索人员结束年龄
	private String unitName;//高级检索人员机构
	private String deptName;//高级检索人员部门/院系
	private String position;// 高级检索人员职务
	private String specialityTitle;//高级检索人员专业职称
	private String disciplineType;//高级检索人员学科门类
	private String researchField;//高级检索人员研究领域
	private String staffCardNumber;//高级检索人员工作证号
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getStartAge() {
		return startAge;
	}
	public void setStartAge(String startAge) {
		this.startAge = startAge;
	}
	public String getEndAge() {
		return endAge;
	}
	public void setEndAge(String endAge) {
		this.endAge = endAge;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getSpecialityTitle() {
		return specialityTitle;
	}
	public void setSpecialityTitle(String specialityTitle) {
		this.specialityTitle = specialityTitle;
	}
	public String getDisciplineType() {
		return disciplineType;
	}
	public void setDisciplineType(String disciplineType) {
		this.disciplineType = disciplineType;
	}
	public String getResearchField() {
		return researchField;
	}
	public void setResearchField(String researchField) {
		this.researchField = researchField;
	}
	public void setStaffCardNumber(String staffCardNumber) {
		this.staffCardNumber = staffCardNumber;
	}
	public String getStaffCardNumber() {
		return staffCardNumber;
	}
	
	//============================[unit]=============================//
	//部级，省级，校级
	private String guName; //高级检索单位名称/院系(研究机构)名称
	private String guCode; //高级检索单位代码/院系(研究机构)代码
	private String guDirector; //高级检索中单位负责人名称
	private String guProvince; //高级检索所在省份
	private String guSname; //高级检索管理部门名称/院系(研究机构)所属高校
	private String guSdirector; //高级检索中部门负责人名称

	public String getGuName() {
		return guName;
	}
	public void setGuName(String guName) {
		this.guName = guName;
	}
	public String getGuCode() {
		return guCode;
	}
	public void setGuCode(String guCode) {
		this.guCode = guCode;
	}
	public String getGuDirector() {
		return guDirector;
	}
	public void setGuDirector(String guDirector) {
		this.guDirector = guDirector;
	}
	public String getGuProvince() {
		return guProvince;
	}
	public void setGuProvince(String guProvince) {
		this.guProvince = guProvince;
	}
	public String getGuSname() {
		return guSname;
	}
	public void setGuSname(String guSname) {
		this.guSname = guSname;
	}
	public String getGuSdirector() {
		return guSdirector;
	}
	public void setGuSdirector(String guSdirector) {
		this.guSdirector = guSdirector;
	}
	
	//===========================[project]============================//
	
	//===========================[product]============================//
	
	//============================[award]=============================//
	
}
