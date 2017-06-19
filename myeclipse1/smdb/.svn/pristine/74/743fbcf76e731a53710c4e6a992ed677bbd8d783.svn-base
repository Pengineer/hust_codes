<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>一般项目</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar" id="view_project_general">
				<s:if test="listType==1">
					当前位置：社科项目数据&nbsp;&gt;&nbsp;一般项目&nbsp;&gt;&nbsp;申请数据&nbsp;&gt;&nbsp;查看
				</s:if>
				<s:elseif test="listType==2">
					当前位置：社科项目数据&nbsp;&gt;&nbsp;一般项目&nbsp;&gt;&nbsp;立项数据&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==3">
					当前位置：社科项目数据&nbsp;&gt;&nbsp;一般项目&nbsp;&gt;&nbsp;中检数据&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==4">
					当前位置：社科项目数据&nbsp;&gt;&nbsp;一般项目&nbsp;&gt;&nbsp;结项数据&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==5">
					当前位置：社科项目数据&nbsp;&gt;&nbsp;一般项目&nbsp;&gt;&nbsp;变更数据&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==6">
					当前位置：评审项目&nbsp;&gt;&nbsp;一般项目&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==7">
					当前位置：我的项目&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==8">
					当前位置：鉴定项目&nbsp;&gt;&nbsp;一般项目&nbsp;&gt;&nbsp;结项数据&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==9">
					当前位置：我的项目&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==10">
					当前位置：评审项目&nbsp;&gt;&nbsp;一般项目&nbsp;&gt;&nbsp;申请数据&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==11">
					当前位置：社科项目数据&nbsp;&gt;&nbsp;一般项目&nbsp;&gt;&nbsp;年检数据&nbsp;&gt;&nbsp;查看
				</s:elseif>
			</div>

			<div class="main">
				<s:hidden id="project_type" value="1"/>
				<s:hidden id="entityId" name="entityId" value="%{entityId}" />
				<s:hidden id="update" name="update"/>
				<s:hidden id="uploadKey" name="uploadKey" value="%{#session.uploadKey}" />
				<s:include value="/project/general/viewButton.jsp"></s:include>
				<s:form id="view_general" theme="simple" namespace="/project/general">
					<s:hidden id="entityId" name="entityId" />
					<s:hidden id="projectid" name="projectid" />
					<s:hidden id="selectedTab" name="selectedTab" />
					<s:hidden id="pageNumber" name="pageNumber" />
					<s:hidden id="listType" name="listType" />
					<s:hidden id="projectType" name="projectType" />
					<s:hidden id="annStatus" name="annStatus"/>
					<s:hidden id="midStatus" name="midStatus"/>
					<s:hidden id="varStatus" name="varStatus"/>
					<s:hidden id="appPassAlready" />
					<s:hidden id="endPassAlready" />
					<s:hidden id="midPassAlready" />
					<s:hidden id="endAllow" />
					<s:hidden id="midForbid" />
					<s:hidden id="endPending" />
					<s:hidden id="annPending" />
					<s:hidden id="midPending" />
					<s:hidden id="varPending"/>
					<s:hidden id="accountType" name="accountType" value="%{#session.loginer.currentType}"/>
				</s:form>
				
				<s:include value="/project/general/viewBasic.jsp" />

				<div class="main_content" style="display:none;" id="tabcontent">
					<div id="tabs" class="p_box_bar">
						<ul>
							<li><a href="#application">申请信息</a></li>
							<li class="un_granted"><a href="#granted">立项信息</a></li>
							<li><a href="#member">相关成员</a></li>
							<li class="un_granted"><a href="#funding">拨款信息</a></li>
							<li class="un_granted"><a href="#anninspection">年检信息</a></li>
							<li class="un_granted"><a href="#midinspection">中检信息</a></li>
							<li class="un_granted"><a href="#endinspection">结项信息</a></li>
							<li class="un_granted"><a href="#variation">变更信息</a></li>
							<li class="un_granted"><a href="#product">相关成果</a></li>
							<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_PUBLISH_VIEW'>
								<li class="un_granted"><a href="#publishStatus">数据发布情况</a></li>
							</sec:authorize>
						</ul>
					</div>

					<div class="p_box">
						<div id="application">
							<s:include value="/project/general/viewApplyTab.jsp"/>
						</div>
						<div id="granted">
							<s:include value="/project/general/viewGrantedTab.jsp"/>
						</div>
						<div id="member">
							<s:include value="/project/general/viewMemberTab.jsp"/>
						</div>
						<div id="funding">
							<s:include value="/project/general/viewFundingTab.jsp" />
						</div>
						<div id="anninspection">
							<s:include value="/project/general/anninspection/apply/view.jsp"/>
						</div>
						<div id="midinspection">
							<s:include value="/project/general/midinspection/apply/view.jsp"/>
						</div>
						<div id="endinspection">
							<s:include value="/project/general/endinspection/apply/view.jsp"/>
						</div>
						<div id="variation">
							<s:include value="/project/general/variation/apply/view.jsp"/>
						</div>
						<div id="product">
							<s:include value="/product/extIf/viewRelated.jsp"/>
						</div>
						<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_PUBLISH_VIEW'>
							<div id="publishStatus">
								<s:include value="/project/general/publishStatus.jsp"/>
							</div>
						</sec:authorize>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/general/view.js',
		           'javascript/project/general/application/apply/view.js',
		           'javascript/project/general/application/granted/popSetUpProjectStatus.js',
		           'javascript/project/general/application/review/view.js',
		           'javascript/project/general/anninspection/view.js',
		           'javascript/project/general/midinspection/view.js',
		           'javascript/project/general/variation/view.js',
		           'javascript/project/general/endinspection/apply/view.js',
		           'javascript/project/general/endinspection/review/view.js'], 
		           function(view, app, granted, appRev, anni, midi, vari, endi, endRev) {
						$(document).ready(function() {
							view.init();
							app.initClick();
							granted.init();
							appRev.initClick();
							anni.initClick();
							midi.initClick();
							vari.initClick();
							endi.initClick();
							endRev.initClick();
						});
				});
			</script>
		</body>
	
</html>