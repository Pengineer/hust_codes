<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>社科管理咨询服务中心</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/project/project.css" />
	</head>
  
  <body>
  	<div id="top">
		奖励管理&nbsp;&gt;&gt;&nbsp;人文社科奖励&nbsp;&gt;&gt;&nbsp;
		<s:if test="isReviewable == 0">
			<span class="text_red">退评奖励</span>
		</s:if>
		<s:else>
			<span class="text_red">参评奖励</span>
		</s:else>
	</div>
	
	<div class="main">
		<div class="main_content">
			<s:form id="search" theme="simple" action="list" namespace="/award/moeSocial">
				<table class="table_bar">
					<tr height="28px">
						<s:if test="#session.visitor.user.issuperuser == 1">
							<td width="60px" align="center"><input type="button" id="popExport" value="导出" class="btn" /></td>
						</s:if>
						<s:if test="isReviewable == 1">
							<s:if test="#session.visitor.user.issuperuser == 1">
								<td width="85px" align="center"><input id="matchExpert" type="button" value="自动匹配" class="btn isCurYear" /></td>
							</s:if>
							<td width="85px" align="center"><input id="reMatchSelected" type="button" value="手动匹配" class="btn isCurYear" /></td>
							<s:if test="#session.visitor.user.issuperuser == 1">
								<td width="85px" align="center"><input id="deleteMatches" type="button" value="清空匹配" class="btn isCurYear" /></td>
							</s:if>
						</s:if>
						<td align="right">
							<s:select cssClass="select" name="keyword1" headerKey="-1" headerValue="--%{'所有届次'}--" list="#{'7':'第7届'}" />
							<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{'不限'}--" list="#{'1':'奖励名称','2':'申请人', '3':'依托高校','4':'学科门类','5':'奖励类型'}" />
							<s:textfield id="keyword" name="keyword" cssClass="input wd2" />
							<s:hidden id="list_pagenumber" name="pageNumber" />
							<s:hidden id="list_sortcolumn" name="sortColumn" />
							<s:hidden id="list_pagesize" name="pageSize" />
							<s:hidden id="isReviewable" name="isReviewable" />
							<s:hidden id="listType" name="listType" />
						</td>
						<td width="50px" align="center"><input id="list_button_query" type="button" value="检索" class="btn" /></td>
					</tr>
				</table>
			</s:form>
			
			<textarea id="list_template" style="display:none;">
				<table id="list_table" class="table_list" cellspacing="0" cellpadding="0">
					<thead id="list_head">
						<tr class="tr_list1">
							<td class="wd0 border0"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有项目" /></td>
							<td class="wd1 border0">序号</td>
							<td class="border0" style="width:200px;"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按奖励名称排序">奖励名称</a></td>
							<td class="border0" style="width:100px;"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按申请人排序">申请人</a></td>
							<td class="border0" style="width:85px;"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按高校名称排序">高校名称</a></td>
							<td class="border0" style="width:55px;"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目子类排序">学科门类</a></td>
							<td class="border0" style="width:70px;"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按成果类型排序">成果类型</a></td>
							<td class="border0" style="width:70px;"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按届次排序">届次</a></td>
							<s:if test="isReviewable == 1">
							<td class="wd_auto border0">评审专家</td>
							<td class="border0" style="width:50px;"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按专家个数排序"><img src="image/reviewer.gif" /></a></td>
							<td class="border0" style="width:40px;"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按警告排序"><img src="image/warning_reviewer.gif" /></a></td>
							</s:if>
						</tr>
					</thead>
					<tbody>  
					{for item in root}
						<tr class="tr_list2">
							<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
							<td>${item.num}</td>
							<td class="table_txt_td"><a id="${item.laData[0]}" class="linkView" href="" title="点击查看详细信息" type="1">${item.laData[1]}</a></td>
							<td>${item.laData[2]}</td>
							<td>${item.laData[3]}</td>
							<td>${item.laData[4]}</td>
							<td>${item.laData[5]}</td>
							<td>第${item.laData[6]}届</td>
							<s:if test="isReviewable== 1">
							<td>
								{for item2 in item.laData[9]}
									<a title="${item2.uname}&nbsp;${item2.title}" href="expert/toView.action?entityId=${item2.id}&listType=4&projectId=${item.laData[0]}">${item2.name}</a>&nbsp;
								{/for}
							</td>
							<td>
								<a title="${item.laData[10]}" class="isCurYear" href="award/moeSocial/toSelectExpert.action?entityId=${item.laData[0]}&isReviewable=1">共
										{if item.laData[7] == null}0{else}${item.laData[7]}{/if}
										个
							</td>
							<td>{if item.laData[8] != null}<img src="image/flag_yellow.gif" title="${item.laData[8]}" />{/if}</td>
							</s:if>
						</tr>
					{forelse}
						<tr>
							<td align="center">暂无符合条件的记录</td>
						</tr>
					{/for}
					</tbody>
				</table>
				
				<table class="table_list">
					<tr class="tr_list1" style="border-top-width: 0px;">
						<td class="border1" style="width:40px;"><input id="list_delete" type="button" value="删除" class="btn" /></td> 
					</tr>
				</table>
			</textarea>
					
			<s:form id="list" theme="simple" action="delete" namespace="/award/moeSocial">
				<div id="list_container" style="display:none;"></div>
				<s:hidden id="isReviewable" name="isReviewable" />
				<s:hidden id="listType" name="listType" />
			</s:form>
		</div>
	</div>
	<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/award/moeSocial/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>
