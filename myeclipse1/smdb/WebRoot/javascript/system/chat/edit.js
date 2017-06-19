define(function(require, exports, module) {
	var view = require('javascript/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var nameSpace = "chat";

	var showDetails = function(result) {// 显示查看内容
		if (result.errorInfo == null || result.errorInfo == "") {
			$("#view_container").hide();
			$("#view_container").html(TrimPath.processDOMTemplate("view_template", result));
			$("#view_container").show();
		} else {
			alert(result.errorInfo);
		}
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	};
	$("#submit").click(function() {// 提交
		var entityId = $("#entityId").val();
		var content = $("#content").val();
		if (content == "") {
			alert("发送内容不能为空！");
			return false;
		}
		$.ajax({
			url: "chat/send.action",
			type: "post",
			data: "content=" + content + "&entityId="+entityId,
			dataType: "json",
			success:function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					window.location.href = basePath + "linkedin/toSend.action?entityId="+entityId;
					return false;
				}else{
					alert(result.errorInfo);
				}
			}
		});
	});
	
	exports.init = function() {
		view.show(nameSpace, showDetails);// 公用方法
		
	};
});
