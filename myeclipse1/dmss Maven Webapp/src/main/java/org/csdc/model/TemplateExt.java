package org.csdc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 模板扩展属性表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_TEMPLATE_EXT" )
public class TemplateExt  implements java.io.Serializable {
 
	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@ManyToOne(targetEntity=Template.class)
	@JoinColumn(name="C_TEMPLATE_ID")
    private Template template;
    
	@Column(name="C_MANDATORY",precision=1,scale=0)
    private Boolean mandatory;
	
	@Column(name="C_POSITION",precision=1,scale=0)
    private Integer position;
	
	@Column(name="C_TYPE",length=20)
    private String type;
	
	@Column(name="C_NAME",length=200)
    private String name;
	
	@Column(name="C_LABEL",length=200)
    private String label;
	
	@Column(name="C_STRING_VALUE",length=200)
    private String stringValue;
	
	@Column(name="C_INTEGER_VALUE",precision=10,scale=0)
    private Integer integerValue;
	
	@Column(name="C_DOUBLE_VALUE",scale=5)
    private Double doubleValue;
    
    @Column(name="C_DATE_VALUE",length=7)
    private Date dateValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Integer getIntegerValue() {
		return integerValue;
	}

	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}

	public Double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public Date getDateValue() {
		return dateValue;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}
    
    
}