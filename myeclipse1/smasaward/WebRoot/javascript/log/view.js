$(document).ready(function() {
	init();
});

//跳转函数
var to = function(url){//跳转
	window.location.href = basePath + url;
};

//配置初始化
var init = function(){
	window.entityId = $("#entityId").val();//获取要显示详情的对应id
	
	//查看
	viewLog("log/view.action");
	
	//删除
	$("#view_del").bind("click", function() {
		deleteSingle("log/delete.action?entityId=" + entityId);
		return false;
	});
	
	//上一条
	$("#view_prev").live("click", function() {
		viewLog("log/prev.action");
		return false;
	});
	
	//下一条
	$("#view_next").bind("click", function() {
		viewLog("log/next.action");
		return false;
	});
	
	//返回列表
	$("#view_back").bind("click", function() {
		var url = "log/toList.action?entityId=" + entityId;
		to(url);
		return false;
	});
};

//加载对应的数据
var viewLog = function(url) {// 加载查看数据
	if (parent != null) {
		parent.loading_flag = true;
		setTimeout("parent.showLoading();", parent.loading_lag_time);
	}
	$.ajax({
		url: url,
		type: "post",
		data: "entityId=" + entityId,
		dataType: "json",
		success: showDetails//数据成功加载后执行此回调函数，显示对应详情
	});
};

//显示对应详情
var showDetails = function(result) {// 显示查看内容
	if (result.errorInfo == null || result.errorInfo == "") {
		entityId = result.log.id;//更新entityId
		$("#entityId").attr("value", result.log.id);
		$("#entityIds").attr("value", result.log.id);
		$("#view_container").hide();
		$("#view_container").html(TrimPath.processDOMTemplate("view_template", result));
		$("#view_container").show();
	} else {
		alert(result.errorInfo);
	}
	if (parent != null) {
		parent.loading_flag = false;
		parent.hideLoading();
	}
};

//删除操作
var deleteSingle = function(url) {// 删除单个项目
	if (confirm("您确定要删除此记录吗？")) {
		$.ajax({
			url: url,
			type: "post",
			data: "entityIds=" + entityId,
			dataType: "json",
			success: function(result) {
				if (result.errorInfo == null || result.errorInfo == "") {
					var url = "log/toList.action?entityId=" + entityId + "&update=1" + $("#update").val();
					to(url);
					return false;
				} else {
					alert(result.errorInfo);
				}
			}
		});
	}
};