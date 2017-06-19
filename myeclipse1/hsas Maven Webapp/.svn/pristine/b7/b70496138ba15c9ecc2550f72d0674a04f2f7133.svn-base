package csdc.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_SYSTEM_OPTION")
@JsonIgnoreProperties({"password"})
public class SystemOption implements Serializable {
	private static final long serialVersionUID = -5700962619784651355L;

	private String id; //系统选项id（PK）
	private String name; //选项名称
	private String description; //选项描述
	private SystemOption parentId; //父选项id（FK）
	private String code; //选项代码
	private Integer isAvailable; //是否可用[1：是；0：否]
	private String standard; //标准
	private String abbr; //缩写
	
	private Set<SystemOption> systemOptions;

	@Id	
	@Column(name="C_ID", unique = true, nullable = false, length=40)
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="C_NAME", length=200)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="C_DESCRIPTION", length=200)  
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name="C_PARENT_ID")
	public SystemOption getParentId() {
		return parentId;
	}

	public void setParentId(SystemOption parentId) {
		this.parentId = parentId;
	}

	@Column(name="C_CODE", length=40)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name="C_IS_AVAILABLE", nullable = false)
	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Column(name="C_STANDARD", length=100)
	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	@Column(name="C_ABBR", length=100)
	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	
	@OneToMany(mappedBy="parentId")
	public Set<SystemOption> getSystemOptions() {
		return systemOptions;
	}

	public void setSystemOptions(Set<SystemOption> systemOptions) {
		this.systemOptions = systemOptions;
	}
}
