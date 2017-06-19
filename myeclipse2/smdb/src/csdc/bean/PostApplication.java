package csdc.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

import csdc.bean.validation.CheckSystemOptionStandard;

public class PostApplication extends ProjectApplication implements java.io.Serializable {

	private static final long serialVersionUID = -2448995348371662151L;
	private String referrer;//推荐人
//@CheckSystemOptionStandard("projectTopic")
	private SystemOption topic;//项目主题
	private Set<PostGranted> postGranted;//立项项目
	private Set<PostMember> postMember;//项目成员对应
	private Set<PostApplicationReview> postApplicationReview;//申报评审 
	
	/**
	 * 后期资助项目申请构造器
	 * 鉴别器字段(projectType='post')
	 */
	public PostApplication() {
		this.setType("post");
	}
	/**
	 * 获取项目对应申报类名
	 */
	@JSON(serialize = false)
	public String getApplicationClassName(){
		return "PostApplication";
	}
	/**
	 * 获取项目对应申报评审类名
	 */
	@JSON(serialize = false)
	public String getApplicationReviewClassName(){
		return "PostApplicationReview";
	}
	/**
	 * 获取项目对应立项类名
	 */
	@JSON(serialize = false)
	public String getGrantedClassName(){
		return "PostGranted";
	}
	/**
	 * 获取项目对应成员类名
	 */
	@JSON(serialize = false)
	public String getMemberClassName(){
		return "PostMember";
	}
	/**
	 * 添加一个立项
	 * @param granted
	 */
	public void addGranted(ProjectGranted granted) {
		if (this.getPostGranted() == null) {
			this.setPostGranted(new HashSet<PostGranted>());
		}
		this.getPostGranted().add((PostGranted)granted);
		((PostGranted)granted).setApplication(this);
	}

	/**
	 * 添加一个成员
	 * @param member
	 */
	public void addMember(ProjectMember member) {
		if (this.getMember() == null) {
			this.setPostMember(new HashSet<PostMember>());
		}
		this.getPostMember().add((PostMember)member);
		((PostMember)member).setApplication(this);
	}

	public String getReferrer() {
		return referrer;
	}
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	@JSON(serialize=false)
	public Set<PostGranted> getPostGranted() {
		return postGranted;
	}
	public void setPostGranted(Set<PostGranted> postGranted) {
		this.postGranted = postGranted;
	}
	@JSON(serialize=false)
	public Set<PostMember> getPostMember() {
		return postMember;
	}
	public void setPostMember(Set<PostMember> postMember) {
		this.postMember = postMember;
	}
	@JSON(serialize=false)
	public Set<PostApplicationReview> getPostApplicationReview() {
		return postApplicationReview;
	}
	public void setPostApplicationReview(
			Set<PostApplicationReview> postApplicationReview) {
		this.postApplicationReview = postApplicationReview;
	}
	@JSON(serialize=false)
	public SystemOption getTopic() {
		return topic;
	}
	public void setTopic(SystemOption topic) {
		this.topic = topic;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectMember> getMember() {
		return getPostMember();
	}
	@JSON(serialize=false)
	@Override
	public Set<? extends ProjectFee> getFee() {
		return getFee();
	}
}
