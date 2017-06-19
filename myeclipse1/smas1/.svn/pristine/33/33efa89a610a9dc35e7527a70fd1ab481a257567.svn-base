<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>专家管理</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/expert/expert.css" />
		<link rel="stylesheet" type="text/css" href="css/autocomplete/jquery.autocomplete.css" />
		<link rel="stylesheet" type="text/css" href="css/project/project.css" />
	</head>
	
	<body>
		<div class="main">
			<div class="main_content">
				<textarea id="list_template" style="display:none;">
					<table id="list_table" class="table_list" cellspacing="0" cellpadding="0">
						<thead id="list_head">
							<tr class="tr_list1">
								<td class="wd0 border0">选择</td>
								<td class="wd1 border0">序号</td>
								<td class="border0" style="width:60px;"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按姓名排序">姓名</a></td>
								<td class="border0" style="width:110px;"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按高校名称排序">高校名称</a></td>
								<td class="border0" style="width:80px;"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按专业职称排序">专业职称</a></td>
								<td class="border0" style="width:160px;"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按学科排序">学科代码及名称</a></td>
							</tr>
						</thead>
						<tbody>
						{for item in root}
							<tr class="tr_list2">
								<td><input type="radio" name="newExpertIds" value="${item.laData[0]}" /></td>
								<td>${item.num}</td>
								<td>${item.laData[1]}</td>
								<td>${item.laData[2]}</td>
								<td>${item.laData[3]}</td>
								<td>${item.laData[4]}</td>
							</tr>
						{forelse}
							<tr>
								<td align="center">暂无符合条件的记录</td>
							</tr>
						{/for}
						</tbody>
					</table>
					
					<table class="table_list">
						<tr class="tr_list1" style="border-top-width: 0px;">
							<td class="border1" style="width:40px;"><input id="list_pop_add" type="button" value="添加" class="btn" /></td>
						</tr>
					</table>
				</textarea>
				
				<div id="poj_info" class="base_info"">
					<div class="title_dis">专家信息列表</div>
				</div>	
				<s:form id="search" theme="simple" action="list" namespace="/expert/select">
					<table class="table_bar">
						<tr height="28px">
							<td align="right">
									<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'姓名','2':'高校名称','3':'专业职称','4':'学科代码','5':'学科名称'}" />
									<s:textfield id="keyword" name="keyword" cssClass="input wd2" />
									<s:hidden id="list_pagenumber" name="pageNumber" />
									<s:hidden id="list_sortcolumn" name="sortColumn" />
									<s:hidden id="list_pagesize" name="pageSize" />
							</td>
							<td width="50px" align="center"><input id="list_button_query" type="button" value="检索" class="btn" /></td>
						</tr>
					</table>
				</s:form>
				
				<div id="list_container" style="display:none;"></div>
			</div>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/autocomplete/jquery.autocomplete.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/expert/select/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>