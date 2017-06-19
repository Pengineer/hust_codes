<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="FCK" uri="http://java.fckeditor.net"%>
<div class="main">
	<div class="main_content">
		<s:include value="/validateError.jsp" />
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr class="table_tr2">
				<td class="table_td2" width="100"><span class="table_title2">新闻标题：</span></td>
				<td class="table_td3"><s:textfield name="news.title" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">新闻类型：</span></td>
				<td class="table_td3"><s:select cssClass="select" name="news.type.id" value="%{news.type.id}" list="#application.newsItems" listKey="id" listValue="name" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">新闻来源：</span></td>
				<td class="table_td3"><s:textfield name="news.source" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">是否公开：</span></td>
				<td class="table_td3"><s:radio name="news.isOpen" value="%{news.isOpen}" list="#{'1':'是','0':'否'}" cssClass="input_css_radio" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">附件：</td>
				<s:if test="flag == 0"><%-- 添加 --%>
					<td class="table_td3">
						<input type="file" id="file_add" />
					</td>
				</s:if>
				<s:elseif test="flag == 1"><%-- 修改 --%>
					<td class="table_td3">
						<input type="file" id="file_${news.id}" />
					</td>
					<s:hidden id = "newsId" name = "news.id"></s:hidden>
				</s:elseif>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">新闻正文：</span></td>
				<td colspan="2">
					<FCK:editor instanceName="news.content" value="${news.content}" basePath="/tool/fckeditor" width="100%" height="450" toolbarSet="Default"></FCK:editor>
				</td>
			</tr>
		</table>
	</div>
	<div id="existingAttachment" style="display:none">${existingAttachment}</div>
	<s:include value="/submit.jsp" />
</div>

<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
<%--<script type="text/javascript" src="tool/uploadify/js/jquery.uploadify.js"></script>--%>
<%--<script type="text/javascript" src="tool/uploadify/js/jquery.uploadify-ext.js"></script>--%>
<%--<script type="text/javascript">--%>
<%--	$(function() {--%>
<%--		$("#file_edit").uploadifyExt({--%>
<%--			uploadLimitExt : 1,--%>
<%--			fileSizeLimit : '3MB',--%>
<%--			fileTypeExts : '*.gif; *.jpg; *.png',--%>
<%--			fileTypeDesc : '图片'--%>
<%--		});--%>
<%--	});--%>
<%--</script>--%>