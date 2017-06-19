<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

		<head>
			<title>审核信息</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="table_tr2">
					<td class="table_td5" width="100"><span class="table_title2">审核状态：</span></td>
					<td class="table_td6"><s:radio name="auditStatus" list="#{'2':getText('通过'),'1':getText('不通过')}" cssClass="input_css_radio"/></td>
					<td class="table_td7"></td>
				</tr>
			</table>
			
			<div class="btn_div_view">
				<input id="confirm" class="btn1" type="button" value="确定" />
				<input id="cancel" class="btn1" type="button" value="取消" />
			</div>
			<script type="text/javascript">
				seajs.use('javascript/pop/view/view.js', function(view) {
					$(document).ready(function() {
						$("input[name='auditStatus']").live('click', function(){
							thisPopLayer.outData = {"auditStatus" : $(this).val()};
						});
					});
				});
			</script>
		</body>
	
</html>