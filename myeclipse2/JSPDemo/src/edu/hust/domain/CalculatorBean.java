package edu.hust.domain;
/**ʹ��JavaBean+JSP����web������ֻ��Ӧ���ڲ�̫���ӵ�webӦ�ÿ�����
 * JavaBean��CalculatorBean.java�������ڷ�װ���ݡ�
 * JSP��calculator.jsp�����������ݵ���ʾ���������ݵĴ���
 */
import java.math.BigDecimal;

public class CalculatorBean {
	//���븳��ʼֵ�������һ�η��ʻ��׳���ָ���쳣
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
		//��ȷ������ʹ��BigDecimal�����װ����
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
				throw new RuntimeException("��������Ϊ0��");
			result = first.divide(second).toString();
			break;
		}
	}
}
