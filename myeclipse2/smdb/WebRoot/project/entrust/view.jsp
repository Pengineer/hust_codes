<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_EntrustSubProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar" id="view_project_entrust">
				<s:if test="listType==1">
					<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_ProjectData" />&nbsp;&gt;&nbsp;<s:text name="i18n_EntrustSubProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_ApplyData" />&nbsp;&gt;&nbsp;<s:text name="i18n_View" />
				</s:if>
				<s:elseif test="listType==2">
					<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_ProjectData" />&nbsp;&gt;&nbsp;<s:text name="i18n_EntrustSubProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_GrantedData" />&nbsp;&gt;&nbsp;<s:text name="i18n_View" />
				</s:elseif>
				<s:elseif test="listType==4">
					<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_ProjectData" />&nbsp;&gt;&nbsp;<s:text name="i18n_EntrustSubProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_EndinspectionData" />&nbsp;&gt;&nbsp;<s:text name="i18n_View" />
				</s:elseif>
				<s:elseif test="listType==5">
					<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_ProjectData" />&nbsp;&gt;&nbsp;<s:text name="i18n_EntrustSubProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_VariationData" />&nbsp;&gt;&nbsp;<s:text name="i18n_View" />
				</s:elseif>
				<s:elseif test="listType==6">
					<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_ReviewProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_EntrustSubProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_View" />
				</s:elseif>
				<s:elseif test="listType==7">
					<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_MyProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_View" />
				</s:elseif>
				<s:elseif test="listType==8">
					<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_ReviewProject1" />&nbsp;&gt;&nbsp;<s:text name="i18n_EntrustSubProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_EndinspectionData" />&nbsp;&gt;&nbsp;<s:text name="i18n_View" />
				</s:elseif>
				<s:elseif test="listType==9">
					<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_MyProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_View" />
				</s:elseif>
				<s:elseif test="listType==10">
					<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_ReviewProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_EntrustSubProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_ApplyData" />&nbsp;&gt;&nbsp;<s:text name="i18n_View" />
				</s:elseif>
			</div>

			<div class="main">
				<s:hidden id="project_type" value="5"/>
				<s:hidden id="entityId" name="entityId" value="%{entityId}" />
				<s:hidden id="update" name="update"/>
				<s:hidden id="uploadKey" name="uploadKey" value="%{#session.uploadKey}" />
				<s:include value="/project/entrust/viewButton.jsp"></s:include>
				<s:form id="view_entrust" theme="simple" namespace="/project/entrust">
					<s:hidden id="entityId" name="entityId" />
					<s:hidden id="projectid" name="projectid" />
					<s:hidden id="selectedTab" name="selectedTab" />
					<s:hidden id="pageNumber" name="pageNumber" />
					<s:hidden id="listType" name="listType" />
					<s:hidden id="projectType" name="projectType" />
					<s:hidden id="varStatus" name="varStatus"/>
					<s:hidden id="appPassAlready" />
					<s:hidden id="endPassAlready" />
					<s:hidden id="endPending" />
					<s:hidden id="varPending"/>
					<s:hidden id="accountType" name="accountType" value="%{#session.loginer.currentType}"/>
				</s:form>
				<textarea id="view_base_template" style="display:none;">
					<div class="main_content">
						<s:include value="/project/entrust/viewBasic.jsp" />
					</div>
				</textarea>

				<div id="view_base" style="display:none; clear:both;"></div>


				<div class="main_content" style="display:none;" id="tabcontent">
					<div id="tabs" class="p_box_bar">
						<ul>
							<li><a href="#application"><s:text name="i18n_ApplyInfo" /></a></li>
							<li class="un_granted"><a href="#granted"><s:text name="i18n_GrantedInfo" /></a></li>
							<li><a href="#member"><s:text name="i18n_RelatedMember" /></a></li>
							<li class="un_granted"><a href="#funding"><s:text name="i18n_FundInfo" /></a></li>
							<li class="un_granted"><a href="#endinspection"><s:text name="i18n_EndInfo" /></a></li>
							<li class="un_granted"><a href="#variation"><s:text name="i18n_VarInfo" /></a></li>
							<li class="un_granted"><a href="#product"><s:text name="i18n_RelatedProd" /></a></li>
						</ul>
					</div>

					<div class="p_box">
						<div id="application">
							<s:include value="/project/entrust/viewApplyTab.jsp"/>
						</div>
						<div id="granted">
							<s:include value="/project/entrust/viewGrantedTab.jsp"/>
						</div>
						<div id="member">
							<s:include value="/project/entrust/viewMemberTab.jsp"/>
						</div>
						<div id="funding">
							<s:include value="/project/entrust/viewFundingTab.jsp" />
						</div>
						<div id="endinspection">
							<s:include value="/project/entrust/endinspection/apply/view.jsp"/>
						</div>
						<div id="variation">
							<s:include value="/project/entrust/variation/apply/view.jsp"/>
						</div>
						<div id="product">
							<s:include value="/product/extIf/viewRelated.jsp"/>
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/entrust/view.js',
				           'javascript/project/entrust/application/apply/view.js',
				           'javascript/project/entrust/application/granted/popSetUpProjectStatus.js',
				           'javascript/project/entrust/application/review/view.js',
				           'javascript/project/entrust/variation/view.js',
				           'javascript/project/entrust/endinspection/apply/view.js',
				           'javascript/project/entrust/endinspection/review/view.js'], 
				           function(view, app, granted, appRev, vari, endi, endRev) {
								$(document).ready(function() {
									view.init();
									app.initClick();
									granted.init();
									appRev.initClick();
									vari.initClick();
									endi.initClick();
									endRev.initClick();
								})
				});
			</script>
		</body>
		
	</s:i18n>
</html>