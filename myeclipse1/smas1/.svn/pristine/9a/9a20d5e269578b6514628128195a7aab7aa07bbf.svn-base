package csdc.bean;

import java.io.Serializable;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class SystemOption implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;// 记录的ID
	private String name;// 下拉框名字
	private String description;// 下拉框 描述
	private SystemOption parent;// 外键
	private String code;// 编码
	private String standard;// 标准
	private String abbr;// 缩写
	private int isAvailable;// 是否可用
	
	private Set<SystemOption> sysoption;
	private Set<SystemOption> userEthnic;
	private Set<SystemOption> userGender;

	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
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
	@JSON(serialize=false)
	public SystemOption getParent() {
		return parent;
	}
	public void setParent(SystemOption parent) {
		this.parent = parent;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}
	@JSON(serialize=false)
	public Set<SystemOption> getSysoption() {
		return sysoption;
	}
	public void setSysoption(Set<SystemOption> sysoption) {
		this.sysoption = sysoption;
	}
	@JSON(serialize=false)
	public Set<SystemOption> getUserEthnic() {
		return userEthnic;
	}
	public void setUserEthnic(Set<SystemOption> userEthnic) {
		this.userEthnic = userEthnic;
	}
	@JSON(serialize=false)
	public Set<SystemOption> getUserGender() {
		return userGender;
	}
	public void setUserGender(Set<SystemOption> userGender) {
		this.userGender = userGender;
	}
}