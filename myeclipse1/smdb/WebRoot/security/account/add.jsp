<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="main">
	<s:form id="form_account" action="add" theme="simple">
		<div class="main_content">
			<s:include value="/validateError.jsp" />
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="table_tr2">
					<td class="table_td2" width="110">账号类型：</td>
					<td class="table_td3" width="300">${param.type}</td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2"><span class="table_title3">账号所属：</span></td>
					<td class="table_td3">
						<input id="selectButton" type="button" class="btn1 select_btn" value="选择" />
						<div id="belongEntityName" class="choose_show"></div>
						<s:hidden id="belongEntityId" name="belongEntityId" />
						<s:hidden id="belongEntityNameValue" name="belongEntityName" />
					</td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2"><span class="table_title3">用户名：</span></td>
					<td class="table_td3"><s:textfield name="passport.name" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2"><span class="table_title3">账号状态：</span></td>
					<td class="table_td3"><s:radio name="account.status" value="1" list="#{'1':'启用','0':'停用'}" cssClass="input_css_radio" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2"><span class="table_title3">有效期限：</span></td>
					<td class="table_td3"><input type="text" id="expireDate" name="account.expireDate" class="input_css" readonly="true" value="<s:date name='%{account.expireDate}' format='yyyy-MM-dd' />" /></td>
					<td class="table_td4"></td>
				</tr>
				<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@ADMINISTRATOR ) == 0">
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title3">最大连接数：</span></td>
						<td class="table_td3"><s:textfield name="passport.maxSession" value="%{#application.maxSession}" cssClass="input_css" /></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">允许登录IP：</td>
						<td class="table_td3">
							<table id="allowedTable">
								<tr class="tr_valid">
									<td>
										<input id="addAllowed" type="button" class="btn1" value="添加"/>
										<a style="cursor:pointer;" title="xxx = 精确匹配; xxx-xxx = 范围（仅仅ip号码）; * = 任何匹配; ? = 匹配单个字符（仅仅ip名称）">ip规则</a>
									</td>
								</tr>
							</table>
							<s:hidden name="passport.allowedIp" id="allowedIp" value="%{passport.allowedIp}" />
						</td>
						<td class="table_td4" id="allowedElement"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">拒绝登录IP：</td>
						<td class="table_td3">
							<table id="refusedTable">
								<tr class="tr_valid">
									<td>
										<input id="addRefused" type="button" class="btn1" value="添加"/>
										<a style="cursor:pointer;" title="xxx = 精确匹配; xxx-xxx = 范围（仅仅ip号码）; * = 任何匹配; ? = 匹配单个字符（仅仅ip名称）">ip规则</a>
									</td>
								</tr>
							</table>
							<s:hidden name="passport.refusedIp" id="refusedIp" value="%{passport.refusedIp}" />
						</td>
						<td class="table_td4" id="refusedElement"></td>
					</tr>
				</s:if>
				<tr class="table_tr2">
					<td class="table_td2"></td>
					<td class="table_td3"><s:checkbox name="passwordWarning" value="1" cssClass="input_css_radio" /><span style="line-height:18px;">登录后提示用户修改密码</span></td>
					<td class="table_td4"></td>
				</tr>
			</table>
		</div> 
		<s:include value="/submit.jsp" />
	</s:form>
</div>

<table width="100%" border="0" cellspacing="2" cellpadding="2" style="display:none;">
	<tr id="allowed" style="display:none;">
		<td><span class="ipInput" >
			<input name="allowedIp[]" type="text" maxlength=7 size=7 />.<input name="allowedIp[]" type="text" maxlength=7 size=7 />.<input name="allowedIp[]" type="text" maxlength=7 size=7 />.<input name="allowedIp[]" type="text" maxlength=7 size=7 />
		</span></td>
		<td><input type="button" class="delete_row btn1" value="删除" /></td>
	</tr>
	<tr id="refused" style="display:none;">
		<td><span class="ipInput oneInput" >
			<input name="refusedIp[]" type="text" maxlength=7 size=7 />.<input name="refusedIp[]" type="text" maxlength=7 size=7 />.<input name="refusedIp[]" type="text" maxlength=7 size=7 />.<input name="refusedIp[]" type="text" maxlength=7 size=7 />
		</span></td>
		<td><input type="button" class="delete_row btn1" value="删除" /></td>
	</tr>
</table>
<div id="errorField" style="display:none"></div>
