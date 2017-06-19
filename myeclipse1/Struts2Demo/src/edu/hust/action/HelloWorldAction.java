package edu.hust.action;


//在strust1中所有的自定义action都要继承Action，但是struts为了实现解耦，就不用这么做了
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
