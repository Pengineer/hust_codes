define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/dataProcessing/fromSourceToMid/validate');
	var nameSpace  = "dataProcessing/";
	var optionData = {};
	var setting = new Setting({

		id: "basic1",
	
		out_check: function(){
			return $("#form_createtask").valid();
		},

		on_in_opt: function(){
		}
	});
	
	var init = function(){
		$.ajax({
			url: nameSpace + "gainSourceNameAndTypeName.action",
			type: "post",
			dataType: "json",
			success: function(result){
				result = result.sourceNameAndTypeNamelMap;
				optionData = result;
				var firstTypeName;
				for(var sourceName in result){
					if(!firstTypeName) firstTypeName = result[sourceName];
					var sourceNameOption = "<option value='" + sourceName + "'>" + sourceName + "</option>";
					$("#sourceName").append(sourceNameOption);
				}
				for(var i = 0; i < firstTypeName.length; i ++) {
					var typeNameOption = "<option value = '" + firstTypeName[i] + "'>" + firstTypeName[i] + "</option>";
					$("#typeName").append(typeNameOption);
				}
			}
		});
		$("#sourceName").change(function(){
			$("#typeName").empty();
			var typeName = optionData[$(this).val()];
			for(var i = 0; i < typeName.length; i ++) {
				var typeNameOption = "<option value = '" + typeName[i] + "'>" + typeName[i] + "</option>";
				$("#typeName").append(typeNameOption);
			}
		});
	};
	
	module.exports = {
		setting : setting,
		init : function(){init()}
	};
});