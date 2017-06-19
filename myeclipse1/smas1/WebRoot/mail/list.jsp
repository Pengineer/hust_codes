<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>邮件管理</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/mail/mail.css" />
	</head>
	<body>
		<div id="top">
			系统管理&nbsp;&gt;&gt;&nbsp;<span class="text_red">邮件管理</span>
		</div>
		
		<div class="main">
			<div class="main_content">
				<s:form id="search" theme="simple" action="list" namespace="/mail">
					<table class="table_bar">
						<tr height="28px">
							<td align="right">
									<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'0':'主题','1':'正文'}" />
									<s:textfield id="keyword" name="keyword" cssClass="input wd2" />
									<s:hidden id="list_pagenumber" name="pageNumber" />
									<s:hidden id="list_sortcolumn" name="sortColumn" />
									<s:hidden id="list_pagesize" name="pageSize" />
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
								<td class="border0"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按主题排序">主题</a></td>
								<td class="border0" style="width:150px;"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按创建时间排序">创建时间</a></td>
								<td class="border0" style="width:150px;"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按发送完毕时间排序">发送完毕时间</a></td>
							</tr>
						</thead>
						<tbody>
						{for item in root}
							<tr class="tr_list2">
								<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
								<td>${item.num}</td>
								<td style="text-align:left;"><a id="${item.laData[0]}" class="linkView" href="" title="点击查看详细信息">${item.laData[1]}</a></td>
								<td>${item.laData[2]}</td>
								<td>${item.laData[3]}</td>
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
						<td class="border1" style="width:40px;"><input id="list_add" type="button" value="添加模板" class="btn" /></td>
							<td class="border1" style="width:40px;"><input id="list_delete" type="button" value="删除" class="btn" /></td>
							<td class="border1" style="width:40px;"><input id="list_send_all" type="button" value="发送全部邮件" class="btn2" /></td>
							<td class="border1" style="width:40px;"><input id="list_cancel_all" type="button" value="取消发送全部邮件" class="btn3" /></td>
						</tr>
					</table>
				</textarea>
				
				<s:form id="list" theme="simple" action="delete" namespace="/mail">
					<div id="list_container" style="display:none;"></div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/mail/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>