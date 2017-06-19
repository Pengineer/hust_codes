<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>选择</title>
		<s:include value="/innerBase.jsp" />
		<link href="tool/jquery.ui/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
			seajs.use('javascript/pop/select/select_university.js', function(select) {
				$(function(){
					select.init();
				})
			});
		</script>
	</head>

	<body>
		<div style="width:430px;">
			<s:form id="search" theme="simple" action="list" namespace="/selectUniversity">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
					<tr class="table_main_tr">
						<td align="right"><span class="choose_bar">
							<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--不限--"
								list="#{'1':'高校名称','2':'高校代码','3':'省份名称'}" />
						</span><s:textfield id="keyword" name="keyword" cssClass="keyword" size="10" />
							<s:hidden id="list_pagenumber" name="pageNumber" />
							<s:hidden id="list_sortcolumn" name="sortColumn" />
							<s:hidden id="label" name="label" />
						</td>
						<td width="66"><input id="list_button_query" type="button" value="检索" class="btn1" /></td>
					</tr>
				</table>
			</s:form>
			
			<textarea id="list_template" style="display:none;">
				<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
					<thead id="list_head">
						<tr class="table_title_tr3">
							<td width="30">选择</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="21" /></td>
							<td width="30">序号</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="21" /></td>
							<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 0}up_css{else}down_css{/if}{/if}" title="点击按高校名称排序">高校名称</a></td>
							<td width="2"><img src="image/table_line.gif" width="2" height="21" /></td>
							<td width="100"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按高校代码排序">高校代码</a></td>
							<td width="2"><img src="image/table_line.gif" width="2" height="21" /></td>
							<td width="120"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 2}up_css{else}down_css{/if}{/if}" title="点击按省份名称排序">省份名称</a></td>
						</tr>
					</thead>
					<tbody>
					{for item in root}
						<tr>
							<td><input type="radio" name="entityId" value="${item.laData[0]}" alt="${item.laData[1]}" class="radio_select" /></td>
							<td></td>
							<td>${item.num}</td>
							<td></td>
							<td>${item.laData[1]}</td>
							<td></td>
							<td>${item.laData[2]}</td>
							<td></td>
							<td>${item.laData[3]}</td>
						</tr>
					{forelse}
						<tr>
							<td align="center">暂无符合条件的记录</td>
						</tr>
					{/for}
					</tbody>
				</table>
				<table width = "100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
					<tr class="table_main_tr3"><td></td></tr>
				</table>
			</textarea>
			<s:include value="/pop/select/radioBottom.jsp" />
		</div>
	</body>
</html>