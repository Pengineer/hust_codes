/**
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	var viewProject = require('javascript/project/project_share/view');
	var project = require('javascript/project/project_share/project');
	var timeValidate = require('javascript/project/project_share/validate');
	
	//查看结项
	var readEnd = function(projectType, showProjectDetails) {
		viewProject.viewProject("project/" + projectType + "/endinspection/apply/view.action", showProjectDetails);
	};
	 
	//下载结项文件
	var downloadEndTemplate = function(projectType) {
		validateUrl = 'project/' + projectType + '/endinspection/apply/validateTemplate.action?projectid='+$("#projectid").val();
	 	successUrl = 'project/' + projectType + '/endinspection/apply/downloadTemplate.action';
	 	downloadFile(validateUrl, successUrl);
	 	return false;
	};
     
   //准备添加结项申请
	var toAddEndApplyPop = function(projectType, showProjectDetails) {
	 	if($("#endStatus").val()== 0){
	 		alert("该业务已停止，无法进行此操作！");
		return false;
	 	}
	 	popProjectOperation({
	 		title: "添加结项申请",
	 		src : 'project/' + projectType + '/endinspection/apply/toAdd.action?projectid=' + $("#projectid").val(),
	 		callBack : function(layer){
	 			$("#update").val(1);
				readEnd(projectType, showProjectDetails);
				layer.destroy();
			}
		});
	 	return false;
	};
     
   //准备修改结项申请
	var toModifyEndApplyPop = function(projectType, showProjectDetails) {
		if($("#endStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		popProjectOperation({
			title: "修改结项申请",
			src : 'project/' + projectType + '/endinspection/apply/toModify.action?projectid=' + $("#projectid").val(),
			callBack : function(layer){
				$("#update").val(1);
				readEnd(projectType, showProjectDetails);
				layer.destroy();
			}
		});
	};
     //提交结项申请
	var submitEndApply = function(projectType, showProjectDetails) {
		if($("#endStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineEnd", $("#endinspection")).val());
		if(flag){
			if( !confirm('提交后无法修改且不能再添加结项成果，是否确认提交？')){
				return;
			}
			$.ajax({
				url: "project/" + projectType + "/endinspection/apply/submit.action",
				type: "post",
				data: "projectid=" + $("#projectid").val(),
				dataType: "json",
				success: function(result){
					if (result.errorInfo == null || result.errorInfo == "") {
						$("#update").val(1);
						readEnd(projectType, showProjectDetails);
					}
					else {
						alert(result.errorInfo);
					}
				}	
			});
		}
	};
     
   //删除结项申请
	var deleteEndApply = function(projectType, showProjectDetails) {
	 	if($("#endStatus").val()== 0){
	 		alert("该业务已停止，无法进行此操作！");
	 		return false;
	 	}
	 	var flag = timeValidate.timeValidate($("#deadlineEnd", $("#endinspection")).val());
	 	if(flag){
	 		if( !confirm('确认删除此结项申请？'))
	 			return;
	 		$.ajax({
	 			url: "project/" + projectType + "/endinspection/apply/delete.action",
	 			type: "post",
	 			data: "entityIds=" + $("#projectid").val(),
	 			dataType: "json",
	 			success: function(result){
	 				if (result.errorInfo == null || result.errorInfo == "") {
	 					$("#update").val(1);
	 					readEnd(projectType, showProjectDetails);
	 				}
	 				else {
	 					alert(result.errorInfo);
	 				}
	 			}	
	 		});
	 	}else{
	 		return false;
	 	}
	};

     //添加结项审核
	var addEndAuditPop = function(projectType, showProjectDetails) {
	 	if($("#endStatus").val()== 0){
	 		alert("该业务已停止，无法进行此操作！");
	 		return false;
	 	}
	 	var accountType = $("#accountType").val();
	 	var endProAudAlr = $("#endProAudAlr").val();
	 	var flag = timeValidate.timeValidate($("#deadlineEnd", $("#endinspection")).val());
	 	if(flag){
	 		//存在结项成果待审
	 		if($("#canAuditEndProduct").val() == 'true') {
	 			var auditflag = true;
	 			if(accountType != "MINISTRY") {//初审
	 				$("input[type='checkbox'][name='entityIds']", $("#list_table_end_0")).each(function() {
	 					if($(this).next().val() == 0) {//待审
	 						auditflag = false; return;
	 					}
	 				});
	 			} else {//终审
	 				$("input[type='checkbox'][name='entityIds']", $("#list_table_end_0")).each(function() {
	 					if($(this).next().next().val() == 0) {//待审
	 						auditflag = false; return;
	 					}
	 				});
	 			}
	 			if(!auditflag) {
	 				alert("请先审核完结项成果, 否则不能进行此操作！");
	 				return;
	 			}
	 		}
	 		var id = document.getElementsByName('projectid')[0].value;
	 		popProjectOperation({
	 			title : "添加审核意见",
	 			src : "project/" + projectType + "/endinspection/applyAudit/toAdd.action?projectid=" + id,
	 			callBack : function(dis){
	 				doSubmitEndAudit(dis, projectType, showProjectDetails);
	 			}
	 		});
	 	}else{
	 		return false;
	 	}
	};

     //修改结项审核
	var modifyEndAuditPop = function(projectType, showProjectDetails) {
	 	if($("#endStatus").val()== 0){
	 		alert("该业务已停止，无法进行此操作！");
	 		return false;
	 	}
	 	var flag = timeValidate.timeValidate($("#deadlineEnd", $("#endinspection")).val());
	 	if(flag){
	 		var accountType = $("#accountType").val();
	 		var endProAudAlr = $("#endProAudAlr").val();
	 		//存在结项成果待审
	 		if($("#canAuditEndProduct").val() == 'true') {
	 			var auditflag = true;
	 			if(accountType != "MINISTRY") {//初审
	 				$("input[type='checkbox'][name='entityIds']", $("#list_table_end_0")).each(function() {
	 					if($(this).next().val() == 0) {//待审
	 						auditflag = false; return;
	 					}
	 				});
	 			} else {//终审
	 				$("input[type='checkbox'][name='entityIds']", $("#list_table_end_0")).each(function() {
	 					if($(this).next().next().val() == 0) {//待审
	 						auditflag = false; return;
	 					}
	 				});
	 			}
	 			if(!auditflag) {
	 				alert("请先审核完结项成果, 否则不能进行此操作！");
	 				return;
	 			}
	 		}
	 		var id = document.getElementsByName('projectid')[0].value;
	 		popProjectOperation({
	 			title : "修改审核意见",
	 			src : "project/" + projectType + "/endinspection/applyAudit/toModify.action?projectid=" + id,
	 			callBack : function(dis){
	 				doSubmitEndAudit(dis, projectType, showProjectDetails);
	 			}
	 		});
	 	}else{
	 		return false;
	 	}
	};
	
     //查看结项审核详情
 	var viewEndAuditPop = function(id, data, projectType) {
     	popProjectOperation({
     		title : "查看审核详细意见",
     		src : 'project/' + projectType + '/endinspection/applyAudit/view.action?endId=' + id + '&vtp=' + data
     	});
    }
     //提交审核
    var submitEndAudit = function(projectType, showProjectDetails) {
     	if($("#endStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineEnd", $("#endinspection")).val());
     	if(flag){
     		if( !confirm('确认提交此审核意见？'))
     			return;
     		$.ajax({ 
     			url: "project/" + projectType + "/endinspection/applyAudit/submit.action",
     			type: "post",
    			data: "projectid=" + $("#projectid").val(),
    			dataType: "json",
     	       	success: function(result){
     				if (result.errorInfo == null || result.errorInfo == "") {
     					$("#update").val(1);
     					readEnd(projectType, showProjectDetails);
     				}
     				else {
     					alert(result.errorInfo);
     				}
     			}	 
     		});
     	}else{
     		return false;
     	}
     };
     //退回结项
     var backEndApply = function(projectType, showProjectDetails) {
     	if($("#endStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineEnd", $("#endinspection")).val());
     	if(flag){
     		if( !confirm('确认退回此结项申请？'))
     			return;
     		$.ajax({ 
     			url: "project/" + projectType + "/endinspection/applyAudit/back.action",
     			type: "post",
    			data: "projectid=" + $("#projectid").val(),
    			dataType: "json",
     	        success: function(result){
     				if (result.errorInfo == null || result.errorInfo == "") {
     					$("#update").val(1);
     					readEnd(projectType, showProjectDetails);
     				}
     				else {
     					alert(result.errorInfo);
     				}
     			}	  
     		});
     	}else{
     		return false;
     	}
     };
     //准备录入结项结果信息
     var addEndResultPop = function(projectType, showProjectDetails){
     	popProjectOperation({
     		title : "录入结项结果信息",
     		src : 'project/' + projectType + '/endinspection/apply/toAddResult.action?projectid='+$("#projectid").val(),
     		callBack : function(dis){
     			doSubmitEndResult(dis, projectType, showProjectDetails);
     		}
     	});
     	return false;
     };

     //提交结项结果
     var submitEndResult = function(projectType, showProjectDetails){
     	if( !confirm('提交后无法修改，是否确认提交？')){
     		return;
     	}
     	$.ajax({
     		url: "project/" + projectType + "/endinspection/apply/submitResult.action",
     		type: "post",
     		data: "projectid=" + $("#projectid").val(),
     		dataType: "json",
     		success: function(result){
     			if (result.errorInfo == null || result.errorInfo == "") {
     				$("#update").val(1);
     				readEnd(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}	
     	});
     };
     //ajax提交结项的审核意见
     var doSubmitEndAudit = function(data, projectType, showProjectDetails) {
    	document.getElementsByName('endAuditResult')[0].value = data.endAuditResult;
		document.getElementsByName('endNoauditResult')[0].value = data.endNoauditResult;
		document.getElementsByName('endExcellentResult')[0].value = data.endExcellentResult;
		document.getElementsByName('endAuditOpinion')[0].value = data.endAuditOpinion;
		document.getElementsByName('endAuditStatus')[0].value = data.endAuditStatus;
		$('#endIsApplyExcellent').attr("value",data.isApplyExcellent);
		$('#endIsApplyNoevaluation').attr("value",data.isApplyNoevaluation);
		$('#endAuditOpinionFeedback').val(data.endAuditOpinionFeedback);
		var zz = document.getElementById('endFormProjectid');
		var tmp = document.getElementById('projectid');
		zz.value = tmp.value;
		var type = data.type;
		var url = "";
		if(type == 1){
			url = "project/" + projectType + "/endinspection/applyAudit/add.action";
		}else if(type == 2){
			url = "project/" + projectType + "/endinspection/applyAudit/modify.action";
		}
		$("#end_form").ajaxSubmit({ 
			url: url,
	        success:  function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#update").val(1);
					readEnd(projectType, showProjectDetails);
				}
				else {
					alert(result.errorInfo);
				}
			}	
		});
     };
     
     //向后台添加或修改结项结果
     var doSubmitEndResult = function(dis, projectType, showProjectDetails){
     	url = "";
     	if(dis.type == 1){
     		url = "project/" + projectType + "/endinspection/apply/addResult.action";
     	}else if(dis.type == 2){
     		url = "project/" + projectType + "/endinspection/apply/modifyResult.action";
     	}
//     	$("#fileIds").val(dis.fileIds);
//    	$("#endImportedIsApplyExcellent").val(dis.isApplyExcellent);
//    	$("#endImportedIsApplyNoevaluation").val(dis.isApplyNoevaluation);
//    	$("#endImportedResult").val(dis.endResult);
//    	$("#endImportedNoauditResult").val(dis.endNoauditResult);
//    	$("#endImportedExcellentResult").val(dis.endExcellentResult);
//    	$("#endImportedCertificate").val(dis.endCertificate);
//    	$("#endImportedDate").val(dis.endDate);
//    	$("#endImportedProductInfo").val(dis.endProductInfo);
//    	$("#endImportedMember").val(dis.endMember);
//    	$("#endImportedStatus").val(dis.endStatus);
//    	$("#endImportedOpinion").val(dis.endImportedOpinion);
//    	$("#endImportedOpinionFeedback").val(dis.endOpinionFeedback);
//    	$("#endModifyFlag").val(dis.modifyFlag);
    	var zz = document.getElementById('endImportedFormProjectid');
    	var tmp = document.getElementById('projectid');
    	zz.value = tmp.value;
//    	$("#end_imported_form").ajaxSubmit({ 
//    		url: url,
//            success: function(result){
//    			if (result.errorInfo == null || result.errorInfo == "") {
//    				$("#update").val(1);
//    				readEnd(projectType, showProjectDetails);
//    			}
//    			else {
//    				alert(result.errorInfo);
//    			}
//    		}	  
//    	});
    	
    	$.ajax({
     		url: url,
     		type: "post",
     		data: "projectid=" + $("#projectid").val() + "&fileIds=" + dis.fileIds + "&isApplyExcellent=" + dis.isApplyExcellent + "&isApplyNoevaluation=" + dis.isApplyNoevaluation + 
     			  "&endResult=" + dis.endResult + "&endNoauditResult=" + dis.endNoauditResult + "&endExcellentResult=" + dis.endExcellentResult + "&endCertificate=" + dis.endCertificate + 
     			  "&endDate=" + dis.endDate +"&endProductInfo=" + dis.endProductInfo +"&endMember=" + dis.endMember +"&endImportedStatus=" + dis.endStatus +"&endImportedOpinion=" + dis.endImportedOpinion +
     			  "&endOpinionFeedback=" + dis.endOpinionFeedback + "&modifyFlag=" + dis.modifyFlag +
     			  															"&bookFee=" + dis.bookFee + "&bookNote=" + dis.bookNote + 
																			"&dataFee=" + dis.dataFee + "&dataNote=" + dis.dataNote +
																			"&travelFee=" + dis.travelFee + "&travelNote=" + dis.travelNote +
																			"&conferenceFee=" + dis.conferenceFee + "&conferenceNote=" + dis.conferenceNote +
																			"&internationalFee=" + dis.internationalFee + "&internationalNote=" + dis.internationalNote +
																			"&deviceFee=" + dis.deviceFee + "&deviceNote=" + dis.deviceNote +
																			"&consultationFee=" + dis.consultationFee + "&consultationNote=" + dis.consultationNote +
																			"&laborFee=" + dis.laborFee + "&laborNote=" + dis.laborNote +
																			"&printFee=" + dis.printFee + "&printNote=" + dis.printNote +
																			"&indirectFee=" +dis.indirectFee + "&indirectNote=" + dis.indirectNote +
																			"&otherFeeD=" + dis.otherFeeD + "&otherNote=" + dis.otherNote +
																			"&totalFee=" + dis.totalFee + "&fundedFee=" + dis.fundedFee + "&feeNote=" + dis.feeNote + "&surplusFee=" + dis.surplusFee,
     		dataType: "json",
     		success:  function(result){
     			if (result.errorInfo == null || result.errorInfo == "") {
     				$("#update").val(1);
     				readEnd(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}	 
     	});
    	
     };

     //准备上传结项申请书
     var uploadEndPop = function(endId, projectType, showProjectDetails){
     	popProjectOperation({
     		title : "上传电子文档",
     		src : 'project/' + projectType + '/endinspection/apply/toUploadFileResult.action?endId=' + endId,
     		inData : {"endId" : endId},
     		callBack : function(dis, type){
     			doSubmitUploadEndResult(dis, type, projectType, showProjectDetails);
     		}
     	});
     	return false;
     };

     var doSubmitUploadEndResult = function (dis, type, projectType, showProjectDetails){
     	url = "project/" + projectType + "/endinspection/apply/uploadFileResult.action?projectid=" + $("#projectid").val();
     	$("#endFileId").val(dis.endFileId);
     	$("#endId").val(dis.endId);
     	$("#uploadEndFile_form").ajaxSubmit({ 
     		url: url,
             success: function(result){
     			if (result.errorInfo == null || result.errorInfo == "") {
     				$("#update").val(1);
     				readEnd(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}	  
     	});
     };
     
   //打印结项证书
    var isLoaded = false, printCounter = 0; 
    var printCertificate = function(elem, projectType, showProjectDetails){
     	var number = $.trim($(elem).parent("div").parent("div").prev().find(".number").html());
     	var info = reorgnizeCertificateInfo(number); //重组结项证书信息
     	for(var i in info){
     		if(null == info[i] || info[i] == ""){
     			alert("结项证书信息不完整，不能打印！");
     			return false;
     		}
     	}
     	
     	var ifr_window = window.frames["print_certificate"];
     	if(!ifr_window){
     		var printIframe = $("<iframe src='' name='print_certificate'></iframe>");
     		printIframe.css({
     			filter:"Alpha(Opacity=0)",//滤镜
     			opacity: "0",//设置透明度
     			scrolling : "no",//隐藏滚动条
     			vspale : "0",//页边距
     			height: "0px",//设置高度
     			width : "100%",
     			border : "0px",
     			frameBorder : "0px"
     		});
     		printIframe.attr("src", "project/" + projectType + "/endinspection/apply/printCertificate.action");
     		$("body").append(printIframe);//将printIframe添加到body所在页面
     		ifr_window = window.frames["print_certificate"];
     	}
     	
     	$(ifr_window.document).ready(function(){
     		isLoaded = true;
     	});
     	
     	if(confirm("您确定要打印结项证书吗？\n打印前请进行必要页面设置：\n  纸张大小：B4(ISO)\n  打印方向：横向\n  页眉页脚：空白\n  页边距（上下左右）：0")){
     		//判断调用的时机，如果iframe没加载完则调用打印没效果，即空白页
			//如果在iframe的页面未完全装入的时候,调用iframe的DOM模型,会发生很严重的错误
     		if(isLoaded){
     			$(ifr_window.document.getElementsByTagName("body")[0]).find("._val").each(function(index){
     				$(this).html("" + info[index] + "");
     			});
     			ifr_window.focus();//聚焦
     			ifr_window.print();//打印
     		}
     		
     		$(ifr_window.document).ready(function(){
     			if(printCounter == 0){//判断打印次数
     				$(ifr_window.document.getElementsByTagName("body")[0]).find("._val").each(function(index){
     					$(this).html("" + info[index] + "");
     				});
     				if(!isLoaded){
     					
     					ifr_window.focus();
     					ifr_window.print();
     				}
     				isLoaded = true;
     				printCounter++;
     			}
     		});
     		
     		if(confirm("您确定结项证书已成功打印吗？")){
     			$.ajax({
     				url: "project/" + projectType + "/endinspection/apply/confirmPrintCertificate.action",
     				type: "post",
     				data: "projectid=" + $("#projectid").val(),
     				dataType: "json",
     				success: function(result){
     					if (result.errorInfo == null || result.errorInfo == "") {
     						readEnd(projectType, showProjectDetails);
     					}
     					else {
     						alert(result.errorInfo);
     					}
     				}	
     			});
     		}
     	}
     };
     
     //重组结项证书信息
    var reorgnizeCertificateInfo = function(number){
     	var dic = {"一" : 1, "二" : 2, "三" : 3, "四" : 4, "五" : 5, "六" : 6, "七" : 7, "八" : 8, "九" : 9, "十" : 10};
     	var endData = certificateInfo[certificateInfo.length - 1][certificateInfo[certificateInfo.length - 1].length - dic[number]];
     	var info = [];
     	for(var i = 0; i < certificateInfo.length - 1; i++){
     		if(i != 3){
     			info.push(certificateInfo[i]);
     		} else if (i == 3){
     			var memNames = endData['memberName'];
     			if(memNames){//项目成员
     				var nameArr = memNames.split("; "), memStr = "";
     					browerVersion = getBrowserVersion();
     				for(var j in nameArr){//保证人名不拆开，多行显示
     					if($.trim(browerVersion) == "msie 6.0"){//如果是IE6
     						memStr += "<span style='white-space:nowrap;'>" + nameArr[j] + "&nbsp;</span>";
     					} else {
     						memStr += "<span style='float:left;word-break:normal; width:auto; display:block'>" + nameArr[j] + "&nbsp;</span>";
     					}
     				}
     				info.push(memStr);
     			} else {
     				info.push("无");
     			}
     		}
     	}
     	info.push(endData['certificate']);
     	info.push(endData['finalAuditDate'].toString().substring(0,4));
     	info.push(endData['finalAuditDate'].toString().substring(5,7));
     	info.push(endData['finalAuditDate'].toString().substring(8));
     	return info;
    };

	module.exports = {
		toAddEndApplyPop: function(projectType, showProjectDetails){toAddEndApplyPop(projectType, showProjectDetails)},
		toModifyEndApplyPop: function(projectType, showProjectDetails){toModifyEndApplyPop(projectType, showProjectDetails)}, 
		submitEndApply: function(projectType, showProjectDetails){submitEndApply(projectType, showProjectDetails)},
		deleteEndApply: function(projectType, showProjectDetails){deleteEndApply(projectType, showProjectDetails)},
		addEndAuditPop: function(projectType, showProjectDetails){addEndAuditPop(projectType, showProjectDetails)},
		modifyEndAuditPop: function(projectType, showProjectDetails){modifyEndAuditPop(projectType, showProjectDetails)},
		viewEndAuditPop: function(id, data, projectType){viewEndAuditPop(id, data, projectType)},
		submitEndAudit: function(projectType, showProjectDetails){submitEndAudit(projectType, showProjectDetails)},
		backEndApply: function(projectType, showProjectDetails){backEndApply(projectType, showProjectDetails)},
		addEndResultPop: function(projectType, showProjectDetails){addEndResultPop(projectType, showProjectDetails)},
		submitEndResult: function(projectType, showProjectDetails){submitEndResult(projectType, showProjectDetails)},
		uploadEndPop: function(endId, projectType, showProjectDetails){uploadEndPop(endId, projectType, showProjectDetails)},
		downloadEndTemplate: function(projectType){downloadEndTemplate(projectType)},
		printCertificate: function(elem, projectType, showProjectDetails){printCertificate(elem, projectType, showProjectDetails)},
		doSubmitEndResult: function(dis, projectType, showProjectDetails){doSubmitEndResult(dis, projectType, showProjectDetails)},
		readEnd: function(projectType, showProjectDetails){readEnd(projectType, showProjectDetails)}
	};
});
