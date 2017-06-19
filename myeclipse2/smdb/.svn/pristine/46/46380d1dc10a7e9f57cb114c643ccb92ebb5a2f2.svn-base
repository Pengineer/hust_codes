<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>常规数据统计</title>
		<s:include value="/innerBase.jsp"/>
		<script type="text/javascript">
			seajs.use('javascript/statistic/list.js', function(list) {
				$(function(){
					list.init();
				})
			});
		</script>
	</head>

	<body>
		<div class="link_bar">
			当前位置：常规数据统计&nbsp;&gt;&nbsp;<s:property value="#session.statisticListType"/> 
		</div>
		
		<div class="main">
			<div class="main_content">
				<s:form id="search" theme="simple" action="list" namespace="/statistic/common">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr">
							<sec:authorize ifAllGranted="ROLE_STATISTIC_COMMON_ADD">
							<td width="80" align="center"><input id="list_add" class="btn1" type="button" value="添加"/></td>
							</sec:authorize>
							<td align="right"><span class="choose_bar">
								<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{getText('不限')}--"
									list="#{'1':'统计主题','2':'统计年度','3':'发布时间'}"/>
								</span><s:textfield id="keyword" name="keyword" cssClass="keyword" size="10"/>
								<s:hidden id="list_pagenumber" name="pageNumber"/>
								<s:hidden id="list_sortcolumn" name="sortColumn"/>
								<s:hidden id="list_pagesize" name="pageSize"/>
							</td>
							<td width="60"><input id="list_button_query" type="button" value="检索" class="btn1"/></td>
							
						</tr>
					</table>
				</s:form>
				
				<textarea id="list_template" style="display:none;">
					<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr">
								<sec:authorize ifAllGranted="ROLE_STATISTIC_COMMON_DELETE">
								<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有" /></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</sec:authorize>
								<td width="30">序号</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="">统计主题</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="200"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="">统计年度</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="200"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="">发布时间</a></td>
							</tr>
						</thead>
						<tbody>
						{for item in root}
							<tr>
								<sec:authorize ifAllGranted="ROLE_STATISTIC_COMMON_DELETE">
								<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
								<td></td>
								</sec:authorize>
								<td>${item.num}</td>
								<td></td>
								<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息">${item.laData[1]}</a></td>
								<td></td>
								<td>
								{if item.laData[2]!=0}
									${item.laData[2]}
									{else}
								{/if}
								</td>
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
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr2">
							<td width="4"></td>
							<sec:authorize ifAllGranted="ROLE_STATISTIC_COMMON_DELETE">
							<td width="58"><input id="list_delete" type="button" class="btn1" value="删除"/></td>
							</sec:authorize>
						</tr>
					</table>
				</textarea>
				<s:form id="list" theme="simple" action="delete" namespace="/statistic/common">
					<s:hidden id="pagenumber" name="pageNumber" />
					<s:hidden id="type" name="type" value="1" />
					<s:hidden id="statisticType" name="statisticType" />
					<div id="list_container" style="display:none;"></div>
				</s:form>
			</div>
		</div>
	</body>
</html>