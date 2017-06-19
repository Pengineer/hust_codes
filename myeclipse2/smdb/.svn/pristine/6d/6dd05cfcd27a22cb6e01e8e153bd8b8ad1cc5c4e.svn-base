define(function(require, exports, module) {
	var view = require('javascript/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var nameSpace = "linkedin";

	var showDetails = function(result) {// 显示查看内容
		if (result.errorInfo == null || result.errorInfo == "") {
			if (result.chats != null) {
				$("#view_container").hide();
				$("#view_container").html(TrimPath.processDOMTemplate("view_template", result));
				$("#view_container").show();
				$("#view_show").show();
			}
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
			url: "inBox/add.action",
			type: "post",
			data: "content=" + content + "&entityId="+entityId + "&sendType=4",
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
