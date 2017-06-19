package org.csdc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 系统配置表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_CONFIG")
public class Config  implements java.io.Serializable {

	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@Column(name="C_NAME",length=200)  
    private String name;
	
	@Column(name="C_KEY",length=200)  
    private String key;
	
	@Column(name="C_VALUE",length=200)  
    private String value;
	
	@Column(name="C_TYPE",length=200)  
    private String type;
	
	@Column(name="C_DESCRIPTION",length=7)  
    private String description;


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

    public String getKey() {
        return this.key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return this.description;
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

}