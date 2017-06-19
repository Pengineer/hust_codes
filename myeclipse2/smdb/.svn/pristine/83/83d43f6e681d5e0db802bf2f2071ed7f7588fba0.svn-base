<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Person">
		<head>
			<title><s:text name="i18n_Select" /></title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/pop/select/select_foreign_language.js', function(select) {
					$(function(){
						select.init();
					})
				});
			</script>
		</head>

		<body>
			<div style="width:430px;">
				<div id="header" style="border:1px solid;">
				<%--
					<s:iterator value="foreignLanguageList" status="stat">
						<s:if test="#stat.index == 0 || abbr.charAt(0) != foreignLanguageList[#stat.index - 1].abbr.charAt(0)">
							<a href="#<s:property value='abbr.toUpperCase().charAt(0)' />">
								<s:property value="abbr.toUpperCase().charAt(0)" />
							</a>
						</s:if>
					</s:iterator>
					--%>
						<s:iterator value="foreignLanguageList" status="stat">
						<s:if test="#stat.index == 0 || abbr.charAt(0) != foreignLanguageList[#stat.index - 1].abbr.charAt(0)">
							<a href="" name="<s:property value='abbr.toUpperCase().charAt(0)'/>" class="anchor" >
								<s:property value="abbr.toUpperCase().charAt(0)" />
							</a>
						</s:if>
					</s:iterator>
				</div>
	
				<div style="height:210px;overflow-y:scroll;" id ="foreignScroll">
					<table border="0" width="411px" cellspacing="0" cellpadding="0">
						<s:iterator value="foreignLanguageList" status="stat">
							<s:if test="#stat.index == 0 || abbr.charAt(0) != foreignLanguageList[#stat.index - 1].abbr.charAt(0)">
								<tr>
									<td>
										<a id="<s:property value='abbr.toUpperCase().charAt(0)' />"/>
											<s:property value="abbr.toUpperCase().charAt(0)" /></a>
									</td>
									<td></td>
								</tr>
								<tr>
									<td colspan="2"><hr /></td>
								</tr>
							</s:if>
							<tr class="el">
								<td width="50px"><s:property value="name" /></td>
								<td class="level">
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
				<div style="height:36px;overflow-y:scroll;">
					<span>已选择:</span>
					<s:hidden name="foreignLanguage" />
					<span id="disp"></span>
				</div>
				<s:include value="/pop/select/checkboxBottom.jsp" />
			</div>
		</body>
	</s:i18n>
</html>