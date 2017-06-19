// ========================================================================
// 文件名：project.js
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
function reMatchSelected() 
{
	var xx = document.getElementsByName('entityIds');
	var cnt = 0;
	var id = '';
	var x = 1;
	for(var i = 0; i < xx.length; i++) {
		if(xx[i].checked) {
			cnt++;
			id = id + xx[i].value + ';';
		}
	}
	if(cnt == 0) {
		alert('请选择至少一个项目！');
		return ;
	}
	if(id.length > 1) {
		id = id.substr(0, id.length - 1);
	}
	
	var url = 'project/toSelectExpert.action?entityId=' +  id;
	document.forms[0].action = url;
	document.forms[0].submit();
	
}

/**
 * 获取选中的项目
 */
function getCheckedPoj() {
	var xx = document.getElementsByName('entityIds');
	for(var i = 0; i < xx.length; i++) {
		if(xx[i].checked) {
			var v = document.getElementsByName("selectPojIds")[0].value;
			if(v.indexOf(xx[i].value) < 0 && v.length > 0) {
				document.getElementsByName("selectPojIds")[0].value = v + ';' + xx[i].value;
			}
			else if(v.length == 0)
				document.getElementsByName("selectPojIds")[0].value = xx[i].value;
		}
		else {
			var b = document.getElementsByName("selectPojIds")[0].value;
			if(b.indexOf(xx[i].value) >= 0) {
				var t = '';
				var cc = b.split(';');
				for(var j = 0; j < cc.length; j++) {
					if(cc[j].indexOf(xx[i].value) < 0) {
						t = t + cc[j] + ';'
					}
				}
				if(t.length > 0)
					t = t.substr(0, t.length - 1);
				document.getElementsByName("selectPojIds")[0].value = t;
			}
		}
	}
}

/**
 * 初始化选中的项目
 */
function initCheckedPoj() {
	var v = document.getElementsByName("selectPojIds")[0].value;
	var xx = document.getElementsByName('entityIds');
	for(var i = 0; i < xx.length; i++) {
		if(v.indexOf(xx[i].value) >= 0)
			xx[i].checked = true;
	}
}
function listPage(data) {
	url = 'project/listProject.action?pageNumber=' + data;
	document.forms[0].action = url;
	document.forms[0].submit();
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

function setReview(ob, // 复选框ID
			ff, // form表单名称
			flag) // 评审状态
{
	// 判断是否有专家被选中
	var cnt = count(ob);
	if (cnt == 0) {
		if (flag == 0) {
			alert("请选择要退出评审的项目！");
		} else if (flag == 1) {
			alert("请选择要参加评审的项目！");
		} else {
			alert("无效的项目参评状态！");
		}
		return false;
	} else {
		if (flag == 0){
			if (confirm("您确定要让选中的项目退出评审吗？")) {
				document.forms[ff].action = "project/disableReview.action";
				document.forms[ff].submit();
				return true;
			}
		} else if (flag == 1) {
			if (confirm("您确定要让选中的项目参加评审吗？")) {
				document.forms[ff].action = "project/enableReview.action";
			document.forms[ff].submit();
			return true;
			}
		} else {
			alert("无效的项目参评状态！");
			return false;
		}
	}
}

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
				document.forms[ff].action = "project/disGrant.action";
				document.forms[ff].submit();
				return true;
			}
		} else {
			if (confirm("您确定要让选中的项目立项吗？")) {
				document.forms[ff].action = "project/grant.action";
				document.forms[ff].submit();
				return true;
			}
		}
	}
}

function exportProject(label) {
	if (label == 0) {// 退评项目
		if (confirm('您确定要导出当前项目数据吗？')) {
			document.location.href = "project/exportProject.action?exportAll=0";
			return true;
		} else {
			return false;
		}
	} else {// 参评项目
		var label = prompt("是否包含评审信息（y/n）？", "y");
		if (label != null) {
			label = label.toLowerCase();
			if (label === "y") {
				var ob = document.getElementById('exppro');
				document.location.href = "project/exportProjectReviewer.action?exportAll=0";
				return true;
			}
			else 
				if (label === "n") {
					var ob = document.getElementById('exppro');
					document.location.href = "project/exportProject.action?exportAll=0";
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

function doSelect() {
	var iframeObj = document.getElementById('expertTreeFrame').contentDocument;
	var exps = iframeObj.getElementById('selectedExpertIds').value;
	if(exps.length == 0 || exps == null) {
		alert('请至少选择一个专家！');
		return ;
	}
	document.getElementById('selectedExpertIds').value = exps;
	document.forms['selectReviewer'].action = 'project/reMatchExpert.action';
	document.forms['selectReviewer'].submit();
}
