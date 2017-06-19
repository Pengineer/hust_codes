package csdc.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;
/**
 * 
 * @author maowh
 *
 */
public class DevrptApplication extends ProjectApplication implements java.io.Serializable {

	private static final long serialVersionUID = -2448995348371662151L;
	private Set<DevrptGranted> devrptGranted;//项目立项信息
	private Set<DevrptMember> devrptMember;//项目成员信息
	private Set<DevrptApplicationReview> devrptApplicationReview;//申请评审
	
	/**
	 * 发展报告申请构造器
	 * 鉴别器字段(projectType='devrpt')
	 */
	public DevrptApplication() {
		this.setType("devrpt");
	}
	/**
	 * 获取项目对应申请类名
	 */
	@JSON(serialize = false)
	public String getApplicationClassName(){
		return "DevrptApplication";
	}
	/**
	 * 获取项目对应申请评审类名
	 */
	@JSON(serialize = false)
	public String getApplicationReviewClassName(){
		return "DevrptApplicationReview";
	}
	/**
	 * 获取项目对应立项类名
	 */
	@JSON(serialize = false)
	public String getGrantedClassName(){
		return "DevrptGranted";
	}
	/**
	 * 获取项目对应成员类名
	 */
	@JSON(serialize = false)
	public String getMemberClassName(){
		return "DevrptMember";
	}
	/**
	 * 添加一个立项
	 * @param granted
	 */
	public void addGranted(ProjectGranted granted) {
		if (this.getDevrptGranted() == null) {
			this.setDevrptGranted(new HashSet<DevrptGranted>());
		}
		this.getDevrptGranted().add((DevrptGranted)granted);
		((DevrptGranted)granted).setApplication(this);
	}

	/**
	 * 添加一个成员
	 * @param member
	 */
	public void addMember(ProjectMember member) {
		if (this.getMember() == null) {
			this.setDevrptMember(new HashSet<DevrptMember>());
		}
		this.getDevrptMember().add((DevrptMember)member);
		((DevrptMember)member).setApplication(this);
	}
	/**
	 * 获取项目立项对象集合
	 */
	@JSON(serialize=false)
	public Set<DevrptGranted> getDevrptGranted() {
		return devrptGranted;
	}
	public void setDevrptGranted(Set<DevrptGranted> devrptGranted) {
		this.devrptGranted = devrptGranted;
	}
	@JSON(serialize=false)
	public Set<DevrptMember> getDevrptMember() {
		return devrptMember;
	}
	public void setDevrptMember(Set<DevrptMember> devrptMember) {
		this.devrptMember = devrptMember;
	}
	@JSON(serialize=false)
	public Set<DevrptApplicationReview> getDevrptApplicationReview() {
		return devrptApplicationReview;
	}
	public void setDevrptApplicationReview(
			Set<DevrptApplicationReview> devrptApplicationReview) {
		this.devrptApplicationReview = devrptApplicationReview;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectMember> getMember() {
		return getDevrptMember();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectFee> getFee() {
		return getFee();
	}
}
