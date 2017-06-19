// ========================================================================
// 文件名：general.js
//
// 文件说明：
//     本文件主要实现项目管理模块中相关功能。
//
// 历史记录：
// 2010-06-19  龚凡          创建文件，完成基本功能。
// ========================================================================

// ==============================================================
// 方法名: reMatchSelected
// 方法描述: 进入手动重新匹配页面
// 返回值：
// true: 提交请求
// false: 不提交请求
// ==============================================================
function grant(ob, // 复选框ID
				ff, // form表单名称
				flag) // 立项状态
				{
	var cnt = count(ob);
	if (cnt == 0) {
		if(flag == 0){
			alert("请选择需要取消立项的项目！");
		} else {
			alert("请选择需要立项的项目！");
		}
	} else {
		if(flag == 0){
			if (confirm("您确定要让选中的项目取消立项吗？")) {
				document.forms[ff].action = "project/general/disGrant.action";
				document.forms[ff].submit();
				return true;
			}
		} else {
			if (confirm("您确定要让选中的项目立项吗？")) {
				document.forms[ff].action = "project/general/grant.action";
				document.forms[ff].submit();
				return true;
			}
		}
	}
}

function exportProject(label) {
	if (label == 0) {// 退评项目
		if (confirm('您确定要导出当前项目数据吗？')) {
			document.location.href = "project/general/exportProject.action?exportAll=0";
			return true;
		} else {
			return false;
		}
	} else {// 参评项目
		var label = prompt("是否包含评审信息（y/n）？", "y");
		if (label != null) {
			label = label.toLowerCase();
			if (label === "y") {
				document.location.href = "project/general/exportProjectReviewer.action?exportAll=0";
				return true;
			}
			else 
				if (label === "n") {
					document.location.href = "project/general/exportProject.action?exportAll=0";
					return true;
				}
				else {
					alert("无效输入！");
					return false;
				}
		} else {
			return false;
		}
	}
}

/**
 * 手工匹配专家确定
 */
function doSelect() {
	var preYear=$(window.parent.document).find("#year[name=year]").val();
	var curYear=$(window.parent.document).find("#year>option[selected=selected]").val();
	if(preYear != curYear){
		alert("不允许对当年度项目做此操作");
	}else {
		var iframeObj = document.getElementById('expertTreeFrame').contentDocument;
		var exps = iframeObj.getElementById('selectedExpertIds').value;
		//取得一般项目评审专家数=一般项目部级评审专家数+一般项目地方评审专家数
		var GeneralMinistryExpertNumber = parseInt($("#GeneralMinistryExpertNumber").val());
		var GeneralLocalExpertNumber = parseInt($("#GeneralLocalExpertNumber").val());
		var projectExpertNumber = GeneralMinistryExpertNumber + GeneralLocalExpertNumber;
//		if(exps.length == 0 || exps == null) {
//			alert('请至少选择一个专家！');
//			return ;
//		}
		document.getElementById('selectedExpertIds').value = exps;
		var selectedExpertNum = exps.split(";").length;
		if(selectedExpertNum > projectExpertNumber){
			alert("项目分配专家数应≤" + projectExpertNumber);
			return false;
		}else{
			document.forms['selectReviewer'].action = 'project/general/reMatchExpert.action';
			document.forms['selectReviewer'].submit();
			
		}
	}
}

function toggle_view(nodeId) {
	var currentNode = document.getElementById(nodeId);
	var Div = currentNode.nextElementSibling || currentNode.nextSibling ;
	var lastChild =  currentNode.firstElementChild || currentNode.firstChild;
	if (Div) {
		if (lastChild.className == 'img_dis') {
			Div.style.display = 'none';
			lastChild.className = 'img_undis';
			
		} else if (lastChild.className == 'img_undis') {
			Div.style.display = 'block';
			lastChild.className = 'img_dis';
		}
	}
}
