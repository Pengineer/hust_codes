<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>常规数据统计</title>
		<s:include value="/innerBase.jsp"/>
		<script type="text/javascript">
			seajs.use('javascript/statistic/view.js', function(view) {
				$(document).ready(function(){
					view.init();
				})
			});
		</script>
	</head>

	<body>
		<s:form id="view_list" action="toList" theme="simple" namespace="/statistic/common">
			<s:hidden id="entityId" name="entityId" />
			<s:hidden id="entityIds" name="entityIds"/>
			<s:hidden id="update" name="update" />
		</s:form>
		<div class="link_bar">
			当前位置：常规数据统计&nbsp;&gt;&nbsp;<s:property value="#session.statisticListType"/>&nbsp;&gt;&nbsp;查看
		</div>
		<div class="main">
			<div class="choose_bar">
				<ul>
					<s:if test="viewFlag!=2">
						<li id="view_back"><input class="btn1" type="button" value="返回"/></li>
					</s:if>
					<s:else>
						<li id="view_back1"><input class="btn1" type="button" value="返回"/></li>
					</s:else>
					<s:if test="viewFlag!=2">
						<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
						<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
					</s:if>
					<sec:authorize ifAllGranted="ROLE_STATISTIC_COMMON_DELETE">
						<li id="view_del"><input class="btn1" type="button" value="删除"/></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_STATISTIC_COMMON_MODIFY">
						<li id="view_mod"><input class="btn1" type="button" value="修改"/></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_STATISTIC_COMMON_ADD">
						<li id="view_add"><input class="btn1" type="button" value="添加"/></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_STATISTIC_EXPORT">
						<li id="view_export"><input class="btn1" type="button" value="导出"/></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_STATISTIC_HOME_SHOW">
						<s:if test="isHomeShow==1">
							<li id="view_isHomeShow"><input class="btn2" type="button" value="<s:text name='首页取消'/>"/></li>
						</s:if>
						<s:else>
							<li id="view_isHomeShow"><input class="btn2" type="button" value="<s:text name='首页显示'/>"/></li>
						</s:else>
					</sec:authorize>
				</ul>
			</div>
			
			<%-- 无钻取时使用 --%>
			<div class="main_content">
				<div class="title_statistic"><s:property value="#session.statisticTitle"/></div>
				<div style="margin-top:4px;">
					<table class="table_statistic" width="100%" cellspacing="0" cellpadding="2">
						<s:iterator value="#session.dataList" status="stat">
							<tr>
								<s:iterator value="#session.dataList[#stat.index]" status="stat2">
									<td>
										<s:property value="#session.dataList[#stat.index][#stat2.index]"/>
									</td>
								</s:iterator>
							</tr>
						</s:iterator>
					</table>
				</div>
			</div>
			
			<%-- 有钻取时使用 --%>
<%--	  			<div class="main_content">--%>
<%--		  			<div class="title_statistic"><s:property value="#session.statisticTitle"/></div>--%>
<%--					<div style="margin-top:4px;">--%>
<%--				  		<table class="table_statistic" width="100%" cellspacing="0" cellpadding="2">--%>
<%--							<s:iterator value="#session.dataList" status="stat">--%>
<%--								<tr>--%>
<%--									<s:iterator value="#session.dataList[#stat.index]" status="stat2">--%>
<%--										<td>--%>
<%--											<s:if test="#stat.index > 1 && #stat2.index > 1">--%>
<%--												<s:if test="!#session.dataList[0][#stat2.index].toString().trim().equals('结项率')--%>
<%--												 && !#session.dataList[0][#stat2.index].toString().trim().equals('中检通过率')--%>
<%--												 && !#session.dataList[0][#stat2.index].toString().trim().equals('立项率')--%>
<%--												 && !#session.dataList[0][#stat2.index].toString().trim().equals('批准经费')">--%>
<%--													<a class="drill_down" rel="<s:property value='#stat.index'/>" rev="<s:property value='#stat2.index'/>" href="">--%>
<%--														<s:property value="#session.dataList[#stat.index][#stat2.index]"/>--%>
<%--													</a>--%>
<%--												</s:if>--%>
<%--												<s:else><s:property value="#session.dataList[#stat.index][#stat2.index]"/></s:else>--%>
<%--											</s:if>--%>
<%--											<s:elseif test="#stat.index > 1 && #stat2.index == 1">--%>
<%--												<s:if test="#session.isLevels == true">--%>
<%--													<a class="drill_level" rel="<s:property value='#session.dataList[#stat.index][#stat2.index -1]'/>" href="">--%>
<%--														<img src="image/statistic/drill_plus.gif"/>--%>
<%--													</a>--%>
<%--												</s:if>--%>
<%--												<s:property value="#session.dataList[#stat.index][#stat2.index]"/>--%>
<%--											</s:elseif>--%>
<%--											<s:else>--%>
<%--												<s:property value="#session.dataList[#stat.index][#stat2.index]"/>--%>
<%--											</s:else>--%>
<%--										</td>--%>
<%--									</s:iterator>--%>
<%--								</tr>--%>
<%--					  		</s:iterator>--%>
<%--				  		</table>--%>
<%--			  		</div>--%>
<%--		  		</div>--%>
			<br/>
			<div><div id="chart"></div></div>
<%--		  		<div id="drillDiv" style="display:none;">--%>
<%--			  		<s:include value="/statistic/drill_down.jsp"></s:include>--%>
<%--		  		</div>--%>
		</div>
		<s:hidden name="MDXQueryId" id="MDXQueryId"/>
		<s:hidden name="encryptedMdxQueryString" id="encryptedMdxQueryString"/>
		<s:hidden name="statisticType" id="statisticType"/>
		<s:hidden name="resultLines" id="resultLines"/>
		<s:hidden name="chartConfig" id="chartConfig"/>
		<s:hidden name="isHomeShow" id="isHomeShow"/>
	</body>
</html>