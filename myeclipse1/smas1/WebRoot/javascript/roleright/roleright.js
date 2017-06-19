// ========================================================================
// 文件名：roleright.js
//
// 文件说明：
//     本文件主要实现添加和修改权限时动态添加删除行
// 
// 历史记录：
// 2010-01-28 龚凡  创建文件。
// ========================================================================

// ==============================================================
// 方法名: addaction
// 方法描述: 本方法主要实现添加权限时，添加url表格的一行
// 返回值：无
// ==============================================================
function addaction()
{
	// 获取table dom
	var idTB = document.getElementById("groupaction");
	// 在列表第一行插入一行
	var oTR = idTB.insertRow(1);
	// 插入一个单元格
	var oTD = oTR.insertCell(0);
	oTD.className = "wd14";
	// 插入一个单元格
	oTD = oTR.insertCell(1);
	oTD.className = "wd15";
    oTD.innerHTML = "<input type='text' name='actionurlarray' class='input1'/>";
	// 插入一个单元格
	oTD = oTR.insertCell(2);
	oTD.className = "wd16";
	oTD.innerHTML = "<input type='text' name='actiondesarray' class='input1'/>";
	// 插入一个单元格
	oTD = oTR.insertCell(3);
	oTD.className = "wd14";
	oTD.innerHTML = "<img src='image/del_item.gif' onclick='delaction(this);'/>";
	// 重新编序号
	addNum("groupaction");
	addLoadEvent($("#form_roleright").validate());
}

// ==============================================================
// 方法名: delaction
// 方法描述: 本方法主要实现添加权限时，删除url表格的指定行
// 返回值：无
// ==============================================================
function delaction(elem) // 鼠标所在位置
{
	// 获取table dom
	var idTB = document.getElementById("groupaction");
	// 获取当前行
	var row = elem.parentNode.parentNode;
	// 删除当前行
	idTB.deleteRow(row.rowIndex);
	// 重新编号
	addNum("groupaction");
}