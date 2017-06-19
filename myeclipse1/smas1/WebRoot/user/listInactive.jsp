<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>未启用用户</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/user/user.css" />
		</head>
		<body>
			<div id="top">
				系统管理&nbsp;&gt;&gt;&nbsp;用户管理&nbsp;&gt;&gt;&nbsp;<span class="text_red">未启用用户</span>
			</div>
			
			<div class="main">
				<div class="main_content">
					<s:form id="search" theme="simple" action="list" namespace="/user">
						<table class="table_bar">
							<tr height="28px">
								<td align="right">
									<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'账号','2':'中文名'}" />
									<s:textfield id="keyword" name="keyword" cssClass="input wd2" />
									<s:hidden id="list_pagenumber" name="pageNumber" />
									<s:hidden id="list_sortcolumn" name="sortColumn" />
									<s:hidden id="list_pagesize" name="pageSize" />
									<s:hidden id="userstatus" name="userstatus" value="-1" />
								</td>
								<td width="50px" align="center"><input id="list_button_query" type="button" value="检索" class="btn" /></td>
							</tr>
						</table>
					</s:form>
					
					<textarea id="list_template" style="display:none;">
						<table id="list_table" class="table_list" cellspacing="0" cellpadding="0">
							<thead id="list_head">
								<tr class="tr_list1">
									<td class="wd0 border0"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有用户" /></td>
									<td class="wd1 border0">序号</td>
									<td class="border0" style="width:90px;"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按账户排序">账号</a></td>
									<td class="border0" style="width:90px;"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title=点击按中文名排序">中文名</a></td>
									<td class="border0" style="width:90px;"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按注册时间排序">注册时间</a></td>
									<td class="border0" style="width:90px;"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按启用时间排序">启用时间</a></td>
									<td class="border0" style="width:90px;"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按账号有效期排序">有效期</a></td>
									<td class="wd_auto border1">角色分配</td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr class="tr_list2">
									<td class="wd0"><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td class="wd1">${item.num}</td>
									<td><a id="${item.laData[0]}" class="linkView" href="" title="点击查看详细信息" type="-1">${item.laData[1]}</a></td>
									<td><a id="${item.laData[0]}" class="linkView" href="" title="点击查看详细信息" type="-1">${item.laData[2]}</a></td>
									<td>${item.laData[3]}</td>
									<td>${item.laData[4]}</td>
									<td>${item.laData[5]}</td>
									<td><a href="role/viewUserRole.action?userId=${item.laData[0]}&userstatus=-1" title="角色分配>">
										{if item.laData[6]!= ""}${item.laData[6]}{else}[未分配]{/if}
										</a>
									</td>
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
								<td class="border1" style="width:40px;"><input id="list_delete" type="button" value="删除" class="btn" /></td>
								<td class="border1" style="width:40px;"><input type="button" value="启用" onclick="aprovSelected('entityIds', 'list', 1);" class="btn" /></td>
							</tr>
						</table>
					</textarea>
					
					<s:form id="list" theme="simple" action="delete" namespace="/user">
						<input type="hidden" name="userstatus" value="-1" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/user/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>