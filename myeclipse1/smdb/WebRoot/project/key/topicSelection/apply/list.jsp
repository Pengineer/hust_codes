<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>重大攻关项目</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				当前位置：社科项目数据&nbsp;&gt;&nbsp;重大攻关项目&nbsp;&gt;&nbsp;年度选题
			</div>
			
			<div class="main">
				<div class="main_content">
					<div class="table_main_tr_adv">
						<s:include value="/project/key/topicSelection/apply/search.jsp"/>
					</div>
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
								<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLY_DELETE">
									<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有项目" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</sec:authorize>
									<td width="30">序号</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按选题名称排序">课题名称</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="200"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目课题来源排序">项目课题来源</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按选题年度排序">选题年度</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
									<td width="70"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按提交状态排序">提交状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</s:if>
								<s:elseif test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@DEPARTMENT, @csdc.tool.bean.AccountType@INSTITUTE)"><!-- 院系或研究基地-->
									<td width="70"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核状态排序">审核状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="64"><a id="sortcolumn9" href="" class="{if sortColumn == 9}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核时间排序">审核时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</s:elseif>
								<s:elseif test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)"><!-- 高校 -->
									<td width="80"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核状态排序">审核（提交）状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn10" href="" class="{if sortColumn == 10}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核时间排序">审核（提交）时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</s:elseif>
								<s:elseif test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@PROVINCE)"><!-- 省厅 -->
									<td width="64"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核状态排序">审核状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="64"><a id="sortcolumn11" href="" class="{if sortColumn == 11}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核时间排序">审核时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</s:elseif>
								<s:else>
									<td width="80"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按选题状态排序">选题状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn12" href="" class="{if sortColumn == 12}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按选题时间排序">选题时间</a></td>
								</s:else>
								<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY)>0">
									<td width="64"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按选题结果排序">选题状态</a></td>
								</s:if>	
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
								<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLY_DELETE">
									<td><input type="checkbox" name="topsIds" value="${item.laData[0]}" /></td>
									<td></td>
								</sec:authorize>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息" type="12">${item.laData[1]}</a></td>
									<td></td>
									<td>
										{if item.laData[2]=="0"}教育部							
										{elseif item.laData[2]=="1"}高校：<a id="${item.laData[5]}" class="view_university" href="" title="点击查看详细信息">${item.laData[6]}</a>
										{elseif item.laData[2]=="2"}专家：<s:hidden id="directors" value="${item.laData[3]}" name="${item.laData[4]}" cssClass="directors" />
										{/if}
									</td>
									<td></td>
									<td>${item.laData[7]}</td>
									<td></td>
								<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
									<td>
										{if item.laData[12]==3}提交
										{elseif item.laData[12]==2}暂存
										{elseif item.laData[12]==1}退回
										{else}
										{/if}
									</td>
									<td></td>
								</s:if>
								<s:elseif test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY)>0">
									<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)"><!-- 高校 -->
										<td>
										{if item.laData[12]==0 && item.laData[15]==3}提交
										{elseif item.laData[12]==0 && item.laData[15]==2}暂存
										{elseif item.laData[12]==0 && item.laData[15]==1}退回
										{elseif item.laData[12]==2 && item.laData[13]==1 && item.laData[15]==0}不同意（暂存）
										{elseif item.laData[12]==1 && item.laData[13]==1 && item.laData[15]==0}退回
										{elseif item.laData[12]==2 && item.laData[13]==2 && item.laData[15]==0}同意（暂存）
										{elseif item.laData[12]==1 && item.laData[13]==2 && item.laData[15]==0}退回
										{elseif item.laData[12]==3 && item.laData[13]==2 && item.laData[15]==0}同意
										{elseif item.laData[12]==3 && item.laData[13]==1 && item.laData[15]==0}不同意
										{else}待审
										{/if}
										</td>
									</s:if>
									<s:else>
										<td>
										{if item.laData[12]==2 && item.laData[13]==1}不同意（暂存）
										{elseif item.laData[12]==1 && item.laData[13]==1}退回
										{elseif item.laData[12]==2 && item.laData[13]==2}同意（暂存）
										{elseif item.laData[12]==1 && item.laData[13]==2}退回
										{elseif item.laData[12]==3 && item.laData[13]==2}同意
										{elseif item.laData[12]==3 && item.laData[13]==1}不同意
										{else}待审
										{/if}
										</td>
									</s:else>
									<td></td>
									<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)"><!-- 高校 -->
										{if item.laData[14] != ''}
											<td>${item.laData[14]}</td>
										{else}
											<td>${item.laData[16]}</td>
										{/if}
									</s:if>
									<s:else>
										<td>${item.laData[14]}</td>
									</s:else>
									<td></td>
								</s:elseif>
								<s:else>
									<td>
										{if item.laData[10] == 2 && item.laData[8] == 1 }不同意（暂存）
										{elseif item.laData[10] == 2 && item.laData[8] == 2}同意（暂存）
										{elseif item.laData[10] == 3 && item.laData[8] == 1}不同意
										{elseif item.laData[10] == 3 && item.laData[8] == 2}同意
										{else}待审
										{/if}		
									</td>
									<td></td>
									<td>${item.laData[12]}</td>
								</s:else>
								<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY)>0">
									<td>
										{if item.laData[10] == 3 && item.laData[8] == 1}不同意
										{elseif item.laData[10] == 3 && item.laData[8] == 2}同意
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
								<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
									<td width="58" align="center"><sec:authorize ifAllGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_DATA_ADD"><input class="btn1" type="button" id="list_add_result" value="添加" /></sec:authorize></td>
								</s:if>
								<s:else>
									<td width="58" align="center"><sec:authorize ifAllGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLY_ADD"><input class="btn1" type="button" id="list_add" value="申请" /></sec:authorize></td>
									<s:hidden name="deadline" id="deadline" value="%{projectService.checkIfTimeValidate(#session.loginer.currentType, '046')}"/>
									<s:hidden name="topsStatus" id="topsStatus" value="%{projectService.getBusinessStatus('046')}"/>
									<s:hidden name="accountType" id="accountType" value="%{#session.loginer.currentType}"/>
								</s:else>
								<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLY_DELETE">
									<td width="58"><input id="list_delete" type="button" class="btn1" value="删除" /></td>
								</sec:authorize>
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="delete" namespace="/project/key/topicSelection/apply">
						<s:hidden id="pagenumber" name="pageNumber" />
						<s:hidden id="type" name="type" value="1" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<div id="container" style="width:779px;height:300px;"></div>
			<script type="text/javascript">
				seajs.use('javascript/project/key/topicSelection/list.js', function(list) {
					list.init();
				});
			</script>
		</body>
	
</html>