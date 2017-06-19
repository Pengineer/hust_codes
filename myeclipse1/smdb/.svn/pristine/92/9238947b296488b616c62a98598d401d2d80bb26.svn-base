define(function(require, exports, module){
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	require('form');
	require('validate');
	function submit(){
	
		if($.trim($("#fee").val()) == ""){
			alert("请填写金额！");
			return;
		}
		var dis={
			"fee":$("#fee").val(),
			"entityId":$("#entityId").val()
		};
		thisPopLayer.callBack(dis);
		thisPopLayer.destroy();
			
	}
	exports.init = function() {
		$("#submit").click(submit);
		$(document).keydown(function(event){
			if(event.keyCode == 13){
				submit();
			}
		});
	}
})