<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>专项任务项目</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar" id="view_project_general">
				当前位置：社科项目数据&nbsp;&gt;&nbsp;专项任务项目&nbsp;&gt;&nbsp;申请数据&nbsp;&gt;&nbsp;申请核算
			</div>

			<div class="main">
				<s:include value="/project/special/application/apply/viewCommon.jsp" />
				<div class="p_box_t">
					<div class="p_box_t_b">
						<input id="mail" class="btn2" type="button" value="邮件通知" />
						<input id="clear" class="btn2" type="button" value="清空核算" />
						<input id="export" class="btn2" type="button" value="导出核算" />
					</div>
				</div>
				<div class="p_box_body">
					<div class="main_content" id="tabcontent">
						<div id="tabs" class="p_box_bar">
							<ul>
								<li><a href="#main">限项申请核算表</a></li>
							</ul>
						</div>
	
						<div class="p_box">
							<s:include value="/project/special/application/apply/viewMain.jsp" />
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/special/application/apply/check.js','javascript/project/special/application/apply/validate.js'], function(check, validate) {
					validate.valid();
					check.init();
				});
			</script>
		</body>
	
</html>