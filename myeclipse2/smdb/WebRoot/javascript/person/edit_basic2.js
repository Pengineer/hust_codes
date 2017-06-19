define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/personService');
	require('uploadify');
	require('tool/uploadify/js/jquery.uploadify-photo');
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/person/validate');

	function call_back_checkIdcard(_result){
		result = _result;
	}
	
	var setting = new Setting({

		id: "basic2",

		on_in_opt: function(){
			$("#identifier2").hide();
			$("#identifier1").show();
			validate.valid_basic2();
		},
		
		on_out_opt: function(){
			var idcardType_val = $("[name='person.idcardType']").val();
			var name = $("[name='person.name']").val();
			var idcardNumber = $("[name='person.idcardNumber']").val();

			$("#person_name").html(name);
			$("#person_idcardType").html(idcardType_val);
			$("#person_idcardNumber").html(idcardNumber);
			
			$("#identifier2").show();
			$("#identifier1").hide();
		},

		out_check: function(){
			if($("#form_person").valid() == false){return false;}

			var len = $(".uploadify-queue").children().length;
			if(len>1){
				alert("不能上传多张照片");
				return false;
			}
			
			var idcardNumber = $("[name='person.idcardNumber']").val();
			if (original_idcardNumber != idcardNumber){
				DWREngine.setAsync(false);
				personService.checkIdcard(idcardNumber, call_back_checkIdcard);
				DWREngine.setAsync(false);
				if (result == true){
					alert("此证件号已分配专职人员，请在任职信息中选择兼职人员!");
				}
			}
			return true;
		}

	});
	
	var init = function(){
		// 子页面初始化接口
		$(function() {
			var personId = $("#personId").val();
			$("#photo_" + personId).uploadifyExt({
				uploadLimitExt : 1,
				fileSizeLimit : '3MB',
				fileTypeExts : '*.gif; *.jpg; *.png',
				fileTypeDesc : '图片'
			});
		});
	};
	
	module.exports = {
		setting : setting,
		init : function(){init()}
	};
});
