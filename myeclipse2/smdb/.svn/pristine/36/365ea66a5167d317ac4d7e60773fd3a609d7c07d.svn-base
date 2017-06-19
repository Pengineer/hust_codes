<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Person">
		<head>
			<title><s:text name="i18n_Select" /></title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/pop/select/select_ethnic_language.js', function(select) {
					$(function(){
						select.init();
					})
				});
			</script>
		</head>
	
		<body>
			<div style="width:430px;">
				<div style="height:230px;overflow-y:scroll;">
					<table border="0" cellspacing="0" width="411px" cellpadding="0">
						<tr>
							<td colspan="2"><hr /></td>
						</tr>
						<s:iterator value="ethnicLanguageList">
							<tr class="el">
								<td width="50px"><s:property value="name" /></td>
								<td class="level" >
									<span class="xiaobai selected">不会</span>
									<span class="soso">一般</span>
									<span class="skilled">熟练</span>
									<span class="master">精通</span>
								</td>
							</tr>
						</s:iterator>
						<tr>
							<td colspan="2"><hr /></td>
						</tr>
					</table>
				</div>
				<div style="height:36px; overflow-y:scroll; overflow-x:hidden;">
					<span>已选择:</span>
					<s:hidden name="ethnicLanguage" />
					<span id="disp"></span>
				</div>
				<s:include value="/pop/select/checkboxBottom.jsp" />
			</div>
		</body>
	</s:i18n>
</html>