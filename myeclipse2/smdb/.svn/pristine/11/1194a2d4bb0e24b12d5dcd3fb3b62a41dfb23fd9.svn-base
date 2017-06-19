/**
 * 用于录入申报评审的校验
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('validate');
	
	var accountType = $("#accountType").val();
	var attrRules = {
		"review_moe":[
			[".reviewerType", {selected:true}],
			[".reviewer.id", {required:true, maxlength:50}]
		],
		"review_same":[
			[".innovationScore", {required: true, number: true, range:[0,50]}],
			[".scientificScore", {required: true, number: true, range:[0,25]}],
			[".benefitScore", {required: true, number: true, range:[0,25]}],
			[".qualitativeOpinion", {selected:true}],
			[".opinion", {maxlength:2000}]
		],
		"review_group":[
			["reviewWay", {required:true}],
			["reviewResult", {required:true}],
			["reviewOpinionQualitative", {selected:true}],
			["reviewOpinion", {maxlength: 2000}]
		]
	};
	
	var valid = function(){
		$(function() {
			$("#review_appform").validate({
				errorElement: "span",
				event: "change",
				rules:{
					
					},
				errorPlacement: function(error, element) { 
					error.appendTo( element.parents("td").next("td") );
				}
			});
			valid_review();
			valid_review_group();
		});
	};
	
	var valid_review = function(){
		//各专家信息校验
		$("#review .table_valid").each(function(key1, value1){
			//专家评审信息
			if(accountType == "MINISTRY"){//教育部录入
				for(i = 0; i < attrRules.review_moe.length; i++){
					$("':input[name*=" + attrRules.review_moe[i][0] + "]'", $(this)).rules("add", attrRules.review_moe[i][1]);
				}
			}
			for(i = 0; i < attrRules.review_same.length; i++){
				$("':input[name*=" + attrRules.review_same[i][0] + "]'", $(this)).rules("add", attrRules.review_same[i][1]);
			}
			//总评信息校验
			for(var j = 0; j < attrRules.review_group.length; j++){
				$("':input[name=" + attrRules.review_group[j][0] + "]'").rules("add", attrRules.review_group[j][1] );
			}
		});
	};
	
	var valid_review_group = function(){
		//总评信息校验
		for(var j = 0; j < attrRules.review_group.length; j++){
			$("':input[name=" + attrRules.review_group[j][0] + "]'").rules("add", attrRules.review_group[j][1] );
		}
	};
	
	exports.valid = function() {
		valid();
	};
	exports.valid_review = function() {
		valid_review();
	};
	exports.valid_review_group = function() {
		valid_review_group();
	};
});