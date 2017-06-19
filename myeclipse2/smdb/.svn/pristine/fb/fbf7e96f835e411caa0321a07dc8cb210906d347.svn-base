/**
 * 用于添加、修改结项申请及结项结果
 * @author 余潜玉、肖雅
 */
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
	var datepick = require("datepick-init");
	var timeValidate = require('javascript/project/project_share/validate');
	var endValid = require('javascript/project/project_share/endinspection/apply/validate');
	
	//日期选择器初始化
	var datepickInit = function(){
		datepick.init();
	};
	
	//是否填写免鉴定结果
	var showNoEval = function(data){
		$("#endProductInfo").html("");
		$("[name='endProductInfo']").val("");
		if(data == 1){
			$("#no_eval_info").show();
		}else{
			$("#no_eval_info").hide();
		}
	};
	
	//是否填写优秀成果结果
	var showExcellect = function(data){
		if(data == 1){
			$("#excellect_info").show();
		}else{
			$("#excellect_info").hide();
		}
	};

	var showNumber = function(type){
		if(type==2){//填写结项证书编号
			$("#number_info").show();
		}else{
			$("#number_info").hide();
		}
	};
	
	//编辑结项成果
	var editProductType = function(){
		var isApplyNoevaluation = $("input[name='isApplyNoevaluation'][type='radio']:checked").val();
		if (isApplyNoevaluation != 0 && isApplyNoevaluation != 1) {
			alert("请先选择是否申请免鉴定！\n");
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
	var addEndApply = function(data, layer, projectType){
		saveEndApply(data, 1, layer, projectType);
	};
	
	//修改结项申请
	var modifyEndApply = function(data, layer, projectType){
		saveEndApply(data, 2, layer, projectType);
	};

	//保存结项申请结果
	var saveEndApply = function(data, type, layer, projectType) {
		if($("#endStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadline").val());
		if(flag){
			
			var projectid = $("#endFileProjectid").val();
			var bookFee = $("#bookFee").val();
			var bookNote= $("#bookNote").val();
		    var dataFee= $("#dataFee").val();
		    var dataNote= $("#dataNote").val();
		    var travelFee= $("#travelFee").val();
		    var travelNote= $("#travelNote").val();
		    var conferenceFee= $("#conferenceFee").val();
		    var conferenceNote= $("#conferenceNote").val();
		    var internationalFee= $("#internationalFee").val();
		    var internationalNote= $("#internationalNote").val();
		    var deviceFee=$("#deviceFee").val();
		    var deviceNote= $("#deviceNote").val();
		    var consultationFee= $("#consultationFee").val();
		    var consultationNote= $("#consultationNote").val();
		    var laborFee= $("#laborFee").val();
		    var laborNote= $("#laborNote").val();
		    var printFee= $("#printFee").val();
		    var printNote= $("#printNote").val();
		    var indirectFee= $("#indirectFee").val();
	  	    var indirectNote= $("#indirectNote").val();
		    var otherFeeD= $("#otherFeeD").val();
		    var otherNote= $("#otherNote").val();
		    var totalFee= $("#totalFeeD").val();
		    var feeNote = $("#feeNote").val();
		    var surplusFee = $("#surplusFee").val();
		    var fundedFee = $("#fundedFee").val();
			
			if(data == 3) {
			if( !confirm('提交后无法修改且不能再添加中检成果，是否确认提交？'))
				return;
			}
			var $fileIds = $("#fsUploadProgress_end").find("input[name='fileIds']");
			if($fileIds.length == 0){
				$("#uploadEndApply").attr("value",0);
			}else{
				$("#uploadEndApply").attr("value",1);
			}
			$('#endApplicantSubmitStatus').val(data);
			var url = "";
			if(type == 1){
				url = "project/" + projectType + "/endinspection/apply/add.action";
			}else if(type == 2){
				url = "project/" + projectType + "/endinspection/apply/modify.action";
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
		showExcellect($("input[name='isApplyExcellent'][type='radio']:checked").val());
		showNumber($("input[name='endResult'][type='radio']:checked").val());
	};
	
	var isNumerUnique = true;
	//添加或修改结项结果预处理
	var addEndResult = function(data, type, layer, projectType){
		endValid.valid_endResult();
		if(!$("#end_result").valid()){
			return;
		}
		var endResult = $("input[name='endResult'][type='radio']:checked").val();
		var endCertificate = $("#endCertificate").val();
		var endId = $("#endId").val();
		$("#endStatus").val(data);
		if (endResult == 2) {
			valid_number(endCertificate, endId, projectType);
		}
		if(!isNumerUnique){
			return;
		}
		toSubmitEndResult(layer);
	};

	var valid_number = function(endCertificate, endId, projectType){
		var endinspection = "";
		if(projectType == "general"){
			endinspection = "GeneralEndinspection";
		}else if(projectType == "instp"){
			endinspection = "InstpEndinspection";
		}else if(projectType == "post"){
			endinspection = "PostEndinspection";
		}else if(projectType == "key"){
			endinspection = "KeyEndinspection";
		}else if(projectType == "entrust"){
			endinspection = "EntrustEndinspection";
		}
		DWREngine.setAsync(false);
		projectService.isEndNumberUnique(endinspection, endCertificate, endId, isEndNumberUniqueCallback);
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
		var fileIds = $("#fsUploadProgress_end").find("input[name='fileIds']").val();
		var isApplyNoevaluation = $("input[name='isApplyNoevaluation'][type='radio']:checked").val();
		var isApplyExcellent = $("input[name='isApplyExcellent'][type='radio']:checked").val();
		var endResult = $("input[name='endResult'][type='radio']:checked").val();
		var endNoauditResult = 0;
		var endExcellentResult = 0;
		var endCertificate = $("#endCertificate").val();
		var endProductInfo = $("input[name='endProductInfo']").val();
		var endMember = $("input[name='endMember']").val();
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
		if(isApplyNoevaluation == 1){
			endNoauditResult = $("input[name='endNoauditResult'][type='radio']:checked").val()
		}
		if(isApplyExcellent == 1){
			endExcellentResult = $("input[name='endExcellentResult'][type='radio']:checked").val()
		}
		var dis = {
			"modifyFlag":modifyFlag,
			"isApplyNoevaluation":isApplyNoevaluation,
			"isApplyExcellent":isApplyExcellent,
			"endResult":endResult,
			"fileIds":fileIds,
			"type":type,
			"endNoauditResult":endNoauditResult,
			"endExcellentResult":endExcellentResult,
			"endCertificate":endCertificate,
			"endDate":endDate,
			"endProductInfo":endProductInfo,
			"endMember":endMember,
			"endStatus":endStatus,
			"endImportedOpinion":endImportedOpinion,
			"endOpinionFeedback":endOpinionFeedback,
			
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
		  "feeNote" : $("#feeNote").val(),
		  "surplusFee" : $("#surplusFee").val(),
		  "fundedFee" : $("#fundedFee").val()
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
	
	var initUpload = function() {
		$(function() {
			$("#file_add").uploadifyExt({
				uploadLimitExt : 1, //文件上传个数的限制
				fileSizeLimit : '300MB',//文件上传大小的限制
				fileTypeDesc : '附件',//可以不用管
				fileTypeExts : '*.doc; *.docx; *.pdf'
			});
		});
	};
	
	/*-------------------初始化方法结束------------------------*/
	
	
	module.exports = {
		 initUpload: function() {initUpload()},
		 addEndApply: function(data, layer, projectType){addEndApply(data, layer, projectType)},
		 modifyEndApply: function(data, layer, projectType){modifyEndApply(data, layer, projectType)}, 
		 initEndResult: function(){initEndResult()},
		 addEndResult: function(data, type, layer, projectType){addEndResult(data, type, layer, projectType)},
		 uploadFileResult: function(data, type, layer){uploadFileResult(data, type, layer)},
		 editProductType: function(){editProductType()}, 
		 showNoEval: function(data){showNoEval(data)},
		 showExcellect: function(data){showExcellect(data)},
		 showNumber: function(type){showNumber(type)},
		 datepickInit: function(){datepickInit()}
	};
});









