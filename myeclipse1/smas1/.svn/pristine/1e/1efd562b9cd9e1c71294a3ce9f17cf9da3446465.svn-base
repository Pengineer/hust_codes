/*
 * 一般项目结项最终审核弹出层
*/
$(document).ready(function(){

	//配置文件上传
	$("#file_add").uploadify({
		'buttonImage' : '',
		'buttonText' : '选择文件',
		'swf'	  : 'tool/uploadify/uploadify.swf',
		'uploader' : basePath + 'upload/upload;jsessionid=' + $.cookie("JSESSIONID"),
		'fileObjName' : 'file',
		'progressData' : 'speed',
		'width' : 80,
		'height' : 22,
		'removeCompleted' : false,
		'uploadLimitExt' : 999,
		
		'onInit' : function(instance) {
			var groupId = instance.settings.id;
			var queueId = instance.settings.queueID;
			$.get("upload/fetchGroupFilesSummary", {"groupId" : groupId}, function(data){
				var summary = data.groupFilesSummary;
				for (var i = 0; i < summary.length; i++) {
					var fakeFile = {
						id : summary[i][0],
						name : summary[i][1],
						size : summary[i][2]
					};
					try {
						instance.settings.file_queued_handler.call(instance, fakeFile);
						instance.settings.upload_progress_handler.call(instance, fakeFile, fakeFile.size, fakeFile.size);
						$("#" + fakeFile.id + " div.uploadify-progress").remove();
					} finally {
					}
				}
			});
			
			$("#" + queueId + " a.discard").live("click", function(){
				var fileId = $(this).parent().parent().attr("fileId");
				var id = $(this).parent().parent().attr("id");
				$.post("upload/discard", {fileId : fileId || id, groupId : groupId}, function(){
					$("#" + groupId).uploadify("cancel", id);
				});
			});
			
			$("#" + groupId + " div.uploadify-button").css("text-indent", "");
		},

		'onUploadStart' : function(file) {
			if ($("#" + this.settings.queueID + " div.uploadify-queue-item").length > this.settings.uploadLimitExt) {
				alert("最多只能上传" + this.settings.uploadLimitExt + "个文件!");
				$("#" + this.settings.id).uploadify("cancel", file.id);
			}
			$("#" + this.settings.id).uploadify("settings", "formData", {groupId : this.settings.id});
		},

		'onUploadSuccess' : function(file, data, response) {
			$("#" + file.id).attr("fileId", $.parseJSON(data).fileId);
			$("#" + file.id + " div.uploadify-progress").remove();
		},
		
		'itemTemplate' : 
			'<div id="${fileID}" class="uploadify-queue-item">\
				<div class="cancel1">\
					<a class="discard" href="javascript:void(0);">X</a>\
				</div>\
				<span class="fileName">${fileName} (${fileSize})</span><span class="data"></span>\
				<div class="uploadify-progress">\
					<div class="uploadify-progress-bar"><!--Progress Bar--></div>\
				</div>\
			</div>'
	});
	
	/*
	 * 经费结算修改输入弹层
	*/
	$(".modifyMidFee").live("click",function(){
		var projectid = thisPopLayer.inData.projectid;
		popGeneralAudit({
			title : "经费结算明细",
			src : "project/general/midinspection/toAddMidFee.action?projectid=" + projectid + "&feeNote=" + $("#feeNote").val() + 
					"&bookFee=" + $("#bookFee").val() + "&bookNote=" + $("#bookNote").val() + 
					"&dataFee=" + $("#dataFee").val() + "&dataNote=" + $("#dataNote").val() +
					"&travelFee=" + $("#travelFee").val() + "&travelNote=" + $("#travelNote").val() +
					"&conferenceFee=" + $("#conferenceFee").val() + "&conferenceNote=" + $("#conferenceNote").val() +
					"&internationalFee=" + $("#internationalFee").val() + "&internationalNote=" + $("#internationalNote").val() +
					"&deviceFee=" + $("#deviceFee").val() + "&deviceNote=" + $("#deviceNote").val() +
					"&consultationFee=" + $("#consultationFee").val() + "&consultationNote=" + $("#consultationNote").val() +
					"&laborFee=" + $("#laborFee").val() + "&laborNote=" + $("#laborNote").val() +
					"&printFee=" + $("#printFee").val() + "&printNote=" + $("#printNote").val() +
					"&indirectFee=" + $("#indirectFee").val() + "&indirectNote=" + $("#indirectNote").val() +
					"&otherFeeD=" + $("#otherFee").val() + "&otherNote=" + $("#otherNote").val() +
					"&totalFee=" + $("#totalFee").val() + "&surplusFee=" + $("#surplusFee").val(),
			callBack : function(result){
				$("#feeNote").val(result.feeNote);
				$("#bookFee").val(result.bookFee);
				$("#bookNote").val(result.bookNote);
				$("#dataFee").val(result.dataFee);
				$("#dataNote").val(result.dataNote);
				$("#travelFee").val(result.travelFee);
				$("#travelNote").val(result.travelNote);
				$("#conferenceFee").val(result.conferenceFee);
				$("#conferenceNote").val(result.conferenceNote);
				$("#internationalFee").val(result.internationalFee);
				$("#internationalNote").val(result.internationalNote);
				$("#deviceFee").val(result.deviceFee);
				$("#deviceNote").val(result.deviceNote);
				$("#consultationFee").val(result.consultationFee);
				$("#consultationNote").val(result.consultationNote);
				$("#laborFee").val(result.laborFee);
				$("#laborNote").val(result.laborNote);
				$("#printFee").val(result.printFee);
				$("#printNote").val(result.printNote);
				$("#indirectFee").val(result.indirectFee);
				$("#indirectNote").val(result.indirectNote);
				$("#otherFee").val(result.otherFee);
				$("#otherNote").val(result.otherNote);
				$("#totalFee").val(result.totalFee);
			}
		});
	});
	
	
	//提交审核结果
	$(".confirm").bind("click",function(){
		var projectid = thisPopLayer.inData.projectid;
		$.ajax({
			url: "project/general/midinspection/addResult.action",
			type: "post",
			data: "projectid=" + projectid + "&midResult=" + $("input[name=midAuditResult]:checked").val()
				+ "&midDate=" + $("#midDate").val() + "&midImportedOpinion=" + $("#midAuditOpinion").val()
				+ "&midOpinionFeedback=" + $("#midAuditOpinionFeedback").val()
				+ "&projectFee.feeNote=" + $("#feeNote").val() + "&projectFee.bookFee=" + $("#bookFee").val()
				+ "&projectFee.bookNote=" + $("#bookNote").val() + "&projectFee.dataFee=" + $("#dataFee").val()
				+ "&projectFee.dataNote=" + $("#dataNote").val() + "&projectFee.travelFee=" + $("#travelFee").val()
				+ "&projectFee.travelNote=" + $("#travelNote").val() + "&projectFee.conferenceFee=" + $("#conferenceFee").val()
				+ "&projectFee.conferenceNote=" + $("#conferenceNote").val() + "&projectFee.internationalFee=" + $("#internationalFee").val()
				+ "&projectFee.internationalNote=" + $("#internationalNote").val() + "&projectFee.deviceFee=" + $("#deviceFee").val()
				+ "&projectFee.deviceNote=" + $("#deviceNote").val() + "&projectFee.consultationFee=" + $("#consultationFee").val()
				+ "&projectFee.consultationNote=" + $("#consultationNote").val() + "&projectFee.laborFee=" + $("#laborFee").val()
				+ "&projectFee.laborNote=" + $("#laborNote").val() + "&projectFee.printFee=" + $("#printFee").val()
				+ "&projectFee.printNote=" + $("#printNote").val() + "&projectFee.indirectFee=" + $("#indirectFee").val()
				+ "&projectFee.indirectNote=" + $("#indirectNote").val() + "&projectFee.otherFee=" + $("#otherFee").val()
				+ "&projectFee.otherNote=" + $("#otherNote").val() + "&projectFee.totalFee=" + $("#totalFee").val(),
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