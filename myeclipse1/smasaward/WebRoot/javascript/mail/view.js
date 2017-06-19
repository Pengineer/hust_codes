var to = function(url){//跳转
	window.location.href = basePath + url;
};

var deleteSingle = function(url) {// 删除单个邮件
	if (confirm("您确定要删除此记录吗？")) {
		$.ajax({
			url: url,
			type: "post",
			data: "entityIds=" + entityId,
			dataType: "json",
			success: function(result) {
				if (result.errorInfo == null || result.errorInfo == "") {
					var url = "mail/toList.action?entityId=" + entityId + "&update=" + $("#update").val();
					to(url);
					return false;
				} else {
					alert(result.errorInfo);
				}
			}
		});
	}
};

var viewMail = function(url) {// 加载查看数据
	if (parent != null) {
		parent.loading_flag = true;
		setTimeout("parent.showLoading();", parent.loading_lag_time);
	}
	$.ajax({
		url: url,
		type: "post",
		data: "entityId=" + entityId,
		dataType: "json",
		success: showDetails
	});
};

/**
 * 下载文件
 * @param validateUrl 后台校验的url
 * @param successUrl 后台校验成功后下载文件的url
 */ 
function downloadFile(validateUrl, successUrl){
	$.ajax({
		url: validateUrl,
		type: "post",
		dataType: "json",
		success: function(result){
			if(result.errorInfo != null && result.errorInfo != ""){
				alert(result.errorInfo);
			}else{
				window.location.href = basePath + successUrl; 
			}
		}
	});
}

var showDetails = function(result) {// 显示查看内容
	if (result.errorInfo == null || result.errorInfo == "") {
		if (result.mail.attachmentName != null
				&& result.mail.attachmentName != "") {
			result.mail.attachmentName = result.mail.attachmentName
					.split("; ");
		}
		entityId = result.mail.id;//更新entityId
		$("#entityId").attr("value", result.mail.id);
		$("#entityIds").attr("value", result.mail.id);
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

var init = function(){
	window.entityId = $("#entityId").val();
	
	//查看
	viewMail("mail/view.action");
	
	//添加
	$("#view_add").bind("click", function() {
		to("mail/toAdd.action");
		return false;
	});
	
	//修改
	$("#view_mod").bind("click", function() {
		to("mail/toModify.action?entityId=" + entityId);
		return false;
	});
	
	$("#view_again").bind(
			"click",
		function() {
			if (confirm("您确定要重新发送此邮件吗？")) {
				$.ajax({
					url : "mail/sendAgain.action",
					type : "post",
					data : "entityId=" + entityId,
					dataType : "json",
					success : function(result) {
						alert(result.errorInfo);
						window.location.href = basePath + nameSpace
								+ "/toView.action?entityId="
								+ entityId;
						return false;
					}
				});
			}
			return false;
		});
	
	//删除
	$("#view_del").bind("click", function() {
		deleteSingle("mail/delete.action?entityId=" + entityId);
		return false;
	});
	
	//上一条
	$("#view_prev").live("click", function() {
		viewMail("mail/prev.action");
		return false;
	});
	
	//下一条
	$("#view_next").bind("click", function() {
		viewMail("mail/next.action");
		return false;
	});
	
	//返回列表
	$("#view_back").bind("click", function() {
		var url = "mail/toList.action?entityId=" + entityId + "&update=" + $("#update").val();
		to(url);
		return false;
	});
	
	//发送邮件
	$("#view_send").live("click", function() {
		var url = "mail/send.action?entityId=" + entityId;
		to(url);
		return false;
	});
	
	//暂停发送邮件
	$("#view_cancel").live("click", function() {
		var url = "mail/cancel.action?entityId=" + entityId;
		to(url);
		return false;
	});
	
	//暂停发送邮件
	$("#view_refresh").live("click", function() {
		viewMail("mail/view.action");
		return false;
	});
	//下载附件
	$(".attach").live("click", function() {
		var validateUrl = "mail/validateFile.action?entityId="+this.id+"&status=" + this.parentNode.id;
		var successUrl = "mail/download.action?entityId=" + $("#entityId").val() + "&status=" + this.parentNode.id;
		downloadFile(validateUrl, successUrl);
		return false;
	});
	
};

$(document).ready(function() {
	init();
});
