// ========================================================================
// 文件名：asset.js
//
// 文件说明：
//     本文件主要实现资产管理模块中页面的动态功能，包括全选、输入框的动态显示、
// 校验等。
//
// 历史记录：
// 2009-12-28  刘雅琴：创建文件，完成基本功能。
// 2010-01-20  周中坚：补充内容。
// ========================================================================

function addInput(num) {
	var newTr, newTd;
	resetAll();
	var objTable = document.getElementById("oTable");
	for (i = 0; i < num; i = i + 1) {
		newTr = objTable.insertRow();
		newTd = newTr.insertCell();
		newTd.width = '22%';
		newTd.innerText = i + 1;
		newTd = newTr.insertCell();
		newTd.innerHTML = "<input type=\"text\" name=\"" + (i + 1)
				+ "\" id=\"" + (i + 1) + "\" />";
	}
}

function resetAll() {
	var objTable = document.getElementById("oTable");
	var rowNum = objTable.rows.length;
	for ( var i = 0; i < rowNum; i=i+1) {
		objTable.deleteRow(i);
		rowNum = rowNum - 1;
		i = i - 1;
	}
}

//==============================================================
//函数名：selectpie
//函数描述：根据用户选择显示图形，未使用。
//返回值：无
//==============================================================	
function selectpie(num) {
	if (num == 1 && document.getElementById("chname").checked == true) {
		document.getElementById("namepie").style.display = "block";
	} else if (num == 1 && document.getElementById("chname").checked == false) {
		document.getElementById("namepie").style.display = "none";
	} else if (num == 2 && document.getElementById("chtype").checked == true) {
		document.getElementById("typepie").style.display = "block";
	} else if (num == 2 && document.getElementById("chtype").checked == false) {
		document.getElementById("typepie").style.display = "none";
	} else if (num == 3 && document.getElementById("chstatus").checked == true) {
		document.getElementById("statuspie").style.display = "block";
	} else if (num == 3 && document.getElementById("chstatus").checked == false) {
		document.getElementById("statuspie").style.display = "none";
	}
}

//==============================================================
//函数名：addNameByUser
//函数描述：根据用户选择是否自定义资产名，显示输入框
//返回值：无
//==============================================================	
function addNameByUser(ob){
	var txt = document.getElementById(ob).value;
	if(txt == "自定义"){
		document.getElementById("fromuser").style.display = "block";
		addLoadEvent($("#form_asset").validate());
	}
	else{
		document.getElementById("fromuser").style.display = "none";
 		document.getElementById("table_name").getElementsByTagName("td")[1].innerHTML = "";
	}
}

//==============================================================
//函数名：selectType
//函数描述：添加与修改时根据用户选择的资产类型显示资产名
//返回值：无
//==============================================================	
function selectType(ob){
	var txt = document.getElementById(ob).value;
	var v1 = document.getElementById("fromdb1").value;
	var v2 = document.getElementById("fromdb2").value;
	var v3 = document.getElementById("fromdb3").value;
	var v4 = document.getElementById("fromdb4").value;
	document.getElementById("fromuser").style.display = "none";
	if(txt == "-1"){
//		document.getElementById("tdfromdb").style.display = "block";
		document.getElementById("fromdb1").style.display = "none";
		document.getElementById("fromdb1").disabled = true;
		document.getElementById("fromdb2").style.display = "none";
		document.getElementById("fromdb2").disabled = true;
		document.getElementById("fromdb3").style.display = "none";
		document.getElementById("fromdb3").disabled = true;
		document.getElementById("fromdb4").style.display = "none";
		document.getElementById("fromdb4").disabled = true;
	}
	if(txt == "软件"){
//		document.getElementById("tdfromdb").style.display = "block";
		document.getElementById("fromdb1").style.display = "block";
		document.getElementById("fromdb1").disabled = false;
		document.getElementById("fromdb2").style.display = "none";
		document.getElementById("fromdb2").disabled = true;
		document.getElementById("fromdb3").style.display = "none";
		document.getElementById("fromdb3").disabled = true;
		document.getElementById("fromdb4").style.display = "none";
		document.getElementById("fromdb4").disabled = true;
		if(v1 == "自定义"){
			document.getElementById("fromuser").style.display = "block";
		}
	}
	else if(txt == "设备"){
//		document.getElementById("tdfromdb").style.display = "block";
		document.getElementById("fromdb1").style.display = "none";
		document.getElementById("fromdb2").style.display = "block";
		document.getElementById("fromdb3").style.display = "none";
		document.getElementById("fromdb4").style.display = "none";
		document.getElementById("fromdb1").disabled = true;
		document.getElementById("fromdb2").disabled = false;
		document.getElementById("fromdb3").disabled = true;
		document.getElementById("fromdb4").disabled = true;
		if(v2 == "自定义"){
			document.getElementById("fromuser").style.display = "block";
		}
	}
	else if(txt == "家具"){
//		document.getElementById("tdfromdb").style.display = "block";
		document.getElementById("fromdb1").style.display = "none";
		document.getElementById("fromdb2").style.display = "none";
		document.getElementById("fromdb3").style.display = "block";
		document.getElementById("fromdb4").style.display = "none";
		document.getElementById("fromdb1").disabled = true;
		document.getElementById("fromdb2").disabled = true;
		document.getElementById("fromdb3").disabled = false;
		document.getElementById("fromdb4").disabled = true;
		if(v3 == "自定义"){
			document.getElementById("fromuser").style.display = "block";
		}
	}
	else if(txt == "其他"){
//		document.getElementById("tdfromdb").style.display = "block";
		document.getElementById("fromdb1").style.display = "none";
		document.getElementById("fromdb2").style.display = "none";
		document.getElementById("fromdb3").style.display = "none";
		document.getElementById("fromdb4").style.display = "block";
		document.getElementById("fromdb1").disabled = true;
		document.getElementById("fromdb2").disabled = true;
		document.getElementById("fromdb3").disabled = true;
		document.getElementById("fromdb4").disabled = false;
		if(v4 == "自定义"){
			document.getElementById("fromuser").style.display = "block";
		}
	}
	document.getElementById("divid").innerHTML = "";
//	document.getElementById("td1").style.display = "none";
}

