// ========================================================================
// 文件名：validate.js
//
// 文件说明：添加修改项目变更的前台校验
//
// 历史记录：
// 2010-12-19  周中坚    添加文件
// 2012-9-7    肖雅         修改文件
// ========================================================================
define(function(require, exports, module) {
	require('validate');
	
	var valid = function(){
		$(function() {
			$("#endFile").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"end_file":{required:function(){return($(".uploadify-queue-item").size()==0);}},
					"isApplyNoevaluation":{required: true},
					"isApplyExcellent":{required: true},
					"note":{maxlength: 200},
					"research_file":{required: function(){
						var $fileIds = $("#fsUploadProgress_end_data").find("input[name='fileIds']");
						if($fileIds.length == 0 && $("#end_flag").val()==0){
							return true;
						}else{
							return false;
						}
					}},
					"projectData.keywords":{required: true,multipleNames:true,maxlength: 100},
					"projectData.summary":{required: true,maxlength: 200},
					"projectData.introduction":{required: true,maxlength: 20000},
					"projectData.surveyMethod":{maxlength: 50},
					"projectData.surveyField":{maxlength: 50},
					"projectData.startDate":{dateISO: true},
					"projectData.endDate":{dateISO: true},
					"projectData.note":{maxlength: 200}
				},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
			$("#end_result").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"end_file":{required:function(){return($(".uploadify-queue-item").size()==0);}},
				},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
			valid_endResult();
		});
	};
	
	var attrRules = {
		"endResult":[
     		["endCertificate", {maxlength:40, required: true}],
     		["endResult", {required: true}],
     		["endDate", {dateISO:true, required: true}],
     		["endImportedOpinion", {maxlength:200}],
     		["endOpinionFeedback", {maxlength:200}]
     	]
	};

	var valid_endResult = function(){
		var $form = $("#end_result");
		if($form!=undefined && $form!=null && $form.length>0){
			for(i = 0; i < attrRules.endResult.length; i++){
				if($("':input[name=" + attrRules.endResult[i][0] + "]'").length != 0){
					$("':input[name=" + attrRules.endResult[i][0] + "]'").rules("add", attrRules.endResult[i][1] );
				}
			}
			var $radioExcel = $("[name='isApplyExcellent']");
			var $radioEnd = $("[name='endResult']");
			var $isCheckEnd = $radioEnd.filter('[value="2"]');
			
			if($radioExcel.val() == 1){//添加校验优秀成果结果
				$("':input[name*=endExcellentResult]'").rules("add", {required: true});
			}
			if($isCheckEnd.attr("checked") == false){//不需校验结项证书编号
				$("':input[name*=" + attrRules.endResult[0][0] + "]'").rules("remove");
			}
		}
	};
	
	exports.valid = function() {
		valid();
	};
	exports.valid_endResult = function() {
		valid_endResult();
	};
});