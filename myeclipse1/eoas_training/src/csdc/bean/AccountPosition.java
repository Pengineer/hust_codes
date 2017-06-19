package csdc.bean;

import java.util.Date;

import csdc.bean.Account;
import csdc.bean.Position;

public class AccountPosition {
	private String id;
	private Account account;
	private Position position;
	private Date collectDate;
	private int collectStatus;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Date getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}
	public int getCollectStatus() {
		return collectStatus;
	}
	public void setCollectStatus(int collectStatus) {
		this.collectStatus = collectStatus;
	}
}