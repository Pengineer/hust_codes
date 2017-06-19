define(function(require, exports, module) {
	
	exports.init = function() {
		$("#reset").click(function(){
			if (confirm("您确定要将基地项目所有查重标记清零吗？")) {
				document.forms[0].action = "dm/dupCheck/deleteInstp.action";
				document.forms[0].submit();
			}
		});
		
		$("#set").click(function(){
			if (confirm("您确定要设置基地项目查重标记吗？")) {
				document.forms[0].action = "dm/dupCheck/addInstp.action";
				document.forms[0].submit();
			}
		});
		
		$("#exportDup").click(function(){
			if (confirm("您确定要导出基地项目查重结果信息吗？")) {
				document.forms[0].action = "dm/dupCheck/exportCheckResult.action";
				document.forms[0].type.value = "instp";
				document.forms[0].submit();
			}
		});
	};
});