// ========================================================================
// 文件名：validate.js
//
// 文件说明：
//     本文件主要实现重大攻关项目模块中页面的输入验证，包括添加、修改。
//
// 历史记录：
// 2012-06-01  肖雅     创建文件，给添加、修改绑定校验。
// ========================================================================

//编辑标志位 1：走流程	2：录入
define(function(require, exports, module) {
	require('validate');

	var editFlag;
	var accountType;

	var valid = function(){
		$(function() {
			$("#topicSelection_form").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"topicSelection.name":{required:true, maxlength:50},
					"topicSelection.englishName":{maxlength:50},
					"topicSelection.summary":{maxlength:300},
					"topsDate":{dateISO:true}
				},
				errorPlacement: function(error, element) { 
					if(element.attr("name") == 'topicSelection.university.id') {
						error.appendTo( element.parent("div").parent("td").next("td") ); 
					} else {
						error.appendTo( element.parent("td").next("td") ); 
					}
				}
			});
			editFlag = $("#editFlag").val();
			accountType = $("#accountType").val();
			initValid();
		});
	};
	
	//选题来源类型
	var validate_moe = function(){
		if ($("#editFlag").val() == 2){//校验选题类型
			$(":input[name='topicSelection.topicSource']").rules("add", {selected:true});
			$(":input[name='topsResult']").rules("add", {required:true});
			$("input[name='topicSelection.year']").rules("add", {required:true, year:true});
		} else {
			$(":input[name='topicSelection.topicSource']").rules("remove");
			$(":input[name='topsResult']").rules("remove");
		}
		if($("[name$='topicSource']").val() == 1){//校验选题来源为高校
			$(":input[name='topicSelection.university.id']").rules("add", {required:true});
			$(":input[name='projectId']").rules("add", {required:true});
		}else{
			$(":input[name='topicSelection.university.id']").rules("remove");
			$(":input[name='projectId']").rules("remove");
		}
		if ($("[name$='topicSource']").val() == 2){//校验选题来源为专家
			$(":input[name='topicSelection.applicantType']", $(".applicant_valid")).rules("add", {selected:true});
			$(":input[name='topicSelection.applicantId']", $(".applicant_valid")).rules("add", {required:true});
		} else {
			$(":input[name='topicSelection.applicantType']", $(".applicant_valid")).rules("remove");
			$(":input[name='topicSelection.applicantId']", $(".applicant_valid")).rules("remove");
		}
	};

	//选题来源类型
	var validate_school = function(){
		$(":input[name='topicSelection.university.id']").rules("add", {required:true});
		$(":input[name='projectId']").rules("add", {required:true});
	};

	var initValid = function (){
		if(editFlag == 2){//教育部直接录入
			validate_moe();
		}else{
			if(!(accountType == "EXPERT" || accountType == "TEACHER" || accountType == "STUDENT")){
				validate_school();//高校申请
			}
		}
	};
	
	exports.valid = function() {
		valid();
	};
	exports.validate_moe = function() {
		validate_moe();
	};

});