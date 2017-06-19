<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>您已登录，请选择前往的服务器</title>
		<s:include value="/innerBase.jsp" />
		<style type="text/css">
			 .login_input {height:20px; border:solid 1px #aaaaaa; background:#FFF;}
		</style>
	</head>
	<body>
	 	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:5px;">
			<tr>
				<td><span>请选择切换的账号：</span></td>
				<td><span id= "error" class="error"></span></td>
			</tr>
		</table>
		
		<s:include value="/validateError.jsp" />
		<div style="height:110px; padding:4px; border:1px solid #A9A9A9;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<s:iterator value="userList" status="stat">
					<s:if test="userList[#stat.index][5] == 1 && userList[#stat.index][9] != 0">
						<tr height="30px">
							<td class="choose" width="40%">
								<input type="radio" name="accountId" value="<s:property value="userList[#stat.index][2]" />" />
								<span><s:property value="userList[#stat.index][3]" /></span>
							</td>
							<td>
								<span><s:property value="userList[#stat.index][4]" /></span>
								<span>启用</span>
								<s:hidden name = "userList[#stat.index][2]"></s:hidden>
							</td>
						</tr>
					</s:if>
					<s:elseif test="userList[#stat.index][9] == 0">
						<tr height="30px">
							<td class="choose" width="40%">
								<input type="radio" name="accountId" value="<s:property value="userList[#stat.index][2]" />" disabled="disabled" />
								<span><s:property value="userList[#stat.index][3]" /></span>
							</td>
							<td>
								<span><s:property value="userList[#stat.index][4]" /></span>
								<span>未分配角色</span>
							</td>
						</tr>
					</s:elseif>
					<s:else>
						<tr height="30px">
							<td class="choose">
								<input type="radio" name="accountId" value="<s:property value="userList[#stat.index][2]" />" disabled="disabled" />
								<span><s:property value="userList[#stat.index][3]" /></span>
							</td>
							<td>
								<span><s:property value="userList[#stat.index][4]" /></span>
								<span>停用</span>
							</td>
						</tr>
					</s:else>
				</s:iterator>
				<s:if test="#session.loginer.isSuperUser == 1">
					<tr height="30px">
						<td class="choose" width="40%">
							<img src="image/input_ico1.gif" /><span>用户名</span>
						</td>
						<td>
							<input type="text" class="login_input" name="username" id="username"  />
						</td>
					</tr>
				</s:if>
			</table>
		</div>
		
		<div class="btn_div_view">
			<ul>
				<li><input id="okbutton" class="btn1" type="button" value="确定" /></li>
			</ul>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/passport/switch_account.js', function(switch_account) {
				switch_account.init();
			});
		</script>
	</body>
</html>