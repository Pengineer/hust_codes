<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>添加</title>
			<s:include value="/innerBase.jsp" />
			
		</head>
		
		<body>
			<div style="width:430px;">
				<s:include value="/validateError.jsp" />
				<s:hidden id="aPrincipal" name="subClassPrincipal" />
				<s:hidden id = "flag" name = "flag"></s:hidden>
				<s:hidden id="aType" name="subClassType" />
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr class="table_tr2">
						<td class="table_td5" width="100">账号类型：</td>
						<td class="table_td6" id="accountType"  colspan="2"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td5">账号所属：</td>
						<td class="table_td6" id="belongName" name="belongName" colspan="2"></td>
					</tr>
					<s:if test="flag == 1">
						<tr class="table_tr2">
							<td class="table_td5"><span class="table_title2">用户名：</span></td>
							<td class="table_td6"><s:textfield name="passport.name" id="accountName" cssClass="input_css" readonly= "true "/></td>
							<td class="table_td7"></td>
						</tr>
					</s:if>
					<s:else>
						<tr class="table_tr2">
							<td class="table_td5"><span class="table_title2">用户名：</span></td>
							<td class="table_td6"><s:textfield name="passport.name" id="accountName" cssClass="input_css" /></td>
							<td class="table_td7"></td>
						</tr>
					</s:else>
					<tr class="table_tr2">
						<td class="table_td5"><span class="table_title2">账号状态：</span></td>
						<td class="table_td6"><s:radio name="accountStatus" value="1" id="accountStatus" list="#{'1':'启用','0':'停用'}" cssClass="input_css_radio" /></td>
						<td class="table_td7"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td5"><span class="table_title2">有效期限：</span></td>
						<td class="table_td6"><s:textfield id="expireDate" name="account.expireDate" cssClass="input_css" readonly="true" /></td>
						<td class="table_td7"></td>
					</tr>
				</table>
				<div class="btn_div_view">
					<input id="submit" class="btn1" type="submit" value="确定" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/security/account/extIf/add.js', function(add) {
					$(document).ready(function() {
						add.init();
					});
				});
			</script>
		</body>
	
</html>