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
			  <li><a href="#">数据字典管理</a></li>
			  <li><a href="#">学科分组</a></li>
			  <li class="active">详情</li>
			</ol>
			    <div class="col-xs-12">
				   <div class="btn-group pull-right view-controler" role="group" aria-label="...">
			  			<button type="button" class="btn btn-sm btn-default" id = "view_add">添加</button>
			  			<button type="button" class="btn btn-sm btn-default" id = "view_mod">修改</button>
			  			<button type="button" class="btn btn-sm btn-default" id = "view_del">删除</button>
			  			<button type="button" class="btn btn-sm btn-default" id = "view_prev">上一条</button>
			  			<button type="button" class="btn btn-sm btn-default" id = "view_next">下一条</button>
			  			<button type="button" class="btn btn-sm btn-default" id = "view_back">返回</button>
					</div>
					<span class="clearfix"></span><!-- 重要!! 用于清除按键组浮动 -->
					
					<div class="panel panel-default panel-view">
					  <div class="panel-heading"><strong>基本信息</strong></div>
					  <div class="panel-body">
					    <table class="table table-striped view-table">
					      <tbody>
							<tr>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td class = "text-right" width = "100">学科名称：</td><td class = "text-left" colspan='4'>占位符</td>
							</tr>
							<tr>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td width = "100" class = "text-right">成果数量：</td><td class = "text-left" width='200'>100</td>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td width = "70" class = "text-right" >届数：</td><td class = "text-left" >2015 </td>
							</tr>
							<tr>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td class = "text-right">初评专家：</td><td class = "text-left" colspan='4'>占位符</td>
							</tr>
							<tr>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td class = "text-right">复评专家：</td><td class = "text-left" colspan='4'>占位符</td>
							</tr>
						  </tbody>
			    		</table>
					  </div>
					</div> <!-- end <div class="panel panel-default"> -->
					
					<div class="panel panel-default panel-view">
					  <div class="panel-heading"><strong>成果数量</strong></div>
					  <div class="panel-body">
					  <div id="simple_search" style="display: none !important;">
		                <form id="search" name="search" action="jsp/system/account/list.json" method="post"
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
		                            <option value="1">名称</option>
		                            <option value="2">作者</option>
		                            <option value="3">发表年份</option>
		                            <option value="4">指标类型</option>
		                            <option value="5">模板类型</option>
		                            <option value="6">备注</option>
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
		                                 <input id="list_search_more" type="button" value="更多条件" class="btn btn-default btn-sm">
		                            </td>
		                        </tr>
		                        </tbody>
		                    </table>
		                    <span class="clearfix"></span>
		                </form>
		            </div>
		            <!-- 高级检索 -->
		            <div id="adv_search"  style="display:none;">
		                <form id="advSearch" action="advSearch" theme="simple" namespace="" method="post">
		                    <input type="hidden" name="adv_sysOptKeyword" value="" id="adv_sysOptKeyword">
		                    <div class="adv_content">
		                        <table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
		                            <tr class="adv_tr">
		                                <td class="adv_td1" width="65" align="right">名称：</td>
		                                <td class="adv_td1" width="200"><input type="text" class="form-control input-sm" name="adv_name"></td>
		                                <td class="adv_td1" width="100"></td>
		                                <td class="adv_td1" width="65" align="right">作者：</td>
		                                <td class="adv_td1" width="200"><input type="text" class="form-control input-sm" name="adv_author"></td>
		                                <td class="adv_td1"></td>
		                            </tr>
		                            <tr class="adv_tr">
		                                <td class="adv_td1" align="right">发表开始时间：</td>
		                                <td class="adv_td1"><input type="date" name="adv_createStartDate" class="form-control input-sm"></td>
		                                <td class="adv_td1" width="100"></td>
		                                <td class="adv_td1" align="right">发表截止时间：</td>
		                                <td class="adv_td1"><input type="date" name="adv_createEndDate" class="form-control input-sm"></td>
		                            </tr>
		                            <tr class="adv_tr">
		                                <td class="adv_td1" align="right">指标类型：</td>
		                                <td class="adv_td1"><input type="text" name="adv_systemOption" class="form-control input-sm"></td>
		                                <td class="adv_td1" width="100"></td>
		                                <td class="adv_td1" align="right">模板类型：</td>
		                                <td class="adv_td1"><input type="date" name="adv_template" class="form-control input-sm"></td>
		                            </tr>
		                            <tr class="adv_tr">
		                                <td class="adv_td1" align="right">备注：</td>
		                                <td class="adv_td1"><input type="text" name="adv_note" class="form-control input-sm"></td>
		                            </tr>
		                        </table>
		                         <table border="0" cellspacing="0" cellpadding="0" class="table-search form-group pull-right">
			                        <tbody>
				                        <tr>
				                            <td align="right"></td>
				                            <td class = "btn-group" style = "display:block">
				                            	<input id="list_button_advSearch" type="button" value="检索" class="btn btn-default btn-sm">
				                            	<input id="list_search_hide" type="button" value="隐藏条件" class="btn btn-default btn-sm">
				                            </td>
				                        </tr>
			                        </tbody>
			                    </table>
			                    <!-- 清除浮动 -->
			                    <span class="clearfix"></span>
		                    </div>
		                </form>
		            </div>
					    <s:form id="list" theme="simple" action="delete" namespace="/portal">
						    <s:hidden id="pagenumber" name="pageNumber"/>
						    <s:hidden id="checkedIds" name="checkedIds"/>
						    <div id="list_container"  style="display:none;"></div>
		        		</s:form>   
					  </div>
					</div> <!-- end <div class="panel panel-default"> -->
					
					
					
				<!-- 显示列表 -->
				<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0"
			                   class="table table-striped table-bordered dataTable no-footer">
			                <thead id="list_head">
			                <tr class="table_title_tr">
			                	<td width="20"><input id="check" name="check" type="checkbox" title="点击全选/不选本页所有项目"/></td>
			                    <td width="50" class="text-center">序号</td>
			                    <td width=""><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按账号名称排序">成果编号</a></td>
			                    <td width="100" ><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}"  title="点击按创建时间排序">成果名称</a></td>
			                    <td width="100" ><a id="sortcolumn2" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}"  title="点击按创建时间排序">作者姓名</a></td>
			                    <td width="100" ><a id="sortcolumn3" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}"  title="点击按有效时间排序">工作单位</a></td>
			                    <td width="100"><a id="sortcolumn4" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}"  title="点击按账号状态排序">学科分组</a></td>
			                    <td width="100"><a id="sortcolumn5" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}"  title="点击按角色分配排序">成果形式</a></td>
			                </tr>
			                </thead>
			                <tbody>
			                {for item in root}
			                <tr>
			                	<td><input type="checkbox" name="entityIds" value="${item.laData[0]}"/></td>
			                    <td class="text-center">${item.num}</td>
			                    <td class="table_txt_td"><a href="${item.laData[0]}" title="点击查看详细信息" type="1">${item.laData[1]}</a></td>
			                    <td >${item.laData[2]}</td>
			                    <td >${item.laData[2]}</td>
			                    <td >${item.laData[2]}</td>
			                    <td >${item.laData[2]}</td>
			                    <td >${item.laData[2]}</td>
			                </tr>
			                {forelse}
			                <tr>
			                    <td align="center">暂无符合条件的记录</td>
			                </tr>
			                {/for}
			                </tbody>
			            </table>
						
				</textarea>
		         
					
			    </div>
			</div>
			<div class="row">
   		<%@ include file="/jsp/footer.jsp"%>
		<script>
			seajs.use("js/system/dataDic/disciplineGroup/view.js", function(view) {
				view.init(); 
		    });
		</script>
	</body>
</html>