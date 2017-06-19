package org.csdc.model;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.csdc.support.CustomDateSerializer;
import org.hibernate.annotations.GenericGenerator;
/**
 * 账号表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_ACCOUNT")
public class Account  implements java.io.Serializable {
   
	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@Column(name="C_NAME",length=160,nullable=false)  
    private String name;
	
	@Column(name="C_PASSWORD",length=160,nullable=false)  
    private String password;
    
	@Column(name="C_START_DATE",length=7)  
    private Date startDate;
	
	@Column(name="C_EXPIRE_DATE",length=7)  
    private Date expireDate;
	
	@Column(name="C_MAX_SESSION", precision=2, scale=0)  
    private Integer maxSession;
	
	@Column(name="C_LAST_LOGIN_DATE", length=7)  
    private Date lastLoginDate;
	
	@Column(name="C_LAST_LOGIN_IP", length=160)  
    private String lastLoginIp;
	
	@Column(name="C_ALLOWED_IP", length=160)  
    private String allowedIp;
	
	@Column(name="C_STATUS", precision=1, scale=0)  
    private int status;
	
	@Column(name="C_REFUSED_IP", length=160)  
    private String refusedIp;
	
	@Column(name="C_LOGIN_COUNT", precision=10, scale=0)  
    private Integer loginCount = 0;
	
	@Column(name="C_RETRIEVE_PASSWORD_START_DATE", length =7)  
    private Date retrivePasswordStartDate;
	
	@Column(name="C_PASSWORD_WARNING", precision=1, scale=0)  
    private Boolean passwordWarning;
	
	@Column(name="C_SECURITY_QUESTION", length=800)  
    private String securityQuestion;
	
	@Column(name="C_SECURITY_ANSWER", length=800)  
    private String securityAnswer;
	
	@Column(name="C_THEME", length =800)  
    private String theme;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="C_PERSON_ID")
    private Person person;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="C_CATEGORY_ID")
	private Category category;
	
	@ManyToMany(targetEntity=Role.class)  
    @JoinTable(name="T_ACCOUNT_ROLE",  
        joinColumns={@JoinColumn(name="C_ACCOUNT_ID")},   
        inverseJoinColumns={@JoinColumn(name="C_ROLE_ID")})  
	private Set<Role> roles;
	
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "account")
    private Set<Message> messages;
	
    public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    @JsonSerialize(using = CustomDateSerializer.class)  
    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpireDate() {
        return this.expireDate;
    }
    
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getMaxSession() {
        return this.maxSession;
    }
    
    public void setMaxSession(Integer maxSession) {
        this.maxSession = maxSession;
    }

    public Date getLastLoginDate() {
        return this.lastLoginDate;
    }
    
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginIp() {
        return this.lastLoginIp;
    }
    
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getAllowedIp() {
        return this.allowedIp;
    }
    
    public void setAllowedIp(String allowedIp) {
        this.allowedIp = allowedIp;
    }

    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }

    public String getRefusedIp() {
        return this.refusedIp;
    }
    
    public void setRefusedIp(String refusedIp) {
        this.refusedIp = refusedIp;
    }

    public Integer getLoginCount() {
        return this.loginCount;
    }
    
    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Date getRetrivePasswordStartDate() {
        return this.retrivePasswordStartDate;
    }
    
    public void setRetrivePasswordStartDate(Date retrivePasswordStartDate) {
        this.retrivePasswordStartDate = retrivePasswordStartDate;
    }

    public Boolean getPasswordWarning() {
        return this.passwordWarning;
    }
    
    public void setPasswordWarning(Boolean passwordWarning) {
        this.passwordWarning = passwordWarning;
    }



    public String getSecurityQuestion() {
        return this.securityQuestion;
    }
    
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return this.securityAnswer;
    }
    
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getTheme() {
        return this.theme;
    }
    
    public void setTheme(String theme) {
        this.theme = theme;
    }

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}



}