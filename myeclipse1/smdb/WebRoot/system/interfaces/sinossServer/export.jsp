<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>导出</title>
		<s:include value="/innerBase.jsp" />
	</head>
	<body>
		<div class="link_bar">
			当前位置：接口管理&nbsp;&gt;&nbsp;社科网服务端&nbsp;&gt;&nbsp;导出
		</div>
	    <s:form action="export" namespace="/system/interfaces/sinossServer">
		    <div class="main">
				<div class="main_content">
					<div class="title_statistic">参数配置</div>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">导出接口：</span></td>
							<td class="table_td31">
								<table width="100%" border="0" cellspacing="0" cellpadding="2" style="border: 1px solid rgb(170, 170, 170);padding-bottom:5px;">
									<tr>
										<td style="width:15%;">
											<input type="radio" name="export_interface" value="ApplicationResult" disabled="true"/>
											<span>申请结果</span>
										</td>
										<td style="width:15%;">
											<input type="radio" name="export_interface" value="MidinspectionResult" disabled="true"/>
											<span>中检结果</span>
										</td>
										<td style="width:15%;">
											<input type="radio" name="export_interface" value="VariationResult" disabled="true"/>
											<span>变更结果</span>
										</td>
										<td style="width:15%;">
											<input type="radio" name="export_interface" value="EndinspectionResult" disabled="true"/>
											<span>结项结果</span>
										</td>
										<td style="width:15%;">
											<input type="radio" name="export_interface" value="MidinspectionRequired"/>
											<span>需中检数据</span>
										</td>																			
									</tr>
								</table>
							</td>	
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">项目类型：</span></td>
							<td class="table_td3">
								<table width="100%" border="0" cellspacing="0" cellpadding="2" style="border: 1px solid rgb(170, 170, 170);padding-bottom:5px;">
									<tr>
										<td style="width:15%;">
											<input type="checkbox" name="export_projectType" value="all"/>
											<span>所有项目</span>
										</td>
										<td style="width:15%;">
											<input type="checkbox" name="export_projectType" value="general"/>
											<span>一般项目</span>
										</td>
										<td style="width:15%;">
											<input type="checkbox" name="export_projectType" value="instp"/>
											<span>基地项目</span>
										</td>										
									</tr>
								</table>
							</td>							
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span style="_padding-top:5px;font-weight:bold;">项目年度：</td>
							<td class="table_td3">
								<s:select cssClass="select" id="startYear" name="startYear" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--不限--" />
								至
								<s:select cssClass="select" id="endYear" name="endYear" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--不限--" />
							</td>
						</tr>
					</table>
					<div class="btn_div_view">
						<input id="submit" class="btn2" type="submit" value="导出" />
					</div>
				</div>
			</div>
		</s:form>
		<script type="text/javascript">
			seajs.use('javascript/system/interfaces/sinossServer/export.js', function(edit) {
				edit.init();
			});
		</script>
	</body>
</html>