<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="FCK" uri="http://java.fckeditor.net"%>
<table width="100%" border="0" cellspacing="2" cellpadding="2">
	<tr class="table_tr_out2">
		<td class="table_td_out2"><span class="table_title9">留言标题：</span></td>
		<td class="table_td_out3" colspan="4">
			<s:textfield name="message.title" cssClass="input_css_out" />
		</td>
		<td class="table_td_out5"></td>
	</tr>
	<tr class="table_tr_out2">
		<td class="table_td_out2"><span class="table_title9">发布者：</span></td>
		<td class="table_td_out4"><s:textfield name="message.authorName" cssClass="input_css_out" /></td>
		<td class="table_td_out5"></td>
		<td class="table_td_out2"><span class="table_title9">留言类型：</span></td>
		<td class="table_td_out4"><s:select cssClass="select" name="message.type.id" list="#application.messageItems" headerKey="-1" headerValue="--请选择--" listKey="id" listValue="name" /></td>
		<td class="table_td_out5"></td>
	</tr>
	<tr class="table_tr_out2">
		<td class="table_td_out2"><span class="table_title9">邮箱：</span></td>
		<td class="table_td_out4"><s:textfield name="message.email" cssClass="input_css_out" /></td>
		<td class="table_td_out5"></td>
		<td class="table_td_out2">电话：</td>
		<td class="table_td_out4"><s:textfield name="message.phone" cssClass="input_css_out" /></td>
		<td class="table_td_out5"></td>
	</tr>
	<tr>
		<td colspan="4"></td>
		<td colspan="2"><span class="tip">区号-电话号(-分机号)或11位手机号</span></td>
	</tr>
	<tr class="table_tr_out2">
		<td class="table_td_out2"><span class="table_title9">留言正文：</span></td>
		<td colspan="5">
			<FCK:editor instanceName="message.content" value="${message.content}" basePath="/tool/fckeditor" width="100%" height="150" toolbarSet="Basic"></FCK:editor>
		</td>
	</tr>
</table>