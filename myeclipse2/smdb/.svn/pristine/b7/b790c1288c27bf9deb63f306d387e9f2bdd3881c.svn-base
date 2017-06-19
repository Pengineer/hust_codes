define(function(require, exports, module) {
	var nameSpace = "system/interfaces/sinossClient";
	require('validate');
	require('javascript/template_tool');
	require('form');
	var getData;

	var showData = function() {
		$.get("system/interfaces/sinossClient/getCurrentObtain.action", function(data){
			if (data.result != null) {
				clearInterval(getData);
				alert("数据获取结束");
				$("#showInfo").hide();
			}
//			$("#view_task").html(TrimPath.processDOMTemplate("view_task", data));
//			$("#view_task").css("visibility", "visible");
			Template_tool.populate(data);
		});
	}
	
	exports.init = function() {
		Template_tool.init();
		$("#methodName").bind("change", function() {
			if($("#methodName").val() == "getApplyProject") {
				$("#tr_year, #tr_count").show();
			} else if ($("#methodName").val() == "getModifyRecord") {
				$("#tr_year, #tr_count").hide();
			}
		});
		$("#submit").live("click", function() {
			if(!confirm("是否确定同步数据？")) {
				return false;
			} else {
				$("#showInfo").show();
				$.get(nameSpace + "/obtain.action?projectType=" + $("#search_projectType").find("option:selected").val()
						 + "&methodName=" + $("#methodName").find("option:selected").val()
						 + "&year=" + $("#year").find("option:selected").val() + "&count=" + $("#count").val());
				getData = setInterval(showData, 30000);
			}
		});
	};

	exports.valid = function() {
		$("#form_obtain").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"count":{required:true, number:true, min:0}
			},
			errorPlacement: function(error, element) { 
				error.appendTo(element.parent("td").next("td"));
			}
		});
	};
});
