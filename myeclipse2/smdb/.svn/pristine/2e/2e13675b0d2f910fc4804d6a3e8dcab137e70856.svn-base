/**
 * 备忘录查看
 */
$(function(){
	init("selfspace/memo");
});

var init = function(nameSpace) {
	window.entityId = $("#entityId").val();
	view(nameSpace+"/view.action");

	$("#view_add").bind("click", function() {
		to(nameSpace + "/toAdd.action");
		return false;
	});
	$("#view_mod").bind("click", function() {
		to(nameSpace + "/toModify.action?entityId=" + entityId);
		return false;
	});
	$("#view_del").bind("click", function() {
		deleteSingle(nameSpace + "/delete.action");
		return false;
	});
	$("#view_prev").bind("click", function() {
		view(nameSpace + "/prev.action");
		return false;
	});
	$("#view_next").bind("click", function() {
		view(nameSpace + "/next.action");
		return false;
	});
	$("#view_back").bind("click", function() {
		to(nameSpace + "/toList.action?entityId=" + entityId + "&update=" + $("#update").val());
		return false;
	});
}

var to = function(url) {// 跳转
	window.location.href = basePath + url;
}

/**
 * 查看页面删除项
 */
var deleteSingle = function(url) {// 删除单个记录
	if (confirm("您确定要删除此记录吗？")) {
		$.ajax({
			url: url,
			type: "post",
			data: "entityIds=" + entityId,
			dataType: "json",
			success: function(result) {
				if (result.errorInfo == null || result.errorInfo == "") {
					to("selfspace/memo/toList.action?update=1&entityId=" + entityId);
					return false;
				} else {
					alert(result.errorInfo);
				}
			}
		});
	}
};

/**
 * 查看备忘录
 */
var view = function(url) {// 加载查看数据
	if (parent != null) {
		parent.loading_flag = true;
		setTimeout("parent.showLoading();", parent.loading_lag_time);
	}
	$.ajax({
		url: url,
		type: "post",
		data: "entityId=" + $("#entityId").val(),
//		data: "entityId=" + entityId,
		dataType: "json",
		success: showDetails
	});
};

/**
 * 查看回调
 */
function showDetails(result){
	if (result.errorInfo == null || result.errorInfo == ""){
		entityId = result.memo.id;
		$("#entityId").val(result.memo.id);
		$("#view_container").html(TrimPath.processDOMTemplate("view_template", result)).show();
		parseWeek();////转换星期显示格式(数字->中文)
	} else {
		alert(result.errorInfo);
	}
	if (parent != null) {
		parent.loading_flag = false;
		parent.hideLoading();
	}
};

///**
// * 查看上、下条
// */
//function next_prev(url) {
//	if (parent != null) {
//		parent.loading_flag = true;
//		setTimeout("parent.showLoading();", parent.loading_lag_time);
//	}
//	$("#view_list").ajaxSubmit({
//		url: url,
//		type: "post",
//		dataType: "json",
//		success: showDetails
//	});
//};

var parseWeek = function(){//将数字转换为中文星期
	var type = $.trim($("#alert_type").html());
	if(type == "指定日期"){
		$("#alert_value1").show();
	}else{
		$("#alert_value1").hide();
	}
	if(type == "按周"){
		var data = ['日', '一',  '二', '三', '四', '五', '六'];
		var values = $("#week").html().split("; ");
		var	value = "";
		for(var i in values){
			if(i < values.length - 1){
				value += data[parseInt(values[i])] + "; ";
			} else{
				value += data[parseInt(values[i])];//最后一个不加分号
			}
		};
		$("#week").html(value);
		$("#alert_value3").show();
	}else{
		$("#alert_value3").hide();
	}
	if(type == "按天"){
		$("#alert_value2").show();
	}else{
		$("#alert_value2").hide();
	}
	if(type == "按月"){
		$("#alert_value4").show();
	}else{
		$("#alert_value4").hide();
	}
    if(type == "倒计时"){
		$("#alert_value5").show();
	}else{
		$("#alert_value5").hide();
	}
    if(type == "阴历"){
		$("#alert_value6").show();
	}else{
		$("#alert_value6").hide();
	}
};
