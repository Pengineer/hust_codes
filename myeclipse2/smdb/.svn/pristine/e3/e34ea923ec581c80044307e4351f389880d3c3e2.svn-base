define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	var datepick = require("datepick-init");
//	var topsValid = require('javascript/project/key/topicSelection/validate');
	var timeValidate = require('javascript/project/project_share/validate');
	
	var flag = 99999;
	
	var init = function(){
		$(function() {
			datepick.init();
			
			//弹出层选择选题依托所在高校
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
					inData : {universityId: universityId, "update" : 1}, 
					proType : projectTypeFlag,
					callBack : function(result){
						$("#projectId").val(result.data.id);
						$("#project_div").html(result.data.name);
					}
				});
			});
		});
	};
	
	//添加选题申报
	var addTopsApply = function(data){
		saveTopsApply(data, 1);
	};
	
	//修改选题申报
	var modifyTopsApply = function(data){
		saveTopsApply(data, 2);
	};
	
	//保存选题申请结果
	var saveTopsApply = function(data, type) {
		if($("#topsStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadline").val());
		if(flag){
			if(data == 3) {
			if( !confirm('提交后无法修改，是否确认提交？'))
				return;
			}
			var appFlag = $("#appFlag").val();
			if(appFlag == 1){
				$('#topsApplicantSubmitStatus').val(data);
			}else if(appFlag == 2){
				$('#topsUniversitySubmitStatus').val(data);
			}else{
				return;
			}
			var url = "";
			if(type == 1){
				url = "project/key/topicSelection/apply/add.action";
			}else if(type == 2){
				url = "project/key/topicSelection/apply/modify.action";
			}
			$("#topicSelection_form").attr("action", url);
			$("#topicSelection_form").submit();
		}else{
			return false;
		}
	};
	
	exports.init = function() {
		init();
		window.addTopsApply = function(data){addTopsApply(data)};
		window.modifyTopsApply = function(data){modifyTopsApply(data)};
	};
});
