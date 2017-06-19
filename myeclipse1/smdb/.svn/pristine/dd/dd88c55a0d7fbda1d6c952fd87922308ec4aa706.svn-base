<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="FCK" uri="http://java.fckeditor.net"%>
<div class="main">
	<div class="main_content">
		<s:if test="status != 0">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="line-height:14px;">
				<tr>
					<td class="tdbg1"><s:property value="replyMessage.title" /></td>
					<td width="30" class="tdbg1"><a href="mail/toAdd.action?mail.sendTo=${replyMessage.email}"><img src="image/g05.gif" title="Email" /></a>&nbsp;</td>
				</tr>
			</table>
			<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse; background:#f7f7f7;" bordercolor="#253d56">
				<tr>
					<td width="200" rowspan="2" valign="top" bgcolor="#e3cfeb" style="border-width:1px 1px 0 1px; padding-top:5px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20%" align="right"></td>
								<td style="font-weight:bold;"><s:property value="replyMessage.authorName" /><s:if test="replyMessage.account != null">(<span class="text_red">认证用户</span>)</s:if></td>
							</tr>
						</table>
					</td>
					<td height="50" class="txtfckpadding">${replyMessage.content}</td>
				</tr>
				<tr>
					<td class="txtpadding">
						<span>
							发表于：<s:date name="replyMessage.createDate" format="yyyy-MM-dd HH:mm:ss" />
						</span>
					</td>
				</tr>
			</table>
			<br />
		</s:if>
		<s:include value="/validateError.jsp" />
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr class="table_tr2">
				<td class="table_td2" width="100"><span class="table_title2">留言标题：</span></td>
				<td class="table_td3">
					<s:if test="status == 0"><s:textfield name="message.title" cssClass="input_css" /></s:if>
					<s:else><s:textfield name="message.title" value="Re：%{replyMessage.title}" cssClass="input_css" /></s:else>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">邮箱：</span></td>
				<td class="table_td3"><s:textfield name="mail.sendTo" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">电话：</td>
				<td class="table_td3"><s:textfield name="message.phone" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">留言类型：</span></td>
				<td class="table_td3"><s:select cssClass="select" name="message.type.id" value="%{message.type.id}" list="#application.messageItems" headerKey="-1" headerValue="--请选择--" listKey="id" listValue="name" /></td>
				<td class="table_td4"></td>
			</tr>
			<s:if test="status != 0">
				<tr class="table_tr2">
					<td class="table_td2"><span class="table_title2">发送邮件：</span></td>
					<td class="table_td3">
						<table>
							<tr>
								<td>
									<input id="chooseAll" name="chooseAll" type="checkbox" value="0">
									<lable class="checkboxLabel" for="chooseAll">全选</label>
								</td>
								<td><s:hidden id="replyTo" value="%{replyMessage.email}" /></td>
							</tr>
							<s:iterator value="participants" status="stat">
								<s:if test="(#stat.index)%2==0"><tr></s:if>
								<td><input type="checkbox" name="sendList" value="<s:property value='key' />" />
								<s:property value="value" /></td><td width="30px;"></td>
								<s:if test="(#stat.index)%2==1"></tr></s:if>
							</s:iterator>
						</table>
					</td>
					<td class="table_td4"></td>
				</tr>
			</s:if>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">是否公开：</span></td>
				<td class="table_td3"><s:radio name="message.isOpen" value="%{message.isOpen}" list="#{'1':'是','0':'否'}" cssClass="input_css_radio" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">是否匿名：</span></td>
				<td class="table_td3"><s:radio name="anonymous" list="#{'1':'是','0':'否'}" cssClass="input_css_radio" /></td>
				<td class="table_td4"></td>
			</tr>
			<s:hidden name="contentHeader" />
			<s:hidden name="message.replyTopic.id" value="%{replyMessage.replyTopic.id}" />
			<s:hidden name="message.replyMessage.id" value="%{replyMessage.id}" />
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">留言正文：</span></td>
				<td colspan="2">
					${contentHeader}
					<FCK:editor instanceName="message.content" value="${message.content}" basePath="/tool/fckeditor" width="100%" height="450" toolbarSet="Default"></FCK:editor>
				</td>
			</tr>
		</table>
	</div>
	<s:include value="/submit.jsp" />
</div>