<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>申请数据入库</title>
			<s:include value="/innerBase.jsp" />
		</head>
		<body>
		<div class="link_bar">
		当前位置：数据入库&nbsp;&gt;&nbsp;
		中检数据入库
	</div>
		<div class="main">
			<div class="main_content">
				<div id = "systemOption">
				<div class="title_statistic">参数配置</div>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
			
					<tr class="table_tr2">
						<td class="table_td2" width="110"><span class=""
							style="_padding-top:5px;font-weight:bold;"><s:text
									name="项目类型" />：</span>
						</td>
						<td class="table_td3"><s:select id="search_range_tabs"
								cssClass="select" name="projectType"
								list="#{'general':'一般项目', 'instp':'基地项目','special':'专项任务项目'}" />
						</td>
						<input  id="importType" name="importType" value="midInspection" type ="hidden"/>
						<td class="table_td4">
							<input id = "startImport" class = "btn1" value = "开始入库">
						</td>
					</tr>
				
			</table>	
				
			</div>
			
			<div id="importResult" style = "display:none">
				<div id="tabs" class="p_box_bar">
					<ul>
						
										
					</ul>
				</div>
				<%--<button id = "showDetail"> 显示详情</button>
				--%><div id = "container" class = "p_box_body" style = "max-height:400px;overflow-y:scroll;display:none">
				
				</div>
			</div>
		</div>
			
			<script>
			seajs.use('javascript/dataProcessing/fromMidToDB/dataImporter.js', function(dataImporter) {
				$(function(){
					dataImporter.init();
				})
			});
		</script>
		</body>
	
</html>
