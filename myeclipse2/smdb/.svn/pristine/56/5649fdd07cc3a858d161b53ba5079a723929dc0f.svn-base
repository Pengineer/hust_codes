define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/personService');
	
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/person/validate');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	function selectDI(){
		$here = $(this).parent();
		var unitType = $("[name^=unitType]").val();
		if (unitType == 0){
			popSelect({
				type : 4,
				inData : {"id" :  $("[name$=department.id]", $here).val(), "name" : $("[id=unitName]", $here).html()},
				callBack : function(result){
					$("[name$=department.id]", $here).val(result.data.id);
					$("[id=unitName]", $here).html(result.data.name);
					$("[name$=institute.id]", $here).val("");
					$(document.forms[0]).valid();
				}
			});
		} else if (unitType == 1){
			popSelect({
				type : 5,
				inData : {"id" : $("[name$=institute.id]", $here).val(), "name" : $("[id=unitName]", $here).html()},
				callBack : function(result){
					$("[name$=institute.id]", $here).val(result.data.id);
					$("[id=unitName]", $here).html(result.data.name);
					$("[name$=department.id]", $here).val("");
					$(document.forms[0]).valid();
				}
			});
		} else {
			alert("请先选择单位类型！");
		}
	}
	
	function call_back_checkIfIsFulltimeTeacher(_result){
		result = _result;
	}
	
	var setting = new Setting({

		id: "teacher_affiliation_add",

		on_in_opt: function(){
			validate.valid_add_teacher_affiliation();
			$(":input[name='unitType']").live('change', function(){ 
				if($(this).val() == 0){
					$("#form_person_teacher_department_id").rules('add', {required:true});
					$("#form_person_teacher_institute_id").rules('remove');
				} else if($(this).val() == 1){
					$("#form_person_teacher_institute_id").rules('add', {required:true});
					$("#form_person_teacher_department_id").rules('remove');
				} else {
					$("#form_person_teacher_department_id").rules('remove');
					$("#form_person_teacher_institute_id").rules('remove');
				}
			})
		},

		out_check: function(){
			if($("#form_person").valid() == false){return false;}
			var curType = $("[name=teacher.type]").val();
			if (curType == '专职人员'){
				var idcardNumber = $("[name='person.idcardNumber']").val();
				DWREngine.setAsync(false);
				personService.checkIfIsFulltimeTeacher(idcardNumber, call_back_checkIfIsFulltimeTeacher);
				DWREngine.setAsync(true);
				if (result != null){
					alert("最多只能有一个专职教师职位!此人已是专职教师!");
					return false;
				}
			}
			return true;
		}

	});
	
	var init = function(){
		$("#select_unitName_btn").click(selectDI);
	};
	
	module.exports = {
		setting : setting,
		init : function(){init()}
	};
});

