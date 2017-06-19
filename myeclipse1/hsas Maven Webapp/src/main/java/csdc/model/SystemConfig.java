package csdc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_SYSTEM_CONFIG")
@JsonIgnoreProperties({"password"})
public class SystemConfig implements java.io.Serializable {
	private static final long serialVersionUID = -788814454787899496L;
	
	@Id	
	@Column(name="C_ID", unique = true, nullable = false, length=40)
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
	private String id; //系统配置id（PK）
	
	@Column(name="C_KEY",length=40, nullable = false)  
	private String key; //配置键（UK）
	
	@Column(name="C_VALUE")
	private String value; //配置值
	
	@Column(name="C_DESCRIPTION", length=800)  
	private String description; //配置描述
	
	@Column(name="C_GROUP", nullable = false)  
	private Integer group; //配置组[0：默认；1：系统；2：人员；3：机构；4：专家；5：投稿；6：奖励；7：其他]
	
	@Column(name="C_IS_READONLY", nullable = false)  
	private Integer isReadonly; //是否只读[1：是；0：否]

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getGroup() {
		return group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public Integer getIsReadonly() {
		return isReadonly;
	}

	public void setIsReadonly(Integer isReadonly) {
		this.isReadonly = isReadonly;
	}
}
