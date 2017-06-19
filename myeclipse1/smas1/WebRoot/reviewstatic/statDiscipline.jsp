<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>高校参评专家数量</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/index.css" />
			<style type="text/css">
				.staticwd1 {text-align: center;padding:0;}
				.staticwd2 {text-align: center;padding:0;}
				.import{ background-color:#989CF3;}
			</style>
		</head>
		<body>
			<div id="top">
				<s:if test="listType == 1">
					统计分析&nbsp;&gt;&gt;&nbsp;一般项目&nbsp;&gt;&gt;&nbsp;<span style="font-weight:bold;">学科退避统计</span>
				</s:if>
				<s:else>
					统计分析&nbsp;&gt;&gt;&nbsp;基地项目&nbsp;&gt;&gt;&nbsp;<span style="font-weight:bold;">学科退避统计</span>
				</s:else>
			</div>			
			<div class="static" >
				<table class="static_table" cellspaning="0" cellpadding="0">
					<tr>
						<td class="staticwd0" colspan="2" >退避至三级学科</td>
					</tr>
					<tr>
						<td class="staticwd1" >项目\专家</td>
						<td class="staticwd1" >3—>3</td>
					</tr>
					<tr>
						<td class="staticwd1" >3->3</td>
						<td class="staticwd2 import" ><s:property value="matrixMap['00']" /></td>
					</tr>
				</table>
			</div>
			<div class="static" >
				<table class="static_table" cellspaning="0" cellpadding="0">
					<tr>
						<td class="staticwd0" colspan="3" >退避至二级学科</td>
					</tr>
					<tr>
						<td class="staticwd1" >项目\专家</td>
						<td class="staticwd1" >3—>2</td>
						<td class="staticwd1" >2—>2</td>
					</tr>
					<tr>
						<td class="staticwd1" >3->2</td>
						<td class="staticwd2" ><s:property value="matrixMap['33']" /></td>
						<td class="staticwd2" ><s:property value="matrixMap['31']" /></td>
					</tr>
					<tr>
						<td class="staticwd1" >2->2</td>
						<td class="staticwd2" ><s:property value="matrixMap['13']" /></td>
						<td class="staticwd2 import" ><s:property value="matrixMap['11']" /></td>
					</tr>
				</table>
			</div>
			<div class="static" >
				<table class="static_table" cellspaning="0" cellpadding="0">
					<tr>
						<td class="staticwd0" colspan="4" >退避至一级学科</td>
					</tr>
					<tr>
						<td class="staticwd1" >项目\专家</td>
						<td class="staticwd1" >3—>1</td>
						<td class="staticwd1" >2—>1</td>
						<td class="staticwd1" >1—>1</td>
					</tr>
					<tr>
						<td class="staticwd1" >3->1</td>
						<td class="staticwd2" ><s:property value="matrixMap['55']" /></td>
						<td class="staticwd2" ><s:property value="matrixMap['54']" /></td>
						<td class="staticwd2" ><s:property value="matrixMap['52']" /></td>
					</tr>
					<tr>
						<td class="staticwd1" >2->1</td>
						<td class="staticwd2" ><s:property value="matrixMap['45']" /></td>
						<td class="staticwd2" ><s:property value="matrixMap['44']" /></td>
						<td class="staticwd2" ><s:property value="matrixMap['42']" /></td>
					</tr>
					<tr>
						<td class="staticwd1" >1->1</td>
						<td class="staticwd2" ><s:property value="matrixMap['25']" /></td>
						<td class="staticwd2" ><s:property value="matrixMap['24']" /></td>
						<td class="staticwd2 import" ><s:property value="matrixMap['22']" /></td>
					</tr>
				</table>
			</div>
			<div class="static" >
				<table class="static_table" cellspaning="0" cellpadding="0">
					<tr>
						<td class="staticwd0" colspan="5" >学科退避合计</td>
					</tr>
					<tr>
						<td class="staticwd1" >匹配</td>
						<td class="staticwd1" >退避</td>
						<td class="staticwd1" >失配</td>
						<td class="staticwd1" >矩阵和</td>
						<td class="staticwd1" >总记录</td>
					</tr>
					<tr>
						<td class="staticwd2" ><s:property value="analyzeMap['complete']" />(<s:property value="analyzeMap['complete_per']" />)</td>
						<td class="staticwd2 " ><s:property value="analyzeMap['withdraw']" />(<s:property value="analyzeMap['withdraw_per']" />)</td>
						<td class="staticwd2 " ><s:property value="matrixMap['01']" /></td>
						<td class="staticwd2 " ><s:property value="analyzeMap['total']" /></td>
						<td class="staticwd2 " ><s:property value="matrixMap['02']" /></td>
					</tr>
				</table>
			</div>
		</body>
</html>