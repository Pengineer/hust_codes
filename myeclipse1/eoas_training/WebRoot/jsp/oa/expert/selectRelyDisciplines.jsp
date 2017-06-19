<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>选择</title>
		<s:include value="/jsp/innerBase.jsp" />
		<script type="text/javascript" src="tool/dhtmlxTree/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="tool/dhtmlxTree/dhtmlxtree.js"></script>
		<script type="text/javascript" src="dwr/interface/expertService.js"></script>
		<script type="text/javascript" src="javascript/engine.js"></script>
		<script type="text/javascript" src="dwr/util.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="javascript/oa/expert/select_discipline.js"></script>
	</head>

	<body>
		<div style="width:437px;">
			<s:form theme="simple">
				<div id="expall">
					<s:hidden id="hiddenId" />
					<table border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse; width:100%">
						<tr>
							<td height="30" style="border:solid #999 1px; width:225px;">一级学科：<s:select cssClass="select" cssStyle="width:164px" id="discipline1" name="discipline1" headerKey="-1" headerValue="--不限--"
							 list="#application.disciplineOne" listKey="id" listValue="name"  onchange="loadDiscpTree(this.value)" /></td>
						</tr>
						<tr>
							<td style="vertical-align:top; border:solid #999 1px; height:240px;">						
								<div id="loading" style="display:none; text-align:center">正在加载学科树，请稍后...</div>
								<div id="treeId" style="display:none; height:240px;"></div>
							</td>			
						</tr>
					</table>
				</div>
				<div id="list_container" style="display:none; height:237px; overflow-y:scroll; overflow-x:hidden;"></div>
				<div>
					当前选择:
					<label id="trimedName" name="checkboxPop" style="cursor:default;">
						
					</label>
				</div>
				<div class="btn_div_view" style = "margin-top:10px;text-align: center;">
					<input id="confirm" class="btn btn-default" type="submit" value="确定" />
					<input id="cancel" class="btn btn-default" type="button" value="取消" />
				</div>
			</s:form>
		</div>
	</body>
</html>