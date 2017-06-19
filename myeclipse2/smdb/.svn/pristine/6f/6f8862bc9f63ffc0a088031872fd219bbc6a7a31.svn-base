<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="main">
	<s:form id="form_account" action="modify" theme="simple">
		<div class="main_content">
			<s:include value="/validateError.jsp" />
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="table_tr2">
					<td class="table_td2" width="110">账号类型：</td>
					<td class="table_td3">${param.type}</td>
					<td class="table_td4"></td>
				</tr>
				<s:hidden name="belongEntityId" />
				<s:hidden name="belongEntityName" />
			<s:if test="(account.type.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)) && account.isPrincipal == 1">
				<tr class="table_tr2">
					<td class="table_td2">所属机构：</td>
					<td class="table_td3">
						<a id="<s:property value='belongEntityId' />" class="linkA" href="" title="点击查看详细信息"><s:property value="belongEntityName" /></a>
					</td>
					<td class="table_td4"></td>
				</tr>
			</s:if>
			<s:elseif test="account.type.equals(@csdc.tool.bean.AccountType@DEPARTMENT) && account.isPrincipal == 1">
				<tr class="table_tr2">
					<td class="table_td2">所属院系：</td>
					<td class="table_td3">
						<a id="<s:property value='belongEntityId' />" class="linkD" href="" title="点击查看详细信息"><s:property value="belongEntityName" /></a>
					</td>
					<td class="table_td4"></td>
				</tr>
			</s:elseif>
			<s:elseif test="account.type.equals(@csdc.tool.bean.AccountType@INSTITUTE) && account.isPrincipal == 1">
				<tr class="table_tr2">
					<td class="table_td2">所属基地：</td>
					<td class="table_td3">
						<a id="<s:property value='belongEntityId' />" class="linkI" href="" title="点击查看详细信息"><s:property value="belongEntityName" /></a>
					</td>
					<td class="table_td4"></td>
				</tr>
			</s:elseif>
			<s:elseif test="(account.type.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT))">
				<tr class="table_tr2">
					<td class="table_td2">所属人员：</td>
					<td class="table_td3">
						<a id="<s:property value='belongEntityId' />" class="linkP" href="" title="点击查看详细信息"><s:property value="belongEntityName" /></a>
					</td>
					<td class="table_td4"></td>
				</tr>
			</s:elseif>
			<s:elseif test="account.isPrincipal == 0">
				<tr class="table_tr2">
					<td class="table_td2">所属人员：</td>
					<td class="table_td3">
						<a id="<s:property value='belongEntityId' />" class="linkP" href="" title="点击查看详细信息"><s:property value="belongEntityName" /></a>
					</td>
					<td class="table_td4"></td>
				</tr>
			</s:elseif>
				<tr class="table_tr2">
					<td class="table_td2"><span class="table_title3">用户名：</span></td>
					<td class="table_td3"><s:textfield name="passport.name" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2"><span class="table_title3">账号状态：</span></td>
					<td class="table_td3"><s:radio name="account.status" list="#{'1':getText('启用'),'0':getText('停用')}" cssClass="input_css_radio" /></td>
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
						<td class="table_td3"><s:textfield name="passport.maxSession" cssClass="input_css" /></td>
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
					<td class="table_td3"><s:checkbox name="passwordWarning" value="%{passport.passwordWarning}" cssClass="input_css_radio" /><span style="line-height:18px;">登录后提示用户修改密码</span></td>
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
