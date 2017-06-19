define(function(require, exports, module) {
	var view = require('javascript/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('javascript/template_tool');
	
	var nameSpace = "management/recruit/applicant";


	// 返回列表
	$("#view_back").click(function(){
		window.location.href = nameSpace + "/toList.action";
	});
	//上一条记录
	$("#view_prev").click(function(){
		if (parent != null) {
			parent.loading_flag = true;
			setTimeout("parent.showLoading();", parent.loading_lag_time);
		};
		$.ajax({
			url: nameSpace + "/prev.action",
			data: "entityId=" + $("#entityId").val(),
			type: "post",
			success: function(json){
				parent.loading_flag = false;
				if(json.errorInfo){
					alert(json.errorInfo)
				} else{
					$("div[id*='view_container_']").remove();
					showDetails(json);
					$("#entityId").val(json.appId);
				}
			}
		})
	});
	//上一条记录
	$("#view_next").click(function(){
		if (parent != null) {
			parent.loading_flag = true;
			setTimeout("parent.showLoading();", parent.loading_lag_time);
		};
		$.ajax({
			url: nameSpace + "/next.action",
			data: "entityId=" + $("#entityId").val(),
			type: "post",
			success: function(json){
				parent.loading_flag = false;
				if(json.errorInfo){
					alert(json.errorInfo)
				} else{
					$("div[id*='view_container_']").remove();
					showDetails(json);
					$("#entityId").val(json.appId);
				}
				
			}
		})
	});
	function popSetStatus(args){
		new top.PopLayer({"title" : "审核意见", "src" : args.src, 
			"document" : top.document, "inData" : args.inData, "callBack" : args.callBack});
	}
	var init_view = function(nameSpace) {
		$(function() {
			view.show(nameSpace, showDetails);
			$("#setVerifyResult").live("click",function(){
				var args = {
						src :"management/recruit/applicant/toVerify.action",
						callBack:function(data){
							var status;
							
							switch (data.status){
							case "1": status = " <span  id = 'setVerifyResult' ><a style = 'color:#31B0D5' title = '点击修改审核意见' href = ''>审核通过</span>"; break;
							case "2": status = "<span id = 'setVerifyResult'><a style = 'color:#D9534F' title = '点击修改审核意见' href = ''>审核不通过</span>"; break;
							}
							$(".status").each(function(){
								$(this).html(status);
							});
						},//回调函数，用于在弹层里更该数据后在负页面更新数据。
						inData:{
							entityId: $("#appId").val()
						}//将id传给弹层。
				}
				
				popSetStatus(args);
				return false;
			});
		});
	};
	

	var showDetails = function(result) {// 显示查看内容
		Template_tool.init();
//		view.inittabs($("#selectedTab").val(), 1);
		Template_tool.populate(result);
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	};
	
	exports.init = function() {
		init_view(nameSpace);
	};
	
});

