<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>重大攻关项目经费</title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/projectFund/key/list.js', function(list) {
					list.init();
				});
			</script>
		</head>

		<body>
			<div class="link_bar">
				当前位置：研究项目经费&nbsp;&gt;&nbsp;重大攻关项目&nbsp;&gt;&nbsp;拨款概况
			</div>
			
			<div class="main">
				<div class="main_content">
					<div class="table_main_tr_adv">
						<s:include value="/projectFund/keyFund/search.jsp"/>
					</div>
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
									<td width="30">序号</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目名称排序">项目名称</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="60"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按批准号排序">批准号</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="65"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按首席专家排序">首席专家</a></td>
								<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="65"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按依托高校排序">依托高校</a></td>
								</s:if>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="65"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按研究类型排序">研究类型</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="65"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目年度排序">项目年度</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="65"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按批准经费金额排序">批准经费</a></td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td">${item.laData[3]}</td>
									<td></td>
									<td>${item.laData[2]}</td>
									<td></td>
									<td>
										{if item.laData[4]==""}${item.laData[5]}
										{else}<s:hidden id="directors" value="${item.laData[4]}" name="${item.laData[5]}" cssClass="directors" />
										{/if}
									</td>
								<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
									<td></td>
									<td>
										{if item.laData[6]==""}${item.laData[7]}
										{else}<a id="${item.laData[6]}" class="view_university" href="" title="点击查看详细信息">${item.laData[7]}</a>
										{/if}
									</td>
								</s:if>
									<td></td>
									<td>${item.laData[8]}</td>
									<td></td>
									<td>${item.laData[9]}</td>
									<td></td>
									<td>${item.laData[10]}</td>
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
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="funding" namespace="/projectFund/key">
						<s:hidden id="pagenumber" name="pageNumber" />
						<s:hidden id="type" name="type" value="1" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
		</body>
	
</html>