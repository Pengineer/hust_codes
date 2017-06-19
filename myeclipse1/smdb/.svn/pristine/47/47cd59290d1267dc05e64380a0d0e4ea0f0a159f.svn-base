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
		<s:form id="form_product" action="modify"  namespace="/product/book">
			<s:hidden name="entityId" id="entityId"/>
			<s:hidden name="viewType" id="viewType"/>
			<s:hidden name="inspectionId"/>
			<s:hidden name="book.submitStatus"/>
			
			<div class="p_box_b">
				<div class="p_box_b_2" style="float:right;padding-right:30px;"><img src="image/ico09.gif" />
					<a href="javascript:void(0);" class="downlaod_product" name="<s:property value='book.file'/>" id="<s:property value='book.id'/>" type="book">下载成果文件</a>
				</div>
			</div>
			
			<div style="overflow-y:scroll;height:400px;width:750px;">
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr class="table_tr2">
						<td class="table_td2" width="130">成果文件：</span></td>
						
						<td class="table_td3">
							<input type="file" id="file_${book.id}" />
							<s:hidden id = "bookId" name = "book.id"></s:hidden>
						</td>						
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">书籍名称：</span></td>
						<td class="table_td3"><s:textfield name="book.chineseName" cssClass="input_css"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">英文名称：</td>
						<td class="table_td3"><s:textfield name="book.englishName" cssClass="input_css"/></td>
						<td class="table_td4"></td>
					</tr>
					<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)">
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title5">所属单位和部门：</span></td>
							<td class="table_td3">
								<s:hidden id="per_id" name="authorId" value="%{#session.loginer.account.person.id}"/>
								<s:select cssClass="select" list="#session.authorTypeIdMap" name="authorTypeId" headerKey="-1" headerValue="--%{'请选择'}--"/>
							</td>
							<td class="table_td4"></td>
						</tr>
					</s:if>
					<tr class="table_tr2">
						<td class="table_td2" width="130">其他作者：</td>
						<td class="table_td3"><s:textfield name="book.otherAuthorName" cssClass="input_css"/>
							<br/><span class="tip">多个以分号（即; 或；）隔开！</span></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">书籍类型：</span></td>
						<td class="table_td3">
							<s:select cssClass="select" name="book.type.id" list="%{baseService.getSystemOptionMap('productType', '02')}" 
								headerKey="-1"	headerValue="--%{'请选择'}--"/>
							<span id="originalLanguage" style="padding-left:10px;display:<s:if test="book.type.id == 'translationWork' || book.type.id == 'translation'"></s:if><s:else>none;</s:else>"><span>原著语言：</span>
		 						<s:select cssClass="select" name="book.originalLanguage" list="%{baseService.getSystemOptionMapAsName('ISO 639-1', null)}"
			 						headerKey="-1" headerValue="--%{'请选择'}--"/>
 							</span>
						</td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">形态：</span></td>
						<td class="table_td3">
							<s:select cssClass="select" id="pForm" list="%{baseService.getSystemOptionMap('productForm', null)}" name="book.form.id" 
								headerKey="-1" headerValue="--%{'请选择'}--"/>
						</td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">是否为译文：</td>
						<td class="table_td3"><s:radio name="book.isTranslation" list="#{'1':'是','0':'否'}"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">是否与国（境）外合作：</td>
						<td class="table_td3"><s:radio name="book.isForeignCooperation" list="#{'1':'是','0':'否'}"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">是否已提交待出版样书：</td>
						<td class="table_td3"><s:radio name="book.publicationStatus" list="#{'1':'是','0':'否'}"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">字数（千）：</td>
						<td class="table_td3"><s:textfield name="book.wordNumber" cssClass="input_css"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">学科门类：</span></td>
						<td class="table_td3">
							<input type="button" id="select_disciplineType_btn" class="btn1 select_btn" value="选择"/>
							<div id="disptr" class="choose_show"><s:property value="book.disciplineType"/></div>
							<s:hidden name="book.disciplineType" id="dispName"/>
						</td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">学科代码：</span></td>
						<td class="table_td3">
							<input type="button" id="select_disciplineCode_btn" class="btn1 select_btn" value="选择"/>
							<div id="rdsp" class="choose_show"><s:property value="book.discipline"/></div>
							<s:hidden name="book.discipline" id="relyDisciplineName"/>
						</td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">关键词：</td>
						<td class="table_td3"><s:textfield name="book.keywords" cssClass="input_css"/>
							<br/><span class="tip">多个以分号（即; 或；）隔开！</span></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">简介：</td>
						<td class="table_td3"><s:textarea name="book.introduction" rows="6" cssClass="textarea_css"/></td>
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
						<td class="table_td2" width="130">出版单位：</td>
						<td class="table_td3"><s:textfield name="book.publishUnit" cssClass="input_css"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">出版时间：</td>
						<td class="table_td3"><s:textfield id="publicationDate" name="book.publishDate" cssClass="input_css" readonly="true"/></td>
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
				modify.init();
			});
		</script>
	</body>
</html>