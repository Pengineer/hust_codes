<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

		<head>
			<title>留言簿</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				当前位置：社科信息发布&nbsp;&gt;&nbsp;留言簿
			</div>

			<div class="main">
				<div class="main_content">
					<s:form id="search" theme="simple" action="list" namespace="/message/outer">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding" >
							<tr class="table_main_tr">
								<td width="80" align="center"><input id="list_add" class="btn1" type="button" value="添加" /></td>
								<td align="right">
									<s:textfield id="keyword" name="keyword" cssClass="keyword" size="10" />
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
									<td width="30">序号</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按留言标题排序">留言标题</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="80"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按发布者排序">发布者</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="80"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按留言时间排序">留言时间</a></td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息">${item.laData[1]}</a></td>
									<td></td>
									<td>${item.laData[2]}</td>
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
								<td></td>
							</tr>
						</table>
					</textarea>
					<div id="list_container" style="display:none;"></div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/system/message/outer/list.js', function(list) {
					$(function(){
						list.init();
					})
				});
			</script>
		</body>
	
</html>
