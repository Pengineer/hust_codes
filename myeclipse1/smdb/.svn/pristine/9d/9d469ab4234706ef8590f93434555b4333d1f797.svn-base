<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查重标记</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：数据管理&nbsp;&gt;&nbsp;基地项目查重标记
		</div>
		<div class="main">
			<div class="main_content">
				<s:form>
					<p>查重条件：</p>
					<p>1、基地，重大攻关，后期资助项目：在研。</p>
					<p>2、国家社会科学基金重大项目：在研。</p>
					<input id="type" type="hidden" name="type"> 
					<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_INSTP_DELETE">
						<input id="reset" type="button" class="btn1" value="清除标记" />
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_INSTP_ADD">
						<input id="set" type="button" class="btn1" value="添加标记" />
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_INSTP_ADD">
						<input id="exportDup" type="button" class="btn1" value="导出结果" />
					</sec:authorize>
				</s:form>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/dm/dupCheck/instp/addInstp.js', function(add) {
				$(function(){
					add.init();
				})
			});
		</script>
	</body>
</html>