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
		<s:form id="form_product" action="modify" namespace="/product/otherProduct">
			<s:hidden name="entityId" id="entityId"/>
			<s:hidden name="viewType" id="viewType"/>
			<s:hidden name="inspectionId"/>
			<s:hidden name="otherProduct.submitStatus"/>
			
			<div class="p_box_b">
				<div class="p_box_b_2" style="float:right;padding-right:30px;"><img src="image/ico09.gif" />
					<a href="javascript:void(0);" class="downlaod_product" name="<s:property value='otherProduct.file'/>" id="<s:property value='otherProduct.id'/>" type="otherProduct">下载成果文件</a>
				</div>
			</div>
			
			<div style="overflow-y:scroll;height:400px;width:750px;">
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr class="table_tr2">
						<td class="table_td2" width="130">成果文件：</span></td>
						<td class="table_td3">
							<input type="file" id="file_${otherProduct.id}" />
							<s:hidden id = "otherProductId" name = "otherProduct.id"></s:hidden>
						</td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">成果名称：</span></td>
						<td class="table_td3"><s:textfield name="otherProduct.chineseName" cssClass="input_css"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">英文名称：</td>
						<td class="table_td3"><s:textfield name="otherProduct.englishName" cssClass="input_css"/></td>
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
						<td class="table_td3"><s:textfield name="otherProduct.otherAuthorName" cssClass="input_css"/>
							<br/><span class="tip">多个以分号（即; 或；）隔开！</span></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">形态：</span></td>
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
						<td class="table_td2">支持课题：</td>
						<td class="table_td3" ><s:textfield name="otherProduct.supportProject" cssClass="input_css"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">评价：</td>
						<td class="table_td3" ><s:textfield name="otherProduct.Evaluation" cssClass="input_css"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">是否与国（境）外合作：</td>
						<td class="table_td3"><s:radio name="otherProduct.isForeignCooperation" list="#{'1':getText('是'),'0':getText('否')}" /></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">学科门类：</span></td>
						<td class="table_td3">
							<input type="button" id="select_disciplineType_btn" class="btn1 select_btn" value="选择"/>
							<div id="disptr" class="choose_show"><s:property value="otherProduct.disciplineType"/></div>
							<s:hidden name="otherProduct.disciplineType" id="dispName"/>
						</td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title5">学科代码：</span></td>
						<td class="table_td3">
							<input type="button" id="select_disciplineCode_btn" class="btn1 select_btn" value="选择"/>
							<div id="rdsp" class="choose_show"><s:property value="otherProduct.discipline"/></div>
							<s:hidden name="otherProduct.discipline" id="relyDisciplineName"/>
						</td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">关键词：</td>
						<td class="table_td3"><s:textfield name="otherProduct.keywords" cssClass="input_css"/>
								<br/><span class="tip">多个以分号（即; 或；）隔开！</span></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">简介：</td>
						<td class="table_td3"><s:textarea name="otherProduct.introduction" rows="6" cssClass="textarea_css"/></td>
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
						<td class="table_td2" width="130">出版单位：</td>
						<td class="table_td3"><s:textfield name="otherProduct.publishUnit" cssClass="input_css"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">出版时间：</td>
						<td class="table_td3">
							<s:textfield id="publicationDate" name="otherProduct.pressDate" cssClass="input_css" readonly="true">
								<s:param name="value">
									<s:date name="%{otherProduct.pressDate}" format="yyyy-MM-dd"/>
								</s:param>
							</s:textfield>
						</td>
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
<%--		<script type="text/javascript" src="javascript/file_upload_setting.js?ver=<%=application.getAttribute("systemVersion")%>"></script>--%>
<%--		<script type="text/javascript">window.onload = initSwf();</script>--%>
</html>