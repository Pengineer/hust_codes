package hust.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_group")
public class Group {
	private int id;
	private String name;
	
	private Set<Person> person = new HashSet<Person>();  //防止getPerson()时发生空指针异常
	
	@Id
	@GenericGenerator(name="idGenerator", strategy="increment")
	@GeneratedValue(generator="idGenerator")
	@Column(name="c_id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="c_name", length=20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/*
	@OneToMany
	@JoinColumn(name="c_group_id")
	*/
	@OneToMany(mappedBy="group",cascade={CascadeType.ALL})  //此种双向关联需要使用mappedBy，表示两个表的关系由Person类中的group属性维护，否则会当成many-to-many处理（生成一张中间表）
	public Set<Person> getPerson() {
		return person;
	}
	public void setPerson(Set<Person> person) {
		this.person = person;
	}
	
}
