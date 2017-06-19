<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>申请人文社科奖</title>
		<s:include value="/innerBase.jsp" />
   		<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：社科奖励数据&nbsp;&gt;&nbsp;人文社科奖&nbsp;&gt;&nbsp;申请数据&nbsp;&gt;&nbsp;修改
		</div>
		
		<div class="main">
			<div class="main_content">
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr>
						<td width="15"></td>
						<td class="text_red">"申请须知：首先下载奖励申请书模板，离线填写完成后，再上传提交！</td>
					</tr>
				</table>
				<s:include value="/validateError.jsp" />
				
				<div style="height: 25px;"></div>
				
				<s:form id="form_award" action="add2012" namespace="/award/moesocial/application/apply" >
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr>
							<td class="table_td2"><span class="table_title1">第一步：</span>&nbsp;</td>
							<td class="table_td4">
								<img style="vertical-align:middle;" src="image/ico09.gif" />
								<a id="download_award_model" href="javascript:void(0);">下载人文社科奖申请书模板</a>
							</td>
							<td class="table_td4"></td>
						</tr>
						<tr>
							<td class="table_td2"><span class="table_title1">第二步：</span>&nbsp;</td>
							<td class="table_td3">
								<input type="file" id="file_${awardApplication.id}" />
							</td>								
                               <s:hidden id = "awardApplicationId" name = "awardApplication.id"></s:hidden>
							<td class="table_td4"></td>
						</tr>
						<s:hidden name="awardApplication.applicantSubmitStatus"/>
						<s:hidden name="listflag"/>
						<s:hidden name="entityId"/>
					</table>
				</s:form>
			</div>
			
			<div class="btn_bar2">
				<input id="save" class="btn1 addSave" type="button" value="保存"/>
				<input id="submit" class="btn1 addSubmit" type="button" value="提交" />
				<input id="cancel" class="btn1" type="button" value="取消" />
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/award/moesocial/application/apply/add', function(add) {
				add.init();
				add.initClick();
			});
		</script>
	</body>
</html>