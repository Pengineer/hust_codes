/**
 * 描述：基地项目申报审核弹出层
 * 作者：fengzq
 * 创建时间：20150315
*/

$(function(){
	/*
	 * 选择立项同意/不同意的折叠效果
	*/
	$("input[name=reviewAuditResult]").change(function(){
		if($("input[name=reviewAuditResult]:checked").val() == "1"){
			$("#approveFeeDiv").hide(500);
			$("#approveNumDiv").hide(500);
		}else{
			$("#approveFeeDiv").show(500);
			$("#approveNumDiv").show(500);
		}
	});
	
	//提交审核结果
	$(".confirm").live("click", function(){
		var entityId = thisPopLayer.inData;
		var reviewAuditDate = $("#reviewAuditDate").val();
		var reviewAuditOpinion = $("#reviewAuditOpinion").val();
		var reviewAuditOpinionFeedback = $("#reviewAuditOpinionFeedback").val();
		var reviewAuditResult = $("input[name='reviewAuditResult']:checked").val();
		var number = $("#number").val(); //项目批准号
		var approveFee = $("#approveFee").val();  //批准金额
		
	//发送ajax请求
	$.ajax({
		url: "project/instp/audit.action",
		type: "post",
		data: "entityId="+entityId+"&reviewAuditDate="+reviewAuditDate+"&reviewAuditOpinion="+reviewAuditOpinion
			+"&reviewAuditOpinionFeedback="+reviewAuditOpinionFeedback+"&reviewAuditResult="+reviewAuditResult+"&number=" + number + "&approveFee=" + approveFee,
		dataType: "json",
		success: function(result) {
			if(result.errorInfo != "" && result.errorInfo != null && result.errorInfo != undefined){
				alert(result.errorInfo);
			}else{
				thisPopLayer.callBack(result);
				thisPopLayer.destroy();
			}
		}
	})
	})
})