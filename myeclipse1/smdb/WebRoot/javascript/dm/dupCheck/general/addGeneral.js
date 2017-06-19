define(function(require, exports, module) {
	
	exports.init = function() {
		$("#reset").click(function(){
			if (confirm("您确定要将一般项目所有查重标记清零吗？")) {
				document.forms[0].action = "dm/dupCheck/deleteGeneral.action";
				document.forms[0].submit();
			}
		});
		
		$("#set").click(function(){
			if (confirm("您确定要设置一般项目查重标记吗？")) {
				document.forms[0].action = "dm/dupCheck/addGeneral.action";
				document.forms[0].submit();
			}
		});
		
		$("#exportDup").click(function(){
			if (confirm("您确定要导出一般项目查重结果信息吗？")) {
				document.forms[0].action = "dm/dupCheck/exportCheckResult.action";
				document.forms[0].type.value = "general";
				document.forms[0].submit();
			}
		});
	};
});