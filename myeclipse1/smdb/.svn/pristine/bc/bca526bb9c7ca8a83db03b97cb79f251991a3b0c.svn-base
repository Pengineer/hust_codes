<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>修改</title>
		<s:include value="/innerBase.jsp" />
	</head>
	
	<body>
		<s:if test="viewFlag == 1">
			<div class="link_bar">
				当前位置：我的资料&nbsp;&gt;&nbsp;修改
			</div>
			<s:hidden id="namespace" value="unit/selfspace" />
		</s:if>
		<s:else>
			<div class="link_bar">
				当前位置：社科机构数据&nbsp;&gt;&nbsp;社科管理机构&nbsp;&gt;&nbsp;部级管理机构&nbsp;&gt;&nbsp;修改
			</div>
			<s:hidden id="nameSpace" value="unit/agency/ministry" />
		</s:else>
		
		<div class="main">
			<s:form id="form_agency" action="modify" theme="simple">
				<s:hidden name="entityId" />
				<s:hidden name="directorId" id="directorId"/>
				<s:hidden name="slinkmanId" id="sLinkmanId"/>
				<s:hidden name="sdirectorId" id="sDirectorId"/>
				<s:hidden name="agency.id" id="entityId" />
				<s:hidden name="viewFlag" id="viewFlag" />
				<s:hidden name="id" id="id" value="%{agency.id}" />
				<s:hidden name="agency.subjection.id" value="%{agency.subjection.id}" />
				
				<div id="info" style="display:none">
					<div class="main_content">
						<div id="procedure" class="step_css">
							<ul>
								<li class="proc" name="basicInfo"><span class="left_step"></span><span class="right_step">基本信息</span></li>
								<li class="proc" name="contactInfo"><span class="left_step"></span><span class="right_step">联系信息</span></li>
								<li class="proc" name="managementInfo"><span class="left_step"></span><span class="right_step">社科管理部门</span></li>
								<li class="proc" name="financialInfo"><span class="left_step"></span><span class="right_step">财务管理部门</span></li>
								<li class="proc step_oo"><span class="left_step"></span><span class="right_step">完成</span></li>
							</ul>
						</div>
					</div>
					
					<div class="main_content">
						<s:include value="/validateError.jsp" />
						<s:include value="/unit/agency/editAgencyBasic.jsp" />
						<s:include value="/unit/agency/editAgencyContact.jsp" />
						<s:include value="/unit/agency/editAgencyManagement.jsp" />
						<s:include value="/unit/agency/editAgencyFinancial.jsp" />
					</div>
				</div>
			</s:form>
			
			<s:include value="/unit/agency/procedureButton.jsp" />
		</div>
		<script type="text/javascript">
			seajs.use('javascript/unit/agency/ministry/modify.js', function(modify) {
				$(function(){
					modify.init();
				})
			});
		</script>
	</body>
</html>