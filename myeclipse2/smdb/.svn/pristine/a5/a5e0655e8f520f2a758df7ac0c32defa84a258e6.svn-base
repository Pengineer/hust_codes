<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>项目分类预测</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：分类预测挖掘&nbsp;&gt;&nbsp;
			<s:if test="predictType=='general_end'">一般项目结项预测</s:if>
			<s:elseif test="predictType=='general_mid'">一般项目中检预测</s:elseif>
		</div>
		
		<div class="choose_bar">
			<ul>
				<li><input onclick="history.back();" class="btn1" type="button" value="返回"/></li>
			</ul>
		</div>
		
		<div id="graphHolder" style="text-align: center;"></div>
	
		<s:form id="search" theme="simple" action="list" namespace="/dataMining/classification">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<tr class="table_main_tr">
					<td align="left">预测年度：<s:property value="predictYear"/></td>
					<td align="right"><span class="choose_bar">
						<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{getText('不限')}--"
							list="#{'1':'项目名称','2':'项目子类','3':'学科门类'}"/>
						</span><s:textfield id="keyword" name="keyword" cssClass="keyword" size="18"/>
						<s:hidden id="list_pagenumber" name="pageNumber"/>
						<s:hidden id="list_sortcolumn" name="sortColumn"/>
						<s:hidden id="list_pagesize" name="pageSize"/>
						<s:hidden id="predictType" name="predictType"/>
						<s:hidden id="predictYear" name="predictYear"/>
						<s:hidden id="toDataBase" name="toDataBase"/>
					</td>
					<td width="60"><input id="list_button_query" type="button" value="检索" class="btn1"/></td>
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
						<td width="100">项目子类</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="100">学科门类</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="100">项目年度</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="100">项目进展年数</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="80">中检状态</td>
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
						<td></td>
						<td>${item.laData[5]}</td>
						<td></td>
						<td>${item.laData[6]}</td>
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
	
		<script type="text/javascript">
			seajs.use('javascript/dataMining/classification/predict.js', function(predict) {
				$(function(){
					predict.init();
				})
			});
		</script>
	</body>	
</html>