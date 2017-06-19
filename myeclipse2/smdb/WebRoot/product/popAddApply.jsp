<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
   		<title>成果报奖</title>
    	<s:include value="/innerBase.jsp" />
    	<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css" />
	</head>

	<body>
		<div style="width:700px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="text_red">本次操作限制在一个小时内完成，若有大段文本输入，建议您离线编辑后粘贴文本！</td>
				</tr>
			</table>
			
			<s:form id="application_form" action="add" namespace="/award/moesocial/application/apply" >
				<div style="overflow-y:scroll; overflow-x:hidden; height:460px;">
					<s:include value="/validateError.jsp" />
	   				<s:hidden id="personid" name="personid" value="%{personid}" />
	   				<s:hidden id="applicantSubmitStatus1" name="awardApplication.applicantSubmitStatus" />
	   				<s:hidden id="productId" name="productId" value="%{productId}" />
	   				<s:hidden name="entityId" value="%{entityId}" />
	   				<s:hidden name="ex_appflag" value="1"></s:hidden>
	   				<s:hidden name="applicationType" value="1"></s:hidden>
	   				<s:hidden name="dtypeList" value="%{dtypeList}" />
					<table width="700" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3">人文社科奖申请书：</span></td>
							<td class="table_td3">
								<input type="file" id="file_add"/>
							</td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3">申请届次：</span></td>
							<td class="table_td3"><s:select cssClass="select" name="awardApplication.session" list="{}" id="session5" headerKey="-1" headerValue="--%{getText('请选择')}--"/></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title3">学科门类：</span></td>
							<td class="table_td3"><s:select cssClass="select" id="dtype" name="awardApplication.disciplineType" list="%{#session.dtypemap}" headerKey="-1" headerValue="--%{getText('请选择')}--" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title3">所属单位：</span></td>
							<td class="table_td3"><s:select cssClass="select" name="unitId" list="unitDetails" headerKey="-1" headerValue="--%{getText('请选择')}--"/></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">获奖情况：<br/>（限500字）</td>
							<td class="table_td3"><s:textarea name="awardApplication.prizeObtained" rows="6" cssClass="textarea_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">社会反映：<br/>（限500字）</td>
							<td class="table_td3"><s:textarea name="awardApplication.response" rows="6" cssClass="textarea_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">引用或被采纳情况：<br/>（限500字）</td>
							<td class="table_td3"><s:textarea name="awardApplication.adoption" rows="6" cssClass="textarea_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">内容简介：<br/>（限20000字）</td>
							<td class="table_td3"><s:textarea name="awardApplication.introduction" rows="6" cssClass="textarea_css" /></td>
							<td class="table_td4"></td>
						</tr>
					</table>
				</div>
				<div class="btn_div_view">
					<input class="btn1 j_addApplySave" type="button" id="addApplySave" value="保存" />
					<input class="btn1 j_addApplySumbit" type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消"/>
				</div>
			</s:form>
		</div>
    	<script type="text/javascript">
			seajs.use('javascript/award/moesocial/application/apply/add.js', function(add) {
				add.init();
				add.initClick();
			});
		</script>
	</body>
</html>