$(function(){
	var nameSpace = "right";
	
	pageShow({"nameSpace":nameSpace,
		"sortColumnId":["sortcolumn0","sortcolumn1"],
		"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1}});
	
	$(".linkView").live("click", function(){
		var url = basePath + nameSpace + "/toView.action?entityId=" + this.id;	//不再需要页码
		window.location.href = url;
		return false;
	});
});