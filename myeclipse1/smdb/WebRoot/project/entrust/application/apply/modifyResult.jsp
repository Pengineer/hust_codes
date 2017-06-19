<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>委托应急课题</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				当前位置：社科项目数据&nbsp;&gt;&nbsp;委托应急课题&nbsp;&gt;&nbsp;申请数据&nbsp;&gt;&nbsp;修改
			</div>
			<div class="main">
				<s:form id="application_form" action="modifyResult" namespace="/project/entrust/application/apply">
					<s:hidden id = "appId" name="application1.id"/>
					<s:hidden id="accountType" name="accountType" value="%{#session.loginer.currentType}"/>
					<s:hidden id="addOrModify" value='2'/>
					<div id="info" style="display:none">
						<div class="main_content">
							<div id="procedure" class="step_css">
								<ul>
									<li class="proc" name="apply"><span class="left_step"></span><span class="right_step">申请信息</span></li>
									<li class="proc" name="granted"><span class="left_step"></span><span class="right_step">立项信息</span></li>
									<li class="proc" name="member"><span class="left_step"></span><span class="right_step">相关成员</span></li>
									<li class="step_oo" name="finish"><span class="left_step"></span><span class="right_step">完成</span></li>
								</ul>
							</div>
						</div>
						<div class="main_content">
							<s:hidden name = "flag"></s:hidden>
							<s:include value="/validateError.jsp" />
							<s:include value="/project/entrust/application/apply/editApplyTab.jsp" />
							<s:include value="/project/entrust/application/apply/editGrantedTab.jsp" />
							<s:include value="/project/entrust/application/apply/editMemberTab.jsp" />
						</div>
					</div>
				</s:form>
				<s:include value="/project/entrust/application/apply/memberTemplate.jsp" />
				<div id="optr" class="btn_bar2">
					<input id="prev" class="btn2" type="button" value="上一步" />
					<input id="next" class="btn2" type="button" value="下一步" />
					<input id="finish" class="btn1" type="button" value="完成" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/entrust/application/apply/modify.js','javascript/project/project_share/application/validate.js'], function(modify, validate) {
					validate.valid();
					modify.init();
				});
			</script>
		</body>
	
</html>