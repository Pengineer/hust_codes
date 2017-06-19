<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Person">
		<head>
			<title><s:text name="i18n_Select" /></title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/pop/select/select_discipline_type.js', function(select) {
					$(function(){
						select.init();
					})
				});
			</script>
		</head>
	
		<body>
			<div style="width:432px;">
				<s:hidden name="namespace" value="selectDisciplineType" />
				<s:hidden id="entityIds" name="entityIds" />
				<s:hidden id="entityNames" name="entityNames" />
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
					<s:iterator value="dispTypeList" status="stat">
						<s:if test="(#stat.index)%3==0"><tr height="26px"></s:if>
						<td><s:if test="selectType==1">
						<input type="radio" id="<s:property value="dispTypeList[#stat.index][0]" />" alt="<s:property value="dispTypeList[#stat.index][1]" />" name="dispTypeIds" onclick="selectDispType(this);" value="false" />
						</s:if>
						<s:else>
								<input type="checkbox" id="<s:property value="dispTypeList[#stat.index][0]" />" alt="<s:property value="dispTypeList[#stat.index][1]" />" name="dispTypeIds" onclick="selectDispType(this);" value="false" />
							</s:else>
						</td><td align="left">
						<s:property value="dispTypeList[#stat.index][1]" /></td>
						<s:if test="(#stat.index+1)%3==0"></tr></s:if>
					</s:iterator>
				</table>
				<s:include value="/pop/select/checkboxBottom.jsp" />
			</div>
		</body>
	</s:i18n>
</html>