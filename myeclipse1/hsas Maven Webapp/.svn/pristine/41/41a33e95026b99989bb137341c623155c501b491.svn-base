/*
 * @author:Liujia
 * @description: 角色添加/修改
 * @date:2015/08/28
 */
define(function(require, exports, module){
	var nameSpace = "role";
	require("validation");
	require("validationInit");
	require('dhtmlxtree');
	require("form");
	function viewTree() {// 加载树
		$("#treeparent").html("<div id='treeId'></div>");
		tree2 = new dhtmlXTreeObject("treeId", "100%", "100%", 0);
		tree2.setImagePath(basePath+"lib/dhtmlxTree_v44_std/imgs/dhxtree_skyblue/"); // 设定图片的路径
		tree2.enableCheckBoxes(1); // 1：为有复选框(做权限)  0：为没有复选框(做部门管理的时候) 
		tree2.enableThreeStateCheckboxes(true); // tree: 假如选中了上级下级也全部选中   false:上级与下级的选择状态无关，不关联
		tree2.setOnClickHandler(tonclick);  // 单击树的时候，做的操作  tonclick为点击的方法名 (类似于回调函数)
		tree2.loadXML("jsp/system/role/treeExample.xml");
		var tonclick = function(id) { // id 直接就是这颗树的id
			$("#text").attr("value", tree2.getItemText(id));// tree2.getItemText(id)
			$("#id").attr("value", id);
		};
	};
	function getnn () {// 生成角色拥有权限字符串
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
	$("#button_query").on("click", function(){
		$("#treeId").empty();
		$("#rightTree").show();
		var keyword = $("#keyword").val();
		var url = "role/createRightTree.action?keyword="+keyword;
		tree2 = new dhtmlXTreeObject("treeId", "100%", "100%", 0);
		tree2.setImagePath(basePath+"lib/dhtmlxTree_v44_std/imgs/dhxtree_skyblue/"); // 设定图片的路径
		tree2.enableCheckBoxes(1); // 1：为有复选框(做权限)  0：为没有复选框(做部门管理的时候) 
		tree2.enableThreeStateCheckboxes(true); // tree: 假如选中了上级下级也全部选中   false:上级与下级的选择状态无关，不关联
		tree2.setOnClickHandler(tonclick);  // 单击树的时候，做的操作  tonclick为点击的方法名 (类似于回调函数)
		tree2.loadXML(encodeURI(encodeURI(url)));
		var tonclick = function(id) { // id 直接就是这颗树的id
			$("#text").attr("value", tree2.getItemText(id));// tree2.getItemText(id)
			$("#id").attr("value", id);
		};
	});
	
	
	$("#cancel").click(function(){
		window.history.go(-1);
	});
	$("#submit").click(function(){
		$("#rightNodeValues").val(getnn());// 设置权限节点字符串
		$("#form_role").submit();
	});
	exports.init = function(){
		$("#form_role").validationEngine({
			scroll: false,
			isOverflown:true,
			validateNonVisibleFields: true,
			promptPosition: 'centerRight',
			addPromptClass: 'formError-noArrow formError-text'
		}).ajaxForm({
			type: "post",
			dataType: "json",
			beforeSubmit: function(){
			},
			success: function(data) {
				if (data.tag == "1") {
					dialog({
						title: '提示',
						content: '角色添加成功!',
						okValue: '确定',
						width: 260,
						ok: function() {
							window.location.href = nameSpace + "/toList.action";
						},
						onclose: function(){
							window.location.href = nameSpace + "/toList.action";
						}
					}).showModal();
				} else {
					dialog({
						title: '提示',
						content: '角色添加失败!',
						width: 260,
						okValue: '确定',
						ok: function() {
//							window.location.reload();
						}
					}).showModal();
				}
			}
		});
		 viewTree();
	}
});