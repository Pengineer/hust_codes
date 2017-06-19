<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加</title>
		<s:include value="/innerBase.jsp" />
		<script type="text/javascript">
			seajs.use('javascript/statistic/add.js', function(add) {
				$(function(){
					add.init();
				})
			});
		</script>
	</head>

	<body>
		<div class="link_bar">
			当前位置：常规统计分析&nbsp;&gt;&nbsp;<s:property value="#session.statisticListType"/>&nbsp;&gt;&nbsp;添加
		</div>

		<s:form id="form_stat" action="add" namespace="/statistic/common" theme="simple">
			<div class="main">
				<div class="main_content">
					<s:include value="/validateError.jsp" />
					<table width="100%" border="0" cellspacing="2" cellpadding="2">
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3">统计主题：</span></td>
							<td class="table_td3"><s:textfield name="mdxQuery.title" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title3">统计年度：</span></td>
							<td class="table_td3"><s:textfield name="mdxQuery.year" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title3">发布时间：</span></td>
							<td class="table_td3"><s:textfield name="mdxQuery.date" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title3">MDX语句：</span></td>
							<td class="table_td3"><s:textarea name="mdxQuery.mdx" rows="6" cssClass="textarea_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title3">统计图配置：</span></td>
							<td class="table_td3"><s:radio cssClass="config_choose" name="chart_config" list="#{'0':'默认配置','1':'自定义配置'}" value="0"/></td>
							<td class="table_td4"></td>
						</tr>
					</table>
					<table id="config" width="100%" border="0" cellspacing="2" cellpadding="2" style="display:none;">
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3">展现列：</span></td>
							<td class="table_td3" id="chart_column"></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title3">展现形式：</span></td>
							<td class="table_td3"><s:radio name="chart_type" list="#{'0':'垂直柱图','1':'饼状图','2':'折线图'}" value="0"/></td>
							<td class="table_td4"></td>
						</tr>
					</table>
				</div> 
				<s:include value="/submit.jsp" />
			</div>
		</s:form>
	</body>
</html>