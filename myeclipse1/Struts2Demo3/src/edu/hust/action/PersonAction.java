package edu.hust.action;

import org.apache.struts2.components.Token;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PersonAction extends ActionSupport{
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String execute(){
		ActionContext.getContext().put("attri", Long.toString(System.currentTimeMillis()));
		try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
		return "success";
	}

}
