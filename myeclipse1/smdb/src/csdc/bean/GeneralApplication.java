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
public class GeneralApplication extends ProjectApplication implements java.io.Serializable {

	private static final long serialVersionUID = -2448995348371662151L;
	//@CheckSystemOptionStandard("projectTopic")
	private Set<GeneralGranted> generalGranted;//项目立项信息
	private Set<GeneralMember> generalMember;//项目成员信息
	private Set<GeneralApplicationReview> generalApplicationReview;//申请评审
	
	/**
	 * 一般项目申请构造器
	 * 鉴别器字段(projectType='general')
	 */
	public GeneralApplication() {
		this.setType("general");
	}
	/**
	 * 获取项目对应申请类名
	 */
	@JSON(serialize = false)
	public String getApplicationClassName(){
		return "GeneralApplication";
	}
	/**
	 * 获取项目对应申请评审类名
	 */
	@JSON(serialize = false)
	public String getApplicationReviewClassName(){
		return "GeneralApplicationReview";
	}
	/**
	 * 获取项目对应立项类名
	 */
	@JSON(serialize = false)
	public String getGrantedClassName(){
		return "GeneralGranted";
	}
	/**
	 * 获取项目对应成员类名
	 */
	@JSON(serialize = false)
	public String getMemberClassName(){
		return "GeneralMember";
	}
	/**
	 * 添加一个立项
	 * @param granted
	 */
	public void addGranted(ProjectGranted granted) {
		if (this.getGeneralGranted() == null) {
			this.setGeneralGranted(new HashSet<GeneralGranted>());
		}
		this.getGeneralGranted().add((GeneralGranted)granted);
		((GeneralGranted)granted).setApplication(this);
	}

	/**
	 * 添加一个成员
	 * @param member
	 */
	public void addMember(ProjectMember member) {
		if (this.getMember() == null) {
			this.setGeneralMember(new HashSet<GeneralMember>());
		}
		this.getGeneralMember().add((GeneralMember)member);
		((GeneralMember)member).setApplication(this);
	}
	/**
	 * 获取项目立项对象集合
	 */
	@JSON(serialize=false)
	public Set<GeneralGranted> getGeneralGranted() {
		return generalGranted;
	}
	public void setGeneralGranted(Set<GeneralGranted> generalGranted) {
		this.generalGranted = generalGranted;
	}
	@JSON(serialize=false)
	public Set<GeneralMember> getGeneralMember() {
		return generalMember;
	}
	public void setGeneralMember(Set<GeneralMember> generalMember) {
		this.generalMember = generalMember;
	}
	@JSON(serialize=false)
	public Set<GeneralApplicationReview> getGeneralApplicationReview() {
		return generalApplicationReview;
	}
	public void setGeneralApplicationReview(
			Set<GeneralApplicationReview> generalApplicationReview) {
		this.generalApplicationReview = generalApplicationReview;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectMember> getMember() {
		return getGeneralMember();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectFee> getFee() {
		return getFee();
	}

}
