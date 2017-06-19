<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>角色分配</title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/security/account/pop_assign_role.js', function(popAssignRole) {
					popAssignRole.init();
				});
			</script>
		</head>

		<body>
			<div class="div_select">
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="table_css1">
					<tr>
						<td></td>
						<td valign="top" width="210">
							<div class="table_s_title">未分配角色</div>
							<div class="table_select">
								<s:select cssClass="select" id="leftselect" name="disroleIds" multiple="true"
									list="#request.disroles" listKey="id" listValue="name" cssStyle="width:190px;" />
							</div>
						</td>
						<td width="70" align="center">
							<br /><br /><img id="add" src="image/b01.gif" />
							<img id="addall" src="image/b03.gif" />
							<img id="remove" src="image/b02.gif" />
							<img id="removeall" src="image/b04.gif" /></td>
						<td valign="top" width="210">
							<div class="table_s_title">已分配角色</div>
							<div class="table_select">
								<s:select cssClass="select" id="rightselect" name="roleIds" multiple="true"
									list="#request.roles" listKey="id" listValue="name" cssStyle="width:190px;" />
							</div>
						</td>
						<td></td>
					</tr>
				</table>
			</div>
			
			<div class="btn_div_view">
				<input id="confirm" class="btn1" type="button" value="确定" />
				<input id="cancel" class="btn1" type="button" value="取消" />
			</div>
		</body>
	
</html>