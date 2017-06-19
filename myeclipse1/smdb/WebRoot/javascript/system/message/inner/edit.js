define(function(require, exports, module) {
	var validate = require('javascript/system/message/inner/validate');
	
	exports.init = function() {
		validate.valid();
		
		$("#chooseAll").live('click', function(){
			var sta = $("#chooseAll").attr('checked');
			if(sta){
				$("input[name='sendList']").each(function(key, value){
					value.checked = true;
				})
			} else {
				$("input[name='sendList']").each(function(key, value){
					value.checked = false;
				})
			}
			
		});
		
		// 载入时设置默认选中回复人
		$("input[name='sendList']").each(function() {
			var send = $("input[name='sendList']");
			var send_length = send.length;
			// 设置选项对全选的反馈控制
			$(this).click(function() {
				if (this.checked) {
					var cnt = 0;
					send.each(function() {
						if (this.checked) {
							cnt += 1;
						}
					});
					if (cnt == send_length) {
						$("#chooseAll").attr("checked", true);
					}
				} else {
					$("#chooseAll").attr("checked", false);
				}
			});
			if ($("#replyTo").val() == $(this).val()) {
				$(this).attr("checked", true);
			}
		});

		$("#submit").live('click',function(){
			return ValidFCK('message.content');
		});
	};
});
