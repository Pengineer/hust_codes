<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>设置账号有效期</title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/security/account/pop_enable.js', function(popEnable) {
					popEnable.init();
				});
			</script>
		</head>

		<body>
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="table_tr2">
					<td class="table_td5" width="100"><span class="table_title2">有效期至：</span></td>
					<td class="table_td6"><s:textfield id="datepick" name="validity" cssClass="input_css_self" size="10" /></td>
					<td class="table_td7"></td>
				</tr>
			</table>
			
			<div class="btn_div_view">
				<input id="confirm" class="btn1" type="button" value="确定" />
				<input id="cancel" class="btn1" type="button" value="取消" />
			</div>
			
			<div style="height:120px"></div>
		</body>
	
</html>