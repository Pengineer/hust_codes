$(document).ready(function(){
	var nameSpace = "project/instp/firstAudit";
	pageShow({
		"nameSpace":nameSpace,
		"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4"],
		"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4}
	});
	
	$(".linkView").live("click", function(){
		var url = basePath + nameSpace + "/toView.action?entityId=" + this.id;	//不再需要页码
		url += (this.type) ? "&listType=" + this.type : "";//(项目列表类型先如是判别)
		url += "&isReviewable=" + $("#isReviewable").val();
		window.location.href = url;
		return false;
	});

	//导出项目查重情况
	$("#exportCheck").live('click', function(){
		window.location.href = basePath + nameSpace + "/exportFirstAudit.action";
		
	});
	
	//更新项目查重情况
	$("#updateCheck").live("click", function(){
		if(!confirm("提示：原有初审结果将被清除，确定更新吗？")){
			return;
		}
		$("#waiting").show();
		$.ajax({
			url: basePath + nameSpace + "/updateFirstAudit.action",
			type: "post",
			dataType: "json", 
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
//					window.location.href = basePath + nameSpace + "toList.action?update=1&isReviewable=0";
					location.reload();
				} else {
					alert(result.errorInfo);
					location.reload();
				}
			}
		});
	});
});


