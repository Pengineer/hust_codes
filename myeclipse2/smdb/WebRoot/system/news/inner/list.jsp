<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

		<head>
			<title>新闻</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				当前位置：社科信息发布&nbsp;&gt;&nbsp;新闻
			</div>
			
			<div class="main">
				<div class="main_content">
					<div class="table_main_tr_adv">
						<s:include value="/system/news/inner/search.jsp"/>
					</div>
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
								<sec:authorize ifAllGranted="ROLE_INFORMATION_NEWS_DELETE">
									<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有新闻" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</sec:authorize>
									<td width="30">序号</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按标题排序">新闻标题</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="60"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按新闻类型排序">新闻类型</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="60"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按是否公开排序">是否公开</a></td>
								<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@ADMINISTRATOR) == 0">				
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="60"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按发布账号排序">发布账号</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="60"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按发布者排序">发布者</a></td>
								</s:if>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="60"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按查看数量排序">查看数量</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="80"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按发布时间排序">发布时间</a></td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
								<sec:authorize ifAllGranted="ROLE_INFORMATION_NEWS_DELETE">
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td></td>
								</sec:authorize>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息">${item.laData[1]}</a></td>
									<td></td>
									<td>${item.laData[2]}</td>
									<td></td>
									<td>{if item.laData[3] == 1}是{else}否{/if}</td>
								<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@ADMINISTRATOR) == 0">
									<td></td>
									<td><a id="${item.laData[4]}" class="link2" href="">${item.laData[5]}</a></td>
									<td></td>
									<td style="width:60px;">${item.laData[8]}</td>
								</s:if>
									<td></td>
									<td>${item.laData[7]}</td>
									<td></td>
									<td>${item.laData[6]}</td>
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
								<sec:authorize ifAllGranted="ROLE_INFORMATION_NEWS_ADD">
									<td width="58"><input id="list_add" type="button" class="btn1" value="添加" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_INFORMATION_NEWS_DELETE">
									<td width="58"><input id="list_delete" type="button" class="btn1" value="删除" /></td>
								</sec:authorize>
							</tr>
						</table> 
					</textarea>
					<s:form id="list" theme="simple" action="delete" namespace="/news/inner">
						<s:hidden id="pagenumber" name="pageNumber" />
						<s:hidden id="type" name="type" value="1" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/system/news/inner/list.js', function(list) {
					$(function(){
						list.init();
					})
				});
			</script>
		</body>

</html>