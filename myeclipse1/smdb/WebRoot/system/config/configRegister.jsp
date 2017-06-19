<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>系统选项表</title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/system/config/validate.js', function(validate) {
					$(function(){
						validate.valid();
					})
				});
			</script>
		</head>

		<body>
			<div class="link_bar">
				当前位置：系统配置&nbsp;&gt;&nbsp;配置系统注册
			</div>
			
			<div class="main" style="width:100%">
				<s:form id="form_config" action="configRegister" namespace="/system/config">
					<div class="main_content">
						<s:include value="/validateError.jsp" />
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td2" width="130">是否开放教师注册</td>
								<td class="table_td3"><s:select cssClass="select" name="teacherRegister" value="%{#application.teacherRegister}" list="#{'-1':'--请选择--','0':'不开放','1':'开放'}" /></td>
								<td class="table_td4"></td>
							</tr>
						</table>
						<s:include value="/submit.jsp" />
					</div>
				</s:form>
			</div>
		</body>
	
</html>
