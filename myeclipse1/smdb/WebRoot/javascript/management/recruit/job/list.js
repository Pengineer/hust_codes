define(function(require,exports,module){
	var list = require('javascript/list');
	var nameSpace = "management/recruit/job";
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3},
			"dealWith": function(){
				$("#list_delete").die();
				$("#list_delete").click(function(){
					if($("tbody input:checked").length > 0){
						if(confirm("确定要删除选中的记录吗？")){
							var entityIds = [];
							$("tbody input:checked").each(function(){
								entityIds.push($(this).val());
							});
							entityIds = "entityIds="+entityIds.join("&entityIds=");
							$.ajax({
								url:nameSpace + "/delete.action",
								data:entityIds,
								type:"post",
								success:function(){
									window.location.reload();
								}
							});
						}
					} else {
						alert("请选择需要删除的数据！");
					}
				});
				$(".date").each(function(){
					var date = $(this).html().substring(0,10);
					$(this).html(date);
				});
				/*$("#list_delete").die();
				$("#list_delete").click(function(){
					var entityIds = [];
					$("tbody input:checked").each(function(){
						entityIds.push($(this).val());
					});
					entityIds = "entityIds="+entityIds.join("&entityIds=")
					$.ajax({
						url:nameSpace + "/delete.action",
						data:entityIds,
						type:"post",
						success:function(){
							window.location.reload();
						}
					});
				});*/
			}
		});
	};
});