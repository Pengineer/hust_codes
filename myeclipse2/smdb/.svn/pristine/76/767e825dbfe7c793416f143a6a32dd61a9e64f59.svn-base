define(function(require, exports, module) {
	var view = require('javascript/view');
	require('tool/poplayer/js/pop');
	require('pop-self');

	var nameSpace = "mail";

	var showDetails = function(result) {// 显示查看内容
		if (result.errorInfo == null || result.errorInfo == "") {
			result.mail.sended = listFormat(result.mail.sended);
			result.mail.sendTo = listFormat(result.mail.sendTo);
			$("#entityId").attr("value", result.mail.id);
			$("#entityIds").attr("value", result.mail.id);
			if (result.mail.status == 0) {
				$("#view_send").show();// 发送按钮
				$("#view_cancel").show();// 取消按钮（永久不发）
				$("#view_pause").hide();// 暂停按钮
			} else if (result.mail.status == 1) {
				$("#view_pause").show();
				$("#view_cancel").hide();
				$("#view_send").hide();
			} else if (result.mail.status == 2) {
				$("#view_send").hide();
				$("#view_cancel").hide();
				$("#view_pause").show();
			} else if (result.mail.status == 3) {
				$("#view_again").show();// 重发按钮
				$("#view_send").hide();
				$("#view_cancel").hide();
				$("#view_pause").hide();
			} else {
				$("#view_pause").hide();
				$("#view_send").hide();
				$("#view_again").hide();
				$("#view_cancel").hide();
			}
			if (result.mail.attachmentName != null
					&& result.mail.attachmentName != "") {
				result.mail.attachmentName = result.mail.attachmentName
						.split("; ");
			}
			$("#view_container").hide();
			$("#view_container").html(
					TrimPath.processDOMTemplate("view_template", result));
			$("#accountid").attr("value", result.accountid);
			$("#accounttype").attr("value", result.accounttype);
			$("#isPrincipal").attr("value", result.isPrincipal);
			$("#view_container").show();
		} else {
			alert(result.errorInfo);
		}
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	};

	/**
	 * 邮件查看页面功能扩展：重发，暂停，手动发送,永久取消发送
	 * 
	 * @param {Object}
	 *            nameSpace
	 * @return {TypeName}
	 */
	var view_again = function(nameSpace) {
		$("#view_again").bind(
				"click",
				function() {
					if (confirm("您确定要重新发送此邮件吗？")) {
						$.ajax({
							url : nameSpace + "/sendAgain.action",
							type : "post",
							data : "entityId=" + $("#entityId").val(),
							dataType : "json",
							success : function(result) {
								alert(result.errorInfo);
								window.location.href = basePath + nameSpace
										+ "/toView.action?entityId="
										+ $("#entityId").val();
								return false;
							}
						});
					}
					return false;
				});
	};

	var view_pause = function(nameSpace) {// 暂停发送
		$("#view_pause").bind(
				"click",
				function() {
					if (confirm("您确定要暂停发送此邮件吗？")) {
						$.ajax({
							url : nameSpace + "/pauseSend.action",
							type : "post",
							data : "entityId=" + $("#entityId").val(),
							dataType : "json",
							success : function(result) {
								alert(result.errorInfo);
								window.location.href = basePath + nameSpace + "/toList.action?update=1&entityId=" + $("#entityId").val();
								return false;
							}
						});
					}
					return false;
				});
	};

	var view_send = function(nameSpace) {// 手动发送
		$("#view_send").bind(
				"click",
				function() {
					if (confirm("您确定要手动发送此邮件吗？")) {
						$.ajax({
							url : nameSpace + "/send.action",
							type : "post",
							data : "entityId=" + $("#entityId").val(),
							dataType : "json",
							success : function(result) {
								alert(result.errorInfo);
								window.location.href = basePath + nameSpace+ "/toView.action?entityId="+ $("#entityId").val();
								return false;
							}
						});
					}
					return false;
				});
	};

	var view_cancel = function(nameSpace) {
		$("#view_cancel").bind(
				"click",
				function() {// 永久取消发送邮件
					if (confirm("您确定要永久取消发送此邮件吗？")) {
						$.ajax({
							url : nameSpace + "/cancel.action?entityId="
									+ $("#entityId").val(),
							type : "post",
							dataType : "json",
							success : function(result) {
								alert(result.errorInfo);
								window.location.href = basePath + nameSpace + "/toList.action?update=1&entityId=" + $("#entityId").val();
								return false;
							}
						});
					}
					return false;
				});
	};

	// ==============================================================
	// 方法名: listFormat
	// 方法描述: 本方法主要实现邮件收件人list格式化
	// 返回值：格式化后的list(每两个一行，每个用;隔开)
	// ==============================================================
	function listFormat(ListString) {
		if (ListString == null || ListString == "") {
			return ListString;
		} else {
			var array = ListString.split(/\s+/);
			var tmp = "";
			for ( var i = 0; i * 2 < array.length; i++) {
				tmp += "<tr><td width='225px'>" + array[i * 2]
						+ "</td><td width='225px'>"
						+ (i * 2 + 1 < array.length ? array[i * 2 + 1] : "")
						+ "</td></tr>";
			}
			return tmp;
		}
	}
	;

	exports.init = function() {
		view.show(nameSpace, showDetails);// 公用方法
		view.add(nameSpace);
		view.del(nameSpace, "您确定要删除此邮件吗？");
		view.prev(nameSpace, showDetails);
		view.next(nameSpace, showDetails);
		view.back(nameSpace);
		view.download(nameSpace);
		view_again(nameSpace);// 模块定义
		view_pause(nameSpace);
		view_cancel(nameSpace);
		view_send(nameSpace);
		$(".link2").live("click", function() {
			popAccount(this.id);
			return false;
		});
	};
});
