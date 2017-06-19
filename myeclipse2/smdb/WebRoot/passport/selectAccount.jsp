<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="i18n_SelectAccountInfo" /></title>
		<s:include value="/outerBase.jsp" />
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</head>

	<body>
		<div class="login_box">
			<s:include value="/outerHead.jsp" />
			<s:hidden id="selectedAccount" value="%{username}" />
			<s:hidden id="lastAccount" value="%{accountId}" />
			<s:form action="selectAccount" namespace="/login" theme="simple">
				<div class="login_input_box">
					<div class="login_input_area">
						<div class="login_select_server_title"><s:text name="i18n_SelectAccountInfo" /></div>
						<div class="login_select_server_txt">
						<s:include value="/validateError.jsp" />
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<s:iterator value="userList" status="stat">
								<s:if test="userList[#stat.index][5] == 1 && userList[#stat.index][9] != 0">
									<tr height="30px">
										<td class="choose">
											<input type="radio" name="accountId" value="<s:property value="userList[#stat.index][2]" />" />
											<span><s:property value="userList[#stat.index][3]" /></span>
										</td>
										<td>
											<span><s:property value="userList[#stat.index][4]" /></span>
											<span>启用</span>
										</td>
									</tr>
								</s:if>
								<s:elseif test="userList[#stat.index][9] == 0">
									<tr height="30px">
										<td class="choose">
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
							</table>
						</div>
						<div class="login_btn_box2">
							<input id="submit" class="login_btn" type="submit" value="<s:text name='i18n_Ok' />" />
							<input id="logout" class="login_btn" type="button" value="<s:text name='i18n_Logout' />" />
						</div>
					</div>
				</div>
			</s:form>
		</div>
		<s:include value="/outerFoot.jsp" />
		<script type="text/javascript">
			$(function() {
				$("input[name='accountId'][type='radio']").focus();
				var account = $("#selectedAccount").val();
				if (account != undefined && account != null && account != "") {
					$("input[name='accountId'][type='radio']").each(function() {
						if ($(this).val() == account) {
							$(this).attr("checked", true);
						} else {
							$(this).attr("checked", false);
						}
					});
				};
				var accountId = $("#lastAccount").val();
				if (accountId != undefined && accountId != null && accountId != "") {
					$("input[name='accountId'][type='radio']").each(function() {
						if ($(this).val() == accountId) {
							$(this).attr("checked", true);
						} else {
							$(this).attr("checked", false);
						}
					});
				};
				
				$(".choose").bind("mouseover",function() {
					if(!($(this).children().eq(0).attr("disabled"))) {
						$(this).children().eq(1).css({
							"cursor":"pointer",
							"color":"teal"
						});
					};
				}).bind("mouseout",function(evt){
					if(!($(this).children().eq(0).attr("disabled"))){
						$(this).children().eq(1).css({
							"color":""
						});
					};
				}).bind("click",function(){
					if(!($(this).children().eq(0).attr("disabled"))){
						$(this).children().eq(0).attr("checked",true);
					};
				});
				$("#logout").click(function(){
					location.href = basePath + "logout";
				});
				$("input[name='accountId'][type='radio']").live("keypress", function(event) {//添加键盘事件，回车提交
					var keyCode = event.which;
					if (keyCode == 13) {
						var flag = false;
						$(":input[name='accountId']").each(function(){
							if($(this).attr('checked')){
								flag = true;
							}
						})
						if(!flag){return false;}
					} else {
						return true;
					}
				});
				$("#submit").click(function(){// 未选择账号，则阻止提交。
					var flag = false;
					$(":input[name='accountId']").each(function(){
						if($(this).attr('checked')){
							flag = true;
						}
					})
					if(!flag){return false;}
				});
			})
		</script>
	</body>
</html>