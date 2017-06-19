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
					to("user/toList.action?update=1&entityId=" + entityId);
					return false;
				} else {
					alert(result.errorInfo);
				}
			}
		});
	}
};

var viewUser = function(url) {// 加载查看数据
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
		entityId = result.user.id;//更新entityId
		$("#entityId").attr("value", result.user.id);
		$("#entityIds").attr("value", result.user.id);
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
	viewUser("user/view.action");
	
	//修改
	$("#view_mod").bind("click", function() {
		to("user/toModify.action?entityId=" + entityId + "&userstatus=" + $("#userstatus").val());
		return false;
	});
	
	//删除
	$("#view_del").bind("click", function() {
		deleteSingle("user/delete.action?entityId=" + entityId);
		return false;
	});
	
	//上一条
	$("#view_prev").live("click", function() {
		viewUser("user/prev.action");
		return false;
	});
	
	//下一条
	$("#view_next").live("click", function() {
		viewUser("user/next.action");
		return false;
	});
	
	//返回列表
	$("#view_back").live("click", function() {
		to("user/toList.action?entityId=" + entityId + "&update=" + $("#update").val() + "&userstatus=" + $("#userstatus").val());
		return false;
	});
	
	//重置密码
	$("#retrieveCode").live("click", function(){
		if (confirm('您确定要给本账号生成密码重置码吗？生成重置码后，该账号用户可以通过指定的链接重置密码。')) {
			$.ajax({
				url: "user/retrieveCode.action?username=" + $("#username").html(),
				type: "post",
				dataType: "json",
				success: function(result) {
					if (result == null || result.errorInfo == null || result.errorInfo == "") {
						setTimeout('alert("密码重置成功");', 200);
					} else {
						alert(result.errorInfo);
					}
				}
			});
		}
		return false;
	});
	
	//修改密码
	$("#modifyPassword").live("click", function(){
		popPasswordModify({
			title : "修改密码",
			src : "user/toModifyPassword.action?username=" + $("#username").html().trim(),
			callBack : function(result){
				if (result == null || result.error == null) {
					alert('修改密码成功');
				} else {
					alert(result.error);
				}
			}
		});
		return false;
	});
};

$(document).ready(function() {
	init();
});
