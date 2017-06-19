<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>招聘人员详情</title>
		<s:include value="/innerBase.jsp" />
		<style>
		.value{
			padding-left:10px !important;
		}
		.p_box_bar {
			margin-top: 13px;
			height:100%;
		}
		</style>
	</head>
	<body>
		<div class="link_bar">
			当前位置：招聘管理 &nbsp;&gt;&nbsp;招聘人员&nbsp;&gt;&nbsp;查看
		</div>
		
		<div class="main">
			<s:hidden id="entityId" name="entityId" value="%{entityId}" />
		<div class="choose_bar">
				<ul>
					<li id="view_back"><input class="btn1" type="button" value="返回"></li>
					<li id="view_next"><input class="btn1" type="button" value="下条"></li>
					<li id="view_prev"><input class="btn1" type="button" value="上条"></li>
				</ul>
			</div>
			<div class="main_content" style="display:none;" id="tabcontent">
				<div id="name_gender" class="title_bar">
					<textarea class="view_template" style="display:none;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<s:hidden id="status" name="status"  class = "status" value="${status}" />
							<s:hidden id="appId" name="appId"  value="${appId}" />
							<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="60" align="right" >姓名：</td>
							<td class="title_bar_td"  id="personName" >
								${name}
							</td>
							<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="60" align="right">性别：</td>
							<td class="title_bar_td" >${gender}</td>
						</tr>
						<tr>
							<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class = "title_bar_td" width = "60">申请职位：</td>
							<td class="title_bar_td" >${jobName}</td>
							<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class = "title_bar_td" width = "60">申请状态：</td>
							<td class="title_bar_td" > 
							<span class = "status" >{if status == 0} <span id = "setVerifyResult"> <a  style = "color:#5cb85c" title = "点击修改审核意见" href = "">已申请 </a></span>
							{elseif status == 1} <span id = "setVerifyResult" ><a style = "color:#31B0D5" title = "点击修改审核意见" href = "">审核通过</a></span> 
							{elseif status == 2} <span id = "setVerifyResult" ><a style = "color:#D9534F" title = "点击修改审核意见" href = "">审核不通过</a></span> {/if}
							</span>
							</td>
						</tr>
						<tr>
							<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class = "title_bar_td" width = "60" align="right">简历：</td>
							<td class="title_bar_td" id="resume" class="value" colspan = "3">
								{if fileSize == null}<a href = 'javascript:void(0)' title = '文件不存在'>下载（文件不存在）</a>
								{else}<a href="portal/recruit/fileDownload.action?appId=${appId}">下载（${fileSize }）</a>{/if}
							</td>
						</tr>						
					</table>
					</textarea>
				</div>
				<div class="main_content">
				<div class="p_box_t">
						<div class="p_box_t_t">基本信息</div>
					</div>
					<div id="basic">
						<textarea class="view_template" style="display:none;">
						<div class="p_box_body">
							<div class="p_box_body_t">
								<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
									<tr class="table_tr7">
										<td class="">民族：</td>
										<td class="value">${ethnic}</td>
										<td class="">籍贯：</td>
										<td class="value">${birthPlace}</td>
										<td id="photo" rowspan="4" style="width:120px; padding-top:2px;">
											{if photoFile == null}
												<img style="width:120px; height:135px;" src="image/photo.png" />
											{else}
												<img style="width:120px; height:135px;" src="${photoFile}" />
											{/if}
										</td>
									</tr>
									<tr class="table_tr7">
										<td class="" width="100">电话：</td>
										<td class="value" width="200">${mobilePhone}</td>
										<td class="" width="100">qq：</td>
										<td class="value">${qq}</td>
									</tr>
									<tr class="table_tr7">
										<td class="" width="100">邮箱：</td>
										<td class="value" width="200">${email}</td>
										<td class = "" width = "100">身份证号：</td>
										<td class = "value">${idCardNumber}</td>
									</tr>
									<tr class="table_tr7">
										<td class="">出生日期：</td>
										<td class="value">${birthday}</td>
										<td class="">政治面貌：</td>
										<td class="value">${membership}</td>
									</tr>
									<tr class = "table_tr7">
										<td class = "" width = "100">学历：</td>
										<td class="value"   width = "200">${education}</td>
										<td class = "" width = "100">地址：</td>
										<td class="value" colspan = "3">${address}</td>
									</tr>
									
								</table>
							</div>
						</div>
						</textarea>
					</div>
					</div>
			</div>
			
		</div>
		
		<script type="text/javascript">
			seajs.use('javascript/management/recruit/applicant/view.js', function(view) {
				$(function(){
					view.init();
				})
			});
		</script>
	</body>
</html>