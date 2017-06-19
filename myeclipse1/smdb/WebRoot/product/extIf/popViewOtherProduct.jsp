<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查看</title>
		<s:include value="/innerBase.jsp"/>
	</head>

	<body>
		<div style="width:600px;overflow-y:scroll;height:500px;">
			<div class="main">
				<s:form id="view_product" action="" theme="simple" namespace="/product/otherProduct">
					<s:hidden id="update" name="update"/>
					<s:hidden id="entityId" name="entityId" value="%{entityId}"/>
					<s:hidden id="entityIds" name="entityIds" value="%{entityId}"/>
					<s:hidden id="productType" value="6"/>
					<s:hidden id="protype" value="otherProduct"/>
					<s:hidden id="productflag" name="productflag"/>
				</s:form>
				
				<textarea id="view_template_base" style="display:none;">
					<div class="main_content">
						<div class="title_bar">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9"/></td>
									<td class="title_bar_td" width="74" align="right">成果名称：</td>
									<td class="title_bar_td">${otherProduct.chineseName}</td>
								</tr>
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9"/></td>
									<td class="title_bar_td" width="74" align="right">英文名称：</td>
									<td class="title_bar_td" colspan="4">${otherProduct.englishName}</td>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9"/></td>
									<td class="title_bar_td" width="74" align="right">第一作者：</td>
									<td class="title_bar_td" width="150">
									{if authorid!=null}<a id="${authorid}" class="view_author" href="">${otherProduct.authorName}</a>
									{else}${otherProduct.authorName}
									{/if}
									</td>
									<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9"/></td>
									<td class="title_bar_td" align="right" width="74">其他作者：</td>
									<td class="title_bar_td">${otherProduct.otherAuthorName}</td>
								</tr>
								<tr>
									<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9"/></td>
									<td class="title_bar_td" align="right">所属单位：</td>
									<td class="title_bar_td">
										{if universityid!=null && universityid!=""}<a id="${universityid}" class="view_university" href="">${otherProduct.agencyName}</a>
										{else}${otherProduct.agencyName}
										{/if}
										</td>
									<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9"/></td>
									<td class="title_bar_td" align="right">所属部门：</td>
									<td class="title_bar_td">${otherProduct.divisionName}</td>
								</tr>
								<tr>
									<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9"/></td>
									<td class="title_bar_td" align="right">审核结果：</td>
									<td class="title_bar_td" width="150">
										{if otherProduct.auditResult==0}待审
							   			{elseif otherProduct.auditResult==1}不同意
							   			{elseif otherProduct.auditResult==2}同意
							   			{/if}  
						   			</td>
									<td class="title_bar_td" align="right"><img src="image/ico09.gif"/></td>
						   			<td class="title_bar_td">
						   				<a href="" class="downlaod_product" name="${otherProduct.file}" id="${otherProduct.id}">下载成果文件</a>
						   			</td>
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
									<td class="key" width="100">支持课题：</td>
									<td class="value" width="120">${otherProduct.supportProject}</td>
									<td class="key" width="120">是否与国（境）外合作：</td>
									<td class="value">{if otherProduct.isForeignCooperation==0}否{else}是{/if}</td>
								</tr>
								<tr class="table_tr7">
									<td class="key">形态：</td>
									<td class="value">${form}</td>
									<td class="key">成果子类型：</td>
									<td class="value">${otherProduct.subtype}</td>
								</tr>
								<tr class="table_tr7">
									<td class="key">学科门类：</td>
									<td class="value">${otherProduct.disciplineType}</td>
									<td class="key">学科代码：</td>
									<td class="value">${otherProduct.discipline}</td>
								</tr>
								<tr class="table_tr7">
									<td class="key">关键词：</td>
									<td class="value" colspan="3">${otherProduct.keywords}</td>
								</tr>
								<tr class="table_tr7">
									<td class="key">评价：</td>
									<td class="value" colspan="3">${otherProduct.evaluation}</td>
								</tr>
								<tr class="table_tr7">
									<td class="key">简介：</td>
									<td class="value" colspan="3"><pre>${otherProduct.introduction}</pre></td>
								</tr>
							</table>
						</div>
					</div>
				</textarea>
				
				<textarea id="view_template_project" style="display:none;">
					<div class="p_box_body">
						{for item in relProjectInfos}
							<div class="p_box_body_t">
								<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
									<tr class="table_tr7">
										<td class="key" width="120">所属项目：</td>
										<td class="value" width="120"><a id="${item.grantedId}" name="${item.projectType}" class="view_project" href="">${item.projectName}</a></td>
										<td class="key" width="100">项目类型：</td>
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
									<td class="key" width="100">出版单位：</td>
									<td class="value" width="120">${otherProduct.publishUnit}</td>
									<td class="key" width="120">出版时间：</td>
									<td class="value">${otherProduct.pressDate}</td>
								</tr>
							</table>
						</div>
					</div>
				</textarea>
				
				<textarea id="view_template_award" style="display:none;">
					<div class="p_box_body">
						<div class="p_box_body_t">
							{if relAwardInfos.award!=null}
								<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
									<tr class="table_tr7">
										<td class="key" width="120">证书编号：</td>
										<td class="value" width="120">${relAwardInfos.award.number}</td>
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
								{elseif relAwardInfos.submitStatus == 2 || relAwardInfos.submitStatus == 1}
									<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
										<tr class="table_tr4">
											<td>已申请人文社科成果奖</td>
											<td>
												<div class="p_box_b_2">
													<a href="" id="download_award_model">
													<span>下载人文社科奖申请书模板</span></a>
												</div>
											</td>
										</tr>
										<tr class="table_tr4">
											<td>保存时间：${relAwardInfos.saveDate}</td>
											<td>
												<div class="p_box_b_2">
													<a href="" class="downlaodAwardFile" name="${relAwardInfos.file}" id="${relAwardInfos.awardApplicationId}">
													<span>下载人文社科奖申请书</span></a>
												</div>
											</td>
										</tr>
									</table>
								{else}
									<div class="p_box_b" style="text-align:center;">
										该成果没有申请奖励！
									</div>
								{/if}
							{else}
								<div class="p_box_b" style="text-align:center;">
									该成果没有申请奖励！
								</div>
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
							<li><a href="#publication">采纳信息</a></li>
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
		</div>
		<script type="text/javascript">
			seajs.use('javascript/product/view.js', function(view) {
				$(function(){
					view.init();
				})
			});
		</script>
	</body>
</html>