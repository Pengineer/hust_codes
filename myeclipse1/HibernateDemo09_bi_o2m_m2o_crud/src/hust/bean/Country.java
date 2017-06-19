package hust.bean;

import java.util.Set;

public class Country {

	private int id;
	private String name;
	
	private Set<Province> provinces;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Province> getProvinces() {
		return provinces;
	}
	public void setProvinces(Set<Province> provinces) {
		this.provinces = provinces;
	}
	
}
