package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 各类奖励评审更新表
 * 和ProjectApplicationReview的区别是，剔除匹配条目在此表中用isAdd=0表示，即添加一条，而非删除一条
 * @author fengcl
 */
public class AwardApplicationReviewUpdate {
	private Integer id;
	private AwardApplication award;
	private Expert reviewer;
	private Integer priority;
	private Integer isManual;
	private Integer year;
	private Date matchTime = new Date();
	private Integer isAdd;	//1:表示增加		0:表示剔除
	private String type;//奖励类型 moeSocial
	

	public Integer getIsManual() {
		return isManual;
	}
	public void setIsManual(Integer isManual) {
		this.isManual = isManual;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public AwardApplication getAward() {
		return award;
	}
	public void setAward(AwardApplication award) {
		this.award = award;
	}
	@JSON(serialize=false)
	public Expert getReviewer() {
		return reviewer;
	}
	public void setReviewer(Expert reviewer) {
		this.reviewer = reviewer;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Date getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}	
	public Integer getIsAdd() {
		return isAdd;
	}
	public void setIsAdd(Integer isAdd) {
		this.isAdd = isAdd;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public AwardApplicationReviewUpdate(){}

	/**
	 * 匹配更新结果快速构造器
	 * @param pr 原始匹配结果
	 * @param isAdd 0:表示删除    1:表示添加
	 * @param isManual 0:表示自动    1:表示手动
	 */
	public AwardApplicationReviewUpdate(AwardApplicationReview aar, Integer isAdd, Integer isManual){
		this.setAward(new AwardApplication(aar.getAward().getId()));
		this.setReviewer(new Expert(aar.getReviewer().getId()));
		this.setIsManual(isManual);
		this.setYear(aar.getYear());
		this.setIsAdd(isAdd);
		this.setType(aar.getType());
		
		//同一时刻的调整认为添加在剔除后面发生
		if (isAdd > 0) {
			matchTime = new Date(matchTime.getTime() + 1000);
		}
	}

}
