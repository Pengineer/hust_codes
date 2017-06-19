<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="i18n_CSDCSMDB" /></title>
		<s:include value="/innerBase.jsp" />
		<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="tool/poplayer/js/pop.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="tool/poplayer/js/pop-self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/server/main.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
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
						<div class="sub_title_center" id="serverName" alt="basis">基础数据库系统</div>
						<div class="sub_title_right"><img src="image/sub_r.png"/></div>
						<s:include value="/server/switchServer.jsp" />
					</div>
					<div class="top_menu">
						<div class="txt">
							<span id="switch_account" class="passport" alt="<s:property value="#session.loginer.account.id" />"><span class="color1">登录为</span>&nbsp;<img src="image/ico08.gif"></span>
							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT) || #session.loginer.isPrincipal == 0">
								<s:property value="#session.loginer.currentPersonName" />
							</s:if>
							<s:else>
								<s:property value="#session.loginer.currentBelongUnitName" />
							</s:else>&nbsp;[<s:property value="#session.loginer.passport.name" />]&nbsp;&nbsp;<script type="text/javascript">document.write(showLocale());</script>
						</div>
						<ul>
							<li><img src="image/top_ico01.png" /><span><a href="login/basisRight.action" target="main"><s:text name="i18n_Index" /></a></span></li>
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
							<sec:authorize ifAnyGranted="ROLE_INFORMATION_NEWS_VIEW,ROLE_INFORMATION_NOTICE_VIEW,ROLE_INFORMATION_MESSAGE_VIEW,ROLE_INFORMATION_INBOX_VIEW">
								<li id="09" class="menu1"><a href="javascript:void(0);"><s:text name="i18n_InformationRelease" /></a></li>
								<sec:authorize ifAllGranted="ROLE_INFORMATION_NEWS_VIEW">
									<li id="0901" class="menu2_sub" style="display:none;"><a href="news/inner/toList.action?update=1" target="main"><s:text name="i18n_News" /></a></li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_INFORMATION_NOTICE_VIEW">
									<li id="0902" class="menu2_sub" style="display:none;"><a href="notice/inner/toList.action?update=1" target="main"><s:text name="i18n_Notice" /></a></li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_INFORMATION_MESSAGE_VIEW">
									<li id="0903" class="menu2_sub" style="display:none;"><a href="message/inner/toList.action?update=1" target="main"><s:text name="i18n_Message" /></a></li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_INFORMATION_INBOX_VIEW">
									<li id="0904" class="menu2_sub" style="display:none;"><a href="inBox/toList.action?update=1" target="main"><s:text name="i18n_InBox" /></a></li>
								</sec:authorize>
							</sec:authorize>
							
							
							
							
					<!-------------------- 人员 --------------->
							<sec:authorize ifAnyGranted="ROLE_PERSON_OFFICER_MINISTRY_VIEW,ROLE_PERSON_OFFICER_PROVINCE_VIEW,ROLE_PERSON_OFFICER_UNIVERSITY_VIEW,ROLE_PERSON_OFFICER_DEPARTMENT_VIEW,ROLE_PERSON_OFFICER_INSTITUTE_VIEW,ROLE_PERSON_EXPERT_VIEW,ROLE_PERSON_TEACHER_VIEW,ROLE_PERSON_STUDENT_VIEW">
							<li id="03" class="menu1"><a href="javascript:void(0);"><s:text name="i18n_PersonData" /></a></li>
								<sec:authorize ifAnyGranted="ROLE_PERSON_OFFICER_MINISTRY_VIEW,ROLE_PERSON_OFFICER_PROVINCE_VIEW,ROLE_PERSON_OFFICER_UNIVERSITY_VIEW,ROLE_PERSON_OFFICER_DEPARTMENT_VIEW,ROLE_PERSON_OFFICER_INSTITUTE_VIEW">
								<li id="0301" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_Officer" /></a></li>
									<sec:authorize ifAllGranted="ROLE_PERSON_OFFICER_MINISTRY_VIEW">
									<li id="030101" class="menu3" style="display:none;"><a href="person/ministryOfficer/toList.action?update=1&unitType=0" target="main"><s:text name="i18n_MinistryOfficer" /></a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_PERSON_OFFICER_PROVINCE_VIEW">
									<li id="030102" class="menu3" style="display:none;"><a href="person/provinceOfficer/toList.action?update=1&unitType=1" target="main"><s:text name="i18n_ProvinceOfficer" /></a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_PERSON_OFFICER_UNIVERSITY_VIEW">
									<li id="030103" class="menu3" style="display:none;"><a href="person/universityOfficer/toList.action?update=1&unitType=2" target="main"><s:text name="i18n_UniversityOfficer" /></a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_PERSON_OFFICER_DEPARTMENT_VIEW">
									<li id="030104" class="menu3" style="display:none;"><a href="person/departmentOfficer/toList.action?update=1&unitType=3" target="main"><s:text name="i18n_DepartmentOfficer" /></a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_PERSON_OFFICER_INSTITUTE_VIEW">
									<li id="030105" class="menu3" style="display:none;"><a href="person/instituteOfficer/toList.action?update=1&unitType=4" target="main"><s:text name="i18n_InstituteOfficer" /></a></li>
									</sec:authorize>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PERSON_EXPERT_VIEW,ROLE_PERSON_TEACHER_VIEW,ROLE_PERSON_STUDENT_VIEW">
								<li id="0302" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_Researcher" /></a></li>
									<sec:authorize ifAllGranted="ROLE_PERSON_EXPERT_VIEW">
									<li id="030201" class="menu3" style="display:none;"><a href="person/expert/toList.action?update=1" target="main"><s:text name="i18n_Expert" /></a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_PERSON_TEACHER_VIEW">
									<li id="030202" class="menu3" style="display:none;"><a href="person/teacher/toList.action?update=1" target="main"><s:text name="i18n_Teacher" /></a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_PERSON_STUDENT_VIEW">
									<li id="030203" class="menu3" style="display:none;"><a href="person/student/toList.action?update=1" target="main"><s:text name="i18n_Student" /></a></li>
									</sec:authorize>
								</sec:authorize>
							</sec:authorize>
					<!-------------------- 机构 --------------->
							<sec:authorize ifAnyGranted="ROLE_UNIT_MINISTRY_VIEW,ROLE_UNIT_PROVINCE_VIEW,ROLE_UNIT_UNIVERSITY_VIEW,ROLE_UNIT_DEPARTMENT_VIEW,ROLE_UNIT_INSTITUTE_VIEW">
								<li id="04" class="menu1"><a href="javascript:void(0);"><s:text name="i18n_UnitData" /></a></li>
									<li id="0401" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_ManagementUnit" /></a></li>
									<sec:authorize ifAnyGranted="ROLE_UNIT_MINISTRY_VIEW">
										<li id="040101" class="menu3" style="display:none;"><a href="unit/agency/ministry/toList.action?update=1" target="main"><s:text name="i18n_CenterU" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_UNIT_PROVINCE_VIEW">
										<li id="040102" class="menu3" style="display:none;"><a href="unit/agency/province/toList.action?update=1" target="main"><s:text name="i18n_ProvinceU" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_UNIT_UNIVERSITY_VIEW">
										<li id="040103" class="menu3" style="display:none;"><a href="unit/agency/university/toList.action?update=1" target="main"><s:text name="i18n_UniversityU" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_UNIT_DEPARTMENT_VIEW">
										<li id="040104" class="menu3" style="display:none;"><a href="unit/department/toList.action?update=1" target="main"><s:text name="i18n_DepartmentU" /></a></li>
									</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_UNIT_INSTITUTE_VIEW">
									<li id="0402" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_InstituteU" /></a></li>
									<li id="040201" class="menu3" style="display:none;"><a href="unit/institute/toList.action?update=1&instType=1" target="main"><s:text name="i18n_MinistryInstituteU" /></a></li>
									<li id="040202" class="menu3" style="display:none;"><a href="unit/institute/toList.action?update=1&instType=2" target="main"><s:text name="i18n_MinistryProvinceInstituteU" /></a></li>
									<li id="040203" class="menu3" style="display:none;"><a href="unit/institute/toList.action?update=1&instType=3" target="main"><s:text name="i18n_MinistryMinistryInstituteU" /></a></li>
									<li id="040204" class="menu3" style="display:none;"><a href="unit/institute/toList.action?update=1&instType=4" target="main"><s:text name="i18n_MinistryLabU" /></a></li>
									<li id="040205" class="menu3" style="display:none;"><a href="unit/institute/toList.action?update=1&instType=5" target="main"><s:text name="i18n_ProvinceInstituteU" /></a></li>
									<li id="040206" class="menu3" style="display:none;"><a href="unit/institute/toList.action?update=1&instType=6" target="main"><s:text name="i18n_UniversityInstituteU" /></a></li>
									<li id="040207" class="menu3" style="display:none;"><a href="unit/institute/toList.action?update=1&instType=7" target="main"><s:text name="i18n_UniversityEnterpriseInstituteU" /></a></li>
									<li id="040208" class="menu3" style="display:none;"><a href="unit/institute/toList.action?update=1&instType=8" target="main"><s:text name="i18n_OtherInstituteU" /></a></li>
								</sec:authorize>
							</sec:authorize>
							
							
							
							
							
							
							<!---------------------------- 项目管理 ------------------->
							<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_VIEW,ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_GENERAL_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_GENERAL_MIDINSPECTION_APPLY_VIEW,ROLE_PROJECT_GENERAL_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_GENERAL_VARIATION_APPLY_VIEW,
														 ROLE_PROJECT_KEY_APPLICATION_APPLY_VIEW,ROLE_PROJECT_KEY_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_KEY_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_KEY_MIDINSPECTION_APPLY_VIEW,ROLE_PROJECT_KEY_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_KEY_VARIATION_APPLY_VIEW,
														 ROLE_PROJECT_INSTP_APPLICATION_APPLY_VIEW,ROLE_PROJECT_INSTP_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_INSTP_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_INSTP_MIDINSPECTION_APPLY_VIEW,ROLE_PROJECT_INSTP_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_INSTP_VARIATION_APPLY_VIEW,
														 ROLE_PROJECT_POST_APPLICATION_APPLY_VIEW,ROLE_PROJECT_POST_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_POST_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_POST_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_POST_VARIATION_APPLY_VIEW,
														 ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_VIEW,ROLE_PROJECT_ENTRUST_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_ENTRUST_VARIATION_APPLY_VIEW">
								<li id="05" class="menu1"><a href="javascript:void(0);"><s:text name="i18n_ProjectData" /></a></li>
							</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_VIEW,ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_GENERAL_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_GENERAL_MIDINSPECTION_APPLY_VIEW,ROLE_PROJECT_GENERAL_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_GENERAL_VARIATION_APPLY_VIEW">
									<li id="0501" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_GeneralProject" /></a></li>
								</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_VIEW">
										<li id="050101" class="menu3" style="display:none;"><a href="project/general/application/apply/toList.action?update=1" target="main"><s:text name="i18n_ApplyData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_VIEW">
										<li id="050102" class="menu3" style="display:none;"><a href="project/general/application/granted/toList.action?update=1" target="main"><s:text name="i18n_GrantedData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_ANNINSPECTION_APPLY_VIEW">
										<li id="050103" class="menu3" style="display:none;"><a href="project/general/anninspection/apply/toList.action?update=1" target="main"><s:text name="i18n_AnninspectionData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_MIDINSPECTION_APPLY_VIEW">
										<li id="050104" class="menu3" style="display:none;"><a href="project/general/midinspection/apply/toList.action?update=1" target="main"><s:text name="i18n_MidinspectionData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_ENDINSPECTION_APPLY_VIEW">
										<li id="050105" class="menu3" style="display:none;"><a href="project/general/endinspection/apply/toList.action?update=1" target="main"><s:text name="i18n_EndinspectionData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_VARIATION_APPLY_VIEW">
										<li id="050106" class="menu3" style="display:none;"><a href="project/general/variation/apply/toList.action?update=1" target="main"><s:text name="i18n_VariationData" /></a></li>
									</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLY_VIEW,ROLE_PROJECT_KEY_APPLICATION_APPLY_VIEW,ROLE_PROJECT_KEY_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_KEY_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_KEY_MIDINSPECTION_APPLY_VIEW,ROLE_PROJECT_KEY_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_KEY_VARIATION_APPLY_VIEW">
										<li id="0502" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_KeyProject" /></a></li>
								</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLY_VIEW">
										<li id="050201" class="menu3" style="display:none;"><a href="project/key/topicSelection/apply/toList.action?update=1" target="main"><s:text name="i18n_AnnualTopic" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_APPLICATION_APPLY_VIEW">
										<li id="050202" class="menu3" style="display:none;"><a href="project/key/application/apply/toList.action?update=1" target="main"><s:text name="i18n_TenderData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_APPLICATION_GRANTED_VIEW">
										<li id="050203" class="menu3" style="display:none;"><a href="project/key/application/granted/toList.action?update=1" target="main"><s:text name="i18n_SuccessfulBiddingData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_ANNINSPECTION_APPLY_VIEW">
										<li id="050204" class="menu3" style="display:none;"><a href="project/key/anninspection/apply/toList.action?update=1" target="main"><s:text name="i18n_AnninspectionData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_MIDINSPECTION_APPLY_VIEW">
										<li id="050205" class="menu3" style="display:none;"><a href="project/key/midinspection/apply/toList.action?update=1" target="main"><s:text name="i18n_MidinspectionData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_ENDINSPECTION_APPLY_VIEW">
										<li id="050206" class="menu3" style="display:none;"><a href="project/key/endinspection/apply/toList.action?update=1" target="main"><s:text name="i18n_EndinspectionData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_VARIATION_APPLY_VIEW">
										<li id="050207" class="menu3" style="display:none;"><a href="project/key/variation/apply/toList.action?update=1" target="main"><s:text name="i18n_VariationData" /></a></li>
									</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_APPLICATION_APPLY_VIEW,ROLE_PROJECT_INSTP_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_INSTP_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_INSTP_MIDINSPECTION_APPLY_VIEW,ROLE_PROJECT_INSTP_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_INSTP_VARIATION_APPLY_VIEW,ROLE_PROJECT_INSTP_ANNINSPECTION_APPLY_VIEW">
									<li id="0503" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_InstpProject" /></a></li>
								</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_APPLICATION_APPLY_VIEW">
										<li id="050301" class="menu3" style="display:none;"><a href="project/instp/application/apply/toList.action?update=1" target="main"><s:text name="i18n_ApplyData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_APPLICATION_GRANTED_VIEW">
										<li id="050302" class="menu3" style="display:none;"><a href="project/instp/application/granted/toList.action?update=1" target="main"><s:text name="i18n_GrantedData" /></a></li>
									</sec:authorize >
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_ANNINSPECTION_APPLY_VIEW">
										<li id="050303" class="menu3" style="display:none;"><a href="project/instp/anninspection/apply/toList.action?update=1" target="main"><s:text name="i18n_AnninspectionData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_MIDINSPECTION_APPLY_VIEW">
										<li id="050304" class="menu3" style="display:none;"><a href="project/instp/midinspection/apply/toList.action?update=1" target="main"><s:text name="i18n_MidinspectionData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_ENDINSPECTION_APPLY_VIEW">
										<li id="050305" class="menu3" style="display:none;"><a href="project/instp/endinspection/apply/toList.action?update=1" target="main"><s:text name="i18n_EndinspectionData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_VARIATION_APPLY_VIEW">
										<li id="050306" class="menu3" style="display:none;"><a href="project/instp/variation/apply/toList.action?update=1" target="main"><s:text name="i18n_VariationData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_APPLICATION_APPLY_VIEW,ROLE_PROJECT_POST_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_POST_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_POST_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_POST_VARIATION_APPLY_VIEW">
										<li id="0504" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_PostProject" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_APPLICATION_APPLY_VIEW">
										<li id="050401" class="menu3" style="display:none;"><a href="project/post/application/apply/toList.action?update=1" target="main"><s:text name="i18n_ApplyData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_APPLICATION_GRANTED_VIEW">
										<li id="050402" class="menu3" style="display:none;"><a href="project/post/application/granted/toList.action?update=1" target="main"><s:text name="i18n_GrantedData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_ANNINSPECTION_APPLY_VIEW">
										<li id="050403" class="menu3" style="display:none;"><a href="project/post/anninspection/apply/toList.action?update=1" target="main"><s:text name="i18n_AnninspectionData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_ENDINSPECTION_APPLY_VIEW">
										<li id="050404" class="menu3" style="display:none;"><a href="project/post/endinspection/apply/toList.action?update=1" target="main"><s:text name="i18n_EndinspectionData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_VARIATION_APPLY_VIEW">
										<li id="050405" class="menu3" style="display:none;"><a href="project/post/variation/apply/toList.action?update=1" target="main"><s:text name="i18n_VariationData" /></a></li>
									</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_VIEW,ROLE_PROJECT_ENTRUST_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_ENTRUST_VARIATION_APPLY_VIEW">
									<li id="0505" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_EntrustSubProject" /></a></li>
								</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_VIEW">
										<li id="050501" class="menu3" style="display:none;"><a href="project/entrust/application/apply/toList.action?update=1" target="main"><s:text name="i18n_ApplyData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_APPLICATION_GRANTED_VIEW">
										<li id="050502" class="menu3" style="display:none;"><a href="project/entrust/application/granted/toList.action?update=1" target="main"><s:text name="i18n_GrantedData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_VIEW">	
										<li id="050503" class="menu3" style="display:none;"><a href="project/entrust/endinspection/apply/toList.action?update=1" target="main"><s:text name="i18n_EndinspectionData" /></a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_VARIATION_APPLY_VIEW">
										<li id="050504" class="menu3" style="display:none;"><a href="project/entrust/variation/apply/toList.action?update=1" target="main"><s:text name="i18n_VariationData" /></a></li>
									</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_PRODUCT_VIEW">
								<li id="06" class="menu1"><a href="javascript:void(0);"><s:text name="i18n_ProductData" /></a></li>
								<li id="0601" class="menu2_sub" style="display:none;"><a href="product/paper/toList.action?update=1" target="main"><s:text name="i18n_Paper" /></a></li>
								<li id="0602" class="menu2_sub" style="display:none;"><a href="product/book/toList.action?update=1" target="main"><s:text name="i18n_Book" /></a></li>
								<li id="0603" class="menu2_sub" style="display:none;"><a href="product/consultation/toList.action?update=1" target="main"><s:text name="i18n_Consultation" /></a></li>
								<li id="0604" class="menu2_sub" style="display:none;"><a href="product/electronic/toList.action?update=1" target="main"><s:text name="i18n_Electronic" /></a></li>
								<li id="0605" class="menu2_sub" style="display:none;"><a href="product/patent/toList.action?update=1" target="main"><s:text name="i18n_Patent" /></a></li>
								<li id="0606" class="menu2_sub" style="display:none;"><a href="product/otherProduct/toList.action?update=1" target="main"><s:text name="i18n_OtherProduct" /></a></li>
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_VIEW,ROLE_AWARD_MOESOCIAL_APPLICATION_PUBLICITY_VIEW,ROLE_AWARD_MOESOCIAL_APPLICATION_AWARDED_VIEW"> 
								<li id="07" class="menu1"><a href="javascript:void(0);"><s:text name="i18n_AwardData" /></a></li>
								<li id="0701" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_Award" /></a></li>
								<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_VIEW"> 
									<li id="070101" class="menu3" style="display:none;"><a href="award/moesocial/application/apply/toList.action?update=1&listflag=1" target="main"><s:text name="i18n_ApplyData" /></a></li>
								</sec:authorize> 
								<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_PUBLICITY_VIEW">
									<li id="070102" class="menu3" style="display:none;"><a href="award/moesocial/application/publicity/toList.action?update=1&listflag=2" target="main"><s:text name="i18n_PublicityData" /></a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_AWARDED_VIEW">
									<li id="070103" class="menu3" style="display:none;"><a href="award/moesocial/application/awarded/toList.action?update=1&listflag=3" target="main"><s:text name="i18n_AwardedData" /></a></li>
								</sec:authorize>
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_OTHER_NSFC_VIEW,ROLE_OTHER_NSSF_VIEW"> 
								<li id="08" class="menu1"><a href="javascript:void(0);"><s:text name="i18n_OtherData" /></a></li>
								<sec:authorize ifAnyGranted="ROLE_OTHER_NSFC_VIEW"> 
									<li id="0801" class="menu2_sub" style="display:none;"><a href="other/nsfc/granted/toList.action?update=1" target="main"><s:text name="i18n_Nsfc" /></a></li>
								</sec:authorize> 
								<sec:authorize ifAnyGranted="ROLE_OTHER_NSSF_VIEW"> 
									<li id="0802" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_Nssf" /></a></li>
									<li id="080201" class="menu3" style="display:none;"><a href="other/nssf/application/toList.action?update=1" target="main"><s:text name="i18n_ApplyData" /></a></li>
									<li id="080202" class="menu3" style="display:none;"><a href="other/nssf/granted/toList.action?update=1" target="main"><s:text name="i18n_GrantedData" /></a></li>
								</sec:authorize>
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_BUSINESS_VIEW">
								<li id="10" class="menu1_sub"><a href="business/toList.action?update=1" target="main"><s:text name="i18n_BusinessManagement" /></a></li>
							</sec:authorize>
						</ul>
					</div>
					<s:include value="/auxiliary/basicAuxiliary/historyAccess.jsp"></s:include>
				</div>
				<s:include value="/server/expandIco.jsp" />
				<s:include value="/server/loadIco.jsp" />
				<div class="right">
					<iframe id="main" width="100%" name="main" src="login/basisRight.action" frameborder="0" marginheight="0" marginwidth="0" scrolling="no" ></iframe>
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
