<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加外部专家</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：社科人员数据&nbsp;&gt;&nbsp;社科研究人员&nbsp;&gt;&nbsp;外部专家&nbsp;&gt;&nbsp;添加
		</div>

		<div class="main">
			<s:form id="form_person" action="add" namespace="/person/expert">
				<s:include value="/person/edit_identifier1.jsp" />
				<s:include value="/person/edit_identifier2.jsp" />

				<div id="info" style="display:none">
					<div class="main_content">
						<div id="procedure" class="step_css">
							<ul>
								<li class="proc" name="basic1"><span class="left_step"></span><span class="right_step">基本信息</span></li>
								<li class="proc" name="expert_affiliation"><span class="left_step"></span><span class="right_step">任职信息</span></li>
								<li class="proc" name="contact"><span class="left_step"></span><span class="right_step">联系信息</span></li>
								<li class="proc step_oo" name="finish"><span class="left_step"></span><span class="right_step">完成</span></li>
							</ul>
						</div>
					</div>

					<div class="main_content">
						<s:include value="/validateError.jsp" />
						<s:include value="/person/edit_basic1.jsp" />
						<s:include value="/person/edit_expert_affiliation.jsp" />
						<s:include value="/person/edit_contact.jsp" />
					</div>
				</div>
			</s:form>

			<div id="optr" class="btn_bar2">
				<input id="prev" class="btn2" type="button" style="display: none" value="上一步" />
				<input id="next" class="btn2" type="button" style="display: none" value="下一步" />
				<input id="finish" class="btn1" type="button" style="display: none" value="完成" />
				<input id="retry" class="btn2" type="button" style="display: none" value="重填" />
				<input id="confirm" class="btn1" type="button" style="display: none" value="确定" />
				<input id="cancel" class="btn1" type="button" style="display: none" value="取消" />
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/person/expert/add.js', function(add) {
				$(function(){
					add.init();
				})
			});
		</script>
	</body>
</html>
