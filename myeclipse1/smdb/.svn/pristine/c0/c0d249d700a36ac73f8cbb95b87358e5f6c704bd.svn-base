<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/pop/view/view.js', function(view) {
					
				});
			</script>
		</head>

		<body>
			<div style="width:450px;">
				<s:if test="#request.map.get('errorInfo') == null || #request.map.get('errorInfo') == ''">
					<div class="title_bar">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" width="64" align="right">用户名：</td>
								<td class="title_bar_td" width="120"><s:property value="#request.map.get('name')" /></td>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" width="64" align="right">账号状态：</td>
								<td class="title_bar_td"><s:property value="#request.map.get('status')" /></td>
							</tr>
							<tr>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right">账号类型：</td>
								<td class="title_bar_td"><s:property value="#request.map.get('type')" /></td>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<s:if test="#request.map.get('belongUnit') == null || #request.map.get('belongUnit') == ''">
									<td class="title_bar_td" align="right">所属人员：</td>
									<td class="title_bar_td"><s:property value="#request.map.get('belongPerson')" /></td>
								</s:if>
								<s:else>
									<td class="title_bar_td" align="right">所属机构：</td>
									<td class="title_bar_td"><s:property value="#request.map.get('belongPerson')" /></td>
								</s:else>
							</tr>
						</table>
					</div>
				</s:if>
				<s:else>
					<div style="text-align:center;"><s:property value="#request.map.get('errorInfo')" /></div>
				</s:else>
				<div class="btn_div_view">
					<input id="okclosebutton" class="btn1" type="button" value="确定" />
				</div>
			</div>
		</body>
	
</html>