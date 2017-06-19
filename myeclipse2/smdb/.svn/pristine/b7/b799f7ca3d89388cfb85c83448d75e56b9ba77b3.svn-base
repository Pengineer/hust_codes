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
					<div class="table_main_tr_adv">
						<s:include value="/system/message/inner/search.jsp"/>
					</div>
					
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
								<sec:authorize ifAnyGranted="ROLE_INFORMATION_MESSAGE_DELETE,ROLE_INFORMATION_MESSAGE_TOGGLEOPEN">
									<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有留言" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</sec:authorize>
									<td width="30">序号</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按留言标题排序">留言标题</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按发布者排序">发布者</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按留言时间排序">留言时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="80">
										<a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按查看数排序">查看</a>/
										<a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按回复数排序">回复</a>
									</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="150"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按最后回复排序">最后回复</a></td>
									<sec:authorize ifAnyGranted="ROLE_INFORMATION_MESSAGE_AUDIT">
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										<td width="70"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核状态排序">审核状态</a></td>
									</sec:authorize>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
								<sec:authorize ifAnyGranted="ROLE_INFORMATION_MESSAGE_DELETE,ROLE_INFORMATION_MESSAGE_TOGGLEOPEN">
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td></td>
								</sec:authorize>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息">${item.laData[1]}</a></td>
									<td></td>
									<td>${item.laData[2]}</td>
									<td></td>
									<td>${item.laData[3]}</td>
									<td></td>
									<td>${item.laData[4]}/${item.laData[5] - 1}</td>
									<td></td>
									<td>${item.laData[7]}&nbsp;By:&nbsp;${item.laData[6]}</td>
									<sec:authorize ifAnyGranted="ROLE_INFORMATION_MESSAGE_AUDIT">
										<td></td>
										<td>{if item.laData[8] == 0}未审核{elseif item.laData[8] == 1}未通过{else}通过{/if}</td>
									</sec:authorize>
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
								<sec:authorize ifAllGranted="ROLE_INFORMATION_MESSAGE_ADD">
									<td width="58"><input id="list_add" class="btn1" type="button" value="添加" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_INFORMATION_MESSAGE_DELETE">
									<td width="58"><input id="list_delete" type="button" class="btn1" value="删除" /></td>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_INFORMATION_MESSAGE_AUDIT">
									<td width="58"><input id="list_audit" type="button" class="btn1" value="审核" /></td>
								</sec:authorize>
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="delete" namespace="/message/inner">
						<s:hidden id="auditStatus" name="auditStatus" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/system/message/inner/list.js', function(list) {
					$(function(){
						list.init();
					})
				});
			</script>
		</body>

</html>
