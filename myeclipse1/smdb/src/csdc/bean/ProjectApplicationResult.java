package csdc.bean;

public class ProjectApplicationResult {
	
	private String id;//主键
	private String universityName;//高校名称
	private Agency university;//依托高校
	private Double endinspectionRatio;//项目按期结项率
	private Double awardScore;//最近一届获奖得分
	private Long baseApplicationNumber;//年度限项申请基数（往年平均申请数）
	private String universityType;//高校类型（部属）
	private String universityArea;//高校所在地区（西部）
	private String province;//高校所在省份
	private Integer applicationNumber;//项目申请数量
	private Integer grantedNumber1;//项目立项数量1
	private Double grantedRatio;//项目立项率
	private Long midinspectionNumber;//项目中检数量
	private Double midinspectionRatio;//项目中检率
	private Long endinspectionNumber;//按期结项数量
	private Long grantedRateWeight;//立项率奖惩数
	private Long overdueMidinspectionWeight;//逾期中检奖惩数
	private Long overdueEndinspectionWeight;//逾期结项奖惩率
	private Long awardScoreWeight;//奖励得分奖励数
	private Integer priApplicationTotal;//初算：限项申请数量
	private Long moeTiltTotal;//部属高校倾斜
	private Long westTiltTotal;//西部高校倾斜
	private String pubApplicationTotal;//发布：限项申请数量
	private Long forApplicationTotal;//预测：预期申请数量
	private Integer year;//核算年度
	private Long grantedNumber2;//项目立项数量2
	private Long grantedNumber3;//项目立项数量3
	private String projrctType;//项目类型
	
	public String getProjrctType() {
		return projrctType;
	}
	public void setProjrctType(String projrctType) {
		this.projrctType = projrctType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	public Agency getUniversity() {
		return university;
	}
	public void setUniversity(Agency university) {
		this.university = university;
	}
	public Double getEndinspectionRatio() {
		return endinspectionRatio;
	}
	public void setEndinspectionRatio(Double endinspectionRatio) {
		this.endinspectionRatio = endinspectionRatio;
	}
	public Double getAwardScore() {
		return awardScore;
	}
	public void setAwardScore(Double awardScore) {
		this.awardScore = awardScore;
	}
	public Long getBaseApplicationNumber() {
		return baseApplicationNumber;
	}
	public void setBaseApplicationNumber(Long baseApplicationNumber) {
		this.baseApplicationNumber = baseApplicationNumber;
	}
	public String getUniversityType() {
		return universityType;
	}
	public void setUniversityType(String universityType) {
		this.universityType = universityType;
	}
	public String getUniversityArea() {
		return universityArea;
	}
	public void setUniversityArea(String universityArea) {
		this.universityArea = universityArea;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public Integer getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(Integer applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	public Integer getGrantedNumber1() {
		return grantedNumber1;
	}
	public void setGrantedNumber1(Integer grantedNumber1) {
		this.grantedNumber1 = grantedNumber1;
	}
	public Double getGrantedRatio() {
		return grantedRatio;
	}
	public void setGrantedRatio(Double grantedRatio) {
		this.grantedRatio = grantedRatio;
	}
	public Long getMidinspectionNumber() {
		return midinspectionNumber;
	}
	public void setMidinspectionNumber(Long midinspectionNumber) {
		this.midinspectionNumber = midinspectionNumber;
	}
	public Double getMidinspectionRatio() {
		return midinspectionRatio;
	}
	public void setMidinspectionRatio(Double midinspectionRatio) {
		this.midinspectionRatio = midinspectionRatio;
	}
	public Long getGrantedRateWeight() {
		return grantedRateWeight;
	}
	public void setGrantedRateWeight(Long grantedRateWeight) {
		this.grantedRateWeight = grantedRateWeight;
	}
	public Long getOverdueMidinspectionWeight() {
		return overdueMidinspectionWeight;
	}
	public void setOverdueMidinspectionWeight(Long overdueMidinspectionWeight) {
		this.overdueMidinspectionWeight = overdueMidinspectionWeight;
	}
	public Long getOverdueEndinspectionWeight() {
		return overdueEndinspectionWeight;
	}
	public void setOverdueEndinspectionWeight(Long overdueEndinspectionWeight) {
		this.overdueEndinspectionWeight = overdueEndinspectionWeight;
	}
	public Long getAwardScoreWeight() {
		return awardScoreWeight;
	}
	public void setAwardScoreWeight(Long awardScoreWeight) {
		this.awardScoreWeight = awardScoreWeight;
	}
	public Integer getPriApplicationTotal() {
		return priApplicationTotal;
	}
	public void setPriApplicationTotal(Integer priApplicationTotal) {
		this.priApplicationTotal = priApplicationTotal;
	}
	public Long getMoeTiltTotal() {
		return moeTiltTotal;
	}
	public void setMoeTiltTotal(Long moeTiltTotal) {
		this.moeTiltTotal = moeTiltTotal;
	}
	public Long getWestTiltTotal() {
		return westTiltTotal;
	}
	public void setWestTiltTotal(Long westTiltTotal) {
		this.westTiltTotal = westTiltTotal;
	}
	public String getPubApplicationTotal() {
		return pubApplicationTotal;
	}
	public void setPubApplicationTotal(String pubApplicationTotal) {
		this.pubApplicationTotal = pubApplicationTotal;
	}
	public Long getForApplicationTotal() {
		return forApplicationTotal;
	}
	public void setForApplicationTotal(Long forApplicationTotal) {
		this.forApplicationTotal = forApplicationTotal;
	}
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	public Long getGrantedNumber2() {
		return grantedNumber2;
	}
	public void setGrantedNumber2(Long grantedNumber2) {
		this.grantedNumber2 = grantedNumber2;
	}
	public Long getGrantedNumber3() {
		return grantedNumber3;
	}
	public void setGrantedNumber3(Long grantedNumber3) {
		this.grantedNumber3 = grantedNumber3;
	}
	public Long getEndinspectionNumber() {
		return endinspectionNumber;
	}
	public void setEndinspectionNumber(Long endinspectionNumber) {
		this.endinspectionNumber = endinspectionNumber;
	}
}
