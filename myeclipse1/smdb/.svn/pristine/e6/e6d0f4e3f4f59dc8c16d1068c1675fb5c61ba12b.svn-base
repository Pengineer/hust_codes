<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

		<head>
			<title>社科业务表</title>
			<s:include value="/innerBase.jsp" />
			
		</head>

		<body>
			<div class="link_bar">
				当前位置：社科业务日程
			</div>
			
			<div class="main">
				<div class="main_content">
					<div class="table_main_tr_adv">
						<s:include value="/system/business/advSearch.jsp"/>
					</div>
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
								<sec:authorize ifAllGranted="ROLE_BUSINESS_DELETE">
									<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有项目" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</sec:authorize>
									<td width="30">序号</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="130"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按业务名称排序">业务类型</a></td>
								<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@STUDENT)">
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="95"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按个人申请截止时间排序">申请截止时间</a></td>
								</s:if>
								<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@INSTITUTE)">
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="95"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按部门审核截止时间排序">部门截止时间</a></td>
								</s:if>	
								<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)">
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="95"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按高校审核截止时间排序">高校截止时间</a></td>
								</s:if>
								<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@PROVINCE)">
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="95"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按省厅审核截止时间排序">省厅截止时间</a></td>
								</s:if>	
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="80"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按业务对象起止年份排序">业务对象起止年份</a></td>	
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按业务状态排序">业务设置</a></td>	
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
								<sec:authorize ifAllGranted="ROLE_BUSINESS_DELETE">
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td></td>
								</sec:authorize>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息" type="3">${item.laData[1]}</a></td>
								<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@STUDENT)">
									<td></td>
									<td>{if item.laData[6] == null || item.laData[6] == ""}<span style="color:#7A5892">无期限</span>
										{else}${item.laData[6]}{/if}
									</td>
								</s:if>
								<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@INSTITUTE)">
									<td></td>
									<td>{if item.laData[7] == null || item.laData[7] == ""}<span style="color:#7A5892">无期限</span>
										{else}${item.laData[7]}{/if}
									</td>
								</s:if>
								<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)">
									<td></td>
									<td>{if item.laData[8] == null || item.laData[8] == ""}<span style="color:#7A5892">无期限</span>
										{else}${item.laData[8]}{/if}
									</td>
								</s:if>
								<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@PROVINCE)">
									<td></td>
									<td>{if item.laData[9] == null || item.laData[9] == ""}<span style="color:#7A5892">无期限</span>
										{else}${item.laData[9]}{/if}
									</td>
								</s:if>
									<td></td>
									<td>{if item.laData[10] == -1 && item.laData[11] == -1}<span style="color:#7A5892">无期限</span>
										{elseif item.laData[10] == -1 && item.laData[11] != -1}${item.laData[11]}止
										{elseif item.laData[10] != -1 && item.laData[11] == -1}${item.laData[10]}起
										{elseif item.laData[10] != -1 && item.laData[11] != -1}${item.laData[10]}-${item.laData[11]}
										{/if}
									</td>
									<td></td>
									<td>{if item.laData[2]==1}业务激活中{else}业务停止{/if}</td>
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
								<sec:authorize ifAllGranted="ROLE_BUSINESS_ADD">
									<td width="58" align="center"><input class="btn1" type="button" id="list_add" value="添加" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_BUSINESS_DELETE">
									<td width="58"><input id="list_delete" type="button" class="btn1" value="删除" /></td>
								</sec:authorize>
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="delete" namespace="/business">
						<s:hidden id="pagenumber" name="pageNumber" />
						<s:hidden id="type" name="type" value="1" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/system/business/list.js', function(list) {
					list.init();
				});
			</script>
		</body>

</html>