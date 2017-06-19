// ========================================================================
// 文件名：common.js
//
// 文件说明：
//     本文件主要实现诸如全选、删除提示等功能
// 
// 历史记录：
// 2010-01-28 龚凡  创建文件。
// 2010-03-04 周中坚  添加函数。
// ========================================================================


var msg = new Array(99);
msg[0] = "请选择要删除的项！"
msg[1] = "您确定要删除选中项吗？"
msg[2] = "无可删除项！"
msg[3] = "关键词请勿超过20个字符！"
msg[4] = "此处不得为空 ！"
msg[5] = "请勿超过10000个字符 ！"
msg[6] = "请选择要发送的邮件！"
msg[7] = "您确定要重新发送选中的邮件吗？"
msg[8] = "请选择要暂停发送的邮件！"
msg[9] = "您确定要暂停发送选中的邮件吗？"

// ==============================================================
// 函数名：addLoadEvent
// 函数描述：定义某些操作完成js事件的重新加载
// 返回值：无
// by Simon Willison 
// ==============================================================
function addLoadEvent(func) {
  var oldonload = window.onload;
  if (typeof window.onload != 'function') {
    window.onload = func;
  } else {
    window.onload = function() {
      if (oldonload) {
        oldonload();
      }
      func();
    }
  }
}

// ==============================================================
// 方法名: checkAll
// 方法描述: 本方法主要实现列表复选框的全选与不选
// 返回值：无
// ==============================================================
function checkAll(e, // 复选框选中状态
		ob) // 复选框名称
{
	// 获取复选框对象数组
	var sel_box = document.getElementsByName(ob);
	// 设置复选框状态
	for ( var i = 0; i < sel_box.length; i += 1) {
		sel_box[i].checked = e;
	}
}

// ==============================================================
// 方法名: count
// 方法描述: 本方法主要实现列表复选框选中个数的统计
// 返回值：
// cnt: 复选框被选中的个数
// ==============================================================
function count(ob) // 复选框名称
{
	// 获取复选框对象数组
	var sel_box = document.getElementsByName(ob);
	// 选中个数
	var cnt = 0;
	// 统计
	for ( var i = 0; i < sel_box.length; i += 1) {
		if (sel_box[i].checked) {
			cnt += 1;
		}
	}
	return cnt;
}

// ==============================================================
// 方法名: delSelected
// 方法描述: 本方法主要实现列表的群删功能
// 返回值：
// false: 不提交请求
// ==============================================================
function delSelected(ob, // 复选框名称
		ff) // 表单form的名称
{
	// 先判断是否有复选框被选中
	var cnt = count(ob);
	if (cnt == 0) {
		alert(msg[0]);
		return false;
	} else {
		if (confirm(msg[1])) {
			document.forms[ff].submit();
		}
	}
}

// ==============================================================
// 方法名: delRow
// 方法描述: 本方法主要实现删除列表最后一行
// 返回值：无
// ==============================================================
function delRow(ob) //列表的ID
{
    // 获取table dom
    var idTB = document.getElementById(ob);
    // 获取当前表格行数
    var i = idTB.rows.length;
    if (i <= 1) {
        alert(msg[2]);
    }
    else {
        idTB.deleteRow(i - 1);
    }
}

// ==============================================================
// 方法名: addNum
// 方法描述: 本方法主要实现列表序号的重写
// 返回值：无
// ==============================================================
function addNum(ob) // 列表的ID
{
	// 获取列表对象
	var idTB = document.getElementById(ob);
	// 开始编号
	for ( var i = 1; i < idTB.rows.length; i+=1) {
		idTB.rows[i].cells[0].innerHTML = i.toString();
	}
}

// ==============================================================
// 方法名: valid
// 方法描述: 本方法主要实现列表页面检索的输入框
// 返回值：
// false: 不提交请求
// ==============================================================
function valid(ob) {
	var cont = document.getElementById(ob).value;
	if (cont.length > 20) {
		alert(msg[3]);
		return false;
	}
}

// ==============================================================
// 方法名: validFCK
// 方法描述: 本方法主要实现FCk输入框的非空和字数验证
// 返回值：
// false: 不提交请求
// ==============================================================
function ValidFCK(EditorName) {
	var cont = FCKeditorAPI.GetInstance(EditorName);
	var str = cont.GetXHTML();
	var trimcont = str.replace(/<[^>]*>|<\/[^>]*>/gm, "");
	//将FCK编辑器中的html元素trim再计算长度
	if (trimcont === "") {
		alert(msg[4]);
		return false;
	} else if (trimcont.length > 10000) {
		alert(msg[5]);
		return false;
	}
}

// ==============================================================
// 方法名: toggle
// 方法描述: 本方法主要实现点一下出现，再点一下隐藏的效果
// 返回值：无
// ==============================================================
function toggle(Id) {
	var Div = document.getElementById(Id);
	if (Div) {
		var display = Div.style.display;
		if (display == 'none') {
			Div.style.display = 'block';
		} else if (display == 'block') {
			Div.style.display = 'none';
		}
	}
} 

function displayLeftFrame() {
	var ob = parent.getElementByName("left");
	if (ob == null) {
		alert("无法获取left帧");
	} else {
		if (ob.style.display == "block") {
			ob.style.display = "none";
		} else {
			ob.style.display = "block";
		}
	}
}
