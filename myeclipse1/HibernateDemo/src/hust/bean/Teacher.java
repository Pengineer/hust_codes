package hust.bean;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity(name="t_teacher")                         //javax.persistence.Entity
public class Teacher {
	private int id;
	private String name;
	private String title;
	
	@Id
	@Column(name="c_id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="c_name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/*@Basic(fetch = FetchType.LAZY)   //生成的是longtext类型
	@Type(type="text")
	@Column(name="c_title")*/
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name="c_title", columnDefinition="TEXT", nullable=true) 
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
