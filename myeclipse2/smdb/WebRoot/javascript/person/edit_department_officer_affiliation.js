define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/person/validate');
	require('tool/poplayer/js/pop');
	require('pop-self');

	var setting = new Setting({

		id: "department_officer_affiliation",

		out_check: function(){
			if(!$("#form_person").valid()){return false;}
			
			if($(":input[name='officer.type']").val().trim() == "专职人员"){
				if(!validate.checkOfficerType()){return false};
			}
			return true;
		},

		on_in_opt: function(){
			validate.valid_department_officer_affiliation();
		}
	});
	
	var init = function(){
		// 子页面初始化接口
		$("#select_department_btn").click(function(){
			popSelect({
				type : 4,
				inData : {"id" : $("#departmentId").val(), "name" : $("#departmentName").html()},
				callBack : function(result){
					$("#departmentId").val(result.data.id);
					$("#departmentName").html(result.data.name);
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
