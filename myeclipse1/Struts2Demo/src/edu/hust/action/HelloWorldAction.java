package edu.hust.action;


//��strust1�����е��Զ���action��Ҫ�̳�Action������strutsΪ��ʵ�ֽ���Ͳ�����ô����
public class HelloWorldAction {
	private String message;
	
	public String getMessage(){
		return message;
	}
	
	public String execute(){
		
		message="this is my first struts2 code";
		
		return "success";  //after busniess logic operation,it will turn to JSP view
	}
}
