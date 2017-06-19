package csdc.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * 一般项目申请
 * 申请及各级审核状态字段：0默认，1退回，2暂存，3提交
 * 各级审核结果字段：0未审核，1不同意，2同意
 * @author 刘雅琴
 */
public class SpecialApplication extends ProjectApplication implements java.io.Serializable {

	private static final long serialVersionUID = -2448995348371662151L;
	//@CheckSystemOptionStandard("projectTopic")
	private Set<SpecialGranted> specialGranted;//项目立项信息
	private Set<SpecialMember> specialMember;//项目成员信息
	private Set<SpecialApplicationReview> specialApplicationReview;//申请评审
	
	/**
	 * 一般项目申请构造器
	 * 鉴别器字段(projectType='special')
	 */
	public SpecialApplication() {
		this.setType("special");
	}
	/**
	 * 获取项目对应申请类名
	 */
	@JSON(serialize = false)
	public String getApplicationClassName(){
		return "SpecialApplication";
	}
	/**
	 * 获取项目对应申请评审类名
	 */
	@JSON(serialize = false)
	public String getApplicationReviewClassName(){
		return "SpecialApplicationReview";
	}
	/**
	 * 获取项目对应立项类名
	 */
	@JSON(serialize = false)
	public String getGrantedClassName(){
		return "SpecialGranted";
	}
	/**
	 * 获取项目对应成员类名
	 */
	@JSON(serialize = false)
	public String getMemberClassName(){
		return "SpecialMember";
	}
	/**
	 * 添加一个立项
	 * @param granted
	 */
	public void addGranted(ProjectGranted granted) {
		if (this.getSpecialGranted() == null) {
			this.setSpecialGranted(new HashSet<SpecialGranted>());
		}
		this.getSpecialGranted().add((SpecialGranted)granted);
		((SpecialGranted)granted).setApplication(this);
	}

	/**
	 * 添加一个成员
	 * @param member
	 */
	public void addMember(ProjectMember member) {
		if (this.getMember() == null) {
			this.setSpecialMember(new HashSet<SpecialMember>());
		}
		this.getSpecialMember().add((SpecialMember)member);
		((SpecialMember)member).setApplication(this);
	}
	/**
	 * 获取项目立项对象集合
	 */
	@JSON(serialize=false)
	public Set<SpecialGranted> getSpecialGranted() {
		return specialGranted;
	}
	public void setSpecialGranted(Set<SpecialGranted> specialGranted) {
		this.specialGranted = specialGranted;
	}
	@JSON(serialize=false)
	public Set<SpecialMember> getSpecialMember() {
		return specialMember;
	}
	public void setSpecialMember(Set<SpecialMember> specialMember) {
		this.specialMember = specialMember;
	}
	@JSON(serialize=false)
	public Set<SpecialApplicationReview> getSpecialApplicationReview() {
		return specialApplicationReview;
	}
	public void setSpecialApplicationReview(
			Set<SpecialApplicationReview> specialApplicationReview) {
		this.specialApplicationReview = specialApplicationReview;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectMember> getMember() {
		return getSpecialMember();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectFee> getFee() {
		return getFee();
	}

}
