/**
 * 热点分析
 */
define(function(require, exports, module) {
	require('form');
	var nameSpace = "dataMining/hotspot";
	// 初始化
	exports.init = function() {
		
		$("#updateIndex").click(function(){
			var type = $("#type").val();
			if(type == -1){alert("请选择项目类型！"); return false;}
			if(confirm("确定更新索引？")){
				$("#updating").css("display", "inline-block");
				$("#updateIndex").removeClass("btn2").addClass("btn4");
				$("#updateIndex").attr("disabled", true);
				var url = nameSpace + "/updateIndex.action";
				$("#form_dm").attr("action", url);
				$("#form_dm").ajaxSubmit({
					success: function(result){
						if (result.errorInfo == null || result.errorInfo == "") {
		 					alert(result.hintInfo);
		 				} else {
		 					alert(result.errorInfo);
		 				}
						$("#updating").css("display", "none");
						$("#updateIndex").removeClass("btn4").addClass("btn2");
						$("#updateIndex").attr("disabled", false);
					}
				});
			}
	 		return false;
		});
		
		$("#submit").click(function(){
			var type = $("#type").val();
			if(type == -1){alert("请选择项目类型！"); return false;}
			$.ajax({
				url: nameSpace + "/isExistIndex.action",
				data: "type=" + type + "&analyzeAngle=" + $("input[name='analyzeAngle']:checked").val(),
	 			type: "post",
	 			dataType: "json",
	 			success: function(result) {
	 				if (result.errorInfo == null || result.errorInfo == "") {
	 					var url = nameSpace + "/toHotspot.action";
	 					$("#form_dm").attr("action", url);
	 					$("#form_dm").submit();
	 				} else {
	 					alert(result.errorInfo);
	 				}
	 				return false;
	 			}
			});
		});
	}
	
});