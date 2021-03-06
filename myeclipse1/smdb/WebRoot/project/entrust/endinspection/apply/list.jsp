<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>委托应急课题</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				当前位置：社科项目数据&nbsp;&gt;&nbsp;委托应急课题&nbsp;&gt;&nbsp;结项数据
			</div>
			
			<div class="main">
				<div class="main_content">
					<div class="table_main_tr_adv">
						<s:include value="/project/entrust/endinspection/apply/search.jsp"/>
					</div>
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_DELETE">
									<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有项目" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</sec:authorize>
									<td width="30">序号</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目名称排序">项目名称</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="51"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按负责人排序">负责人</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
									<td width="64"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按依托高校排序">依托高校</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</s:if>
									<td width="54"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目类别排序">课题类别</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="54"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目年度排序">项目年度</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
									<td width="64"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按提交状态排序">提交状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</s:if>
								<s:elseif test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@DEPARTMENT, @csdc.tool.bean.AccountType@INSTITUTE)"><!-- 院系或研究基地-->
									<td width="64"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核状态排序">审核状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="64"><a id="sortcolumn14" href="" class="{if sortColumn == 14}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核时间排序">审核时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</s:elseif>
								<s:elseif test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)"><!-- 高校 -->
									<td width="64"><a id="sortcolumn8" href="" class="{if sortColumn == 8}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核状态排序">审核状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="64"><a id="sortcolumn15" href="" class="{if sortColumn == 15}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核时间排序">审核时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_VIEW">
										<td width="54"><a id="sortcolumn11" href="" class="{if sortColumn == 11}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按评审状态排序">鉴定状态</a></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									</sec:authorize>
								</s:elseif>
								<s:elseif test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@PROVINCE)"><!-- 省厅 -->
									<td width="64"><a id="sortcolumn9" href="" class="{if sortColumn == 9}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核状态排序">审核状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="64"><a id="sortcolumn16" href="" class="{if sortColumn == 16}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核时间排序">审核时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_VIEW">
										<td width="54"><a id="sortcolumn11" href="" class="{if sortColumn == 11}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按评审状态排序">鉴定状态</a></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									</sec:authorize>
								</s:elseif>
								<s:else>
									<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
										<td width="54"><a id="sortcolumn10" href="" class="{if sortColumn == 10}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核状态排序">审核状态</a></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_VIEW">
											<td width="54"><a id="sortcolumn11" href="" class="{if sortColumn == 11}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按评审状态排序">鉴定状态</a></td>
											<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										</sec:authorize>
									</s:if>
									<td width="54"><a id="sortcolumn12" href="" class="{if sortColumn == 12}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按结项状态排序">结项状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="54"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目状态排序">项目状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="64"><a id="sortcolumn17" href="" class="{if sortColumn == 17}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按结项时间排序">结项时间</a></td>
								</s:else>
								<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY)>0">
									<td width="64"><a id="sortcolumn12" href="" class="{if sortColumn == 12}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按结项状态排序">结项状态</a></td>
								</s:if>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_DELETE">
									<td><input type="checkbox" name="entityIds" value="${item.laData[1]}" /></td>
									<td></td>
								</sec:authorize>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息" type="4">${item.laData[2]}</a></td>
									<td></td>
									<td><input type="hidden" value="${item.laData[5]}" name="${item.laData[6]}" class="directors" /></td>
									<td></td>
								<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
									<td><a id="${item.laData[3]}" class="view_university" href="" title="点击查看详细信息">${item.laData[4]}</a></td>
									<td></td>
								</s:if>
									<td>${item.laData[7]}</td>
									<td></td>
									<td>${item.laData[9]}</td>
									<td></td>
								<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
									<td>
										{if item.laData[15]==3}提交
										{elseif item.laData[15]==2}暂存
										{elseif item.laData[15]==1}退回
										{else}
										{/if}
									</td>
									<td></td>
								</s:if>
								<s:elseif test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY)>0">
									<td>
										{if item.laData[15]==1 && item.laData[16]==1}退回
										{elseif item.laData[15]==1 && item.laData[16]==2}退回
										{elseif item.laData[15]==2 && item.laData[16]==1}不同意（暂存）
										{elseif item.laData[15]==2 && item.laData[16]==2}同意（暂存）
										{elseif item.laData[15]==3 && item.laData[16]==1}不同意
										{elseif item.laData[15]==3 && item.laData[16]==2}同意
										{else}待审
										{/if}
									</td>
									<td></td>
									<td>${item.laData[17]}</td>
									<td></td>
									<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@PROVINCE, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)">
										<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_VIEW">
											<td>
												{if item.laData[18]==2 && item.laData[19]==1}不同意（暂存）
												{elseif item.laData[18]==2 && item.laData[19]==2}同意（暂存）
												{elseif item.laData[18]==3 && item.laData[19]==2}同意
												{elseif item.laData[18]==3 && item.laData[19]==1}不同意
												{else}待审
												{/if}
											</td>
											<td></td>
										</sec:authorize>
									</s:if>
								</s:elseif>
								<s:else>
									<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
										<td>
											{if item.laData[15]==2 && item.laData[16]==1}不同意（暂存）
											{elseif item.laData[15]==2 && item.laData[16]==2}同意（暂存）
											{elseif item.laData[15]==3 && item.laData[16]==2}同意
											{elseif item.laData[15]==3 && item.laData[16]==1}不同意
											{else}待审
											{/if}
										</td>
										<td></td>
										<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_VIEW">
											<td>
												{if item.laData[17]==2 && item.laData[18]==1}不同意（暂存）
												{elseif item.laData[17]==2 && item.laData[18]==2}同意（暂存）
												{elseif item.laData[17]==3 && item.laData[18]==2}同意
												{elseif item.laData[17]==3 && item.laData[18]==1}不同意
												{else}待审
												{/if}
											</td>
											<td></td>
										</sec:authorize>
									</s:if>
										<td>
											{if item.laData[13] == 2 && item.laData[14] == 1 }不同意（暂存）
											{elseif item.laData[13] == 2 && item.laData[14] == 2}同意（暂存）
											{elseif item.laData[13] == 3 && item.laData[14] == 1}不同意
											{elseif item.laData[13] == 3 && item.laData[14] == 2}同意
											{else}待审
											{/if}	
										</td>
										<td></td>
										<td>
											{if item.laData[8] == 0}
											{elseif item.laData[8] == 1}在研
											{elseif item.laData[8] == 3}已中止
											{elseif item.laData[8] == 2}已结项
											{elseif item.laData[8] == 4}已撤项
											{else}
											{/if}
										</td>
										<td></td>
										<td>${item.laData[19]}</td>
								</s:else>
								<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY)>0">
									<td>
										{if item.laData[13] == 3 && item.laData[14] == 1}不同意
										{elseif item.laData[13] == 3 && item.laData[14] == 2}同意
										{else}待审
										{/if}
									</td>
								</s:if>
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
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_DELETE">
									<td width="58"><input id="list_delete" type="button" class="btn1" value="删除" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_PUBLISH">
									<td width="58"><input id="list_publish" type="button" class="btn1" value="发布" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_DATA_PRINT">
									<td width="58"><input id="list_print" type="button" class="btn1" value="打印" /></td>
									<td width="58"><input id="list_export" type="button" class="btn1" value="导出" /></td>
								</sec:authorize>
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="delete" namespace="/project/entrust/endinspection/apply">
						<s:hidden id="pagenumber" name="pageNumber" />
						<s:hidden id="type" name="type" value="1" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<div id="container" style="width:779px;height:300px;"></div>
			<script type="text/javascript">
				seajs.use('javascript/project/entrust/endinspection/apply/list.js', function(list) {
					list.init();
				});
			</script>
		</body>
	
</html>