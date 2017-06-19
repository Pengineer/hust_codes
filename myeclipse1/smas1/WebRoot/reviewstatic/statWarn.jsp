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
			</style>
		</head>
		<body>
			<div id="top">
				<s:if test="listType == 1">
					统计分析&nbsp;&gt;&gt;&nbsp;一般项目&nbsp;&gt;&gt;&nbsp;<span style="font-weight:bold;">警告统计</span>
				</s:if>
				<s:else>
					统计分析&nbsp;&gt;&gt;&nbsp;基地项目&nbsp;&gt;&gt;&nbsp;<span style="font-weight:bold;">警告统计</span>
				</s:else>
			</div>			
		
			<div class="static" >
				<table class="static_table" cellspaning="0" cellpadding="0" style="width: 98%;">
					<tr>
						<td class="staticwd0" colspan="4" style="border-width:1 1px 1px 1px;">警告详细信息（<s:property value="#request.wnall" />条）</td>
					</tr>
					<s:iterator value="#request.num" status="stat">
						<tr>
							<td colspan="4" style="padding-left:10px;">
								<span class="text_red">“<s:property value="#request.num[#stat.index][0]" />”：</span><s:property value="#request.num[#stat.index][1]" />条
							</td>
						</tr>
					</s:iterator>
				</table>	
			</div>
		</body>
</html>