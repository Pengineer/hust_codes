define(function(require, exports, module) {
	
	exports.init = function() {
		
		$("#submit").live("click", function(){
			if(!confirm("是否确定导出？")){
				return false;
			}
		});
		$("#startYear, #endYear").change(function(){
			if($("#startYear").val() > $("#endYear").val()) {
				$("#endYear").val($("#startYear").val());
			}
		});
		
		$("#submit").click(function(){
			var exportInterface = $("input[type='radio'][name='export_interface']:checked");
			if(exportInterface.length == 0){alert("导出接口不能为空！"); return false;}
			var exportProjectType = $("input[type='checkbox'][name='export_projectType']:checked");
			if(exportProjectType.length == 0){alert("项目类型不能为空！"); return false;}
		});
	};
});