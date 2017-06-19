define(function(require, exports, module) {
	
	exports.init = function() {
		$("#reset").click(function(){
			if (confirm("您确定要将专项任务项目所有查重标记清零吗？")) {
				document.forms[0].action = "dm/dupCheck/deleteSpecial.action";
				document.forms[0].submit();
			}
		});
		
		$("#set").click(function(){
			if (confirm("您确定要设置专项任务项目查重标记吗？")) {
				document.forms[0].action = "dm/dupCheck/addSpecial.action";
				document.forms[0].submit();
			}
		});
		
		$("#exportDup").click(function(){
			if (confirm("您确定要导出专项任务查重结果信息吗？")) {
				document.forms[0].action = "dm/dupCheck/exportCheckResult.action";
				document.forms[0].type.value = "special";
				document.forms[0].submit();
			}
		});
	};
});