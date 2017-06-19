<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<base href="<%=basePath%>" />
		<title><s:text name="考勤管理" /></title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
		<link href="tool/jquery.datepick.package-4.0.5/flora.datepick.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick.js"></script>
		<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick-zh-CN.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.datepick.self.js"></script>
	</head>

		<body>
			<div class="title_bar">
				<ul>
					<li class="m"><s:text name="考勤管理" /></li>
					<li class="text_red"><s:text name="请假申请" /></li>
				</ul>
			</div>
			<div class="div_table">
				<s:form action="add" id="form_asset" namespace="/attendance" theme="simple" method="post">
					<table>
						<tr>
							<div><s:hidden name="tag" /></div>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="申请人" />：</td>
							<td>
								<div class="input0" style="float:left;">
									<s:property value = "person.realName" />
								</div>
								<div></div>
							</td>


							
						
						<s:if test="person.department == null">
							<tr>
								<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="申请部门" />：</td>
								<td><s:property value = "" /></td>
							</tr>
						</s:if>
						<s:else>
							<tr>
								<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="申请部门" />：</td>
								<td><s:property value = "person.department.name"  /></td>
							</tr>
						</s:else>

							
							
							
							
							
							
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="申请日期" />：</td>
							<td>
								<div class="input0" style="float:left;">
									<s:textfield id = "applyTime" name="attendance.applyTime" cssClass="inputcss" />
								</div>
								<div></div>
							</td>
						</tr>	
						<tr>	
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="工号" />：</td>
							<td>
								<div class="input0" style="float:left;">
									<s:textfield name="person.staffnum" cssClass="inputcss" />
								</div>
								<div></div>
							</td>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="申请天数" />：</td>
							<td>
								<div class="input0" style="float:left;">
									<s:textfield name="attendance.days" cssClass="inputcss" />
								</div>
								<div></div>
							</td>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="起止时间" />：</td>
							<td>
								<div class="input0" style="float:left;">
									<s:textfield id = "startTime" name="attendance.startTime" cssClass="inputcss" />
								</div>
								<div></div>
							</td>
							<td>
								<div class="input0" style="float:left;">
									<s:textfield id = "endTime" name="attendance.endTime" cssClass="inputcss" />
								</div>
								<div></div>
							</td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="请假类型" />：</td>
							<td>
								<s:select list="#{1:'事假',2:'病假',3:'婚假',4:'丧假',5:'产假',6:'年假',7:'调休'}" name = "attendance.type" headerKey="0" headerValue="请选择" /> 
								
							</td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="备注" />：</td>
							<td>&nbsp; 
								<br><div class="input0" style="float:left;">
									<s:textfield name="attendance.note" cssClass="inputcss" />
								</div>
								<div></div>
							</td>

							<div class="btn_bar2">
								<input id="submit" class="btn1" type="submit" value="<s:text name='确定' />" />
								<input id="cancel" class="btn1" type="button" value="<s:text name='取消' />" onclick="history.back();" />
							</div>
						</tr>
					</table>
					
				</s:form>
			</div>
		</body>
</html>