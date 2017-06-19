<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>选择</title>
		<s:include value="/innerBase.jsp" />
		<script type="text/javascript">
			seajs.use('javascript/project/general/midinspection/midinspectionRequired.js', function(midinspectionRequired) {
				$(function(){
					midinspectionRequired.init();
				})
			});
		</script>
	</head>

	<body>
		<div style="width:840px;">
			<s:form id="search" theme="simple" action="list" namespace="/project/general/midinspection/apply/midRequired">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
					<tr class="table_main_tr">
						<td align="right"><span class="choose_bar">
							<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--不限--"
								list="#{'1':'项目名称','6':'项目年度','2':'项目负责人','3':'依托高校','4':'项目类别','5':'学科门类'}" />
						</span><s:textfield id="keyword" name="keyword" cssClass="keyword" size="10" />
							<s:hidden id="list_pagenumber" name="pageNumber" />
							<s:hidden id="list_sortcolumn" name="sortColumn" />
						</td>
						<td width="60"><input id="list_button_query" type="button" value="检索" class="btn1" /></td>
					</tr>
				</table>
			</s:form>
			
			<textarea id="list_template" style="display:none;">
				<table id="list_table" width="840" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
					<thead id="list_head">
						<tr class="table_title_tr">
							<td width="30">序号</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="21" /></td>
							<td ><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目名称排序">项目名称</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="21" /></td>
							<td width="80"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目负责人排序">项目负责人</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="21" /></td>
							<td width="80"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目批准号排序">项目批准号</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="21" /></td>
							<td width="70"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按依托高校排序">依托高校</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="21" /></td>
							<td width="90"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按依托高校代码排序">依托高校代码</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="21" /></td>
							<td width="70"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目子类排序">项目子类</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="21" /></td>
							<td width="70"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按学科门类排序">学科门类</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="21" /></td>
							<td width="70"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目年度排序">项目年度</td>
						</tr>
					</thead>
					<tbody>
					{for item in root}
						<tr>
							<td>${item.num}</td>
							<td></td>
							<td>${item.laData[1]}</td>
							<td></td>
							<td>${item.laData[5]}</td>
							<td></td>
							<td>${item.laData[2]}</td>
							<td></td>
							<td>${item.laData[3]}</td>
							<td></td>
							<td>${item.laData[4]}</td>
							<td></td>
							<td>${item.laData[6]}</td>
							<td></td>
							<td>${item.laData[7]}</td>
							<td></td>
							<td>${item.laData[8]}</td>
						</tr>
					{forelse}
						<tr>
							<td align="center">暂无符合条件的记录</td>
						</tr>
					{/for}
					</tbody>
				</table>
				<table width="840" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
					<tr class="table_main_tr3">
						<td width="4"></td>
						<td width="58"><input id="midRequired_export" type="button" class="btn1" value="导出" /></td>
					</tr>
					
				</table>
			</textarea>
			<div id="list_container" style="display:none; width:820; overflow-y:hidden; overflow-x:hidden;"></div>
		</div>
	</body>
</html>