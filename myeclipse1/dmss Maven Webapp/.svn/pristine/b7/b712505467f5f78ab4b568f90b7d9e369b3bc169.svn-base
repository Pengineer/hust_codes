package org.csdc.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 模板表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_TEMPLATE" )
public class Template  implements java.io.Serializable {

	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@Column(name="C_LAST_MODIFIED_DATE",length=7)
    private Date lastModifuedDate;
	
	@Column(name="C_NAME",length=200)
    private String name;
	
	@Column(name="C_DESCRIPTION",length=200)
    private String description;
	
	@Column(name="C_CATEGORY",length=200)
    private String category;
	
	@Column(name="C_DOC_COUNT",precision=10,scale=0)
    private Integer docCount;
	
	@OneToMany(mappedBy = "template")
	private Set<Document> documents;
	
	@OneToMany(cascade =CascadeType.ALL,mappedBy = "template")
	private Set<TemplateExt> templateExts;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getLastModifuedDate() {
		return lastModifuedDate;
	}

	public void setLastModifuedDate(Date lastModifuedDate) {
		this.lastModifuedDate = lastModifuedDate;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getDocCount() {
		return docCount;
	}

	public void setDocCount(Integer docCount) {
		this.docCount = docCount;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	public Set<TemplateExt> getTemplateExts() {
		return templateExts;
	}

	public void setTemplateExts(Set<TemplateExt> templateExts) {
		this.templateExts = templateExts;
	}
	
	
}