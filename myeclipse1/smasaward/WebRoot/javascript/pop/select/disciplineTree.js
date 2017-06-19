
//定义学科代码树
var zTreeObj;
// zTree 的参数配置，（setting 配置详解）
var setting = {
		view: {
			selectedMulti: false
		},
		async : {
			enable : true,
			url : "pop/select/disciplineTree/createDisciplineTree.action",
			autoParam:["id"]
		},
		check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType: { "Y": "", "N": "" }
		},
		callback: {
			onCheck: zTreeOnCheck
		}
};
				
$(document).ready(function(){
    zTreeObj = $.fn.zTree.init($("#discipline_tree"), setting);

    defualtSelectDisp();
    
    $(".del_discipline").live("click", function() {
		deleteDiscpById(this.id);
		return false;
	});
    
    $("#confirm").unbind("click").click(function() {
    	var result = {"codes" : $("#hiddenCode").val(), "codeNames" : $("#hiddenCodeName").val()}
    	thisPopLayer.callBack(result);
		thisPopLayer.destroy();
	});
});

//节点勾选事件
function zTreeOnCheck(event, treeId, treeNode){
	nodeOperation(treeNode);
	showDetail(null);// 显示已选项
}

/**
 * 设置当前节点节点选中关联的父节点
 * @param {Object} treeNode 当前节点
 */
function nodeOperation(treeNode){
	var parentNodes = [];//当前节点的所有父节点
	var parentNode = treeNode.getParentNode();
	while(parentNode != null){
		parentNodes.push(parentNode);
 		parentNode = parentNode.getParentNode();
	}
	if(treeNode.checked){//当前节点勾选上 true
		for(var i=0, len=parentNodes.length; i<len;i++){
			parentNodes[i].checked = false;
			parentNodes[i].chkDisabled = true;
			zTreeObj.updateNode(parentNodes[i], true);
		}
	}else{//当前节点勾选取消 false
		for(var i=0, len=parentNodes.length; i<len;i++){
			parentNodes[i].checked = false;
			parentNodes[i].chkDisabled = false;
			zTreeObj.updateNode(parentNodes[i], true);
		}
	}
}
 
 /**
  * 通过删除按钮取消
  * @param {Object} id	学科代码id
  */
 function deleteDiscpById(id) {
	var node = zTreeObj.getNodesByParam("id", id, null);
	if(node != null && node.length > 0){
		node[0].checked = false;
		zTreeObj.updateNode(node[0], true);
		nodeOperation(node[0]);
	}
	
	var newIdCodeName = [];
	var oldIdCodeName = $("#hiddenIdCodeName").val().split("; ");
	for(var i = 0, len = oldIdCodeName.length; i < len; i++){
		if(oldIdCodeName[i].indexOf(id) < 0){
			newIdCodeName.push(oldIdCodeName[i]);
		}
	}
	showDetail(newIdCodeName.join("; "));// 显示已选项
 }
  
/**
 * 显示已选学科
 * @param {Object} idCodeName 学科代码名称字符串
 */
function showDetail(idCodeName) {
	var idCodeNames = [];
	var codeNames = [];
	var codes = [];
	if(idCodeName != null && idCodeName != "") {
		// 将学科代码名称拆分为数组
		idCodeNames = idCodeName.split('; ');
		for(var i = 0, len = idCodeNames.length; i < len; i++){
			codeNames.push(idCodeNames[i].substring(idCodeNames[i].indexOf("%")+1));
		}
	}else if(idCodeName == null){
		var allCheckedNodes = zTreeObj.getCheckedNodes(true);
		for(var i = 0, len = allCheckedNodes.length; i < len; i++){
			idCodeNames.push(allCheckedNodes[i].id + "%" + allCheckedNodes[i].name);
			codeNames.push(allCheckedNodes[i].name);
		}
	}
	for(var i = 0, len = codeNames.length; i < len; i++){
		var codeName = codeNames[i];
		var code = codeName.substring(0, codeName.indexOf("/"));
		codes.push(code);
	}
	$("#hiddenIdCodeName").val(idCodeNames.join("; "));	// id + code + name
	$("#hiddenCodeName").val(codeNames.join("; "));		// code + name
	$("#hiddenCode").val(codes.join("; "));				// code
	$("#showselect").empty();
	if(idCodeNames != null && idCodeNames.length > 0) {
		// 将学科代码名称拆分为数组
		for(var i = 0, len= idCodeNames.length; i < len; i++) {
			var num = i+1;
			var code_name = idCodeNames[i].split("%");
			var del_link = $("<a id='" + code_name[0] + "' class='del_discipline' href='' title='点击删除此项'><img src='image/stop_medium.gif' /></a>");
			var row = $("<tr></tr>");// 一行
			var cell1 = $("<td></td>");// 一列
			var cell2 = $("<td></td>");// 一列
			var cell3 = $("<td></td>");// 一列
			cell1.append(num);
			cell2.append(code_name[1]);
			cell3.append(del_link);
			row.append(cell1).append(cell2).append(cell3);
			$("#showselect").append(row);
		}
	}
}


// 勾选默认选中，显示默认选中
function defualtSelectDisp(){
	var codes = thisPopLayer.inData;
	if(codes != null && codes != ''){
		$.ajax({
			url : "pop/select/disciplineTree/getDispIdCodeNameByCode.action?codes=" + codes,
			dataType : "json",
			method : "post",
			success : function(result){
				showDetail(result.idCodeName);
			}
		});
	}
} 
