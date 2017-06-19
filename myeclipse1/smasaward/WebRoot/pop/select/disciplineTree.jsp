<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>学科代码</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" href="tool/ztree/css/zTreeStyle.css" type="text/css" />
		<style type="text/css" media="screen">
			table td,th {text-align:center;padding:5px;}
		</style>
	</head>

	<body>
		<div class="main" style="width:450px;height:300px;">
			<div style="vertical-align:top;width:200px;height:300px; border:solid #999 1px;overflow:scroll;float:left;">
				<ul id="discipline_tree" class="ztree"></ul>
			</div>
			<div id="selected_discipline" style="vertical-align:top;width:240px;;height:300px;overflow:scroll; border:solid #999 1px;float:left;">
				<s:hidden id="hiddenIdCodeName"/>
				<s:hidden id="hiddenCodeName"/>
				<s:hidden id="hiddenCode"/>
				<table width="100%" cellspacing="0" cellpadding="0" class="table_xk">
					<thead>
						<tr style="background-color:rgb(230, 230, 230);">
							<td width="30">序号</td>
							<td>学科代码/名称</td>
							<td width="30">删除</td>
						</tr>
					</thead>
					<tbody id="showselect"></tbody>
				</table>
			</div>
		</div>
		<div>
			<div class="btn_div">
				<input id="confirm" type="button" class="btn" value="确定" />
				<input id="cancel" type="button" class="btn" value="取消" />
			</div>
		</div>	
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="tool/ztree/js/jquery.ztree.core-3.3.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="tool/ztree/js/jquery.ztree.excheck-3.3.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/pop/pop_init.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/pop/select/disciplineTree.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>
