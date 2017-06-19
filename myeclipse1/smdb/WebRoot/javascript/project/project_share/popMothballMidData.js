define(function(require,exports,module){
	require("form");
	require('validate');
	var datepick = require("datepick-init");
	$("#mid_mothball").submit(function(){
	
	});
	
	$("#cancel").click(function(){
		top.PopLayer.instances[1].destroy();
	});
	$("#midAuditDate").val((new Date()).format("yyyy-MM-dd"))
	exports.init = function(nameSpace){
		datepick.init();
		$("#mid_mothball").attr("action",nameSpace + "/mothball.action");

		$("#mid_mothball").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"midAuditOpinion":{maxlength: 200},
				"midAuditOpinionFeedback":{maxlength: 200},
				"midAuditResult":{required: true}
			},
			errorPlacement: function(error, element){
				error.appendTo( element.parent("td").next("td") );
			},
			submitHandler: function(form){
				
				var msg = "本操作将封存超期未审核的项目中检数据，所有被封存的数据将审核为";
				if($("input[name='midAuditResult']:checked").val() === "2"){
					msg += "同意";
				}  else {
					msg += "不同意";
				}
				
				msg += "，是否继续？";
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