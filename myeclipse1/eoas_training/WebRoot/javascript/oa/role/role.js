$(document).ready(function(){	
	getRightTree();
});

var getRightTree = function() {// 获得权限树
	tree2 = new dhtmlXTreeObject("treeId", "100%", "100%", 0);
	tree2.setSkin('dhx_skyblue');
	tree2.setImagePath("/eoas/image/tree/csh_bluebooks/"); // 设定图片的路径
	tree2.enableCheckBoxes(1); // 1：为有复选框(做权限)  0：为没有复选框(做部门管理的时候) 
	tree2.enableThreeStateCheckboxes(1); // tree: 假如选中了上级下级也全部选中   false:上级与下级的选择状态无关，不关联
	tree2.setOnClickHandler(tonclick);  // 单击树的时候，做的操作  tonclick为点击的方法名 (类似于回调函数)
	tree2.loadXML("role/createRightTree.action");
	var tonclick = function(id) { // id 直接就是这颗树的id
		$("#text").attr("value", tree2.getItemText(id));// tree2.getItemText(id)
		$("#id").attr("value", id);
	};
};

var getnn = function() {// 生成角色拥有权限字符串
	var tree = tree2.getAllChecked();
	tree = tree.split(',');
	var str = '';
	for (var i = 0; i < tree.length; i++) {
		if (tree2.getOpenState(tree[i]) == 0)
			str+=tree[i]+',';
	}
	str = str.substr(0, str.length-1);
	return str;
};

$("#submit").click(function(){
	alert(getnn());
	$("#rightNodeValues").val(getnn());// 设置权限节点字符串
});