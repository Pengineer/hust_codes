<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>国家自然科学基金项目</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：社科其他数据&nbsp;&gt;&nbsp;国家自然科学基金项目
		</div>
		
		<div class="main">
			<div class="main_content">
				<div class="table_main_tr_adv">
					<s:include value="/other/nsfc/granted/search.jsp"/>
				</div>

				<textarea id="list_template" style="display:none;"><div style="overflow-x:scroll">
					<table id="list_table" width="1200" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr">
								<td width="40">序号</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按项目名称排序">项目名称</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="65"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按年份排序">年份</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="90"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按批准号排序">批准号</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="90"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按申请编号排序">申请编号</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="90"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按项目负责人排序">项目负责人</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="90"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按工作单位排序">工作单位</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="70"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按批准金额排序">批准金额</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="70"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按立项时间排序">立项时间</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="90"><a id="sortcolumn8" href="" class="{if sortColumn == 8}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按计划完成时间排序">计划完成时间</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="70"><a id="sortcolumn9" href="" class="{if sortColumn == 9}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按资助类别排序">资助类别</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="70"><a id="sortcolumn10" href="" class="{if sortColumn == 10}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按亚类说明排序">亚类说明</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="70"><a id="sortcolumn11" href="" class="{if sortColumn == 11}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按附注说明排序">附注说明</a></td>
							</tr>
						</thead>
						<tbody>
						{for item in root}
							<tr>
								<td>${item.num}</td>
								<td></td>
								<td>${item.laData[1]}</td>
								<td></td>
								<td>${item.laData[2]}</td>
								<td></td>
								<td>${item.laData[3]}</td>
								<td></td>
								<td>${item.laData[4]}</td>
								<td></td>
								<td>${item.laData[5]}</td>
								<td></td>
								<td>${item.laData[6]}</td>
								<td></td>
								<td>${item.laData[7]}</td>
								<td></td>
								<td>${item.laData[8]}</td>
								<td></td>
								<td>${item.laData[9]}</td>
								<td></td>
								<td>${item.laData[10]}</td>
								<td></td>
								<td>${item.laData[11]}</td>
								<td></td>
								<td>${item.laData[12]}</td>
							</tr>
						{forelse}
							<tr>
								<td align="center">暂无符合条件的记录</td>
							</tr>
						{/for}
						</tbody>
					</table></div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr2">
							<td width="4"></td>
							<sec:authorize ifAllGranted="ROLE_OTHER_NSFC_UPDATE">
								<td width="58"><input id="list_update" type="button" class="btn1" value="更新" /></td>
							</sec:authorize>
							<sec:authorize ifAllGranted="ROLE_OTHER_NSFC_EXPORT">
								<td width="58"><input id="export" type="button" class="btn1" value="导出" /></td>
							</sec:authorize>
						</tr>
					</table>
				</textarea>
				<s:form id="list" theme="simple" action="update" namespace="/other/nsfc/granted">
					<div id="list_container" style="display:none;"></div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/other/nsfc/granted/list.js', function(list) {
				$(function(){
					list.init();
				})
			});
		</script>
	</body>
</html>