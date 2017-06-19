define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	/**
	 * 初始化列表链接，主要包括：查看项目所属高校、项目负责人
	 * 删除按钮在list.js中统一绑定。
	 */
	exports.initListButton = function(nameSpace) {
		$(".view_university").live("click", function() {
			popAgency(this.id, 1);
			return false;
		});
		
		$(".linkDirectors").live("click", function() {
			popPerson(this.id, 7);
			return false;
		});
		$("#mainFlag").bind("change", function() {
			$("#search").attr("action", nameSpace + "/simpleSearch.action");
			$("#search").submit();
			return false;
		});
		$("#list_publish").live("click",function(){// 批量发布按钮
			popProjectOperation({
	     		title : "发布数据",
	     		inData:{
	     			nameSpace:nameSpace
	     		},
	     		src : nameSpace + "/toBatchPublish.action",
	     		callBack : function(data){
	     				
	     		}
	     	});
		});
		/**
		$("#list_publish").live("click", function() {// 发布按钮
			var cnt = count("entityIds");
			if (cnt == 0) {
				alert("请选择要发布的记录！");
			} else {
				if (confirm("您确定要公开发布选中的记录吗？")) {
					if($("#checkedIds").length > 0){
						$("#checkedIds").attr("value", "");
					};
					$("#list").attr("action", nameSpace + "/publish.action");
					$("#list").submit();
				}
			}
			return false;
		});
		**/
		$("#list_notPublish").live("click", function() {// 取消发布按钮
			var cnt = count("entityIds");
			if (cnt == 0) {
				alert("请选择要取消发布的记录！");
			} else {
				if (confirm("您确定要取消公开发布选中的记录吗？")) {
					if($("#checkedIds").length > 0){
						$("#checkedIds").attr("value", "");
					};
					$("#list").attr("action", nameSpace + "/notPublish.action");
					$("#list").submit();
				}
			}
			return false;
		});
	};
});