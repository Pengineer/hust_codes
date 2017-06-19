/**
 * @author 江荣国
 */

/**
 * 生成学科树
 * @param {Object} value 一级学科代码
 */
function loadDiscpTree(value) {
	if(value != null && value != "" && value != -1) {
		$("#loading").show();
		$("#treeId").hide();
		var ids = $("#hiddenId").attr("value");
		try {
			tree2.destructor();
		} catch(error) {
		}
		tree2 = new dhtmlXTreeObject("treeId", "100%", "100%", 0);
		tree2.setSkin("dhx_skyblue");
		tree2.setImagePath(basePath+"/image/tree/csh_bluebooks/");
		tree2.enableCheckBoxes(1);
		tree2.enableThreeStateCheckboxes(false);
		
		url = "unit/institute/createDisciplineTree.action?hiddenId=" + ids + "&discpId=" + value;
		tree2.loadXML(url, showTree);
//		$(".containerTableStyle").eq(0).css("width", "225px");
		$(".containerTableStyle").eq(0).find("table").each(function() {
			$(this).css("width", "");
			$(this).attr("width", "");
		});
		$(".containerTableStyle").eq(0).css("overflow", "scroll");
		$("#expList").show();
		tree2.setOnCheckHandler(showDisc);
		tree2.setItemStyle()
	} else {
		$("#loading").hide();
		$("#treeId").hide();
	}
}

/**
 * 显示学科树，设置默认选中
 */
function showTree() {
	$("#loading").hide();
	$("#treeId").show();
	// 获取已选学科代码及名称，拆分为数组
	var ids = $("#hiddenId").attr("value");
	var idCodeName = ids.split("; ");
	var id = new Array(idCodeName.length);
	for(var i = 0; i < idCodeName.length ; i++) {
		id[i] = idCodeName[i].split("%")[0];
	}
	for(var i = 0; i < id.length; i++) {
		if(id[i] != null && id[i] != ""){
			tree2.setCheck(id[i], 1);
		}
	}
}

/**
 * 显示学科树，设置节点选中、取消事件
 * @param {Object} id 这棵树的ID
 */
function showDisc(id) {
	if(tree2.isItemChecked(id) == 1) {
		setDisable(id);
	} else if(tree2.isItemChecked(id) == 0){
		abolish(id);
	}
}

/**
 * 设置父节点不能被选中
 * @param {Object} id 这棵树的ID
 */
function setDisable(id) {
	var rootId = $("#discipline1").attr("value");
	var ids = $("#hiddenId").attr("value");
	var hid = ids.split("; ");
	if(id == rootId) {
		if(tree2.isItemChecked(rootId) == 1) {
			unitExtService.getDispIdCodeNameById(id, addToHiddenId);
			unitExtService.getDispIdCodeNameById(id, addToHiddenId);
		}
	} else {
		if(tree2.isItemChecked(id) == 1){
			unitExtService.getDispIdCodeNameById(id, addToHiddenId);
		}
		var parent = tree2.getParentId(id);
		if(tree2.isItemChecked(parent) == 1) {
			for(var i = 0; i < hid.length; i++) { //如果需要删除数组中的父节点
				if(hid[i].indexOf(parent) >= 0) {
					hid.splice(i,1);
					break;
				}
			}
			$("#hiddenId").attr("value", hid.join("; "));
			tree2.setCheck(parent,0)
		}
		tree2.disableCheckbox(parent,1);
		setDisable(parent);
	}
}

/**
 * 取消父节点不能选中
 * @param {Object} id 这棵树的ID
 */
function abolish(id) {
	var parent = tree2.getParentId(id);
	var ids = $("#hiddenId").attr("value");
	var hid = ids.split("; ");
	var hname = ids.split("; ");
	var rootId = $("#discipline1").attr("value");
	if(id == rootId) {
		for(var i = 0;i < hid.length; i++) { //如果需要删除数组中的父节点
			if(hid[i].indexOf(rootId) >= 0) {
				hid.splice(i,1);
				break;
			}
		}
		$("#hiddenId").attr("value", hid.join("; "));
	} else {
		for(var i = 0; i < hid.length; i++) {
			if(hid[i].indexOf(id) >= 0) {
				hid.splice(i,1);
				break;
			}
		}
		$("#hiddenId").attr("value", hid.join("; "));
		var leaves = tree2.getAllSubItems(parent);
		if(leaves != null && leaves.length > 0) {
			var leaf = leaves.split(",");
			var flag = 0
			for(var j = 0; j < leaf.length; j++) {
				if(tree2.isItemChecked(leaf[j]) == 1) {
					flag = 1;
					break;
				}
			}
			if(flag == 0) {
				tree2.disableCheckbox(parent,0);
			}
		}
		this.abolish(parent);
	}
	showSelected($("#hiddenId").attr("value"));// 显示已选项
}

/**
 * 通过删除按钮取消
 * @param {Object} id
 */
function deleteDiscp(id) {
	try {
		tree2.setCheck(id, 0);
		abolish(id);
	}catch(error){
		var ids = $("#hiddenId").attr("value");
		var hid = ids.split("; ");
		for(var i = 0; i < hid.length; i++) {
			if(hid[i].indexOf(id) >= 0) {
				hid.splice(i,1);
				break;
			}
		}
		$("#hiddenId").attr("value", hid.join("; "));
		showDetail($("#hiddenId").attr("value"));// 显示已选项
	}
}

