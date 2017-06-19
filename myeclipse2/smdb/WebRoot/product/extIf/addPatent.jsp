<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<s:form id="form_product" action="add" namespace="/product/patent">
	<s:hidden name="exflag" value="1"/>
	<s:hidden id="productType" value="5"/>
	<s:hidden id="proj_name" name="projectId"/>
	<s:hidden name="viewType" id="viewType"/>
	<s:hidden name="inspectionId"/>
	
	<div style="overflow-y:scroll;height:400px;">
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr class="table_tr2">
				<td class="table_td2" width="130">成果文件：</span></td>
				
				<td class="table_td3">
					<input type="file" id="file_add_pat" />
				</td>
			<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">专利名称：</span></td>
				<td class="table_td3"><s:textfield name="patent.chineseName" cssClass="input_css"/></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">英文名称：</td>
				<td class="table_td3"><s:textfield name="patent.englishName" cssClass="input_css"/></td>
				<td class="table_td4"></td>
			</tr>
			<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)">
				<tr class="table_tr2">
					<td class="table_td2"><span class="table_title5">所属单位和部门：</span></td>
					<td class="table_td3">
						<s:hidden id="per_id" name="authorId" value="%{#session.loginer.account.person.id}"/>
						<s:select cssClass="select" list="#session.authorTypeIdMap" name="authorTypeId" headerKey="-1" headerValue="--%{getText('请选择')}--"/>
					</td>
					<td class="table_td4"></td>
				</tr>
			</s:if>
			<tr class="table_tr2">
				<td class="table_td2" width="130">其他作者：</td>
				<td class="table_td3"><s:textfield name="patent.otherAuthorName" cssClass="input_css"/>
					<br/><span class="tip">多个以分号（即; 或；）隔开！</span></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">形态：</span></td>
				<td class="table_td3">
					<s:select cssClass="select" id="pForm" list="%{baseService.getSystemOptionMap('productForm', null)}" name="patent.form.id" 
						headerKey="-1" headerValue="--%{getText('请选择')}--"/>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">范畴分类：</td>
				<td class="table_td3"><s:textfield name="patent.categotyType" cssClass="input_css"/></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">是否与国（境）外合作：</td>
				<td class="table_td3"><s:radio name="patent.isForeignCooperation" list="#{'1':getText('是'),'0':getText('否')}" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">学科门类：</span></td>
				<td class="table_td3">
					<input type="button" id="select_disciplineType_btn" class="btn1 select_btn" value="选择"/>
					<div id="disptr" class="choose_show"><s:property value="patent.disciplineType"/></div>
					<s:hidden name="patent.disciplineType" id="dispName"/>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">学科代码：</span></td>
				<td class="table_td3">
					<input type="button" id="select_disciplineCode_btn" class="btn1 select_btn" value="选择"/>
					<div id="rdsp" class="choose_show"><s:property value="patent.discipline"/></div>
					<s:hidden name="patent.discipline" id="relyDisciplineName"/>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2" >国省代码：</td>
				<td class="table_td3"><s:textfield name="patent.countriesProvinceCode" cssClass="input_css"/></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">关键词：</td>
				<td class="table_td3"><s:textfield name="patent.keywords" cssClass="input_css"/>
						<br/><span class="tip">多个以分号（即; 或；）隔开！</span></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">独立权利要求：</td>
				<td class="table_td3"><s:textarea name="patent.independentClaims" rows="6" cssClass="textarea_css"/></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">简介：</td>
				<td class="table_td3"><s:textarea name="patent.introduction" rows="6" cssClass="textarea_css"/></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">摘要：</td>
				<td class="table_td3"><s:textarea name="patent.summary" rows="6" cssClass="textarea_css"/></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">是否标注教育部社科项目资助：</span></td>
				<td class="table_td3"><s:radio name="projectProduct.isMarkMoeSupport" list="#{'1':getText('是'),'0':getText('否')}"/></td>
				<td class="table_td4"></td>
			</tr>
			<!-- 结项成果增加是否最终成果 -->
			<s:if test="viewType==2">
				<tr class="table_tr2">
					<td class="table_td2"><span class="table_title5">是否最终成果：</span></td>
					<td class="table_td3"><s:radio name="isFinalProduct" list="#{'1':getText('是'),'0':getText('否')}"/></td>
					<td class="table_td4"></td>
				</tr>
			</s:if>
			<tr class="table_tr2">
				<td class="table_td2" width="130">发明人姓名：</td>
				<td class="table_td3"><s:textfield name="patent.inventorName" cssClass="input_css"/></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2" width="100">申请号：</td>
				<td class="table_td3"><s:textfield name="patent.applicationNumber" cssClass="input_css"/></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">申请时间：</td>
					<td class="table_td3">
						<s:textfield id="applicationDate" name="patent.applicationDate" cssClass="input_css" readonly="true">
							<s:param name="value">
								<s:date name="%{patent.applicationDate}" format="yyyy-MM-dd"/>
							</s:param>
						</s:textfield>
					</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2" width="100">公开号：</td>
				<td class="table_td3"><s:textfield name="patent.publicNumber" cssClass="input_css"/></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">公开日：</td>
					<td class="table_td3">
						<s:textfield id="publicationDate" name="patent.publicDate" cssClass="input_css" readonly="true">
							<s:param name="value">
								<s:date name="%{patent.publicDate}" format="yyyy-MM-dd"/>
							</s:param>
						</s:textfield>
					</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2" width="100">分类号：</td>
				<td class="table_td3"><s:textfield name="patent.classNumber" cssClass="input_css"/></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2" width="100">优先权号：</td>
				<td class="table_td3"><s:textfield name="patent.priorityNumber" cssClass="input_css"/></td>
				<td class="table_td4"></td>
			</tr>
			
		</table>
	</div>
	<div class="btn_div_view">
		<input id="submit" type="button" class="btn1" value="完成"/>
		<input id="cancel" type="button" class="btn1" value="取消"/>
	</div>
</s:form>


<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
<script type="text/javascript" src="tool/uploadify/js/jquery.uploadify.js"></script>  
<script type="text/javascript" src="tool/uploadify/js/jquery.uploadify-ext.js"></script>
<script type="text/javascript">
	$(function() {
		$("#file_add_pat").uploadifyExt({
			uploadLimitExt : 1,
			fileSizeLimit : '300MB',
			fileTypeExts:'*.doc; *.docx; *.pdf',
			fileTypeDesc : '附件'
		});
	});
</script> 