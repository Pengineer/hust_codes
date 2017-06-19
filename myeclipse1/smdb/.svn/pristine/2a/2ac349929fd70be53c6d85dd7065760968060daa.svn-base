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
			if($.trim($("#rate").val()) == ""){
				alert("请填写拨款比率！");
				return;
			}
			if($.trim($("#projectYear").val()) == "-1"){
				alert("请选择项目年度！");
				return;
			}
			thisPopLayer.callBack({
				listName:$("#listName").val(),
				rate:$("#rate").val(),
				projectYear:$("#projectYear").val(),
				projectSubtype:$("#projectSubtype").val(),
				note:$("#note").val(),
			});
		});
	}
});