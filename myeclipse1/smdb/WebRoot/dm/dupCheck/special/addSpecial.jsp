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
			当前位置：数据管理&nbsp;&gt;&nbsp;专项项目查重标记
		</div>
		<div class="main">
			<div class="main_content">
				<s:form>
					<p>查重说明：</p>
					<p>1、职称与年龄审核：规划基金项目申请者，应为具有高级职称（含副高）的在编在岗教师；青年基金项目申请者，应为具有博士学位或中级以上（含中级）职称的在编在岗教师，年龄不超过40周岁（1975年1月1日以后出生）。</p>
					<p>2、在研项目查重：项目申请人应不具有在研的国家自然科学基金、国家社会科学基金（含教育学、艺术学单列）、教育部人文社会科学研究一般项目、重大攻关项目、后期资助项目、基地项目、发展报告项目、专项任务项目、委托项目。</p>
					<p>3、撤项项目审核：项目申请人应不具有三年内撤项的教育部人文社会科学研究一般项目、重大攻关项目、后期资助项目、基地项目、专项任务项目、委托项目 。</p>
					<p>4、申请项目限制：申请国家社科基金项目的负责人同年度不能申请教育部专项任务项目；申请国家社科基金后期资助项目的负责人同年度不能申请教育部专项任务项目。</p>
					<p>导出说明：</p>
					<p>1、导出系统当前年份的该类项目的查重结果信息。</p>
					<input id="type" type="hidden" name="type"> 
					<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_SPECIAL_DELETE">
						<input id="reset" type="button" class="btn1" value="清除标记" />
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_SPECIAL_ADD">
						<input id="set" type="button" class="btn1" value="添加标记" />
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_SPECIAL_ADD">
						<input id="exportDup" type="button" class="btn1" value="导出结果" />
					</sec:authorize>
				</s:form>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/dm/dupCheck/special/addSpecial.js', function(add) {
				$(function(){
					add.init();
				})
			});
		</script>
	</body>
</html>