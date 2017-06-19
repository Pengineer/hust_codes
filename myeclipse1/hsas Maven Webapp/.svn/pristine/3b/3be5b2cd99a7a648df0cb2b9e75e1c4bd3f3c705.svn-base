/*
 * @author:Liujia
 * @description: 角色常看
 * @date:2015/08/28
 */
define(function(require, exports, module){
	var view = require("view");
	require('dhtmlxtree');
	var nameSpace = "role";
	var showDetail = function(results){
		if (results.errorInfo == null || results.errorInfo == "") {
			$("#entityId").val(results.role.id);
	        $("#view_container").hide();
	        $("#view_container").html(TrimPath.processDOMTemplate("view_template",results.role))
	        viewTree();
	        $("#view_container").show();
		} else {
			alert(results.errorInfo);
		}
		  $.isLoading("hide");//关闭加载信息
	}
	var viewTree = function() {// 加载树
		$("#treeparent").html("<div id='treeId'></div>");
		tree2 = new dhtmlXTreeObject("treeId", "100%", "100%", 0);
		tree2.setImagePath(basePath+"lib/dhtmlxTree_v44_std/imgs/dhxtree_skyblue/"); // 设定图片的路径
		tree2.enableCheckBoxes(0); // 1：为有复选框(做权限)  0：为没有复选框(做部门管理的时候) 
		tree2.enableThreeStateCheckboxes(false); // tree: 假如选中了上级下级也全部选中   false:上级与下级的选择状态无关，不关联
		tree2.setOnClickHandler(null);  // 单击树的时候，做的操作  tonclick为点击的方法名 (类似于回调函数)
		tree2.loadXML("jsp/system/role/treeExample.xml");
	};
	exports.init = function(){
		view.show(nameSpace,showDetail);
    	view.add(nameSpace);
    	view.mod(nameSpace);
    	view.del(nameSpace);
    	view.next(nameSpace,showDetail);
    	view.prev(nameSpace,showDetail);
    	view.back(nameSpace);
	}
});