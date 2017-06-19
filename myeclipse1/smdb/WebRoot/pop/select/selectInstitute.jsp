<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>选择</title>
		<s:include value="/innerBase.jsp" />
		<script type="text/javascript">
			seajs.use('javascript/pop/select/select_institute.js', function(select) {
				$(function(){
					select.init();
				})
			});
		</script>
	</head>

	<body>
		<div style="width:430px;">
			<s:form id="search" theme="simple" action="list" namespace="/selectInstitute">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
					<tr class="table_main_tr">
						<td align="right">
							<label style = "margin-right:5px">所属高校：<input class = "keyword" size = "10" name = "universityName"/></label>
							<label>基地名称：<input class = "keyword" size = "10" name = "departmentName"/></label>
							<input id = "keyword" type = "hidden" name = "keyword"/>
							<s:hidden id="list_pagenumber" name="pageNumber" />
							<s:hidden id="list_sortcolumn" name="sortColumn" />
							<s:hidden id="label" name="label" />
							<s:hidden name="universityId"/>
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
							<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 0}up_css{else}down_css{/if}{/if}" title="点击按研究基地名称排序">所属高校</a></td>
							<td width="2"><img src="image/table_line.gif" width="2" height="21" /></td>
							<td width="180"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 2}up_css{else}down_css{/if}{/if}" title="点击按所属高校排序">研究基地名称</a></td>
						</tr>
					</thead>
					<tbody>
					{for item in root}
						<tr>
							<td><input type="radio" name="entityId" value="${item.laData[0]}" alt="${item.laData[1]}${item.laData[2]}" class="radio_select" /></td>
							<td></td>
							<td class="item_num">${item.num}</td>
							<td></td>
							<td class="i_name">${item.laData[1]}</td>
							<td></td>
							<td class="u_name">${item.laData[2]}</td>
						</tr>
					{forelse}
						<tr>
							<td align="center">暂无符合条件的记录</td>
						</tr>
					{/for}
					</tbody>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
					<tr class="table_main_tr3"><td></td></tr>
				</table>
			</textarea>
			<s:include value="/pop/select/radioBottom.jsp" />
		</div>
	</body>
</html>