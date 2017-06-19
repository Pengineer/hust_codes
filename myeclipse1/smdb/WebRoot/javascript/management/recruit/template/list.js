/**
 * 招聘的模板列表页面
 * @author liujia
 *
 */
define(function(require,exports,module){
	var list = require('javascript/list');
	var nameSpace = "management/recruit/template";
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":nameSpace,	
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2},
			"dealWith": function(){
				$(".date").each(function(){
					var date = $(this).html().substring(0,10);
					$(this).html(date);
				});//日期处理函数
				$("#list_delete").die()//解绑删除函数
				$("#list_delete").click(function(){
					if($("tbody input:checked").length > 0){
						if(confirm("确定要删除选中的记录吗？")){
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
						}
					} else {
						alert("请选择需要删除的数据！");
					}
				});//自定义删除函数
			}
		});
	};
});