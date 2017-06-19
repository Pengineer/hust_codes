<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加教师</title>
		<s:include value="/innerBase.jsp" />
	</head>
	<body>
		<div style="width:430px;">
			<div id="fri">
				<s:hidden id="entityId" name="entityId"/>
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr class="table_tr2">
						<td class="table_td2" width="200"><span class="table_title2" >拒绝理由：</span></td>
						<td class="table_td3"><s:textarea id="refuse" name="refuse" style="width: 240px; height: 95px;" /></td>
						<td class="table_td4"></td>
					</tr>
				</table>
			</div>

			<div class="btn_div_view">
				<input id="refuseBut" class="btn1" type="submit" value="确定" />
				<input id="cancel" class="btn1" type="button" value="取消" />
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/person/link/edit.js', function(edit) {
				$(function(){
					edit.init();
				})
			});
		</script>
	</body>
</html>
