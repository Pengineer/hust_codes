<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>基地项目</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				当前位置：社科项目数据&nbsp;&gt;&nbsp;基地项目&nbsp;&gt;&nbsp;申请数据&nbsp;&gt;&nbsp;添加
			</div>
			<div class="main">
				<s:form id="application_form" action="add" namespace="/project/instp/application/apply">
					<s:hidden id="accountType" name="accountType" value="%{#session.loginer.currentType}"/>
					<s:hidden id="addOrModify" value='1'/>
					<div id="info" style="display:none">
						<div class="main_content">
							<div id="procedure" class="step_css">
								<ul>
									<li class="proc" name="apply"><span class="left_step"></span><span class="right_step">申请信息</span></li>
									<li class="proc" name="member"><span class="left_step"></span><span class="right_step">相关成员</span></li>
									<li class="step_oo" name="finish"><span class="left_step"></span><span class="right_step">完成</span></li>
								</ul>
							</div>
						</div>
						<div class="main_content">
							<s:hidden name = "flag"></s:hidden>
							<s:include value="/validateError.jsp" />
							<s:include value="/project/instp/application/apply/editApplyTab.jsp" />
							<s:include value="/project/instp/application/apply/editMemberTab.jsp" />
						</div>
					</div>
					<s:hidden id="proApplicantSubmitStatus" name="proApplicantSubmitStatus"/>
				</s:form>
				<s:include value="/project/instp/application/apply/memberTemplate.jsp" />
				<div id="optr" class="btn_bar2">
					<input id="prev" class="btn2" type="button" value="上一步" />
					<input id="next" class="btn2" type="button" value="下一步" />
					<input id="save" class="btn1" type="button" value="保存" />
					<input id="submit" class="btn1" type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
					<s:hidden name="deadline" id="deadline" value="%{projectService.checkIfTimeValidate(#session.loginer.currentType, '021')}"/>
					<s:hidden name="appStatus" id="appStatus" value="%{projectService.getBusinessStatus('021')}"/>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/instp/application/apply/add.js','javascript/project/project_share/application/validate.js'], function(add, validate) {
					validate.valid();
					add.init();
				});
			</script>
		</body>
	
</html>