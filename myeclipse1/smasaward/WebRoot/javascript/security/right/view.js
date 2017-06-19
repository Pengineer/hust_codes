var to = function(url){//跳转
	window.location.href = basePath + url;
};

var deleteSingle = function(url) {// 删除单个项目
	if (confirm("您确定要删除此记录吗？")) {
		$.ajax({
			url: url,
			type: "post",
			data: "entityIds=" + entityId,
			dataType: "json",
			success: function(result) {
				if (result.errorInfo == null || result.errorInfo == "") {
					to("right/toList.action?update=1&entityId=" + entityId);
					return false;
				} else {
					alert(result.errorInfo);
				}
			}
		});
	}
};

var viewRight = function(url) {// 加载查看数据
//	if (parent != null) {
//		parent.loading_flag = true;
//		setTimeout("parent.showLoading();", parent.loading_lag_time);
//	}
	$.ajax({
		url: url,
		type: "post",
		data: "entityId=" + entityId,
		dataType: "json",
		success: showDetails
	});
};

var showDetails = function(result) {// 显示查看内容
	if (result.errorInfo == null || result.errorInfo == "") {
		entityId = result.right.id;//更新entityId
		$("#entityId").attr("value", result.right.id);
		$("#entityIds").attr("value", result.right.id);
		$("#view_container").hide();
		$("#view_container").html(TrimPath.processDOMTemplate("view_template", result));
		$("#view_container").show();
	} else {
		alert(result.errorInfo);
	}
//	if (parent != null) {
//		parent.loading_flag = false;
//		parent.hideLoading();
//	}
};

var init = function(){
	window.entityId = $("#entityId").val();
	
	//查看
	viewRight("right/view.action");
	
	//添加
	$("#view_add").bind("click", function() {
		to("right/toAdd.action");
		return false;
	});
	
	//修改
	$("#view_mod").bind("click", function() {
		to("right/toModify.action?entityId=" + entityId);
		return false;
	});
	
	//删除
	$("#view_del").bind("click", function() {
		deleteSingle("right/delete.action?entityId=" + entityId);
		return false;
	});
	
	//上一条
	$("#view_prev").live("click", function() {
		viewRight("right/prev.action");
		return false;
	});
	
	//下一条
	$("#view_next").bind("click", function() {
		viewRight("right/next.action");
		return false;
	});
	
	//返回列表
	$("#view_back").bind("click", function() {
		to("right/toList.action?entityId=" + entityId + "&update=" + $("#update").val());
		return false;
	});
};

$(document).ready(function() {
	init();
});