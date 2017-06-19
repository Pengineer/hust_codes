define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/person/validate');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	function selectDI(){
		$here = $(this).parent();
		var unitType = $("[name=unitType]").val();
		if (unitType == 0){
			popSelect({
				type : 4,
				inData : {"type" : -1, "id" : $("[name=departmentId]", $here).val(), "name" : $("[id=unitName]", $here).html()},
				callBack : function(result){
					$("[name=departmentId]", $here).val(result.data.id);
					$("[id=unitName]", $here).html(result.data.name);
					$("[name=instituteId]", $here).val("");
					$(document.forms[0]).valid();
				}
			});
		} else if (unitType == 1){
			popSelect({
				type : 5,
				inData : {"type" : -1, "id" : $("[name=instituteId]",$here).val(), "name" : $("[id=unitName]", $here).html()},
				callBack : function(result){
					$("[name=instituteId]", $here).val(result.data.id);
					$("[id=unitName]", $here).html(result.data.name);
					$("[name=departmentId]", $here).val("");
					$(document.forms[0]).valid();
				}
			});
		} else {
			alert("请先选择单位类型！");
		}
	};
	
	var setting = new Setting({

		id: "student",

		out_check: function(){
			return $("#form_person").valid();
		},

		on_in_opt: function(){
			validate.valid_student();
			$(":input[name='unitType']").live('change', function(){ 
				$("#form_person_unitType").rules('remove');
				if($(this).val() == 0){
					$("#form_person_departmentId").rules('add', {required:true});
					$("#form_person_instituteId").rules('remove');
				} else if($(this).val() == 1){
					$("#form_person_instituteId").rules('add', {required:true});
					$("#form_person_departmentId").rules('remove');
				} else {
					$("#form_person_departmentId").rules('remove');
					$("#form_person_instituteId").rules('remove');
				}
			})
		}

	});
	
	var init = function(){
		$("#select_unitName_btn").click(selectDI);

		$("#select_tutorName_btn").click(function(){
			popSelect({
				type : 8,
				inData : {"id" : $("[name='student.tutor.id']").val(), "name" : $("#tutorName").html()},
				callBack : function(result){
					$("[name='student.tutor.id']").val(result.data.id);
					$("#tutorName").html(result.data.name);
					$(document.forms[0]).valid();
				}
			});
		});
	};
	
	module.exports = {
		setting : setting,
		init : function(){init()}
	};
});
