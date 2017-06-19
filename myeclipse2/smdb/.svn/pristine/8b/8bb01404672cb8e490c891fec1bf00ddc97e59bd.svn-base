define(function(require, exports, module) {
	require('pop-init');
	
	exports.init = function() {
		$("#submit").click(function() {// 提交
			var entityId = $("#entityId").val();
			var reason = $("#reason").val();
			if (reason == "") {
				alert("请输入验证信息！");
				return false;
			}
			$.ajax({
				url: "linkedin/addFriend.action",
				type: "post",
				data: "reason=" + reason + "&entityId="+entityId,
				dataType: "json",
				success:function(result){
					if (result.errorInfo == null || result.errorInfo == "") {
						thisPopLayer.destroy();
					}else{
						alert(result.errorInfo);
					}
				}
			});
		});
		
		
		$("#refuseBut").click(function() {
			var entityId = $("#entityId").val();
			var refuse = $("#refuse").val();
			$.ajax({
				url: "linkedin/checkFriend.action",
				type: "post",
				data: "refuse=" + refuse + "&entityId="+entityId + "&type="+1,
				dataType: "json",
				success:function(result){
					if (result.errorInfo == null || result.errorInfo == "") {
						thisPopLayer.destroy();
					}else{
						alert(result.errorInfo);
					}
				}
			});
		});
		
	}
})
