<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<title>系统权限
		</title>
		<s:include value="/innerBase.jsp" />
		
	</head>

	<body>
		<div class="link_bar">
			当前位置：通行证管理
		</div>

		<div class="main">
			<div class="main_content">
				<div class="table_main_tr_adv">
					<s:include value="/security/passport/search.jsp" />
				</div>

				<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0"
						cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
									<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_MAIN_DELETE,ROLE_SECURITY_ACCOUNT_DEPARTMENT_MAIN_STATUS,ROLE_SECURITY_ACCOUNT_ASSIGNROLE">
										<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选" /></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									</sec:authorize>
									<td width="30">序号</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按通行证排序">通行证</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按照创建时间排序">创建时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按照有效期限排序">有效期限</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按照状态排序">状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn8" href="" class="{if sortColumn == 8}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按照上次登录时间排序">上次登录时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按照最大连接数排序">最大连接数</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="65"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按照最后登录IP排序">上次登录IP</a></td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
								<sec:authorize ifAllGranted="ROLE_SECURITY_RIGHT_DELETE">
									<td><input type="checkbox" name="entityIds"
										value="${item.laData[0]}" />
									</td>
									<td></td>
								</sec:authorize>
									<td>${item.num}</td>
									<td></td>
									<td><a id="${item.laData[0]}"class="link1" href="" title="点击查看详细信息">${item.laData[1]}</a>
									</td>
									<td></td>
									<td>${item.laData[6]}</td>
									<td></td>
									<td>${item.laData[7]}</td>
									<td></td>
									<td>
									{if item.laData[8] == 1}
											启用
									{else}
											停用
									{/if}
									</td>
									<td></td>
									<td>${item.laData[9]}</td>
									<td></td>
									<td>${item.laData[2]}</td>
									<td></td>
									<td>${item.laData[5]}</td>
								</tr>
							{forelse}
								<tr>
									<td align="center">暂无符合条件的记录
								</td>
								</tr>
							{/for}
							</tbody>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="table_td_padding">
							<tr class="table_main_tr2">
								
							</tr>
						</table>
					</textarea>
				<s:form id="list" theme="simple" action="delete" namespace="/passport">
					<s:hidden id="pagenumber" name="pageNumber" />
					<div id="list_container" style="display:none;"></div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/security/passport/list.js', function(list) {
				$(function() {
					list.init();
				})
			});
		</script>
	</body>

</html>