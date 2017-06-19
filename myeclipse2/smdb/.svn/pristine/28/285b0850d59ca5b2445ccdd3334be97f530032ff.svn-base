<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
   		<title>添加</title>
   		<s:include value="/innerBase.jsp" />
   		<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css" />
		</head>
 		
	<body>
		<div class="link_bar">
			当前位置：社科成果数据&nbsp;&gt;&nbsp;其他成果&nbsp;&gt;&nbsp;添加
		</div>
		
		<div class="main">
			<s:form id="form_product" action="add" namespace="/product/otherProduct">
				<s:hidden id="accountType" value="%{#session.loginer.currentType}"/>
				<s:hidden id="addOrModify" value="1"/>
				<s:hidden id="productType" value="6"/>
				<s:hidden id="submitStatus" name="otherProduct.submitStatus"/>
				<s:hidden name="productflag"/>
				<div class="main_content">
					<div class="step_css">
						<ul id="add_tabs">
							<li class="proc step_e" name="base_info"><span class="left_step"></span><span class="right_step">基本信息</span></li>
							<li class="proc" name="project_info"><span class="left_step"></span><span class="right_step">项目信息</span></li>
							<li class="proc" name="publication_info"><span class="left_step"></span><span class="right_step">出版信息</span></li>
							<li class="proc step_oo" name="finish"><span class="left_step"></span><span class="right_step">完成</span></li>
						</ul>
					</div>
				</div>
				
				<div class="main_content">
					<s:include value="/validateError.jsp"/>
					
					<div class="edit_info" id="base_info">
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td2" width="150">成果文件：</span></td>
								<td class="table_td3">
									<input type="file" id="file_add" />
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">成果名称：</span></td>
								<td class="table_td3" id="err_chineseName"><s:textfield name="otherProduct.chineseName" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">英文名称：</td>
								<td class="table_td3"><s:textfield name="otherProduct.englishName" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">申报形式：</span></td>
								<td class="table_td3"><s:radio cssClass="applyType" name="applyType" list="#{'0': getText('个人名义'), '1': getText('团队名义')}" value="0"/></td>
								<td class="table_td4"></td>
							</tr>
						</table>
						
						<!-- 个人成果 -->
						<table width="100%" border="0" cellspacing="2" cellpadding="2" id="personal_info" >
							<!-- 管理人员 -->
							<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0">
								<tr class="table_tr2">
									<td class="table_td2" width="150"><span class="table_title5">第一作者：</span></td>
									<td class="table_td3" id="err_firstAuthor">
										<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@PROVINCE)>0">
											<s:select cssClass="select" cssStyle="float:left; margin-right:10px;" id="person_authorType" name="authorType" list="#{'1': getText('教师'), '3': getText('学生')}" headerKey="-1" headerValue="--%{getText('请选择')}--"/>
										</s:if>
										<s:else>
											<s:select cssClass="select" cssStyle="float:left; margin-right:10px;" id="person_authorType" name="authorType" list="#{'1': getText('教师'), '2': getText('外部专家'), '3': getText('学生')}" headerKey="-1" headerValue="--%{getText('请选择')}--"/>
										</s:else>
										<input type="button" class="btn2 select_btn select_author_btn" value="选择" alt="person"/>
										<div id="person_per_name_div" class="choose_show"></div>
										<s:hidden id="person_per_id" name="authorId" />
										<s:hidden id="person_authorTypeId" name="authorTypeId"/>
									</td>
									<td class="table_td4"></td>
								</tr>
							</s:if>
							<!-- 研究人员 -->
							<s:else>
								<tr class="table_tr2">
									<td class="table_td2" width="150"><span class="table_title5">所属单位和部门：</span></td>
									<td class="table_td3">
										<s:select cssClass="select" list="#session.authorTypeIdMap" name="authorTypeId" headerKey="-1" headerValue="--%{getText('请选择')}--"/>
									</td>
									<td class="table_td4"></td>
								</tr>
								<s:hidden id="person_per_id" name="authorId" value="%{#session.loginer.account.person.id}"/>
								<s:hidden name="authorType" value="%{#session.authorType}"/>
							</s:else>
							<tr class="table_tr2">
								<td class="table_td2">其他作者：</td>
								<td class="table_td3">
									<s:textfield name="otherProduct.otherAuthorName" cssClass="input_css"/>
									<br/><span class="tip">多个以分号（即; 或；）隔开！</span></td>
								<td class="table_td4"></td>
							</tr>
						</table>
							
						<!-- 团队成果 -->
						<table width="100%" border="0" cellspacing="2" cellpadding="2" id="team_info" style="display:none;">
							<!-- 管理人员 -->
							<!-- 团队名称 -->
							<tr class="table_tr2">
								<td class="table_td2" width="150"><span class="table_title5">团队名称：</span></td>
								<td class="table_td3"><s:textfield name="otherProduct.orgName" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">所属单位：</span></td>
								<td class="table_td3">
									<input type="button" id="select_dep_btn" class="btn1 select_btn" value="院系" />
									<input type="button" id="select_ins_btn" class="btn2 select_btn" value="研究基地" />
									<div id="unit" class="choose_show"></div>
									<s:hidden id="instituteId" name="otherProduct.institute.id"/>
									<s:hidden id="departmentId" name="otherProduct.department.id"/>
									<s:hidden id="unitName" name="otherProduct.agencyName"/>
								</td>
								<td class="table_td4"></td>
							</tr>
							<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0">
								<tr class="table_tr2">
									<td class="table_td2" width="150"><span class="table_title5">负责人：</span></td>
									<td class="table_td3">
										<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@PROVINCE)>0">
											<s:select cssClass="select" cssStyle="float:left; margin-right:10px;" id="team_authorType" name="organizationAuthorType" list="#{'1': getText('教师'), '3': getText('学生')}" headerKey="-1" headerValue="--%{getText('请选择')}--"/>
										</s:if>
										<s:else>
											<s:select cssClass="select" cssStyle="float:left; margin-right:10px;" id="team_authorType" name="organizationAuthorType" list="#{'1': getText('教师'), '2': getText('外部专家'), '3': getText('学生')}" headerKey="-1" headerValue="--%{getText('请选择')}--"/>
										</s:else>
										<input type="button" class="btn2 select_btn select_author_btn" alt="team" value="选择"/>
										<div id="team_per_name_div" class="choose_show"></div>
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
										<s:select cssClass="select" list="#session.authorTypeIdMap" name="organizationAuthorTypeId" headerKey="-1" headerValue="--%{getText('请选择')}--"/>
									</td>
									<td class="table_td4"></td>
								</tr>
								<s:hidden id="team_per_id" name="organizationAuthorId" value="%{#session.loginer.account.person.id}"/>
								<s:hidden name="organizationAuthorType" value="%{#session.authorType}"/>
							</s:else>
							<tr class="table_tr2">
								<td class="table_td2">其他成员：</td>
								<td class="table_td3"><s:textfield name="otherProduct.orgMember" cssClass="input_css"/>
									<br/><span class="tip">多个以分号（即; 或；）隔开！</span></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">团队学科门类：</td>
								<td class="table_td3">
									<input type="button" class="btn1 select_btn select_disciplineType_btn" alt="team" value="选择"/>
									<div id="team_disptr" class="choose_show"></div>
									<s:hidden name="otherProduct.orgDiscipline" id="team_dispName"/>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">办公电话：</td>
								<td class="table_td3"><s:textfield name="otherProduct.orgOfficePhone" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">办公地址：</td>
								<td class="table_td3"><s:textfield name="otherProduct.orgOfficeAddress" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">办公邮编：</td>
								<td class="table_td3"><s:textfield name="otherProduct.orgOfficePostcode" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">办公邮箱：</td>
								<td class="table_td3"><s:textfield name="otherProduct.orgEmail" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">手机：</td>
								<td class="table_td3"><s:textfield name="otherProduct.orgMobilePhone" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
						</table>
						
						<table width="100%" border="0" cellspacing="2" cellpadding="2">		
							<tr class="table_tr2">
								<td class="table_td2" width="150"><span class="table_title5">形态：</span></td>
								<td class="table_td3">
									<s:select cssClass="select" id="pForm" list="%{baseService.getSystemOptionMap('productForm', null)}" name="otherProduct.form.id" 
										headerKey="-1" headerValue="--%{getText('请选择')}--"/>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">成果子类型：</td>
								<td class="table_td3" ><s:textfield name="otherProduct.subtype" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">是否与国（境）外合作：</td>
								<td class="table_td3"><s:radio name="otherProduct.isForeignCooperation" list="#{'1':getText('是'),'0':getText('否')}"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">学科门类：</span></td>
								<td class="table_td3" id="err_dtype">
									<input type="button" class="btn1 select_btn select_disciplineType_btn" value="选择" alt="product"/>
									<div id="product_disptr" class="choose_show"></div>
									<s:hidden name="otherProduct.disciplineType" id="product_dispName"/>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">学科代码：</span></td>
								<td class="table_td3" id="err_discipline">
									<input type="button" id="select_disciplineCode_btn" class="btn1 select_btn" value="选择"/>
									<div id="rdsp" class="choose_show"></div>
									<s:hidden name="otherProduct.orgDiscipline" id="relyDisciplineName"/>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">关键词：</td>
								<td class="table_td3" id="err_keyWord"><s:textfield name="otherProduct.keywords" cssClass="input_css"/>
										<br/><span class="tip">多个以分号（即; 或；）隔开！</span></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">简介：</td>
								<td class="table_td3"><s:textarea name="otherProduct.introduction" rows="6" cssClass="textarea_css"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">评价：</td>
								<td class="table_td3"><s:textarea name="otherProduct.evaluation" rows="6" cssClass="textarea_css"/></td>
								<td class="table_td4"></td>
							</tr>
						</table>
					</div>
						
					<div class="edit_info" id="project_info"> 
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td2" width="150"><span class="table_title5">是否项目成果：</span></td>
								<td class="table_td3"><s:radio name="isProRel" cssClass="isProRel" list="#{'1': getText('是'), '0': getText('否')}"/></td>
								<td class="table_td4"></td>
							</tr>
						</table>
						<div id="pro_info" style="display:none">
							<table width="100%" border="0" cellspacing="2" cellpadding="0">
								<s:hidden name="projectType" id="proj_type_id" />
								<tr class="table_tr2">
									<td class="table_td2" width="150"><span class="table_title5">项目类型：</span></td>
									<td class="table_td3" id="err_projectType">
										<s:property value="@ProjectGranted.typeMap"/>
										<s:select cssClass="select" id="project_type" list="%{#session.projectType}"
											headerKey="-1" headerValue="--%{getText('请选择')}--"/>
									</td>
									<td class="table_td4"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td2"><span class="table_title5">项目获取方式：</span></td>
									<td class="table_td3" id="err_project">
										<s:radio cssClass="isSelPro" name="isSelPro"  list="#{'1': getText('根据第一作者选择'), '0': getText('输入项目批准号获取')}" value="1"/>
									</td>
									<td class="table_td4"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td2"><span class="table_title5">所属项目：</span></td>
									<td class="table_td3" name="err_project" id="gainBySelect">
										<input type="button" class="btn1 select_btn" id="select_project_btn" value="选择"/>
										<div id="proj_name_div"></div>
									</td>
									<td class="table_td3" id="gainByNumber" style="display:none"><span ><s:textfield  id="proNum" name="proNum" /></span>
										<span><input type="button" class="btn2" id="get_project_btn" value="获取项目" /></span>
										<span id="proj_name_span"></span>
									</td>
									<td style="display:none"><s:hidden name="projectGrantedId" id="proj_name"/></td>
									<td class="table_td4"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td2"><span class="table_title5">是否标注教育部社科项目资助：</span></td>
									<td class="table_td3"><s:radio name="projectProduct.isMarkMoeSupport" list="#{'1': getText('是'), '0': getText('否')}"/></td>
									<td class="table_td4"></td>
								</tr>
							</table>
						</div>
					</div>
					
					<div class="edit_info" id="publication_info">
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td2" width="100">出版单位：</td>
								<td class="table_td3"><s:textfield name="otherProduct.publishUnit" cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">出版时间：</td>
								<td class="table_td3"><s:textfield id="publicationDate" name="otherProduct.pressDate" cssClass="input_css" readonly="true"/></td>
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
			seajs.use('javascript/product/otherProduct/edit.js', function(add) {
				$(function() {
					add.init();
				});
			});
		</script>
	</body>
</html>