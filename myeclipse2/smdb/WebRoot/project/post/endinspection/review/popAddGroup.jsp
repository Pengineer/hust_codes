<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>后期资助项目</title>
			<s:include value="/innerBase.jsp" />
		</head>
		<body>
			<div style="width:450px;">
				<s:form id="end_review_group">
					<s:hidden id="endId" name="endId"/>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" style="width:100px"><span class="table_title2">鉴定方式：</span></td>
							<td class="table_td12">
								<input type="radio" name="reviewWay" value="1" checked="true"/><span>通讯鉴定</span>
								<input type="radio" name="reviewWay" value="2" disabled="disabled"/><span class="disable">会议鉴定</span>
							</td>
							<td class="table_td13" width="90"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11" style="width:100px"><span class="table_title2">鉴定结果：</span></td>
							<td class="table_td12"><s:radio name="reviewResult" list="#{'2':getText('同意'),'1':getText('不同意')}"/></td>
							<td class="table_td13" width="90"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title2">成果修改定性意见：</span></td>
							<td class="table_td12"><s:select cssClass="select" id="reviewOpinionQualitative" name="reviewOpinionQualitative" list="%{baseService.getSystemOptionMapAsName('reviewOpinionQualitative', null)}" headerKey="-1" headerValue="--%{getText('请选择')}--" /></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span>鉴定意见：<br/>（限2000字）</span></td>
							<td class="table_td12"><s:textarea name="reviewOpinion" rows="2" cssClass="textarea_css" /><br/><a class="j_groupOpinion" href="javascript:void(0)">参考本组专家所有意见</a></td>
							<td class="table_td13"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_submitOrSaveAddEndGroupReview" data="2" type="button" value="保存" />
					<input id="submit" class="btn1 j_submitOrSaveAddEndGroupReview" data="3" type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/post/endinspection/review/edit.js','javascript/project/project_share/endinspection/review/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
					window.onload = edit.initGroupOpinion("post");
				});
			</script>
		</body>
	
</html>
