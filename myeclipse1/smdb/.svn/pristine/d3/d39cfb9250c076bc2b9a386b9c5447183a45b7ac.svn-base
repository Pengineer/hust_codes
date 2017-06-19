<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
		
	</head>
	<body>
		<div class="link_bar">
			当前位置：招聘管理&nbsp;&gt;&nbsp;模板管理&nbsp;&gt;&nbsp;修改
		</div>
		<input type = "hidden" id = "entityId" value = "${entityId }"/>
		<form id = "template" method = "post" action = "management/recruit/template/modify.action?entityId=">
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr class="table_tr2">
				<td class="table_td2" width="130"><span class="table_title2">模版文件：</span></td>
				<td class="table_td3">
					<input type="file" id="template_${entityId }" />
					<input type = "hidden" id = "file" name = "file" value = "file"/>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2" width="100"><span class="table_title2">文件名：</span></td>
				<td class="table_td3"><input type="text" name="name" value="" id="name" class="input_css"/></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span>描述：<br/>（限200字）</span></td>
				<td class="table_td3"><s:textarea  id = "description" name="description" rows="6"  cssClass="textarea_css" /></td>
				<td class="table_td4"></td>
			</tr>
		</table>
		<div id="optr" class="btn_div_view">
			<input id="confirm" class="btn1" type="submit" value="提交" />
			<input id="cancel" class="btn1" type="button" value="取消" />
		</div>
		</form>
		<script type="text/javascript">
			seajs.use('javascript/management/recruit/template/modify.js', function(modify) {
				$(function(){
					modify.init();
				})
			});
		</script>
	</body>
</html>