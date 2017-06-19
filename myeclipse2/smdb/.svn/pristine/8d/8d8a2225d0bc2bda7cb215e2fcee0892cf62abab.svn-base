<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查询</title>
		<s:include value="/outerBase.jsp" />
		<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/lib/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/lib/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/template_tool.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		
		<script type="text/javascript">
		$(function(){
			Template_tool.init();
			$("#form_query").submit(function(){
				if (!/^\d+$/.test($("input[name=firstResult]").val())) {
					$("input[name=firstResult]").val(0);
				}
				if (!/^\d+$/.test($("input[name=maxResults]").val()) || $("input[name=maxResults]").val() > 100) {
					$("input[name=maxResults]").val(100);
				}
				$("#form_query").ajaxSubmit({
					success : function(result){
						Template_tool.populate(result);
					}
				});
				return false;
			});
		});
		</script>
		
	</head>

	<body>
		<s:form id="form_query" action="query" namespace="/query">
			<s:textarea name="queryString" cols="90" rows="8" />
			<table>
				<tr>
					<td>firstResult:</td>
					<td><s:textfield name="firstResult"  /></td>
					<td rowspan="3"><input type="submit" id="btn_query" value="Query" style="height:70px;width:100px;"/></td>
				</tr>
				<tr>
					<td>maxResults:</td>
					<td><s:textfield name="maxResults" /></td>
				</tr>
				<tr>
					<td>type:</td>
					<td><s:radio name="type" value="0" list="#{'0':'HQL','1':'SQL'}" /></td>
				</tr>
			</table>
		</s:form>
		
		<textarea class="view_template" style="display:none">
		{if data == null}
			<span> Query <font color="red">failed</font>, costing <span style="weitght:bold;font-size:20px;color:blue">${costTime}</span> ms !</span>
			<pre>${errorInfo}</pre>
		{else}
			<span> Query <font color="green">successfully</font>, with <span style="weitght:bold;font-size:20px;color:blue">${data.length}</span> rows, </>costing <span style="weitght:bold;font-size:20px;color:blue">${costTime}</span> ms !</span>
			<table>
			{for row in data}
				<tr>
				{if row instanceof Array}
					{for col in row}
						<td>${JSON.stringify(col)}</td>
					{/for}
				{else}
					<td>${JSON.stringify(row)}</td>
				{/if}
				</tr>
			{/for}
			</table>
		{/if}
		</textarea>
	</body>
</html>
