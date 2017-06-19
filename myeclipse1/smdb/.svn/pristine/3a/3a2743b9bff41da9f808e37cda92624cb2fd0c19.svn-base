define(function(require, exports, module) {
	var view = require('javascript/view');
	var list = require('javascript/list');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('javascript/template_tool');
	/**
	 * 重载全局view方法，添加账号模块特有的按钮事件。
	 * 主要包括：重置密码(retri)、分配角色(distr)、启用(enable)、停用(disable)、修改密码(modpass)。
	 * @param {string} nameSpace
	 */
	var retri = function(nameSpace){
		$("#view_retri").live("click", function() {
			if (confirm('您确定要给本账号生成密码重置码吗？生成重置码后，该账号用户可以通过指定的链接重置密码。')) {
				$("#view").ajaxSubmit({
					url: nameSpace + "/retrieveCode.action",
					type: "post",
					dataType: "json",
					success: function(result) {
						if (result.errorInfo == null || result.errorInfo == "") {
							alert("密码重置成功");
						} else {
							alert(result.errorInfo);
						}
					}
				});
			}
			return false;
		});
	}


	var modpass = function(nameSpace){
		$("#view_modpass").live("click", function() {
			popPasswordModify({
				src : nameSpace +"/toModifyPassword.action?entityId=" + $("#entityId").val(),
				callBack : function(layer, flag){
					if (flag) {
						setTimeout("alert('修改密码成功');", 200);
					} else {
						setTimeout("alert('修改密码失败');", 200);
					}
					layer.destroy();
				}
			});
			return false;
		});
	}
	
	/**
	 * 初始化日志列表
	 * 包括列表数据的初始化和弹层查看点击事件的绑定
	 * @memberOf {实现列表初始化方法} 
	 */
	var initLog = function() {
		list.pageShow({
			"nameSpace":"logEmbed",
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3},
			"listType":3
		});
		
		$(".link2").live("click", function() {
			popLog(this.id);
			return false;
		});
		$(".j_viewDetail").live("mouseover",function() {
			var ip = this.id;
			$(this).css({
				"cursor":"default"
			});
			$("#j_view").show();
			$.ajax({
				url: "log/getIpAddress.action",
				type: "post",
				data: "ip=" + ip,
				dataType: "json",
				success: function(result) {
					if (result.code == 0) {
						$("#j_view").empty();
						$("#j_view").append("IP地址：").append(result.city);
					} else {
						$("#j_view").empty();
						$("#j_view").append("IP地址：").append("未知");
					}
				}
			});
		}).live("mouseout",function(){
			$("#j_view").empty();
			$(this).css({
				"cursor":""
			});
			$("#j_view").css({
				"display":"none"
			});
		});
	};
	
	
	/**
	 * 显示查看内容
	 * @param {Object} result
	 */
	function showDetails(result) {
		image("image");
		if (result.errorInfo == null || result.errorInfo == "") {
			$("#entityId").attr("value", result.passport.id);
			$("#entityIds").attr("value", result.passport.id);
			$("#passportId").attr("value", result.passport.id);
			// 目前查看页面分标签显示初始化模板有两种方法，一是下面的按顺序灌入模板，二是调用template_tool.js
			Template_tool.populate(result);
			initImage("image",$(".link_bar").eq(0).attr("id"));
			setOddEvenLine("account", 0);
			if (typeof(result.userList) == "undefined" || result.userList == 0) {
				setColspan("list_account");
			}
			showNumber();
			var td_width = 120;
			var td_height = 133;
			var img = new Image();
			img.src = $("#photo img").attr("src");
			$("#photo img").width(td_width);
			$("#photo img").load(function(){
				$(this).removeAttr("style");
				if (img.width / img.height > td_width / td_height){
					$(this).width(td_width);
				} else {
					$(this).height(td_height);
				}
			});
			
			$("#view_choose_bar").html(TrimPath.processDOMTemplate("view_choose_bar_template", result));
			$("#view_common").html(TrimPath.processDOMTemplate("view_common_template", result));
			if ($("#accountType").val() == "ADMINISTRATOR") {
				$("#view_firewall").html(TrimPath.processDOMTemplate("view_firewall_template", result));
				$("#view_log").html(TrimPath.processDOMTemplate("view_log_template", result));
			}
			view.inittabs();// 初始化以上标签
			$("#view_content").show();
			// 如果有，则显示提示信息
			var pageInfo = $("#pageInfo").val();
			if (pageInfo != undefined && pageInfo != "") {
				alert(pageInfo);
				$("#pageInfo").val("");
			}
			// 重新加载日志信息
			currentPageNo = 1;
			startPageNumber = 1;
			list.getData();
		} else {
			alert(result.errorInfo);
		}
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	}
	
	/**
	 * 对外接口。
	 * 初始化账号查看页面及按钮，主要包括：
	 * 1. 调用公共view的部分：添加、修改、删除、上条、下条、返回。
	 * 2. 账号模块view特有的：启用、停用、分配角色、重置密码、修改密码、初始化日志列表。
	 * @param {string} nameSpace
	 * @param (String) del_confirm(删除时的警告)
	 */
	exports.init = function(nameSpace) {
		Template_tool.init();
		var nameSpace = "passport";
		view.show(nameSpace, showDetails);// 查看
		view.mod(nameSpace);// 修改
		view.del(nameSpace, "您确定要删除此账号吗？");// 删除
		view.prev(nameSpace, showDetails);// 上一条
		view.next(nameSpace, showDetails);// 下一条
		view.back(nameSpace);// 所有查看页面公共部分
		retri(nameSpace);// 重置密码
		modpass(nameSpace);// 修改密码
		initLog();// 加载日志信息
		$(".linkA").live("click", function() {
			popAgency(this.id, 1);
			return false;
		});
		$(".linkP").live("click", function() {
			var type = this.type;
			popPerson(this.id, type);
			return false;
		});
		
		
		$(".linkD").live("click", function() {
			popDept(this.id, 2);
			return false;
		});
		$(".linkI").live("click", function() {
			popInst(this.id, 3);
			return false;
		});
	};
});