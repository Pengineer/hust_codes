<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>个人成果</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：个人成果
		</div>
		
		<div class="main">
			<div class="main_content">
				<s:form id="search" theme="simple" action="%{#request.url}" namespace="/product">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr"></tr>
					</table>
				</s:form>
				
				<textarea id="list_template" style="display:none;">
					<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr">
								<td width="30">序号</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td>成果名称</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="90">成果形式</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="90">第一作者"</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
								<td width="90">"所属单位"</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="90">学科门类</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="90">提交时间</td>
							</tr>
						</thead>
						<tbody>
						{for item in root}
							<tr>
								<td>${item.num}</td>
								<td></td>
								<td class="table_txt_td"><a id="${item.laData[0]}" name="${item.laData[2]}" class="link1" href="" title="点击查看详细信息" >${item.laData[1]}</a></td>
								<td></td>
								<td>
									{if item.laData[2] == 'paper'}论文
									{elseif item.laData[2] == 'book'}著作
									{elseif item.laData[2] == 'consultation'}研究咨询报告
									{elseif item.laData[2] == 'electronic'}电子出版物
									{elseif item.laData[2] == 'patent'}专利
									{elseif item.laData[2] == 'otherProduct'}其他成果
									{/if}
								</td>
								<td></td>
								<td><a id="${item.laData[4]}" class="linkAuthor" href="" title="点击查看详细信息" >${item.laData[3]}</a></td>
								<td></td>
								<td><a id="${item.laData[6]}" class="linkUni" href="" title="点击查看详细信息" >${item.laData[5]}</a></td>
								<td></td>
								<td>${item.laData[7]}</td>
								<td></td>
								<td>${item.laData[8]}</td>
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
				<s:form id="list" theme="simple" action="delete" namespace="/product">
					<s:hidden id="pagenumber" name="pageNumber"/>
					<s:hidden id="type" name="type" value="1"/>
					<div id="list_container" style="display:none;"></div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/product/list_my_product.js', function(list) {
				$(function() {	
					list.init();
				})
			});
		</script>
	</body>
</html>