//==============================================================
//函数名：selectTypeSta
//函数描述：统计时根据用户选择的资产类型显示资产名
//返回值：无
//==============================================================	
function selectTypeSta(ob){
	var txt = document.getElementById(ob).value;
	if(txt == "全部"){
		document.getElementById("softsta").style.display = "none";
		document.getElementById("softsta").disabled = true;
		document.getElementById("hardsta").style.display = "none";
		document.getElementById("hardsta").disabled = true;
		document.getElementById("fursta").style.display = "none";
		document.getElementById("fursta").disabled = true;
		document.getElementById("othersta").style.display = "none";
		document.getElementById("othersta").disabled = true;
	}
	if(txt == "软件"){
		document.getElementById("softsta").style.display = "block";
		document.getElementById("softsta").disabled = false;
		document.getElementById("hardsta").style.display = "none";
		document.getElementById("hardsta").disabled = true;
		document.getElementById("fursta").style.display = "none";
		document.getElementById("fursta").disabled = true;
		document.getElementById("othersta").style.display = "none";
		document.getElementById("othersta").disabled = true;
		}
	else if(txt == "设备"){
		document.getElementById("softsta").style.display = "none";
		document.getElementById("hardsta").style.display = "block";
		document.getElementById("fursta").style.display = "none";
		document.getElementById("othersta").style.display = "none";
		document.getElementById("softsta").disabled = true;
		document.getElementById("hardsta").disabled = false;
		document.getElementById("fursta").disabled = true;
		document.getElementById("othersta").disabled = true;
	}
	else if(txt == "家具"){
		document.getElementById("softsta").style.display = "none";
		document.getElementById("hardsta").style.display = "none";
		document.getElementById("fursta").style.display = "block";
		document.getElementById("othersta").style.display = "none";
		document.getElementById("softsta").disabled = true;
		document.getElementById("hardsta").disabled = true;
		document.getElementById("fursta").disabled = false;
		document.getElementById("othersta").disabled = true;
	}
	else if(txt == "其他"){
		document.getElementById("softsta").style.display = "none";
		document.getElementById("hardsta").style.display = "none";
		document.getElementById("fursta").style.display = "none";
		document.getElementById("othersta").style.display = "block";
		document.getElementById("softsta").disabled = true;
		document.getElementById("hardsta").disabled = true;
		document.getElementById("softsta").disabled = true;
		document.getElementById("othersta").disabled = false;
	}
}

/*
function pageRefresh(){
	try{
		document.execCommand('Refresh',false,0);
	}catch(BorwerSupportException){
		window.location.reload();
	}
}
*/

$("#transfer").live("click", function(){
	var assetid = $(this).attr("myvalue");
	var getPara = function(obj){
		return "?rsper=" + obj["rsper"] + "&begtime=" + obj["begtime"] + "&usage=" + 
			obj["usage"];
	};
	transferAsset({
		title : "资产移交",
//		src : "asset/transferAsset.action" + "?assetid=" + assetid,
		src : "asset/transferAsset.action",
		callBack : function(result){
			var url = basePath + "asset/confirmTransferAsset.action" + getPara(result) +  "&assetid=" + assetid;
			location.href = url;
			this.destroy();
	}
	});
});


function transfer(ob) // 多选框的名称
{
	// 先判断是否有复选框被选中
	var cnt = count(ob);
	if (cnt == 0) {
		alert("请选择要移交的资产项！");
		return false;
	} else {
		var sel_box = $("input[type='checkbox'][name='delid']:checked");
		var assetid = "";
		sel_box.each(function(index){
			assetid +=  $(this).val() + ";";
		});
	}
		var getPara = function(obj){
		return "?rsper=" + obj["rsper"] + "&begtime=" + obj["begtime"] + "&usage=" + 
			obj["usage"];
	};
	transferAsset({
		title : "资产移交",
//		src : "asset/transferAsset.action" + "?assetid=" + assetid,
		src : "asset/transferAsset.action",
		callBack : function(result){
			var url = basePath + "asset/groupConfirmTransferAsset.action" + getPara(result) +  "&assetid=" + assetid;
			location.href = url;
			this.destroy();
	}
	});
}