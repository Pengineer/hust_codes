<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%><%--添加对EL表达式的支持--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%--添加对struts标签的支持 --%>
<!DOCTYPE html>
<html>
<head>
<title>湖北省社会科学优秀成果奖励申报评审系统</title>
<%@ include file="/jsp/base.jsp"%>
</head>
<body>
	<%@ include file="/jsp/innerNav.jsp"%>
	<a name="top" id="top"></a>
	<div class="row mySlide">
		<ol class="breadcrumb mybreadcrumb">
			当前位置：
			<li><a href="#">个人空间</a>
			</li>
			<li class="active">个人信息</li>
		</ol>
		<div class="col-xs-12">
			<div class="panel panel-default panel-view">
				<div class="panel-heading">
					<strong>基本信息</strong>
					<a class="u-modify" href="selfspace/toModify.action" title="修改"><span class="glyphicon glyphicon-edit"></span></a>
				</div>
				<div class="panel-body">
					<table class="table table-striped view-table">
						<tbody>
							<tr>
								<td width="50" class="text-right"><span
									class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span>
								</td>
								<td class="text-right" width="100">用户名：</td>
								<td class="text-left" colspan='4'>fengzheqi</td>
							</tr>
							<tr>
								<td width="50" class="text-right"><span
									class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span>
								</td>
								<td width="100" class="text-right">中文名：</td>
								<td class="text-left" width='200'>冯哲奇</td>
								<td width="50" class="text-right"><span
									class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span>
								</td>
								<td width="100" class="text-right">性别：</td>
								<td class="text-left">男</td>
							</tr>
							<tr>
								<td width="50" class="text-right"><span
									class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span>
								</td>
								<td width="100" class="text-right">账号状态：</td>
								<td class="text-left" width='200'>启用</td>
								<td width="50" class="text-right"><span
									class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span>
								</td>
								<td width="100" class="text-right">所在单位：</td>
								<td class="text-left">华中科技大学</td>
							</tr>
							<tr>
								<td width="50" class="text-right"><span
									class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span>
								</td>
								<td width="100" class="text-right">常用邮箱：</td>
								<td class="text-left" width='200'>fengzheqiyx@163.com</td>
								<td width="50" class="text-right"><span
									class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span>
								</td>
								<td width="100" class="text-right">联系电话：</td>
								<td class="text-left">15527649730</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<!-- end <div class="panel panel-default"> -->

			<div class="panel panel-default panel-view">
				<div class="panel-heading">
					<strong>申报信息</strong>
				</div>
				<div class="panel-body">
					<div id="simple_search" style="display: none !important;">
						<form id="search" name="search" action="jsp/selfspace/list.json"
							method="post" class="form-inline">
							<input type="hidden" name="sim_sysOptKeyword" value=""
								id="sim_sysOptKeyword">
							<table border="0" cellspacing="0" cellpadding="0"
								class="table_td_padding form-group pull-right">
								<tbody>
									<tr>
										<td align="right">
											<span class="choose_bar"> 
												<select name="searchType" id="search_searchType" class="select form-control input-sm">
													<option value="-1" selected="selected">--不限--</option>
													<!-- 名称、作者、发表时间、指标类型、模板类型、备注 -->
													<option value="1">名称</option>
													<option value="2">作者</option>
												</select>
											</span> 
											<input class="form-control input-sm keyword" type="text" name="keyword" size="10" value="" id="keyword">
											<input type="hidden" name="pageNumber" value="" id="list_pagenumber">
											<input type="hidden" name="sortColumn" value="0" id="list_sortcolumn">
											<input type="hidden" name="pageSize" value="0" id="list_pagesize">
										</td>
										<td class="btn-group" style="display:block">
											<input id="list_button_query" class="btn btn-default btn-sm" type="button" value="检索" >
										</td>
									</tr>
								</tbody>
							</table>
							<span class="clearfix"></span>
						</form>
					</div>
					<s:form id="list" theme="simple" action="delete"
						namespace="/portal">
						<s:hidden id="pagenumber" name="pageNumber" />
						<s:hidden id="checkedIds" name="checkedIds" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<!-- end <div class="panel panel-default"> -->



			<!-- 显示列表 -->
			<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0"
					cellpadding="0"
					class="table table-striped table-bordered dataTable no-footer">
			                <thead id="list_head">
			                <tr class="table_title_tr">
			                	<td width="20"><input id="check" name="check"
								type="checkbox" title="点击全选/不选本页所有项目" />
							</td>
			                    <td width="50" class="text-center">序号</td>
			                    <td width="100"><a id="sortcolumn0" href=""
								class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}"
								title="点击按奖励编号排序">奖励编号</a>
							</td>
			                    <td width="300"><a id="sortcolumn1" href=""
								class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}"
								title="点击按奖励名称排序">奖励名称</a>
							</td>
			                    <td width="100"><a id="sortcolumn2" href=""
								class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}"
								title="点击按作者姓名排序">作者姓名</a>
							</td>
			                    <td width="100"><a id="sortcolumn3" href=""
								class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}"
								title="点击按工作单位排序">工作单位</a>
							</td>
			                    <td width="100"><a id="sortcolumn4" href=""
								class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}"
								title="点击按学科分组排序">学科分组</a>
							</td>
			                    <td width="100"><a id="sortcolumn5" href=""
								class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}"
								title="点击按成果形式排序">成果形式</a>
							</td>
			                    <td width="100"><a id="sortcolumn6" href=""
								class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}">状态</a>
							</td>
			                </tr>
			                </thead>
			                <tbody>
			                {for item in root}
			                <tr>
			                	<td><input type="checkbox" name="entityIds"
								value="${item.laData[0]}" />
							</td>
			                    <td class="text-center">${item.num}</td>
			                    <td class="table_txt_td"><a
								href="${item.laData[0]}" title="点击查看详细信息" type="1">${item.laData[1]}</a>
							</td>
			                    <td>${item.laData[2]}</td>
			                    <td>${item.laData[3]}</td>
			                    <td>${item.laData[3]}</td>
			                    <td>${item.laData[3]}</td>
			                    <td>${item.laData[3]}</td>
			                    <td>${item.laData[3]}</td>
			                </tr>
			                {forelse}
			                <tr>
			                    <td align="center">暂无符合条件的记录</td>
			                </tr>
			                {/for}
			                </tbody>
			            </table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="table_td_padding form-inline">
			                <tr class="table_main_tr2">
			                </tr>
			            </table>
				</textarea>


		</div>
	</div>
	<div class="row">
		<%@ include file="/jsp/footer.jsp"%>
		<script>
			seajs.use('js/selfspace/selfInfo.js', function(view) {
				view.init();
			})
		</script>
</body>
</html>