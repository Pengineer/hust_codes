package org.csdc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 标签表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_TAG" )
public class Tag  implements java.io.Serializable {

	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@ManyToOne(targetEntity=Document.class)
	@JoinColumn(name="C_Document_ID")  
    private Document document;
	
	@Column(name="C_NAME",length=40)
    private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}