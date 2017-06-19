<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>修改申请</title>
		<s:include value="/swfupload.jsp" />
	</head>

	<body>
		<div style="width:700px;">
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr>
					<td class="text_red">本次操作限制在一个小时内完成，若有大段文本输入，建议您离线编辑后粘贴文本！</td>
				</tr>
			</table>
			<s:include value="/validateError.jsp" />
			<s:form id="form_award_modify" action="modify" namespace="/award/moesocial/application/apply">
			 <div style="overflow-y:scroll; overflow-x:hidden; height:490px;">
				 <s:hidden name="type" value="%{type}"/>
               	 <s:hidden id="personid" name="awardApplication.person.id" value="%{awardApplication.person.id}" />
               	 <s:hidden name="awardApplication.applicantSubmitStatus"/>
               	 <s:hidden name="awardApplication.productType.id"/>
               	 <s:hidden name="awardApplication.productId"/>
               	 <s:hidden name="ex_appflag" value="1"></s:hidden>
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr class="table_tr2">
						<td class="table_td2" width="100"><span class="table_title2">人文社科奖申请书：</span></td>
						<%--<td class="table_td3">
							<div>
								<span id="spanButtonPlaceHolder"></span>
								<input id="btnCancel" type="button" value="取消所有上传" onclick="swfu.cancelQueue();" disabled="disabled" class="btn3" />
							</div>
							<div class="fieldset flash" id="fsUploadProgress">
							</div>
							<div id="divStatus" style="display:none;">0个文件已上传</div>
							<s:hidden id="uploadKey" name="uploadKey" value="%{#session.uploadKey}" />
						</td>
						--%>
						<td class="table_td3">
							<input type="file" id="file_add" />
						</td>	
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title2">学科门类：</span></td>
						<td class="table_td3"><s:select cssClass="select" id="dtype" name="awardApplication.disciplineType"  value="awardApplication.disciplineType" list="%{#session.dtypemap}"  headerKey="-1" 
	 						listKey="key" listValue="value" headerValue="--%{getText('请选择')}--"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title3">所属单位：</span></td>
						<td class="table_td3"><s:select cssClass="select" name="unitId" list="unitDetails" headerKey="-1" headerValue="--%{getText('请选择')}--"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span>获奖情况：<br/>（限500字）</span></td>
						<td class="table_td3"><s:textarea name="awardApplication.prizeObtained" rows="6" cssClass="textarea_css" /></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span>社会反映：<br/>（限500字）</span></td>
						<td class="table_td3"><s:textarea name="awardApplication.response" rows="6" cssClass="textarea_css" /></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span>引用或被采纳情况：<br/>（限500字）</span></td>
						<td class="table_td3"><s:textarea name="awardApplication.adoption" rows="6" cssClass="textarea_css" /></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span>内容简介：<br/>（限20000字）</span></td>
						<td class="table_td3"><s:textarea name="awardApplication.introduction" rows="6" cssClass="textarea_css" /></td>
						<td class="table_td4"></td>
					</tr>
				</table>
				</div> 
				<div class="btn_div_view">
					<input class="btn1 j_addSaveModify" type="button" id="modifyApplySave" value="保存" />
					<input class="btn1 j_addSubmitModify" type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</s:form>
		</div>
		<script type="text/javascript">
			<s:if test="null != type && type != 0">
				(function(){
					var thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
					thisPopLayer.callBack(thisPopLayer);
				})();
			</s:if>
			seajs.use(['javascript/award/moesocial/application/apply/modify.js', 
					   'javascript/product/product.js'],
				 function(add, pro) {
				 	$(function() {
						modify.init();
						modify.initClick();
						pro.init();
						initSwf();
					})
			});
		</script>
	</body>
</html>