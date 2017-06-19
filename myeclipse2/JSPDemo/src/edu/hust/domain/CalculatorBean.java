package edu.hust.domain;
/**使用JavaBean+JSP进行web开发。只能应用于不太复杂的web应用开发。
 * JavaBean（CalculatorBean.java）：用于封装数据。
 * JSP（calculator.jsp）：既做数据的显示，又做数据的处理。
 */
import java.math.BigDecimal;

public class CalculatorBean {
	//必须赋初始值，否则第一次访问会抛出空指针异常
	private String firstNum = "0";
	private String secondNum = "0";
	private char operate = '+';
	private String result;
	public String getFirstNum() {
		return firstNum;
	}
	public void setFirstNum(String firstNum) {
		this.firstNum = firstNum;
	}
	public String getSecondNum() {
		return secondNum;
	}
	public void setSecondNum(String secondNum) {
		this.secondNum = secondNum;
	}
	public char getOperate() {
		return operate;
	}
	public void setOperate(char operate) {
		this.operate = operate;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public void calculate(){
		//精确计算中使用BigDecimal对象封装数据
		BigDecimal first = new BigDecimal(firstNum);
		BigDecimal second = new BigDecimal(secondNum);
		
		switch(operate){
		case '+': result = first.add(second).toString();
			break;
		case '-': result = first.subtract(second).toString();
			break;
		case '*': result = first.multiply(second).toString();
			break;
		case '/': 
			if(second.doubleValue()==0)
				throw new RuntimeException("除数不能为0！");
			result = first.divide(second).toString();
			break;
		}
	}
}
