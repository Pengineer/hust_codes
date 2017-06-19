define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	require('form');
	require('validate');
	exports.init = function() {
		$("#submit").click(function(){
			if($.trim($("#listName").val()) == ""){
				alert("请填写清单名称！");
				return;
			}
			var dis={
				"listName":$("#listName").val(),
				"note":$("#note").val(),
				"entityId":$("#entityId").val()
			};
			thisPopLayer.callBack(dis);
			thisPopLayer.destroy();
		});
	}
});