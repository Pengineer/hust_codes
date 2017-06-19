<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored ="true"%><%--添加对EL表达式的支持--%>
<%@ taglib prefix="s" uri="/struts-tags"%> <%--添加对struts标签的支持 --%>
<!DOCTYPE html>
<html>
    <head>
        <title>湖北省社会科学优秀成果奖励申报评审系统</title>
        <%@ include file="/jsp/base.jsp"%>
    </head>
    <body>
   		<%@ include file="/jsp/nav.jsp"%>
   		<a name="top" id="top"></a>
			<div class="row mySlide">
			<ol class="breadcrumb mybreadcrumb">当前位置：
			  <li><a href="#">系统管理</a></li>
			  <li><a href="#">账户管理</a></li>
			  <li class="active">社科联管理员账号</li>
			</ol>
			    <div class="col-xs-12 main-content">
				   
					<div id="simple_search" style="display: none !important;">
		                <form id="search" name="search" action="account/list.action" method="post"
		                      class="form-inline">
		                    <input type="hidden" name="sim_sysOptKeyword" value="" id="sim_sysOptKeyword">
		                    <table border="0" cellspacing="0" cellpadding="0" class="table_td_padding form-group pull-right">
		                        <tbody>
		                        <tr>
		                         <td align="right">
								<span class="choose_bar">
								<select name="searchType" id="search_searchType" class="select form-control input-sm">
		                            <option value="-1" selected="selected">--不限--</option>
		                            <!-- 名称、作者、发表时间、指标类型、模板类型、备注 -->
		                            <option value="1">账户名称</option>
		                            <option value="2">创建时间</option>
		                            <option value="3">有效时间</option>
		                            <option value="4">账号状态</option>
		                            <option value="5">角色分配</option>
		                        </select>
								</span>
		                                <input class="form-control input-sm" type="text" name="keyword" size="10" value="" id="keyword"
		                                       class="keyword">
		                                <input type="hidden" name="pageNumber" value="" id="list_pagenumber">
		                                <input type="hidden" name="sortColumn" value="0" id="list_sortcolumn">
		                                <input type="hidden" name="pageSize" value="0" id="list_pagesize">
		                            </td>
		                            <td class = "btn-group" style = "display:block">
		                                 <input id="list_button_query" type="button" value="检索" class="btn btn-default btn-sm">
		                            </td>
		                        </tr>
		                        </tbody>
		                    </table>
		                    <span class="clearfix"></span>
		                </form>
		            </div>
					<!-- 高级检索 -->
				<!-- 显示列表 -->
				<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table table-striped table-bordered dataTable no-footer">
			                <thead id="list_head">
			                <tr class="table_title_tr">
			                	<td width="20"><input id="check" name="check" type="checkbox" title="点击全选/不选本页所有项目"/></td>
			                    <td width="50" class="text-center">序号</td>
			                    <td width=""><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按账号名称排序">账号名称</a></td>
			                    <td width="150" ><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}"  title="点击按创建时间排序">创建时间</a></td>
			                    <td width="150" ><a id="sortcolumn2" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}"  title="点击按有效时间排序">有效时间</a></td>
			                    <td width="100"><a id="sortcolumn3" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}"  title="点击按账号状态排序">账号状态</a></td>
			                    <td width="100"><a id="sortcolumn4" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}"  title="点击按角色分配排序">角色分配</a></td>
			                </tr>
			                </thead>
			                <tbody>
			                {for item in root}
			                <tr>
			                	<td><input type="checkbox" name="entityIds" value="${item.laData[0]}"/></td>
			                    <td class="text-center">${item.num}</td>
			                    <td class="table_txt_td"><a id="${item.laData[0]}"  class = "link1" title="点击查看详细信息" type="1">${item.laData[1]}</a></td>
			                    <td >${item.laData[2]}</td>
			                    <td >${item.laData[3]}</td>
			                    <td >${item.laData[4]}</td>
			                    <td >${item.laData[5]}</td>
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
			                    	<button id = "list_delete" class = "btn btn-default btn-sm">删除</button>
			                    	<button id = "" class = "btn btn-default btn-sm">启用</button>
			                    	<button id = "" class = "btn btn-default btn-sm">停用</button>
			                    	<button id = "" class = "btn btn-default btn-sm">分配角色</button>
			                    	<button id = "" class = "btn btn-default btn-sm">导出</button>
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
		<script>
		    seajs.use("js/system/account/administrator/list.js", function(list) {
		         list.init(); 
		    });
		</script>
	</body>
</html>