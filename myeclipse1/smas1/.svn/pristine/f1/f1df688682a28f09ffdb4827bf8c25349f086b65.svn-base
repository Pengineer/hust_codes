<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<base href="<%=basePath%>" />
			<title>项目清单</title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="css/expert/expert.css" />
			<link rel="stylesheet" type="text/css" href="tool/jquery-ui-1.8.16.custom/css/redmond/jquery-ui-1.8.16.custom.css" />
		</head>

		<body>
			<div style="width: 720px;height:353px;">
<%--				<input type="hidden" name="isReviewable" value="${isReviewable}" />--%>
				<form>
					<div class="base_info expand" style="cursor:pointer;margin-top:0px;">
						<div class="title_dis">选择专家职称</div>
					</div>
					<div class="hide" style="display:none;">
						<table style="width:100%;padding:10px;">
							<!-- 
							<s:iterator value="#application.specialityTitleRank" status="stat">
								<s:if test="(#stat.index)%3==0"><tr></s:if>
									<td style="width:33.3%;">
										<input type="checkbox" checked="checked" name="specialityTitles[<s:property value="#stat.index"/>]" value="<s:property value="specialityTitleRank[#stat.index].code"/>"/>
										<%--<s:property value="value"/>--%>
									</td>
								<s:if test="(#stat.index+1)%3==0"></tr></s:if>
							</s:iterator>
							 -->
							 <tr>
							 	<td style="text-align:center;"><input type="radio" id="allSpecialityTitles" checked="checked" name="chooseSpecialityTitles" value="所有"/>所有职称</td>
							 	<td style="text-align:center;"><input type="radio" name="chooseSpecialityTitles" value="部分"/>指定职称</td>
							 </tr>
						</table>
						 <div id="partSpecialityTitles" style="display:none;">
						 	<table style="width:100%;padding:10px;border:1px solid #BAC3D2">
							 	<tr>
								 	<td style="text-align:center;"><input type="checkbox" name="specialityTitles[0]" value="正高级"/>正高级职称</td>
								 	<td style="text-align:center;"><input type="checkbox" name="specialityTitles[1]" value="副高级"/>副高级职称</td>
								 	<td style="text-align:center;"><input type="checkbox" name="specialityTitles[2]" value="中级"/>中级职称</td>
								 	<td style="text-align:center;"><input type="checkbox" name="specialityTitles[3]" value="助理级"/>初（助理）级职称</td>
								 	<td style="text-align:center;"><input type="checkbox" name="specialityTitles[4]" value="初级"/>初级（员级）职称</td>
							 	</tr>
						 	</table>
						 </div>
					</div>
					
					<div class="base_info expand" style="cursor:pointer;">
						<div class="title_dis">选择专家学科</div>
					</div>
					
					<div class="hide" style="display:none;height:100px;overflow:scroll;overflow-y: scroll;">
						<table style="width:100%;padding:10px 10px 0 10px;">
							<tr><td colspan="3"><input type="checkbox" name="selectAllDisciplines" id="selectAllDisciplines" checked="checked" value="全选"/> 全选</td></tr>
							<s:iterator value="disList" status="stat">
								<s:if test="(#stat.index)%3==0"><tr></s:if>
									<td style="width:33.3%;">
										<input type="checkbox" checked="checked" name="disciplines[<s:property value="#stat.index"/>]" value="<s:property value="disList[#stat.index].code"/>"/>
										<s:property value="disList[#stat.index].code"/><s:property value="disList[#stat.index].name"/>
									</td>
								<s:if test="(#stat.index+1)%3==0"></tr></s:if>
							</s:iterator>
						</table>
					</div>
					
					<div class="base_info expand" style="cursor:pointer;">
						<div class="title_dis">选择专家参评项目类型</div>
					</div>
					
					<div class="hide" style="display:none;">
						<table style="width:100%;padding:10px 10px 0 10px;">
							<tr>
								<td style="width:33.3%;text-align:center;"><input type="radio" name="reviewExpertProjectType" value="所有项目" checked="checked"> 所有项目</td>
								<td style="width:33.3%;text-align:center;"><input type="radio" name="reviewExpertProjectType" value="一般项目"> 一般项目</td>
								<td style="width:33.3%;text-align:center;"><input type="radio" name="reviewExpertProjectType" value="基地项目"> 基地项目</td>
							</tr>
						</table>
					</div>
					
					<div class="base_info expand" style="cursor:pointer;">
						<div class="title_dis">选择专家参评情况</div>
					</div>
					
					<div class="hide" style="display:none;">
						<table style="width:100%;padding:10px 10px 0 10px;">
							<tr>
								<td style="width:33.3%;text-align:center;"><input type="checkbox" name="reviewExpertType[0]" value="所有参评专家"> 所有参评专家</td>
								<td style="width:33.3%;text-align:center;"><input type="checkbox" name="reviewExpertType[1]" value="参与匹配专家" checked="checked"> 参与匹配专家</td>
								<td style="width:33.3%;text-align:center;"><input type="checkbox" name="reviewExpertType[2]" value="已匹配专家"> 已匹配专家</td>
							</tr>
						</table>
					</div>
					<div class="base_info expand" style="cursor:pointer;">
						<div class="title_dis">选择专家所在高校</div>
					</div>
					
					<div class="hide" style="display:none;">
						<table style="width:100%;padding:10px 10px 0 10px;">
							<tr>
								<td style="width:33.3%;text-align:center;"><input type="radio" name="universityType" value="0" checked="checked"> 所有高校</td>
								<td style="width:33.3%;text-align:center;"><input type="radio" name="universityType" value="1"> 部属高校</td>
								<td style="width:33.3%;text-align:center;"><input type="radio" name="universityType" value="2"> 地方高校</td>
							</tr>
						</table>
					</div>
					
					<div class="base_info expand" style="cursor:pointer;">
						<div class="title_dis">设置数量上限</div>
					</div>
					
					<div class="hide" style="display:none;">
						<table style="width:100%;padding:10px 10px 0 10px;">
							<tr>
								<td style="text-align:center;" colspan="3">数量上限：<input type="input" name="maximunNumber" value="5000"></td>
							</tr>
						</table>
					</div>
					
					<div class="btn_div">
						<input id="confirm" type="button" class="btn" value="确定" />
						<input id="cancel" type="button" class="btn" value="取消" />
					</div>
				</form>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/jquery-ui-1.8.16.custom/js/jquery-ui-1.8.16.custom.min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery-ui-timepicker-addon.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/pop/pop_init.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/expert/popExport.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>