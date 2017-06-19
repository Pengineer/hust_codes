package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 备忘录类
 * @author xn
 */
public class Memo {
	private String id; // 备注id
	private Account account; // 对应账号
	private String title; // 备忘标题
	private String content; // 备忘内容
	private Date updateTime; // 更新日期
	private Date remindTime; // 指定日期的提醒时间
	private Date startDateWeek, endDateWeek; // 开始时间(按周),结束时间(按周)
	private Date startDateDay, endDateDay; // 开始时间(按天),结束时间(按天)
//	private Date reverseRemindTime;//倒计时提醒时间
//	private String remindDay;//阴历提醒时间
	private String excludeDate;// 排除时间
	private String week; //周几
	private String month;//指定月日
	private int isRemind; //是否提醒(1是;0否)
	private int remindWay; // 提醒方式
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Account getAccount() {
		return account;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setRemindTime(Date remindTime) {
		this.remindTime = remindTime;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getRemindTime() {
		return remindTime;
	}
	public void setIsRemind(int isRemind) {
		this.isRemind = isRemind;
	}
	public int getIsRemind() {
		return isRemind;
	}
	public void setRemindWay(int remindWay) {
		this.remindWay = remindWay;
	}
	public int getRemindWay() {
		return remindWay;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getWeek() {
		return week;
	}
	public void setExcludeDate(String excludeDate) {
		this.excludeDate = excludeDate;
	}
	public String getExcludeDate() {
		return excludeDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getStartDateWeek() {
		return startDateWeek;
	}
	public void setStartDateWeek(Date startDateWeek) {
		this.startDateWeek = startDateWeek;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getEndDateWeek() {
		return endDateWeek;
	}
	public void setEndDateWeek(Date endDateWeek) {
		this.endDateWeek = endDateWeek;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getStartDateDay() {
		return startDateDay;
	}
	public void setStartDateDay(Date startDateDay) {
		this.startDateDay = startDateDay;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getEndDateDay() {
		return endDateDay;
	}
	public void setEndDateDay(Date endDateDay) {
		this.endDateDay = endDateDay;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
//	public void setReverseRemindTime(Date reverseRemindTime) {
//		this.reverseRemindTime = reverseRemindTime;
//	}
//	@JSON(format="yyyy-MM-dd HH:mm:ss")
//	public Date getReverseRemindTime() {
//		return reverseRemindTime;
//	}
//	public void setRemindDay(String remindDay) {
//		this.remindDay = remindDay;
//	}
//	public String getRemindDay() {
//		return remindDay;
//	}
}
	
