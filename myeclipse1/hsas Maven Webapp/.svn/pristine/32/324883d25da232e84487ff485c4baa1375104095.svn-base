<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%><%--添加对EL表达式的支持--%>
<%@ taglib prefix="s" uri="/struts-tags"%> <%--添加对struts标签的支持 --%>
<!DOCTYPE html>
<html>
	<head>
		<title>湖北省社会科学优秀成果奖励申报评审系统</title>
		<%@ include file="/jsp/base.jsp"%>
	</head>
	<body>
		<%@ include file="/jsp/innerNav.jsp"%>
		<div class="row mySlide">
			<ol class="breadcrumb mybreadcrumb">当前位置：
				<li class="active">常用下载</li>
			</ol>
			
			<div class="col-xs-12">
				<div id="simple_search" style="display: none !important;">
					<form id="search"  class="form-inline" name="search" action="jsp/download/list.json" method="post">
						<input type="hidden" name="sim_sysOptKeyword" value="" id="sim_sysOptKeyword">
						<table border="0" cellspacing="0" cellpadding="0" class="table_td_padding form-group pull-right">
							<tbody>
							<tr>
							 <td align="right">
							<span class="choose_bar">
							<select name="searchType" id="search_searchType" class="select form-control input-sm">
								<option value="-1" selected="selected">--不限--</option>
								<!-- 名称、作者、发表时间、指标类型、模板类型、备注 -->
								<option value="1">分组名称</option>
							</select>
							</span>
									<input id="keyword" class="form-control input-sm keyword" type="text" name="keyword" size="10" value="">
									<input id="list_pagenumber" type="hidden" name="pageNumber" value="" >
									<input id="list_sortcolumn" type="hidden" name="sortColumn" value="0" >
									<input id="list_pagesize" type="hidden" name="pageSize" value="0" >
								</td>
								<td class = "btn-group" style = "display:block">
									 <input id="list_button_query" class="btn btn-default btn-sm" type="button" value="检索" >
								</td>
							</tr>
							</tbody>
						</table>
						<span class="clearfix"></span>
					</form>
				</div>
			
				<!-- 显示列表 -->
				<textarea id="list_template" style="display:none;">
					<table id="list_table" class="table table-striped table-bordered dataTable no-footer" width="100%" border="0" cellspacing="0" cellpadding="0">
						<thead id="list_head">
							<tr class="table_title_tr">
								<td width="20"><input id="check" name="check" type="checkbox" title="点击全选/不选本页所有项目"/></td>
								<td width="50" class="text-center">序号</td>
								<td width="250"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按账号名称排序">下载文件</a></td>
								<td width="80" ><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}"  title="点击按奖励数量排序">发布日期</a></td>
							</tr>
						</thead>
						<tbody>
						{for item in root}
						<tr>
							<td><input type="checkbox" name="entityIds" value="${item.laData[0]}"/></td>
							<td class="text-center">${item.num}</td>
							<td class="table_txt_td"><a href="${item.laData[0]}" title="点击查看详细信息" type="1">${item.laData[1]}</a></td>
							<td >${item.laData[2]}</td>
						</tr>
						{forelse}
						<tr>
							<td align="center">暂无符合条件的记录</td>
						</tr>
						{/for}
						</tbody>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding form-inline">
						<tr class="table_main_tr2">
							<td class = "btn-group">
								<button id = "list_add_displineGroup" class = "btn btn-default btn-sm">添加</button>
								<button id = "list_delete" class = "btn btn-default btn-sm">删除</button>
							</td>
						</tr>
					</table>
				</textarea>
				<s:form id="list" theme="simple" action="delete" namespace="/portal">
					<s:hidden id="pagenumber" name="pageNumber"/>
					<s:hidden id="checkedIds" name="checkedIds"/>
					<div id="list_container"  style="display:none;"></div>
				</s:form>
			</div>
		</div>
		
		<div class="row">
		<%@ include file="/jsp/footer.jsp"%>
		<!-- <script src="js/main.js" type="text/javascript"></script> -->
	</body>
	<script>
		seajs.use('js/download/list.js', function(list){
			list.init();
		})
	</script>
</html>