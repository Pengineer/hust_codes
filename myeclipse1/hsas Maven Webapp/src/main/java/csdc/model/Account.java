package csdc.model;


import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_ACCOUNT")
@JsonIgnoreProperties({"password"})
public class Account implements java.io.Serializable {
	private static final long serialVersionUID = -4428555607314534636L;

	@Id	
	@Column(name="C_ID", unique = true, nullable = false, length=40)
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
	private String id; //账号id（PK）
	
	@Column(name="C_USERNAME", length=200, nullable = false)  
	private String username; //用户名
	
	@Column(name="C_PASSWORD", length=40, nullable = false)  
	private String password; //密码[密文]
	
	@Column(name="C_PW_RETRIEVE_CODE", length=40)  
	private String pwRetrieveCode; //密码找回码
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_START_DATE", nullable = false)
	private Date startDate; //开始（注册或创建）时间
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_APPROVE_TIME")
	private Date approveTime; //开始（注册或创建）时间
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_EXPIRE_DATE", nullable = false)
	private Date expireDate; //有效期限
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_LAST_LOGIN_DATE")
	private Date lastLoginDate; //上次登录时间
	
	@Column(name="C_STATUS", nullable = false)
	private Integer status; //账号状态[0：停用；1：启用]
	
	@Column(name="C_IS_SUPER_USER", nullable = false)
	private Integer isSuperUser; //是否超级管理员[1：是，0：否]
	
	@Column(name="C_CREATE_TYPE")
	private Integer createType; //创建类型[0：管理员分配；1：用户注册]
	
	@Column(name="C_TYPE", length=40, nullable = false)  
	private String type; //账号类型。[1：Super_Administrator：高级管理员；2：General_Administrator：一般管理员；
						 //3：Agency_Administrator：机构管理员；4：Review_Expert：评审专家；5：Award_Applicant：奖励申请人；
						 //6：UNDEFINED：未定义]
	
	@OneToOne
	@JoinColumn(name="C_PERSON_ID")
	private Person person; //账号所属人员ID（FK）
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="T_ACCOUNT_ROLE",  
		joinColumns={@JoinColumn(name="C_ACCOUNT_ID")},   
		inverseJoinColumns={@JoinColumn(name="C_ROLE_ID")}) 
	private Set<Role> roles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPwRetrieveCode() {
		return pwRetrieveCode;
	}

	public void setPwRetrieveCode(String pwRetrieveCode) {
		this.pwRetrieveCode = pwRetrieveCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCreateType() {
		return createType;
	}

	public void setCreateType(Integer createType) {
		this.createType = createType;
	}

	public Integer getIsSuperUser() {
		return isSuperUser;
	}

	public void setIsSuperUser(Integer isSuperUser) {
		this.isSuperUser = isSuperUser;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	  @JSON(serialize=false)
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
