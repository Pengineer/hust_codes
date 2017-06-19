define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	require('form');
	require('validate');
	

	exports.init = function() {
		
		$("#submit").click(function(){
			if($.trim($("#fee").val()) == ""){
				alert("请填写金额！");
				return;
			}
			var dis={
				"fee":$("#fee").val(),
				"fundId":$("#entityId").val(),
			};
			thisPopLayer.callBack(dis);
			thisPopLayer.destroy();
		});
	}
});