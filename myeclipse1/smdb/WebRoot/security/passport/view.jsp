<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>查看</title>
			<s:include value="/innerBase.jsp" />
			
		</head>
		<body>
			<div class="link_bar">
				当前位置：通行证管理&nbsp;&gt;&nbsp;查看
			</div>
			<div id="view_content" class="main" style="display:none;">
				<s:form id="view" action="" theme="simple">
					<s:hidden id="entityId" name="entityId" value="%{entityId}" />
					<s:hidden id="entityIds" name="entityIds" value="%{entityId}" />
					<s:hidden id="update" name="update" />
					<s:hidden id="datepick" name="validity" />
					<s:hidden id="roleIds" name="roleIds" />
					<s:hidden id="pageInfo" name="pageInfo" />
					<input id="accountType" type="hidden" value="<s:property value="#session.loginer.currentType" />" />
				</s:form>
			
				<textarea id="view_choose_bar_template" style="display:none;">
					<s:include value="/security/passport/viewChooseBar.jsp" />
				</textarea>
				
				<div id="view_choose_bar" style="clear:both;"></div>
				
				<div class="main_content">
					<s:include value="/security/passport/viewCommon.jsp" />
					<div id="view_common" style="clear:both;"></div>
					<div class="main_content">
						<div id="tabs" class="p_box_bar">
							<ul>
								<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@ADMINISTRATOR ) == 0">
									<li><a href="#firewall">防火墙</a></li>
									<li><a href="#log">日志信息</a></li>
									<li><a href="#account">关联账号</a></li>
								</s:if>
							</ul>
						</div>
			
						<div class="p_box">
							<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@ADMINISTRATOR ) == 0">
								<div id="firewall">
									<s:include value="/security/account/viewFirewall.jsp" />
								</div>
								<div id="log">
									<s:include value="/security/passport/viewLog.jsp" />
								</div>
								<div id="account">
									<s:include value="/security/passport/viewAccount.jsp" />
								</div>
							</s:if>
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/security/passport/view.js', function(view) {
					$(function(){
						view.init();
					})
				});
			</script>
		</body>
	
</html>