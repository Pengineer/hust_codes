package edu.hust.action.validatebyhand;

import java.util.regex.Pattern;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 手工编写代码实现对action中所有方法输入校验：（用的少）
 * 通过重写validate()方法实现， validate()方法会校验action中所有与execute方法签名相同的方法。当某个数据校验失败时，我们应该调用
 * addFieldError()方法往系统的fieldErrors添加校验失败信息（为了使用addFieldError()方法，action可以继承ActionSupport ），
 * 如果系统的fieldErrors包含失败信息，struts2会将请求转发到名为input的result。在input视图中可以通过<s:fielderror/>显示失败信息。
 * 
 * 手工编写代码实现对action中指定方法输入校验：（用的多）
 * 通过validateXxx()方法实现， validateXxx()只会校验action中方法名为Xxx的方法。其中Xxx的第一个字母要大写。当某个数据校验失败时，我们
 * 应该调用addFieldError()方法往系统的fieldErrors添加校验失败信息（为了使用addFieldError()方法，action可以继承ActionSupport，
 * 如果系统的fieldErrors包含失败信息，struts2会将请求转发到名为input的result。在input视图中可以通过<s:fielderror/>显示失败信息。
 *
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

	/*@Override  //能对所有方法进行的校验（只要方法本Action的方法就会先校验）
	public void validate() {
		if(username == null || "".equals(username.trim())){
			this.addFieldError("username", "用户名为空");
		}
		if(Pattern.compile("^1[358]\\d{9}").matcher(this.phonenumber.trim()).matches()){
			this.addFieldError("phonenumber", "手机格式不对");
		}
	}*/
	
	//对指定方法校验（只有当调用本Action的save()方法是才会校验）
	public void validateSave() {
		if(username == null || "".equals(username.trim())){
			this.addFieldError("username", "用户名为空");
		}
		if(!Pattern.compile("^1[358]\\d{9}").matcher(this.phonenumber.trim()).matches()){
			this.addFieldError("phonenumber", "手机格式不对");
		}
	}

}
