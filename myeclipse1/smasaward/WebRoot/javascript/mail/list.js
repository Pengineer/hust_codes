$(function(){
	var nameSpace = "mail/";
	
	pageShow({
		"nameSpace":nameSpace,
		"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7"],
		"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7}
	});
	
	$(".linkView").live("click", function(){
		var url = basePath + nameSpace + "/toView.action?entityId=" + this.id;	//不再需要页码
		window.location.href = url;
		return false;
	});
	
	$("#list_send_all").live("click", function() {
		$("#list").attr("action", nameSpace + "/sendAll.action");
		$("#list").submit();
	});
	
	$("#list_add").live("click", function() {
		$("#list").attr("action", nameSpace + "/toAdd.action");
	});

	$("#list_cancel_all").live("click", function() {
		$("#list").attr("action", nameSpace + "/cancelAll.action");
		$("#list").submit();
	});
	
});
