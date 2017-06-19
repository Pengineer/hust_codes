define(function(require, exports, module) {
	require('pop-init');
	var list = require('javascript/list');
   
	
	/**
	 * 弹出人员合并弹出层
	 * @param {Object} type(1-8)
	 * @param {Object} nameSpace
	 * @memberOf {TypeName} 
	 */
	exports.popMergeDialog = function(type, nameSpace){				
		
		$("#list_merge").live("click",function(){
			var checkedIds = "";
			$("input[name='entityIds']").each(function() {
				if (this.checked && (checkedIds.indexOf(this.value) == -1)) {
					checkedIds += $(this).val() + ",";
				}
			});
			var cnt = checkedIds.split(",").length - 1;
			var mainId = checkedIds.split(",")[0];
			if($("#canMerge").val() =="true"){
				if (cnt < 2 ) {
					alert("选择合并的人员至少为2个！");
				} else {
					window.location.href = basePath + nameSpace+"/toMerge.action?checkedIds="+checkedIds+"&entityId="+mainId;
					$("#checkedIds").val("");
				}
			}else{
				alert("数据库结构发生变化，当前不可合并！");
			}
		});
	};
	
	
});
