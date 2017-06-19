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
		<title><s:text name="热点新闻" /></title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	</head>

	<body onload="displayFileError();">
		<div class="title_bar">
			<ul>
				<li class="m"><s:text name="热点新闻" /></li>
				<li class="text_red"><s:text name="修改新闻" /></li>
			</ul>
		</div>
		<div class="div_table">
			<s:form theme="simple" method="post" id="form_news" validate="true" action="modify" namespace="/news">
				<div class="errorInfo">
					<s:property value="tip" />
				</div>
				<table width="100%" border="1" cellspacing="0" cellpadding="4" style="border-collapse:collapse;" bordercolor="#253d56">
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="新闻标题" />：</td>
						<td><s:textfield name="news.title" id="title" value="%{news.title}" theme="simple" size="60" cssClass="inputcss" onblur="check()"/></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="作者" />：</td>
						<td><s:property value="#session.newsAuthor"/></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="新闻类型" />：</td>
						<td><s:select list="{'公司新闻','转载新闻'}" theme="simple" headerKey="0" headerValue="--请选择--"></s:select></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="新闻来源" />：</td>
						<td><s:textfield name="news.source" value="%{news.source}" theme="simple" size="60" cssClass="inputcss" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="可见范围" />：</td>
						<td><s:radio name="news.isopen" list="#{'0':'内网可见','1':'所有可见'}" value="%{news.isopen}" theme="simple" /></td>
					</tr>


					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9" style="padding-top: 5px; vertical-align: top;"><s:text name="正文" />：</td>
						<td>
							<s:textfield name="news.content" />
							<input type="hidden" name="newsId" value="<s:property value="newsId" />" />
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#b3c6d9">&nbsp;</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="90"><input type="submit" class ="btn1" value="<s:text name='确定' />" /></td>
									<td><input type="button" class="btn1" value="<s:text name="取消" />" onclick="history.back();" /></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	</body>

</html>