/**
 * 用于添加、修改中检申请及中检结果
 * @author 余潜玉
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	require('uploadify');
	require('uploadify-ext');
	require('form');
	var datepick = require("datepick-init");
	var timeValidate = require('javascript/project/project_share/validate');
	
	//添加中检申请
	var addMidApply = function(data, layer, projectType){
		saveMidApply(data, 1, layer,projectType);
	};
	
	//修改中检申请
	var modifyMidApply = function(data, layer, projectType){
		saveMidApply(data, 2, layer, projectType);
	};
	
	//保存中检申请结果
	var saveMidApply = function(data, type, layer, projectType) {
		if($("#midStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadline").val());
		if(flag){
			
			var projectid = $("#midFileProjectid").val();
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
			$('#midApplicantSubmitStatus').val(data);
			var url = "";
			if(type == 1){
				url = "project/" + projectType + "/midinspection/apply/add.action";
			}else if(type == 2){
				url = "project/" + projectType + "/midinspection/apply/modify.action";
			}
			$("#midFile").attr("action", url);
			$("#midFile").submit();
		}else{
			return false;
		}
	};
	
	//添加或修改中检结果预处理
	var addMidResult = function(data, type, layer){
		if(!$("#mid_result").valid()){
			return;
		}
		if(data == 3){
			if (!confirm("提交后不能修改，是否确认提交?")) {
				return;
			}
		}
		var fileIds = $("input[name='fileIds']:eq(0)").val();
		var midResult=$("input[name='midResult'][type='radio']:checked").val();
		var midDate = $("[name='midDate']:eq(0)").val();
		var midImportedOpinion = $("[name='midImportedOpinion']:eq(0)").val();
		var midOpinionFeedback = $("[name='midOpinionFeedback']:eq(0)").val();
		dis = {
			"type" : type,
			"fileIds" : fileIds,
			"midResult" : midResult,
			"midStatus" : data,
			"midDate" : midDate,
			"midImportedOpinion" : midImportedOpinion,
			"midOpinionFeedback" : midOpinionFeedback,
			
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
	$("#auditOption").change(function(){
		var innerText = $("textarea[name='midOpinionFeedback']").val();
		if($(":selected",this).text() != "自定义"){
			if(innerText){
				innerText += ("；"+ $("#auditOption option:checked").html());
			} else {
				innerText = $("#auditOption option:checked").html();
			}
			
		} else {
			if(innerText){
				innerText += "；";
			} 					
		}
		innerText = innerText.replace(/;/g,"；");//将英文";"换为中文"；"
		innerText = innerText.replace(/^；{1,}/g,"");//删除字符窜开头的"；"		
		innerText = innerText.replace(/；{1,}/g,"；");//删掉重复的"；"
		$("textarea[name='midOpinionFeedback']").val(innerText.trim());

	});
	//上传中检申请书
	var uploadFileResult = function(data, type, layer){
		var midFileId = $("#fsUploadProgress_end").find("input[name='fileIds']").val();
		var midId = $("#midId").val();
		var dis = {
			"midFileId":midFileId,
			"midId":midId
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
		addMidApply: function(data, layer, projectType){addMidApply(data, layer, projectType)},
		modifyMidApply: function(data, layer, projectType){modifyMidApply(data, layer, projectType)}, 
		addMidResult: function(data, type, layer){addMidResult(data, type, layer)},
		uploadFileResult: function(data, type, layer){uploadFileResult(data, type, layer)},
		datepickInit: function(){datepickInit()}// 初始化日期选择器
	};
});









