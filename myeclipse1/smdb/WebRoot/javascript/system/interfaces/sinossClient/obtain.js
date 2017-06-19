define(function(require, exports, module) {
	var nameSpace = "system/interfaces/sinossClient";
	require('validate');
	require('javascript/template_tool');
	require('form');
	var getData;

	var showData = function() {
		$.get("system/interfaces/sinossClient/getCurrentObtain.action", function(data){
			if(data.isLinked == false){
				alert("连接服务器失败，请与社科网联系！");
				clearInterval(getData);
				$("#showInfo").hide();
			} else {
				if (data.result != null) {
					clearInterval(getData);
					alert("数据获取结束");
					$("#showInfo").hide();
				}
				Template_tool.populate(data);
			}
		});
	}
	
	exports.init = function() {
		Template_tool.init();
		$("#methodName").bind("change", function() {
			if($("#methodName").val() == "getApplyProject") {
//				$("#tr_year, #tr_count").show();
				$("#search_projectType").append('<option value="special">专项任务</option>');
			} else  {
//				$("#tr_year, #tr_count").hide();
				$("#search_projectType option[value='special']").remove();
			}
		});
		$("#submit").live("click", function() {
			if(!confirm("是否确定同步数据？")) {
				return false;
			} else {
				$("#showInfo").show();
				$.get(nameSpace + "/obtain.action?projectType=" + $("#search_projectType").find("option:selected").val()
						 + "&methodName=" + $("#methodName").find("option:selected").val());
				getData = setInterval(showData, 10000);
			}
		});
		
		
	};
});
