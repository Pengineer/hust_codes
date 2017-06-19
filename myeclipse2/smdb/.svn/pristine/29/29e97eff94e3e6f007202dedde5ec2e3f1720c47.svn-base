package csdc.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * 重大攻关项目申报
 * 申请及各级审核状态字段：0默认，1退回，2暂存，3提交
 * 各级审核结果字段：0未审核，1不同意，2同意
 * @author 肖雅
 */
public class KeyApplication extends ProjectApplication implements java.io.Serializable {

	private static final long serialVersionUID = -110488671090955899L;
	private KeyTopicSelection topicSelection;//选题信息

	private Set<KeyGranted> keyGranted;//项目中标信息
	private Set<KeyMember> keyMember;//项目成员信息
	private Set<KeyApplicationReview> keyApplicationReview;//招标评审
	
	/**
	 * 重大攻关项目招标构造器
	 * 鉴别器字段(projectType='key')
	 */
	public KeyApplication() {
		this.setType("key");
	}
	/**
	 * 获取项目对应招标类名
	 */
	@JSON(serialize = false)
	public String getApplicationClassName(){
		return "KeyApplication";
	}
	/**
	 * 获取项目对应招标评审类名
	 */
	@JSON(serialize = false)
	public String getApplicationReviewClassName(){
		return "KeyApplicationReview";
	}
	/**
	 * 获取项目对应中标类名
	 */
	@JSON(serialize = false)
	public String getGrantedClassName(){
		return "KeyGranted";
	}
	/**
	 * 获取项目对应成员类名
	 */
	@JSON(serialize = false)
	public String getMemberClassName(){
		return "KeyMember";
	}
	/**
	 * 添加一个中标项目
	 * @param granted
	 */
	public void addGranted(ProjectGranted granted) {
		if (this.getKeyGranted() == null) {
			this.setKeyGranted(new HashSet<KeyGranted>());
		}
		this.getKeyGranted().add((KeyGranted)granted);
		((KeyGranted)granted).setApplication(this);
	}

	/**
	 * 添加一个成员
	 * @param member
	 */
	public void addMember(ProjectMember member) {
		if (this.getKeyMember() == null) {
			this.setKeyMember(new HashSet<KeyMember>());
		}
		this.getKeyMember().add((KeyMember)member);
		((KeyMember)member).setApplication(this);
	}
	/**
	 * 获取项目中标对象集合
	 */
	@JSON(serialize = false)
	public KeyTopicSelection getTopicSelection() {
		return topicSelection;
	}
	public void setTopicSelection(KeyTopicSelection topicSelection) {
		this.topicSelection = topicSelection;
	}
	@JSON(serialize = false)
	public Set<KeyGranted> getKeyGranted() {
		return keyGranted;
	}
	public void setKeyGranted(Set<KeyGranted> keyGranted) {
		this.keyGranted = keyGranted;
	}
	@JSON(serialize = false)
	public Set<KeyMember> getKeyMember() {
		return keyMember;
	}
	public void setKeyMember(Set<KeyMember> keyMember) {
		this.keyMember = keyMember;
	}
	@JSON(serialize = false)
	public Set<KeyApplicationReview> getKeyApplicationReview() {
		return keyApplicationReview;
	}
	public void setKeyApplicationReview(
			Set<KeyApplicationReview> keyApplicationReview) {
		this.keyApplicationReview = keyApplicationReview;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectMember> getMember() {
		return getKeyMember();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectFee> getFee() {
		return getFee();
	}

}
