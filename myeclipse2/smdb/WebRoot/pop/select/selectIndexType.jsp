<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Person">
		<head>
			<title>选择索引类型</title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/pop/view/view.js', function(view) {
					
				});
				seajs.use('javascript/pop/select/select_index_type.js', function(select) {
					$(function(){
						select.init();
					})
				});
			</script>
		</head>
	
		<body>
			<div style="width:330px;">
				<s:form id="index_type_form">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="20"><input id="check" name="check" type="checkbox" /></td>
							<td>索引类型</td>
							<td>引用次数</td>
							<td class="table_td4" width="80"></td>
						</tr>
						<s:iterator value="indexTypeList" status="stat">
							<tr class="itl">
								<td width="20"><input type="checkbox" name="entityNames" alt="<s:property value='indexTypeList[#stat.index][1]'/>" /></td>
								<td><s:property value="indexTypeList[#stat.index][1]" /></td>
								<td class="level">
									<s:textfield cssStyle="width:50px;" name="indexNumber[%{#stat.index}]" />
								</td>
								<td class="table_td4" width="80"></td>
							</tr>
						</s:iterator>
					</table>
					<s:include value="/pop/select/checkboxBottom.jsp" />
				</s:form>
			</div>
		</body>	
	</s:i18n>
</html>