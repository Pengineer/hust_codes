<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title><s:text name="i18n_GeneralProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
<%--			<div style="height:84px;">--%>
			<div>
				<div style="background: none repeat scroll 0 0 #F7F7F7; border: 1px solid #E1E1E1; padding:0 0 0 30px;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr >
								<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_ADD">
									<td style="padding:10px 0 0 0;">
										<input type="radio" id="general" name="projectType" value="01" />
										<span >一般项目</span>
									</td>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_APPLICATION_APPLY_ADD">
									<td style="padding:10px 0 0 30px;">
										<input type="radio" id="instp" name="projectType" value="02"/>
										<span>基地项目</span>
									</td>
								</sec:authorize>
							</tr>
							<tr>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_APPLICATION_APPLY_ADD">
									<td style="padding:10px 0 0 0;">
										<input type="radio" id="key" name="projectType" value="04"/>
										<span>重大攻关项目</span>
									</td>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_APPLICATION_APPLY_ADD">
									<td style="padding:10px 0 0 30px;">
										<input type="radio" id="post" name="projectType" value="03"/>
										<span>后期资助项目</span>
									</td>
								</sec:authorize>
							</tr>
						<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_ADD">
							<tr>
								<td style="padding:10px 0 10px 0;">
									<input type="radio" id="entr" name="projectType" value="05"/>
									<span>委托应急课题</span>
								</td>
								<td style="padding:10px 0 10px 30px;"></td>
							</tr>
						</sec:authorize>
					</table>
					<s:hidden id="genappStatus" name="genappStatus" />
					<s:hidden id="insappStatus" name="insappStatus" />
					<s:hidden id="posappStatus" name="posappStatus" />
					<s:hidden id="keyappStatus" name="keyappStatus" />
					<s:hidden id="entappStatus" name="entappStatus" />
				</div>
				<div class="btn_div_view">
					<input id="apply" class="btn1" type="button" value="<s:text name='确定' />" />
					<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/project/project_share/apply_my_project.js', function(list) {
					list.init();
				});
			</script>
		</body>
</html>