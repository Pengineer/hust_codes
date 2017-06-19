<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info" id="basic2">
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr class="table_tr2">
			<td class="table_td2" width="100">英文名：</td>
			<td class="table_td3"><s:textfield name="person.englishName" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr>
		<s:iterator value="#incomeOfficers" status="statu" id="item">  
		    <s:property value="name" />   
		</s:iterator>  
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">曾用名：</td>
			<td class="table_td3"><s:textfield name="person.usedName" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">性别：</span></td>
			<td class="table_td3"><s:select cssClass="select" name="person.gender" headerKey="-1" headerValue="--请选择--" list="#application.sexList" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">出生日期：</td>
			<td class="table_td3">
				<s:textfield name="person.birthday" cssClass="input_css FloraDatepick">
					<s:param name="value">
						<s:date name="%{person.birthday}" format="yyyy-MM-dd" />
					</s:param>
				</s:textfield>
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">政治面貌：</td>
			<td class="table_td3"><s:select cssClass="select" name="person.membership" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('membership', null)}" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">国家或地区：</td>
			<td class="table_td3"><s:select cssClass="select" name="person.countryRegion" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('ISO3166-1', null)}" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">民族：</td>
			<td class="table_td3"><s:select cssClass="select" name="person.ethnic" headerKey="-1" headerValue="--请选择民族--" list="%{baseService.getSystemOptionMapAsName('GB3304-91', null)}" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">籍贯：</td>
			<td class="table_td3"><s:textfield name="person.birthplace" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">照片：</td>
			<td class="table_td3">
				<input type="file" id="photo_${person.id}" />
<%--				<input id="personId" value="${person.id}" style="display:none;"/>--%>
			</td>
			<td class="table_td4"></td>
		</tr>
		<s:hidden id = "personId" name = "person.id"></s:hidden>
	</table>
</div>

<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
<%--<script type="text/javascript" src="tool/uploadify/js/jquery.uploadify.js"></script>--%>
<%--<script type="text/javascript" src="tool/uploadify/js/jquery.uploadify-ext.js"></script>--%>
<%--<script type="text/javascript">--%>
<%--	$(function() {--%>
<%--		$("#photo_${person.id}").uploadifyExt({--%>
<%--			uploadLimitExt : 1,--%>
<%--			fileSizeLimit : '3MB',--%>
<%--			fileTypeExts : '*.gif; *.jpg; *.png',--%>
<%--			fileTypeDesc : '图片'--%>
<%--		});--%>
<%--	});--%>
<%--</script>--%>