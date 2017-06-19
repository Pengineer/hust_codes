<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="main">
	<s:form id="form_account" action="modify" theme="simple">
		<div class="main_content">
			<s:include value="/validateError.jsp" />
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="table_tr2">
					<td class="table_td2"><span class="table_title3">通行证：</span></td>
					<td class="table_td3"><s:textfield name="passport.name" cssClass="input_css" /></td>
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
										<%--<a style="cursor:pointer;" title="xxx = 精确匹配; xxx-xxx = 范围（仅仅ip号码）; * = 任何匹配; ? = 匹配单个字符（仅仅ip名称）">ip规则</a>
									--%>
										<br/><strong><span class="tip">ip规则：</span></strong>
										<br/><span class="tip">1、xxx = 精确匹配;</span>
										<br/><span class="tip">2、xxx-xxx = ip范围匹配;</span>
										<br/><span class="tip">3、* = 模糊匹配;</span>
										<br/><span class="tip">4、? = 单个字符匹配。</span>
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
										<%--<a style="cursor:pointer;" title="xxx = 精确匹配; xxx-xxx = 范围（仅仅ip号码）; * = 任何匹配; ? = 匹配单个字符（仅仅ip名称）">ip规则</a>
									--%>
										<br/><strong><span class="tip">ip规则：</span></strong>
										<br/><span class="tip">1、xxx = 精确匹配;</span>
										<br/><span class="tip">2、xxx-xxx = ip范围匹配;</span>
										<br/><span class="tip">3、* = 模糊匹配;</span>
										<br/><span class="tip">4、? = 单个字符匹配。</span>
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
