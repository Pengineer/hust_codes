/**
 * 招聘的模板详情页面
 * @author liujia
 *
 */
define(function(require, exports, modules) {
	var nameSpace = "management/recruit/template";
	// 添加
	$("#view_add").click(function() {
		window.location.href = nameSpace + "/toAdd.action";
	});
	//删除
	$("#view_del").click(function() {
		if (confirm("确定要删除选中的记录吗？")) {
			entityIds = "entityIds=" + $("#entityId").val();
			$.ajax({
				url: nameSpace + "/delete.action",
				data: entityIds,
				type: "post",
				success: function() {
					window.location.href = nameSpace + "/toList.action?listType=3";
				}
			});
		}
	});
	// 返回列表
	$("#view_back").click(function(){
		window.location.href = nameSpace + "/toList.action?listType=3";
	});
	//修改
	$("#view_mod").click(function() {
		window.location.href = nameSpace + "/toModify.action?entityId=" + $("#entityId").val();
	});
	//上一条记录
	$("#view_prev").click(function(){
		$.ajax({
			url: nameSpace + "/prev.action",
			data: "entityId=" + $("#entityId").val(),
			type: "post",
			success: function(json){
				if(json.errorInfo){
					alert(json.errorInfo)
				} else{
					view(json);
				}
			}
		})
	});
	//上一条记录
	$("#view_next").click(function(){
		$.ajax({
			url: nameSpace + "/next.action",
			data: "entityId=" + $("#entityId").val(),
			type: "post",
			success: function(json){
				if(json.errorInfo){
					alert(json.errorInfo)
				} else{
					view(json);
				}
				
			}
		})
	});
	var view = function(json){
		$("#entityId").val(json.id);
		$("#templateName").html(json.name);
		$("#description").html(json.description);
		$("#uploadDate").html(json.date.substring(0, 10));
		if (json.fileSize) {
			$("#templateDownload").html(function() {
				var url = nameSpace + '/download.action?entityId=' + json.id;
				return "<a href='" + url + "'>" + "下载模版文件（" + json.fileSize + "）" + "</a>";
			})
		} else {
			$("#templateDownload").html(function() {
				return "<a href = 'javascript:void(0)' title = '文件不存在'>" + "下载模版文件（文件不存在！）" + "</a>";
			})
		}
	}//查看函数
	exports.init = function() {
		$(function() {
			$.ajax({
				url: nameSpace + "/view.action",
				data: "entityId=" + $("#entityId").val(),
				type: "post",
				dataType: "json",
				success: function(json) {
					view(json);
				}
			});// 填充数据
		});
	}
})