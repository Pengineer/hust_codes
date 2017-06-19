<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>系统日志</title>
			<s:include value="/innerBase.jsp" />
			
		</head>

		<body>
			<div class="link_bar">
				当前位置：系统管理&nbsp;&gt;&nbsp;系统日志
			</div>
			<div style=" color: #555555; z-index:100; position:absolute; background:white; display:none; margin-left: 420px;margin-top: 250px;" id="j_view"></div>
			<div class="main">
				<div class="main_content">
					<div class="table_main_tr_adv">
						<s:include value="/system/log/search.jsp"/>
					</div>
					
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
								<sec:authorize ifAllGranted="ROLE_SYSTEM_LOG_DELETE">
									<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有日志" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</sec:authorize>
									<td width="30">序号</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="150"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按操作时间排序">操作时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="100"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按操作账号排序">操作者</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="100"><a id="sortcolumn4" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按账号所属排序">账号所属</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="100"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按操作时间排序">操作地点</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按事件描述排序">事件描述</a></td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
								<sec:authorize ifAllGranted="ROLE_SYSTEM_LOG_DELETE">
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td></td>
								</sec:authorize>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息">${item.laData[1]}</a></td>
									<td></td>
									{if item.laData[3] == null || item.laData[3] == ""}
									<td>${item.laData[2]}</td>
									{else}
									<td><a id="${item.laData[3]}" class="link2" href="">${item.laData[2]}</a></td>
									{/if}
									<td></td>
									<td>${item.laData[6]}</td>
									<td></td>
									<td class = "j_viewDetail" id = "${item.laData[4]}">${item.laData[4]}</td>
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
								<sec:authorize ifAllGranted="ROLE_SYSTEM_LOG_DELETE">
									<td width="58"><input id="list_delete" type="button" class="btn1" value="删除" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_SYSTEM_STATISTIC">
									<td width="58"><input id="list_statistic" type="button" class="btn1" value="统计" /></td>
								</sec:authorize>
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="delete" namespace="/log">
						<s:hidden id="pagenumber" name="pageNumber" />
						<s:hidden id="type" name="type" value="1" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/system/log/list.js', function(list) {
					$(function(){
						list.init();
					})
				});
			</script>
		</body>
	
</html>