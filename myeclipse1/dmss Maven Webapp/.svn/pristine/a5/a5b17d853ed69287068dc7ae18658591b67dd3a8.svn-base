package org.csdc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
// default package

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.csdc.bean.TreeNode;
import org.hibernate.annotations.GenericGenerator;

import sun.tools.tree.ThisExpression;



/**
 * 分类目录表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_CATEGORY")
public class Category  implements java.io.Serializable {

	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@Column(name="C_NAME",length=200)  
    private String name;
	
	@Column(name="C_DESCRIPTION",length=400)  
    private String description;
	
	@Column(name="C_WEIGHT",precision=3, scale=0)  
	private Integer weight;
	
	@ManyToOne(targetEntity=Account.class)
    @JoinColumn(name = "C_CREATOR_ID")  
	private Account creator;
	
	@ManyToOne(targetEntity=Category.class)
    @JoinColumn(name = "C_PARENT_ID")  
    private Category parent;
    
	@Column(name="C_DOC_COUNT",precision=10, scale=0)  
	private Integer docCount;
	
	@Column(name="C_LAST_MODIFIED_DATE",length=7)  
	private Date lastModifiedDate;
	
	@ManyToOne
    @JoinColumn(name = "C_TEMPLATE_ID")  
	private Template template;
	
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "parent")  
    @OrderBy("weight") 
    private Set<Category> children;
	
	@ManyToMany(targetEntity=Role.class, mappedBy="categories")
	private Set<Role> roles;
	
	@ManyToMany(targetEntity=Document.class, mappedBy="categories", cascade={CascadeType.REMOVE,CascadeType.MERGE,CascadeType.PERSIST})
	private Set<Document> documents;
	
	
	@OneToOne(mappedBy = "category")
    private Account account;
	
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

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Account getCreator() {
		return creator;
	}

	public void setCreator(Account creator) {
		this.creator = creator;
	}

	public Integer getDocCount() {
		return docCount;
	}

	public void setDocCount(Integer docCount) {
		this.docCount = docCount;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}
	
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	public TreeNode convertToTreeNode(){
		TreeNode node = new TreeNode(id,name);
		node.setChildren(new ArrayList<TreeNode>());
		node.setLeaf(false);
		return node;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	 
	
	public static void main(String[] args) {
		Set<Category> categories = new HashSet<Category>();
		Category c1 = new Category();
		c1.setId(new String("1"));
		categories.add(c1);
		Category c2 = new Category();
		c2.setId(new String("1"));
		System.out.println(categories.contains(c2));
		System.out.println(c1.equals(c2));
		System.out.println("1");
		String aString=new String("1");
		String bString=new String("1");
		System.out.println(aString==bString);
		System.out.println(c1.getId()==c2.getId());
	}
	
}