<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title>简易计算器</title>
 
	<script type="text/javascript">
	   function welcome(){
	   	  alert("欢迎使用");
	   	  }
	</script>
	
  </head>
  
  <body style="text-align: center;" onload="welcome()">
    <jsp:useBean id="calculatorBean" class="edu.hust.domain.CalculatorBean" scope="page"></jsp:useBean>
	<jsp:setProperty property="*" name="calculatorBean"/><!-- 使用通配符的前提是table表单里的属性名和CalculatorBean里面的完全一致 -->
	
	<%
		try{
			calculatorBean.calculate();
			}catch(Exception e){
				out.print(e.getMessage());
			}
	 %>
	
	<br/>-----------------------------------------------------------------<br/>
	计算结果:
	<%=calculatorBean.getFirstNum()+calculatorBean.getOperate()+calculatorBean.getSecondNum()+"="+calculatorBean.getResult() %>
	<br/>-----------------------------------------------------------------<br/><br/><br/><br/>
	
	<form action="/JSPDemo/calculator.jsp" method="post"><!-- 将表单提交到本JSP界面 -->
		<table width="40%" border="1" cellspacing="0" align="center">
			<tr>
				<th colspan="2">简易计算器</th>
			</tr>
			<tr>
				<th>第一个参数</th>
				<td>
					<input type="text" name="firstNum"/>
				</td>
			</tr>
			<tr>
			<th>运算符</th>
			    <td>
					<select name="operate">
						<option value="+">+</option>
						<option value="-">-</option>
						<option value="*">*</option>
						<option value="/">/</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>第二个参数</th>
				<td>
					<input type="text" name="secondNum"/>
				</td>
			</tr>
			<tr>
				<th colspan="2">
					<input type="submit" value="计算"/>
					<input type="reset" value="重置"/>
				</th>
			</tr>
		</table>
		
	</form>	
  </body>
</html>
