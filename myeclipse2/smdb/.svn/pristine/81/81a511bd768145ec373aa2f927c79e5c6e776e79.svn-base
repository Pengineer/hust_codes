<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Person">
		<head>
			<title><s:text name="i18n_Select" /></title>
			<link rel="stylesheet" type="text/css" href="tool/tree/css/dhtmlxtree.css" />
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript" src="tool/tree/js/dhtmlxcommon.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/tree/js/dhtmlxtree.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="dwr/interface/unitExtService.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/engine.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="dwr/util.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/lib/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/pop/pop_init_old.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/pop/select/select_rely_discipline.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</head>
	
		<body>
			<div style="width:437px;">
				<s:form theme="simple">
					<div id="expall">
						<s:hidden id="hiddenId" />
						<table border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse; width:100%">
							<tr>
								<td height="30" style="border:solid #999 1px; width:225px;">一级学科：<s:select cssClass="select" cssStyle="width:164px" id="discipline1" name="discipline1" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" list="%{unitExtService.getDisciplineOne()}" onchange="loadDiscpTree(this.value)" /></td>
								<td rowspan ="2" style="vertical-align:top; border:solid #999 1px; width:200px; height:240px;">
									<div id="expList" style="height:270px; overflow:scroll;">
										<table width="190" cellspacing="0" cellpadding="0" class="table_xk">
											<thead>
												<tr style="background-color:rgb(197,176,217);">
													<td width="30">序号</td>
													<td>学科</td>
													<td width="50">学科代码</td>
													<td width="30">删除</td>
												</tr>
											</thead>
											<tbody id="showselect"></tbody>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td style="vertical-align:top; border:solid #999 1px; width:225px; height:240px;">						
									<div id="loading" style="display:none; text-align:center">正在加载学科树，请稍后...</div>
									<div id="treeId" style="display:none; height:240px;"></div>
								</td>
							
							</tr>
						</table>
					</div>
					<s:include value="/pop/select/checkboxBottom.jsp" />
				</s:form>
			</div>
		</body>
	</s:i18n>
</html>