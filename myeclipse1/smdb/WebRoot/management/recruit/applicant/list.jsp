<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>招聘申请</title>
		<s:include value="/innerBase.jsp" />
	</head>
	
	<body>
		<div class="link_bar">
			当前位置：招聘管理&nbsp;&gt;&nbsp;招聘申请
		</div>
		<div class="main">
			<div class="main_content">					
				<s:form id="search" theme="simple" action="list" namespace="/management/recruit/applicant">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr">
							<td align="right"><span class="choose_bar">
								<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--不限--" value="-1"
									list="#{
										'0':'姓名',
										'1':'职位',
										'2':'申请时间',
										'3':'申请状态'			
									}"
								/>
							</span><s:textfield  id="keyword" name="keyword" cssClass="keyword" size="10" />
								<s:hidden id="list_pagenumber" name="pageNumber" />
								<s:hidden id="list_sortcolumn" name="sortColumn" />
								<s:hidden id="list_pagesize" name="pageSize" />
							</td>
							<td width="60"><input id="list_button_query" type="button" value="检索" class="btn1" /></td>
						</tr>
					</table>
				</s:form>
				
				<textarea id="list_template" style="display:none;">
					<%--<s:hidden id="universityVariationid" name="universityVariationid" />
					--%><table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr">
								<td width="20">选择</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="30">序号</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="80"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按申请人排序">申请人</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="80"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按申请职位排序">申请职位</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="100"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按申请时间排序">申请时间</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="100"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按申请状态排序">申请状态</a></td>
							</tr>
						</thead>
						<tbody style = "text-align:center">
						{for item in root}
							<tr>
								<td><input type="radio" name="appId" value="${item.laData[4]}" /></td>
								<td></td>
								<td>${item.num}</td>
								<td></td>
								<td ><a id="${item.laData[4]}" class="link1" href="javascript:void(0)" title="点击查看详细信息">${item.laData[0]}</a></td>
								<td></td>
								<td>${item.laData[1]}</td>
								<td></td>
								<td class= "applyDate">${item.laData[2]}</td>
								<td></td>
								<td  class  = "status">{if item.laData[3] == 0} <span style = "color:#5cb85c">已申请 </span>{elseif item.laData[3] == 1} <span style = "color:#31B0D5">审核通过
								</span> {elseif item.laData[3] == 2} <span style = "color:#D9534F">审核不通过</span> {/if}</td>
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
							<sec:authorize ifAllGranted="ROLE_RECRUIT_VERIFY">
								<td width="58" align="center"><input id="setVerifyResult" class="btn1" type="button" value="审核意见" /></td>
							</sec:authorize>
						</tr>
					</table>
				</textarea>
				<s:form id="list" theme="simple" action="delete" namespace="/verify">
					<s:hidden id="pagenumber" name="pageNumber" />
					<s:hidden id="checkedIds" name="checkedIds" />
					<div id="list_container" style="display:none;"></div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/management/recruit/applicant/list.js', function(list) {
				$(function(){
					list.init();	
				})
			});
		</script>
	</body>
</html>