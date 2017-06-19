<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>中国高校社会科学管理数据库</title>
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
							<li><img src="image/top_ico01.png" /><span><a href="login/scenterRight.action" target="main">首页</a></span></li>
						<sec:authorize ifAnyGranted="ROLE_SYSTEM_CONFIG">
							<li><img src="image/top_ico02.png" /><span><a href="system/config/toConfig.action" target="main">设置</a></span></li>
						</sec:authorize>
							<li><img src="image/top_ico03.png" /><span><a href="javascript:void(0);" target="main">帮助</a></span></li>
							<li><img src="image/top_ico04.png" /><span><a href="javascript:void(0);" target="main">关于</a></span></li>
							<li><img src="image/top_ico05.png" /><span><a href="logout">退出</a></span></li>
						</ul>
					</div>
				</div>
			</div>
			
			<div id="center" class="center">
				<div class="left_menu">
	           		<div class="left_menu_bar">
						<ul>
							<li id="menu_bar" class="left_menu_curr1"><a href="#" onclick="menufunction(0);return false;">主菜单</a></li>
							<li id="func_bar" class="left_menu_curr2"><a href="#" onclick="menufunction(1);return false;">导航栏</a></li>
						</ul>
					</div>
					<div id="menu" class="menu_bar">
		            	<ul class="menu">
		            		<sec:authorize ifAnyGranted="ROLE_SECURITY_ROLE_VIEW,ROLE_SECURITY_RIGHT_VIEW,ROLE_SYSTEM_LOG_VIEW,ROLE_SYSTEM_MAIL_VIEW">
								<li id="01" class="menu1"><a href="javascript:void(0);">系统管理</a></li>
								<sec:authorize ifAllGranted="ROLE_SECURITY_ROLE_VIEW">
									<li id="0101" class="menu2_sub" style="display:none;"><a href="role/toList.action?update=1" target="main">系统角色</a></li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_SECURITY_RIGHT_VIEW">
									<li id="0102" class="menu2_sub" style="display:none;"><a href="right/toList.action?update=1" target="main">系统权限</a></li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_SYSTEM_LOG_VIEW">
									<li id="0103" class="menu2_sub" style="display:none;"><a href="log/toList.action?update=1" target="main">系统日志</a></li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_SYSTEM_MAIL_VIEW">
									<li id="0104" class="menu2_sub" style="display:none;"><a href="mail/toList.action?update=1" target="main">系统邮件</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_SYSTEM_MONITOR_VISITOR_VIEW">
									<li id="0105" class="menu2" style="display:none;"><a href="javascript:void(0);">系统监控</a></li>
									<sec:authorize ifAllGranted="ROLE_SYSTEM_MONITOR_VISITOR_VIEW">
										<li id="010501" class="menu3" style="display:none;"><a href="system/monitor/visitor/toList.action?update=1" target="main">访客监控</a></li>
									</sec:authorize>
								</sec:authorize>
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_SECURITY_PASSPORT">
								<li id="04" class="menu1_sub"><a href="passport/toList.action?update=1" target="main">通行证管理</a></li>
							</sec:authorize>
							
							<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_PROVINCE_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_UNIVERSITY_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_DEPARTMENT_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_INSTITUTE_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_MINISTRY_SUB_VIEW,ROLE_SECURITY_ACCOUNT_PROVINCE_SUB_VIEW,ROLE_SECURITY_ACCOUNT_UNIVERSITY_SUB_VIEW,ROLE_SECURITY_ACCOUNT_DEPARTMENT_SUB_VIEW,ROLE_SECURITY_ACCOUNT_INSTITUTE_SUB_VIEW,ROLE_SECURITY_ACCOUNT_EXPERT_VIEW,ROLE_SECURITY_ACCOUNT_TEACHER_VIEW">
								<li id="02" class="menu1"><a href="javascript:void(0);">账号管理</a></li>

								<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_MINISTRY_SUB_VIEW">
									<li id="0201" class="menu2" style="display:none;"><a href="javascript:void(0);">部级管理账号</a></li>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_MAIN_VIEW">
										<li id="020101" class="menu3" style="display:none;"><a href="account/ministry/main/toList.action?update=1" target="main">主账号</a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_SUB_VIEW">
										<li id="020102" class="menu3" style="display:none;"><a href="account/ministry/sub/toList.action?update=1" target="main">子账号</a></li>
									</sec:authorize>
								</sec:authorize>

								<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_PROVINCE_SUB_VIEW">
									<li id="0202" class="menu2" style="display:none;"><a href="javascript:void(0);">省级管理账号</a></li>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_MAIN_VIEW">
										<li id="020201" class="menu3" style="display:none;"><a href="account/province/main/toList.action?update=1" target="main">主账号</a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_SUB_VIEW">
										<li id="020202" class="menu3" style="display:none;"><a href="account/province/sub/toList.action?update=1" target="main">子账号</a></li>
									</sec:authorize>
								</sec:authorize>

								<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_UNIVERSITY_SUB_VIEW">
									<li id="0203" class="menu2" style="display:none;"><a href="javascript:void(0);">校级管理账号</a></li>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_MAIN_VIEW">
										<li id="020301" class="menu3" style="display:none;"><a href="account/university/main/toList.action?update=1" target="main">主账号</a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_SUB_VIEW">
										<li id="020302" class="menu3" style="display:none;"><a href="account/university/sub/toList.action?update=1" target="main">子账号</a></li>
									</sec:authorize>
								</sec:authorize>

								<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_DEPARTMENT_SUB_VIEW">
									<li id="0204" class="menu2" style="display:none;"><a href="javascript:void(0);">院系管理账号</a></li>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_MAIN_VIEW">
										<li id="020401" class="menu3" style="display:none;"><a href="account/department/main/toList.action?update=1" target="main">主账号</a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_SUB_VIEW">
										<li id="020402" class="menu3" style="display:none;"><a href="account/department/sub/toList.action?update=1" target="main">子账号</a></li>
									</sec:authorize>
								</sec:authorize>

								<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_MAIN_VIEW,ROLE_SECURITY_ACCOUNT_INSTITUTE_SUB_VIEW">
									<li id="0205" class="menu2" style="display:none;"><a href="javascript:void(0);">基地管理账号</a></li>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_MAIN_VIEW">
										<li id="020501" class="menu3" style="display:none;"><a href="account/institute/main/toList.action?update=1" target="main">主账号</a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_SUB_VIEW">
										<li id="020502" class="menu3" style="display:none;"><a href="account/institute/sub/toList.action?update=1" target="main">子账号</a></li>
									</sec:authorize>
								</sec:authorize>

								<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_EXPERT_VIEW">
									<li id="0206" class="menu2_sub" style="display:none;"><a href="account/expert/toList.action?update=1" target="main">外部专家账号</a></li>
								</sec:authorize>

								<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_TEACHER_VIEW">
									<li id="0207" class="menu2_sub" style="display:none;"><a href="account/teacher/toList.action?update=1" target="main">教师账号</a></li>
								</sec:authorize>
								
								<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_STUDENT_VIEW">
									<li id="0208" class="menu2_sub" style="display:none;"><a href="account/student/toList.action?update=1" target="main">学生账号</a></li>
								</sec:authorize>
							</sec:authorize>
							
							<sec:authorize ifAnyGranted="ROLE_DATAMANAGEMENT_UNIVERSITY_RENAME_VIEW,ROLE_DATAMANAGEMENT_DUP_CHECK_GENERAL_VIEW,ROLE_DATAMANAGEMENT_DUP_CHECK_INSTP_VIEW">
								<li id="03" class="menu1"><a href="javascript:void(0);">数据管理</a></li>
								
								<sec:authorize ifAnyGranted="ROLE_DATAMANAGEMENT_UNIVERSITY_RENAME_VIEW">
									<li id="0301" class="menu2_sub" style="display:none;"><a href="dm/universityVariation/toList.action?update=1" target="main">高校变更</a></li>
								</sec:authorize>
								
								<sec:authorize ifAnyGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_GENERAL_VIEW,ROLE_DATAMANAGEMENT_DUP_CHECK_INSTP_VIEW">
									<li id="0302" class="menu2" style="display:none;"><a href="javascript:void(0);">查重标记</a></li>
									<sec:authorize ifAnyGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_GENERAL_VIEW">	
										<li id="030201" class="menu2_sub" style="display:none;"><a href="dm/dupCheck/toAddGeneral.action?update=1" target="main">一般项目查重标记</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_INSTP_VIEW">	
										<li id="030202" class="menu2_sub" style="display:none;"><a href="dm/dupCheck/toAddInstp.action?update=1" target="main">基地项目查重标记</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_SPECIAL_VIEW">	
										<li id="030203" class="menu2_sub" style="display:none;"><a href="dm/dupCheck/toAddSpecial.action?update=1" target="main">专项任务项目查重标记</a></li>
									</sec:authorize>
								</sec:authorize>
							</sec:authorize>
							

							<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_APP_RESULT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_RESULT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_VAR_RESULT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_END_RESULT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_RECORD_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_OBTAIN,ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_IMPORTER,ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_DOWNLOAD,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_RECORD_EXPORT">
								<li id="06" class="menu1"><a href="javascript:void(0);">接口管理</a></li>
								<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_APP_RESULT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_RESULT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_VAR_RESULT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_END_RESULT_CONFIG,ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_REQUIRED_CONFIG">
									<li id="0601" class="menu2" style="display:none;"><a href="javascript:void(0);">社科网服务端</a></li>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_APP_RESULT_CONFIG">
										<li id="060101" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossServer/toApplicationResultConfig.action" target="main">申请结果接口</a></li>									
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_RESULT_CONFIG">
										<li id="060102" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossServer/toMidinspectionResultConfig.action" target="main">中检结果接口</a></li>									
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_VAR_RESULT_CONFIG">
										<li id="060103" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossServer/toVariationResultConfig.action" target="main">变更结果接口</a></li>									
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_END_RESULT_CONFIG">
										<li id="060104" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossServer/toEndinspectionResultConfig.action" target="main">结项结果接口</a></li>									
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_REQUIRED_CONFIG">
										<li id="060105" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossServer/toMidinspectionRequiredConfig.action" target="main">需中检数据接口</a></li>									
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_REQUIRED_EXPORT">
										<li id="060106" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossServer/toExport.action" target="main">导出</a></li>									
									</sec:authorize>																		
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_OBTAIN,ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_DOWNLOAD,
								ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_IMPORT,ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_XMLDOWNLOAD,ROLE_SYSTEM_CONFIG">
									<li id="0602" class="menu2" style="display:none;"><a href="javascript:void(0);">社科网客户端</a></li>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_OBTAIN">
										<li id="060201" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossClient/toObtain.action" target="main">数据获取</a></li>									
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_IMPORT">
										<li id="060202" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossClient/toImporter.action" target="main">数据入库</a></li>									
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_DOWNLOAD">
										<li id="060203" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossClient/toDownload.action" target="main">附件下载</a></li>																	
									</sec:authorize>									
									<sec:authorize ifAnyGranted="ROLE_INTERFACE_WEBSERVICE_SINOSS_CLIENT_XMLDOWNLOAD">
										<li id="060204" class="menu2_sub" style="display:none;"><a href="system/interfaces/sinossClient/toSearchXml.action" target="main">XML备份下载</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_SYSTEM_CONFIG">
									<li id="0603" class="menu2" style="display:none;"><a href="javascript:void(0);" >文档管理服务系统</a></li>
										<li id="060301" class="menu2_sub" style="display:none;"><a href="system/config/toConfigUplodToDmss.action" target="main">文件上传接口</a></li>
									</sec:authorize>
								</sec:authorize>					
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_DATAPROCESSING_DATAIMPORTER_SINOSS">
								<li id="07" class="menu1"><a href="javascript:void(0);">数据入库</a></li>
								<li id="0702" class="menu2_sub" style="display:none;"><a href="dataProcessing/toConfigView.action" target="main">从数据源到中间表</a></li>
								<li id="0701" class="menu2" style="display:none;"><a href="javascript:void(0);">从中间表到数据库</a></li>
								<sec:authorize ifAnyGranted="ROLE_DATAPROCESSING_DATAIMPORTER_SINOSS">
									<li id="070101" class="menu3" style="display:none;"><a href="dataProcessing/dataImporter/toApplicationView.action" target="main">申请</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_DATAPROCESSING_DATAIMPORTER_SINOSS">
									<li id="070102" class="menu3" style="display:none;"><a href="dataProcessing/dataImporter/toMidinspectionView.action" target="main">中检</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_DATAPROCESSING_DATAIMPORTER_SINOSS">
									<li id="070103" class="menu3" style="display:none;"><a href="dataProcessing/dataImporter/toVariationView.action" target="main">变更</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_DATAPROCESSING_DATAIMPORTER_SINOSS">
									<li id="070104" class="menu3" style="display:none;"><a href="dataProcessing/dataImporter/toEndinspectionView.action" target="main">结项</a></li>
								</sec:authorize>
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_DATAPROCESSING_DATAIMPORTER_SINOSS">
								<li id="08" class="menu1"><a href="javascript:void(0);">任务配置模块</a></li>
								<li id="0801" class="menu2_sub" style="display:none;"><a href="taskConfig/toTaskListView.action" target="main">创建事务</a></li>
								<li id="0802" class="menu2_sub" style="display:none;"><a href="taskConfig/toTaskConfigListView.action" target="main">查看事务状态</a></li>
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_ACCESS_DOC">
								<li id="09" class="menu1"><a href="system/doc/toView.action" target = "main">文档同步</a></li>
							</sec:authorize>
							
							<sec:authorize ifAnyGranted="ROLE_SYSTEM_CONFIG">
								<li id="05" class="menu1_sub"><a href="system/option/toView.action?update=1" target="main">系统选项</a></li>
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_SYSTEM_CONFIG">
								<li id="10" class="menu1_sub"><a href="system/config/toConfig.action" target="main">系统配置</a></li>
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_SYSTEM_CONFIG">
								<li id="11" class="menu1"><a href="javascript:void(0);">业务配置</a></li>
									<li id="1101" class="menu2_sub" style="display:none;"><a href="system/config/toConfigFee.action" target="main">配置经费</a></li>
									<li id="1102" class="menu2_sub" style="display:none;"><a href="system/config/toConfigAward.action" target="main">配置奖励</a></li>
									<li id="1103" class="menu2_sub" style="display:none;"><a href="system/config/toConfigReview.action" target="main">配置评审</a></li>
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_SYSTEM_CONFIG">
								<li id="12" class="menu1"><a href="javascript:void(0);">系统工具</a></li>
									<li id="1201" class="menu2_sub" style="display:none;"><a href="execution/toExecute.action" target="main">执行任务</a></li>
									<li id="1202" class="menu2_sub" style="display:none;"><a href="query/toQuery.action" target="_blank">数据库操作</a></li>
									<li id="1203" class="menu2_sub" style="display:none;"><a href="system/config/toClear2ndCache.action" target="main">清除二级缓存</a></li>
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_SYSTEM_CONFIG">
								<li id="13" class="menu1_sub"><a href="system/search/toTotalSearch.action" target="main">全站检索</a></li>
							</sec:authorize>
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