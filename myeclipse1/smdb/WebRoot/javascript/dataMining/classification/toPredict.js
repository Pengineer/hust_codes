/**
 * 关联分析
 */
define(function(require, exports, module) {
	require('form');
	
	var nameSpace = "dataMining/classification";
	
	// 初始化
	exports.init = function() {
		
		// checkbox 全选
		$(".select_all").click(function(){
			var flag = $(this).attr("checked");
			$(".selected").each(function(){
				$(this).attr("checked",flag);
			});
		});
		
		// 检测模型是否存在，并显示
		$("#predictType").change(function(){
			$.ajax({
				url: nameSpace + "/fetchModelName.action",
				data: "predictType=" + $(this).val(),
	 			type: "post",
	 			dataType: "json",
	 			success: function(result) {
	 				if (result.errorInfo == null || result.errorInfo == "") {
	 					$("#modelName").css("font-size", "13px");
	 					$("#modelName").css("color", "#B15BFF");
	 					$("#modelName").val(result.hintInfo);
	 				} else {
	 					$("#modelName").css("font-size", "12px");
	 					$("#modelName").css("color", "red");
	 					$("#modelName").val(result.errorInfo);
	 				}
	 				return false;
	 			}	
			});
		});
		
		// 更新模型：预测因子的展开与收起
		$("#open_update").click(function(){
			if($("#predictorVariable").css("display") == "none"){
				$("#predict").css("display", "none");
				$("#isToDataBase").css("display", "none");
				$("#predictorVariable").css("display", "");
				$("#train").css("display", "");
				$(this).val("收起");
			} else {
				$("#predictorVariable").css("display", "none");
				$("#train").css("display", "none");
				$("#predict").css("display", "");
				$("#isToDataBase").css("display", "");
				$(this).val("更新模型");
			}
				
		});
		
		// 预测模型更新
		$("#train").click(function(){
			var selectVal = $("#predictType").val();
			alert(selectVal);
			if(selectVal == '-1') { alert("请选择预测类型"); return false; }
			if(confirm("确定更新模型？")){
				$("#updating").css("display", "inline-block");
				$("#train").removeClass("btn2").addClass("btn4");
				$("#train").attr("disabled", true);
				var url = nameSpace + "/trainModel.action";
				$("#form_dm").attr("action", url);
				$("#form_dm").ajaxSubmit({
					success: function(result){
						if (result.errorInfo == null || result.errorInfo == "") {
		 					alert(result.hintInfo);
		 				} else {
		 					alert(result.errorInfo);
		 				}
						$("#updating").css("display", "none");
						$("#train").removeClass("btn4").addClass("btn2");
						$("#train").attr("disabled", false);
					}
				});
			}
	 		return false;
		});
		
		$("#predict").click(function(){
			// 提交预测前，检查是否存在预测模型
			if($("#modelName").val().indexOf(".model") <= 0){
				alert("暂无预测模型，请先更新模型！");
				return false;
			}
			var url = nameSpace + "/toPredict.action";
			$("#form_dm").attr("action", url);
		});
		
	};
});