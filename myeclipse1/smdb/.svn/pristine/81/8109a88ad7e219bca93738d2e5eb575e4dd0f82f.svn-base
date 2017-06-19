package csdc.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * 委托（应急）课题申请
 * 申请及各级审核状态字段：0默认，1退回，2暂存，3提交
 * 各级审核结果字段：0未审核，1不同意，2同意
 * @author 王燕
 */
public class EntrustApplication extends ProjectApplication implements java.io.Serializable {

	private static final long serialVersionUID = -2448995348371662151L;
	//@CheckSystemOptionStandard("projectTopic")
	private SystemOption topic;//项目主题  
	private Set<EntrustGranted> entrustGranted;//项目立项信息
	private Set<EntrustMember> entrustMember;//项目成员信息
	private Set<EntrustApplicationReview> entrustApplicationReview;//申请评审
	
	/**
	 * 委托（应急）课题申请构造器
	 * 鉴别器字段(projectType='entrust')
	 */
	public EntrustApplication() {
		this.setType("entrust");
	}
	/**
	 * 获取项目对应申请类名
	 */
	@JSON(serialize = false)
	public String getApplicationClassName(){
		return "EntrustApplication";
	}
	/**
	 * 获取项目对应申请评审类名
	 */
	@JSON(serialize = false)
	public String getApplicationReviewClassName(){
		return "EntrustApplicationReview";
	}
	/**
	 * 获取项目对应立项类名
	 */
	@JSON(serialize = false)
	public String getGrantedClassName(){
		return "EntrustGranted";
	}
	/**
	 * 获取项目对应成员类名
	 */
	@JSON(serialize = false)
	public String getMemberClassName(){
		return "EntrustMember";
	}
	/**
	 * 添加一个立项
	 * @param granted
	 */
	public void addGranted(ProjectGranted granted) {
		if (this.getEntrustGranted() == null) {
			this.setEntrustGranted(new HashSet<EntrustGranted>());
		}
		this.getEntrustGranted().add((EntrustGranted)granted);
		((EntrustGranted)granted).setApplication(this);
	}

	/**
	 * 添加一个成员
	 * @param member
	 */
	public void addMember(ProjectMember member) {
		if (this.getMember() == null) {
			this.setEntrustMember(new HashSet<EntrustMember>());
		}
		this.getEntrustMember().add((EntrustMember)member);
		((EntrustMember)member).setApplication(this);
	}
	@JSON(serialize=false)
	public SystemOption getTopic() {
		return topic;
	}
	public void setTopic(SystemOption topic) {
		this.topic = topic;
	}
	/**
	 * 获取项目立项对象集合
	 */
	@JSON(serialize=false)
	public Set<EntrustGranted> getEntrustGranted() {
		return entrustGranted;
	}
	public void setEntrustGranted(Set<EntrustGranted> entrustGranted) {
		this.entrustGranted = entrustGranted;
	}
	@JSON(serialize=false)
	public Set<EntrustMember> getEntrustMember() {
		return entrustMember;
	}
	public void setEntrustMember(Set<EntrustMember> entrustMember) {
		this.entrustMember = entrustMember;
	}
	@JSON(serialize=false)
	public Set<EntrustApplicationReview> getEntrustApplicationReview() {
		return entrustApplicationReview;
	}
	public void setEntrustApplicationReview(
			Set<EntrustApplicationReview> entrustApplicationReview) {
		this.entrustApplicationReview = entrustApplicationReview;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectMember> getMember() {
		return getEntrustMember();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectFee> getFee() {
		return getFee();
	}

}
