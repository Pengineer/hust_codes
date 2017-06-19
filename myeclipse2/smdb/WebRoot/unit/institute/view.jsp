<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查看</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<s:if test="viewFlag == 1">
			<div class="link_bar">
			当前位置：我的资料&nbsp;&gt;&nbsp;查看
			</div>
			<s:hidden id="namespace" value="unit/selfspace" />
		</s:if>
		<s:else>
			<div class="link_bar">
			当前位置：社科机构数据&nbsp;&gt;&nbsp;社科研究机构&nbsp;&gt;&nbsp;查看
			</div>
			<s:hidden id="namespace" value="unit/institute" />
		</s:else>
		<div class="main">
				<s:hidden id="entityId" name="entityId" />
				<s:hidden id="entityIds" name="entityIds" />
				<s:hidden id="update" name="update" />
				<s:hidden id="unitType" name="unitType" value="4" />
				<s:hidden id="viewFlag" name="viewFlag" />
			<div class="choose_bar">
				<ul>
					<s:if test="viewFlag ==1">
						<sec:authorize ifAllGranted="ROLE_BASE_UNIT_SELF_MODIFY">
							<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
						</sec:authorize>
					</s:if>
					<s:else>
						<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
						<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
						<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
						<sec:authorize ifAllGranted="ROLE_UNIT_INSTITUTE_DELETE">
							<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
						</sec:authorize>
						<sec:authorize ifAllGranted="ROLE_UNIT_INSTITUTE_MODIFY">
							<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
						</sec:authorize>
						<sec:authorize ifAllGranted="ROLE_UNIT_INSTITUTE_ADD">
							<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
						</sec:authorize>
					</s:else>
				</ul>
			</div>

			<div class="main_content" style="display:none;" id="tabcontent">
				<s:include value="/unit/institute/viewInstituteCommon.jsp" />
				
				<div class="main_content">
					<div id="tabs" class="p_box_bar">
						<ul>
							<li><a href="#basic">基本信息</a></li>
							<li><a href="#contact">联系信息</a></li>
							<li><a href="#academic">学术信息</a></li>
							<li><a href="#project">项目信息</a></li>
							<li><a href="#product">成果信息</a></li>
							<li><a href="#person">人员信息</a></li>
							<li><a href="#funding">拨款信息</a></li>
							<li><a href="#report">工作报告与成果简报</a></li>
							<li><a href="#assessment">检查评估</a></li>
						</ul>
					</div>
					
					<div class="p_box">
						<s:include value="/unit/institute/viewInstituteBasic.jsp" />
						<s:include value="/unit/institute/viewInstituteContact.jsp" />
						<s:include value="/unit/institute/viewInstituteAcademic.jsp" />
						<s:include value="/unit/institute/viewInstituteProject.jsp" />
						<s:include value="/unit/institute/viewInstituteProduct.jsp" />
						<s:include value="/unit/institute/viewInstitutePerson.jsp" />
						<s:include value="/unit/institute/viewInstituteFunding.jsp" />
						<s:include value="/unit/institute/viewInstituteReport.jsp" />
						<s:include value="/unit/institute/viewInstituteAssessment.jsp" />
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/unit/institute/view.js', function(view) {
				$(function(){
					view.init();
				})
			});
		</script>
	</body>
</html>