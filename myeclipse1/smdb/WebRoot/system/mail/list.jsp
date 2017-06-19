<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>系统邮件</title>
			<s:include value="/innerBase.jsp" />
			
		</head>

		<body>
			<div class="link_bar">
				当前位置：系统管理&nbsp;&gt;&nbsp;系统邮件
			</div>
			
			<div class="main">
				<div class="main_content">
					<div class="table_main_tr_adv">
						<s:include value="/system/mail/search.jsp"/>
					</div>
					
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
								<sec:authorize ifAllGranted="ROLE_SYSTEM_MAIL_DELETE">
									<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有邮件" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</sec:authorize>
									<td width="30">序号</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按主题排序">邮件主题</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按创建账号排序">创建账号</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按创建者排序">创建者</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="80"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按创建时间排序">创建时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="80"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按完成时间排序">首次完成时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="50"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按状态排序">状态</a></td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
								<sec:authorize ifAllGranted="ROLE_SYSTEM_MAIL_DELETE">
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td></td>
								</sec:authorize>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息">${item.laData[1]}</a></td>
									<td></td>
									<td><a id="${item.laData[2]}" class="link2" href="">${item.laData[3]}</a></td>
									<td></td>
									<td>${item.laData[7]}</td>
									<td></td>
									<td>${item.laData[4]}</td>
									<td></td>
									<td>
										{if item.laData[5] == null || item.laData[5] == ""}
										未完成
										{else}
										${item.laData[5]}
										{/if}
									</td>
									<td></td>
									<td>
										{if item.laData[6] == "0"}未开始
										{elseif item.laData[6] == "1"}准备发送
										{elseif item.laData[6] == "2"}发送中
										{elseif item.laData[6] == "3"}已完成
										{else}已取消
										{/if}
									</td>
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
								<sec:authorize ifAllGranted="ROLE_SYSTEM_MAIL_ADD">
									<td width="58" align="center"><input id="list_add" class="btn1" type="button" value="添加" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_SYSTEM_MAIL_DELETE">
									<td width="58"><input id="list_delete" type="button" class="btn1" value="删除" /></td>
								</sec:authorize>
<%--								<sec:authorize ifAllGranted="ROLE_SYSTEM_MAIL_MODIFY">--%>
<%--									<td width="58"><input id="list_again" type="button" class="btn1" value="重发" /></td>--%>
<%--									<td width="58"><input id="list_cancel" type="button" class="btn1" value="取消" /></td>--%>
<%--								</sec:authorize>--%>
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="delete" namespace="/mail">
						<s:hidden id="pagenumber" name="pageNumber" />
						<s:hidden id="type" name="type" value="1" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/system/mail/list.js', function(list) {
					$(function(){
						list.init();
					})
				});
			</script>
		</body>
	
</html>