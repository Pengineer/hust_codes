$(function(){
	var nameSpace = "expert/select";
	
	pageShow({
		"nameSpace":nameSpace,
		"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3"],
		"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3}
	});
	
	$.ajax({
		url : "expert/fetchAutoData.action",
		dataType : "json",
		method : "post",
		success : function(result){
			$("#keyword").autocomplete(result.autoData);
		}
	});
	
	// 弹出层添加举报信息，初始化列表
	$("#list_pop_add").live('click', function(){
		popOperation({
			title : "添加专家",
			src : basePath + nameSpace + "/toPopAdd.action",
			callBack : function(result){
				if(result.errorInfo == null || result.errorInfo == '') {
					showSimpleSearch();//更新列表
				} else {
					alert(result.errorInfo);
				}
			}
		});
	});
});