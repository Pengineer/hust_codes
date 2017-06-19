<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>修改密码</title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/security/account/pop_modify_password.js', function(popModifyPassword) {
					popModifyPassword.init();
				});
				<s:if test="pageInfo != null">
					(function(){
						var thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
						thisPopLayer.callBack(thisPopLayer, true);
					})();
				</s:if>
			</script>
		</head>

		<body>
			<div style="width:430px;">
				<s:form id="form_account" action="modifyPassword" theme="simple">
					<s:include value="/validateError.jsp" />
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td5" width="100"><span class="table_title2">新密码：</span></td>
							<td class="table_td6"><s:password name="newPassword" cssClass="input_css_self" size="20" /></td>
							<td class="table_td7"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td5"><span class="table_title2">重复密码：</span></td>
							<td class="table_td6"><s:password name="rePassword" cssClass="input_css_self" size="20" /></td>
							<td class="table_td7"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td5"></td>
							<td class="table_td6"><s:checkbox name="passwordWarning" value="1" cssClass="input_css_radio" /><span style="line-height:18px;">登录后提示用户修改密码</span></td>
							<td class="table_td7"></td>
						</tr>
					</table>
					<div class="btn_div_view">
						<input id="submit" class="btn1" type="submit" value="确定" />
						<input id="cancel" class="btn1" type="button" value="取消" />
					</div>
				</s:form>
			</div>
		</body>
	
</html>