package csdc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_SOLICIT_PAPERS")
@JsonIgnoreProperties({"password"})
public class SolicitPapers implements Serializable {
	private static final long serialVersionUID = 6149718577136086623L;

	@Id	
	@Column(name="C_ID", unique = true, nullable = false, length=40)
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
	private String id; //论坛投稿表id（PK）
	
	@Column(name="C_NAME", length=400, nullable = false)
	private String name; //名称
	
	@Column(name="C_AUTHOR", length=100)
	private String author; //作者
	
	@Column(name="C_UNIT", length=400)
	private String unit; //所在单位
	
	@Column(name="C_POSITION", length=400)
	private String pesition; //职务
	
	@Column(name="C_PHONE", length=400)
	private String phone; //联系电话
	
	@Column(name="C_GENDER", length=40)
	private String gender; //性别
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_BIRTHDAY")
	private Date birthday; //出生日期
	
	@Column(name="C_ADDRESS", length=400)
	private String address; //联系地址
	
	@Column(name="C_POSTCODE", length=40)
	private String postcode; //邮政编码
	
	@Column(name="C_EMAIL", length=400)
	private String email; //电子邮箱
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_CREATE_DATE")
	private Date createDate; //创建时间
	
	@Column(name="C_DESCRIPTION", length=4000)  
	private String description; //说明
	
	@Column(name="C_TYPE", length=40)  
	private String type; //类型
	
	@Column(name="C_FILE", length=800)  
	private String file; //文档文件
	
	@Column(name="C_NOTE", length=800)  
	private String note; //备注
	
	@Column(name="C_FILE_NAME", length=800)  
	private String fileName; //附件名

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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPesition() {
		return pesition;
	}

	public void setPesition(String pesition) {
		this.pesition = pesition;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
