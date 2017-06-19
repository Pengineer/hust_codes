package csdc.bean.common;

import java.util.HashSet;
import java.util.Set;

import csdc.bean.User;

public class Visitor {
	
	private User user;
	private Set<String> userRight = new HashSet<String>();

	public Set<String> getUserRight() {
		return userRight;
	}
	public void setUserRight(Set<String> userRight) {
		this.userRight = userRight;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}