/**
 * 将已选项添加到隐藏域
 * @param {Object} idCodeName 学科代码名称字符串
 */
function addToHiddenId(idCodeName) {
	var ids = $("#hiddenId").attr("value");
	if(ids.indexOf(idCodeName) == -1) {//去掉重复项
		if (ids == null || ids == "null" || ids == "") {
			$("#hiddenId").attr("value", idCodeName);
		} else {
			$("#hiddenId").attr("value", ids + "; " + idCodeName);
		}
	}
	
	showSelected($("#hiddenId").attr("value"));// 显示已选项
}

/**
 * 显示已选学科
 * @param {Object} idCodeName 学科代码名称字符串
 */
function showDetail(idCodeName) {
	$("#showselect").empty();
	if(idCodeName != null && idCodeName != "") {
		// 将学科代码名称拆分为数组
		var xk = idCodeName.split('; ');
		for(var i = 0; i < xk.length; i++) {
			var num = i+1;
			var code_name = xk[i].split("%");
			var del_link = $("<a id='" + code_name[0] + "' class='del_xk' href='' title='点击删除此项'><img src='image/del.gif' /></a>");
			var row = $("<tr></tr>");// 一行
			var cell1 = $("<td></td>");// 一列
			var cell2 = $("<td></td>");// 一列
			var cell3 = $("<td></td>");// 一列
			var cell4 = $("<td></td>");// 一列
			cell1.append(num);
			cell2.append(code_name[2]);
			cell3.append(code_name[1]);
			cell4.append(del_link);
			row.append(cell1).append(cell2).append(cell3).append(cell4);
			$("#showselect").append(row);
		}
	}
}

/**
 * 显示当前选择
 */
function showSelected(idCodeName) {
	$("#trimedName").empty();
	if(!!idCodeName) {
		idCodeName = idCodeName.split("; ");
		for(var i = 0; i < idCodeName.length; i ++) {
			var temp = idCodeName[i].split("%");
			idCodeName[i] = [];
			idCodeName[i][0] = temp[0];
			idCodeName[i][1] = temp[1] + temp[2];
		}
		for(var i = 0 ; i < idCodeName.length; i ++) {
			if(i == 0 ) var trimName = (idCodeName[i][1].length > 71) ? idCodeName[i][1].substring(0, 70) + "……" : idCodeName[i][1];
			 else  var trimName = "; " + ( (idCodeName[i][1].length > 71) ? idCodeName[i][1].substring(0, 70) + "……" : idCodeName[i][1] );
			$("<span>").html(trimName).attr("id", idCodeName[i][0]).append('<a class = "cancelSelectedItem"  href="javascript:void(0)" title="点击删除" ><img id="image" src="image/del.gif" /></a>').appendTo("#trimedName");
		}
	}
	
}
$(".cancelSelectedItem").live("click", function(){
	var id = $(this).parent().attr("id");
	try {
		tree2.setCheck(id, 0);
		abolish(id);
	}catch(error){}
		var ids = $("#hiddenId").attr("value");
		var hid = ids.split("; ");
		for(var i = 0; i < hid.length; i++) {
			if(hid[i].indexOf(id) >= 0) {
				hid.splice(i,1);
				break;
			}
		}
		$("#hiddenId").attr("value", hid.join("; "));
		showSelected($("#hiddenId").attr("value"));// 显示已选项
})
/**
 * 获取隐藏域中的已选学科字符串，并作处理
 */
function getValues(ob) {
	var ids = $("#hiddenId").attr("value");
	if (ids != null && ids != "") {
		var id = ids.split('; ');
		for (var i = 0; i < id.length; i++) {
			id[i] = id[i].substring(id[i].indexOf("%") + 1);
		}
		ids = id.join("; ")
		ids = ids.replace(/%/g,"/");
		thisPopLayer.callBack(ids);
		thisPopLayer.destroy();
	} else {
		if(confirm("您还没有选择学科,确定退出选择吗？")) {
			thisPopLayer.callBack(ids);
			thisPopLayer.destroy();
		}
	}
}

/**
 * 将已选学科添加到隐藏域，并在右侧显示，每选一项都重新生成
 * @param {Object} idCodeName 学科代码名称字符串
 */
function getIdCodeName(idCodeName) {
	$("#hiddenId").attr("value", idCodeName);
	showSelected(idCodeName);
}

/**
 * 载入页面时将父页面中的值同步到弹出页面
 */
function sync() {
	var mainIfm = parent.document.getElementById("main");
	var relyDisciplineId = thisPopLayer.inData + "";//兼顾弹出层中弹出层
	unitExtService.getDispIdCodeNameByCodeName(relyDisciplineId,getIdCodeName);
}

/**
 * 绑定确定、取消事件
 */
$(document).ready(function() {
	sync();
	$(".del_xk").live("click", function() {
		deleteDiscp(this.id);
		return false;
	});
	$("#confirm").unbind("click").click(function() {
		getValues(this);
	});
});