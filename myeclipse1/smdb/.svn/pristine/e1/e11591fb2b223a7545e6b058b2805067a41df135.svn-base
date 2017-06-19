define(function(require, exports, module) {
	require('pop-init');
	var list = require('javascript/list');
	
	exports.init = function() {
		$(document).ready(function() {
			setOddEvenLine("institute", 1);
			$("input[name='entityId']").click(function(){
				$("#mainId").val(this.value);
				$("#name").val($(this).parent().next().next().next().next().html());
				$("#code").val($(this).parent().next().next().next().next().next().next().html());
			});
			
			$("#confirm").unbind("click").click(function(){
				var checkedIds = $("#checkedIds").val();
				var mainId = $("#mainId").val();
				if(mainId == ""){
					alert("请选择主要研究基地");
					return false;
				} else if($("#name").val() == ""){
					alert("请输入合并后研究基地名称");
					return false;
				} else if($("#code").val() == ""){
					alert("请输入合并后研究基地代码");
					return false;
				} else {
					thisPopLayer.callBack({
						data : 
						{
							"checkedIds" : checkedIds,
							"mainId" : mainId,
							"name" : $("#name").val(),
							"code" : $("#code").val()
						}
					});
					thisPopLayer.destroy();
				}
			});
		});
	}
	
});
