<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>我的备忘录</title>
			<s:include value="/innerBase.jsp" />
			
		</head>
  
       <body>
			<div class="link_bar">
				当前位置：我的备忘录
			</div>
			<div class="main">
				<div class="main_content">
					<s:form id="search" theme="simple" action="list" namespace="/selfspace/memo">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<tr class="table_main_tr">
								<td style="width:80px;text-align:center;">
									<input id="list_add" class="btn1" type="button" value="添加"/>
								</td>
								<td align="right"><span class="choose_bar">
									<s:select name="searchType" headerKey="-1" headerValue="--%{getText('不限')}--"
											list="#{'1':'标题','2':'是否提醒','3':'提醒方式'}"/>
									</span><s:textfield id="keyword" name="keyword" cssClass="keyword" size="10"/>
									<s:hidden id="list_pagenumber" name="pageNumber"/>
									<s:hidden id="list_sortcolumn" name="sortColumn"/>
									<s:hidden id="list_pagesize" name="pageSize"/>
								</td>
								<td style="width:60px;"><input id="list_button_query" type="button" value="检索" class="btn1"/></td>
								<td style="width:80px;"><input id="list_button_advsearch" type="button" value="高级检索" class="btn2" /></td>
							</tr>
						</table>
					</s:form>
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
									<td width="20"><input id="check" name="check" type="checkbox"  title="<s:text name='全选' />" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="30">序号</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="180"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="">标题</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="120"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="">更新时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="60">是否提醒</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="100">提醒方式</td>
								</tr>
							</thead>
							<tbody>
							 {for item in root}
								<tr>
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td></td>
									<td>${item.num}</td>
									<td></td>
									<td><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息">${item.laData[2]}</a>
									</td>
									<td></td>
									<td>${item.laData[4]}</td>
									<td></td>
									<td>
										{if item.laData[6] == "1" }是
										{else}否
										{/if}
									</td>
									<td></td>
									<td><span id="remind_type">
									    {if item.laData[7] == 1}指定日期
									    {elseif item.laData[7] == 2}按天
									    {elseif item.laData[7] == 3}按周
									    {elseif item.laData[7] == 4}按月
<%--									    {elseif item.laData[7] == 5}按倒计时--%>
<%--									    {elseif item.laData[7] == 6}按阴历--%>
									    {else}无
									    {/if}</span>
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
								<td style="width:10px;"></td>
								<td style="width:58px;">
									<input id="list_delete" type="button" class="btn1" value="删除"/>
								</td>
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="delete" namespace="/selfspace/memo">
						<s:hidden id="pagenumber" name="pageNumber" />
						<s:hidden id="type" name="type" value="1" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/lib/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script> 
			<script type="text/javascript" src="javascript/lib/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/list_old.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/selfspace/memo/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			
		</body>

</html>

