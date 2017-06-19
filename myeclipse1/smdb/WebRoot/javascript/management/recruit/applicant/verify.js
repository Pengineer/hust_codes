define(function(require,exports,module){
	$(function(){
		$("#confirmed").live("click",function(){
			$.ajax({
				url:"management/recruit/applicant/verify.action",
				data : "verifyResult=" + $(":radio:checked").val() + "&entityId=" + top.PopLayer.instances[1].inData.entityId,
				success:function (){				
					top.PopLayer.instances[1].callBack({
						status:$(":radio:checked").val()
					});			
					top.PopLayer.instances[1].destroy();
				}
			});
			return false;
		});
		$("#cancel").live("click",function(){
			top.PopLayer.instances[1].destroy();
		});
	});
})
