/**
 * 用于添加、修改年检申请及年检结果
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('dwr/util');
	require('javascript/engine');
	require('dwr/interface/projectService');
	require('pop-init');
	require('uploadify');
	require('uploadify-ext');
	require('form');
	var datepick = require("datepick-init");
	var timeValidate = require('javascript/project/project_share/validate');
	
	//添加年检申请
	var addAnnApply = function(data, layer, projectType){
		saveAnnApply(data, 1, layer,projectType);
	};
	
	//修改年检申请
	var modifyAnnApply = function(data, layer, projectType){
		saveAnnApply(data, 2, layer, projectType);
	};
	
	//保存年检申请结果
	var saveAnnApply = function(data, type, layer, projectType) {
		if($("#annStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadline").val());
		if(flag){
			if(!$("#annFile").valid()){
				return;
			}
			var projectid = $("#annFileProjectid").val();
			var annId = $("#annId").val();
			var annYear=$("input[name='annYear']:eq(0)").val();
			
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
			
			valid_year1(annYear, annId, projectid, projectType);
			if(!isAuditReport){
				return;
			}
			valid_year2(annYear, projectid);
			if(!isEarlyGranted){
				return;
			}
			if(data == 3) {
				if( !confirm('提交后无法修改且不能再添加年检成果，是否确认提交？'))
					return;
			}
			$('#annApplicantSubmitStatus').val(data);
			var url = "";
			if(type == 1){
				url = "project/" + projectType + "/anninspection/apply/add.action";
			}else if(type == 2){
				url = "project/" + projectType + "/anninspection/apply/modify.action";
			}
			$("#annFile").attr("action", url);
			$("#annFile").submit();
		}else{
			return false;
		}
	};
	
	//添加或修改年检结果预处理
	var addAnnResult = function(data, type, layer, projectType){
		if(!$("#ann_result").valid()){
			return;
		}
		if(data == 3){
			if (!confirm("提交后不能修改，是否确认提交?")) {
				return;
			}
		}
		var fileIds = $("input[name='fileIds']:eq(0)").val();
		var projectid = $("#projectid").val();
		var annId = $("#annId").val();
		var annResult=$("input[name='annResult'][type='radio']:checked").val();
		var annYear=$("input[name='annYear']:eq(0)").val();
		var annDate = $("[name='annDate']:eq(0)").val();
		var annImportedOpinion = $("[name='annImportedOpinion']:eq(0)").val();
		var annOpinionFeedback = $("[name='annOpinionFeedback']:eq(0)").val();
		
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
		
		valid_year1(annYear, annId, projectid, projectType);
		if(!isAuditReport){
			return;
		}
		valid_year2(annYear, projectid, projectType);
		if(!isEarlyGranted){
			return;
		}
		dis = {
				"type" : type,
				"fileIds" : fileIds,
				"annResult" : annResult,
				"annYear" : annYear,
				"annStatus" : data,
				"annDate" : annDate,
				"annImportedOpinion" : annImportedOpinion,
				"annOpinionFeedback" : annOpinionFeedback,
				
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
				
		}
		layer.callBack(dis);
		layer.destroy();
	};
	
	var valid_year1 = function(annYear, annId, projectid, projectType){
		DWREngine.setAsync(false);
		var projectAnninspection = "";
		if(projectType == "general"){
			projectAnninspection = "GeneralAnninspection";
		}else if(projectType == "instp"){
			projectAnninspection = "InstpAnninspection";
		}else if(projectType == "post"){
			projectAnninspection = "PostAnninspection";
		}else if(projectType == "key"){
			projectAnninspection = "KeyAnninspection";
		}
		projectService.isAuditReport(projectAnninspection, annYear, annId, projectid, isAuditReportCallback);
		DWREngine.setAsync(true);
	};
	
	var isAuditReportCallback = function(data){
		var $annYear = $("#annYear");
		var $errorTd = $annYear.parent("td").next("td");
		if(!data){
			$errorTd.html('<span class="error">该年度的年检已审批</span>');// 写错误信息
			isAuditReport = false;
		}else{
			$errorTd.html('');// 清错误信息
				isAuditReport = true;;
		}
	};

	var valid_year2 = function(annYear, projectid){
		DWREngine.setAsync(false);
		projectService.isEarlyGranted(annYear, projectid, isEarlyGrantedCallback);
		DWREngine.setAsync(true);
	};
	
	var isEarlyGrantedCallback = function(data){
		var $annYear = $("#annYear");
		var $errorTd = $annYear.parent("td").next("td");
		if(!data){
			$errorTd.html('<span class="error">年检年度不能早于项目年度</span>');// 写错误信息
			isEarlyGranted = false;
		}else{
			$errorTd.html('');// 清错误信息
			isEarlyGranted = true;;
		}
	};

	//上传年检申请书
	var uploadFileResult = function(data, type, layer){
		var annFileId = $("#fsUploadProgress_end").find("input[name='fileIds']").val();
		var annId = $("#annId").val();
		var dis = {
			"annFileId":annFileId,
			"annId":annId
		};
		layer.callBack(dis, type);
		layer.destroy();
	};
	
	var datepickInit = function(){
		datepick.init();
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
	
	module.exports = {
		initUpload: function() {initUpload()},
		addAnnApply: function(data, layer, projectType){addAnnApply(data, layer, projectType)},
		modifyAnnApply: function(data, layer, projectType){modifyAnnApply(data, layer, projectType)}, 
		addAnnResult: function(data, type, layer, projectType){addAnnResult(data, type, layer, projectType)},
		uploadFileResult: function(data, type, layer){uploadFileResult(data, type, layer)},
		datepickInit: function(){datepickInit()}// 初始化日期选择器
	};
});









