/*
 * 基地项目变更审核弹出层
*/
$(document).ready(function(){
	
	//初始化弹出层变更事项
	var varItems = thisPopLayer.inData.varItems;
	var varItemsHtml = "";
	if(varItems.indexOf("changeMember") != -1){
		varItemsHtml += "<input type='checkbox' name='approveIssue' value='01' checked='true'>变更项目成员（含负责人）<br>"
	}
	if(varItems.indexOf("changeAgency") != -1){
		varItemsHtml += "<input type='checkbox' name='approveIssue' value='02' checked='true'>变更项目机构）<br>"
	}
	if(varItems.indexOf("changeProductType") != -1){
		varItemsHtml += "<input type='checkbox' name='approveIssue' value='03' checked='true'>变更成果形式<br>"
	}
	if(varItems.indexOf("changeName") != -1){
		varItemsHtml += "<input type='checkbox' name='approveIssue' value='04' checked='true'>变更项目名称<br>"
	}
	if(varItems.indexOf("changeContent") != -1){
		varItemsHtml += "<input type='checkbox' name='approveIssue' value='05' checked='true'>重大内容调整<br>"
	}
	if(varItems.indexOf("postponement") != -1){
		varItemsHtml += "<input type='checkbox' name='approveIssue' value='06' checked='true'>项目延期<br>"
	}
	if(varItems.indexOf("stop") != -1){
		varItemsHtml += "<input type='checkbox' name='approveIssue' value='07' checked='true'>项目中止<br>"
	}
	if(varItems.indexOf("changeFee") != -1){
		varItemsHtml += "<input type='checkbox' name='approveIssue' value='09' checked='true'>变更经费<br>"
	}
	if(varItems.indexOf("other") != -1){
		varItemsHtml += "<input type='checkbox' name='approveIssue' value='20' checked='true'>变更经费<br>"
	}
	$("#varItemsContainer").append(varItemsHtml);
	
	
	/*
	 * 选择变更同意/不同意的折叠效果
	*/
	$("input[name=varAuditResult]").change(function(){
		if($("input[name=varAuditResult]:checked").val() == "1"){
			$("#varItemsDiv").hide(500);
		}else{
			$("#varItemsDiv").show(500);
		}
	});
	
	
	//提交审核结果
	$(".confirm").bind("click",function(){
		var varId = thisPopLayer.inData.varId;
		var projectid = thisPopLayer.inData.projectid;
		var varAuditDate = $("#varAuditDate").val();
		var varAuditOpinion = $("#varAuditOpinion").val();
		var varAuditOpinionFeedback = $("#varAuditOpinionFeedback").val();
		var varAuditResult = $("input[name='varAuditResult']:checked").val();
		var varAuditSelectIssue = [];
		$("input[name='approveIssue']:checked").each(function(){ varAuditSelectIssue.push($(this).val()); })

		$.ajax({
			url: "project/instp/variation/audit.action",
			type: "post",
			data: "varId=" + varId + "&varAuditDate=" + varAuditDate
					+ "&projectid=" + projectid
					+ "&varAuditOpinion=" + varAuditOpinion + "&varAuditOpinionFeedback=" +　varAuditOpinionFeedback
					+ "&varAuditResult=" + varAuditResult + "&varAuditSelectIssue=" + varAuditSelectIssue,
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