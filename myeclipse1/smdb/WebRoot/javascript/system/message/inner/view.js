define(function(require, exports, module) {
	var view = require('javascript/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var nameSpace = "message/inner";
	
	var deleteMessageSingle = function(id) {// 删除单个留言
		if (confirm("您确定要删除此留言吗？")) {
			$.ajax({
				url: "message/inner/delete.action",
				type: "post",
				data: "entityIds=" + id,
				dataType: "json",
				success: function(result) {
				alert(result);
					if (result.errorInfo == null || result.errorInfo == "") {
						if (result.type == 1) {// 删除的为主贴
							window.location.href = basePath + "message/inner/toList.action?update=1&entityId=" + entityId;
							return false;
						} else {// 删除的为子贴
							viewMessage("message/inner/view.action");
						}
					} else {
						alert(result.errorInfo);
					}
				}
			});
		}
	};

	var toggleOpen = function(obj) {// 设置留言公开性
		var status = 1 - $(obj).attr("isOpen");
		$.ajax({
			url: "message/inner/toggleOpen.action",
			type: "post",
			data: "entityId=" + $(obj).attr("alt") + "&status=" + status,
			dataType: "json",
			success: function(result) {
				if (result.errorInfo == null || result.errorInfo == "") {
					$(obj).attr("isOpen", status);
					var $par = $(obj).parent().parent().parent().parent().parent().parent().next();
					if (status) {
						$('div[name="open"]', $par).eq(0).show();
						$(obj).find("img").eq(0).attr("src", "image/g06.gif");
						$(obj).find("img").eq(0).attr("title", "取消外网可见");
					} else {
						$('div[name="open"]', $par).eq(0).hide();
						$(obj).find("img").eq(0).attr("src", "image/g07.gif");
						$(obj).find("img").eq(0).attr("title", "设为外网可见");
					}
				} else {
					alert(result.errorInfo);
				}
			}
		});
	};

	/**
	 * 审核留言
	 */
	function auditMessage() {
		popMessageAudit({
			callBack : function(result){
				if(result){
					var auditStatus = result.auditStatus;
					if(auditStatus){
						$.ajax({
							url: "message/inner/audit.action",
							type: "post",
							data: "entityIds=" + $("#entityId").val() + "&auditStatus=" + auditStatus,
							dataType: "json",
							success: function(result) {
								if (result.errorInfo == null || result.errorInfo == "") {
									view.show(nameSpace, showDetails);
								} else {
									alert(result.errorInfo);
								}
							}
						});
					}
				}
			}
		});
	};

	var showDetails = function(result) {// 显示查看内容
		if (result.errorInfo == null || result.errorInfo == "") {
			$("#entityId").val(result.message.id);
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
	
	exports.init = function() {
		view.show(nameSpace, showDetails);// 公用方法
		view.add(nameSpace);
		view.del(nameSpace, "您确定要删除此留言吗？");
		view.prev(nameSpace, showDetails);
		view.next(nameSpace, showDetails);
		view.back(nameSpace);
		$(".toggleOpen").live("click", function() {
			var that = this;
			toggleOpen(that);
			return false;
		});
		$(".message_audit").live("click", function() {
			auditMessage();
			return false;
		});
		$(".message_modify").live("click", function() {
			window.location.href = basePath + "message/inner/toModify.action?entityId=" + $(this).attr("alt");
			return false;
		});
		$(".message_delete").live("click", function() {
			deleteMessageSingle($(this).attr("alt"));
			return false;
		});
		$(".message_mail").live("click", function() {
			window.location.href = basePath + "mail/toAdd.action?mail.sendTo=" + $(this).attr("alt");
			return false;
		});
		$(".message_quoto").live("click", function() {
			window.location.href = basePath + "message/inner/toAdd.action?entityId=" + $(this).attr("alt") + "&status=2";
			return false;
		});
		$(".message_reply").live("click", function() {
			window.location.href = basePath + "message/inner/toAdd.action?entityId=" + $(this).attr("alt") + "&status=1";
			return false;
		});
	};
});
