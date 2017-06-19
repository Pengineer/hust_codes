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
	</head>
	<body>
		<div id="top">
			专家管理&nbsp;&gt;&gt;&nbsp;
			
				<s:if test="expertType == 0 && isReviewer == 0">退评专家&nbsp;&gt;&gt;&nbsp;<span class="text_red">内部专家</span></s:if>
				<s:elseif test="expertType == 0 && isReviewer == 1">参评专家&nbsp;&gt;&gt;&nbsp;<span class="text_red">内部专家</span></s:elseif>
				<s:elseif test="expertType == 1 && isReviewer == 0">退评专家&nbsp;&gt;&gt;&nbsp;<span class="text_red">外部专家</span></s:elseif>
				<s:else>参评专家&nbsp;&gt;&gt;&nbsp;<span class="text_red">外部专家</span></s:else>
			
		</div>
		<div class="main">
			<div class="main_content">
				<s:form id="search" theme="simple" action="list" namespace="/expert">
					<table class="table_bar">
						<tr height="28px">
							<td width="50px" align="center"><input type="button" value="导出" class="btn" onclick="exportExpert();"/></td>
							<td align="right">
								<s:if test="isReviewer == 0"><s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'姓名','2':'高校名称','3':'专业职称','4':'学科代码','5':'学科名称','6':'退评原因'}" /></s:if>
								<s:else><s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'姓名','2':'高校名称','3':'专业职称','4':'学科代码','5':'学科名称'}" /></s:else>
								<s:textfield id="keyword" name="keyword" cssClass="input wd2" />
								<s:hidden id="list_pagenumber" name="pageNumber" />
								<s:hidden id="list_sortcolumn" name="sortColumn" />
								<s:hidden id="list_pagesize" name="pageSize" />
								<s:hidden id="expertType" name="expertType" />
								<s:hidden id="isReviewer" name="isReviewer" />
							</td>
							<td width="50px" align="center"><input id="list_button_query" type="button" value="检索" class="btn" /></td>
						</tr>
					</table>
				</s:form>
				
				<textarea id="list_template" style="display:none;">
					<table id="list_table" class="table_list" cellspacing="0" cellpadding="0">
						<thead id="list_head">
							<tr class="tr_list1">
								<td class="wd0 border0"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有专家" /></td>
								<td class="wd1 border0">序号</td>
								<td class="border0" style="width:60px;"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按姓名排序">姓名</a></td>
								<td class="border0" style="width:110px;"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按高校名称排序">高校名称</a></td>
								<td class="border0" style="width:80px;"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按专业职称排序">专业职称</a></td>
								<td class="border0" style="width:160px;"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按学科排序">学科代码及名称</a></td>
								<s:if test="isReviewer == 0">
									<td class="border0" style="width:100px;"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按退评原因排序">退评原因</a></td>
								</s:if>
							</tr>
						</thead>
						<tbody>
						{for item in root}
							<tr class="tr_list2">
								<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
								<td>${item.num}</td>
								<td>
									<a id="${item.laData[0]}" class="linkView" href="" title="点击查看详细信息">${item.laData[1]}</a>
									<s:if test="(#session.visitor.userRight.contains(\"重点信息管理\")) || (#session.visitor.user.issuperuser == 1)">
									<a class="toggleKey" href="javascript:void(0)" alt="${item.laData[0]}" iskey="${item.laData[6]}">
									{if item.laData[6] == 1}<img src="image/error2.png" title="是重点人，点击可切换" />{else}<img src="image/highlight.png" title="非重点人，点击可切换" />{/if}
									</a>
									</s:if>
								</td>
								<td>${item.laData[2]}</td>
								<td>${item.laData[3]}</td>
								<td>${item.laData[4]}</td>
								<s:if test="isReviewer == 0">
									<td style="text-align:left; padding-left:0;">${item.laData[5]}</td>
								</s:if>
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
							<td class="border1" style="width:40px;"><input id="list_add" type="button" value="添加" class="btn" /></td>
							<td class="border1" style="width:40px;"><input id="list_delete" type="button" value="删除" class="btn isCurYear" /></td>
							<s:if test="isReviewer == 0">
								<td class="border1" style="width:40px;"><input id="review" type="button" value="参评" class="btn isCurYear" /></td>
							</s:if>
							<s:else>
								<td class="border1" style="width:40px;"><input id="unReview" type="button" value="退评" class="btn isCurYear" /></td>
							</s:else>
						</tr>
					</table>
				</textarea>
				
				<s:form id="list" theme="simple" action="delete" namespace="/expert">
					<div id="list_container" style="display:none;"></div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/autocomplete/jquery.autocomplete.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/expert/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/expert/expert.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>