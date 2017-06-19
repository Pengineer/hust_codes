<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>重大攻关项目</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar" id="view_project_key">
				<s:if test="listType==12">
					当前位置：社科项目数据&nbsp;&gt;&nbsp;重大攻关项目&nbsp;&gt;&nbsp;年度选题&nbsp;&gt;&nbsp;查看
				</s:if>
				<s:elseif test="listType==1">
					当前位置：社科项目数据&nbsp;&gt;&nbsp;重大攻关项目&nbsp;&gt;&nbsp;投标数据&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==2">
					当前位置：社科项目数据&nbsp;&gt;&nbsp;重大攻关项目&nbsp;&gt;&nbsp;中标数据&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==3">
					当前位置：社科项目数据&nbsp;&gt;&nbsp;重大攻关项目&nbsp;&gt;&nbsp;中检数据&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==4">
					当前位置：社科项目数据&nbsp;&gt;&nbsp;重大攻关项目&nbsp;&gt;&nbsp;结项数据&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==5">
					当前位置：社科项目数据&nbsp;&gt;&nbsp;重大攻关项目&nbsp;&gt;&nbsp;变更数据&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==6">
					当前位置：评审项目&nbsp;&gt;&nbsp;重大攻关项目&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==7">
					当前位置：我的项目&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==8">
					当前位置：鉴定项目&nbsp;&gt;&nbsp;重大攻关项目&nbsp;&gt;&nbsp;结项数据&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==9">
					当前位置：我的项目&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==10">
					当前位置：评审项目&nbsp;&gt;&nbsp;重大攻关项目&nbsp;&gt;&nbsp;申报数据&nbsp;&gt;&nbsp;查看
				</s:elseif>
				<s:elseif test="listType==11">
					当前位置：社科项目数据&nbsp;&gt;&nbsp;重大攻关项目&nbsp;&gt;&nbsp;年检数据&nbsp;&gt;&nbsp;查看
				</s:elseif>
			</div>

			<div class="main">
				<s:hidden id="topsId" name="topsId"/>
				<s:hidden id="entityId" name="entityId" value="%{entityId}" />
				<s:hidden id="update" name="update"/>
				<s:hidden id="uploadKey" name="uploadKey" value="%{#session.uploadKey}" />
				<s:include value="/project/key/viewButton.jsp"></s:include>
				<s:form id="view_key" theme="simple" namespace="/project/key">
					<s:hidden id="topsId" name="topsId"/>
					<s:hidden id="entityId" name="entityId" />
					<s:hidden id="projectid" name="projectid" />
					<s:hidden id="selectedTab" name="selectedTab" />
					<s:hidden id="pageNumber" name="pageNumber" />
					<s:hidden id="listType" name="listType" />
					<s:hidden id="projectType" name="projectType" />
					<s:hidden id="topsStatus" name="topsStatus"/>
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
				<textarea id="view_base_template" style="display:none;">
					<div class="main_content">
						<s:include value="/project/key/viewBasic.jsp" />
					</div>
				</textarea>

				<div id="view_base" style="display:none; clear:both;"></div>

				<div class="main_content" style="display:none;" id="tabcontent">
					<div id="tabs" class="p_box_bar">
						<ul>
							<li><a href="#topicSelection">选题信息</a></li>
							<li class="un_selected"><a href="#application">申报信息</a></li>
							<li class="un_granted un_selected"><a href="#granted">立项信息</a></li>
							<li class="un_selected"><a href="#member">相关成员</a></li>
							<li class="un_granted un_selected"><a href="#funding">拨款信息</a></li>
							<li class="un_granted un_selected"><a href="#anninspection">年检信息</a></li>
							<li class="un_granted un_selected"><a href="#midinspection">中检信息</a></li>
							<li class="un_granted un_selected"><a href="#endinspection">结项信息</a></li>
							<li class="un_granted un_selected"><a href="#variation">变更信息</a></li>
							<li class="un_granted un_selected"><a href="#product">相关成果</a></li>
						</ul>
					</div>

					<div class="p_box">
						<div id="topicSelection">
							<s:include value="/project/key/viewTopicTab.jsp"/>
						</div>
						<div id="application">
							<s:include value="/project/key/viewApplyTab.jsp"/>
						</div>
						<div id="granted">
							<s:include value="/project/key/viewGrantedTab.jsp"/>
						</div>
						<div id="member">
							<s:include value="/project/key/viewMemberTab.jsp"/>
						</div>
						<div id="funding">
							<s:include value="/project/key/viewFundingTab.jsp" />
						</div>
						<div id="anninspection">
							<s:include value="/project/key/anninspection/apply/view.jsp"/>
						</div>
						<div id="midinspection">
							<s:include value="/project/key/midinspection/apply/view.jsp"/>
						</div>
						<div id="endinspection">
							<s:include value="/project/key/endinspection/apply/view.jsp"/>
						</div>
						<div id="variation">
							<s:include value="/project/key/variation/apply/view.jsp"/>
						</div>
						<div id="product">
							<s:include value="/product/extIf/viewRelated.jsp"/>
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/key/view.js',
				           'javascript/project/key/topicSelection/view.js',
				           'javascript/project/key/application/apply/view.js',
				           'javascript/project/key/application/granted/popSetUpProjectStatus.js',
				           'javascript/project/key/application/review/view.js',
				           'javascript/project/key/anninspection/view.js',
				           'javascript/project/key/midinspection/view.js',
				           'javascript/project/key/variation/view.js',
				           'javascript/project/key/endinspection/apply/view.js',
				           'javascript/project/key/endinspection/review/view.js'], 
				           function(view, tops, app, granted, appRev, anni, midi, vari, endi, endRev) {
								$(document).ready(function() {
									view.init();
									tops.initClick();
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