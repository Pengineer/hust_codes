<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>修改人文社科奖申请</title>
		<s:include value="/innerBase.jsp" />
   		<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：社科奖励数据&nbsp;&gt;&nbsp;人文社科奖&nbsp;&gt;&nbsp;申请数据&nbsp;&gt;&nbsp;修改
		</div>
		<div style="height: 25px;"></div>
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr>
				<td style="width:15"></td>
				<td class="text_red">"申请须知：首先下载奖励申请书模板，离线填写完成后，再上传提交！</td>
			</tr>
		</table>
		<s:include value="/validateError.jsp" />
		<div class="main_content">
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="table_tr2">
					<td class="table_td2" width="135"><span class="table_title5">是否在线填写：</span></td>
					<td class="table_td3">
						<s:radio cssClass="applyType" name="existOrNot" list="#{'1':'是','0':'否'}" value="1" /></td>
					<td class="table_td4"></td>
				</tr>
			</table>
		</div>
		<!-- 方式一:离线申请 -->
		<div id="award_main" class="main" style="display: none;">
			<div class="main_content">
				<s:form id="form_award" action="add2012" namespace="/award/moesocial/application/apply" >
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr>
							<td class="table_td2" width="130"><span class="table_title5">第一步：</span>&nbsp;</td>
							<td class="table_td3">
								<img style="vertical-align:middle;" src="image/ico09.gif" />
								<a id="download_award_model" href="javascript:void(0);">下载人文社科奖申请书模板</a>
							</td>
							<td class="table_td4"></td>
						</tr>
						<tr>
							<td class="table_td2"><span class="table_title5">第二步：</span>&nbsp;</td>
							    <td class="table_td3">
									<input type="file" id="file_${awardApplication.id}" />
                                    <s:hidden id = "awardApplicationId" name = "awardApplication.id"></s:hidden>
								</td>
<%--								<td class="table_td3">--%>
<%--									<div>--%>
<%--										<span id="spanButtonPlaceHolder"></span>--%>
<%--										<input id="btnCancel" type="button" value="取消所有上传" onclick="swfu.cancelQueue();" disabled="disabled" class="btn3" />--%>
<%--									</div>--%>
<%--									<div class="fieldset flash" id="fsUploadProgress">--%>
<%--									</div>--%>
<%--									<div id="divStatus" style="display:none;">0个文件已上传</div>--%>
<%--									<s:hidden id="uploadKey" name="uploadKey" value="%{#session.uploadKey}" />--%>
<%--									<s:hidden name="file"/>--%>
<%--								</td>--%>
                                
							<td class="table_td4"></td>
						</tr>
						<s:hidden id="applicantSubmitStatus2" name="awardApplication.applicantSubmitStatus"/>
					</table>
				</s:form>
			</div>
			<div class="btn_bar2">
				<input id="save" class="btn1 j_addSaveOr" type="button" value="保存" />
				<input id="submit" class="btn1 j_addSaveOrNot" type="button" value="提交" />
				<input id="cancel" class="btn1" type="button" value="取消" />
			</div>
	   	</div>
	   	
	   	<!-- 方式二:在线申请 -->
		<div id="basic_main" class="main">
			<div class="main_content">
				<s:form id="application_form" action="modify" theme="simple" namespace="/award/moesocial/application/apply" >
					<s:hidden id="addOrModify" value='2'/>		
					<s:hidden id="applicantSubmitStatus2" name="awardApplication.applicantSubmitStatus"/>													
					<s:hidden id="defualSession" value="%{awardApplication.session}"/>
					<s:hidden id="personid" name="personid" value="%{#session.loginer.account.person.id}" />
					<s:hidden id="ptypeid" name="ptypeid" value="%{#session.ptypeid}"/>
					<s:hidden name="listflag" />
					<div id="info" style="display:none">
						<div class="main_content">
							<div id="procedure" class="step_css">
								<ul>
									<li class="proc" name="apply"><span class="left_step"></span><span class="right_step">基本信息</span></li>
									<li class="proc" name="product"><span class="left_step"></span><span class="right_step">成果信息</span></li>
									<li class="step_oo" name="finish"><span class="left_step"></span><span class="right_step">完成</span></li>
								</ul>
							</div>
						</div>
						<div class="main_content">
							<s:include value="/validateError.jsp" />
							<s:include value="/award/moesocial/application/apply/editApplyTab.jsp" />
							<s:include value="/award/moesocial/application/apply/editProductTab.jsp" />
						</div>
					</div>
				</s:form>
			</div>
			<div id="optr" class="btn_bar2">
				<input id="prev" class="btn2" type="button" value="上一步" />
				<input id="next" class="btn2" type="button" value="下一步" />
				<input id="save1" class="btn2" type="button" value="保存" />
				<input id="finish" class="btn1" type="button" value="完成" />
				<input id="cancel" class="btn1" type="button" value="取消" />
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/award/moesocial/application/apply/modify.js', function(modify) {
				modify.init();
				modify.initClick();
			});
		</script>
	</body>
</html>