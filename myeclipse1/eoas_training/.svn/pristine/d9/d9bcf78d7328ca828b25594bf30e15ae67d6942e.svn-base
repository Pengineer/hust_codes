//定义zTree对象
var zTreeObj;
var json;
//zTree配置
var setting = {
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom
	},
	key:{
		children: "nodeValue",
		name : "name"
	},
/*	check: {
		enable: true,
		chkStyle: "radio",
		chkboxType: { "Y": "ps", "N": "ps" }
	},*/
	data: {
		simpleData: {
			enable: true,	//确定 zTree 初始化时的节点数据、异步加载时的节点数据、或 addNodes 方法中输入的 newNodes 数据是否采用简单数据模式 (Array)
			idKey: "id",
			pIdKey: "pId"
		}
	},
	callback:{
        onCheck:onCheck
    },
	edit:{
		enable:true,
		removeTitle: "删除节点",
		renameTitle: "编辑节点名称",
		showRemoveBtn: true,
		showRenameBtn: true
	}
};

$(document).ready(function(){
	$.ajax({
		type:"post",
		url: "department/view.action",
		dataType:"json",
		success:function(result){
			json = result.tree;
			zTreeObj = $.fn.zTree.init($("#depTree"), setting, result.tree);	//初始化zTree
		}
	});
	
	$("#data_submit").on("click", function () {
		if (confirm("提交将保存之前所做修改，确定继续操作吗？")) {
			
			/*var zTreeObj = $.fn.zTree.getZTreeObj("depTree");*/
			var nodes = zTreeObj.getNodes();
			var nodesString = JSON.stringify(nodes);
			$.ajax ({ 
				url: "department/modify.action", 
				type: "post", 
				data: "nodesString=" + nodesString,
				success: function (flag) {
					if (flag) {
						alert('aaa');
						window.location.href = "localhost:8080/eoas/department/view.action";//type:1链接进入；2提交修改后刷新。
					} else {
						alert("修改导航数据失败！");
					}
				}
			});
		}
	});
});

//定义节点命名计数变量
var newCount = 1;

function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
	+ "' title='添加' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		var zTree = $.fn.zTree.getZTreeObj("depTree");
		zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
		return false;
	});
};
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};


function onCheck(e,treeId,treeNode){
    var treeObj=$.fn.zTree.getZTreeObj("depTree");
    var id = "";
    var name = "";
    var depSelected = "";
    $("#depSelected").empty();
    nodes=treeObj.getCheckedNodes(true),
    v="";
    for(var i=0;i<nodes.length;i++){
    v+=nodes[i].name + ",";
    id = nodes[i].id;
    name = nodes[i].name;
    }
    depSelected += "<span name = 'departmentId'  value ='"+id+"'>";
    /*depSelected += "<span name='"+id+"'>";*/
    depSelected += name+"</span>";
    $("#depSelected").append(depSelected);
    $("#departmentInfo").append("<input name = 'departmentId'  value ='"+id+"'/>" + "</input>");
};

$("#save").click(function(){
	  $("#depSelected").show();
	});