<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>我的奖励</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：我的奖励
		</div>
		
		<div class="main">
			<div class="main_content">
				<s:form id="search" theme="simple" action="%{#request.url}" namespace="/award">
					<s:hidden id="listflag" name="listflag"/>
					<s:hidden id="audflag" name="audflag" value="1"/>
				</s:form>
				<textarea id="list_template" style="display:none;">
					<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr">
								<td width="30">序号</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td>成果名称</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="55">成果类型</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="45">申请人</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="64">依托高校</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="100">奖励类别</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="55">获奖等级</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="55">获奖届次</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="55">获奖年度</td>
							</tr>
						</thead>
						<tbody>
						{for item in root}
							<tr>
								<td>${item.num}</td>
								<td></td>
								<td><a id="${item.laData[0]}" name="${item.laData[12]}" class="link1" href="" title="点击查看详细信息" >${item.laData[1]}</a></td>
								<td></td>
								<td>${item.laData[5]}</td>
								<td></td>
								<td><a id="${item.laData[8]}" class="viewapplicant" href="" title="点击查看详细信息" > ${item.laData[2]}</a></td>
								<td></td>
								<td><a id="${item.laData[9]}" class="viewuniversity" href="" title="点击查看详细信息">${item.laData[3]}</a></td>
								<td></td>
								<td id="subType">${item.laData[11]}</td>
								<td></td>
								<td>${item.laData[6]}</td>
								<td></td>
								<td>第<span class="session">${item.laData[7]}</span>届</td>
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
				<div id="list_container" style="display:none;"></div>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/award/list_my_award.js', function(list) {
				$(function() {
					list.initAward();
				})
			});
		</script>
	</body>
</html>