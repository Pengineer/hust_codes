package edu.hust.action.validatebyhand;

import java.util.regex.Pattern;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * �ֹ���д����ʵ�ֶ�action�����з�������У�飺���õ��٣�
 * ͨ����дvalidate()����ʵ�֣� validate()������У��action��������execute����ǩ����ͬ�ķ�������ĳ������У��ʧ��ʱ������Ӧ�õ���
 * addFieldError()������ϵͳ��fieldErrors���У��ʧ����Ϣ��Ϊ��ʹ��addFieldError()������action���Լ̳�ActionSupport ����
 * ���ϵͳ��fieldErrors����ʧ����Ϣ��struts2�Ὣ����ת������Ϊinput��result����input��ͼ�п���ͨ��<s:fielderror/>��ʾʧ����Ϣ��
 * 
 * �ֹ���д����ʵ�ֶ�action��ָ����������У�飺���õĶࣩ
 * ͨ��validateXxx()����ʵ�֣� validateXxx()ֻ��У��action�з�����ΪXxx�ķ���������Xxx�ĵ�һ����ĸҪ��д����ĳ������У��ʧ��ʱ������
 * Ӧ�õ���addFieldError()������ϵͳ��fieldErrors���У��ʧ����Ϣ��Ϊ��ʹ��addFieldError()������action���Լ̳�ActionSupport��
 * ���ϵͳ��fieldErrors����ʧ����Ϣ��struts2�Ὣ����ת������Ϊinput��result����input��ͼ�п���ͨ��<s:fielderror/>��ʾʧ����Ϣ��
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
		ActionContext.getContext().put("info", "����ɹ�");
		return "message";
	}
	
	public String update(){
		ActionContext.getContext().put("info", "���³ɹ�");
		return "message";
	}

	/*@Override  //�ܶ����з������е�У�飨ֻҪ������Action�ķ����ͻ���У�飩
	public void validate() {
		if(username == null || "".equals(username.trim())){
			this.addFieldError("username", "�û���Ϊ��");
		}
		if(Pattern.compile("^1[358]\\d{9}").matcher(this.phonenumber.trim()).matches()){
			this.addFieldError("phonenumber", "�ֻ���ʽ����");
		}
	}*/
	
	//��ָ������У�飨ֻ�е����ñ�Action��save()�����ǲŻ�У�飩
	public void validateSave() {
		if(username == null || "".equals(username.trim())){
			this.addFieldError("username", "�û���Ϊ��");
		}
		if(!Pattern.compile("^1[358]\\d{9}").matcher(this.phonenumber.trim()).matches()){
			this.addFieldError("phonenumber", "�ֻ���ʽ����");
		}
	}

}
