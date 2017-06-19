define(function(require, exports, module) {
	require('pop-init');
	
	function swicthAccount() {
		var accountId = $("input[name='accountId'][type='radio']:checked").val();
		var username = $("#username").val();
		if(username != "" && username != undefined){
			$.ajax({
				url: "login/ckeckAccount.action?username=" +username,
				type: "post",
				dataType: "json",
				success: function(result) {
					if (result.errorInfo == null || result.errorInfo == "") {
						thisPopLayer.callBack({
							"accountId" : accountId,
							"username" : username
						});
						thisPopLayer.destroy();
					} else {
						$("#error").html(result.errorInfo);
						//alert(result.errorInfo);
						return false;
					}
				}
			});
		} else {
			thisPopLayer.callBack({
				"accountId" : accountId,
				"username" : username
			});
			thisPopLayer.destroy();
		}
	}
	
	exports.init = function() {
		thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
		
		if (thisPopLayer.inData && thisPopLayer.inData.accountId) {// 父页面有值传过来，则设置默认选中
			$("input[name='accountId'][type='radio']").each(function() {
				if ($(this).val() == thisPopLayer.inData.accountId) {
//					if($(this).val() == 'admin'){
//						$(this).attr("checked", false);
//					} else {
//						$(this).attr("checked", true);
//					}
					$(this).attr("checked", true);
				} else {
					$(this).attr("checked", false);
				}
			});
		}
		$("#okbutton").bind("click", function() {
			swicthAccount();
		});
		
		$("#username").live("keypress", function(event) {// 添加键盘事件，回车提交
			var keyCode = event.which;
			if (keyCode == 13) {
				swicthAccount();
			} else {
				return true;
			}
		});
		
		$(".choose").bind("mouseover",function() {
			if(!($(this).children().eq(0).attr("disabled"))) {
				$(this).children().eq(1).css({
					"cursor":"pointer",
					"color":"teal"
				});
			};
		}).bind("mouseout",function(evt){
			if(!($(this).children().eq(0).attr("disabled"))){
				$(this).children().eq(1).css({
					"color":""
				});
			};
		}).bind("click",function(){
			if(!($(this).children().eq(0).attr("disabled"))){
				$(this).children().eq(0).attr("checked",true);
			};
		});
	};
});
