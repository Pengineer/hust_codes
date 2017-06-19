<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统选项表</title>
		<s:include value="/innerBase.jsp" />
		
		<link rel="stylesheet" href="tool/ztree/css/zTreeStyle.css" type="text/css" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：系统选项表&nbsp;&gt;&nbsp;查看
		</div>
		<div class="main" style="width:100%">
			<div style="width:98%;height:200px;overflow:scroll;float:left">
				<ul id="system_option_tree" class="ztree"></ul>
			</div>
			<div class="main_content">
				<s:include value="/system/option/list.jsp"/>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/system/option/view.js', function(view) {
				$(function(){
					view.init();
				})
			});
		</script>
	</body>
</html>
