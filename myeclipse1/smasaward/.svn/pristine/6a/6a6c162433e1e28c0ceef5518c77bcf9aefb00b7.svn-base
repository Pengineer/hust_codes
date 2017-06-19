/*
 * 一般项目中检审核弹出层
*/
$(document).ready(function(){
	
	//提交审核结果
	$(".confirm").bind("click",function(){
		var midId = thisPopLayer.inData.midId;
		var projectid = thisPopLayer.inData.projectid;
		var midDate = $("#midDate").val();
		var midAuditOpinion = $("#midAuditOpinion").val();
		var midAuditOpinionFeedback = $("#midAuditOpinionFeedback").val();
		var midAuditResult = $("input[name='midAuditResult']:checked").val();
		
		$.ajax({
			url: "project/general/midinspection/audit.action",
			type: "post",
			data: "midId=" + midId + "&midDate=" + midDate
					+ "&projectid=" + projectid
					+ "&midAuditOpinion=" + midAuditOpinion + "&midAuditOpinionFeedback=" +　midAuditOpinionFeedback
					+ "&midAuditResult=" + midAuditResult,
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