<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>访客监控访客列表</title>
			<s:include value="/innerBase.jsp" />
			
		</head>

		<body>
			<div class="link_bar">
				当前位置：系统管理&nbsp;&gt;&nbsp;系统监控&nbsp;&gt;&nbsp;访客监控
			</div>
			
			<div class="main">
				<div class="main_content">
					<s:form id="search" theme="simple" action="list" namespace="/system/monitor/visitor">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<tr class="table_main_tr">
								<td align="right"><span class="choose_bar">
									<s:select cssClass="select" name="searchType" value="%{searchMap.searchType}" headerKey="-1" headerValue="--%{getText('不限')}--" list="#{'0':getText('用户名'),'1':getText('IP')}" />
								</span><s:textfield id="keyword" name="keyword" value="%{searchMap.keyword}" cssClass="keyword" size="10" />
									<s:hidden id="list_pagenumber" name="pageNumber" />
									<s:hidden id="list_sortcolumn" name="sortColumn" />
									<s:hidden id="list_pagesize" name="pageSize" />
								</td>
								<td width="60"><input id="list_button_query" type="button" value="检索" class="btn1" /></td>
							</tr>
						</table>
					</s:form>

					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
									<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有访客" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="30">序号</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td><a id="sortcolumn0" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按用户名排序">用户名</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="100"><a id="sortcolumn1" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按IP排序">IP</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="200"><a id="sortcolumn2" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按到访时刻排序">到访时刻</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="100"><a id="sortcolumn3" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按活跃时长排序">活跃时长</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="100"><a id="sortcolumn4" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按发呆时长排序">发呆时长</a></td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td></td>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td">${item.laData[1]}</td>
									<td></td>
									<td>${item.laData[2]}</td>
									<td></td>
									<td>${item.laData[3]}</td>
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
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<tr class="table_main_tr2">
								<td width="4"></td>
								<sec:authorize ifAllGranted="ROLE_SYSTEM_MONITOR_VISITOR_EVICT">
									<td width="58"><input id="list_evict" type="button" class="btn1" value="踢出" /></td>
									<td width="58"><input id="list_evict_all" type="button" class="btn1" value="全部踢出" /></td>
								</sec:authorize>
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="evict" namespace="/system/monitor/visitor">
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/system/monitor/visitor/list.js', function(list) {
					$(function(){
						list.init();
					})
				});
			</script>
		</body>

</html>
