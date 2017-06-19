<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
   		<title>修改</title>
   		<s:include value="/innerBase.jsp" />
   		<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css" />
		</head>
	
	<body>
		<s:form id="form_product" action="modify" namespace="/product/paper">
			<s:hidden name="entityId" id="entityId"/>
			<s:hidden name="projectId" id="projectId"/>
			<s:hidden name="viewType" id="viewType"/>
			<s:hidden name="inspectionId"/>
			<s:hidden name="paper.submitStatus"/>
			
			<div class="p_box_b">
				<div class="p_box_b_2" style="float:right;padding-right:30px;"><img src="image/ico09.gif" />
					<a href="javascript:void(0);" class="downlaod_product" name="<s:property value='paper.file'/>" id="<s:property value='paper.id'/>" type="paper">下载成果文件</a>
				</div>
			</div>
			
			<div style="overflow-y:scroll;height:400px;width:750px;">
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr class="table_tr2">
						<td class="table_td2" width="130">成果文件：</span></td>
						<td class="table_td3">
							<input type="file" id="file_${paper.id}" />
							<s:hidden id = "paperId" name = "paper.id"></s:hidden>
						</td>
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
					<tr class="table_tr2">
						<td class="table_td2" width="130">其他作者：</td>
						<td class="table_td3"><s:textfield name="paper.otherAuthorName" cssClass="input_css"/>
							<br/><span class="tip">多个以分号（即; 或；）隔开！</span></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">论文类型：</span></td>
						<td class="table_td3">
							<s:select cssClass="select" list="%{baseService.getSystemOptionMap('productType', '01')}" name="paper.type.id" 
								headerKey="-1" headerValue="--%{'请选择'}--"/>&nbsp;
							<s:fielderror cssStyle="color:red"><s:param>paper.Type.id</s:param></s:fielderror></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">形态：</span></td>
						<td class="table_td3">
							<s:select cssClass="select" id="pForm" list="%{baseService.getSystemOptionMap('productForm', null)}" name="paper.form.id" 
								headerKey="-1" headerValue="--%{'请选择'}--"/>
							&nbsp;<s:fielderror cssStyle="color:red"><s:param>paper.form.id</s:param></s:fielderror></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">是否为译文：</td>
						<td class="table_td3"><s:radio name="paper.isTranslation" list="#{'1':'是','0':'否'}"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">是否与国（境）外合作：</td>
						<td class="table_td3"><s:radio name="paper.isForeignCooperation" list="#{'1':'是','0':'否'}"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">字数（千）：</td>
						<td class="table_td3"><s:textfield name="paper.wordNumber" cssClass="input_css"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">学科门类：</span></td>
						<td class="table_td3">
							<input type="button" id="select_disciplineType_btn" class="btn1 select_btn" value="选择"/>
							<div id="disptr" class="choose_show"><s:property value="paper.disciplineType"/></div>
							<s:hidden name="paper.disciplineType" id="dispName"/>
						</td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">学科代码：</span></td>
						<td class="table_td3">
							<input type="button" id="select_disciplineCode_btn" class="btn1 select_btn" value="选择"/>
							<div id="rdsp" class="choose_show"><s:property value="paper.discipline"/></div>
							<s:hidden name="paper.discipline" id="relyDisciplineName"/>
						</td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">关键词：</td>
						<td class="table_td3"><s:textfield name="paper.keywords" cssClass="input_css"/>
							<br/><span class="tip">多个以分号（即; 或；）隔开！</span></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">简介：</td>
						<td class="table_td3"><s:textarea name="paper.introduction" rows="6" cssClass="textarea_css"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">是否标注教育部社科项目资助：</span></td>
						<td class="table_td3"><s:radio name="projectProduct.isMarkMoeSupport" list="#{'1':'是','0':'否'}"/></td>
						<td class="table_td4"></td>
					</tr>
					<!-- 结项成果增加是否最终成果 -->
					<s:if test="viewType==2">
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title5">是否最终成果：</span></td>
							<td class="table_td3"><s:radio name="isFinalProduct" list="#{'1':'是','0':'否'}"/></td>
							<td class="table_td4"></td>
						</tr>
					</s:if>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">刊物级别：</span></td>
						<td class="table_td3">
							<s:select cssClass="select" id="publication_level" list="%{baseService.getSystemOptionMap('publicationLevel', null)}" name="paper.publicationLevel.id"
								headerKey="-1" headerValue="--%{'请选择'}--" />
						</td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">发表刊物：</span></td>
						<td class="table_td3">
							<span><s:select cssClass="select" id="selectOrNot" list="#{'1':'选择','0':'自定义'}" headerKey="-1" headerValue="--%{'请选择'}--" value="0"/></span>
							<span id="select_yes" style="display:none;"><input type="button" id="select_publication_btn" class="btn1" value="选择"/>&nbsp;<span id="publication_name" ><s:property value="paper.publication"/></span></span>
							<span id="select_no"><s:textfield id="publication" name="paper.publication"/></span></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">发表时间：</span></td>
						<td class="table_td3">
							<s:textfield id="publicationDate" name="paper.publicationDate" cssClass="input_css" readonly="true">
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
								headerKey="-1" headerValue="--%{'请选择'}--" />
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
						<td class="table_td2">序号：</td>
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
			<div class="btn_div_view">
				<input id="submit" type="button" class="btn1" value="完成"/>
				<input id="cancel" type="button" class="btn1" value="取消"/>
			</div>
		</s:form>
   		<script type="text/javascript">
			seajs.use('javascript/product/extIf/product.js', function(modify) {
				$(function(){
					modify.init();
				})
			});
		</script>
	</body>
<%--		<script type="text/javascript" src="javascript/file_upload_setting.js?ver=<%=application.getAttribute("systemVersion")%>"></script>--%>
<%--		<script type="text/javascript">window.onload = initSwf();</script>--%>
</html>