<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>研究热点分析</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：领域热点分析&nbsp;&gt;&nbsp;
			<s:if test="type=='general'">一般项目研究热点</s:if>
			<s:elseif test="type=='key'">重大攻关项目研究热点</s:elseif>
			<s:elseif test="type=='instp'">基地项目研究热点</s:elseif>
			<s:elseif test="type=='post'">后期资助项目研究热点</s:elseif>
			<s:elseif test="type=='entrust'">应急委托课题研究热点</s:elseif>
			
			<s:if test="#session.analyzeAngle==0">（申请数据）</s:if>
			<s:elseif test="#session.analyzeAngle==1">（立项数据）</s:elseif>
		</div>
		
		<div class="choose_bar">
			<ul>
				<li><input onclick="history.back();" class="btn1" type="button" value="返回"/></li>
			</ul>
		</div>
		<div class="main_content">
			<div class="title_statistic"></div>
			<div style="margin-top:4px;"><table id="dataList" class="table_statistic" width="100%" cellspacing="0" cellpadding="2"></table></div>
			
			<div id="graphHolder" style="text-align: center;"></div>
			
			<s:form id="search" theme="simple" action="list" namespace="/dataMining/hotspot">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
					<tr class="table_main_tr">
						<td align="left"><span class="choose_bar">
							研究主题：<s:textfield id="keyword" name="keyword" cssClass="keyword" size="20" cssStyle="background:#ccc;" readonly="true"/>
							<s:hidden id="list_pagenumber" name="pageNumber"/>
							<s:hidden id="list_sortcolumn" name="sortColumn"/>
							<s:hidden id="list_pagesize" name="pageSize"/>
							<s:hidden id="type" name="type"/>
							<s:hidden id="analyzeAngle" name="analyzeAngle"/>
							<s:hidden id="topKnum" name="topKnum"/>
						</td>
					</tr>
				</table>
			</s:form>
				
			<textarea id="list_template" style="display:none;">
				<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
					<thead id="list_head">
						<tr class="table_title_tr">
							<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有" /></td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td width="30">序号</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td>项目名称</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td width="200">依托高校</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td width="200">项目负责人</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td width="200">项目年度</td>
						</tr>
					</thead>
					<tbody>
					{for item in root}
						<tr>
							<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
							<td></td>
							<td>${item.num}</td>
							<td></td>
							<td class="table_txt_td"><a id="${item.laData[0]}" class="linkP" href="" title="点击查看详细信息">${item.laData[1]}</a></td>
							<td></td>
							<td>${item.laData[2]}</td>
							<td></td>
							<td>${item.laData[3]}</td>
							<td></td>
							<td>${item.laData[4]}</td>
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
			
			<div id="list_container" style="display:none;"></div>
		</div>
		
		<script type="text/javascript">
			seajs.use(['javascript/dataMining/hotspot/list.js', 'javascript/dataMining/hotspot/hotspot.js'], function(list, hotspot) {
				$(function(){
					list.init();
					hotspot.init("dataMining/hotspot");
				})
			});
		</script>	
	</body>
</html>