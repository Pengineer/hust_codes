package edu.hust.action.validatebyxml;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author liangjian
 * 基于XML配置方式实现对action的所有方法进行输入校验：
 * 使用基于XML配置方式实现输入校验时，Action也需要继承ActionSupport，并且提供校验文件，校验文件和action类放在同一个包下，文件的取名
 * 格式为：ActionClassName-validation.xml，其中ActionClassName为action的简单类名，-validation为固定写法。如果Action
 * 类为cn.itcast.UserAction，那么该文件的取名应为：UserAction-validation.xml。
 * 
 * 基于XML配置方式对指定action方法实现输入校验：
 * 当校验文件的取名为ActionClassName-validation.xml时，会对 action中的所有处理方法实施输入验证。如果你只需要对action中的某个action
 * 方法实施校验，那么，校验文件的取名应为:ActionClassName-ActionName-validation.xml，其中ActionName为struts.xml中action的名称。
 */
public class PersonAction extends ActionSupport{
	private String username;
	private String phonenumber;
		
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	public String save(){
		ActionContext.getContext().put("info", "保存成功");
		return "message";
	}
	
	public String update(){
		ActionContext.getContext().put("info", "更新成功");
		return "message";
	}
}
