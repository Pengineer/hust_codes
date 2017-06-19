<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>高校变更</title>
		<s:include value="/innerBase.jsp" />
	</head>
	
	<body>
		<div class="link_bar">
			当前位置：数据管理&nbsp;&gt;&nbsp;高校变更
		</div>
		<div class="main">
			<div class="main_content">					
				<s:form id="search" theme="simple" action="list" namespace="/dm/universityVariation">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr">
							<td align="right"><span class="choose_bar">
								<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--不限--" value="-1"
									list="#{
										'0':'原高校名称',
										'1':'原高校代码',
										'2':'新高校名称',
										'3':'新高校代码',
										'4':'类别'
									}"
								/>
							</span><s:textfield  id="keyword" name="keyword" cssClass="keyword" size="10" />
								<s:hidden id="list_pagenumber" name="pageNumber" />
								<s:hidden id="list_sortcolumn" name="sortColumn" />
								<s:hidden id="list_pagesize" name="pageSize" />
							</td>
							<td width="60"><input id="list_button_query" type="button" value="检索" class="btn1" /></td>
						</tr>
					</table>
				</s:form>
				
				<textarea id="list_template" style="display:none;">
					<s:hidden id="universityVariationid" name="universityVariationid" />
					<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr">
								<td width="5%"><input id="check" name="check" type="checkbox" /></td>
								<td width="1%"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="5%">序号</td>
								<td width="1%"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="20%"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按原高校名称排序">原高校名称</a></td>
								<td width="1%"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="12%"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按原高校代码排序">原高校代码</a></td>
								<td width="1%"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="18%"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按新高校名称排序">新高校名称</a></td>
								<td width="1%"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="8%"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按新高校代码排序">新高校代码</a></td>
								<td width="1%"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="8%"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按高校变更类别排序">类别</a></td>
								<td width="1%"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="8%"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按变更时间排序">添加时间</a></td>
								<td width="1%"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="8%"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按备注排序">变更时间</a></td>
							</tr>
						</thead>
						<tbody>
						{for item in root}
							<tr>
								<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
								<td></td>
								<td>${item.num}</td>
								<td></td>
								<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息">${item.laData[1]}</a></td>
								<td></td>
								<td>${item.laData[2]}</td>
								<td></td>
								<td>${item.laData[3]}</td>
								<td></td>
								<td>${item.laData[4]}</td>
								<td></td>
								<td>{if item.laData[5] == 0}
								{elseif item.laData[5] == 1}更名
								{elseif item.laData[5] == 2}合并
								{/if}
								</td>
								<td></td>
								<td>${item.laData[6]}</td>
								<td></td>
								<td>${item.laData[7]}</td>
							</tr>
						{forelse}
							<tr>
								<td align="center">暂无符合条件的记录</td>
							</tr>
						{/for}
						</tbody>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr2">
							<td width="4"></td>
							<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_UNIVERSITY_RENAME_ADD">
								<td width="58" align="center"><input id="list_add" class="btn1" type="button" value="添加" /></td>
							</sec:authorize>
							<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_UNIVERSITY_RENAME_DELETE">
								<td width="58"><input id="list_delete" type="button" class="btn1" value="删除" /></td>
							</sec:authorize>
						</tr>
					</table>
				</textarea>
				<s:form id="list" theme="simple" action="delete" namespace="/dm/universityVariation">
					<s:hidden id="pagenumber" name="pageNumber" />
					<s:hidden id="checkedIds" name="checkedIds" />
					<div id="list_container" style="display:none;"></div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/dm/universityVariation/list.js', function(list) {
				$(function(){
					list.init();
				})
			});
		</script>
	</body>
</html>