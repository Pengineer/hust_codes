package edu.hust.action.interceptor;

public class BusinessCode {
	private String methodInfo;
	
	
	public String getMethodInfo() {
		return methodInfo;
	}

	public void setMethodInfo(String methodInfo) {
		this.methodInfo = methodInfo;
	}

	public String method1(){
		methodInfo = "method1";
		return "message";
	}
	
	public String method2(){
		methodInfo = "method2";
		return "message";
	}

}
