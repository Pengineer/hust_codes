package csdc.bean;

import java.util.Set;

public class Sysoption {
	
	private String id;// 记录的ID
	private String name;// 下拉框名字
	private String description;// 下拉框 描述
	private Set<SysoptionValue> sysoptionvalue; // 外键对象
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<SysoptionValue> getSysoptionvalue() {
		return sysoptionvalue;
	}
	public void setSysoptionvalue(Set<SysoptionValue> sysoptionvalue) {
		this.sysoptionvalue = sysoptionvalue;
	}
}