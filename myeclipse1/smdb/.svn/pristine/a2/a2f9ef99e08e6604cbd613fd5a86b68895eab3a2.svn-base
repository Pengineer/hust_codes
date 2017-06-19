define(function(require, exports, module) {
	require('validate');
	require('javascript/template_tool');
	require('form');
	var getData;
	
	var showData = function() {
		$.get("system/interfaces/sinossClient/getCurrentAttachment.action", function(data){
			if (data.totalAttachment !=0 && (data.finishedAttachment == data.totalAttachment)) {
				clearInterval(getData);
				alert("附件下载完成");
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
				$("#search_projectType").append('<option value="special">专项任务</option>');
			} else  {
				$("#search_projectType option[value='special']").remove();
			}
		});
		$("#submit").live("click", function(){
			if(!confirm("是否确定下载附件？")) {
				return false;
			} else {
				$("#showInfo").show();
				$("#form_download").ajaxSubmit();
				getData = setInterval(showData, 15000);
			}
		});
		
	};
});
