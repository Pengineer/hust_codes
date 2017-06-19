<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
   		<title>修改</title>
   		<s:include value="/innerBase.jsp" />
   		<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css" />
		</head>
 		
	<body>
		<div class="link_bar">
			<s:if test="productflag==1">
				当前位置：个人成果&nbsp;&gt;&nbsp;修改
			</s:if>
			<s:else>
				当前位置：社科成果数据&nbsp;&gt;&nbsp;论文&nbsp;&gt;&nbsp;修改
			</s:else>
		</div>
   		
   		<div class="main">
			<s:form id="form_product" action="modify" namespace="/product/paper">
				<s:hidden id="accountType" value="%{#session.loginer.currentType}"/>
				<s:hidden id="addOrModify" value="0"/>
   			 	<s:hidden name="entityId" value="%{paper.id}"/>
   				<s:hidden id="productType" value="1" />
   				<s:hidden id="submitStatus" name="paper.submitStatus"/>
   				<s:hidden name="productflag"/>
   				<div class="main_content">
					<div class="step_css">
						<ul id="mod_tabs">
							<li class="proc step_e" name="base_info"><span class="left_step"></span><span class="right_step">基本信息</span></li>
							<li class="proc" name="project_info"><span class="left_step"></span><span class="right_step">项目信息</span></li>
							<li class="proc" name="publication_info"><span class="left_step"></span><span class="right_step">出版信息</span></li>
							<li class="proc step_oo" name="finish"><span class="left_step"></span><span class="right_step">完成</span></li>
						</ul>
					</div>
				</div>
				<div class="p_box_b" style="margin-top:10px;">
					<div class="p_box_b_2" style="float:right;padding-right:30px;"><img src="image/ico09.gif" />
						<a href="" class="downlaod_product" name="${paper.file}" id="${paper.id}">下载成果文件</a>
					</div>
				</div>
				
   				<div class="main_content" style="margin-top:0px;">
					<s:include value="/validateError.jsp"/>
					<div class="edit_info" id="base_info">
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td2" width="150">成果文件：</span></td>
								<td class="table_td3">
									<input type="file" id="file_${paper.id}" />
								</td>
								<s:hidden id = "paperId" name = "paper.id"></s:hidden>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">论文名称：</span></td>
								<td class="table_td3"><s:textfield name="paper.chineseName" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">英文名称：</td>
								<td class="table_td3"><s:textfield name="paper.englishName" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
						</table>
						
						<!-- 个人成果 -->
						<s:if test="applyType == 0">
							<table width="100%" border="0" cellspacing="2" cellpadding="2" id="personal_info" >
								<!-- 管理人员 -->
								<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0">
									<tr class="table_tr2">
										<td class="table_td2" width="150"><span class="table_title5">第一作者：</span></td>
										<td class="table_td3" id="err_firstAuthor">
											<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@PROVINCE)>0">
												<s:select cssClass="select" cssStyle="float:left; margin-right:10px;" id="person_authorType" name="authorType" list="#{'1': '教师', '3': '学生'}" headerKey="-1" headerValue="--%{'请选择'}--"/>
											</s:if>
											<s:else>
												<s:select cssClass="select" cssStyle="float:left; margin-right:10px;" id="person_authorType" name="authorType" list="#{'1': '教师', '2': '外部专家', '3': '学生'}" headerKey="-1" headerValue="--%{'请选择'}--"/>
											</s:else>
											<input type="button" class="btn2 select_btn select_author_btn" value="选择" alt="person"/>
											<div id="person_per_name_div" class="choose_show"><s:property value="paper.authorName"/>&nbsp;&nbsp;<s:property value="paper.agencyName"/>&nbsp;<s:property value="paper.divisionName"/></div>
											<s:hidden id="person_per_id" name="authorId"/>
											<s:hidden id="person_authorTypeId" name="authorTypeId"/>
										</td>
										<td class="table_td4"></td>
									</tr>
								</s:if>
								<!-- 研究人员 -->
								<s:else>
									<s:hidden id="person_per_id" name="authorId" value="%{#session.loginer.account.person.id}"/>
									<s:hidden name="authorType" value="%{authorType}"/>
									<s:hidden id="person_authorTypeId" name="authorTypeId"/>
								</s:else>
								<tr class="table_tr2">
									<td class="table_td2" width="150">其他作者：</td>
									<td class="table_td3"><s:textfield name="paper.otherAuthorName" cssClass="input_css"/></td>
									<td class="table_td4"></td>
								</tr>
							</table>
						</s:if>
						<!-- 团队成果 -->
						<s:else>
							<table width="100%" border="0" cellspacing="2" cellpadding="2" id="team_info">
								<!-- 管理人员 -->
								<!-- 团队名称 -->
								<tr class="table_tr2">
									<td class="table_td2" width="150"><span class="table_title5">团队名称：</span></td>
									<td class="table_td3"><s:textfield name="paper.orgName" cssClass="input_css"/></td>
									<td class="table_td4"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td2"><span class="table_title5">所属单位：</span></td>
									<td class="table_td3">
										<input type="button" id="select_dep_btn" class="btn1 select_btn" value="院系" />
										<input type="button" id="select_ins_btn" class="btn2 select_btn" value="研究基地" />
										<div id="unit" class="choose_show"><s:property value="paper.agencyName"/>&nbsp;<s:property value="paper.divisionName"/></div>
										<s:hidden id="instituteId" name="paper.institute.id"/>
										<s:hidden id="departmentId" name="paper.department.id"/>
									</td>
									<td class="table_td4"></td>
								</tr>
								<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0">
									<tr class="table_tr2">
										<td class="table_td2"><span class="table_title5">负责人：</span></td>
										<td class="table_td3" width="150">
											<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@PROVINCE)>0">
												<s:select cssClass="select" cssStyle="float:left; margin-right:10px;" id="team_authorType" name="organizationAuthorType" list="#{'1': '教师', '3': '学生'}" headerKey="-1" headerValue="--%{'请选择'}--"/>
											</s:if>
											<s:else>
												<s:select cssClass="select" cssStyle="float:left; margin-right:10px;" id="team_authorType" name="organizationAuthorType" list="#{'1': '教师', '2': '外部专家', '3': '学生'}" headerKey="-1" headerValue="--%{'请选择'}--"/>
											</s:else>
											<input type="button" class="btn2 select_btn select_author_btn" alt="team" value="选择"/>
											<div id="team_per_name_div" class="choose_show"><s:property value="author.name"/>&nbsp;&nbsp;<s:property value="paper.agencyName"/>&nbsp;<s:property value="paper.divisionName"/></div>
											<s:hidden id="team_per_id" name="organizationAuthorId"/>
											<s:hidden id="team_authorTypeId" name="organizationAuthorTypeId"/>
										</td>
										<td class="table_td4"></td>
									</tr>
								</s:if>
								<!-- 研究人员 -->
								<s:else>
									<tr class="table_tr2">
										<td class="table_td2" width="150"><span class="table_title5">所属单位和部门：</span></td>
										<td class="table_td3">
											<s:select cssClass="select" list="#session.authorTypeIdMap" name="organizationAuthorTypeId" headerKey="-1" headerValue="--%{'请选择'}--"/>
										</td>
										<td class="table_td4"></td>
									</tr>
									<s:hidden id="team_per_id" name="organizationAuthorId" value="%{#session.loginer.account.person.id}"/>
									<s:hidden name="organizationAuthorType" value="%{#session.authorType}"/>
								</s:else>
								<tr class="table_tr2">
									<td class="table_td2">其他成员：</td>
									<td class="table_td3"><s:textfield name="paper.orgMember" cssClass="input_css"/>
										<br/><span class="tip">多个以分号（即; 或；）隔开！</span></td>
									<td class="table_td4"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td2">团队学科门类：</td>
									<td class="table_td3">
										<input type="button" class="btn1 select_btn select_disciplineType_btn" alt="team" value="选择"/>
										<div id="team_disptr" class="choose_show"><s:property value="paper.orgDiscipline"/></div>
										<s:hidden name="paper.orgDiscipline" id="team_dispName"/>
									</td>
									<td class="table_td4"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td2">办公电话：</td>
									<td class="table_td3"><s:textfield name="paper.orgOfficePhone" cssClass="input_css"/></td>
									<td class="table_td4"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td2">办公地址：</td>
									<td class="table_td3"><s:textfield name="paper.orgOfficeAddress" cssClass="input_css"/></td>
									<td class="table_td4"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td2">办公邮编：</td>
									<td class="table_td3"><s:textfield name="paper.orgOfficePostcode" cssClass="input_css"/></td>
									<td class="table_td4"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td2">办公邮箱：</td>
									<td class="table_td3"><s:textfield name="paper.orgEmail" cssClass="input_css"/></td>
									<td class="table_td4"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td2">手机：</td>
									<td class="table_td3"><s:textfield name="paper.orgMobilePhone" cssClass="input_css"/></td>
									<td class="table_td4"></td>
								</tr>
							</table>
						</s:else>
						
						<table width="100%" border="0" cellspacing="2" cellpadding="2">		
							<tr class="table_tr2">
								<td class="table_td2" width="150"><span class="table_title5">论文类型：</span></td>
								<td class="table_td3">
									<s:select cssClass="select" list="%{baseService.getSystemOptionMap('productType', '01')}" name="paper.type.id" 
										headerKey="-1" headerValue="--%{'请选择'}--"/>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">形态：</span></td>
								<td class="table_td3">
									<s:select cssClass="select" id="pForm" list="%{baseService.getSystemOptionMap('productForm', null)}" name="paper.form.id" 
										headerKey="-1" headerValue="--%{'请选择'}--"/>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">是否为译文：</td>
								<td class="table_td3"><s:radio name="paper.isTranslation" list="#{'1': '是', '0': '否'}"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">是否与国（境）外合作：</td>
								<td class="table_td3"><s:radio name="paper.isForeignCooperation" list="#{'1': '是', '0': '否'}"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">字数（千）：</td>
								<td class="table_td3"><s:textfield name="paper.wordNumber" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">学科门类：</span></td>
								<td class="table_td3" id="err_dtype">
									<input type="button" class="btn1 select_btn select_disciplineType_btn" alt="person" value="选择"/>
									<div id="person_disptr" class="choose_show"><s:property value="paper.disciplineType"/></div>
									<s:hidden name="paper.disciplineType" id="person_dispName"/>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">学科代码：</span></td>
								<td class="table_td3" id="err_discipline">
									<input type="button" id="select_disciplineCode_btn" class="btn1 select_btn" value="选择"/>
									<div id="rdsp" class="choose_show"><s:property value="paper.discipline"/></div>
									<s:hidden name="paper.discipline" id="relyDisciplineName"/>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">关键词：</td>
								<td class="table_td3" id="err_keyWord"><s:textfield name="paper.keywords" cssClass="input_css"/>
									<br/><span class="tip">多个以分号（即; 或；）隔开！</span></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">简介：</td>
								<td class="table_td3"><s:textarea name="paper.introduction" rows="6" cssClass="textarea_css"/></td>
								<td class="table_td4"></td>
							</tr>
						</table>
					</div>
						
					<div class="edit_info" id="project_info">
						<!-- 项目成果 -->
						<s:if test="isProRel == 1">
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
								<tr class="table_tr2">
									<td class="table_td2" width="150">是否项目成果：</td>
									<td class="table_td3">是</td>
									<td class="table_td4"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td2">所属项目：</td>
									<td class="table_td3"><s:property value="#session.projectInfos['projectName']"/></td>
									<td class="table_td4"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td2">项目类型：</td>
									<td class="table_td3"><s:property value="#session.projectInfos['projectTypeName']"/></td>
									<td class="table_td4"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td2">是否标注教育部社科项目资助：</td>
									<td class="table_td3">
										<s:if test="#session.projectInfos['isMarkMoeSupport'] == 1">是</s:if>
										<s:else>否</s:else>
									</td>
									<td class="table_td4"></td>
								</tr>
							</table>
						</s:if>
						<!-- 非项目成果 -->
						<s:else>
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
								<tr class="table_tr2">
									<td class="table_td2" width="150"><span class="table_title5">是否项目成果：</span></td>
									<td class="table_td3"><s:radio name="isProRel" cssClass="isProRel" list="#{'1': '是', '0': '否'}"/></td>
									<td class="table_td4"></td>
								</tr>
							</table>
							<div id="pro_info" style="display:none">
								<table width="100%" border="0" cellspacing="2" cellpadding="0">
									<tr class="table_tr2">
										<td class="table_td2" width="150"><span class="table_title5">项目类型：</span></td>
										<td class="table_td3" id="err_projectType">
											<s:select cssClass="select" id="project_type" list="%{#session.projectType}" listKey="id" listValue="name" 
												headerKey="-1" headerValue="--%{'请选择'}--"/>
										</td>
										<td class="table_td4"></td>
									</tr>
									<tr class="table_tr2">
										<td class="table_td2"><span class="table_title5">项目获取方式：</span></td>
										<td class="table_td3" id="err_project">
											<s:radio cssClass="isSelPro" name="isSelPro"  list="#{'1': '根据第一作者选择', '0': '输入项目批准号获取'}" value="1"/>
										</td>
										<td class="table_td4"></td>
									</tr>
									<tr class="table_tr2">
										<td class="table_td2"><span class="table_title5">所属项目：</span></td>
										<td class="table_td3" name="err_project" id="gainBySelect">
											<input type="button" class="btn1 select_btn" id="select_project_btn" value="选择"/>
											<div id="proj_name_div"></div>
										</td>
										<td class="table_td3" id="gainByNumber" style="display:none"><span><s:textfield  id="proNum" name="proNum" value="%{#request.proNum}"/></span>
											<span><input type="button" class="btn2" id="get_project_btn" value="获取项目" /></span>
											<span id="proj_name_span"></span>
										</td>
										<td style="display:none"><s:hidden name="projectGrantedId" id="proj_name"/></td>
										<td class="table_td4"></td>
									</tr>
									<tr class="table_tr2">
										<td class="table_td2"><span class="table_title5">是否标注教育部社科项目资助：</span></td>
										<td class="table_td3"><s:radio name="projectProduct.isMarkMoeSupport" list="#{'1': '是', '0': '否'}" /></td>
										<td class="table_td4"></td>
									</tr>
								</table>
							</div>
						</s:else>
					</div>
						
					<div class="edit_info" id="publication_info">
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">刊物级别：</span></td>
								<td class="table_td3">
									<s:select cssClass="select" id="publication_level" list="%{baseService.getSystemOptionMap('publicationLevel', null)}" name="paper.publicationLevel.id"
										headerKey="-1" headerValue="--%{'请选择'}--" />
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2" width="100"><span class="table_title5">发表刊物：</span></td>
								<td class="table_td3">
									<span><s:select cssClass="select" id="selectOrNot" list="#{'1' :'选择', '0' :'自定义'}" headerKey="-1" headerValue="--%{'请选择'}--"/></span>
									<span id="select_yes" style="display:none;"><input type="button" id="select_publication_btn" class="btn1" value="选择"/>&nbsp;<span id="publication_name" ></span></span>
									<span id="select_no"><s:textfield id="publication" name="paper.publication"/></span></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">发表时间：</span></td>
								<td class="table_td3">
									<s:textfield id="publicationDate" name="paper.publicationDate" cssClass="input_css">
										<s:param name="value">
											<s:date name="%{paper.publicationDate}" format="yyyy-MM-dd"/>
										</s:param>
									</s:textfield>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">发表范围：</span></td>
								<td class="table_td3">
									<s:select cssClass="select" list="%{baseService.getSystemOptionMap('publicationScope', null)}" name="paper.publicationScope.id"
										headerKey="-1" headerValue="--%{'请选择'}--"/>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">被引情况：</td>
								<td class="table_td3">
									<input type="button" id="select_indexType_btn" class="btn1 select_btn" value="选择"/>
									<div id="indexType" class="choose_show"><s:property value="%{paper.index}"/></div>
									<s:hidden name="paper.index" />
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">卷号：</td>
								<td class="table_td3"><s:textfield name="paper.volume" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">期号：</td>
								<td class="table_td3"><s:textfield name="paper.number" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">起止页码：</td>
								<td class="table_td3"><s:textfield name="paper.page" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">ISSN号：</td>
								<td class="table_td3"><s:textfield name="paper.issn" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">CN号：</td>
								<td class="table_td3"><s:textfield name="paper.cn" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
						</table>
					</div>
				</div>
				
				<div id="optr" class="btn_bar2">
					<input id="prev" type="button" class="btn2" style="display: none" value="上一步"/>
					<input id="next" type="button" class="btn2" style="display: none" value="下一步" />
				<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@INSTITUTE)>0">
					<input id="save_product" type="button" class="btn1" style="display: none" value="保存"/>
					<input id="submit_product" type="button" class="btn1" style="display: none" value="提交"/>
				</s:if>
				<s:else>
					<input id="finish" type="button" class="btn1" style="display: none" value="完成"/>
				</s:else>
					<input id="cancel" type="button" class="btn1" style="display: none" value="取消"/>
				</div>
			</s:form>
		</div>
   		<script type="text/javascript">
			seajs.use('javascript/product/paper/edit.js', function(modify) {
				$(function() {
					modify.init();
				});
			});
		</script>
	</body>
</html>