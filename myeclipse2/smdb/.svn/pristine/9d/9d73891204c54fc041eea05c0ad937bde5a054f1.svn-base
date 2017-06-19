<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info" id="product">
	<table width="100%" border="0" cellspacing="2" cellpadding="2">
		<tr class="table_tr2">
			<td class="table_td2" width="130"><span class="table_title5">是否从已有成果中选择：</span></td>
			<td class="table_td3">
				<s:radio name="addExistOrNot" list="#{'1':getText('是'),'0':getText('否')}" value="1" cssClass="input_css_radio" />
					<input id="add_product" style="visibility:hidden; margin-right:20px;" class="btn1" type="button" value="添加成果"/>
					<br/><span class="tip">（如果您找不到要报奖的成果，请选择“否”，点击“添加成果”按钮来添加您的成果！）</span></td>
			<td class="table_td4"></td>
		</tr>
	</table>
	<div id="exist_product" >
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr class="table_tr2">
				<td class="table_td2" width="130"><span class="table_title5">成果类型：</span></td>
				<td class="table_td3"><s:radio name="resultType" list="%{#session.ptypes}"/></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">成果名称：</span></td>
				<td class="table_td3">
					<s:if test="#session.get('addOrModify')==1">
						<s:select cssClass="select j_selectDtype" id="product1" name="proId"  list="{}" headerKey="-1" headerValue="--%{getText('请选择')}--" />
					</s:if>
					<s:else>
						<s:select cssClass="select j_selectDtype" id="product1" name="proId"  list="%{#session.productmap}" headerKey="-1" headerValue="--%{getText('请选择')}--"/>
					</s:else>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">学科门类：</span></td>
				<td class="table_td3">
				<s:if test="#session.get('addOrModify')==1">
						<s:select cssClass="select" id="dtype" name="awardApplication.disciplineType" list="{}" headerKey="-1" headerValue="--%{getText('请选择')}--"/>
					</s:if>
					<s:else>
						<s:select cssClass="select" id="dtype" name="awardApplication.disciplineType" list="%{#session.dtypemap}"  headerKey="-1"  headerValue="--%{getText('请选择')}--"/>
					</s:else>
				</td>
				<td class="table_td4"></td>
			</tr>
		</table>
	</div>
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr class="table_tr2">
			<td class="table_td2" width="130"><span class="table_title5">申请届次：</span></td>
			<td class="table_td3"><s:select cssClass="select" name="awardApplication.session" list="{}" id="session1" headerKey="-1" headerValue="--%{getText('请选择')}--" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span class="table_title5">所在单位：</span></td>
			<td class="table_td3"><s:select cssClass="select" name="unitId" list="%{#session.unitDetails}" headerKey="-1" headerValue="--%{getText('请选择')}--"/></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span>获奖情况：<br/>（限500字）</span></td>
			<td class="table_td3"><s:textarea name="awardApplication.prizeObtained" rows="6" cssClass="textarea_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span>社会反映：<br/>（限500字）</span></td>
			<td class="table_td3"><s:textarea name="awardApplication.response" rows="6" cssClass="textarea_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span>引用或被采纳情况：<br/>（限500字）</span></td>
			<td class="table_td3"><s:textarea name="awardApplication.adoption" rows="6" cssClass="textarea_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span>内容简介：<br/>（限20000字）</span></td>
			<td class="table_td3"><s:textarea name="awardApplication.introduction" rows="6" cssClass="textarea_css" /></td>
			<td class="table_td4"></td>
		</tr>
	</table>
</div>
