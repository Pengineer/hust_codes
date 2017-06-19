<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<base href="<%=basePath%>" />
			<title>项目清单</title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="css/project/project.css" />
			<link rel="stylesheet" type="text/css" href="tool/jquery-ui-1.8.16.custom/css/redmond/jquery-ui-1.8.16.custom.css" />
		</head>

		<body>
			<div>
				<div>
					<s:form>
						<div class="base_info" style="margin-top: 0;">
							<div class="title_dis">
								<input type="checkbox" id="deleteMatches" name="deleteMatchesPair"/><span class="pop_span">清空匹配对</span>
							</div>
						</div>
						<div class="base_info_content">
							<input type="checkbox" id="deleteAutoMatches" name="deleteAutoMatches" disabled="disabled"/><span class="pop_span">清空自动匹配对</span></br>
						</div>
						<div class="base_info_content">
							<input type="checkbox" id="deleteManualMatches" name="deleteManualMatches"/><span class="pop_span">清空手动匹配对</span>
						</div>
						<div class="base_info">
							<div class="title_dis">
								<input type="checkbox" name="deleteMatchesVarianHistory" checked="true" disabled="disabled"/><span class="pop_span">清空匹配更新记录</span>
							</div>
						</div>
						<div class="btn_div">
							<input id="confirm" type="button" class="btn" value="确定" />
							<input id="cancel" type="button" class="btn" value="取消" />
						</div>
					</s:form>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/jquery-ui-1.8.16.custom/js/jquery-ui-1.8.16.custom.min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery-ui-timepicker-addon.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/pop/pop_init.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/award/moeSocial/popDeleteMatches.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>