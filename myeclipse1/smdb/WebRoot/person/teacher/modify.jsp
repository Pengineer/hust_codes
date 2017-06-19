<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>修改教师</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：社科人员数据&nbsp;&gt;&nbsp;社科研究人员&nbsp;&gt;&nbsp;教师&nbsp;&gt;&nbsp;修改
		</div>

		<div class="main">
			<s:form id="form_person" action="modify" namespace="/person/teacher">
				<s:include value="/person/edit_identifier1.jsp" />
				<s:include value="/person/edit_identifier2.jsp" />

				<div id="info" style="display:none">
					<div class="main_content">
						<div id="procedure" class="step_css">
							<ul>
								<li class="proc" name="basic2"><span class="left_step"></span><span class="right_step">基本信息</span></li>
								<li class="proc" name="teacher_affiliation_modify"><span class="left_step"></span><span class="right_step">任职信息</span></li>
								<li class="proc" name="contact"><span class="left_step"></span><span class="right_step">联系信息</span></li>
								<li class="proc" name="academic"><span class="left_step"></span><span class="right_step">学术信息</span></li>
								<li class="proc" name="education"><span class="left_step"></span><span class="right_step">教育背景</span></li>
								<li class="proc" name="work"><span class="left_step"></span><span class="right_step">工作经历</span></li>
								<li class="proc" name="abroad"><span class="left_step"></span><span class="right_step">出国（境）经历</span></li>
								<li class="proc" name="bank"><span class="left_step"></span><span class="right_step">银行信息</span></li>
								<li class="proc step_oo" name="finish"><span class="left_step"></span><span class="right_step">完成</span></li>
							</ul>
						</div>
					</div>

					<div class="main_content">
						<s:include value="/validateError.jsp" />
						<s:include value="/person/edit_basic2.jsp" />
						<s:include value="/person/edit_teacher_affiliation_modify.jsp" />
						<s:include value="/person/edit_contact.jsp" />
						<s:include value="/person/edit_academic.jsp" />
						<s:include value="/person/edit_education.jsp" />
						<s:include value="/person/edit_work.jsp" />
						<s:include value="/person/edit_abroad.jsp" />
						<s:include value="/person/edit_bank.jsp" />
					</div>
				</div>
			</s:form>

			<div id="optr" class="btn_bar2">
				<input id="prev" class="btn2" type="button" value="上一步" />
				<input id="next" class="btn2" type="button" value="下一步" />
				<input id="finish" class="btn1" type="button" value="完成" />
				<input id="cancel" class="btn1" type="button" value="取消" />
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/person/teacher/modify.js', function(modify) {
				$(function(){
					modify.init();
				});
			});
		</script>
	</body>
</html>
