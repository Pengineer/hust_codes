<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<textarea id="list_template" style="display:none;">
	<table id="list_table" width="98%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
		<thead id="list_head">
			<tr class="table_title_tr">
				<td width="5%">序号</td>
				<td width="2%"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="20%"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按照名称排序">名称</a></td>
				<td width="2%"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="20%"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按照描述排序">描述</a></td>
				<td width="2%"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="10%"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按照代码排序">代码</a></td>
				<td width="2%"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="8%"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按照是否可用排序">是否可用</a></td>
				<td width="2%"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="15%"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按照代码标准排序">代码标准</a></td>
				<td width="2%"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按照缩写排序">缩写</a></td>
			</tr>
		</thead>
		<tbody>
		{for item in root}
			<tr>
				<td>${item.num}</td>
				<td></td>
				<td>${item.laData[0]}</td>
				<td></td>
				<td>${item.laData[1]}</td>
				<td></td>
				<td>${item.laData[2]}</td>
				<td></td>
				<td>
					{if item.laData[3] == 0}不可用
					{elseif item.laData[3] == 1}可用
					{else}
					{/if}
				</td>
				<td></td>
				<td>${item.laData[4]}</td>
				<td></td>
				<td>${item.laData[5]}</td>
			</tr>
		{forelse}
			<tr>
				<td align="center">暂无符合条件的记录</td>
			</tr>
		{/for}
		</tbody>
	</table>
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
		<tr class="table_main_tr2">
			<sec:authorize ifAllGranted="ROLE_SYSTEM_CONFIG">
				<td ><input id="list_download" type="button" class="btn2" value="下载XML" /></td>
			</sec:authorize>
		</tr>
	</table>
	</textarea>
	<s:form id="search" theme="simple" action="list" namespace="/system/option">
		<s:hidden id="list_pagenumber" name="pageNumber" value="1" />
		<s:hidden id="list_sortcolumn" name="sortColumn" />
		<s:hidden id="list_pagesize" name="pageSize" value="10" />
		<s:hidden id="update" name="update" value="1" />
		<div id="list_container" style="display:none;"></div>
	</s:form>
	<script type="text/javascript">
				seajs.use('javascript/system/option/list.js', function(list) {
					$(function(){
						list.init();
					})
				});
			</script>
</html>
