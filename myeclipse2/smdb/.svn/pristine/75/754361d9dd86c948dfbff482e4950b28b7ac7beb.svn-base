<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:form id="advSearch" action="advSearch" theme="simple">
	<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>
	<s:hidden id="createDate1" name="createDate1" value="%{searchQuery.createDate1}"/>
	<s:hidden id="createDate2" name="createDate2" value="%{searchQuery.createDate2}" />
	<s:hidden id="expireDate1" name="expireDate1" value="%{searchQuery.expireDate1}"/>
	<s:hidden id="expireDate2" name="expireDate2" value="%{searchQuery.expireDate2}" />
	<div class="adv_content">
	<s:include value="/validateError.jsp" />
	<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="65" align="right">用户名：</td>
					<td class="adv_td1" width="200"><s:textfield name="accountName" value="%{searchQuery.accountName}" cssClass="input_css" /></td>
					<td class="adv_td1" width="100" align="right"></td>
					<td class="adv_td1" width="65" align="right">所属人员：</td>
					<td class="adv_td1" width="200" ><s:textfield name="belongPersonName" value="%{searchQuery.belongPersonName}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">创建时间：</td>
					<td class="adv_td1"><s:textfield id="createDate3" value="%{searchQuery.createDate1}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" />&nbsp;至&nbsp;<s:textfield id="createDate4" value="%{searchQuery.createDate2}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">有效期限：</td>
					<td class="adv_td1"><s:textfield id="expireDate3" value="%{searchQuery.expireDate1}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" />&nbsp;至&nbsp;<s:textfield id="expireDate4" value="%{searchQuery.expireDate2}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td2" align="right">账号状态：</td>
					<td class="adv_td2" ><s:select cssClass="select" name="status" value="%{searchQuery.status}" value="-1" headerKey="-1" headerValue="--%{getText('不限')}--" list="#{'1':getText('启用'),'0':getText('停用')}" /> </td>
					<td class="adv_td2" ></td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
    		 <tr style="height:36px;">
				  <td align="right"></td>
				  <td width="60"><input id="list_button_advSearch" type="button" value="检索" class="btn1"/></td>
				  <td width="80"><input id="list_search_hide" type="button" value="隐藏条件" class="btn2" /></td>
	   	  	</tr>
		</table>
	</div> 
</s:form>
