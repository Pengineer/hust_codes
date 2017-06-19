<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>重大攻关项目</title>
			<s:include value="/innerBase.jsp" />
		</head>
		
		<body>
			<div style="width:480px;">
				<s:form id="tops_audit">
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" width="120"><span class="table_title4">是否采纳课题：</span></td>
							<td class="table_td12"><s:radio cssClass="r_type" name="topsAuditResult" theme="simple" list="#{'2':getText('同意'),'1':getText('不同意')}" /></td>
							<td class="table_td13" width="90"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span>审核意见：</span></td>
							<td class="table_td12"><s:textarea name="topsAuditOpinion" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td13"></td>
						</tr>
					</table>
					<s:hidden id="deadline" name="deadline" />
					<s:hidden id="topsStatus" name="topsStatus" />
					<div class="btn_div_view">
						<input id="save" class="btn1" type="button" value="保存" onclick="modifyTopsAudit(2, thisPopLayer);" />
						<input id="submit" class="btn1" type="button" value="提交" onclick="modifyTopsAudit(3, thisPopLayer);" />
						<input id="cancel" class="btn1" type="button" value="取消" />
					</div>
				</s:form>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/project/key/topicSelection/audit.js', function(audit) {
					audit.init();
					audit.valid();
				});
			</script>
		</body>
	
</html>