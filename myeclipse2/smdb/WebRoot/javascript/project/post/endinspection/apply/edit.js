define(function(require, exports, module) {
	require('dwr/util');
	require('javascript/engine');
	require('dwr/interface/projectService');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	require('uploadify');
	require('uploadify-ext');
	require('form');
	require('jquery-ui');
	require('validate');
	var datepick = require("datepick-init");
	var timeValidate = require('javascript/project/project_share/validate');
	var endValid = require('javascript/project/post/endinspection/apply/validate');
	
	var projectType="post";
	
	var showNumber = function(type){
		if(type==2){//填写结项证书编号
			$("#number_info").show();
		}else{
			$("#number_info").hide();
		}
	}
	//选择结项成果
	var editProductType = function(){
		var isApplyNoevaluation = $("input[name='isApplyNoevaluation'][type='radio']:checked").val();
		if (isApplyNoevaluation != 0 && isApplyNoevaluation != 1) {
			alert("请先选择是否免请免鉴定！\n");
			return false;
		}
		else {
			popEdit({
				type : 1,
				isApplyNoevaluation : isApplyNoevaluation,
				inData : $("[name='endProductInfo']").val(),
				callBack : function(result){
					$("[name='endProductInfo']").val(result.data.productType);
					$("#endProductInfo").html(result.data.productType);
				}
			});
			return false;
		}
	};
	
	//添加结项申请
	var addEndApply = function(data,layer){
		saveEndApply(data, 1, layer);
	};

	//修改结项申请
	var modifyEndApply = function(data,layer){
		saveEndApply(data, 2, layer);
	};
	
	//保存结项申请结果
	var saveEndApply = function(data, type, layer) {
		if($("#endStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadline").val());
		if(flag){
			var $fileIds = $("#fsUploadProgress_end").find("input[name='fileIds']");
			if($fileIds.length == 0){
				$("#uploadEndApply").attr("value",0);
			}else{
				$("#uploadEndApply").attr("value",1);
			}
			$('#endApplicantSubmitStatus').val(data);
			var url = "";
			if(type == 1){
				url = "project/post/endinspection/apply/add.action";
			}else if(type == 2){
				url = "project/post/endinspection/apply/modify.action";
			}
			$("#endFile").attr("action", url);
			$("#endFile").submit();
		}else{
			return false;
		}
	};
	
	//初始化修改结项结果
	var initEndResult = function() {
		if($("input[name='isApplyNoevaluation'][type='radio']:checked").val() == 1){
			$("#no_eval_info").show();
		}else{
			$("#no_eval_info").hide();
		}
		showNumber($("input[name='endResult'][type='radio']:checked").val());
	};
	
	var isNumerUnique = true;
	//添加或修改结项结果预处理
	var addEndResult = function(data, type, layer){
		endValid.valid_endResult();
		if(!$("#end_result").valid()){
			return;
		}
		var endResult = $("input[name='endResult'][type='radio']:checked").val();
		var endCertificate = $("#endCertificate").val();
		var endId = $("#endId").val();
		$("#endStatus").val(data);
		if (endResult == 2) {
			valid_number(endCertificate, endId);
		}
		if(!isNumerUnique){
			return;
		}
		toSubmitEndResult(layer);
	};
	
	var valid_number = function(endCertificate, endId){
		DWREngine.setAsync(false);
		projectService.isEndNumberUnique("PostEndinspection", endCertificate, endId, isEndNumberUniqueCallback);
		DWREngine.setAsync(true);
	};
	
	var isEndNumberUniqueCallback = function(data){
		var $endCertificate = $("#endCertificate");
		var $errorTd = $endCertificate.parent("td").next("td");
		if(!data){
			$errorTd.html('<span class="error">该证书编号已存在</span>');// 写错误信息
			isNumerUnique = false;
		}else{
			$errorTd.html('');// 清错误信息
				isNumerUnique = true;;
		}
	};
	
	var toSubmitEndResult = function(layer){
		var endNoauditResult = 0;
		var fileIds = $("#fsUploadProgress_end").find("input[name='fileIds']").val();
		var endResult = $("input[name='endResult'][type='radio']:checked").val();
		var endExcellentResult = $("input[name='endExcellentResult'][type='radio']:checked").val();
		var endCertificate = $("#endCertificate").val();
		var endDate = $("[name='endDate']:eq(0)").val();
		var endStatus = $("#endStatus").val();
		var type = $("#type").val();
		var modifyFlag = $("#modifyFlag").val();
		var endImportedOpinion = $("[name='endImportedOpinion']:eq(0)").val();
		var endOpinionFeedback = $("[name='endOpinionFeedback']:eq(0)").val();
		if(endStatus == 3){
			if( !confirm('提交后确定结果，是否确认提交？'))
			return;
		}
		var dis = {
			"type":type,
			"modifyFlag":modifyFlag,
			"endResult":endResult,
			"fileIds":fileIds,
			"endNoauditResult":endNoauditResult,
			"endExcellentResult":endExcellentResult,
			"endCertificate":endCertificate,
			"endDate":endDate,
			"endStatus":endStatus,
			"endImportedOpinion":endImportedOpinion,
			"endOpinionFeedback":endOpinionFeedback
		};
		
		layer.callBack(dis, type);
		layer.destroy();
	};

	//上传结项申请书
	var uploadFileResult = function(data, type, layer){
		var endFileId = $("#fsUploadProgress_end").find("input[name='fileIds']").val();
		var endId = $("#endId").val();
		var dis = {
			"endFileId":endFileId,
			"endId":endId
		};
		layer.callBack(dis, type);
		layer.destroy();
	};
	
	//初始化onclick事件
	exports.init = function() {
		$(function() {
			$("#file_add").uploadifyExt({
				uploadLimitExt : 1, //文件上传个数的限制
				fileSizeLimit : '300MB',//文件上传大小的限制
				fileTypeDesc : '附件',//可以不用管
				fileTypeExts : '*.doc; *.docx; *.pdf'
			});
		});
		$(function() {
			$("#file_research_add").uploadifyExt({
				uploadLimitExt : 1, //文件上传个数的限制
				fileSizeLimit : '300MB',//文件上传大小的限制
				fileTypeDesc : '附件',//可以不用管
				fileTypeExts : '*.zip; *.rar'
			});
		});
		var endId = $("#endId").val();
		$("#file_" +　endId).uploadifyExt({
			uploadLimitExt : 1, //文件上传个数的限制
			fileSizeLimit : '300MB',//文件上传大小的限制
			fileTypeDesc : '附件',//可以不用管
			fileTypeExts : '*.doc; *.docx; *.pdf'
		});
		$("#file_research_" +　endId).uploadifyExt({
			uploadLimitExt : 1, //文件上传个数的限制
			fileSizeLimit : '300MB',//文件上传大小的限制
			fileTypeDesc : '附件',//可以不用管
			fileTypeExts : '*.zip; *.rar'
		});
		
		$(".j_addEndApply").live("click", function(){
			var data = $(this).attr("data");
			addEndApply(data, thisPopLayer);
		});
		$(".j_modifyEndApply").live("click", function(){
			var data = $(this).attr("data");
			modifyEndApply(data, thisPopLayer);
		});
		$(".j_addEndResult").live("click", function(){
			var data = $(this).attr("data");
			var type = $(this).attr("types");
			addEndResult(data, type, thisPopLayer, projectType)
		});
		
		$(".j_showNumber").live("click", function(){
			var type = $("input[name='endResult'][type='radio']:checked").val();
			editEndinspection.showNumber(type);
		});
		initEndResult();
		
		window.showNumber = function(type){showNumber(type)};
		window.editProductType = function(){editProductType()};
//		window.addEndApply = function(data, layer){addEndApply(data, layer)};
//		window.modifyEndApply = function(data, layer){modifyEndApply(data, layer)};
//		window.initEndResult = function(){initEndResult()};
//		window.addEndResult = function(data, type, layer){addEndResult(data, type, layer, projectType)};
		window.uploadFileResult = function(data, type, layer){uploadFileResult(data, type, layer)};
		datepick.init();
		
		$("#addEndFee").live("click",function() {
			popAddEndFee({
				title : "添加结项经费结算明细",
				src : "project/post/endinspection/apply/toAddEndFee.action?projectid=" + $("#projectid").val() + "&feeNote=" + $("#feeNote").val() + 
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
																			"&otherFeeD=" + $("#otherFeeD").val() + "&otherNote=" + $("#otherNote").val() +
																			"&totalFee=" + $("#totalFeeD").val() + "&surplusFee=" + $("#surplusFee").val(),
				inData : {"projectid" : $("#projectid").val(),
						  "feeNote" : $("#feeNote").val(),
						  "bookFee" : $("#bookFee").val(),
						  "bookNote" : $("#bookNote").val(),
						  "dataFee" : $("#dataFee").val(),
						  "dataNote" : $("#dataNote").val(),
						  "travelFee" : $("#travelFee").val(),
						  "travelNote" : $("#travelNote").val(),
						  "conferenceFee" : $("#conferenceFee").val(),
						  "conferenceNote" : $("#conferenceNote").val(),
						  "internationalFee" : $("#internationalFee").val(),
						  "internationalNote" : $("#internationalNote").val(),
						  "deviceFee" : $("#deviceFee").val(),
						  "deviceNote" : $("#deviceNote").val(),
						  "consultationFee" : $("#consultationFee").val(),
						  "consultationNote" : $("#consultationNote").val(),
						  "laborFee" : $("#laborFee").val(),
						  "laborNote" : $("#laborNote").val(),
						  "printFee" : $("#printFee").val(),
						  "printNote" : $("#printNote").val(),
						  "indirectFee" : $("#indirectFee").val(),
						  "indirectNote" : $("#indirectNote").val(),
						  "otherFeeD" : $("#otherFeeD").val(),
						  "otherNote" : $("#otherNote").val(),
						  "totalFee" : $("#totalFeeD").val(),
						  "surplusFee" : $("#surplusFee").val()
						  },
				callBack : function(result){
				    $("#totalFee").html(result.totalFee);
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
					$("#otherFeeD").val(result.otherFeeD);
					$("#otherNote").val(result.otherNote);
					$("#totalFeeD").val(result.totalFee);
					$("#surplusFee").val(result.surplusFee);
					$("#fundedFee").val(result.fundedFee);
					this.destroy();
				}
			});
		});
		
		
		$("#addEndFeeApply").live("click",function() {
			popAddEndFee({
				title : "添加结项经费结算明细",
				src : "project/post/endinspection/apply/toAddEndFee.action?projectid=" + $("#endFileProjectid").val() + "&feeNote=" + $("#feeNote").val() + 
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
																			"&otherFeeD=" + $("#otherFeeD").val() + "&otherNote=" + $("#otherNote").val() +
																			"&totalFee=" + $("#totalFeeD").val() + "&surplusFee=" + $("#surplusFee").val(),
				inData : {"projectid" : $("#endFileProjectid").val(),
						  "feeNote" : $("#feeNote").val(),
						  "bookFee" : $("#bookFee").val(),
						  "bookNote" : $("#bookNote").val(),
						  "dataFee" : $("#dataFee").val(),
						  "dataNote" : $("#dataNote").val(),
						  "travelFee" : $("#travelFee").val(),
						  "travelNote" : $("#travelNote").val(),
						  "conferenceFee" : $("#conferenceFee").val(),
						  "conferenceNote" : $("#conferenceNote").val(),
						  "internationalFee" : $("#internationalFee").val(),
						  "internationalNote" : $("#internationalNote").val(),
						  "deviceFee" : $("#deviceFee").val(),
						  "deviceNote" : $("#deviceNote").val(),
						  "consultationFee" : $("#consultationFee").val(),
						  "consultationNote" : $("#consultationNote").val(),
						  "laborFee" : $("#laborFee").val(),
						  "laborNote" : $("#laborNote").val(),
						  "printFee" : $("#printFee").val(),
						  "printNote" : $("#printNote").val(),
						  "indirectFee" : $("#indirectFee").val(),
						  "indirectNote" : $("#indirectNote").val(),
						  "otherFeeD" : $("#otherFeeD").val(),
						  "otherNote" : $("#otherNote").val(),
						  "totalFee" : $("#totalFeeD").val(),
						  "surplusFee" : $("#surplusFee").val()
						  },
				callBack : function(result){
				    $("#totalFee").html(result.totalFee);
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
					$("#otherFeeD").val(result.otherFeeD);
					$("#otherNote").val(result.otherNote);
					$("#totalFeeD").val(result.totalFee);
					$("#surplusFee").val(result.surplusFee);
					$("#fundedFee").val(result.fundedFee);
					this.destroy();
				}
			});
		});
		
		$("#modifyEndFee").live("click",function() {
			popAddEndFee({
				title : "修改结项经费结算明细",
				src : "project/post/endinspection/apply/toAddEndFee.action?projectid=" + $("#projectid").val() + "&feeNote=" + $("#feeNote").val() + 
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
																			"&otherFeeD=" + $("#otherFeeD").val() + "&otherNote=" + $("#otherNote").val() +
																			"&totalFee=" + $("#totalFeeD").val() + "&surplusFee=" + $("#surplusFee").val(),
				inData : {"projectid" : $("#projectid").val(),
						  "feeNote" : $("#feeNote").val(),
						  "bookFee" : $("#bookFee").val(),
						  "bookNote" : $("#bookNote").val(),
						  "dataFee" : $("#dataFee").val(),
						  "dataNote" : $("#dataNote").val(),
						  "travelFee" : $("#travelFee").val(),
						  "travelNote" : $("#travelNote").val(),
						  "conferenceFee" : $("#conferenceFee").val(),
						  "conferenceNote" : $("#conferenceNote").val(),
						  "internationalFee" : $("#internationalFee").val(),
						  "internationalNote" : $("#internationalNote").val(),
						  "deviceFee" : $("#deviceFee").val(),
						  "deviceNote" : $("#deviceNote").val(),
						  "consultationFee" : $("#consultationFee").val(),
						  "consultationNote" : $("#consultationNote").val(),
						  "laborFee" : $("#laborFee").val(),
						  "laborNote" : $("#laborNote").val(),
						  "printFee" : $("#printFee").val(),
						  "printNote" : $("#printNote").val(),
						  "indirectFee" : $("#indirectFee").val(),
						  "indirectNote" : $("#indirectNote").val(),
						  "otherFeeD" : $("#otherFeeD").val(),
						  "otherNote" : $("#otherNote").val(),
						  "totalFee" : $("#totalFeeD").val(),
						  "surplusFee" : $("#surplusFee").val()
						  },
				callBack : function(result){
				    $("#totalFee").html(result.totalFee);
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
					$("#otherFeeD").val(result.otherFeeD);
					$("#otherNote").val(result.otherNote);
					$("#totalFeeD").val(result.totalFee);
					$("#surplusFee").val(result.surplusFee);
					$("#fundedFee").val(result.fundedFee);
					this.destroy();
				}
			});
		});
		
		$("#modifyEndFeeApply").live("click",function() {
			popAddEndFee({
				title : "修改结项经费结算明细",
				src : "project/post/endinspection/apply/toAddEndFee.action?projectid=" + $("#endFileProjectid").val() + "&feeNote=" + $("#feeNote").val() + 
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
																			"&otherFeeD=" + $("#otherFeeD").val() + "&otherNote=" + $("#otherNote").val() +
																			"&totalFee=" + $("#totalFeeD").val() + "&surplusFee=" + $("#surplusFee").val(),
				inData : {"projectid" : $("#endFileProjectid").val(),
						  "feeNote" : $("#feeNote").val(),
						  "bookFee" : $("#bookFee").val(),
						  "bookNote" : $("#bookNote").val(),
						  "dataFee" : $("#dataFee").val(),
						  "dataNote" : $("#dataNote").val(),
						  "travelFee" : $("#travelFee").val(),
						  "travelNote" : $("#travelNote").val(),
						  "conferenceFee" : $("#conferenceFee").val(),
						  "conferenceNote" : $("#conferenceNote").val(),
						  "internationalFee" : $("#internationalFee").val(),
						  "internationalNote" : $("#internationalNote").val(),
						  "deviceFee" : $("#deviceFee").val(),
						  "deviceNote" : $("#deviceNote").val(),
						  "consultationFee" : $("#consultationFee").val(),
						  "consultationNote" : $("#consultationNote").val(),
						  "laborFee" : $("#laborFee").val(),
						  "laborNote" : $("#laborNote").val(),
						  "printFee" : $("#printFee").val(),
						  "printNote" : $("#printNote").val(),
						  "indirectFee" : $("#indirectFee").val(),
						  "indirectNote" : $("#indirectNote").val(),
						  "otherFeeD" : $("#otherFeeD").val(),
						  "otherNote" : $("#otherNote").val(),
						  "totalFee" : $("#totalFeeD").val(),
						  "surplusFee" : $("#surplusFee").val()
						  },
				callBack : function(result){
				    $("#totalFee").html(result.totalFee);
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
					$("#otherFeeD").val(result.otherFeeD);
					$("#otherNote").val(result.otherNote);
					$("#totalFeeD").val(result.totalFee);
					$("#surplusFee").val(result.surplusFee);
					$("#fundedFee").val(result.fundedFee);
					this.destroy();
				}
			});
		});
	};
});