/*
 * 基地项目结项审核弹出层
*/
$(document).ready(function(){
	//初始化弹出层结项申请免鉴定或者优秀成果选项
	var isAppExce = thisPopLayer.inData.isAppExce;
	var isAppNoeval = thisPopLayer.inData.isAppNoeval;
	var endAppItemsHtml = "";
	if(isAppExce == 1){
		endAppItemsHtml += "<tr><td style='text-align:right;width:40%;'>优秀成果：</td>" +
				"<td style='width:30%;'><input type='radio' name='endExcellentResult' value='2' checked='true'/>同意</td>" +
				"<td style='width:30%;'><input type='radio' name='endExcellentResult' value='1'/>不同意</td></tr>";
	}
	if(isAppNoeval == 1){
		endAppItemsHtml += "<tr><td style='text-align:right;width:40%;'>免检定：</td>" +
				"<td style='width:30%;'><input type='radio' name='endNoauditResult' value='2' checked='true'/>同意</td>" +
				"<td style='width:30%;'><input type='radio' name='endNoauditResult' value='1'/>不同意</td></tr>";
	}
	$(endAppItemsHtml).insertBefore("#endAppItemsSign");
	//$("#endAppItemsSign").append(endAppItemsHtml);
	
	
	//提交审核结果
	$(".confirm").bind("click",function(){
		var endId = thisPopLayer.inData.endId;
		var projectid = thisPopLayer.inData.projectid;
		var ministryAuditDate = $("#endMinistryAuditDate").val();
		var ministryAuditOpinion = $("#ministryAuditOpinion").val();
		var endAuditResult = $("input[name='ministryResultEnd']:checked").val();
		var endExcellentResult = $("input[name='endExcellentResult']:checked").val();
		var endNoauditResult = $("input[name='endNoauditResult']:checked").val();
		var endAuditOpinionFeedback = $("input[name='endAuditOpinionFeedback']:checked").val();
		$.ajax({
			url: "project/instp/endinspection/audit.action",
			type: "post",
			data: "endId=" + endId + "&projectid=" + projectid + "&ministryAuditDate=" + ministryAuditDate
					+ "&ministryAuditOpinion=" + ministryAuditOpinion + "&isApplyExcellent=" + isAppExce
					+ "&endExcellentResult=" + endExcellentResult + "&endAuditOpinionFeedback=" + endAuditOpinionFeedback
					+ "&isApplyNoevaluation=" + isAppNoeval + "&endNoauditResult=" + endNoauditResult
					+ "&endAuditResult=" + endAuditResult,
			dataType: "json",
			success: function(result) {
				if(result.errorInfo != "" && result.errorInfo != null && result.errorInfo != undefined){
					alert(result.errorInfo);
				}else{
					thisPopLayer.callBack(result);
					thisPopLayer.destroy();
				}
			}
		});
	});
});