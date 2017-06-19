<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查看</title>
		<s:include value="/innerBase.jsp"/>
	</head>

	<body>
		<div class="link_bar">
			<s:if test="productflag==1">
				当前位置：个人成果&nbsp;&gt;&nbsp;查看
			</s:if>
			<s:else>
				当前位置：社科成果数据&nbsp;&gt;&nbsp;电子出版物&nbsp;&gt;&nbsp;查看
			</s:else>
		</div>

		<div class="main">
			<s:hidden id="update" name="update"/>
			<s:hidden id="entityId" name="entityId" value="%{entityId}"/>
			<s:hidden id="entityIds" name="entityIds" value="%{entityId}"/>
			<s:hidden id="productType" value="4"/>
			<s:hidden id="protype" value="electronic"/>
			<s:hidden id="productflag" name="productflag"/>
			
			<textarea id="view_template_base" style="display:none;">
				<div class="choose_bar">
					<ul>
						<li id="view_back"><input class="btn1" type="button" value="返回"/></li>
						<s:if test="productflag!=1">
							<li id="view_next"><input class="btn1" type="button" value="下条"/></li>
							<li id="view_prev"><input class="btn1" type="button" value="上条"/></li>
							<sec:authorize ifAllGranted="ROLE_PRODUCT_DELETE">
								<li id="view_del"><input class="btn1" type="button" value="删除"/></li>
							</sec:authorize>
							<sec:authorize ifAllGranted="ROLE_PRODUCT_MODIFY">
								{if canBeModify}
								<li id="view_mod"><input class="btn1" type="button" value="修改"/></li>
								{/if}
							</sec:authorize>
							<sec:authorize ifAllGranted="ROLE_PRODUCT_ADD">
								<li id="view_add"><input class="btn1" type="button" value="添加"/></li>
							</sec:authorize>
						</s:if>
					</ul>
				</div>
				
				<div class="main_content">
					<div class="title_bar">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9"/></td>
								<td class="title_bar_td" width="74" align="right">出版物名称：</td>
								<td class="title_bar_td">${electronic.chineseName}</td>
							</tr>
							<tr>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9"/></td>
								<td class="title_bar_td" width="74" align="right">英文名称：</td>
								<td class="title_bar_td">${electronic.englishName}</td>
							</tr>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9"/></td>
								<td class="title_bar_td" width="74" align="right">第一作者：</td>
								<td class="title_bar_td" width="200">
									{if electronic.orgName != null}
										<a id="${electronic.id}" class="view_organization" href="">${electronic.orgName}</a>
									{elseif authorid != null}
										<a id="${authorid}" class="view_author" href="">${electronic.authorName}</a>
									{else}
										${electronic.authorName}
									{/if}
								</td>
								<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9"/></td>
								<td class="title_bar_td" align="right" width="74">其他作者：</td>
								<td class="title_bar_td" colspan="3">${electronic.otherAuthorName}</td>
							</tr>
							<tr>
								<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9"/></td>
								<td class="title_bar_td" align="right">所属单位：</td>
								<td class="title_bar_td">
									{if universityid!=null && universityid !=""} <a id="${universityid}" class="view_university" href="">${electronic.agencyName}</a>
									{else}${electronic.agencyName}
									{/if}
								</td>
								<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9"/></td>
								<td class="title_bar_td" align="right">所属部门：</td>
								<td class="title_bar_td" colspan="3">${electronic.divisionName}</td>
							</tr>
							<tr>
								<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9"/></td>
								<td class="title_bar_td" align="right">审核状态：</td>
								<td class="title_bar_td" >
									{if electronic.auditStatus==0}待审
									{else}已审
						   			{/if} 
						   			<!-- 管理人员均能审核 -->
						   			<s:if test=" #session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0">
						   				<span id="view_audit">[<a href="" title="审核">审核</a>]</span>
						   				{if electronic.auditStatus==3}
						   					<span id="view_audit_info">[<a href="" title="查看详情">查看详情</a>]</span>
						   				{/if}
						   			</s:if>
					   			</td>
					   			<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9"/></td>
								<td class="title_bar_td" align="right">审核结果：</td>
								<td class="title_bar_td" width="120">
									{if electronic.auditResult==0}待审
						   			{elseif electronic.auditResult==1}不同意
						   			{elseif electronic.auditResult==2}同意
						   			{/if}  
					   			</td>
					   			{if electronic.file != null}
						   			<td class="title_bar_td" align="right"><img src="image/ico09.gif"/></td>
						   			<td class="title_bar_td">
						   				<a href="" class="downlaod_product" name="${electronic.file}" id="${electronic.id}">下载成果文件</a>
										({if attachmentSizeList[0] != null}
											${attachmentSizeList[0]}
											{else}附件不存在
										{/if})
									</td>
								{/if}
							</tr>
						</table>
					</div>
				</div>
			</textarea>
			
			<textarea id="view_template_basic" style="display:none;">
				<div class="p_box_body">
					<div class="p_box_body_t">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
							<tr class="table_tr7">
								<td class="key" width="145">是否为译文：</td>
								<td class="value" width="200">{if electronic.isTranslation==0}否{elseif electronic.isTranslation==1}是{/if}</td>
								<td class="key" width="145">是否与国（境）外合作：</td>
								<td class="value">{if electronic.isForeignCooperation==null || electronic.isForeignCooperation==0}否{else}是{/if}</td>
							</tr>
							<tr class="table_tr7">
								<td class="key">出版物类型：</td>
								<td class="value">${electronicType}</td>
								<td class="key">形态：</td>
								<td class="value">${form}</td>
							</tr>
							<tr class="table_tr7">
								<td class="key">学科门类：</td>
								<td class="value">${electronic.disciplineType}</td>
								<td class="key">学科代码：</td>
								<td class="value">${electronic.discipline}</td>
							</tr>
							<tr class="table_tr7">
								<td class="key">关键词：</td>
								<td class="value" colspan="3">${electronic.keywords}</td>
							</tr>
							<tr class="table_tr7">
								<td class="key">简介：</td>
								<td class="value" colspan="3"><pre>${electronic.introduction}</pre></td>
							</tr>
						</table>
					</div>
				</div>
			</textarea>
			
			<textarea id="view_template_project" style="display:none;">
				<div class="p_box_body">
					<!-- 一个成果关联若干项目 -->
					{for item in relProjectInfos}
						<div class="p_box_body_t">
							<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
								<tr class="table_tr7">
									<td class="key" width="120">所属项目：</td>
									<td class="value" width="200"><a id="${item.grantedId}" name="${item.projectType}" class="view_project" href="">${item.projectName}</a></td>
									<td class="key" width="120">项目类型：</td>
									<td class="value">${item.projectTypeName}</td>
								</tr>
								<tr class="table_tr7">
									<td class="key">是否年检成果：</td>
									<td class="value">{if item.isAnnProduct}是{else}否{/if}</td>
									<td class="key">是否中检成果：</td>
									<td class="value">{if item.isMidProdduct}是{else}否{/if}</td>
								</tr>
								<tr class="table_tr7">
									<td class="key">是否结项成果：</td>
									<td class="value">{if item.isEndProduct}是{else}否{/if}</td>
									<td class="key">是否标注教育部社科项目资助：</td>
									<td class="value">{if item.isMarkMoeSupport}是{else}否{/if}</td>
								</tr>
							</table>
						</div>
					{forelse}
						<div class="p_box_b" style="text-align:center;">
							该成果无资助项目！
						</div>
					{/for}
				</div>		
			</textarea>
			
			<textarea id="view_template_publication" style="display:none;">
				<div class="p_box_body">
					<div class="p_box_body_t">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
							<tr class="table_tr7">
								<td class="key" width="120">出版单位：</td>
								<td class="value" width="200">${electronic.publishUnit}</td>
								<td class="key" width="120">出版时间：</td>
								<td class="value">${electronic.publishDate}</td>
							</tr>
							<tr class="table_tr7">
								<td class="key" width="120">采纳单位：</td>
								<td class="value" colspan="3">${electronic.useUnit}</td>
							</tr>
						</table>
					</div>
				</div>
			</textarea>
			
			<textarea id="view_template_publication" style="display:none;">
				<div class="p_box_body">
					<div class="p_box_body_t">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
							<tr class="table_tr7">
								<td class="key" width="120">出版单位：</td>
								<td class="value" width="200">${electronic.publishUnit}</td>
								<td class="key" width="120">出版时间：</td>
								<td class="value">${electronic.publishDate}</td>
							</tr>
						</table>
					</div>
				</div>
			</textarea>
			
			<textarea id="view_template_award" style="display:none;">
				<div class="p_box_body">
					<div class="p_box_body_t">
						{if null != relAwardInfos.award}
							<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
								<tr class="table_tr7">
									<td class="key" width="100">证书编号：</td>
									<td class="value" width="200">${relAwardInfos.award.number}</td>
									<td class="key" width="100">学科门类：</td>
									<td class="value">${relAwardInfos.dtype}</td>
								</tr>
								<tr class="table_tr7">
									<td class="key">获奖等级：</td>
									<td class="value">${relAwardInfos.awardGrade}</td>
									<td class="key">获奖届次：</td>
									<td class="value">${relAwardInfos.award.session}</td>
								</tr>
								<tr class="table_tr7">
									<td class="key">获奖年度：</td>
									<td class="value">${relAwardInfos.award.year}</td>
									<td class="key">获奖时间：</td>
									<td class="value">${relAwardInfos.award.date}</td>
								</tr>
							</table>
						{elseif relAwardInfos.isAwarded==1}
							<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
								<tr class="table_tr4"><td>该成果没有获奖！</td></tr>
							</table>
						{elseif relAwardInfos.isAwarded==0}
							{if relAwardInfos.submitStatus==3}
								<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
									<tr class="table_tr4"><td>审核中！</td></tr>
								</table>
							{elseif relAwardInfos.submitStatus==2 || relAwardInfos.submitStatus==1}
								<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
									<tr class="table_tr4">
										<td>已申请人文社科成果奖</td>
										<td><span><img src="image/ico09.gif" /><a href="" id="download_award_model">下载人文社科奖申请书模板</a></span></td>
									</tr>
									<tr class="table_tr4">
										<td>保存时间：${relAwardInfos.saveDate}</td>
										<td><img src="image/ico09.gif" /><a href="" class="downlaodAwardFile"  name="${relAwardInfos.file}" id="${relAwardInfos.awardApplicationId}">
											<span>下载人文社科奖申请书</span></a></td>
									</tr>
									<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_MODIFY,ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_ADD,ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_DELETE">
										<tr class="table_tr4">
											<td colspan="2">
												<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_MODIFY">
													<input class="btn1" type="button" id="modify_apply" alt="${relAwardInfos.awardApplicationId}" value="修改"/>&nbsp;&nbsp;
												</sec:authorize>
												<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_ADD">
													<input class="btn1" type="button" id="submit_apply" alt="${relAwardInfos.awardApplicationId}" value="提交"/>&nbsp;&nbsp;
												</sec:authorize>
												<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_DELETE">
													<input class="btn1" type="button" id="delete_apply" alt="${relAwardInfos.awardApplicationId}" value="删除"/>&nbsp;&nbsp;
												</sec:authorize>
											</td>
										</tr>
									</sec:authorize>
								</table>
							{else}
								<div class="p_box_b" style="text-align:center;">
									该成果没有申请奖励！
								</div>
							 	<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_ADD">
									<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@TEACHER)">
              								<div class="p_box_b">
                  								<div class="p_box_b_1">
                  									<input class="btn2" type="button" id="apply_award" value="申请奖励"/>
											</div>
											<div class="p_box_b_2">
												<span><a href="" id="download_award_model">下载人文社科奖申请书模板</a></span>
											</div>
										</div>
									</s:if>
							 	</sec:authorize>
							{/if}
						{else}
							<div class="p_box_b" style="text-align:center;">
								该成果没有申请奖励！
							</div>
							<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_ADD">
								<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@TEACHER)">
          								<div class="p_box_b">
              								<div class="p_box_b_1">
              									<input class="btn2" type="button" id="apply_award" value="申请奖励"/>
										</div>
										<div class="p_box_b_2">
											<span><a href="" id="download_award_model">下载人文社科奖申请书模板</a></span>
										</div>
									</div>
								</s:if>
				 	  		</sec:authorize>
						{/if}
					</div>
				</div>
			</textarea>
			
		 	<div id="view_container_base" style="display:none; clear:both;"></div>
	 		<div class="main_content" style="display:none;" id="view_content">
				<div id="tabs" class="p_box_bar">
					<ul>
						<li><a href="#basic">基本信息</a></li>
						<li><a href="#project">资助项目</a></li>
						<li><a href="#publication">出版信息</a></li>
						<li><a href="#award">奖励信息</a></li>
					</ul>
				</div>
				<div class="p_box">
					<div id="basic">
						<div id="view_container_basic" style="display:none; clear:both;"></div>
					</div>
					<div id="project">
						<div id="view_container_project" style="display:none; clear:both;"></div>
					</div>
					<div id="publication">
						<div id="view_container_publication" style="display:none; clear:both;"></div>
					</div>
					<div id="award">
						<div id="view_container_award" style="display:none; clear:both;"></div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/product/view.js', function(view) {
				view.init();
			});
		</script>
	</body>
</html>