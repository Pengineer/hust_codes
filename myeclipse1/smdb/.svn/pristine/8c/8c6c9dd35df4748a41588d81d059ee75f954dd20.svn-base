<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored ="true"%> 
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>公示数据</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：社科奖励数据&nbsp;&gt;&nbsp;人文社科奖&nbsp;&gt;&nbsp;公示数据
		</div>
		
		<div class="main">
			<div class="main_content">
				<div class="table_main_tr_adv">
					<s:include value="/award/moesocial/application/publicity/search.jsp"/>
				</div>
				<textarea id="list_template" style="display:none;">
					<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr">
							<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@ADMINISTRATOR)>0">
							 <sec:authorize ifAllGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_PUBLICITYAUDIT_ADD,ROLE_AWARD_MOESOCIAL_APPLICATION_PUBLICITYAUDIT_MODIFY">	
								<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不全选本页所有奖励申请" /></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						 	</sec:authorize>	
						 	</s:if>
								<td width="30">序号</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td ><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按成果名称排序">成果名称</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="51"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按申请人姓名排序">申请人</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="64"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按依托高校排序">依托高校</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="64"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按学科门类排序">学科门类</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="64"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按成果类型排序">成果类型</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="64"><a id="sortcolumn27" href="" class="{if sortColumn == 27}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按获奖等级排序">获奖等级</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="64"><a id="sortcolumn10" href="" class="{if sortColumn == 10}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按获奖届次排序">获奖届次</a></td>
							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@MINISTRY)">
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="64"><a id="sortcolumn19" href="" class="{if sortColumn == 19}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核状态排序">审核状态</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="64"><a id="sortcolumn25" href="" class="{if sortColumn == 25}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按审核时间排序">审核时间</a></td>
							</s:if>
		<!--				<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)">
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="39">申请书</td>
							</s:if>
						-->
							</tr>
						</thead>
						<tbody>
						{for item in root}
							<tr>
								<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@ADMINISTRATOR)>0">
								<sec:authorize ifAllGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_PUBLICITYAUDIT_ADD,ROLE_AWARD_MOESOCIAL_APPLICATION_PUBLICITYAUDIT_MODIFY">	
								<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
								<td></td>
								</sec:authorize>
								</s:if>		
								<td>${item.num}</td>
								<td></td>
								<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息" >${item.laData[1]}</a></td>
								<td></td>
								<td>{if item.laData[8]==""}${item.laData[2]}
									{else}<a id="${item.laData[8]}" class="viewapplicant" href="" title="点击查看详细信息" > ${item.laData[2]}</a>
									{/if}
								</td>
								<td></td>
								<td>{if item.laData[9]==""}${item.laData[3]}
									{else}<a id="${item.laData[9]}" class="viewuniversity" href="" title="点击查看详细信息">${item.laData[3]}</a>
									{/if}
								</td>
								<td></td>
								<td>${item.laData[4]}</td>
								<td></td>
								<td>${item.laData[5]}</td>
								<td></td>
								<td>${item.laData[6]}</td>
								<td></td>
								<td>第<span class="session">${item.laData[7]}</span>届</td>
							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@MINISTRY)">	
								<td></td>
				                <td>
									{if item.laData[11] == 0}待审
				                    {elseif item.laData[11] == 2&&item.laData[12]==1}不同意（暂存）
				                 	{elseif item.laData[11] == 2&&item.laData[12]==2}同意（暂存）
				                 	{elseif item.laData[11] == 3&&item.laData[12]==1}不同意
				                 	{elseif item.laData[11] == 3&&item.laData[12]==2}同意
				                    {/if}
				                 </td>
				                 <td></td>
								<td>${item.laData[13]}</td>
							</s:if>
					<!--	<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY) == 0">	
								 <td></td>
				                 <td>
				                 	{if item.laData[10] != ""}
										<a href=""  class="downlaod" name="${item.laData[10]}" id="${item.laData[0]}"><img src="image/ico03.gif" width="11" height="12" title="下载" /></a>
									{/if}
								</td>
							</s:if>
						  -->
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
							<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@ADMINISTRATOR)>0">
								<sec:authorize ifAllGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_PUBLICITYAUDIT_ADD,ROLE_AWARD_MOESOCIAL_APPLICATION_PUBLICITYAUDIT_MODIFY">	
									<td width="58"><input id="awarded_audits" type="button" class="btn1" value="审核" /></td>
						 		</sec:authorize>	
					 		</s:if>
						</tr>
					</table>
				</textarea>
				<s:form id="list" theme="simple" name="" >
					<s:hidden id="pagenumber" name="pageNumber" />
					<s:hidden id="type" name="type" value="1" />
					<s:hidden name="audflag" value="1"/>
					<s:hidden name="auditResult"/>
					<s:hidden name="auditOpinion"/>
					<s:hidden name="auditOpinionFeedback"/>
					<s:hidden name="year"/>
					<s:hidden name="auditStatus"/>
					<div id="list_container" style="display:none;"></div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/award/moesocial/application/publicity/list.js', function(list) {
				list.init();
				list.initClick();
			});
		</script>
	</body>
</html>