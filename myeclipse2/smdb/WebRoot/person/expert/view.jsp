<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查看专家</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar" id="view_person_expert">
			当前位置：社科人员数据&nbsp;&gt;&nbsp;社科研究人员&nbsp;&gt;&nbsp;外部专家&nbsp;&gt;&nbsp;查看
		</div>

		<div class="main">
			<s:hidden id="entityId" name="entityId" value="%{entityId}" />

			<div class="choose_bar">
				<ul>
					<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
					<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
					<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
					<li id="view_del"><sec:authorize ifAllGranted="ROLE_PERSON_EXPERT_DELETE"><input class="btn1" type="button" value="删除" /></sec:authorize></li>
					<li id="view_mod"><sec:authorize ifAllGranted="ROLE_PERSON_EXPERT_MODIFY"><input class="btn1" type="button" value="修改" /></sec:authorize></li>
					<li id="view_add"><sec:authorize ifAllGranted="ROLE_PERSON_EXPERT_ADD"><input class="btn1" type="button" value="添加" /></sec:authorize></li>
				</ul>
			</div>

			<div class="main_content" style="display:none;" id="tabcontent">
				<div id="name_gender" class="title_bar">
					<textarea class="view_template" style="display:none;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" width="100" align="right" >姓名：</td>
								<td class="title_bar_td" width="100" id="personName" >
									${person.name}
								</td>
								<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" width="100" align="right">性别：</td>
								<td class="title_bar_td">${person.gender}</td>

								<sec:authorize ifAnyGranted="ROLE_SYSTEM_KEY,ROLE_SYSTEM_KEY_VIEW,ROLE_SYSTEM_KEY_MODIFY">
									<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="100" align="right" >
										标识：
									</td>
									<td class="title_bar_td" width="100" id="personName" >
										<a class="toggleKey" href="javascript:void(0)" alt="${person.id}" iskey="${person.isKey}">
										{if person.isKey == 1}<img src="image/person_on.png" title="重点人，点击切换至非重点人" />{else}<img src="image/person_off.png" title="非重点人，点击切换至重点人" />{/if}
										</a>
									</td>
								</sec:authorize>
							</tr>
							<tr>
								<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_EXPERT_ADD">
									<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="100" align="right">通行证：</td>
									<td class="title_bar_td" width="130" id="accountDisp">
										{if passportId == null || passportId == ""}
											<span>[<a id="view_addAccount" href="" title="点击创建通行证" >创建</a>]</span>
										{else}
											<span><a id="${passportId}" class="linkPcc" href="" title="点击查看详细信息" >${accountName}</a></span>
										{/if}
									</td>
									<td class="title_bar_td" width="100" align="right">账号：</td>
									<td class="title_bar_td" width="130" id="accountDisp">	
										{if accountId == null || accountId == ""}
											<span>[<a id="view_addAccount" href="" title="点击分配账号" >创建</a>]</span>
										{else}
											<span>[<a id="${accountId}" class="linkAcc" href="" title="点击查看详细信息" >已创建</a>]</span>
										{/if}
									</td>
								</sec:authorize>
							</tr>
						</table>
					</textarea>
				</div>

				<div class="main_content">
					<div id="tabs" class="p_box_bar">
						<ul>
							<li><a href="#basic">基本信息</a></li>
							<li><a href="#affiliation">任职信息</a></li>
							<li><a href="#contact">联系信息</a></li>
							<li><a href="#academic">学术信息</a></li>
							<li><a href="#education">教育背景</a></li>
							<li><a href="#work">工作经历</a></li>
							<li><a href="#abroad">出国（境）经历</a></li>
							<li><a href="#bank">银行信息</a></li>
							<li><a href="#project">参与项目</a></li>
							<li><a href="#product">成果信息</a></li>
						</ul>
					</div>

					<div class="p_box">
						<s:include value="/person/view_basic.jsp" />
						<s:include value="/person/view_expert_affiliation.jsp" />
						<s:include value="/person/view_contact.jsp" />
						<s:include value="/person/view_academic.jsp" />
						<s:include value="/person/view_education.jsp" />
						<s:include value="/person/view_work.jsp" />
						<s:include value="/person/view_abroad.jsp" />
						<s:include value="/person/view_bank.jsp" />
						<s:include value="/person/view_project.jsp" />
						<s:include value="/person/view_product.jsp" />
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/person/expert/view.js', function(view) {
				$(function(){
					view.init();
				})
			});
		</script>
	</body>
</html>
