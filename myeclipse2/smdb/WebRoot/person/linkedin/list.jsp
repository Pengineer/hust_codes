<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Person">
		<head>
			<title><s:text name="好友管理" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				<s:text name="i18n_CurrentPosition" />：<s:text name="好友列表" />
			</div>
			<div class="main">
				<div class="main_content">
					<div class="table_main_tr_adv">
						<s:include value="/person/linkedin/search.jsp"/>
					</div>

					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
									<td width="20"><input id="check" name="check" type="checkbox"  title="<s:text name='i18n_SelectAllOrNot' />" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="30"><s:text name="i18n_Number" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="100"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByName' />"><s:text name="i18n_Name" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='按照时间排序' />"><s:text name="成为好友的时间" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='按照状态排序' />"><s:text name="状态" /></a></td>
								</tr>
							</thead>
							<tbody>
								{for item in root}
									<tr>
										<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
										<td></td>
										<td>${item.num}</td>
										<td></td>
										<td >
											{if item.laData[2] == 2}
												<a id="${item.laData[0]}" class="linkCh" href="javascript:void(0)" title="<s:text name='发消息' />">${item.laData[1]}</a>
											{else}
												${item.laData[1]}
											{/if}
										</td>
										<td></td>
										<td>${item.laData[3]}</td>
										<td></td>
										<td>
											{if item.laData[2] == 0}
												<s:text name="等待对方验证" />
											{elseif item.laData[2] == 1}
												<a id="${item.laData[0]}" class="" href="javascript:void(0)" title="<s:text name='拒绝理由：${item.laData[4]}' />"><s:text name="对方拒绝好友请求" /></a>
											{else}
												<s:text name="同意" />
											{/if}
										</td>
									</tr>
								{forelse}
									<tr>
										<td align="center"><s:text name="i18n_NoRecords" /></td>
									</tr>
								{/for}
							</tbody>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<tr class="table_main_tr2">
								<td width="10"></td>
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="delete" namespace="/linkedin">
						<s:hidden id="checkedIds" name="checkedIds" />
						<s:hidden id="mainId" name="mainId" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/person/link/list.js', function(list) {
					$(function(){
						list.init();
					})
				});
			</script>
		</body>
	</s:i18n>
</html>
