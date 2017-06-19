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
		<title>添加专家</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" type="text/css" href="css/global.css" />
		<link rel="stylesheet" type="text/css" href="css/expert/expert.css" />
	</head>

	<body>
		<div style="width:700px;overflow-y:scroll; overflow-x:hidden;" >
			<s:form id="form_expert" action="popAdd" theme="simple" namespace="/expert/select" >
				<s:include value="/validateError.jsp" />
				<table class="table_edit" cellspacing="0" cellpadding="0">
					<tr><th class="thhead" colspan="4">专家信息</th></tr>
					<tr><td class="wd14"><span class="table_title">姓名</span></td>
						<td colspan="3">
							<table style="width: 100%">
								<tr>
									<td class="wd16" style="border:0;"><s:textfield theme="simple" name="expert.name" cssStyle="width:99%" /></td>
									<td class="td_input" style="border:0;"></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="wd14"><span class="table_title">移动电话</span></td>
						<td colspan="3">
							<table style="width: 100%">
								<tr>
									<td class="wd16" style="border:0;"><s:textfield theme="simple" name="expert.mobilePhone" cssStyle="width:99%" /></td>
									<td class="td_input" style="border:0;"></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="wd14"><span class="table_title">邮箱</span></td>
						<td colspan="3">
							<table style="width: 100%">
								<tr>
									<td class="wd16" style="border:0;"><s:textfield theme="simple" name="expert.email" cssStyle="width:99%" /></td>
									<td class="td_input" style="border:0;"></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="wd14"><span class="table_title">高校</span></td>
						<td colspan="3">
							<table>
								<tr>
									<td class="wd16" style="border:0;">
										<s:select name="expert.universityCode" headerKey="-1" headerValue="请选择" list="#application.univCodeNameMap" ></s:select>
									</td>
									<td class="td_input" style="border:0;"></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="wd14"><span class="table_title">学科代码</span></td>
						<td colspan="3">
							<table>
								<tr>
									<td class="wd16" style="border:0;">
										<input type="button" class="btn" id="selectDiscipline" value="选择" style="width: 58px;" />
										<s:hidden id="discipline" name="expert.discipline" value="%{expert.discipline}" />
										<div id="choosed" class="choose_show" style="display:inline" ></div>
									</td>
									<td class="td_input" style="border:0;"></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="wd14">备注</td>
						<td>
							<table>
								<tr>
									<td class="wd16" style="height:62px; border:0;">
										<s:textarea name="expert.remark" cssStyle="height:47px; width:300px;" cssClass="textarea" /></td>
									<td class="td_input" style="border:0;"></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<div class="btn_div">
					<input id="submit" type="button" class="btn" value="提交" />
					<input type="button" class="cancel btn" value="取消" />
				</div>
			</s:form>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/expert/expert_validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/pop/pop_init.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/expert/select/popAdd.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>