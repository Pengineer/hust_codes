define(function(require,exports,module){
	require("form");
	require('validate');
	var datepick = require("datepick-init");
	$("#publish").submit(function(){
	
	});
	
	$("#cancel").click(function(){
		top.PopLayer.instances[1].destroy();
	});
	exports.init = function(nameSpace){
		datepick.init();
		$("#publish").attr("action",nameSpace + "/batchPublish.action");

		$("#publish").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"isPublish":{required: true}
			},
			errorPlacement: function(error, element){
				error.appendTo( element.parent("td").next("td") );
			},
			submitHandler: function(form){
				
				var msg = "本操作将";
				if($("input[name='isPublish']:checked").val() === "1"){
					msg += "发布";
				}  else {
					msg += "取消发布";
				}
				
				msg += "满足条件的数据，是否继续？";
				if(confirm(msg)){
					$(form).ajaxSubmit({
						type:"post",
						success: function(){
							top.PopLayer.instances[1].destroy();
						}
					});
					return false;		
				}
			}//表单提交函数
		});
	}
});