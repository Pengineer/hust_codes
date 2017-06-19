<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>一般项目</title>
			<s:include value="/innerBase.jsp" />
		</head>
		
		<body>
	<div style="width:520px;">
		<form id="mid_mothball">
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="table_tr2">
					<td class="table_td11" width="170"><span >未审核级别：</span></td>
					<td class="table_td12">
						<input type="checkbox"  name = "unAuditLevel" id = "apartment" value = "1"/>
						<label for="apartment" class="checkboxLabel">部级</label>
						<input type="checkbox"  name = "unAuditLevel" id = "province" value = "2"/>
						<label for="province" class="checkboxLabel">省级</label>
						<input type="checkbox"  name = "unAuditLevel" id = "agency" value = "3"/>
						<label for="agency" class="checkboxLabel">校级</label>
						<input type="checkbox"  name = "unAuditLevel" id = "department" value = "4"/>
						<label for="department" class="checkboxLabel">院级/基地</label>
					</td>
					<td class="table_td13" width="90"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td11"><span>中检申请时间：</span></td>
					<td class="table_td12">
						<input type="text" id="midStartDate" name="midStartDate" class="input_css_other date" readonly="true" placeholder = "--不限--"/>
						<span> 至  </span>
						<input type="text" id="midEndDate" name="midEndDate" class="input_css_other date" readonly="true" placeholder = "--不限--"/>
					</td>
					<td class="table_td13"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td11"><span class = "table_title4">是否同意中检：</span></td>
					<td class="table_td12">
						<input type="radio" name="midAuditResult" id="mid_audit_midAuditResult2" value="2" class="j_showOpinionFeedbackOrNot">
						<label for="mid_audit_midAuditResult2" class="j_showOpinionFeedbackOrNot">同意</label>
						<input type="radio" name="midAuditResult" id="mid_audit_midAuditResult1" value="1" class="j_showOpinionFeedbackOrNot">
						<label for="mid_audit_midAuditResult1" class="j_showOpinionFeedbackOrNot">不同意</label>

					</td>
					<td class="table_td13"></td>
				</tr>

				<tr class="table_tr2">
					<td class="table_td11"><span>中检时间：</span></td>
					<td class="table_td12">
						<input type="text" id="midAuditDate" name="midAuditDate" class="input_css_other date" readonly="true" />
					</td>
					<td class="table_td13"></td>
				</tr>

				<tr class="table_tr2">
							<td class="table_td11"><span>审核意见：</span><br /> <span> （限200字）</span></td>
							<td class="table_td12">
								<textarea style = "width:100%" name="midAuditOpinion" rows="2" cssClass="textarea_css" >封存超期未审核数据</textarea>
							</td>
							<td class="table_td13"></td>
				</tr>

				<tr class="table_tr2">
						<td class="table_td11">审核意见：<br/>（反馈给负责人）<br />
								<span>（限200字）</span></td>
						<td class="table_td12">
							<textarea  style = "width:100%" name="midAuditOpinionFeedback" rows="2" cssClass="textarea_css" ></textarea>
								<br/>
								<span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span>
								
						</td>															
						<td class="table_td13"></td>
				</tr>
							
			</table>
				<div class="btn_div_view">
					<input id="submit" class="btn1 j_addSubmit" type="submit" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
			</form>
			<script type="text/javascript">
				seajs.use('javascript/project/project_share/popMothballMidData.js', function(mothball) {
					mothball.init("project/key/midinspection/applyAudit");
				});
			</script>
		</body>
		
	</html>