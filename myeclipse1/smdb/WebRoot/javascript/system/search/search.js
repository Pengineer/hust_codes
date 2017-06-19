/**
 * 文件说明：完成全站检索前端显示
 * @author liujia
 */
define(function(require, exports, module) {
	var tabs = require("javascript/system/search/tabs.js");
	var nameSpace = "system/search";
	exports.init = function() {	
		var isInit = $("#isInit").val()
		if(isInit==1){
			$.ajax({
				url: nameSpace + "/isExistIndex.action",
				data: "searchRange=all",
	 			type: "post",
	 			dataType: "json",
	 			success: function(result) {
	 				if (result.errorInfo == null || result.errorInfo == "") {
	 					$("#search_range_list").val(function(){
							return $("#search_range_tabs").val();
						});
						$("#search_keyword_list").val(function(){
							return $("#search_keyword_tabs").val();
						});
						if(tabs.flag) {
							tabs.init(); 
						}
						else {
							tabs.getData();
						}
	 				} else {
	 					alert(result.errorInfo);
	 				}
	 			}
			});
		}
		$("#update").click(function(){	
			var searchRange = $("#search_range_tabs").val();
			$("#updating").css("display", "inline-block");
			$("#update").attr("disabled", true).css({
				"color":"rgb(128, 128, 128)"
			});
			$.ajax({
				url: nameSpace + "/updateIndex.action",
				data: "searchRange=" + searchRange,
	 			type: "post",
	 			dataType: "json",
	 			success: function(result) {
	 				if (result.errorInfo == null || result.errorInfo == "") {
	 					alert(result.hintInfo);
	 				} else {
	 					alert(result.errorInfo);
	 				}
					$("#updating").css("display", "none");
					$("#update").attr("disabled", false).css({
						"color":"rgb(68, 68, 68)"
					});
	 			}
			});
			
		});//更新索引
		
		$("#searchAll").click(function(){
			var searchRange = $("#search_range_tabs").val();
			$.ajax({
				url: nameSpace + "/isExistIndex.action",
				data: "searchRange=" + searchRange,
	 			type: "post",
	 			dataType: "json",
	 			success: function(result) {
	 				/*$("#container_box").hide();
	 				$("#loading").css({left:function(){
	 					return $(document.body).width() * 0.41 + "px";
	 				},top:"38px"}).show();*/
	 				if (result.errorInfo == null || result.errorInfo == "") {
	 					$("#search_range_list").val(function(){
							return $("#search_range_tabs").val();
						});
						$("#search_keyword_list").val(function(){
							return $("#search_keyword_tabs").val();
						});
						if(tabs.flag) {
							tabs.init(); 
						}
						else {
							tabs.getData();
						}
	 				} else {
	 					alert(result.errorInfo);
	 				}
	 			}
			});
			
		});//判断索引是否存在，若存在则进行检索工作，若不存在则进行更新索引工作。
			
	};
});