<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>关联性分析</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：关联规则挖掘&nbsp;&gt;&nbsp;
			<s:if test="type=='discipline'">学科关联性分析</s:if>
			<s:elseif test="type=='university'">高校关联性分析</s:elseif>
			<s:elseif test="type=='member'">研究人员关联性分析</s:elseif>
			
			<s:if test="#session.analyzeAngle==0">（申请数据）</s:if>
			<s:elseif test="#session.analyzeAngle==1">（立项数据）</s:elseif>
			<s:elseif test="#session.analyzeAngle==2">（批准经费）</s:elseif>
		</div>
		
		<div class="choose_bar">
			<ul>
				<li><input onclick="history.back();" class="btn1" type="button" value="返回"/></li>
				<li>
					<sec:authorize ifAllGranted="ROLE_DATAMINING_ASSOCIATION_ANALYZE">
						<input id="view_export" type="button" class="btn1" value="导出" />
					</sec:authorize>
				</li>
			</ul>
		</div>
		
		<div class="main_content">
			<div class="title_statistic"></div>
			<div style="margin-top:4px;"><table id="dataList" class="table_statistic" width="100%" cellspacing="0" cellpadding="2"></table></div>
			
			<div style="margin-top:10px; padding-left: 12px;">注：1、两节点间的连接线的粗细表示两者之间的合作情况；2、每个节点的连接线数量表示合作的范围。</div>
			<div id="graphHolder" style="text-align: center;"></div>
		</div>	
		
		<script type="text/javascript">
			seajs.use('javascript/dataMining/association/analyze.js', function(analyze) {
				$(function(){
					analyze.init();
				})
			});
		</script>
	</body>
</html>