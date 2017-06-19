package csdc.bean;

import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class SystemOption implements java.io.Serializable {

	private static final long serialVersionUID = 5369949164525166652L;
	private String id;//ID
	private SystemOption systemOption;//父选项
	private String name;//名称
	private String description;//描述
	private String code;//代码:若存在标准，则记录标准代码；若无标准，记录父选项首字母拼音加序号，例如"项目类型"代码为xmlx00，项目类型中的 "教育部一般项目"代码为xmlx01。
	private int isAvailable;//是否可用
	private String standard;//代码标准
	private String abbr;//缩写

	private Set<SystemOption> systemOptions;
	private Set<Institute> institutesForResearchType;//研究机构研究类型
	private Set<Institute> institutesForType;//研究机构类型
//	private Set<AwardApplication> productType;//成果类别
	private Set<AwardApplication> subType;//奖励类别
	private Set<AwardApplication> awardGradeAdvice1;
	private Set<AwardGranted> awardGrade;//获奖等级
	private Set<AwardReview> awardGradeAdvice;
	private Set<Agency> province;//管理机构省份
	private Set<Agency> city;//管理机构市
	private Set<Paper> publicationScope;
	private Set<Paper> projectType;
	private Set<Paper> form;
	private Set<Paper> ptype;
	private Set<Paper> publicationLevel;
	private Set<Book> bookType;
	private Set<Electronic> eleProjectType;
	private Set<Electronic> eleForm;
	private Set<Electronic> eleType;
	private Set<GeneralApplication> subtype;//一般项目子类
	private Set<GeneralApplication> topic;//一般项目主题
	private Set<GeneralApplication> researchType;//一般项目研究类型
	private Set<GeneralGranted> grantedSubtype;//立项项目子类
	private Set<GeneralEndinspection> generalEndGrade;
	private Set<GeneralEndinspectionReview> generalEndReviewGrade;
	private Set<PostApplication> postSubtype;//后期资助项目子类
	private Set<PostApplication> postTopic;//后期资助项目主题
	private Set<PostApplication> postResearchType;//后期资助研究类型
	private Set<PostGranted> postGrantedSubtype;//立项项目子类
	private Set<PostEndinspection> postEndGrade;
	private Set<PostEndinspectionReview> postEndReviewGrade;
	private Set<News> newsType;//新闻类型
	private Set<Notice> noticeType;//通知类型
	private Set<Notice> messageType;//留言类型
	private Set<Academic> expertType; //专家类型
	private Set<ProjectData> resProjectType; 
	private Set<InstpApplication> researchTypeInstpApp; //基地项目研究类别
	private Set<InstpGranted> subtypeInstpGra; //基地项目子类
	private Set<InstpEndinspectionReview> instpEndReviewGrade; //基地项目等级(优秀、合格、不合格)
	private Set<Academic> computerLevel; //专家类型

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public SystemOption getSystemOption() {
		return systemOption;
	}
	public void setSystemOption(SystemOption systemOption) {
		this.systemOption = systemOption;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@JSON(serialize=false)
	public Set<SystemOption> getSystemOptions() {
		return systemOptions;
	}
	public void setSystemOptions(Set<SystemOption> systemOptions) {
		this.systemOptions = systemOptions;
	}
	@JSON(serialize=false)
	public Set<Institute> getInstitutesForResearchType() {
		return institutesForResearchType;
	}
	public void setInstitutesForResearchType(
			Set<Institute> institutesForResearchType) {
		this.institutesForResearchType = institutesForResearchType;
	}
	@JSON(serialize=false)
	public Set<Institute> getInstitutesForType() {
		return institutesForType;
	}
	public void setInstitutesForType(Set<Institute> institutesForType) {
		this.institutesForType = institutesForType;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public int getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
//	@JSON(serialize=false)
//	public Set<AwardApplication> getProductType() {
//		return productType;
//	}
//	public void setProductType(Set<AwardApplication> productType) {
//		this.productType = productType;
//	}
	@JSON(serialize=false)
	public Set<AwardApplication> getSubType() {
		return subType;
	}
	public void setSubType(Set<AwardApplication> subjectType) {
		this.subType = subType;
	}
	@JSON(serialize=false)
	public Set<Book> getBookType() {
		return bookType;
	}
	public void setBookType(Set<Book> bookType) {
		this.bookType = bookType;
	}
	@JSON(serialize=false)
	public Set<Electronic> getEleProjectType() {
		return eleProjectType;
	}
	public void setEleProjectType(Set<Electronic> eleProjectType) {
		this.eleProjectType = eleProjectType;
	}
	@JSON(serialize=false)
	public Set<Electronic> getEleForm() {
		return eleForm;
	}
	public void setEleForm(Set<Electronic> eleForm) {
		this.eleForm = eleForm;
	}
	@JSON(serialize=false)
	public Set<Electronic> getEleType() {
		return eleType;
	}
	public void setEleType(Set<Electronic> eleType) {
		this.eleType = eleType;
	}
	@JSON(serialize=false)
	public Set<AwardGranted> getAwardGrade() {
		return awardGrade;
	}
	public void setAwardGrade(Set<AwardGranted> awardGrade) {
		this.awardGrade = awardGrade;
	}
	@JSON(serialize=false)
	public Set<AwardReview> getAwardGradeAdvice() {
		return awardGradeAdvice;
	}
	public void setAwardGradeAdvice(Set<AwardReview> awardGradeAdvice) {
		this.awardGradeAdvice = awardGradeAdvice;
	}
	@JSON(serialize=false)
	public Set<AwardApplication> getAwardGradeAdvice1() {
		return awardGradeAdvice1;
	}
	public void setAwardGradeAdvice1(Set<AwardApplication> awardGradeAdvice1) {
		this.awardGradeAdvice1 = awardGradeAdvice1;
	}
	@JSON(serialize=false)
	public Set<Agency> getProvince() {
		return province;
	}
	public void setProvince(Set<Agency> province) {
		this.province = province;
	}
	@JSON(serialize=false)
	public Set<Agency> getCity() {
		return city;
	}
	public void setCity(Set<Agency> city) {
		this.city = city;
	}
	@JSON(serialize=false)
	public Set<Paper> getPublicationScope() {
		return publicationScope;
	}
	public void setPublicationScope(Set<Paper> publicationScope) {
		this.publicationScope = publicationScope;
	}
	@JSON(serialize=false)
	public Set<Paper> getProjectType() {
		return projectType;
	}
	public void setProjectType(Set<Paper> projectType) {
		this.projectType = projectType;
	}
	@JSON(serialize=false)
	public Set<Paper> getForm() {
		return form;
	}
	public void setForm(Set<Paper> form) {
		this.form = form;
	}
	@JSON(serialize=false)
	public Set<Paper> getPtype() {
		return ptype;
	}
	public void setPtype(Set<Paper> ptype) {
		this.ptype = ptype;
	}
	@JSON(serialize=false)
	public Set<Paper> getPublicationLevel() {
		return publicationLevel;
	}
	public void setPublicationLevel(Set<Paper> publicationLevel) {
		this.publicationLevel = publicationLevel;
	}
	@JSON(serialize=false)
	public Set<GeneralApplication> getSubtype() {
		return subtype;
	}
	public void setSubtype(Set<GeneralApplication> subtype) {
		this.subtype = subtype;
	}
	@JSON(serialize=false)
	public Set<GeneralApplication> getTopic() {
		return topic;
	}
	public void setTopic(Set<GeneralApplication> topic) {
		this.topic = topic;
	}
	@JSON(serialize=false)
	public Set<GeneralApplication> getResearchType() {
		return researchType;
	}
	public void setResearchType(Set<GeneralApplication> researchType) {
		this.researchType = researchType;
	}
	@JSON(serialize=false)
	public Set<GeneralEndinspection> getGeneralEndGrade() {
		return generalEndGrade;
	}
	public void setGeneralEndGrade(Set<GeneralEndinspection> generalEndGrade) {
		this.generalEndGrade = generalEndGrade;
	}
	@JSON(serialize=false)
	public Set<GeneralEndinspectionReview> getGeneralEndReviewGrade() {
		return generalEndReviewGrade;
	}
	public void setGeneralEndReviewGrade(
			Set<GeneralEndinspectionReview> generalEndReviewGrade) {
		this.generalEndReviewGrade = generalEndReviewGrade;
	}
	@JSON(serialize=false)
	public Set<GeneralGranted> getGrantedSubtype() {
		return grantedSubtype;
	}
	public void setGrantedSubtype(Set<GeneralGranted> grantedSubtype) {
		this.grantedSubtype = grantedSubtype;
	}
	@JSON(serialize=false)
	public Set<News> getNewsType() {
		return newsType;
	}
	public void setNewsType(Set<News> newsType) {
		this.newsType = newsType;
	}
	@JSON(serialize=false)
	public Set<Notice> getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(Set<Notice> noticeType) {
		this.noticeType = noticeType;
	}
	@JSON(serialize=false)
	public Set<Notice> getMessageType() {
		return messageType;
	}
	public void setMessageType(Set<Notice> messageType) {
		this.messageType = messageType;
	}
	@JSON(serialize=false)
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	@JSON(serialize=false)
	public Set<Academic> getExpertType() {
		return expertType;
	}
	public void setExpertType(Set<Academic> expertType) {
		this.expertType = expertType;
	}
	@JSON(serialize=false)
	public Set<ProjectData> getResProjectType() {
		return resProjectType;
	}
	public void setResProjectType(Set<ProjectData> resProjectType) {
		this.resProjectType = resProjectType;
	}
	@JSON(serialize=false)
	public Set<InstpApplication> getResearchTypeInstpApp() {
		return researchTypeInstpApp;
	}
	public void setResearchTypeInstpApp(Set<InstpApplication> researchTypeInstpApp) {
		this.researchTypeInstpApp = researchTypeInstpApp;
	}
	@JSON(serialize=false)
	public Set<InstpGranted> getSubtypeInstpGra() {
		return subtypeInstpGra;
	}
	public void setSubtypeInstpGra(Set<InstpGranted> subtypeInstpGra) {
		this.subtypeInstpGra = subtypeInstpGra;
	}
	@JSON(serialize=false)
	public Set<InstpEndinspectionReview> getInstpEndReviewGrade() {
		return instpEndReviewGrade;
	}
	public void setInstpEndReviewGrade(
			Set<InstpEndinspectionReview> instpEndReviewGrade) {
		this.instpEndReviewGrade = instpEndReviewGrade;
	}
	@JSON(serialize=false)
	public Set<PostApplication> getPostSubtype() {
		return postSubtype;
	}
	public void setPostSubtype(Set<PostApplication> postSubtype) {
		this.postSubtype = postSubtype;
	}
	@JSON(serialize=false)
	public Set<PostApplication> getPostResearchType() {
		return postResearchType;
	}
	public void setPostResearchType(Set<PostApplication> postResearchType) {
		this.postResearchType = postResearchType;
	}
	@JSON(serialize=false)
	public Set<PostGranted> getPostGrantedSubtype() {
		return postGrantedSubtype;
	}
	public void setPostGrantedSubtype(Set<PostGranted> postGrantedSubtype) {
		this.postGrantedSubtype = postGrantedSubtype;
	}
	@JSON(serialize=false)
	public Set<PostEndinspection> getPostEndGrade() {
		return postEndGrade;
	}
	public void setPostEndGrade(Set<PostEndinspection> postEndGrade) {
		this.postEndGrade = postEndGrade;
	}
	@JSON(serialize=false)
	public Set<PostEndinspectionReview> getPostEndReviewGrade() {
		return postEndReviewGrade;
	}
	public void setPostEndReviewGrade(
			Set<PostEndinspectionReview> postEndReviewGrade) {
		this.postEndReviewGrade = postEndReviewGrade;
	}
	@JSON(serialize=false)
	public Set<PostApplication> getPostTopic() {
		return postTopic;
	}
	public void setPostTopic(Set<PostApplication> postTopic) {
		this.postTopic = postTopic;
	}
	@JSON(serialize=false)
	public Set<Academic> getComputerLevel() {
		return computerLevel;
	}
	public void setComputerLevel(Set<Academic> computerLevel) {
		this.computerLevel = computerLevel;
	}
}