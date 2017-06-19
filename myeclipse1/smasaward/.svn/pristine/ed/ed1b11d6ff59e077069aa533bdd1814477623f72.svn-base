$(function(){
	var nameSpace = "project/general/special";
	
	pageShow({
		"nameSpace":nameSpace,
		"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5"],
		"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5}
	});
	
	$(".linkView").live("click", function(){
		var url = basePath + nameSpace + "/toView.action?entityId=" + this.id;	//不再需要页码
		url += (this.type) ? "&listType=" + this.type : "";//(项目列表类型先如是判别)
		url += "&isReviewable=" + $("#isReviewable").val();
		window.location.href = url;
		return false;
	});
});
