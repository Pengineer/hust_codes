<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>

<s:form id="form_product" action="add" namespace="/product/consultation">
	<s:hidden name="exflag" value="1"/>
	<s:hidden id="productType" value="3"/>
	<s:hidden id="proj_name" name="projectId"/>
	<s:hidden name="viewType" id="viewType"/>
	<s:hidden name="inspectionId"/>
	
	<div style="overflow-y:scroll;height:400px;">
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr class="table_tr2">
				<td class="table_td2" width="130">成果文件：</span></td>
				
				<td class="table_td3">
					<input type="file" id="file_add_con" />
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">报告名称：</span></td>
				<td class="table_td3"><s:textfield name="consultation.chineseName" cssClass="input_css"/></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">英文名称：</td>
				<td class="table_td3"><s:textfield name="consultation.englishName" cssClass="input_css"/></td>
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
				<td class="table_td3"><s:textfield name="consultation.otherAuthorName" cssClass="input_css"/>
					<br/><span class="tip">多个以分号（即; 或；）隔开！</span></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">形态：</span></td>
				<td class="table_td3">
					<s:select cssClass="select" id="pForm" list="%{baseService.getSystemOptionMap('productForm', null)}" name="consultation.form.id" 
						headerKey="-1" headerValue="--%{getText('请选择')}--"/>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">是否为译文：</td>
				<td class="table_td3"><s:radio name="consultation.isTranslation" list="#{'1':getText('是'),'0':getText('否')}" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">是否与国（境）外合作：</td>
				<td class="table_td3"><s:radio name="consultation.isForeignCooperation" list="#{'1':getText('是'),'0':getText('否')}" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">字数（千）：</td>
				<td class="table_td3"><s:textfield name="consultation.wordNumber" cssClass="input_css"/></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">学科门类：</span></td>
				<td class="table_td3">
					<input type="button" id="select_disciplineType_btn" class="btn1 select_btn" value="选择"/>
					<div id="disptr" class="choose_show"><s:property value="consultation.disciplineType"/></div>
					<s:hidden name="consultation.disciplineType" id="dispName"/>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">学科代码：</span></td>
				<td class="table_td3">
					<input type="button" id="select_disciplineCode_btn" class="btn1 select_btn" value="选择"/>
					<div id="rdsp" class="choose_show"><s:property value="consultation.discipline"/></div>
					<s:hidden name="consultation.discipline" id="relyDisciplineName"/>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">关键词：</td>
				<td class="table_td3"><s:textfield name="consultation.keywords" cssClass="input_css"/>
						<br/><span class="tip">多个以分号（即; 或；）隔开！</span></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">简介：</td>
				<td class="table_td3"><s:textarea name="consultation.introduction" rows="6" cssClass="textarea_css"/></td>
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
				<td class="table_td2" width="130">采纳单位：</td>
				<td class="table_td3"><s:textfield name="consultation.useUnit" cssClass="input_css"/></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">发表时间：</td>
				<td class="table_td3"><s:textfield id="publicationDate" name="consultation.publicationDate" cssClass="input_css" readonly="true"/></td>
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
		$("#file_add_con").uploadifyExt({
			uploadLimitExt : 1,
			fileSizeLimit : '300MB',
			fileTypeExts:'*.doc; *.docx; *.pdf',
			fileTypeDesc : '附件'
		});
	});
</script> 