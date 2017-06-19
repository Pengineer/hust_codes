define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/person/validate');
	require('tool/poplayer/js/pop');
	require('pop-self');

	var setting = new Setting({

		id: "institute_officer_affiliation",

		out_check: function(){
			if(!$("#form_person").valid()){return false;}
			
			if($(":input[name='officer.type']").val().trim() == "专职人员"){
				if(!validate.checkOfficerType()){return false};
			}
			return true;
		},

		on_in_opt: function(){
			validate.valid_institute_officer_affiliation();
		}
	});
	
	var init = function(){
		// 子页面初始化接口
		$("#select_instituteName_btn").click(function(){
			popSelect({
				type : 5,
				inData : {"id" : $("#instituteId").val(), "name" : $("#instituteName").html()},
				callBack : function(result){
					$("#instituteId").val(result.data.id);
					$("#instituteName").html(result.data.name);
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
