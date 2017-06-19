<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <title><s:text name="i18n_LoginInfo" /></title>
	    <s:include value="/outerBase.jsp" />
	    <script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</head>

	<body>
		<div class="login_box">
			<s:include value="/outerHead.jsp" />
			<s:hidden id="selectedServer" value="%{serverName}" />
			<s:form action="switchServer" namespace="/login" theme="simple">
				<div class="login_input_box">
					<div class="login_input_area">
						<div class="login_select_server_title"><s:text name="i18n_LoginInfo" /></div>
						<div class="login_select_server_txt">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<s:iterator value="serverList" status="stat">
									<s:if test="(#stat.index)%2 == 0"><tr height="30px"></s:if>
									<s:if test='serverList[#stat.index][1] == 1'>
										<td class="choose">
											<input type="radio" name="serverName" value="<s:property value="serverList[#stat.index][2]" />" />
											<span><s:property value="serverList[#stat.index][0]" /></span>
										</td>
									</s:if>
									<s:else>
										<td class="choose">
											<input type="radio" name="serverName" value="<s:property value="serverList[#stat.index][2]" />" disabled="disabled" />
											<span class="disable"><s:property value="serverList[#stat.index][0]" /></span>
										</td>
									</s:else>
									</td>
									<s:if test="(#stat.index+1)%2 == 0"></tr></s:if>
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
			$(document).ready(function() {
				var serverName = $("#selectedServer").val();
				if (serverName != undefined && serverName != null && serverName != "") {
					$("input[name='serverName'][type='radio']").each(function() {
						if ($(this).val() == serverName) {
							$(this).attr("checked", true);
						} else {
							$(this).attr("checked", false);
						}
					});
				}
				var selectedServer = $("input[name='serverName'][type='radio']:checked");
				if (selectedServer != null && selectedServer.length != 0) {
					selectedServer.get(0).focus();
				}
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
			});
		</script>
	</body>
</html>