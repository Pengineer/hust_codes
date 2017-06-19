<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>数据挖掘结果</title>
		<s:include value="/innerBase.jsp"/>
	</head>

	<body>
		<s:form id="view_list" action="toList" theme="simple" namespace="/dataMining/result">
			<s:hidden id="entityId" name="entityId" />
			<s:hidden id="entityIds" name="entityIds"/>
			<s:hidden id="update" name="update" />
		</s:form>
		<div class="link_bar">
			当前位置：数据挖掘结果&nbsp;&gt;&nbsp;查看
		</div>
		<div class="main">
			<div class="choose_bar">
				<ul>
					<li id="view_back"><input class="btn1" type="button" value="返回"/></li>
					<s:if test="viewFlag!=2">
						<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
						<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
					</s:if>
					<sec:authorize ifAllGranted="ROLE_DATAMINING_RESULT_DELETE">
						<li id="view_del"><input class="btn1" type="button" value="删除"/></li>
					</sec:authorize>
				</ul>
			</div>
			
			<div class="main_content">
				<div class="title_statistic">参数配置</div>
				<div class="title_bar">
					<table width="100%" cellspacing="0" cellpadding="0" border="0">
						<s:iterator value="#session.dataMining_parm" status="stat">
							<tbody>
								<tr>
									<td class="title_bar_td" width="30" align="right">
										<img width="5" height="9" src="image/ico08.gif"/>
									</td>
									<td width="30"></td>
									<td class="title_bar_td" align="left"><s:property value="#session.dataMining_parm[#stat.index]"/></td>
								</tr>
							</tbody>
						</s:iterator>
					</table>
				</div>
			</div>
			<br/>
			<div id="graphHolder" style="text-align: center;"></div>
		</div>
		
		<script type="text/javascript">
			seajs.use('javascript/dataMining/result/view.js', function(view) {
				$(document).ready(function(){
					view.init("dataMining/result");
				})
			});
		</script>
	</body>
</html>