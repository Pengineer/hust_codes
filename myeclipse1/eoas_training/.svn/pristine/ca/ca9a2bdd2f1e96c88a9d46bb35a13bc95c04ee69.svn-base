$(document).ready(function(){
	viewTree();
});

var viewTree = function() {// 加载树
	$("#treeparent").html("<div id='treeId'></div>");
	tree2 = new dhtmlXTreeObject("treeId", "100%", "100%", 0);
	tree2.setSkin('dhx_skyblue');
	tree2.setImagePath("/eoas/image/tree/csh_bluebooks/"); // 设定图片的路径
	tree2.enableCheckBoxes(0); // 1：为有复选框(做权限)  0：为没有复选框(做部门管理的时候) 
	tree2.enableThreeStateCheckboxes(false); // tree: 假如选中了上级下级也全部选中   false:上级与下级的选择状态无关，不关联
	tree2.setOnClickHandler(null);  // 单击树的时候，做的操作  tonclick为点击的方法名 (类似于回调函数)
	tree2.loadXML("role/createRightTree.action?roleId=" + $("#roleId").val());
};

$(function(){
	$("#view_add").click(function(){
		window.location.href = "role/toAdd.action";
	});
	$("#view_mod").click(function(){
		window.location.href = "role/toModify.action?roleId=" + $("#roleId").val();
	});
	$("#view_del").click(function(){
		dialog({
		    title: '提示',
		    content: '确定要删除吗?',
		    width:250,
		    okValue: '确定',
		    ok: function () {
		    	var url = "role/delete.action?roleId=" + $("#roleId").val();
		    	$.get(url, function(data){
		    		if(data.result == "1"){
		    			dialog({
		    			    title: '提示',
		    			    content: '删除成功',
		    			    width:250,
		    			    okValue: '确定',
		    			    ok: function () {
		    			    	window.location.href = "role/toList.action";
		    			    }
		    			}).showModal();
		    		} else {
		    			dialog({
		    			    title: '提示',
		    			    content: '删除失败!',
		    			    width:250,
		    			    okValue: '确定',
		    			    ok: function () {
		    			    	window.location.href = "role/toList.action";
		    			    }
		    			}).showModal();
		    		}
		    	})

		    	
		    },
		    cancelValue: '取消',
		    cancel: function () {}
		}).showModal();
	});
	$("#view_back").click(function(){
		window.location.href = "role/toList.action";
	});
});