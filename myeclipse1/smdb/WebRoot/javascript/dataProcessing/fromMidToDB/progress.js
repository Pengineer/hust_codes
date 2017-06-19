define(function(require, exports, module) {
	exports.init = function() {
		var projectType = top.PopLayer.instances[1].inData.projectType,
			importType = top.PopLayer.instances[1].inData.importType;

		var id = setInterval(function() {//保存定时器ID，待入库完成后清除定时器
			$.ajax({
				url: "dataProcessing/dataImporter/startImport.action",
				data: "projectType=" + projectType + "&importType=" + importType + "&isStarted=0",
				type: "post",
				dataType: "json",
				success: function(result) {
					if (!result.isFinished && result.totalNum) {
						console.log(result.currentNum);
						//当前入库的条数<1时，显示后台程序正在初始化界面
						if (result.currentNum > 1) {
							$(".progress").css({
								"opacity": "1"
							});
							$(".loadings").css({
								"opacity": "0"
							});
							var progress = Math.floor(result.currentNum / result.totalNum * 100) + "%";
							$(".progress-bar").width(progress);
							$("#counter").html(progress);
						}

					} else {
						clearInterval(id);
						$(".progress").css({
							"opacity": "1"
						});
						$(".loadings").css({
							"opacity": "0"
						});
						$(".progress-bar").width("100%");
						$("#counter").html("100%");


						$("#cancel").unbind("click").addClass("disable");
						$("#finished").removeClass("disable").bind("click", function() {
							alert("数据成功导入！");
							$.ajax({
								url: "dataProcessing/dataImporter/resultDetail.action",
								data: "projectType=" + projectType + "&importType=" + importType + "&isStarted=0",
								type: "post",
								dataType: "json",
								success: function(result) {
									top.PopLayer.instances[1].callBack(result);
									top.PopLayer.instances[1].destroy();
								}
							});

						});
					}
				}
			});
		}, 30000);//每30秒查询一次入库状态

		
		$("#cancel").bind("click", function() {
			if (confirm("你确定要取消数据入库吗？")) {
				$.ajax({
					url: "dataProcessing/dataImporter/cancelImport.action",
					data: "projectType=" + projectType + "&importType=" + importType,
					type: "post",
					dataType: "json",
					success: function(result) {
						if (result) {
							alert("数据入库取消成功！");
							top.PopLayer.instances[1].destroy()
						}
					}
				});
			}
		});//取消操作
		console.log("hello,world");
		console.log($(".close_out", top.document));
		$(".close_out", top.document).remove();
		/*$(".close_out",top.document).click(function(){
			if(confirm("你确定要取消数据入库吗？")){
				$.ajax({
					url:"dataImporter/dataImporter/cancelImport.action",
					data:"status=1",
					success:function(result){
						if(result){
							alert("数据入库取消成功！");
							top.PopLayer.instances[1].destroy()
						}
					}
				});				
			}
		});	*/
		$("#finished").addClass("gray");

	};

})