package edu.whut.json;

public class Province {
	private String pid;
	private String pname;
	public Province(String pid, String pname) {
		this.pid = pid;
		this.pname = pname;
	}
	public Province() {
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	
}
