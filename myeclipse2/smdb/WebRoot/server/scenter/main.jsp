<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="i18n_CSDCSMDB" /></title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" href="tool/ztree/css/zTreeStyle.css" type="text/css" />
		<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="tool/poplayer/js/pop.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="tool/poplayer/js/pop-self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/server/main.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="tool/ztree/js/jquery.ztree.core-3.3.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="tool/ztree/js/jquery.ztree.excheck-3.3.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<style type="text/css" media="screen">
			table td,th {text-align:center;padding:5px;}
		</style>
	</head>

	<body>
		<div class="container">
			<div class="top">
				<div class="top_bar">
					<div class="top_logo">
						<img class="ico" src="image/logo_ico.png" />
						<img class="txt" src="image/logo_txt.png" />
					</div>
					<div id="switch_database" class="sub_title">
						<div class="sub_title_left"><img src="image/sub_l.png"/></div>
						<div class="sub_title_center" id="serverName" alt="scenter">系统管控中心</div>
						<div class="sub_title_right"><img src="image/sub_r.png"/></div>
						<s:include value="/server/switchServer.jsp" />
					</div>
					<div class="top_menu">
						<div class="txt">
							<span id="switch_account" class="passport" alt="<s:property value="#session.loginer.account.id" />"><span class="color1">登录为</span>&nbsp;<img src="image/ico08.gif"></span>
							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT , @csdc.tool.bean.AccountType@STUDENT) || #session.loginer.isPrincipal == 0">
								<s:property value="#session.loginer.currentPersonName" />
							</s:if>
							<s:else>
								<s:property value="#session.loginer.currentBelongUnitName" />
							</s:else>&nbsp;[<s:property value="#session.loginer.passport.name" />]&nbsp;&nbsp;<script type="text/javascript">document.write(showLocale());</script>
						</div>
						<ul>
							<li><img src="image/top_ico01.png" /><span><a href="login/scenterRight.action" target="main"><s:text name="i18n_Index" /></a></span></li>
						<sec:authorize ifAnyGranted="ROLE_SYSTEM_CONFIG">
							<li><img src="image/top_ico02.png" /><span><a href="system/config/toConfig.action" target="main"><s:text name="i18n_Config" /></a></span></li>
						</sec:authorize>
							<li><img src="image/top_ico03.png" /><span><a href="javascript:void(0);" target="main"><s:text name="i18n_Help" /></a></span></li>
							<li><img src="image/top_ico04.png" /><span><a href="javascript:void(0);" target="main"><s:text name="i18n_About" /></a></span></li>
							<li><img src="image/top_ico05.png" /><span><a href="logout"><s:text name="i18n_Exit" /></a></span></li>
						</ul>
					</div>
				</div>
			</div>
			
			<div id="center" class="center">
				<div class="left_menu">
	           		<div class="left_menu_bar">
						<ul>
							<li id="menu_bar" class="left_menu_curr1"><a href="#" onclick="menufunction(0);return false;"><s:text name="i18n_MainMenu" /></a></li>
							<li id="func_bar" class="left_menu_curr2"><a href="#" onclick="menufunction(1);return false;"><s:text name="i18n_NavigationBar" /></a></li>
						</ul>
					</div>
					<div id="menu" class="menu_bar">
		            	<ul class="menu">
		            		<sec:authorize ifAnyGranted="ROLE_SECURITY_ROLE_VIEW,ROLE_SECURITY_RIGHT_VIEW,ROLE_SYSTEM_LOG_VIEW,ROLE_SYSTEM_MAIL_VIEW">
								<li id="01" class="menu1"><a href="javascript:void(0);"><s:text name="i18n_SystemManagement" /></a></li>
								<sec:authorize ifAllGranted="ROLE_SECURITY_ROLE_VIEW">
									<li id="0101" class="menu2_sub" style="display:none;"><a href="role/toList.action?update=1" target="main"><s:text name="i18n_SystemRole" /></a></li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_SECURITY_RIGHT_VIEW">
									<li id="0102" class="menu2_sub" style="display:none;"><a href="right/toList.action?update=1" target="main"><s:text name="i18n_SystemRight" /></a></li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_SYSTEM_LOG_VIEW">
									<li id="0103" class="menu2_sub" style="display:none;"><a href="log/toList.action?update=1" target="main"><s:text name="i18n_SystemLog" /></a></li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_SYSTEM_MAIL_VIEW">
									<li id="0104" class="menu2_sub" style="display:none;"><a href="mail/toList.action?update=1" target="main"><s:text name="i18n_SystemMail" /></a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_SYSTEM_MONITOR_VISITOR_VIEW">
									<li id="0105" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_SystemMonitor" /></a></li>
									<sec:authorize ifAllGranted="ROLE_SYSTEM_MONITOR_VISITOR_VIEW">
										<li id="010501" class="menu3" style="display:none;"><a href="system/monitor/visitor/toList.action?update=1" target="main"><s:text name="i18n_VisitorMonitor" /></a></li>
									</sec:authorize>
								</sec:authorize>
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_SECURITY_PASSPORT">
								<li id="04" class="menu1_sub"><a href="passport/toList.action?update=1" target="main"><s:text name="i18n_PassportManagement" /></a></li>
							</sec:authorize>
							
							<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_PROVINCE_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_UNIVERSITY_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_DEPARTMENT_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_INSTITUTE_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_MINISTRY_SUB_VIEW,ROLE_SECURITY_ACCOUNT_PROVINCE_SUB_VIEW,ROLE_SECURITY_ACCOUNT_UNIVERSITY_SUB_VIEW,ROLE_SECURITY_ACCOUNT_DEPARTMENT_SUB_VIEW,ROLE_SECURITY_ACCOUNT_INSTITUTE_SUB_VIEW,ROLE_SECURITY_ACCOUNT_EXPERT_VIEW,ROLE_SECURITY_ACCOUNT_TEACHER_VIEW">
								<li id="02" class="menu1"><a href="javascript:void(0);"><s:text name="i18n_AccountManagement" /></a></li>

								<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_MINISTRY_SUB_VIEW">
									<li id="0201" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_MinistryAccount" /></a></li>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_MAIN_VIEW">
										<li id="020101" class="menu3" style="display:none;"><a href="account/ministry/main/toList.action?update=1" target="main"><s:text name="i18n_MainAccount" /></a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_SUB_VIEW">
										<li id="020102" class="menu3" style="display:none;"><a href="account/ministry/sub/toList.action?update=1" target="main"><s:text name="i18n_SubAccount" /></a></li>
									</sec:authorize>
								</sec:authorize>

								<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_PROVINCE_SUB_VIEW">
									<li id="0202" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_ProvinceAccount" /></a></li>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_MAIN_VIEW">
										<li id="020201" class="menu3" style="display:none;"><a href="account/province/main/toList.action?update=1" target="main"><s:text name="i18n_MainAccount" /></a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_SUB_VIEW">
										<li id="020202" class="menu3" style="display:none;"><a href="account/province/sub/toList.action?update=1" target="main"><s:text name="i18n_SubAccount" /></a></li>
									</sec:authorize>
								</sec:authorize>

								<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_UNIVERSITY_SUB_VIEW">
									<li id="0203" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_UniversityAccount" /></a></li>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_MAIN_VIEW">
										<li id="020301" class="menu3" style="display:none;"><a href="account/university/main/toList.action?update=1" target="main"><s:text name="i18n_MainAccount" /></a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_SUB_VIEW">
										<li id="020302" class="menu3" style="display:none;"><a href="account/university/sub/toList.action?update=1" target="main"><s:text name="i18n_SubAccount" /></a></li>
									</sec:authorize>
								</sec:authorize>

								<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_DEPARTMENT_SUB_VIEW">
									<li id="0204" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_DepartmentAccount" /></a></li>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_MAIN_VIEW">
										<li id="020401" class="menu3" style="display:none;"><a href="account/department/main/toList.action?update=1" target="main"><s:text name="i18n_MainAccount" /></a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_SUB_VIEW">
										<li id="020402" class="menu3" style="display:none;"><a href="account/department/sub/toList.action?update=1" target="main"><s:text name="i18n_SubAccount" /></a></li>
									</sec:authorize>
								</sec:authorize>

								<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_INSTITUTE_SUB_VIEW">
									<li id="0205" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_InstituteAccount" /></a></li>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_MAIN_VIEW">
										<li id="020501" class="menu3" style="display:none;"><a href="account/institute/main/toList.action?update=1" target="main"><s:text name="i18n_MainAccount" /></a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_SUB_VIEW">
										<li id="020502" class="menu3" style="display:none;"><a href="account/institute/sub/toList.action?update=1" target="main"><s:text name="i18n_SubAccount" /></a></li>
									</sec:authorize>
								</sec:authorize>

								<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_EXPERT_VIEW">
									<li id="0206" class="menu2_sub" style="display:none;"><a href="account/expert/toList.action?update=1" target="main"><s:text name="i18n_ExpertAccount" /></a></li>
								</sec:authorize>

								<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_TEACHER_VIEW">
									<li id="0207" class="menu2_sub" style="display:none;"><a href="account/teacher/toList.action?update=1" target="main"><s:text name="i18n_TeacherAccount" /></a></li>
								</sec:authorize>
								
								<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_STUDENT_VIEW">
									<li id="0208" class="menu2_sub" style="display:none;"><a href="account/student/toList.action?update=1" target="main"><s:text name="i18n_StudentAccount" /></a></li>
								</sec:authorize>
							</sec:authorize>
							
							<sec:authorize ifAnyGranted="ROLE_DATAMANAGEMENT_UNIVERSITY_RENAME_VIEW,ROLE_DATAMANAGEMENT_DUP_CHECK_GENERAL_VIEW,ROLE_DATAMANAGEMENT_DUP_CHECK_INSTP_VIEW">
								<li id="03" class="menu1"><a href="javascript:void(0);"><s:text name="i18n_DataManagement" /></a></li>
								
								<sec:authorize ifAnyGranted="ROLE_DATAMANAGEMENT_UNIVERSITY_RENAME_VIEW">
									<li id="0301" class="menu2_sub" style="display:none;"><a href="dm/universityVariation/toList.action?update=1" target="main"><s:text name="i18n_UniversityVariation" /></a></li>
								</sec:authorize>
								
								<sec:authorize ifAnyGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_GENERAL_VIEW,ROLE_DATAMANAGEMENT_DUP_CHECK_INSTP_VIEW">
									<li id="0302" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_DupCheck" /></a></li>
									<sec:authorize ifAnyGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_GENERAL_VIEW">	
										<li id="030201" class="menu2_sub" style="display:none;"><a href="dm/dupCheck/toAddGeneral.action?update=1" target="main"><s:text name="i18n_DupCheckGeneral" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_INSTP_VIEW">	
										<li id="030202" class="menu2_sub" style="display:none;"><a href="dm/dupCheck/toAddInstp.action?update=1" target="main"><s:text name="i18n_DupCheckInstp" /></a></li>
									</sec:authorize>
								</sec:authorize>
							</sec:authorize>
							

							<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_APP_RESULT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_RESULT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_VAR_RESULT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_END_RESULT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_REQUIRED_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_OBTAIN,ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_IMPORTER,ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_DOWNLOAD,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_REQUIRED_EXPORT">
								<li id="06" class="menu1"><a href="javascript:void(0);"><s:text name="i18n_InterfaceConfig" /></a></li>
								<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_APP_RESULT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_RESULT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_VAR_RESULT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_END_RESULT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_REQUIRED_CONFIG">
									<li id="0601" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_SinossServer" /></a></li>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_APP_RESULT_CONFIG">
										<li id="060101" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossServer/toApplicationResultConfig.action" target="main"><s:text name="i18n_AppResultsInterface" /></a></li>									
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_RESULT_CONFIG">
										<li id="060102" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossServer/toMidinspectionResultConfig.action" target="main"><s:text name="i18n_MidResultsInterface" /></a></li>									
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_VAR_RESULT_CONFIG">
										<li id="060103" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossServer/toVariationResultConfig.action" target="main"><s:text name="i18n_VarResultsInterface" /></a></li>									
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_END_RESULT_CONFIG">
										<li id="060104" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossServer/toEndinspectionResultConfig.action" target="main"><s:text name="i18n_EndResultsInterface" /></a></li>									
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_REQUIRED_CONFIG">
										<li id="060105" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossServer/toMidinspectionRequiredConfig.action" target="main"><s:text name="i18n_MidRequiredInterface" /></a></li>									
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_REQUIRED_EXPORT">
										<li id="060106" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossServer/toExport.action" target="main"><s:text name="导出" /></a></li>									
									</sec:authorize>																		
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_ACCESS,ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_IMPORT">
									<li id="0602" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_SinossClient" /></a></li>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_ACCESS,ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_CONFIG">
										<li id="060201" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossClient/toObtain.action" target="main"><s:text name="数据获取" /></a></li>									
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_IMPORT">
										<li id="060202" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossClient/toImporter.action" target="main"><s:text name="数据入库" /></a></li>									
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_ACCESS,ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_CONFIG">
										<li id="060203" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossClient/toDownload.action" target="main"><s:text name="附件下载" /></a></li>																	
									</sec:authorize>									
								</sec:authorize>					
							</sec:authorize>
							
							<sec:authorize ifAnyGranted="ROLE_SYSTEM_CONFIG">
								<li id="05" class="menu1_sub"><a href="system/option/toView.action?update=1" target="main"><s:text name="i18n_SystemOption" /></a></li>
							</sec:authorize>
						</ul>
						<ul>
						</ul>
					<div  id="0501" style="width:197px; overflow:auto; float:left" >
						<li id="system_option_tree" class="ztree" ></li>
					</div>
					</div>
					<s:include value="/auxiliary/basicAuxiliary/historyAccess.jsp"></s:include>
				</div>
				<s:include value="/server/expandIco.jsp" />
				<s:include value="/server/loadIco.jsp" />
			    <div class="right">
			    	<iframe id="main" width="100%" name="main" src="login/scenterRight.action" frameborder="0" marginheight="0" marginwidth="0" scrolling="no"></iframe>
			    </div>
			</div>
			
			<s:include value="/innerFoot.jsp" />
		</div>
		<div id="gotop"></div>
		<div style="display:none">
			<script src="http://s6.cnzz.com/stat.php?id=4360704&web_id=4360704&show=pic" language="JavaScript"></script>
		<div>
	</body>
</html>