define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	var datepick = require("datepick-init");
	var topsValid = require('javascript/project/key/topicSelection/validate');
	
	var flag = 99999;
	
	var init = function(){
		$(function() {
			datepick.init();
			
			if($("[name$='topicSource']").val() == 1){
				$(".university").show();
				$(".applicant_valid").hide();
			}else if($("[name$='topicSource']").val() == 2){
				$(".applicant_valid").show();
				$(".university").hide();
			}else{
				$(".university").hide();
				$(".applicant_valid").hide();
			};
			
			$("#finish").click(function(){
				var val = $("#topicSelection_form").valid();
				if(val){
					$("#topicSelection_form").submit();
				}
			});
			
			//选择选题来源
			$("[name$='topicSource']").live("change", function(){
				if($(this).val() == 1){//高校
					$(".university").show();
					$(".applicant_valid").hide();
					topsValid.validate_moe();
				}else if($(this).val() == 2){//专家
					$(".applicant_valid").show();
					$(".university").hide();
					if($("#editFlag").val() == 2){//录入
						topsValid.validate_moe();
					}
				}else{
					$(".university").hide();
					$(".applicant_valid").hide();
				}
			});
			
			//弹出层选择成员所在高校
			$("[name='selectUniversity']").live("click", function(){
				popSelect({
					type : 3,
					label : 2,
					title : "选择高校",
					inData : {
						"id" : $("[name='topicSelection.university.id']").val(),
						"name" : $("[name='topicSelection.university.name']").val()
					},
					callBack : function(result){
						$("[name='topicSelection.university.id']").val(result.data.id);
						$("[name='topicSelection.university.name']").val(result.data.name);
						$("[name='universityNameDiv']").html(result.data.name);
					}
				});
			});
			
			//选择选题所属项目
			$(".select_project_btn").live("click", function(){
				$(this).parent("td").next("td").html("");
				var projectTypeFlag = $("#projectTypeFlag").val();
				var universityId = $("[name='topicSelection.university.id']").val();
				if(universityId == "" || universityId == "undefined"){
					alert("请先选择高校！"); return false;
				}
				if(projectTypeFlag == -1) {
					alert("请先选择项目类型！"); return false;
				}
				popSelect({
					type : 13,
					inData : {universityId: universityId, update : 1}, 
					proType : projectTypeFlag,
					callBack : function(result){
						$("#projectId").val(result.data.id);
						$("#project_div").html(result.data.name);
					}
				});
			});
			
			//选择申请人类型
			$("[name*='applicantType']").live("change", function(){
				$("[name='topicSelection.applicantId']").val('');
				$("[name='topicSelection.applicantName']").val('');
				$("[name='applicantNameDiv']").html('');
				$("[name='applicantUnitDiv']").html('');
			});
			
			//选择选题申请人
			$("[name='selectApplicant']").live("click", function(){
				var appType=$("[name='topicSelection.applicantType']").val();
				if(appType == '-1'){
					alert("请选择申请者类型");
					return false;
				}
				var type = {'1' : 8, '2' : 7};//教师|专家|学生
				popSelect({
					type : type[appType],
					inData : {
						"id" : $("[name='topicSelection.applicantId']").val(), 
						"name" : $("[name='topicSelection.applicantName']").val()
					},
					callBack : function(result){
						$("[name='topicSelection.applicantId']").val(result.data.id);
						$("[name='topicSelection.applicantName']").val(result.data.name);
						$("[name='topicSelection.applicantName']").valid();
						$("[name='applicantNameDiv']").html(result.data.name);
						$("[name='applicantUnitDiv']").html(result.data.sname);
					}
				});
			});
		});
	};
	
	exports.init = function() {
		init();
	};
	
});
