package edu.hust.action.validatebyxml;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author liangjian
 * ����XML���÷�ʽʵ�ֶ�action�����з�����������У�飺
 * ʹ�û���XML���÷�ʽʵ������У��ʱ��ActionҲ��Ҫ�̳�ActionSupport�������ṩУ���ļ���У���ļ���action�����ͬһ�����£��ļ���ȡ��
 * ��ʽΪ��ActionClassName-validation.xml������ActionClassNameΪaction�ļ�������-validationΪ�̶�д�������Action
 * ��Ϊcn.itcast.UserAction����ô���ļ���ȡ��ӦΪ��UserAction-validation.xml��
 * 
 * ����XML���÷�ʽ��ָ��action����ʵ������У�飺
 * ��У���ļ���ȡ��ΪActionClassName-validation.xmlʱ����� action�е����д�����ʵʩ������֤�������ֻ��Ҫ��action�е�ĳ��action
 * ����ʵʩУ�飬��ô��У���ļ���ȡ��ӦΪ:ActionClassName-ActionName-validation.xml������ActionNameΪstruts.xml��action�����ơ�
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
		ActionContext.getContext().put("info", "����ɹ�");
		return "message";
	}
	
	public String update(){
		ActionContext.getContext().put("info", "���³ɹ�");
		return "message";
	}
}
