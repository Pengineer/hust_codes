define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/person/validate');
	require('tool/poplayer/js/pop');
	require('pop-self');

	var setting = new Setting({

		id: "university_officer_affiliation",

		out_check: function(){
			if(!$("#form_person").valid()){return false;}
			if($(":input[name='officer.type']").val().trim() == "专职人员"){
				if(!validate.checkOfficerType()){return false};
			}
			return true;
		},

		on_in_opt: function(){
			validate.valid_officer_affiliation();
		}

	});
	
	var init = function(){
		$("#select_unitName_btn").click(function(){
			popSelect({
				type : 3,
				inData : {"id" : $("#unitId").val(), "name" : $("#unitName").html()},
				callBack : function(result){
					$("#unitId").val(result.data.id);
					$("#unitName").html(result.data.name);
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
