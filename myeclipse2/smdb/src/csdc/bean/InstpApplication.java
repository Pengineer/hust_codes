package csdc.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class InstpApplication extends ProjectApplication implements java.io.Serializable {

	private static final long serialVersionUID = -2448995348371662151L;
	private Set<InstpGranted> instpGranted;//立项项目
	private Set<InstpMember> instpMember;//项目成员对应
	private Set<InstpApplicationReview> instpApplicationReview;//申报评审 
	
	/**
	 * 基地项目申报构造器
	 * 鉴别器字段(projectType='instp')
	 */
	public InstpApplication() {
		this.setType("instp");
	}
	/**
	 * 获取项目对应申报类名
	 */
	@JSON(serialize = false)
	public String getApplicationClassName(){
		return "InstpApplication";
	}
	/**
	 * 获取项目对应申报评审类名
	 */
	@JSON(serialize = false)
	public String getApplicationReviewClassName(){
		return "InstpApplicationReview";
	}
	/**
	 * 获取项目对应立项类名
	 */
	@JSON(serialize = false)
	public String getGrantedClassName(){
		return "InstpGranted";
	}
	/**
	 * 获取项目对应成员类名
	 */
	@JSON(serialize = false)
	public String getMemberClassName(){
		return "InstpMember";
	}
	/**
	 * 添加一个立项
	 * @param granted
	 */
	public void addGranted(ProjectGranted granted) {
		if (this.getInstpGranted() == null) {
			this.setInstpGranted(new HashSet<InstpGranted>());
		}
		this.getInstpGranted().add((InstpGranted)granted);
		((InstpGranted)granted).setApplication(this);
	}

	/**
	 * 添加一个成员
	 * @param member
	 */
	public void addMember(ProjectMember member) {
		if (this.getMember() == null) {
			this.setInstpMember(new HashSet<InstpMember>());
		}
		this.getInstpMember().add((InstpMember)member);
		((InstpMember)member).setApplication(this);
	}
	
	/**
	 * 获取项目立项对象集合
	 */
	@JSON(serialize=false)
	public Set<InstpGranted> getInstpGranted() {
		return instpGranted;
	}
	public void setInstpGranted(Set<InstpGranted> instpGranted) {
		this.instpGranted = instpGranted;
	}

	/**
	 * 获取项目成员对象集合
	 */
	@JSON(serialize=false)
	public Set<InstpMember> getInstpMember() {
		return instpMember;
	}
	public void setInstpMember(Set<InstpMember> instpMember) {
		this.instpMember = instpMember;
	}
	/**
	 * 获取申报评审对象集合
	 */
	@JSON(serialize=false)
	public Set<InstpApplicationReview> getInstpApplicationReview() {
		return instpApplicationReview;
	}
	public void setInstpApplicationReview(
			Set<InstpApplicationReview> instpApplicationReview) {
		this.instpApplicationReview = instpApplicationReview;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectMember> getMember() {
		return getInstpMember();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectFee> getFee() {
		return getFee();
	}
}
