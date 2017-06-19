<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
   		<title>添加</title>
   		<s:include value="/innerBase.jsp" />
		</head>
	
	<body>
		<div style="width:750px;">
			<s:hidden id="viewTypeFlag" name="viewType" />
			<s:hidden id="projectId" name="projectId"/>
			<s:hidden id="projectType" name="projectType"/>
			<s:hidden id="inspectionId" name="inspectionId"/>
			<div style="height:<s:if test='viewType==3'>500px;</s:if><s:else>545px;</s:else> *margin-right:20px; _margin-right:20px;">
				<s:include value="/validateError.jsp"/>
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<s:if test="viewType!=3">
						<tr class="table_tr2">
							<td class="table_td2" width="130"><span class="table_title5">从已有成果选择：</span></td>
							<td class="table_td3"><s:radio cssClass="SecOrAdd" name="SecOrAdd" list="#{'1': getText('是'), '0': getText('否')}"/></td>
							<td class="table_td4"></td>
						</tr>
					</s:if>
				</table>
				<table width="100%" border="0" cellspacing="2" cellpadding="2">
					<tr class="table_tr2">
						<td class="table_td2" width="130"><span class="table_title5">成果形式：</span></td>
						<td class="table_td3"><s:radio cssClass="pro_form" name="pro_form" list="#{'1': getText('论文'), '2': getText('著作'), '3': getText('研究咨询报告'), '4': getText('电子出版物'), '5': getText('专利'), '6': getText('其他成果')}"/></td>
						<td class="table_td4"></td>
					</tr>
				</table>
				<div id="product" style="display:none;">
					<div id="paper"><s:include value="/product/extIf/addPaper.jsp"/></div>
					<div id="book"><s:include value="/product/extIf/addBook.jsp"/></div>
					<div id="consultation"><s:include value="/product/extIf/addConsultation.jsp"/></div>
					<div id="electronic"><s:include value="/product/extIf/addElectronic.jsp"/></div>
					<div id="patent"><s:include value="/product/extIf/addPatent.jsp"/></div>
					<div id="otherProduct"><s:include value="/product/extIf/addOtherProduct.jsp"/></div>
				</div>
				<div id="select_product" style="display:none;">
					<s:form id="select_form" theme="simple" action="" method="post">
						<s:hidden name="inspectionId"/>
						<s:hidden name="viewType" />
						<s:hidden name="projectId"/>
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td2" width="130">
									<span class="table_title5">成果名称：</span>
								</td>
								<td class="table_td3" id="product_content">
									<s:select cssClass="select" list="#{}" id="list_product" headerKey="-1" headerValue="--%{getText('请选择')}--" name="entityId"/>
								</td>
								<td class="table_td4"></td>
							</tr>
						</table>
						<div class="btn_div_view">
							<input id="submit_btn" type="button" class="btn1" value="完成"/>
							<input id="cancel" type="button" class="btn1" value="取消"/>
						</div>
					</s:form>
				</div>
			</div>
		</div>
   		<script type="text/javascript">
				seajs.use('javascript/product/extIf/product.js', function(add) {
						$(function(){
							add.init();
//							initSwf();
						})
					});
			</script>
	</body>
</html>