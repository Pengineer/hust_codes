<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>找回密码</title>
			<s:include value="/outerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/user/register.css" />
			<link rel="stylesheet" type="text/css" href="css/user/validate.css" />
		</head>

		<body>
			<div class="container">
				<div class="head">
					<p>社科管理咨询服务系统</p>
					<div class="menu">
						<a href="main/toIndex.action">首页</a>
					</div>
				</div>
				<div class="retrieve_pass_center">
					<div class="retrievepassword">
						<strong>请填写以下信息找回密码</strong>
					</div>
					<s:include value="/validateError.jsp" />
					<div class="center">
						<s:form action="retrievePassword" id="form_user_retrievePassword" namespace="/user"
							theme="simple">
							<table class="table_center">
								<tr>
									<td class="td_right">
										<span class="text_red">*</span>&nbsp;
										账号
									</td>
									<td class="td_value">
										<div class="input0">
											<s:textfield name="username" cssClass="input2" />
										</div>
									</td>
								</tr>
								<tr>
									<td></td>
									<td class="td_sub">
										<input type="submit" class ="btn" value = "确定" />
										<input type="button" class="btn" value="取消" onclick="history.back();" />
									</td>
								</tr>
							</table>
						</s:form>
					</div>
				</div>
			</div>
			<div class="login_footer">
				<div class="footer_r_txt">制作单位：中国高校社会科学数据中心
					<br/>Copyright&copy; 2009-2013 All Rights Reserved.&nbsp; 系统版本：1.0.<%=application.getAttribute("systemVersion")%>					
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/user/user_validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<s:if test="#session.locale.equals(\"en_US\")">
				<script type="text/javascript" src="javascript/user/user_validate_en.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			</s:if>
		</body>
</html>